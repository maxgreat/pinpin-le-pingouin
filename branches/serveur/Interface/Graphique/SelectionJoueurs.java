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
	names = new JComboBox(test);
	diff = new JComboBox(difficultees.toArray());
	diff.setVisible(false);
	if(menu == 3 || menu == 4)
		names.setSelectedItem("Aucun");
	if(menu == 1)
		names.setSelectedItem("Atos");
	if(menu == 2)
		{
		names.setSelectedItem("Ordinateur");
		diff.setVisible(true);
		}
		diff.setSelectedItem("Intermediaire");
	names.addActionListener(new EcouteurDeBox(this,true,mp.inter,menu));
	diff.addActionListener(new EcouteurDeBox(this,false,mp.inter,menu));
	
	
	joueur.setLayout(new BoxLayout(joueur, BoxLayout.PAGE_AXIS));
	
	if(menu == 1)
	{
		Img J1 = new Img("j1.png");
		joueur.add(J1);	
	}
	if(menu == 2)
	{
		Img J2 = new Img("j2.png");
		joueur.add(J2);	
	}
	if(menu ==3)
	{
		Img J3 = new Img("j3.png");
		joueur.add(J3);
	}
	if(menu == 4)
	{
		Img J4 = new Img("j4.png");
		joueur.add(J4);	
	}
	joueur.add(names);
	joueur.add(diff);
	joueur.setOpaque(false);
	
	}

public void selectionDifficulte()
	{
	
		if(Ordinateur)
		{
			diff.setVisible(true);
		}
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
