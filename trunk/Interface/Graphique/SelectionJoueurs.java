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
	names.addActionListener(new EcouteurDeBox(this,true,mp.inter,menu));
	diff = new JComboBox(difficultees.toArray());
	diff.addActionListener(new EcouteurDeBox(this,false,mp.inter,menu));
	diff.setVisible(false);
	joueur.setLayout(new BoxLayout(joueur, BoxLayout.PAGE_AXIS));
	
	joueur.add(names);
	joueur.add(diff);
	joueur.setOpaque(false);
	
	}

public void selectionDifficulte()
	{
	
		if(Ordinateur)
		{
			diff.setVisible(true);
			System.out.println("on a selectionné l'ordinateur");
		}
	}
public JPanel getPan()
	{
	return joueur;
	}
} 
