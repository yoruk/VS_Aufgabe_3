/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package bank_access;

public class OverdraftException extends Exception {
	private static final long serialVersionUID = -1080515700023202592L;

	public OverdraftException(String message) {
		super(message);
	}
}
