package io.swipepay.walletapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class UserBiller {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String billerCode;
	
	@Column(nullable = false)
	private String billerName;
	
	@Column(nullable = false)
	private String reference;

	@Column(nullable = false)
	private String amount;
	
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	public UserBiller() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}