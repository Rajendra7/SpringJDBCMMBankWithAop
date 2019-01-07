package com.cg.app.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cg.app.account.SavingsAccount;
import com.cg.app.account.util.DBUtil;
import com.cg.app.exception.AccountNotFoundException;

@Repository
public class SavingsAccountDAOImpl implements SavingsAccountDAO {

	public class SavingsAccountJdbcDAO {

	}


	public class SavingAccountJdbcDAO {

	}


	public SavingsAccount createNewAccount(SavingsAccount account)
			throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)");
		preparedStatement
				.setInt(1, account.getBankAccount().getAccountNumber());
		preparedStatement.setString(2, account.getBankAccount()
				.getAccountHolderName());
		preparedStatement.setDouble(3, account.getBankAccount()
				.getAccountBalance());
		preparedStatement.setBoolean(4, account.isSalary());
		preparedStatement.setObject(5, null);
		preparedStatement.setString(6, "SA");
		preparedStatement.executeUpdate();
		preparedStatement.close();
		DBUtil.commit();
		return account;
	}

	public List<SavingsAccount> getAllSavingsAccount()
			throws ClassNotFoundException, SQLException {
		List<SavingsAccount> savingsAccounts = new ArrayList<SavingsAccount>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			savingsAccounts.add(savingsAccount);
		}
		DBUtil.commit();
		return savingsAccounts;
	}
	
	
	public void updateBalance(int accountNumber, double currentBalance)
			throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		connection.setAutoCommit(false);
		PreparedStatement preparedStatement = connection
				.prepareStatement("UPDATE ACCOUNT SET account_bal=? where account_id=?");
		preparedStatement.setDouble(1, currentBalance);
		preparedStatement.setInt(2, accountNumber);
		preparedStatement.executeUpdate();
		DBUtil.commit();
	}

	
	public SavingsAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account where account_id=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		SavingsAccount savingsAccount = null;
		if (resultSet.next()) {
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			return savingsAccount;
		}
		throw new AccountNotFoundException("Account with account number "
				+ accountNumber + " does not exist.");
	}

	
	public SavingsAccount getAccountByName(String accountHolderName)
			throws SQLException, AccountNotFoundException,
			ClassNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account where account_hn=?");
		preparedStatement.setString(1, accountHolderName);
		ResultSet resultSet = preparedStatement.executeQuery();
		SavingsAccount savingsAccount = null;
		if (resultSet.next()) {
			int accountNumber = resultSet.getInt(1);
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);
			return savingsAccount;
		}
		throw new AccountNotFoundException("Account with account Name "
				+ accountHolderName + " does not exist.");
	}

	
	public List<SavingsAccount> getAccountsBetweenTheSalary(double minimum,
			double maximum) throws SQLException, ClassNotFoundException {
		List<SavingsAccount> savingsAccountList = new ArrayList<SavingsAccount>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account where account_bal BETWEEN ? AND ?");
		preparedStatement.setDouble(1, minimum);
		preparedStatement.setDouble(2, maximum);
		ResultSet resultSet = preparedStatement.executeQuery();
		SavingsAccount savingsAccount = null;
		if (resultSet.next()) {
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			boolean salary = resultSet.getBoolean("salary");
			savingsAccount = new SavingsAccount(accountNumber,
					accountHolderName, accountBalance, salary);

			savingsAccountList.add(savingsAccount);

		}
		return savingsAccountList;
	}

	/*@Override
	public SavingsAccount updateAccount(int accountNumber,
			String NewAccountHolderName) throws ClassNotFoundException,
			SQLException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("UPDATE account SET account_hn=? where account_id=?");
		preparedStatement.setString(1, NewAccountHolderName);
		preparedStatement.setInt(2, accountNumber);
		preparedStatement.executeUpdate();
		DBUtil.commit();
		return null;

	}*/
	
	
	public boolean updateAccountType(SavingsAccount account) throws SQLException, ClassNotFoundException{
		
				Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement
						("UPDATE ACCOUNT SET account_hn=?,salary=? where account_id=?");
				preparedStatement.setString(1, account.getBankAccount().getAccountHolderName());
				preparedStatement.setBoolean(2,account.isSalary() );
				preparedStatement.setInt(3, account.getBankAccount().getAccountNumber());
				int count  = preparedStatement.executeUpdate();
				boolean result = false;
				if(count!=0){
					result = true;
				}
				DBUtil.commit();
			
			return result;
	}


	
	public SavingsAccount deleteAccount(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("DELETE FROM account where account_id=?");
		preparedStatement.setInt(1, accountNumber);
		preparedStatement.execute();
		DBUtil.commit();
		return null;
	}

	
	public double checkBalance(int accountNumber) throws SQLException,
			ClassNotFoundException, AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account where account_id=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			double accountBalance = resultSet.getDouble(3);
			DBUtil.commit();
			return accountBalance;
		}
		throw new AccountNotFoundException("Account with account number "
				+ accountNumber + " does not exist.");
	}

	
	public void commit() throws SQLException {
		DBUtil.commit();
	}
}
