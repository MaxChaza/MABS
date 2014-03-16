package fr.irit.ens.m1mabs.biblio;

import java.util.Scanner;
import java.util.Vector;

public class Inscrit 
{
	String photo, nom, prenom, adresse, telephone;
	int age;
	Genre genre;
	Vector<Livre> livresEmpruntes;
	
	public Inscrit(String ph, String n, String pr, String a, String t, int age, Genre g)
	{
		photo = ph;
		nom = n;
		prenom = pr;
		adresse = a;
		telephone = t;
		genre = g;
		livresEmpruntes = new Vector<Livre>();
	}
	
	public Inscrit()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir le nom de la photo : ");
		photo = sc.nextLine();
		System.out.println("Veuillez saisir le nom : ");
		nom = sc.nextLine();
		System.out.println("Veuillez saisir le prénom : ");
		prenom = sc.nextLine();
		System.out.println("Veuillez saisir l'adresse : ");
		adresse = sc.nextLine();
		System.out.println("Veuillez saisir le telephone : ");
		telephone = sc.nextLine();
		System.out.println("Veuillez saisir l'age : ");
		age = Integer.parseInt(sc.nextLine());
		
		System.out.println("Êtes-vous ");
		for(Genre g:Genre.values())
			System.out.println((g.ordinal()+1)+"- "+g);
		int g = Integer.parseInt(sc.nextLine());
		Genre[] tabGenre = Genre.values();
		genre = tabGenre[g-1];

		livresEmpruntes = new Vector<Livre>();
	}
	
	public void afficheLivresEmpruntes()
	{
		System.out.println("Liste des livres empruntés : ");
		for(int i=0;i<livresEmpruntes.size();i++)
		{
			Livre l =  livresEmpruntes.elementAt(i);
			System.out.println("- "+l.titre);
		}
	}
	
	public void affichage()
	{
		System.out.println("Nom : "+nom);
		System.out.println("Prénom : "+prenom);
		System.out.println("Adresse : "+adresse);
		System.out.println("Téléphone : "+telephone);
		afficheLivresEmpruntes();
	}
}
