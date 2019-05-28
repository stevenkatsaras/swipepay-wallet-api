package io.swipepay.walletapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.swipepay.walletapi.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmailAddress(String emailAddress);

}