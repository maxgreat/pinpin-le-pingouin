package Interface;
import javax.swing.*;
import Arbitre.*;
import java.awt.*;
import java.awt.event.*;

public class Interface
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
		
		//Barre de menu
		//menu principal
		JMenu menu = new JMenu("Principal");
		JMenuItem item2 = new JMenuItem("Sauvegarder");
		item2.addActionListener(new Sauvegarder());
		JMenuItem item3 = new JMenuItem("Charger");
		item3.addActionListener(new Charger());
		JMenuItem item = new JMenuItem("Quitter");
		item.addActionListener(new QuitterApplication());
		
		
		JMenuBar barre = new JMenuBar();
		menu.add(item2);
		menu.add(item3);
		menu.add(item);
		barre.add(menu);
		
		//menu edition
		JMenu menuEd = new JMenu("Edition");
		JMenuItem itemEd = new JMenuItem("Annuler");
		itemEd.addActionListener(new MenuEdition("annuler"));
		JMenuItem itemEd2 = new JMenuItem("Refaire");
		itemEd2.addActionListener(new MenuEdition("refaire"));

		menuEd.add(itemEd);
		menuEd.add(itemEd2);
		barre.add(menuEd);
		frame.setJMenuBar(barre);
		
		
		//Bouton nouvelle partie human vs humain
		JButton b1 = new JButton("H vs H");
		b1.addActionListener(new EcouteurDeBouton("HvH", this));
		pan.add(b1);
		//Bouton nouvelle partie human vs humain
		b1 = new JButton("H vs CPU");
		b1.addActionListener(new EcouteurDeBouton("HvCPU", this));
		pan.add(b1);
		//barre de choix de la largeur
		JSlider slide = new JSlider();
		slide.setMaximum(10);
    	slide.setMinimum(2);
    	slide.setValue(5);
    	slide.setPaintTicks(true);
    	slide.setPaintLabels(true);
    	slide.setMinorTickSpacing(1);
    	slide.setMajorTickSpacing(1);
    	slide.addChangeListener(new EcouteurDeBarre(aire, "largeur"));
		pan.add(slide, BorderLayout.SOUTH);

		
		//barre de choix de la hauteur
		JSlider slide2 = new JSlider();
		slide2.setMaximum(10);
    	slide2.setMinimum(2);
    	slide2.setValue(5);
    	slide2.setPaintTicks(true);
    	slide2.setPaintLabels(true);
    	slide2.setMinorTickSpacing(1);
    	slide2.setMajorTickSpacing(1);
    	slide2.addChangeListener(new EcouteurDeBarre(aire, "hauteur"));
		pan.add(slide2, BorderLayout.SOUTH);

		
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




