package Interface.Graphique;
import javax.swing.*;
import Arbitre.*;
import java.awt.*;
import java.awt.event.*;
import Interface.*;

public class InterfaceGraphique extends Interface
{
    public JPanel pan;
    protected AireDeJeu aire;

    public void run(String [] arguments)
    {		
		//Fenetre principale
		JFrame frame = new JFrame("pinpin le pingouin");
		
		//Aire de dessin
		//Boutons
		pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
		aire = new AireDeJeu(frame, pan);
		aire.setPreferredSize(new Dimension(300,300));
		//Ecouteurs
		aire.addMouseListener(new EcouteurDeSouris(aire));
		
		//Bouton nouvelle partie human vs humain
		JButton b1 = new JButton("H vs H");
		b1.addActionListener(new EcouteurDeBouton("HvH", this));
		pan.add(b1,BorderLayout.CENTER);
		//Bouton nouvelle partie human vs humain
		b1 = new JButton("H vs CPU");
		b1.addActionListener(new EcouteurDeBouton("HvCPU", this));
		pan.add(b1,BorderLayout.CENTER);
		//le premier bouton normal (partie rapide)
		JButton b2  = new JButton("Partie Rapide");
		b2.addActionListener(new EcouteurDeBouton("Partie Rapide", this));
		pan.add(b2,BorderLayout.CENTER);
		// le deuxieme bouton partie personnalisé
		JButton b3 = new JButton("Partie Personalisé");
		b3.addActionListener(new EcouteurDeBouton("Partie Personalisé", this));
		pan.add(b3,BorderLayout.CENTER);
		// le troisieme bouton partie reseaux
		JButton b4 = new JButton("Partie reseaux");
		b4.addActionListener(new EcouteurDeBouton("Partie reseaux", this));
		pan.add(b4,BorderLayout.CENTER);
		// le troisieme bouton partie Options
		JButton b5 = new JButton("Options");
		b5.addActionListener(new EcouteurDeBouton("Options", this));
		pan.add(b5,BorderLayout.CENTER);
		// le Quatrieme bouton partie classement
		JButton b6 = new JButton("Classement");
		b6.addActionListener(new EcouteurDeBouton("Classement", this));
		pan.add(b6,BorderLayout.CENTER);
		//ajout du panel à la fenetre
		frame.add(pan);

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
        aire.repaint();
    }

    public AireDeJeu getAire()
    {
        return aire;
    }
            

}




