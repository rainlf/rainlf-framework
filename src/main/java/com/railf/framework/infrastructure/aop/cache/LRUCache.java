package com.railf.framework.infrastructure.aop.cache;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : rain
 * @date : 2021/5/6 14:05
 */
@Component
public class LRUCache {
    private final int CACHE_CAPACITY = 1000;
    // seconds
    private final int EXPIRED_SECOND = 30;

    private final LRUMap<String, Object> lruMap = new LRUMap<>(CACHE_CAPACITY);
    private final ConcurrentHashMap<String, LocalTime> timerMap = new ConcurrentHashMap<>();
    private final Lock lock = new ReentrantLock();

    public Object get(String key) {
        Object result;
        if (!exist(key)) {
            return null;
        }

        try {
            lock.lock();
            result = lruMap.get(key);
        } finally {
            lock.unlock();
        }
        return result;
    }

    public void put(String key, Object object) {
        timerMap.put(key, LocalTime.now());
        try {
            lock.lock();
            lruMap.put(key, object);
        } finally {
            lock.unlock();
        }
    }

    public Boolean exist(String key) {
        LocalTime putTime = timerMap.get(key);
        if (putTime != null && LocalTime.now().isAfter(putTime.plusSeconds(EXPIRED_SECOND))) {
            timerMap.remove(key);
            return false;
        }
        return true;
    }

    private static class LRUMap<K, V> extends LinkedHashMap<K, V> {
        private static final float HASH_TABLE_LOAD_FACTOR = 0.75f;
        private final int capacity;

        LRUMap(int capacity) {
            super((int) Math.ceil(capacity / HASH_TABLE_LOAD_FACTOR), HASH_TABLE_LOAD_FACTOR, true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > capacity;
        }
    }
}
