/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package tests;

import cash_access.OverdraftException;
import cash_access.TransactionImplBase;
import mware_lib.NameService;
import mware_lib.ObjectBroker;
import bank_access.InvalidParamException;
import bank_access.ManagerImplBase;

public class ClientTransactionTest {
	public static void main(String[] args) {
		String owner = "test";
		String branch = "123";
		String accountId = "";
		double result = 0;
		
		System.out.println("ClientTransactionTest is running");
		
		System.out.println("ClientTransactionTest: creating ObjectBroker-Object");
		ObjectBroker objBroker = ObjectBroker.init("localhost", 6666, true);
		
		System.out.println("ClientTransactionTest: getting NameService reference");
		NameService nameSvc = objBroker.getNameService();
		
		System.out.println("ClientTransactionTest: getting reference for managerimpl object");
		Object rawObjRef1 = nameSvc.resolve("testmanager");
		
		System.out.println("ClientTransactionTest: doing the narrow cast");
		ManagerImplBase manager = ManagerImplBase.narrowCast(rawObjRef1);
		
		System.out.println("ClientTransactionTest: getting reference for transactionimpl object");
		Object rawObjRef2 = nameSvc.resolve("testtransaction");
		
		System.out.println("ClientTransactionTest: doing the narrow cast");
		TransactionImplBase transcation = TransactionImplBase.narrowCast(rawObjRef2);
		
		System.out.println("ClientTransactionTest: creating account: " + owner + " " + branch);
		try {
			accountId = manager.createAccount(owner, branch);
		} catch (InvalidParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ClientTransactionTest: accountId: " + accountId);
		
		try {
			result = transcation.getBalance(accountId);
		} catch (cash_access.InvalidParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ClientTransactionTest: getbalance: " + result);
		
		try {
			transcation.deposit(accountId, 100);
		} catch (cash_access.InvalidParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ClientTransactionTest: deposit: 100");
		
		try {
			result = transcation.getBalance(accountId);
		} catch (cash_access.InvalidParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ClientTransactionTest: getbalance: " + result);
		
		try {
			transcation.withdraw(accountId, 50);
		} catch (cash_access.InvalidParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OverdraftException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ClientTransactionTest: deposit: 50");
		
		try {
			result = transcation.getBalance(accountId);
		} catch (cash_access.InvalidParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ClientTransactionTest: getbalance: " + result);
		
		
		System.out.println("ende");
	}
}
