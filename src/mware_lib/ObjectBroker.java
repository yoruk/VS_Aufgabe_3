package mware_lib;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bank_access.AccountImplBase;
import bank_access.AccountImplSkeleton;
import bank_access.OverdraftException;

public class ObjectBroker {
	static class ReqHandler implements Runnable {
		static class ClientReq implements Runnable {
/******************************* ClientReq starts here *******************************/
			private Socket socket;
			private Map<String, Object> object_cloud;
			
			public ClientReq(Socket socket, Map<String, Object> object_cloud) {
				this.socket = socket;
				this.object_cloud = object_cloud;
			}
			
			@Override
			public void run() {
				Connection connection = null;
				Message msg = null;
				Object obj = null;
				
				try {
					connection = new Connection(socket);
					msg = connection.receive();
					
					// is it really a method call?
					if(msg.getReason() == Message.MessageReason.METHOD_CALL) {
						// for which object?
						obj = object_cloud.get(msg.getObjName());
						
						if(obj != null) {
							
							// object is a AccountImplBase
							if(obj instanceof AccountImplBase) {
								AccountImplSkeleton skeleton = new AccountImplSkeleton(connection, (AccountImplBase)obj);
								
								switch(msg.getMethod_name()) {
									case "transfer":
										Object[] params = msg.getMethod_params();
										try {
											skeleton.transfer((double)params[0]);
										} catch (OverdraftException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										break;
									case "getbalance":
										skeleton.getBalance();
										break;
									default:
										msg = new Message();
										msg.setReason(Message.MessageReason.ERROR);
										connection.send(msg);
								}
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
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
/******************************* ReqHandler starts here *******************************/
		private int port;
		private ServerSocket serverSocket;
		private Map<String, Object> object_cloud;
		
		public ReqHandler(int port, Map<String, Object> object_cloud) {
			this.port = port;
			this.object_cloud = object_cloud;
		}
		
		@Override
		public void run() {
			try {
				ExecutorService thread_pool = Executors.newFixedThreadPool(10);
				serverSocket = new ServerSocket(port);
				Socket clientSocket = null;
				
				while(!Thread.interrupted()) {
					clientSocket = serverSocket.accept();
					thread_pool.execute(new ClientReq(clientSocket, object_cloud));
				}
			} catch (IOException e) {
				System.out.println("ObjectBroker.Reqhandler.run(): ERROR!");
				e.printStackTrace();
			}
			
		}
	}
	
/******************************* ObjectBroker starts here *******************************/
	
	private static Map<String, Object> object_cloud = new HashMap<String, Object>();
    private static NameService nameService;
    private static String nameServiceAddress;
    private static int nameServicePort;
    private static ServerSocket serverSocket;
    private static String serverAddress;
    private static final int serverPort = 6666;
    private static Thread reqHandler;
    private static boolean debugFlag;

    public static ObjectBroker init(String serviceHost, int listenPort, boolean debug) {
    	if(debug) {
    		System.out.println("ObjectBroker.init(): NameService @ " + serviceHost + ":" + listenPort);
    	}
    	
    	nameServiceAddress = serviceHost;
    	nameServicePort = listenPort;
    	debugFlag = debug;
    	
    	try {
			serverSocket =  new ServerSocket(serverPort);
			serverAddress = serverSocket.getLocalSocketAddress().toString();
		} catch (IOException e) {
	    	System.out.println("ObjectBroker.init(): ERROR, during opening serverSocket!");
			e.printStackTrace();
		}
    	
    	reqHandler = new Thread(new ObjectBroker.ReqHandler(serverPort, object_cloud));
    	reqHandler.start();
    	
    	nameService = new NameServiceImpl(nameServiceAddress, nameServicePort, serverAddress,
    										serverPort, object_cloud, debugFlag);
    	
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

    public void shutdown() {
    	nameService = null;
    	
    	// TODO hier alle threads beenden und socket schliessen
        
    	/* Beendet die Benutzung der Middleware in dieser Anwendung. */
        // getInstance() von laufenden Threads oder Sockets
    }

}
