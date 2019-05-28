package io.swipepay.walletapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.swipepay.walletapi.entity.UserWallet;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
	
	UserWallet findByCode(String code);
	
	UserWallet findByUserEmailAddressAndWalletId(String emailAddress, Long walletId);
	
	List<UserWallet> findByUserEmailAddressOrderByWalletCoinTypeAsc(String emailAddress);
}