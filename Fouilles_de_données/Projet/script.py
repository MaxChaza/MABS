from Bio import SeqIO


donnees= open("donnees.txt", "w")
arch = "genome_complet.gb"
record = SeqIO.parse(arch, "genbank")
rec = next(record)                       # there is only one record
distInterGene = 0
prevGene = 0

for f in rec.features:
	
	if f.type == 'CDS':
		operon=open('OperonSet.txt', 'r')
		isOperon = False
		for lineOperon in operon.readlines():
			tabLine=lineOperon.split('\t')
			if tabLine[0][0]!='#':
				liste=tabLine[5].split(',')
				for l in liste:	
					if l == str(f.qualifiers['gene'][0]) :
						if int(tabLine[4])>1:
							isOperon = True
						try:
							donnees.write(str(f.qualifiers['gene'])+"\t" + str(f.location.start)+'\t'+ str(f.location.end)+ '\t'+ str(f.location.strand)+ '\t' + str(f.qualifiers.get('function')) + '\t' + str(isOperon) +"\n" )
						except KeyError:
							donnees.write(str(f.qualifiers['gene'])+"\t" + str(f.location)+ '\t'+ str(f.location.end)+ '\t'+ str(f.location.strand)+ '\t' + "NA" + str(isOperon) +"\n" )
			
		
		
		#if f.qualifiers['function'] is not None:
		#donnees.write(str(f.qualifiers['gene'])+"\t" + str(f.location)+ f.qualifiers.get('function') +"\n" )
operon.close()			
donnees.close()
