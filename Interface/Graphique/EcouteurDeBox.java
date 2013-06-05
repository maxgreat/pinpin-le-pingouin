package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;
import Joueurs.*;
public class EcouteurDeBox implements ActionListener
{
	// on a besoin de selectionjoueur pour recuperer le booleen ordinateur et modifier les noms
	SelectionJoueurs S;
	InterfaceGraphique inter;
	int i;
    SelectionJoueur type;

	public EcouteurDeBox(SelectionJoueurs S , SelectionJoueur type, InterfaceGraphique inter,int i)
	{
		this.inter = inter;
		this.S = S;
		this.i = i;
        this.type = type;

	}
	public void actionPerformed(ActionEvent e)
	{
		JComboBox cb = (JComboBox)e.getSource();
		String test = (String)cb.getSelectedItem();	

        if (type.estJoueur())
		{        
			S.Sjoueurs[i-1] = new JoueurHumain();
			S.Sjoueurs[i-1].setNom((String)cb.getSelectedItem());
        }
        else if (type.estOrdinateur())
        {
            S.difficultesJoueur = (String)cb.getSelectedItem();
            if(test.compareTo("Facile") == 0)
            {
                S.Sjoueurs[i-1] = new JoueurCPUUniversel(3);
                S.Sjoueurs[i-1].setNom("Ordinateur (facile)");
            }
            if(test.compareTo("Intermediaire") == 0)
            {
                S.Sjoueurs[i-1] = new JoueurCPUUniversel(2);
                S.Sjoueurs[i-1].setNom("Ordinateur (intermédiaire)");
            }
            if(test.compareTo("Difficile") == 0)
            {		
                S.Sjoueurs[i-1] = new JoueurCPUUniversel(1);
                S.Sjoueurs[i-1].setNom("Ordinateur (difficile)");
            }
        }	
        else
        {
            S.Sjoueurs[i-1] = null;
        }
        
        inter.setTab(S.Sjoueurs);
	}

}
