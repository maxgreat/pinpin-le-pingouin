package Interface.Graphique;
import javax.swing.*;
import Arbitre.*;
import java.awt.*;
import java.awt.event.*;
import Interface.*;
import Joueurs.*;


public class InterfaceGraphique extends Interface
{
    public JPanel pan , panfinal;
    protected AireDeJeu aire;
    protected JFrame frame;
    public void run(String [] arguments)
    {		
		//Fenetre principale
		frame = new JFrame("pinpin le pingouin");

		//Aire de dessin
		//Boutons
		panfinal = new JPanel();
		addBouton(panfinal,"Partie Rapide");
		addBouton(panfinal,"Partie Personalisé");
		addBouton(panfinal,"Partie en Réseau");		
		addBouton(panfinal,"Options");
		addBouton(panfinal,"Classements");
		addBouton(panfinal,"Quitter");
		panfinal.setLayout(new BoxLayout(panfinal, BoxLayout.PAGE_AXIS));
		frame.add(panfinal);
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

		frame.setSize(300,300);
		frame.setVisible(true);
    }

    /**
     * Repaint l'interface (ici l'AireDeJeu)
     **/
    public void repaint()
    {
	if(aire != null)
		{        
		aire.repaint();
    		}
    }	

    public AireDeJeu getAire()
    {
        return aire;
    }
    public void addBouton(JPanel panfinal, String S)
    {
	JPanel pan  = new JPanel();
	pan.setLayout(new BoxLayout(pan, BoxLayout.LINE_AXIS));
	JButton b1 = new JButton(S);
	b1.addActionListener(new EcouteurDeBouton(S, this));
	pan.add(b1);
	panfinal.add(pan);
     }
    public void afficherPanel(String S)
	{
		if (S.compareTo("Partie Rapide") == 0 )
		{
			frame.remove(panfinal);
			//definition des joueurs
			Joueur [] joueurs = new Joueur[2];
			joueurs[0] = new JoueurHumain();
			joueurs[1] = new JoueurHumain();
			//lancement de la partie
			ArbitreManager.initialiserPartie(joueurs ,ArbitreManager.LARGEUR_GRILLE , ArbitreManager.HAUTEUR_GRILLE, this); 
			aire = new AireDeJeu(frame);
			aire.setPreferredSize(new Dimension(500,500));
			aire.addMouseListener(new EcouteurDeSouris(aire));
			frame.add(aire);
			aire.repaint();
			aire.setVisible(true);
			frame.repaint();
			frame.pack();		
		}
		
	}

}




