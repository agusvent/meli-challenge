package com.agustinventura.melichallenge.cache.services;

import com.agustinventura.melichallenge.cache.dtos.KeyValue;

import java.util.Optional;

public interface CacheService {
    public Iterable<KeyValue> findAllKeys();

    public Optional<KeyValue> findByKey(String key);

    public KeyValue saveInCache(KeyValue oKeyValue);
}
