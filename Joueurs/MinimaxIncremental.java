package Joueurs;

import Arbitre.*;
import Arbitre.Regles.*;
import Utilitaires.*;

public class MinimaxIncremental extends Minimax
{
    Signal<Object> signalSynchro;


	public MinimaxIncremental(Joueur joueur, int profondeur, Arbitre arbitre, Boolean finish)
    {
	    super(joueur, profondeur, arbitre, finish);
	    signalSynchro = new Signal<Object>();
    }


    public Signal<Object> getSignalSynchro()
    {
	return signalSynchro;
    }

    public void run()
    {
	    Coup c = minimax(ArbitreManager.instance.getConfiguration().clone());
	    ArbitreManager.instanceThread.interrupt();
	    getSignalCoup().envoyerSignal(c);
    }
}
