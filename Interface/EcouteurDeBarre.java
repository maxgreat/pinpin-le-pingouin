package Interface;
import javax.swing.event.*;
import javax.swing.*;

public class EcouteurDeBarre implements ChangeListener{
	AireDeJeu aire;
	String message;
	
	public EcouteurDeBarre(AireDeJeu a, String m){
		aire = a;
		message = m;
	}
	
	public void stateChanged(ChangeEvent event){
		if(message.compareTo("largeur") == 0)
        	aire.largeur = ((JSlider)event.getSource()).getValue();
        else
        	aire.hauteur = ((JSlider)event.getSource()).getValue();
    }	
}
