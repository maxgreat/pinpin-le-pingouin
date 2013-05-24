package Utilitaires;

import java.util.*;

/**
 * File ThreadSave 
 **/
public class File<T>
{
    ArrayList<T> file;

    public File()
    {
	file = new ArrayList<T>();
    }

    public synchronized void push(T object)
    {
	file.add(object);
	notifyAll();
    }

    public synchronized T pull()
    {
	while (file.size() == 0)
	{
            try 
	    {
                wait();
            }
	    catch (InterruptedException e) 
	    {
            }
	}

	T resultat = file.get(0);
	file.remove(0);

        notifyAll();
        return resultat;
    }
}
	
