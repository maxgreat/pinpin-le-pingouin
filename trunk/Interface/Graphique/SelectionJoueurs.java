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
JPanel names;
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
	names = new JPanel();
	diff = new JComboBox(difficultees.toArray());
	diff.setVisible(false);
	names.setLayout(new GridLayout(0,3));
	JButton b1 = new JButton("HUMAIN");
	JButton b2 = new JButton("CPU");
	JButton b3 = new JButton("AUCUN");
	names.add(b1);
	names.add(b2);
	names.add(b3);

	if(menu == 2)
		{
		diff.setVisible(true);
		}
		diff.setSelectedItem("Intermediaire");
	//names.addActionListener(new EcouteurDeBox(this,true,mp.inter,menu));
	diff.addActionListener(new EcouteurDeBox(this,false,mp.inter,menu));
	
	
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
