package util;

import java.util.List;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mware_lib.Message;
import util.Connection;

public class Connection_ServerTest {
	public static void main(String[] args) {
		class ReqHandler implements Runnable {
			private Connection connection = null;
			private List<Message>  msg_container = new ArrayList<Message>();
			
			public ReqHandler(Socket socket) {
				try {
					connection = new Connection(socket);
				} catch(Exception e) {
					System.out.println("RegHandler(), Error!!!\n" + e);
				}
			}
			
			@Override
			public void run() {
				try {
					System.out.println("RegHandler.run()\n");
					
					Message tmp_msg = connection.receive();
					System.out.println("Message received: " + tmp_msg + "\n");
					msg_container.add(tmp_msg);
					
				} catch(Exception e) {
					System.out.println("RegHandler.run(), Error!!!\n" + e);
				} finally {
					try {
						connection.close();
					} catch(Exception e) {
						System.out.println("RegHandler.run(), Error!!!\n" + e);
					}
				}
			}
		}
		
		System.out.println("Connection_ServerTest\n");	
		
		ServerSocket server_socket = null;
	
		try {
			server_socket =  new ServerSocket(6666);
			ExecutorService thread_pool = Executors.newFixedThreadPool(4);
			Socket client_socket = server_socket.accept();
			thread_pool.execute(new ReqHandler(client_socket));
		} catch (Exception e) {
			System.out.println("Main(), Error!!!\n" + e);
		} finally {
			try {
				server_socket.close();
			} catch(Exception e) {
				System.out.println("Main(), Error!!!\n" + e);
			}
		}
		
	}
}
