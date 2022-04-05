package bri;


import Utilisateurs.Programmeur;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.*;


class ServiceBRi implements Runnable {

    private Socket client;
    private int num;

    ServiceBRi(Socket socket, int n) {
        client = socket;
        num = n;
    }

    public void run() {
        System.err.println("Connexion à Service Amat " + num);
        try {
            BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
            PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
            out.println(ServiceRegistry.toStringue());
            int choix = Integer.parseInt(in.readLine());
            Class<? extends Service> c = (Class<? extends Service>)ServiceRegistry.getServiceClass(choix);
            Service s =  c.getConstructor(Socket.class).newInstance(client);
            new Thread(s).start();
            //Constructor<? extends Service> cons = c.getConstructor(Socket.class);
            //new Thread(cons.newInstance(client)).start();
            // instancier le service num�ro "choix" en lui passant la socket "client"
            // invoquer run() pour cette instance ou la lancer dans un thread � part

        }
        catch (IOException e){
            System.err.println("Fin de Service Amat " + num);
        }
        catch (Exception e) {
            //Fin du service
            System.err.println("Fin de Service Bri");
        }
        System.err.println("Fin de Service Amat " + num);

        //try {client.close();} catch (IOException e2) {}
    }

    protected void finalize() throws Throwable {
        client.close();
    }

    // lancement du service
    public void start() {
        (new Thread(this)).start();
    }

}
