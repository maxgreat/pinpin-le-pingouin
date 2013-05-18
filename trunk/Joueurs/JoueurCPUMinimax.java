package Joueurs;
import Arbitre.*;
import java.util.Random;

public class JoueurCPUMinimax extends Joueur
{
    /**
     * Temps d'attente minimal en seconde
     * 2 secondes
     **/
    static final long TEMPS_ATTENTE_MINIMAL = 2000; 

    public static String getType()
    {
        return "CPU_Minimax";
    }

    public Coup coupSuivant()
    {
        // Créé l'algo minimax
        Minimax mini  = new Minimax(this);

        Thread t = new Thread(mini);
        long startMilli = System.currentTimeMillis();
        t.start();

        Coup c = mini.getSignalCoup().attendreSignal();

        // Demande de stop forcé
        if (c == null)
        {
            t.interrupt();
            return null;
        }
        long stopMilli = System.currentTimeMillis();
	    
        try
        {
            if (stopMilli - startMilli < JoueurCPUMinimax.TEMPS_ATTENTE_MINIMAL)
                Thread.sleep(JoueurCPUMinimax.TEMPS_ATTENTE_MINIMAL - (stopMilli - startMilli));
        }
        catch (InterruptedException e)
        {
            // Stop forcé, renvoie null
            return null;
        }

        return c;
    }
}








