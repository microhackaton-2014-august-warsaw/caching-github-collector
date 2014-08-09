package com.ofg.microservice.github

import groovy.json.JsonSlurper
import groovy.transform.PackageScope
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

/**
 * Created by Milosz Olszewski (olszewm1) on 2014-08-09.
 */
@TypeChecked
@PackageScope
@Slf4j
abstract class GithubGetter {
    String oauthToken

    GithubGetter(String oauthToken) {
        this.oauthToken = oauthToken
    }

    List<Object> getData(String uri, String githubLogin) {
        try {
            ClientHttpRequestInterceptor acceptHeaderGithub = new AcceptHeaderHttpRequestInterceptor();

            RestTemplate restTemplate = new RestTemplate()
            restTemplate.setInterceptors((List<ClientHttpRequestInterceptor>) [acceptHeaderGithub]);

            String retVal = new RestTemplate().getForObject(GithubConfig.GITHUB_URL + "${uri}?access_token=${oauthToken}", String.class)

            List<Object> result = (List<Object>) new JsonSlurper().parseText(retVal)

            log.debug(result.toString())

            result
        } catch (HttpClientErrorException ex) {
            def httpCode = ex.getStatusCode();
            log.error("HTTP ${httpCode} for githubLogin ${githubLogin}")
            []
        }
    }
}
