/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package bank_access;

import bank_access.InvalidParamException;

public class ManagerImpl extends ManagerImplBase {
	@Override
	public String createAccount(String owner, String branch) throws InvalidParamException {	
		if(owner == null && branch == null) {
			throw new InvalidParamException("whatever");
		} else {
			return "hallo welt";
		}
	}

	
}
