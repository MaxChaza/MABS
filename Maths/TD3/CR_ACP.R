> U=iris[,-5]
> acp=princomp(U)
plot(acp$score[,1],acp$score[,2], pch=19, col=iris[,5])
> biplot(acp)
> summary(acp)
> Uc = apply(U, 2, function(X) X-mean(X)) #Centrer la matrice

> class(Uc)
[1] "data.frame" #Il faut préciser à R que Uc est une matrice
> Uc=as.matrix(Uc)
> class(Uc)
[1] "matrix"
> Ucov=(1/150)*(t(Uc)%*%Uc) # ou Ucov=cov(Uc)
> Ucov
             Sepal.Length Sepal.Width Petal.Length Petal.Width
Sepal.Length    9.0917819  -0.2071038     4.704326   -4.060016
Sepal.Width    -0.2071038   3.1189779     0.311150    3.520123
Petal.Length    4.7043263   0.3111500     5.910471    1.392238
Petal.Width    -4.0600162   3.5201228     1.392238    8.380934

> vp=eigen(Ucov) # récupérer les vecteurs propres de la matrice
> vp$values
[1] 4.20005343 0.24105294 0.07768810 0.02367619
> vp$values/ sum(vp$values)
[1] 0.924618723 0.053066483 0.017102610 0.005212184
> cumsum(vp$values)/ sum(vp$values)
[1] 0.9246187 0.9776852 0.9947878 1.0000000
> par(mfrow=c(1,2))
> plot(acp$score[,1],acp$score[,2], pch=19, col=iris[,5])

#Calcule des coefs de projection
> UProj=Uc %*% vp$vectors
> plot(UProj[,1],UProj[,2], pch=5, col=iris[,5])