package fr.irit.ens.m1mabs.biblio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class EcouterBoutonParcourir implements ActionListener
{
	String nom;
	JTextField cs;
	
	public EcouterBoutonParcourir(String nom, JTextField c)
	{
		this.nom = nom;
		cs=c;
		Object o = null;
		
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		cs.setText("ok");
		System.out.println("Bien reçu par "+nom);
	}
}
