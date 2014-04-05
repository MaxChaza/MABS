install.packages("ade4");
setwd("~/MABS/Analyse_Multivariée/projetAM");
library(ade4);
tabsp=read.table("tabsp.txt", header=TRUE);
tabtraits=read.table("tabtraits.txt", header=TRUE);
tabmil=read.table("tabmil.txt", header=TRUE);
nom=read.table("noms.txt", header=TRUE);
altitude=read.table("altitude.txt", header=TRUE);

tabmil4=tabmil;
tabmil2=tabmil[,2:14];
tabmil=cbind(tabmil2, altitude);

cca1 = cca(tabsp,tabmil);
names(cca1);
s.corcircle(cca1$as);
s.label(cca1$l1, clab = 0, cpoi = 1.5);
s.label(cca1$co, add.plot=TRUE);

s.arrow(cca1$fa);
s.match(cca1$li, cca1$ls, clab=0.5);

install.packages("vegan");
library(vegan);
cca2=cca(tabsp, tabmil); 
plot(cca2);
cca3=cca(tabsp~Alti + phot + roch,tabmil );
plot(cca3);
anova(cca3);

cca4=cca(tabsp~Alti + phot,tabmil );
plot(cca4);
anova(cca4);

cca5=cca(tabsp~Alti + phot + arbrp,tabmil );
plot(cca5);
anova(cca5);


anova(cca(tabsp~herb + Condition(Alti + roch),tabmil ))

#########################################
tabmil
altitude
tabtraits

# Table Milieu -> Présence espèce 376 x 98
tabsp

# Table Milieu -> Altitude 376 x 2
altitude

# Table Espèces -> Taille 98 x 5
tabtraits[,15:19]
tabEspSize = tabtraits[,15:19];
tabEspSize

# Table Milieu -> Taille 376 x 5 
tabMilSize = matrix(0, nrow=376, ncol=5) ;
colnames(tabMilSize) <- colnames(tabEspSize)
rownames(tabMilSize) <- rownames(altitude)
tabMilSize


for(i in 1:376) {
  for(j in 1:98){
    if(tabsp[i,j]==1){
      k=1;
      while(k<5 && tabEspSize[j,k]==0){
        k=k+1;  
      }
      tabMilSize[i,k]=tabMilSize[i,k]+1;
    }
  }
};

tabMilSize

# Trouver le nombre d'altitude différents par milieu 
altName=c()
alt=0;
nbAlt=0;
for(i in 1:376) {
  if(altitude[i,]>alt)
  {
    alt=altitude[i,];
    # créer un vecteur pour nommer les lignes(altitudes)
    altName=append(altName,alt ,after=length(altName));
    nbAlt=nbAlt+1;
  }
};
nbAlt
altName
# On trouve 183 Altitude différentes

# Table Altitude -> Taille 183 x 5 
tabAltSize = matrix(0, nrow=183, ncol=5) ;

colnames(tabAltSize) <- colnames(tabEspSize);
rownames(tabAltSize) <- altName;
tabAltSize[183,5]

alt=0;
nbAlt=0;
for(i in 1:376) {
  for(k in 1:5){
    if(altitude[i,]>alt)
    {
      alt=altitude[i,];
      nbAlt=nbAlt+1;
    }
    tabAltSize[nbAlt,k]=tabAltSize[nbAlt,k]+tabMilSize[i,k];  
  }
};

tabAltSize

# Test de chi2 test si on peut dire que les variables sont independantes
res=chisq.test(tabAltSize);
res$expected; # Ce qu'on aurai attendu si 2 variable indépendantes
res; # p-value = 9.692e-05 < 0.05 on ne peut pas dire que les variables sont indépendantes.

# AFC 
afc1=dudi.coa(tabAltSize);
2
names(afc1);
sum(afc1$eig)*sum(tabAltSize);
afc1$eig[1]/sum(afc1$eig)+afc1$eig[2]/sum(afc1$eig);# 70% d'info conservée
scatter(afc1);# On voit ici la participation de chaque variable à l'effet de dépendance

# ACM -> Pour trouver la position des individus qui maximise les pourcentages
# de varible expliquée,en moyenne, pour toutes les variables quantitatives
tabmil;
limiteBasse = altitude[1,];
tailleGroup = (altitude[375,]-limiteBasse)/5;
tailleGroup # 442 Tranche altitudinale

tabmilTranch = tabmil;
nbAlt=0;

for(i in 1:376) {
  trancheAlt = round((tabmil[i,14]-limiteBasse)/tailleGroup);
  tabmilTranch[i,14]=trancheAlt;  
};
tabmilTranch$;

# Convertir les données en factors
tabmilTranch$phot <- factor(tabmilTranch$phot)
tabmilTranch$roch <- factor(tabmilTranch$roch)
tabmilTranch$habi <- factor(tabmilTranch$habi)
tabmilTranch$eau <- factor(tabmilTranch$eau)
tabmilTranch$neig <- factor(tabmilTranch$neig)
tabmilTranch$pent <- factor(tabmilTranch$pent)
tabmilTranch$arbrp <- factor(tabmilTranch$arbrp)
tabmilTranch$arbrf <- factor(tabmilTranch$arbrf)
tabmilTranch$arbup <- factor(tabmilTranch$arbup)
tabmilTranch$arbuf <- factor(tabmilTranch$arbuf)
tabmilTranch$buip <- factor(tabmilTranch$buip)
tabmilTranch$buif <- factor(tabmilTranch$buif)
tabmilTranch$herb <- factor(tabmilTranch$herb)
tabmilTranch$Alti <- factor(tabmilTranch$Alti)

acm2=dudi.acm(tabmilTranch);
2

names(acm2);
scatter.dudi(acm2);
scatter(acm2);

# Lien entre modalités
acm2$cr
#phot  9.320972e-02 0.220479868
#roch  7.390560e-01 0.008022951
#habi  6.374103e-02 0.161106965
#eau   3.950927e-06 0.022191088
#neig  5.975102e-01 0.001375443
#pent  1.049935e-01 0.232763103
#arbrp 2.104585e-01 0.453588249
#arbrf 3.510524e-01 0.192829078
#arbup 1.459337e-01 0.473599554
#arbuf 7.688010e-01 0.005026408
#buip  3.175865e-02 0.240334229
#buif  6.856669e-01 0.010184266
#herb  4.226433e-02 0.299754183
#Alti  8.546112e-01 0.288011514

