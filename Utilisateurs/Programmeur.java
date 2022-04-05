package Utilisateurs;

public class Programmeur {
    private String nom;
    private String mdp;
    private String ftp;

    public Programmeur(String n, String m, String f){
        this.nom=n;
        this.mdp=m;
        this.ftp=f;
    }

    public boolean bonmdp (String tentative){
        return tentative.equals(mdp);
    }

    public String getNom(){
        return nom;
    }

    public String getFtp(){
        return ftp;
    }

    public void setFtp(String newftp){
        this.ftp=newftp;
    }
}
