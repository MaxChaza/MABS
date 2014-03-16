package fr.irit.ens.m1mabs.objet;


class Rectangle 
{
	Point p;
	int longueur, hauteur;
	
	Rectangle()
	{
		p = new Point();
		longueur=0;
		hauteur=0;
	}

	Rectangle(Rectangle r)
	{
		p = new Point(r.p);
		this.longueur=r.longueur;
		this.hauteur=r.hauteur;
	}
	
	Rectangle(int longueur, int hauteur)
	{
		p = new Point();
		this.longueur=longueur;
		this.hauteur=hauteur;
	}
	
	Rectangle(int x, int y, int longueur, int hauteur)
	{
		p = new Point(x,y);
		this.longueur=longueur;
		this.hauteur=hauteur;
	}

	public boolean equals(Rectangle r)
	{
		return ((longueur == r.longueur)&&(hauteur==r.hauteur)&&(p.equals(r.p)));
//		Identique à :
//		boolean b = false;
//		if((longueur == r.longueur)&&(hauteur==r.hauteur)&&(p.equals(r.p)))
//			b=true;
//		return b;
	}
	
	public boolean contains(Point p2)
	{
		if(this.p.x<p2.x 
		&& this.p.x+this.longueur>p2.x 
		&& this.p.y<p2.y 
		&& this.p.y+this.hauteur>p2.y)
			return true;
		return false;
	}
	
	int calculSurface()
	{
		int surface = longueur * hauteur;
		return surface;
	}
	
	int calculPerimetre()
	{
		return (longueur + hauteur)*2;
	}
	
	void deplacer(int deltaX, int deltaY)
	{
		p.deplacer(deltaX, deltaY);
	}
	
	void affichage()
	{
		System.out.println("("+p.x+","+p.y+") "+longueur+"-"+hauteur);
	}
	
	public static void main(String[] args)
	{
		/*Rectangle rect1;
		rect1 = new Rectangle();
		rect1.affichage();
		rect1.deplacer(10, 0);
		rect1.affichage();*/
		Rectangle rect2 = new Rectangle(10,10,30,50);
		rect2.affichage();
		Rectangle rect3 = rect2;
		rect3.deplacer(20, 20);
		rect3.affichage();
		rect2.affichage();
	}
}
