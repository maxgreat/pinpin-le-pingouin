package Interface;
import Joueurs.*;

public interface Interface
{
    /**
     * repaint() doit afficher le jeu en cours à l'écran
     **/
    public void repaint();

    /**
     * run() lance les différentes actions à effectuer
     **/
    public void run(String [] args);

    /**
     * Garde en mémoire les joueurs
     **/
    public void setJoueurs(Joueur [] joueurs);
}
