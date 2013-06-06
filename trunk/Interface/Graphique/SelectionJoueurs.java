package Interface.Graphique;

import Joueurs.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import Joueurs.*;



public class SelectionJoueurs{

    //le panel dans lequel on mettra les deux selecteurs
    JPanel joueur = new JPanel();

    // les listes de difficultés des deux 
    LinkedList<String> joueurs;
    LinkedList<String> joueursPris;
    LinkedList<String> difficultees;

    //les informations sur le joueur
    String nomJoueur = new String();
    String difficultesJoueur;
    //il nous faut l'ancien joueur pour pouvoir l'enlever de joueursPris
    String nomJoueurOld = new String();
    //on a besoin de menu perso pour
    MenuPerso mp;
    //les combobox
    JComboBox names;
    JComboBox diff;
    int menu;
    JPanel vide;
    JPanel buttons;
    InterfaceGraphique inter;
    SelectionJoueur partage;

    public SelectionJoueurs(JPanel joueur,  LinkedList<String> joueurs , LinkedList<String> joueursPris, LinkedList<String> difficultees,MenuPerso mp,InterfaceGraphique inter ,int menu)
	{
		this.inter = inter;
		this.menu = menu;
		this.joueur = joueur;
		this.joueurs = joueurs;
		this.joueursPris = joueursPris;
		this.difficultees=difficultees;
		nomJoueur = joueurs.getFirst();
		difficultesJoueur = difficultees.getFirst();
		this.mp = mp;

	}


    public void selectionJoueur()
	{
        buttons = new JPanel();
        diff = new JComboBox(difficultees.toArray());
        names = new JComboBox(joueurs.toArray());
        diff.setVisible(false);
        names.setEditable(true);
        names.setVisible(false);
        buttons.setLayout(new GridLayout(0,3));
	double width =  buttons.getSize().getWidth(), height = buttons.getSize().getHeight();
	JButton b1 = null;
	if (this.menu == 1)
		b1 = new JButton(new ImageIcon("Interface/Graphique/Img/joueurHumain1.png"));
	else if (this.menu == 2)
		b1 = new JButton(new ImageIcon("Interface/Graphique/Img/joueurHumain2.png"));
	else if (this.menu == 3)
		b1 = new JButton(new ImageIcon("Interface/Graphique/Img/joueurHumain3.png"));
	else if (this.menu == 4)
		b1 = new JButton(new ImageIcon("Interface/Graphique/Img/joueurHumain4.png"));		
	b1.setBorder(BorderFactory.createEmptyBorder());
	b1.setContentAreaFilled(false);
	b1.setOpaque(false);	
        b1.setBorderPainted(false); 
	JButton b2 = new JButton(new ImageIcon("Interface/Graphique/Img/joueurOrdinateur.png"));
	b2.setBorder(BorderFactory.createEmptyBorder());
	b2.setContentAreaFilled(false);
	b2.setOpaque(false);	
	JButton b3 = new JButton(new ImageIcon("Interface/Graphique/Img/aucun.png"));
	b3.setBorder(BorderFactory.createEmptyBorder());
	b3.setContentAreaFilled(false);
	b3.setOpaque(false);	

        this.partage = new SelectionJoueur(menu, names, diff);

        b1.addActionListener(new EcouteurBoutonMenuPerso(this, partage, 1));
        b2.addActionListener(new EcouteurBoutonMenuPerso(this, partage, 2));
        b3.addActionListener(new EcouteurBoutonMenuPerso(this, partage, 3));

	JPanel pan1 = new JPanel();
	pan1.add(b1,BorderLayout.CENTER);
	pan1.setOpaque(false);	


	JPanel pan2 = new JPanel();
	pan2.add(b2,BorderLayout.CENTER);
	pan2.setOpaque(false);	
	JPanel pan3 = new JPanel();
	pan3.add(b3,BorderLayout.CENTER);
	pan3.setOpaque(false);	


        buttons.add(pan1);
        buttons.add(pan2);
        buttons.add(pan3);
	buttons.setOpaque(false);	

        names.addActionListener(new EcouteurDeBox(this, partage, mp.inter, menu));
        diff.addActionListener(new EcouteurDeBox(this, partage, mp.inter, menu));
        vide = new JPanel();
        vide.setOpaque(false);
        joueur.setLayout(new BoxLayout(joueur, BoxLayout.PAGE_AXIS));
        joueur.add(buttons);
        joueur.add(names);
        joueur.add(diff);
        joueur.add(vide);

        joueur.setOpaque(false);

        selectionDifficulte(partage);
	}

    public void selectionDifficulte(SelectionJoueur type)
	{
        names.setVisible(type.estJoueur());
        diff.setVisible(type.estOrdinateur());
        vide.setVisible(type.estAucun());	
        
	}
    public JPanel getPan()
	{
        return joueur;
	}

    private URL getImage(String nom) {
        ClassLoader cl = getClass().getClassLoader();
        return cl.getResource("Interface/Graphique/Img/" + nom);
    }
}
