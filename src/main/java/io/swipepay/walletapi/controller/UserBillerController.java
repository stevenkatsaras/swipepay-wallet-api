package io.swipepay.walletapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swipepay.walletapi.service.UserBillerService;

@RestController
public class UserBillerController {
	
	@Autowired
	private UserBillerService userBillerService;
	
	@PostMapping(value = "/user/biller/list")
	public List<Map<String, String>> list(@RequestBody Map<String, String> request) {
		return userBillerService.list(request);
	}
	
	@PostMapping(value = "/user/biller/lookup")
	public Map<String, String> lookup(@RequestBody Map<String, String> request) {
		return userBillerService.lookup(request);
	}
	
	@PostMapping(value = "/user/biller/add")
	public Map<String, String> add(@RequestBody Map<String, String> request) {
		return userBillerService.add(request);
	}
	
	@PostMapping(value = "/user/biller/edit")
	public Map<String, String> edit(@RequestBody Map<String, String> request) {
		return userBillerService.edit(request);
	}
	
	@PostMapping(value = "/user/biller/get")
	public Map<String, String> get(@RequestBody Map<String, String> request) {
		return userBillerService.get(request);
	}
}