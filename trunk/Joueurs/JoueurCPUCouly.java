package Joueurs;
import Interface.Console.*;
import Arbitre.*;
import Arbitre.Regles.*;
import java.util.Random;
import java.lang.*;
public class JoueurCPUCouly extends Joueur {    
    
	/**
	 * Phase de placement : choisi la case dont la somme des poissons des cases adjacentes est la plus élevée
	 * Phase de jeu : choisi une des cases qui contient le plus de poisson
	 **/
	public Coup coupSuivant() {
		Case [][] terrain = ArbitreManager.instance.getConfiguration().getTerrain();
		int largeur = ArbitreManager.instance.getConfiguration().getLargeur();
		int hauteur = ArbitreManager.instance.getConfiguration().getHauteur();
		int max = 0, imax = -1;
      int imax2=0;
      int minCase = 0;
      Coup [] coupPossible = ArbitreManager.instance.getConfiguration().toutCoupsPossibles();	
      int [][] tableau = new int[hauteur][largeur];
      Configuration config = ArbitreManager.instance.getConfiguration();
		// Phase de placement
		System.out.println("A mon tour");
      if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN) {
			Coup [] placementPossible = ArbitreManager.instance.getConfiguration().toutPlacementsPossibles();
			int score = 0, ci, cj;
       
       for(int i = 0; i< hauteur; i++){
           for(int j = 0; j<largeur; j++){
               tableau[i][j] =0;

           }
       }
       int l;
       for(int i = 0; i< hauteur; i++){
           for(int j = 0; j<largeur; j++){
               // Sortie du terrain
		         if (i%2 == 0 && j == largeur - 1)
		             continue;
		 
		         // Regarde les cases du joueur en cours
		         if (terrain[i][j].getJoueurSurCase() == null)
		             continue;

               
		           l = j + 1;
                 int k = i;
		           if (i%2 == 0)
		           {
		               while (l < largeur - 1 && !terrain[i][l].estObstacle())
		               {
								 //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +i);
		               tableau[k][l]++;
								 l++;
		               }
		           }
		           else
		           {
		               while (l < largeur && !terrain[i][l].estObstacle())
		               {
		                   //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +i);
		                   tableau[k][l]++;
		                   l++;
		               }
		           }
				    // Vers la gauche
				    {
				        l = j - 1;
                    k = i;
				        while (l >= 0 && !terrain[i][l].estObstacle())
				        {
				                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +i);
				            tableau[k][l]++;
				            l--;
				        }
				    }

				    // Vers le haut droit
				    {
				        l = j;
				        k = i - 1;

				        while(k >= 0)
				        {
				            if (k%2 == 1)
				                l++;

				            if ((k%2 == 0 && l >= largeur - 1) || (k%2 == 1 && l >= largeur) || terrain[k][l].estObstacle())
				                break;
				                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +k);

				            tableau[k][l]++;
				            k--;
				        }
				    }

				    // Vers le haut gauche
				    {

				        l = j;
				        k = i - 1;

				        while(k >= 0)
				        {
				            if (k%2 == 0)
				                l--;

				            if (l < 0 || terrain[k][l].estObstacle())
				                break;

				                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +k);
				            tableau[k][l]++;
				            k--;
				        }
				    }

				    // Vers le bas droit
				    {
				        l = j;
				        k = i + 1;

				        while(k < hauteur)
				        {
				            if (k%2 == 1)
				                l++;

				            if ((k%2 == 0 && l == largeur - 1) || (k%2 == 1 && l == largeur) || terrain[k][l].estObstacle())
				                break;
				                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +k);

				            tableau[k][l]++;
				            k++;
				        }
				    }

				    // Vers le bas gauche
				    {
				        l = j;
				        k = i + 1;

				        while(l >= 0 && k < hauteur)
				        {
				            if (k%2 == 0)
				                l--;

				            if (l < 0 || terrain[k][l].estObstacle())
				                break;
				                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +k);

				            tableau[k][l]++;
				            k++;
				        }
				    }
		      }
		   }
			for (int i = 0; i < placementPossible.length; i++) {
				ci = placementPossible[i].getXDepart();
				cj = placementPossible[i].getYDepart();
				if (cj%2 == 0) {
					if (ci < largeur - 2)
						score += terrain[cj][ci+1].scorePoisson();
					if (cj > 1) {
						score += terrain[cj-1][ci+1].scorePoisson();
						score += terrain[cj-1][ci].scorePoisson();
					}
					if (ci > 0)
						score += terrain[cj][ci-1].scorePoisson();
               if(ci < largeur -1)
					   score += terrain[cj+1][ci+1].scorePoisson();
				   score += terrain[cj+1][ci].scorePoisson();
               
           } else {
					if (ci < largeur - 1) {
						score += terrain[cj][ci+1].scorePoisson();
						score += terrain[cj-1][ci].scorePoisson();
					}
					if (ci > 0) {
						score += terrain[cj-1][ci-1].scorePoisson();	
						score += terrain[cj][ci-1].scorePoisson();
					}
					if (cj < hauteur - 2 && ci > 0)
						score += terrain[cj+1][ci-1].scorePoisson();	
					if (cj < hauteur - 2 && ci < largeur - 1)
						score += terrain[cj+1][ci].scorePoisson();	
				}


				if (score > max && tableau[cj][ci] < 2) {
					max = score;
					imax = i;
				}
            score =0;
			}
			return placementPossible[imax];
		}
		// Phase de jeu
      try{
      //do what you want to do before sleeping
      Thread.sleep(1000);//sleep for 1000 ms
  
      }
      catch(InterruptedException ie){
       //If this thread was intrrupted by nother thread 
	   }
      boolean unIlotPersonnel = false;
      boolean adversaireTrouve;
      max = 50;
      int jmax = 0;
      int score = 0;
      int nombreCaseAdjacente =0;
      int nombreCasesVides = 0;
      for (int i = 0; i < hauteur; i++)
      {
         for (int j = 0; j < largeur; j++)
         {      
             if (i%2 == 0 && j == largeur - 1)
                continue;
             if(terrain[i][j].estVide())
                nombreCasesVides++;
         }
      }
      System.out.println("nb case vide "+nombreCasesVides);
      if(nombreCasesVides < 16){
         int minAdj = 7;
         max = -1;
		   for (int i = 0; i < hauteur; i++)
		   {   
		      for (int j = 0; j < largeur; j++)
		      {
		         
		         // Sortie du terrain
		         if (i%2 == 0 && j == largeur - 1)
		             continue;
		 
		         // Regarde les cases du joueur en cours
		         if (terrain[i][j].getJoueurSurCase() != ArbitreManager.instance.getConfiguration().getJoueurSurConfiguration())
		             continue;
		         
		             
	             unIlotPersonnel = false;
                adversaireTrouve = false;
	             if(i%2 == 0 && j < largeur-2 && !terrain[i][j+1].estObstacle() && !adversaireTrouve){
						  unIlotPersonnel = estSeulSurIlot(i,j+1,i,j);
                    adversaireTrouve = !unIlotPersonnel;
                }
	             if(i%2 == 1 && j < largeur-1 && !terrain[i][j+1].estObstacle()&& !adversaireTrouve){
						  unIlotPersonnel = estSeulSurIlot(i,j+1,i,j);
                    adversaireTrouve = !unIlotPersonnel;
                }
	             if(j>=1 && !terrain[i][j-1].estObstacle()&& !adversaireTrouve){
	                 unIlotPersonnel = estSeulSurIlot(i,j-1,i,j);
                    adversaireTrouve = !unIlotPersonnel;
                }	             
	             if(i%2==0 && j>=0 && i<hauteur-1 && !terrain[i+1][j].estObstacle()&& !adversaireTrouve){
						  unIlotPersonnel = estSeulSurIlot(i+1,j,i,j);
                    adversaireTrouve = !unIlotPersonnel;
                }
	             if(i%2==0 && j>=0 && i>0 &&!terrain[i-1][j].estObstacle()&& !adversaireTrouve){
						  unIlotPersonnel = estSeulSurIlot(i-1,j,i,j);
	                 adversaireTrouve = !unIlotPersonnel;
                }
	             if(i%2==1 && j>=1 && i<hauteur-1 && !terrain[i+1][j-1].estObstacle()&& !adversaireTrouve){
						  unIlotPersonnel = estSeulSurIlot(i+1,j-1,i,j);
	                 adversaireTrouve = !unIlotPersonnel;
                }
	             if(i%2==1 && j>=1 && i>0 &&!terrain[i-1][j-1].estObstacle()&& !adversaireTrouve){
						  unIlotPersonnel = estSeulSurIlot(i-1,j-1,i,j);
	                 adversaireTrouve = !unIlotPersonnel;
                }
	             


	             if(i%2==0 && j<largeur-1 && i<hauteur-1 && !terrain[i+1][j+1].estObstacle()&& !adversaireTrouve){
						  unIlotPersonnel = estSeulSurIlot(i+1,j+1,i,j);
	                 adversaireTrouve = !unIlotPersonnel;
                }
	             if(i%2==0 && j< largeur-1 && i>0 &&!terrain[i-1][j+1].estObstacle()&& !adversaireTrouve){
						  unIlotPersonnel = estSeulSurIlot(i-1,j+1,i,j);
	                 adversaireTrouve = !unIlotPersonnel;
                }
	             if(i%2==1 && j<largeur-2 && i<hauteur-1 && !terrain[i+1][j].estObstacle()&& !adversaireTrouve){
						  unIlotPersonnel = estSeulSurIlot(i+1,j,i,j);
	                 adversaireTrouve = !unIlotPersonnel;
                }
	             if(i%2==1 && j<largeur-2 && i>0 &&!terrain[i-1][j].estObstacle()&& !adversaireTrouve){
						  unIlotPersonnel = estSeulSurIlot(i-1,j,i,j);
                    adversaireTrouve = !unIlotPersonnel;
                }
	             
                nombreCaseAdjacente = nombreDeCasesAdjacentes(i,j);
                if(unIlotPersonnel)
                   System.out.println("le pingouin en "+i+" "+j+" est sur un ilot");
                if(nombreCaseAdjacente == 1 && !unIlotPersonnel){
	                max = 0;
						 imax = i;
						 jmax = j; 
                   System.out.println("le pingouin en "+i+" "+j+" a qu'une sortie");
	             }			
		       } 
			}    
			Case tmp;
			for (int i = 0; i < coupPossible.length; i++) {
				tmp = terrain[coupPossible[i].getYArrivee()][coupPossible[i].getXArrivee()];
			 	if (coupPossible[i].getYDepart() == imax && coupPossible[i].getXDepart()==jmax && tmp.scorePoisson() > max && !unIlotPersonnel || tmp.scorePoisson() > max && !unIlotPersonnel && max != 0 ) {
					imax2 = i;
                                  System.out.println("le pingouin en "+imax+" "+jmax);
               System.out.println(" 1 : "+(coupPossible[i].getYDepart() == imax && coupPossible[i].getXDepart()==jmax && tmp.scorePoisson() > max && !unIlotPersonnel)+" 2 : "+(tmp.scorePoisson() > max && !unIlotPersonnel && max != 0));
               System.out.println("le pingouin en "+coupPossible[i].getYDepart()+" "+coupPossible[i].getXDepart()+" est le max");
					max = tmp.scorePoisson();
					if (max > 2) {
						break;
					}
				}
			}
      }
      else{
          Coup dernierPingouinSeul;
          
          boolean trouver = false;
          int minCaseSuiv;
          int nombreCaseAdjacenteSuivante;
          
          for (int k = 0; k < coupPossible.length; k++) {
              int i = coupPossible[k].getYDepart();
              int j = coupPossible[k].getXDepart();
              adversaireTrouve = false;    
              unIlotPersonnel = false;
              minCase =7;
              minCaseSuiv = 7;
              nombreCaseAdjacente = 0;
              nombreCaseAdjacenteSuivante = 0;
	           if(i%2 == 0 && j < largeur-2 && !terrain[i][j+1].estObstacle() && !adversaireTrouve){
				      unIlotPersonnel = estSeulSurIlot(i,j+1,i,j);
	               adversaireTrouve = !unIlotPersonnel;
              }
              if(i%2 == 1 && j < largeur-1 && !terrain[i][j+1].estObstacle() && !adversaireTrouve){
					   unIlotPersonnel = estSeulSurIlot(i,j+1,i,j);              
                  adversaireTrouve = !unIlotPersonnel;
              }
	           if(j>=1 && !terrain[i][j-1].estObstacle() && !adversaireTrouve){
	               unIlotPersonnel = estSeulSurIlot(i,j-1,i,j);
	               adversaireTrouve = !unIlotPersonnel;
              }
	           if(i%2==0 && j>=0 && i<hauteur-1 && !terrain[i+1][j].estObstacle() && !adversaireTrouve){
					   unIlotPersonnel = estSeulSurIlot(i+1,j,i,j);
                  adversaireTrouve = !unIlotPersonnel;
              }
              if(i%2==0 && j>=0 && i>0 &&!terrain[i-1][j].estObstacle()&& !adversaireTrouve){
					   unIlotPersonnel = estSeulSurIlot(i-1,j,i,j);
                  adversaireTrouve = !unIlotPersonnel;
              }
              if(i%2==1 && j>=1 && i<hauteur-1 && !terrain[i+1][j-1].estObstacle()&& !adversaireTrouve){
					   unIlotPersonnel = estSeulSurIlot(i+1,j-1,i,j);
                  adversaireTrouve = !unIlotPersonnel;
              }
              if(i%2==1 && j>=1 && i>0 &&!terrain[i-1][j-1].estObstacle()&& !adversaireTrouve){
					   unIlotPersonnel = estSeulSurIlot(i-1,j-1,i,j);
	               adversaireTrouve = !unIlotPersonnel;
              }
	           if(i%2==0 && j<largeur-1 && i<hauteur-1 && !terrain[i+1][j+1].estObstacle()&& !adversaireTrouve){
					   unIlotPersonnel = estSeulSurIlot(i+1,j+1,i,j);
                  adversaireTrouve = !unIlotPersonnel;
              }
              if(i%2==0 && j< largeur-1 && i>0 &&!terrain[i-1][j+1].estObstacle()&& !adversaireTrouve){
					   unIlotPersonnel = estSeulSurIlot(i-1,j+1,i,j);
                  adversaireTrouve = !unIlotPersonnel;
              }
              if(i%2==1 && j<largeur-2 && i<hauteur-1 && !terrain[i+1][j].estObstacle()&& !adversaireTrouve){
					   unIlotPersonnel = estSeulSurIlot(i+1,j,i,j);
                  adversaireTrouve = !unIlotPersonnel;
              }
              if(i%2==1 && j<largeur-2 && i>0 &&!terrain[i-1][j].estObstacle()&& !adversaireTrouve){
					   unIlotPersonnel = estSeulSurIlot(i-1,j,i,j);
                  adversaireTrouve = !unIlotPersonnel;
              }
              if(unIlotPersonnel){
                 System.out.println("Le pingouin "+i+" "+j+" est sur un ilot");
              
                  continue;
              }
              System.out.println(" i, j :"+i+" "+j);

       
              int ibis = coupPossible[k].getYArrivee();
              int jbis = coupPossible[k].getXArrivee();
              
              nombreCaseAdjacente = nombreDeCasesAdjacentes(i,j);
              nombreCaseAdjacenteSuivante = nombreDeCasesAdjacentes(ibis,jbis);

              if(nombreCaseAdjacente < minCase && nombreCaseAdjacenteSuivante >1){
                  minCase = nombreCaseAdjacente;
                  minCaseSuiv = nombreCaseAdjacenteSuivante;
                  imax2 = k;
                  trouver = true;
				  }

          }

          if (!trouver || minCase == 2){
				  boolean aucunDansCoin = true;
              int points=0;
              int caseAdj = 7;
              imax = coupPossible[0].getYArrivee();
              jmax = coupPossible[0].getXArrivee();
              for (int i = 0; i < hauteur; i++)
		        {
		           for (int j = 0; j < largeur; j++)
		           {
		         
		               // Sortie du terrain
		              if (i%2 == 0 && j == largeur - 1)
		              continue;
		             // System.out.println("Je suis la");
		              // Regarde les cases du joueur en cours
		              if (terrain[i][j].getJoueurSurCase() != ArbitreManager.instance.getConfiguration().getJoueurSurConfiguration())
		              continue;
                    nombreCaseAdjacente = nombreDeCasesAdjacentes(i,j);
                    if(nombreCaseAdjacente == 1){
		                // System.out.println("Pour le moment avec :"+i+" et "+j);   
                       imax = i;
                       jmax = j;
                       aucunDansCoin = false;
                    }
                    if(nombreCaseAdjacente != 0 && nombreCaseAdjacente < caseAdj && aucunDansCoin){
                     //  System.out.println("OU avec :"+i+" et "+j);   
                       imax = i;
                       jmax = j;
                       caseAdj = nombreCaseAdjacente;
                    }
                 }
              }
                 
              Configuration clone = ArbitreManager.instance.getConfiguration().clone();
              Coup c = meilleurChemin(jmax,imax,clone, 6).coup;

        
              return c;
          }
      } 

		return coupPossible[imax2];
	}
	
   public boolean estSeulSurIlot(int i, int j,int posx, int posy){
      boolean estSeul = true;
      boolean nouveau =true;
      Case [][] terrain = ArbitreManager.instance.getConfiguration().getTerrain();
      
		int largeur = ArbitreManager.instance.getConfiguration().getLargeur();
		int hauteur = ArbitreManager.instance.getConfiguration().getHauteur();
		int [][] tableau = new int [hauteur][largeur];


      for(int k = 0; k<hauteur;k++){
         for(int l = 0; l<largeur; l++){
		      tableau[k][l] = 0;        
		   } 
		}
   //  System.out.println("on regarde si "+i+" "+j+" est sur un ilot");
      tableau[i][j] = 1;
      tableau[posx][posy] =2;
      while(nouveau){
         nouveau = false;
         for(int k = 0; k<hauteur;k++){
            for(int l = 0; l<largeur; l++){
		         if(tableau[k][l] == 1){
   //               System.out.println("k = "+k+", l = "+l);
                  if(k%2==0){

		               if(l < largeur-2 && !terrain[k][l+1].estObstacle()&&tableau[k][l+1] == 0)
		               {
		                  tableau[k][l+1]=1;
		                  nouveau = true;
		               }
							if(l>=1 && !terrain[k][l-1].estObstacle()&&tableau[k][l-1] == 0)
							{
		                  tableau[k][l-1]=1;
		                  nouveau = true;
		               }
		               if(l>=0 && k<hauteur-1 && !terrain[k+1][l].estObstacle()&&tableau[k+1][l] == 0){
		                  tableau[k+1][l]=1;
		                  nouveau = true;
		               }
							if(l>=0 && k>0 &&!terrain[k-1][l].estObstacle()&&tableau[k-1][l] == 0){
		                  tableau[k-1][l]=1;
		                  nouveau = true;
		               }
							if(l<largeur-1 && k<hauteur-1 && !terrain[k+1][l+1].estObstacle()&&tableau[k+1][l+1] == 0){
		                  tableau[k+1][l+1]=1;
		                  nouveau = true;
		               }
							if(l< largeur-1 && k>0 &&!terrain[k-1][l+1].estObstacle()&&tableau[k-1][l+1] == 0){
		                  tableau[k-1][l+1]=1;
		                  nouveau = true;
		               }
                     if (k > 0 && l < largeur-1 &&tableau[k][l] ==1 &&terrain[k-1][l+1].getJoueurSurCase() != ArbitreManager.instance.getJoueurCourant() && terrain[k-1][l+1].getJoueurSurCase()!= null)
							    return false;
                     if (l < largeur -2 && tableau[k][l] ==1 &&tableau[k][l] ==1 && terrain[k][l+1].getJoueurSurCase() != ArbitreManager.instance.getJoueurCourant() && terrain[k][l+1].getJoueurSurCase()!= null)
							    return false;
                     if (l > 0 &&tableau[k][l] ==1 && terrain[k][l-1].getJoueurSurCase() != ArbitreManager.instance.getJoueurCourant()&& terrain[k][l-1].getJoueurSurCase()!= null)
							    return false;
                     if (l < largeur - 1 && k < hauteur -1 &&tableau[k][l] ==1 && terrain[k+1][l+1].getJoueurSurCase() != ArbitreManager.instance.getJoueurCourant() && terrain[k+1][l+1].getJoueurSurCase()!= null)
							    return false;
                     if (k < hauteur-1 &&tableau[k][l] ==1 && terrain[k+1][l].getJoueurSurCase() != ArbitreManager.instance.getJoueurCourant()&& terrain[k+1][l].getJoueurSurCase()!= null)
							    return false;
                     if (k > 0 &&tableau[k][l] ==1 && terrain[k-1][l].getJoueurSurCase() != ArbitreManager.instance.getJoueurCourant()&& terrain[k-1][l].getJoueurSurCase()!= null)
							    return false;
			         } 




                  else{
                     //System.out.println("k : "+k+", l : "+l);
                     if(l < largeur-1 && !terrain[k][l+1].estObstacle()&&tableau[k][l+1] == 0)
		               {
		                  tableau[k][l+1]=1;
		                  nouveau = true;
		               }
							if(l>=1 && !terrain[k][l-1].estObstacle()&&tableau[k][l-1] == 0)
							{
		                  tableau[k][l-1]=1;
		                  nouveau = true;
		               }
		               if(k<hauteur-1 && l< largeur - 1 &&!terrain[k+1][l].estObstacle()&&tableau[k+1][l] == 0){
		                  tableau[k+1][l]=1;
		                  nouveau = true;
		               }
							if(k>0 && l< largeur - 1 &&!terrain[k-1][l].estObstacle()&&tableau[k-1][l] == 0){
		                  tableau[k-1][l]=1;
		                  nouveau = true;
		               }
							if(l>0 && k<hauteur-1 && !terrain[k+1][l-1].estObstacle()&&tableau[k+1][l-1] == 0){
		                  tableau[k+1][l-1]=1;
		                  nouveau = true;
		               }
							if(l>0 && k>0 &&!terrain[k-1][l-1].estObstacle()&&tableau[k-1][l-1] == 0){
		                  tableau[k-1][l-1]=1;
		                  nouveau = true;
		               }
                     if (k> 0 && l< largeur - 1 &&terrain[k-1][l].getJoueurSurCase() != ArbitreManager.instance.getJoueurCourant()&& terrain[k-1][l].getJoueurSurCase()!= null)
							    return false;
                     if (l < largeur -1 &&tableau[k][l] ==1 && terrain[k][l+1].getJoueurSurCase() != ArbitreManager.instance.getJoueurCourant()&&terrain[k][l+1].getJoueurSurCase()!= null)
								 return false;
                     if (l > 0&&tableau[k][l] ==1 && terrain[k][l-1].getJoueurSurCase() != ArbitreManager.instance.getJoueurCourant()&& terrain[k][l-1].getJoueurSurCase()!= null)
							    return false;
                     if (k < hauteur -1 &&tableau[k][l] ==1 && l>0 && terrain[k+1][l-1].getJoueurSurCase() != ArbitreManager.instance.getJoueurCourant()&& terrain[k+1][l-1].getJoueurSurCase()!= null)
							    return false;
                     if (k< hauteur -1 &&l< largeur - 1 &&tableau[k][l] ==1 && terrain[k+1][l].getJoueurSurCase() != ArbitreManager.instance.getJoueurCourant()&&terrain[k+1][l].getJoueurSurCase()!= null)
							    return false;
                     if (k> 0 && l > 0 &&tableau[k][l] ==1 && terrain[k-1][l-1].getJoueurSurCase() != ArbitreManager.instance.getJoueurCourant()&& terrain[k-1][l-1].getJoueurSurCase()!= null)
							    return false; 
			         

                  }
               }
		      }
		   }
      } 
    
      return true;
   }
 
   public static String getType() {
	   return "CPU_Facile";
   }
   
   public ScoreCoup meilleurChemin(int i, int j, Configuration configuration, int occurence){
      Coup [] lesCoups = configuration.coupsPossiblesCase(i,j);
      Case [][] terrain =  configuration.getTerrain();

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
             coupJoue = meilleurChemin(lesCoups[k].getXArrivee(),lesCoups[k].getYArrivee(),configurationBis,occ );

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
   
   public int nombreDeCasesAdjacentes(int i,int j){
      int largeur = ArbitreManager.instance.getConfiguration().getLargeur();
		int hauteur = ArbitreManager.instance.getConfiguration().getHauteur();
		Case [][] terrain = ArbitreManager.instance.getConfiguration().getTerrain();
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
