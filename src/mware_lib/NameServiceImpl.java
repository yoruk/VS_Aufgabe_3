package mware_lib;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

import util.Connection;

public class NameServiceImpl extends NameService {
    private Map<String, Object> index;
    private Socket clientSocket;
    private final int SERVER_PORT = 5555;
    private final String REBIND = "rebind";
    private final String RESOLVE = "resolve";
    private final String OK = "ok";
    private final String ERROR = "error";

    public NameServiceImpl(String serviceHost, int listenPort, Map<String, Object> index) throws IOException {
        this.index = index;
        clientSocket = new Socket(serviceHost, listenPort);
    }

    @Override
    public void rebind(Object servant, String name) {
        Connection connection = null;
        try {
            // send rebind to nameserver and register object
            connection = new Connection(clientSocket);
            connection.send(REBIND);

            // handshake with nameserver before transfering the object reference
            if(connection.receive().equals(OK)) {
                ObjectRef objRef = new ObjectRef(clientSocket.getLocalAddress().toString(), SERVER_PORT, name);
                connection.send(objRef);
                if(connection.receive().equals(OK)) {
                    index.put(name, servant);
                } else {
                    connection.close();
                }
            } else {
                connection.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // connection closen
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object resolve(String name) {  
        Connection connection = null;
        try {
            // send rebind to nameserver and register object
            connection = new Connection(clientSocket);
            connection.send(RESOLVE);

            // handshake with nameserver before transfering the object reference
            if(connection.receive().equals(OK)) {
                connection.send(name);
                ObjectRef tmp_objRef = (ObjectRef)connection.receive();
                if(!tmp_objRef.getHost().equals(ERROR)) {
                    connection.close();
                    return tmp_objRef;
                } else {
                    connection.close();
                    System.out.println("### NameServiceImpl: Error, resolve could not find object reference!\n");
                    return null;
                }                        
            } else {
                connection.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // connection closen
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

}