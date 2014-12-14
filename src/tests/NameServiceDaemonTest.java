package tests;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import mware_lib.Connection;
import mware_lib.Message;
import mware_lib.ObjectRef;

public class NameServiceDaemonTest {
	public static void main(String[] args) {
		System.out.println("NameServiceDaemonTest");
		
		Socket socket = null;
		Connection connection;
		Message tmp_msg;
		ObjectRef tmp_ObjectRef;
		
		try {
			socket = new Socket("localhost", 6666);
			connection = new Connection(socket);
			tmp_msg = new Message();
			System.out.println("### Sending faulty message: " + tmp_msg);
			connection.send(tmp_msg);
			socket.close();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			socket = new Socket("localhost", 6666);
			connection = new Connection(socket);
			tmp_msg = new Message();
			tmp_ObjectRef = new ObjectRef("schnitzel", 1234, "xxx");
			tmp_msg.setPayload(tmp_ObjectRef);
			tmp_msg.setReason(Message.MessageReason.REBIND);
			System.out.println("### Sending rebind message: " + tmp_msg);
			connection.send(tmp_msg);
			socket.close();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			socket = new Socket("localhost", 6666);
			connection = new Connection(socket);
			tmp_msg = new Message();
			tmp_msg.setPayload("xxx");
			tmp_msg.setReason(Message.MessageReason.RESOLVE);
			System.out.println("### Sending reolve message: " + tmp_msg);
			connection.send(tmp_msg);

			tmp_msg = connection.receive();
			System.out.println("### Sending receiving message: " + tmp_msg);
			socket.close();
			
		} catch (UnknownHostException e) {
			System.out.println("NameServiceDaemonTest: ERROR!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("NameServiceDaemonTest: ERROR!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("NameServiceDaemonTest: ERROR!");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println("NameServiceDaemonTest: ERROR!");
				e.printStackTrace();
			}
		}
		
	}
}
