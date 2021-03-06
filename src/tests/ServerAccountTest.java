/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package tests;

import bank_access.AccountImpl;
import bank_access.AccountImplBase;
import bank_access.OverdraftException;
import mware_lib.NameService;
import mware_lib.ObjectBroker;

public class ServerAccountTest {
	public static void main(String[] args) {
		System.out.println("ServerAccountTest is running");
		
		System.out.println("ServerAccountTest: creating ObjectBroker-Object");
		ObjectBroker objBroker = ObjectBroker.init("localhost", 6666, true);
		
		System.out.println("ServerAccountTest: getting NameService reference");
		NameService nameSvc = objBroker.getNameService();
		
		System.out.println("ServerAccountTest: binding a AccountImpl object");
		AccountImplBase konto = new AccountImpl();
		nameSvc.rebind((Object)konto, "testkonto");
		
//		try {
//			konto.transfer(10);
//		} catch (OverdraftException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("######### test:" + konto.getBalance());
	}
}
