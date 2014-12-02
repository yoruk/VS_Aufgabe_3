package mware_lib;

public class ObjectBroker {

  public static ObjectBroker init(String serviceHost, int listenPort, boolean debug) {
    /* Das hier zurueckgelieferte Objekt soll der zentrale Einstiegspunkt der
     * Middleware aus der Anwendersicht sein.
     * Parameter: Host und Port, bei dem die Dienste (Namensdienst) kontaktiert
     *            werden sollen.
     *            Mit debug sollen Testausgaben der Middleware ein- und ausge-
     *            schaltet werden können.
     * */
    return null;
  }  

  public NameService getNameService() {
    /* Liefert den Namensdienst (Stellvertreterobjekt). */
    return null;
  }

  public void shutdown() {
    /* Beendet die Benutzung der Middleware in dieser Anwendung. */
  }

}
