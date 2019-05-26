package io.swipepay.walletapi.support;

import java.util.LinkedHashMap;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestSupport {
	
	public HttpHeaders httpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
	}
	
	public HttpEntity<LinkedHashMap<String, String>> httpEntity(LinkedHashMap<String, String> request) {
		return new HttpEntity<LinkedHashMap<String, String>>(request, httpHeaders());		
	}
	
	public RestTemplate buildRestTemplate(String rootUri) {
		return new RestTemplateBuilder()
				.rootUri(rootUri)
				.setConnectTimeout(60000)
				.setReadTimeout(60000)
				.build();
	}
	
	public RestTemplate buildRestTemplate(String rootUri, String username, String password) {
		return new RestTemplateBuilder()
				.rootUri(rootUri)
				.setConnectTimeout(60000)
				.setReadTimeout(60000)
				.basicAuthorization(username, password)
				.build();
	}
}