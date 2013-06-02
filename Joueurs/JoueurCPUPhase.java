package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import java.util.*;

public class JoueurCPUPhase extends Joueur {
      	
	JoueurCPURandomizable cpuRdizable;
	Joueur player;

	public JoueurCPUPhase() {
		this.cpuRdizable = new JoueurCPURandomizable(this);
		this.player = this;
	}

	public JoueurCPUPhase(Joueur j) {
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
			System.out.println("Facile");
			JoueurCPUFacile cpuFacile = new JoueurCPUFacile();
			return cpuFacile.coupSuivant();
		} else {
			Configuration c = ArbitreManager.instance.getConfiguration();	
			Boolean finish = true;
			Couple [] pingouins = c.coordPingouins(this.player);
			for (int i = 0; i < pingouins.length && finish; i++) {
				Couple couple = c.estIlot(pingouins[i].getX(), pingouins[i].getY(), new ArrayList<Couple>());
				if (couple.getX() == -1) {
					finish = false;
					System.out.println("estIlot PAS : " + pingouins[i].getX() + "," + pingouins[i].getY());
				} else
					System.out.println("estIlot OK : " + pingouins[i].getX() + "," + pingouins[i].getY());
			}
			if (finish) {
				System.out.println("Randomizable");
				return this.cpuRdizable.coupSuivant();
			}
			/**
			 * Phase centrale
			 * Utilisation du MinimaxIncremental
			 **/
			System.out.println("Incremental");
			JoueurCPUMinimaxIncremental cpuMinimax = new JoueurCPUMinimaxIncremental(this.player, false);
			Coup coup = cpuMinimax.coupSuivant();
			System.out.println(coup);
			return coup;
		}
	}
}
