package com.cg.app.account.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.app.account.SavingsAccount;
import com.cg.app.account.SortAccountByBalanceInAscending;
import com.cg.app.account.SortAccountByBalanceInDescending;
import com.cg.app.account.SortAccountByHolderNameInAscending;
import com.cg.app.account.SortAccountByHolderNameInDescending;
import com.cg.app.account.service.SavingsAccountService;
import com.cg.app.account.util.DBUtil;
import com.cg.app.exception.AccountNotFoundException;

@Component
public class AccountCUI {
	private  Scanner scanner = new Scanner(System.in);
	@Autowired
	private  SavingsAccountService savingsAccountService ;
	private  SavingsAccount savingsAccount;

	public  void start() throws ClassNotFoundException, SQLException {

		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Savings Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Savings Account Details");
			System.out.println("10. Sort Accounts");
			System.out.println("11. Exit");
			System.out.println();
			System.out.println("Make your choice: ");

			int choice = scanner.nextInt();

			performOperation(choice);

		} while (true);
	}

	private void performOperation(int choice)
			throws ClassNotFoundException, SQLException {
		switch (choice) {
		case 1:
			acceptInput("SA");
			break;
		case 2:
			System.out.println("Enter your account number to update : ");
			int accountId = scanner.nextInt();
			try {
				savingsAccount = savingsAccountService.getAccountById(accountId);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e1) {
				e1.printStackTrace();
			}
			System.out.println("To Update Your Name Enter "+"1");
			System.out.println("To Update Your Salary Type Enter "+"2");
			System.out.println("To Update Your Name and Salary Type Enter "+"3");
			int select = scanner.nextInt();
			selectOptionsToUpdate(select,savingsAccount);
			break;
		case 3:
			deleteAccount();
			break;
		case 4:
			searchAccount();
			break;
		case 5:
			withdraw();
			break;
		case 6:
			deposit();
			break;
		case 7:
			fundTransfer();
			break;
		case 8:
			try {
				checkBalance();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 9:
			showAllAccounts();
			break;
		case 10:
			sortAccounts();
			break;
		case 11:
			try {
				DBUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}

	}

private  void selectOptionsToUpdate(int select, SavingsAccount savingsAccount2) {
		
		switch(select){

		case 1:
			System.out.println("Enter new name change your name : ");
			String changeName = scanner.nextLine();
			changeName = scanner.nextLine();
			savingsAccount2.getBankAccount().setAccountHolderName(changeName);
			try {
				boolean name;
				name = savingsAccountService.updateAccount(savingsAccount2);
				if(name==true){
					System.out.println("Name Changed for "+savingsAccount2.getBankAccount().getAccountNumber()+" to "+changeName);
				}

			
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}
			
			
			break;
		
		case 2:
			
			System.out.println("If your account type is salaried enter (n) to change your account type as unsalaried");
			System.out.println("If your account type is savings enter (y)to change your account type as salaried");
			boolean changeSalaryType = scanner.next().equalsIgnoreCase("n")?false:true;
			savingsAccount2.setSalary(changeSalaryType);
		
			 
			try {
				boolean salary;
				salary = savingsAccountService.updateAccount(savingsAccount2);

				if(salary==true){
					System.out.println("Salary type of "+savingsAccount2.getBankAccount().getAccountNumber()+"Changed to "+changeSalaryType);
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			break;
			
		case 3:
			System.out.println("Enter new name change your name : ");
			String changename = scanner.nextLine();
			changename = scanner.nextLine();
			savingsAccount2.getBankAccount().setAccountHolderName(changename);
			System.out.println("If your account type is salaried enter (n) to change your account type as unsalaried");
			System.out.println("If your account type is savings enter (y)to change your account type as salaried");
			boolean changeTypeOfSalary = scanner.next().equalsIgnoreCase("n")?false:true;
			savingsAccount2.setSalary(changeTypeOfSalary);
			
			try {
				boolean salaryResult;
				salaryResult = savingsAccountService.updateAccount(savingsAccount2);
				if(salaryResult==true){
					System.out.println("Name and Salary type for "+savingsAccount2.getBankAccount().getAccountNumber()+" Changed to "+changename+" and "+changeTypeOfSalary);
			}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
			
		default:
			System.err.println("Invalid Choice!");
			break;
		}
	}

	private  void sortAccounts() throws ClassNotFoundException,
			SQLException {
		do {
			System.out
					.println("Enter the choice in which you want to sort the Accounts:");
			System.out.println("1.Ascending Order");
			System.out.println("2.Descending Order");
			System.out.println("3.Exit.");
			int choiceType = scanner.nextInt();
			sortType(choiceType);
		} while (true);

	}

	private  void sortType(int choiceType) throws ClassNotFoundException,
			SQLException {
		switch (choiceType) {
		case 1:
			do {
				System.out.println("1.Sort Accounts By Id");
				System.out.println("2.Sort Account By Balance");
				System.out.println("3.Exit");
				int choice = scanner.nextInt();
				sortFieldsInAscending(choice);
			} while (true);
		case 2:
			do {
				System.out.println("1.Sort Accounts By Id");
				System.out.println("2.Sort Account By Balance");
				System.out.println("3.Exit");
				int choice = scanner.nextInt();
				sortFieldsInDescending(choice);
			} while (true);
		case 3:
			start();
			break;
		default:
			System.out.println("Invalid Choice.");
			break;
		}

	}

	private  void sortFieldsInAscending(int choice)
			throws ClassNotFoundException, SQLException {
		switch (choice) {
		case 1:
			try {
				List<SavingsAccount> savingsAccounts = savingsAccountService
						.getAllSavingsAccount();
				Collections.sort(savingsAccounts,
						new SortAccountByHolderNameInAscending());
				for (SavingsAccount savingsAccount : savingsAccounts) {
					System.out.println(savingsAccount);
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;

		case 2:
			List<SavingsAccount> savingsAccounts = savingsAccountService
					.getAllSavingsAccount();
			Collections.sort(savingsAccounts,
					new SortAccountByBalanceInAscending());
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
			break;
		case 3:
			sortAccounts();
			break;
		default:
			System.out.println("Invalid choice.");
			break;

		}

	}

	private  void sortFieldsInDescending(int choice)
			throws ClassNotFoundException, SQLException {
		switch (choice) {
		case 1:
			try {
				List<SavingsAccount> savingsAccounts = savingsAccountService
						.getAllSavingsAccount();
				Collections.sort(savingsAccounts,
						new SortAccountByHolderNameInDescending());
				for (SavingsAccount savingsAccount : savingsAccounts) {
					System.out.println(savingsAccount);
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;

		case 2:
			List<SavingsAccount> savingsAccounts = savingsAccountService
					.getAllSavingsAccount();
			Collections.sort(savingsAccounts,
					new SortAccountByBalanceInDescending());
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
			break;
		case 3:
			sortAccounts();
			break;
		default:
			System.out.println("Invalid choice.");
			break;

		}
	}

	private  void searchAccount() throws ClassNotFoundException,
			SQLException {
		do {
			System.out.println("1.search Account by Entering Your id:");
			System.out.println("2.search Account by Entering Your Name:");
			System.out.println("3.search Account By Entering the Balance");
			System.out.println("4.Exit");
			System.out.println();
			System.out.println("Make Your Choice");
			int choice = scanner.nextInt();
			searchAccountByChoice(choice);
		} while (true);
	}

	private  void searchAccountByChoice(int choice)
			throws ClassNotFoundException, SQLException {
		switch (choice) {
		case 1:
			System.out.println("Enter the account Number");
			int accountNumber = scanner.nextInt();
			SavingsAccount getAccountById;
			try {
				getAccountById = savingsAccountService
						.getAccountById(accountNumber);
				System.out.println("Your Account Details is:" + getAccountById);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("Enter your Account Holder Name");
			String acountHolderName = scanner.next();
			SavingsAccount getAccountByName;
			try {
				getAccountByName = savingsAccountService
						.getAccountByName(acountHolderName);
				System.out.println("Your Account Details is:"
						+ getAccountByName);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			System.out
					.println("Enter the maximum and minimum amount that you want to search:");
			double maximum = scanner.nextDouble();
			double minimum = scanner.nextDouble();
			List<SavingsAccount> savingsAccounts=new ArrayList<SavingsAccount>();
			try {
				savingsAccounts = savingsAccountService
						.getAccountsBetweenTheSalary(minimum, maximum);
				for (SavingsAccount savingsAccount : savingsAccounts) {
					System.out.println(savingsAccount);
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			break;
		case 4:
			start();
			break;
		}
	}
	
	/*private static void updateAccount() throws ClassNotFoundException,
			SQLException {
		System.out.println("Enter new name that you want to update: ");
		String NewAccountHolderName = scanner.next();
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();

		savingsAccountService
				.updateAccount(accountNumber, NewAccountHolderName);
	}*/

	private  void checkBalance() throws ClassNotFoundException,
			SQLException, AccountNotFoundException {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		double amount = savingsAccountService.checkBalance(accountNumber);
		System.out.println("Current Balance " + amount);

	}

	private  void deleteAccount() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		try {
			savingsAccountService.deleteAccount(accountNumber);
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {

			e.printStackTrace();
		}

	}

	private  void fundTransfer() {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		try {
			SavingsAccount senderSavingsAccount = savingsAccountService
					.getAccountById(senderAccountNumber);
			SavingsAccount receiverSavingsAccount = savingsAccountService
					.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(senderSavingsAccount,
					receiverSavingsAccount, amount);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private  void deposit() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.deposit(savingsAccount, amount);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private  void withdraw() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.withdraw(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private  void showAllAccounts() {
		List<SavingsAccount> savingsAccounts;
		try {
			savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	private  void acceptInput(String type) {
		if (type.equalsIgnoreCase("SA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out
					.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance = 0.0;
			if (!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n") ? false
					: true;
			createSavingsAccount(accountHolderName, accountBalance, salary);
		}
	}

	private  void createSavingsAccount(String accountHolderName,
			double accountBalance, boolean salary) {
		try {
			savingsAccountService.createNewAccount(accountHolderName,
					accountBalance, salary);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
