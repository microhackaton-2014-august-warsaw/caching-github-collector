package com.ofg.microservice.github


public interface GithubCollector {
    void collectAndPassToAnalyzers(String twitterLogin, String pairId)
}