package io.swipepay.walletapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swipepay.walletapi.service.UserWalletService;

@RestController
public class UserWalletController {
	
	@Autowired
	private UserWalletService userWalletService;
	
	@PostMapping(value = "/user/wallet/list")
	public List<Map<String, String>> list(@RequestBody Map<String, String> request) {
		return userWalletService.list(request);
	}
	
	@PostMapping(value = "/user/wallet/get")
	public Map<String, String> get(@RequestBody Map<String, String> request) {
		return userWalletService.get(request);
	}
}