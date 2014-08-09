package com.ofg.microservice.github


public interface GithubCollector {
    void collectAndPassToAnalyzers(String twitterLogin, Long pairId)
}