package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;

public class EcouteurDeBox implements ActionListener{
	InterfaceGraphique inter;
	String S;
	JComboBox Comb;
	MenuPerso perso;
	String current;

	int i;
	public EcouteurDeBox(InterfaceGraphique inter , String S, JComboBox Comb,MenuPerso perso,String current,int i){
		this.inter = inter;
		this.S = S;
		this.i = i;
		this.Comb = Comb;
		this.perso = perso;
		this.current = current;
		
		if( i==1 && current.compareTo("Ordinateur") != 0)
			perso.b1 = false;
		if( i==1 &&current.compareTo("Aucun") == 0)
			perso.b1 = false;
		if( i==2 &&current.compareTo("Ordinateur") != 0)
			perso.b2 = false;
		if( i==2 &&current.compareTo("Aucun") == 0)
			perso.b2 = false;
		if( i==3 &&current.compareTo("Ordinateur") == 0)
			perso.b3 = false;
		if( i==3 &&current.compareTo("Aucun") == 0)
			perso.b3 = false;
		if( i==4 &&current.compareTo("Ordinateur") != 0)
			perso.b4 = false;
		if( i==4 &&current.compareTo("Aucun") == 0)
			perso.b4 = false;
	}
	

	public void actionPerformed(ActionEvent e){
	if(S.compareTo("joueur1") == 0)
		{
		inter.joueur1 = (String)Comb.getSelectedItem();
		String test = (String)Comb.getSelectedItem();
		if( test.compareTo("Ordinateur") == 0)
			perso.b1 = true;
		if( test.compareTo("Ordinateur") != 0)
			perso.b1 = false;
		if( test.compareTo("Aucun") == 0)
			perso.b1 = false;
		
		
		}
	if(S.compareTo("joueur2") == 0)
		{
		inter.joueur2 = (String)Comb.getSelectedItem();
		String test = (String)Comb.getSelectedItem();
		if( test.compareTo("Ordinateur") == 0)
			perso.b2 = true;
		if( test.compareTo("Ordinateur") != 0)
			perso.b2 = false;
		if( test.compareTo("Aucun") == 0)
			perso.b2 = false;
		
		}
	if(S.compareTo("joueur3") == 0)
		{
		inter.joueur3 = (String)Comb.getSelectedItem();
		String test = (String)Comb.getSelectedItem();
		if( test.compareTo("Ordinateur") == 0)
			perso.b3 = true;
		if( test.compareTo("Ordinateur") != 0)
			perso.b3 = false;
		if( test.compareTo("Aucun") == 0)
			perso.b3 = false;

		}
	if(S.compareTo("joueur4") == 0)
		{
		inter.joueur4 =(String)Comb.getSelectedItem();
		String test = (String)Comb.getSelectedItem();
		if( test.compareTo("Ordinateur") == 0)
			perso.b4 = true;
		if( test.compareTo("Ordinateur") != 0)
			perso.b4 = false;
		if( test.compareTo("Aucun") == 0)
			perso.b4 = false;

		}
	perso.setBoutons("Nouvelle Partie");
	}

}
