package Utilisateurs;

public class NoSuchProgrammeurException extends Exception{
    @Override
    public String toString() {
        return "Aucun Programmeur ne possède ce nom";
    }
}
