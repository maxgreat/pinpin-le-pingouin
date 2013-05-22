package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import java.util.Random;

public class JoueurCPUMinimaxIncremental extends Joueur
{
    /**
     * Temps d'attente en seconde
     **/
    static final long TEMPS_ATTENTE_MINIMAL = 1000; 
    static final long TEMPS_ATTENTE_MAXIMAL = 5000;

    public static String getType()
    {
        return "CPU_MinimaxIncremental";
    }

    public Coup coupSuivant()
    {
	// Phase de placement
	if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN) 
	{
	    Random r = new Random();
	    Coup [] placementPossible = ArbitreManager.instance.getConfiguration().toutPlacementsPossibles();
	    return placementPossible[r.nextInt(placementPossible.length)];
	}

	// Récupérer temps départ
	long startMilli = System.currentTimeMillis();
	// coup = Lancement minimax profondeur 1 (thread)
	Minimax mini  = new Minimax(this, 1, ArbitreManager.instance);
	Thread t = new Thread(mini);
	System.out.println("Lance un coup pour une profondeur de 1");
	t.start();

	Coup coup = mini.getSignalCoup().attendreSignal();
	int pas = 2;

	// Demande de stop forcé
	if (coup == null)
	{
	    t.interrupt();
	    return null;
	}
	
	// tant que tempsCourant - tempsDepart < TEMPS_ATTENTE_MAXIMAL
	while (pas < 100 && System.currentTimeMillis() - startMilli < TEMPS_ATTENTE_MAXIMAL)
	{
	    System.out.println("Lance un coup pour une profondeur de "+pas);
	    MinimaxIncremental miniI = new MinimaxIncremental(this, pas, ArbitreManager.instance);
	    t = new Thread(miniI);
	    try
	    {
		t.start();
		Thread.sleep(TEMPS_ATTENTE_MAXIMAL - (System.currentTimeMillis() - startMilli));
		try
		{
		    t.stop();
		    t.join();
		}
		catch (ThreadDeath e)
		{
		    System.out.println("ThreadDeath");
		}

		break;
	    }
	    catch (InterruptedException e)
	    {		
		synchronized(miniI.getSignalSynchro())
		{
		    miniI.getSignalSynchro().envoyerSignal();
		}

		coup = miniI.getSignalCoup().attendreSignal();

		try
		{
		    t.join();
		}
		catch (InterruptedException ep)
		{
		}

		// Demande de stop forcé
		if (coup == null)
		{
		    t.interrupt();
		    return null;
		}

		pas++;
	    }
	}

	long stopMilli = System.currentTimeMillis();

	try
	{
	    if (stopMilli - startMilli < JoueurCPUMinimax.TEMPS_ATTENTE_MINIMAL)
		Thread.sleep(JoueurCPUMinimax.TEMPS_ATTENTE_MINIMAL - (stopMilli - startMilli));
	}
	catch (InterruptedException e)
	{
	    System.out.println("toto");
	    // Stop forcé, renvoie null
	    return null;
	}
	    
        return coup;
    }
}








