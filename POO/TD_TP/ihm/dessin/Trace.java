package fr.irit.ens.m1mabs.ihm.dessin;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Vector;

public class Trace 
{
	Vector<Point2D.Double> listePoint;
	
	public Trace()
	{
		listePoint = new Vector<Point2D.Double>();
	}
	
	public void addPoint(int x, int y)
	{
		listePoint.addElement(new Point2D.Double(x,y));
	}
	
	public int size()
	{
		return listePoint.size();
	}
	
	public Point2D.Double getPoint(int i)
	{
		return listePoint.elementAt(i);
	}
	
	public void paint(Graphics2D g2)
	{
		for(int i=1;i<size();i++)
		{
			Point2D.Double p1 = getPoint(i-1);
			Point2D.Double p2 = getPoint(i);
			Line2D.Double l = new Line2D.Double(p1,p2);
			g2.draw(l);
		}	
	}
}
