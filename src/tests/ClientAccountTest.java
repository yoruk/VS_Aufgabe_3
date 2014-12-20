/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package tests;

import bank_access.AccountImplBase;
import bank_access.OverdraftException;
import mware_lib.NameService;
import mware_lib.ObjectBroker;

public class ClientAccountTest {
	public static void main(String[] args) {
		System.out.println("ClientAccountTest is running");
		
		System.out.println("ClientAccountTest: creating ObjectBroker-Object");
		ObjectBroker objBroker = ObjectBroker.init("localhost", 6666, true);
		
		System.out.println("ClientAccountTest: getting NameService reference");
		NameService nameSvc = objBroker.getNameService();
		
		System.out.println("ClientAccountTest: getting generic object reference");
		Object rawObjRef = nameSvc.resolve("testkonto");
		
		System.out.println("ClientAccountTest: doing the narrow cast");
		AccountImplBase konto = AccountImplBase.narrowCast(rawObjRef);
		
		System.out.println("ClientAccountTest: getbalance");
		System.out.println("guthaben: " + konto.getBalance());
		
		System.out.println("ClientAccountTest: transfer");
		try {
			konto.transfer(10);
		} catch (OverdraftException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ClientAccountTest: getbalance");
		System.out.println("guthaben: " + konto.getBalance());
		
		System.out.println("ClientAccountTest: transfer");
		try {
			konto.transfer(-11);
		} catch (OverdraftException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ClientAccountTest: getbalance");
		System.out.println("guthaben: " + konto.getBalance());
		
		System.out.println("ende");
	}
}
