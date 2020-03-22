package com.tech.mkblogs.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.mkblogs.constants.SQLConstants;
import com.tech.mkblogs.model.Account;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountService {

	@Autowired
	private DataSource datasource;

	public Account saveAccount(Account account) throws Exception {

		try {
			log.info("Starting the method " + account);
			Connection connection = datasource.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_INSERT,
					new String[] { "id" });

			preparedStatement.setString(1, account.getAccountName());
			preparedStatement.setString(2, account.getAccountType());
			preparedStatement.setBigDecimal(3, account.getAmount());

			preparedStatement.setInt(4, account.getCreatedBy());
			preparedStatement.setString(5, account.getCreatedName());
			preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));

			preparedStatement.setInt(7, 0);

			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();

			Integer generatedKey = rs.next() ? rs.getInt(1) : 0;

			log.info("Generated Primary Key : " + generatedKey);

			account.setId(generatedKey);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;
	}

	public Account updateAccount(Account account) throws Exception {

		try {
			log.info("Starting the method " + account);

			Account dbObject = getAccount(account.getId());

			if (dbObject != null) {
				Connection connection = datasource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_UPDATE);

				preparedStatement.setString(1, account.getAccountName());
				preparedStatement.setString(2, account.getAccountType());
				preparedStatement.setBigDecimal(3, account.getAmount());

				preparedStatement.setInt(4, dbObject.getVersion() + 1);

				preparedStatement.setInt(5, account.getLastModifiedBy());
				preparedStatement.setString(6, account.getLastModifiedName());
				preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));

				preparedStatement.executeUpdate();

			} else {
				throw new RuntimeException("Entity Not Found " + account.getId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;
	}

	public Account getAccount(Integer id) throws Exception {

		Account account = new Account();
		try {
			log.info("Starting the method " + id);
			Connection connection = datasource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_GET_ACCOUNT);

			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				account.setId(rs.getInt("id"));
				account.setAccountName(rs.getString("account_name"));
				account.setAccountType(rs.getString("account_type"));
				account.setAmount(rs.getBigDecimal("amount"));
				account.setVersion(rs.getInt("version"));
				account.setCreatedBy(rs.getInt("created_by"));
				account.setCreatedName(rs.getString("created_name"));
				Timestamp createdTimestamp = rs.getTimestamp("created_ts");
				if (createdTimestamp != null)
					account.setCreatedTs(createdTimestamp.toLocalDateTime());
				account.setLastModifiedBy(rs.getInt("last_modified_by"));
				account.setLastModifiedName(rs.getString("last_modified_name"));
				Timestamp modifiedTime = rs.getTimestamp("last_modified_ts");
				if (modifiedTime != null)
					account.setLastModifiedTs(modifiedTime.toLocalDateTime());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;
	}

	public List<Account> getAllData() throws Exception {
		List<Account> list = new ArrayList<>();
		try {
			log.info("Starting the method ");
			Connection connection = datasource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_GET_ALL_DATA);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Account account = new Account();
				account.setId(rs.getInt("id"));
				account.setAccountName(rs.getString("account_name"));
				account.setAccountType(rs.getString("account_type"));
				account.setAmount(rs.getBigDecimal("amount"));
				account.setVersion(rs.getInt("version"));
				account.setCreatedBy(rs.getInt("created_by"));
				account.setCreatedName(rs.getString("created_name"));
				Timestamp createdTimestamp = rs.getTimestamp("created_ts");
				if (createdTimestamp != null)
					account.setCreatedTs(createdTimestamp.toLocalDateTime());
				account.setLastModifiedBy(rs.getInt("last_modified_by"));
				account.setLastModifiedName(rs.getString("last_modified_name"));
				Timestamp modifiedTime = rs.getTimestamp("last_modified_ts");
				if (modifiedTime != null)
					account.setLastModifiedTs(modifiedTime.toLocalDateTime());
				list.add(account);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
