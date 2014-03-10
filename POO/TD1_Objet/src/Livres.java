import java.util.Scanner;

public class Livres {
	
	String couverture,titre,auteur,resume;
	int nbPages;
	Categories categorie;
	Themes[] theme; // pour dire que c'est un tableau on rajoute les crochets
	
	
	public Livres() // créer un constructeur pour pas avoir à tout refaire si quelque chose change
	{
		Scanner sc= new Scanner(System.in);
		System.out.println("Veuillez saisir la couverture: ");
		couverture = sc.nextLine();
		System.out.println("Veuillez saisir le titre: ");
		titre = sc.nextLine();
		System.out.println("Veuillez saisir l'auteur: ");
		auteur = sc.nextLine();
		System.out.println("Veuillez saisir le résumé: ");
		resume = sc.nextLine();
		System.out.println("Veuillez saisir le nombre de pages: ");
		nbPages = Integer.parseInt(sc.nextLine());	
		
		System.out.println("Veuillez saisir la catégorie du livre: ");
		for(Categories c:Categories.values()) // je veux parcourir l'ensemble des valeurs de categorie
			System.out.println((c.ordinal()+1)+"- "+c);
		int cate = Integer.parseInt(sc.nextLine());
		Categories [] tabCat = Categories.values(); //on créer un tableau
		categorie = tabCat[cate-1]; // on fait -1 pour se repositionner à la bonne valeur
		
		theme = new Themes[3]; // on crée un tableau qui a 3 cellules et dans chaque cellule on va pouvoir mettre un élément de Themes
		System.out.println("Veuillez saisir le(s) thème(s) du livre: ");
		for(Themes t:Themes.values())
			System.out.println((t.ordinal()+1)+"- "+t);
		System.out.println("Vous pouvez saisir jusqu'à 3 thèmes");
		System.out.println("Lorsque vous avez fini, tapez 0");
		int th = Integer.parseInt(sc.nextLine());
		int id = 0;
		Themes[] tabTheme = Themes.values();
		while (th!=0 && id<3)
		{
			theme[id] = tabTheme[th-1];
			id++;
			th = Integer.parseInt(sc.nextLine());
		}	

	}
	
	
	public Livres(String c, String t, String a, String r, int n)
	{
		couverture = c;
		auteur = a;
		resume = r;
		titre = t;
		nbPages = n;
	}
	
	public static void main(String[] args)
	{
		Livres livre = new Livres();
			
	}
	
	
	
	
	
	

	
}
