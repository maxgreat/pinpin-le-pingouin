package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import Utilitaires.*;
import java.util.Random;


public class Minimax implements Runnable
{
	Joueur joueur;
	Joueur adversaire;
	int profondeur;
	Arbitre arbitre;
	Signal<Coup> signalCoup;

	public Minimax(Joueur joueur, int profondeur, Arbitre arbitre)
	{
		this.joueur = joueur;
		if (arbitre.getPosition(this.joueur) == 1) 
			this.adversaire = arbitre.getJoueurParPosition(2);
		else
			this.adversaire = arbitre.getJoueurParPosition(1);
		this.arbitre = arbitre;
		this.profondeur = 2 * profondeur;
		this.signalCoup = new Signal<Coup>();
	}

	public Signal<Coup> getSignalCoup()
	{
		return signalCoup;
	}

	public void run()
	{
		getSignalCoup().envoyerSignal(minimax(ArbitreManager.instance.getConfiguration().clone()));
	}

	public Coup minimax(Configuration cc){
		Coup [] coupPossible = cc.toutCoupsPossibles();
		int max= Integer.MIN_VALUE,tmp,maxi=-1;
		Configuration cl;

		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			cl = cc.clone();
			cl.effectuerCoup(coupPossible[i]);
			tmp = Min(cl, max, this.profondeur);
			if(tmp > max){
				max = tmp;
				maxi = i;
			}
		}				
		return coupPossible[maxi];
	}

	public int Min(Configuration cc, int max, int profondeur){

		Configuration clcc = cc.clone();
		clcc.setJoueurSurConfiguration(this.adversaire);
		Coup [] coupPossible = clcc.toutCoupsPossibles();
		if(coupPossible.length == 0)
			return 20;
		if (profondeur < 1)
			return 20 - coupPossible.length;
		int min = Integer.MAX_VALUE, tmp;
		Configuration cl;

		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			tmp = 0;
			cl = clcc.clone();
			tmp += cl.effectuerCoup(coupPossible[i]);
			tmp += Max(cl, min, profondeur-1);
			if(tmp < min){
				min = tmp;
			}
			if (min < max)
				break;
		}

		return min;		
	}


	public int Max(Configuration cc, int min, int profondeur)
	{
		Configuration clcc = cc.clone();
		clcc.setJoueurSurConfiguration(joueur);
		Coup [] coupPossible = clcc.toutCoupsPossibles();
		if(coupPossible.length == 0)
			return -20;
		if (profondeur < 1)
			return -(20 - coupPossible.length);
		int max = Integer.MIN_VALUE, tmp;
		Configuration cl;

		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			tmp = 0;
			cl = clcc.clone();
			tmp -= cl.effectuerCoup(coupPossible[i]);
			tmp -= Min(cl, max, profondeur-1);
			if(tmp > max){
				max = tmp;
			}
			if (min < max)
				break;
		}
		return max;		
	}
}
