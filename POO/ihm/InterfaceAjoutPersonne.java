package fr.irit.ens.m1mabs.ihm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class InterfaceAjoutPersonne extends JDialog implements ActionListener
{
	JTextField csNom, csPrenom;
	InterfaceListePersonne ilp;
	
	public InterfaceAjoutPersonne(InterfaceListePersonne f)
	{
		super(f,"Ajout Personne",true);
		ilp = f;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Container c  = getContentPane();
		c.setLayout(new BorderLayout());

		JPanel panneauLabel = new JPanel(new GridLayout(2,1));
		panneauLabel.add(new JLabel("Nom : "));
		panneauLabel.add(new JLabel("Prénom : "));

		csNom = new JTextField(20);
		csPrenom = new JTextField(20);
		JPanel panneauTextField = new JPanel(new GridLayout(2,1));
		panneauTextField.add(csNom);
		panneauTextField.add(csPrenom);

		JButton boutonFermer = new JButton("Fermer");
		boutonFermer.addActionListener(this);
		boutonFermer.setActionCommand("Fermer");
		
		JButton boutonAjouter = new JButton("Ajouter");
		boutonAjouter.addActionListener(this);
		boutonAjouter.setActionCommand("Ajouter");
		
		JPanel panneauBouton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panneauBouton.add(boutonFermer);
		panneauBouton.add(boutonAjouter);
		
		c.add(panneauBouton,BorderLayout.SOUTH);
		c.add(panneauLabel,BorderLayout.WEST);
		c.add(panneauTextField,BorderLayout.CENTER);
		
		setLocation(1600,200);
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getActionCommand().equals("Fermer"))
		{
			dispose();
		}
		if(arg0.getActionCommand().equals("Ajouter"))
		{
			ilp.listePersonne.addElement(new Personne(csNom.getText(),csPrenom.getText()));
			dispose();
		}
	}
}
