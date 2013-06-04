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
    private Hexagone tabCase;
    JFrame frame;
    JPanel pan;
    InterfaceGraphique inter;
    private Point clicPrec;
    private Point clicPrec2;
	private Coup coupPrec;
	int largeurMenu;
	int hauteurMenu;
	int largeurAide;
	int hauteurAide;
	int largeurAnnuler;
	int hauteurAnnuler;



    private Point centreAide = null;
    private Point centreSuggestion = null;

    boolean showDialog = true;
	boolean popup1 = true;
	boolean menuOuvert = false;
	boolean aide = false;

    //definition des images
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
	BufferedImage nonInfo = null;
	BufferedImage suggest = null;
	BufferedImage infoBulle = null;
	BufferedImage poissonJ1 = null;
	BufferedImage poissonJ2 = null;
	BufferedImage poissonJ3 = null;
	BufferedImage poissonJ4 = null;
	BufferedImage caseJ1 = null;
	BufferedImage caseJ2 = null;
	BufferedImage caseJ3 = null;
	BufferedImage caseJ4 = null;
	
    //images des poissons
    BufferedImage un_poisson = null;
    BufferedImage deux_poissons = null;
    BufferedImage trois_poissons = null;
    BufferedImage poissonRouge = null;
	BufferedImage un_poisson_aide = null;
	BufferedImage deux_poissons_aide = null;
    BufferedImage trois_poissons_aide = null;

    // largeur de l'aire	
    public int largeur;
    // hauteur de l'aire
    public int hauteur;
	protected String s2;
	protected String s;
    protected double margeHaut, margeGauche, margeDroite, margeBas;
    protected double rayonH, rayonL;
	
	
	//-------------------------------------------------
	//Constructeur
    //-------------------------------------------------
    public AireDeJeu(JFrame f, InterfaceGraphique inter)
    {
		frame = f;
	
		largeur = 8;
		hauteur = 8;
		
		this.inter = inter;
		
		//création du tableau de case
	    tabCase = new Hexagone();
	    tabCase.initHexagone();
	    clicPrec = new Point(-1,-1);
		coupPrec = new Coup(-1,-1,-1,-1);
		aide = true;
		
		//
		//Chargement des Images
        try 
        {
			un_poisson = ImageIO.read(getImage("caseGlaceTest.png"));
			deux_poissons = ImageIO.read(getImage("caseGlaceTest2.png"));
			trois_poissons = ImageIO.read(getImage("caseGlaceTest3.png"));
			poissonRouge = ImageIO.read(getImage("caseGlaceTestPose.png"));
			carreGlace = ImageIO.read(getImage("carreGlace.png"));
			placement = ImageIO.read(getImage("placement.png"));
			entoure = ImageIO.read(getImage("entoure.png"));
         un_poisson_aide = ImageIO.read(getImage("caseGlaceTestPose.png"));
			deux_poissons_aide = ImageIO.read(getImage("caseGlaceTest2Aide.png"));
			trois_poissons_aide = ImageIO.read(getImage("caseGlaceTest3Aide.png"));
		}catch(Exception e){
			System.out.println("Erreur lecture image" + e);
		}
		try{ //chargement image joueurs
			imageJoueur1 = ImageIO.read(getImage("pingouin1.png"));
			imageJoueur2 = ImageIO.read(getImage("pingouin2.png"));
			//imageJoueur3 = ImageIO.read(getImage("pingouin3.png"));
			//imageJoueur4 = ImageIO.read(getImage("pingouin4.png"));
		}catch(Exception e){
			System.out.println("Erreur lecture image" + e);
		}
		try{  //chargement images boutons et icones
			boutonMenu = ImageIO.read(getImage("boutonMenu.jpg"));
			boutonAnnuler = ImageIO.read(getImage("revenir.png"));
			boutonRefaire = ImageIO.read(getImage("refaire.png"));
			fondEau = ImageIO.read(getImage("backgroundWater.png"));
			info =  ImageIO.read(getImage("caseGlaceTestAide.png"));
         	nonInfo =  ImageIO.read(getImage("caseGlaceTestNonAide.png"));
			suggest= ImageIO.read(getImage("caseGlaceTestSuggestion.png"));
			infoBulle = ImageIO.read(getImage("caseGlaceTestNonAide.png"));
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

    //-------------------------------------------------
    public int getHauteur()
    {
        return hauteur;
    }

    //-------------------------------------------------
    public int getLargeur()
    {
        return largeur;
    }
    
    
    //-------------------------------------------------
    //affiche les carres des joueurs
    //-------------------------------------------------
    private void afficherCarres(Graphics2D drawable, int margeGauche, int margeHaut)
    {
    	showDialog = true;
    	//joueur1
		drawable.drawImage(carreGlace, 0,0, margeGauche, margeHaut, null);
		drawable.drawImage(imageJoueur1, margeGauche, 0 , 2*margeGauche/3, 2*margeHaut/3, null);
		
		drawable.setPaint(Color.black);
		drawable.drawString(inter.joueurs[0].getNom(), margeGauche/4 , margeHaut/6);
		
		//dessin poisson+losange+score
		drawable.drawImage(poissonJ1, margeGauche/4 , margeHaut/4, margeGauche/4 , margeHaut/4, null);
		drawable.drawString(" : " + inter.joueurs[0].getScore(), margeGauche/2,7*margeHaut/16);
		drawable.drawImage(caseJ1, margeGauche/4 , 2*margeHaut/4+1, margeGauche/4 , margeHaut/4, null);
		drawable.drawString(" : " + inter.joueurs[0].getNombreTuile(), margeGauche/2,11*margeHaut/16);
		
		
		
		//joueur 2
		drawable.drawImage(carreGlace,largeur-margeGauche,0,margeGauche,margeHaut, null);
		drawable.drawImage(imageJoueur2, largeur-margeGauche-2*margeGauche/3, 0 , 2*margeGauche/3, 2*margeHaut/3, null);
			
			
		drawable.drawString(inter.joueurs[1].getNom(),largeur - 3*margeGauche/4,margeHaut/6);	
		
		//dessin poisson+losange+score
		drawable.drawImage(poissonJ2, largeur-3*margeGauche/4 , margeHaut/4, margeGauche/4 , margeHaut/4, null);
		drawable.drawString(" : " + inter.joueurs[1].getScore(),largeur-2*margeGauche/4,7*margeHaut/16);
		drawable.drawImage(caseJ2, largeur-3*margeGauche/4 , 2*margeHaut/4+1, margeGauche/4 , margeHaut/4, null);
		drawable.drawString(" : " + inter.joueurs[1].getNombreTuile(),largeur-2*margeGauche/4,11*margeHaut/16);
    }       


    private URL getImage(String nom) {
        ClassLoader cl = getClass().getClassLoader();
        return cl.getResource("Interface/Graphique/Img/" + nom);
    }


    //-------------------------------------------------
	//affiche de n pingouins pour le joueur j
    //-------------------------------------------------
	private void afficherNPingouins(Graphics2D drawable, int j, int n)
	{
		switch(j){
			case 0: //joueur 1
				{ 
					for(int i = 1; i <= n; i++)
					{
						drawable.drawImage(imageJoueur1, (i-1)*(int)margeGauche/4, (int)margeHaut, (int)margeGauche/2, (int)margeHaut/2, null);
					}
					break;
				}	
			case 1: //joueur 2
				{ 
					for(int i = 1; i <= n; i++)
					{
						drawable.drawImage(imageJoueur2, largeur-((int)margeGauche - (i-2)*(int)margeGauche/4), (int)margeHaut, (int)margeGauche/2, (int)margeHaut/2, null);
					}
					break;
				}	
		/*	case 2: //joueur 3
				{ 
			
					break;
				}	
			case 3: //joueur 4
				{ 
			
					break;
				}*/	
		}
	}

    //-------------------------------------------------
	//affichage des pingouins qu'il reste à placer
    //-------------------------------------------------
	private void afficherPingouins(Graphics2D drawable)
	{
		//on récupère le nombre de pingouins de chaque joueur
		int [] nbPing = ArbitreManager.instance.getConfiguration().getNombrePingouinsParJoueur(inter.joueurs);
		
		//Pour chaque joueur on affiche son nombre de pingouins
		for(int i = 0; i < nbPing.length; i++){
			if(nbPing.length == 2){
				afficherNPingouins(drawable, i, 4 - nbPing[i]);
			}
			else if(nbPing.length == 3){
				afficherNPingouins(drawable, i, 4 - nbPing[i]);
			}
			else if(nbPing.length == 4){
				afficherNPingouins(drawable, i, 4 - nbPing[i]);
			}
		}
	}


    //-------------------------------------------------
	//PaintComponent
    //-------------------------------------------------
    public void paintComponent(Graphics g)
    {
    	
		Graphics2D drawable = (Graphics2D) g;
        
        Arbitre arbitre = ArbitreManager.instance;
			hauteur = this.getHeight();
			largeur = this.getWidth();
        if (arbitre == null)
            return;
        
        if (arbitre.partieFinie())
        { //La partie est terminée
            arbitre.setPartieFinie(false);
            if(inter.joueurs[0].getScore() > inter.joueurs[1].getScore())
			{
                s ="Victoire du joueur 1 - Poissons : " + inter.joueurs[0].getScore()+", Tuiles : "+ inter.joueurs[0].getNombreTuile()+" !!!";
                s2 = "Defaite du joueur 2 - Poissons : " + inter.joueurs[1].getScore()+", Tuiles : "+ inter.joueurs[1].getNombreTuile()+".";
			}
            else if(inter.joueurs[0].getScore() < inter.joueurs[1].getScore())
			{
                s2 = "Defaite du joueur 1 - Poissons : " + inter.joueurs[0].getScore()+", Tuiles : "+inter.joueurs[0].getNombreTuile()+".";
                s ="Victoire du joueur 2 - Poissons : "+inter.joueurs[1].getScore()+", Tuiles : "+inter.joueurs[1].getNombreTuile()+" !!!";
			}
            else
			{//scores égaux
	    		if(inter.joueurs[0].getNombreTuile() > inter.joueurs[1].getNombreTuile())
	    		{
                 s ="   Victoire du joueur 1 - Poissons : " + inter.joueurs[0].getScore()+", Tuiles : "+ inter.joueurs[0].getNombreTuile()+" !!!   ";
                 s2 = "   Defaite du joueur 2 - Poissons : " + inter.joueurs[1].getScore()+", Tuiles : "+ inter.joueurs[1].getNombreTuile()+".   ";
	    		}
	    		else if(inter.joueurs[0].getScore() < inter.joueurs[1].getScore())
	    		{
                 s2 = "   Defaite du joueur 1 - Poissons : " + inter.joueurs[0].getScore()+", Tuiles : "+inter.joueurs[0].getNombreTuile()+".   ";
                 s ="   Victoire du joueur 2 - Poissons : "+inter.joueurs[1].getScore()+", Tuiles : "+inter.joueurs[1].getNombreTuile()+" !!!   ";
	    		}
                else
	    		{
                	s ="   Egalité   ";
                  s2 = "   Poissons : " + inter.joueurs[0].getScore()+", Tuiles : "+inter.joueurs[0].getNombreTuile()+".   ";
                }
			}
           
            // on retourne au menu principal

          
		    //default title and icon
			if (showDialog)
			{
				System.out.println(s);
			
				POPMenuVictoire popup = new POPMenuVictoire(hauteur,largeur);
				popup.addInternalFrameListener(new EcouteurDeFenetre(this));
				JLabel score = new JLabel (s);
           		JLabel score2 = new JLabel(s2);
				popup.setVisible(true);
				JPanel panFin = new JPanel();
				JButton bQuitter = new JButton("Quitter");
				JButton recommencer = new JButton("Recommencer");
				JButton menuP = new JButton("Menu Principal");
				JButton save = new JButton("Sauvegarder");
				panFin.setLayout(new GridLayout( 6, 1));
				recommencer.addActionListener(new EcouteurDeBoutonMenuVictoire("Recommencer", inter, popup));
				bQuitter.addActionListener(new EcouteurDeBoutonMenuVictoire("Quitter", inter, popup));
				menuP.addActionListener(new EcouteurDeBoutonMenuVictoire("Retour Menu Principal", inter, popup));
				save.addActionListener(new EcouteurDeBoutonMenuVictoire("Sauvegarder", inter, popup));
           		panFin.add(score);
           		panFin.add(score2);
				panFin.add(save);
				panFin.add(recommencer);
				panFin.add(menuP);
				panFin.add(bQuitter);
           		popup.setSize(largeur/3,hauteur);
				popup.setContentPane(panFin);
				popup.pack();
				this.add(popup);
				popup.toFront();
				try {
					popup.setSelected(true);
				} catch (java.beans.PropertyVetoException e) {}
				showDialog = false;
			}
	
	
        }//fin traitement partie finie 
        
        //Recuperation de la configuration
        Configuration config = arbitre.getConfiguration();
        //Recuperation du Terrain
        Case [][] c = config.getTerrain();
        
        //Recuperation de la hauteur et de la largeur	
	
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
		
		
		//Si on est en mode pose pingouins
		if(ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN)
		{
				afficherPingouins(drawable);
		}
		
		
		
		//Dessin des boutons
		largeurMenu = largeur/8;
		hauteurMenu = (int)margeHaut/2;
		largeurAide = (int)tabCase.largeur()/2;
		hauteurAide = (int)tabCase.hauteur()/2;
		largeurAnnuler = largeur/8;
		hauteurAnnuler = (int)margeHaut/4;
		
		//bouton aide
		if(aide)
			drawable.drawImage(info, largeur/2-largeurMenu/2-largeurAide, 0, largeurAide, hauteurAide,null);
		else
			drawable.drawImage(nonInfo,largeur/2-largeurMenu/2-largeurAide, 0, largeurAide, hauteurAide,null);
		//bouton info bulle
		drawable.drawImage(infoBulle, largeur/2+largeurMenu/2, 0, largeurAide, hauteurAide, null);
		//bouton suggestion
		drawable.drawImage(suggest,largeur-(int)tabCase.largeur, hauteur/2, (int)tabCase.largeur,(int)tabCase.hauteur,null);
		//bouton menu
		drawable.drawImage(boutonMenu, largeur/2-largeurMenu/2, 0, largeurMenu,hauteurMenu,null);
		//bouton annuler
		drawable.drawImage(boutonAnnuler, largeur/4, hauteur-(int)margeHaut/2, largeurAnnuler, hauteurAnnuler, null);
		//bouton refaire
		drawable.drawImage(boutonRefaire, 3*largeur/4-largeurAnnuler, hauteur-(int)margeHaut/2, largeurAnnuler, hauteurAnnuler, null);


		
		Joueur joueur;
		//Tracage des lignes de 7 pavés
		for(int i=0;i<7;i++)
		{
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
					
					
					//dessin du joueur
					joueur = c[2*j][i].getJoueurSurCase();
					
					if(joueur != null){
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

		//tracage des lignes de 8
		for(int i=0;i<8;i++)
		{
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
				
				
					//dessin du joueur
					joueur = c[2*j+1][i].getJoueurSurCase();
				
					if(joueur != null){
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
    	} //fin boucle for
      
      
         if(aide)
         {
		     if(clicPrec.x != -1 && clicPrec.y != -1)
		     {
				Coup [] coup =  ArbitreManager.instance.getConfiguration().coupsPossiblesCase(clicPrec.y, clicPrec.x);
			    int i,j;
		
				for(int k = 0; k < coup.length;k++)
				{
				   i = coup[k].getXArrivee();
				   j = coup[k].getYArrivee();
				   if(c[j][i].getEtat() == Etat.DEUX_POISSONS)
				   {
					   drawable.drawImage(deux_poissons_aide,tabCase.sommetG_x(i,j),tabCase.sommetG_y(i,j),tabCase.largeur(),tabCase.hauteur(),null);
					}
					else if(c[j][i].getEtat() == Etat.UN_POISSON)
					{
					   drawable.drawImage(un_poisson_aide,tabCase.sommetG_x(i,j),tabCase.sommetG_y(i,j),tabCase.largeur(),tabCase.hauteur(),null);
					}
					else if(c[j][i].getEtat() == Etat.TROIS_POISSONS)
					{
					   drawable.drawImage(trois_poissons_aide,tabCase.sommetG_x(i,j),tabCase.sommetG_y(i,j),tabCase.largeur(),tabCase.hauteur(),null);
					} 
			  }  
	   		}
	   	}
       
        //affichage du joueur selectionné
        if(clicPrec.x != -1 && clicPrec.y != -1)
		{ 
			drawable.drawImage(entoure, tabCase.sommetG_x(clicPrec.y, clicPrec.x), tabCase.sommetG_y(clicPrec.y, clicPrec.x), tabCase.largeur(), tabCase.hauteur(), null);
		}
       
       //tracage du coup précédent
		coupPrec = ArbitreManager.instance.getConfiguration().getCoupEffectue();
		if(coupPrec != null)
		{
			if(coupPrec.getYDepart() != -1 && coupPrec.getXDepart() != -1)
				drawable.drawImage(entoure, tabCase.sommetG_x(coupPrec.getXDepart(), coupPrec.getYDepart()), tabCase.sommetG_y(coupPrec.getXDepart(), coupPrec.getYDepart()), tabCase.largeur(), tabCase.hauteur(), null);
			if(coupPrec.getYArrivee() != -1 && coupPrec.getXArrivee() != -1)
				drawable.drawImage(entoure, tabCase.sommetG_x(coupPrec.getXArrivee(), coupPrec.getYArrivee()), tabCase.sommetG_y(coupPrec.getXArrivee(), coupPrec.getYArrivee()), tabCase.largeur(), tabCase.hauteur(), null);
		}	
       
       
    }//fin methode paint


	//
	//Gestion du clic dans la fenetre
	//
    public void click(int x, int y)
    { 
    	Point p = tabCase.estDansHexagone(x,y);
    	
		if(p.x != -1 && p.y != -1)
		{ //On est sur une case du jeu
			
			if(ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN)
			{ //mode pose pingouin 
				if(ArbitreManager.instance.getConfiguration().estCoupPossible(new Coup(p.y, p.x, -1, -1)))
				{
					ArbitreManager.instance.getJoueurCourant().getSignalCoup().envoyerSignal(new Coup(p.y, p.x, -1, -1));
	        	}
				//else
					//System.out.println("Coup illegal, pas de positionnement ici !");
			}//fin mode pose pingouin
			
			else
			{ //mode jeu
				if(clicPrec.x == -1 || clicPrec.y == -1)
				{ //le coup precedent est nul
					Case [][] t = ArbitreManager.instance.getConfiguration().getTerrain();
					if(t[p.x][p.y] != null){
						if(t[p.x][p.y].getJoueurSurCase() == ArbitreManager.instance.getJoueurCourant()) {
						   clicPrec.x = p.x;
							clicPrec.y = p.y;
						}
					}
				} //fin coup precedent nul
				else 
				{ //le coup precedent est sur un pingouin du joueur
				
					if(clicPrec.x == p.x && clicPrec.y == p.y)
					{
						clicPrec.x = -1;
						clicPrec.y = -1;
					}
					else
					{
						Coup c = new Coup(clicPrec.y, clicPrec.x, p.y, p.x);
						if(ArbitreManager.instance.getConfiguration().estCoupPossible(c))
						{ //le coup demandé est possible
							ArbitreManager.instance.getJoueurCourant().getSignalCoup().envoyerSignal(c);
							clicPrec.x = p.x;
							clicPrec.y = p.y;
						}
						else
						{//coup non dispo
							System.out.println("Le coup " + c + " n'est pas pas autorisé"); 
							Case [][] t = ArbitreManager.instance.getConfiguration().getTerrain();
							if(t[p.x][p.y].getJoueurSurCase() == ArbitreManager.instance.getJoueurCourant()) 
							{//clic sur un pingouin du joueur
								clicPrec.x = p.x;
								clicPrec.y = p.y;
							}//fin si
							else
							{//on annule la selecion du pingouin
								clicPrec = new Point(-1, -1);
							}//fin else
						}//fin coup non dispo
					}
				}//fin else
			} //fin mode jeu
		}//fin cadre de jeu
    	else
    	{ //on regarde si on a cliquer sur un bouton	
			if(x > (largeur/2 - largeurMenu/2) && x < (largeur/2 + largeurMenu/2) && y < hauteurMenu)
			{//clic sur menu
				//Clic sur le bouton menu;
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
					recommencer.addActionListener(new EcouteurDeBoutonMenu("Recommencer", inter, popup));
					bQuitter.addActionListener(new EcouteurDeBoutonMenu("Quitter", inter, popup));
					menuP.addActionListener(new EcouteurDeBoutonMenu("Retour Menu Principal", inter, popup));
					save.addActionListener(new EcouteurDeBoutonMenu("Sauvegarder", inter, popup));
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
			}//fin clic sur menu
			
			else if(y > (hauteur-margeHaut/2) && y < hauteur-margeHaut/2+hauteurAnnuler)
			{//clic dans la bande en bas
				if(x > largeur/4 && x < (largeur/4 + largeurAnnuler)) 
				{
					//Clic sur Retour
					clicPrec.x = -1;
					clicPrec.y = -1;
					ArbitreManager.instance.reculerHistorique();
				}
				else if(x > (3*largeur/4-largeurAnnuler) && x < (3*largeur/4))
				{   
					//clic sur refaire
					clicPrec.x = -1;
					clicPrec.y = -1;
					ArbitreManager.instance.avancerHistorique();
				}
			}//fin clic sur la bande du bas
			
			if(x > largeur - margeGauche)
			{
				if (y > hauteur/2 && y <(int)tabCase.hauteur+hauteur/2) 
				{
				//clic sur le bouton suggestion
					ArbitreManager.instance.getJoueurCourant().getSignalCoup().envoyerSignal(inter.joueurs[0].getCoup());
					clicPrec.x = p.x;
					clicPrec.y = p.y;
				}
			}
			if(y < hauteurAide)
			{
				if(x > (largeur/2-largeurMenu/2-largeurAide) && x < largeur/2-largeurMenu/2)
				{ //clic sur aide
					aide = !aide;
				}
				if(x > (largeur/2+largeurMenu/2) && x < largeur/2+largeurMenu/2+largeurAide)
				{ //clic sur infobulle
					aide = !aide;
				}
			}
    	}

    	this.repaint();
    }
   
}
