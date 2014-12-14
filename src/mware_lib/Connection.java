package mware_lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import mware_lib.Message;

public class Connection {	
    private class IncomingHandler implements Runnable {
    	private Socket socket = null;
    	private BlockingQueue<Message> incoming_queue = null;
    	
    	public IncomingHandler(Socket socket, BlockingQueue<Message> incoming_queue) {
    		this.socket = socket;
    		this.incoming_queue = incoming_queue;
    	}
    	
		@Override
		public void run() {
			ObjectInputStream in;
			
			try {
				//in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				in = new ObjectInputStream(socket.getInputStream());
				
				while(!Thread.interrupted()) {
					//System.out.println((Message)in.readObject());
					incoming_queue.add((Message)in.readObject());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	
	private Socket socket;
    private ObjectOutputStream out;
    private BlockingQueue<Message> incoming_queue = new ArrayBlockingQueue<Message>(1);
    private Thread thread_handler;
    
    public Connection(Socket socket) throws IOException {
    	//System.out.println("Connection.Connection()");
    	
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());

        thread_handler = new Thread(new IncomingHandler(socket, incoming_queue));
        thread_handler.start();
    }

    public Message receive() throws IOException, ClassNotFoundException {
		try {
			Message tmp_msg = incoming_queue.take();
			
			//System.out.println("Connection.receive(): Message received:\n   " + tmp_msg);
			
			return tmp_msg;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
    }

    public void send(Message msg) throws IOException {
    	//System.out.println("Connection.send(): sending Message:\n   " + msg);
    	out.writeObject((Object)msg);
    	out.flush();
    }

    public void close() throws IOException {
    	thread_handler.interrupt();
        socket.close();
    }
}
