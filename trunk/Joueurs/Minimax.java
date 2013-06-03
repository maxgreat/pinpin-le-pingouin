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
	boolean finish;

	public Minimax(Joueur joueur, int profondeur, Arbitre arbitre, boolean finish) {
		this.joueur = joueur;
		if (arbitre.getPosition(this.joueur) == 1) 
			this.adversaire = arbitre.getJoueurParPosition(2);
		else
			this.adversaire = arbitre.getJoueurParPosition(1);
		this.arbitre = arbitre;
		this.profondeur = 2 * profondeur;
		this.finish = finish;
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
		int max= Integer.MIN_VALUE,tmp,maxi=-1, score;
		Configuration cl;

		/*		int [] ppj = cl.getNombrePingouinsDispoParJoueur(arbitre.getJoueurs());
				int nbPingouins = ppj[this.arbitre.getPosition(this.adversaire)-1];*/

		ArrayList<Couple> poissonIlot = new ArrayList<Couple>();
		int h=-1,l=-1;
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			if(h!=coupPossible[i].getYDepart() || l!=coupPossible[i].getXDepart()){
				h=coupPossible[i].getYDepart();
				l=coupPossible[i].getXDepart();
				if(cc.estIlot(h,l).getX()!=-1){
					poissonIlot.add(new Couple(h,l));
				}
			}
		}
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){	
			score = 0;
			if(!poissonIlot.contains(new Couple(coupPossible[i].getYDepart(),coupPossible[i].getXDepart()))){
				cl = cc.clone();
				score += cl.getTerrain()[coupPossible[i].getYArrivee()][coupPossible[i].getXArrivee()].scorePoisson();
				cl.effectuerCoup(coupPossible[i]);
				tmp = Min(cl, max, this.profondeur,cc,score);
				if(tmp > max){
					max = tmp;
					maxi = i;
				}
			}
		}	
		if(maxi==-1){
			return coupPossible[0];
		} 
		return coupPossible[maxi];
	}

	public int Min(Configuration cc, int max, int profondeur, Configuration cDeb, int s) {
		Configuration clcc = cc.clone();
		clcc.setJoueurSurConfiguration(this.adversaire);
		Coup [] coupPossible = clcc.toutCoupsPossibles();
		int min = Integer.MAX_VALUE, tmp, score;
		Configuration cl;
		
		if(coupPossible.length == 0 || profondeur < 1) 
			return eval(clcc, cDeb, s);
				
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			score = 0;
			cl = clcc.clone();
			score = cl.getTerrain()[coupPossible[i].getYArrivee()][coupPossible[i].getXArrivee()].scorePoisson();
			cl.effectuerCoup(coupPossible[i]);
			tmp = Max(cl, min, profondeur - 1, cDeb, s);
			if(tmp < min){
				min = tmp;
			}
			if (min < max)
				break;
		}

		return min;		
	}


	public int Max(Configuration cc, int min, int profondeur, Configuration cDeb, int s)
	{
		Configuration clcc = cc.clone();

		Coup [] coupPossible = clcc.toutCoupsPossibles();
		int max = Integer.MIN_VALUE, tmp, score;
		Configuration cl;

		if(coupPossible.length == 0 || profondeur < 1)
			return eval(clcc, cDeb, s);
		
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			score = 0;
			cl = clcc.clone();
			score = cl.getTerrain()[coupPossible[i].getYArrivee()][coupPossible[i].getXArrivee()].scorePoisson();
			cl.effectuerCoup(coupPossible[i]);
			Couple c = new Couple(coupPossible[i].getYArrivee(),coupPossible[i].getXArrivee());
			tmp = Min(cl, max, profondeur-1, cDeb, score + s);
			if(tmp > max){
				max = tmp;
			}

			if (min < max)
				break;
		}
		return max;		
	}

	public int eval(Configuration c, Configuration cDeb, int s){
		// Nombre de poissons
		int score = s;
		int [] nbPingouinsRestants = cDeb.getNombrePingouinsDispoParJoueur(arbitre.getJoueurs());
		int [] sipj = cDeb.scoreIlotParJoueur(arbitre.getJoueurs());

		int numJ = arbitre.getPosition(this.joueur) - 1;
		int numA = arbitre.getPosition(this.adversaire) - 1;

		// Pingouins bloqués
		int [] newNbPingouinsRestants = c.getNombrePingouinsDispoParJoueur(arbitre.getJoueurs());
		score += (nbPingouinsRestants[numA] - newNbPingouinsRestants[numA]) * 250;
		score -= (nbPingouinsRestants[numJ] - newNbPingouinsRestants[numJ]) * 250;
		
		// nouveau Ilot
		int [] newsipj = c.scoreIlotParJoueur(arbitre.getJoueurs());
		if (newsipj[numA]-sipj[numA] >= c.nombrePoissonsRestant()/5)
			score = score - 200 - (newsipj[numA]-sipj[numA]);
		if (newsipj[numJ]-sipj[numJ] < c.nombrePoissonsRestant()/5)
			score = score - 200 - (newsipj[numJ]-sipj[numJ]);
		if (newsipj[numJ]-sipj[numJ] >= c.nombrePoissonsRestant()/5)
			score = score + 200 + (newsipj[numJ]-sipj[numJ]);
		if (newsipj[numA]- sipj[numA] < c.nombrePoissonsRestant()/5)
			score = score + 200 + (newsipj[numA]-sipj[numA]);

		// pingouin presque bloqué
		Case [][] terrainCopieJ = c.cloneTerrain();
		Case [][] terrainCopieA = c.cloneTerrain();

		Couple [] p =  c.coordPingouins(this.joueur);
		for(int i=0;i<p.length;i++){
			if(c.getVoisins(terrainCopieJ,p[i].getX(),p[i].getY(),true).size()==1)
				score -= 150;
		}
		p =  c.coordPingouins(this.adversaire);
		for(int i=0;i<p.length;i++){
			if(c.getVoisins(terrainCopieJ,p[i].getX(),p[i].getY(),true).size()==1)
				score += 150;
		}

/*	idem pour le nombre de pingouin restant, regarder le nombre de pingouin isolé au début avec le nombre de poisson quil peuvent avoir
	et comparer avec la meme chose mais a la fin*/

		return score;
	}
}
