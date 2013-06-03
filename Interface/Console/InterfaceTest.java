package Interface.Console;

import Interface.*;
import Joueurs.*;
import Arbitre.*;
import Arbitre.Regles.*;
import Utilitaires.*;

import java.util.*;
import java.awt.event.*;
import javax.swing.Timer;

public class InterfaceTest extends Interface {
	/**
	 * repaint() doit afficher le jeu en cours à l'écran
	 **/
	public Timer time = null;
	
	public  void repaint() {
		if (ArbitreManager.instance.partieFinie()) {
			Joueur j1 = this.joueurs[0];
			Joueur j2 = this.joueurs[1];
			System.out.print("Result:");
			if (j1.getScore() > j2.getScore())
				System.out.print("j1:");
			else if (j1.getScore() < j2.getScore())
				System.out.print("j2:");
			else 
				if (j1.getNombreTuile() > j2.getNombreTuile())
					System.out.print("j1:");
				else if (j1.getNombreTuile() < j2.getNombreTuile())
					System.out.print("j2:");
				else
					System.out.print("draw:");
			System.out.println(j1.getScore()+":"+j1.getNombreTuile()+":"+j2.getScore()+":"+j2.getNombreTuile());

			//			ArbitreManager.stopperPartie();
			ArbitreManager.instance.getSignalStop().envoyerSignal();
			time = new Timer(500, new ActionListener() 
				{
				      
					public void actionPerformed(ActionEvent evt)
					{
						Interface inter = ArbitreManager.instance.getInterface();
						((InterfaceTest)inter).time.stop();
						ArbitreManager.stopperPartie();
						while(ArbitreManager.instanceThread != null)
						{
							try
							{
								Thread.sleep(100);
							}
							catch (InterruptedException e)
							{
							}
						}
						ArbitreManager.initialiserPartie(inter.joueurs, ArbitreManager.LARGEUR_GRILLE, ArbitreManager.HAUTEUR_GRILLE,inter);
						ArbitreManager.lancerPartie();				
					       
					}
				
				});
			time.start();
		}
	}
	
	/**
	 * run() lance les différentes actions à effectuer
	 **/
	public  void run(String [] args) {
		this.joueurs = new Joueur[2];
		this.joueurs[0] = new JoueurCPUUniversel(2);
		this.joueurs[1] = new JoueurCPUUniversel(3);
		ArbitreManager.initialiserPartie(joueurs, ArbitreManager.LARGEUR_GRILLE, ArbitreManager.HAUTEUR_GRILLE, this);

		Thread t = new Thread(new Runnable() 
			{
				public void run()
				{
					ArbitreManager.lancerPartie();
				        boolean interrupt = false;
					while (!interrupt)
					{
						try
						{
							interrupt = false;
							Thread.sleep(1000);
						}
						catch (InterruptedException e)
						{
							interrupt = true;
						}
					}
				}
			});
		t.start();
	}
}
