/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package tests;

import java.util.List;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mware_lib.Message;
import mware_lib.Connection;

public class ConnectionServerTest {
	public static void main(String[] args) {
		class ReqHandler implements Runnable {
			private Connection connection = null;
			private List<Message>  msg_container = new ArrayList<Message>();
			
			public ReqHandler(Socket socket) {
				System.out.println("ConnectionServerTest.RegHandler()");
				
				try {
					connection = new Connection(socket);
				} catch(Exception e) {
					System.out.println("ConnectionServerTest.RegHandler(): Error!!!");
					e.printStackTrace();
				}
			}
			
			@Override
			public void run() {
				try {
					System.out.println("ConnectionServerTest.RegHandler.run()");
					
					while(!Thread.interrupted()) {
						Message tmp_msg = connection.receive();
						System.out.println("ConnectionServerTest, Message received: " + tmp_msg);
						msg_container.add(tmp_msg);
					}
					
				} catch(Exception e) {
					System.out.println("ConnectionServerTest.RegHandler.run(): Error!!!");
					e.printStackTrace();
				} finally {
					try {
						connection.close();
					} catch(Exception e) {
						System.out.println("ConnectionServerTest.RegHandler.run(): Error!!!");
						e.printStackTrace();
					}
				}
			}
		}
		
		System.out.println("ConnectionServerTest");	
		
		ServerSocket server_socket = null;
	
		try {
			server_socket =  new ServerSocket(6666);
			ExecutorService thread_pool = Executors.newFixedThreadPool(4);
			Socket client_socket = server_socket.accept();
			thread_pool.execute(new ReqHandler(client_socket));
		} catch (Exception e) {
			System.out.println("ConnectionServerTest.Main(): Error!!!");
			e.printStackTrace();
		} finally {
			try {
				server_socket.close();
			} catch(Exception e) {
				System.out.println("ConnectionServerTest.Main(): Error!!!");
				e.printStackTrace();
			}
		}
		
	}
}
