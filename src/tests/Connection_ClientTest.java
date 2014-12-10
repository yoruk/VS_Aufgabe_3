package tests;

import java.net.Socket;

import mware_lib.Connection;
import mware_lib.Message;

public class Connection_ClientTest {
	public static void main(String[] args) {		
		Connection connection = null;
		Socket socket = null;
	
		System.out.println("Connection_ClientTest\n");

		try {
			socket =  new Socket("localhost", 6666);
			connection = new Connection(socket);
			
			Message tmp_msg = new Message();
			tmp_msg.setReason(Message.MessageReason.REBIND);
			tmp_msg.setMethod_name("test");
			String[] hw = {"Hello", "world!"};
			tmp_msg.setMethod_params(hw);
			
			connection.send(tmp_msg);
			//connection.send(tmp_msg);
			
		} catch (Exception e) {
			System.out.println("Connection_ClientTest, Error!!!\n");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch(Exception e) {
				System.out.println("Connection_ClientTest, Error!!!\n");
				e.printStackTrace();
			}
		}
	}
}

