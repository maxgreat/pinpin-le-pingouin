import Network.*;
import java.util.*;
import java.nio.channels.*;

public class Serveur
{
    public static final String DEFAULT_ADDR = "0.0.0.0"; // Accepte toute connexion
    public static final int DEFAULT_PORT = 4242;
    public final static void main(String [] args)
    {
	String addr = DEFAULT_ADDR;
	int port = DEFAULT_PORT;

	Set<SocketChannel> listeSocket = new HashSet<SocketChannel>();
	listeSocket = Collections.synchronizedSet(listeSocket);

	if (args.length > 2)
	{
	    addr = args[2];
	    port = Integer.valueOf(args[1]);
	}
		
	System.out.println("Lancement du serveur...");
	ConnexionServeur cs = new ConnexionServeur(addr, port, listeSocket);
	Writer ws           = new Writer(listeSocket);
	Listener ls         = new Listener(listeSocket);

	cs.setListener(ls);

	Thread tConnexion = new Thread(cs);
	Thread tListener = new Thread(ls);
	Thread tWriter = new Thread(ws);


	System.out.println("Lancement du gestionnaire d'entrée...");
	tListener.start();

	System.out.println("Lancement du gestionnaire de lecture...");
	tWriter.start();

	System.out.println("Lancement du gestionnaire de connection...");
	tConnexion.start();


    }
}
