package com.creditcard.api.service;

import org.springframework.stereotype.Service;

import com.creditcard.api.dao.impl.DaoFactory;
import com.creditcard.api.entity.CreditAccount;

@Service
public class ValidationService {

	private final DaoFactory daoFactory = DaoFactory.getDAOFactory(DaoFactory.H2);

	/**
	 * 1) It checks if account already exists or not. 2) If it has valid card
	 * number.
	 * 
	 * @param newCardObj
	 * @return validation status
	 */
	public boolean validateCard(CreditAccount newCardObj) {
		boolean result = ifAccountExists(newCardObj);
		boolean result2 = isValidCardNumber(newCardObj.getCardNumber());
		if (!ifAccountExists(newCardObj) && isValidCardNumber(newCardObj.getCardNumber())) {
			return true;
		}
		return false;
	}

	public boolean ifAccountExists(CreditAccount newCardObj) {
		if ((daoFactory.getCreditAccountDAO().getCardByCardNumber(newCardObj.getCardNumber()) != null)
				&& (daoFactory.getCreditAccountDAO().getCardByUsername(newCardObj.getUserName()) != null)) {
			return true;
		}
		return false;
	}

	public boolean isValidCardNumber(String cardnum) {
		cardnum = cardnum.replaceAll("\\s+","");
		int sum = 0;
		boolean secondDigit = false;
		for (int i = cardnum.length() - 1; i >= 0; i--) {
			int n = cardnum.charAt(i) - '0';
			if (secondDigit) {
				n *= 2;
			}
			sum += n / 10;
			sum += n % 10;
			sum += n;
			secondDigit = !secondDigit;
		}
		return (sum % 10 == 0);
	}

}
