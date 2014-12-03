package mware_lib;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import util.Connection;

public class NameServiceRequestHandler implements Runnable {
	private Connection connection;
	private NameServiceIndex index;
	
	public NameServiceRequestHandler(Connection connection, NameServiceIndex index) {
		this.connection = connection;
		this.index = index;
	}
	
	@Override
	public void run() {
		String method_name;
		
		while(true) {
			try {
				method_name = connection.receive();
				
				switch(method_name) {
					case "rebind":
						break;
						
						
					case "resolve":
						break;
						
					default:
						System.out.println("NameServerImpl:Requesthandler:run(), unknown method received!\n");
				}
				
			} catch (IOException e) {
				System.out.println("NameServerImpl:Requesthandler:run(), IOException!!!\n");
			}
			
		}
	}
}
