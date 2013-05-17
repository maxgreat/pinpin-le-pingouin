package MonToolkit;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;

public class Bouton extends JComponent{
int x0 , y0 ;
int dimx ,dimy;
String nom;

	public Bouton(int x ,int y ,  int dimx , int dimy,String s)    
	{
	this.x0 = x;
	this.y0 = y;
	this.dimx = dimx;
	this.dimy = dimy;
	this.nom = s;
	}
	private URL getImage(String nom) {
        ClassLoader cl = getClass().getClassLoader();
        return cl.getResource(nom);
    	}
	
	public void paintComponent(Graphics g){
		Graphics2D drawable = (Graphics2D) g;
	BufferedImage image_case = null;
	try 
                {
		    
                    image_case = ImageIO.read(getImage(nom));
                } 
        catch (IOException e) 
                {
                    System.err.println(e);
                    System.exit(1);
                }
	 drawable.drawImage(image_case,x0,y0,dimx,dimy,null);
	
	}
	           
}
