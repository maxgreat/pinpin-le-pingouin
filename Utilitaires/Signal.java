package Utilitaires;

/**
 * Classe permettant d'envoyer des signaux
 * Inter-Threads avec gestion d'argument (classe T)
 **/
public class Signal<T> 
{
	T donnee;

	public Signal() {
		this.donnee = null;
	}

	/**
	 * Attend le signal et renvoit l'objet envoyé
	 **/
	public synchronized T attendreSignal()
			      {
				      if (this.donnee == null)
					      try
					      {
						      wait();
						      T tmp = this.donnee;
						      this.donnee = null;
						      return tmp;
					      }
					      catch (InterruptedException e)
					      {
						      // Demande de fermeture, on abandonne le wait
						      return null;
					      }
				      T tmp = this.donnee;
				      this.donnee = null;
				      return tmp;
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
