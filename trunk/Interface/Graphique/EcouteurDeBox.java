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
	// on a beson de selectionjoueur pour recuperer le booleen ordinateur et modifié les noms
	SelectionJoueurs S;
	boolean name;
	InterfaceGraphique inter;
	int i;
	public EcouteurDeBox(SelectionJoueurs S ,boolean b,InterfaceGraphique inter,int i)
	{
		this.inter = inter;
		this.S = S;
		name = b;
		this.i = i;

	}
	public void actionPerformed(ActionEvent e)
	{
		JComboBox cb = (JComboBox)e.getSource();
		String test = (String)cb.getSelectedItem();
		if(i == 1)
			{	
			if(test.compareTo("Ordinateur") != 0)
				{
				inter.joueurs[0] = new JoueurHumain();
				inter.joueurs[0].setNom(test);
				}
			}
		if(i == 2)
			{	
			if(test.compareTo("Ordinateur")!= 0)
				{
				inter.joueurs[1] = new JoueurHumain();
				inter.joueurs[1].setNom(test);
				}
			}
		if(i == 3)
			{	
		
			}
		if(i == 4)
			{	
		
			}
			
		if(name)
		{
			S.nomJoueur = (String)cb.getSelectedItem();
			if(S.nomJoueur.compareTo("Ordinateur")==0)
			{
				S.Ordinateur = true;
				S.selectionDifficulte();
				S.mp.refresh();
			}
		if(S.nomJoueur.compareTo("Ordinateur")!=0)
			{
				S.Ordinateur = true;
				S.diff.setVisible(false);
			}
			S.joueursPris.add(S.nomJoueur);
			S.joueursPris.remove(S.nomJoueurOld);
			S.nomJoueurOld = S.nomJoueur;
		}
	else
	

		{
			S.difficultésJoueur = (String)cb.getSelectedItem();
			if(i == 1)
				{
				if(test.compareTo("Facile") == 0)
					{
					inter.joueurs[0] = new JoueurCPUUniversel(3);
					inter.joueurs[0].setNom("Ordinateur");
					}
				if(test.compareTo("Intermediaire") == 0)
					{
					inter.joueurs[0] = new JoueurCPUUniversel(2);
					inter.joueurs[0].setNom("Ordinateur");
					}
				if(test.compareTo("Difficile") == 0)
					inter.joueurs[0] = new JoueurCPUUniversel(1);
					inter.joueurs[0].setNom("Ordinateur");
				}
			if(i == 2)
				if(test.compareTo("Facile") == 0)
					{
					inter.joueurs[1] = new JoueurCPUUniversel(3);
					inter.joueurs[1].setNom("Ordinateur");
					}
				if(test.compareTo("Intermediaire") == 0)
					{
					inter.joueurs[1] = new JoueurCPUUniversel(2);
					inter.joueurs[1].setNom("Ordinateur");
					}
				if(test.compareTo("Difficile") == 0)
					{
					inter.joueurs[1] = new JoueurCPUUniversel(1);
					inter.joueurs[1].setNom("Ordinateur");
					}
			if(i == 3){}
				
			if(i == 4){}
			
			
			
		}
	}

}
