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

    public JPanel Menu;
    protected AireDeJeu aire;
    protected JFrame frame;
    Banniere ban ;
    public Fond fond;
    Panels P = new Panels(this);

     
    public void run(String [] arguments)
    {		
		
		Dimension Dim = new Dimension(700,500);
		frame = new JFrame("pinpin le pingouin");
		fond = new Fond("Image_fon.jpg",frame);
		ban = new Banniere("banniere.png",frame);
			 	
		
		// on met un layout de grid bag sur fond
		fond.setLayout(new GridLayout(2,1));
		fond.add(ban);
		JPanel Menu = new JPanel();
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
		
		fond.add(Menu);
		frame.add(fond);
		frame.setResizable(true);
		frame.setMinimumSize(Dim);
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
   
    public void removeFrame(JFrame frame){
    	frame.remove(fond);
	frame.remove(ban);
	fond = new Fond("Image_fon.jpg",frame);
	fond.setLayout(new GridLayout(2,1));
		fond.add(ban);
	Menu = new JPanel();
	Menu.setLayout(new GridLayout(3,3));
    }

    public void afficherPanel(String S)
	{
	JPanel Oldpan = P.Tab[0];
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
		}	
	}
}
