package com.cg.app.account;

import java.util.Comparator;

public class SortAccountByHolderNameInAscending extends SavingsAccount implements Comparator<SavingsAccount> {

	
	public int compare(SavingsAccount savingsAccount1,SavingsAccount savingsAccount2) {
		
		return (savingsAccount1.getBankAccount().getAccountHolderName().compareToIgnoreCase(savingsAccount2.getBankAccount().getAccountHolderName()));
	}

}
