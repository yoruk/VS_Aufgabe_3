package mware_lib;

import java.io.BufferedInputStream;
//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.OutputStream;
import java.net.Socket;
//import java.util.Queue;
import java.util.concurrent.SynchronousQueue;
import mware_lib.Message;

public class Connection {	
    private class IncomingHandler implements Runnable {
    	private Socket socket = null;
    	private SynchronousQueue<Message> incoming_queue = null;
    	
    	public IncomingHandler(Socket socket, SynchronousQueue<Message> incoming_queue) {
    		this.socket = socket;
    		this.incoming_queue = incoming_queue;
    	}
    	
		@Override
		public void run() {
			ObjectInputStream in;
			
			//System.out.println("run\n");
			
			try {
				in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				//in = new ObjectInputStream(socket.getInputStream());
				
				while(!Thread.interrupted()) {
					//System.out.println("test1\n");
					//in.readObject();
					incoming_queue.add((Message)in.readObject());
					//System.out.println("test2\n");
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
    private SynchronousQueue<Message> incoming_queue = new SynchronousQueue<Message>();
    private Thread thread_handler;
    
    public Connection(Socket socket) throws IOException {
    	System.out.println("Connection.Connection()\n");
    	
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());

        thread_handler = new Thread(new IncomingHandler(socket, incoming_queue));
        thread_handler.start();
    }

    public Message receive() throws IOException, ClassNotFoundException {
		try {
			Message tmp_msg = incoming_queue.take();
			
			System.out.println("Connection.receive(): Message received: " + tmp_msg + "\n");
			
			return tmp_msg;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
    }

    public void send(Message msg) throws IOException {
    	System.out.println("Connection.send(): sending Message: " + msg + "\n");
    	out.writeObject((Object)msg);
    }

    public void close() throws IOException {
    	thread_handler.interrupt();
        socket.close();
    }
}
