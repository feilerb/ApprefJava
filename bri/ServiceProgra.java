package bri;

import Utilisateurs.NoSuchProgrammeurException;
import Utilisateurs.Programmeur;
import Utilisateurs.ProgrammeurAlreadyHere;
import Utilisateurs.Programmeurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

public class ServiceProgra implements Runnable{

    private final Socket client;
    private Programmeur p;
    private int num;

    ServiceProgra(Socket socket, int n) {
        client = socket;
        num=n;
    }

    @Override
    public void run() {
        System.err.println("Connexion à Service Pro "+ num);
        try {

            BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
            PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
            String envoye = "";
            URLClassLoader urlcl;
            while (true){
                envoye += """
                        Vous pouvez desormais
                        1 : Vous connectez
                        2 : Vous inscrire
                        *
                        """;
                out.println(envoye);
                envoye = "";
                String nbstr = in.readLine();
                if (nbstr.equals("1")){
                    try{
                        envoye += """
                            Entrée votre pseudo
                            *
                            """;
                        out.println(envoye);
                        String nom = in.readLine();
                        p = Programmeurs.getProgrammeur(nom);
                        envoye = """
                            Entrée votre mdp
                            *
                            """;
                        out.println(envoye);
                        envoye = "Mauvais mot de passe\n\n";
                        String mdp = in.readLine();
                        if (p.bonmdp(mdp)){
                            String fileName = p.getFtp();
                            urlcl = URLClassLoader.newInstance(new URL[]{new URL(fileName)});
                            break;
                        }

                    }
                    catch (NoSuchProgrammeurException e){
                        envoye = e.toString() + "\n\n";
                    }
                } else if (nbstr.equals("2")) {
                    try {
                        envoye = """
                            Veuillez inscrire votre nom
                            *
                            """;
                        out.println(envoye);
                        String n = in.readLine();
                        Programmeurs.Programmeurpresent(n);
                        envoye = """
                            Veuillez inscrire votre mot de passe
                            *
                            """;
                        out.println(envoye);
                        String m = in.readLine();
                        envoye = """
                            Veuillez inscrire votre adresse de serveur ftp (exemple : ftp://localhost:2121/ )
                            *
                            """;
                        out.println(envoye);
                        String f = in.readLine();
                        try {
                            urlcl = URLClassLoader.newInstance(new URL[]{new URL(f)});
                        }
                        catch (IOException e){
                            envoye = "URL non valide, inscription a refaire\n\n";
                            continue;
                        }

                        Programmeurs.setnewProgrammeur(n,m,f);
                        p = Programmeurs.getProgrammeur(n);
                        break;
                    }
                    catch (ProgrammeurAlreadyHere | NoSuchProgrammeurException e){
                        envoye = e.toString() + "\n\n";
                    }

                }
                else{
                    envoye = """
                            J'ai dit 1 ou 2
                            """;
                }
            }

            envoye = "";
            while (true){
                envoye += """
                        Vous pouvez desormais
                        1 : Ajouter un service dans un package a votre nom dans votre serveur ftp
                        2 : Mettre a jour un Service
                        3 : Declarer un changement d'adresse ftp
                        4 : Afficher ftp
                        5 : Activer Desactiver un Service
                        6 : Supprimer un Service
                        7 : Quitter
                        *""";
                out.println(envoye);
                String nbstr = in.readLine();
                if (nbstr.equals("1")){
                    envoye = "Quelle service souhaitez vous rajoutez\n" +
                            "*";
                    out.println(envoye);
                    envoye = "";
                    String classeName = in.readLine();
                    try {
                        Class <?> classe = urlcl.loadClass(p.getNom()+"." +classeName);
                        ServiceRegistry.addService(classe);
                    }
                    catch (Exception e){
                        envoye = e.toString() + "\n\n";
                    }

                } else if (nbstr.equals("2")) {
                    envoye = """
                            Quelle service souhaitez vous mettre a jour
                            *
                            """;
                    out.println(envoye);
                    envoye = "";
                    String classeName = in.readLine();
                    try {
                        Class <?> classe = urlcl.loadClass(p.getNom()+"." +classeName);
                        ServiceRegistry.majService(classe);
                    }
                    catch (Exception e){
                        envoye = e.toString() + "\n\n";
                    }
                }
                else if (nbstr.equals("3")){
                    envoye = """
                            Veuillez inscrire votre adresse de serveur ftp (exemple : ftp://localhost:2121/ )
                            *
                            """;
                    out.println(envoye);
                    envoye = "";
                    String f = in.readLine();
                    p.setFtp(f);
                    String fileName = p.getFtp();
                    urlcl = URLClassLoader.newInstance(new URL[] {new URL(fileName)});
                }
                else if (nbstr.equals("4")){
                    envoye = p.getFtp() + "\n";
                }
                else if (nbstr.equals("5")){
                    envoye = """
                            Quelle service souhaitez vous activer/desactiver
                            *
                            """;
                    out.println(envoye);
                    envoye = "";
                    String classeName = in.readLine();
                    try {
                        Class <?> classe = urlcl.loadClass(p.getNom()+"." +classeName);
                        ServiceRegistry.activerdesactive(classe);
                    }
                    catch (Exception e){
                        envoye = e.toString() + "\n\n";
                    }
                }
                else if (nbstr.equals("6")){
                    envoye = """
                            Quelle service souhaitez vous supprimer
                            *
                            """;
                    out.println(envoye);
                    envoye = "";
                    String classeName = in.readLine();
                    try {
                        Class <?> classe = urlcl.loadClass(p.getNom()+"." +classeName);
                        ServiceRegistry.suppService(classe);
                    }
                    catch (Exception e){
                        envoye = e.toString() + "\n\n";
                    }
                }
                else if (nbstr.equals("7")){
                    client.close();
                }
                else {
                    envoye = "Pas de commentaire\n";
                }
            }
        }
        catch (IOException e){

            System.err.println("Fin de Service Progra : " + num + " " + p.getNom());
        }

    }
    public void start() {
        (new Thread(this)).start();
    }
}
