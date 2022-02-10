import java.rmi.*;

public interface AuktionInterface extends Remote {
    
    public String[] getAngebote() throws RemoteException;
    
    public void bid(String kundenname, int angebot_id, float preis) throws RemoteException;

}
