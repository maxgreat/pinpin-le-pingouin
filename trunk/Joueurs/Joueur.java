package Joueurs;
import Arbitre.*;

public abstract class Joueur
{
    Signal<Coup> signalCoup;
    
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
    public static Joueur getJoueurByName(String nom)
    {
        if (nom.compareTo("CPU_Minimax") == 0)
            return new JoueurCPUMinimax();
        else if(nom.compareTo("CPU_Random") == 0)
            return new JoueurCPURd();
        else 
            return new JoueurHumain();
    }
    
    /**
     * Fonction à implanter dans les classes enfants
     **/
    public abstract Coup coupSuivant();
    public abstract String getNom();
}
