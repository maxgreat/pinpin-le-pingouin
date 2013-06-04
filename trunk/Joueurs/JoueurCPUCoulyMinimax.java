package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import java.util.Random;
import java.util.*;
import java.io.*;

public class JoueurCPUCoulyMinimax extends Joueur
{
    /**
     * Temps d'attente en seconde
     **/
    static final long TEMPS_ATTENTE_MINIMAL = 1000; 
    static final long TEMPS_ATTENTE_MAXIMAL = 5000;
	
	Joueur j;
   boolean finish;
   JoueurCPURandomizable cpuRdizable;



	public JoueurCPUCoulyMinimax() {
		super();
		this.j = this;
      this.finish = false;
		this.cpuRdizable = new JoueurCPURandomizable(this);
	}
	
	public JoueurCPUCoulyMinimax(Joueur j, boolean finish) {
		super();
		this.j = j;
      this.finish = finish;
	}
	
	public static String getType()
    {
        return "CPU_CoulyMinimax";
    }

    public Coup coupSuivant()
    {
	// Phase de placement
	if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN) 
	{
Case [][] terrain = ArbitreManager.instance.getConfiguration().getTerrain();
		int largeur = ArbitreManager.instance.getConfiguration().getLargeur();
		int hauteur = ArbitreManager.instance.getConfiguration().getHauteur();
		int max = 0, imax = -1;
      int imax2=0;
      int minCase = 0;
      int min = 9;
      Coup [] coupPossible = ArbitreManager.instance.getConfiguration().toutCoupsPossibles();	
      int [][] tableau = new int[hauteur][largeur];
      Configuration config = ArbitreManager.instance.getConfiguration();
		// Phase de placement
		System.out.println("A mon tour");
   	Coup [] placementPossible = ArbitreManager.instance.getConfiguration().toutPlacementsPossibles();
		int score = 0, ci, cj;
      for(int i = 0; i< hauteur; i++){
           for(int j = 0; j<largeur; j++){
            if(i%2 == 0 && j == 7)
                     continue;
             tableau[i][j] = 4;
           }
       }    
       for(int i = 0; i< hauteur; i++){
           for(int j = 0; j<largeur; j++){
               if(i%2 == 0 && j == 7)
                     continue;
               if(tableau[i][j] != 0)
                tableau[i][j] =terrain[i][j].scorePoisson();
               if(terrain[i][j].estObstacle()){
                   tableau[i][j] = 0;

					   if(i%2 == 0 && j < largeur-2){
							tableau[i][j+1] = 0;
                     System.out.println("0 en "+i+" "+(j+1));
				      }
				      if(i%2 == 1 && j < largeur-1){
							tableau[i][j+1] = 0;
                  }
					   if(j>=1){
					      tableau[i][j-1] = 0;
				      }
					   if(i%2==0 && j>=0){
							tableau[i+1][j] = 0;
				      }
				      if(i%2==0 && j>=0 && i>0){
							tableau[i-1][j] = 0;
				      }
				      if(i%2==1 && j>=1 && i<hauteur-1){
							tableau[i+1][j-1] = 0;
				      }
				      if(i%2==1 && j>=1 && i>0 ){
							tableau[i-1][j-1] = 0;
				      }
					   if(i%2==0 && j<largeur-1 && i<hauteur-1 ){
							tableau[i+1][j+1] = 0;
				      }
				      if(i%2==0 && j< largeur-1 && i>0 ){
							tableau[i-1][j+1] = 0;
				      }
				      if(i%2==1 && j<largeur-2 && i<hauteur-1){
							tableau[i+1][j] = 0;
				      }
				      if(i%2==1 && j<largeur-2 && i>0){
							tableau[i-1][j] = 0;
				      }
               }
           }
       }
      for(int i = 0; i<8; i++){
         for(int j = 0;j<8;j++){
            System.out.print(tableau[i][j]);
         }
         System.out.println(" ");
      }
      int l;
     max = 0;   
     for (int i = 0; i < placementPossible.length; i++) {
       Coup [] casePossible = config.coupsPossiblesCase(placementPossible[i].getXDepart(), placementPossible[i].getYDepart());
       score = 0;
       for(int j = 0; j < config.nombreCoupsPossiblesCase(placementPossible[i].getYDepart(), placementPossible[i].getXDepart());j++){
 
            if(terrain[casePossible[j].getYArrivee()][casePossible[j].getXArrivee()].scorePoisson() == 3)
               score++;
            
         }
         
			if (score > max && tableau[placementPossible[i].getYDepart()][placementPossible[i].getXDepart()] != 0) {

				max = score;
				imax = i;
			}
         score =0;
		}
		return placementPossible[imax];
	}
/*
**
**
**   Tous les pingouins sont placés !
**
**
*/
  try{
      Thread.sleep(2500);//sleep for 1000 ms
  
   }
   catch(InterruptedException ie){
   }
	// Récupérer temps départ
	long startMilli = System.currentTimeMillis();
	// coup = Lancement minimax profondeur 1 (thread)
	CoulyMinimax mini  = new CoulyMinimax(j, 1, ArbitreManager.instance, this.finish);
	Thread t = new Thread(mini);
	t.start();

	Coup coup = mini.getSignalCoup().attendreSignal();
	int pas = 2;

	// Demande de stop forcé
	if (coup == null)
	{
	    t.interrupt();
	    return null;
	}

   Boolean finish = true;
	Couple [] pingouins = ArbitreManager.instance.getConfiguration().coordPingouins(this.j);
	for (int i = 0; i < pingouins.length && finish; i++) {
		Couple couple = ArbitreManager.instance.getConfiguration().estIlot(pingouins[i].getX(), pingouins[i].getY(), new ArrayList<Couple>(), 0);
		if (couple.getX() == -1)
			finish = false;
	}
	if (finish) {
		System.out.println("Randomizable");
		return this.cpuRdizable.coupSuivant();
	}
   
	// tant que tempsCourant - tempsDepart < TEMPS_ATTENTE_MAXIMAL
	while (pas < 100 && System.currentTimeMillis() - startMilli < TEMPS_ATTENTE_MAXIMAL)
	{
	    if (ArbitreManager.instance.getForceStop())
	    {
		return null;
	    }

	    CoulyMinimaxIncremental miniI = new CoulyMinimaxIncremental(j, pas, ArbitreManager.instance,this.finish);
	    t = new Thread(miniI);
	    try
	    {
		t.start();
		Thread.sleep(TEMPS_ATTENTE_MAXIMAL - (System.currentTimeMillis() - startMilli));
		try
		{
		    t.interrupt();
		    t.join();
		}
		catch (InterruptedException ep)
		{
		 
		}

		break;
	    }
	    catch (InterruptedException e)
	    {	
		if (ArbitreManager.instance.getForceStop())
		{
		    t.interrupt();
		    try
		    {
			t.join();
		    }
		    catch(InterruptedException ep)
		    {
		    }

		    return null;
		}	

		synchronized(miniI.getSignalSynchro())
		{
		    miniI.getSignalSynchro().envoyerSignal();
		}

		coup = miniI.getSignalCoup().attendreSignal();

		try
		{
		    t.join();
		}
		catch (InterruptedException ep)
		{
		}

		// Demande de stop forcé
		if (coup == null)
		{
		    t.interrupt();
		    return null;
		}

		pas++;
	    }
	}

	long stopMilli = System.currentTimeMillis();

	try
	{
	    if (stopMilli - startMilli < TEMPS_ATTENTE_MINIMAL)
		Thread.sleep(TEMPS_ATTENTE_MINIMAL - (stopMilli - startMilli));
	}
	catch (InterruptedException e)
	{
	    // Stop forcé, renvoie null
	    return null;
	}

        return coup;
    }
}

