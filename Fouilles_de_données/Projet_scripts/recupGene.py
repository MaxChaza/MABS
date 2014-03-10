######################################################################################################################
# Récupération de tous les gènes permettant l'utilisation du programme String
######################################################################################################################

donneesGene= open("donneesGene.txt", "r") # Ouverture du fichier donneesGene.txt en lecture
nomGene= open("nomGene.txt", "w") # Ouverture du fichier nomGene.txt en écriture

for g in donneesGene.readlines(): # Lecture de chaque ligne du fichier donneesGene.txt
	lG=g.split('	') # Création d'une liste de chaine de caractères
	nomGene.write(lG[0]+"\n" ) # Ecriture du nom de chaque gène dans le fichier nomGene.txt
			
# On ferme les fichiers
nomGene.close()			
donneesGene.close()
