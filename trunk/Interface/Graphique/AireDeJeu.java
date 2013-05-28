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

public class AireDeJeu extends JComponent{
	Hexagone tabCase;
	JFrame frame;
	JPanel pan;
	InterfaceGraphique inter;
	Point coupPrec;
	
	//images des joueurs
	BufferedImage imageJoueur1 = null;
	BufferedImage imageJoueur2 = null;
	BufferedImage imageJoueur3 = null;
	BufferedImage imageJoueur4 = null;
	BufferedImage boutonMenu = null;
	BufferedImage boutonAnnuler = null;
	BufferedImage boutonRefaire = null;
	BufferedImage fondEau = null;
	
	//images des poissons
	private BufferedImage un_poisson, deux_poissons, trois_poissons;
	

    // Nombre de cases sur la largeur	
	public int largeur;
	// Nombre de cases sur la longueur
	public int hauteur;
	
	
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
        
        try 
        {
				un_poisson = ImageIO.read(getImage("caseGlace1.png"));
				deux_poissons = ImageIO.read(getImage("caseGlace2.png"));
				trois_poissons = ImageIO.read(getImage("caseGlace3.png"));
		} catch (IOException e) {
            System.err.println("erreur lecture images : " +e);
            System.exit(1);
        }
        
        try{
	   	    imageJoueur1 = ImageIO.read(getImage("pingNoir.png"));
        }catch(Exception e){
        	System.out.println("Erreur lecture image" + e);
        }
        try{
	   	    imageJoueur2 = ImageIO.read(getImage("pingRouge.png"));
        }catch(Exception e){
        	System.out.println("Erreur lecture image" + e);
        }
        try{
	   	    boutonMenu = ImageIO.read(getImage("boutonMenu.jpg"));
	   	    boutonAnnuler = ImageIO.read(getImage("boutonAnnuler.jpg"));
	   	    boutonRefaire = ImageIO.read(getImage("boutonRefaire.jpg"));
	   	    fondEau = ImageIO.read(getImage("backgroundWater.jpg"));
        }catch(Exception e){
        	System.out.println("Erreur lecture image" + e);
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
    
    private void afficherCarres(Graphics2D drawable, int margeGauche, int margeHaut){
    	
    	//joueur1
    	drawable.setPaint(Color.black);
        drawable.fillRect(0,0,margeGauche,margeHaut);
		if(ArbitreManager.instance.getJoueurCourant() == inter.joueurs[0])
			drawable.setPaint(Color.yellow);
		else
			drawable.setPaint(Color.white);
    	drawable.fillRect(5,5,margeGauche-10,margeHaut-10);
		
		drawable.setPaint(Color.black);
		drawable.drawString("Joueur 1", 20,20);	
		drawable.drawString("Score " + inter.joueurs[0].getScore(), 20,40);
		drawable.drawString("Tuiles " + inter.joueurs[0].getNombreTuile(), 20,60);
		
		//joueur 2
		drawable.setPaint(Color.red);
    	drawable.fillRect(largeur-margeGauche,0,margeGauche,margeHaut);
		if(ArbitreManager.instance.getJoueurCourant() == inter.joueurs[1])
			drawable.setPaint(Color.yellow);
		else
			drawable.setPaint(Color.white);
    	drawable.fillRect(largeur-margeGauche+5,5,margeGauche-10,margeHaut-10);
    	
    	drawable.setPaint(Color.red);
		drawable.drawString("Joueur 2",largeur-margeGauche+20,20);	
		drawable.drawString("Score " + inter.joueurs[1].getScore(),largeur-margeGauche+20,40);
		drawable.drawString("Tuiles " + inter.joueurs[1].getNombreTuile(),largeur-margeGauche+20,60);
	}       

    private URL getImage(String nom) {
        ClassLoader cl = getClass().getClassLoader();
        return cl.getResource("Interface/Graphique/Img/" + nom);
    }

    public void paintComponent(Graphics g){
		Graphics2D drawable = (Graphics2D) g;
        

        Arbitre arbitre = ArbitreManager.instance;
	
        if (arbitre == null)
        { //erreur
            System.out.println("Erreur dans le moteur du jeu.");
            System.exit(0);
        }
        else	//l'arbitre n'est pas nul
        {
            if (arbitre.partieFinie())
            {
                if(inter.joueurs[0].getScore() > inter.joueurs[1].getScore())
                    System.out.println("Victoire du joueur 1 - Poissons : " + inter.joueurs[0].getNombreTuile());
                else if(inter.joueurs[0].getScore() < inter.joueurs[1].getScore())
                    System.out.println("Victoire du joueur 2");
                else{ //scores égaux
                	if(inter.joueurs[0].getNombreTuile() > inter.joueurs[1].getNombreTuile())
                    	System.out.println("Victoire du joueur 1 - Tuiles : " + inter.joueurs[0].getNombreTuile());
                	else if(inter.joueurs[0].getScore() < inter.joueurs[1].getScore())
                    	System.out.println("Victoire du joueur 2");
                    else
                    	System.out.println("Egalité");
                }
                
                System.out.println("Joueur1 : " + inter.joueurs[0].getNom() +" : "+inter.joueurs[0].getScore() +" : "+inter.joueurs[0].getNombreTuile());
                System.out.println("Joueur2 : " + inter.joueurs[1].getNom() +" : "+inter.joueurs[1].getScore() +" : "+inter.joueurs[1].getNombreTuile());
                
                System.exit(0);
            }
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
				
				
				//Dessin des boutons
				//bouton menu
				drawable.drawImage(boutonMenu, largeur/2-35, 0, 70,50,null);
				//bouton annuler
				drawable.drawImage(boutonAnnuler, largeur/4, hauteur-(int)rayonH, (int)rayonL*2, (int)rayonH, null);
				//bouton refaire
				drawable.drawImage(boutonRefaire, 3*largeur/4, hauteur-(int)rayonH, (int)rayonL*2, (int)rayonH, null);
				
				
				
				//Tracage des lignes de 7 pavés
				for(int i=0;i<7;i++){
					for(int j=0;j<4;j++){
						if(c[2*j][i] != null){               
							if(c[2*j][i].getEtat() == Etat.DEUX_POISSONS){
								drawable.drawImage(deux_poissons,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);
							}
							else if(c[2*j][i].getEtat() == Etat.UN_POISSON){
								drawable.drawImage(un_poisson,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);
							}
							else if(c[2*j][i].getEtat() == Etat.TROIS_POISSONS){
								drawable.drawImage(trois_poissons,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);
							}
							
							joueur = c[2*j][i].getJoueurSurCase();
							
							if(joueur != null){
								if(coupPrec.x == 2*j && coupPrec.y == i)
									drawable.drawOval(tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur());
							    BufferedImage imageJoueur = null;
							    if(joueur == inter.joueurs[0]){
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
				for(int i=0;i<8;i++){
					for(int j=0;j<4;j++){
						if(c[2*j+1][i] != null){                  
							if(c[2*j+1][i].getEtat() == Etat.DEUX_POISSONS){
								drawable.drawImage(deux_poissons,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur(),null);
							}
							else if(c[2*j+1][i].getEtat() == Etat.UN_POISSON){
								drawable.drawImage(un_poisson,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j
								+1),tabCase.largeur(),tabCase.hauteur(),null);
							}
							else if(c[2*j+1][i].getEtat() == Etat.TROIS_POISSONS){
								drawable.drawImage(trois_poissons,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur(),null);
							}
						
							joueur = c[2*j+1][i].getJoueurSurCase();
						
							if(joueur != null){
								if(coupPrec.x == 2*j+1 && coupPrec.y == i)
									drawable.drawOval(tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur());
							    BufferedImage imageJoueur = null;
							    if(joueur == inter.joueurs[0]){
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

        }//fin gestion erreur arbitre
    }//fin methode paint

    public void click(int x, int y)
    { 
    	Point p = tabCase.estDansHexagone(x,y);
    	
    	if(p.x != -1 && p.y != -1){
			if(ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN){
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
					if(ArbitreManager.instance.getConfiguration().estCoupPossible(c)){
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
			System.out.println("On regarde si on est sur un bouton");
			if(x > (largeur/2 - 35) && x < (largeur/2 + 35) && y < 50){
				System.out.println("Clic sur le bouton menu");
				
				POPMenu popup = new POPMenu();
				popup.setVisible(true);
				JButton bQuitter = new JButton();
				bQuitter.addActionListener(new EcouteurDeBouton("demarrage", inter));
				popup.add(bQuitter);
				this.add(popup);
				popup.toFront();
				try {
        			popup.setSelected(true);
    			} catch (java.beans.PropertyVetoException e) {}
				
				
			}
			else if(y > hauteur - (int)rayonH){
				if(x > largeur/4 && x < (largeur/4 + (int)rayonL*2)){
					System.out.println("Retour");
					ArbitreManager.instance.reculerHistorique();
				}
				else if(x > (3*largeur/4) && x < (3*largeur/4 + (int)rayonL*2)){
					System.out.println("Refaire");
					ArbitreManager.instance.avancerHistorique();
				}
			}	
    	}
    	this.repaint();
    }
}
