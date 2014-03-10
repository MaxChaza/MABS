A = matrix(c(1,-2,3,1,1,3,-3,1,2,0,3,4,4,4,-2,0),nrow=4,byrow=T)
t(A)
sum(diag(A))
det(A)
#La matrice A n'est pas singulière car son déterminant est non nul
#Quand le déterminant est non nul on dit que la matrice
#est régulière. Donc A est régulière.

#A est inversible car elle est régulière

InverseDeA = solve(A); InverseDeA

#Vérification:
I = A %*% InverseDeA;I
# I: matrice identité

#Orthogonale?
TA = t(A)%*%A; TA
AT = A%*%t(A); AT
# A n'est pas orthogonale

#Symétrique?
A
t(A)
#A n'est pas symétrique

#Idempotente?
A%*%A
A
#La matrice A n'est pas idempotente

#Résolution du système d'équation à 4 inconnues:

library(deSolve)

# La fonction appliquant les équations d'état du système (calcul de dX, dY et dZ)
Lorenz<-function(x1, x2, x3, x4) {
  with(as.list(c(state, parameters)),{
    # rate of change
    x1 - 2*x2 + 3*x3 + x4 = 2
    x1 + 3*x2 - 3*x3 + x4 = 1
    2*x1 + 3*x3 + 4*x4 = 0
    4*x1+4*x2-2*x3 = -2
    # return the rate of change
    list(c(x1, x2, x3, x4))
  })
  # end with(as.list ...
}



