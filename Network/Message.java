package Network;

import java.io.*;
import java.util.*;

/**
 * Message à envoyer
 **/
public class Message implements Serializable
{
    ArrayList<Object> data = new ArrayList<Object>();
    NetworkCmd cmd;

    public static final long serialVersionUID = 42L;
    
    public Message(NetworkCmd cmd)
    {
	this.cmd = cmd;
    }

    public void setCmd(NetworkCmd cmd)
    {
	this.cmd = cmd;
    }

    public NetworkCmd getCmd()
    {
	return this.cmd;
    }

    /**
     * Ecris un objet dans le message (pas l'ID)
     **/
    public synchronized <T extends Serializable> void write(T object)
    {
	data.add(object);
    }

    
    /**
     * Lis un objet du message et avance (Thread Safe)
     **/
    public synchronized Object read() throws IndexOutOfBoundsException
    {
	return data.remove(0);
    }

    /**
     * Serialize les 
     **/
    private void writeObject(ObjectOutputStream out) throws IOException
    {
	out.writeObject(this.cmd);
	out.writeObject(this.data);
    }

    /**
     * Charge les données à partir d'une chaine serialize
     **/
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {   
	setCmd((NetworkCmd)in.readObject());
	
	try
	{
	    data = (ArrayList<Object>)in.readObject();
	}
	catch (ClassNotFoundException e)
	{
	    throw e;
	}
    }


    public String toString()
    {
	return cmd.toString();
    }
}
