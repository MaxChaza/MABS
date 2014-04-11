package fr.irit.ens.m1mabs.heritage;

import java.util.Vector;

public abstract class FormeGeometrique 
{
	int x, y;
	String nom;
	
	public FormeGeometrique(String n, int x, int y)
	{
		this.nom = n;
		this.x = x;
		this.y = y;
	}
	
	public void deplacer(int deltaX, int deltaY)
	{
		x+= deltaX;
		y+= deltaY;
	}
	
	public abstract double calculSurface();
	
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		s.append("Nom : ");
		s.append(nom);
		s.append("\n");
		s.append("Coordonnées du centre : ");
		s.append(x);
		s.append(", ");
		s.append(y);
		s.append("\n");
		return s.toString();
	}
	
	public static void main(String[] args) 
	{
		Vector<FormeGeometrique> listeForme = new Vector<FormeGeometrique>();
		listeForme.addElement(new Rectangle("R1", 50, 50, 100, 20));
		listeForme.addElement(new Cercle("C1", 20, 20, 15));
		
		for(int i=0;i<listeForme.size();i++)
		{
			FormeGeometrique fg = listeForme.elementAt(i);
			fg.deplacer(10,10);
			System.out.println("Forme "+fg.nom+" centrée en "+fg.x+","+fg.y);
			System.out.println("Surface de "+fg.nom+" : "+fg.calculSurface());
			System.out.println(fg);
		}
	}
}
