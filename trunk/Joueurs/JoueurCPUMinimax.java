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

    public String getNom()
    {
        return "CPU_Minimax";
    }

    public Coup coupSuivant()
    {
	if(getCoupVainqueur()!=null)
	{
	    return getCoupVainqueur();
	}
	else
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
		t.stop();
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


    public Coup getCoupVainqueur()
    {
	Coup [] coupPossible = ArbitreManager.instance.getConfiguration().toutCoupsPossibles();
	boolean trouve = false;
	int i=0;

	while(i < coupPossible.length && !trouve)
	{
	    trouve = true;
	    for(int j = 0; j < coupPossible.length; j++)
	    {
		if(coupPossible[j].getX() < coupPossible[i].getX() || coupPossible[j].getY() > coupPossible[i].getY())
		    trouve = false;
	    }
	    i++;
	}
	if (trouve) 
	{
	    return coupPossible[i-1];
	}
	return null;
    }
}








