package util;

import java.net.Socket;

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
			tmp_msg.setCommand("Hello");
			tmp_msg.setRet_val(new String("World!"));
			
			connection.send(tmp_msg);
			
		} catch (Exception e) {
			System.out.println("Main(), Error!!!\n" + e);
		} finally {
			try {
				socket.close();
			} catch(Exception e) {
				System.out.println("Main(), Error!!!\n" + e);
			}
		}
	}
}

