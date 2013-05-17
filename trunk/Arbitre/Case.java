package Arbitre;
import Joueurs.*;

public class Case implements Cloneable
{
    protected Etat e;
    protected Joueur joueurSurCase;

    /**
     * Une case est caractérisé par son état et
     * Si un joueur l'occupe
     **/
    public Case(Etat e, Joueur joueurSurCase)
    {
        this.e = e;
        this.joueurSurCase = joueurSurCase;
    }

    public Joueur getJoueurSurCase()
    {
        return joueurSurCase;
    }

    public void setJoueurSurCase(Joueur joueurSurCase)
    {
        this.joueurSurCase = joueurSurCase;
    }

    public Etat getEtat()
    {
        return e;
    }

    /**
     * Retourne si une case est vide
     **/
    public boolean estVide()
    {
        return e == Etat.VIDE;
    }

    /**
     * Retourne si une case est un obstacle
     **/
    public boolean estObstacle()
    {
        return estVide() || joueurSurCase != null;
    }

    /**
     * Score d'une case suivant le nombre de poisson ou vide
     **/
    public int scorePoisson()
    {
        switch(e)
        {
        case UN_POISSON:
            return 1;
        case DEUX_POISSONS:
            return 2;
        case TROIS_POISSONS:
            return 3;
        case VIDE:
        default:
            return 0;
        }
    }

    public Case clone()
    {
        return new Case(e, joueurSurCase);
    }
}
