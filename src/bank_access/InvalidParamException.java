/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package bank_access;

public class InvalidParamException extends Exception {
	private static final long serialVersionUID = -7858290009064645069L;

	public InvalidParamException(String message) {
		super(message);
	}
}
