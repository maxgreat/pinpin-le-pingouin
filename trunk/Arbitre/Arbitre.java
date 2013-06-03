package Arbitre;

import Joueurs.*;
import Interface.*;
import Arbitre.Regles.*;
import Utilitaires.*;

import java.io.*;

public class Arbitre implements Runnable, Serializable
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

    public static final long serialVersionUID = 2L;

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

        if (joueur == null)
            return 0;

        while (i <= joueurs.length && joueurs[i - 1] != joueur)
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
        if (position < 1 || joueurs == null || position > joueurs.length)
            return null;

        return joueurs[position - 1];
    }
    /**
     * Retourne l'ensemble des joueurs
     **/
    public Joueur [] getJoueurs() {
        return this.joueurs;
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
        while (!forceStop && !estFini)
	    {
            int tourJoueur = getPosition(getJoueurCourant());

            // Récupère le coup du joueur
            Coup coup = getJoueurCourant().coupSuivant();
            while (coup != null && !getConfiguration().estCoupPossible(coup, getMode()))
		    {
                //System.out.println("Mauvais coup !"+getMode());
                //System.out.println(coup);
                // Envoyer signal joueur mauvais coup
                coup = getJoueurCourant().coupSuivant();
		    } 

	    
            // Changement d'historique
            if (coup == null && !forceStop)
                continue; // Refait un tour, le joueur courant à changer
	    
            // Effectue et vérifie la fin de partie
            Configuration configurationSuivante = configurationCourante.clone();
	    
            // Vérifie si une fin forcée n'a pas été déclanchée entre temps
            if (forceStop)
                break;

            // Lance le coup proposé
            getConfiguration().setCoupEffectue(coup);
            int score = configurationSuivante.effectuerCoup(coup);

            // Met à jour le score du joueur
            getJoueurCourant().setScore(getJoueurCourant().getScore() + score);

            // Change le mode de jeu si nécessaire
            if (getMode() == ModeDeJeu.POSE_PINGOUIN)
		    {
                int [] restePingouins = configurationSuivante.getNombrePingouinsParJoueur(joueurs);
                int totalPions = 0;

                for (int i = 0; i < restePingouins.length; i++)
                    totalPions += restePingouins[i];

                // 2 joueurs, 8 pions
                // 3 joueurs, 9 pions
                // 4 joueurs, 8 pions
                if ((joueurs.length == 2 && totalPions == 8) ||
                    (joueurs.length == 3 && totalPions == 9) ||
                    (joueurs.length == 4 && totalPions == 8))
                    setMode(ModeDeJeu.JEU_COMPLET);
		    }
            // Vient de gagner une tuile
            else if (getMode() == ModeDeJeu.JEU_COMPLET)
		    {
                getJoueurCourant().incrementNombreTuile();
		    }

            // Change de joueur (en prenant en compte ceux qui peuvent jouer)
            int totalPouvantJouer = joueurs.length;
            do
		    {
                tourJoueur = (tourJoueur % joueurs.length) + 1;
                setJoueurCourant(getJoueurParPosition(tourJoueur));
                configurationSuivante.setJoueurSurConfiguration(getJoueurParPosition(tourJoueur));
                configurationSuivante.setScoreSurConfiguration(configurationSuivante.getJoueurSurConfiguration().getScore());
                totalPouvantJouer--;

		    } while (totalPouvantJouer >= 0 && 
                     (getMode() == ModeDeJeu.JEU_COMPLET &&
                      !configurationSuivante.peutJouer(getJoueurCourant())
                      ));

            // Met en place la configuration
            setConfiguration(configurationSuivante);

            // Si plus personne ne peut jouer on arrête
            if (totalPouvantJouer < 0)
                estFini = true;
	    }

        // Nettoie le terrain à la fin de la partie
        if (!forceStop && estFini)
	    { 
            int [] scoresJoueurs = getConfiguration().nettoyerConfiguration(joueurs);
            
            // Met à jour le score de tous les joueurs
            for (int i = 0; i < scoresJoueurs.length; i++)
                joueurs[i].setScore(joueurs[i].getScore() + scoresJoueurs[i]);
            
	    }
        
        // Informe l'interface
        inter.repaint();

        // Attends qu'on lui signal la fin d'une partie (sauf si force le stop)
        if (!forceStop || estFini)
            signalStop.attendreSignal();

        return;
    }
  
    /**
     * Sauvegarde l'historique de jeu dans un fichier
     **/
    public void sauvegarderPartie(String filename)
    {
        System.out.println("Sauvegarde de la partie dans "+filename);
        Sauvegarde save = new Sauvegarde();
        save.setArbitre(this);

        try 
	    {
            FileOutputStream fstream = new FileOutputStream(filename);
            save.save(fstream);
	    }
        catch (IOException e) 
	    {
            System.err.println("Impossible de sauvegarder dans le fichier "+filename);
	    }
    }

    /**
     * Charge la sauvegarde
     **/
    public void chargerPartie(Interface inter)
    {
        this.inter = inter;
        this.inter.setJoueurs(joueurs);
        this.inter.repaint();
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
    public void setPartieFinie(boolean fini)
    {
        estFini = fini;
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
     * Avance dans l'historique (refaire)
     **/
    public void avancerHistorique()
    {
        // Annule jusqu'a un joueur
        Configuration c = null;

        do
	    {
            c = historique.avance();
            if (c != null)
		    {
		
                // Restaure le score du joueur
                getConfiguration().getJoueurSurConfiguration().setScore(getConfiguration().getJoueurSurConfiguration().getScore() + getConfiguration().scoreCoupEffectue());


		
                int [] restePingouins = c.getNombrePingouinsParJoueur(joueurs);
                int totalPions = 0;
		
                for (int i = 0; i < restePingouins.length; i++)
                    totalPions += restePingouins[i];

                // 2 joueurs, 8 pions
                // 3 joueurs, 9 pions
                // 4 joueurs, 8 pions
                if (getMode() == ModeDeJeu.POSE_PINGOUIN && 
                    ((joueurs.length == 2 && totalPions == 8) ||
                     (joueurs.length == 3 && totalPions == 9) ||
                     (joueurs.length == 4 && totalPions == 8)))
			    {
                    setMode(ModeDeJeu.JEU_COMPLET);
			    }
                else if (getMode() == ModeDeJeu.JEU_COMPLET)
                    getConfiguration().getJoueurSurConfiguration().incrementNombreTuile();

		
		
                // Met à jour la configuration
                configurationCourante = c;
                setJoueurCourant(c.getJoueurSurConfiguration());
		    }
	    } 
        while (c != null && !(c.getJoueurSurConfiguration() instanceof JoueurHumain));

		
        // Stop le thread pour lui indique le changement de joueur
        ArbitreManager.instanceThread.interrupt();
		
        inter.repaint();
    }

    /**
     * Recule dans l'historique (annuler)
     **/
    public void reculerHistorique()
    {
        Configuration c = null;
        do
	    {
            c = historique.reculer();
            if (c != null)
		    {	
                // Met à jour la configuration
                configurationCourante = c;
                setJoueurCourant(c.getJoueurSurConfiguration());
		
                // Restaure le score du joueur
                getJoueurCourant().setScore(c.getScoreSurConfiguration());

                int [] restePingouins = c.getNombrePingouinsParJoueur(joueurs);
                int totalPions = 0;
		
                for (int i = 0; i < restePingouins.length; i++)
                    totalPions += restePingouins[i];

                // 2 joueurs, 8 pions
                // 3 joueurs, 9 pions
                // 4 joueurs, 8 pions
                if ((joueurs.length == 2 && totalPions < 8) ||
                    (joueurs.length == 3 && totalPions < 9) ||
                    (joueurs.length == 4 && totalPions < 8))
                    setMode(ModeDeJeu.POSE_PINGOUIN);
		
                if (getMode() == ModeDeJeu.JEU_COMPLET)
                    getConfiguration().getJoueurSurConfiguration().decrementNombreTuile();
		    }
	    }
        while(c != null && !(c.getJoueurSurConfiguration() instanceof JoueurHumain));
	
        // Stop le thread pour lui indique le changement de joueur
        ArbitreManager.instanceThread.interrupt();

        inter.repaint();
	
    }


    /**
     * Recule dans l'historique (annuler)
     **/
    public Configuration recommencer()
    {
        estFini = false;

        Configuration c = null;

        do
	    {
            c = historique.reculer();
            if (c != null)
		    {	
                // Met à jour la configuration
                configurationCourante = c;
                setJoueurCourant(c.getJoueurSurConfiguration());
		
                // Restaure le score du joueur
                getJoueurCourant().setScore(c.getScoreSurConfiguration());

                int [] restePingouins = c.getNombrePingouinsParJoueur(joueurs);
                int totalPions = 0;
		
                for (int i = 0; i < restePingouins.length; i++)
                    totalPions += restePingouins[i];

                // 2 joueurs, 8 pions
                // 3 joueurs, 9 pions
                // 4 joueurs, 8 pions
                if ((joueurs.length == 2 && totalPions < 8) ||
                    (joueurs.length == 3 && totalPions < 9) ||
                    (joueurs.length == 4 && totalPions < 8))
                    setMode(ModeDeJeu.POSE_PINGOUIN);
		
                if (getMode() == ModeDeJeu.JEU_COMPLET)
                    getConfiguration().getJoueurSurConfiguration().decrementNombreTuile();
		    }
	    }
        while(c != null);

        return getConfiguration();
    }


    /**
     * Serialize les données d'une partie
     **/
    private void writeObject(ObjectOutputStream out) throws IOException
    {
        // Largeur
        out.writeInt(getLargeur());
        // Hauteur
        out.writeInt(getHauteur());
        // Mode de jeu
        out.writeObject(getMode());
        
        // Nombre de joueurs
        out.writeInt(joueurs.length);

        // Liste des joueurs
        for (int i = 0; i < joueurs.length; i++)
	    {
            // Ecris la classe à part pour recharger plus tard
            out.writeObject(joueurs[i].getClass().getName());
            out.writeObject(joueurs[i]);
	    }
        
        // Tour du joueur en cours
        out.writeInt(getPosition(getJoueurCourant()));

        // Liste des configurations
        out.writeObject(historique);
    }

    /**
     * Charge les données à partir d'une chaine serialize
     **/
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    { 
        // Largeur
        setLargeur(in.readInt());
        // Hauteur
        setHauteur(in.readInt());
        // Mode de jeu
        setMode((ModeDeJeu)in.readObject());

        // Nombre de joueurs
        joueurs = new Joueur[in.readInt()];
        
        // Liste des joueurs
        for (int i = 0; i < joueurs.length; i++)
	    {
            // Charge le joueur en dynamic cast
            String type = (String)in.readObject();
            joueurs[i] =(Joueur)( Class.forName(type).cast(in.readObject()));
	    }
        
        // Tour du joueur en cours
        setJoueurCourant(getJoueurParPosition(in.readInt()));

        // Liste des configurations
        historique = (Historique)in.readObject();

        configurationCourante = historique.courante();
        signalStop = new Signal<Object>();
    }
}
