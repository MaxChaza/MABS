package fr.irit.ens.m1mabs.heritage;

public class Cercle extends FormeGeometrique
{
	int rayon;
	
	public Cercle(String nom, int x, int y, int r)
	{
		super(nom,x,y);
		rayon = r;
	}
	
	public double calculSurface()
	{
		return Math.PI*rayon*rayon;
	}
	
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		s.append(super.toString());
		s.append("Rayon : ");
		s.append(rayon);
		s.append("\n");
		return s.toString();
	}
}
