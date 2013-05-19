package Interface.Console;

import Arbitre.*;
import java.util.*;

/**
 * Classe de gestion d'entrées-sortie
 **/
public class GestionnaireEntree implements Runnable
{
    Scanner in = new Scanner(System.in);
    Signal<Object> signalSyncAffichage = new Signal<Object>();

    public Signal<Object> getSignalSync()
    {
        return signalSyncAffichage;
    }

    public void run()
    {
        boolean needSignalSync = true;

        // Récupère la commande, et la convertie en coup si nécessaire
        while (true)
        {
            if (needSignalSync)
                getSignalSync().attendreSignal();

            needSignalSync = false;

            String couleur = InterfaceConsole.couleurs[ArbitreManager.instance.getPosition(ArbitreManager.instance.getJoueurCourant()) - 1];

            System.out.print("["+couleur+ArbitreManager.instance.getJoueurCourant().getNom()+":"+ArbitreManager.instance.getJoueurCourant().getScore()+InterfaceConsole.RESET+"] ");

            if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN)
	    {
                System.out.println("Veuillez entrer une position où poser votre pingouin sous la forme x y ou quit :");
	    }
            else
	    {
                System.out.println("Veuillez entrer un coup (ou quit) sous la forme xDepart yDepart xArrivée yArrivée :");
		Coup [] listeCoup = ArbitreManager.instance.getConfiguration().toutCoupsPossibles();

		for (int i = 0; i < listeCoup.length; i++)
		    System.out.println("("+listeCoup[i].getXDepart()+", "+listeCoup[i].getYDepart()+") -> ("+listeCoup[i].getXArrivee()+", "+listeCoup[i].getYArrivee()+")");
	    }


            String command = in.nextLine();

            // Demande de fermeture
            if ("quit".equals(command.trim()))
            {
                // Stoppe la partie en cours
                ArbitreManager.stopperPartie();
                break;
            }

            // Sinon commande de coordonnées
            String [] intList = command.trim().split(" ");
            Coup coup = null;
            
            if (ArbitreManager.instance.getMode() == ModeDeJeu.POSE_PINGOUIN)
            {
                if (intList.length != 2)
                {
                    System.out.println("Mauvaise commande !");
                    continue;
                }
                
                // Récupère les coordonnées
                int x = Integer.valueOf(intList[0]);
                int y = Integer.valueOf(intList[1]);

                coup = new Coup(x, y, -1, -1);
            }
            else
            {
                if (intList.length != 4)
                {
                    System.out.println("Mauvaise commande !");
                    continue;
                }
                
                // Récupère les coordonnées
                int xDepart = Integer.valueOf(intList[0]);
                int yDepart = Integer.valueOf(intList[1]);
                int xArrivee = Integer.valueOf(intList[2]);
                int yArrivee = Integer.valueOf(intList[3]);
                
                coup = new Coup(xDepart, yDepart, xArrivee, yArrivee);
            }
            
            // Vérifie la validitée du coup
            if (!ArbitreManager.instance.getConfiguration().estCoupPossible(coup))
            {
                System.out.println("Coup impossible !");
                continue;
            }

            ArbitreManager.instance.getJoueurCourant().getSignalCoup().envoyerSignal(coup);
            needSignalSync = true;            
        }
    }
}
