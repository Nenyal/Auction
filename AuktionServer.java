import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuktionServer extends UnicastRemoteObject implements AuktionInterface {
    
    Vector<Artikel> angebote = new Vector();
    public AuktionServer() throws RemoteException {
        super();
        angebote.add(new Artikel(1, "chinese vase", 30));
        angebote.add(new Artikel(2, "persian rug", 250));
        angebote.add(new Artikel(3, "nintendo gameboy", 55));
    } 
    
    public String[] getAngebote() throws RemoteException {
        String[] response = new String[angebote.size()];
        int i = 0;
        for (Artikel a:angebote) {
            response[i] = angebote.get(i).id + " - " +
                          angebote.get(i).name + " - " +
                          angebote.get(i).preis + " - " +
                          angebote.get(i).hoechstbieter;
            i++;
        }
        return response;
    }
    
    
    public void bid(String kundenname, int angebot_id, float preis) throws RemoteException {
        System.out.println("bid request from " + kundenname + " " + preis);
        int i = angebote.indexOf(new Artikel(angebot_id, "", 0));
        if (i != -1) {
           
                Artikel a = angebote.get(i);
                // lese preis von Datenbank
                System.out.println("old price " + a.preis);
                if (a.preis < preis) {
                    try {
                        Thread.sleep(10000);
                        a.preis = preis;
                        long j = 0;Thread.sleep(10000);
                        // schreibe preis zu Datenbank
                        a.hoechstbieter = kundenname;
                        System.out.println("new price " + a.preis);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AuktionServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            
        }
    }
    
    public static void main(String[] args) {
        
        try {
          AuktionServer as = new AuktionServer();
          // rmi registry  
          LocateRegistry.createRegistry(1900);
          Naming.rebind("rmi://10.144.82.80:1900/as", as);
          
        } catch (Exception ie) {
            System.out.println("Exception:" +ie);
        }
        
    }

}


class Artikel {
    int id;
    String name;
    float preis;
    String hoechstbieter;
    
    public Artikel(int id, String n, float p) {
        this.id = id;
        name = n;
        preis = p;
    }
    
    public boolean equals(Object o) {
        Artikel a = (Artikel) o;
        if (this.id == a.id) return true;
        return false;
    }
} 
