package Joueurs;

import Arbitre.*;
import Arbitre.Regles.*;

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
        return getSignalCoup().attendreSignal();        
    }

    public static String getType()
    {
        return "Humain";
    }            
}
