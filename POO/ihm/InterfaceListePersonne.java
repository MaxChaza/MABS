package fr.irit.ens.m1mabs.ihm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class InterfaceListePersonne extends JFrame
{
	Vector<Personne> listePersonne;
	JList liste;
	
	public InterfaceListePersonne()
	{
		super("Liste Personne");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		listePersonne = new Vector<Personne>();
		liste = new JList(listePersonne);
		JButton boutonAjouter = new JButton("Ajouter Personne");
		boutonAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new InterfaceAjoutPersonne(InterfaceListePersonne.this);
				liste.updateUI();
			}
		});
		
		c.add(boutonAjouter,BorderLayout.SOUTH);
		c.add(new JScrollPane(liste),BorderLayout.CENTER);
		
		setBounds(1600,200,300,400);
		setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		new InterfaceListePersonne();
	}
}
