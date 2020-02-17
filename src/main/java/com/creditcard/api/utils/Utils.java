package com.creditcard.api.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

	private static Properties properties = new Properties();

	final static Logger log = LoggerFactory.getLogger(Utils.class);

	public static void loadConfig(String fileName) {
		if (fileName == null) {
			log.warn("loadConfig: config file name cannot be null");
		} else {
			try {
				log.info("loadConfig(): Loading config file: " + fileName);
				final InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
				properties.load(fis);

			} catch (FileNotFoundException fne) {
				log.error("loadConfig(): file name not found " + fileName, fne);
			} catch (IOException ioe) {
				log.error("loadConfig(): error when reading the config " + fileName, ioe);
			}
		}

	}

	public static String getStringProperty(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			value = System.getProperty(key);
		}
		return value;
	}

	static {
		String configFileName = System.getProperty("application.properties");

		if (configFileName == null) {
			configFileName = "application.properties";
		}
		loadConfig(configFileName);

	}

}
