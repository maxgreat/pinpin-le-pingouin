package Interface;
import java.awt.event.*;
import javax.swing.*;
import Arbitre.*;

public class MenuEdition implements ActionListener {
    String nom;
    
    public MenuEdition(String m){
        nom = m;
    }
    
    public void actionPerformed(ActionEvent e) {
    	if(nom.compareTo("annuler") == 0)
    		ArbitreManager.instance.reculerHistorique();
    	else
    		ArbitreManager.instance.avancerHistorique();
    }
}
