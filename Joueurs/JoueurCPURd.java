package Joueurs;
import Arbitre.*;
import java.util.Random;

public class JoueurCPURd extends Joueur
{    
    /**
     * Choisi simplement un coup au hasard
     **/
	public Coup coupSuivant() {
		Random r = new Random();
		// Phase de placement
		if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN) {
			Coup [] placementPossible = ArbitreManager.instance.getConfiguration().toutPlacementPossible();
			return placementPossible[r.nextInt(placementPossible.length)];
		}
		// Phase de jeu
		Coup [] coupPossible = ArbitreManager.instance.getConfiguration().toutCoupsPossibles();
		return coupPossible[r.nextInt(coupPossible.length)];
	}
	
    public static String getType()
    {
        return "CPU_Random";
    }
}
