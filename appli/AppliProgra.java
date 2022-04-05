package appli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppliProgra {
    public static void main(String[] args){
        String mot;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Socket client;

        try{
        client = new Socket("localhost", 3001);

            PrintWriter sOut = new PrintWriter(client.getOutputStream(),true);
            BufferedReader sIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String mess;
            boolean fin =false;
            while (true) {
                while (true){
                    mess=sIn.readLine();
                    if (mess.equals("*"))break;
                    if (mess.equals("e")){
                        mess=sIn.readLine();
                        System.err.println(mess);
                    }
                    else
                        System.out.println(mess);
                }
                if (fin)break;
                System.out.print("> ");
                mot = in.readLine();
                sOut.println(mot);
                if (mot.equals("exit")) break;
            }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            System.err.println("Fin de Service");
        }
    }
}