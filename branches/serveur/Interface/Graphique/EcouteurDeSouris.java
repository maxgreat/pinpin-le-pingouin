package Interface.Graphique;

import java.awt.event.*;

public class EcouteurDeSouris implements MouseListener{
	AireDeJeu a;
	
	public EcouteurDeSouris(AireDeJeu a){
		this.a = a;
	}

	public void mousePressed(MouseEvent e){
		a.click(e.getX(), e.getY());
	}
	
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}

}
