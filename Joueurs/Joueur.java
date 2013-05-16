package Joueurs;
import Arbitre.*;

public abstract class Joueur
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
     * Fonction à implanter dans les classes enfants
     **/
    public abstract Coup coupSuivant();
    // Ne peut pas déclarer une méthode abstraite et static en Java, dommage
    // public abstract static String getType();
}
