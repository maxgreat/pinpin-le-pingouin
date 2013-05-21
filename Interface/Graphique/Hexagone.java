package Interface.Graphique;
import java.awt.Point;

class Hexagone{
	public Point [][] centres;
	public Point [][] sommetsG;
	public double largeur;
	public double hauteur;
	double rayonL;
	double rayonH;
	double margeHaut;
	double margeGauche;
	
	public void initHexagone(){
		centres = new Point[8][8];
		sommetsG = new Point[8][8];
		largeur = 0;
		hauteur = 0;
	}

	public void setTab(double rayonH, double rayonL, double margeHaut, double margeGauche){
		double l;
		double h;
		
		largeur = 2.0*rayonL;
		hauteur = 2.0*rayonH;
		this.rayonL = rayonL;
		this.rayonH = rayonH;
		this.margeHaut = margeHaut;
		this.margeGauche = margeGauche;
		
		for(int i=0;i<7;i++){
	    	for(int j=0;j<4;j++){
				l= margeGauche + rayonL + 2.0*rayonL*(double)i;
				h= margeHaut + (double)j*3.0*rayonH;
				sommetsG[2*j][i] = new Point((int)l,(int)h);
				centres[2*j][i] = new Point((int)(l+rayonL),(int)(h+rayonH));
			}
		}
		
		for(int i=0;i<8;i++){
			for(int j=0;j<4;j++){
				l= margeGauche + (double)i*2.0*rayonL;
				h= margeHaut + (3.0*rayonH)/2.0 +(double)j*3.0*rayonH;
				sommetsG[2*j+1][i] = new Point((int)l,(int)h);
				centres[2*j+1][i] = new Point((int)(l+rayonL),(int)(h+rayonH));
			}
		}
	}

	public int sommetG_x(int i, int j){
		return sommetsG[j][i].x;
	}
	
	public int sommetG_y(int i, int j){
		return sommetsG[j][i].y;
	}
	
	public int largeur(){
		return (int)largeur;
	}
	
	public int hauteur(){
		return (int)hauteur;
	}
	
	public Point estDansHexagone(int x, int y){
		Point p = new Point(0,0);
		int colonne = 0;
		double min = largeur;
		double distance;
		Point v;
		
		if(y > margeHaut && x > margeGauche){
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(centres[i][j] != null){
						v = new Point(centres[i][j].x - x, centres[i][j].y - y);
						distance = Math.sqrt(v.x*v.x + v.y*v.y);
						if(distance < min){
							min = distance;
							p = new Point(i,j);
						}
					}
				}
			}
		}
		return p;
	}
}
