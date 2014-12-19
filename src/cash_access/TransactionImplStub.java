package cash_access;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import cash_access.InvalidParamException;
import cash_access.OverdraftException;
import mware_lib.Connection;
import mware_lib.Message;
import mware_lib.NameServiceImpl;
import mware_lib.ObjectRef;

public class TransactionImplStub extends TransactionImplBase {
	private String serverAddress;
	private int serverPort;
	private String objName;
	private boolean debug;
	private String nameServiceAddress;
	private int nameServicePort;
	
	public TransactionImplStub(ObjectRef objRef, boolean debug) {
		this.serverAddress = objRef.getHost();
		this.serverPort = objRef.getPort();
		this.objName= objRef.getObjId();
		this.debug = debug;
		this.nameServiceAddress = objRef.getNameServiceAddr();
		this.nameServicePort = objRef.getNameServicePort();
		
		if(debug) {
			System.out.println("TransactionImplStub() server @: " + serverAddress + ":" + serverPort 
					+ " objectname: " + objName);
		}
	}

	@Override
	public void deposit(String accountID, double amount) throws InvalidParamException {
		if(debug) {
			System.out.println("TransactionImplStub.transfer(): objectname: " + objName + " amount: " + amount);
		}
		
		Socket socket = null;
		Connection connection = null;
		Message msg = null;
		NameServiceImpl nameservice = new NameServiceImpl(nameServiceAddress, nameServicePort, true);
		ObjectRef accountRef = (ObjectRef)nameservice.resolve(accountID);
		if(accountRef == null) {
			return;
		}
		
		try {
			socket = new Socket(serverAddress, serverPort);
			connection = new Connection(socket);
			
			// sending message call
			msg = new Message();
			msg.setReason(Message.MessageReason.METHOD_CALL);
//			msg.setObjName(objName);
//			msg.setMethod_name("deposit");
//			Object[] methodParams = {accountID, amount};
//			msg.setMethod_params(methodParams);
			msg.setObjName(accountRef.getObjId());
			msg.setMethod_name("transfer");
			Object[] methodParams = {amount};
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
		NameServiceImpl nameservice = new NameServiceImpl(nameServiceAddress, nameServicePort, true);
		ObjectRef accountRef = (ObjectRef)nameservice.resolve(accountID);
		if(accountRef == null) {
			return;
		}
		
		try {
			socket = new Socket(serverAddress, serverPort);
			connection = new Connection(socket);
			
			// sending message call
			msg = new Message();
			msg.setReason(Message.MessageReason.METHOD_CALL);
			msg.setObjName(accountRef.getObjId());
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
		NameServiceImpl nameservice = new NameServiceImpl(nameServiceAddress, nameServicePort, true);
		ObjectRef accountRef = (ObjectRef)nameservice.resolve(accountID);
		if(accountRef == null) {
			System.out.println("###################################" + accountID + " " + ret);
			return 0;
		}
		
		System.out.println("################################### 1");
		try {
			socket = new Socket(serverAddress, serverPort);
			connection = new Connection(socket);
			
			System.out.println("################################### 2");
			
			// sending message call
			msg = new Message();
			msg.setReason(Message.MessageReason.METHOD_CALL);
			msg.setObjName(accountRef.getObjId());
			msg.setMethod_name("getbalance");
			Object[] methodParams = {accountID};
			msg.setMethod_params(methodParams);
			System.out.println("################################### 3");
			connection.send(msg);
			
			System.out.println("################################### 4");
			
			// receiving method return value
			msg = (Message)connection.receive();
			System.out.println("################################### 5");
			if((msg.getReason() == Message.MessageReason.EXCEPTION) && (msg.getPayload() instanceof InvalidParamException)) {
					throw (InvalidParamException)msg.getPayload();
			} else {
				System.out.println("################################### vor ret" + ret);
				ret = (double)msg.getMethod_return();
				System.out.println("################################### danach ret" + ret);
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
		System.out.println("################################### 6");
		return ret;
	}
}
