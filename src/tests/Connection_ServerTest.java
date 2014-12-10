package tests;

import java.util.List;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mware_lib.Message;
import mware_lib.Connection;

public class Connection_ServerTest {
	public static void main(String[] args) {
		class ReqHandler implements Runnable {
			private Connection connection = null;
			private List<Message>  msg_container = new ArrayList<Message>();
			
			public ReqHandler(Socket socket) {
				System.out.println("Connection_ServerTest.RegHandler()\n");
				
				try {
					connection = new Connection(socket);
				} catch(Exception e) {
					System.out.println("Connection_ServerTest.RegHandler(), Error!!!\n");
					e.printStackTrace();
				}
			}
			
			@Override
			public void run() {
				try {
					System.out.println("Connection_ServerTest.RegHandler.run()\n");
					
					Message tmp_msg = connection.receive();
					System.out.println("Connection_ServerTest, Message received: " + tmp_msg + "\n");
					msg_container.add(tmp_msg);
					
					while(true);
					
				} catch(Exception e) {
					System.out.println("RegHandler.run(), Error!!!\n");
					e.printStackTrace();
				} finally {
					try {
						connection.close();
					} catch(Exception e) {
						System.out.println("RegHandler.run(), Error!!!\n");
						e.printStackTrace();
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
			System.out.println("Connection_ServerTest.Main(), Error!!!\n");
			e.printStackTrace();
		} finally {
			try {
				server_socket.close();
			} catch(Exception e) {
				System.out.println("Connection_ServerTest.Main(), Error!!!\n");
				e.printStackTrace();
			}
		}
		
	}
}
