package Joueurs;
import Arbitre.*;
import java.util.Random;

public class JoueurCPURd extends Joueur
{    
    /**
     * Choisi simplement un coup au hasard
     **/
	public Coup coupSuivant()
    {
		Coup [] coupPossible = ArbitreManager.instance.getConfiguration().toutCoupsPossibles();
		Random r = new Random();
		return coupPossible[r.nextInt(coupPossible.length)];
	}

    public static String getType()
    {
        return "CPU_Random";
    }
}
