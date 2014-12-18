package bank_access;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import bank_access.InvalidParamException;
import mware_lib.Connection;
import mware_lib.Message;

public class ManagerImplStub extends ManagerImplBase {
	private String serverAddress;
	private int serverPort;
	private String objName;
	private boolean debug;
	
	public ManagerImplStub(String serverAddress, int serverPort, String objName, boolean debug) {
		if(debug) {
			System.out.println("ManagerImplStub() server @: " + serverAddress + ":" + serverPort 
								+ " objectname: " + objName);
		}
		
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.objName= objName;
		this.debug = debug;
	}
	
	@Override
	public String createAccount(String owner, String branch) throws InvalidParamException {
		if(debug) {
			System.out.println("ManagerImplStub.transfer(): objectname: " + objName + " owner: " + owner + " branch: " + branch);
		}
		
		Socket socket = null;
		Connection connection = null;
		Message msg = null;
		String ret = null;
		
		try {
			socket = new Socket(serverAddress, serverPort);
			connection = new Connection(socket);
			
			// sending message call
			msg = new Message();
			msg.setReason(Message.MessageReason.METHOD_CALL);
			msg.setObjName(objName);
			msg.setMethod_name("createaccount");
			Object[] methodParams = {owner, branch};
			msg.setMethod_params(methodParams);
			connection.send(msg);
			
			// receiving method return value
			msg = (Message)connection.receive();
			if((msg.getReason() == Message.MessageReason.EXCEPTION) && (msg.getPayload() instanceof InvalidParamException)) {
				throw (InvalidParamException)msg.getPayload();
			} else {
				ret = (String)msg.getMethod_return();
			}
			
		} catch (UnknownHostException e) {
			if(debug) System.out.println("ManagerImplStub.transfer(): ERROR!");
			e.printStackTrace();
		} catch (IOException e) {
			if(debug) System.out.println("ManagerImplStub.transfer(): ERROR!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			if(debug) System.out.println("ManagerImplStub.transfer(): ERROR!");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				if(debug) System.out.println("ManagerImplStub.transfer(): ERROR!");
				e.printStackTrace();
			}
		}
		
		return ret;
	}

}
