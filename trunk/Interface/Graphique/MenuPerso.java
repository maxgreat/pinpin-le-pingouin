package Interface.Graphique;
import Joueurs.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;

public class MenuPerso{
	protected JPanel fond = new JPanel();
	protected JPanel Menu = new JPanel();
	JFrame frame;
	InterfaceGraphique inter;
	Banniere ban;
	protected JPanel menuJ1;
	protected JPanel menuJ2;
	protected JPanel menuJ3;
	protected JPanel menuJ4;
	JPanel cellGauche;
	JPanel cellDroite;

	boolean b1 = true;
	boolean b2 = true;
	boolean b3 = true;
	boolean b4= true;
	
	public MenuPerso(JFrame frame, InterfaceGraphique inter)
	{
		this.frame = frame;
		this.inter = inter;
		Dimension Dim = new Dimension(700,500);	
		//creation du grid panel
		JPanel panel = new JPanel();
		panel.setPreferredSize(Dim);
		panel.setLayout(new GridBagLayout());
		panel.setOpaque(false);
		
		//chargement fond
		fond = new Fond("backgroundIce1.png",frame);
		fond.setOpaque(true);
		fond.setPreferredSize(new Dimension(700,500));
		
		
		//----------------------------------
		GridBagConstraints gbc = new GridBagConstraints();
		//On crée nos différents conteneurs
		//Ligne 1
		ban = new Banniere("partiePersonalisee.png",frame);
		ban.setPreferredSize(new Dimension(700, 500/4));
		//-------------------------------------------------------
		//
		//ligne 2
		JPanel cell21 = new JPanel();
		cell21.setOpaque(false);
		cell21.setPreferredSize(new Dimension(700, 500/16));
		//-------------------------------------------------------
		//
		//Ligne 3
		JPanel cell31 = new JPanel();
		cell31.setOpaque(false);
		cell31.setPreferredSize(new Dimension(700/8, 500/4));
		//Menuj1
		menuJ1 = new JPanel();
		menuJ1.setOpaque(false);
		menuJ1.setPreferredSize(new Dimension(5*700/16, 500/4));
		//-------------------------------------------------------
		JPanel cell33 = new JPanel();
		cell33.setOpaque(false);
		cell33.setPreferredSize(new Dimension(700/8, 500/4));
		//Menuj2
		menuJ2 = new JPanel();
		menuJ2.setOpaque(false);
		menuJ2.setPreferredSize(new Dimension(5*700/16, 500/4));
		//-------------------------------------------------------
		JPanel cell35 = new JPanel();
		cell35.setOpaque(false);
		cell35.setPreferredSize(new Dimension(700/8, 500/4));
		//-------------------------------------------------------
		//
		//Ligne 4
		JPanel cell41 = new JPanel();
		cell41.setOpaque(false);
		cell41.setPreferredSize(new Dimension(700, 500/16));    
		//-------------------------------------------------------
		//
		//Ligne 5
		JPanel cell51 = new JPanel();
		cell51.setOpaque(false);
		cell51.setPreferredSize(new Dimension(700/8, 500/4));
		//Menuj1
		menuJ3 = new JPanel();
		menuJ3.setOpaque(false);
		menuJ3.setPreferredSize(new Dimension(5*700/16, 500/4));
		//-------------------------------------------------------
		JPanel cell53 = new JPanel();
		cell53.setOpaque(false);
		cell53.setPreferredSize(new Dimension(700/8, 500/4));
		//Menuj2
		menuJ4 = new JPanel();
		menuJ4.setOpaque(false);
		menuJ4.setPreferredSize(new Dimension(5*700/16, 500/4));
		//-------------------------------------------------------
		JPanel cell55 = new JPanel();
		cell55.setOpaque(false);
		cell55.setPreferredSize(new Dimension(700/8, 500/4));
		//-------------------------------------------------------
		//
		//Ligne 6
		cellGauche = new JPanel();
		cellGauche.setOpaque(false);
		cellGauche.setPreferredSize(new Dimension(700/8, 500/8));
		//------------------------------------------------------
		JPanel cell62 = new JPanel();
		cell62.setOpaque(false);
		cell62.setPreferredSize(new Dimension(5*700/16, 500/8));
		//-------------------------------------------------------
		JPanel cell63 = new JPanel();
		cell63.setOpaque(false);
		cell63.setPreferredSize(new Dimension(700/8, 500/8));
		//------------------------------------------------------
		JPanel cell64 = new JPanel();
		cell64.setOpaque(false);
		cell64.setPreferredSize(new Dimension(5*700/16, 500/8));
		//-------------------------------------------------------
		cellDroite = new JPanel();
		cellDroite.setOpaque(false);
		cellDroite.setPreferredSize(new Dimension(700/8, 500/8));
		//-------------------------------------------------------  
		//-------------------------------------------------------
		//
		
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
		panel.add(menuJ1, gbc);
		//---------------------------------------------
		gbc.gridx = 2;     
		panel.add(cell33, gbc);       
		//---------------------------------------------
		gbc.gridx = 3;     
		panel.add(menuJ2, gbc); 
		//---------------------------------------------
		gbc.gridx = 4;  
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(cell35, gbc);
		//---------------------------------------------
		//Ligne 4
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(cell41, gbc);
		//--------------------------------------------
		//Ligne 5
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel.add(cell51, gbc);
		//---------------------------------------------
		gbc.gridx = 1;
		panel.add(menuJ3, gbc);
		//---------------------------------------------
		gbc.gridx = 2;     
		panel.add(cell53, gbc);       
		//---------------------------------------------
		gbc.gridx = 3;     
		panel.add(menuJ4, gbc); 
		//---------------------------------------------
		gbc.gridx = 4;  
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(cell55, gbc);
		//---------------------------------------------
		//Ligne 6
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel.add(cellGauche, gbc);
		//---------------------------------------------
		gbc.gridx = 1;
		panel.add(cell62, gbc);
		//---------------------------------------------
		gbc.gridx = 2;     
		panel.add(cell63, gbc);       
		//---------------------------------------------
		gbc.gridx = 3;     
		panel.add(cell64, gbc); 
		//---------------------------------------------
		gbc.gridx = 4;  
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(cellDroite, gbc);
		//--------------------------------------------



		//panel.repaint();
		fond.add(panel);

	}
	
	public void addBouton(JPanel panel, String S)
	{
		JPanel pan = new JPanel();

		pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
		JButton b1 = new JButton(S);
		b1.addActionListener(new EcouteurDeBouton(S, inter));
		pan.add(b1);
		pan.setOpaque(false);	
		panel.add(pan);
	}
		public void setBoutons(String s)
	{
		if(s.compareTo("Nouvelle Partie") == 0)
		{			
		setBoutonsJoueur(menuJ1,1);
		setBoutonsJoueur(menuJ2,2);
		setBoutonsJoueur(menuJ3,3);
		setBoutonsJoueur(menuJ4,4);
		addBouton(cellDroite , "Lancer");
		addBouton(cellGauche , "Retour");
		}
	}

	public void setBoutonsJoueur(JPanel panel,int i)
	{
			String [] diff = {"Facile","Intermediaire","Difficile","Aucune"};
			String [] noms = {"Atos","Portos","Aramis","Dartagnan","Ordinateur","Aucun"};
			
			JComboBox names = new JComboBox(noms);
			if(i == 1)
				{
				names.setSelectedIndex(0);
				inter.joueur1 = noms[0];
				}			
			if(i == 2)
				{
				names.setSelectedIndex(4);
				inter.joueur2 = noms[4];
				}			
			if(i == 3)
				{
				names.setSelectedIndex(5);
				inter.joueur3 = noms[5];
				}		
			if(i == 4)
				{
				names.setSelectedIndex(5);
				inter.joueur4 = noms[5];
				} 
			names.addActionListener(
			new EcouteurDeBox(inter,"joueur"+i,names,this,(String)names.getSelectedItem(),i)
			);
			JPanel pan = new JPanel();
			pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
			pan.add(names);
			pan.setOpaque(false);
			panel.add(pan);
			

			JComboBox difficultes = new JComboBox(diff);

			if(i == 1)
				{
				inter.niveau1 = diff[3];
				difficultes.setSelectedIndex(3);
				}
			if(i == 2)
				{
				inter.niveau2 = diff[0];
				difficultes.setSelectedIndex(1);
				}
			if(i == 3)
				{
				inter.niveau3 = diff[3];
				difficultes.setSelectedIndex(3);
				}			
			if(i == 4)
				{
				inter.niveau4 = diff[3];
				difficultes.setSelectedIndex(3);
				}		
			if((i == 1 && b1) || (i == 2 && b2) || (i == 3 && b3) || (i == 4 && b4))
			{
				difficultes.addActionListener(new EcouteurDeDiff(inter,"niveau"+i,difficultes));
				pan = new JPanel();
				pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
				pan.add(difficultes);
				pan.setOpaque(false);
				panel.add(pan);
			}
	}
	
}