package com.agustinventura.apiproxy.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.agustinventura.apiproxy.dtos.KeyValue;

@Component
public class CacheHelper {

	@Value("${meliapiproxy.cache.endpoint}")
	String cacheUri;
	
	@Autowired
	RestTemplate restTemplate;
	
	final Logger logger = LoggerFactory.getLogger(CacheHelper.class);
	
	public String getDateTimeFromKey(String key, Integer idKeyType) {
		String minute = "";
		if(idKeyType==1) {
			minute = key.split("_")[1];
		}else if(idKeyType==2) {
			minute = key.split("_")[2];
		}
		return minute;
	}
	
	public void saveCache(String keyFilter) {
		KeyValue oKeyValue = new KeyValue();
		oKeyValue.setKey(keyFilter);
		oKeyValue.setValue(1);
		String uri = cacheUri;
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);
		KeyValue oKeyValueResponse = restTemplate.postForEntity(builder.build().encode().toUri(),oKeyValue, KeyValue.class).getBody();
	
		logger.info(oKeyValueResponse.getKey());
	}	
	
	public KeyValue getRequestsByKey(String key) {
		/*
		 * Voy a buscar a la cache el registro de esta key para terminar usando el value, 
		 * que me da la cantidad de requests que realizó esta key.
		 */
		
		KeyValue oKeyValue = new KeyValue();
		String uri = cacheUri;
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri).
				path(key);
		oKeyValue = restTemplate.getForEntity(builder.build().encode().toUri(), KeyValue.class).getBody();
		return oKeyValue;
	}
	
}
