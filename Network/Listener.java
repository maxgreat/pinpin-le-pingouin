package Network;

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

import Utilitaires.*;

public class Listener implements Runnable
{
    protected Selector selector;
    protected Set<SocketChannel> listeSocket;

    // Liste des buffer en cours de lecture
    protected HashMap<SocketChannel, Pair<Integer, ByteBuffer>> buffering;

    protected Object lock;

    public Listener(Set<SocketChannel> listeSocket)
    {
	this.listeSocket = listeSocket;
	this.buffering = new HashMap<SocketChannel, Pair<Integer, ByteBuffer>>();

	this.lock = new Object();

	try
	{
	    this.selector = Selector.open();
	}
	catch (IOException e)
	{
	    System.out.println("Problème lors de l'ouverture du Selector.");
	    System.exit(-1);
	}
    }

    public Selector getListenerSelector()
    {
	return selector;
    }

    public void register(SocketChannel channel)
    {
	try
	{
	    synchronized(this.lock)
	    {
		// Le register est bloqué lors du select (bug jvm)
		getListenerSelector().wakeup();
		channel.register(getListenerSelector(), SelectionKey.OP_READ);
	    }
	} 
	catch (ClosedChannelException e)
	{
	    System.out.println("Problème lors de l'association du selector a l'hote, serveur déjà partit.");
	    listeSocket.remove(channel);
	}
    }

    public void run()
    {
	while (!Thread.currentThread().isInterrupted()) 
	{
	    // Attends des données
	    try
	    {
		if (getListenerSelector().select() == 0)
		{
		    // Point de synchronisation
		    synchronized(this.lock)
		    {
		    }
		    continue;
		}

	    
		// Récupère les sockets actives
		Set<SelectionKey> keys = selector.selectedKeys();

		// Parcours les sockets actives
		Iterator<SelectionKey> it = keys.iterator();
		while (it.hasNext()) 
		{
		    // Récupère la socket
		    SelectionKey key = it.next();
		    it.remove();
		    Message message = null;
		    SocketChannel socket = (SocketChannel)key.channel();

		    if (!listeSocket.contains(socket))
			continue;

		    try
		    {
			Pair<Integer, ByteBuffer> buffer = null;

			if (buffering.containsKey(socket))
			{
			    buffer = buffering.get(socket);
			}
			else
			{
			    buffer = new Pair<Integer, ByteBuffer>(new Integer(0), ByteBuffer.wrap(new byte[4]));
			    buffering.put(socket, buffer);
			}


			if (buffer.first.equals(0)) 
			{
			    try
			    {
				if (socket.read(buffer.second) == -1)
				    throw new DisconnectException();
			    }
			    catch (IOException e)
			    {
				throw new DisconnectException();
			    }

			    if (buffer.second.remaining() == 0) 
			    {
				buffer.first = buffer.second.getInt(0);

				buffer.second = ByteBuffer.allocate(buffer.first);
				buffer.second.clear();
			    }
			} 
			else
			{
			    try
			    {
				if (socket.read(buffer.second) == -1)
				    throw new DisconnectException();
			    }
			    catch (IOException e)
			    {
				throw new DisconnectException();
			    }

			    if (buffer.second.remaining() == 0) 
			    {
				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.second.array()));

				try
				{
				    Object obj = ois.readObject();
				    if (obj instanceof Message)
					message = (Message)obj;
				    else
					throw new ClassNotFoundException();

				    Cmd infoPacket = Cmd.getInformation(message.getCmd());
				    if (infoPacket == null)
					throw new ClassNotFoundException();

				    System.out.println("Informations : "+infoPacket);
				    buffering.remove(socket);
				}
				catch (ClassNotFoundException e)
				{
				    System.out.println("Problème lors de la récupération d'un message par un serveur, kick.");
				    listeSocket.remove(socket);
				    key.cancel();
				}
			    }
			}
		    } 
		    catch (IOException e)
		    {
			System.out.println("Problème lors de la récupération du message de "+message);
			// Enlève la socket de la liste
			listeSocket.remove(socket);
			buffering.remove(socket);
			key.cancel();
	
		    }
		    catch (DisconnectException e)
		    {
			System.out.println("Deconnection hôte détecté");
			// Enlève la socket de la liste
			listeSocket.remove(socket);
			buffering.remove(socket);
			key.cancel();
		    }
		}

		keys.clear();
	    }
	    catch(IOException e)
	    {
		System.out.println("Problème lors de la récupération d'une écriture par un serveur.");
		System.exit(-1);
	    }
	}
    }
}
