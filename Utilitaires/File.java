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
	System.out.println("push");
	file.add(object);
	notifyAll();
    }

    /**
     * Cas particulier de clean sur les message
     **/
    public synchronized void clean()
    {
	file = new ArrayList<T>();
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
		return null;
            }
	}

	T resultat = file.get(0);
	file.remove(0);

        return resultat;
    }

    public synchronized int size()
    {
	return file.size();
    }
				    
}
	
