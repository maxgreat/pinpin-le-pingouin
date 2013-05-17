package Interface.Graphique;
import java.awt.event.*;
import Arbitre.*;
import Joueurs.*;

public class EcouteurDeBouton implements ActionListener{
	String message;
	InterfaceGraphique inter;

	public EcouteurDeBouton(String m, InterfaceGraphique inter){
		message = m;
		this.inter = inter;
	}

	public void actionPerformed(ActionEvent e)
    {
        Joueur [] joueurs = new Joueur[2];
        joueurs[0] = new JoueurHumain();
        joueurs[0].setNom("David");

        joueurs[1] = new JoueurHumain();
        joueurs[1].setNom("Goliath");
       

        // Change l'interface
        inter.aire.enleverBoutons();
		inter.aire.setVisible(true);

        // Sélectionne les joueurs
		if(message.compareTo("HvCPU") == 0)
        {
			joueurs[1] = new JoueurHumain();
			joueurs[2] = new JoueurCPUMinimax();
		}
		else{
			joueurs[1] = new JoueurHumain();
			joueurs[2] = new JoueurHumain();
		}
		
        // Lance la partie
        ArbitreManager.stopperPartie();
        ArbitreManager.initialiserPartie(joueurs, ArbitreManager.LARGEUR_GRILLE, ArbitreManager.HAUTEUR_GRILLE, (Interface)inter);
        ArbitreManager.lancerPartie();
	}
}
