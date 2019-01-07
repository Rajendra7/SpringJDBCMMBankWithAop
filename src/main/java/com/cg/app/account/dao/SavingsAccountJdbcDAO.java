package com.cg.app.account.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cg.app.account.SavingsAccount;
import com.cg.app.exception.AccountNotFoundException;

@Repository
@Primary
public class SavingsAccountJdbcDAO implements SavingsAccountDAO {

	@Autowired
	private JdbcTemplate template;
	
	@Override
	public SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException {
		template.update("INSERT INTO account VALUES(?,?,?,?,?,?)", new Object[] {account.getBankAccount().getAccountNumber(),
				account.getBankAccount().getAccountHolderName(),
				account.getBankAccount().getAccountBalance(),
				account.isSalary(),
				null,"SA"});
		return account;
	}

	@Override
	public SavingsAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount account=template.queryForObject("SELECT * FROM account where account_id=?" , new Object[] {accountNumber},new SavingsAccountMapper());
		
		return account;
	}

	@Override
	public SavingsAccount deleteAccount(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount account=getAccountById(accountNumber);
		template.update("DELETE FROM account where account_id=?" , new Object[] {accountNumber});
		return account;
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		List<SavingsAccount> list=template.query("SELECT * FROM ACCOUNT",new SavingsAccountMapper());
		return list;
	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException {
		SavingsAccount account=template.queryForObject("UPDATE ACCOUNT SET account_bal=? where account_id=?",new Object[] {currentBalance,accountNumber},new SavingsAccountMapper());
		
	}

	@Override
	public void commit() throws SQLException {
		
		
	}

	@Override
	public double checkBalance(int accountNumber)
			throws SQLException, ClassNotFoundException, AccountNotFoundException {
		SavingsAccount account=template.queryForObject("SELECT * FROM account where account_id=?", new Object[] {accountNumber},new SavingsAccountMapper());
		return account.getBankAccount().getAccountBalance();
	}

	@Override
	public SavingsAccount getAccountByName(String accountHolderName)
			throws SQLException, AccountNotFoundException, ClassNotFoundException {
		SavingsAccount account=template.queryForObject("SELECT * FROM account where account_hn=?" , new Object[] {accountHolderName},new SavingsAccountMapper());
		return account;
	}

	@Override
	public List<SavingsAccount> getAccountsBetweenTheSalary(double minimum, double maximum)
			throws SQLException, ClassNotFoundException {
		List<SavingsAccount> list=template.query("SELECT * FROM account where account_bal BETWEEN ? AND ?",new SavingsAccountMapper()); 
				return list;
	}

	@Override
	public boolean updateAccountType(SavingsAccount account) throws SQLException, ClassNotFoundException {
		
		return template.update
				("UPDATE ACCOUNT SET account_hn=?,salary=? where account_id=?",
						new Object[] {account.getBankAccount().getAccountHolderName(),account.isSalary(),account.getBankAccount().getAccountNumber()})>0;
	}

}
