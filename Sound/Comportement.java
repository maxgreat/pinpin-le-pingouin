package Sound;

public class Comportement implements Runnable {
	String titre;
	Donnees donnees;
	boolean fond;

	public Comportement(String t, Donnees d) {
		this.titre = t;
		this.donnees = d;
		if(t.contains("bruit"))
			fond = false;
		else
			fond = true;
	}

	public void run() {
		if (fond) {
			while(donnees.getMusic() && donnees.getFond()){
				try
				{
					Sound son = new Sound(this.titre);
					son.play();
				}
				catch(Exception e){System.out.println("Impossible de lire le fichier son : \n"+e);}
			}
		} else {
			if(donnees.getMusic()){
				if(donnees.getBruit()){
					try{
						Sound son = new Sound(this.titre);
						son.play();
					}
					catch(Exception e){System.out.println("Impossible de lire le fichier son : \n"+e);}
				}
			}
		}
	}
}
