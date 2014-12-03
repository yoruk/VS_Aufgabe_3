package mware_lib;

import util.Connection;

public class NameServiceEntry {
	private Object objRef;
	private Connection connection;
	
	public NameServiceEntry(Object objRef, Connection connection) {
		this.objRef = objRef;
		this.connection = connection;
	}
	
	public Object getObjRef() {
		return objRef;
	}

	public Connection getConnection() {
		return connection;
	}
}