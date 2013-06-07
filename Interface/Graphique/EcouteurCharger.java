package Interface.Graphique;
import Interface.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
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
				ArbitreManager.chargerPartie(f.getAbsolutePath());
			}
		} else if (this.opt == 1) {
			if (this.charger.getList().getSelectedValue() != null) {
				if (JOptionPane.showConfirmDialog(((InterfaceGraphique)inter).frame,
							      "Supprimer la sauvegarde \"" +
							      this.charger.getList().getSelectedValue() + "\" ?",
							      "Confirmation" , 
								  JOptionPane.YES_NO_OPTION) == 0) {
					File f = new File("Save/" + this.charger.getList().getSelectedValue());
					f.delete();
					this.charger.listLoad();
				}
			}
		}
	}
}
