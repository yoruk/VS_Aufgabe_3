package bank_access;

import java.io.IOException;

import mware_lib.Connection;
import mware_lib.Message;

public class AccountImplSkeleton extends AccountImplBase {
	AccountImplBase obj;
	Connection connection;
	
	public AccountImplSkeleton(Connection connection, AccountImplBase obj) {
		this.connection = connection;
		this.obj = obj;
	}
	
	@Override
	public void transfer(double amount) throws OverdraftException {
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

	@Override
	public double getBalance() {
		Message msg = new Message();
		msg.setReason(Message.MessageReason.METHOD_RETURN);
		msg.setMethod_return(obj.getBalance());
		
		try {
			connection.send(msg);
		} catch (IOException e) {
			System.out.println("AccountImpleSkeleton.getBalance(): ERROR!");
			e.printStackTrace();
		}
		
		return obj.getBalance();
	}

}
