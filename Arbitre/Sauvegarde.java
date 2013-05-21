package Arbitre;

import java.util.*;
import java.io.*;

public class Sauvegarde
{

    protected Arbitre arbitre;

    /**
     * Retour l'arbitre serializé contenant les données à écrire
     **/
    public void save(FileOutputStream fstream) throws IOException
    {
	ObjectOutputStream out = new ObjectOutputStream(fstream);
	out.writeObject(this.arbitre);
	out.flush();
	out.close();
    }
    
    /**
     * Lis une chaine serialisée et retourne l'arbitre correspondant
     **/
    public Arbitre load(ObjectInputStream in)
    {
        try
        {
            return (Arbitre)in.readObject();
        }
        catch (IOException e)
        {
            System.out.println(e);
            return null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Getter/Setter
     **/
    public void setArbitre(Arbitre arbitre)
    {
        this.arbitre = arbitre;
    }

    public Arbitre getArbitre()
    {
        return arbitre;
    }
}
