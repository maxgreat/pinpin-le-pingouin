package Sound;

public class Donnees {
	boolean music;
	boolean fond;
	boolean bruit;

	public Donnees(){
		music = true;
		fond = true;
		bruit = true;
	}

	public boolean getMusic(){
		return music;
	}
	public boolean getFond(){
		return fond;
	}
	public boolean getBruit(){
		return bruit;
	}
	public void setMusic(boolean m){
		this.music = m;
	}
	public void setFond(boolean f){
		this.fond = f;
	}
	public void setBruit(boolean b){
		this.bruit = b;
	}
}
