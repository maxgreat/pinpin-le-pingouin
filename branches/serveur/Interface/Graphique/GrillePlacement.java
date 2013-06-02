package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.IOException;
import java.net.URL;

public class GrillePlacement extends JPanel{
	InterfaceGraphique inter;

	public GrillePlacement(InterfaceGraphique inter, int width, int height)
	{
		this.setPreferredSize(new Dimension(width,height));
		this.inter = inter;
	}

	public void paintComponent(Graphics g){
		//this.setPreferredSize(new Dimension(inter.frame.getWidth(),inter.frame.getHeight()));
		//inter.frame.pack();
		//super(g);
		
		//System.out.println("Taille de la fenetre : " + inter.frame.getWidth() + inter.frame.getHeight());
		//System.out.println("Taille du panel : " + this.getWidth() + this.getHeight());
		
		
		
	}
}
