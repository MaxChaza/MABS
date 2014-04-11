package fr.irit.ens.m1mabs.tableau;

public class Min 
{
	int valeur;
	int indice;
	
	public Min(int v, int i)
	{
		valeur=v;
		indice=i;
	}
	
	public String toString()
	{
		return String.valueOf(valeur)+" "+String.valueOf(indice);
	}
}
