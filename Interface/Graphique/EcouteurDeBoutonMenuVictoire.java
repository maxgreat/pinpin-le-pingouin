package Interface.Graphique;
import Interface.*;
import java.awt.event.*;
import Arbitre.*;
import Joueurs.*;

public class EcouteurDeBoutonMenuVictoire implements ActionListener{
	String message;
	InterfaceGraphique inter;
	POPMenuVictoire menu;

	public EcouteurDeBoutonMenuVictoire(String m, InterfaceGraphique inter, POPMenuVictoire menu){
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
