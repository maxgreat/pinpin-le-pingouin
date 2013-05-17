package Joueurs;
import Arbitre.*;
import java.util.Random;

public class JoueurCPUFacile extends Joueur
{    
    /**
     * Choisi une des cases qui contient le plus de poisson
     **/
	public Coup coupSuivant()
    {
		Coup [] coupPossible = ArbitreManager.instance.getConfiguration().toutCoupsPossibles();
		Case [][] terrain = ArbitreManager.instance.getConfiguration().getTerrain();
		Case tmp;
		int max = 0, imax = -1;
		
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

    public static String getType()
    {
        return "CPU_Facile";
    }
}
