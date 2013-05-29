package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import java.util.*;

public class JoueurCPUOptimal extends Joueur {    

	/**
	 *  Prend le plus de poissons possible
	 **/

	public Coup coupSuivant() {
		Configuration c = ArbitreManager.instance.getConfiguration();
		Coup [] coupPossible = c.toutCoupsPossibles();
		int max = -1, maxi = -1, tmp;
		for (int i = 0; i < coupPossible.length; i++) {
			Configuration cl = c.clone();
			tmp = cl.effectuerCoup(coupPossible[i]);
			tmp += calculScore(cl, 5);
			if (tmp > max) {
				max = tmp;
				maxi = i;
			}
		}
		return coupPossible[maxi];
	}
	
	public int calculScore(Configuration c, int lvl) {
		Coup [] coupPossible = c.toutCoupsPossibles();
		if (lvl == 0 || coupPossible.length < 1)
			return 0;
		int max = -1, maxi = -1, tmp;
		for (int i = 0; i < coupPossible.length; i++) {
			Configuration cl = c.clone();
			tmp = cl.effectuerCoup(coupPossible[i]);
			tmp += calculScore(cl, lvl-1);
			if (tmp > max) {
				max = tmp;
				maxi = i;
			}
		}
		return max;
	}

	public static String getType() {
		return "CPU_Optimal";
	}
}
