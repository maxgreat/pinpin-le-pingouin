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
	

	public AireDeJeu(JFrame f, JPanel p){
		frame = f;
		pan = p;
		largeur = 8;
		hauteur = 8;
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
					double l,h;
					double rayonH;
					double rayonL;
					
					rayonH = (3.0*(double)hauteur)/44.0;
					rayonL = (3.0*(double)largeur)/63.0;
					
					double margeHaut = (double)hauteur/8.0;
					double margeGauche = (double)largeur/8.0;
					
					//Tracage des lignes de 7 pavés
					for(int i=0;i<7;i++){
						for(int j=0;j<4;j++){
							l= margeGauche + rayonL + 2.0*rayonL*(double)i;
							h= margeHaut + (double)j*3.0*rayonH;
							if(c[j][i] == null){}                  
							else if(c[j][i].getEtat() == Etat.DEUX_POISSONS){
								drawable.drawImage(deux_poissons,(int)l,(int)h,(int)(2.0*rayonL),(int)(2.0*rayonH),null);
							}
							else if(c[j][i].getEtat() == Etat.UN_POISSON){
								drawable.drawImage(un_poisson,(int)l,(int)h,(int)(2.0*rayonL),(int)(2.0*rayonH),null);
							}
							else if(c[j][i].getEtat() == Etat.TROIS_POISSONS){
								drawable.drawImage(trois_poissons,(int)l,(int)h,(int)(2.0*rayonL),(int)(2.0*rayonH),null);
							}
						}
					}
					
					System.out.println("Tracage des lignes de 8, largeur =" + largeur + " hauteur = " + hauteur);
					for(int i=0;i<8;i++){
						for(int j=0;j<4;j++){
							l= margeGauche + (double)i*2.0*rayonL;
							h= margeHaut + (3.0*rayonH)/2.0 +(double)j*3.0*rayonH;
							
							if(c[j][i] == null){}                  
							else if(c[j][i].getEtat() == Etat.DEUX_POISSONS){
								drawable.drawImage(deux_poissons,(int)l,(int)h,(int)(2.0*rayonL),(int)(2.0*rayonH),null);
							}
							else if(c[j][i].getEtat() == Etat.UN_POISSON){
								drawable.drawImage(un_poisson,(int)l,(int)h,(int)(2.0*rayonL),(int)(2.0*rayonH),null);
							}
							else if(c[j][i].getEtat() == Etat.TROIS_POISSONS){
								drawable.drawImage(trois_poissons,(int)l,(int)h,(int)(2.0*rayonL),(int)(2.0*rayonH),null);
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
    }
	
    public void enleverBoutons()
    {
        //pan.setVisible(false);
        frame.remove(pan);
        this.setSize(600,600);
        this.repaint();
        this.setVisible(true);
	frame.setResizable(true);		
        frame.add(this);
        frame.setVisible(true);
        frame.pack();
        frame.repaint();
    }

}
