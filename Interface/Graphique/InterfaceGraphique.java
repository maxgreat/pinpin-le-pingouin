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
    JPanel Panels[] = new JPanel[10];
    public JPanel pan , panfinal;
    public Fond fond;
    protected AireDeJeu aire;
    protected JFrame frame;
    GridBagConstraints banniere,joueur1,joueur2,joueur3,joueur4,menu,quitter,jouer; 
    public void run(String [] arguments)
    {		
		Dimension Dim = new Dimension(700,500);
		initialiserPanel();
		//Fenetre principale
		frame = new JFrame("pinpin le pingouin");
		//gestion du panel des boutons	
	 	initialiserContraintes();
		Banniere ban = new Banniere("banniere.png",frame);
		
		// on met un layout de grid bag sur fond
		fond.setLayout(new GridBagLayout());

		fond.add(Panels[0],menu);
		fond.add(Panels[5],quitter);
		fond.add(ban,banniere);
		frame.add(fond);
		frame.setResizable(true);
		frame.setMinimumSize(Dim);
		frame.pack();
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
		;
		frame.setVisible(true);
    }
    /**
     * Repaint l'interface (ici l'AireDeJeu)
     **/
    public void repaint()
    {
	if(aire != null)
		{        
		aire.repaint();
		
    		}
    }	
    public AireDeJeu getAire()
    {
        return aire;
    }
    public void addBouton(JPanel panel, String S)
    {	
	initialiserContraintes();
	JPanel pan  = new JPanel();
	pan.setLayout(new BoxLayout(pan, BoxLayout.LINE_AXIS));
	JButton b1 = new JButton(S);
	b1.addActionListener(new EcouteurDeBouton(S, this));
	pan.add(b1);
		if(S.compareTo("Revenir")==0)
		{
			fond.add(pan,joueur3);
		}
	pan.setOpaque(false);	
	panel.add(pan);
     }
    public void initialiserPanel()
	{
	for(int i = 0;i<10;i++)
	{
		Panels[i] = new JPanel();
	}
	// le panel 0 correspond a la page d'acceuil
	Panels[0].setLayout(new BoxLayout(Panels[0], BoxLayout.PAGE_AXIS));
	Panels[0].setOpaque(false);
	addBouton(Panels[0],"Partie Rapide");
	addBouton(Panels[0],"Partie Personalisé");	
	addBouton(Panels[0],"Options");

	// le panel 1 correspond a la page d'options
	Panels[1].setLayout(new BoxLayout(Panels[1], BoxLayout.PAGE_AXIS));
	Panels[1].setOpaque(false);
	addBouton(Panels[1],"Gestion de profil");
	addBouton(Panels[1],"Classements");			
	// le panel 2 est le panel correspondant a la partie personalisé
	Panels[6].setOpaque(false);
	Panels[6].setLayout(new GridLayout( 3 , 1) );
	addBouton(Panels[6],"nom du joueur1");
	addBouton(Panels[6],"niveau du joueur1");
	addBouton(Panels[6],"avatar joueur 1");

	Panels[7].setOpaque(false);
	Panels[7].setLayout(new GridLayout( 3 , 1) );
	addBouton(Panels[7],"nom du joueur2");
	addBouton(Panels[7],"niveau du joueur2");
	addBouton(Panels[7],"avatar joueur 2");

	Panels[8].setOpaque(false);
	Panels[8].setLayout(new GridLayout( 3 , 1) );
	addBouton(Panels[8],"nom du joueur3");
	addBouton(Panels[8],"niveau du joueur3");
	addBouton(Panels[8],"avatar joueur 3");

	Panels[9].setOpaque(false);
	Panels[9].setLayout(new GridLayout( 3 , 1) );
	addBouton(Panels[9],"nom du joueur4");
	addBouton(Panels[9],"niveau du joueur4");
	addBouton(Panels[9],"avatar joueur 4");
	// le panel 3 est le panel correspondant au gestion de profil
	Panels[3].setLayout(new BoxLayout(Panels[3], BoxLayout.PAGE_AXIS));
	Panels[3].setOpaque(false);
	// le panel 4 est le panel correspondant au classement
	Panels[4].setLayout(new BoxLayout(Panels[4], BoxLayout.PAGE_AXIS));
	Panels[4].setOpaque(false);
	//le panel correspondant au deux boutons quitter et revenir
	Panels[5].setLayout(new BoxLayout(Panels[5], BoxLayout.PAGE_AXIS));
	Panels[5].setOpaque(false);
	addBouton(Panels[5],"Revenir");
	addBouton(Panels[5],"Quitter");
	}
    public void removeFrame(JFrame frame){
    
    for(int i=0;i<10;i++)
	{
	
	fond.remove(Panels[i]);
	}
    }
    public void initialiserContraintes()
	{
	fond = new Fond("Image_fon.jpg",frame);
	banniere = new GridBagConstraints();
	joueur1 = new GridBagConstraints();
	joueur2 = new GridBagConstraints();
	joueur3 = new GridBagConstraints();
	joueur4 = new GridBagConstraints();
	menu = new GridBagConstraints();
	quitter = new GridBagConstraints();
	jouer = new GridBagConstraints();
	// la banniere
	banniere.gridx = 0;
	banniere.gridy =0;
	banniere.gridwidth =7;
	banniere.gridheight =2;
	banniere.anchor =GridBagConstraints.FIRST_LINE_START;
	banniere.fill =GridBagConstraints.BOTH;
	banniere.weightx=1;
	banniere.weighty=1;	
	
	//le joueur 1 dans la configuration
	joueur1.gridx =1;
	joueur1.gridy =3;
	joueur1.gridwidth =2;
	joueur1.gridheight =2;
	joueur1.weightx=0;
	joueur1.weighty=0;
	joueur1.anchor = GridBagConstraints.CENTER;
	joueur1.fill =GridBagConstraints.NONE;

	//le joueur 2 dans la configuration
	joueur2.gridx =4;
	joueur2.gridy =3;
	joueur2.gridwidth =2;
	joueur2.gridheight =2;
	joueur2.weightx=0;
	joueur2.weighty=0;
	joueur2.anchor = GridBagConstraints.CENTER;
	joueur2.fill =GridBagConstraints.NONE;

	//le joueur 3 dans la configuration
	joueur3.gridx =1;
	joueur3.gridy =6;
	joueur3.gridwidth =2;
	joueur3.gridheight =2;
	joueur3.weightx=0;
	joueur3.weighty=0;
	joueur3.anchor = GridBagConstraints.CENTER;
	joueur3.fill =GridBagConstraints.NONE;

	//le joueur 4 dans la configuration
	joueur4.gridx =4;
	joueur4.gridy =6;
	joueur4.gridwidth =2;
	joueur4.gridheight =2;
	joueur4.weightx=0;
	joueur4.weighty=0;
	joueur4.anchor = GridBagConstraints.CENTER;
	joueur4.fill =GridBagConstraints.NONE;
	// les menu
	menu.gridx =1;
	menu.gridy =3;
	menu.gridwidth =3;
	menu.gridheight =6;
	menu.weightx=0;
	menu.weighty=0;
	menu.anchor = GridBagConstraints.CENTER;
	//menu.fill =GridBagConstraints.BOTH;
	// quitter revenir
	quitter.gridx =1;
	quitter.gridy =9;
	quitter.gridwidth =1;
	quitter.gridheight =1;
	quitter.weightx=0.5;
	quitter.weighty=0.5;
	quitter.anchor = GridBagConstraints.CENTER;
	quitter.fill =GridBagConstraints.NONE;	
	// jouer
	jouer.gridx =5;
	jouer.gridy =9;
	jouer.gridwidth =1;
	jouer.gridheight =1;
	jouer.weightx=0;
	jouer.weighty=0;
	jouer.anchor = GridBagConstraints.CENTER;
	jouer.fill =GridBagConstraints.NONE;



	}
    public void afficherPanel(String S)
	{
	JPanel Oldpan = Panels[0];
	LinkedList<String> Old_page = new LinkedList<String>();
		Old_page.addFirst("Menu Principal");
		if (S.compareTo("Menu Principal") == 0)
		{	
			removeFrame(frame);
			fond.add(Panels[0],menu);
			fond.add(Panels[5],quitter);
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
			fond.add(Panels[1],menu);
			fond.add(Panels[5],quitter);
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
			fond.add(Panels[2],menu);
			fond.add(Panels[5],quitter);
			fond.add(Panels[6],joueur1);
			fond.add(Panels[7],joueur2);
			fond.add(Panels[8],joueur3);
			fond.add(Panels[9],joueur4);
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
			fond.add(Panels[3],menu);
			fond.add(Panels[5],quitter);
			frame.add(fond);
			frame.repaint();
			frame.pack();	
		}
		if(S.compareTo("Classements") == 0)
		{
			removeFrame(frame);
			Old_page.addFirst("Classement");
			fond.add(Panels[4],menu);
			fond.add(Panels[5],quitter);
			frame.add(fond);
			frame.repaint();
			frame.pack();	
		}	
	}
}
