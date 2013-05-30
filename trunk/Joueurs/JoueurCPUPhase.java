package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import java.util.*;

public class JoueurCPUPhase extends Joueur {
      	
	JoueurCPURandomizable cpuRdizable;

	public JoueurCPUPhase() {
		this.cpuRdizable = new JoueurCPURandomizable(this);
	}

	public static String getType() {
		return "CPU_Phase";
	}
	
	public Coup coupSuivant() {
		if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN) {
			/**
			 * Phase de placement
			 * Utilisation du CPUFacile
			 **/
			JoueurCPUFacile cpuFacile = new JoueurCPUFacile();
			cpuFacile.setScore(this.getScore());
			cpuFacile.setNombreTuile(this.getNombreTuile());
			return cpuFacile.coupSuivant();
		} else {
			Configuration c = ArbitreManager.instance.getConfiguration();	
			Boolean finish = true;
			Couple [] pingouins = c.coordPingouins(this);
			for (int i = 0; i < pingouins.length && finish; i++) {
				Couple couple = c.estIlot(pingouins[i].getX(), pingouins[i].getY(), new ArrayList<Couple>());
				if (couple.getX() == -1)
					finish = false;
			}
			if (finish) {
				System.out.println("Randomizable");
				return this.cpuRdizable.coupSuivant();
			}
			/**
			 * Phase centrale
			 * Utilisation du MinimaxIncremental
			 **/
			JoueurCPUMinimaxIncremental cpuMinimax = new JoueurCPUMinimaxIncremental(this, false);
			Coup coup = cpuMinimax.coupSuivant();
			System.out.println(coup);
			return coup;
		}
	}
}
