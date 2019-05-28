package io.swipepay.walletapi.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String emailAddress;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<UserBiller> userBillers;
	
	@OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY)
	private List<UserWallet> userWallets;
	
	public User() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<UserBiller> getUserBillers() {
		return userBillers;
	}

	public void setUserBillers(List<UserBiller> userBillers) {
		this.userBillers = userBillers;
	}

	public List<UserWallet> getUserWallets() {
		return userWallets;
	}

	public void setUserWallets(List<UserWallet> userWallets) {
		this.userWallets = userWallets;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}