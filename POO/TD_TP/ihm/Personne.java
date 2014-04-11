package fr.irit.ens.m1mabs.ihm;

public class Personne 
{
	String nom, prenom;
	
	public Personne(String n, String p)
	{
		nom=n;
		prenom=p;
	}
	
	public String toString()
	{
		return nom.toUpperCase()+" "+prenom;
	}
}
