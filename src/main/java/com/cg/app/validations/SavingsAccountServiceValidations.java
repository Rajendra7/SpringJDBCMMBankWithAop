package com.cg.app.validations;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.cg.app.account.SavingsAccount;
import com.cg.app.exception.InsufficientFundsException;

@Aspect
@Component
public class SavingsAccountServiceValidations {
	
	Logger logger =Logger.getLogger(SavingsAccountServiceValidations.class.getName());
	
	@Around("execution(* com.cg.app.account.service.SavingsAccountServiceImpl.deposit(..))")
	public void depositValidation(ProceedingJoinPoint pjp) throws Throwable {
		Object[] param=pjp.getArgs();
		double amount =(Double) param[1];
		if(amount>0) {
			pjp.proceed();
		}
		else {
			throw new InsufficientFundsException("Invallid Input or Insufficient funds!!!");
			
		}
	}
	@Around("execution(* com.cg.app.account.service.SavingsAccountServiceImpl.withdraw(..))")
	public void withdrawValidation(ProceedingJoinPoint pjp) throws Throwable {
		Object[] param=pjp.getArgs();
		SavingsAccount savingsAccount=(SavingsAccount)param[0];
		double currentBalance=savingsAccount.getBankAccount().getAccountBalance();
		double amount =(Double) param[1];
		if(amount>0 && currentBalance>=amount) {
			pjp.proceed();
		}
		else {
			throw new InsufficientFundsException("Invallid Input or Insufficient funds!!!");
			
		}
	}
	

}
