package Interface.Graphique;
import Interface.*;
import java.awt.event.*;
import Arbitre.*;
import Joueurs.*;

public class EcouteurDeBoutonMenu implements ActionListener{
	String message;
	InterfaceGraphique inter;
	POPMenu menu;

	public EcouteurDeBoutonMenu(String m, InterfaceGraphique inter, POPMenu menu){
		message = m;
		this.inter = inter;
		this.menu = menu;
	}

	public void actionPerformed(ActionEvent e)
    {
    	menu.close();
		inter.afficherPanel(message);	
	}
}
