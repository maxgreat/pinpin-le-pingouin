package Arbitre;

import java.io.*;

public class Coup implements Serializable
{
    protected int xDepart;
    protected int yDepart;
    protected int xArrivee;
    protected int yArrivee;
	
    public Coup(int xDepart, int yDepart,int xArrivee, int yArrivee)
    {
        this.xDepart = xDepart;
	this.yDepart = yDepart;
	this.xArrivee = xArrivee;
	this.yArrivee = yArrivee;
    }

    public void setXDepart(int x)
    {
	this.xDepart = x;
    }
    public void setYDepart(int y)
    {
	this.yDepart = y;
    }	


    public void setXArrivee(int x)
    {
	this.xArrivee = x;
    }	

    public void setYArrivee(int y)
    {
	this.yArrivee = y;
    }
    
    public int getXDepart()
    {
	return xDepart;
    }
    public int getYDepart()
    {
	return yDepart;
    }
    
    public int getXArrivee()
    {
	return xArrivee;
    }
    public int getYArrivee()
    {
	return yArrivee;
    }

    public String toString()
    {
	return "("+getXDepart()+", "+getYDepart()+") -> ("+getXArrivee()+", "+getYArrivee()+")";
    }
}
