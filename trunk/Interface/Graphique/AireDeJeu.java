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
	
	
	//images des poissons
	private BufferedImage un_poisson, deux_poissons, trois_poissons;
	

    // Nombre de cases sur la largeur	
	public int largeur;
	// Nombre de cases sur la longueur
	public int hauteur;
	
	// Largeur d'une case
	protected int largeurCase;
	// Hauteur d'une case
	protected int hauteurCase;
	

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
				un_poisson = ImageIO.read(getImage("un_poisson.png"));
				deux_poissons = ImageIO.read(getImage("deux_poissons.png"));
				trois_poissons = ImageIO.read(getImage("trois_poissons.png"));
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
        
	}

    public int getHauteur()
    {
        return hauteur;
    }

    public int getLargeur()
    {
        return largeur;
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
            drawable.setPaint(Color.WHITE);
            drawable.fillRect(0,0,300,300);
            drawable.setPaint(Color.black);
            drawable.drawRect(20,20,50,50);
        }
        else	//l'arbitre n'est pas nul
        {
            if (arbitre.partieFinie())
            {
                Joueur joueur = arbitre.getJoueurCourant();
                
                if(joueur == arbitre.getJoueurCourant())
                    System.out.print("Victoire du joueur 1");
                else
                    System.out.print("Victoire du joueur 2");

                System.out.println(" - "+joueur.getNom());
                System.exit(0);
            }
            else  //partie en cours
            {
                //Recuperation de la configuration
                Configuration config = arbitre.getConfiguration();
                //Recuperation du Terrain
                Case [][] c = config.getTerrain();
                
                //Recuperation de la hauteur et de la largeur
                hauteur = config.getHauteur();
                largeur = config.getLargeur();
                hauteurCase = getSize().height;
                hauteurCase = hauteurCase/hauteur;
                largeurCase = getSize().width;	
                largeurCase = largeurCase/largeur;		
				hauteur = this.getHeight();
				largeur = this.getWidth();
                
                
			
				double rayonH = (3.0*(double)hauteur)/44.0;
				double rayonL = (3.0*(double)largeur)/63.0;
				double margeHaut = (double)hauteur/8.0;
				double margeGauche = (double)largeur/8.0;
				
				//maj du tableau case
				tabCase.setTab(rayonH, rayonL, margeHaut, margeGauche);
				Joueur joueur;
				
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
    	
    	if(ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN){
    		System.out.println("Envoi du coup" + p);
    		if(ArbitreManager.instance.getConfiguration().estCoupPossible(new Coup(p.y, p.x, -1, -1)))
    			ArbitreManager.instance.getJoueurCourant().getSignalCoup().envoyerSignal(new Coup(p.y, p.x, -1, -1));
    		else
    			System.out.println("Coup illegal");
    		
    	}
    	else{ //mode jeu
    		System.out.println("Coup de jeu");
    		if(coupPrec.x == -1 || coupPrec.y == -1){
    			Case [][] t = ArbitreManager.instance.getConfiguration().getTerrain();
    			if(t[p.x][p.y] != null){
    				if(t[p.x][p.y].getJoueurSurCase() == ArbitreManager.instance.getJoueurCourant()) {
    					coupPrec.x = p.x;
    					coupPrec.y = p.y;
    					System.out.println("Case préc = " + coupPrec);
    				}
    			}
    		}
    		else { //le coup precedent est sur un pingouin du joueur
    			System.out.println("Coup pour deplacer");
    			Coup c = new Coup(coupPrec.y, coupPrec.x, p.y, p.x);
    			if(ArbitreManager.instance.getConfiguration().estCoupPossible(c)){
    				System.out.println("Coup lancé");
					ArbitreManager.instance.getJoueurCourant().getSignalCoup().envoyerSignal(c);
				}
				coupPrec = new Point(-1,-1);
    		}
    	}
    	
    	this.repaint();
    }

}
