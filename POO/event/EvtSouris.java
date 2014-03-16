package fr.irit.ens.m1mabs.event;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import fr.irit.ens.triangle.Vecteur;

public class EvtSouris extends JFrame implements ActionListener
{
	JLabel compteur;
	Vector<ActionListener> listeEcouteur;
	
	public EvtSouris()
	{
		super("Exo Souris");
	
		listeEcouteur = new Vector<ActionListener>();
		
		AddActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(new GridLayout(3,1));
	
		compteur = new JLabel("",JLabel.CENTER);
		compteur.setBorder(new LineBorder(Color.RED));
		compteur.setFont(new Font(Font.SERIF,Font.BOLD,32));
		compteur.addMouseListener(new MouseAdapter() {
			
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				compteur.setBorder(new LineBorder(Color.RED));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				compteur.setBorder(new LineBorder(Color.GREEN));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ActionEvent action = new ActionEvent(compteur, 0, "eventLabel");
				for(int i=0;i<listeEcouteur.size();i++)
				{
					ActionListener al = listeEcouteur.elementAt(i);
					al.actionPerformed(action);
				}
			}
		});
		
		
		c.add(new JPanel());
		c.add(compteur);
		
		setBounds(1600,100,600,600);
		setVisible(true);
	}
	
	public void AddActionListener(ActionListener a)
	{
		listeEcouteur.add(a);
	}
	
	public static void main(String[] args) 
	{
		EvtSouris evt = new EvtSouris();
		Compteur c = new Compteur();
		evt.AddActionListener(c);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("ok");
		
	}
}
