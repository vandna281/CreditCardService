package com.creditcard.api.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.creditcard.api.dao.CreditAccountDao;
import com.creditcard.api.entity.CreditAccount;
import com.creditcard.api.exception.CreditAccountException;

public class CreditAccountDaoImpl implements CreditAccountDao {

	final static Logger logger = LoggerFactory.getLogger(CreditAccountDaoImpl.class);

	private final static String SQL_GET_ALL_CARDS = "SELECT * FROM CreditAccount order by id asc";
	private final static String SQL_GET_CARD_BY_CARD_NUM = "SELECT * FROM CreditAccount where cardNumber = ?";
	private final static String SQL_GET_CARD_BY_USERNAME = "SELECT * FROM CreditAccount where userName = ?";
	private final static String SQL_INSERT_CARD = "INSERT INTO CreditAccount (userName, cardNumber, cardLimit) VALUES (?, ?, ?)";

	@Override
	public List<CreditAccount> getAllCards() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<CreditAccount> cardList = new ArrayList<CreditAccount>();
		try {
			conn = DaoFactoryHelper.getConnection();
			stmt = conn.prepareStatement(SQL_GET_ALL_CARDS);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CreditAccount ca = new CreditAccount(rs.getInt("id"), rs.getString("userName"),
						rs.getString("cardNumber"), rs.getBigDecimal("cardLimit"), rs.getBigDecimal("balance"));
				cardList.add(ca);
			}
		} catch (SQLException e) {
			throw new CreditAccountException("Error fetching credit accounts");
		} finally {
			DbUtils.closeQuietly(conn, stmt, rs);
		}
		return cardList;
	}

	@Override
	public void saveCard(CreditAccount cardObj) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet generatedKeys = null;
		try {
			conn = DaoFactoryHelper.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT_CARD, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cardObj.getUserName());
			stmt.setString(2, cardObj.getCardNumber());
			stmt.setBigDecimal(3, cardObj.getCardLimit());
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				logger.error("saveCard(): Error creating credit account" + cardObj.getUserName());
				throw new CreditAccountException("Error creating account");
			}
			generatedKeys = stmt.getGeneratedKeys();
			if (!generatedKeys.next()) {
				logger.error("saveCard():  Error creating credit account, no ID retrieved for card."
						+ cardObj.getUserName());
				throw new CreditAccountException("Error creating account");
			}
		} catch (SQLException e) {
			logger.error("Error Inserting User :" + cardObj.getUserName() + " with error : " + e.getMessage());
			throw new CreditAccountException("Error creating account");
		} finally {
			DbUtils.closeQuietly(conn, stmt, generatedKeys);
		}
	}

	@Override
	public CreditAccount getCardByCardNumber(String cardNum) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DaoFactoryHelper.getConnection();
			stmt = conn.prepareStatement(SQL_GET_CARD_BY_CARD_NUM);
			stmt.setString(1, cardNum);
			rs = stmt.executeQuery();
			if (rs.next()) {
				CreditAccount ca = new CreditAccount(rs.getInt("id"), rs.getString("userName"),
						rs.getString("cardNumber"), rs.getBigDecimal("cardLimit"), rs.getBigDecimal("balance"));
				return ca;
			}
		} catch (SQLException e) {
			throw new CreditAccountException("Error fetching card details by card number for validating card");
		} finally {
			DbUtils.closeQuietly(conn, stmt, rs);
		}
		return null;
	}

	@Override
	public CreditAccount getCardByUsername(String username) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DaoFactoryHelper.getConnection();
			stmt = conn.prepareStatement(SQL_GET_CARD_BY_USERNAME);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			if (rs.next()) {
				CreditAccount ca = new CreditAccount(rs.getInt("id"), rs.getString("userName"),
						rs.getString("cardNumber"), rs.getBigDecimal("cardLimit"), rs.getBigDecimal("balance"));
				return ca;
			}
		} catch (SQLException e) {
			throw new CreditAccountException("Error fetching card details by username for validating card");
		} finally {
			DbUtils.closeQuietly(conn, stmt, rs);
		}
		return null;
	}

}
