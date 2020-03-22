package com.tech.mkblogs.constants;

public interface SQLConstants {

	 String SQL_INSERT = "INSERT INTO account "
			+ "(account_name,account_type,amount,created_by,created_name,created_ts,VERSION) "
			+ "VALUES(?,?,?,?,?,?,?)";
	 String SQL_UPDATE = "UPDATE account SET account_name=?,account_type=?,amount=?,version=?,"
			+ "last_modified_by=?,last_modified_name=?,last_modified_ts=? "
			+ " WHERE id=?";
	String SQL_GET_ACCOUNT = "SELECT * FROM account where id = ?";
	String SQL_GET_ALL_DATA = "SELECT * FROM account ";
	String SQL_DELETE = "DELETE FROM account where id = ?";
}
