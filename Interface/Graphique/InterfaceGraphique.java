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
    public JPanel pan , panfinal;
    protected AireDeJeu aire;
    protected JFrame frame;
    public void run(String [] arguments)
    {		
		//Fenetre principale
		frame = new JFrame("pinpin le pingouin");
		frame.setResizable(true);
		frame.setPreferredSize(new Dimension(300,300));
		frame.pack();
		//Aire de dessin
		//Boutons
		panfinal = new JPanel();
		panfinal.setOpaque(false);
		JPanel Fond = new JPanel();
		BufferedImage Image_fond;
		Image_fond = null;
		try
		{	
		    Image_fond = ImageIO.read(getImage("Image_fond.png"));
		}
		catch (IOException e) 
                {
                    System.err.println("erreur lecture images 2" +e);
                    System.exit(1);
                }
		addBouton(panfinal,"Partie Rapide");
		addBouton(panfinal,"Partie Personalisé");	
		addBouton(panfinal,"Options");
		addBouton(panfinal,"Quitter");
		panfinal.setLayout(new BoxLayout(panfinal, BoxLayout.PAGE_AXIS));
		frame.add(panfinal);
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
    public void addBouton(JPanel panfinal, String S)
    {
	JPanel pan  = new JPanel();
	pan.setLayout(new BoxLayout(pan, BoxLayout.LINE_AXIS));
	JButton b1 = new JButton(S);
	b1.addActionListener(new EcouteurDeBouton(S, this));
	pan.add(b1);
	panfinal.add(pan);
     }
    private URL getImage(String nom) {
        ClassLoader cl = getClass().getClassLoader();
        return cl.getResource("Interface/Graphique/Img/" + nom);
    }
    public void afficherPanel(String S)
	{
	LinkedList<String> Old_page = new LinkedList<String>();
		Old_page.addFirst("Menu Principal");
		if (S.compareTo("Menu Principal") == 0)
		{
			frame.remove(panfinal);
			panfinal = new JPanel();
			panfinal.setLayout(new BoxLayout(panfinal, BoxLayout.PAGE_AXIS));
			addBouton(panfinal,"Partie Rapide");
			addBouton(panfinal,"Partie Personalisé");	
			addBouton(panfinal,"Options");
			addBouton(panfinal,"Quitter");
			frame.add(panfinal);
			frame.pack();			
			frame.repaint();
		}
		if (S.compareTo("Partie Rapide") == 0 )
		{
			
			frame.remove(panfinal);
			//definition des joueurs
			Joueur [] joueurs = new Joueur[2];
			joueurs[0] = new JoueurHumain();
			joueurs[1] = new JoueurHumain();
			//lancement de la partie
			ArbitreManager.initialiserPartie(joueurs ,ArbitreManager.LARGEUR_GRILLE , ArbitreManager.HAUTEUR_GRILLE, this); 
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
			Old_page.addFirst("Options");
			frame.remove(panfinal);
			panfinal = new JPanel();
			panfinal.setLayout(new BoxLayout(panfinal, BoxLayout.PAGE_AXIS));
			addBouton(panfinal,"Gestion de profil");
			addBouton(panfinal,"Classements");		
			addBouton(panfinal,"Revenir");
			frame.add(panfinal);
			frame.pack();			
			frame.repaint();
					
		}
		if(S.compareTo( "Quitter") == 0 )
		{
			ArbitreManager.stopperPartie();
			System.exit(0);
		}
		if(S.compareTo( "Partie Personalisé") == 0 )
		{
			Old_page.addFirst("Options");
			frame.remove(panfinal);
			panfinal = new JPanel();
			panfinal.setLayout(new GridLayout( 7 , 2) );
			addBouton(panfinal,"nom du joueur1");
			addBouton(panfinal,"nom du joueur2");		
			addBouton(panfinal,"niveau du joueur1");
			addBouton(panfinal,"niveau du joueur2");
			addBouton(panfinal,"avatar joueur 1");		
			addBouton(panfinal,"avatar joueur 2");
			addBouton(panfinal,"nom du joueur3");
			addBouton(panfinal,"nom du joueur4");		
			addBouton(panfinal,"niveau du joueur3");
			addBouton(panfinal,"niveau du joueur4");
			addBouton(panfinal,"avatar joueur 3");		
			addBouton(panfinal,"avatar joueur 4");
			addBouton(panfinal,"Revenir");
			frame.add(panfinal);
			frame.pack();			
			frame.repaint();

			
		}
		if(S.compareTo("Revenir") == 0)
		{
			
			afficherPanel(Old_page.poll());
		}
		if(S.compareTo("Gestion de profil") == 0)
		{
			
			Old_page.addFirst("Gestion de profil");
			frame.remove(panfinal);
			panfinal = new JPanel();
			panfinal.setLayout(new BoxLayout(panfinal, BoxLayout.PAGE_AXIS));
			addBouton(panfinal,"Revenir");
			frame.add(panfinal);
			frame.pack();			
			frame.repaint();

		}
		if(S.compareTo("Classements") == 0)
		{
			
			Old_page.addFirst("Classement");
			frame.remove(panfinal);
			panfinal = new JPanel();
			panfinal.setLayout(new BoxLayout(panfinal, BoxLayout.PAGE_AXIS));
			addBouton(panfinal,"Revenir");
			frame.add(panfinal);
			frame.pack();			
			frame.repaint();

		}
		
	}

}




