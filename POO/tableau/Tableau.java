package fr.irit.ens.m1mabs.tableau;

import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Tableau 
{
	int[] tab;
	
	public Tableau()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir le nombre d'éléments que vous souhaitez :");
		int n = Integer.parseInt(sc.nextLine());
		tab = new int[n];
		for(int i=0;i<n;i++)
		{
			System.out.println("Veuillez saisir l'élément "+(i+1));
			tab[i] = Integer.parseInt(sc.nextLine());
		}
	}
	
	public Tableau(int n)
	{
		Random r = new Random();
		tab = new int[n];
		for(int i=0;i<n;i++)
		{
			tab[i] = r.nextInt(100)+1;
		}
	}
	
	public Tableau(Tableau t)
	{
		tab = t.tab.clone();
	}
	
	public void inserer(int i, int j)
	{
		if(i>j)
		{
			int x = tab[i];
			for(int id=i-1;id>=j;id--)
				tab[id+1]=tab[id];
			tab[j]=x;
		}
	}
	
//	public int min(int id)
//	{
//		int min = tab[id];
//		int idMin = id;
//		for(int i=id;i<tab.length;i++)
//		{
//			if(min>tab[i])
//			{
//				min=tab[i];
//				idMin=i;
//			}
//		}
//		
//		return idMin;
//	}
	
	public void min(Min m)
	{
		for(int i=m.indice;i<tab.length;i++)
		{
			if(m.valeur>tab[i])
			{
				m.valeur=tab[i];
				m.indice=i;
			}
		}
	}
	
	public void triSelection()
	{
		for(int i=0;i<tab.length-1;i++)
		{
			Min m = new Min(tab[i],i);
			System.out.println("-----------------------------------");
			System.out.println("Avant : "+m);
			min(m);
			System.out.println("Après : "+m);
			inserer(m.indice, i);
		}
	}
	
	public String toString()
	{
		StringBuffer buf = new StringBuffer("Les éléments du tableau sont :");
		for(int i=0;i<tab.length;i++)
		{
			buf.append(" ");
			buf.append(tab[i]); 
		}
		return buf.toString();
	}
	
	public static void main(String[] args) 
	{
		Tableau t = new Tableau(10);
		System.out.println("--- Tableau initial");
		System.out.println(t);
		t.triSelection();
		System.out.println("--- Tableau après tri");
		System.out.println(t);
	}
}
