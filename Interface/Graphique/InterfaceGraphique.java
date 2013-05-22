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
    GridBagConstraints c1,c2,c3,c4; 
    public void run(String [] arguments)
    {		
		Dimension Dim = new Dimension(700,700);
		initialiserPanel();
		//Fenetre principale
		frame = new JFrame("pinpin le pingouin");
		//gestion du panel des boutons	
	 	initialiserContraintes();
		banniere ban = new banniere("banniere.png",frame);
		
		// on met un layout de grid bag sur font
		fond.setLayout(new GridBagLayout());
		fond.setSize(Dim);
		fond.add(Panels[0],c2);
		fond.add(ban,c1);
		frame.add(fond);
		frame.setResizable(true);
		frame.setPreferredSize(Dim);
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
			fond.add(pan,c4);
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
    public void initialiserContraintes()
	{
	fond = new Fond("Image_fon.jpg",frame);
	c1 = new GridBagConstraints();
	c2 = new GridBagConstraints();
	c3 = new GridBagConstraints();
	c4 = new GridBagConstraints();
	c1.gridx = 1;
	c1.gridy =0;
	c1.gridwidth =3;
	c1.gridheight =1;
	c1.anchor =GridBagConstraints.FIRST_LINE_START;
	c1.fill =GridBagConstraints.BOTH;
	c1.weightx=1;
	c1.weighty=1;	
	c1.ipadx = 0;
	c1.ipady = 0;
	
	c2.gridx =2;
	c2.gridy =1;
	c2.gridwidth =1;
	c2.gridheight =1;
	c2.weightx=1;
	c2.weighty=1;
	c2.anchor = GridBagConstraints.CENTER;
	c2.fill =GridBagConstraints.BOTH;
	c3.gridx =2;
	c3.gridy =4;
	c3.gridwidth =1;
	c3.gridheight =1;
	c3.anchor =GridBagConstraints.LAST_LINE_END;
	c3.fill =GridBagConstraints.BOTH;
	c4.gridx =0;
	c4.gridy =4;
	c4.gridwidth =1;
	c4.gridheight =1;
	c4.anchor = GridBagConstraints.LAST_LINE_START;
	c4.fill =GridBagConstraints.BOTH;

	}
    public void afficherPanel(String S)
	{
	JPanel Oldpan = Panels[0];
	LinkedList<String> Old_page = new LinkedList<String>();
		Old_page.addFirst("Menu Principal");
		if (S.compareTo("Menu Principal") == 0)
		{	
			removeFrame(frame);
			fond.add(Panels[0]);
			
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
			fond.add(Panels[1]);
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
			fond.add(Panels[2]);
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
			fond.add(Panels[3]);
			frame.add(fond);
			frame.repaint();
			frame.pack();	
		}
		if(S.compareTo("Classements") == 0)
		{
			removeFrame(frame);
			Old_page.addFirst("Classement");
			fond.add(Panels[4]);
			frame.add(fond);
			frame.repaint();
			frame.pack();	
		}	
	}
}
