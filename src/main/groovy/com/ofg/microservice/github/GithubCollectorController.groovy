package com.ofg.microservice.github

import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

import static org.springframework.util.Assert.notNull
import static org.springframework.util.StringUtils.hasText

@TypeChecked
@RestController
class GithubCollectorController {
    private GithubCollector collectorWorker

    @Autowired
    GithubCollectorController(GithubCollector collectorWorker) {
        this.collectorWorker = collectorWorker
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/{githubLogin}/{pairId}", produces="application/json", method = RequestMethod.GET)
    void getGithubInfo(@PathVariable String githubLogin, @PathVariable Long pairId) {
        hasText(githubLogin); notNull(pairId)
        collectorWorker.collectAndPassToAnalyzers(githubLogin, pairId)
    }
}