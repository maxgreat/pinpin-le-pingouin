package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;

public class EcouteurDeBox implements ActionListener
	{
		// on a beson de selectionjoueur pour recuperer le booleen ordinateur et modifié les noms
		SelectionJoueurs S;
		boolean name;
		public EcouteurDeBox(SelectionJoueurs S ,boolean b){
		this.S = S;
		name = b;		
		}
	public void actionPerformed(ActionEvent e)
	{
	JComboBox cb = (JComboBox)e.getSource();
	if(name)
		{
		S.nomJoueur = (String)cb.getSelectedItem();
		if(S.nomJoueur.compareTo("Ordinateur")==0)
			{
			S.Ordinateur = true;
			S.selectionDifficulte();
			S.mp.refresh();
			}
		S.joueursPris.add(S.nomJoueur);
		S.joueursPris.remove(S.nomJoueurOld);
		S.nomJoueurOld = S.nomJoueur;
		}
	else
		{
		S.difficultésJoueur = (String)cb.getSelectedItem();
		}
	}

}
