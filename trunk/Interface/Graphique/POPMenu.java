package Interface.Graphique;
import javax.swing.*;
import java.awt.*;

public class POPMenu extends JInternalFrame{

	public POPMenu() {
		super("Menu",
		      true, //resizable
		      true, //closable
		      true, //maximizable
		      true);//iconifiable
		//Set the window's location.
		reshape(100,100,100,200);
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
