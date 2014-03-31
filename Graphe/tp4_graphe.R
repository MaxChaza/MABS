quentin@ibcg-biotoul.fr
Pour le 30/03
#La fonction read.graph permet de lire la liste des arêtes à partir d’un fichier.
library(ade4)
library(igraph)
graph <- read.graph("Cleandb_Luca_1_A_1_1_65_Iso_Tr_1.gr", format = "ncol", directed = FALSE);
#Nous allons utiliser les fonctions vcount et ecount pour compter le nombre de sommets et d’arêtes du graphe.
nbVert <- vcount(graph)
nbEdge <- ecount(graph)
#> nbVert
#[1] 611
#> nbEdge
#[1] 7266
#Avant de poursuivre, il est nécessaire d’appliquer la fonction simplify() sur notre graphe.
graph = simplify(graph)
> ecount(graph)
[1] 3633
> vcount(graph)
[1] 611
# 1) Quelle est l’effet de cette fonction ? 
Description
Simple graphs are graphs which do not contain loop and multiple edges.
# 2) Discutez cette observation en la ramenant à la façon dont a été construit le graphe.
# Dans la suite de l’exercice nous utiliserons le graphe 'simplifié'.
Cette fonction permet de supprimer les arêtes multiples (liens d isorthologies)
Du coup, on perd la moitié de nos arêtes (on avait A->B et B->A)

#On s’attend à ce qu’un assemblage de gènes isorthologues ne comporte pas 
#de gènes paralogues. 
#Nous allons donc écrire une fonction qui calcule le taux de gènes paralogues 
#dans une liste de gènes. Pour cela nous allons compter le nombre de gènes présent 
#dans la liste et le comparer au nombre de souches. 
#S’il n’y a pas de paralogue, chaque souche est représentée par un seul gènes,
#sinon, on peut avoir plus d'un gène par souche. 
#Pour obtenir le taux de paralogues, on divise la différence entre nombre de gènes 
#et nombre de souches par le nombre de gènes.
#Les fonctions à utiliser sont : substr() et unique().
#L’expression V(graph)$name permet d’obtenir la liste des sommets du graphe.
count_paralogs <-function(list) {
 gene_list <- list
 all_strains <- substr(gene_list,1,5)
 nb_gene = length(all_strains)
 nb_souche = length(unique(all_strains))
 paralogs = (nb_gene - nb_souche)/nb_gene
 return(paralogs)
}
#Utilisez cette fonction pour calculer le taux de paralogues dans le graphe.

count_paralogs(V(graph)$name)
> paralogs
[1] 0.9050736
count_paralogs()
#Quelle peut être l’origine de ce taux élevé da paralogie ?
#Nous avons beaucoup de gènes pour peu de souches, forcément, on s attend à tomber sur un taux élevé de paralogues.



#REPRESENTATION GRAPHIQUE 
#Nous allons observer graphiquement le graphe. 
#Pour faciliter les comparaisons entre différentes « colorations » du graphe, 
#nous allons fixer la disposition (le layout) du graphe à l’aide de l’expression 
#suivante :
globalGraphLayout = layout.fruchterman.reingold(graph);


plot(graph, layout=globalGraphLayout, vertex.label=NA, vertex.size=3);

#*Décrire le graphe obtenu.
Nous avons un nombre conséquent de sous graphes isolés.
DOnc c est un graphe peu dense, peu connecté avec beaucoup de composantes connexes
(de tailles variables)
On peut penser que ces petites composantes connexes sont les protéines se situant en
amont et en aval de nos protéines du transporteur ABC.




#Annotations 

#Nous allons utiliser les annotations en domaines contenu dans le fichier 
#annot_file pour colorier le graphe.
#Lecture du fichier :
  
annot_file <- 'Annotation.txt';
annotation <- read.table(annot_file, header=TRUE, row.names=1);

#Annotation en domaines 
#Les colonnes du fichier sont  : 
#'protein name', 'anchor', 'cog', 'assembly', 'domain', 'subfamily'. 
# La difficulté est que certaines protéines n’ont pas de domaines annotés 
#(ils ne sont pas partenaire d'un système ABC) dans le fichier.
domains = annotation[V(graph)$name, 4];
domains
#Pensez à associer une couleur à chaque domaine (color = rainbow(length(levels(x))).
color = rainbow(length(levels(domains)))
palette(color)
plot(graph, layout=globalGraphLayout, vertex.color=domains, vertex.label=NA, vertex.size=3);
legend("topright", legend=levels(domains), cex=0.7, fill=color);
#On remarque qu'a l'intérieur d'une sous famille de transporteur, on a déjà pas mal
#de diversité.

#Annotation en sous familles

ssfam = annotation[V(graph)$name, 5];#5 pour la sous famille
color = rainbow(length(levels(ssfam)))
par(mar=c(6,6,6,6))
palette(color)
plot(graph, layout=globalGraphLayout, vertex.color=ssfam, vertex.label=NA, vertex.size=3);
legend("right", legend=levels(ssfam), cex=0.7, fill=color);


#Commentez les résultats obtenus.
#Il ya beaucoup de sous famille est ce n'est pas celles que l'on attendait


#Extraction des composantes connexes 

#Nous allons maintenant extraire les composantes connexes (CC) du graphe à 
#l’aide de la fonction decompose.graph() en utilisant le mode = "strong".
cc <- decompose.graph(graph, mode = "strong", min.vertices = 5)
#Nous ne conserverons que les CC contenant au moins 5 sommets. 
#La liste des objets graphe sera conservée dans la variable cc.
#Combien de composantes connexes avez-vous obtenu ?
nb_cc = length(cc)
nb_cc 
26

#Propriétés des composantes connexes 

#Les CC apparaissent hétérogènes au niveau de leur taille mais aussi au niveau 
#de leur densité en arêtes.
#Quelle est le nombre d’arêtes maximum possible dans un graphe de n sommets ?
(n²-n)/2

#Vous pouvez établir une fonction donnant le nombre d’arêtes maximum d’un graphe 
#en fonction du nombre de sommets. 
#Nous allons donc étudier la distribution du nombre d’arêtes en fonction 
#du nombre de sommets de nos CC et nous allons tracer la courbe de 
#la fonction d’arêtes maximum.
#On peut par exemple placer ces informations dans un tableau :

cc_info <- array(0, c(nb_cc, 2));

for ( i in 1:nb_cc ) {
  cc_info[i,1] <- vcount(cc[[i]])
  cc_info[i,2] <- ecount(cc[[i]])  
}

# OU
cc[[i]]

# est le graphe de la CC i.

# Puis calculer la courbe de la fonction:
  
maxx <- max(cc_info[,1])
maxy <- max(cc_info[,2])
courbe <- c();
i <- 0;
j <- 0;
while(j < maxy && i < maxx ) {
  i <- i + 1;
  j <- i*(i-1)/2;
  courbe[i] <- j;
}


xlim = c(0,maxx)
ylim=c(0,maxy)
zlim = c(0,1)

plot(cc_info, xlim = xlim, ylim = ylim, xlab = "sommets", ylab = 'aretes', pch = 16, col = "green", main = 'Composantes connexe')
points(courbe, pch = 1, col= "blue", type="l", lty= 2, lwd =2)
######################################################################
#On pourra utiliser la fonction points() pour ajouter la courbe à la distribution 
#des CC.
#
#Commentez le résultat obtenu.
#On nee peut pas comparer toutes les composantes connexes entre elles
# Seuelement quelques unes suivent le maximum d'arêtes par sommet (regarder le plot)
# Ce sont donc des cliques et il est inutile de chercher des communautés dans celles ci.
# En revanche, les graphes qui s'éloignent de la distribution max sont intéressant
# car on va pouvoir trouver des communauté du fait qu'ils sont peu denses


#Pour compléter l’analyse, nous allons ajouter le taux de paralogies de chaque CC.
paralogs <- array(0, c(nb_cc, 2));

for ( i in 1:nb_cc ) {
  list_name <- V(cc[[i]])$name;
  paralogs[i, 1] <- vcount(cc[[i]]);
  paralogs[i, 2] <- count_paralogs(list_name);
}

#Pour superposer le résultat sur le graphique précédant, nous pouvons utiliser :

par(new=T)
plot(paralogs, pch=18, col='red', axes=F, xlim=xlim, ylim=zlim, ,xlab= '', ylab='');
axis(side=4)
mtext('paralogs', side=4, line=2, col='red')

#Il peut être nécessaire d’augmenter la place disponible à droite du 
#graphique pour afficher la légende (fonction mar() à utiliser dans un par()).


* Quelle propriété du graphe semble liée à la distribution du taux de paralogie ?
# Lorsque l'on se rapproche de la distribution de densité maximale, on remarque que
# le taux de paralogie à tendence à être assez bas voire nul. Plus on s'éloigne, plus il augmente
# Ceci est vrai jusqu'à un certain nombre de sommet au delà duquel l'éloignement par rapport à la courbe 
# n'est plus proportionnel au taux de paralogie (voire la valeur extrême à 90 sommets)

####################### Taux de paralogie et modularité #######################

#Nous allons étudier la distribution du taux de paralogie en fonction 
#de la modularité des CC. 
#Pour cela nous allons utiliser fastgreedy.community()qui permet de faire le 
#découpage en communautés d’un graphe.
#Remarque: il faut utiliser get.adjlist() pour passer des noms des protéines 
#à la liste des sommets.

modularity <- c();
for ( i in 1:nb_cc ) {
  g <- graph.adjlist(get.adjlist(cc[[i]]),mode = 'all')
  com <- fastgreedy.community(g, merges=TRUE, modularity=TRUE);
  modularity[i] <- max(com$modularity);
}


plot(modularity,paralogs[,2], pch=18, col='red', xlim = c(0,1), ylim = c(0,1),xlab= 'Modularité', ylab='taux de paralogie');
* Que pouvez-vous en conclure?
#La modularité est corrélé positivement avec le taux de paralogie
# On observe cependant qu'il existe un ensemble de sommets ayant un taux de paralogie nul 
# et pourtant ayant une modularité positive. Donc sans doute une structure dans ce sous graphe
# On doit donc pouvoir séparer en communauté d'orthologues.


#Relation entre voisinage des gènes et découpage en CC 
#Nous avons vu que les gènes codant pour différents partenaires de transporteurs ABC 
#étaient très souvent co-localisés sur le chromosome. 
#Dans notre analyse nous avons ajouté 4 gènes en aval et en amont des gènes de 
#chaque système.

# EN LIGNE: LES SYSTEMES COMPOSES DE DIFFERENTS PARTENAIRES + GENES EN AMONT/AVAL 
# =(LOCALISATION SUR LE CHROMOSOME)

# EN COLONNE: LES COMPOSANTES CONNEXES

all_name = V(graph)$name;

cc_ass_class <- data.frame(row.names=all_name)
cc_ass_class[,1:5] <- annotation[V(graph)$name, 1:5];
cc_name_list <- c();

for ( i in 1:nb_cc ) {
  list_name <- V(cc[[i]])$name;
  cc_name_list <- append(cc_name_list, list_name);
  cc_ass_class[list_name,6] <- i;
}

color = c('white', 'black', 'grey');
matrix = as.matrix(cc_ass_class[cc_name_list,])

mat =table(matrix[,1], matrix[,6])
mat[mat>1]=1 # recouvrement entre systèmes

heatmap(mat, scale='none', col=color)
mat

##################################################
* Que pouvez-vous conclure?
###################################################
# On remarque que les composantes conenxes 1,3,4 sont sdouvent ensemble mais
# la composante 1 est aprtagé par plusieurs systèmes
# Il va sans doute fallior la déocuper car elle doit avoir beaucoup de paralogie
#L'hypothèse sous jacente étant bien entendu que les gènes d'un système coévolue
# On voit que pour certain d'entre eux c'est le cas(1 et 3 sont souvent ensemble 
# de même que 12 et 13), mais on peut essayer de mieux découper
# les composantes connexes.

###################### Décomposition en communautés #################

#Nous allons représenter graphiquement le résultat du découpage en communautés 
#réalisée par fastgreedy, uniquement pour le CC qui ont un taux de paralogie 
#supérieur à 0.

####################################
* Pouvez-vous justifier ce choix ?
####################################
#On souhaite découper de manière plus précise nos Composantes connexes
#
#
#Pour la suite nous allons conserver les graphes modifiés pour être compatibles avec les méthodes de communautés et les dispositions pour faciliter les comparaisons entre les méthodes (utilisation de listes).

G_list  <- list();       # liste des graphes dans le format attendu par '''fastgreedy''' et les autres méthodes.
GLayout_list <-  list(); # liste des layout

for ( i in 1:nb_cc ) {
  G_list[[i]] <- graph.adjlist(get.adjlist(cc[[i]]), mode = 'all')
  GLayout_list[[i]] <- layout.fruchterman.reingold(G_list[[i]]);
}


#Fastgreedy 
#Une composante connexe 
#Exemple pour une CC
i <- 1;
par(mar = c(5,5,5,5))
com <- fastgreedy.community(G_list[[i]], merges=TRUE, modularity=TRUE);
Q = max(com$modularity);

#On sélectionne l'indice du tableau ayant la plus forte modularité
Step <- which.max(com$modularity);
#On associe une classe à chaque sommet du graphe
cla <- community.to.membership(G_list[[i]], com$merges, membership=TRUE, csize=TRUE, steps=Step-1);

color = rainbow(10)
palette(color)
membership = cla$membership+1; #+1 car commence à les classes commencent à zéro
classe_size = length(cla$csize);

title = paste('CC ', i, ' Q=', round(Q,3), ' taille=', classe_size,  sep='');
method = "fastgreedy"
vertex.size = 5
plot(G_list[[i]], layout=GLayout_list[[i]], vertex.color= membership, vertex.label=NA, vertex.size= vertex.size, main=title);
mtext(method, line=3, side=3);

#####################
#Commentaires:
#####################
#On remarque que le groupe de deux vert est peut être surdécoupé
# le groupe de jaune à un des relations avec les rouges et les oranges
# AU final, c'est assez difficile à interpréter

######################
#Vous pouvez suivre l'évolution de la modularité en fonction des pas de l'algorithme:

plot(com$modularity)

###################################
#Pour une meilleure présentation:
m <- matrix(c(1,1,2,1), 2, 2)
layout(m, widths=c(2,1), heights=c(1,2))
plot(G_list[[i]], layout=GLayout_list[[i]], vertex.color= membership, vertex.label=NA, vertex.size= vertex.size,main=title);
mtext(method, line=3, side=3);
plot(com$modularity, xlab='step', ylab='Q')
layout(matrix(1))

#Toutes les composantes connexes 
#Réaliser le découpage en communautés pour toutes le CC dont le taux de paralogie 
#est supérieur à 0.
#Nous utiliserons la fonction layout() pour découper notre graphique en 6 plages.
color = rainbow(12)
palette(color)
method = "fastgreedy"
vertex.size = 15
layout(matrix(1:6,2, 3))

fastgreedy <- function(){
  for (i in 1:nb_cc){
    if (paralogs[i,2] > 0) {
      com <- fastgreedy.community(G_list[[i]], merges=TRUE, modularity=TRUE);
      Q = max(com$modularity);
      #On sélectionne l'indice du tableau ayant la plus forte modularité
      Step <- which.max(com$modularity);
      #On associe une classe à chaque sommet du graphe
      cla <- community.to.membership(G_list[[i]], com$merges, membership=TRUE, csize=TRUE, steps=Step-1);
      membership = cla$membership+1; #+1 car commence à les classes commencent à zéro
      classe_size = length(cla$csize);
      title = paste('CC ', i, ' Q=', round(Q,3), ' taille=', classe_size,  sep='');
      plot(G_list[[i]], layout=GLayout_list[[i]], vertex.color= membership, vertex.label=NA, vertex.size= vertex.size, main=title);
      mtext(method, line=3, side=3);
    }
  }
}
fastgreedy()

#On voit que pour Q=0.084 (très petit) il n'y a pas besoin de redécouper.
# Le pb est que l'algo découpe automatiquement.
# Le numéro 3: le point central en orange est ce le bon découpage ?
# LE numero 4: 

#Représentation sous la forme de heatmap() 
hm <- function(meth){
  all_name <- V(graph)$name;
  cc_ass_class <- data.frame(row.names=all_name)
  cc_ass_class[,1:5] <- annotation[V(graph)$name, 1:5];
  cc_name_list <- c();
  for ( i in 1:nb_cc ) {
    list_name = V(cc[[i]])$name;          # liste des protéines présentes dans la CC
    cc_name_list <- append(cc_name_list, list_name);
    cc_ass_class[list_name,6] <- i;       # numéro de la CC
    cc_ass_class[list_name,7] <- i*100;   # combiner numéro de CC avec numéro de communauté
    
    if ( paralogs[i, 2] > 0 ) {
      com <- switch(meth,
                    fastgreedy.community(G_list[[i]], merges=TRUE, modularity=TRUE),
                    walktrap.community(G_list[[i]], merges=TRUE, modularity=TRUE),
      )
      Q = max(com$modularity);
      #On sélectionne l'indice du tableau ayant la plus forte modularité
      Step <- which.max(com$modularity);
      #On associe une classe à chaque sommet du graphe
      cla <- community.to.membership(G_list[[i]], com$merges, membership=TRUE, csize=TRUE, steps=Step-1);
      membership = cla$membership+1; #+1 car commence à les classes commencent à zéro
      classe_size = length(cla$csize);
      
      cc_ass_class[list_name,7] <- cc_ass_class[list_name,7]  + membership;
    }
  }
  
  dist_method <- 1; # jaccard
  #dist_method <- 9; # Phi of Pearson
  
  ########################################
  #Nous avons aussi le choix entre différentes méthodes de classification.
  clust_method <- 'ward';
  
  #################################
  #Application aux lignes de la matrice :
  color = c('white', 'black', 'grey');
  matrix = as.matrix(cc_ass_class[cc_name_list,])
  mat =table(matrix[,1], matrix[,7])
  mat[mat>1]=1 # recouvrement entre systèmes (certaines valeur dépasse 1, on les remet à 1)
  
  df = as.data.frame(mat[,1:ncol(mat)])
  d = dist.binary(df, method= dist_method, diag=TRUE, upper=TRUE);
  cd <- hclust(d, method='ward')
  tmat = t(mat)
  dft = as.data.frame(tmat[,1:ncol(tmat)])
  dt = dist.binary(dft, method= dist_method, diag=TRUE, upper=TRUE);
  cdt <- hclust(dt, method='ward')
  #################################
  #Nous allons maintenant essayer de découper nos systèmes en classes. 
  #Pour cela, nous allons utiliser la fonction cutree().
  nb_class = 7;
  ass_class = cutree(cd,  nb_class)
  mod_mat = mat* ass_class;
  color = c('white', rainbow(15));
  
  ###########################
  #Faire le même type de classification sur les colonnes de la matrice et utiliser heatmap() pour représenter les résultats.

  dev.new();
  heatmap(mod_mat,Colv=as.dendrogram(cdt),  Rowv=as.dendrogram(cd),  scale='none', col=color)
  
}
hm(1)

##################################
* Commentez le résultat obtenu.

#C'est encore un peu trop découpé, on retoruve des éclatements mais globalement on retoruve les
#corrélations entre partenaires. On peut aranger le sur/sosu découpage de la méthode
#fastgreedy.
#


#Walktrap
#La méthode walktrap peut être utilisée de façon similaire.

walktrap <- function(){
 for (i in 1:nb_cc){
    if (paralogs[i,2] > 0) {
      com <- walktrap.community(G_list[[i]], merges=TRUE, modularity=TRUE);
      Q = max(com$modularity);
      #On sélectionne l'indice du tableau ayant la plus forte modularité
      Step <- which.max(com$modularity);
      #On associe une classe à chaque sommet du graphe
      cla <- community.to.membership(G_list[[i]], com$merges, membership=TRUE, csize=TRUE, steps=Step-1);
      membership = cla$membership+1; #+1 car commence à les classes commencent à zéro
      classe_size = length(cla$csize);
      title = paste('CC ', i, ' Q=', round(Q,3), ' taille=', classe_size,  sep='');
      plot(G_list[[i]], layout=GLayout_list[[i]], vertex.color= membership, vertex.label=NA, vertex.size= vertex.size, main=title);
      mtext(method, line=3, side=3);
   }
  }
}
method = "walktrap"
walktrap()

#### HEATMAP de la Walktrap ############
hm(2)
###################################################
#Betweenness

#La méthode betweenness est un peu plus compliquée à utiliser car il est nécessaire 
#de reprendre les différentes partitions et de retenir celle qui maximise la modularité.
layout(matrix(1:6,2, 3))
betweeness <- function(){
  for (i in 1:nb_cc){
    list_name = V(cc[[i]])$name;          # liste des protéines présentes dans la CC
    cc_name_list <- append(cc_name_list, list_name);
    cc_ass_class[list_name,6] <- i;       # numéro de la CC
    cc_ass_class[list_name,7] <- i*100;   # combiner numéro de CC avec numéro de communauté
    if (paralogs[i,2] > 0) {
      com <- edge.betweenness.community(G_list[[i]]);
      end <- length(com$bridges);
      start <- end-20; # pour éviter de tout parcourir!
      
      com_to_member <- list();
      Qiter <- array(0, c(end));
      
      for ( k in start:end ) { 
        com_to_member[[k]] <- community.to.membership(G_list[[i]], com$merge, step=k);
        Qiter[k] <- modularity(G_list[[i]], com_to_member[[k]]$membership+1);
      }
      Q <- max(Qiter);
      Step <- which.max(Qiter);
      cla <- com_to_member[[Step]]$membership;
      
      membership = cla;
      classe_size = length(unique(membership));
      title = paste('CC ', i, ' Q=', round(Q,3), ' taille=', classe_size,  sep='');
      plot(G_list[[i]], layout=GLayout_list[[i]], vertex.color= membership, vertex.label=NA, vertex.size= vertex.size, main=title);
      mtext(method, line=3, side=3);
    }
  }
}
betweeness()


#SPINGLASS    
layout(matrix(1:6,2, 3))
method = 'spinglass';

# pour chaque itération conserver:
member_list <- list();      # le découpage en communautés 
Qiter <- array(0, c(iter)); # la modularité

spinglass <- function(){
# paramètres
iter <- 10;     # nombre d'itérations
my_gamma <- 1.0 # a faire varier!
for (i in 1:nb_cc){
  list_name = V(cc[[i]])$name;          # liste des protéines présentes dans la CC
  cc_name_list <- append(cc_name_list, list_name);
  cc_ass_class[list_name,6] <- i;       # numéro de la CC
  cc_ass_class[list_name,7] <- i*100;   # combiner numéro de CC avec numéro de communauté
  if (paralogs[i,2] > 0) {
    member_list <- list();      # le découpage en communautés 
    Qiter <- array(0, c(iter)); # la modularité

      for ( k in 1:iter )# boucle sur les itérations
      {
        com <- spinglass.community(G_list[[i]], 
                                   start.temp=1, stop.temp=0.1, cool.fact=0.95, 
                                   update.rule="config", gamma=my_gamma);
        member_list[[k]] <- com$membership+1;
        Qiter[k] <- com$modularity;
      }
      # chercher la modularité MAX et l'itération correspondante
      Q <- max(Qiter);
      Step <- which.max(Qiter);
      
      membership <- member_list[[Step]];
      classe_size <- length(unique(membership));
      
      title <- paste('CC', i, 'Q=', round(Q,3), 'taille=', classe_size, sep=' ');
      plot(G_list[[i]], layout=GLayout_list[[i]], vertex.color= membership , 
           vertex.label=NA, vertex.size=vertex.size, main=title);
      text <- paste(method, 'iteration=', iter, 'gamma=', my_gamma, sep=' ');
      mtext(text, line=3, side=3);
  }
}
}
spinglass()
# Donne l'impression de sur découper les CC 2  et 1
#Sinon les autres communautés sont mieux découpées que précédemment


#COMPARAISON DES METHODES   
       
# Boucle sur les méthodes
# conserver les résultats de chaque méthode pour chaque CC (utilisation de listes!)
all_name <- V(graph)$name;
cc_ass_class <- data.frame(row.names=all_name)
cc_ass_class[,1:5] <- annotation[V(graph)$name, 1:5];
cc_name_list <- c();

CC_compile_membership <- list();
CC_compile_modularity <- list();
CC_compile_classe_size <- list();

j <- 0;
for ( i in 1:nb_cc ) {
  if ( paralogs[i, 2] > 0 ) {
    
    j <- j + 1;
    Best_membership <- list();
    Best_modularity <- list();
    Best_classe_size <- list();
#########################################    
    method = 'fastgreedy';  
	
    com <- fastgreedy.community(G_list[[i]], merges=TRUE, modularity=TRUE);
    Q = max(com$modularity);
    #On sélectionne l'indice du tableau ayant la plus forte modularité
    Step <- which.max(com$modularity);
    #On associe une classe à chaque sommet du graphe
    cla <- community.to.membership(G_list[[i]], com$merges, membership=TRUE, csize=TRUE, steps=Step-1);
    membership = cla$membership+1; #+1 car commence à les classes commencent à zéro
    classe_size = length(cla$csize);
    #title = paste('CC ', i, ' Q=', round(Q,3), ' taille=', classe_size,  sep='');
    #plot(G_list[[i]], layout=GLayout_list[[i]], vertex.color= membership, vertex.label=NA, vertex.size= vertex.size, main=title);
    #mtext(method, line=3, side=3);

    Best_membership[[method]] <- cla$membership+1;
    Best_modularity[[method]] <- Q;
    Best_classe_size[[method]] <- length(cla$csize);
    
####################################################################    
    method = 'walktrap';
	

    com <- walktrap.community(G_list[[i]], merges=TRUE, modularity=TRUE);
    Q = max(com$modularity);
    #On sélectionne l'indice du tableau ayant la plus forte modularité
    Step <- which.max(com$modularity);
    #On associe une classe à chaque sommet du graphe
    cla <- community.to.membership(G_list[[i]], com$merges, membership=TRUE, csize=TRUE, steps=Step-1);
    membership = cla$membership+1; #+1 car commence à les classes commencent à zéro
    classe_size = length(cla$csize);
    #title = paste('CC ', i, ' Q=', round(Q,3), ' taille=', classe_size,  sep='');
    #plot(G_list[[i]], layout=GLayout_list[[i]], vertex.color= membership, vertex.label=NA, vertex.size= vertex.size, main=title);
    #mtext(method, line=3, side=3);
  
    Best_membership[[method]] <- cla$membership+1;
    Best_modularity[[method]] <- Q;
    Best_classe_size[[method]] <- length(cla$csize);

#################################################################### 
    method = 'betweenness';
	

  list_name = V(cc[[i]])$name;          # liste des protéines présentes dans la CC
  cc_name_list <- append(cc_name_list, list_name);
  cc_ass_class[list_name,6] <- i;       # numéro de la CC
  cc_ass_class[list_name,7] <- i*100;   # combiner numéro de CC avec numéro de communauté

    com <- edge.betweenness.community(G_list[[i]]);
    end <- length(com$bridges);
    start <- end-20; # pour éviter de tout parcourir!
    
    com_to_member <- list();
    Qiter <- array(0, c(end));
    
    for ( k in start:end ) { 
      com_to_member[[k]] <- community.to.membership(G_list[[i]], com$merge, step=k);
      Qiter[k] <- modularity(G_list[[i]], com_to_member[[k]]$membership+1);
    }
    Q <- max(Qiter);
    Step <- which.max(Qiter);
    cla <- com_to_member[[Step]]$membership;
    
    membership = cla;
    classe_size = length(unique(membership));
 
    Best_membership[[method]] <- cla +1;
    Best_modularity[[method]] <- Q;
    Best_classe_size[[method]] <- length(unique(cla));

####################################################################    
    method = 'spinglass';
	
iter <- 10;     # nombre d'itérations
my_gamma <- 0.75 # a faire varier!

  list_name = V(cc[[i]])$name;          # liste des protéines présentes dans la CC
  cc_name_list <- append(cc_name_list, list_name);
  cc_ass_class[list_name,6] <- i;       # numéro de la CC
  cc_ass_class[list_name,7] <- i*100;   # combiner numéro de CC avec numéro de communauté
  member_list <- list();      # le découpage en communautés 
  Qiter <- array(0, c(iter)); # la modularité
    
  for ( k in 1:iter )# boucle sur les itérations
  {
    com <- spinglass.community(G_list[[i]], 
                               start.temp=1, stop.temp=0.1, cool.fact=0.95, 
                               update.rule="config", gamma=my_gamma);
    member_list[[k]] <- com$membership+1;
    Qiter[k] <- com$modularity;
  }
  # chercher la modularité MAX et l'itération correspondante
  Q <- max(Qiter);
  Step <- which.max(Qiter);
  
  cla <- member_list[[Step]];
  cc_ass_class[list_name,7] <- cc_ass_class[list_name,7] + membership ; 

  Best_membership[[method]] <- cla;
  Best_modularity[[method]] <- Q;
  Best_classe_size[[method]] <- length(unique(cla));
    
  }
  
  CC_compile_membership[[j]] <- Best_membership;
  CC_compile_modularity [[j]] <- Best_modularity;
  CC_compile_classe_size [[j]] <- Best_classe_size;
}


#AFFICHAGE DES CC SUR 4 METHODES 

par(mar=c(4,4,4,4));
layout(matrix(1:24 , 4, 6));
vertex.size = 15;
j <- 0;
for ( i in 1:nb_cc ) {
  if ( paralogs[i, 2] > 0 ) {
    j <- j + 1;
    Best_membership <- CC_compile_membership[[j]];
    Best_modularity <- CC_compile_modularity [[j]];
    Best_classe_size <- CC_compile_classe_size [[j]];
    
    nb_method <- length(Best_membership);
    print(nb_method)
    names = names(Best_membership);
    
    for ( k in 1: nb_method  ) {
      method = names[k];
      title = paste('CC ', i, ' Q=', round(Best_modularity[[method]],3), ' taille=', Best_classe_size[[method]],  sep='');
      
      plot(G_list[[i]], layout=GLayout_list[[i]], vertex.color= Best_membership[[method]], vertex.label=NA, vertex.size= vertex.size, main=title);
      mtext(method, line=3, side=3);
    }
  }
}



#REPRESENTATION SOUS FORME DE HEATMAP 

#initialisations
par(mar=c(1,1,1,1))
all_name <- V(graph)$name;
names <- c('fastgreedy', 'walktrap', 'betweenness', 'spinglass');
nb_method <- length(names);
nb_class <- 7;
color <- c('white', rainbow(15));
dist_method <- 1;
dist_method <- 9;
cc_ass_class <- data.frame(row.names=all_name);
cc_ass_class[,1:5] <- annotation[V(graph)$name, 1:5];

for ( k in 1: nb_method )
{
  dev.new();
  method <- names[k];
  col = 5+k
  title <- paste('method ', method);
  cc_name_list <- c();
  j = 0;
  for ( i in 1:nb_cc ) 
  {
    list_name = V(cc[[i]])$name;
    cc_name_list <- append(cc_name_list, list_name);
    #cc_ass_class[list_name,6] <- i;
    cc_ass_class[list_name, col] <- i*100;
    if ( paralogs[i, 2] > 0 ) 
    {
      j <- j + 1;
      cc_ass_class[list_name, col] <- cc_ass_class[list_name, col] + CC_compile_membership[[j]][[k]];
    }
  };
  
  matrix <- as.matrix(cc_ass_class[cc_name_list,]);
  mat =table(matrix[,1], matrix[, col]);
  mat[mat>1]=1 
  
  df = as.data.frame(mat[,1:ncol(mat)]);
  d = dist.binary(df, method= dist_method, diag=TRUE, upper=TRUE);
  cd <- hclust(d, method='ward');
  tmat=t(mat);
  dft=as.data.frame(tmat[,1:ncol(tmat)]);
  dt=dist.binary(dft,method=dist_method, diag=TRUE, upper=TRUE);
  cdt <- hclust(dt,method='ward');
  ass_class = cutree(cd, nb_class);
  mod_mat = mat* ass_class;
  color = c('white', rainbow(15));
  heatmap(mod_mat,Colv=as.dendrogram(cdt), Rowv=as.dendrogram(cd), scale='none', col=color)
  print (method);
  for ( l in 1: nb_class) 
  {
    print (paste('classe ', l, ' taux paralogie ', count_paralogs(names( ass_class[ass_class == l])), sep=''))
  }
}



#exctraction

classCC <- matrix[,6:9]
rownames(classCC)
adjMat <- matrix(data = 0, ncol = nrow(classCC), nrow = nrow(classCC))

for (i in 1:nrow(classCC)){
   pos1 <- which(classCC[-(1:i),1] == classCC[i,1]) + i
   pos2 <- which(classCC[-(1:i),2] == classCC[i,2]) + i
   pos3 <- which(classCC[-(1:i),3] == classCC[i,3]) + i
   pos4 <- which(classCC[-(1:i),4] == classCC[i,4]) + i
   adjMat[i,pos1] <- adjMat[i,pos1] + 1
   adjMat[i,pos2] <- adjMat[i,pos2] + 1
   adjMat[i,pos3] <- adjMat[i,pos3] + 1
   adjMat[i,pos4] <- adjMat[i,pos4] + 1
}

test <- adjMat + t(adjMat)
head(test)
heatmap(adjMat, scale='none', col=color)

