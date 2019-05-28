package io.swipepay.walletapi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.swipepay.walletapi.crypto.exchange.CryptonatorExchangeService;
import io.swipepay.walletapi.crypto.wallet.btc.BTCService;
import io.swipepay.walletapi.entity.User;
import io.swipepay.walletapi.entity.UserWallet;
import io.swipepay.walletapi.entity.Wallet;
import io.swipepay.walletapi.entity.enums.Coin;
import io.swipepay.walletapi.repository.UserRepository;
import io.swipepay.walletapi.repository.UserWalletRepository;
import io.swipepay.walletapi.repository.WalletRepository;
import io.swipepay.walletapi.support.MoneySupport;

@Service
public class UserWalletService {
	
	@Autowired
	private BTCService btcService;
	
	@Autowired
	private CryptonatorExchangeService cryptonatorExchangeService;
	
	@Autowired
	private MoneySupport moneySupport;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserWalletRepository userWalletRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Transactional(readOnly = true)
	public List<Map<String, String>> list(Map<String, String> request) {
		List<Map<String, String>> response = new ArrayList<Map<String, String>>();
		
		User user = userRepository.findByEmailAddress(request.get("emailAddress"));
		List<Wallet> wallets = walletRepository.findAll();
		for (Wallet wallet : wallets) {
			UserWallet userWallet = userWalletRepository.findByUserEmailAddressAndWalletId(user.getEmailAddress(), wallet.getId());
			if (userWallet == null) {				
				createWallet(user, wallet);
			}
		}
		
		List<UserWallet> userWallets = userWalletRepository.findByUserEmailAddressOrderByWalletCoinTypeAsc(user.getEmailAddress());
		for (UserWallet userWallet : userWallets) {
			BigDecimal walletBalance = getWalletBalance(userWallet);
			BigDecimal fiatBalance = null;
			
			try {
				fiatBalance = cryptonatorExchangeService.convertToFiat(userWallet.getWallet().getCoinType(), walletBalance, "AUD");
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", userWallet.getCode());
			map.put("coinType", userWallet.getWallet().getCoinType());
			map.put("coinName", userWallet.getWallet().getCoinName());
			map.put("walletBalance", moneySupport.formatWithSeparators(walletBalance));
			map.put("fiatBalance", moneySupport.formatWithSeparators(fiatBalance));
			map.put("fiatType", "AUD");
			response.add(map);
		}
		return response;
	}
	
	public Map<String, String> get(Map<String, String> request) {
		UserWallet userWallet = userWalletRepository.findByCode(request.get("code"));
		BigDecimal walletBalance = getWalletBalance(userWallet);
		BigDecimal fiatBalance = null;
		
		try {
			fiatBalance = cryptonatorExchangeService.convertToFiat(userWallet.getWallet().getCoinType(), walletBalance, "AUD");
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		
		Map<String, String> response = new HashMap<String, String>();
		response.put("coinType", userWallet.getWallet().getCoinType());
		response.put("coinName", userWallet.getWallet().getCoinName());
		response.put("walletBalance", moneySupport.formatWithSeparators(walletBalance));
		response.put("fiatBalance", moneySupport.formatWithSeparators(fiatBalance));
		response.put("fiatType", "AUD");
		return response;
	}
	
	@Transactional(readOnly = false)
	private void createWallet(User user, Wallet wallet) {
		String code = UUID.randomUUID().toString().replaceAll("-", "");
		try {
			Coin coin = Enum.valueOf(Coin.class, wallet.getCoinType());
			switch (coin) {
			case BTC:
				btcService.createWallet(code, wallet);
				break;
			default:
				break;
			}
			
			UserWallet userWallet = new UserWallet();
			userWallet.setCode(code);
			userWallet.setUser(user);
			userWallet.setWallet(wallet);
			userWalletRepository.saveAndFlush(userWallet);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	private BigDecimal getWalletBalance(UserWallet userWallet) {
		Wallet wallet = userWallet.getWallet();
		BigDecimal balance = null;
		try {
			Coin coin = Enum.valueOf(Coin.class, wallet.getCoinType());
			switch (coin) {
			case BTC:
				balance = btcService.getWalletBalance(userWallet.getCode(), wallet);
				break;
			default:
				break;
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		return balance;
	}
	
}