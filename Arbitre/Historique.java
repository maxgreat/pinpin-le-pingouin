package Arbitre;
import java.util.*;

public class Historique
{
    Pile<Configuration> pileAvant;
    Pile<Configuration> pileApres;

    int limiteHistorique;

    /**
     * Constructeur de la classe historique
     * limiteHistorique et la profondeur maximum de l'historique
     **/
    public Historique(int limiteHistorique)
    {
        pileAvant = new Pile<Configuration>();
        pileApres = new Pile<Configuration>();

        this.limiteHistorique = limiteHistorique;
    }

    /**
     * Ajout d'un élément dans l'historique
     * Avec gestion de la taille max
     **/
    public void ajout(Configuration config)
    {
        // Libère un espace si la pile d'avant est pleine
        // if (pileAvant.size() >= limiteHistorique)
        //     libererPileEspace();

        // Push sur la pile d'avant et vide la pile d'après
        pileAvant.push(config);
        pileApres = new Pile<Configuration>();
    }

    /**
     * Avance dans l'historique
     * Retourne Nil si l'on ne peut pas avancer dans l'historique
     **/
    public Configuration avance()
    {
        if (!peutAvancer())
            return null;

        Configuration c = pileApres.pop();
        pileAvant.push(c);

        return pileAvant.peek();
    }


    /**
     * Recule dans l'historique
     * Retourne Nil si l'on ne peut pas reculer dans l'historique
     **/
    public Configuration reculer()
    {
        if (!peutReculer())
            return null;

        Configuration c = pileAvant.pop();
        pileApres.push(c);

        return pileAvant.peek();
    }


    /**
     * Prédicat pour savoir si on peut avancer
     **/
    public boolean peutAvancer()
    {
        return pileApres.size() > 0;
    }

    /**
     * Prédicat pour savoir si on peut reculer 
     **/
    public boolean peutReculer()
    {
        return pileAvant.size() > 1;
    }

    /**
     * Libère un espace dans la pile d'avant
     * Suppose que la pile n'est pas vide
     **/
    protected void libererPileEspace()
    {
        pileAvant.remove(0);
    }

    /**
     * Récupère un itérateur sur l'historique
     **/
    public ListIterator<Configuration> getIterateur()
    {
        return pileAvant.iterator();
    }
}
