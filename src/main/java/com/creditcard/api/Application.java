package com.creditcard.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.creditcard.api.dao.impl.DaoFactory;

@SpringBootApplication
public class Application {

	final static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		logger.info("Initialize DB schema .....");
		DaoFactory h2DaoFactory = DaoFactory.getDAOFactory(DaoFactory.H2);
		h2DaoFactory.initializeInMemorySchema();
		logger.info("Initialisation DB Complete....");
		
		logger.info("Starting Application!!");
		SpringApplication.run(Application.class, args);
	}

}