package com.agustinventura.apiproxy.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.agustinventura.apiproxy.services.ProxyServices;


@Component
public class ProxyHelper {

	@Value("${meliapiproxy.proxy.endpoint}")
	private String proxyUri;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ProxyServices proxyServices;
	
	final Logger logger = LoggerFactory.getLogger(ProxyHelper.class);
	
	public boolean isUnwishedClient(String clientIp) {
		logger.info("DENTRO DE PROXY HELPER: "+clientIp);
		/*
		 * Voy a buscar al servicio de proxy si esta IP es no deseada
		 */
		logger.info(proxyServices.isIpUnwished(clientIp).toString());
		return proxyServices.isIpUnwished(clientIp);
		/*String uri = proxyUri;
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri).path("/proxyservices".concat(clientIp));
		return restTemplate.getForEntity(builder.build().encode().toUri(), Boolean.class).getBody();*/
	}	
}
