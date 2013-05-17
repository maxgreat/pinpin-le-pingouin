import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import MonToolkit.*;

public class TestFenetre implements Runnable {
    public void run() {
        // Creation d'une fenetre
        JFrame frame = new JFrame("Essai SWING");
	frame.setSize(500 , 500);
        JPanel panel = new JPanel();

        // Nos boutons a nous
	
        Bouton bouton = new Bouton(0,0,500,500,"case.txt");
        panel.add(bouton);

        frame.add(panel);
         
        // Un clic sur le bouton de fermeture clos l'application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On fixe la taille de la fenetre au minimum pour contenir tous les
        // composants
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String [] args) {
        SwingUtilities.invokeLater(new TestFenetre());
    }
}
