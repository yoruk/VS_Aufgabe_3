package name_service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NameService {

  public static void main(String[] args) {

    // Standard-Port fuer Verbindungen
    int port = 12345;

    // Thread-Pool fuer Client-Anfragen
    final ExecutorService threads = Executors.newCachedThreadPool();

    // Uebergebenen Port pruefen
    if(args.length > 0) {
      try {
        port = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        System.out.println("Das uebergebene Argument ist keine gueltige Port-Nummer!\n"
            + "Verwende den Standard-Port " + port + "!\n");
      }
    }

    // Lokale Host-Adresse des NameService-Servers ermitteln
    try {
      InetAddress localhost = InetAddress.getLocalHost();
      System.out.println("Starte NameService...\n" + localhost.getHostAddress()
                       + ":" + port + " (" + localhost.getHostName() + ")\n");

      // Server-Socket fuer Port erstellen
      ServerSocket server_socket = new ServerSocket(port);

      // Client-Anfragen entgegennehmen
      while(true) {
        final Socket client_socket = server_socket.accept();
        System.out.println("Eingehende Client-Verbindung: " + client_socket.getInetAddress().getHostAddress()
                         + ":" + client_socket.getPort() + " (" + client_socket.getInetAddress().getHostName() + ")\n");
        threads.execute(new RequestHandler(client_socket, storage)); // FIXME: Mehrere Callable benutzen, eventuell mit Verarbeitung des Rueckgabewerts
      }
    } catch (UnknownHostException e) {
      System.err.println("Lokale Host-Adresse konnte nicht ermittelt werden!");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("Socket auf Port " + port + " konnte nicht erstellt werden!");
      e.printStackTrace();
    }
  }
}
