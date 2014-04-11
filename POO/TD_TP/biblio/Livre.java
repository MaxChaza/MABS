package fr.irit.ens.m1mabs.biblio;

import java.util.Scanner;

public class Livre 
{
	String couverture, titre, auteur, resume;
	int nbPages;
	Categories categorie;
	Themes[] theme;
	int idInscrit;
	
	public Livre()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir la couverture : ");
		couverture = sc.nextLine();
		System.out.println("Veuillez saisir le titre : ");
		titre = sc.nextLine();
		System.out.println("Veuillez saisir l'auteur : ");
		auteur = sc.nextLine();
		System.out.println("Veuillez saisir le résumé : ");
		resume = sc.nextLine();
		System.out.println("Veuillez saisir le nombre de pages : ");
		nbPages = Integer.parseInt(sc.nextLine());
		
		System.out.println("Quelle est la catégorie du livre ?");
		for(Categories c:Categories.values())
			System.out.println((c.ordinal()+1)+"- "+c);
		int cate = Integer.parseInt(sc.nextLine());
		Categories[] tabCat = Categories.values();
		categorie = tabCat[cate-1];
		
		theme = new Themes[3];
		System.out.println("Quels sont les thèmes du livre ?");
		for(Themes t:Themes.values())
			System.out.println((t.ordinal()+1)+"- "+t);
		System.out.println("Vous pouvez saisir jusqu'à 3 thèmes");
		System.out.println("Lorsque vous avez fini, tapez 0");
		int th = -1;
		int id = 0;
		Themes[] tabTheme = Themes.values();
		while(th!=0 && id<3)
		{
			th = Integer.parseInt(sc.nextLine());
			if(th!=0)
			{
				theme[id] = tabTheme[th-1];
				id++;
			}
		}
		idInscrit=-1;
	}
	
	public Livre(String c, String t, String a, String r, int n, Categories cat, Themes th)
	{
		couverture = c;
		titre = t;
		auteur = a;
		resume = r;
		nbPages = n;
		categorie = cat;
		theme = new Themes[3];
		theme[0] = th;
		idInscrit=-1;
	}
	
	public void affichage()
	{
		System.out.println("Titre : "+titre);
		System.out.println("Auteur : "+auteur);
		System.out.println("Résumé : "+resume);
		System.out.println("Nombre de pages : "+nbPages);
		System.out.println("Catégorie : "+categorie);
		System.out.print("Thème(s) : ");
		for(int i=0;i<theme.length;i++)
			if(theme[i]!=null)
				System.out.print(theme[i]+" ");
		System.out.println();
		if(idInscrit>=0)
			System.out.println("Le livre est emprunté");
		else
			System.out.println("Le livre n'est pas emprunté");
	}
}