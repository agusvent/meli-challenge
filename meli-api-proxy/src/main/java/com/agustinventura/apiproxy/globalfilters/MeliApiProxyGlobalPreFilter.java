package com.agustinventura.apiproxy.globalfilters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.agustinventura.apiproxy.dtos.FilterProcessResponse;
import com.agustinventura.apiproxy.helpers.FilterHelper;
import com.agustinventura.apiproxy.helpers.RemoteAddressKeyResolver;
import com.fasterxml.jackson.core.JsonProcessingException;

import reactor.core.publisher.Mono;

@Component
public class MeliApiProxyGlobalPreFilter implements GlobalFilter{

	@Autowired
	FilterHelper filterHelper;

	final Logger logger = LoggerFactory.getLogger(MeliApiProxyGlobalPreFilter.class);
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
		ServerHttpRequest request = exchange.getRequest();
		
		String client = this.getClientIP(exchange);
		logger.info("REQUEST BY: "+client);
		String path = request.getPath().value().replace("/", "-");
		FilterProcessResponse filterResponse = null;
		try {
			filterResponse = filterHelper.executeFilters(client,path);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		if(filterResponse!=null && filterResponse.isMustFilter()) {
			return this.rejectRequest(exchange,filterResponse.getError(), filterResponse.getStatus());
		}

		return chain.filter(exchange);
	}

	private String getClientIP(ServerWebExchange exchange) {
    	String clientIP = "";
			RemoteAddressKeyResolver oResolver = new RemoteAddressKeyResolver();
			Mono<String> cli = oResolver.resolve(exchange);
			clientIP = cli.block();
		return clientIP;
    }

    private Mono<Void> rejectRequest(ServerWebExchange exchange, String err, HttpStatus httpStatus)  {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        logger.info(err);
        return response.setComplete();
    }	
    
}
