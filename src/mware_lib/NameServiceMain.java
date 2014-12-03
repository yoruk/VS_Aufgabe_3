package mware_lib;

import java.io.IOException;


public class NameServiceMain {	
	public static void main(String[] args) throws IOException {
		NameService nameService = new NameServiceImpl(6666);
	}
}
