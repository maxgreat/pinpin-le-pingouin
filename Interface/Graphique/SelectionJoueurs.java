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
String nomJoueur;
String difficultésJoueur;
//il nous faut l'ancien joueur pour pouvoir l'enlever de joueursPris
String nomJoueurOld;

//les combobox
JComboBox names;
JComboBox diff;

public SelectionJoueurs(JPanel joueur,  LinkedList<String> joueurs , LinkedList<String> JoueursPris, LinkedList<String> difficultees)
	{
		this.joueur = joueur;
		this.joueurs = joueurs;
		this.joueursPris = joueursPris;
		this.difficultees=difficultees;
	}

public void selectionJoueur()
	{

	Object [] test = joueurs.toArray();
	names = new JComboBox(test);
	names.addActionListener(new EcouteurDeBox(this,true));
	joueur.setLayout(new BoxLayout(joueur, BoxLayout.PAGE_AXIS));
	joueur.add(names);
	joueur.setOpaque(false);
	}

public void selectionDifficulte()
	{
		if(Ordinateur)
		{
			diff = new JComboBox(difficultees.toArray());
			diff.addActionListener(new EcouteurDeBox(this,false));
		}
	}
public JPanel getPan()
	{
	System.out.println("caca");
	return joueur;
	}
} 
