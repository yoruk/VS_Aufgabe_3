package bank_access;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import bank_access.OverdraftException;
import mware_lib.Connection;
import mware_lib.Message;

public class AccountImplStub extends AccountImplBase {
	private String serverAddress;
	private int serverPort;
	private String objName;
	private boolean debug;
	
	public AccountImplStub(String serverAddress, int serverPort, String objName, boolean debug) {
		if(debug) {
			System.out.println("AccountImplStub() server @: " + serverAddress + ":" + serverPort 
								+ " objectname: " + objName);
		}
		
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.objName= objName;
		this.debug = debug;
	}
	
	@Override
	public void transfer(double amount) throws OverdraftException {
		if(debug) {
			System.out.println("AccountImplStub.transfer(): objectname: " + objName + " amount: " + amount);
		}
		
		Socket socket = null;
		Connection connection = null;
		Message msg = null;
		
		try {
			socket = new Socket(serverAddress, serverPort);
			connection = new Connection(socket);
			
			// sending message call
			msg = new Message();
			msg.setReason(Message.MessageReason.METHOD_CALL);
			msg.setMethod_name("transfer");
			Object[] methodParams = {amount};
			msg.setMethod_params(methodParams);
			msg.setObjName(objName);
			connection.send(msg);
			
			// receiving method return value
			msg = (Message)connection.receive();
			if((msg.getReason() == Message.MessageReason.EXCEPTION) && (msg.getPayload() instanceof OverdraftException)) {
				throw (OverdraftException)msg.getPayload();
			}
			
		} catch (UnknownHostException e) {
			if(debug) System.out.println("AccountImplStub.transfer(): ERROR!");
			e.printStackTrace();
		} catch (IOException e) {
			if(debug) System.out.println("AccountImplStub.transfer(): ERROR!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			if(debug) System.out.println("AccountImplStub.transfer(): ERROR!");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				if(debug) System.out.println("AccountImplStub.transfer(): ERROR!");
				e.printStackTrace();
			}
		}
	}

	@Override
	public double getBalance() {
		if(debug) {
			System.out.println("AccountImplStub.getBalance(): serverAddress: " 
								+ serverAddress + ":" + serverPort + " objectname: " + objName);
		}
		
		Socket socket = null;
		Connection connection = null;
		Message msg = null;
		double res = 0;
		
		try {
			socket = new Socket(serverAddress, serverPort);
			connection = new Connection(socket);
			
			// sending message call
			msg = new Message();
			msg.setReason(Message.MessageReason.METHOD_CALL);
			msg.setMethod_name("getbalance");
			msg.setObjName(objName);
			connection.send(msg);
					
			// receiving method return value
			msg = (Message)connection.receive();
			res = (double)msg.getMethod_return();
			
		} catch (UnknownHostException e) {
			if(debug) System.out.println("AccountImplStub.getBalance(): ERROR!");
			e.printStackTrace();
		} catch (IOException e) {
			if(debug) System.out.println("AccountImplStub.getBalance(): ERROR!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			if(debug) System.out.println("AccountImplStub.getBalance(): ERROR!");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				if(debug) System.out.println("AccountImplStub.getBalance(): ERROR!");
				e.printStackTrace();
			}
		}
		
		return res;
	}

}
