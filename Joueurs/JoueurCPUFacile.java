package Joueurs;
import Arbitre.*;
import java.util.Random;

public class JoueurCPUFacile extends Joueur {    
	/**
	 * Choisi une des cases qui contient le plus de poisson
	 **/
	public Coup coupSuivant() {
		Case [][] terrain = ArbitreManager.instance.getConfiguration().getTerrain();
		int max = 0, imax = -1;
		// Phase de placement
		if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN) {
			Coup [] placementPossible = ArbitreManager.instance.getConfiguration().toutPlacementsPossibles();
			
			for (int i = 0; i < placementPossible.length; i++) {
				// Problème tout coup possible depuis une coordonnée donnée
			}
			// return coup
		}
		// Phase de jeu
		Coup [] coupPossible = ArbitreManager.instance.getConfiguration().toutCoupsPossibles();		
		Case tmp;
		
		for (int i = 0; i < coupPossible.length; i++) {
			tmp = terrain[coupPossible[i].getXArrivee()][coupPossible[i].getYArrivee()];
			if (tmp.scorePoisson() > max) {
				imax = i;
				max = tmp.scorePoisson();
				if (max > 2) {
					break;
				}
			}
		}
		return coupPossible[imax];
	}
	
    public static String getType() {
	    return "CPU_Facile";
    }
}
