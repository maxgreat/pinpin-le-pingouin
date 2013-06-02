package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

public class EcouteurDeFenetre implements InternalFrameListener
{
	AireDeJeu aire;
	
	public EcouteurDeFenetre(AireDeJeu a)
	{
		this.aire = a;
	}

	public void internalFrameClosed(InternalFrameEvent e)
	{
  		aire.menuOuvert = false;
	}
	
	 public void internalFrameActivated(InternalFrameEvent e){}
	 public void internalFrameClosing(InternalFrameEvent e){}

	 public void internalFrameDeactivated(InternalFrameEvent e){}

	 public void internalFrameDeiconified(InternalFrameEvent e){}

	  public void internalFrameIconified(InternalFrameEvent e){}

	  public void internalFrameOpened(InternalFrameEvent e) {}

}
