package Arbitre;

import java.util.*;

public class Configuration implements Cloneable
{
    protected int largeur;
    protected int hauteur;
    protected Case [][] terrain;


    public Configuration(int largeur, int hauteur, Case [][] terrain)
    {
	this.largeur = largeur;
	this.hauteur = hauteur;
	this.terrain = terrain;
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

    /**
     * Vérifie si un coup est possible
     **/
    public boolean estCoupPossible(Coup coup)
    {
	int x = coup.getX();
	int y = coup.getY();

	// Reste dans le terrain
	if (x < 0 || x >= largeur || y < 0 || y >= hauteur)
	    return false;

	// Case vide
	if (!terrain[y][x].estPleine())
	    return false;

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
		terrainCopie[i][j] = new Case(terrain[i][j].etat());

	return new Configuration(largeur, hauteur, terrainCopie);
    }
    
    public void afficherTerrain()
    {
    	for (int i = 0; i < hauteur; i++)
    	{
    		for (int j = 0; j < largeur; j++)
    			if (terrain[i][j].estLibre())
    				System.out.print(". ");
    			else if(terrain[i][j].estPoison())
    				System.out.print("P ");
    			else
    				System.out.print("X ");
    		System.out.println();	
    	}
    }	

    /**
     * Serialize les données du terrain
     **/
    public String getSerialize()
    {
	String result = "";

	for (int i = 0; i < hauteur; i++)
	    for (int j = 0; j < largeur; j++)
		if (terrain[i][j].estPleine())
		    result += "1";
		else if(terrain[i][j].estLibre())
		    result += "0";
		else 
		    result += "2";
	
	return result;
    }

    /**
     * Charge les données à partir d'une chaine serialize
     * Renvoit vrai si la chaine est correcte
     **/
    public boolean setSerialized(String result)
    {   
	if (hauteur * largeur != result.length())
	    return false;

	terrain = new Case[hauteur][largeur];

	for (int i = 0; i < hauteur; i++)
	{
	    for (int j = 0; j < largeur; j++)
	    {
		if (result.charAt(i*largeur + j) == '1')
		    terrain[i][j] = new Case(Etat.PLEINE);
		else if (result.charAt(i*largeur + j) == '0')
		    terrain[i][j] = new Case(Etat.LIBRE);
		else
		    terrain[i][j] = new Case(Etat.POISON);
	    }
	}

	return true;
    }
}
