package tests;

import mware_lib.NameService;
import mware_lib.ObjectBroker;
import bank_access.InvalidParamException;
import bank_access.ManagerImplBase;

public class ClientManagerTest {
	public static void main(String[] args) {
		System.out.println("ClientManagerTest is running");
		
		System.out.println("ClientManagerTest: creating ObjectBroker-Object");
		ObjectBroker objBroker = ObjectBroker.init("localhost", 6666, true);
		
		System.out.println("ClientManagerTest: getting NameService reference");
		NameService nameSvc = objBroker.getNameService();
		
		System.out.println("ClientManagerTest: getting generic object reference");
		Object rawObjRef = nameSvc.resolve("testmanager");
		
		System.out.println("ClientManagerTest: doing the narrow cast");
		ManagerImplBase manager = ManagerImplBase.narrowCast(rawObjRef);
		
		System.out.println("ClientManagerTest: createAccount m s");
		try {
			System.out.println("account id: " + manager.createAccount("m", "s"));
		} catch (InvalidParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ende");
	}
}
