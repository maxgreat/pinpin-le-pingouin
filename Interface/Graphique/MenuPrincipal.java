package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java .io.File;
import java.net.URL;

public class MenuPrincipal{
	protected JPanel fond = new JPanel();
	protected JPanel Menu = new JPanel();
	JFrame frame;
	InterfaceGraphique inter;
	Banniere ban;
	JPanel cellGauche;
	JPanel cellDroite;
	
	public MenuPrincipal(JFrame frame, InterfaceGraphique inter,String s)
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
		fond = new Fond(s,frame);
		fond.setOpaque(true);
		//fond.setPreferredSize(new Dimension(700,500));
		
		
		//------------------------------------------------------
		GridBagConstraints gbc = new GridBagConstraints();
		//On crée nos différents conteneurs
		//Ligne 1
		ban = new Banniere("pinpin.png",frame);
		ban.setPreferredSize(new Dimension(700, (500)/4));
		//------------------------------------------------------
		//
		//ligne 2
		JPanel cell21 = new JPanel();
		cell21.setOpaque(false);
		cell21.setPreferredSize(new Dimension(700, 500/16));
		//------------------------------------------------------
		//
		//Ligne 3
		JPanel cell31 = new JPanel();
		cell31.setOpaque(false);
		cell31.setPreferredSize(new Dimension(700/8, (9*500)/16));
		//-------------------------------------------------------
		JPanel cell32 = new JPanel();
		cell32.setOpaque(false);
		cell32.setPreferredSize(new Dimension(700/8, (9*500)/16));
		//-------------------------------------------------------
		//menu du jeu
		Menu.setLayout(new FlowLayout());
		Menu.setOpaque(false);
		Menu.setPreferredSize(new Dimension(700/2, (9*500)/16));
		//--------------------------------------------------------
		JPanel cell34 = new JPanel();
		cell34.setOpaque(false);
		cell34.setPreferredSize(new Dimension(700/8, (9*500)/16));
		JPanel cell35 = new JPanel();
		cell35.setOpaque(false);
		cell35.setPreferredSize(new Dimension(700/8, (9*500)/16));
		//--------------------------------------------------------
		//
		//Ligne 4
		cellGauche = new JPanel();
		cellGauche.setOpaque(false);
		cellGauche.setPreferredSize(new Dimension(700/8, 500/8)); 
		JPanel cell42 = new JPanel();
		cell42.setOpaque(false);
		cell42.setPreferredSize(new Dimension(700/8, 500/16));   
		JPanel cell43 = new JPanel();
		cell43.setOpaque(false);
		cell43.setPreferredSize(new Dimension(700/2, 500/8));    
		cellDroite = new JPanel();
		JPanel cell44 = new JPanel();
		cell44.setOpaque(false);
		cell44.setPreferredSize(new Dimension(700/8, 500/16));
		cellDroite.setOpaque(false);
		cellDroite.setPreferredSize(new Dimension(700/8, 500/8));
		
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
		//
		//Ligne 4
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		panel.add(cellGauche, gbc);
		//---------------------------------------------
		gbc.gridx = 1;
		panel.add(cell42, gbc);
		//---------------------------------------------
		gbc.gridx = 2;     
		panel.add(cell43, gbc);       
		//---------------------------------------------
		gbc.gridx = 3;     
		panel.add(cell44, gbc); 
		//---------------------------------------------
		gbc.gridx = 4;  
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(cellDroite, gbc);
		//--------------------------------------------

		fond.add(panel);

	}
	
	public void addBouton(JPanel panel, String S)
	{
	JPanel pan = new JPanel();
        File f = new File("Interface/Graphique/Img/"+S);
        JButton b1 = null;
        // Test l'existence du fichier d'image avant d'essayer de le charger
        if(f.exists())
        {
            b1 = new JButton(new ImageIcon("Interface/Graphique/Img/" + S));
            b1.setBorder(BorderFactory.createEmptyBorder());
            b1.setContentAreaFilled(false);
        }
        else
        {
            b1 = new JButton(S);
        }

		b1.addActionListener(new EcouteurDeBouton(S, inter));
		pan.add(b1,BorderLayout.CENTER);
		pan.setOpaque(false);	
		panel.add(pan,BorderLayout.CENTER);
	}
	public void setBoutons(String s)
	{
		if(s.compareTo("demarrage") == 0){
			ban = new Banniere("pinpin.png",frame);
			ban.setPreferredSize(new Dimension(700, (500)/4));
			Menu.setLayout(new GridLayout( 6, 1));
			addBouton(Menu, "partieRapide.png");
			addBouton(Menu, "partiePerso.png");
			addBouton(Menu, "charger.png");
			addBouton(Menu, "options.png");
			addBouton(Menu, "quitter.png");
			addBouton(Menu, "sonAllume.png");
		}else if(s.compareTo("demarrageEteint") == 0){
			ban = new Banniere("pinpin.png",frame);
			ban.setPreferredSize(new Dimension(700, (500)/4));
			Menu.setLayout(new GridLayout( 6, 1));
			addBouton(Menu, "partieRapide.png");
			addBouton(Menu, "partiePerso.png");
			addBouton(Menu, "charger.png");
			addBouton(Menu, "options.png");
			addBouton(Menu, "quitter.png");
			addBouton(Menu, "sonEteint.png");
		}
		else if(s.compareTo("Options") == 0){

			ban = new Banniere("nuageOptions.png",frame);
			ban.setPreferredSize(new Dimension(700, (500)/4));
			Menu.setLayout(new GridLayout( 2 , 1));
			addBouton(Menu, "son.png");
			addBouton(Menu, "reglesDuJeu.png");
			addBouton(cellGauche,"retour.png");
			frame.repaint();
		}
		else if(s.compareTo("Son") == 0){

			ban = new Banniere("nuageSon.png",frame);
			ban.setPreferredSize(new Dimension(700, (500)/4));

			if(!inter.d.getMusic()){
				Menu.setLayout(new GridLayout( 1 , 1));
				addBouton(Menu, "generalDesactive.png");
			} else {
				Menu.setLayout(new GridLayout( 4 , 1));
				addBouton(Menu, "generalActive.png");
				if(!inter.d.getFond())
					addBouton(Menu, "musiqueDesactive.png");
				else
					addBouton(Menu, "musiqueActive.png");
				if(!inter.d.getBruit())
					addBouton(Menu, "bruitagesDesactive.png");
				else
					addBouton(Menu, "bruitagesActive.png");
				addBouton(Menu, "nextSong.png");
			}
			addBouton(cellGauche,"retour.png");
		}
		else if(s.compareTo("Gestion de profil") == 0){
			ban = new Banniere("pinpin.png",frame);
			ban.setPreferredSize(new Dimension(700, (500)/4));
			addBouton(Menu, "Quitter");
			addBouton(cellGauche,"retour.png");
		}
		else if(s.compareTo("regle") == 0){
			ban = new Banniere("pinpin.png",frame);
			ban.setPreferredSize(new Dimension(700, (500)/4));
			addBouton(cellGauche,"retour.png");
			addBouton(cellDroite,"Page 2");
		}
		else if(s.compareTo("Page 2") == 0){
			ban = new Banniere("pinpin.png",frame);
			ban.setPreferredSize(new Dimension(700, (500)/4));
			addBouton(cellGauche,"retour.png");
			addBouton(cellDroite,"Page 3");
		}
		else if(s.compareTo("Page 3") == 0){
			ban = new Banniere("pinpin.png",frame);
			ban.setPreferredSize(new Dimension(700, (500)/4));
			addBouton(cellGauche,"retour.png");
			addBouton(cellDroite,"Page 4");
		}
		else if(s.compareTo("Page 4") == 0){
			ban = new Banniere("pinpin.png",frame);
			ban.setPreferredSize(new Dimension(700, (500)/4));
			addBouton(cellGauche,"retour.png");
			addBouton(cellDroite,"Menu Principal");
		}
		
	
	}

}
