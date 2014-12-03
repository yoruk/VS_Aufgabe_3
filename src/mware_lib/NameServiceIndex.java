package mware_lib;

import java.util.HashMap;
import java.util.Map;
import util.Connection;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NameServiceIndex {
	private final Map<String, NameServiceEntry> index = new HashMap<String, NameServiceEntry>();
	private WriteLock lock;
	
	public NameServiceIndex() {
		
	}
	
	public void addIndexEntry(String name, Object objRef, Connection connection) {
//		try {
//			locker.writeLock().lock();
			index.put(name, new NameServiceEntry(objRef, connection));
//		} finally {
//			locker.writeLock().unlock();
//		}
	}
	
	public NameServiceEntry getIndexEntry(String name) {
//		try {
//			locker.readLock().lock();
			return index.get(name);
//		} finally {
//			locker.writeLock().unlock();
//		}
	}
}
