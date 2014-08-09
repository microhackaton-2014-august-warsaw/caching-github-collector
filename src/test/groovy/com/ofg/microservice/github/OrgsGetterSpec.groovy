package com.ofg.microservice.github

import spock.lang.Specification

/**
 * Created by Milosz Olszewski (olszewm1) on 2014-08-09.
 */
class OrgsGetterSpec extends Specification {
    OrgsGetter orgsGetter = new OrgsGetter()
    private String githubId = "szimano"

    def "should filter json"() {
        when:
            List orgs = orgsGetter.getOrgs(githubId)
        then:
            orgs.size() > 0
    }
}
