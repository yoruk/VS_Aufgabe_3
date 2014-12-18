package cash_access;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import cash_access.InvalidParamException;
import cash_access.OverdraftException;
import mware_lib.Connection;
import mware_lib.Message;

public class TransactionImplStub extends TransactionImplBase {
	private String serverAddress;
	private int serverPort;
	private String objName;
	private boolean debug;
	
	public TransactionImplStub(String serverAddress, int serverPort, String objName, boolean debug) {
		if(debug) {
			System.out.println("TransactionImplStub() server @: " + serverAddress + ":" + serverPort 
								+ " objectname: " + objName);
		}
		
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.objName= objName;
		this.debug = debug;
	}

	@Override
	public void deposit(String accountID, double amount) throws InvalidParamException {
		if(debug) {
			System.out.println("TransactionImplStub.transfer(): objectname: " + objName + " amount: " + amount);
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
			msg.setObjName(objName);
			msg.setMethod_name("deposit");
			Object[] methodParams = {accountID, amount};
			msg.setMethod_params(methodParams);
			connection.send(msg);
			
			// receiving method return value
			msg = (Message)connection.receive();
			if((msg.getReason() == Message.MessageReason.EXCEPTION) && (msg.getPayload() instanceof InvalidParamException)) {
				throw (InvalidParamException)msg.getPayload();
			}
			
		} catch (UnknownHostException e) {
			if(debug) System.out.println("TransactionImplStub.transfer(): ERROR!");
			e.printStackTrace();
		} catch (IOException e) {
			if(debug) System.out.println("TransactionImplStub.transfer(): ERROR!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			if(debug) System.out.println("TransactionImplStub.transfer(): ERROR!");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				if(debug) System.out.println("TransactionImplStub.transfer(): ERROR!");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void withdraw(String accountID, double amount) throws InvalidParamException, OverdraftException {
		if(debug) {
			System.out.println("TransactionImplStub.withdraw(): objectname: " + objName + " amount: " + amount);
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
			msg.setObjName(objName);
			msg.setMethod_name("withdraw");
			Object[] methodParams = {accountID, amount};
			msg.setMethod_params(methodParams);
			connection.send(msg);
			
			// receiving method return value
			msg = (Message)connection.receive();
			if(msg.getReason() == Message.MessageReason.EXCEPTION) {
				if((msg.getPayload() instanceof InvalidParamException)) {
					throw (InvalidParamException)msg.getPayload();					
				} else {
					throw (OverdraftException)msg.getPayload();
				}
			}
			
		} catch (UnknownHostException e) {
			if(debug) System.out.println("TransactionImplStub.withdraw(): ERROR!");
			e.printStackTrace();
		} catch (IOException e) {
			if(debug) System.out.println("TransactionImplStub.withdraw(): ERROR!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			if(debug) System.out.println("TransactionImplStub.withdraw(): ERROR!");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				if(debug) System.out.println("TransactionImplStub.withdraw(): ERROR!");
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public double getBalance(String accountID) throws InvalidParamException {
		if(debug) {
			System.out.println("TransactionImplStub.getBalance(): objectname: " + objName);
		}
		
		Socket socket = null;
		Connection connection = null;
		Message msg = null;
		double ret = 0;
		
		try {
			socket = new Socket(serverAddress, serverPort);
			connection = new Connection(socket);
			
			// sending message call
			msg = new Message();
			msg.setReason(Message.MessageReason.METHOD_CALL);
			msg.setObjName(objName);
			msg.setMethod_name("getbalance");
			connection.send(msg);
			
			// receiving method return value
			msg = (Message)connection.receive();
			if((msg.getReason() == Message.MessageReason.EXCEPTION) && (msg.getPayload() instanceof InvalidParamException)) {
					throw (InvalidParamException)msg.getPayload();
			} else {
				ret = (double)msg.getMethod_return();
			}
			
		} catch (UnknownHostException e) {
			if(debug) System.out.println("TransactionImplStub.withdraw(): ERROR!");
			e.printStackTrace();
		} catch (IOException e) {
			if(debug) System.out.println("TransactionImplStub.withdraw(): ERROR!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			if(debug) System.out.println("TransactionImplStub.withdraw(): ERROR!");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				if(debug) System.out.println("TransactionImplStub.withdraw(): ERROR!");
				e.printStackTrace();
			}
		}
		
		return ret;
	}
}
