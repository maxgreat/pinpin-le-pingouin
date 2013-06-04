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

		// On récupère le nom des fichiers
		String [] data;
		File repertoire = new File("Save");
		data = repertoire.list();

		// Création de la liste avec les noms de fichiers
		list = new JList(data);
		list.setPreferredSize(new Dimension(250,150));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);

		//Bouton de sauvegarde
		JButton btnCharger = new JButton("Charger");
		btnCharger.addActionListener(new EcouteurCharger(this, inter, 0));

		JButton btnDelete = new JButton("Supprimer");
		btnCharger.addActionListener(new EcouteurCharger(this, inter, 1));


		fond.add(btnCharger);
		fond.add(list);
	}

	public JList getList() {
		return this.list;
	}
}

