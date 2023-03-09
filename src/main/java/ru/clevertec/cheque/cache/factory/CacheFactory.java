package ru.clevertec.cheque.cache.factory;

import ru.clevertec.cheque.cache.Cache;
import ru.clevertec.cheque.cache.impl.LFUCache;
import ru.clevertec.cheque.cache.impl.LRUCache;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheFactory {
    public Cache createCache(String cacheType, int cacheSize) {
        return switch (cacheType) {
            case "LFU" -> new LFUCache(cacheSize);
            case "LRU" -> new LRUCache(cacheSize);
            default -> null;
        };
    }
}
