package Interface;
import java.awt.event.*;
import javax.swing.*;
import Arbitre.*;

import java.io.*;

public class Sauvegarder implements ActionListener 
{    
    public void actionPerformed(ActionEvent e) 
    {
        JFileChooser fileChooser = new JFileChooser();
        if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            File fichierChoisi = fileChooser.getSelectedFile();
            ArbitreManager.sauvegarderPartie(fichierChoisi.getAbsolutePath());
        }

    }
}
