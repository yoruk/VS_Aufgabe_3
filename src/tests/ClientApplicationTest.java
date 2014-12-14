package tests;

import bank_access.AccountImplBase;
import mware_lib.NameService;
import mware_lib.ObjectBroker;

public class ClientApplicationTest {
	public static void main(String[] args) {
		System.out.println("ClientApplicationTest is running");
		
		System.out.println("ClientApplicationTest: creating ObjectBroker-Object");
		ObjectBroker objBroker = ObjectBroker.init("localhost", 6666, true);
		
		System.out.println("ClientApplicationTest: getting NameService reference");
		NameService nameSvc = objBroker.getNameService();
		
		System.out.println("ClientApplicationTest: getting generic object reference");
		Object rawObjRef = nameSvc.resolve("testkonto");
		
		System.out.println("ClientApplicationTest: doing the narrow cast");
		AccountImplBase konto = AccountImplBase.narrowCast(rawObjRef);
		
		System.out.println(konto.getBalance());
	}
}
