package bank_access;

import bank_access.InvalidParamException;
import mware_lib.ObjectRef;

public abstract class ManagerImplBase {

  public abstract String createAccount(String owner, String branch) throws InvalidParamException;

  public static ManagerImplBase narrowCast(Object rawObjectRef) {
	  	ObjectRef objRef = (ObjectRef)rawObjectRef;
  		return new ManagerImplStub(objRef, true);
  }
}
