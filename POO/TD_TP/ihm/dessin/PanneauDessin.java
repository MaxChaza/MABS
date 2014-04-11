package fr.irit.ens.m1mabs.ihm.dessin;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class PanneauDessin extends JComponent
{
	Vector<Trace> listeTrace;
	Trace trace;
	
	public PanneauDessin()
	{
		super();
		trace = null;
		listeTrace = new Vector<Trace>();
		
		addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				listeTrace.addElement(trace);
				trace = null;
				repaint();
			}
			public void mousePressed(MouseEvent arg0) {
				trace = new Trace();
				int x = arg0.getX();
				int y = arg0.getY();
				trace.addPoint(x, y);
				repaint();
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() 
		{
			public void mouseDragged(MouseEvent arg0) 
			{
				int x = arg0.getX();
				int y = arg0.getY();
				trace.addPoint(x, y);
				repaint();
			}
		});
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		if(trace!=null)
		{
			g2.setColor(Color.red);
			trace.paint(g2);
		}
		g2.setColor(Color.black);
		for(int j=0;j<listeTrace.size();j++)
		{
			Trace t = listeTrace.elementAt(j);
			t.paint(g2);
		}
	}
	
	public static void main(String[] args) 
	{
		JFrame f = new JFrame("Exemple dessin");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = f.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(new PanneauDessin(),BorderLayout.CENTER);
		f.setBounds(200, 200, 500, 500);
		f.setVisible(true);
	}
}
