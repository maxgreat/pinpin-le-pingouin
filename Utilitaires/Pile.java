package Utilitaires;
import java.util.*;
import java.io.*;

/**
 * Pile thread-safe basé sur un ArrayList
 **/
public class Pile<T> implements Serializable
{
    public static final long serialVersionUID = 1L;
    ArrayList<T> pile;

    public Pile()
    {
        pile = new ArrayList<T>();
    }

    public synchronized T pop()
    {
        return pile.remove(pile.size() - 1);
    }

    public synchronized void push(T e)
    {
        pile.add(e);
    }

    public synchronized T peek()
    {
        return pile.get(pile.size() - 1);
    }

    public synchronized int size()
    {
        return pile.size();
    }

    public synchronized T remove(int index)
    {
        return pile.remove(index);
    }

    public synchronized ListIterator<T> iterator()
    {
        return pile.listIterator();
    }

    /**
     * Serialize les données de la pile
     **/
    private void writeObject(ObjectOutputStream out) throws IOException
    {
        out.writeObject(pile);
    }

    /**
     * Charge les données à partir d'une chaine serialize
     **/
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    { 
        pile = (ArrayList<T>)in.readObject();
    }
}
