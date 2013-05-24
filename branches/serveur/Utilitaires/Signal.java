package Utilitaires;

/**
 * Classe permettant d'envoyer des signaux
 * Inter-Threads avec gestion d'argument (classe T)
 **/
public class Signal<T> 
{
    T donnee;

    /**
     * Attend le signal et renvoit l'objet envoyé
     **/
    public synchronized T attendreSignal()
    {
        try
        {
            wait();
        }
        catch (InterruptedException e)
        {
            // Demande de fermeture, on abandonne le wait
            return null;
        }
    
        return donnee;
    }

    /**
     * Envoit le signal à tous
     **/
    public synchronized void envoyerSignal(T donnee)
    {
        this.donnee = donnee;
        notifyAll();
    }

    /**
     * Envoit le signal à tous
     **/
    public synchronized void envoyerSignal()
    {
        notifyAll();
    }
}
