package io.swipepay.walletapi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.swipepay.walletapi.entity.User;
import io.swipepay.walletapi.entity.UserBiller;
import io.swipepay.walletapi.repository.UserBillerRepository;
import io.swipepay.walletapi.repository.UserRepository;

@Service
public class UserBillerService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserBillerRepository userBillerRepository;

	@Transactional(readOnly = true)
	public List<Map<String, String>> list(Map<String, String> request) {
		List<Map<String, String>> response = new ArrayList<Map<String, String>>();
		
		List<UserBiller> userBillers = userBillerRepository.findByUserEmailAddressOrderByBillerNameAsc(request.get("emailAddress"));
		for (UserBiller userBiller : userBillers) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", userBiller.getId().toString());
			map.put("billerCode", userBiller.getBillerCode());
			map.put("billerName", userBiller.getBillerName());
			map.put("reference", userBiller.getReference());
			map.put("amount", userBiller.getAmount());
			response.add(map);
		}
		return response;
	}
	
	// THIS IS A STUB OF BPAY BILLERS! NEED TO REPLACE THIS WITH A BPAY API LOOKUP
	
	private static final Map<String, String> bpayBillerMap;
    static {
        Map<String, String> aMap = new HashMap<String, String>();
        aMap.put("8755", "Banyule City Council");
        aMap.put("344366", "Yarra Valley Water");
        aMap.put("52225", "Vodafone PostPaid");
        aMap.put("75556", "Tax Office Payments");
        aMap.put("8862", "Coles MasterCard");
        aMap.put("21915", "Centrelink");
        aMap.put("127456", "BYOJET");
        aMap.put("878934", "Banyule Debtors");
        aMap.put("858993", "Ambulance Victoria");
        bpayBillerMap = Collections.unmodifiableMap(aMap);
    }
    
	public Map<String, String> lookup(Map<String, String> request) {
		String billerName = bpayBillerMap.get(request.get("billerCode"));
		
		Map<String, String> response = new HashMap<String, String>();
		response.put("billerName", billerName);
		return response;
	}

	@Transactional(readOnly = false)
	public Map<String, String> add(Map<String, String> request) {
		User user = userRepository.findByEmailAddress(request.get("emailAddress"));
		
		UserBiller userBiller = new UserBiller();
		userBiller.setBillerCode(request.get("billerCode"));
		userBiller.setBillerName(request.get("billerName"));
		userBiller.setReference(request.get("reference"));
		userBiller.setAmount((StringUtils.isBlank(request.get("amount")) ? "0.00" : request.get("amount")));
		userBiller.setUser(user);
		
		userBillerRepository.saveAndFlush(userBiller);
		return new HashMap<String, String>();
	}
	
	@Transactional(readOnly = false)
	public Map<String, String> edit(Map<String, String> request) {
		UserBiller userBiller = userBillerRepository.getOne(Long.parseLong(request.get("id").toString()));
		userBiller.setReference(request.get("reference"));
		userBiller.setAmount((StringUtils.isBlank(request.get("amount")) ? "0.00" : request.get("amount")));
		
		userBillerRepository.saveAndFlush(userBiller);
		return new HashMap<String, String>();
	}

	@Transactional(readOnly = true)
	public Map<String, String> get(Map<String, String> request) {
		UserBiller userBiller = userBillerRepository.getOne(Long.parseLong(request.get("id").toString()));
		
		Map<String, String> response = new HashMap<String, String>();
		response.put("billerCode", userBiller.getBillerCode());
		response.put("billerName", userBiller.getBillerName());
		response.put("reference", userBiller.getReference());
		response.put("amount", userBiller.getAmount());
		return response;
	}
	
}