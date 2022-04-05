package appli;

import Utilisateurs.NoSuchProgrammeurException;
import Utilisateurs.Programmeur;
import Utilisateurs.Programmeurs;
import bri.ServeurBRi;
import bri.ServeurProgra;
import bri.ServiceRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

public class appli {
    private final static int PORT_SERVICE_PROGRA = 3001;
    private final static int PORT_SERVICE_CLI = 3000;




    public static void main(String[] args) throws MalformedURLException {
        Programmeurs ps = new Programmeurs();
        new Thread(new ServeurBRi(PORT_SERVICE_CLI)).start();
        new Thread(new ServeurProgra(PORT_SERVICE_PROGRA)).start();






    }
}
