package fr.irit.ens.m1mabs.biblio;

import java.util.Scanner;
import java.util.Vector;

public class Bibliotheque 
{
	Vector<Livre> listeLivre;
	Vector<Inscrit> listeInscrit;
	int nbMaximumEmprunt;
	
	public Bibliotheque()
	{
		listeLivre = new Vector<Livre>();
		listeLivre.addElement(new Livre("Couverture1", "Java : un jeu d'enfants", "M. Raynal", "Y a pas plus simple comme langage ....", 1632, Categories.ENSEIGNEMENT, Themes.ART));
		listeLivre.addElement(new Livre("Couverture2", "Les bisounours", "Babar", "Tout est beau dans le monde", 45, Categories.BD, Themes.HUMOUR));
		listeLivre.addElement(new Livre("Couverture3", "Le PSG : équipe du millénaire", "Riolo", "La meilleure équipe au monde depuis 40 ans ...", 674, Categories.MAGAZINE, Themes.SPORT));
		listeLivre.addElement(new Livre("Couverture4", "Pourquoi la France est-elle notée CCC- ?", "Anonyme", "Au fond du trou et commence à creuser ...", 85, Categories.MAGAZINE, Themes.POLITIQUE));
		listeInscrit = new Vector<Inscrit>();
		listeInscrit.addElement(new Inscrit("Photo1", "Dupont", "Marc", "Toulouse", "06.25.58.62.37", 27, Genre.HOMME));
		listeInscrit.addElement(new Inscrit("Photo2", "Durant", "Carole", "L'Union", "05.14.26.94.55", 34, Genre.FEMME));
		listeInscrit.addElement(new Inscrit("Photo3", "Martin", "Stéphanie", "Castanet", "09.65.31.25.39", 18, Genre.FEMME));
		listeInscrit.addElement(new Inscrit("Photo4", "Dubois", "Paul", "St Jean", "06.88.22.47.61", 54, Genre.HOMME));
		nbMaximumEmprunt = 2;
	}
	
	public void ajouterLivre()
	{
		Livre l = new Livre();
		listeLivre.addElement(l);
	}
	
	public void affichageListeLivre()
	{
		for(int i=0;i<listeLivre.size();i++)
		{
			Livre l = listeLivre.elementAt(i);
			System.out.println(i+"- "+l.titre+" ("+l.auteur+")");
		}
	}
	
	public void ajouterInscrit()
	{
		Inscrit i = new Inscrit();
		listeInscrit.addElement(i);
	}
	
	public void affichageListeInscrit()
	{
		for(int i=0;i<listeInscrit.size();i++)
		{
			Inscrit ins = listeInscrit.elementAt(i);
			System.out.println(i+"- "+ins.nom+" "+ins.prenom);
		}
	}
	
	public void afficheMenu()
	{
		int choix;
		do{
			System.out.println("---------------------------------------------");
			System.out.println("|           Menu bibliothèque            |");
			System.out.println("---------------------------------------------");
			System.out.println("Que souhaitez-vous faire ?");
			System.out.println("1- Afficher liste des livres");
			System.out.println("2- Ajouter un livre");
			System.out.println("3- Consulter un livre");
			System.out.println("4- Afficher liste des inscrits");
			System.out.println("5- Ajouter un inscrit");
			System.out.println("6- Consulter un inscrit");
			System.out.println("7- Emprunter un livre");
			System.out.println("8- Rendre un livre");
			System.out.println("0- QUITTER");
			Scanner sc = new Scanner(System.in);
			choix = Integer.parseInt(sc.nextLine());
			switch (choix) 
			{
				case 1:
				 	affichageListeLivre();
				break;
				case 2:
					ajouterLivre();
				break;
				case 3:
			 		affichageListeLivre();
			 		System.out.println("Quel livre souhaitez-vous consulter ? ");
					int choixLivre = Integer.parseInt(sc.nextLine());
					Livre l = listeLivre.elementAt(choixLivre);
					l.affichage();
				break;
				case 4:
					affichageListeInscrit();
					break;
				case 5:
					ajouterInscrit();
					break;
				case 6:
				 	affichageListeInscrit();
				 	System.out.println("Quel inscrit souhaitez-vous consulter ? ");
				 	int choixInscrit = Integer.parseInt(sc.nextLine());
					Inscrit i = listeInscrit.elementAt(choixInscrit);
					i.affichage();
					break;
				case 7:
				 	// Choisir l'inscrit
					affichageListeInscrit();
					System.out.println("Quel inscrit souhaite emprunter un livre ? ");
				 	choixInscrit = Integer.parseInt(sc.nextLine());
					i = listeInscrit.elementAt(choixInscrit);
					// Vérifier que l'inscrit peut emprunter un nouveau livre
					if(i.livresEmpruntes.size() < nbMaximumEmprunt)
					{
						// Choisir le livre
						affichageListeLivre();
				 		System.out.println("Quel livre souhaitez-vous emprunter ? ");
				 		choixLivre = Integer.parseInt(sc.nextLine());
						l = listeLivre.elementAt(choixLivre);
						//Vérifier si le livre n'est pas déjà emprunté
						if(l.idInscrit == -1)
						{
							l.idInscrit = choixInscrit;
							i.livresEmpruntes.addElement(l);	
						}
						else
							System.out.println("Désolé, ce livre est déjà emprunté.");
					}
					else
						System.out.println("Désolé, "+i.nom+" "+i.prenom+" a déjà "+nbMaximumEmprunt+" livres.");
					break;
				case 8:
					affichageListeInscrit();
					System.out.println("Quel inscrit souhaite rendre un livre ? ");
				 	choixInscrit = Integer.parseInt(sc.nextLine());
					i = listeInscrit.elementAt(choixInscrit);
					
					i.afficheLivresEmpruntes();
					System.out.println("Quel livre souhaitez-vous rendre ? ");
			 		choixLivre = Integer.parseInt(sc.nextLine());
					Livre li = i.livresEmpruntes.remove(choixLivre);
					li.idInscrit = -1;
					break;
				case 0:
					System.out.println("Merci, au revoir ...");
				break;
				default:
						System.out.println("Votre choix est incorrect");
				break;
			}
		}while(choix!=0);
	}
	
	
	public static void main(String[] args) 
	{
		Bibliotheque bib = new Bibliotheque();
		bib.afficheMenu();
	}
}
