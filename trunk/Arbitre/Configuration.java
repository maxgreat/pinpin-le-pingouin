package Arbitre;

import java.util.*;
import Joueurs.*;

public class Configuration implements Cloneable
{
    protected int largeur;
    protected int hauteur;
    protected Case [][] terrain;
    protected Joueur joueurSurConfiguration;


    public Configuration(int largeur, int hauteur, Case [][] terrain, Joueur joueurSurConfiguration)
    {
	this.largeur = largeur;
	this.hauteur = hauteur;
	this.terrain = terrain;
	this joueurSurConfiguration = joueurSurConfiguration;
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
	return joueurSurConfiguration;
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

	// Reste dans le terrain
	if (xDepart < 0 || xDepart >= largeur || yDepart < 0 || yDepart >= hauteur)
	    return false;

	if (xArrivee < 0 || xArrivee >= largeur || yArrivee < 0 || yArrivee >= hauteur)
	return false;

	if (xDepart == xArrivee && yDepart == yArrivee)
	return false;

	// Ligne paire
	if (yArrivee % 2 == 0 && xArrivee >= largeur - 1)
	    return false;

	if (yDepart % 2 == 0 && xDepart >= largeur - 1)
	    return false;

	// Doit déplacer le pingouin de la config
	if (depart.getJoueurSurCase() != getJoueurSurConfiguration())
	    return false;

	// Sans obstacles avec déplacement possible
	if (!estDeplacementPossible(Coup))
	    return false;

	return true;
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

    protected boolean memeDiagonale(Coup c)
    {
	if (c.getYDepart() % 2 == 0)
	    return (c.getXArrivee() - c.getXDepart()) == ((c.getYArrivee() - c.getYDepart()) / 2);
	else 
	    return (c.getXArrivee() - c.getXDepart()) == arrondiSup(c.getYArrivee() - c.getYDepart(), 2);
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
	return memeDiagonale(c) && x.getXArrivee() >= x.getXDepart() && y.getXArrivee() > c.getYDepart();
    }

    protected boolean versBasGauche(Coup c)
    {
	return memeDiagonale(c) && x.getXArrivee() <  x.getXDepart() && y.getXArrivee() > c.getYDepart();
    }

    protected boolean versHautDroite(Coup c)
    {
	return memeDiagonale(c) && x.getXArrivee() >= x.getXDepart() && y.getXArrivee() < c.getYDepart();
    }

    protected boolean versHautGauche(Coup c)
    {
	return memeDiagonale(c) && x.getXArrivee() < x.getXDepart() && y.getXArrivee() < c.getYDepart();
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
	    int l = xDepart;

	    while (l =< xArrivee)
	    {
		if (terrain[yDepart][l].estObstacle())
		    return false;
		l++;
	    }
	}
	// Vers la gauche
	else if (versGauche(c))
	{
	    int l = xDepart;
	    while (l >= xArrivee)
	    {
		if (terrain[yDepart][l].estObstacle())
		    return false;
		l--;
	    }
	}
	// Vers le haut droit
	else if (versHautDroite(c))
	{
	    int l = c.getXDepart();
	    
	    for (int k = c.getYDepart() - 1; k > c.getYArrivee(); k--)
	    {
		if (k%2 == 0)
		    l++;
		
		if (terrain[k][l].estObstacle())
		    return false;
	    }		    
	}
	// Vers le haut gauche
	else if (versHautGauche(c))
	{
	    int l = c.getXDepart();
	    
	    for (int k = c.getYDepart() - 1; k > c.getYArrivee(); k--)
	    {
		if (k%2 == 1)
		    l--;
		
		if (terrain[k][l].estObstacle())
		    return false;
	    }
	}
	// Vers le bas droit
	else if (versBasDroite(c))
	{
	    int l = c.getXDepart();
	    
	    for (int k = c.getYDepart() + 1; k < c.getYArrivee(); k++)
	    {
		if (k%2 == 0)
		    l++;
		
		if (terrain[k][l].estObstacle())
		    return false;
	    }
	}
	// Vers le bas gauche
	else if (versBasGauche(c))
	{
	    int l = c.getXDepart();
	    
	    for (int k = c.getYDepart() + 1; k < c.getYArrivee(); k++)
	    {
		if (k%2 == 1)
		    l--;
		
		if (terrain[k][l].estObstacle())
		    return false;
	    }

	}

	return true;
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
		Coup c = new Coup(j, i);
		if (estCoupPossible(c))
		    liste.add(c);
	    }
	}

	Coup [] tab = new Coup[liste.size()];
	liste.toArray(tab);
	return tab;
    }    

    /** 
     * Effectue un coup et renvoit si le jeu est fini
     * Suppose que le coup est valide
     **/
    public boolean effectuerCoup(Coup c)
    {
	boolean estFini = true;

	for (int i = 0; i < hauteur; i++)
	{
	    for (int j = 0; j < largeur; j++)
	    {
		if (i <= c.getY() && j >= c.getX())
		    terrain[i][j] = new Case(Etat.LIBRE);
		else 
		    estFini = estFini && (terrain[i][j].estLibre() || terrain[i][j].estPoison());
	    }
	}

	return estFini;
    }

    /**
     * Clone un objet configuration
     **/
    public Configuration clone()
    {
	Case [][] terrainCopie = new Case[hauteur][largeur];
	
	for (int i = 0; i < hauteur; i++)
	    for (int j = 0; j < largeur; j++)
		terrainCopie[i][j] = terrain[i][j].clone();

	return new Configuration(largeur, hauteur, terrainCopie, joueurSurConfiguration);
    }
    

    /**
     * Serialize les données du terrain
     **/
    public String getSerialize()
    {
	String result = "";

	for (int i = 0; i < hauteur; i++)
	{
	    for (int j = 0; j < largeur; j++)
	    {
		Joueur j = terrain[i][j].getJoueurSurConfiguration();

		if (j == null)
		    result += "0";
		else
		    result += String.valueOf(j.getPosition());

		result += String.valueOf(terrain[i][j].scorePoisson());
	    }
	}
	
	return result;
    }

    /**
     * Charge les données à partir d'une chaine serialize
     * Renvoit vrai si la chaine est correcte
     **/
    public boolean setSerialized(String result)
    {   
	if (hauteur * largeur != result.length() * 2)
	    return false;

	terrain = new Case[hauteur][largeur];
	int compteur = 0;

	for (int i = 0; i < hauteur; i++)
	{
	    for (int j = 0; j < largeur; j++)
	    {
		Joueur j = null;
		Etat e = VIDE;
		if (result.charAt(compteur) != '0')
		    j =ArbitreManager.instance.getJoueurParPosition(Integer.valueOf(result.charAt(compteur)));

		compteur++;

		if (result.charAt(compteur) == '1')
		    e = UN_POISSON;
		else if (result.charAt(compteur) == '2')
		    e = DEUX_POISSONS;
		else if (result.charAt(compteur) == '3')
		    e = TROIS_POISSONS;
		else
		    e = VIDE;

		terrain[i][j] = new Case(e, j);
	    }
	}

	return true;
    }
}
