package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;

public class MenuPerso{
	protected JPanel fond = new JPanel();
	protected JPanel Menu = new JPanel();
	JFrame frame;
	InterfaceGraphique inter;
	Banniere ban;
	
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
		JPanel menuJ1 = new JPanel();
		menuJ1.setBackground(Color.BLACK);
		menuJ1.setPreferredSize(new Dimension(5*700/16, 500/4));
		//-------------------------------------------------------
		JPanel cell33 = new JPanel();
		cell33.setOpaque(false);
		cell33.setPreferredSize(new Dimension(700/8, 500/4));
		//Menuj2
		JPanel menuJ2 = new JPanel();
		menuJ2.setBackground(Color.ORANGE);
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
		JPanel menuJ3 = new JPanel();
		menuJ3.setBackground(Color.RED);
		menuJ3.setPreferredSize(new Dimension(5*700/16, 500/4));
		//-------------------------------------------------------
		JPanel cell53 = new JPanel();
		cell53.setOpaque(false);
		cell53.setPreferredSize(new Dimension(700/8, 500/4));
		//Menuj2
		JPanel menuJ4 = new JPanel();
		menuJ4.setBackground(Color.YELLOW);
		menuJ4.setPreferredSize(new Dimension(5*700/16, 500/4));
		//-------------------------------------------------------
		JPanel cell55 = new JPanel();
		cell55.setOpaque(false);
		cell55.setPreferredSize(new Dimension(700/8, 500/4));
		//-------------------------------------------------------
		//
		//Ligne 6
		JPanel cell61 = new JPanel();
		cell61.setOpaque(false);
		cell61.setPreferredSize(new Dimension(700, 500/8));    
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
		panel.add(cell31, gbc);
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
	
	}

}
