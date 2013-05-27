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
    public synchronized void write(Object object) throws NotSerializableException
    {
	if (!(object instanceof java.io.Serializable))
	    throw new NotSerializableException("L'objet écrit doit implementé Serializable");

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
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {   
	setCmd((NetworkCmd)in.readObject());
	Object tmp = in.readObject();
	
	if (tmp instanceof ArrayList<?>)
	    data = (ArrayList<Object>)tmp;
	else
	    throw new IOException("Problème manque liste objet");
    }

    /**
     * Essaye de parser un objet sans donnée
     **/
    private void readObjectNoData() throws ObjectStreamException
    {
        throw new NotSerializableException("Aucune commande trouvée");
    }
}
