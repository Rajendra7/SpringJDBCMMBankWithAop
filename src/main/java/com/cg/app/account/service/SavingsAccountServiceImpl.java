package com.cg.app.account.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.app.account.SavingsAccount;
import com.cg.app.account.dao.SavingsAccountDAO;
import com.cg.app.account.factory.AccountFactory;
import com.cg.app.exception.AccountNotFoundException;
import com.cg.app.exception.InsufficientFundsException;
import com.cg.app.exception.InvalidInputException;

@Transactional(rollbackFor= {Throwable.class})
@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {

	private AccountFactory factory;
	
	@Autowired
	private SavingsAccountDAO savingsAccountDAO;

	public SavingsAccountServiceImpl() {
		factory = AccountFactory.getInstance();
	}

	
	public SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary)
			throws ClassNotFoundException, SQLException {
		SavingsAccount account = factory.createNewSavingsAccount(accountHolderName, accountBalance, salary);
		savingsAccountDAO.createNewAccount(account);
		return null;
	}

	
	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.getAllSavingsAccount();
	}

	
	public void deposit(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException {
		
			double currentBalance = account.getBankAccount().getAccountBalance();
			currentBalance += amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			//savingsAccountDAO.commit();
		
	}
	
	public void withdraw(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException {
		double currentBalance = account.getBankAccount().getAccountBalance();
		
			currentBalance -= amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			//savingsAccountDAO.commit();
		
	}

	
	public void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount)
			throws ClassNotFoundException, SQLException {
		
			withdraw(sender, amount);
			deposit(receiver, amount);
			
		
	}

	public boolean updateAccount(SavingsAccount account) throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.updateAccountType(account);
	}

	
	public SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.getAccountById(accountNumber);
	}
	
	
	public SavingsAccount getAccountByName(String accountHolderName) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.getAccountByName(accountHolderName);
	}
	
	
	public List<SavingsAccount> getAccountsBetweenTheSalary(double minimum, double maximum) throws ClassNotFoundException, SQLException  {
		return savingsAccountDAO.getAccountsBetweenTheSalary(minimum, maximum);
	}
	

	
	public SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
	//	SavingsAccount account = factory.deleteAccount(accountNumber);
		
		return savingsAccountDAO.deleteAccount(accountNumber);
	}
	
	public double checkBalance(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException  {
	
		
		return savingsAccountDAO.checkBalance(accountNumber);
	}

}
