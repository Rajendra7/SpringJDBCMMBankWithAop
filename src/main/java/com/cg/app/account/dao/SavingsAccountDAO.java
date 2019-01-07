package com.cg.app.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.cg.app.account.SavingsAccount;
import com.cg.app.exception.AccountNotFoundException;

public interface SavingsAccountDAO {

	SavingsAccount createNewAccount(SavingsAccount account)
			throws ClassNotFoundException, SQLException;

	/*SavingsAccount updateAccount(int accountNumber, String NewAccountHolderName)
			throws ClassNotFoundException, SQLException;*/

	SavingsAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException;

	SavingsAccount deleteAccount(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException;

	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException,
			SQLException;

	void updateBalance(int accountNumber, double currentBalance)
			throws ClassNotFoundException, SQLException;

	void commit() throws SQLException;

	double checkBalance(int accountNumber) throws SQLException,
			ClassNotFoundException, AccountNotFoundException;

	SavingsAccount getAccountByName(String accountHolderName)
			throws SQLException, AccountNotFoundException,
			ClassNotFoundException;

	List<SavingsAccount> getAccountsBetweenTheSalary(double minimum,
			double maximum) throws SQLException, ClassNotFoundException;

	boolean updateAccountType(SavingsAccount account) throws SQLException,
			ClassNotFoundException;

}
