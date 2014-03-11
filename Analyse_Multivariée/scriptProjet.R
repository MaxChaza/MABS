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

library(vegan);
cca2=cca(tabsp, tabmil); 
plot(cca2);
cca3=cca(tabsp~Alti + phot + roch,tabmil );
plot(cca3);
anova(cca3);

cca4=cca(tabsp~Alti + phot,tabmil );
plot(cca4);
anova(cca4);