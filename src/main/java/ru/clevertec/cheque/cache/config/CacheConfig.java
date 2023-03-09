package ru.clevertec.cheque.cache.config;

import ru.clevertec.cheque.annotation.conditional.ConditionalOnCorrectCacheProperties;
import ru.clevertec.cheque.cache.Cache;
import ru.clevertec.cheque.cache.factory.CacheFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnCorrectCacheProperties
public class CacheConfig {
    @Value("${cache.algorithm}")
    private String cacheType;
    @Value("${cache.size}")
    private int cacheSize;

    @Bean(name = "myCache")
    public Cache createBeanFromNonStaticMethodFactory() {
        CacheFactory factory = new CacheFactory();
        return factory.createCache(cacheType, cacheSize);
    }
}
