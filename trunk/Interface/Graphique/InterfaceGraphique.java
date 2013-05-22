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
    protected GridBagConstraints contraintes;
    public void run(String [] arguments)
    {		
		contraintes = new GridBagConstraints();

		initialiserPanel();
		//Fenetre principale
		frame = new JFrame("pinpin le pingouin");
		//gestion du panel des boutons	
		fond = new Fond("Image_fon.jpg");
		fond.setLayout(new GridBagLayout());
		contraintes.gridy = 1;
		
		fond.add(Panels[0] , contraintes);
		Fond image = new Fond("banniere.png");
		image.setSize(300,100);	
	        
		contraintes.gridy = 0;
		
         		
		fond.add(image ,contraintes);
		frame.add(fond);
		frame.setResizable(true);
		frame.setPreferredSize(new Dimension(300,300));
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
	JPanel pan  = new JPanel();
	pan.setLayout(new BoxLayout(pan, BoxLayout.LINE_AXIS));
	JButton b1 = new JButton(S);
	b1.setSize(new Dimension(150,150));
	b1.addActionListener(new EcouteurDeBouton(S, this));
	pan.add(b1);
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
	addBouton(Panels[0],"Quitter");
	// le panel 1 correspond a la page d'options
	Panels[1].setLayout(new BoxLayout(Panels[1], BoxLayout.PAGE_AXIS));
	Panels[1].setOpaque(false);
	addBouton(Panels[1],"Gestion de profil");
	addBouton(Panels[1],"Classements");		
	addBouton(Panels[1],"Revenir");	
	// le panel 2 est le panel correspondant a la partie personalisé
	Panels[2].setOpaque(false);
	Panels[2].setLayout(new GridLayout( 7 , 2) );
	addBouton(Panels[2],"nom du joueur1");
	addBouton(Panels[2],"nom du joueur2");		
	addBouton(Panels[2],"niveau du joueur1");
	addBouton(Panels[2],"niveau du joueur2");
	addBouton(Panels[2],"avatar joueur 1");		
	addBouton(Panels[2],"avatar joueur 2");
	addBouton(Panels[2],"nom du joueur3");
	addBouton(Panels[2],"nom du joueur4");		
	addBouton(Panels[2],"niveau du joueur3");
	addBouton(Panels[2],"niveau du joueur4");
	addBouton(Panels[2],"avatar joueur 3");		
	addBouton(Panels[2],"avatar joueur 4");
	addBouton(Panels[2],"Revenir");
	// le panel 3 est le panel correspondant au gestion de profil
	Panels[3].setLayout(new BoxLayout(Panels[3], BoxLayout.PAGE_AXIS));
	Panels[3].setOpaque(false);
	addBouton(Panels[3],"Revenir");
	// le panel 4 est le panel correspondant au classement
	Panels[4].setLayout(new BoxLayout(Panels[4], BoxLayout.PAGE_AXIS));
	Panels[4].setOpaque(false);
	addBouton(Panels[4],"Revenir");
	}
    public void removeFrame(JFrame frame){
    
    for(int i=0;i<10;i++)
	{
	
	fond.remove(Panels[i]);
	}
    }
    public void afficherPanel(String S)
	{
	contraintes.gridy = 1;	
	JPanel Oldpan = Panels[0];
	LinkedList<String> Old_page = new LinkedList<String>();
		Old_page.addFirst("Menu Principal");
		if (S.compareTo("Menu Principal") == 0)
		{	
			removeFrame(frame);
			fond.add(Panels[0] , contraintes);
			
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
			joueurs[1] = new JoueurHumain();
			//lancement de la partie
			ArbitreManager.initialiserPartie(joueurs ,ArbitreManager.LARGEUR_GRILLE , ArbitreManager.HAUTEUR_GRILLE, this); 
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
			fond.add(Panels[1]);
			frame.add(fond , contraintes);
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
			fond.add(Panels[2]);
			frame.add(fond , contraintes);
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
			fond.add(Panels[3] , contraintes);
			frame.add(fond);
			frame.repaint();
			frame.pack();	
		}
		if(S.compareTo("Classements") == 0)
		{
			removeFrame(frame);
			Old_page.addFirst("Classement");
			fond.add(Panels[4] , contraintes);
			frame.add(fond);
			frame.repaint();
			frame.pack();	
		}	
	}
}
