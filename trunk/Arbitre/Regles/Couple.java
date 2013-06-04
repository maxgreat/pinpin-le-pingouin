package Arbitre.Regles;


public class Couple extends Object{
	protected int x;
	protected int y;

	public Couple(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public void setX(int n){
		this.x = n;
	}

	public void setY(int n){
		this.y = n;
	}

	public boolean equals(Object o){
		Couple c = (Couple) o;
		return (this.x == c.getX() && this.y == c.getY());
	}

	public int hashCode(){
		return this.x*101 + this.y;
	}
}
