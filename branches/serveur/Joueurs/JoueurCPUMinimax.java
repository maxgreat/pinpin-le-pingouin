package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import java.util.Random;

public class JoueurCPUMinimax extends Joueur {
	/**
	 * Temps d'attente minimal en seconde
	 * 5 secondes
	 **/
	static final long TEMPS_ATTENTE_MINIMAL = 1000; 

	Joueur player;
	
	public JoueurCPUMinimax() {
		this. player = this;
	}

	public JoueurCPUMinimax(Joueur j) {
		this. player = j;
	}

	public static String getType() {
		return "CPU_Minimax";
	}

	public Coup coupSuivant() {
		// Phase de placement
		if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN) {
			Random r = new Random();
			Coup [] placementPossible = ArbitreManager.instance.getConfiguration().toutPlacementsPossibles();
			return placementPossible[r.nextInt(placementPossible.length)];
		}
		// Créé l'algo minimax
		Minimax mini  = new Minimax(this.player, 1, ArbitreManager.instance, false);
	    
		Thread t = new Thread(mini);
		long startMilli = System.currentTimeMillis();
		t.start();
	    
		Coup c = mini.getSignalCoup().attendreSignal();
		System.out.println(c);
		// Demande de stop forcé
		if (c == null) {
			t.interrupt();
			return null;
		}
		long stopMilli = System.currentTimeMillis();
	    
		try {
			if (stopMilli - startMilli < JoueurCPUMinimax.TEMPS_ATTENTE_MINIMAL)
				Thread.sleep(JoueurCPUMinimax.TEMPS_ATTENTE_MINIMAL - (stopMilli - startMilli));
		} catch (InterruptedException e) {
			// Stop forcé, renvoie null
			return null;
		}
		return c;
	}
}








