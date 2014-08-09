package com.ofg.microservice.github

import com.github.tomakehurst.wiremock.client.WireMock
import com.jayway.awaitility.Awaitility
import com.ofg.base.MicroserviceMvcWiremockSpec
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import java.util.concurrent.TimeUnit

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.jayway.awaitility.Awaitility.await
import static com.ofg.microservice.github.GithubCollectorWorker.GITHUB_TOPICS_ANALYZER_CONTENT_TYPE_HEADER
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
            await().atMost(5, TimeUnit.SECONDS).until({ wireMock.verifyThat(postRequestedFor(urlEqualTo("/topics-analyzer/api/analyze")).
                        withRequestBody(containing('githubId')).
                        withHeader("Content-Type", containing(GITHUB_TOPICS_ANALYZER_CONTENT_TYPE_HEADER.toString())))
            })
    }

    private ResultActions sendUsernameAndPairId() {
        mockMvc.perform(get("/$testUserGithubId/$pairId").
                accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
    }

    private analyzerRespondsOk() {
        stubInteraction(post(WireMock.urlEqualTo("/topics-analyzer/api/analyze")), aResponse().withStatus(OK.value()))
    }
}
