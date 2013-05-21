package Joueurs;
import Arbitre.*;
import java.io.*;

public abstract class Joueur implements Serializable
{
    protected Signal<Coup> signalCoup;
    protected int score = 0;
    protected String nom;
    
    
    public Joueur()
    {
        // Signal d'attente sur un coup (clic souris si graphique, coordonnée si console, etc.)
        signalCoup = new Signal<Coup>();
    }

    /**
     * Récupère le signal pour les coups
     **/
    public Signal<Coup> getSignalCoup()
    {
        return signalCoup;
    }

    /**
     * Renvoit une instance pour un type de joueur donné
     **/
    public static Joueur getJoueurByType(String type)
    {
        if (type.compareTo(JoueurCPUMinimax.getType()) == 0)
            return new JoueurCPUMinimax();
        else if(type.compareTo(JoueurCPURd.getType()) == 0)
            return new JoueurCPURd();
        else 
            return new JoueurHumain();
    }

    /**
     * Gestion du score
     **/
    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    /**
     * Gestion du nom du joueur
     **/
    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    /**
     * Serialize les données d'une partie
     **/
    private void writeObject(ObjectOutputStream out) throws IOException
    {
	out.writeInt(getScore());
	out.writeObject(getNom());
    }

    /**
     * Charge les données à partir d'une chaine serialize
     **/
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    { 
	setScore(in.readInt());
	setNom((String)in.readObject());
	signalCoup = new Signal<Coup>();
    }

    /**
     * Essaye de parser un objet sans donnée
     **/
    private void readObjectNoData() throws ObjectStreamException
    {
        throw new NotSerializableException("La sérialization d'un joueur doit se faire sur une chaine non vide");
    }


    /**
     * Fonction à implanter dans les classes enfants
     **/
    public abstract Coup coupSuivant();
    // Ne peut pas déclarer une méthode abstraite et static en Java, dommage
    // public abstract static String getType();
}
