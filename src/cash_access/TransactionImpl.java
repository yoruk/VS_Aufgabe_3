package cash_access;

import java.util.Map;

import cash_access.InvalidParamException;
import cash_access.OverdraftException;
import bank_access.AccountImplBase;
import mware_lib.NameService;
import mware_lib.StringChecker;

public class TransactionImpl extends TransactionImplBase {
	private NameService nameService;
	
	public void initTransactionImpl(NameService nameService) {
		this.nameService = nameService;
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
			
			// get the actual account object to work on
			Object rawObjRef = nameService.resolve(accountID);
			account = AccountImplBase.narrowCast(rawObjRef);
			
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
			
			// get the actual account object to work on
			Object rawObjRef = nameService.resolve(accountID);
			account = AccountImplBase.narrowCast(rawObjRef);
			
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
		
		// check if the string has a valid format
		if((accountID == null) || !StringChecker.checkString(accountID)) {
			throw new InvalidParamException("TransactionImpl.getBalance(): ERROR, invalid Account identifier!");
		} else {
			
			// get the actual account object to work on
			Object rawObjRef = nameService.resolve(accountID);
			account = AccountImplBase.narrowCast(rawObjRef);
			
			// check if there is an object that belongs to the id
			if(account == null) {
				throw new InvalidParamException("TransactionImpl.getBalance(): ERROR, Account doesn't exist!");
			} else {
				
				return account.getBalance();
				
			}
		}
	}

}
