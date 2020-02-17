package com.creditcard.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.creditcard.api.config.APIConstants;
import com.creditcard.api.entity.CreditAccount;
import com.creditcard.api.exception.CreditAccountException;
import com.creditcard.api.service.CreditAccountService;
import com.creditcard.api.service.CreditAccountServiceHelper;
import com.creditcard.api.service.ValidationService;

public class CreditAccountServiceTest {

	@InjectMocks
	private CreditAccountService caSvc;

	@Mock
	private ValidationService valSvc;
	
	@Mock
	private CreditAccountServiceHelper casvcHlpr;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllCards_Success() {
		List<CreditAccount> clist = new ArrayList<CreditAccount>();
		clist.add(getDummyValidCreditCard());
		when(casvcHlpr.getAllCards()).thenReturn(clist);

		List<CreditAccount> cardResult = caSvc.getAllCards();
		assertThat(cardResult.size(), is(1));
	}
	
	@Test
	public void testGetAllCards_SuccessEmptyList() {
		when(casvcHlpr.getAllCards()).thenReturn(new ArrayList<CreditAccount>());
		List<CreditAccount> cardResult = caSvc.getAllCards();
		assertThat(cardResult.size(), is(0));
	}

	@Test
	public void testAddNewCard_success() {
		CreditAccount cardObj = getDummyValidCreditCard();
		Mockito.doReturn(true).when(valSvc).validateCard(cardObj);
		Mockito.doNothing().when(casvcHlpr).saveCard(cardObj);
		Map<String, String> result = caSvc.addNewCard(cardObj);
		assertThat(result.get("status"), is(Integer.toString(APIConstants.CREATED)));
	}

	@Test
	public void testAddNewCard_excepHandling() {
		Mockito.doThrow(CreditAccountException.class).when(valSvc).validateCard(null);
		Map<String, String> result = caSvc.addNewCard(null);
		assertThat(result.get("status"), is(Integer.toString(APIConstants.BAD_REQUEST)));
	}

	@Test
	public void testAddNewCard_validationFailure() {
		ValidationService spyValSvc = Mockito.spy(valSvc);
		Mockito.doReturn(false).when(spyValSvc).ifAccountExists(Mockito.any());
		Map<String, String> result = caSvc.addNewCard(getDummyInvalidCreditCard());
		assertThat(result.get("status"), is(Integer.toString(APIConstants.CONFLICT)));

	}

	private CreditAccount getDummyValidCreditCard() {
		return new CreditAccount("testUser", "1111222233334444", new BigDecimal(1000), new BigDecimal(120));
	}

	private CreditAccount getDummyInvalidCreditCard() {
		return new CreditAccount("testUser", "111122223333444", new BigDecimal(1000), new BigDecimal(120));
	}
}
