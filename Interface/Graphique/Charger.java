package Interface.Graphique;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JList;
import javax.swing.*;
import Arbitre.*;

import java.io.*;

public class Charger {
	//            ArbitreManager.chargerPartie(fichierChoisi.getAbsolutePath());
	JFrame frame;
	JPanel fond;
	JList list;

	public Charger (JFrame frame, InterfaceGraphique inter) {
		this.frame = frame;
		this.fond = new Fond("backgroundIce1.png",frame);
		fond.setOpaque(true);
		fond.setPreferredSize(new Dimension(700,500));

		// Création de la liste avec les noms de fichiers
		list = new JList();
		listLoad();
		list.setPreferredSize(new Dimension(250,150));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);

		//Bouton de sauvegarde
		JButton btnCharger = new JButton("Charger");
		btnCharger.addActionListener(new EcouteurCharger(this, inter, 0));

		JButton btnDelete = new JButton("Supprimer");
		btnDelete.addActionListener(new EcouteurCharger(this, inter, 1));

		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new EcouteurDeBouton("Retour", inter));

		fond.add(btnCharger);
		fond.add(btnDelete);
		fond.add(btnRetour);
		fond.add(list);
	}

	public JList getList() {
		return this.list;
	}

	public void listLoad() {
		String [] data;
		File repertoire = new File("Save");
		data = repertoire.list();
		DefaultListModel model = new DefaultListModel();
		for (int i = 0; i < data.length; i++) {
			model.add(0,data[i]);
		}
		list.setModel(model);
	}
}

