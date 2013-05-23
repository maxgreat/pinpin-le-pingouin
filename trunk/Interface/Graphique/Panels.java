package Interface.Graphique;
import javax.swing.*;
import Arbitre.*;
import java.awt.*;
import java.awt.event.*;
import Interface.*;
import Joueurs.*;
import java.util.*;
import javax.imageio.*;
import java.net.URL;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Panels{
   JPanel Tab[] = new JPanel[10];
   InterfaceGraphique inter;
   public Panels(InterfaceGraphique inter)
   {
   this.inter = inter;
   initialiserPanel();
   }
   public void addBouton(JPanel panel, String S)
    {	

	JPanel pan  = new JPanel();
	
	pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
	JButton b1 = new JButton(S);
	b1.addActionListener(new EcouteurDeBouton(S, inter));
	pan.add(b1);
	pan.setOpaque(false);	
	panel.add(pan);
     }
    public void initialiserPanel()
	{
	for(int i = 0;i<10;i++)
	{
		Tab[i] = new JPanel();
	}
	// le panel 0 correspond a la page d'acceuil
	Tab[0].setLayout(new BoxLayout(Tab[0], BoxLayout.PAGE_AXIS));
	Tab[0].setOpaque(false);
	addBouton(Tab[0],"Partie Rapide");
	addBouton(Tab[0],"Partie Personalisé");	
	addBouton(Tab[0],"Options");

	// le panel 1 correspond a la page d'options
	Tab[1].setLayout(new BoxLayout(Tab[1], BoxLayout.PAGE_AXIS));
	Tab[1].setOpaque(false);
	addBouton(Tab[1],"Gestion de profil");
	addBouton(Tab[1],"Classements");			
	// le panel 2 est le panel correspondant a la partie personalisé
	Tab[6].setOpaque(false);
	Tab[6].setLayout(new GridLayout( 3 , 1) );
	addBouton(Tab[6],"nom du joueur1");
	addBouton(Tab[6],"niveau du joueur1");
	addBouton(Tab[6],"avatar joueur 1");

	Tab[2].setOpaque(false);
	Tab[2].setLayout(new GridLayout( 3 , 1) );
	addBouton(Tab[2],"Quitter");
	Tab[2].setOpaque(false);
	
	Tab[7].setLayout(new GridLayout( 3 , 1) );
	addBouton(Tab[7],"nom du joueur2");
	addBouton(Tab[7],"niveau du joueur2");
	addBouton(Tab[7],"avatar joueur 2");

	Tab[8].setOpaque(false);
	Tab[8].setLayout(new GridLayout( 3 , 1) );
	addBouton(Tab[8],"nom du joueur3");
	addBouton(Tab[8],"niveau du joueur3");
	addBouton(Tab[8],"avatar joueur 3");

	Tab[9].setOpaque(false);
	Tab[9].setLayout(new GridLayout( 3 , 1) );
	addBouton(Tab[9],"nom du joueur4");
	addBouton(Tab[9],"niveau du joueur4");
	addBouton(Tab[9],"avatar joueur 4");
	// le panel 3 est le panel correspondant au gestion de profil
	Tab[3].setLayout(new BoxLayout(Tab[3], BoxLayout.PAGE_AXIS));
	Tab[3].setOpaque(false);
	// le panel 4 est le panel correspondant au classement
	Tab[4].setLayout(new BoxLayout(Tab[4], BoxLayout.PAGE_AXIS));
	Tab[4].setOpaque(false);
	//le panel correspondant au deux boutons quitter et revenir
	Tab[5].setLayout(new BoxLayout(Tab[5], BoxLayout.PAGE_AXIS));
	Tab[5].setOpaque(false);
	addBouton(Tab[5],"Revenir");
	addBouton(Tab[5],"Quitter");
	}







}
