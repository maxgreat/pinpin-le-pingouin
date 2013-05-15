package Interface;
import java.awt.event.*;
import Arbitre.*;
import Joueurs.*;

public class EcouteurDeBouton implements ActionListener{
	String message;
	Interface inter;

	public EcouteurDeBouton(String m, Interface inter){
		message = m;
		this.inter = inter;
	}

	public void actionPerformed(ActionEvent e)
    {
        Joueur joueur1 = null;
        Joueur joueur2 = null;

        // Change l'interface
        inter.aire.enleverBoutons();
		inter.aire.setVisible(true);

        // Sélectionne les joueurs
		if(message.compareTo("HvCPU") == 0)
        {
			joueur1 = new JoueurHumain();
			joueur2 = new JoueurCPUMinimax();
		}
		else{
			joueur1 = new JoueurHumain();
			joueur2 = new JoueurHumain();
		}
		
        // Lance la partie
        ArbitreManager.stopperPartie();
        ArbitreManager.initialiserPartie(joueur1, joueur2, inter.getAire().getLargeur(), inter.getAire().getHauteur(), inter);
        ArbitreManager.lancerPartie();
	}
}
