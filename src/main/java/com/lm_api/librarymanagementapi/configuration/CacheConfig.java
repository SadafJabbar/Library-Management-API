package com.lm_api.librarymanagementapi.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean       //cache manager is a spring interface for managing chaches
    public CacheManager cacheManager(){
        CaffeineCacheManager cacheManager=new CaffeineCacheManager(
                "books",
                "users",
                "categories",
                "loans"
        );
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(5, TimeUnit.HOURS)
                        .maximumSize(500)
        );
        return cacheManager;
    }
}
