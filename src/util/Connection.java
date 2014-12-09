package util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;
import mware_lib.Message;

public class Connection {	
    private class IncomingHandler implements Runnable {
    	private Socket socket = null;
    	private SynchronousQueue<Message> incoming = null;
    	
    	public IncomingHandler(Socket socket, SynchronousQueue<Message> incoming) {
    		this.socket = socket;
    		this.incoming = incoming;
    	}
    	
		@Override
		public void run() {
			ObjectInputStream in;
			try {
				in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				
				while(Thread.interrupted()) {
					incoming.add((Message)in.readObject());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    }
	
	private Socket socket;
    private ObjectOutputStream out;
    private SynchronousQueue<Message> incoming = new SynchronousQueue<Message>();
    private Thread handler;
    
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());

        handler = new Thread(new IncomingHandler(socket, incoming));
        handler.start();
    }

    public Message receive() throws IOException, ClassNotFoundException {
		try {
			return incoming.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
    }

    public void send(Message msg) throws IOException {
    	System.out.println(msg);
    	out.writeObject((Object)msg);
    }

    public void close() throws IOException {
    	handler.interrupt();
        socket.close();
    }
}
