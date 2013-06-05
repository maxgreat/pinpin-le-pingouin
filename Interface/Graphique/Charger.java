package Interface.Graphique;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JList;
import javax.swing.*;
import Arbitre.*;

import java.io.*;

public class Charger {
	protected JPanel fond = new JPanel();
	protected JPanel Menu = new JPanel();
	InterfaceGraphique inter;
	Banniere ban;
	JPanel cellGauche;
	JPanel cellDroite;
	JFrame frame;
	JList list;

	public Charger (JFrame frame, InterfaceGraphique inter) {
		String s = "backgroundIce2.png";
		this.frame = frame;
		this.inter = inter;
		Dimension Dim = new Dimension(700,500);	
		//creation du grid panel
		JPanel panel = new JPanel();
		panel.setPreferredSize(Dim);
		panel.setLayout(new GridBagLayout());
		panel.setOpaque(false);
		
		//chargement fond
		fond = new Fond(s,frame);
		fond.setOpaque(true);
		//fond.setPreferredSize(new Dimension(700,500));
		
		
		//------------------------------------------------------
		GridBagConstraints gbc = new GridBagConstraints();
		//On crée nos différents conteneurs
		//Ligne 1
		ban = new Banniere("pinpin.png",frame);
		ban.setPreferredSize(new Dimension(700, (500)/4));
		//------------------------------------------------------
		//
		//ligne 2
		JPanel cell21 = new JPanel();
		cell21.setOpaque(false);
		cell21.setPreferredSize(new Dimension(700, 500/16));
		//------------------------------------------------------
		//
		//Ligne 3
		JPanel cell31 = new JPanel();
		cell31.setOpaque(false);
		cell31.setPreferredSize(new Dimension(700/8, (9*500)/16));
		//-------------------------------------------------------
		JPanel cell32 = new JPanel();
		cell32.setOpaque(false);
		cell32.setPreferredSize(new Dimension(700/8, (9*500)/16));
		//-------------------------------------------------------
		//menu du jeu
		Menu.setLayout(new FlowLayout());
		Menu.setOpaque(false);
		Menu.setPreferredSize(new Dimension(700/2, (9*500)/16));
		//--------------------------------------------------------
		JPanel cell34 = new JPanel();
		cell34.setOpaque(false);
		cell34.setPreferredSize(new Dimension(700/8, (9*500)/16));
		JPanel cell35 = new JPanel();
		cell35.setOpaque(false);
		cell35.setPreferredSize(new Dimension(700/8, (9*500)/16));
		//--------------------------------------------------------
		//
		//Ligne 4
		cellGauche = new JPanel();
		cellGauche.setOpaque(false);
		cellGauche.setPreferredSize(new Dimension(700/8, 500/8)); 
		JPanel cell42 = new JPanel();
		cell42.setOpaque(false);
		cell42.setPreferredSize(new Dimension(700/8, 500/16));   
		JPanel cell43 = new JPanel();
		cell43.setOpaque(false);
		cell43.setPreferredSize(new Dimension(700/2, 500/8));    
		cellDroite = new JPanel();
		JPanel cell44 = new JPanel();
		cell44.setOpaque(false);
		cell44.setPreferredSize(new Dimension(700/8, 500/16));
		cellDroite.setOpaque(false);
		cellDroite.setPreferredSize(new Dimension(700/8, 500/8));
		
		//On positionne la case de départ du composant
		gbc.gridx = 0;
		gbc.gridy = 0;
		//La taille en hauteur et en largeur
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(ban, gbc);    
	    //---------------------------------------------
		//Ligne 2
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(cell21, gbc);     
		//---------------------------------------------
		//Ligne 3
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel.add(cell31, gbc);
		//---------------------------------------------
		gbc.gridx = 1;
		panel.add(cell32, gbc);
		//---------------------------------------------
		gbc.gridx = 2;     
		panel.add(Menu, gbc);       
		//---------------------------------------------
		gbc.gridx = 3;     
		panel.add(cell34, gbc); 
		//---------------------------------------------
		gbc.gridx = 4;  
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(cell35, gbc);
		//---------------------------------------------
		//
		//Ligne 4
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel.add(cellGauche, gbc);
		//---------------------------------------------
		gbc.gridx = 1;
		panel.add(cell42, gbc);
		//---------------------------------------------
		gbc.gridx = 2;     
		panel.add(cell43, gbc);       
		//---------------------------------------------
		gbc.gridx = 3;     
		panel.add(cell44, gbc); 
		//---------------------------------------------
		gbc.gridx = 4;  
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(cellDroite, gbc);
		//--------------------------------------------

		fond.add(panel);

		// Création de la liste avec les noms de fichiers
		list = new JList();
		listLoad();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		
		JScrollPane pan = new JScrollPane(list);
		//		pan.setVerticalScrollBarPolicy();
		pan.setPreferredSize(new Dimension(250,150));
		Menu.add(pan, BorderLayout.CENTER);
		
		//Bouton de sauvegarde
		addBouton(Menu, "supprimer.png", 1);
		addBouton(Menu, "petitCharger.png", 0);
		addBouton(Menu, "retour.png", -1);
	}

	public void addBouton(JPanel panel, String S, int opt) {
		JPanel pan = new JPanel();
		File f = new File("Interface/Graphique/Img/"+S);
		JButton b1 = null;
		// Test l'existence du fichier d'image avant d'essayer de le charger
		if(f.exists())
			{
				b1 = new JButton(new ImageIcon("Interface/Graphique/Img/" + S));
				b1.setBorder(BorderFactory.createEmptyBorder());
				b1.setContentAreaFilled(false);
			}
		else
			{
				b1 = new JButton(S);
			}
		if (opt == -1)
			b1.addActionListener(new EcouteurDeBouton(S, inter));
		else
			b1.addActionListener(new EcouteurCharger(this, inter, opt));
		pan.add(b1,BorderLayout.CENTER);
		pan.setOpaque(false);	
		panel.add(pan,BorderLayout.CENTER);
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
			if (!data[i].equals(".svn"))
				model.add(0,data[i]);
		}
		list.setModel(model);
	}
}

