package io.swipepay.walletapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.swipepay.walletapi.entity.UserBiller;

public interface UserBillerRepository extends JpaRepository<UserBiller, Long> {
	
	List<UserBiller> findByUserEmailAddressOrderByBillerNameAsc(String emailAddress);
	
}