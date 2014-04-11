package fr.irit.ens.m1mabs.biblio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class InterfaceAjoutLivre extends JFrame implements ActionListener
{
	JTextField csCouverture;
	
	public InterfaceAjoutLivre()
	{
		super("Ajouter livre");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		JLabel labelCouverture = new JLabel("Couverture");
		csCouverture = new JTextField(20);
		JButton boutonParcourir = new JButton("Parcourir");
		JButton boutonParcourir2 = new JButton("Parcourir 2");

		boutonParcourir2.setActionCommand("bouton2");
		boutonParcourir2.addActionListener(this);
		boutonParcourir2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				csCouverture.setText("Coucou");
			}
		});

		boutonParcourir.setActionCommand("bouton1");
		boutonParcourir.addActionListener(this);
		EcouterBoutonParcourir ebp1 = new EcouterBoutonParcourir("Ecouteur 1",csCouverture);
		boutonParcourir.addActionListener(ebp1);
		EcouterBoutonParcourir ebp2 = new EcouterBoutonParcourir("Ecouteur 2",csCouverture);
		boutonParcourir.addActionListener(ebp2);
		
		Box ligne1 = new Box(BoxLayout.X_AXIS);
		ligne1.add(labelCouverture);
		ligne1.add(csCouverture);
		ligne1.add(boutonParcourir);
		ligne1.add(boutonParcourir2);
		
		Box ligne2 = new Box(BoxLayout.X_AXIS);
		JLabel labelCategorie = new JLabel("Catégorie");
		ligne2.add(labelCategorie);
		ButtonGroup bg = new ButtonGroup();
		for(Categories cate : Categories.values())
		{
			JRadioButton rb = new JRadioButton(cate.toString());
			ligne2.add(rb);
			bg.add(rb);
		}
		
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		c.add(ligne1);
		c.add(ligne2);
		
		setLocation(1600, 100);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		new InterfaceAjoutLivre();
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getActionCommand().equals("bouton1"))
			System.out.println("Bien reçu par l'interface");
		if(arg0.getActionCommand().equals("bouton2"))
			System.out.println("Envoyé par le bouton 2");
	}

}
