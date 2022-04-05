package bri;

import java.io.IOException;
import java.net.ServerSocket;

public class ServeurProgra implements Runnable {
    private ServerSocket listen_socket;
    private int num=0;

    // Cree un serveur TCP - objet de la classe ServerSocket
    public ServeurProgra(int port) {
        try {
            listen_socket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Le serveur ecoute et accepte les connections.
    // pour chaque connection, il cree un ServiceInversion,
    // qui va la traiter.
    public void run() {
            System.out.println("Serveur Progra on");
        try {
            while(true)
                new ServiceProgra(listen_socket.accept(),num++).start();
        }
        catch (IOException e) {
            try {this.listen_socket.close();} catch (IOException e1) {}
            System.err.println("Pb sur le port d'écoute :"+e);
        }
    }

    // restituer les ressources --> finalize
    protected void finalize() throws Throwable {
        try {this.listen_socket.close();} catch (IOException e1) {}
    }

    // lancement du serveur
    public void lancer() {
        (new Thread(this)).start();
    }
}
