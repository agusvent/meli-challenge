package com.agustinventura.apiproxy;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
class MeliApiProxyApplicationTests {

	final Logger logger = LoggerFactory.getLogger(MeliApiProxyApplicationTests.class);
	
	@Value("${meliapiproxy.proxy.endpoint}")
	private String proxyUri;
	
	@Test
	void contextLoads() {
		
	}
	/*
	@Test
	public void testUnwishedIp() throws URISyntaxException {
		logger.info("TESTING UNWISHED IPs");
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = "http://localhost:8080/meli/categories/MLA1071";
	    URI uri = new URI(baseUrl);
	    ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
	    
	    String baseUrlProxyServices = "http://localhost:8080/proxyservices";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrlProxyServices);
		String unwishedIp = "127.0.0.1";
		result = restTemplate.postForEntity(builder.build().encode().toUri(),unwishedIp, String.class);
		try {
			result = restTemplate.getForEntity(uri, String.class);
		}catch(Exception ex) {
			//Assert.assertEquals(401, result.getStatusCodeValue());
			logger.info("TEST: "+(401==result.getStatusCodeValue()));
		}
		//Verify request succeed
	    
	    logger.info("END TESTING UNWISHED IPs");
	}*/
	

	/*@Test
	public void testMaxRequestsByClientAndPathPerMinute() throws URISyntaxException {
		logger.info("TESTING MAX REQUESTS BY CLIENT AND PATH PER MINUTE");
		RestTemplate restTemplate = new RestTemplate();
		String baseUrlDeleteAllUnwished = "http://localhost:8080/proxyservices/all";
		restTemplate.delete(baseUrlDeleteAllUnwished);
		
		String baseUrl = "http://localhost:8080/meli/categories/MLA1071";
	    URI uri = new URI(baseUrl);
	    ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
	    try {
		    result = restTemplate.getForEntity(uri, String.class);
		    result = restTemplate.getForEntity(uri, String.class);
		    result = restTemplate.getForEntity(uri, String.class);
		    result = restTemplate.getForEntity(uri, String.class);
		    result = restTemplate.getForEntity(uri, String.class);
		    result = restTemplate.getForEntity(uri, String.class);
	    }catch(Exception ex) {
	    	//Assert.assertEquals(429, result.getStatusCodeValue());
	    	logger.info("TEST: "+(429==result.getStatusCodeValue()));
	    }

	    logger.info("END TESTING MAX REQUESTS BY CLIENT AND PATH PER MINUTE");
	}*/


}
