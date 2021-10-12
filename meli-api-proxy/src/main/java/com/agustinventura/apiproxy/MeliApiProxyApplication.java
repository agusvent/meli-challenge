package com.agustinventura.apiproxy;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.agustinventura.apiproxy.services.ProxyServices;


@SpringBootApplication
@RestController
public class MeliApiProxyApplication{

	final Logger logger = LoggerFactory.getLogger(MeliApiProxyApplication.class);
	
	@Autowired
	private ProxyServices proxyServices;
	
	@Bean 
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}
	
	@Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MeliApiProxyApplication.class, args);
	}
	
	@GetMapping("/proxyservices")
	public ArrayList<String> getUnwishedClients(){
		return proxyServices.getAllUnwishedIps();
	}
	
	@PostMapping("/proxyservices")
	public ResponseEntity<?> addUnwishedClient(@RequestBody String clientIp){
		return ResponseEntity.status(HttpStatus.CREATED).body(proxyServices.setUnwishedIp(clientIp));
	}
	
	@DeleteMapping("/proxyservices")
	public ResponseEntity<?> deleteUnwishedClient(@RequestBody String clientIp){
		return ResponseEntity.status(HttpStatus.OK).body(proxyServices.removeUnwishedIp(clientIp));
	}

	@DeleteMapping("/proxyservices/all")
	public ResponseEntity<?> deleteAllUnwishedClients(){
		return ResponseEntity.status(HttpStatus.OK).body(proxyServices.removeAllUnwishedIps());
	}

	@Bean
	public RouteLocator meliChallengeProxy(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("meli_proxy", p -> p
					.path("/meli/**")
					.filters(
							f -> f.rewritePath("meli/(?<todoloquevenga>.*)", "${todoloquevenga}")
					)
					.uri("http://api.mercadolibre.com")
				)
				/*.route("btc_proxy", p -> p
						.path("/btc/**")
						.filters(
								f -> f.rewritePath("btc/(?<todoloquevenga>.*)", "${todoloquevenga}")
						)
						.uri("https://api.lunarcrush.com")
				)
				.route("agus_proxy", p -> p
						.path("/agus/**")
						.filters(
								f -> f.rewritePath("agus/(?<todoloquevenga>.*)", "${todoloquevenga}")
						)
						.uri("http://localhost:8080")
				)*/
			.build();
	}
}


