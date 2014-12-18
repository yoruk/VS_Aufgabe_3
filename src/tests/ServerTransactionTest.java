package tests;

import cash_access.TransactionImpl;
import cash_access.TransactionImplBase;
import mware_lib.NameService;
import mware_lib.ObjectBroker;
import bank_access.AccountImpl;
import bank_access.AccountImplBase;
import bank_access.ManagerImpl;
import bank_access.ManagerImplBase;

public class ServerTransactionTest {
	public static void main(String[] args) {		
		System.out.println("ServerTransactionTest is running");
		
		System.out.println("ServerManagerTest: creating ObjectBroker-Object");
		ObjectBroker objBroker = ObjectBroker.init("localhost", 6666, true);
		
		System.out.println("ServerManagerTest: getting NameService reference");
		NameService nameSvc = objBroker.getNameService();
		
//		System.out.println("ServerManagerTest: binding a AccountImpl object");
//		AccountImplBase account = new AccountImpl();
//		nameSvc.rebind((Object)account, "testaccount");
		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println("ServerManagerTest: binding a ManagerImpl object");
		ManagerImplBase manager = new ManagerImpl();
		nameSvc.rebind((Object)manager, "testmanager");
		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
				
		System.out.println("ServerManagerTest: binding a TransactionImpl object");
		TransactionImplBase transaction = new TransactionImpl();
		nameSvc.rebind((Object)transaction, "testtransaction");
	}
}
