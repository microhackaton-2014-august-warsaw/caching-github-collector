package com.ofg.microservice.github

import groovy.json.JsonSlurper
import groovy.transform.PackageScope
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

/**
 * Created by Milosz Olszewski (olszewm1) on 2014-08-09.
 */
@TypeChecked
@Component
@PackageScope
@Slf4j
class ReposGetter {

    String oauthToken

    @Autowired
    ReposGetter(@Value('${oauthToken}') String oauthToken) {
        this.oauthToken = oauthToken
    }

    @Cacheable("github")
    List<Object> getRepos(String githubLogin) {
        ClientHttpRequestInterceptor acceptHeaderGithub = new AcceptHeaderHttpRequestInterceptor();

        RestTemplate restTemplate = new RestTemplate()
//        restTemplate.setInterceptors([acceptHeaderGithub]);

        String retVal = new RestTemplate().getForObject(GithubConfig.GITHUB_URL + "users/${githubLogin}/repos?access_token=${oauthToken}", String.class)

        List<Object> result = (List<Object>) new JsonSlurper().parseText(retVal)

        log.debug(result.toString())

        result
    }
}
