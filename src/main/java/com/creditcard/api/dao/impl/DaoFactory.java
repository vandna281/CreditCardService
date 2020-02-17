package com.creditcard.api.dao.impl;

import com.creditcard.api.dao.CreditAccountDao;

public abstract class DaoFactory {

	public static final int H2 = 1;

	public abstract CreditAccountDao getCreditAccountDAO();

	public abstract void initializeInMemorySchema();

	public static DaoFactory getDAOFactory(int factoryCode) {
		return new DaoFactoryHelper();
	}
}
