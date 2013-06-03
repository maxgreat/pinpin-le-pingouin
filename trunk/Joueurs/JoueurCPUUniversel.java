package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import java.util.Random;

public class JoueurCPUUniversel extends Joueur
{    
    /**
     * Apeller toujours ce joueur mais avec un niveau different
     **/
	
	/** Defini le niveau du CPU
	 * 1 : Difficile 
	 * 2 : Intermédiaire
	 * 3 : Facile
	 **/
	int level;
	JoueurCPUPhase cpuDiff;
	JoueurCPUPhaseInter cpuInter;
	JoueurCPUFacile cpuFacile;
	
	public JoueurCPUUniversel(int level) {
		this.level = level;
		this.cpuDiff = new JoueurCPUPhase(this);
		this.cpuInter = new JoueurCPUPhaseInter(this);
		this.cpuFacile = new JoueurCPUFacile();
	}

	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public Coup coupSuivant() {
		switch (this.level) {
		case 1: // Difficile
			return cpuDiff.coupSuivant();
		case 2: // Intermédiaire
			return cpuInter.coupSuivant();
		case 3: // Facile
			return cpuFacile.coupSuivant();
		}
		return null;
	}
	
	public static String getType()	{
		return "CPU_Universel";
	}
}
