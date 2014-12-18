package tests;

import mware_lib.NameService;
import mware_lib.ObjectBroker;
import bank_access.InvalidParamException;
import bank_access.ManagerImplBase;

public class ClientManagerTest {
	public static void main(String[] args) {
		String owner = "123";
		String branch = "456";
		
		System.out.println("ClientManagerTest is running");
		
		System.out.println("ClientManagerTest: creating ObjectBroker-Object");
		ObjectBroker objBroker = ObjectBroker.init("localhost", 6666, true);
		
		System.out.println("ClientManagerTest: getting NameService reference");
		NameService nameSvc = objBroker.getNameService();
		
		System.out.println("ClientManagerTest: getting generic object reference");
		Object rawObjRef = nameSvc.resolve("testmanager");
		
		System.out.println("ClientManagerTest: doing the narrow cast");
		ManagerImplBase manager = ManagerImplBase.narrowCast(rawObjRef);
		
		System.out.println("ServerManagerTest: creating account: " + owner + " " + branch);
		try {
			manager.createAccount(owner, branch);
		} catch (InvalidParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ende");
	}
}
