package Interface.Graphique;
import javax.swing.*;
import java.awt.*;
import Joueurs.*;

public class MenuSelectionIA extends JInternalFrame{
	JoueurCPUUniversel j;
	AireDeJeu aire;

	public MenuSelectionIA(JoueurCPUUniversel j, AireDeJeu a) {
		super("Menu",
		      true, //resizable
		      true, //closable
		      true, //maximizable
		      true);//iconifiable
		//Set the window's location.
		reshape(100,100,100,200);
		this.j = j;
		aire = a;
		this.setVisible(true);
		
		
		//ajout des boutons

		JPanel pan = new JPanel();;
		JButton bFacile = new JButton("Ordinateur facile");
		JButton bInter = new JButton("Ordinateur moyen");
		JButton bDur = new JButton("Ordinateur difficile");
		pan.setLayout(new GridLayout( 3, 1));
		bFacile.addActionListener(new EcouteurDeBoutonMenuIA("bFacile", this));
		bInter.addActionListener(new EcouteurDeBoutonMenuIA("bInter", this));
		bDur.addActionListener(new EcouteurDeBoutonMenuIA("bDur", this));
		pan.add(bFacile,BorderLayout.CENTER);
		pan.add(bInter,BorderLayout.CENTER);
		pan.add(bDur,BorderLayout.CENTER);

		this.setContentPane(pan);
	}
	
	public void close(){
		try{
			this.setClosed(true);
			aire.menuOuvert = false;
		}catch(Exception e){
			System.out.println("Erreur fermeture menu : " + e);
			System.exit(1);
		}
	}
	
	public void nouveauJoueur(String m)
	{
		if(m.compareTo("bFacile") == 0)
			j.setLevel(3);
		else if(m.compareTo("bInter") == 0)
			j.setLevel(2);
		else if(m.compareTo("bDur") == 0)
			j.setLevel(1);
	}	
}
