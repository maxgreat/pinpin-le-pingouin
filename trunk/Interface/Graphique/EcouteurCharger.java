package Interface.Graphique;
import Interface.*;
import java.awt.event.*;
import java.io.*;
import Arbitre.*;
import Joueurs.*;

public class EcouteurCharger implements ActionListener{
	Charger charger;
	Interface inter;
	int opt;

	public EcouteurCharger(Charger charger, Interface inter, int opt) {
		this.charger = charger;
		this.inter = inter;
		this.opt = opt;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (this.opt == 0) {
			if (this.charger.getList().getSelectedValue() != null) {
				File f = new File("Save/" + this.charger.getList().getSelectedValue());
				
				Joueur [] joueurs = new Joueur[2];
				joueurs[0] = new JoueurHumain();
				joueurs[1] = new JoueurCPUPhase();
				inter.setJoueurs(joueurs);
				((InterfaceGraphique)inter).initialiserPartie(joueurs);
				System.out.println("je charge");
				ArbitreManager.chargerPartie(f.getAbsolutePath());
				System.out.println("aller");
			}
		} else if (this.opt == 1) {
			return;
		}
	}
}
