package tests;

import bank_access.ManagerImpl;
import bank_access.ManagerImplBase;
import mware_lib.NameService;
import mware_lib.ObjectBroker;

public class ServerManagerTest {
	public static void main(String[] args) {
		System.out.println("ServerManagerTest is running");
		
		System.out.println("ServerManagerTest: creating ObjectBroker-Object");
		ObjectBroker objBroker = ObjectBroker.init("localhost", 6666, true);
		
		System.out.println("ServerManagerTest: getting NameService reference");
		NameService nameSvc = objBroker.getNameService();
		
		System.out.println("ServerManagerTest: binding a ManagerImpl object");
		ManagerImplBase manager = new ManagerImpl(objBroker);
	}
}