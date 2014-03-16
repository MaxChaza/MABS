package fr.irit.ens.m1mabs.event;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Compteur extends JFrame implements ActionListener
{
	int cpt;
	JLabel compteur;
	
	public Compteur()
	{
		super("Compteur");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
	
		cpt = 0;
		compteur = new JLabel(String.valueOf(cpt),JLabel.CENTER);
		
		JPanel panelBouton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton boutonMoins = new JButton("-");
		boutonMoins.addActionListener(this);
		boutonMoins.setActionCommand("-");
		JButton boutonPlus = new JButton("+");
		boutonPlus.addActionListener(this);
		boutonPlus.setActionCommand("+");
		panelBouton.add(boutonMoins);
		panelBouton.add(boutonPlus);

		c.add(panelBouton,BorderLayout.SOUTH);
		c.add(compteur,BorderLayout.CENTER);
		
		setBounds(1600,100,200,200);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		String commande = arg0.getActionCommand();
		if(commande.equals("-"))
			cpt--;
		if(commande.equals("+"))
			cpt++;
		if(commande.equals("eventLabel"))
			cpt+=5;
		compteur.setText(String.valueOf(cpt));
	}
	
	public static void main(String[] args) 
	{
		new Compteur();
	}
}
