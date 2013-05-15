package Arbitre;
import java.util.*;

/**
 * Pile thread-safe basé sur un ArrayList
 **/
public class Pile<T>
{
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
}
