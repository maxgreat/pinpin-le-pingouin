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
	SelectionJoueur type;
	JButton b1;
	JButton b2;
	JButton b3;
	int menu;

	public EcouteurBoutonMenuPerso(SelectionJoueurs J, SelectionJoueur type, int but, JButton b1, JButton b2, JButton b3, int menu)
    {
        this.J = J;
        this.but = but;
        this.type = type;
	this.b1 = b1;
	this.b2 = b2;
	this.b3 = b3;
	this.menu = menu;
    }

	public void actionPerformed(ActionEvent e) {
		if (but == 1) {
			type.setJoueur();
			b1.setIcon(new ImageIcon("Interface/Graphique/Img/selectedHumain"+menu+".png"));
			b2.setIcon(new ImageIcon("Interface/Graphique/Img/joueurOrdinateur.png"));
			b3.setIcon(new ImageIcon("Interface/Graphique/Img/aucun.png"));
		} else if (but == 2) {
			type.setOrdinateur();
			b1.setIcon(new ImageIcon("Interface/Graphique/Img/joueurHumain"+menu+".png"));
			b2.setIcon(new ImageIcon("Interface/Graphique/Img/selectedOrdinateur"+menu+".png"));
			b3.setIcon(new ImageIcon("Interface/Graphique/Img/aucun.png"));
		} else if (but == 3) {
			type.setAucun();
			b1.setIcon(new ImageIcon("Interface/Graphique/Img/joueurHumain"+menu+".png"));
			b2.setIcon(new ImageIcon("Interface/Graphique/Img/joueurOrdinateur.png"));
			b3.setIcon(new ImageIcon("Interface/Graphique/Img/selectedAucun"+menu+".png"));
		}
		J.selectionDifficulte(type);	
		
	}
}
