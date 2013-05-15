package Interface;
import java.awt.event.*;

public class EcouteurDeSouris implements MouseListener{
	AireDeJeu a;
	
	public EcouteurDeSouris(AireDeJeu a){
		this.a = a;
	}

	public void mousePressed(MouseEvent e){
		int x, y;
		
		x = e.getX()/a.largeurCase;
		y = e.getY()/a.hauteurCase;

		a.click(x, y);
		System.out.println("Click en " + x + " " + y);
	}
	
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}

}
