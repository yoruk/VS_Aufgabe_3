package mware_lib;

import java.io.Serializable;

public class ObjectRef implements Serializable {
	private static final long serialVersionUID = 1L;
	private String host;
	private int port;
	private String objId;
	
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
}