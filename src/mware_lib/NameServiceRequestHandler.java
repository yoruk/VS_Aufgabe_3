package mware_lib;

import java.io.IOException;
import java.util.Map;
import util.Connection;

public class NameServiceRequestHandler implements Runnable {
	private Connection connection;
	private Map<String, ObjectRef> index;
	private ObjectRef tmp_objRef;
	private String tmp_objId;
	private final String REBIND = "rebind";
	private final String RESOLVE = "resolve";
	private final String OK = "ok";
	private final String ERROR = "error";
	
	public NameServiceRequestHandler(Connection connection, Map<String, ObjectRef> index) {
		System.out.println("### NameServiceRequestHandler()\n");
	    this.connection = connection;
		this.index = index;
	}
	
	@Override
	public void run() {
		System.out.println("### NameServiceRequestHandler: run()\n");
	    String method_name;
		
		while(true) {
			try {
				method_name = (String)connection.receive();
				
				
				switch(method_name) {
					case REBIND:
						connection.send(OK);
						tmp_objRef = (ObjectRef)connection.receive();
						connection.send(OK);
						index.put(tmp_objRef.getObjId(), tmp_objRef);
						tmp_objRef = null;
						break;
						
						
					case RESOLVE:
						connection.send(OK);
						tmp_objId = (String)connection.receive();
						if(tmp_objId == null) {
							//connection.send(ERROR);
							connection.send(new ObjectRef(ERROR, 0, ERROR));
						} else {
							connection.send(index.get(tmp_objId));
						}
						tmp_objRef = null;
						break;
						
					default:
						connection.send(ERROR);
						System.out.println("### NameServerRequestHandler: Error, unknown method received!\n");
				}
				
			} catch (IOException e) {
				System.out.println("NameServerImpl:Requesthandler:run(), IOException!!!\n");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
