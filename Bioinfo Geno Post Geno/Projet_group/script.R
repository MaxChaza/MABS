setwd("~/Cours/M1_Bioinfo/S8/BGPG/Projets/projet_groupe")

install.packages("vegan")
library(vegan)
library(ade4)
library(MASS)
install.packages("fpc")
library(fpc)

profilgen = read.table('EcolX_profil.txt', header = T)

todel = which(profilgen[,1]==profilgen[,2] &
              profilgen[,2]==profilgen[,3] & 
              profilgen[,3]==profilgen[,4] &
              profilgen[,4]==profilgen[,5] &
              profilgen[,5]==profilgen[,6] &
              profilgen[,6]==profilgen[,7] )
profilgen= profilgen[-todel,]



profilgen
names(profilgen)
profilgen[,2:8]

# Jaccard vegan -----------------------------------------------------------

pseudoDist_veg <- vegdist(profilgen, method="jaccard")



# Jaccard ade4 ------------------------------------------------------------

pseudoDist_ade <- dist.binary(profilgen,method=1)





# clustering --------------------------------------------------------------
?Mclust

mclust_grp=Mclust(pseudoDist_veg)
mclust_grp$classification



#plot#
cmd <- cmdscale(pseudoDist_veg)
# plot MDS, with colors by groups from kmeans
groups <- levels(factor(mclust_grp$classification))
ordiplot(cmd, type = "n")
cols <- c("blue", "red", "green") #, "yellow", "pink", "black","purple","orange", "grey")
for(i in seq_along(groups)){
  points(cmd[factor(mclust_grp$classification) == groups[i], ], col = cols[i], pch = 16)
}

# add spider and hull
ordispider(cmd, factor(mclust_grp$classification), label = TRUE)
ordihull(cmd, factor(mclust_grp$classification), lty = "dotted")


# KMEAN -----------------------------------------------------------

#####vegan#####
grp_kmean_veg=kmeans(pseudoDist_veg,15 )
grp_kmean_veg$cluster

#plot#
cmd <- cmdscale(pseudoDist_veg)
# plot MDS, with colors by groups from kmeans
groups <- levels(factor(grp_kmean_veg$cluster))
ordiplot(cmd, type = "n")
cols <-rainbow(15)
cols2=palette(cols)#c("blue", "red", "green", "yellow", "pink", "black")
for(i in seq_along(groups)){
  points(cmd[factor(grp_kmean_veg$cluster) == groups[i], ], col = cols[i], pch = 16)
}

# add spider and hull
ordispider(cmd, factor(grp_kmean_veg$cluster), label = TRUE)
ordihull(cmd, factor(grp_kmean_veg$cluster), lty = "dotted")

cols=rainbow(50)


#####ADE#####
grp_kmean_ade=kmeans(pseudoDist_ade, 15)
grp_kmean_ade$cluster

#plot#
cmd <- cmdscale(pseudoDist_ade)
# plot MDS, with colors by groups from kmeans
groups <- levels(factor(grp_kmean_ade$cluster))
ordiplot(cmd, type = "n")
#cols <- c("blue", "red", "green", "yellow", "pink", "black")
for(i in seq_along(groups)){
  points(cmd[factor(grp_kmean_ade$cluster) == groups[i], ], col = cols[i], pch = 16)
}

# add spider and hull
ordispider(cmd, factor(grp_kmean_ade$cluster), label = TRUE)
ordihull(cmd, factor(grp_kmean_ade$cluster), lty = "dotted")



# HIERARCHIQUE ------------------------------------------------------------


#####vegan#####
dendro_veg <- hclust(pseudoDist_veg, method='ward',hang)
grp_hier_veg = cutree(dendro_veg,50)
plot(dendro_veg, ylim=c(0, 1))
abline(h=5, col='tomato3')
?plot
#plot#
cmd <- cmdscale(pseudoDist_veg)
# plot MDS, with colors by groups from kmeans
groups <- levels(factor(grp_hier_veg))
ordiplot(cmd, type = "n")
#cols <- c("blue", "red", "green", "yellow", "pink", "black")
for(i in seq_along(groups)){
  points(cmd[factor(grp_hier_veg) == groups[i], ], col = cols[i], pch = 16)
}

# add spider and hull
ordispider(cmd, factor(grp_hier_veg), label = TRUE)
ordihull(cmd, factor(grp_hier_veg), lty = "dotted")




#####ade#####
dendro_ade <- hclust(pseudoDist_ade, method='ward')
grp_hier_ade = cutree(dendro_ade,6)


#plot#
cmd <- cmdscale(pseudoDist_ade)
# plot MDS, with colors by groups from kmeans
groups <- levels(factor(grp_hier_ade))
ordiplot(cmd, type = "n")
#cols <- c("blue", "red", "green", "yellow", "pink", "black")
for(i in seq_along(groups)){
  points(cmd[factor(grp_hier_ade) == groups[i], ], col = cols[i], pch = 16)
}

# add spider and hull
ordispider(cmd, factor(grp_hier_ade), label = TRUE)
ordihull(cmd, factor(grp_hier_ade), lty = "dotted")





# recuperer les noms des genes pour chaque cluster ------------------------
groupe=as.factor(grp_kmean_veg$clust)
profilgen2=cbind(profilgen,groupe)
todel = which(profilgen2$groupe!=19)
profilgen22=profilgen2[-todel,]
profilgen22

