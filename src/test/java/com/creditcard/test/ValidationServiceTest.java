package com.creditcard.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.creditcard.api.entity.CreditAccount;
import com.creditcard.api.service.ValidationService;

public class ValidationServiceTest {

	@InjectMocks
	private ValidationService valSvc;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testValidateCard_ValidNewEntry() {
		ValidationService tempSvc = new ValidationService();
		ValidationService spyValSvc = Mockito.spy(tempSvc);
		Mockito.doReturn(false).when(spyValSvc).ifAccountExists(Mockito.any());
		boolean result = spyValSvc.validateCard(getDummyValidCreditCard());
		assertThat(result, is(true));
	}

	@Test
	public void testValidateCard_InvalidNewEntry_AccountExists() {
		ValidationService tempSvc = new ValidationService();
		ValidationService spyValSvc = Mockito.spy(tempSvc);
		Mockito.doReturn(true).when(spyValSvc).ifAccountExists(Mockito.any());
		boolean result = spyValSvc.validateCard(getDummyValidCreditCard());
		assertThat(result, is(false));
	}

	@Test
	public void testValidateCard_InvalidNewEntry_invalidcard() {
		ValidationService tempSvc = new ValidationService();
		ValidationService spyValSvc = Mockito.spy(tempSvc);
		Mockito.doReturn(false).when(spyValSvc).ifAccountExists(Mockito.any());
		boolean result = spyValSvc.validateCard(getDummyInvalidCreditCard());
		assertThat(result, is(false));
	}

	@Test
	public void testIsValidCardNumber_InvalidCardNum() {
		boolean result = valSvc.isValidCardNumber(getDummyInvalidCreditCard().getCardNumber());
		assertThat(result, is(false));
	}

	@Test
	public void testIsValidCardNumber_ValidCardNum() {
		boolean result = valSvc.isValidCardNumber(getDummyValidCreditCard().getCardNumber());
		assertThat(result, is(true));
	}
	
	@Test
	public void testIsValidCardNumber_ValidCardNumWithspace() {
		boolean result = valSvc.isValidCardNumber(getDummyValidCreditCardWithSpace().getCardNumber());
		assertThat(result, is(true));
	}

	private CreditAccount getDummyValidCreditCard() {
		return new CreditAccount("testUser", "1111222233334444", new BigDecimal(1000), new BigDecimal(120));
	}
	
	private CreditAccount getDummyValidCreditCardWithSpace() {
		return new CreditAccount("testUser", "4111 1111 1111 1111", new BigDecimal(1000), new BigDecimal(120));
	}

	private CreditAccount getDummyInvalidCreditCard() {
		return new CreditAccount("testUser", "111122223333444", new BigDecimal(1000), new BigDecimal(120));
	}

}
