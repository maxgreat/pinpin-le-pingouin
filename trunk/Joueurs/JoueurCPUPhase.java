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
			/**
			 * Phase de placement
			 * Utilisation du CPUFacile
			 **/
			System.out.println("Utilisation JoueurCPUFacile");
			JoueurCPUFacile cpuFacile = new JoueurCPUFacile();
			cpuFacile.setScore(this.getScore());
			cpuFacile.setNombreTuile(this.getNombreTuile());
			return cpuFacile.coupSuivant();
		} else {
			Joueur adversaire;
			if (ArbitreManager.instance.getPosition(this) == 1) 
				adversaire = ArbitreManager.instance.getJoueurParPosition(2);
			else
				adversaire = ArbitreManager.instance.getJoueurParPosition(1);
			Configuration c = ArbitreManager.instance.getConfiguration();	
			int [] nbPingRest = c.getNombrePingouinsDispoParJoueur(ArbitreManager.instance.getJoueurs());
			if (nbPingRest[ArbitreManager.instance.getPosition(adversaire) - 1] < 1) {
				/**
				 * Phase de terminaison
				 * Utilisation d'un algo de chemin optimale pour recuperer le plus de poisson
				 **/
				/*System.out.println("Utilisation JoueurCPUOptimal");
				JoueurCPUOptimal jCPUOpt = new JoueurCPUOptimal();
				return jCPUOpt.coupSuivant();*/
				System.out.println("Utilisation JoueurCPUMinimaxIncremental Finish : TRUE");
				JoueurCPUMinimaxIncremental cpuMinimax = new JoueurCPUMinimaxIncremental(this, true);
				return cpuMinimax.coupSuivant();
			}
			/**
			 * Phase centrale
			 * Utilisation du MinimaxIncremental
			 **/
			System.out.println("Utilisation JoueurCPUMinimaxIncremental Finish : FALSE");
			JoueurCPUMinimaxIncremental cpuMinimax = new JoueurCPUMinimaxIncremental(this, false);
			Coup coup = cpuMinimax.coupSuivant();
			System.out.println(coup);
			return coup;
		}
	}
}
