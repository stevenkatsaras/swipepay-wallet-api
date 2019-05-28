package io.swipepay.walletapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swipepay.walletapi.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/user/authenticate")
	public Map<String, String> authenticate(@RequestBody Map<String, String> request) {
		return userService.authenticate(request);
	}
	
	@PostMapping(value = "/user/signup")
	public Map<String, String> signup(@RequestBody Map<String, String> request) {
		return userService.signup(request);
	}
}