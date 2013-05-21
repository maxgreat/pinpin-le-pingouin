package Interface.Graphique;
import Arbitre.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import Joueurs.*;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;

public class AireDeJeu extends JComponent{
	Hexagone tabCase;
	JFrame frame;
	JPanel pan;

    // Nombre de cases sur la largeur	
	public int largeur;
	// Nombre de cases sur la longueur
	public int hauteur;
	
	// Largeur d'une case
	protected int largeurCase;
	// Hauteur d'une case
	protected int hauteurCase;
	

	public AireDeJeu(JFrame f ){
		frame = f;
	
		largeur = 8;
		hauteur = 8;
		
		//création du tableau de case
        tabCase = new Hexagone();
        tabCase.initHexagone();
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
        BufferedImage un_poisson, deux_poissons, trois_poissons;
		un_poisson = null;
		deux_poissons = null;
		trois_poissons = null;

        Arbitre arbitre = ArbitreManager.instance;
	
        if (arbitre == null)
        { //menu demarrer*/
            drawable.setPaint(Color.WHITE);
            drawable.fillRect(0,0,300,300);
            drawable.setPaint(Color.black);
            drawable.drawRect(20,20,50,50);
        }
        else
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
            else
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
                
                try 
                {
						un_poisson = ImageIO.read(getImage("un_poisson.png"));
					 } 
                catch (IOException e) 
                {
                    System.err.println("erreur lecture images 1" +e);
                    System.exit(1);
                }
					 try{	
						deux_poissons = ImageIO.read(getImage("deux_poissons.png"));
					 }
					 catch (IOException e) 
                {
                    System.err.println("erreur lecture images 2" +e);
                    System.exit(1);
                }
					 try{        
						trois_poissons = ImageIO.read(getImage("trois_poissons.png"));
					 }
					 catch (IOException e) 
                {
                    System.err.println("erreur lecture images 3" +e);
                    System.exit(1);
                }


               /* for(int i = 0; i < largeur; i++){
                    for(int j = 0; j < hauteur; j++){
                        if(c[j][i] == null){}                  
                        else if(c[j][i].getEtat() == Etat.DEUX_POISSONS){
                            drawable.drawImage(deux_poissons,largeurCase*i,hauteurCase*j, largeurCase,hauteurCase,null);
                        }
								else if(c[j][i].getEtat() == Etat.UN_POISSON){
                            drawable.drawImage(un_poisson,largeurCase*i,hauteurCase*j, largeurCase, hauteurCase,null);
								}
								else if(c[j][i].getEtat() == Etat.TROIS_POISSONS){
                            drawable.drawImage(trois_poissons,largeurCase*i,hauteurCase*j, largeurCase, hauteurCase,null);
                        }
			 
                    }
                }*/
					double rayonH;
					double rayonL;
					
					rayonH = (3.0*(double)hauteur)/44.0;
					rayonL = (3.0*(double)largeur)/63.0;
					
					double margeHaut = (double)hauteur/8.0;
					double margeGauche = (double)largeur/8.0;
					
					//maj du tableau case
					tabCase.setTab(rayonH, rayonL, margeHaut, margeGauche);
					
					
					//Tracage des lignes de 7 pavés
					for(int i=0;i<7;i++){
						for(int j=0;j<4;j++){
							if(c[j][i] == null){}                  
							else if(c[j][i].getEtat() == Etat.DEUX_POISSONS){
								drawable.drawImage(deux_poissons,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);
							}
							else if(c[j][i].getEtat() == Etat.UN_POISSON){
								drawable.drawImage(un_poisson,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);
							}
							else if(c[j][i].getEtat() == Etat.TROIS_POISSONS){
								drawable.drawImage(trois_poissons,tabCase.sommetG_x(i,2*j),tabCase.sommetG_y(i,2*j),tabCase.largeur(),tabCase.hauteur(),null);
							}
						}
					}
					
					System.out.println("Tracage des lignes de 8, largeur =" + largeur + " hauteur = " + hauteur);
					for(int i=0;i<8;i++){
						for(int j=0;j<4;j++){
							if(c[j][i] == null){}                  
							else if(c[j][i].getEtat() == Etat.DEUX_POISSONS){
								drawable.drawImage(deux_poissons,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur(),null);
							}
							else if(c[j][i].getEtat() == Etat.UN_POISSON){
								drawable.drawImage(un_poisson,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur(),null);
							}
							else if(c[j][i].getEtat() == Etat.TROIS_POISSONS){
								drawable.drawImage(trois_poissons,tabCase.sommetG_x(i,2*j+1),tabCase.sommetG_y(i,2*j+1),tabCase.largeur(),tabCase.hauteur(),null);
							}
						}
					}

				}


  /* 
            //Affichage de la grille
            drawable.setPaint(Color.black);

							
            for(int i = 1; i < hauteur; i++){
                drawable.drawLine(0,hauteurCase*i,getSize().width, hauteurCase*i);
            }
            for(int i = 1; i < largeur; i++){
                drawable.drawLine(largeurCase*i, 0, largeurCase*i, getSize().height);
            }*/

        }
    }

    public void click(int x, int y)
    { 
    	Point p = tabCase.estDansHexagone(x,y);
    	System.out.println("Point p = " + p);
    }
	
    public void enleverBoutons()
    {
        frame.repaint();
    }

}
