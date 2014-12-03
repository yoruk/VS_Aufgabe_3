package mware_lib;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.Connection;

public class NameServiceDaemon {
	public static void main(String[] args) {
		ServerSocket svr_socket;
		NameServiceIndex index = new NameServiceIndex();
		ExecutorService thread_pool = Executors.newCachedThreadPool();
		int port = 6666;
		
		System.out.println("### NameServiceDeamon(), is running ###\n");
		
		// Uebergebenen Port pruefen
	    if(args.length > 0) {
	    	try {
	    		port = Integer.parseInt(args[0]);
	    	} catch (NumberFormatException e) {
	    		System.out.println("Das uebergebene Argument ist keine gueltige Port-Nummer!\n"
	            + "Verwende den Standard-Port " + port + "!\n");
	    	}
	    }

	    // Lokale Host-Adresse des NameService-Servers ermitteln
	    try {
	    	InetAddress localhost = InetAddress.getLocalHost();
	    	System.out.println("Starte NameService...\n" + localhost.getHostAddress()
	                       		+ ":" + port + " (" + localhost.getHostName() + ")\n");
		
	    	svr_socket = new ServerSocket(port);
		
	    	while(true) {
	    		final Connection tmp_connection = new Connection(svr_socket.accept());
	    		thread_pool.execute(new NameServiceRequestHandler(tmp_connection, index));
	    	}
	    } catch (UnknownHostException e) {
	    	System.err.println("Lokale Host-Adresse konnte nicht ermittelt werden!");
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	System.err.println("Socket auf Port " + port + " konnte nicht erstellt werden!");
	    	e.printStackTrace();
	    }
	}
}	
