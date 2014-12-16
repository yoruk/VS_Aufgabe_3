package bank_access;

import mware_lib.NameService;
import mware_lib.ObjectBroker;
import mware_lib.StringChecker;

public class ManagerImpl extends ManagerImplBase {
	private ObjectBroker objBroker;
	
	public ManagerImpl(ObjectBroker objBroker) {
		this.objBroker = objBroker;
	}
	
	@Override
	public String createAccount(String owner, String branch) throws InvalidParamException {
		StringBuilder sb = new StringBuilder();
		
		if(owner == null) {
			owner = "null";
		} else {
			if(!StringChecker.checkString(owner)) {
				throw new InvalidParamException("ManagerImpl.createAccount(): invalid identifier for owner");
			}
		}
		
		if(branch == null) {
			branch = "null";
		} else {
			if(!StringChecker.checkString(branch)) {
				throw new InvalidParamException("ManagerImpl.createAccount(): invalid identifier for branch");
			}
		}

		NameService nameSvc = objBroker.getNameService();
		AccountImplBase konto = new AccountImpl();
		sb.append(owner).append("-").append(branch);
		nameSvc.rebind(konto, sb.toString());
		
		return sb.toString();
	}

}
