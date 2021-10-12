package com.agustinventura.melichallenge.cache.services;

import com.agustinventura.melichallenge.cache.dtos.KeyValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheServiceImpl implements CacheService {

    private ConcurrentHashMap<String,Integer> chmCache = new ConcurrentHashMap<String,Integer>();

    public List<KeyValue> findAllKeys() {
        List<KeyValue> lKeyValues = new ArrayList<KeyValue>();
        Enumeration keys = chmCache.keys();
        while(keys.hasMoreElements()) {
            Object key = keys.nextElement();
            KeyValue oKeyValue = new KeyValue();
            oKeyValue.setKey(key.toString());
            oKeyValue.setValue(chmCache.get(key));
            lKeyValues.add(oKeyValue);
        }

        return lKeyValues;
    }

    public Optional<KeyValue> findByKey(String key) {
        KeyValue oKeyValue = new KeyValue();
        oKeyValue.setKey(key);
        Integer value = chmCache.get(key);
        if(value!=null) {
            oKeyValue.setValue(value);
        }else{
            oKeyValue.setValue(0);
        }
        return Optional.ofNullable(oKeyValue);
    }

    public KeyValue saveInCache(KeyValue oKeyValue) {
        try {
        	/*
        	 * getOrDefault te trae el valor que tenga esa key y si es nulo, 
        	 * definis que 0 sea el valor por defecto
        	 */
            chmCache.put(oKeyValue.getKey(), chmCache.getOrDefault(oKeyValue.getKey(),0)+1);
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            oKeyValue = null;
        }
        return oKeyValue;
    }
}
