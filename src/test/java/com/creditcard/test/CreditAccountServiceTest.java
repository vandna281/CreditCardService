package com.creditcard.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.creditcard.api.dao.impl.CreditAccountDaoImpl;
import com.creditcard.api.dao.impl.DaoFactory;
import com.creditcard.api.entity.CreditAccount;
import com.creditcard.api.exception.CreditAccountException;
import com.creditcard.api.service.CreditAccountService;

public class CreditAccountServiceTest {

	@InjectMocks
	private CreditAccountService caSvc;

	@Mock
	private DaoFactory daoFactory;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = CreditAccountException.class)
	public void testGetAllCards_Exception() {
		caSvc.getAllCards();
	}

	@Test
	public void testGetAllCards_success() {
		List<CreditAccount> clist = new ArrayList<CreditAccount>();
		clist.add(getDummyValidCreditCard());
		
		DaoFactory spyDao = Mockito.spy(daoFactory);
		spyDao.getCreditAccountDAO();
		when(daoFactory.getCreditAccountDAO()).thenReturn(new CreditAccountDaoImpl());

		List<CreditAccount> cardResult = caSvc.getAllCards();
		assertThat(cardResult.size(), is(1));
	}

	private CreditAccount getDummyValidCreditCard() {
		return new CreditAccount("testUser", "1111222233334444", new BigDecimal(1000), new BigDecimal(120));
	}

	private CreditAccount getDummyInvalidCreditCard() {
		return new CreditAccount("testUser", "111122223333444", new BigDecimal(1000), new BigDecimal(120));
	}

	@Test
	public void testAddNewCard_success() {

	}

	@Test
	public void testAddNewCard_exception() {

	}

	@Test
	public void testAddNewCard_validationFailure() {

	}
}
