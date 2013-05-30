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
	static final long TEMPS_ATTENTE_MAXIMAL = 1000;
	
	Joueur j;
	Boolean finish;

	public JoueurCPUMinimaxIncremental() {
		super();
		this.j = this;
		this.finish = false;
	}
	
	public JoueurCPUMinimaxIncremental(Joueur j, Boolean finish) {
		super();
		this.j = j;
		this.finish = finish;
	}
	
	public static String getType()
	{
		return "CPU_MinimaxIncremental";
	}

	public Coup coupSuivant()
	{
		// Phase de placement
		if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN) {
			Random r = new Random();
			Coup [] placementPossible = ArbitreManager.instance.getConfiguration().toutPlacementsPossibles();
			return placementPossible[r.nextInt(placementPossible.length)];
		}
		
		// Récupérer temps départ
		long startMilli = System.currentTimeMillis();
		// coup = Lancement minimax profondeur 1 (thread)
		Minimax mini  = new Minimax(j, 1, ArbitreManager.instance, this.finish);
		Thread t = new Thread(mini);
		t.start();

		Coup coup = mini.getSignalCoup().attendreSignal();
		int pas = 2;

		// Demande de stop forcé
		if (coup == null) {
	       		t.interrupt();
			return null;
		}
		
		// tant que tempsCourant - tempsDepart < TEMPS_ATTENTE_MAXIMAL
		while (pas < 100 && System.currentTimeMillis() - startMilli < TEMPS_ATTENTE_MAXIMAL) {
			if (ArbitreManager.instance.getForceStop()) {
		       		return null;
			}
			
			MinimaxIncremental miniI = new MinimaxIncremental(j, pas, ArbitreManager.instance, this.finish);
			t = new Thread(miniI);
			try {
				t.start();
				Thread.sleep(TEMPS_ATTENTE_MAXIMAL - (System.currentTimeMillis() - startMilli));
				try {
					t.interrupt();
					t.join();
				}
				catch (InterruptedException ep) {}
				break;
			}
			catch (InterruptedException e) {	
				if (ArbitreManager.instance.getForceStop()) {
					t.interrupt();
					try {
						t.join();
					}
					catch(InterruptedException ep) {}
					return null;
				}	
				
				System.out.println("Attends coup");
				Coup tmp = miniI.getSignalCoup().attendreSignal();
				if (tmp != null)
					coup = tmp;
				System.out.println("Coup attendu : " + coup);
				try {
					t.join();
				}
				catch (InterruptedException ep) {}
				
				// Demande de stop forcé
				if (coup == null) {
					t.interrupt();
					return null;
				}
				pas++;
			}
		}
		
		long stopMilli = System.currentTimeMillis();
		
		try {
			if (stopMilli - startMilli < TEMPS_ATTENTE_MINIMAL)
				Thread.sleep(TEMPS_ATTENTE_MINIMAL - (stopMilli - startMilli));
		}
		catch (InterruptedException e) {
			// Stop forcé, renvoie null
			return null;
		}
		return coup;
	}
}








