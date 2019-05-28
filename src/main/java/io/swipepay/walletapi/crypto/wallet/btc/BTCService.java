package io.swipepay.walletapi.crypto.wallet.btc;

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

import io.swipepay.walletapi.entity.Wallet;
import io.swipepay.walletapi.support.RestSupport;

@Service
public class BTCService {
	
	@Autowired
	private RestSupport restSupport;
	
	public void createWallet(String code, Wallet wallet) throws JsonParseException, JsonMappingException, IOException {
		HttpEntity<LinkedHashMap<String, String>> httpEntity = restSupport.httpEntity(new LinkedHashMap<String, String>());
		
		RestTemplate restTemplate = restSupport.buildRestTemplate(wallet.getBlockChainNode(), wallet.getUsername(), wallet.getPassword());
		restTemplate.exchange("/wallet/" + code, HttpMethod.PUT, httpEntity, String.class);
	}
	
	public BigDecimal getWalletBalance(String code, Wallet wallet) throws JsonParseException, JsonMappingException, IOException {
		HttpEntity<LinkedHashMap<String, String>> httpEntity = restSupport.httpEntity(new LinkedHashMap<String, String>());
		
		RestTemplate restTemplate = restSupport.buildRestTemplate(wallet.getBlockChainNode(), wallet.getUsername(), wallet.getPassword());
		ResponseEntity<String> responseEntity = restTemplate.exchange("/wallet/" + code + "/balance", HttpMethod.GET, httpEntity, String.class);
		LinkedHashMap<String, Object> response = new ObjectMapper().readValue(responseEntity.getBody(), new TypeReference<LinkedHashMap<String, Object>>(){});
		
		return new BigDecimal(response.get("confirmed").toString()).setScale(4, BigDecimal.ROUND_UP);
	}
}