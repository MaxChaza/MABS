package fr.irit.ens.m1mabs.enumeration;

public enum TypeObjet 
{
	RECTANGLE("un rectangle"), 
	CARRE("un carré"), 
	TRIANGLE("un triangle"), 
	ELLIPSE("une ellipse"), 
	CERCLE("un cercle");
	
	private String nom;
	
	private TypeObjet(String nom)
	{
		this.nom = nom;
	}
	
	public String getNom()
	{
		return nom;
	}
}
