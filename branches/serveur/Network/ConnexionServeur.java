package Network;

import java.nio.channels.*;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Classe qui gère les connexions côté serveur
 * code select basé sûr http://www.javaworld.com/jw-04-2003/jw-0411-select.html?page=2
 **/
public class ConnexionServeur implements Runnable
{
    protected ServerSocketChannel channel;
    protected Set<SocketChannel> listeSocket;
    protected String bindAddr;
    protected int port;

    public ConnexionServeur(String bindAddr, int port, Set<SocketChannel> listeSocket)
    {
	Cmd.init();
	this.listeSocket = listeSocket;
	this.bindAddr = bindAddr;
	this.port = port;
    }

    public void run()
    {
	Selector selector = null;

	try
	{
	    channel = ServerSocketChannel.open();
	    channel.configureBlocking(false);
	    channel.socket().bind(new InetSocketAddress(bindAddr, port));
	

	    selector = Selector.open();
	    channel.register(selector, SelectionKey.OP_ACCEPT);
	
	}
	catch (IOException e)
	{
	    System.out.println("Problème lors du lancement du serveur : "+e);
	    System.exit(-1);
	}

	while (true) 
	{
	    // Attends des données
	    try
	    {
		if (selector.select() == 0)
		    continue;
	    
		// Récupère les sockets actives
		Set<SelectionKey> keys = selector.selectedKeys();

		// Parcours les sockets actives
		Iterator<SelectionKey> it = keys.iterator();
		while (it.hasNext()) 
		{
		    // Récupère la socket
		    SelectionKey key = it.next();
		    it.remove();

		    if (key.isConnectable()) 
			System.out.println("Disconnect ?");

		    // Ajoute la socket dans la liste des clients
		    SocketChannel client = ((ServerSocketChannel)key.channel()).accept();
		    client.configureBlocking(false);
		    System.out.println("Nouvelle connexion !");
		    listeSocket.add(client);
		}
		// Remove the selected keys because you've dealt
		// with them.
		keys.clear();
	    }
	    catch(IOException e)
	    {
		System.out.println("Problème lors de la récupération d'une nouvelle connexion.");
		System.exit(-1);
	    }
	}
    }
}
