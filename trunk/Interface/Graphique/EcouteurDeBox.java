package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;

public class EcouteurDeBox implements ActionListener{
	MenuPerso menu;
	String S;
	JComboBox Comb;
	public EcouteurDeBox(MenuPerso menu , String S, JComboBox Comb){
		this.menu = menu;
		this.S = S;
		this.Comb = Comb;
	}
	

	public void actionPerformed(ActionEvent e){
	if(S.compareTo("joueur1") == 0)
		menu.joueur1 = (String)Comb.getSelectedItem();
	if(S.compareTo("joueur2") == 0)
		menu.joueur2 = (String)Comb.getSelectedItem();
	if(S.compareTo("joueur3") == 0)
		menu.joueur3 = (String)Comb.getSelectedItem();
	if(S.compareTo("joueur4") == 0)
		menu.joueur4 =(String) Comb.getSelectedItem();
	if(S.compareTo("niveau1") == 0)
		menu.niveau1 = (String)Comb.getSelectedItem();
	if(S.compareTo("niveau2") == 0)
		menu.niveau2 = (String)Comb.getSelectedItem();
	if(S.compareTo("niveau3") == 0)
		menu.niveau3 = (String)Comb.getSelectedItem();
	if(S.compareTo("niveau4") == 0)
		menu.niveau4 = (String)Comb.getSelectedItem();
	}

}
