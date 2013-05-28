package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;

public class MenuPrincipal{
	protected JPanel fond = new JPanel();
	protected JPanel Menu = new JPanel();
	JFrame frame;
	InterfaceGraphique inter;
	Banniere ban;
	
	public MenuPrincipal(JFrame frame, InterfaceGraphique inter)
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
		ban = new Banniere("pinpin.png",frame);
		ban.setPreferredSize(new Dimension(700, (500)/4));
		
		//ligne 2
		JPanel cell21 = new JPanel();
		cell21.setOpaque(false);
		cell21.setPreferredSize(new Dimension(700, 500/16));
		
		//Ligne 3
		JPanel cell31 = new JPanel();
		cell31.setOpaque(false);
		cell31.setPreferredSize(new Dimension(700/8, (9*500)/16));
		JPanel cell32 = new JPanel();
		cell32.setOpaque(false);
		cell32.setPreferredSize(new Dimension(700/8, (9*500)/16));
		//menu du jeu
		Menu.setLayout(new FlowLayout());
		Menu.setOpaque(false);
		Menu.setPreferredSize(new Dimension(700/2, (9*500)/16));
		
		
		JPanel cell34 = new JPanel();
		cell34.setOpaque(false);
		cell34.setPreferredSize(new Dimension(700/8, (9*500)/16));
		JPanel cell35 = new JPanel();
		cell35.setOpaque(false);
		cell35.setPreferredSize(new Dimension(700/8, (9*500)/16));
		
		//Ligne 4
		JPanel cell41 = new JPanel();
		cell41.setOpaque(false);
		cell41.setPreferredSize(new Dimension(700, 500/8));    
		
		
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
		//Ligne 4
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(cell41, gbc);
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
		if(s.compareTo("demarrage") == 0){
			ban = new Banniere("pinpin.png",frame);
			ban.setPreferredSize(new Dimension(700, (500)/4));
			addBouton(Menu, "Partie Rapide");
			addBouton(Menu, "Partie Personalisée");
			addBouton(Menu, "Options");
			addBouton(Menu, "Quitter");
		}
		else if(s.compareTo("Options") == 0){
			ban = new Banniere("options.png",frame);
			ban.setPreferredSize(new Dimension(700, (500)/4));
			addBouton(Menu, "Son");
			addBouton(Menu, "Gestion de profil");
			addBouton(Menu, "Quitter");
		}
		else if(s.compareTo("Gestion de profil") == 0){
			ban = new Banniere("profil.png",frame);
			ban.setPreferredSize(new Dimension(700, (500)/4));
			addBouton(Menu, "Quitter");
		}
	
	
	}

}
