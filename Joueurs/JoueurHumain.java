package Joueurs;
import Arbitre.*;

public class JoueurHumain extends Joueur
{
    public JoueurHumain()
    {
        super();
    }

    /**
     * Attend de recevoir le coup depuis l'IHM et renvoi
     **/
    public Coup coupSuivant()
    {
        return (Coup)getSignalCoup().attendreSignal();        
    }

    public static String getType()
    {
        return "Humain";
    }            
}
