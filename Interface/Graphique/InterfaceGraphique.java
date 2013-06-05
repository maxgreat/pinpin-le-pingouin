package Interface.Graphique;
import javax.swing.*;
import Arbitre.*;
import Arbitre.Regles.*;
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
    Joueur [] joueurstemp = new Joueur[2];

    public String filename = null;

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
	    JComponent.setDefaultLocale(Locale.FRENCH);
	    
        if (arguments.length > 0)
            filename = arguments[0];

    	//historique de navigation dans les menus
        this.joueurs = new Joueur[2];
		joueurs[0] = new JoueurHumain();
		joueurs[1] = new JoueurCPUCoulyMinimax();

		joueurs[0].setNom("Joueur");
		joueurs[1].setNom("Ordianteur");

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
        if (filename != null)
        {
            Case [][] terrain = Configuration.terrainOfficiel(filename);
            ArbitreManager.initialiserPartie(j ,ArbitreManager.LARGEUR_GRILLE, ArbitreManager.HAUTEUR_GRILLE, this, terrain); 
        }
        else
        {   
            // Lancement de la partie
            ArbitreManager.initialiserPartie(j ,ArbitreManager.LARGEUR_GRILLE, ArbitreManager.HAUTEUR_GRILLE, this); 
        }
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
		
		if (S.compareTo("partieRapide.png") == 0 ){
			//definition des joueurs
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
		if(S.compareTo( "options.png") == 0 )
		{
			MenuPrincipal m = new MenuPrincipal(frame, this,"backgroundIce2.png");
			m.setBoutons("Options");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo( "quitter.png") == 0 || S.compareTo("Quitter") == 0)
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
            System.out.println(joueurstemp.length);
            for (int i = 0; i < joueurstemp.length; i++)
                System.out.println(joueurstemp[i]);

			this.setJoueurs(joueurstemp);
			initialiserPartie(joueurstemp);
		}
		if(S.compareTo( "Retour Menu Principal") == 0 )
		{	
			ArbitreManager.stopperPartie();
			afficherPanel("Menu Principal");

		}

		if(S.compareTo("partiePerso.png") == 0)
		{
			MenuPerso m = new MenuPerso(frame, this);
			m.setBoutons("Nouvelle Partie");
			frame.setContentPane(m.fond);
			frame.pack();	
		}
		if(S.compareTo("Retour") == 0)
		{
			afficherPanel(oldPage.pop());
		}
		if(S.compareTo( "Nouvelle partie") == 0 )
		{
            ArbitreManager.stopperPartie();
            initialiserPartie(getJoueurs());
		}


		if(S.compareTo( "Recommencer") == 0 )
		{
		    ArbitreManager.recommencerPartie();
		}
		
		if(S.compareTo( "charger.png") == 0 )
		{
			Charger c = new Charger(frame, this);
			frame.setContentPane(c.fond);
			frame.pack();	
		}
		
		if(S.compareTo( "Sauvegarder") == 0 )
		{
			String file = JOptionPane.showInputDialog(null,
								      "Nom de la sauvegarde ?",
									      null,
								      JOptionPane.QUESTION_MESSAGE);
			ArbitreManager.sauvegarderPartie("Save/"+file);
		}

	}
	public void setTab(Joueur[] joueurs)
	{
        int size = 0;
      
		for (int i = 0; i < joueurs.length; i++, size++){
			if (joueurs[i] == null)
             System.out.println("joueur "+i+" vide");

      }
        joueurstemp = new Joueur[size];
        for (int i = 0; i < size; i++)
            joueurstemp[i] = joueurs[i];
	}

	public Joueur[] getTab()
	{
      return joueurstemp;
	}


}	
