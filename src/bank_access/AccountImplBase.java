package bank_access;

import bank_access.OverdraftException;
import mware_lib.ObjectRef;

public abstract class AccountImplBase {

    public abstract void transfer(double amount) throws OverdraftException;

    public abstract double getBalance();

    public static AccountImplBase narrowCast(Object rawObjectRef) {
    	ObjectRef objRef = (ObjectRef)rawObjectRef;
    	return new AccountImplStub(objRef.getHost(), objRef.getPort(), objRef.getObjId(), true);
    }

}
