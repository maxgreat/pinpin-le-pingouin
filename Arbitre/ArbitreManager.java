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
     * Constantes du jeu
     **/
    public static final int NOMBRE_TROIS_POISSONS = 10;
    public static final int NOMBRE_DEUX_POISSONS  = 20;
    public static final int NOMBRE_UN_POISSON     = 30;
    public static final int HAUTEUR_GRILLE        = 8;
    public static final int LARGEUR_GRILLE        = 8;

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

        // Probabilité de tirer les poissons
        double totalPoisson = NOMBRE_UN_POISSON + NOMBRE_DEUX_POISSONS + NOMBRE_TROIS_POISSONS;

        double chanceUnPoisson = (double)NOMBRE_UN_POISSON * 100 / totalPoisson;
        double chanceDeuxPoissons = (double)NOMBRE_DEUX_POISSONS * 100 / totalPoisson;
        double chanceTroisPoissons = (double)NOMBRE_TROIS_POISSONS * 100 / totalPoisson;

        // Normalisation
        double sommeTotal = chanceUnPoisson + chanceDeuxPoissons + chanceTroisPoissons;
        double norm = 1 / sommeTotal;

        chanceUnPoisson *= norm;
        chanceDeuxPoissons *= norm;
        chanceTroisPoissons *= norm;
      
        // Tri les probabilité
        double maxi   = Math.max(chanceUnPoisson, Math.max(chanceDeuxPoissons, chanceTroisPoissons));
        double mini   = Math.min(chanceUnPoisson, Math.min(chanceDeuxPoissons, chanceTroisPoissons));
        double medium = chanceUnPoisson + chanceDeuxPoissons + chanceTroisPoissons - maxi - mini;           

        // Récupère les poissons
        Etat maxPoisson = Etat.VIDE;
        Etat medPoisson = Etat.VIDE;
        Etat minPoisson = Etat.VIDE;
        int masque = 0;


        int totalMax = 0;
        int totalMed = 0;
        int totalMin = 0;

        if (maxi == chanceUnPoisson)
        {
            maxPoisson = Etat.UN_POISSON;
            totalMax = NOMBRE_UN_POISSON;
            masque = 1;
        }
        else if (maxi == chanceDeuxPoissons)
        {
            maxPoisson = Etat.DEUX_POISSONS;
            totalMax = NOMBRE_DEUX_POISSONS;
            masque = 2;
        }
        else
        {
            maxPoisson = Etat.TROIS_POISSONS;
            totalMax = NOMBRE_TROIS_POISSONS;
            masque = 4;
        }


        if (mini == chanceUnPoisson)
        {
            minPoisson = Etat.UN_POISSON;
            totalMin = NOMBRE_UN_POISSON;
            masque += 1;
        }
        else if (mini == chanceDeuxPoissons)
        {
            masque += 2;
            totalMin = NOMBRE_DEUX_POISSONS;
            minPoisson = Etat.DEUX_POISSONS;
        }
        else
        {
            minPoisson = Etat.TROIS_POISSONS;
            totalMin = NOMBRE_TROIS_POISSONS;
            masque += 4;
        }


        if (7 - masque == 1)
        {
            medPoisson = Etat.UN_POISSON;
            totalMed = NOMBRE_UN_POISSON;
        }
        else if (7 - masque == 2)
        {
            medPoisson = Etat.DEUX_POISSONS;
            totalMed = NOMBRE_DEUX_POISSONS;
        }
        else
        {
            medPoisson = Etat.TROIS_POISSONS;
            totalMed = NOMBRE_TROIS_POISSONS;
        }


        // Aléatoire avec rejet

        // Assigne le terrain aléatoirement
        Random r = new Random();

        for (int i = 0; i < hauteur; i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                if (i%2 == 0 && j == largeur - 1)
                    continue;

                // Tire un nombre aléatoire
                while (true)
                {
                    double alea = r.nextDouble();
                
                    // Assigne la case
                    if (alea < maxi)
                    {
                        if (totalMax > 0)
                        {
                            terrain[i][j] = new Case(maxPoisson, null);
                            totalMax--;
                            break;
                        }
                    }
                    else if (alea >= maxi && alea < maxi + medium)
                    {
                        if (totalMed > 0)
                        {
                            totalMed--;
                            terrain[i][j] = new Case(medPoisson, null);
                            break;
                        }
                    }
                    else
                    {
                        if (totalMin > 0)
                        {
                            terrain[i][j] = new Case(minPoisson, null);
                            totalMin--;
                            break;
                        }
                    }
                }
            }
        }
                    

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

	Arbitre arbitre = null;

	try
	{
	    FileInputStream fstream = new FileInputStream(filename);
	    ObjectInputStream in = new ObjectInputStream(fstream);
            
	    Sauvegarde save = new Sauvegarde();
	    arbitre = save.load(in);
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("Impossible de charger le fichier "+filename);
            return;
        }
	catch(IOException e)
	{
	    System.out.println(e);
            System.err.println("Impossible de charger le fichier "+filename);
            return;
        }


	
        if (arbitre == null)
        {
            System.err.println("Impossible de parser la sauvegarde "+filename);
            return;
        }
	
	Interface inter = instance.getInterface();

        stopperPartie();
	instance = arbitre;
	arbitre.chargerPartie(inter);
	lancerPartie();
    }
            
}
