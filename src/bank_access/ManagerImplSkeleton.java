package bank_access;

import java.io.IOException;
import java.util.Map;

import mware_lib.Connection;
import mware_lib.Message;

public class ManagerImplSkeleton {
	ManagerImplBase obj = null;
	Connection connection = null;
	//Map<String, Object> object_cloud = null;
	
	public ManagerImplSkeleton(Connection connection, Message msg, ManagerImplBase obj, Map<String, Object> object_cloud) {
		
		this.connection = connection;
		//this.object_cloud = object_cloud;
		ManagerImpl tmp_obj = (ManagerImpl)obj;
		Message tmp_msg = new Message();
		String ret = null;
		
		// called method is "createaccount"
		if(msg.getMethod_name().equals("createaccount")) {
			//String[] params = (String[])msg.getMethod_params();
			Object[] params= msg.getMethod_params();
			
			try {
				tmp_obj.initManagerImpl(object_cloud);
				ret = tmp_obj.createAccount((String)params[0], (String)params[1]);
			} catch (InvalidParamException e) {
				System.out.println("ManagerImpleSkeleton.createAccount(): ERROR!");
				e.printStackTrace();
				
				tmp_msg.setReason(Message.MessageReason.EXCEPTION);
				tmp_msg.setPayload(e);
				
				try {
					connection.send(tmp_msg);
				} catch (IOException e1) {
					System.out.println("ManagerImpleSkeleton.createAccount(): ERROR!");
					e1.printStackTrace();
				}
			}
			
			tmp_msg.setReason(Message.MessageReason.METHOD_RETURN);
			tmp_msg.setMethod_return(ret);
			try {
				connection.send(tmp_msg);
			} catch (IOException e) {
				System.out.println("AccountImpleSkeleton.transfer(): ERROR!");
				e.printStackTrace();
			}
			
		// called method is unknown
		} else {
			
			try {
				tmp_msg.setReason(Message.MessageReason.ERROR);
				connection.send(tmp_msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
