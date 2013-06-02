package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;
public class Img extends JPanel{
String s;
Dimension d;

public Img(String s )
	{

	this.s = s;
	}
public void paintComponent(Graphics g)
    {
	Graphics2D drawable = (Graphics2D) g;
        BufferedImage Fond;
	Fond = null;
	try 
        {
		Fond = ImageIO.read(getImage(s));
	}
	catch (IOException e)
	{
                    System.err.println("erreur lecture images : " +e);
                    System.exit(1);
        }
		
	g.drawImage(Fond, 0, 0,this.getWidth(),this.getHeight(), null);
    }
private URL getImage(String nom)
    {
        ClassLoader cl = getClass().getClassLoader();
        return cl.getResource("Interface/Graphique/Img/" + nom);
    }
}
