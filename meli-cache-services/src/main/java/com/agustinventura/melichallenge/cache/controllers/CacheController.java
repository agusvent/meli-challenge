package com.agustinventura.melichallenge.cache.controllers;

import com.agustinventura.melichallenge.cache.dtos.KeyValue;
import com.agustinventura.melichallenge.cache.services.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping
    public ResponseEntity<?> createInCache(@RequestBody KeyValue oKeyValue){
        return ResponseEntity.status(HttpStatus.CREATED).body(cacheService.saveInCache(oKeyValue));
    }

    @GetMapping
    public List<KeyValue> getKeys(){
        return (List<KeyValue>) cacheService.findAllKeys();
    }

    @GetMapping("/hi")
    public String sayHi(){
        return "hi!";
    }

    @GetMapping("/{key}")
    public ResponseEntity<?> getKey(@PathVariable(value = "key") String key){
        Optional<KeyValue> oKeyValue = cacheService.findByKey(key);

        if(!oKeyValue.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(oKeyValue);
    }
}
