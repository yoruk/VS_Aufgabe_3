package util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import mware_lib.Message;

public class Connection_eugen {
    private Socket socket;
    private ObjectInputStream objectInputStream;

    /**
     * Connection constructor using a socket for communication
     * 
     * @param socket
     */
    public Connection_eugen(Socket socket) {
        this.socket = socket;
    }

    /**
     * Receive a message object via the connection socket
     * 
     * @return Message object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Message receive() throws IOException, ClassNotFoundException {
        this.objectInputStream = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
        Message msg = (Message) objectInputStream.readObject();
        System.out.println("[CONNECTION] | receive(): Received message " + msg + " from " + this.socket.getInetAddress() + ":" + this.socket.getPort());
        return msg;
    }

    /**
     * Send a meesage object via the connection socket
     * 
     * @param msg
     * @throws IOException
     */
    public void send(Message msg) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(msg);
        System.out.println("[CONNECTION] | send(): Sending message " + msg + " to " + this.socket.getInetAddress() + ":" + this.socket.getPort());
        objectOutputStream.flush();	    
    }

    /**
     * Close the socket that was used for the connection
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        this.socket.close();
    }
}
