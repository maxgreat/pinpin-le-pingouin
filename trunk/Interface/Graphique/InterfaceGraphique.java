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

public class InterfaceGraphique extends Interface
{
    protected AireDeJeu aire;
    protected JFrame frame;
    protected JPanel panel;
    protected Fond fond;
	protected Banniere ban;
     
    public void addBouton(JPanel panel, String S)
	{
		JPanel pan = new JPanel();

		pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
		JButton b1 = new JButton(S);
		b1.addActionListener(new EcouteurDeBouton(S, this));
		pan.add(b1);
		pan.setOpaque(false);	
		panel.add(pan);
	}
     
     
    public void run(String [] arguments)
    {	
    	//Dimension de départ	
		Dimension Dim = new Dimension(700,500);
		
		//creation de la fenetre principale
		frame = new JFrame("pinpin le pingouin");
		frame.setMinimumSize(new Dimension(700,500));
		
		//creation du grid panel
		panel = new JPanel();
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
		Banniere ban = new Banniere("pinpin.png",frame);
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
		JPanel Menu = new JPanel();
		Menu.setLayout(new FlowLayout());
		Menu.setOpaque(false);
		addBouton(Menu,"Partie Rapide");
		addBouton(Menu,"Partie Personalisé");	
		addBouton(Menu,"Options");
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
		frame.setContentPane(fond);
		frame.pack();	
		
		frame.setResizable(true);
		//frame.setMinimumSize(Dim);
		//intercepte la demande de fermeture the close button
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//create custom close operation
		frame.addWindowListener(new WindowAdapter()
		    {
			    public void windowClosing(WindowEvent e)
			    {
			        // Ferme la partie correctement
			        ArbitreManager.stopperPartie();
			        System.exit(0);
			    }
		    });
		frame.setVisible(true);
    }
    
    /**
     * Repaint l'interface (ici l'AireDeJeu)
     **/
    public void repaint()
    {
		if(aire != null)   
			aire.repaint();
    }	
    
    public AireDeJeu getAire()
    {
        return aire;
    }
   
    public void removeFrame(JFrame frame){
    	frame.remove(fond);
		frame.remove(ban);
    }

    public void afficherPanel(String S)
	{
		//creation de la fenetre principale
		frame.remove(fond);
		
		//definition des joueurs
		Joueur [] joueurs = new Joueur[2];
		joueurs[0] = new JoueurHumain();
		joueurs[1] = new JoueurCPUFacile();
		//lancement de la partie
		ArbitreManager.initialiserPartie(joueurs ,ArbitreManager.LARGEUR_GRILLE, ArbitreManager.HAUTEUR_GRILLE, this); 
		ArbitreManager.lancerPartie();
		this.setJoueurs(joueurs);
		aire = new AireDeJeu(frame, this);
		aire.setPreferredSize(new Dimension(700,500));
		aire.addMouseListener(new EcouteurDeSouris(aire));
		aire.repaint();
		aire.setVisible(true);
		frame.setContentPane(aire);
		frame.pack();
	
	/*	JPanel Oldpan = P.Tab[0];
		LinkedList<String> Old_page = new LinkedList<String>();
		Old_page.addFirst("Menu Principal");
		if (S.compareTo("Menu Principal") == 0)
		{	
			
			removeFrame(frame);
			Menu.setLayout(new GridLayout(3,3));
			//ligne1
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			//ligne2
			Menu.add(new JPanel());
			Menu.add(P.Tab[0]);
			Menu.add(new JPanel());

			//ligne3
			Menu.add(P.Tab[2]);
			Menu.add(new JPanel());
			Menu.add(new JPanel());
	
			
			fond.add(ban);
			fond.add(Menu);
			frame.add(fond);
			frame.repaint();
			frame.pack();				
		}
		if (S.compareTo("Partie Rapide") == 0 )
		{	
			removeFrame(frame);
			frame.remove(fond);
			//definition des joueurs
			Joueur [] joueurs = new Joueur[2];
			joueurs[0] = new JoueurHumain();
			joueurs[1] = new JoueurCPUFacile();
			//lancement de la partie
			ArbitreManager.initialiserPartie(joueurs ,ArbitreManager.LARGEUR_GRILLE, ArbitreManager.HAUTEUR_GRILLE, this); 
			ArbitreManager.lancerPartie();
			this.setJoueurs(joueurs);
			aire = new AireDeJeu(frame , this);
			aire.setPreferredSize(new Dimension(500,500));
			aire.addMouseListener(new EcouteurDeSouris(aire));
			frame.add(aire);
			aire.repaint();
			aire.setVisible(true);
			frame.repaint();
			frame.pack();	
		}
		if(S.compareTo( "Options") == 0 )
		{
			removeFrame(frame);
			Old_page.addFirst("Options");			
			Menu.add(P.Tab[1]);
			Menu.add(P.Tab[5]);
			fond.add(ban);
			fond.add(Menu);
			frame.add(fond);
			frame.repaint();
			frame.pack();			
		}
		if(S.compareTo( "Quitter") == 0 )
		{	
			removeFrame(frame);
			ArbitreManager.stopperPartie();
			System.exit(0);
		}
		if(S.compareTo( "Partie Personalisé") == 0 )
		{
			removeFrame(frame);
			Old_page.addFirst("Options");
			fond.add(Menu);
			Menu.setLayout(new GridLayout(5,5));
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(P.Tab[6]);
			Menu.add(new JPanel());
			Menu.add(P.Tab[7]);
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(P.Tab[8]);
			Menu.add(new JPanel());
			Menu.add(P.Tab[9]);
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			Menu.add(new JPanel());
			
			Menu.add(P.Tab[5]);
			
			fond.add(Menu);
			frame.add(fond);
			frame.repaint();
			frame.pack();	
		}
		if(S.compareTo("Revenir") == 0)
		{
			afficherPanel(Old_page.poll());
		}
		if(S.compareTo("Gestion de profil") == 0)
		{
			removeFrame(frame);
			Old_page.addFirst("Gestion de profil");
			Menu.add(P.Tab[3]);
			Menu.add(P.Tab[5]);
			fond.add(ban);
			fond.add(Menu);
			frame.add(fond);
			frame.repaint();
			frame.pack();	
		}
		if(S.compareTo("Classements") == 0)
		{
			removeFrame(frame);
			
			Old_page.addFirst("Classement");
			Menu.add(P.Tab[4]);
			Menu.add(P.Tab[5]);
			fond.add(Menu);
			frame.add(fond);
			frame.repaint();
			frame.pack();	
		}	*/
	}
}
