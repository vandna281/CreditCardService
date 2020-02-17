package com.creditcard.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CreditAccountException extends RuntimeException {
	public CreditAccountException(String message) {
		super(message);
	}
}
