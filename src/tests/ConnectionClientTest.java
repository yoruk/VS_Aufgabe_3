package tests;

import java.net.Socket;

import mware_lib.Connection;
import mware_lib.Message;

public class ConnectionClientTest {
	public static void main(String[] args) {		
		Connection connection = null;
		Socket socket = null;
	
		System.out.println("ConnectionClientTest");

		try {
			socket =  new Socket("localhost", 6666);
			connection = new Connection(socket);
			
			Message tmp_msg = new Message();
			tmp_msg.setReason(Message.MessageReason.REBIND);
			tmp_msg.setMethod_name("test");
			String[] hw = {"Hello", "world!"};
			tmp_msg.setMethod_params(hw);
			
			connection.send(tmp_msg);
			connection.send(tmp_msg);
			
			while(true);
			
		} catch (Exception e) {
			System.out.println("ConnectionClientTest: Error!!!");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch(Exception e) {
				System.out.println("ConnectionClientTest: Error!!!");
				e.printStackTrace();
			}
		}
	}
}

