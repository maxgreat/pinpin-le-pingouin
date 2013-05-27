package Network;

import java.util.*;

public class Cmd
{
    protected NetworkCmd cmd;
    protected Destinataire qui;
    protected Type type;

    public static ArrayList<Cmd> informations;

    public Cmd(NetworkCmd cmd, Destinataire qui, Type type)
    {
	this.cmd = cmd;
	this.qui = qui;
	this.type = type;
    }

    public NetworkCmd getCmd()
    {
	return cmd;
    }

    public Destinataire getDestinataire()
    {
	return qui;
    }

    public Type getType()
    {
	return type;
    }


    /**
     * Initialise les données des paquets
     * Chaque paquet doit avoir ses informations
     **/
    public static void init()
    {
	System.out.println("Chargement des informations réseaux...");

	informations = new ArrayList<Cmd>();
	
	/**
	 * Client -> Server
	 **/
	informations.add(new Cmd(NetworkCmd.CONNECTION, Destinataire.MANAGER, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.CREER_PARTIE, Destinataire.MANAGER, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.REJOINDRE_IMMEDIATEMENT, Destinataire.MANAGER, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.CHARGER_PARTIE, Destinataire.MANAGER, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.LISTER_PARTIE, Destinataire.MANAGER, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.PONG, Destinataire.MANAGER, Type.CLIENT_TO_SERVEUR));

	informations.add(new Cmd(NetworkCmd.SAUVEGARDER_PARTIE, Destinataire.ARBITRE, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.ENVOYER_COUP, Destinataire.ARBITRE, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.ANNULER_COUP, Destinataire.ARBITRE, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.ACCEPTER_ANNULER, Destinataire.ARBITRE, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.REFAIRE_COUP, Destinataire.ARBITRE, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.ACCEPTER_REFAIRE, Destinataire.ARBITRE, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.REFUSER_ANNULER, Destinataire.ARBITRE, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.REFUSER_REFAIRE, Destinataire.ARBITRE, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.QUITTER_PARTIE, Destinataire.ARBITRE, Type.CLIENT_TO_SERVEUR));
	informations.add(new Cmd(NetworkCmd.DEMANDER_CONFIGURATION, Destinataire.ARBITRE, Type.CLIENT_TO_SERVEUR));

	/**
	 * Serveur -> Client
	 **/
	informations.add(new Cmd(NetworkCmd.LOGIN_OK, Destinataire.MANAGER, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.LOGIN_BAD, Destinataire.MANAGER, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.CREATION_OK, Destinataire.MANAGER, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.CREATION_MAUVAIS, Destinataire.MANAGER, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.CHARGEMENT_OK, Destinataire.MANAGER, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.CHARGEMENT_MAUVAIS, Destinataire.MANAGER, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.LISTE_PARTIE, Destinataire.MANAGER, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.PARTIE_PRETE, Destinataire.MANAGER, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.PING, Destinataire.MANAGER, Type.SERVEUR_TO_CLIENT));


	informations.add(new Cmd(NetworkCmd.SAUVEGARDER_OK, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.SAUVEGARDER_MAUVAIS, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.DEMANDER_COUP, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.COUP_MAUVAIS, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.COUP_OK, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.VEUT_ANNULER, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.VEUT_REFAIRE, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.DEMANDE_ANNULER_OK, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.DEMANDE_ANNULER_MAUVAIS, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.DACCORD_ANNULER, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.PARTIE_PRETE, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.NOUVELLE_CONFIGURATION, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.FIN_PARTIE, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.TOUR_JOUEUR, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));
	informations.add(new Cmd(NetworkCmd.COUP_JOUEUR, Destinataire.ARBITRE, Type.SERVEUR_TO_CLIENT));

	/**
	 * Communication Inter-Thread serveur
	 **/
    }
	
}
