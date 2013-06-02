package Interface.Graphique;
import Interface.*;
import java.awt.event.*;
import Arbitre.*;
import Joueurs.*;

public class EcouteurDeBouton implements ActionListener{
	String message;
	InterfaceGraphique inter;

	public EcouteurDeBouton(String m, InterfaceGraphique inter){
		message = m;
		this.inter = inter;
	}

	public void actionPerformed(ActionEvent e)
    {
		inter.afficherPanel(message);	
	}
}
