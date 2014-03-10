# -*- coding: utf8 -*-
# C:\Python33\py.exe

import os

while 1 :
	annee = input("Saisissez l'année à tester ou \"Q\" pour quitter s: \n")
	if annee == 'Q' or annee == 'q' : 
		break
	try:
		annee = int(annee)
	except:
		print("Erreur lors de la conversion de l'année")
		os.system("pause")

	if (annee%4!=0):
		print(annee, " n'est pas une année bissextile.")
	elif (annee%100==0):
		if (annee%400==0):
			print(annee, " est une année bissextile.")
		else :
			print(annee, " n'est pas une année bissextile.")
	else :
		print(annee, " est une année bissextile.")
