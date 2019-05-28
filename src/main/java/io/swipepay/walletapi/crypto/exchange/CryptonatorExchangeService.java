package io.swipepay.walletapi.crypto.exchange;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swipepay.walletapi.support.RestSupport;

@Service
public class CryptonatorExchangeService {
	
	@Autowired
	private RestSupport restSupport;
	
	public BigDecimal convertToFiat(String coinType, BigDecimal coinBalance, String fiatType) throws JsonParseException, JsonMappingException, IOException {
		HttpEntity<LinkedHashMap<String, String>> httpEntity = restSupport.httpEntity(new LinkedHashMap<String, String>());
		
		RestTemplate restTemplate = restSupport.buildRestTemplate("https://api.cryptonator.com/api/full");
		ResponseEntity<String> responseEntity = restTemplate.exchange("/" + coinType +"-" + fiatType, HttpMethod.GET, httpEntity, String.class);
		LinkedHashMap<String, Object> response = new ObjectMapper().readValue(responseEntity.getBody(), new TypeReference<LinkedHashMap<String, Object>>(){});
		
		LinkedHashMap<String, Object> ticker = (LinkedHashMap<String, Object>) response.get("ticker");
		BigDecimal fiatRate = new BigDecimal((String) ticker.get("price"));		
		
		return coinBalance.multiply(fiatRate).setScale(2, BigDecimal.ROUND_UP);
	}
	
	public void convertToCrypto() {
		
	}
}