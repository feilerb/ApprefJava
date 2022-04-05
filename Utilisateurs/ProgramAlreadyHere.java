package Utilisateurs;

public class ProgramAlreadyHere extends Exception{
    @Override
    public String toString() {
        return "Programme déjà existant";
    }
}
