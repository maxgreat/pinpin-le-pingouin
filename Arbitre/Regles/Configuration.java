package Arbitre.Regles;

import java.util.*;
import java.awt.Point;
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
     * Permet de savoir si un pingouin en i,j est isolé sur un ilot
     **/
	public boolean estIlot(int ii, int jj){
		Case [][] terrainCopie = new Case[hauteur][largeur];

		Stack<Point> pile = new Stack();

		for (int i = 0; i < hauteur; i++){
			for (int j = 0; j < largeur; j++){
				if (i%2 == 0 && j == largeur - 1)
				  continue;
				terrainCopie[i][j] = terrain[i][j].clone();
				if (i==ii && j==jj){
					pile.push(new Point(i,j));
					terrainCopie[i][j].setEtat(Etat.VIDE);
				}
			}
		}
		Point p;
		while(!pile.empty()){
			p = pile.pop();
			ArrayList<Point> voisins = getVoisins(terrainCopie,(int)p.getX(),(int)p.getY(),false);
			for(int taille=0;taille<voisins.size();taille++){
				p = voisins.remove(0);
				if(terrainCopie[(int)p.getX()][(int)p.getY()].getJoueurSurCase()==null)
					pile.push(new Point((int)p.getX(),(int)p.getY()));
				else if(terrainCopie[(int)p.getX()][(int)p.getY()].getJoueurSurCase()!=getJoueurSurConfiguration())
					return false;
			}
		}
		return true;
	}

    /**
     * Récupère les cases voisines non vides et {non occupées(obstacle=true) ou occupées(obstacle=false)} d'une case i,j du terrain
     **/
	public ArrayList<Point> getVoisins(Case [][] t,int i, int j,boolean obstacle){
		ArrayList<Point> liste = new ArrayList<Point>();
		 {
		     if (i%2 == 0 && j + 1 < largeur - 1 && ((obstacle && !t[i][j + 1].estObstacle()) || (!obstacle && !t[i][j + 1].estVide())))
					liste.add(new Point(i,j+1));
		     else if (i%2 == 1 && j + 1 < largeur && ((obstacle && !t[i][j + 1].estObstacle()) || (!obstacle && !t[i][j + 1].estVide())))
					liste.add(new Point(i,j+1));
		 }
		 {
		     if (j - 1 >= 0 && ((obstacle && !t[i][j - 1].estObstacle()) || (!obstacle && !t[i][j - 1].estVide())))
					liste.add(new Point(i,j-1));
		 }
		 {
		     if (i - 1 >= 0)
		     {
		         if (i%2 == 0 && j + 1 < largeur && ((obstacle && !t[i-1][j + 1].estObstacle()) || (!obstacle && !t[i-1][j + 1].estVide())))
						liste.add(new Point(i-1,j+1));
		         else if (i%2 == 1 && j < largeur - 1 && ((obstacle && !t[i-1][j].estObstacle()) || (!obstacle && !t[i-1][j].estVide())))
						liste.add(new Point(i-1,j));
		     }
		 }
		 {
		     if (i - 1 >= 0)
		     {
		         if (i%2 == 0 && j < largeur - 1 && ((obstacle && !t[i-1][j].estObstacle()) || (!obstacle && !t[i-1][j].estVide())))
						liste.add(new Point(i-1,j));
		         else if (i%2 == 1 && j - 1 >= 0 && ((obstacle && !t[i-1][j-1].estObstacle()) || (!obstacle && !t[i-1][j-1].estVide())))
						liste.add(new Point(i-1,j-1));
		     }
		 }
		 {
		     if (i + 1 < hauteur)
		     {
		         if (i%2 == 0 && j + 1 < largeur && ((obstacle && !t[i+1][j+1].estObstacle()) || (!obstacle && !t[i+1][j+1].estVide())))
						liste.add(new Point(i+1,j+1));
		         else if (i%2 == 1 && j < largeur - 1 && ((obstacle && !t[i+1][j].estObstacle()) || (!obstacle && !t[i+1][j].estVide())))
						liste.add(new Point(i+1,j));
		     }
		 }
		 {
		     if (i + 1 < hauteur)
		     {
		         if (i%2 == 0 && ((obstacle && !t[i+1][j].estObstacle()) || (!obstacle && !t[i+1][j].estVide())))
						liste.add(new Point(i+1,j));
		         else if (i%2 == 1 && j - 1 >= 0 && ((obstacle && !t[i+1][j-1].estObstacle()) || (!obstacle && !t[i+1][j-1].estVide())))
						liste.add(new Point(i+1,j-1));
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

        return new Configuration(largeur, hauteur, terrainCopie, getJoueurSurConfiguration(), getCoupEffectue());
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
	          


}
