package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;
public class Banniere extends JPanel{
	String s;
	JFrame frame;
	
	public Banniere(String s, JFrame frame)
	{
		this.s = s;
		this.frame = frame;
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
		
		g.drawImage(Fond, 0, 0,700,500/4, null);
	}
	
	
	private URL getImage(String nom)
	{
	    ClassLoader cl = getClass().getClassLoader();
	    return cl.getResource("Interface/Graphique/Img/" + nom);
	}


}
