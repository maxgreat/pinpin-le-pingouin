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
   }
      
}
