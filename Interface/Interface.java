package Interface;
import Joueurs.*;

public abstract class Interface
{
    /**
     * repaint() doit afficher le jeu en cours à l'écran
     **/
    public abstract void repaint();

    /**
     * run() lance les différentes actions à effectuer
     **/
    public abstract void run(String [] args);

    /**
     * Garde en mémoire les joueurs
     **/
    public Joueur [] joueurs;

    public void setJoueurs(Joueur [] joueurs)
    {
        this.joueurs = joueurs;
    }

    public Joueur [] getJoueurs()
    {
        return this.joueurs;
    }
}
