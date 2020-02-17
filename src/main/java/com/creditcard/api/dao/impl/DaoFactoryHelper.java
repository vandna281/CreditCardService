package com.creditcard.api.dao.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.h2.tools.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.creditcard.api.dao.CreditAccountDao;
import com.creditcard.api.utils.Utils;

/**
 * H2 DAO
 */
public class DaoFactoryHelper extends DaoFactory {

	final static Logger logger = LoggerFactory.getLogger(DaoFactoryHelper.class);
	private static final String h2_driver = Utils.getStringProperty("h2_driver");
	private static final String h2_connection_url = Utils.getStringProperty("h2_connection_url");
	private static final String h2_username = Utils.getStringProperty("h2_username");
	private static final String h2_password = Utils.getStringProperty("h2_password");

	private final CreditAccountDaoImpl caDAO = new CreditAccountDaoImpl();

	DaoFactoryHelper() {
		DbUtils.loadDriver(h2_driver);
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(h2_connection_url, h2_username, h2_password);

	}

	public CreditAccountDao getCreditAccountDAO() {
		return caDAO;
	}

	@Override
	public void initializeInMemorySchema() {
		Connection conn = null;
		try {
			conn = DaoFactoryHelper.getConnection();
			RunScript.execute(conn, new FileReader("src/main/resources/database.sql"));
		} catch (SQLException e) {
			logger.error("initializeInMemorySchema(): Error initializeInMemorySchema : ", e);
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			logger.error("initializeInMemorySchema(): Error finding database file ", e);
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

}
