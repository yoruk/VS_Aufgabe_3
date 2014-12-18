package cash_access;

import java.io.IOException;
import java.util.Map;

import cash_access.InvalidParamException;
import cash_access.OverdraftException;
import mware_lib.Connection;
import mware_lib.Message;
import mware_lib.NameService;

public class TransactionImplSkeleton {
	TransactionImplBase obj = null;
	Connection connection = null;
	//NameService nameService = null;
	
	public TransactionImplSkeleton(Connection connection, Message msg, TransactionImplBase obj, Map<String, Object> object_cloud, NameService nameService) {
		this.obj = obj;
		this.connection = connection;
		this.connection = connection;
		Object[] params;
		
		((TransactionImpl)obj).initTransactionImpl(nameService);
		
		switch(msg.getMethod_name()) {
		case "deposit":
			params = msg.getMethod_params();
			deposit((String)params[0], (double)params[1]);
			break;
		case "withdraw":
			params = msg.getMethod_params();
			withdraw((String)params[0], (double)params[1]);
			break;
		case "getbalance":
			params = msg.getMethod_params();
			getBalance((String)params[0]);
			break;	
		default:
			msg = new Message();
			
			try {
				msg.setReason(Message.MessageReason.ERROR);
				connection.send(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void deposit(String accountID, double amount) {
		try {
			obj.deposit(accountID, amount);
		} catch (InvalidParamException e) {
			System.out.println("TransactionImpleSkeleton.deposit(): ERROR!");
			//e.printStackTrace();
			
			Message msg =  new Message();
			msg.setReason(Message.MessageReason.EXCEPTION);
			msg.setPayload(e);
			
			try {
				connection.send(msg);
			} catch (IOException e1) {
				System.out.println("TransactionImpleSkeleton.deposit(): ERROR!");
				e1.printStackTrace();
			}
		}
		
		Message msg = new Message();
		msg.setReason(Message.MessageReason.METHOD_RETURN);
		try {
			connection.send(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void withdraw(String accountID, double amount) {
		try {
			obj.withdraw(accountID, amount);
		} catch (InvalidParamException e) {
			System.out.println("TransactionImpleSkeleton.withdraw(): ERROR!");
			e.printStackTrace();
			
			Message msg =  new Message();
			msg.setReason(Message.MessageReason.EXCEPTION);
			msg.setPayload(e);
			
			try {
				connection.send(msg);
			} catch (IOException e1) {
				System.out.println("TransactionImpleSkeleton.withdraw(): ERROR!");
				e1.printStackTrace();
			}
		} catch (OverdraftException e) {
			System.out.println("TransactionImpleSkeleton.withdraw(): ERROR!");
			e.printStackTrace();
			
			Message msg =  new Message();
			msg.setReason(Message.MessageReason.EXCEPTION);
			msg.setPayload(e);
			
			try {
				connection.send(msg);
			} catch (IOException e1) {
				System.out.println("TransactionImpleSkeleton.withdraw(): ERROR!");
				e1.printStackTrace();
			}
		}
		
		Message msg = new Message();
		msg.setReason(Message.MessageReason.METHOD_RETURN);
		try {
			connection.send(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getBalance(String accountID) {
		double ret = 0;
		
		try {
			ret = obj.getBalance(accountID);
		} catch (InvalidParamException e) {
			System.out.println("TransactionImpleSkeleton.getBalance(): ERROR!");
			e.printStackTrace();
			
			Message msg =  new Message();
			msg.setReason(Message.MessageReason.EXCEPTION);
			msg.setPayload(e);
			
			try {
				connection.send(msg);
			} catch (IOException e1) {
				System.out.println("TransactionImpleSkeleton.getBalance(): ERROR!");
				e1.printStackTrace();
			}
		} 
		
		Message msg = new Message();
		msg.setReason(Message.MessageReason.METHOD_RETURN);
		msg.setMethod_return(ret);
		try {
			connection.send(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
