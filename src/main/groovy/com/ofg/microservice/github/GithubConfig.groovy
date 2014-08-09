package com.ofg.microservice.github

import groovy.transform.TypeChecked
import net.sf.ehcache.config.CacheConfiguration
import org.springframework.cache.CacheManager
import org.springframework.cache.ehcache.EhCacheCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@TypeChecked
class GithubConfig {

    public static final String GITHUB_URL = "https://api.github.com/"

    @Bean
    CacheManager cacheManager() {
        CacheConfiguration orgsCacheConfiguration = new CacheConfiguration()
        orgsCacheConfiguration.setName("orgs")
        orgsCacheConfiguration.setMemoryStoreEvictionPolicy("LRU")
        orgsCacheConfiguration.setMaxEntriesLocalHeap(1000)
        orgsCacheConfiguration.setTimeToLiveSeconds(60 * 10)

        CacheConfiguration reposCacheConfiguration = new CacheConfiguration()
        reposCacheConfiguration.setName("repos")
        reposCacheConfiguration.setMemoryStoreEvictionPolicy("LRU")
        reposCacheConfiguration.setMaxEntriesLocalHeap(1000)
        reposCacheConfiguration.setTimeToLiveSeconds(60 * 10)

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration()

        config.addCache(orgsCacheConfiguration)
        config.addCache(reposCacheConfiguration)

        return new EhCacheCacheManager(net.sf.ehcache.CacheManager.newInstance(config))
    }

}
