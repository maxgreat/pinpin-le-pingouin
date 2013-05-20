package Arbitre;
import Joueurs.*;
import java.io.*;

public class Case implements Cloneable, Serializable
{
    protected Etat e;
    protected Joueur joueurSurCase;

    /**
     * Une case est caractérisé par son état et
     * Si un joueur l'occupe
     **/
    public Case(Etat e, Joueur joueurSurCase)
    {
        this.e = e;
        this.joueurSurCase = joueurSurCase;
    }

    public Joueur getJoueurSurCase()
    {
        return joueurSurCase;
    }

    public void setJoueurSurCase(Joueur joueurSurCase)
    {
        this.joueurSurCase = joueurSurCase;
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
        return estVide() || joueurSurCase != null;
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
        return new Case(e, joueurSurCase);
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
        Joueur joueur = null;
        int position = in.readInt();

        if (position != 0)
            joueur = ArbitreManager.instance.getJoueurParPosition(position);

        setJoueurSurCase(joueur);

        setEtat((Etat)in.readObject());
    }

    /**
     * Essaye de parser un objet sans donnée
     **/
    private void readObjectNoData() throws ObjectStreamException
    {
        throw new NotSerializableException("La sérialization d'une case doit se faire sur une chaine non vide");
    }
}
