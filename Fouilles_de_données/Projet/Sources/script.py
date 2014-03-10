# -*- coding: utf8 -*-
# C:\Python33\py.exe

import os

from Bio import SeqIO
donnees= open("donnees.txt", "w")
arch = "genome_complet.gb"
record = SeqIO.parse(arch, "genbank")
rec = next(record)                      
for f in rec.features:
	if f.type == 'CDS':
		print(f.qualifiers['gene'])
donnees.close()