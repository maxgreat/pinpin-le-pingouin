package Arbitre;

public class Coup
{
    protected int x;
    protected int y;

    public Coup(int x , int y)
    {
	this.x = x;
	this.y = y;
    }

    public void setX(int x)
    {
	this.x = x;
    }

    public void setY(int y)
    {
	this.y = y;
    }
    
    public int getX()
    {
	return x;
    }

    public int getY()
    {
	return y;
    }
}
