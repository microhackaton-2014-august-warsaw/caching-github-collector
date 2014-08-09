package com.ofg.microservice.github

import groovy.transform.PackageScope
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

/**
 * Created by Milosz Olszewski (olszewm1) on 2014-08-09.
 */
@TypeChecked
@Component
@PackageScope
class OrgsGetter extends GithubGetter {

    @Autowired
    OrgsGetter(@Value('${oauthToken}') String oauthToken) {
        super(oauthToken)
    }

    @Cacheable("orgs")
    List<Object> getOrgs(String githubLogin) {
        getData("users/${githubLogin}/orgs", githubLogin)
    }
}
