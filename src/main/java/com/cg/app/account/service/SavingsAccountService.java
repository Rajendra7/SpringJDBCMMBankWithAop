package com.cg.app.account.service;

import java.sql.SQLException;
import java.util.List;

import com.cg.app.account.SavingsAccount;
import com.cg.app.exception.AccountNotFoundException;

public interface SavingsAccountService {

	SavingsAccount createNewAccount(String accountHolderName,
			double accountBalance, boolean salary)
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

	void fundTransfer(SavingsAccount sender, SavingsAccount receiver,
			double amount) throws ClassNotFoundException, SQLException;

	void deposit(SavingsAccount account, double amount)
			throws ClassNotFoundException, SQLException;

	void withdraw(SavingsAccount account, double amount)
			throws ClassNotFoundException, SQLException;

	double checkBalance(int accountNumber) throws ClassNotFoundException,
			SQLException, AccountNotFoundException;

	SavingsAccount getAccountByName(String accountHolderName)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException;

	List<SavingsAccount> getAccountsBetweenTheSalary(double minimum,
			double maximum) throws ClassNotFoundException, SQLException;

	boolean updateAccount(SavingsAccount account)
			throws ClassNotFoundException, SQLException;

}
