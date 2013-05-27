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
	
	Joueur j;

	public JoueurCPUMinimaxIncremental() {
		super();
		this.j = this;
	}
	
	public JoueurCPUMinimaxIncremental(Joueur j) {
		super();
		this.j = j;
	}
	
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
	Minimax mini  = new Minimax(j, 1, ArbitreManager.instance);
	Thread t = new Thread(mini);
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
	    if (ArbitreManager.instance.getForceStop())
	    {
		return null;
	    }

	    MinimaxIncremental miniI = new MinimaxIncremental(j, pas, ArbitreManager.instance);
	    t = new Thread(miniI);
	    try
	    {
		t.start();
		Thread.sleep(TEMPS_ATTENTE_MAXIMAL - (System.currentTimeMillis() - startMilli));
		try
		{
		    t.interrupt();
		    t.join();
		}
		catch (InterruptedException ep)
		{
		 
		}

		break;
	    }
	    catch (InterruptedException e)
	    {	
		if (ArbitreManager.instance.getForceStop())
		{
		    t.interrupt();
		    try
		    {
			t.join();
		    }
		    catch(InterruptedException ep)
		    {
		    }

		    return null;
		}	

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
	    if (stopMilli - startMilli < TEMPS_ATTENTE_MINIMAL)
		Thread.sleep(TEMPS_ATTENTE_MINIMAL - (stopMilli - startMilli));
	}
	catch (InterruptedException e)
	{
	    // Stop forcé, renvoie null
	    return null;
	}

        return coup;
    }
}








