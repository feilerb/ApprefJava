package Utilisateurs;

public class ProgrammeurAlreadyHere extends Exception{
    @Override
    public String toString() {
        return "Programmeur déjà existant";
    }
}
