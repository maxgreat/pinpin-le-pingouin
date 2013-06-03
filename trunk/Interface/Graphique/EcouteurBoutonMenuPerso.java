package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;
import Joueurs.*;


public class EcouteurBoutonMenuPerso implements ActionListener{
SelectionJoueurs J;
int but;

public EcouteurBoutonMenuPerso(SelectionJoueurs J ,int but)
{
this.J = J;
this.but = but;
}

public void actionPerformed(ActionEvent e)
{
	if(but == 1)
	{
	J.Ordinateur = false;
	J.Humain = true;
	J.Aucun = false;
	J.selectionDifficulte();	
	System.out.println("caca1");
	}
	if(but == 2)
	{
	J.Ordinateur = true;
	J.Humain = false;
	J.Aucun = false;
	J.selectionDifficulte();
	System.out.println("caca2");
	}
	if(but == 3)
	{
	J.Ordinateur = false;
	J.Humain = false;
	J.Aucun = true;
	J.selectionDifficulte();
	}
	
}





}
