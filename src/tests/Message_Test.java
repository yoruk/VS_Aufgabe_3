package tests;

import mware_lib.Message;

public class Message_Test {
	public static void main(String[] args) {
		System.out.println("Message_Test.main()");
		
		Message msg1 = new Message();
		System.out.println(msg1);
		
		msg1.setReason(Message.MessageReason.REBIND);
		System.out.println(msg1);
	}
}
