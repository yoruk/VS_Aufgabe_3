package bank_access;

import java.io.IOException;
import java.util.Map;

import bank_access.InvalidParamException;
import mware_lib.Connection;
import mware_lib.Message;
import mware_lib.NameService;

public class ManagerImplSkeleton {	
	public ManagerImplSkeleton(Connection connection, Message msg, 
			ManagerImplBase obj, Map<String, Object> object_cloud, NameService nameService) {
		
		String ret = null;
		
		// called method is "createaccount"
		if(msg.getMethod_name().equals("createaccount")) {
			//String[] params = (String[])msg.getMethod_params();
			Object[] params = msg.getMethod_params();
			
			try {
				//((ManagerImpl)obj).initManagerImpl(object_cloud, nameService);
				ret = obj.createAccount((String)params[0], (String)params[1]);
			} catch (InvalidParamException e) {
				System.out.println("ManagerImpleSkeleton.createAccount(): ERROR!");
				e.printStackTrace();
				
				msg =  new Message();
				msg.setReason(Message.MessageReason.EXCEPTION);
				msg.setPayload(e);
				
				try {
					connection.send(msg);
				} catch (IOException e1) {
					System.out.println("ManagerImpleSkeleton.createAccount(): ERROR!");
					e1.printStackTrace();
				}
			}
			
			msg = new Message();
			msg.setReason(Message.MessageReason.METHOD_RETURN);
			msg.setMethod_return(ret);
			try {
				connection.send(msg);
			} catch (IOException e) {
				System.out.println("AccountImpleSkeleton.transfer(): ERROR!");
				e.printStackTrace();
			}
			
		// called method is unknown
		} else {
			
			try {
				msg = new Message();
				msg.setReason(Message.MessageReason.ERROR);
				connection.send(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
