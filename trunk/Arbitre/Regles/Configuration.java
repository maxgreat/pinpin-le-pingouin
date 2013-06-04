package Arbitre.Regles;

import java.util.*;
import Joueurs.*;
import java.io.*;
import Arbitre.*;

public class Configuration implements Cloneable, Serializable
{
    protected int largeur;
    protected int hauteur;
    protected Case [][] terrain;
    protected int joueurSurConfiguration;
    protected int scoreSurConfiguration;
    protected Coup coupEffectue;
    public boolean estFinale = false;

    public Configuration(int largeur, int hauteur, Case [][] terrain, Joueur joueurSurConfiguration)
    {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.terrain = terrain;
        this.joueurSurConfiguration = ArbitreManager.instance.getPosition(joueurSurConfiguration);
        this.scoreSurConfiguration = joueurSurConfiguration.getScore();
    }

    public Configuration(int largeur, int hauteur, Case [][] terrain, Joueur joueurSurConfiguration, Coup coupEffectue)
    {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.terrain = terrain;
        this.joueurSurConfiguration = ArbitreManager.instance.getPosition(joueurSurConfiguration);
        this.scoreSurConfiguration = joueurSurConfiguration.getScore();
        this.coupEffectue = coupEffectue;
    }

    /**
     * Getter des éléments
     **/
    public void setTerrain (Case [][] t)
    {
        this.terrain = t;
    }
    public int getLargeur()
    {
        return largeur;
    }

    public int getHauteur()
    {
        return hauteur;
    }

    public Case [][] getTerrain()
    {
        return terrain;
    }

	public int getNbCasesRestantes() {
		int res = 0;
		for (int i = 0; i < this.getHauteur(); i++) {
			for (int j = 0; j < this.getLargeur(); j++) {
				if (i%2 == 0 && j == largeur - 1)
					continue;
				if (this.getTerrain()[i][j].getEtat() != Etat.VIDE)
					res++;
			}
		}
		return res;
	}

    public Joueur getJoueurSurConfiguration()
    {
        if (joueurSurConfiguration == 0)
            return null;

        return ArbitreManager.instance.getJoueurParPosition(joueurSurConfiguration);
    }

    public void setJoueurSurConfiguration(Joueur joueur)
    {
        if (joueur == null)
            joueurSurConfiguration = 0;

        this.joueurSurConfiguration = ArbitreManager.instance.getPosition(joueur);
    }

    public int getScoreSurConfiguration()
    {
        return scoreSurConfiguration;
    }

    public void setScoreSurConfiguration(int scoreSurConfiguration)
    {
        this.scoreSurConfiguration = scoreSurConfiguration;
    }
            
    public Coup getCoupEffectue()
    {
        return coupEffectue;
    }

    public void setCoupEffectue(Coup coup)
    {  
        this.coupEffectue = coup;
    }

    public int scoreCoupEffectue()
    {
        if (coupEffectue.getXArrivee() == -1 && coupEffectue.getYArrivee() == -1)
            return 0;

        return terrain[coupEffectue.getYDepart()][coupEffectue.getXDepart()].scorePoisson();
    }

    /**
     * Indique le nombre de pingouins sur le plateau de chaque joueurs
     **/
    public int [] getNombrePingouinsParJoueur(Joueur [] joueurs)
    {
        int [] pingouins = new int[joueurs.length];
        ArrayList<Joueur> joueurList = new ArrayList<Joueur>(Arrays.asList(joueurs));
	
        for (int i = 0; i < joueurs.length; i++)
            pingouins[i] = 0;

        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                if (i%2 == 0 && j == largeur - 1)
                    continue;

                Joueur joueurSurCase = terrain[i][j].getJoueurSurCase();
		
                if (joueurSurCase != null)
                {
                    pingouins[joueurList.indexOf(joueurSurCase)]++;
                }
            }
        }

        return pingouins;
    }

    /**
     * Indique le nombre de pingouins pouvant etre jouer sur le plateau de chaque joueurs
     **/
    public int [] getNombrePingouinsDispoParJoueur(Joueur [] joueurs)
    {
        int [] pingouins = new int[joueurs.length];
        ArrayList<Joueur> joueurList = new ArrayList<Joueur>(Arrays.asList(joueurs));
	
        for (int i = 0; i < joueurs.length; i++)
            pingouins[i] = 0;

        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                if (i%2 == 0 && j == largeur - 1)
                    continue;

                Joueur joueurSurCase = terrain[i][j].getJoueurSurCase();
		
                if (joueurSurCase != null)
                {
                    if(!getVoisins(this.terrain,i,j,true).isEmpty())
                        pingouins[joueurList.indexOf(joueurSurCase)]++;
                }
            }
        }

        return pingouins;
    }

    /**
     * Récupère les coordonnées des pîngouins d'un joueur j
     **/
	public Couple [] coordPingouins(Joueur joueur){
        ArrayList<Couple> coord = new ArrayList<Couple>();
        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                if (i%2 == 0 && j == largeur - 1)
                    continue;

                Joueur joueurSurCase = terrain[i][j].getJoueurSurCase();
		
                if (joueurSurCase == joueur)
                {
                    coord.add(new Couple(i,j));
                }
            }
        }

        return (Couple[])coord.toArray(new Couple[coord.size()]);
	}

    /**
     * Retourne le score que rapporte tout les pingouins isolé par joueurs et le nb pingouin isolé par joueur dans un tableau de couple(scoreIlot,Pingouin sur ilot)
     **/
	public Couple [] scoreIlotParJoueur(Joueur [] joueurs){
        Couple [] res = new Couple[joueurs.length];
        ArrayList<Joueur> joueurList = new ArrayList<Joueur>(Arrays.asList(joueurs));
        Joueur sauv = getJoueurSurConfiguration();
        for (int i = 0; i < joueurs.length; i++)
            res[i] = new Couple(0,0);

        for (int i = 0; i < joueurs.length; i++)
        {
            Couple [] cP = coordPingouins(joueurs[i]);
            setJoueurSurConfiguration(joueurs[i]);
				ArrayList<Couple> liste = new ArrayList<Couple>();
				Couple pres;
				int n,b;
            for (int j = 0; j < cP.length; j++)
            {
					if((n=estIlot(cP[j].getX(), cP[j].getY(), new ArrayList<Couple>(), 0).getX()) != -1){
						res[i].setX(res[i].getX()+terrain[cP[j].getX()][cP[j].getY()].scorePoisson());
						if(estIlot(cP[j].getX(), cP[j].getY(),liste, 1).getX() != -2){
							res[i].setX(res[i].getX()+n);
						}else{
							pres = estIlot(cP[j].getX(), cP[j].getY(),liste, 0);
							if(n > (b=estIlot(pres.getX(), pres.getY(), new ArrayList<Couple>(), 0).getX()))
								res[i].setX(res[i].getX()+n-b);
						}
						res[i].setY(res[i].getY()+1);
						liste.add(cP[j]);
					}
            }            
        }
        setJoueurSurConfiguration(sauv);
        return res;
	}

	/**
	 * Permet de savoir si un pingouin en i,j est isolé sur un ilot
	 * -1 si non
	 * nombre de poisson sur l'ilot si oui
	 **/
	public Couple estIlot(int ii, int jj, ArrayList<Couple> liste, int inutile){
		Case [][] terrainCopie = cloneTerrain();
		int nbP = 0,nbC = 0;
		Stack<Couple> pile = new Stack();
		int advProxi;
		pile.push(new Couple(ii,jj));
		/*nbP += terrainCopie[ii][jj].scorePoisson();
		nbC++;*/
		terrainCopie[ii][jj].setEtat(Etat.VIDE);
		ArrayList<Couple> voisinsTe = getVoisins(terrainCopie,ii,jj,false);
		ArrayList<Couple> voisinsTest = getVoisins(terrainCopie,ii,jj,true);
		advProxi = voisinsTe.size() - voisinsTest.size();
		Couple p, pres=new Couple(0,0);
		boolean phase1 = true, amie = false;
		while(!pile.empty()){
			p = pile.pop();
			ArrayList<Couple> voisins = getVoisins(terrainCopie,p.getX(),p.getY(),false);
			for(int taille=0;taille<voisins.size();taille++){
				p = voisins.get(taille);
				if(liste.contains(p)){
					amie = true;
					pres = p;
				}
				if(terrainCopie[p.getX()][p.getY()].getJoueurSurCase()==null){
					nbP += terrainCopie[p.getX()][p.getY()].scorePoisson();
					nbC++;
					pile.push(new Couple(p.getX(),p.getY()));
					terrainCopie[p.getX()][p.getY()].setEtat(Etat.VIDE);
				}
				else if(terrainCopie[p.getX()][p.getY()].getJoueurSurCase()==getJoueurSurConfiguration()){
					if(advProxi > 0 && phase1)
						advProxi--;
				}
				else if(terrainCopie[p.getX()][p.getY()].getJoueurSurCase()!=getJoueurSurConfiguration()){
					if(advProxi > 0 && phase1)
						advProxi--;
					else
						return new Couple(-1,-1);
				}
			}
			phase1 = false;
		}
		if(amie)
			if(inutile==1)
				return new Couple(-2, -2);
			else
				return pres;
		else
			return new Couple(nbP,nbC);
	}

	/**
	 * Permet de savoir si un pingouin en i,j est isolé sur un ilot
	 * -1 si non
	 * nombre de poisson sur l'ilot si oui
	 * /!\/!\ A utiliser seulement pour savoir si TOUT les pingouins sont sur un ilot
	 **/
	public Couple estIlot(int ii, int jj, ArrayList<Couple> liste){
		Case [][] terrainCopie = cloneTerrain();
		int nbP = 0,nbC = 0;
		Stack<Couple> pile = new Stack();
		int advProxi = 0;
		pile.push(new Couple(ii,jj));
		Couple p;
		boolean phase1 = false;
		while(!pile.empty()){
			p = pile.pop();
			if(terrain[p.getX()][p.getY()].getJoueurSurCase()==getJoueurSurConfiguration()){
				ArrayList<Couple> voisinsTe = getVoisins(terrainCopie,p.getX(),p.getY(),false);
				ArrayList<Couple> voisinsTest = getVoisins(terrainCopie,p.getX(),p.getY(),true);
				advProxi = voisinsTe.size() - voisinsTest.size();

				phase1 = true;
			}
			ArrayList<Couple> voisins = getVoisins(terrainCopie,p.getX(),p.getY(),false);
			for(int taille=0;taille<voisins.size();taille++){
				p = voisins.get(taille);
				if (liste.contains(p))
					return new Couple(-2,-2);
				if(terrainCopie[p.getX()][p.getY()].getJoueurSurCase()==null){
					nbP += terrainCopie[p.getX()][p.getY()].scorePoisson();
					nbC++;
					pile.push(new Couple(p.getX(),p.getY()));
					terrainCopie[p.getX()][p.getY()].setEtat(Etat.VIDE);
				}
				else if(terrainCopie[p.getX()][p.getY()].getJoueurSurCase()==getJoueurSurConfiguration()){
					if(advProxi > 0 && phase1)
						advProxi--;
					nbP += terrainCopie[p.getX()][p.getY()].scorePoisson();
					nbC++;
					pile.push(new Couple(p.getX(),p.getY()));
					terrainCopie[p.getX()][p.getY()].setEtat(Etat.VIDE);
				}
				else if(terrainCopie[p.getX()][p.getY()].getJoueurSurCase()!=getJoueurSurConfiguration()){
					if(advProxi > 0 && phase1)
						advProxi--;
					else
						return new Couple(-1,-1);
				}
			}
			phase1 = false;
		}
		return new Couple(nbP,nbC);
	}
	
    /**
     * Récupère les cases voisines non vides et {non occupées(obstacle=true) ou occupées(obstacle=false)} d'une case i,j du terrain
     **/
	public ArrayList<Couple> getVoisins(Case [][] t,int i, int j,boolean obstacle){
		ArrayList<Couple> liste = new ArrayList<Couple>();
        {
            if (i%2 == 0 && j + 1 < largeur - 1 && ((obstacle && !t[i][j + 1].estObstacle()) || (!obstacle && !t[i][j + 1].estVide())))
                liste.add(new Couple(i,j+1));
            else if (i%2 == 1 && j + 1 < largeur && ((obstacle && !t[i][j + 1].estObstacle()) || (!obstacle && !t[i][j + 1].estVide())))
                liste.add(new Couple(i,j+1));
        }
        {
            if (j - 1 >= 0 && ((obstacle && !t[i][j - 1].estObstacle()) || (!obstacle && !t[i][j - 1].estVide())))
                liste.add(new Couple(i,j-1));
        }
        {
            if (i - 1 >= 0)
            {
                if (i%2 == 0 && j + 1 < largeur && ((obstacle && !t[i-1][j + 1].estObstacle()) || (!obstacle && !t[i-1][j + 1].estVide())))
                    liste.add(new Couple(i-1,j+1));
                else if (i%2 == 1 && j < largeur - 1 && ((obstacle && !t[i-1][j].estObstacle()) || (!obstacle && !t[i-1][j].estVide())))
                    liste.add(new Couple(i-1,j));
            }
        }
        {
            if (i - 1 >= 0)
            {
                if (i%2 == 0 && j < largeur - 1 && ((obstacle && !t[i-1][j].estObstacle()) || (!obstacle && !t[i-1][j].estVide())))
                    liste.add(new Couple(i-1,j));
                else if (i%2 == 1 && j - 1 >= 0 && ((obstacle && !t[i-1][j-1].estObstacle()) || (!obstacle && !t[i-1][j-1].estVide())))
                    liste.add(new Couple(i-1,j-1));
            }
        }
        {
            if (i + 1 < hauteur)
            {
                if (i%2 == 0 && j + 1 < largeur && ((obstacle && !t[i+1][j+1].estObstacle()) || (!obstacle && !t[i+1][j+1].estVide())))
                    liste.add(new Couple(i+1,j+1));
                else if (i%2 == 1 && j < largeur - 1 && ((obstacle && !t[i+1][j].estObstacle()) || (!obstacle && !t[i+1][j].estVide())))
                    liste.add(new Couple(i+1,j));
            }
        }
        {
            if (i + 1 < hauteur)
            {
                if (i%2 == 0 && ((obstacle && !t[i+1][j].estObstacle()) || (!obstacle && !t[i+1][j].estVide())))
                    liste.add(new Couple(i+1,j));
                else if (i%2 == 1 && j - 1 >= 0 && ((obstacle && !t[i+1][j-1].estObstacle()) || (!obstacle && !t[i+1][j-1].estVide())))
                    liste.add(new Couple(i+1,j-1));
            }
        }
		return liste;
	}

    /**
     * Indique si un joueur peut encore bouger sur une configuration donnée
     **/
	public int nombrePoissonsRestant(){
        int nb = 0;
        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
				if ((i%2 == 0 && j == largeur - 1) || (terrain[i][j].estObstacle()))
					continue;

				nb+=terrain[i][j].scorePoisson();
			}
		}
		return nb;
	}

    /**
     * Indique si un joueur peut encore bouger sur une configuration donnée
     **/
    public boolean peutJouer(Joueur joueur)
    {

        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                if ((i%2 == 0 && j == largeur - 1) || joueur != terrain[i][j].getJoueurSurCase())
                    continue;
                
                // Vers la droite
                {
                    if (i%2 == 0 && j + 1 < largeur - 1 && !terrain[i][j + 1].estObstacle())
                        return true;
                    else if (i%2 == 1 && j + 1 < largeur && !terrain[i][j + 1].estObstacle())
                        return true;
                }

                // Vers la gauche
                {
                    if (j - 1 >= 0 && !terrain[i][j - 1].estObstacle())
                        return true;
                }

                // Vers le haut droit
                {
                    // Ne sort pas du terrain
                    if (i - 1 >= 0)
                    {
                        if (i%2 == 0 && j + 1 < largeur && !terrain[i-1][j + 1].estObstacle())
                            return true;
                        else if (i%2 == 1 && j < largeur - 1 && !terrain[i-1][j].estObstacle())
                            return true;
                    }
                }

                // Vers le haut gauche
                {
                    // Ne sort pas du terrain
                    if (i - 1 >= 0)
                    {
                        if (i%2 == 0 && j < largeur - 1 && !terrain[i-1][j].estObstacle())
                            return true;
                        else if (i%2 == 1 && j - 1 >= 0 && !terrain[i-1][j - 1].estObstacle())
                            return true;
                    }
                }

                // Vers le bas droite
                {
                    // Ne sort pas du terrain
                    if (i + 1 < hauteur)
                    {
                        if (i%2 == 0 && j + 1 < largeur && !terrain[i+1][j+1].estObstacle())
                            return true;
                        else if (i%2 == 1 && j < largeur - 1 && !terrain[i+1][j].estObstacle())
                            return true;
                    }
                }

                // Vers le bas gauche
                {
                    // Ne sort pas du terrain
                    if (i + 1 < hauteur)
                    {
                        if (i%2 == 0 && !terrain[i+1][j].estObstacle())
                            return true;
                        else if (i%2 == 1 && j - 1 >= 0 && !terrain[i+1][j-1].estObstacle())
                            return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Vérifie si un coup est possible
     **/
    public boolean estCoupPossible(Coup coup)
    {
        int xDepart = coup.getXDepart();
        int yDepart = coup.getYDepart();
        int xArrivee = coup.getXArrivee();
        int yArrivee = coup.getYArrivee();

        // Mode pose de pingouin
        if (xArrivee == -1 && yArrivee == -1)
        {
            // Reste dans le terrain
            if (xDepart < 0 || xDepart >= largeur || yDepart < 0 || yDepart >= hauteur)
                return false;

            if (yDepart % 2 == 0 && xDepart >= largeur - 1)
                return false;

            // Vérifie qu'il n'y a pas déjà un pion
            if (terrain[yDepart][xDepart].getJoueurSurCase() != null)
                return false;

            // Vérifie qu'il n'y a qu'un poissons sur cette case
            if (terrain[yDepart][xDepart].getEtat() != Etat.UN_POISSON)
                return false;
        }
        else
        {
            // Reste dans le terrain
            if (xDepart < 0 || xDepart >= largeur || yDepart < 0 || yDepart >= hauteur)
            {
                return false;
            }
            
            if (xArrivee < 0 || xArrivee >= largeur || yArrivee < 0 || yArrivee >= hauteur)
            {   return false;
            }
             
            if (xDepart == xArrivee && yDepart == yArrivee)
            {   return false;
            }
             
            // Ligne paire
            if (yArrivee % 2 == 0 && xArrivee >= largeur - 1)
            {
                return false;
            }
             
            if (yDepart % 2 == 0 && xDepart >= largeur - 1)
            {
                return false;
            }
             
            // Doit déplacer le pingouin de la config
            if (terrain[yDepart][xDepart].getJoueurSurCase() != getJoueurSurConfiguration())
            {
                return false;
            }
             
            // Sans obstacles avec déplacement possible
            if (!estDeplacementPossible(coup))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si un coup est possible selon le mode
     **/
    public boolean estCoupPossible(Coup coup, ModeDeJeu mode)
    {
        int xDepart = coup.getXDepart();
        int yDepart = coup.getYDepart();
        int xArrivee = coup.getXArrivee();
        int yArrivee = coup.getYArrivee();

        // Mode pose de pingouin
        if (mode == ModeDeJeu.POSE_PINGOUIN && (xArrivee != -1 || yArrivee != -1))
            return false;
	    
        if (mode == ModeDeJeu.JEU_COMPLET && (xDepart == -1 || xArrivee == -1 || yDepart == -1 || yArrivee == -1))
            return false;

        return estCoupPossible(coup);
    }


    /**
     * Fonctions qui vérifient si deux points sont sur le même axe
     **/
    protected int arrondiSup(int x, int y)
    {
        if ((double)x/(double)y - x/y >= 0.5)
            return x/y + 1;
        else
            return x/y;
    }

    protected int arrondiInf(int x, int y)
    {
        if ((double)x/(double)y - x/y <= -0.5)
            return x/y - 1;
        else
            return x/y;
    }

    protected boolean memeDiagonaleBasDroite(Coup c)
    {
        if (c.getYArrivee() % 2 == 0)
            return (c.getXArrivee() - c.getXDepart()) == arrondiInf((c.getYArrivee() - c.getYDepart()), 2);
        else 
            return (c.getXArrivee() - c.getXDepart()) == arrondiSup(c.getYArrivee() - c.getYDepart(), 2);
    }

    protected boolean memeDiagonaleBasGauche(Coup c)
    {
        if (c.getYArrivee() % 2 == 0)
            return (c.getXArrivee() - c.getXDepart()) == arrondiInf((c.getYArrivee() - c.getYDepart()), -2);
        else 
            return (c.getXArrivee() - c.getXDepart()) == arrondiSup(c.getYArrivee() - c.getYDepart(), -2);
    }

    protected boolean versDroite(Coup c)
    {
        return c.getYDepart() == c.getYArrivee() && c.getXDepart() < c.getXArrivee();
    }

    protected boolean versGauche(Coup c)
    {
        return c.getYDepart() == c.getYArrivee() && c.getXDepart() > c.getXArrivee();
    }

    protected boolean versBasDroite(Coup c)
    {
        return memeDiagonaleBasDroite(c) && c.getXArrivee() >= c.getXDepart() && c.getYArrivee() > c.getYDepart();
    }

    protected boolean versBasGauche(Coup c)
    {
        return memeDiagonaleBasGauche(c) && c.getXArrivee() <=  c.getXDepart() && c.getYArrivee() > c.getYDepart();
    }

    protected boolean versHautDroite(Coup c)
    {
        return memeDiagonaleBasGauche(c) && c.getXArrivee() >= c.getXDepart() && c.getYArrivee() < c.getYDepart();
    }

    protected boolean versHautGauche(Coup c)
    {
        return memeDiagonaleBasDroite(c) && c.getXArrivee() <= c.getXDepart() && c.getYArrivee() < c.getYDepart();
    }

    /**
     * Vérifie que le déplacement est possible entre deux coups
     **/
    protected boolean estDeplacementPossible(Coup c)
    {
        int xDepart = c.getXDepart();
        int yDepart = c.getYDepart();
        int xArrivee = c.getXArrivee();
        int yArrivee = c.getYArrivee();

        // Vers la droite
        if (versDroite(c))
        {
            int l = xDepart + 1;

            while (l <= xArrivee)
            {
                if (terrain[yDepart][l].estObstacle())
                    return false;
                l++;
            }

            return true;
        }
        // Vers la gauche
        else if (versGauche(c))
        {
            int l = xDepart - 1;
            while (l >= xArrivee)
            {
                if (terrain[yDepart][l].estObstacle())
                    return false;
                l--;
            }

            return true;
        }
        // Vers le haut droit
        else if (versHautDroite(c))
        {
            int l = c.getXDepart();
	    
            for (int k = c.getYDepart() - 1; k >= c.getYArrivee(); k--)
            {
                if (k%2 == 1)
                    l++;
		
                if (terrain[k][l].estObstacle())
                    return false;
            }		

            return true;
        }
        // Vers le haut gauche
        else if (versHautGauche(c))
        {
            int l = c.getXDepart();
	    
            for (int k = c.getYDepart() - 1; k >= c.getYArrivee(); k--)
            {
                if (k%2 == 0)
                    l--;
		
                if (terrain[k][l].estObstacle())
                    return false;
            }

            return true;
        }
        // Vers le bas droit
        else if (versBasDroite(c))
        {
            int l = c.getXDepart();
	    
            for (int k = c.getYDepart() + 1; k <= c.getYArrivee(); k++)
            {
                if (k%2 == 1)
                    l++;
		
                if (terrain[k][l].estObstacle())
                    return false;
            }

            return true;
        }
        // Vers le bas gauche
        else if (versBasGauche(c))
        {
            int l = c.getXDepart();
	    
            for (int k = c.getYDepart() + 1; k <= c.getYArrivee(); k++)
            {
                if (k%2 == 0)
                    l--;
		
                if (terrain[k][l].estObstacle())
                    return false;
            }

            return true;
        }

        return false;
    }

    /**
     * Retourne l'ensemble des placements possibles
     **/
    public Coup [] toutPlacementsPossibles()
    {
        ArrayList<Coup> list = new ArrayList<Coup>();

        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                if (i%2 == 0 && j == largeur - 1)
                    continue;

                if (terrain[i][j].getJoueurSurCase() == null && terrain[i][j].getEtat() == Etat.UN_POISSON)
                    list.add(new Coup(j, i, -1, -1));
            }
        }

        Coup [] tab = new Coup[list.size()];
        list.toArray(tab);
        return tab;
    }

    /**
     * Retourne une liste de tous les coups possibles
     **/
    public Coup [] toutCoupsPossibles()
    {
        ArrayList<Coup> liste = new ArrayList<Coup>();

        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                // Sortie du terrain
                if (i%2 == 0 && j == largeur - 1)
                    continue;

                // Regarde les cases du joueur en cours
                if (terrain[i][j].getJoueurSurCase() != getJoueurSurConfiguration())
                    continue;

                // Vers la droite
                {
                    int l = j + 1;
                    if (i%2 == 0)
                    {
                        while (l < largeur - 1 && !terrain[i][l].estObstacle())
                        {
                            liste.add(new Coup(j, i, l, i));
                            l++;
                        }
                    }
                    else
                    {
                        while (l < largeur && !terrain[i][l].estObstacle())
                        {
                            liste.add(new Coup(j, i, l, i));
                            l++;
                        }
                    }
                }

                // Vers la gauche
                {
                    int l = j - 1;
                    while (l >= 0 && !terrain[i][l].estObstacle())
                    {
                        liste.add(new Coup(j, i, l, i));
                        l--;
                    }
                }

                // Vers le haut droit
                {
                    int l = j;
                    int k = i - 1;

                    while(k >= 0)
                    {
                        if (k%2 == 1)
                            l++;

                        if ((k%2 == 0 && l >= largeur - 1) || (k%2 == 1 && l >= largeur) || terrain[k][l].estObstacle())
                            break;

                        liste.add(new Coup(j, i, l, k));
                        k--;
                    }
                }

                // Vers le haut gauche
                {

                    int l = j;
                    int k = i - 1;

                    while(k >= 0)
                    {
                        if (k%2 == 0)
                            l--;

                        if (l < 0 || terrain[k][l].estObstacle())
                            break;

                        liste.add(new Coup(j, i, l, k));
                        k--;
                    }
                }

                // Vers le bas droit
                {
                    int l = j;
                    int k = i + 1;

                    while(k < hauteur)
                    {
                        if (k%2 == 1)
                            l++;

                        if ((k%2 == 0 && l == largeur - 1) || (k%2 == 1 && l == largeur) || terrain[k][l].estObstacle())
                            break;

                        liste.add(new Coup(j, i, l, k));
                        k++;
                    }
                }

                // Vers le bas gauche
                {
                    int l = j;
                    int k = i + 1;

                    while(l >= 0 && k < hauteur)
                    {
                        if (k%2 == 0)
                            l--;

                        if (l < 0 || terrain[k][l].estObstacle())
                            break;

                        liste.add(new Coup(j, i, l, k));
                        k++;
                    }
                }
            }
        }

        Coup [] tab = new Coup[liste.size()];
        liste.toArray(tab);
        return tab;
    }    

    /** 
     * Renvoit le score pour le joueur
     * Suppose que le coup est valide
     **/
    public int effectuerCoup(Coup c)
    {
        int score = 0;

        // Mode pose de pingouin
        if (c.getYArrivee() == -1 && c.getXArrivee() == -1)
        {
            terrain[c.getYDepart()][c.getXDepart()].setJoueurSurCase(getJoueurSurConfiguration());
        }
        else
        {
            score = terrain[c.getYDepart()][c.getXDepart()].scorePoisson();
            
            terrain[c.getYDepart()][c.getXDepart()] = new Case(Etat.VIDE, null);
            terrain[c.getYArrivee()][c.getXArrivee()].setJoueurSurCase(getJoueurSurConfiguration());
        }

        return score;
    }


    /**
     * Nettoie le terrain des pingouins qui sont isolés en renvoyant le score de
     * Chaque joueur 
     **/
    public int [] nettoyerConfiguration(Joueur [] joueurs)
    {
        int [] scoresJoueurs = new int[joueurs.length];
        ArrayList<Joueur> indexJoueur = new ArrayList<Joueur>(Arrays.asList(joueurs));

        for (int i = 0; i < joueurs.length; i++)
            scoresJoueurs[i] = 0;

        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                if (i%2 == 0 && j == largeur - 1 || terrain[i][j].getJoueurSurCase() == null)
                    continue;

                scoresJoueurs[indexJoueur.indexOf(terrain[i][j].getJoueurSurCase())] += terrain[i][j].scorePoisson();
            }
        }

        return scoresJoueurs;
    }

    /**
     * Clone un objet configuration
     **/
    public Configuration clone()
    {
        Case [][] terrainCopie = new Case[hauteur][largeur];
        
        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                if (i%2 == 0 && j == largeur - 1)
                    continue;

                terrainCopie[i][j] = terrain[i][j].clone();
            }
        }

        Configuration c = new Configuration(largeur, hauteur, terrainCopie, getJoueurSurConfiguration(), getCoupEffectue());
        c.estFinale = estFinale;

        return c;
    }

    /**
     * Clone un terrain
     **/
    public Case [][] cloneTerrain()
    {
        Case [][] terrainCopie = new Case[hauteur][largeur];
        
        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                if (i%2 == 0 && j == largeur - 1)
                    continue;

                terrainCopie[i][j] = terrain[i][j].clone();
            }
        }

        return terrainCopie;
    }

    /**
     * Serialize les données du terrain
     **/
    private void writeObject(ObjectOutputStream out) throws IOException
    {
        // Sauvegarde le joueur en cours et son score
        out.writeInt(ArbitreManager.instance.getPosition(getJoueurSurConfiguration()));
        out.writeInt(getScoreSurConfiguration());
        out.writeObject((Coup)getCoupEffectue());

        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                if (i%2 == 0 && j == largeur - 1)
                    continue;

                out.writeObject(terrain[i][j]);
            }
        }
    }

    /**
     * Charge les données à partir d'une chaine serialize
     **/
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {   
        hauteur = ArbitreManager.instance.getHauteur();
        largeur = ArbitreManager.instance.getLargeur();
        terrain = new Case[hauteur][largeur];

        setJoueurSurConfiguration(ArbitreManager.instance.getJoueurParPosition(in.readInt()));
        setScoreSurConfiguration(in.readInt());
        setCoupEffectue((Coup)in.readObject());

        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                if (i%2 == 0&& j == largeur - 1)
                    continue;

                terrain[i][j] = (Case)in.readObject();
            }
        }
    }

    /**
     * Essaye de parser un objet sans donnée
     **/
    private void readObjectNoData() throws ObjectStreamException
    {
        throw new NotSerializableException("La sérialization d'une configuration doit se faire sur une chaine non vide");
    }
    
    public Coup[] coupsPossiblesCase(int j, int i){

        ArrayList<Coup> liste = new ArrayList<Coup>();
    

        // Sortie du terrain
        if (i%2 == 0 && j == largeur - 1)
            return null;

        /*     // Regarde les cases du joueur en cours
               if (terrain[i][j].getJoueurSurCase() != getJoueurSurConfiguration())
               continue;
        */
        // Vers la droite
        {
            int l = j + 1;
            if (i%2 == 0)
            {
                while (l < largeur - 1 && !terrain[i][l].estObstacle())
                {
                    //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +i);
                    liste.add(new Coup(j, i, l, i));
                    l++;
                }
            }
            else
            {
                while (l < largeur && !terrain[i][l].estObstacle())
                {
                    //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +i);
                    liste.add(new Coup(j, i, l, i));
                    l++;
                }
            }
        }

        // Vers la gauche
        {
            int l = j - 1;
            while (l >= 0 && !terrain[i][l].estObstacle())
            {
                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +i);
                liste.add(new Coup(j, i, l, i));
                l--;
            }
        }

        // Vers le haut droit
        {
            int l = j;
            int k = i - 1;

            while(k >= 0)
            {
                if (k%2 == 1)
                    l++;

                if ((k%2 == 0 && l >= largeur - 1) || (k%2 == 1 && l >= largeur) || terrain[k][l].estObstacle())
                    break;
                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +k);

                liste.add(new Coup(j, i, l, k));
                k--;
            }
        }

        // Vers le haut gauche
        {

            int l = j;
            int k = i - 1;

            while(k >= 0)
            {
                if (k%2 == 0)
                    l--;

                if (l < 0 || terrain[k][l].estObstacle())
                    break;

                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +k);
                liste.add(new Coup(j, i, l, k));
                k--;
            }
        }

        // Vers le bas droit
        {
            int l = j;
            int k = i + 1;

            while(k < hauteur)
            {
                if (k%2 == 1)
                    l++;

                if ((k%2 == 0 && l == largeur - 1) || (k%2 == 1 && l == largeur) || terrain[k][l].estObstacle())
                    break;
                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +k);

                liste.add(new Coup(j, i, l, k));
                k++;
            }
        }

        // Vers le bas gauche
        {
            int l = j;
            int k = i + 1;

            while(l >= 0 && k < hauteur)
            {
                if (k%2 == 0)
                    l--;

                if (l < 0 || terrain[k][l].estObstacle())
                    break;
                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +k);

                liste.add(new Coup(j, i, l, k));
                k++;
            }
        }
        Coup[] tab = new Coup[liste.size()];
        liste.toArray(tab);
        return tab;
    }

    public int nombreCoupsPossiblesCase(int i, int j){
        // Sortie du terrain
        int nombreCoups = 0;
        if (i%2 == 0 && j == largeur - 1)
            return nombreCoups;

        /*     // Regarde les cases du joueur en cours
               if (terrain[i][j].getJoueurSurCase() != getJoueurSurConfiguration())
               continue;
        */
        // Vers la droite
        {
            int l = j + 1;
            if (i%2 == 0)
            {
                while (l < largeur - 1 && !terrain[i][l].estObstacle())
                {
                    //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +i);
                    nombreCoups++;
                    l++;
                }
            }
            else
            {
                while (l < largeur && !terrain[i][l].estObstacle())
                {
                    //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +i);
                    nombreCoups++;
                    l++;
                }
            }
        }

        // Vers la gauche
        {
            int l = j - 1;
            while (l >= 0 && !terrain[i][l].estObstacle())
            {
                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +i);
                nombreCoups++;
                l--;
            }
        }

        // Vers le haut droit
        {
            int l = j;
            int k = i - 1;

            while(k >= 0)
            {
                if (k%2 == 1)
                    l++;

                if ((k%2 == 0 && l >= largeur - 1) || (k%2 == 1 && l >= largeur) || terrain[k][l].estObstacle())
                    break;
                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +k);

                nombreCoups++;
                k--;
            }
        }

        // Vers le haut gauche
        {

            int l = j;
            int k = i - 1;

            while(k >= 0)
            {
                if (k%2 == 0)
                    l--;

                if (l < 0 || terrain[k][l].estObstacle())
                    break;

                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +k);
                nombreCoups++;
                k--;
            }
        }

        // Vers le bas droit
        {
            int l = j;
            int k = i + 1;

            while(k < hauteur)
            {
                if (k%2 == 1)
                    l++;

                if ((k%2 == 0 && l == largeur - 1) || (k%2 == 1 && l == largeur) || terrain[k][l].estObstacle())
                    break;
                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +k);

                nombreCoups++;
                k++;
            }
        }

        // Vers le bas gauche
        {
            int l = j;
            int k = i + 1;

            while(l >= 0 && k < hauteur)
            {
                if (k%2 == 0)
                    l--;

                if (l < 0 || terrain[k][l].estObstacle())
                    break;
                //System.out.println("Coup possible : "+j+ " " +i+" "+l+ " " +k);

                nombreCoups++;
                k++;
            }
        }
        return nombreCoups;
    }
	          
    /**
     * Charge un terrain dont le format est libre
     **/
    public static Case [][] terrainOfficiel(String filename)
    {
        int hauteur = ArbitreManager.HAUTEUR_GRILLE;
        int largeur = ArbitreManager.LARGEUR_GRILLE;
        Case [][] terrain = new Case[hauteur][largeur];

        int i = 0;
        int j = 0;
        
        try 
	    {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            Scanner intReader = new Scanner(reader);

            int case_ = 0;

            while (intReader.hasNextInt())
            {
                case_ = intReader.nextInt();
                
                if (case_ == 1)
                    terrain[i][j] = new Case(Etat.UN_POISSON, null);
                else if (case_ == 2)
                    terrain[i][j] = new Case(Etat.DEUX_POISSONS, null);
                else
                    terrain[i][j] = new Case(Etat.TROIS_POISSONS, null);

                if (i%2 == 0 && j == largeur - 2)
                {
                    i++;
                    j = 0;
                }
                else if (i%2 == 1 && j == largeur  - 1)
                {
                    i++;
                    j = 0;
                }
                else
                {
                    j++;
                }
            }

            if (i != ArbitreManager.HAUTEUR_GRILLE || j != 0)
                throw new IOException();

            reader.close();
	    }
        catch (IOException e) 
	    {
            System.err.println("Impossible de lire le terrain du fichier "+filename);
            return null;
	    }

        return terrain;
    }


    public int nombrePoissonIlot(int ii, int jj){
		Case [][] terrainCopie = cloneTerrain();
		int nbP = 0;
		Stack<Couple> pile = new Stack();
		pile.push(new Couple(ii,jj));
		nbP += terrainCopie[ii][jj].scorePoisson();
		terrainCopie[ii][jj].setEtat(Etat.VIDE);
      Couple p;
		while(!pile.empty()){
			p = pile.pop();
			ArrayList<Couple> voisins = getVoisins(terrainCopie,p.getX(),p.getY(),false);
			for(int taille=0;taille<voisins.size();taille++){
				p = voisins.get(taille);
				if(terrainCopie[p.getX()][p.getY()].getJoueurSurCase()==null){
					nbP += terrainCopie[p.getX()][p.getY()].scorePoisson();
					pile.push(new Couple(p.getX(),p.getY()));
					terrainCopie[p.getX()][p.getY()].setEtat(Etat.VIDE);
				}
			}
		}
		return nbP;
	}

}
