package io.swipepay.walletapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.swipepay.walletapi.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
	
	
}