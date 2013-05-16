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
		largeur = 5;
		hauteur = 5;
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
        return cl.getResource("Interface/Img/" + nom);
    }

	public void paintComponent(Graphics g){
		Graphics2D drawable = (Graphics2D) g;
        BufferedImage gaufreSaine, gaufrePoison, gaufreMange;
		gaufreSaine = null;
		gaufrePoison = null;
		gaufreMange = null;

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
                
                if(joueur == arbitre.getJoueur1())
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
                
                
                try 
                {
                    gaufreSaine = ImageIO.read(getImage("gaufre_saine.png"));
                    gaufrePoison = ImageIO.read(getImage("gaufre_poison.png"));
                    gaufreMange = ImageIO.read(getImage("gaufre_mange.png"));
                } 
                catch (IOException e) 
                {
                    System.err.println(e);
                    System.exit(1);
                }


                for(int i = 0; i < largeur; i++){
                    for(int j = 0; j < hauteur; j++){
                        if(c[j][i].estPleine()){
                            drawable.drawImage(gaufreSaine,largeurCase*i,hauteurCase*j, largeurCase, hauteurCase,null);
                        }
                        else if(c[j][i].estLibre()){
                            drawable.drawImage(gaufreMange,largeurCase*i,hauteurCase*j, largeurCase, hauteurCase,null);
                        }
                        else{
                            drawable.drawImage(gaufrePoison,largeurCase*i,hauteurCase*j, largeurCase, hauteurCase,null);
                        }
                    }
                }
            }

            //Affichage de la grille
            drawable.setPaint(Color.black);

							
            for(int i = 1; i < hauteur; i++){
                drawable.drawLine(0,hauteurCase*i,getSize().width, hauteurCase*i);
            }
            for(int i = 1; i < largeur; i++){
                drawable.drawLine(largeurCase*i, 0, largeurCase*i, getSize().height);
            }

        }
    }

    public void click(int x, int y)
    {

        if (ArbitreManager.instance != null)
            ArbitreManager.instance.getJoueurCourant().getSignalCoup().envoyerSignal(new Coup(x, y));
    }
	
    public void enleverBoutons()
    {
        //pan.setVisible(false);
        frame.remove(pan);
        this.setSize(300,300);
        this.repaint();
        this.setVisible(true);
				
        frame.add(this);
        frame.setVisible(true);
        frame.pack();
        frame.repaint();
    }

}
