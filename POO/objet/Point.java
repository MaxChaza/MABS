package fr.irit.ens.m1mabs.objet;

public class Point {
	int x, y;
	
	Point()
	{
		x=0;
		y=0;
	}
	
	public Point(Point p)
	{
		x = p.x;
		y = p.y;
	}
	
	Point(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public boolean equals(Point p)
	{
		if(this.x==p.x && this.y==p.y)
			return true;
		
		return false;
	}
	
	void deplacer(int deltaX, int deltaY)
	{
		x += deltaX;
		y += deltaY;
	}
}
