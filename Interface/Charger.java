package Interface;
import java.awt.event.*;
import javax.swing.*;
import Arbitre.*;

import java.io.*;

public class Charger implements ActionListener 
{    
    public void actionPerformed(ActionEvent e) 
    {
        JFileChooser fileChooser = new JFileChooser();
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            File fichierChoisi = fileChooser.getSelectedFile();
            ArbitreManager.chargerPartie(fichierChoisi.getAbsolutePath());
        }

    }
}
