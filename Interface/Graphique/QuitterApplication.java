package Interface.Graphique;

import Arbitre.ArbitreManager;
import java.awt.event.*;

class QuitterApplication implements ActionListener {
    public void actionPerformed(ActionEvent e) {
	// Stop la partie en cours proprement
	ArbitreManager.stopperPartie();

	// Quitte l'application
        System.exit(0);
    }
}
