package io.swipepay.walletapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Wallet {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String coinType;
	
	@Column(nullable = false)
	private String coinName;
	
	@Column(nullable = false)
	private String blockChainNode;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	public Wallet() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCoinType() {
		return coinType;
	}

	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public String getBlockChainNode() {
		return blockChainNode;
	}

	public void setBlockChainNode(String blockChainNode) {
		this.blockChainNode = blockChainNode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}