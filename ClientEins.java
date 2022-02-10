import java.rmi.*;
import java.util.Scanner;

public class ClientEins {
    
    public static void main(String[] args) {
        
        try {
            String name = args[0];
            AuktionInterface ai = (AuktionInterface) Naming.lookup("rmi://10.144.82.80:1900/as");
            
            Scanner sc = new Scanner(System.in);
            String eingabe;
            int id;
            float preis;
            while (true) {
                eingabe = sc.nextLine();
                if (eingabe.equals("get")) {
                    String[] response = ai.getAngebote();
                    for (String s:response) {
                        System.out.println(s);
                    }
                } else if (eingabe.equals("bid")) {
                    id = sc.nextInt();
                    preis = sc.nextFloat();
                    ai.bid(name, id, preis);
                }
            }
            
        } catch (Exception ie) {
            System.out.println("Exception in Client1: "+ie);
        }
    }
}
