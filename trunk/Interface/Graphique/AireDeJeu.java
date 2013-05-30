package Interface.Graphique;
import Arbitre.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import Joueurs.*;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;

import Arbitre.Regles.*;

public class AireDeJeu extends JComponent
{
    Hexagone tabCase;
    JFrame frame;
    JPanel pan;
    InterfaceGraphique inter;
    Point coupPrec;

    boolean showDialog = true;
	boolean popup1 = true;
	boolean menuOuvert = false;
    //images des joueurs
    BufferedImage imageJoueur1 = null;
    BufferedImage imageJoueur2 = null;
    BufferedImage imageJoueur3 = null;
    BufferedImage imageJoueur4 = null;
    BufferedImage boutonMenu = null;
    BufferedImage boutonAnnuler = null;
    BufferedImage boutonRefaire = null;
    BufferedImage fondEau = null;
    BufferedImage posePoisson = null;
    BufferedImage bougePoisson = null;
	BufferedImage entoure = null;
	BufferedImage carreGlace = null;
	BufferedImage placement = null;
	BufferedImage info = null;
	BufferedImage suggest = null;
	BufferedImage poissonJ1 = null;
	BufferedImage poissonJ2 = null;
	BufferedImage poissonJ3 = null;
	BufferedImage poissonJ4 = null;
	BufferedImage caseJ1 = null;
	BufferedImage caseJ2 = null;
	BufferedImage caseJ3 = null;
	BufferedImage caseJ4 = null;
	
    //images des poissons
    private BufferedImage un_poisson, deux_poissons, trois_poissons , poissonRouge;
	

    // Nombre de cases sur la largeur	
    public int largeur;
    // Nombre de cases sur la longueur
    public int hauteur;
	
	protected String s;
    protected double margeHaut, margeGauche, margeDroite, margeBas;
    protected double rayonH, rayonL;
	
    public AireDeJeu(JFrame f, InterfaceGraphique inter){
		frame = f;
	
		largeur = 8;
		hauteur = 8;
		
		this.inter = inter;
		
		//création du tableau de case
	    tabCase = new Hexagone();
	    tabCase.initHexagone();
	    coupPrec = new Point(-1,-1);

		un_poisson = null;
		deux_poissons = null;
		trois_poissons = null;
		poissonRouge = null;
		entoure = null;
        try 
        {
			un_poisson = ImageIO.read(getImage("caseGlaceTest.png"));
			deux_poissons = ImageIO.read(getImage("caseGlaceTest2.png"));
			trois_poissons = ImageIO.read(getImage("caseGlaceTest3.png"));
			poissonRouge = ImageIO.read(getImage("caseGlaceTestPose.png"));
			carreGlace = ImageIO.read(getImage("carreGlace.png"));
			placement = ImageIO.read(getImage("placement.png"));
		} catch (IOException e) {
		        System.err.println("erreur lecture images : " +e);
		        System.exit(1);
		}
		try{
			entoure = ImageIO.read(getImage("entoure.png"));
		}catch(Exception e){
			System.out.println("Erreur lecture image" + e);
		}
		try{
			imageJoueur1 = ImageIO.read(getImage("pingouin1.png"));
			imageJoueur2 = ImageIO.read(getImage("pingouin2.png"));
			//imageJoueur3 = ImageIO.read(getImage("pingouin3.png"));
			//imageJoueur4 = ImageIO.read(getImage("pingouin4.png"));
		}catch(Exception e){
			System.out.println("Erreur lecture image" + e);
		}
		try{
			boutonMenu = ImageIO.read(getImage("boutonMenu.jpg"));
			boutonAnnuler = ImageIO.read(getImage("revenir.png"));
			boutonRefaire = ImageIO.read(getImage("refaire.png"));
			fondEau = ImageIO.read(getImage("backgroundWater.png"));
			info =  ImageIO.read(getImage("caseGlaceTestAide.png"));
			suggest= ImageIO.read(getImage("caseGlaceTestSuggestion.png"));
			poissonJ1 = ImageIO.read(getImage("poisson1.png"));
			poissonJ2 = ImageIO.read(getImage("poisson2.png"));
			//poissonJ3 = ImageIO.read(getImage("poisson3.png"));
			//poissonJ4 = ImageIO.read(getImage("poisson4.png"));
			caseJ1 = ImageIO.read(getImage("caseJoueur1.png"));
			caseJ2 = ImageIO.read(getImage("caseJoueur2.png"));
			//caseJ3 = ImageIO.read(getImage("caseJoueur3.png"));
			//caseJ4 = ImageIO.read(getImage("caseJoueur4.png"));
			
		}catch(Exception e){
			System.out.println("Erreur lecture image" + e);
			System.exit(1);
		}
        
        
    }

    public int getHauteur()
    {
        return hauteur;
    }

    public int getLargeur()
    {
        return largeur;
    }
    
    //
    //affiche les carres des joueurs
    //
    private void afficherCarres(Graphics2D drawable, int margeGauche, int margeHaut)
    {
    	showDialog = true;
    	//joueur1
		drawable.drawImage(carreGlace, 0,0, margeGauche, margeHaut, null);
		
		drawable.setPaint(Color.black);
		drawable.drawString("Joueur 1", margeGauche/4 , margeHaut/6);
		
		//dessin poisson+losange+score
		drawable.drawImage(poissonJ1, margeGauche/4 , margeHaut/4, margeGauche/4 , margeHaut/4, null);
		drawable.drawString(" : " + inter.joueurs[0].getScore(), margeGauche/2,7*margeHaut/16);
		drawable.drawImage(caseJ1, margeGauche/4 , 2*margeHaut/4+1, margeGauche/4 , margeHaut/4, null);	
		drawable.drawString(" : " + inter.joueurs[0].getNombreTuile(), margeGauche/2,11*margeHaut/16);
		
		//joueur 2
		drawable.drawImage(carreGlace,largeur-margeGauche,0,margeGauche,margeHaut, null);
			
		drawable.setPaint(Color.red);
		drawable.drawString("Joueur 2",largeur - 2*margeGauche/3,margeHaut/4);	
		drawable.drawString(" : " + inter.joueurs[1].getScore(),largeur-margeGauche+20,2*margeHaut/4);
		drawable.drawString("Tuiles " + inter.joueurs[1].getNombreTuile(),largeur-margeGauche+20,3*margeHaut/4);
    }       

    private URL getImage(String nom) {
        ClassLoader cl = getClass().getClassLoader();
        return cl.getResource("Interface/Graphique/Img/" + nom);
    }


	//
	//PaintComponent
	//
    public void paintComponent(Graphics g)
    {
    	
	Graphics2D drawable = (Graphics2D) g;
        
        Arbitre arbitre = ArbitreManager.instance;
		
        if (arbitre == null)
        { //erreur
            System.out.println("Erreur dans le moteur du jeu. L'abitre vaut null.");
            System.exit(0);
        }
        
        if (arbitre.partieFinie())
        { //La partie est terminée
            if(inter.joueurs[0].getScore() > inter.joueurs[1].getScore())
			{
                s ="Victoire du joueur 1 - Poissons : " + inter.joueurs[0].getNombreTuile();
			}
            else if(inter.joueurs[0].getScore() < inter.joueurs[1].getScore())
			{
                s ="Victoire du joueur 2";
			}
            else
			{
	    	//scores égaux
	    		if(inter.joueurs[0].getNombreTuile() > inter.joueurs[1].getNombreTuile())
	    		{
                	s ="Victoire du joueur 1 - Tuiles : " + inter.joueurs[0].getNombreTuile();
	    		}
	    		else if(inter.joueurs[0].getScore() < inter.joueurs[1].getScore())
	    		{
                	s = "Victoire du joueur 2";
	    		}
                else
	    		{
                	s ="Egalité" ;	
                }
			}
           
            // on retourne au menu principal

          
		        //default title and icon
			if (showDialog)
			{
				System.out.println(s);
			
				java.awt.EventQueue.invokeLater(new Runnable()
				{
					private String message;
					public void run() 
					{
					JOptionPane.showMessageDialog(null, message, "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
					}

					private Runnable init(String msg)
					{
					message = msg;
					return this;
					}  
				}.init(s));
		    	try
			{
				Thread.sleep(100000);
			}
		catch (InterruptedException e)
			{
			}
				ArbitreManager.stopperPartie();
				inter.afficherPanel("Menu Principal");
				showDialog = false;
			}
			/*
			TODO
			s = "placer vos pingouins sur les cases rouges";
			if (popup1)
			{
				System.out.println(s);
			
				java.awt.EventQueue.invokeLater(new Runnable()
				{
					private String message;
					public void run() 
					{
					JOptionPane.showMessageDialog(null, message, "Informations", JOptionPane.INFORMATION_MESSAGE);
					}

					private Runnable init(String msg)
					{
					message = msg;
					return this;
					}  
				}.init(s));
				popup1 = false;
			}
			*/
	
	
        }//fin traitement partie finie
        
        else  //partie en cours
        {
            //Recuperation de la configuration
            Configuration config = arbitre.getConfiguration();
            //Recuperation du Terrain
            Case [][] c = config.getTerrain();
            
            //Recuperation de la hauteur et de la largeur	
			hauteur = this.getHeight();
			largeur = this.getWidth();
			        
			        
			//redefinition des marges
			margeHaut = (double)hauteur/8.0;
			margeGauche = (double)largeur/8.0;
			margeDroite = margeGauche;
			margeBas = margeHaut;
			
			//calcul des rayons
			rayonH = ((double)hauteur - margeHaut - margeBas) / 12.5;
			rayonL = ((double)largeur - margeGauche - margeDroite) / 16.0;
			
			drawable.drawImage(fondEau, 0,0, largeur, hauteur, null);
			
			
			//dessin des carrés de joueur
			afficherCarres(drawable, (int)margeGauche, (int)margeHaut);
			
			//maj du tableau case
			tabCase.setTab(rayonH, rayonL, margeHaut, margeGauche, largeur, hauteur);
			Joueur joueur;
			
			//On demande a l'utilisateur de placer ses pingouins
			if(ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN){
					drawable.drawImage(placement, largeur/4 + (int)rayonL*3, hauteur-(int)rayonH-10, largeur/4 + 30, 2*(int)rayonH/3, null);
			}
			
			
			
			//Dessin des boutons
			drawable.drawImage(info,(int)largeur- (int)tabCase.largeur    ,hauteur - 2*(int)tabCase.hauteur,(int)tabCase.largeur,(int)tabCase.hauteur,null);
			drawable.drawImage(suggest,(int)largeur-(int)tabCase.largeur ,hauteur + 2*(int)tabCase.hauteur,(int)tabCase.largeur,(int)tabCase.hauteur,null);
			//bouton menu
			drawable.drawImage(boutonMenu, largeur/2-35, 0, 70,50,null);
			//bouton annuler
			drawable.drawImage(boutonAnnuler, largeur/4, hauteur-(int)rayonH-10, (int)rayonL*2+20, (int)rayonH, null);
			//bouton refaire
			drawable.drawImage(boutonRefaire, 3*largeur/4-(int)rayonL, hauteur-(int)rayonH-10, (int)rayonL*2+20, (int)rayonH, null);
	
	

			//Tracage des lignes de 7 pavés
			for(int i=0;i<7;i++){
				for(int j=0;j<4;j++){
					if(c[2*j][i] != null){               
						if(c[2*j][i].getEtat() == Etat.DEUX_POISSONS){
							drawable.drawImage(deux_poissons,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);
						}
						else if(c[2*j][i].getEtat() == Etat.UN_POISSON)
						{
					   		if (arbitre.getMode() == ModeDeJeu.POSE_PINGOUIN)
					   		{
					   			drawable.drawImage(poissonRouge,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);			   		
					   		}
					   		else
					   		{
					   			drawable.drawImage(un_poisson,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);
					   		} 
			
					   	}
						else if(c[2*j][i].getEtat() == Etat.TROIS_POISSONS){
						drawable.drawImage(trois_poissons,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);
						}
						
						joueur = c[2*j][i].getJoueurSurCase();
						
						if(joueur != null){
							if(coupPrec.x == 2*j && coupPrec.y == i)
							{
								drawable.drawImage(entoure,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);
							}
							BufferedImage imageJoueur = null;
							if(joueur == inter.joueurs[0])
							{
								drawable.drawImage(imageJoueur1,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);
							}
							else if(joueur == inter.joueurs[1]){
								drawable.drawImage(imageJoueur2,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);
							}
						}
					}
				}
			}
			// case de suggestions
			
	
			//tracage des lignes de 8
			for(int i=0;i<8;i++){
				for(int j=0;j<4;j++){
					if(c[2*j+1][i] != null){                  
						if(c[2*j+1][i].getEtat() == Etat.DEUX_POISSONS){
							drawable.drawImage(deux_poissons,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur(),null);
						}
						else if(c[2*j+1][i].getEtat() == Etat.UN_POISSON)
						{
							if (arbitre.getMode() == ModeDeJeu.POSE_PINGOUIN)
							{
								drawable.drawImage(poissonRouge,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur(),null);
							}
							else
							{
								drawable.drawImage(un_poisson,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur(),null);
							}
						}
						else if(c[2*j+1][i].getEtat() == Etat.TROIS_POISSONS){
							drawable.drawImage(trois_poissons,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur(),null);
						}
					
						joueur = c[2*j+1][i].getJoueurSurCase();
					
						if(joueur != null){
							if(coupPrec.x == 2*j+1 && coupPrec.y == i)
							{
								drawable.drawImage(entoure,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur(),null);
							}
							BufferedImage imageJoueur = null;
							if(joueur == inter.joueurs[0])
							{
								drawable.drawImage(imageJoueur1,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur(),null);
							}
							else if(joueur == inter.joueurs[1]){
								drawable.drawImage(imageJoueur2,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur(),null);
							}
						}
					}
				}
			}
			
			
    	} //fin affichage partie en cours

    }//fin methode paint

    public void click(int x, int y)
    { 
    	Point p = tabCase.estDansHexagone(x,y);
    	
    	if(p.x != -1 && p.y != -1)
    	{
			if(ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN)
			{	
				if(ArbitreManager.instance.getConfiguration().estCoupPossible(new Coup(p.y, p.x, -1, -1)))
					ArbitreManager.instance.getJoueurCourant().getSignalCoup().envoyerSignal(new Coup(p.y, p.x, -1, -1));
				else
					System.out.println("Coup illegal, pas de positionnement ici !");
			}
	    	else{ //mode jeu
				if(coupPrec.x == -1 || coupPrec.y == -1){
		    		Case [][] t = ArbitreManager.instance.getConfiguration().getTerrain();
		    		if(t[p.x][p.y] != null){
						if(t[p.x][p.y].getJoueurSurCase() == ArbitreManager.instance.getJoueurCourant()) {
							coupPrec.x = p.x;
							coupPrec.y = p.y;
						}
		    		}
				}
				else { //le coup precedent est sur un pingouin du joueur
					Coup c = new Coup(coupPrec.y, coupPrec.x, p.y, p.x);
					if(ArbitreManager.instance.getConfiguration().estCoupPossible(c))
					{
						ArbitreManager.instance.getJoueurCourant().getSignalCoup().envoyerSignal(c);
						coupPrec.x = p.x;
						coupPrec.y = p.y;
					}
		    		else{
						System.out.println("Le coup " + c + " n'est pas pas autorisé"); 
						Case [][] t = ArbitreManager.instance.getConfiguration().getTerrain();
						if(t[p.x][p.y].getJoueurSurCase() == ArbitreManager.instance.getJoueurCourant()) {
			    			coupPrec.x = p.x;
			    			coupPrec.y = p.y;
						}
						else{
			    			coupPrec = new Point(-1, -1);
						}
		    		}

				}
	    	}
    	}
    	else{ //on regarde si on a cliquer sur un bouton	
			if(x > (largeur/2 - 35) && x < (largeur/2 + 35) && y < 50)
			{
				//System.out.println("Clic sur le bouton menu");
				if(!menuOuvert)
				{
					menuOuvert = true;
					
					//definition du menu
					POPMenu popup = new POPMenu();
					popup.addInternalFrameListener(new EcouteurDeFenetre(this));
					
					popup.setVisible(true);
					JPanel pan = new JPanel();
					JButton bQuitter = new JButton("Quitter");
					JButton recommencer = new JButton("Recommencer");
					JButton menuP = new JButton("Menu Principal");
					JButton save = new JButton("Sauvegarder");
					pan.setLayout(new GridLayout( 4, 1));
					recommencer.addActionListener(new EcouteurDeBouton("Recommencer", inter));
					bQuitter.addActionListener(new EcouteurDeBouton("Quitter", inter));
					menuP.addActionListener(new EcouteurDeBouton("Retour Menu Principal", inter));
					save.addActionListener(new EcouteurDeBouton("Sauvegarder", inter));
					pan.add(save,BorderLayout.CENTER);
					pan.add(recommencer,BorderLayout.CENTER);
					pan.add(menuP,BorderLayout.CENTER);
					pan.add(bQuitter,BorderLayout.CENTER);

					popup.setContentPane(pan);
					popup.pack();
					this.add(popup);
					popup.toFront();
					try {
						popup.setSelected(true);
					} catch (java.beans.PropertyVetoException e) {}
				}
			}
			else if(y > hauteur - (int)rayonH){
				if(x > largeur/4 && x < (largeur/4 + (int)rayonL*2))
				{
					System.out.println("Retour");
					ArbitreManager.instance.reculerHistorique();
				}
				else if(x > (3*largeur/4) && x < (3*largeur/4 + (int)rayonL*2))
				{
					System.out.println("Refaire");
					ArbitreManager.instance.avancerHistorique();
				}
			}	
    	}
    	this.repaint();
    }
}
