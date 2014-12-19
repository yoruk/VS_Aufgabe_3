package mware_lib;

import java.io.Serializable;

public class ObjectRef implements Serializable {
	private static final long serialVersionUID = 20L;
	private String host;
	private int port;
	private String objId;
	private String nameServiceAddress;
	private int nameServicePort;
	
	public ObjectRef(String host, int port, String objId) {
		this.host = host;
		this.port = port;
		this.objId = objId;
	}
	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}
	
	public String getObjId() {
		return objId;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(host + ":" + port + "-" + objId);
		
		return sb.toString();
	}

	public String getNameServiceAddr() {
		return nameServiceAddress;
	}

	public void setNameServiceAddr(String nameServiceAddr) {
		this.nameServiceAddress = nameServiceAddr;
	}

	public int getNameServicePort() {
		return nameServicePort;
	}

	public void setNameServicePort(int nameServicePort) {
		this.nameServicePort = nameServicePort;
	}
}