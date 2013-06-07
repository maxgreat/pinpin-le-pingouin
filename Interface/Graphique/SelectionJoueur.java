package Interface.Graphique;

import Joueurs.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;


public class SelectionJoueur
{
    Integer type = 0;
    JComboBox nomsJoueurs;
    JComboBox difficultesCPU;
    int numeroJoueur;

    public SelectionJoueur(int numeroJoueur, JComboBox nomsJoueurs, JComboBox difficultesCPU)
    {
        this.numeroJoueur = numeroJoueur;
        this.nomsJoueurs = nomsJoueurs;
        this.difficultesCPU = difficultesCPU;
            
        if (numeroJoueur == 1)
            type = 2;
        else if (numeroJoueur == 2)
            type = 1;
        else 
            type = 0;
    }

    public Joueur getJoueur()
    {
        Joueur j = null;
        if (estJoueur())
		{        
			j = new JoueurHumain();
            if (nomsJoueurs.getSelectedItem().equals("Entrez votre nom..."))
                j.setNom("Joueur "+numeroJoueur);
            else
                j.setNom(String.valueOf(nomsJoueurs.getSelectedItem()));
        }
        else if (estOrdinateur())
        {         
		String diff = (String) difficultesCPU.getSelectedItem();
            if (diff.compareTo("Facile") == 0)
                j = new JoueurCPUUniversel(3);
            else if (diff.compareTo("Intermediaire") == 0)
                j = new JoueurCPUUniversel(2);
            else
                j = new JoueurCPUUniversel(1);
            j.setNom("Ordinateur ("+String.valueOf(difficultesCPU.getSelectedItem())+")");
        }

        return j;
    }
    
    public boolean estOrdinateur()
    {
        return type == 1;
    }
    public void setOrdinateur()
    {
        type = 1;
    }

    public boolean estJoueur()
    {
        return type == 2;
    }
    public void setJoueur()
    {
        type = 2;
    }


    public boolean estAucun()
    {
        return type == 0;
    }
    public void setAucun()
    {
        type = 0;
    }
}

