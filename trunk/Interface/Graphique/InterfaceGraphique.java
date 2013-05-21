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
		JFrame frame = new JFrame("Gaufre empoisonnée");
		
		//Aire de dessin
		//Boutons
		pan = new JPanel();
		aire = new AireDeJeu(frame, pan);
		aire.setPreferredSize(new Dimension(300,300));
		//Ecouteurs
		aire.addMouseListener(new EcouteurDeSouris(aire));
		
		//Bouton nouvelle partie human vs humain
		JButton b1 = new JButton("H vs H");
		b1.addActionListener(new EcouteurDeBouton("HvH", this));
		pan.add(b1);
		//Bouton nouvelle partie human vs humain
		b1 = new JButton("H vs CPU");
		b1.addActionListener(new EcouteurDeBouton("HvCPU", this));
		pan.add(b1);
		
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




