/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package tests;

import mware_lib.Message;

public class MessageTest {
	public static void main(String[] args) {
		System.out.println("MessageTest.main()");
		
		Message msg1 = new Message();
		System.out.println(msg1);
		
		msg1.setReason(Message.MessageReason.REBIND);
		System.out.println(msg1);
	}
}
