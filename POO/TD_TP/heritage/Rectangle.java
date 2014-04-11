package fr.irit.ens.m1mabs.heritage;

public class Rectangle extends FormeGeometrique
{
	int longueur, hauteur;
	
	public Rectangle()
	{
		this("",0,0,0,0);
	}
	
	public Rectangle(String nom, int x, int y, int l, int h)
	{
		super(nom, x,y);
//		Equivalent à :
//		super.nom = nom;
//		super.x = x;
//		super.y = y;
		this.longueur = l;
		this.hauteur = h;
	}
	
	public double calculSurface()
	{
		return longueur*hauteur;
	}
	
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		s.append(super.toString());
		s.append("Longueur : ");
		s.append(longueur);
		s.append("\n");
		s.append("Hauteur : ");
		s.append(hauteur);
		s.append("\n");
		return s.toString();
	}
}
