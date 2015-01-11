/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

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
	private boolean debug = false;
	private String nameServiceAddress;
	private int nameServicePort;
	
	public TransactionImplStub(ObjectRef objRef, boolean debug) {
		this.serverAddress = objRef.getHost();
		this.serverPort = objRef.getPort();
		this.objName= objRef.getObjId();
		this.debug = debug;
		this.nameServiceAddress = objRef.getNameServiceAddr();
		this.nameServicePort = objRef.getNameServicePort();
		
		//if(debug) {
			System.out.println("TransactionImplStub() server @: " + serverAddress + ":" + serverPort 
					+ " objectname: " + objName);
		//}
	}

	@Override
	public void deposit(String accountID, double amount) throws InvalidParamException {
		String tmp_accountID = (accountID == null) ? "null_pointer" : accountID;
		System.out.println("### DEBUG: transaction.deposit(): accountID=" + tmp_accountID + " amount=" + amount);
		
		if(debug) {
			System.out.println("TransactionImplStub.transfer(): objectname: " + objName + " amount: " + amount);
		}
		
		Socket socket = null;
		Connection connection = null;
		Message msg = null;
		NameServiceImpl nameservice = new NameServiceImpl(nameServiceAddress, nameServicePort, debug);
		ObjectRef accountRef = (ObjectRef)nameservice.resolve(accountID);
		if(accountRef == null) {
			return;
		}
		
		try {
			socket = new Socket(accountRef.getHost(), accountRef.getPort());
			connection = new Connection(socket);
			
			// sending message call
			msg = new Message();
			msg.setReason(Message.MessageReason.METHOD_CALL);
			msg.setObjName(accountRef.getObjId());
			msg.setMethod_name("transfer");
			Object[] methodParams = {amount};
			msg.setMethod_params(methodParams);
			connection.send(msg);
			
			// receiving method return value
			msg = (Message)connection.receive();
			if((msg.getReason() == Message.MessageReason.EXCEPTION) && (msg.getPayload() instanceof InvalidParamException)) {
				//throw (InvalidParamException)msg.getPayload();
				throw new InvalidParamException(((InvalidParamException)msg.getPayload()).getMessage());
			}
			
		} catch (UnknownHostException e) {
			if(debug) {
				System.out.println("TransactionImplStub.transfer(): ERROR!");
				e.printStackTrace();
			}
		} catch (IOException e) {
			if(debug) {
				System.out.println("TransactionImplStub.transfer(): ERROR!");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			if(debug) {
				System.out.println("TransactionImplStub.transfer(): ERROR!");
				e.printStackTrace();
			}
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				if(debug) {
					System.out.println("TransactionImplStub.transfer(): ERROR!");
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void withdraw(String accountID, double amount) throws InvalidParamException, OverdraftException {
		String tmp_accountID = (accountID == null) ? "null_pointer" : accountID;
		System.out.println("### DEBUG: transaction.deposit(): accountID=" + tmp_accountID + " amount=" + amount);
		
		if(debug) {
			System.out.println("TransactionImplStub.withdraw(): objectname: " + objName + " amount: " + amount);
		}
		
		Socket socket = null;
		Connection connection = null;
		Message msg = null;
		NameServiceImpl nameservice = new NameServiceImpl(nameServiceAddress, nameServicePort, debug);
		ObjectRef accountRef = (ObjectRef)nameservice.resolve(accountID);
		if(accountRef == null) {
			return;
		}
		
		try {
			socket = new Socket(accountRef.getHost(), accountRef.getPort());
			connection = new Connection(socket);
			
			// sending message call
			msg = new Message();
			msg.setReason(Message.MessageReason.METHOD_CALL);
			msg.setObjName(accountRef.getObjId());
			msg.setMethod_name("transfer");
			Object[] methodParams = {-amount};
			msg.setMethod_params(methodParams);
			connection.send(msg);
			
			// receiving method return value
			msg = (Message)connection.receive();
			if(msg.getReason() == Message.MessageReason.EXCEPTION) {
				if((msg.getPayload() instanceof InvalidParamException)) {
					//throw (InvalidParamException)msg.getPayload();
					throw new InvalidParamException(((InvalidParamException)msg.getPayload()).getMessage());
				} else {
					//throw (OverdraftException)msg.getPayload();
					throw new OverdraftException(((OverdraftException)msg.getPayload()).getMessage());
				}
			}
			
		} catch (UnknownHostException e) {
			if(debug) {
				System.out.println("TransactionImplStub.withdraw(): ERROR!");
				e.printStackTrace();
			}
		} catch (IOException e) {
			if(debug) {
				System.out.println("TransactionImplStub.withdraw(): ERROR!");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			if(debug) {
				System.out.println("TransactionImplStub.withdraw(): ERROR!");
				e.printStackTrace();
			}
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				if(debug) {
					System.out.println("TransactionImplStub.withdraw(): ERROR!");
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public double getBalance(String accountID) throws InvalidParamException {
		String tmp_accountID = (accountID == null) ? "null_pointer" : accountID;
		System.out.println("### DEBUG: transaction.getBalance(): accountID=" + tmp_accountID);
		
		if(debug) {
			System.out.println("TransactionImplStub.getBalance(): objectname: " + objName);
		}
		
		Socket socket = null;
		Connection connection = null;
		Message msg = null;
		double ret = 0;
		NameServiceImpl nameservice = new NameServiceImpl(nameServiceAddress, nameServicePort, debug);
		ObjectRef accountRef = (ObjectRef)nameservice.resolve(accountID);
		if(accountRef == null) {
			return 0;
		}
		
		try {
			socket = new Socket(accountRef.getHost(), accountRef.getPort());
			connection = new Connection(socket);
			
			// sending message call
			msg = new Message();
			msg.setReason(Message.MessageReason.METHOD_CALL);
			msg.setObjName(accountRef.getObjId());
			msg.setMethod_name("getbalance");
			Object[] methodParams = {accountID};
			msg.setMethod_params(methodParams);
			connection.send(msg);
			
			// receiving method return value
			msg = (Message)connection.receive();
			if(msg.getReason() == Message.MessageReason.EXCEPTION) {
					//throw (InvalidParamException)msg.getPayload();
					//throw new InvalidParamException(((InvalidParamException)msg.getPayload()).getMessage());
					try {
						throw (Exception)msg.getPayload();
					} catch (Exception e) {
						if(debug) e.printStackTrace();
					}
			} else {
				ret = (double)msg.getMethod_return();
			}
			
		} catch (UnknownHostException e) {
			if(debug) {
				System.out.println("TransactionImplStub.withdraw(): ERROR!");
				e.printStackTrace();
			}
		} catch (IOException e) {
			if(debug) {
				System.out.println("TransactionImplStub.withdraw(): ERROR!");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			if(debug) {
				System.out.println("TransactionImplStub.withdraw(): ERROR!");
				e.printStackTrace();
			}
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				if(debug) {
					System.out.println("TransactionImplStub.withdraw(): ERROR!");
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
}
