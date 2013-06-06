package Interface.Graphique;
import Utilitaires.File;
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
import java.io.*;
import java.awt.image.BufferedImage;
import Sound.*;

public class InterfaceGraphique extends Interface
{
	protected AireDeJeu aire;
	protected JFrame frame;
	protected JFrame regle;    
	protected JPanel panel;
	protected Fond fond;
	protected Banniere ban;
	private LinkedList<String> oldPage;
	Joueur [] joueurstemp = new Joueur[2];
	Utilitaires.File<String> playlist;
	String currentSong;
	Thread music;
	Donnees d;

    MenuPerso menuPerso = null;

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
	    //Initialisation de la playlist
	    playlist = new Utilitaires.File<String>();
	    java.io.File rep = new java.io.File("Sound/Music");
	    String [] data = rep.list();
	    for (int i = 0; i < data.length; i++) {
		    if (!data[i].equals(".svn"))
			    playlist.push("Sound/Music/" + data[i]);
	    }
	    
	    if (arguments.length > 0)
		    filename = arguments[0];

    	//historique de navigation dans les menus
        this.joueurs = new Joueur[2];
		joueurs[0] = new JoueurHumain();
		joueurs[1] = new JoueurCPUUniversel(2);

		joueurs[0].setNom("Joueur");
		joueurs[1].setNom("Ordinateur");

    	oldPage = new LinkedList<String>();
    	oldPage.push("Menu Principal");
    	
    	//Dimension de départ	
		Dimension Dim = new Dimension(700,500);
		
		//creation de la fenetre principale
		frame = new JFrame("Pingouins");
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

		Comportement c;
		d = new Donnees();

		if(d.getFond()){
			String musique = playlist.pull();
			currentSong = musique;
			c = new Comportement(musique,d);
			playlist.push(musique);
			music = new Thread(c);
			music.start();
		}
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
			if(d.getMusic())
				m.setBoutons("demarrage");
			else
				m.setBoutons("demarrageEteint");
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
		if(S.compareTo("son.png") == 0 )
		{
			MenuPrincipal m = new MenuPrincipal(frame, this,"backgroundIce2.png");
			m.setBoutons("Son");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo("nextSong.png") == 0 ) {
			music.stop();
			if(d.getFond()){
				Comportement c;
				String musique = playlist.pull();
				currentSong = musique;
				c = new Comportement(musique,d);
				playlist.push(musique);
				music = new Thread(c);
				music.start();
			}
		}
		if(S.compareTo("generalActive.png") == 0 || S.compareTo( "generalDesactive.png") == 0 )
		{
			if(S.compareTo("generalActive.png") == 0){
				d.setMusic(false);
				music.stop();
			} else {
				d.setMusic(true);
				if(d.getFond()){
					Comportement c;
					c = new Comportement(currentSong,d);
					music = new Thread(c);
					music.start();
				}
			}
			MenuPrincipal m = new MenuPrincipal(frame, this,"backgroundIce2.png");
			m.setBoutons("Son");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo("bruitagesActive.png") == 0 || S.compareTo( "bruitagesDesactive.png") == 0 )
		{
			if(S.compareTo("bruitagesActive.png") == 0)
				d.setBruit(false);
			else
				d.setBruit(true);
			MenuPrincipal m = new MenuPrincipal(frame, this,"backgroundIce2.png");
			m.setBoutons("Son");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo("musiqueActive.png") == 0 || S.compareTo( "musiqueDesactive.png") == 0 )
		{
			if(S.compareTo("musiqueActive.png") == 0){
				d.setFond(false);
				music.stop();
			}else{
				d.setFond(true);
				Comportement c;
				c = new Comportement(currentSong,d);
				music = new Thread(c);
				music.start();
			}
			MenuPrincipal m = new MenuPrincipal(frame, this,"backgroundIce2.png");
			m.setBoutons("Son");
			frame.setContentPane(m.fond);
			frame.pack();				
		}
		if(S.compareTo( "quitter.png") == 0 || S.compareTo("Quitter") == 0)
		{	
			ArbitreManager.stopperPartie();
			System.exit(0);
		}
		if(S.compareTo( "reglesDuJeu.png") == 0 )
		{	
			System.out.println("Ouverture d'une fenetre de regle");
			regle = new JFrame();
			Img image1 = new Img("regles_page_1.png");
			//Img image2 = new Img("regles_page_2.png");
			image1.setPreferredSize(new Dimension(500, 700));
			//image2.setPreferredSize(new Dimension(200, 500));
			JScrollPane panRegle = new JScrollPane(image1);
			panRegle.setPreferredSize(new Dimension(500,500));
			//panRegle.add(image2);
			regle.setContentPane(panRegle);
			regle.setTitle("Regles du Jeu");
			regle.setVisible(true);
			regle.pack();
			
		
		/*regle = new JFrame("Regles");
		regle.setMinimumSize(new Dimension(700,500));
		MenuPrincipal m = new MenuPrincipal(frame, this,"reglePremierePage.png");
		m.setBoutons("regle");
		regle.setContentPane(m.fond);
		regle.pack();
		
		regle.setResizable(true);
		//frame.setMinimumSize(Dim);
		//intercepte la demande de fermeture the close button
		regle.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//create custom close operation
		regle.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
			        // Ferme la partie correctement
			        ArbitreManager.stopperPartie();
			        System.exit(0);
			}
		});
		regle.setVisible(true);	*/
		}
		if(S.compareTo("Page 2") == 0 )
		{	
			oldPage.push("Regles du jeux");
			MenuPrincipal m = new MenuPrincipal(frame, this,"regleDeuxiemePage.png");
			m.setBoutons("Page 2");
			regle.setContentPane(m.fond);
			regle.pack();				
		}
		if(S.compareTo("Page 3") == 0 )
		{	
			oldPage.push("Page 2");
			MenuPrincipal m = new MenuPrincipal(frame, this,"regleTroisiemePage.png");
			m.setBoutons("Page 3");
			regle.setContentPane(m.fond);
			regle.pack();				
		}
		if(S.compareTo("Page 4") == 0 )
		{	
			oldPage.push("Page 3");
			MenuPrincipal m = new MenuPrincipal(frame, this,"regleQuatriemePage.png");
			m.setBoutons("Page 4");
			regle.setContentPane(m.fond);
			regle.pack();				
		}
		if(S.compareTo( "lancer.png") == 0 )
		{	
            int nombreJoueur = 0;
            Joueur j1 = menuPerso.J1.partage.getJoueur();
            Joueur j2 = menuPerso.J2.partage.getJoueur();
            Joueur j3 = menuPerso.J3.partage.getJoueur();
            Joueur j4 = menuPerso.J4.partage.getJoueur();

            if (j1 != null)
                nombreJoueur++;

            if (j2 != null)
                nombreJoueur++;

            if (j3 != null)
                nombreJoueur++;

            if (j4 != null)
                nombreJoueur++;

            joueurstemp = new Joueur[nombreJoueur];

            int i = 0;
            if (j1 != null)
                joueurstemp[i++] = j1;

            if (j2 != null)
                joueurstemp[i++] = j2;

            if (j3 != null)
                joueurstemp[i++] = j3;

            if (j4 != null)
                joueurstemp[i++] = j4;
            
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
			this.menuPerso = new MenuPerso(frame, this);
            
			this.menuPerso.setBoutons("Nouvelle Partie");
			frame.setContentPane(this.menuPerso.fond);
			frame.pack();	
		}
		if(S.compareTo("retour.png") == 0)
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

		if(S.compareTo( "sonAllume.png") == 0 )
		{
			MenuPrincipal m = new MenuPrincipal(frame, this,"backgroundIce2.png");
			m.setBoutons("demarrageEteint");
		  d.setMusic(false);
		  music.stop();
			frame.setContentPane(m.fond);
			frame.pack();	
		}
		if(S.compareTo( "sonEteint.png") == 0 )
		{
			MenuPrincipal m = new MenuPrincipal(frame, this,"backgroundIce2.png");
			m.setBoutons("demarrage");

		  d.setMusic(true);
        Comportement c;
        c = new Comportement(currentSong,d);
        music = new Thread(c);
        music.start();

			frame.setContentPane(m.fond);
			frame.pack();	
		}


	}
	public void setTab(Joueur[] joueurs)
	{
        int size = 0;
      
		for (int i = 0; i < joueurs.length; i++, size++){
			if (joueurs[i] == null)
             break;

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
