package cash_access;

import java.util.Map;

import bank_access.AccountImplBase;
import mware_lib.StringChecker;

public class TransactionImpl extends TransactionImplBase {
	private Map<String, Object> object_cloud;
	
	public void initTransactionImpl(Map<String, Object> object_cloud) {
		this.object_cloud = object_cloud;
	}
	
	@Override
	public void deposit(String accountID, double amount) throws InvalidParamException {
		AccountImplBase account = null;
		
		// check if amount is negative
		if(amount < 0) {
			throw new InvalidParamException("TransactionImpl.deposit(): ERROR, amount is negativ: " + amount);
		}
		
		// check if the string has a valid format
		if((accountID == null) || !StringChecker.checkString(accountID)) {
			throw new InvalidParamException("TransactionImpl.deposit(): ERROR, invalid Account identifier!");
		} else {
			
			account = (AccountImplBase)object_cloud.get(accountID);
			
			// check if there is an object that belongs to the id
			if(account == null) {
				throw new InvalidParamException("TransactionImpl.deposit(): ERROR, Account doesn't exist!");
			} else {
				
				try {
					account.transfer(amount);
				} catch (bank_access.OverdraftException e) {
					System.out.println("TransactionImpl.deposit(): ERROR");
					e.printStackTrace();
				}
				
			}
		}
	}

	@Override
	public void withdraw(String accountID, double amount) throws InvalidParamException, OverdraftException {
		AccountImplBase account = null;
		
		// check if amount is negative
		if(amount < 0) {
			throw new InvalidParamException("TransactionImpl.withdraw(): ERROR, amount is negativ: " + amount);
		}
		
		// check if the string has a valid format
		if((accountID == null) || !StringChecker.checkString(accountID)) {
			throw new InvalidParamException("TransactionImpl.withdraw(): ERROR, invalid Account identifier!");
		} else {
			
			account = (AccountImplBase)object_cloud.get(accountID);
			
			// check if there is an object that belongs to the id
			if(account == null) {
				throw new InvalidParamException("TransactionImpl.withdraw(): ERROR, Account doesn't exist!");
			} else {
				
				try {
					account.transfer(-amount);
				} catch (bank_access.OverdraftException e) {
					//System.out.println("TransactionImpl.withdraw(): ERROR");
					//e.printStackTrace();
					throw new OverdraftException(e.getMessage());
				}
				
			}
		}
	}

	@Override
	public double getBalance(String accountID) throws InvalidParamException {
		AccountImplBase account = null;
		double ret = 0;
		
		// check if the string has a valid format
		if((accountID == null) || !StringChecker.checkString(accountID)) {
			throw new InvalidParamException("TransactionImpl.getBalance(): ERROR, invalid Account identifier!");
		} else {
			
			account = (AccountImplBase)object_cloud.get(accountID);
			
			// check if there is an object that belongs to the id
			if(account == null) {
				throw new InvalidParamException("TransactionImpl.getBalance(): ERROR, Account doesn't exist!");
			} else {
				
				ret = account.getBalance();
				
			}
		}
		
		return ret;
	}

}
