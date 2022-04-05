package Utilisateurs;

import java.util.ArrayList;

public class Programmeurs {
    private static ArrayList<Programmeur> liste;

    public Programmeurs(){
        liste = new ArrayList<Programmeur>();
    }

    public static void setnewProgrammeur(String n,String m, String f){
        liste.add(new Programmeur(n,m,f));
    }

    public static Programmeur getProgrammeur(String n) throws NoSuchProgrammeurException{
        for (Programmeur p:liste) {
            if(p.getNom().equals(n)){
                return p;
            }
        }
        throw new NoSuchProgrammeurException();
    }

    public static void Programmeurpresent(String n) throws ProgrammeurAlreadyHere {
        for (Programmeur p:liste) {
            if(p.getNom().equals(n)){
                throw new ProgrammeurAlreadyHere();
            }
        }
    }
}
