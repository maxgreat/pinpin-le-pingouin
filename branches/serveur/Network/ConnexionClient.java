package Network;

import java.nio.channels.*;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Classe qui gère les connexions côté client
 **/
public class ConnexionClient 
{
    protected ServerSocketChannel channel;
    protected Set<SocketChannel> listeSocket;
    protected String serveurAddr;
    protected int port;
    protected Listener listener;

    public ConnexionClient(String serveurAddr, int port, Set<SocketChannel> listeSocket)
    {
	Cmd.init();
	this.listeSocket = listeSocket;
	this.serveurAddr = serveurAddr;
	this.port = port;
    }


    public void setListener(Listener listener)
    {
	this.listener = listener;
    }

    public Listener getListener()
    {
	return this.listener;
    }

    public SocketChannel connectClient()
    {
	SocketChannel sc = null;
	try
	{
	    sc = SocketChannel.open();
	    sc.configureBlocking(false);
	    sc.connect(new InetSocketAddress(serveurAddr, port));
	    while (!sc.finishConnect());
		
	}
	catch (IOException e) 
	{
	    System.out.println("Problème lors de la connexion au serveur "+serveurAddr+":"+port);
	    return null;
	}

	listeSocket.add(sc);
	getListener().registerServeur(sc);
	

	return sc;
    }
}
