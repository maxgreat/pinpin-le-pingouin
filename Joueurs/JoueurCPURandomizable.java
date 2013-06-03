package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import java.util.*;

public class JoueurCPURandomizable extends Joueur
{    
    /**
     * Lance une sequence de chemin aléatoire et prend le mielleur
     **/

	ArrayList<Coup> path;
	Joueur joueur;

	public JoueurCPURandomizable(Joueur joueur) {
		this.path = new ArrayList<Coup>();
		this.joueur = joueur;
	}

	public void initChemin() {
		int caseRestantes = 0;
		Configuration c = ArbitreManager.instance.getConfiguration();	
		Couple [] pingouins = c.coordPingouins(this.joueur);
		ArrayList<Couple> liste = new ArrayList<Couple>();
		Couple couple = c.estIlot(pingouins[0].getX(), pingouins[0].getY(), liste);
		liste.add(new Couple(pingouins[0].getX(), pingouins[0].getY()));
		caseRestantes += couple.getY();
		for (int i = 1; i < pingouins.length; i++) {
			couple = c.estIlot(pingouins[i].getX(), pingouins[i].getY(), liste);
			if (couple.getX() != -1)
				caseRestantes += couple.getY();	
			liste.add(new Couple(pingouins[i].getX(), pingouins[i].getY()));
		}
		Random r = new Random();
		int max = Integer.MIN_VALUE;
		ArrayList<Coup> chemin = new ArrayList<Coup>();
		for (int i = 0; i < 200000 && !Thread.currentThread().isInterrupted(); i++) {
			chemin.clear();
			Configuration cl = c.clone();
			Coup [] coupPossible = cl.toutCoupsPossibles();
			int nbCoups = 4;
			while (coupPossible.length > 0) {
				int a = r.nextInt(coupPossible.length);
				cl.effectuerCoup(coupPossible[a]);
				nbCoups++;
				chemin.add(coupPossible[a]);
				coupPossible = cl.toutCoupsPossibles();
			}
			if (nbCoups > max) {
				max = nbCoups;
				this.path = (ArrayList<Coup>) chemin.clone();
				if (nbCoups >= caseRestantes) {
					break;
				}
			}
		}
	}
	
	public Coup coupSuivant() {
		Random r = new Random();
		// Phase de placement
		if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN) {
			Coup [] placementPossible = ArbitreManager.instance.getConfiguration().toutPlacementsPossibles();
			return placementPossible[r.nextInt(placementPossible.length)];
		}
		// Phase de jeu
		if (this.path.isEmpty()) 
			initChemin();
		try
			{
				Thread.sleep(500);
			}
		catch (InterruptedException e)
			{
				return null;
			}
		return this.path.remove(0);
	}
	
	public static String getType() {
		return "CPU_Randomizable";
	}
}
