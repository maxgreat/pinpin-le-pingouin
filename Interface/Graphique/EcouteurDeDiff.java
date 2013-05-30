package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;

public class EcouteurDeDiff implements ActionListener{
	InterfaceGraphique inter;
	String S;
	JComboBox Comb;

	
	public EcouteurDeDiff(InterfaceGraphique inter , String S, JComboBox Comb){
		this.inter = inter;
		this.S = S;
		this.Comb = Comb;

	}
	public void actionPerformed(ActionEvent e){
	if(S.compareTo("niveau1") == 0)
		{
		inter.niveau1 = (String)Comb.getSelectedItem();
		}
	if(S.compareTo("niveau2") == 0)
		{
		inter.niveau2 = (String)Comb.getSelectedItem();
		}
	if(S.compareTo("niveau3") == 0)
		{
		inter.niveau3 = (String)Comb.getSelectedItem();
		}
	if(S.compareTo("niveau4") == 0)
		{
		inter.niveau4 = (String)Comb.getSelectedItem();
		}
	}

}
