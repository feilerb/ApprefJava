package examples;

import bri.Service;

import java.io.*;
import java.net.Socket;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ServiceLectureXML implements Service {

    private final Socket client;

    public ServiceLectureXML(Socket socket) {
        client = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
            PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
            String envoye ="";
            Document document;
            while (true){
                envoye += """
                            Veuillez mettre le chemin pour acceder a votre fichier xml
                            *
                            """;
                out.println(envoye);
                String recu = in.readLine();
                try {
                    File file = new File(recu);

                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    document = db.parse(file);
                    document.getDocumentElement().normalize();
                    break;
                }
                catch (IOException | SAXException | ParserConfigurationException e){
                    out.println(e.toString());
                }
            }
            out.println("Root Element :" + document.getDocumentElement().getNodeName());
            NodeList nList = document.getChildNodes();
            out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                out.println("\nCurrent Element : " + nNode.getNodeName());
                recana(nNode, out);
            }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void recana(Node nNode, PrintWriter out){
        System.out.println("----------------------------");
        System.out.println("Nom de noeud="+nNode.getNodeName());
        System.out.println("Contenu du noeud="+nNode.getNodeValue());
        String passe = "\n";
        if (nNode.getNodeValue()!=null){
        System.out.println(nNode.getNodeName().equals("#text"));
        System.out.println(nNode.getNodeValue().charAt(0) == passe.charAt(0));}
        if ((nNode.getNodeValue()==null)||(!nNode.getNodeName().equals("#text") || !(nNode.getNodeValue().charAt(0) == passe.charAt(0)))){
        if (!nNode.getNodeName().equals("#text")){
        out.println(nNode.getNodeName() + " {");}
        if (nNode.getNodeValue()!=null){
        out.println(nNode.getNodeValue());
        }
        }
        NodeList nList = nNode.getChildNodes();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node n2Node = nList.item(temp);
            recana(n2Node, out);
        }
        if (!nNode.getNodeName().equals("#text")){
            out.println("} (" + nNode.getNodeName() + ")");}


    }
}
