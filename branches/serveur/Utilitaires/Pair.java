package Utilitaires;


public class Pair<T, U>
{
    public T first;
    public U second;

    public Pair(T first, U second)
    {
	this.first = first;
	this.second = second;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object other)
    {
	if (other instanceof Pair<?, ?>)
	{
	    Pair<T, U> ot = (Pair<T, U>)other;

	    return ot.first == this.first;
	}

	return false;
    }
}

