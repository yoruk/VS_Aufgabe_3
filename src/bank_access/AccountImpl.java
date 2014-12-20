/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package bank_access;

import bank_access.OverdraftException;

public class AccountImpl extends AccountImplBase {	
	@Override
	public void transfer(double amount) throws OverdraftException {
	}

	@Override
	public double getBalance() {
		return 42;
	}

}
