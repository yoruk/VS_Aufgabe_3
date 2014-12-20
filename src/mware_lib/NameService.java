/*
 * 	Verteilte Systeme Praktikum, Wintersemester 2014/15
 * 
 *  Eugen Winter, Michael Schmidt
 */

package mware_lib;

public abstract class NameService {
    public abstract void rebind(Object servant, String name);

    /*
     * Meldet ein Objekt (servant) beim Namensdienst an. Eine eventuell schon
     * vorhandene Objektreferenz gleichen Namens soll ueberschrieben werden.
     */

    public abstract Object resolve(String name);
    /* Liefert eine generische Objektreferenz zu einem Namen. */

}
