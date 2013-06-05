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

    public EcouteurBoutonMenuPerso(SelectionJoueurs J, SelectionJoueur type, int but)
    {
        this.J = J;
        this.but = but;
        this.type = type;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (but == 1)
            type.setJoueur();
        else if (but == 2)
            type.setOrdinateur();
        else if (but == 3)
            type.setAucun();

        J.selectionDifficulte(type);	
        
    }
}
