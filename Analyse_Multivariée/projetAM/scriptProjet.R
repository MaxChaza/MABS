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
2
names(cca1);
s.corcircle(cca1$as);
s.label(cca1$l1, clab = 0, cpoi = 1.5);
s.label(cca1$co, add.plot=TRUE);

s.arrow(cca1$fa);
s.match(cca1$li, cca1$ls, clab=0.5);

install.packages("vegan");
library(vegan);
cca2=cca(tabsp, tabmil); 
2
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

# Table Espèces -> Taille 98 x 5
tabtraits[,15:19]
tabEspSize = tabtraits[,15:19];
tabEspSize

# Table Milieu -> Présence espèce 376 x 98
tabsp[376,]

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

###################################################
# Effet de l'altitude sur la taille
###################################################

# Table Milieu -> Altitude 376 x 2
altitude

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

# Meme démarche pour l'altitude avec des tranche d'altitude 
# Choisir la meilleure solution

# Table Altitude -> Taille 5 x 5 
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
tabmilTranch;

tabAltSizeTranch = matrix(0, nrow=6, ncol=5) ;

colnames(tabAltSizeTranch) <- colnames(tabEspSize);
rownames(tabAltSizeTranch) <- c("Alt_0","Alt_1","Alt_2","Alt_3","Alt_4","Alt_5");
tabAltSizeTranch

tabmilTranch$Alti

for(i in 1:376) {
  for(k in 1:5){
    tabAltSizeTranch[tabmilTranch$Alti[i]+1,k]=tabAltSizeTranch[tabmilTranch$Alti[i]+1,k]+tabMilSize[i,k];  
  }
};

tabAltSizeTranch

# Test de chi2 test si on peut dire que les variables sont independantes
resTranch=chisq.test(tabAltSizeTranch);
resTranch$expected; # Ce qu'on aurai attendu si 2 variable indépendantes
resTranch; # p-value = 9.692e-05 < 0.05 on ne peut pas dire que les variables sont indépendantes.

# AFC 
afc1Tranch=dudi.coa(tabAltSizeTranch);
2
names(afc1Tranch);
sum(afc1Tranch$eig)*sum(tabAltSizeTranch);
afc1Tranch$eig[1]/sum(afc1Tranch$eig)+afc1Tranch$eig[2]/sum(afc1Tranch$eig);# 97,67% d'info conservée
scatter(afc1Tranch);# On voit ici la participation de chaque variable à l'effet de dépendance

###################################################
# Effet de la présence de roche dans le milieu
###################################################

# Table Milieu -> Roch 376 x 2
tabmil$roch[376]
tabMilSize

# Table Roch -> Taille 4 x 5 
tabRochSize = matrix(0, nrow=4, ncol=5) ;
colnames(tabRochSize) <- colnames(tabEspSize);
rownames(tabRochSize) <- c("Roch_0","Roch_1","Roch_2","Roch_3");

sum(tabMilSize[1])


for(i in 1:376) {
  for(k in 1:5){
    tabRochSize[(tabmil$roch[i])+1,k]=tabRochSize[(tabmil$roch[i])+1,k]+tabMilSize[i,k];  
  }
};

tabRochSize

# Test de chi2 test si on peut dire que les variables sont independantes
resRoch=chisq.test(tabRochSize);
resRoch$expected; # Ce qu'on aurai attendu si 2 variable indépendantes
resRoch; # p-value = 2.2e-16 < 0.05 on ne peut pas dire que les variables sont indépendantes.

# AFC 
afc1Roch=dudi.coa(tabRochSize);
2
names(afc1Roch);
sum(afc1Roch$eig)*sum(tabRochSize);
afc1Roch$eig[1]/sum(afc1Roch$eig)+afc1Roch$eig[2]/sum(afc1Roch$eig);# 99,5% d'info conservée
scatter(afc1Roch);# On voit ici la participation de chaque variable à l'effet de dépendance
afc1Roch

#####################################################
# Effet du recouvrement de la strate arborée 
# (0 absent, 1, 2, 3 maximum) à feuillage persistant
#####################################################

# Table Milieu -> Arbrp 376 x 2
tabmil$arbrp
tabMilSize

# Table Arbrp -> Taille 4 x 5 
tabArbrpSize = matrix(0, nrow=4, ncol=5) ;
colnames(tabArbrpSize) <- colnames(tabEspSize);
rownames(tabArbrpSize) <- c("Arbrp_0","Arbrp_1","Arbrp_2","Arbrp_3");

for(i in 1:376) {
  for(k in 1:5){
    tabArbrpSize[(tabmil$arbrp[i])+1,k]=tabArbrpSize[(tabmil$arbrp[i])+1,k]+tabMilSize[i,k];  
  }
};

tabArbrpSize

# Test de chi2 test si on peut dire que les variables sont independantes
resArbrp=chisq.test(tabArbrpSize);
resArbrp$expected; # Ce qu'on aurai attendu si 2 variable indépendantes
resArbrp; # p-value = 2.2e-16 < 0.05 on ne peut pas dire que les variables sont indépendantes.

# AFC 
afc2Arbrp=dudi.coa(tabArbrpSize);
2
sum(afc2Arbrp$eig)*sum(tabArbrpSize);
afc2Arbrp$eig[1]/sum(afc2Arbrp$eig)+afc2Arbrp$eig[2]/sum(afc2Arbrp$eig);# 99,5% d'info conservée
scatter(afc2Arbrp);# On voit ici la participation de chaque variable à l'effet de dépendance


#####################################################
# Effet de L'indice photique 
#####################################################

# Table Milieu -> Phot 376 x 2
tabmil$phot
tabMilSize

# Table Phot -> Taille 5 x 5 
tabPhotSize = matrix(0, nrow=5, ncol=5) ;
colnames(tabPhotSize) <- colnames(tabEspSize);
rownames(tabPhotSize) <- c("Phot_1","Phot_2","Phot_3","Phot_4","Phot_5");

for(i in 1:376) {
  for(k in 1:5){
    tabPhotSize[(tabmil$phot[i]),k]=tabPhotSize[(tabmil$phot[i]),k]+tabMilSize[i,k];  
  }
};

tabPhotSize

# Test de chi2 test si on peut dire que les variables sont independantes
resPhot=chisq.test(tabPhotSize);
resPhot$expected; # Ce qu'on aurai attendu si 2 variable indépendantes
resPhot; # p-value = 2.2e-16 < 0.05 on ne peut pas dire que les variables sont indépendantes.

# AFC 
afc2Phot=dudi.coa(tabPhotSize);
2
sum(afc2Phot$eig)*sum(tabPhotSize);
afc2Phot$eig[1]/sum(afc2Phot$eig)+afc2Phot$eig[2]/sum(afc2Phot$eig);# 98,5% d'info conservée
scatter(afc2Phot);# On voit ici la participation de chaque variable à l'effet de dépendance

#############################################################################
# ACM -> Pour trouver la position des individus qui maximise les pourcentages
# de varible expliquée, en moyenne, pour toutes les variables quantitatives
#############################################################################

tabmilTranch;


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

