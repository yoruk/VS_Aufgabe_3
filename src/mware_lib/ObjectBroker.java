/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package mware_lib;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bank_access.AccountImplBase;
import bank_access.AccountImplSkeleton;
import bank_access.ManagerImplBase;
import bank_access.ManagerImplSkeleton;

public class ObjectBroker {
	static class ReqHandler implements Runnable {
		static class ClientReq implements Runnable {
/******************************* ClientReq starts here *******************************/
			private Socket socket;
			private Map<String, Object> object_cloud;
			private NameService nameService;
			private boolean debug;
			
			public ClientReq(Socket socket, Map<String, Object> object_cloud, NameService nameService, boolean debug) {
				this.socket = socket;
				this.object_cloud = object_cloud;
				this.nameService = nameService;
				this.debug = debug;
			}
			
			@Override
			public void run() {
				if(debug) {
		    		System.out.println("ObjectBroker.ReqHandler.ClientReq.run(): started");
		    	}
				
				Connection connection = null;
				Message msg = null;
				Object obj = null;
				
				try {					
					// opening connection and receiving a message
					connection = new Connection(socket);
					msg = connection.receive();
					
					// is it really a method call?
					if(msg.getReason() == Message.MessageReason.METHOD_CALL) {
						
						if(debug) {
							System.out.println("### object_cloud:");
							for (Map.Entry<String, Object> entry : object_cloud.entrySet()) {
								System.out.println(entry.getKey()+" : "+entry.getValue());
							}
						}
						
						// for which object?
						obj = object_cloud.get(msg.getObjName());
						
						// is the object in the cloud?
						if(obj != null) {
							
							// object is of what type ?
							if(obj instanceof AccountImplBase) {
								
								// object is a AccountImplBase
								new AccountImplSkeleton(connection, msg, (AccountImplBase)obj, debug);
							
							} else if(obj instanceof ManagerImplBase) {
						
								// object is a ManagerImplBase
								new ManagerImplSkeleton(connection, msg, (ManagerImplBase)obj, object_cloud, nameService, debug);
							
							}
											
						// object is not in the cloud
						} else {
							msg = new Message();
							msg.setReason(Message.MessageReason.ERROR);
							connection.send(msg);
						}
					
					// no method call
					} else {
						msg = new Message();
						msg.setReason(Message.MessageReason.ERROR);
						connection.send(msg);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					if(debug) e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					if(debug) e.printStackTrace();
				} finally {
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						if(debug) e.printStackTrace();
					}
				}
			}
		}
/******************************* ReqHandler starts here *******************************/
		private ServerSocket serverSocket;
		private Map<String, Object> object_cloud;
		private NameService nameService;
		private boolean debug = false;
		
		public ReqHandler(ServerSocket serverSocket, Map<String, Object> object_cloud, NameService nameService, boolean debug) {
			this.serverSocket = serverSocket;
			this.object_cloud = object_cloud;
			this.nameService = nameService;
			this.debug = debug;
		}
		
		@Override
		public void run() {
			try {
				if(debug) {
		    		System.out.println("ObjectBroker.ReqHandler.run(): started");
		    	}
				
				ExecutorService thread_pool = Executors.newFixedThreadPool(10);
				Socket clientSocket = null;
				
				while(!Thread.interrupted()) {
					clientSocket = serverSocket.accept();
					thread_pool.execute(new ClientReq(clientSocket, object_cloud, nameService, debug));
				}
				
				thread_pool.shutdown();
				
			} catch (IOException e) {
				//System.out.println("ObjectBroker.Reqhandler.run(): ERROR!");
				//e.printStackTrace();
			}
			
		}
	}
	
/******************************* ObjectBroker starts here *******************************/
	
	private static Map<String, Object> object_cloud = new HashMap<String, Object>();
    private static NameService nameService;
    private static String nameServiceAddress;
    private static int nameServicePort;
    private static String serverAddress;
    private static int serverPort;
    private static ServerSocket serverSocket;
    private static Thread reqHandler;
    private static boolean debugFlag = false;

    public static ObjectBroker init(String serviceHost, int listenPort, boolean debug) {
    	if(debug) {
    		System.out.println("ObjectBroker.init(): NameService @ " + serviceHost + ":" + listenPort);
    	}
    	
    	nameServiceAddress = serviceHost;
    	nameServicePort = listenPort;
    	serverPort = listenPort + 1;
    	debugFlag = debug;
    	
    	// find an unused port for the incoming connections
    	boolean init_port_done = false;
    	while(init_port_done == false) {
    		try {
    			serverSocket =  new ServerSocket(serverPort);
    			init_port_done = true;
    		} catch (IOException e) {
    	    	//System.out.println("ObjectBroker.init(): ERROR, during opening serverSocket!");
    			//e.printStackTrace();
    			serverPort++;
    		}
    	}
    	
    	//serverAddress = serverSocket.getLocalSocketAddress().toString();
    	
    	try {
			serverAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			if(debug) e.printStackTrace();
		}
    	
    	System.out.println("ObjectBroker.init(): Reqhandler @ " + serverAddress + ":" + serverPort);
    	
    	nameService = new NameServiceImpl(nameServiceAddress, nameServicePort, serverAddress,
    			serverPort, object_cloud, debugFlag);

    	reqHandler = new Thread(new ObjectBroker.ReqHandler(serverSocket, object_cloud, nameService, debugFlag));
    	reqHandler.start();
    	
        /*
         * Das hier zurueckgelieferte Objekt soll der zentrale Einstiegspunkt
         * der Middleware aus der Anwendersicht sein. Parameter: Host und Port,
         * bei dem die Dienste (Namensdienst) kontaktiert werden sollen. Mit
         * debug sollen Testausgaben der Middleware ein- und ausgeschaltet
         * werden koennen.
         */
        return new ObjectBroker();
    }

    public NameService getNameService() {
        return nameService;
        /* Liefert den Namensdienst (Stellvertreterobjekt). */
    }

    public void shutDown() {
    	nameService = null;
    	try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    	reqHandler.interrupt();
    	
    	// TODO hier alle threads beenden und socket schliessen
        
    	/* Beendet die Benutzung der Middleware in dieser Anwendung. */
        // getInstance() von laufenden Threads oder Sockets
    }

}
