package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import java.util.*;

public class JoueurCPUPhaseInter extends Joueur {
      	
	JoueurCPURandomizable cpuRdizable;
	Joueur player;

	public JoueurCPUPhaseInter() {
		this.cpuRdizable = new JoueurCPURandomizable(this);
		this.player = this;
	}

	public JoueurCPUPhaseInter(Joueur j) {
		this.cpuRdizable = new JoueurCPURandomizable(j);
		this.player = j;
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
			return cpuFacile.coupSuivant();
		} else {
			Configuration c = ArbitreManager.instance.getConfiguration();	
			Boolean finish = true;
			Couple [] pingouins = c.coordPingouins(this.player);
			for (int i = 0; i < pingouins.length && finish; i++) {
				Couple couple = c.estIlot(pingouins[i].getX(), pingouins[i].getY(), new ArrayList<Couple>());
				if (couple.getX() == -1) 
					finish = false;
			}
			if (finish) 
				return this.cpuRdizable.coupSuivant();
			/**
			 * Phase centrale
			 * Utilisation du MinimaxIncremental
			 **/
			JoueurCPUMinimaxIncremental cpuMinimax = new JoueurCPUMinimaxIncremental(this.player, false, 1000);
			Coup coup = cpuMinimax.coupSuivant();
			return coup;
		}
	}
}
