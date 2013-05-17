package Arbitre;
import Joueurs.*;
import Interface.*;

import java.io.*;
import java.util.*;

public class Arbitre implements Runnable
{
    protected Configuration configurationCourante;
    protected Joueur [] joueurs;
    protected Joueur joueurCourant;
    protected int largeur;
    protected int hauteur;

    protected Interface inter;
    protected Historique historique;

    protected boolean estFini   = false;
    protected boolean forceStop = false;

    protected ModeDeJeu mode = ModeDeJeu.POSE_PINGOUIN;

    protected Signal<Object> signalStop;

    public Arbitre(Joueur [] joueurs, int largeur, int hauteur)
    {
        this.joueurs = joueurs;
        this.joueurCourant = joueurs[0];
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.signalStop = new Signal<Object>();
        this.historique = new Historique(ArbitreManager.LIMITE_HISTORIQUE);
    }
    
    /**
     * Retourne la position d'un joueur dans la liste
     **/
    public int getPosition(Joueur joueur)
    {
        int i = 1;
        while (joueurs[i - 1 ] != joueur)
            i++;

        // Si on a pas trouvé
        if (i > joueurs.length)
            return 0;

        return i;
    }

    /**
     * Retourne un joueur étant donnée sa position
     **/
    public Joueur getJoueurParPosition(int position)
    {
        if (position < 1 || position > joueurs.length)
            return null;

        return joueurs[position - 1];
    }
           

    public int getLargeur()
    {
        return largeur;
    }

    public int getHauteur()
    {
        return hauteur;
    }

    public void setLargeur(int largeur)
    {
        this.largeur = largeur;
    }

    public void setHauteur(int hauteur)
    {
        this.hauteur = hauteur;
    }

    public ModeDeJeu getMode()
    {
        return mode;
    }

    public void setMode(ModeDeJeu mode)
    {
        this.mode = mode;
    }


    /**
     * Lance et joue la partie
     **/
    public void run()
    {
        int tourJoueur = getPosition(getJoueurCourant());

        while (!estFini && !forceStop)
        {
            // Récupère le coup du joueur
            Coup coup = getJoueurCourant().coupSuivant();
	    
            // Effectue et vérifie la fin de partie
            Configuration configurationSuivante = configurationCourante.clone();
	    
            // Vérifie si une fin forcée n'a pas été déclanchée entre temps
            if (forceStop)
                break;

            // Lance le coup proposé
            int score = configurationSuivante.effectuerCoup(coup);

            // Met à jour le score du joueur
            getJoueurCourant().setScore(getJoueurCourant().getScore() + score);

	    // Nettoyer le terrain pour les pingouins isolés
	    if (getMode() == ModeDeJeu.JEU_COMPLET)
	    {
		int [] scoresJoueurs = configurationSuivante.nettoyerConfiguration(joueurs);
		
		System.out.println("TODO : mettre à jour le score des joueurs lorsque nettoyage plateau");
	    }

	    int [] restePingouins = configurationSuivante.getNombrePingouinsParJoueur(joueurs);
	    int totalPions = 0;

	    for (int i = 0; i < restePingouins.length; i++)
		totalPions += restePingouins[i];
	    
            if (totalPions == 0 && getMode() == ModeDeJeu.JEU_COMPLET)
            {
                // Fin du jeu
                estFini = true;
                break;
            }
            else
            {
		// Change le mode de jeu si nécessaire
		if (getMode() == ModeDeJeu.POSE_PINGOUIN)
		{
		    // 2 joueurs, 8 pions
		    // 3 joueurs, 9 pions
		    // 4 joueurs, 8 pions
		    if ((joueurs.length == 2 && totalPions == 8) ||
			(joueurs.length == 3 && totalPions == 9) ||
			(joueurs.length == 4 && totalPions == 10))
			setMode(ModeDeJeu.JEU_COMPLET);
		}

                // Change de joueur
                tourJoueur = (tourJoueur % joueurs.length) + 1;
                setJoueurCourant(getJoueurParPosition(tourJoueur));
		configurationSuivante.setJoueurSurConfiguration(getJoueurParPosition(tourJoueur));

                // Met en place la configuration
                setConfiguration(configurationSuivante);

            }
        }

        // Informe l'interface
        inter.repaint();

        // Attends qu'on lui signal la fin d'une partie (sauf si force le stop)
        if (!forceStop)
            signalStop.attendreSignal();

        return;
    }

    /**
     * Sauvegarde l'historique de jeu dans un fichier
     **/
    public void sauvegarderPartie(String filename)
    {
/*
        System.out.println("Sauvegarde de la partie dans "+filename);
        Sauvegarde save = new Sauvegarde();
        save.setLargeur(getLargeur());
        save.setHauteur(getHauteur());
        save.setJoueur1(joueur1.getNom());
        save.setJoueur2(joueur2.getNom());

        if (joueur1 == joueurCourant)
            save.setJoueurEnCours(1);
        else
            save.setJoueurEnCours(2);

        save.setIterateur(historique.getIterateur());

        String content = save.getXml();

        try 
        {
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(content);

            out.close();
        }
        catch (IOException e) 
        {
            System.err.println("Impossible de sauvegarder dans le fichier "+filename);
        }
*/
        System.out.println("TODO : implanter données à sauvegarde");
    }

    /**
     * Charge une partie depuis une liste de configuration
     **/
    public void chargerPartie(Sauvegarde save)
    {
        /**
        Configuration derniereConfiguration = null;
        ListIterator<Configuration> listConfig = save.getIterateur();

        setLargeur(save.getLargeur());
        setHauteur(save.getHauteur());
        
        // Remet à zéro l'historique
        historique = new Historique(ArbitreManager.LIMITE_HISTORIQUE);

        while (listConfig.hasNext())
        {
            Configuration c = listConfig.next();

            historique.ajout(c);
            derniereConfiguration = c;
        }

        if (derniereConfiguration != null)
            this.configurationCourante = derniereConfiguration;
        **/
        System.out.println("TODO : implanter données à charger");
    }


    /**
     * Retourne le signalStop
     **/
    public Signal<Object> getSignalStop()
    {
        return signalStop;
    }

    /**
     * Test la fin d'une partie
     **/
    public boolean partieFinie()
    {
        return estFini;
    }

    /**
     * Met en place l'interface
     **/
    public void setInterface(Interface inter)
    {
        this.inter = inter;
    }

    /**
     * Récupère l'interface
     **/
    public Interface getInterface()
    {
        return inter;
    }

    /**
     * Force la fin d'une partie
     **/
    public void setForceStop(boolean status)
    {
        forceStop = status;
    }

    public boolean getForceStop()
    {
        return forceStop;
    }

    /**
     * Retourne la configuration en cours
     **/
    public Configuration getConfiguration()
    {
        return configurationCourante;
    }


    /**
     * Assigne la configuration en cours c 
     * et le Joueurs à j
     **/
    public void setConfiguration(Configuration c)
    {
        historique.ajout(c);
        this.configurationCourante = c;
        inter.repaint();
    }

    

    /**
     * Retourne le joueur courant
     **/
    public Joueur getJoueurCourant()
    {
        return joueurCourant;
    }

    /**
     * Change le joueur en cours
     **/
    public void setJoueurCourant(Joueur j)
    {
        joueurCourant = j;
    }

    /**
     * Avance dans l'historique
     **/
    public void avancerHistorique()
    {
        Configuration c = historique.avance();
        if (c != null)
        {
            configurationCourante = c;
            inter.repaint();
        }
    }

    /**
     * Recule dans l'historique
     **/
    public void reculerHistorique()
    {
        Configuration c = historique.reculer();
        if (c != null)
        {
            configurationCourante = c;
            inter.repaint();
        }
    }
}