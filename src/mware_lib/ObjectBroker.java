package mware_lib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ObjectBroker {
    private static NameServiceImpl nameService;
    private static Map<String, Object> index = new HashMap<String, Object>();

    public static ObjectBroker init(String serviceHost, int listenPort, boolean debug) {
        try {
            nameService = new NameServiceImpl(serviceHost, listenPort, index);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*
         * Das hier zurueckgelieferte Objekt soll der zentrale Einstiegspunkt
         * der Middleware aus der Anwendersicht sein. Parameter: Host und Port,
         * bei dem die Dienste (Namensdienst) kontaktiert werden sollen. Mit
         * debug sollen Testausgaben der Middleware ein- und ausgeschaltet
         * werden koennen.
         */
        return null;
    }

    public NameService getNameService() {
        return nameService;
        /* Liefert den Namensdienst (Stellvertreterobjekt). */
    }

    public void shutdown() {
        /* Beendet die Benutzung der Middleware in dieser Anwendung. */
        // getInstance() von laufenden Threads oder Sockets
    }

}
