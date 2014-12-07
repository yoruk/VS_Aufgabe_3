package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket MySocket;
//	private BufferedReader In;
//	private OutputStream Out;
	private Connection connection;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Client(String host, int port) throws UnknownHostException, IOException {
		MySocket = new Socket(host, port);
		
		//in = new ObjectInputStream(new InputStreamReader(MySocket.getInputStream()));
		//out = MySocket.getOutputStream();
		connection = new Connection(MySocket);
	}
	
	public Object receive() throws IOException, ClassNotFoundException {
	    return connection.receive();
	}
	
	public void send(Object message) throws IOException {
		connection.send(message);
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
		MySocket.close();
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		// Verbindung aufbauen
		Client myClient = new Client("192.168.100.101", 14001);
		
		System.out.println("### Client is connected to server\n");
		
		// Kommunikation
		myClient.send((Object)"Knock, knock!");
		try {
            System.out.println((String)myClient.receive());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		// Verbindung schliessen
		myClient.close();
	}

}
