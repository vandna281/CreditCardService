package com.creditcard.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.creditcard.api.dao.impl.DaoFactory;
import com.creditcard.api.entity.CreditAccount;

@Service
public class CreditAccountServiceHelper {

	private final DaoFactory daoFactory = DaoFactory.getDAOFactory(DaoFactory.H2);

	public void saveCard(CreditAccount newCardObj) {
		daoFactory.getCreditAccountDAO().saveCard(newCardObj);
	}
	
	public List<CreditAccount> getAllCards() {
		return daoFactory.getCreditAccountDAO().getAllCards();
	}

}
