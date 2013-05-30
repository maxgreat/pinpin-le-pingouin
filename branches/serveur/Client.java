import Network.*;
import java.util.*;
import java.nio.channels.*;

public class Client
{
    public static final String DEFAULT_ADDR = "localhost";
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
		
	System.out.println("Lancement du client...");
	ConnexionClient cs = new ConnexionClient(addr, port, listeSocket);
	Listener ls        = new Listener(listeSocket);
	Writer ws          = new Writer(listeSocket);
	cs.setListener(ls);

	Thread tListener = new Thread(ls);
	Thread tWriter = new Thread(ws);


	System.out.println("Lancement du gestionnaire d'entrée...");
	tListener.start();

	System.out.println("Lancement du gestionnaire de lecture...");
	tWriter.start();
	
	System.out.println("Ajout d'un client (1)");
	SocketChannel client1 = cs.connectClient();

	System.out.println("Ajout d'un client (2)");
	SocketChannel client2 = cs.connectClient();

	Message ping1 = new Message(NetworkCmd.PING);
	ping1.write(new Integer(1));

	Message ping2 = new Message(NetworkCmd.PING);
	ping2.write(new Integer(2));

	//	while (client1.isRegistered() || client2.isRegistered())
	{
	    if (client1.isRegistered())
		ws.send(client1, ping1);
	    if (client2.isRegistered())
		ws.send(client2, ping2);
	}
	try
	{
	    Thread.sleep(5000);
	}
	catch (InterruptedException e)
	{
	}

	System.out.println("Fin");
	tListener.interrupt();
	tWriter.interrupt();
    }
}
