package Network;

import Utilitaires.*;


import java.io.*;
import java.nio.channels.*;
import java.nio.*;
import java.util.*;

/**
 * Classe qui gère les écritures sur les connexions
 **/
public class Writer implements Runnable
{
    Utilitaires.File<Pair<SocketChannel, Message>> file;
    protected Set<SocketChannel> listeSocket;

    public Writer(Set<SocketChannel> listeSocket)
    {
	file = new Utilitaires.File<Pair<SocketChannel, Message>>();
	this.listeSocket = listeSocket;
    }

    public void send(SocketChannel client, Message data)
    {
	file.push(new Pair<SocketChannel, Message>(client, data));
    }

    public void run()
    {
	while (!Thread.currentThread().isInterrupted()) 
	{
	    // pull bloquant
	    Pair<SocketChannel, Message> p = file.pull();
	    if (p == null)
		break;
	    SocketChannel socket = p.first;
	    Message message = p.second;

	    if (!listeSocket.contains(socket))
	    {
		file.clean();
		continue;
	    }

	    try
	    {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		System.out.println("ENVOIE de "+message.getCmd());
		// Write int
		for(int i = 0; i < 4; i++) 
		    baos.write(0);

		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(message);
		oos.close();

		// Ecris taille message + message
		ByteBuffer wrap = ByteBuffer.wrap(baos.toByteArray());
		wrap.putInt(0, baos.size()-4);
		
		try
		{
		    if (socket.write(wrap) != baos.size())
			throw new DisconnectException();
		}
		catch (IOException e)
		{
		    throw new DisconnectException();
		}

	    } 
	    catch (IOException e)
	    {
		System.out.println("Problème lors de l'écriture sur "+socket);
		listeSocket.remove(socket);
		try
		{
		    socket.close();
		}
		catch (IOException ep)
		{
		}
	    }
	    catch (DisconnectException e)
	    {
		System.out.println("Hote déconnecté");
		try
		{
		    socket.close();
		} 
		catch (IOException ep)
		{
		}
		
		listeSocket.remove(socket);
	    }
	}
    }
}
