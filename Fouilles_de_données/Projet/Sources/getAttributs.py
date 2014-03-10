# -*- coding: utf8 -*-
# C:\Python33\py.exe

import os

geneSequence = open("../Gene_sequence.txt","r")
data = open("data.txt", "w")

distInterGene = 0
prevGene = 0
for line in geneSequence.readlines():
	infogene = line.split("	")

	if infogene[0] is not None and infogene[0]!='' and infogene[0]!='NA':			
		if( (infogene[0][0:3] == "ECK" ) | (isinstance(infogene[0], int)) ):
			if( prevGene != 0 ):
				beginGene = int(infogene[2])
				distInterGene = beginGene + 1 - prevGene
			
			if infogene[3] is not None and infogene[3]!='' and infogene[3]!='NA':
				prevGene = int(infogene[3]) 
			print(distInterGene)
			data.write(infogene[1] + "\t" + infogene[4] + "\t" + str(distInterGene)  + "\n")
data.close()
geneSequence.close()

os.system("pause")