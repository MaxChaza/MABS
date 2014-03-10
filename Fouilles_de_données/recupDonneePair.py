########################################################################################################################################
# Script permettant la création d'une liste de paires de gènes avec leurs caractéristiques 
# Sur chaque ligne : Nom, distance, taile des deux gènes concaténés, si le brin est positif, score de string, si elle forme un opéron 
########################################################################################################################################

donneesGene= open("donneesGene.txt", "r")# Ouverture du fichier donneesGene.txt en lecture
donneesPair= open("donneesPair.txt", "w")# Ouverture du fichier donneesPair.txt en écriture
donneesString= open("donneesString.txt", "r")# Ouverture du fichier donneesString.txt en lecture qui contient le score de string pour chaque paire de gènes
# Header
donneesPair.write("Nom"+"\t" + "Distance" + '\t' + "TailleDeuxGene" + '\t' + "Brin Positif?" + '\t' + "Score de string" + '\t' + "Operon" +"\n" )

########################################################################################################################################
#Calcul de la moyenne des score String
somme=0.0
i=0.0
for g in donneesString.readlines():# Lecture de chaque ligne du fichier donneesString.txt
	lG=g.split('	') # Création d'une liste de chaine de caractères
	somme+=float(lG[14]) # Somme de tous les scores
	i+=1		
moy = somme/i
donneesString.close()# Fermeture du fichier donneesString.txt
j=0
########################################################################################################################################
first = True # Initialisation de first à true  

for lineGene in donneesGene.readlines():# Lecture de chaque ligne du fichier donneesGene.txt
	j+=1
	print(round(((j/4121)*100),2),"%")
	if first :# Première ligne
		previousGene = lineGene # Initialisation de previousGene avec la première ligne 
		first = False # Premier tour terminé
	else:
		isOperon = False # Initialisation de isOperon à false
		lG=lineGene.split('	') # Création d'une liste de chaine de caractères pour la ligne x
		pG=previousGene.split('	') # Création d'une liste de chaine de caractères pour la ligne x-1
		if pG[3] == lG[3]: # Si les deux gènes sont sur le même brin 
		
			pairNom = pG[0] + " " + lG[0] # Concaténation des deux noms de gène pour créer le nom de la paire 
			dist = int(lG[1]) - int(pG[2])  # Calcul de la distance entre les deux gènes
			# Recherche du brin : True si positif ett False si négatif
			if int(pG[3])==-1:
				brinPos = False
			else:
				brinPos = True
			
			# True s'il sont sur le même opéron
			if str(pG[5])=="True\n" and str(lG[5])=="True\n":
				isOperon = True	
			
			# Somme de la taille des deux gènes
			tailleDeuxGene = int(lG[2]) - int(lG[1]) + int(pG[2]) - int(pG[1])
			stringScore = moy
			
			#########################################################################################################
			# Récupération du score de String 
			donneesString= open("donneesString.txt", "r")
			
			for lineString in donneesString.readlines():
				lS=lineString.split('	')
				if (lS[0]== pG[0] and lS[1]== lG[0]) or (lS[0]== lG[0] and lS[1]== pG[0]) :
					stringScore = float(lS[14])
			#########################################################################################################
			# Ecriture des caractéristiques de chaque paire de gènes dans le fichier donneesPair.txt
			donneesPair.write(str(pairNom)+"\t" + str(dist)+'\t'+ str(tailleDeuxGene)+ '\t' + str(brinPos) + '\t' + str(stringScore) + '\t' + str(isOperon) +"\n" )
		previousGene = lineGene
# Fermeture des fichiers				
donneesGene.close()
donneesPair.close()
