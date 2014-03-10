######################################################################################################################
# Script permettant la création d'une liste de paires de gènes avec leurs caractéristiques 
# Sur chaque ligne : nom du gène, début du gène, fin du gène, brin, fonction, si le brin est dans un opéron
###################################################################################################################### 


# Importation du module BioPython Pour traiter le fichier genBank
from Bio import SeqIO

donneesGene= open("donneesGene.txt", "w") # Ouverture du fichier donneesGene.txt en écriture
arch = "genome_complet.gb"
record = SeqIO.parse(arch, "genbank") # Parse le fichier genome_complet.gb qui contient le nom, la position et la fonction des gènes
rec = next(record)	# There is only one record

for f in rec.features: # Lecture de chaque ligne du fichier parsé
	if f.type != 'gene' and ('function' in f.qualifiers or 'product' in f.qualifiers): 
		operon=open('OperonSet.txt', 'r') # Ouverture du fichier OperonSet.txt en lecture contenant la composition de tous les opérons 
		isOperon = False # Initiation d'un booléen à Faux
		
		for lineOperon in operon.readlines(): # Lecture de chaque ligne du fichier contenant les opérons
			tabLine=lineOperon.split('\t') # Création d'une liste de chaine de caractères qui étaient séparées par des tabulations 

			if tabLine[0][0]!='#': # Pour éviter toutes les lignes commençant par # 
				liste=tabLine[5].split(',') # Création d'une liste de nom de gène qui étaient séparées par des virgule

				for l in liste:	# On parcour la liste de gène
					i=0 # itérateur
					nom = l 
					
					#########################################################################################################
					# Boucle permettant la gestion des noms comportant un tiret ou un underscore
					for lettre in l:
						if lettre == '_':  
							l=l[:i] # Si il y a un underscore au ième caractère on supprime l'underscore et la suite
						if lettre == '-':
							l=l[:i] # Si il y a un tiret au ième caractère on supprime le tiret et la suite
						i+=1
					#########################################################################################################
					# Les trois lignes suivantes permettent de gérer la difference entre le génome de genBank et le génome d'OperonSet
					end = 1000	
					if (f.location.start> 1000000): end=2000
					elif (f.location.start> 4000000): end=4000
					
					#########################################################################################################
					# Ecriture dans le fichier
					# Si le début du gène de genBank est compris entre le début et la fin de l'opéron 
					# et que le nom du gène de genBank et celui présent dans l'opéron sont les même
					if (int(tabLine[1])-10<=f.location.start) and (int(tabLine[2])+ end >=f.location.start) and (l == str(f.qualifiers['gene'][0])) :
						if int(tabLine[4])>1: 
							isOperon = True # Le gène est dans un opéron si le nombre de gènes est supérieur à 1
						try: # Ecriture des caractéristiques de chaque gène dans le fichier donneesGene.txt
							donneesGene.write(nom+"\t" + str(f.location.start)+'\t'+ str(f.location.end)+ '\t'+ str(f.location.strand)+ '\t' + str(f.qualifiers.get('function')) + '\t' + str(isOperon) +"\n" )
						except KeyError: #  Ecriture des caractéristiques de chaque gène dans le fichier donneesGene.txt malgrès le fait que la fonction ne soit pas présente
							donneesGene.write(nom+"\t" + str(f.location.start)+ '\t'+ str(f.location.end)+ '\t'+ str(f.location.strand)+ '\t' + "NA" + str(isOperon) +"\n" )
			
# On ferme les fichiers
operon.close()			
donneesGene.close()
