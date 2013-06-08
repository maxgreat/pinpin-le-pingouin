package Arbitre.Regles;

import Arbitre.*;
import Joueurs.*;
import java.io.*;

public class Case implements Cloneable, Serializable
{
    protected Etat e;
    protected int position;
    public static final long serialVersionUID = -1656867859518053793L;

    /**
     * Une case est caractérisé par son état et
     * Si un joueur l'occupe
     **/
    public Case(Etat e, Joueur joueurSurCase)
    {
        this.e = e;
        if (joueurSurCase == null)
            this.position = 0;
        else
            this.position = ArbitreManager.instance.getPosition(joueurSurCase);
    }

    public Joueur getJoueurSurCase()
    {
        if (this.position == 0)
            return null;

        return ArbitreManager.instance.getJoueurParPosition(position);
    }

    public void setJoueurSurCase(Joueur joueurSurCase)
    {
        if (joueurSurCase == null)
            this.position = 0;
        else
            this.position = ArbitreManager.instance.getPosition(joueurSurCase);
    }

    public Etat getEtat()
    {
        return e;
    }

    public Etat setEtat(Etat e)
    {
        return this.e = e;
    }

    /**
     * Retourne si une case est vide
     **/
    public boolean estVide()
    {
        return e == Etat.VIDE;
    }

    /**
     * Retourne si une case est un obstacle
     **/
    public boolean estObstacle()
    {
        return estVide() || getJoueurSurCase() != null;
    }

    /**
     * Score d'une case suivant le nombre de poisson ou vide
     **/
    public int scorePoisson()
    {
        switch(e)
        {
        case UN_POISSON:
            return 1;
        case DEUX_POISSONS:
            return 2;
        case TROIS_POISSONS:
            return 3;
        case VIDE:
        default:
            return 0;
        }
    }

    public Case clone()
    {
        return new Case(e, getJoueurSurCase());
    }

    /**
     * Serialize les données du terrain
     **/
    private void writeObject(ObjectOutputStream out) throws IOException
    {
        Joueur joueur = getJoueurSurCase();
        
        if (joueur == null)
            out.writeInt(0);
        else
            out.writeInt(ArbitreManager.instance.getPosition(joueur));
        
        out.writeObject(getEtat());
    }

    /**
     * Charge les données à partir d'une chaine serialize
     **/
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    { 
        int position = in.readInt();
        Etat e = (Etat)in.readObject();

        this.position = position;
        setEtat(e);
    }

    /**
     * Essaye de parser un objet sans donnée
     **/
    private void readObjectNoData() throws ObjectStreamException
    {
        throw new NotSerializableException("La sérialization d'une case doit se faire sur une chaine non vide");
    }
}
