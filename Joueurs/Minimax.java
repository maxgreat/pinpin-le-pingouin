package Joueurs;
import Arbitre.*;
import java.util.Random;


public class Minimax implements Runnable
{
    Joueur joueur;
    Signal<Coup> signalCoup;

    public Minimax(Joueur joueur)
    {
        this.joueur = joueur;
        this.signalCoup = new Signal<Coup>();
    }

    public Signal<Coup> getSignalCoup()
    {
        return signalCoup;
    }

    public void run()
    {
        getSignalCoup().envoyerSignal(minimax(ArbitreManager.instance.getConfiguration().clone()));
    }

    public Coup minimax(Configuration cc){
        Coup [] coupPossible = cc.toutCoupsPossibles();
        int max=-10000,tmp,maxi=-1;
        Configuration cl;

        for(int i = 0; i < coupPossible.length && !Thread.interrupted(); i++){
            cl = cc.clone();
            cl.effectuerCoup(coupPossible[i]);
            tmp = Min(cl, max, 4);
            if(tmp > max){
                max = tmp;
                maxi = i;
            }
        }				
        return coupPossible[maxi];
    }

    public int Max(Configuration cc, int min, int profondeur)
    {
        Coup [] coupPossible = cc.toutCoupsPossibles();
        if(coupPossible.length == 0)
            return -1000;
        if (profondeur < 1)
            return -(cc.toutCoupsPossibles().length);
        int max = -10000;
        int tmp;
        Configuration cl;

        for(int i = 0; i < coupPossible.length && !Thread.interrupted(); i++){
            cl = cc.clone();
            cl.effectuerCoup(coupPossible[i]);
            tmp = Min(cl, max, profondeur-1);
            if(tmp > max){
                max = tmp;
            }
            if (min < max)
                break;
        }

        return max;		
    }

    public int Min(Configuration cc, int max, int profondeur){
        Coup [] coupPossible = cc.toutCoupsPossibles();
        if(coupPossible.length == 0)
            return 1000;
        if (profondeur < 1)
            return cc.toutCoupsPossibles().length;
        int min = 10000;
        int tmp;
        Configuration cl;

        for(int i = 0; i < coupPossible.length && !Thread.interrupted(); i++){
            cl = cc.clone();
            cl.effectuerCoup(coupPossible[i]);
            tmp = Max(cl, min, profondeur-1);
            if(tmp < min){
                min = tmp;
            }
            if (min < max)
                break;
        }

        return min;		
    }
}
