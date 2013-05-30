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
	Boolean finish;
	Signal<Coup> signalCoup;

	public Minimax(Joueur joueur, int profondeur, Arbitre arbitre, Boolean finish) {
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
		int [] nbPingouinsRestants = cc.getNombrePingouinsDispoParJoueur(arbitre.getJoueurs());
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			score = 0;
			cl = cc.clone();
			score += cl.getTerrain()[coupPossible[i].getYArrivee()][coupPossible[i].getXArrivee()].scorePoisson();
			cl.effectuerCoup(coupPossible[i]);
			if (this.finish)
				tmp = Max(cl, max, this.profondeur,nbPingouinsRestants,score);
			else 
				tmp = Min(cl, max, this.profondeur,nbPingouinsRestants,score);
			if(tmp > max){
				max = tmp;
				maxi = i;
			}
		}	
		if(maxi==-1){
			return coupPossible[0];
		} 
		
		return coupPossible[maxi];
	}

	public int Min(Configuration cc, int max, int profondeur, int [] nbPingouinsRestants, int s) {
		Configuration clcc = cc.clone();
		clcc.setJoueurSurConfiguration(this.adversaire);
		Coup [] coupPossible = clcc.toutCoupsPossibles();
		int min = Integer.MAX_VALUE, tmp, score;
		Configuration cl;
		
		if(coupPossible.length == 0 || profondeur < 1) 
			return eval(clcc, nbPingouinsRestants, s);
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			score = 0;
			cl = clcc.clone();
			score = cl.getTerrain()[coupPossible[i].getYArrivee()][coupPossible[i].getXArrivee()].scorePoisson();
			cl.effectuerCoup(coupPossible[i]);
			tmp = Max(cl, min, profondeur - 1, nbPingouinsRestants, s);
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
		if(coupPossible.length == 0 || profondeur < 1) 
			return eval(clcc, nbPingouinsRestants, s);
		
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			score = 0;
			cl = clcc.clone();
			score = cl.getTerrain()[coupPossible[i].getYArrivee()][coupPossible[i].getXArrivee()].scorePoisson();
			cl.effectuerCoup(coupPossible[i]);
			if (this.finish)
				tmp = Max(cl, max, profondeur-1,nbPingouinsRestants,score+s);
			else 
				tmp = Min(cl, max, profondeur-1,nbPingouinsRestants,score+s);
			if(tmp > max){
				max = tmp;
			}
			if (min < max)
				break;
		}
		return max;		
	}

	public int eval(Configuration c, int [] nbPingouinsRestants, int s){
		if (this.finish) {
			return s;
		}	
		// Nombre de poissons
		int score = s;
	
		int numJ = arbitre.getPosition(this.joueur) - 1;
		int numA = arbitre.getPosition(this.adversaire) - 1;

		// Pingouins bloqués
		int [] newNbPingouinsRestants = c.getNombrePingouinsDispoParJoueur(arbitre.getJoueurs());
		score += (nbPingouinsRestants[numA] - newNbPingouinsRestants[numA]) * 250;
		score -= (nbPingouinsRestants[numJ] - newNbPingouinsRestants[numJ]) * 250;
		
		// nouveau Ilot
		/*int [] newsipj = c.scoreIlotParJoueur(arbitre.getJoueurs());
		if (newsipj[numA]-sipj[numA] >= c.nombrePoissonsRestant()/4)
			score = score - 200 - (newsipj[numA]-sipj[numA]);
		if (newsipj[numJ]-sipj[numJ] < c.nombrePoissonsRestant()/4)
			score = score - 200 - (newsipj[numJ]-sipj[numJ]);
		if (newsipj[numJ]-sipj[numJ] >= c.nombrePoissonsRestant()/4)
			score = score + 200 + (newsipj[numJ]-sipj[numJ]);
		if (newsipj[numA]-sipj[numA] < c.nombrePoissonsRestant()/4)
			score = score + 200 + (newsipj[numA]-sipj[numA]);
		*/
		// pingouin presque bloqué
		Case [][] terrainCopieJ = c.cloneTerrain();
		Case [][] terrainCopieA = c.cloneTerrain();

		Couple [] p =  c.coordPingouins(this.joueur);
		for(int i=0;i<p.length;i++){
			if(c.getVoisins(terrainCopieJ,p[i].getX(),p[i].getY(),true).size()==1)
				score -= 200;
		}
		p =  c.coordPingouins(this.adversaire);
		for(int i=0;i<p.length;i++){
			if(c.getVoisins(terrainCopieJ,p[i].getX(),p[i].getY(),true).size()==1)
				score += 200;
		}

		return score;
	}

	public Couple meilleurChemin(int i, int j, Configuration configuration, int score){
		Coup [] lesCoups = configuration.coupsPossiblesCase(i,j);
		Case [][] terrain =  configuration.getTerrain();
		System.out.println(score);
		if(lesCoups == null)
			return new Couple(0,terrain[i][j].scorePoisson());
		else{
			int maxScore = 0, indice = 0, tmp;
			for(int k=0; k<lesCoups.length && !Thread.currentThread().isInterrupted();k++){
				tmp = 0;
				Configuration configurationBis = configuration.clone();
				tmp = configurationBis.effectuerCoup(lesCoups[k]);
				tmp += meilleurChemin(lesCoups[k].getXArrivee(),lesCoups[k].getYArrivee(),configurationBis,score).getY();

				if(maxScore < tmp){
					maxScore = tmp	;
					indice = k; 
				}
				if(maxScore == score)
					k = lesCoups.length;
			}
			return new Couple(indice,maxScore);
		}
	}
}
