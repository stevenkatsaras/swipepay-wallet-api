package io.swipepay.walletapi.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.swipepay.walletapi.entity.User;
import io.swipepay.walletapi.repository.UserRepository;
import io.swipepay.walletapi.support.PasswordSupport;

@Service
public class UserService {
	
	@Autowired
	private PasswordSupport passwordSupport;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public Map<String, String> authenticate(Map<String, String> request) {
		Map<String, String> response = new HashMap<String, String>();
		response.put("authenticated", "false");
		
		User user = userRepository.findByEmailAddress(request.get("emailAddress"));
		if (user != null) {
			if (passwordSupport.matches(request.get("password"), user.getPassword())) {
				response.put("authenticated", "true");
			}
		}
		return response;
	}
	
	@Transactional(readOnly = false)
	public Map<String, String> signup(Map<String, String> request) {
		Map<String, String> response = new HashMap<String, String>();
		response.put("signedUp", "false");
		
		User user = new User();
		user.setEmailAddress(request.get("emailAddress"));
		user.setFirstName(request.get("firstName"));
		user.setLastName(request.get("lastName"));
		user.setPassword(passwordSupport.hash(request.get("password")));
		userRepository.saveAndFlush(user);
		
		response.put("signedUp", "true");
		return response;
	}
}