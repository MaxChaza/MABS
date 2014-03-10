

############## Download ###################################################################################
#http://www.r-project.org/
#	Download CRAN
#	http://cran.cict.fr/
#	Windows (ou autres)
#	base
#	Download R 2.12.0 for Windows
#installer
###########################################################################################################


############## Lancer R  /  Script ########################################################################
#  Un dossier sur le bureau par exemple, dans lequel se trouvent 
#  les fichiers de données et les fichiers que vous allez éventuellement créer
#  - Lancer R --> File --> change dir --> Dossier Bureau
#  - ouvrir un script ou "new script"
#  - attente de commande:  control R pour copier-coller la commande
#  - # = insérer des commentaires
#  - flèches haut et bas = naviguer dans les commandes déjà lancées 
###########################################################################################################


############## Aide #######################################################################################
?help				# aide sur la fonction "aide"
help(help)

?plot				# aide sur la fonction "plot"
help.search("plot")	# liste de fichiers d'aide ayant un lien avec la fonction "plot" (idem ??plot)
###########################################################################################################


############## Structure de données #######################################################################
#### Scalaire
2+2
exp(10)
a = log(2)
b <- cos(10)
a+b
a
2==3
b = 2<3
ls()
rm(a)
a
a="toto"

#### Vecteur
d = c(2,3,5,8,4,6);d
is.vector(d)
1:10
seq(from=1,to=20,by=2)
rep(5,times=10)  #rep(5,10)
d[2] ;d[2:3] ;d[-3]
f = c(a=12,b=26,c=32,d=41) ;f
names(f) ;f["a"]
names(f)=c("a1","a2","a3","a4")
f>30 ;f[f>30]
which(f>30)
f[2] = 22 ;f+100 ;f+d
c(f,d)
cos(f) ;length(f) ;sort(d)


#### Matrice
A = matrix(1:15,ncol=5);A
B = matrix(1:15,nc=5,byrow=T);B
cbind(A,B); rbind(A,B)
A[1,3]; A[1,]; A[,2]; A[1:3,2:4] ### [ligne,colonne]  
g = seq(0,1,length=20)           ### valeurs espacées également
C = matrix(g,nrow=4)
C[C[,1]>0.1,]
A+B; A*B
cos(A) ; cos(A[1:2,1:2])
solve(A)
solve(A[1:2,1:2])	               ### inversion de matrice: condition = matrice carrée
A %*% B
A[1:2,1:2] %*% B[1:2,1:3]        ### condition: ncol(A)= nrow(b)
apply(A,2,sum)                   ### somme les éléments des colonnes
apply(A,1,max)                   ### donne les max des lignes


#### Tableau - Array
H=array(1:12,c(2,3,2))		   ### généralisation du type matrix à plus de 2 dimensions c(row,col,3ème_dim (n>=2) )
H[,,1]; H[,1,]; H[1,,1:2]
apply(H,1,sum)
apply(H,2,sum)
apply(H,3,sum)


#### Liste
x = list("bidon",1:8) ;x
x[[1]]
x[[1]]+1
x[[2]]+10
x[[2]][1]+10
y = list(matrice=D,vecteur=f,texte="bidon",scalaire=8)
names(y); y[[1]]
y$matrice; y$vec
y[c("texte","scal")]
y[c("texte","scalaire")]
length(y)
length(y$vecteur)
cos(y$scal)+y[[2]][1]

#### Dataframe
taille = runif(12,150,180)
masse  = runif(12,50,90)
sexe = rep(c("M","F","F","M"),3)
H = data.frame(taille,masse,sexe)
H ;summary(H)
H[1,]; H$taille
H$sexe
###########################################################################################################


############## Importation de données #####################################################################
### attention pour les tableaux de données, R n'aime pas les espaces vides au sein des noms
### (de variables ou d'individus) -->  "_" ou "." ou "tout attaché"
### la décimale doit être un point

fichier "CSV" enregistré avec séparateur=point-virgule

help(read.table)
crime_a = read.table("USArrests.csv", sep=";", dec=",", header=TRUE)  ; crime_a
crime_b = read.table("USArrests.txt", sep=" ",header=TRUE)  ; crime_b
metadata = read.table("gold.metadata.txt", sep="\t",header=TRUE); metadata
###########################################################################################################


############## Exportation d'objets R #####################################################################
A=seq(1,10,l=50)
write.table(A,"A.txt")    # , quote =F, col.names= T, row.names = T, sep = " ") 

sink("A2.txt")            # redirection du résultat des commandes vers un fichier (pas d’affichage à l’écran).
A ; summary(A)
sink()                    # ne pas oublier de fermer le fichier en rappelant sink() sans argument.
summary(A)
###########################################################################################################


############## Fonctions graphiques #######################################################################
help.search("plot")

#### une variable qualitative (Effectif)
data=c(12,10,7,13,26,16,4,12)
names=c("a","b","c","d","e","f","g","h")
names=c(a,b,c,d,e,f,g,h)
pie(data)                # , names)
barplot(data)            # , names.arg=names, main="exemple", cex.names = 0.9, las=3)

#### une variable quantitative
# ex : Tirage aléatoire d’un échantillon de taille 100 issu d’une loi uniforme sur l’intervalle [0,1]
x=runif(100,0,1); x
hist(x, xlab= "valeurs")      # , xlab= "valeurs",ylab = "fréquence",freq=F)
plot(density(x))
boxplot(x)
stripchart(x)                 # scatter plots une dimension - ou dot plots - des data (alternative au boxplot quand N est petit)

par(mfrow=c(3,1))             # découper une fenêtre graphique
hist(x, xlab= "valeurs")                
boxplot(x)
stripchart(x)

## exemple avec les fichier de données "USArrest" et "metadata":
attach(crime_a); names(crime_a)
names=X
pie(Murder,names)
crime <- crime_b
hist(Murder)

attach(metadata);names(metadata)
plot(Chr_ID,Gc_content,las=2,cex.axis=0.7)
barplot(Gc_content[order(Gc_content)],names.arg=Chr_ID[order(Gc_content)],las=2,cex.axis=0.7)


#### Deux variables quantitatives
# ex : représentation de la fonction sinus sur l’intervalle [-10,10]
x=seq(-10,10,l=100)
plot(x,sin(x))
plot(x,sin(x),type="l",col="blue",lwd=2)
abline(h=0,v=0,lty=2)
points(0,0,pch="+",cex=3,col="red")
lines(x,cos(x),col="green",lty=2)

#### Trois variables
# ex : représentation de la fonction sinus cardinal sur [-10,10]2
#Construction de x, y, et z
x <- seq(-10, 10, length=50)
y <- x
f <- function(x,y) {
  r <- sqrt(x^2+y^2)
  10 * sin(r)/r
}
z <- outer(x, y, f)   # fonction f appliquée aux coordonnées x et y
z[is.na(z)] <- 1
# Représentations graphiques
persp(x,y,z)
persp(x, y, z, theta = 30, phi = 30, expand = 0.5, col = "lightblue",shade=.5,xlab = "X", ylab = "Y", zlab = "Z")
image(x,y,z)
persp(x,y,z)
contour(x,y,z)
filled.contour(x,y,z)

### Autres
#plot(),image()...                               # créer un graphique
#lines(), abline(), points(), text(), rect()...  # ajouter à un graphique existant :
locator(1); text(locator(1),"ici")               # récupérer les coordonnées d’un point en cliquant :
#windows(), X11()                                # ouvrir une nouvelle fenêtre graphique :
#par(mfrow=c(lig,col)), layout()                 # découper une fenêtre graphique :

layout(matrix(c(1,1,2,3), 2, 2, byrow = TRUE))
image(x,y,z)
persp(x,y,z)
contour(x,y,z)
###
###########################################################################################################


############## Sauvegarde et/ou exportation ###############################################################
# copier-coller : menu Fichier > Copier vers le presse-papier puis coller dans le logiciel de son choix
# sauvegarder : menu Fichier, rubrique Sauver sous (Formats : emf, ps, pdf, png, bmp, jpeg ...)
# utiliser les fonctions associées à la sauvegarde de fichiers (graphiques : bmp(), jpeg(), pdf() ...)

jpeg("persp.jpg")  			# redirection de la sortie graphique vers un fichier
persp(x,y,z) 				# tracé du graphique
dev.off()					# fermer le fichier (ne pas oublier cette étape!)
###########################################################################################################


############## Stat descriptives unidimensionnelle ########################################################
#boxplot()			# boxplot() et hist() peuvent ne pas produire de graphique (option plot=FALSE)
#summary()			# fonction générique (comme plot()) qui s’adapte à la classe (class()) de l’objet passé en paramètre (vecteur, matrice, dataframe, résultat d’une fonction...)

x=rnorm(100,0,1)
y=rnorm(100,0,1)
mean(x); var(x); sd(x)						# moyenne, variance (correction "n-1"), écart-type
min(x); max(x)							# valeur minimale, maximale
median(x); quantile(x);	quantile(x,c(0.5,0.95,0.99))	# quantiles
boxplot(x,plot=FALSE)
cov(x,y); cor(x,y); plot(x,y)
hist(x)								# Effectifs: "freq=T"   Fréquences: "freq=F"
summary(x) 
A = matrix(1:15,ncol=5); class(A)				# classe de l'objet ("dataframe","matrix","list")
mode(A)								# mode de l'objet ("logical", "integer", "character", "expression", "name",...) 

# Classe modale = plus compliqué (pas de fonction définie)
# Une possibilité (exemple avec la loi normale) 
#x=rnorm(10000,10,5)
#breaks = min(x): max(x)
#class_length=c()
#X1=cut(x,breaks,labels=F)     
#for (i in c(1:length(breaks))){
#class_length[i]=length(which(X1==i))
#}
#max(class_length)
#which.max(class_length)
#
#hist(x,length(breaks),freq=F,col="grey",xlab="",main="")
#abline(v=breaks[which.max(class_length)],lwd=2)
###########################################################################################################





