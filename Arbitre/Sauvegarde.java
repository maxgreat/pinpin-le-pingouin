package Arbitre;

import java.util.*;
import java.io.*;

public class Sauvegarde
{

    protected Arbitre arbitre;

    /**
     * Retour l'arbitre serializé contenant les données à écrire
     **/
    public String getXml()
    {
        try
        {
            ByteArrayOutputStream outByte = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outByte);
            out.writeObject(this.arbitre);
            
            return outByte.toString();
        }
        catch (IOException e)
        {
            System.out.println(e);
            return null;
        }
    }
    
    /**
     * Lis une chaine serialisée et retourne l'arbitre correspondant
     **/
    public Arbitre readXml(ObjectInputStream in)
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
