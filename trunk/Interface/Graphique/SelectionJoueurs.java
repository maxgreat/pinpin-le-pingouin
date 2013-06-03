package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;




public class SelectionJoueurs{

//le panel dans lequel on mettra les deux selecteurs
JPanel joueur = new JPanel();

// les listes de difficultés des deux 
LinkedList<String> joueurs;
LinkedList<String> joueursPris;
LinkedList<String> difficultees;
//les informations sur le joueur
boolean Ordinateur;
boolean Humain;
boolean Aucun;
String nomJoueur = new String();
String difficultésJoueur;
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
public SelectionJoueurs(JPanel joueur,  LinkedList<String> joueurs , LinkedList<String> joueursPris, LinkedList<String> difficultees,MenuPerso mp,InterfaceGraphique inter ,int menu)
	{
		this.inter = inter;
		this.menu = menu;
		this.joueur = joueur;
		this.joueurs = joueurs;
		this.joueursPris = joueursPris;
		this.difficultees=difficultees;
		nomJoueur = joueurs.getFirst();
		difficultésJoueur = difficultees.getFirst();
		this.mp = mp;
	}

public void selectionJoueur()
	{

	Object [] test = joueurs.toArray();
	JPanel buttons = new JPanel();
	diff = new JComboBox(difficultees.toArray());
	names = new JComboBox(test);
	diff.setVisible(false);
	names.setVisible(false);
	buttons.setLayout(new GridLayout(0,3));
	JButton b1 = new JButton("HUMAIN");
	JButton b2 = new JButton("CPU");
	JButton b3 = new JButton("AUCUN");

	b1.addActionListener(new EcouteurBoutonMenuPerso(this,1));
	b2.addActionListener(new EcouteurBoutonMenuPerso(this,2));
	b3.addActionListener(new EcouteurBoutonMenuPerso(this,3));
	
	buttons.add(b1);
	buttons.add(b2);
	buttons.add(b3);
	names.addActionListener(new EcouteurDeBox(this,true,mp.inter,menu));
	diff.addActionListener(new EcouteurDeBox(this,false,mp.inter,menu));
	vide = new JPanel();
	vide.setOpaque(false);
	joueur.setLayout(new BoxLayout(joueur, BoxLayout.PAGE_AXIS));
	joueur.add(buttons);
	joueur.add(names);
	joueur.add(diff);
	joueur.add(vide);

	joueur.setOpaque(false);

	Aucun = true;
	Ordinateur=false;
	Humain=false;

	selectionDifficulte();
	
	}

public void selectionDifficulte()
	{
	names.setVisible(Humain);
	diff.setVisible(Ordinateur);
	vide.setVisible(Aucun);
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
