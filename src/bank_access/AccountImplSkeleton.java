package bank_access;

import java.io.IOException;

import bank_access.OverdraftException;
import mware_lib.Connection;
import mware_lib.Message;

public class AccountImplSkeleton {	
	AccountImplBase obj = null;
	Connection connection = null;
	
	public AccountImplSkeleton(Connection connection, Message msg, AccountImplBase obj) {		
		this.obj = obj;
		this.connection = connection;
		
		switch(msg.getMethod_name()) {
		case "transfer":
			Object[] params = msg.getMethod_params();
			transfer((double)params[0]);
			break;
		case "getbalance":
			getBalance();
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
	
	public void transfer(double amount) {
		Message msg =  new Message();

		try {
		
			obj.transfer(amount);
		
		} catch (OverdraftException e) {
			System.out.println("AccountImpleSkeleton.transfer(): ERROR!");
			e.printStackTrace();

			msg.setReason(Message.MessageReason.EXCEPTION);
			msg.setPayload(e);
			try {
				connection.send(msg);
			} catch (IOException e1) {
				System.out.println("AccountImpleSkeleton.transfer(): ERROR!");
				e1.printStackTrace();
			}
			
			return;
		}
		
		msg.setReason(Message.MessageReason.METHOD_RETURN);
		try {
			connection.send(msg);
		} catch (IOException e) {
			System.out.println("AccountImpleSkeleton.transfer(): ERROR!");
			e.printStackTrace();
		}
	}

	public void getBalance() {
		Message msg = new Message();
		msg.setReason(Message.MessageReason.METHOD_RETURN);
		msg.setMethod_return(obj.getBalance());
		
		try {
			connection.send(msg);
		} catch (IOException e) {
			System.out.println("AccountImpleSkeleton.getBalance(): ERROR!");
			e.printStackTrace();
		}
	}

}
