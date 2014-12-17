package bank_access;

import java.util.Map;

import mware_lib.StringChecker;

public class ManagerImpl extends ManagerImplBase {
	private Map<String, Object> object_cloud;
	
	public void initManagerImpl(Map<String, Object> object_cloud) {
		this.object_cloud = object_cloud;
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

		AccountImplBase account = new AccountImpl();
		sb.append(owner).append("-").append(branch);
		object_cloud.put(sb.toString(), account);
		
		return sb.toString();
	}

}
