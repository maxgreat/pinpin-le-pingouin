package Arbitre;

public class Case
{
    Etat e;

    public Case(Etat e)
    {
	this.e = e;
    }

    public boolean estLibre()
    {
	return e == Etat.LIBRE;
    }

    public boolean estPleine()
    {
	return e == Etat.PLEINE;
    }

    public boolean estPoison()
    {
	return e == Etat.POISON;
    }

    public Etat etat()
    {
	return this.e;
    }
}
