package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import Utilitaires.*;
import java.util.*;
import java.awt.*;


public class Minimax implements Runnable {
	Joueur joueur;
	Joueur adversaire;
	int profondeur;
	Arbitre arbitre;
	Signal<Coup> signalCoup;

	public Minimax(Joueur joueur, int profondeur, Arbitre arbitre) {
		this.joueur = joueur;
		if (arbitre.getPosition(this.joueur) == 1) 
			this.adversaire = arbitre.getJoueurParPosition(2);
		else
			this.adversaire = arbitre.getJoueurParPosition(1);
		this.arbitre = arbitre;
		this.profondeur = 2 * profondeur;
		this.signalCoup = new Signal<Coup>();
	}

	public Signal<Coup> getSignalCoup() {
		return signalCoup;
	}
	
	public void run() {
		getSignalCoup().envoyerSignal(minimax(ArbitreManager.instance.getConfiguration().clone()));
	}
	
	public Coup minimax(Configuration cc) {
		Coup [] coupPossible = cc.toutCoupsPossibles();
		int max= Integer.MIN_VALUE,tmp,maxi=-1, score = 0;
		Configuration cl;
		int [] nbPingouinsRestants = cc.getNombrePingouinsDispoParJoueur(arbitre.getJoueurs());

		/*		int [] ppj = cl.getNombrePingouinsDispoParJoueur(arbitre.getJoueurs());
				int nbPingouins = ppj[this.arbitre.getPosition(this.adversaire)-1];*/
		ArrayList<Point> poissonIlot = new ArrayList<Point>();
		int h=-1,l=-1;
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			if(h!=coupPossible[i].getYDepart() || l!=coupPossible[i].getXDepart()){
				h=coupPossible[i].getYDepart();
				l=coupPossible[i].getXDepart();
				if(cc.estIlot(h,l))
					poissonIlot.add(new Point(h,l));
			}
		}

		boolean recommencer = false;
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){	

			if(!poissonIlot.contains(new Point(coupPossible[i].getYDepart(),coupPossible[i].getXDepart())) || recommencer){
				cl = cc.clone();

				score += cl.effectuerCoup(coupPossible[i]);
				tmp = Min(cl, max, this.profondeur,nbPingouinsRestants,score);
				if(tmp > max){
					max = tmp;
					maxi = i;
				}
			}
			if(maxi==-1 && i==coupPossible.length-1){
				maxi=0;
			}
		}	

		return coupPossible[maxi];
	}

	public int Min(Configuration cc, int max, int profondeur, int [] nbPingouinsRestants, int s) {
		Configuration clcc = cc.clone();
		clcc.setJoueurSurConfiguration(this.adversaire);
		Coup [] coupPossible = clcc.toutCoupsPossibles();
		int min = Integer.MAX_VALUE, tmp, score;
		Configuration cl;
		
		if(coupPossible.length == 0 ) 
			return eval(clcc, nbPingouinsRestants, s);
		else if (profondeur < 1)
			return eval(clcc, nbPingouinsRestants, s+coupPossible.length);
				
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			cl = clcc.clone();
			score = cl.effectuerCoup(coupPossible[i]);
			tmp = Max(cl, min, profondeur - 1, nbPingouinsRestants, score + s);
			if(tmp < min){
				min = tmp;
			}
			if (min < max)
				break;
		}

		return min;		
	}


	public int Max(Configuration cc, int min, int profondeur, int [] nbPingouinsRestants, int s)
	{
		Configuration clcc = cc.clone();
		clcc.setJoueurSurConfiguration(joueur);
		Coup [] coupPossible = clcc.toutCoupsPossibles();
		int max = Integer.MIN_VALUE, tmp, score;
		Configuration cl;

		if(coupPossible.length == 0 ) 
			return -eval(clcc, nbPingouinsRestants, s);
		else if (profondeur < 1)
			return eval(clcc, nbPingouinsRestants, s+coupPossible.length);
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			cl = clcc.clone();
			score = cl.effectuerCoup(coupPossible[i]);
			tmp = Min(cl, max, profondeur-1,nbPingouinsRestants, score + s);
			if(tmp > max){
				max = tmp;
			}
			if (min < max)
				break;
		}
		return max;		
	}

	public int eval(Configuration c, int [] nbPingouinsRestants, int s){
		// Nombre de poissons
		int score = s;
		
		// Pingouins bloqués
		int [] newNbPingouinsRestants = c.getNombrePingouinsDispoParJoueur(arbitre.getJoueurs());
		int numJ = arbitre.getPosition(this.joueur) - 1;
		int numA = arbitre.getPosition(this.adversaire) - 1;
		if (newNbPingouinsRestants[numA] < nbPingouinsRestants[numA])
			score += 200;
		if (newNbPingouinsRestants[numJ] < nbPingouinsRestants[numJ])
			score -= 200;

		
		return score;
	}
}
