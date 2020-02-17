package com.creditcard.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.creditcard.api.entity.CreditAccount;
import com.creditcard.api.service.CreditAccountService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/card")
public class CardController {

	final static Logger logger = LoggerFactory.getLogger(CardController.class);

	@Autowired
	private CreditAccountService caSvc;

	/**
	 * 
	 * @param cardObj
	 * @return @{@link ResponseEntity} the status of card added
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json", consumes = {
			"application/JSON" })
	public ResponseEntity addCard(@RequestBody CreditAccount cardObj) throws IOException {
		logger.info("New credit request for : " + cardObj.getUserName());
		Map<String, String> resp = caSvc.addNewCard(cardObj);
		return new ResponseEntity(resp.get("msg"), HttpStatus.valueOf(Integer.parseInt(resp.get("status"))));
	}

	/**
	 * 
	 * @return @{@link ResponseEntity} returns list of cards
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity getAllCards() throws IOException {
		logger.info("Get all registered credit cards : ");
		List<CreditAccount> cardList = caSvc.getAllCards();
		return new ResponseEntity(cardList, HttpStatus.OK);
	}

}
