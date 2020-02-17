package com.creditcard.api.dao;

import java.util.List;

import com.creditcard.api.entity.CreditAccount;

public interface CreditAccountDao {

	List<CreditAccount> getAllCards();

	CreditAccount getCardByCardNumber(String cardNum);

	CreditAccount getCardByUsername(String username);

	void saveCard(CreditAccount cardObj);
}
