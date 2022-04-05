package examples;

import bri.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ServiceMessagerie implements Service {

    private final Socket client;

    public ServiceMessagerie(Socket socket) {
        client = socket;
    }

    static {
        Messages = Collections.synchronizedList(new ArrayList<>());
        Utilisateur = Collections.synchronizedList(new ArrayList<>());
    }
    private static List<String> Messages;
    private static List<String> Utilisateur;
    private String user;

    private static void addMessage(BufferedReader in, PrintWriter out, String nom) throws IOException {
        out.println("""
                    Tapez le nom du Destinataire
                    *""");
        String destinataire = in.readLine();
        StringBuilder Message = new StringBuilder(destinataire + ":" + nom +":\n");
        out.println("""
                    Tapez votre message, pour le terminer entrez une ligne vide
                    *""");
        while (true){
            String recu = in.readLine();
            if (recu.equals("")) break;
            Message.append(recu).append("\n");
            out.println("*");
        }
        Messages.add(Message.toString());

    }

    private static void lireMessage(BufferedReader in, PrintWriter out, String nom) throws IOException{
        int num = 0;
        int mess = 1;
        ArrayList<Integer> supp = new ArrayList<Integer>();
        for (String m: Messages) {
            if (m.split(":")[0].equals(nom)){
                out.println("Message " + mess++);
                m = m.substring(m.split(":")[0].length()+1);
                out.println(m);
                supp.add(num);
            }
            num++;

        }
        int nb = supp.size();
        for (int x = 1; x<=nb; x++){
            Messages.remove(supp.get(supp.size()-x).intValue());
        }
        if (mess==1){
            out.println("\nAucun message reçu\n");
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
            PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
            String envoye = "";
            while (true){
                envoye += """
                        Vous pouvez desormais
                        1 : Vous connectez
                        2 : Vous inscrire
                        *
                        """;
                out.println(envoye);
                envoye ="";
                String nbstr = in.readLine();
                if (nbstr.equals("1")){
                    try{
                        envoye += """
                            Entrée votre pseudo
                            *
                            """;
                        out.println(envoye);
                        String nom = in.readLine();
                        if (!getUser(nom)){
                            envoye = "Utilisateur inconnu\n\n";
                            continue;
                        }
                        envoye = """
                            Entrée votre mdp
                            *
                            """;
                        out.println(envoye);
                        envoye = "Mauvais mot de passe\n\n";
                        String mdp = in.readLine();
                        if (bonmdp(mdp))break;

                    }
                    catch (Exception e){
                        envoye = "Utilisateur inconnu\n\n";

                    }
                } else if (nbstr.equals("2")) {
                    envoye = """
                            Veuillez inscrire votre nom
                            *
                            """;
                    out.println(envoye);
                    String n = in.readLine();
                    if (getUser(n)){
                        envoye = "Nom déjà pris\n";
                        continue;
                    }
                    envoye = """
                            Veuillez inscrire votre mot de passe
                            *
                            """;
                    out.println(envoye);
                    String m = in.readLine();
                    setnewuser(n,m);
                    getUser(n);
                    break;
                }
                else{
                    envoye = """
                            J'ai dit 1 ou 2
                            """;
                }
            }


            while (true){
                out.println("""
                        Vous pouvez soit
                        1 : Envoyer un message
                        2 : Lire vos messages
                        3 : Quitter
                        *""");

                String choix = in.readLine();
                if (choix.equals("1")){
                    addMessage(in, out, user.split(":")[0]);
                }
                else if (choix.equals("2")){
                    lireMessage(in, out, user.split(":")[0]);
                }
                else if (choix.equals("3")){
                    break;
                }
                else {
                   out.println("\nPas comme ça Zinedine\n");
                }
            }
            client.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean bonmdp(String mdp) {
        if (user.split(":")[1].equals(mdp)){
            return true;
        }
        return false;
    }

    private boolean getUser(String nom) throws Exception {
        for (String u:Utilisateur) {
            String nomli = u.split(":")[0];
            if (nomli.equals(nom)){
                user= u;
                return true;
            }

        }
        return false;
    }

    private void setnewuser(String n, String m) {
        Utilisateur.add(n+":"+m);
    }
}
