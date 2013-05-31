package Joueurs;
import Arbitre.*;
import Arbitre.Regles.*;
import Utilitaires.*;
import java.util.*;
import java.awt.*;


public class CoulyMinimax implements Runnable {
	Joueur joueur;
	Joueur adversaire;
	int profondeur;
	Arbitre arbitre;
	Signal<Coup> signalCoup;
   boolean finish;
 
	public CoulyMinimax(Joueur joueur, int profondeur, Arbitre arbitre, Boolean finish) {
		this.joueur = joueur;
		if (arbitre.getPosition(this.joueur) == 1) 
			this.adversaire = arbitre.getJoueurParPosition(2);
		else
			this.adversaire = arbitre.getJoueurParPosition(1);
		this.arbitre = arbitre;
      this.finish = finish;
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
		int max= Integer.MIN_VALUE,tmp,maxi=-1, score;
		Configuration cl,cloneConf;
		int [] nbPingouinsRestants = cc.getNombrePingouinsDispoParJoueur(arbitre.getJoueurs());
		int [] sipj = cc.scoreIlotParJoueur(arbitre.getJoueurs());
		/*		int [] ppj = cl.getNombrePingouinsDispoParJoueur(arbitre.getJoueurs());
				int nbPingouins = ppj[this.arbitre.getPosition(this.adversaire)-1];*/

      Couple [] p = new Couple [nbPingouinsRestants[arbitre.getPosition(joueur)-1]];

		ArrayList<Point> poissonIlot = new ArrayList<Point>();
		int h=-1,l=-1;
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			if(h!=coupPossible[i].getYDepart() || l!=coupPossible[i].getXDepart()){
				h=coupPossible[i].getYDepart();
				l=coupPossible[i].getXDepart();
				if(cc.estIlot(h,l).getX()!=-1){
	            System.out.println("le pingouin "+h+" "+l+" est isolé");
					poissonIlot.add(new Point(h,l));

	         }
            
			}
		}
      p = cc.coordPingouins(joueur); 
      Case [][] terrain= new Case[8][8];
     
      Couple [] pp =  cc.coordPingouins(this.joueur); 
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){	
			score = 0;
			if(!poissonIlot.contains(new Point(coupPossible[i].getXDepart(),coupPossible[i].getYDepart()))){
          	cl = cc.clone();
				score += cl.getTerrain()[coupPossible[i].getYArrivee()][coupPossible[i].getXArrivee()].scorePoisson();
            score = score*score*2;
            for(int j=0;j<pp.length;j++){
                int nbCaseAdj = nombreDeCasesAdjacentes((int)pp[j].getX(),(int)pp[j].getY(),cc.cloneTerrain());
			       if(nbCaseAdj ==5)
                   score += 2 ;
                if(nbCaseAdj == 4)
                   score += 4;
                if(nbCaseAdj == 3)
                   score += 7;
                if(nbCaseAdj == 2)
                   score += 11;
                if(nbCaseAdj == 1)
                   score += 16;
		      }
				for(int k = 0; k<nbPingouinsRestants[arbitre.getPosition(joueur)-1];k++){
		         int ii = coupPossible[i].getYArrivee();
		         int jj = coupPossible[i].getXArrivee();
            
		         if(coupPossible[i].getXDepart() == p[k].getY() && coupPossible[i].getYDepart()==p[k].getX() ){

                   continue;
               }
		         if(ii%2 == 0 && ii == p[k].getX()&& jj+1 == p[k].getY())
						 score -=15;
					if(ii%2 == 1 && ii == p[k].getX() && jj+1 == p[k].getY())
						 score -=15;
					if(ii == p[k].getX() && jj-1 == p[k].getY())
						 score -=15;         
					if(ii%2==0 && ii+1 == p[k].getX() && jj == p[k].getY())
						 score -=15;
					if(ii%2==0 && ii-1 == p[k].getX() && jj == p[k].getY())
						 score -=15;
					if(ii%2==1 &&  ii+1 == p[k].getX() && jj-1 == p[k].getY())
						 score -=15;
					if(ii%2==1 && ii-1 == p[k].getX() && jj-1 == p[k].getY())
						 score -=15;
					if(ii%2==0 &&  ii+1 == p[k].getX() && jj+1 == p[k].getY())
						 score -=15;
					if(ii%2==0 && ii-1 == p[k].getX() && jj+1 == p[k].getY())
						 score -=15;
					if(ii%2==1 &&  ii+1 == p[k].getX() && jj == p[k].getY())
						 score -=15;
					if(ii%2==1 && ii-1 == p[k].getX() && jj == p[k].getY())
						 score -= 15;
           }


            cl.effectuerCoup(coupPossible[i]);
            tmp = Min(cl, max, this.profondeur,nbPingouinsRestants,sipj,score);
				if(tmp > max){
					max = tmp;
					maxi = i;
				}
			}
       //  System.out.println("Score du deplacement : "+coupPossible[i].getXDepart()+" "+coupPossible[i].getYDepart()+" "+coupPossible[i].getXArrivee()+" "+coupPossible[i].getYArrivee()+" est de : "+score); 
		}	
		if(maxi==-1){
					return coupPossible[0];
		}
		return coupPossible[maxi];
	}

	public int Min(Configuration cc, int max, int profondeur, int [] nbPingouinsRestants, int [] sipj, int s) {
		Configuration clcc = cc.clone();
		clcc.setJoueurSurConfiguration(this.adversaire);
		Coup [] coupPossible = clcc.toutCoupsPossibles();
		int min = Integer.MAX_VALUE, tmp, score;
		Configuration cl;
		
		if(coupPossible.length == 0 ) 
			return eval(clcc, nbPingouinsRestants, sipj, s);
		else if (profondeur < 1)
			return eval(clcc, nbPingouinsRestants, sipj, s+coupPossible.length);
				
		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){
			score = 0;
			cl = clcc.clone();
			score = cl.getTerrain()[coupPossible[i].getYArrivee()][coupPossible[i].getXArrivee()].scorePoisson();
			cl.effectuerCoup(coupPossible[i]);
         
			tmp = Max(cl, min, profondeur - 1, nbPingouinsRestants, sipj, s-score*score*2);
			if(tmp < min){
				min = tmp;
			}
			if (min < max)
				break;
		}

		return min;		
	}


	public int Max(Configuration cc, int min, int profondeur, int [] nbPingouinsRestants, int [] sipj, int s)
	{
		Configuration clcc = cc.clone();
		clcc.setJoueurSurConfiguration(joueur);
		Coup [] coupPossible = clcc.toutCoupsPossibles();
		int max = Integer.MIN_VALUE, tmp, score;
		Configuration cl;

		if(coupPossible.length == 0 ) 
			return eval(clcc, nbPingouinsRestants, sipj, s);
		else if (profondeur < 1)
			return eval(clcc, nbPingouinsRestants, sipj, s+coupPossible.length);

		for(int i = 0; i < coupPossible.length && !Thread.currentThread().isInterrupted(); i++){

         score = 0;
			cl = clcc.clone();
			score = cl.getTerrain()[coupPossible[i].getYArrivee()][coupPossible[i].getXArrivee()].scorePoisson();
			cl.effectuerCoup(coupPossible[i]);
			tmp = Min(cl, max, profondeur-1,nbPingouinsRestants, sipj, score + s);
         if(tmp > max){
				max = tmp;
			}
			if (min < max)
				break;
		}
		return max;		
	}

	public int eval(Configuration c, int [] nbPingouinsRestants, int [] sipj, int s){
		// Nombre de poissons
		int score = s;
		int nbCasesRestantes = c.getNbCasesRestantes();
		int numJ = arbitre.getPosition(this.joueur) - 1;
		int numA = arbitre.getPosition(this.adversaire) - 1;
      
		// Pingouins bloqués
		int [] newNbPingouinsRestants = c.getNombrePingouinsDispoParJoueur(arbitre.getJoueurs());
		score += (nbPingouinsRestants[numA] - newNbPingouinsRestants[numA]) * 150;
		score -= (nbPingouinsRestants[numJ] - newNbPingouinsRestants[numJ]) * 300;
		int nbPoissonsRestants = c.nombrePoissonsRestant();
		// nouveau Ilot
		int [] newsipj = c.scoreIlotParJoueur(arbitre.getJoueurs());
		if (newsipj[numA]-sipj[numA] >= nbPoissonsRestants/8)
			score = score - 200 - (newsipj[numA]-sipj[numA]);
		if (newsipj[numJ]-sipj[numJ] < nbPoissonsRestants/8)
			score = score - 200 - (newsipj[numJ]-sipj[numJ]);
		if (newsipj[numJ]-sipj[numJ] >= nbPoissonsRestants/12)
			score = score + 200+ (newsipj[numJ]-sipj[numJ]);
		if (newsipj[numA]-sipj[numA] < nbPoissonsRestants/12)
			score = score + 200+ (newsipj[numA]-sipj[numA]);

		// pingouin presque bloqué
		Case [][] terrainCopieJ = c.cloneTerrain();
		Case [][] terrainCopieA = c.cloneTerrain();

		Couple [] p =  c.coordPingouins(this.joueur);
		for(int i=0;i<p.length;i++){

			if(c.getVoisins(terrainCopieJ,p[i].getX(),p[i].getY(),true).size()==1)
				score -= 100/(61-nbCasesRestantes);
		}
		p =  c.coordPingouins(this.adversaire);
		for(int i=0;i<p.length;i++){
			if(c.getVoisins(terrainCopieJ,(int)p[i].getX(),(int)p[i].getY(),true).size()==1)
				score += 100/(61-nbCasesRestantes);
		}

/*	idem pour le nombre de pingouin restant, regarder le nombre de pingouin isolé au début avec le nombre de poisson quil peuvent avoir
	et comparer avec la meme chose mais a la fin*/

		return score;
	}

   public ScoreCoup meilleurCheminC(int i, int j, Configuration configuration, int occurence){
      Coup [] lesCoups = configuration.coupsPossiblesCase(i,j);
      Case [][] terrain =  configuration.getTerrain();
      System.out.println("Meilleur chemin");
      ScoreCoup coupJoue = null;
      int nombreCoups = configuration.nombreCoupsPossiblesCase(j, i);

      if(occurence == 0)
         return new ScoreCoup(0, null);
      if(nombreCoups == 0)
         return new ScoreCoup(0, new Coup(0, 0,0,0));
      else{
         int maxScore = 0;
         int pointDuCoup = 0;
         int indice=0;
         for(int k=0; k<lesCoups.length;k++){
             Configuration configurationBis = configuration.clone();
             pointDuCoup = terrain[lesCoups[k].getYArrivee()][lesCoups[k].getXArrivee()].scorePoisson();
             int occ = occurence-1;
             configurationBis.effectuerCoup(lesCoups[k]);
             coupJoue = meilleurCheminC(lesCoups[k].getXArrivee(),lesCoups[k].getYArrivee(),configurationBis,occ );

             if(maxScore < pointDuCoup+coupJoue.score){
                 maxScore = pointDuCoup+coupJoue.score;
                 indice = k; 
             }
        }
        coupJoue.score = maxScore;
        coupJoue.coup = lesCoups[indice];
        return coupJoue;
      }

   }
    public int nombreDeCasesAdjacentes(int i,int j,Case [][] terrain){
      int largeur = ArbitreManager.instance.getConfiguration().getLargeur();
		int hauteur = ArbitreManager.instance.getConfiguration().getHauteur();
      int nombreCaseAdjacente = 0;

	   if(i%2 == 0 && j < largeur-2 && !terrain[i][j+1].estObstacle())
		    nombreCaseAdjacente++;
      if(i%2 == 1 && j < largeur-1 && !terrain[i][j+1].estObstacle())
		    nombreCaseAdjacente++;
      if(j>=1 && !terrain[i][j-1].estObstacle())
          nombreCaseAdjacente++;          
      if(i%2==0 && j>=0 && i<hauteur-1 && !terrain[i+1][j].estObstacle())
		    nombreCaseAdjacente++;
      if(i%2==0 && j>=0 && i>0 &&!terrain[i-1][j].estObstacle())
		    nombreCaseAdjacente++;
      if(i%2==1 && j>=1 && i<hauteur-1 && !terrain[i+1][j-1].estObstacle())
		    nombreCaseAdjacente++;
      if(i%2==1 && j>=1 && i>0 &&!terrain[i-1][j-1].estObstacle())
		    nombreCaseAdjacente++;
      if(i%2==0 && j<largeur-1 && i<hauteur-1 && !terrain[i+1][j+1].estObstacle())
	       nombreCaseAdjacente++;
      if(i%2==0 && j< largeur-1 && i>0 &&!terrain[i-1][j+1].estObstacle())
		    nombreCaseAdjacente++;
      if(i%2==1 && j<largeur-2 && i<hauteur-1 && !terrain[i+1][j].estObstacle())
		    nombreCaseAdjacente++;
      if(i%2==1 && j<largeur-2 && i>0 &&!terrain[i-1][j].estObstacle())
		    nombreCaseAdjacente++;

      return nombreCaseAdjacente++;
    }
}
