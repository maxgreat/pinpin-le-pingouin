package Interface.Graphique;
import Interface.*;
import java.awt.event.*;
import Arbitre.*;
import Joueurs.*;

public class EcouteurDeBoutonMenuIA implements ActionListener{
	String message;
	MenuSelectionIA menu;

	public EcouteurDeBoutonMenuIA(String m, MenuSelectionIA menu){
		message = m;
		this.menu = menu;
	}

	public void actionPerformed(ActionEvent e)
    {
    	menu.nouveauJoueur(message);
    	menu.close();	
	}
}
