package mware_lib;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NameServiceDaemon {
	public static void main(String[] args) {
		class ReqHandler implements Runnable {
			private Connection connection = null;
			private Map<String, ObjectRef> object_index = new HashMap<String, ObjectRef>();
			
			public ReqHandler(Socket client_socket, Map<String, ObjectRef> object_index) {
				System.out.println("NameServiceDaemon.ReqHandler: answering incoming connection");
				
				this.object_index = object_index;
				
				try {
					connection = new Connection(client_socket);
				} catch(Exception e) {
					System.out.println("NameServiceDaemon.ReqHandler: ERROR!");
					e.printStackTrace();
				}
			}
			
			@Override
			public void run() {
				System.out.println("NameServiceDaemon.ReqHandler.run()");
				
				Message.MessageReason REBIND = Message.MessageReason.REBIND;
				Message.MessageReason RESOLVE = Message.MessageReason.RESOLVE;
				Message tmp_msg;
				ObjectRef tmp_ObjectRef;
				
				try {
					tmp_msg = (Message)connection.receive();
					System.out.println("NameServiceDaemon.ReqHandler.run(): message received:\n   " + tmp_msg);
					
					switch(tmp_msg.getReason()) {
						case REBIND:
							System.out.println("NameServiceDaemon.ReqHandler.run(): REBIND");
							tmp_ObjectRef = (ObjectRef)tmp_msg.getPayload();
							object_index.put(tmp_ObjectRef.getObjId(), tmp_ObjectRef);
							break;
							
						case RESOLVE:
							System.out.println("NameServiceDaemon.ReqHandler.run(): RESOLVE");
							tmp_ObjectRef = object_index.get(tmp_msg.getPayload());
							tmp_msg.setReason(Message.MessageReason.RESOLVE_REPLY);
							tmp_msg.setPayload((Object)tmp_ObjectRef);
							connection.send(tmp_msg);
							break;
							
						default:
							System.out.println("NameServiceDaemon.ReqHandler.run(): ERROR, unknown message type received!");
					}
					
				} catch (ClassNotFoundException e) {
					System.out.println("NameServiceDaemon.ReqHandler.run(): ERROR!");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("NameServiceDaemon.ReqHandler.run(): ERROR!");
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("NameServiceDaemon.main()");
		
		Map<String, ObjectRef> object_index = new HashMap<String, ObjectRef>();
		ExecutorService thread_pool = Executors.newFixedThreadPool(10);
		ServerSocket server_socket = null;
		int port = 6666;
		
		if(args.length != 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.out.println("NameServiceDaemon.main(): ERROR, the given port isn't from type int!");
				e.printStackTrace();
			}
		}
		
		try {
			server_socket =  new ServerSocket(port);
			
			while(true) {
				Socket client_socket = server_socket.accept();
				thread_pool.execute(new ReqHandler(client_socket, object_index));
			}
			
		} catch (IOException e) {
			System.out.println("NameServiceDaemon.main(): ERROR!");
			e.printStackTrace();
		} finally {
			try {
				System.out.println("NameServiceDaemon.main(): closing server socket");
				server_socket.close();
			} catch (IOException e) {
				System.out.println("NameServiceDaemon.main(): ERROR!");
				e.printStackTrace();
			}
		}	
	}
}
