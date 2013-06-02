package Joueurs;
import Arbitre.Regles.*;

public class ScoreCoup{
int score;
Coup coup;

    public ScoreCoup(int sc, Coup c){
       score =sc;
       coup = c;
    }

	 public int getScore(){
		return score;
	 }

	 public void setScore(int s){
		score = s;
	 }

	 public Coup getCoup(){
		return coup;
	 }

	 public void setCoup(Coup c){
		coup = c;
	 }
}
