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
				if(cc.estIlot(h, l, new ArrayList<Couple>(), 0).getX()!=-1){
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
		Couple [] sipj = cDeb.scoreIlotParJoueur(arbitre.getJoueurs());

		int numJ = arbitre.getPosition(this.joueur) - 1;
		int numA = arbitre.getPosition(this.adversaire) - 1;

		// Pingouins bloqués
		int [] newNbPingouinsRestants = c.getNombrePingouinsDispoParJoueur(arbitre.getJoueurs());
		score += (nbPingouinsRestants[numA] - newNbPingouinsRestants[numA]) * 100;
		score -= (nbPingouinsRestants[numJ] - newNbPingouinsRestants[numJ]) * 100;
		
		// nouveau Ilot
		Couple [] newsipj = c.scoreIlotParJoueur(arbitre.getJoueurs());
		if(newsipj[numA].getY()>sipj[numA].getY()){
			if (newsipj[numA].getX()-sipj[numA].getX() >= (c.nombrePoissonsRestant()-sipj[numA].getX())/5)
				score = score - 100 - (newsipj[numA].getX()-sipj[numA].getX());
			if (newsipj[numA].getX()- sipj[numA].getX() < (c.nombrePoissonsRestant()-sipj[numA].getX())/5)
				score = score + 100 + (newsipj[numA].getX()-sipj[numA].getX());
		}
		if(newsipj[numJ].getY()>sipj[numJ].getY()){
			if (newsipj[numJ].getX()-sipj[numJ].getX() < (c.nombrePoissonsRestant()-sipj[numJ].getX())/5)
				score = score - 100 - (newsipj[numJ].getX()-sipj[numJ].getX());
			if (newsipj[numJ].getX()-sipj[numJ].getX() >= (c.nombrePoissonsRestant()-sipj[numJ].getX())/5)
				score = score + 100 + (newsipj[numJ].getX()-sipj[numJ].getX());
		}

		// pingouin presque bloqué
		Case [][] terrainCopieJ = c.cloneTerrain();
		Case [][] terrainCopieA = c.cloneTerrain();

		Couple [] p =  c.coordPingouins(this.joueur);
		for(int i=0;i<p.length;i++){
			if(c.getVoisins(terrainCopieJ,p[i].getX(),p[i].getY(),true).size()==1)
				score -= 25;
			if(c.getVoisins(terrainCopieJ,p[i].getX(),p[i].getY(),true).size()==2)
				score -= 12;
			if(c.estIlot(p[i].getX(), p[i].getY(), new ArrayList<Couple>(), 0).getX() != -1)
				if(poissonAtteignable(c, terrainCopieJ, p[i].getX(), p[i].getY())<8)
					score -= 12;
		}
		p =  c.coordPingouins(this.adversaire);
		for(int i=0;i<p.length;i++){
			if(c.getVoisins(terrainCopieA,p[i].getX(),p[i].getY(),true).size()==1)
				score += 25;
			if(c.getVoisins(terrainCopieA,p[i].getX(),p[i].getY(),true).size()==1)
				score += 12;
			if(c.estIlot(p[i].getX(), p[i].getY(), new ArrayList<Couple>(), 0).getX() != -1)
				if(poissonAtteignable(c, terrainCopieA, p[i].getX(), p[i].getY())<8)
					score += 12;
		}		

/*	idem pour le nombre de pingouin restant, regarder le nombre de pingouin isolé au début avec le nombre de poisson quil peuvent avoir
	et comparer avec la meme chose mais a la fin*/

		return score;
	}

	// Utiliser un terrain cloner
	public int poissonAtteignable(Configuration c, Case [][] terrain, int i, int j){
		Stack<Couple> pile = new Stack();
		int somme = 0;
		pile.push(new Couple(i,j));
		terrain[i][j].setEtat(Etat.VIDE);
		Couple p;
		while(!pile.empty()){
			p = pile.pop();
			ArrayList<Couple> voisins = c.getVoisins(terrain,p.getX(),p.getY(),true);
			for(int taille=0;taille<voisins.size();taille++){
				p = voisins.get(taille);
				somme += terrain[p.getX()][p.getY()].scorePoisson();
				pile.push(new Couple(p.getX(),p.getY()));
				terrain[p.getX()][p.getY()].setEtat(Etat.VIDE);
			}
		}
		return somme;
	}
}
