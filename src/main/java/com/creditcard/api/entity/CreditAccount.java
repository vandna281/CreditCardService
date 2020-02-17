package com.creditcard.api.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditAccount {

	@JsonProperty(required = true)
	private long id;

	@JsonProperty(required = true)
	private String userName;

	@JsonProperty(required = true)
	private BigDecimal balance;

	@JsonProperty(required = true)
	private BigDecimal cardLimit;

	@JsonProperty(required = true)
	private String cardNumber;

	public CreditAccount() {
	}

	public CreditAccount(String uName, String cardNum, BigDecimal limit, BigDecimal balance) {
		this.userName = uName;
		this.cardNumber = cardNum;
		this.cardLimit = limit;
		this.balance = balance;
	}

	public CreditAccount(int id, String uName, String cardNum, BigDecimal limit, BigDecimal balance) {
		this.id = id;
		this.userName = uName;
		this.cardNumber = cardNum;
		this.cardLimit = limit;
		this.balance = balance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getCardLimit() {
		return cardLimit;
	}

	public void setCardLimit(BigDecimal cardLimit) {
		this.cardLimit = cardLimit;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

}
