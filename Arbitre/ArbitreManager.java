package Arbitre;

import Joueurs.*;
import Interface.*;

import java.io.*;
import java.util.*;

public class ArbitreManager
{
    public static Arbitre instance;
    public static Thread instanceThread;

    public static final int LIMITE_HISTORIQUE = 5;

    /**
     * Stop une partie
     **/
    public static void stopperPartie()
    {
        if (instanceThread == null)
            return;

        System.out.println("Demande d'arrêt");
        instance.setForceStop(true);
        instance.signalStop.envoyerSignal();

        // Force les attentes des signaux des joueurs abandonner
        instanceThread.interrupt(); 

        boolean interrupted = true;

        // Rééssaie si interrompu
        while (interrupted)
        {
            try
            {
                interrupted = false;       
                instanceThread.join();
            }
            catch (InterruptedException e)
            {
                interrupted = true;
            }
        }

        instanceThread = null;
        instance = null;

        System.out.println("Arrêt fini");
    }

    /**
     * Lance une partie
     **/
    public static void lancerPartie()
    {
        if (instanceThread != null)
        {
            boolean interrupted = true;

            // Rééssaie si interrompu
            while (interrupted)
            {
                try
                {
                    interrupted = false;       
                    instanceThread.join();
                }
                catch (InterruptedException e)
                {
                    interrupted = true;
                }
            }
        }

        instanceThread = new Thread(instance);
        instanceThread.start();
    }


    /**
     * Initialise une partie
     **/
    public static Arbitre initialiserPartie(Joueur [] joueurs, int largeur, int hauteur, Interface inter)
    {
        // Créé l'arbitre
        instance = new Arbitre(joueurs, largeur, hauteur);
  
        // Créé le terrain de base
        Case [][] terrain = new Case[hauteur][largeur];

        for (int i = 0; i < hauteur; i++)
            for (int j = 0; j < largeur; j++)
                terrain[i][j] = new Case(Etat.VIDE, null);

        // Lie l'interface
        instance.setInterface(inter);

        // Met en place ce terrain
        instance.setConfiguration(new Configuration(largeur, hauteur, terrain, joueurs[0]));

        // Renvoit notre arbitre en cours
        return instance;
    }

    /**
     * Sauvegarde l'historique de jeu dans un fichier
     **/
    public static void sauvegarderPartie(String filename)
    {
        if (instance != null)
            instance.sauvegarderPartie(filename);
    }

    /**
     * Charge un historique de jeu depuis un fichier
     **/
    public static void chargerPartie(String filename)
    {
        if (instance == null)
            return;

        System.out.println("Chargement de la partie depuis "+filename);

        File file = new File(filename);
        String data = "";
        try 
        {
            Scanner scanner = new Scanner(file);
	    
            while (scanner.hasNextLine()) 
                data += scanner.nextLine();

            scanner.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("Impossible de charger le fichier "+filename);
            return;
        }


        Sauvegarde save = new Sauvegarde();
        ListIterator<Configuration> listConfig = save.readXml(data);
	
        if (listConfig == null || !listConfig.hasNext())
        {
            System.err.println("Impossible de parser la sauvegarde "+filename);
            return;
        }

/**
	
	Joueur joueur1 = null;
	Joueur joueur2 = null;

	if (save.getJoueurEnCours() == 1)
	{
	    joueur1 = Joueur.getJoueurByName(save.getJoueur1());
	    joueur2 = Joueur.getJoueurByName(save.getJoueur2());
	}
	else
	{   
	    joueur1 = Joueur.getJoueurByName(save.getJoueur2());
	    joueur2 = Joueur.getJoueurByName(save.getJoueur1());
	}

	System.out.println(joueur1.getNom());
	System.out.println(joueur2.getNom());

        Interface inter = instance.getInterface();
        int largeur = save.getLargeur();
        int hauteur = save.getHauteur();

        stopperPartie();
        initialiserPartie(joueur1, joueur2, largeur, hauteur, inter);
        instance.chargerPartie(save);
        lancerPartie();
    }
**/
        System.out.println("TODO: implanter chargement");
    }
            
}
