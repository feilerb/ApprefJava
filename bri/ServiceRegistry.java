package bri;

import Utilisateurs.NoSuchProgramHere;
import Utilisateurs.ProgramAlreadyHere;

import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceRegistry {
    // cette classe est un registre de services
    // partag�e en concurrence par les clients et les "ajouteurs" de services,
    // un Vector pour cette gestion est pratique

    static {
        servicesClasses = Collections.synchronizedList(new ArrayList<>());
        actif = Collections.synchronizedList(new ArrayList<>());
    }
    private static List<Class<?>> servicesClasses;
    private static List<Boolean> actif;

    // ajoute une classe de service apr�s contr�le de la norme BLTi
    public static void addService(Class<?> runnableClass) throws InstantiationException, IllegalAccessException, ProgramAlreadyHere {

        for (Class<?> c: servicesClasses) {
            if (c.getName().equals(runnableClass.getName()))
                throw new ProgramAlreadyHere();
        }
        servicesClasses.add(runnableClass);
        actif.add(true);

    }

    public static void majService(Class<?> runnableClass) throws NoSuchProgramHere {

        int num =0;
        for (Class<?> c: servicesClasses) {
            if (c.getName().equals(runnableClass.getName())) break;
            num++;
        }
        if(servicesClasses.size()!=num){
            servicesClasses.set(num, runnableClass);
        }
        else throw new NoSuchProgramHere();

    }

    public static void activerdesactive(Class<?> runnableClass) throws NoSuchProgramHere {
        int num =0;
        for (Class<?> c: servicesClasses) {
            if (c.getName().equals(runnableClass.getName())) break;
            num++;
        }
        if(servicesClasses.size()!=num){
            actif.set(num, !actif.get(num));
        }
        else throw new NoSuchProgramHere();
    }

    public static void suppService(Class<?> runnableClass) throws NoSuchProgramHere {
        int num =0;
        for (Class<?> c: servicesClasses) {
            if (c.getName().equals(runnableClass.getName())) break;
            num++;
        }
        if(servicesClasses.size()!=num){
            servicesClasses.remove(num);
            actif.remove(num);
        }
        else throw new NoSuchProgramHere();
    }

    // renvoie la classe de service (numService -1)
    public static Class<?> getServiceClass(int numService) throws NoSuchProgramHere {
        int count = numService;
        int n = 0;
        for (Class<?> c: servicesClasses) {
            if (actif.get(n)){
                count--;
            }
            if (count == 0){
                return c;
            }
            n++;
        }
        throw new NoSuchProgramHere();

        //return servicesClasses.get(numService-1);
    }

    // liste les activit�s pr�sentes
    public static String toStringue() {
        StringBuilder result = new StringBuilder("Activités présentes :\n");
        int num =1;
        int n = 0;
        for (Class<?> c: servicesClasses) {
            if (actif.get(n)){
                result.append("#").append(num).append(" ").append(c.getName()).append("\n");
                num++;
            }
            n++;
        }
        result.append("*");
        return result.toString();
    }

}
