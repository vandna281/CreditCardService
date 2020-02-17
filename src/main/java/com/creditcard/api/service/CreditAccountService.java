package com.creditcard.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditcard.api.config.APIConstants;
import com.creditcard.api.dao.impl.DaoFactory;
import com.creditcard.api.entity.CreditAccount;

@Service
public class CreditAccountService {

	private final DaoFactory daoFactory = DaoFactory.getDAOFactory(DaoFactory.H2);
	
	final static Logger logger = LoggerFactory.getLogger(CreditAccountService.class);

	@Autowired
	private ValidationService validate;
	
	public Map<String, String> addNewCard(CreditAccount newCardObj) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			if (validate.validateCard(newCardObj)) {
				daoFactory.getCreditAccountDAO().saveCard(newCardObj);
				resultMap.put("status", Integer.toString(APIConstants.CREATED));
				resultMap.put("msg", "Credit Account successfully created.");
			} else {
				resultMap.put("status", Integer.toString(APIConstants.CONFLICT));
				resultMap.put("msg", "Account already exists or card number is not valid.");
			}
		} catch (Exception ex) {
			resultMap.put("status", Integer.toString(APIConstants.BAD_REQUEST));
			resultMap.put("msg", ex.getMessage());
		}
		return resultMap;
	}

	public List<CreditAccount> getAllCards() {
		return daoFactory.getCreditAccountDAO().getAllCards();
	}

}
