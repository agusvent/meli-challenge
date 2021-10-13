package com.agustinventura.apiproxy.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.agustinventura.apiproxy.dtos.FilterProcessResponse;
import com.agustinventura.apiproxy.dtos.KeyValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Component
public class FilterHelper {

	@Autowired
	CacheHelper cacheHelper;

	@Autowired
	ProxyHelper proxyHelper;

	@Value("${meliapiproxy.maxRequestsByClientPerMinute}")
	Integer maxRequestsByClientPerMinute;
	
	@Value("${meliapiproxy.maxRequestsByPathPerMinute}")
	Integer maxRequestsByPathPerMinute;

	@Value("${meliapiproxy.maxRequestsByClientAndPathPerMinute}")
	Integer maxRequestsByClientAndPathPerMinute;

	final Logger logger = LoggerFactory.getLogger(FilterHelper.class);

	public FilterProcessResponse executeFilters(String client, String path) throws JsonMappingException, JsonProcessingException {
		LocalDateTime dateTime = LocalDateTime.now();
		String fecha = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
		
		String keyFilterByClient = client.concat("_").concat(fecha);
		String keyFilterByPath = path.concat("_").concat(fecha);
		String keyFilterByClientAndPath = client.concat("_").concat(path).concat("_").concat(fecha);
		
		if(!this.filterUnwishedClients(client)) {
			logger.info("FILTRANDO POR CLIENTE NO DESEADO");
			if(!this.filterByClient(keyFilterByClient)) {
				logger.info("FILTRANDO POR MAX REQ POR CLIENTE POR MINUTO");
				cacheHelper.saveCache(keyFilterByClient);
			}else {
				return new FilterProcessResponse(true,"Too Many Connections: se alcanzó el máximo de requests por cliente por minuto.",HttpStatus.TOO_MANY_REQUESTS);
			}

			if (!this.filterByPath(keyFilterByPath)){
				logger.info("FILTRANDO POR MAX REQ POR PATH POR MINUTO");
				cacheHelper.saveCache(keyFilterByPath);
			}else{
				return new FilterProcessResponse(true,"Too Many Connections: se alcanzó el máximo de requests por path por minuto.",HttpStatus.TOO_MANY_REQUESTS);
			}

			if (!this.filterByClientAndPath(keyFilterByClientAndPath)){
				logger.info("FILTRANDO POR MAX REQ POR CLIENTE Y PATH POR MINUTO");
				cacheHelper.saveCache(keyFilterByClientAndPath);
			}else{
				return new FilterProcessResponse(true,"Too Many Connections: se alcanzó el máximo de requests por cliente y path por minuto.",HttpStatus.TOO_MANY_REQUESTS);
			}
			
			return new FilterProcessResponse(false,"",HttpStatus.ACCEPTED);			
		}else {
			return new FilterProcessResponse(true,"Cliente no deseado.",HttpStatus.UNAUTHORIZED);
		}
	}
	
	private boolean filterByClient(String key) throws JsonMappingException, JsonProcessingException {
		boolean mustFilter = false;
		KeyValue oKeyValue = cacheHelper.getRequestsByKey(key);
		if(oKeyValue!=null) {
			mustFilter = this.evaluateFiltering(oKeyValue, maxRequestsByClientPerMinute,1);
		}
		return mustFilter;
	}
	
	private boolean filterByPath(String key) {
		boolean mustFilter = false;
		KeyValue oKeyValue = cacheHelper.getRequestsByKey(key);
		if(oKeyValue!=null) {
			mustFilter = this.evaluateFiltering(oKeyValue, maxRequestsByPathPerMinute,1);
		}
		return mustFilter;
	}

	private boolean filterByClientAndPath(String key) {
		boolean mustFilter = false;
		KeyValue oKeyValue = cacheHelper.getRequestsByKey(key);
		if(oKeyValue!=null) {
			mustFilter = this.evaluateFiltering(oKeyValue, maxRequestsByClientAndPathPerMinute,2);
		}
		return mustFilter;
	}
	
	private boolean filterUnwishedClients(String key) throws JsonMappingException, JsonProcessingException {
		boolean mustFilter = proxyHelper.isUnwishedClient(key);
		return mustFilter;
	}
	
	private boolean evaluateFiltering(KeyValue oKeyValue, Integer limit, Integer idKeyType) {
		/*
		 * idKeyType = 1 ==> Sería para cliente y para path.
		 * idKeyType = 2 ==> Sería para cliente y path en conjunto.
		 * Esto es porque la Key de Cliente y Path en conjunto tiene un "_" más y es el símbolo que
		 * uso para separar los componentes de la key
		 */
		boolean mustFilter = false;
		Integer counter = oKeyValue.getValue();
		String minute = cacheHelper.getDateTimeFromKey(oKeyValue.getKey(), idKeyType);

		String actualMinute = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
		
		if(minute.equals(actualMinute)) {
			if(counter==limit) {
				mustFilter = true;
			}
		}		
		return mustFilter;
	}
		

	
}
