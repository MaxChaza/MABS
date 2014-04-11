package fr.irit.ens.m1mabs.ihm.timer;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Compteur extends JFrame implements ActionListener
{
	int compteur;
	JButton boutonStart, boutonStop;
	JLabel label;
	Timer t;
	
	public Compteur()
	{
		super("Compteur");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		t = new Timer(1000, this);
		t.setActionCommand("Timer");
		
		Container c = getContentPane();
		c.setLayout(new GridLayout(2,1));
		
		boutonStart = new JButton("Start");
		boutonStart.addActionListener(this);
		boutonStart.setActionCommand("Start");
		
		boutonStop = new JButton("Stop");
		boutonStop.addActionListener(this);
		boutonStop.setActionCommand("Stop");
		boutonStop.setEnabled(false);
		
		JPanel panneauBouton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panneauBouton.add(boutonStart);
		panneauBouton.add(boutonStop);
	
		compteur = 0;
		label = new JLabel(String.valueOf(compteur),JLabel.CENTER);
		c.add(label);
		c.add(panneauBouton);
		
		setLocation(300,300);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		new Compteur();
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		String commande = arg0.getActionCommand();
		if(commande.equals("Start"))
		{
			compteur=0;
			label.setText(String.valueOf(compteur));
			t.start();
			boutonStart.setEnabled(false);
			boutonStop.setEnabled(true);
		}
		if(commande.equals("Stop"))
		{
			t.stop();
			boutonStart.setEnabled(true);
			boutonStop.setEnabled(false);	
		}
		if(commande.equals("Timer"))
		{
			compteur++;
			label.setText(String.valueOf(compteur));
		}
	}
}
