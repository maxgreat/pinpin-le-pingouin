package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import java.util.Random;

public class JoueurCPUFacile extends Joueur {    
	/**
	 * Phase de placement : choisi la case dont la somme des poissons des cases adjacentes est la plus élevée
	 * Phase de jeu : choisi une des cases qui contient le plus de poisson
	 **/
	public Coup coupSuivant() {
		Case [][] terrain = ArbitreManager.instance.getConfiguration().getTerrain();
		int largeur = ArbitreManager.instance.getConfiguration().getLargeur();
		int hauteur = ArbitreManager.instance.getConfiguration().getHauteur();
		int max = 0, imax = -1;
		// Phase de placement
		if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN) {
			Coup [] placementPossible = ArbitreManager.instance.getConfiguration().toutPlacementsPossibles();
			int score = 0, ci, cj;
			for (int i = 0; i < placementPossible.length; i++) {
				score = 0;
				ci = placementPossible[i].getXDepart();
				cj = placementPossible[i].getYDepart();
				if (cj%2 == 0) {
					if (ci < largeur - 2)
						score += terrain[cj][ci+1].scorePoisson();
					if (cj > 1) {
						score += terrain[cj-1][ci+1].scorePoisson();
						score += terrain[cj-1][ci].scorePoisson();
					}
					if (ci > 0)
						score += terrain[cj][ci-1].scorePoisson();
					score += terrain[cj+1][ci].scorePoisson();
					score += terrain[cj+1][ci+1].scorePoisson();
				} else {
					if (ci < largeur - 1) {
						score += terrain[cj][ci+1].scorePoisson();
						score += terrain[cj-1][ci].scorePoisson();
					}
					if (ci > 0) {
						score += terrain[cj-1][ci-1].scorePoisson();	
						score += terrain[cj][ci-1].scorePoisson();
					}
					if (cj < hauteur - 2 && ci > 0)
						score += terrain[cj+1][ci-1].scorePoisson();	
					if (cj < hauteur - 2 && ci < largeur - 1)
						score += terrain[cj+1][ci].scorePoisson();	
				}
				if (score > max) {
					max = score;
					imax = i;
				}
			}
			return placementPossible[imax];
		}
		// Phase de jeu
		Coup [] coupPossible = ArbitreManager.instance.getConfiguration().toutCoupsPossibles();		
		Case tmp;
		for (int i = 0; i < coupPossible.length; i++) {
			tmp = terrain[coupPossible[i].getYArrivee()][coupPossible[i].getXArrivee()];
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
