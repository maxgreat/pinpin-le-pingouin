package Interface.Graphique;
import javax.swing.*;
import java.awt.*;

public class POPMenu extends JInternalFrame{

	public POPMenu(AireDeJeu a) {
		super("Menu",
		      true, //resizable
		      true, //closable
		      true, //maximizable
		      true);//iconifiable
		//Set the window's location.
		reshape(100,100,100,200);

        Dimension desktopSize = a.getSize();
        Dimension jInternalFrameSize = getSize();
        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
        this.setLocation(width, height);
		this.setVisible(true);


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
