package cash_access;

import mware_lib.ObjectRef;
import cash_access.InvalidParamException;
import cash_access.OverdraftException;

public abstract class TransactionImplBase {

  public abstract void deposit(String accountID, double amount) throws InvalidParamException;

  public abstract void withdraw(String accountID, double amount) throws InvalidParamException, OverdraftException;

  public abstract double getBalance(String accountID) throws InvalidParamException;

  public static TransactionImplBase narrowCast(Object rawObjectRef) {
	  ObjectRef objRef = (ObjectRef)rawObjectRef;
	  return new TransactionImplStub(objRef, true);
  }

}
