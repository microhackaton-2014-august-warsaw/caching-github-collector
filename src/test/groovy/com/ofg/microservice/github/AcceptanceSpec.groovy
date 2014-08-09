package com.ofg.microservice.github

import com.jayway.awaitility.Awaitility
import com.ofg.base.MicroserviceMvcWiremockSpec
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import java.util.concurrent.TimeUnit

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.jayway.awaitility.Awaitility.await
import static com.ofg.infrastructure.base.dsl.WireMockHttpRequestMapper.wireMockPut
import static com.ofg.microservice.github.GithubCollectorWorker.TWITTER_PLACES_ANALYZER_MEDIA_TYPE
import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AcceptanceSpec extends MicroserviceMvcWiremockSpec {
    String pairId = '1'
    String testUserGithubId = 'ingwarsw'

    def "should return HTTP 200"() {
        given:
            analyzerRespondsOk()
        expect:
            await().atMost(20, TimeUnit.SECONDS).until({
                sendUsernameAndPairId().andExpect(status().isOk())
            })
    }

    def "should send github with pairId to analyzer"() {
        given:
            analyzerRespondsOk()
        when:
            sendUsernameAndPairId()
        then:
            await().atMost(5, TimeUnit.SECONDS).until({ wireMock.verifyThat(putRequestedFor(urlEqualTo("/sentence-analyzer/api/$pairId")).
                        withRequestBody(containing('[{"extraData":{')).
                        withHeader("Content-Type", equalTo(TWITTER_PLACES_ANALYZER_MEDIA_TYPE.toString())))
            })
    }

    private ResultActions sendUsernameAndPairId() {
        mockMvc.perform(get("/github/$testUserGithubId/$pairId").
                accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
    }

    private analyzerRespondsOk() {
        stubInteraction(wireMockPut("/sentence-analyzer/api/$pairId"), aResponse().withStatus(OK.value()))
    }
}