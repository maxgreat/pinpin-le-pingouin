package Interface.Graphique;
import javax.swing.*;
import java.awt.*;

public class POPMenuVictoire extends JInternalFrame{

	public POPMenuVictoire(int h, int l) {
		super("Fin de Partie",
		      true, //resizable
		      true, //closable
		      true, //maximizable
		      true);//iconifiable
		//Set the window's location.
		reshape(5,250,1000,1000);
      
	}
	
	public void close(){
		try{
			this.setClosed(true);
		}catch(Exception e){
			System.out.println("Erreur fermeture menu : " + e);
			System.exit(1);
		}
	}
	
	
}
