/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package bank_access;

import java.io.IOException;

import bank_access.OverdraftException;
import mware_lib.Connection;
import mware_lib.Message;

public class AccountImplSkeleton {	
	AccountImplBase obj = null;
	Connection connection = null;
	boolean debug = false;
	
	public AccountImplSkeleton(Connection connection, Message msg, AccountImplBase obj, boolean debug) {		
		this.obj = obj;
		this.connection = connection;
		this.debug = debug;
		
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
				if(debug) e.printStackTrace();
			}
		}
	}
	
	public void transfer(double amount) {
		Message msg =  new Message();

		try {
		
			obj.transfer(amount);
		
		} catch (OverdraftException e) {
			if(debug) {
				System.out.println("AccountImpleSkeleton.transfer(): ERROR!");
				e.printStackTrace();				
			}

			msg.setReason(Message.MessageReason.EXCEPTION);
			msg.setPayload((Exception)e);
			try {
				connection.send(msg);
			} catch (IOException e1) {
				if(debug) {
					System.out.println("AccountImpleSkeleton.transfer(): ERROR!");
					e1.printStackTrace();					
				}
			}
			
			return;
		}
		
		msg.setReason(Message.MessageReason.METHOD_RETURN);
		try {
			connection.send(msg);
		} catch (IOException e) {
			if(debug) {
				System.out.println("AccountImpleSkeleton.transfer(): ERROR!");
				e.printStackTrace();								
			}
		}
	}

	public void getBalance() {
		Message msg = new Message();
		msg.setReason(Message.MessageReason.METHOD_RETURN);
		msg.setMethod_return(obj.getBalance());
		
		try {
			connection.send(msg);
		} catch (IOException e) {
			if(debug) {
				System.out.println("AccountImpleSkeleton.getBalance(): ERROR!");
				e.printStackTrace();				
			}
		}
	}

}
