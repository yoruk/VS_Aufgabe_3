package util;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.OutputStream;
import java.net.Socket;

public class Connection {
//	private BufferedReader in;
//	private OutputStream out;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
//	public Connection(Socket mySock) throws IOException {
//		in = new BufferedReader(new InputStreamReader(mySock.getInputStream()));
//		out = mySock.getOutputStream();		
//	}
	public Connection(Socket mySock) throws IOException {
		in = new ObjectInputStream(mySock.getInputStream());
		out = new ObjectOutputStream(mySock.getOutputStream());		
	}
	
//	public String receive() throws IOException {
//		return in.readLine();
//	}
	public Object receive() throws IOException, ClassNotFoundException {
			return in.readObject();
	}
	
//	public void send(String message) throws IOException {
//		out.write((message + "\n").getBytes());
//	}
	public void send(Object obj) throws IOException {
		out.writeObject(obj);
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
	}
}
