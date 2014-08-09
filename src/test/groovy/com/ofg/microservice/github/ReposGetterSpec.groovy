package com.ofg.microservice.github

import spock.lang.Specification

/**
 * Created by Milosz Olszewski (olszewm1) on 2014-08-09.
 */
class ReposGetterSpec extends Specification {
    ReposGetter reposGetter = new ReposGetter("")

    private String githubId = "szimano"

    def "should filter json"() {
        when:
        List repos = reposGetter.getRepos(githubId)
        then:
        repos.size() > 0
    }

    def "should return empty list"() {
        when:
        List repos = reposGetter.getRepos("uuuuuuuuuuuuuuuuuuuuuuuuuuNoSuchUser")
        then:
        repos.size() == 0
    }
}
