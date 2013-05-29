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
    private LinkedList<String> Old_page;
     
     
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
    	//historique de navigation dans les menus
    	Old_page = new LinkedList<String>();
    	
    	
    	//Dimension de départ	
		Dimension Dim = new Dimension(700,500);
		
		//creation de la fenetre principale
		frame = new JFrame("pinpin le pingouin");
		frame.setMinimumSize(new Dimension(700,500));
		MenuPrincipal m = new MenuPrincipal(frame, this);
		m.setBoutons("demarrage");
		frame.setContentPane(m.fond);
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
		
		Old_page.addFirst("Menu Principal");
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
    
    public void initialiserPartie(Joueur [] j)
    {		
    		//lancement de la partie
			ArbitreManager.initialiserPartie(j ,ArbitreManager.LARGEUR_GRILLE, ArbitreManager.HAUTEUR_GRILLE, this); 
			ArbitreManager.lancerPartie();
			
			aire = new AireDeJeu(frame, this);
			aire.setPreferredSize(new Dimension(700,500));
			aire.addMouseListener(new EcouteurDeSouris(aire));
			aire.repaint();
			aire.setVisible(true);
			frame.setContentPane(aire);
			frame.pack();
    }
    
    
    
    

    public void afficherPanel(String S)
	{
		if (S.compareTo("Partie Rapide") == 0 ){
			//definition des joueurs
			Joueur [] joueurs = new Joueur[2];
			joueurs[0] = new JoueurHumain();
			joueurs[1] = new JoueurCPUMinimaxIncremental();
			this.setJoueurs(joueurs);
			initialiserPartie(joueurs);
		}
		else if (S.compareTo("Menu Principal") == 0)
		{	
			MenuPrincipal m = new MenuPrincipal(frame, this);
			m.setBoutons("demarrage");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo( "Options") == 0 )
		{
			MenuPrincipal m = new MenuPrincipal(frame, this);
			m.setBoutons("Options");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo( "Quitter") == 0 )
		{	
			ArbitreManager.stopperPartie();
			System.exit(0);
		}
		if(S.compareTo( "Retour Menu Principal") == 0 )
		{	
			ArbitreManager.stopperPartie();
			afficherPanel("Menu Principal");

		}
		if(S.compareTo( "Partie Personalisée") == 0 )
		{
			MenuPerso m = new MenuPerso(frame, this);
			m.setBoutons("Partie Personnalisée");
			frame.setContentPane(m.fond);
			frame.pack();
		}
		if(S.compareTo("Revenir") == 0)
		{
			afficherPanel(Old_page.poll());
		}
		if(S.compareTo( "Recommencer") == 0 )
		{
		    ArbitreManager.instance.recommencer();
		}
		/*if(S.compareTo("Gestion de profil") == 0)
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
		}*/
	}
}
