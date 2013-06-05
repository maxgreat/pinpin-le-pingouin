package Interface.Graphique;

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

    public SelectionJoueur(int numeroJoueur)
    {
        if (numeroJoueur == 1)
            type = 2;
        else if (numeroJoueur == 2)
            type = 1;
        else 
            type = 0;
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

