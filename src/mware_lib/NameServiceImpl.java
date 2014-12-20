/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package mware_lib;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class NameServiceImpl extends NameService {
	private String nameServiceAddress;
	private int nameServicePort;
	private String serverAddress;
	private int serverPort;
	private boolean debug = false;
	private Map<String, Object> object_cloud;
	
	public NameServiceImpl(String nameServiceAddress, int nameServicePort, String serverAddress,
							int serverPort, Map<String, Object> object_cloud, boolean debug) {
		if(debug) {
			System.out.println("NameServiceImpl(): NameService @ " + nameServiceAddress + ":" + nameServicePort);
		}
		
		this.nameServiceAddress = nameServiceAddress;
		this.nameServicePort = nameServicePort;
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.object_cloud = object_cloud;
		this.debug = debug;
	}
	
	public NameServiceImpl(String nameServiceAddress, int nameServicePort, boolean debug) {
		if(debug) {
			System.out.println("NameServiceImpl(): NameService @ " + nameServiceAddress + ":" + nameServicePort);
		}

		this.nameServiceAddress = nameServiceAddress;
		this.nameServicePort = nameServicePort;
		this.debug = debug;
	}
	
	@Override
	public void rebind(Object servant, String name) {
		if(debug) {
			System.out.println("NameServiceImpl.rebind(): obj: " + servant + " name: " + name);
		}
		
		Socket socket = null;
		Connection connection = null;
		ObjectRef tmp_ObjRef = null;
		Message tmp_msg = null;
		
		try {
			socket = new Socket(nameServiceAddress, nameServicePort);
			connection = new Connection(socket);
			
			object_cloud.put(name, servant);			
			tmp_ObjRef = new ObjectRef(serverAddress, serverPort, name);
			tmp_ObjRef.setNameServiceAddr(nameServiceAddress);
			tmp_ObjRef.setNameServicePort(nameServicePort);
			tmp_msg = new Message();
			tmp_msg.setReason(Message.MessageReason.REBIND);
			tmp_msg.setPayload(tmp_ObjRef);
			connection.send(tmp_msg);

			// really ugly!!!
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			if(debug) {
				System.out.println("NameServiceImpl.rebind(): ERROR!");
				e.printStackTrace();				
			}
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				if(debug) {
					System.out.println("NameServiceImpl.rebind(): ERROR!");
					e.printStackTrace();					
				}
			}
		}
	}

	@Override
	public Object resolve(String name) {
		if(debug) {
			System.out.println("NameServiceImpl.resolve(): objectname: " + name);
		}
		
		Message tmp_msg = null;
		Socket socket = null;
		Connection connection = null;
		
		try {
			socket = new Socket(nameServiceAddress, nameServicePort);
			connection = new Connection(socket);
		
			tmp_msg = new Message();
			tmp_msg.setPayload(name);
			tmp_msg.setReason(Message.MessageReason.RESOLVE);
			if(debug) System.out.println("### Sending reolve message: " + tmp_msg);
			connection.send(tmp_msg);

			tmp_msg = connection.receive();
			if(debug) System.out.println("### Sending receiving message: " + tmp_msg);
			
		} catch (IOException e) {
			if(debug) {
				System.out.println("NameServiceImpl.resolve(): ERROR!");
				e.printStackTrace();				
			}
		} catch (ClassNotFoundException e) {
			if(debug) {
				System.out.println("NameServiceImpl.resolve(): ERROR!");
				e.printStackTrace();				
			}
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				if(debug) {
					System.out.println("NameServiceImpl.resolve(): ERROR!");
					e.printStackTrace();					
				}
			}
		}
		
		return tmp_msg.getPayload();
	}

}
