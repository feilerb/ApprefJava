package Utilisateurs;

public class NoSuchProgramHere extends Exception{
    @Override
    public String toString() {
        return "Aucun Programme enregistré ne possède ce nom";
    }
}
