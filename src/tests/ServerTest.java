package tests;

import bank_access.AccountImpl;
import mware_lib.NameService;
import mware_lib.ObjectBroker;

public class ServerTest {
	public static void main(String[] args) {
		System.out.println("ServerApplicationTest is running");
		
		System.out.println("ServerApplicationTest: creating ObjectBroker-Object");
		ObjectBroker objBroker = ObjectBroker.init("localhost", 6666, true);
		
		System.out.println("ServerApplicationTest: getting NameService reference");
		NameService nameSvc = objBroker.getNameService();
		
		System.out.println("ServerApplicationTest: binding a AccountImpl object");
		AccountImpl konto = new AccountImpl();
		nameSvc.rebind((Object)konto, "testkonto");
	}
}
