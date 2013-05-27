package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import java.util.Random;

public class JoueurCPUPhase extends Joueur {

	public static String getType() {
		return "CPU_Phase";
	}
	
	public Coup coupSuivant() {
		if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN) {
			JoueurCPUFacile cpuFacile = new JoueurCPUFacile();
			cpuFacile.setScore(this.getScore());
			cpuFacile.setNombreTuile(this.getNombreTuile());
			return cpuFacile.coupSuivant();
		} else {
			JoueurCPUMinimaxIncremental cpuMinimax = new JoueurCPUMinimaxIncremental(this);
			cpuMinimax.setScore(this.getScore());
			cpuMinimax.setNombreTuile(this.getNombreTuile());
			return cpuMinimax.coupSuivant();
		}
	}
}
