/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package cash_access;

public class OverdraftException extends Exception {
	private static final long serialVersionUID = 4890520406792671352L;

	public OverdraftException(String message) {
		super(message);
	}
}
