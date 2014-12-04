package mware_lib;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.Connection;

public class NameServiceDaemon {
	public static void main(String[] args) {
		Map<String, ObjectRef> index = new HashMap<String, ObjectRef>();
		ServerSocket svr_socket;
		InetAddress localhost;
		int port = 6666;
		ExecutorService thread_pool = Executors.newCachedThreadPool();
		
		System.out.println("### NameServiceDeamon: main()");
		
		// check given port
	    if(args.length > 0) {
	    	try {
	    		port = Integer.parseInt(args[0]);
	    	} catch (NumberFormatException e) {
	    		System.out.println("### NameServiceDeamon: given port isn't valid! using default: \n"
	            + port + "!\n");
	    	}
	    }

	    // get own host address
	    try {
	    	localhost = InetAddress.getLocalHost();
	    	System.out.println("### NameServiceDeamon: starting on: " + localhost.getHostAddress()
	                       		+ ":" + port + " (" + localhost.getHostName() + ")\n");
		
	    	svr_socket = new ServerSocket(port);
		
	    	while(true) {
	    		final Connection tmp_connection = new Connection(svr_socket.accept());
	    		thread_pool.execute(new NameServiceRequestHandler(tmp_connection, index));
	    	}
	    } catch (UnknownHostException e) {
	    	System.err.println("### NameServiceDeamon: Error, local host address hasnt been found!\n");
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	System.err.println("### NameServiceDeamon: Error, couldn't open socket on port " + port + "\n");
	    	e.printStackTrace();
	    }
	}
}	
