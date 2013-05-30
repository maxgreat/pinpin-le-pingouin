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
    private LinkedList<String> oldPage;
	String joueur1 , joueur2 , joueur3 , joueur4 , niveau1 , niveau2 , niveau3 , niveau4;
     
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
    	oldPage = new LinkedList<String>();
    	oldPage.push("Menu Principal");
    	
    	//Dimension de départ	
		Dimension Dim = new Dimension(700,500);
		
		//creation de la fenetre principale
		frame = new JFrame("pinpin le pingouin");
		frame.setMinimumSize(new Dimension(700,500));
		MenuPrincipal m = new MenuPrincipal(frame, this,"backgroundIce2.png");
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
			joueurs[1] = new JoueurHumain();
			this.setJoueurs(joueurs);
			initialiserPartie(joueurs);
		}
		else if (S.compareTo("Menu Principal") == 0)
		{	
			oldPage.push("Menu Principal");
			MenuPrincipal m = new MenuPrincipal(frame, this,"backgroundIce2.png");
			m.setBoutons("demarrage");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo( "Options") == 0 )
		{
			MenuPrincipal m = new MenuPrincipal(frame, this,"backgroundIce2.png");
			m.setBoutons("Options");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo( "Quitter") == 0 )
		{	
			ArbitreManager.stopperPartie();
			System.exit(0);
		}
		if(S.compareTo( "Regles du jeux") == 0 )
		{	
			
			oldPage.push("Options");
			MenuPrincipal m = new MenuPrincipal(frame, this,"reglePremierePage.png");
			m.setBoutons("regle");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo("Page 2") == 0 )
		{	
			oldPage.push("Regles du jeux");
			MenuPrincipal m = new MenuPrincipal(frame, this,"regleDeuxiemePage.png");
			m.setBoutons("Page 2");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo("Page 3") == 0 )
		{	
			oldPage.push("Page 2");
			MenuPrincipal m = new MenuPrincipal(frame, this,"regleTroisiemePage.png");
			m.setBoutons("Page 3");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo("Page 4") == 0 )
		{	
			oldPage.push("Page 3");
			MenuPrincipal m = new MenuPrincipal(frame, this,"regleQuatriemePage.png");
			m.setBoutons("Page 4");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo( "Lancer") == 0 )
		{	
			Joueur [] joueurs = new Joueur[2];
			if(joueur1.compareTo("Ordinateur") != 0)
				joueurs[0] = new JoueurHumain();
			if(joueur2.compareTo("Ordinateur") != 0)
				joueurs[1] = new JoueurHumain();
			
			if(joueur1.compareTo("Ordinateur") == 0)
				{
				if(niveau1.compareTo("Facile") == 0)
					joueurs[0] = new JoueurCPURd();
				if(niveau1.compareTo("Intermediaire") == 0)
					joueurs[0] = new JoueurCPUFacile();
				if(niveau1.compareTo("Difficile") == 0)
					joueurs[0] = new JoueurCPUMinimaxIncremental();
				
				}
			if(joueur2.compareTo("Ordinateur") == 0)
				{
				if(niveau2.compareTo("Facile") == 0)
					joueurs[1] = new JoueurCPURd();
				if(niveau2.compareTo("Intermediaire") == 0)
					joueurs[1] = new JoueurCPUFacile();
				if(niveau2.compareTo("Difficile") == 0)
					joueurs[1] = new JoueurCPUMinimaxIncremental();
				
				
				}
			this.setJoueurs(joueurs);
			initialiserPartie(joueurs);
		}
		if(S.compareTo( "Retour Menu Principal") == 0 )
		{	
			ArbitreManager.stopperPartie();
			afficherPanel("Menu Principal");

		}
		if(S.compareTo( "Partie Personalisée") == 0 )
		{
			oldPage.push("Menu Principal");
			MenuPerso m = new MenuPerso(frame, this);
			m.setBoutons("Nouvelle Partie");
			frame.setContentPane(m.fond);
			frame.pack();
		}
		if(S.compareTo("Retour") == 0)
		{
			afficherPanel(oldPage.pop());
		}
		if(S.compareTo( "Recommencer") == 0 )
		{
			System.out.println("on a clicker sur recommencer");
		    ArbitreManager.instance.recommencer();
		}
		
		if(S.compareTo( "Charger") == 0 )
		{
		
		}
		
		if(S.compareTo( "Sauvegarder") == 0 )
		{
		
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
