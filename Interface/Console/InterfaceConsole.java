package Interface.Console;

import Interface.*;
import Joueurs.*;
import Arbitre.*;

import java.util.*;

public class InterfaceConsole implements Interface
{
    /**
     * Scanner pour la récupération de commande
     **/
    Scanner in = new Scanner(System.in);
    
    /**
     * Le gestionnaire des entrées console
     **/
    GestionnaireEntree inputManager = new GestionnaireEntree();

    /**
     * Codes couleurs pour la console
     **/
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";


    /**
     * Couleur des joueurs selon leur rang
     **/
    public static final String [] couleurs = { RED, GREEN, BLUE, PURPLE };

    /**
     * Bannière
     **/
    public void printBanniere()
    {
        System.out.println(BLUE);
        System.out.println(" _______ _________ _        _______ _________ _       ");
        System.out.println("(  ____ )\\__   __/( (    /|(  ____ )\\__   __/( (    /|");
        System.out.println("| (    )|   ) (   |  \\  ( || (    )|   ) (   |  \\  ( |");
        System.out.println("| (____)|   | |   |   \\ | || (____)|   | |   |   \\ | |");
        System.out.println("|  _____)   | |   | (\\ \\) ||  _____)   | |   | (\\ \\) |");
        System.out.println("| (         | |   | | \\   || (         | |   | | \\   |");
        System.out.println("| )      ___) (___| )  \\  || )      ___) (___| )  \\  |");
        System.out.println("|/       \\_______/|/    )_)|/       \\_______/|/    )_)");
        System.out.println("                                                      ");
        System.out.println(RESET);
        System.out.println(" _        _______ ");
        System.out.println("( \\      (  ____ \\");
        System.out.println("| (      | (    \\/");
        System.out.println("| |      | (__    ");
        System.out.println("| |      |  __)   ");
        System.out.println("| |      | (      ");
        System.out.println("| (____/\\| (____/\\");
        System.out.println("(_______/(_______/");
        System.out.println("                  ");
        System.out.println(PURPLE);
        System.out.println(" _______ _________ _        _______  _______          _________ _       ");
        System.out.println("(  ____ )\\__   __/( (    /|(  ____ \\(  ___  )|\\     /|\\__   __/( (    /|");
        System.out.println("| (    )|   ) (   |  \\  ( || (    \\/| (   ) || )   ( |   ) (   |  \\  ( |");
        System.out.println("| (____)|   | |   |   \\ | || |      | |   | || |   | |   | |   |   \\ | |");
        System.out.println("|  _____)   | |   | (\\ \\) || | ____ | |   | || |   | |   | |   | (\\ \\) |");
        System.out.println("| (         | |   | | \\   || | \\_  )| |   | || |   | |   | |   | | \\   |");
        System.out.println("| )      ___) (___| )  \\  || (___) || (___) || (___) |___) (___| )  \\  |");
        System.out.println("|/       \\_______/|/    )_)(_______)(_______)(_______)\\_______/|/    )_)");
        System.out.println(RESET);

        System.out.println();
        System.out.println();
        
        System.out.println("Légende : ");
        System.out.println("\t0 pour case vide");
        System.out.println("\t1 pour un poisson");
        System.out.println("\t2 pour deux poissons");
        System.out.println("\t3 pour trois poissons");
        System.out.println();
        System.out.println("La case est colorée de la couleur du joueur si un pingouin est dessus");
        
        System.out.println();
        System.out.println();
        
    }

    /**
     * Lance l'interface graphique
     **/
    public void run(String [] args)
    {
        printBanniere();

        System.out.println("Seul le mode joueur contre joueur est disponible pour le moment.");
        System.out.println("Tappez entré si vous êtes d'accord avec ça, ou CTRL+C pour quitter de manière sale (shame on you)...");

        in.nextLine();


        // Créé la partie avec les données de base
        Joueur [] joueurs = new Joueur[2];
        joueurs[0] = new JoueurHumain();
        joueurs[0].setNom("David");

        joueurs[1] = new JoueurHumain();
        joueurs[1].setNom("Goliath");

        
        System.out.print("Début du jeu en 8*8 avec deux joueurs humains : ");
        System.out.print(RED+joueurs[0].getNom()+RESET);
        System.out.print(" et ");
        System.out.println(GREEN+joueurs[1].getNom()+RESET);
        System.out.println();

        // Créé le thread pour les entrées-sorties
        Thread t = new Thread(inputManager);
        t.start();

        ArbitreManager.initialiserPartie(joueurs, ArbitreManager.LARGEUR_GRILLE, ArbitreManager.HAUTEUR_GRILLE, this);

        // Lance la partie
        ArbitreManager.lancerPartie();

    }

    /**
     * Doit réafficher la configuration à l'écran
     **/
    public void repaint()
    {
        if (ArbitreManager.instance.getForceStop())
            return;

        Configuration config = ArbitreManager.instance.getConfiguration();
        
        for (int i = 0; i < ArbitreManager.instance.getHauteur(); i++)
        {
            for (int j = 0; j < ArbitreManager.instance.getLargeur(); j++)
            {
                if (i%2 == 1)
                    System.out.print(String.valueOf(config.getTerrain()[i][j].scorePoisson())+" ");
                else if (j < ArbitreManager.instance.getLargeur() - 1)
                    System.out.print(" "+String.valueOf(config.getTerrain()[i][j].scorePoisson()));
            }
            System.out.println();
        }

        System.out.println();
        
        // Synchronise la demande de commande
        inputManager.getSignalSync().envoyerSignal();
    }
}
