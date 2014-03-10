###CC Math 2012-12 
#http://silico.biotoul.fr/site/index.php/M1_MABS_CC_Math_2012-12

#Aide compréhension/deéfinition matrice: 
#http://www.unilim.fr/pages_perso/jean.debord/math/matrices/matrices.htm

###Calcul matriciel:
#1.Création de la matrice A:
A = matrix( c(1, -2, 3, 1, 1, 3, -3, 1, 2, 0, 3, 4, 4, 4, -2, 0), ncol=4, byrow=TRUE );A

#2.afficher la transposée:
TansposA=t(A);TansposA

#3.afficher sa trace :
Trace=sum(diag(A)); Trace

#4.Calcul du determinant :
determinant(t=det(A);determinant

#5. Est-elle singulière ?
#Si A**-1 n'existe pas, elle est singulière. C'est le cas si la matrice n'est pas carré et/ou que sont determinant est nul.            
solve(A)
#A**-1 existe par conséquent, Non elle n'est pas singulière, (matrice carré colonnes=lignes=4 et det=-40 donc matrice inversible).
            
#6. Est-elle régulière ?
#Oui s'il existe une matrice carré A**-1 telle que :
#A* A**-1 = A**-1 *A=I, (A**-1)**-1 = A
solve(solve(A))#doit être égal à A,ce qui à priori est le cas. 
A
#Donc oui la matrice est régulière.

#7. Est elle inversible?
#La matrice est singulière c'est donc qu'elle est inversible            
Ai=solve(A);Ai  
I = A %*% Ai;I # I: matrice identitée  
         
        
#8. Est-elle orthogonale ?
#La matrice est orthogonale si: 
#-Soit A**T =A**-1 
#-Soit det(A)= +/- 1
#det(A) =-40  et TansposAest différent de Ai en conséquent A n'est pas orthogonale.
            
#9.Est-elle symétrique ?
#Une matrice carré est dite symétrique si Aij=Aji, ce n'est pas le cas A n'est pas symétrique.

#10. Est-elle idempotente ?
#Une matrice idempotente est une matrice (carrée mais pas nécessairement symétrique) telle que AA=A.
A%*%A#c'est différent de A donc elle n'est pas idempotente.
            
#11. Résoudre le système :
b=c(2,1,0,-2);b
solve(A,b)
            
###modelisation           
library(deSolve)
#12. Editer le tableau d'amortissement

state <- c( mois = 0, du = 120000, cout = 0, interets = 0, echeance = 0, Assurances = 18, amortis = 0)

parameters <- c(a = 0.038/12)
            
# La fonction :
emprunt<-function(t, state, parameters) {
  with(as.list(c(state, parameters)),{
    
    # rate of change
    mois1 = mois +1
    
    if (mois == 0) mensualite1 = 0
    if (0<=mois && mois<144) mensualite1 = 619
    if (mois >= 144) mensualite1 = 891.13
    
    
    interets1 = round(du * a,2)
    
    cout1 = interets1 + cout
    
    amortis1 = mensualite1 - interets1 - Assurances
    
    du1 = du - amortis1
    
    
    dmois= mois1 - mois
    ddu = du1 - du
    dcout = cout1 - cout
    dinterets = interets1 - interets
    damortis = amortis1 - amortis
    dAssurances=0
    decheance = mensualite1 - echeance
    # return the rate of change
    list(c(dmois, ddu, dcout, dinterets, decheance, dAssurances, damortis))
  })
  # end with(as.list ...
}          

times <- seq(0, 400, by = 1)            
            
out <- ode(y = state, times = times, func = emprunt, parms = parameters, method = euler)
#On fait notre test selon la method euler car le pas de temps est discret

head(out)
round(out,2)            
 
#13.Au bout de combien de mois, le prêt sera-t-il remboursé 
#Le prêt sera payé au bout du252 ième mois c'est à dire après 21 ans.
#14. Les interets total:

times <- seq(0, 252, by = 1)            
            
out <- ode(y = state, times = times, func = emprunt, parms = parameters, method = euler)

head(out)
round(out,2) 
#->60841.96???
        
#15. Quel est le coût total du prêt (ce que le particulier a payé en plus des 120 000???) ?
#les intérets + les assurances :
cout_total=60841.96+252*18;cout_total    
#16. Pour un prêt à 3,5% (on change les paramètres):
parameters <- c(a = 0.035/12)            
            
emprunt<-function(t, state, parameters) {
  with(as.list(c(state, parameters)),{
    
    # rate of change
    mois1 = mois +1
    
    if (mois == 0) mensualite1 = 0
    if (0<=mois && mois<144) mensualite1 = 619
    if (mois >= 144) mensualite1 = 891.13
    
    
    interets1 = round(du * a,2)
    
    cout1 = interets1 + cout
    
    amortis1 = mensualite1 - interets1 - Assurances
    
    du1 = du - amortis1
    
    
    dmois= mois1 - mois
    ddu = du1 - du
    dcout = cout1 - cout
    dinterets = interets1 - interets
    damortis = amortis1 - amortis
    dAssurances=0
    decheance = mensualite1 - echeance
    # return the rate of change
    list(c(dmois, ddu, dcout, dinterets, decheance, dAssurances, damortis))
  })
  # end with(as.list ...
}          

times <- seq(0, 252, by = 1)            
            
out <- ode(y = state, times = times, func = emprunt, parms = parameters, method = euler)

#On fait notre test selon la method euler car le pas de temps est discret

head(out)
            
round(out,2)  

#On obtient un remboursement en 244 mois soit 20 ans et un trimestre.
#Combien aurait-il économisé:
economie=cout_total-(53219.86+18*244);economie

###Proba:
            
aln=read.table("CRP.TFBS.alignment.txt")
aln
motifLength = ncol(aln)
nucleotides = c('A','C','G','T')            
            
pNtPos = matrix( nrow=4, ncol=motifLength)
rownames(pNtPos) = nucleotides
for (i in nucleotides) 
  for (j in 1:motifLength) 
    pNtPos[i, j] = length(which(aln[,j]==i))/nrow(aln)
pNtPos   #On obtient une matrice avec la probabilité de chaque nucléotide à chaque position
            
#donné extraite d'internet:
PCRP=5092/5130000;PCRP #Nbre de gènes sur nombre de base (taille du génome)

library(seqinr)
freq=matrix(c(0.247,0.253,0.253,0.247),ncol=4,byrow=T);freq 
#colnames(freq)=c("A","C","G","T")            

Pnt=as.vector(freq)
names(Pnt) = nucleotides
Pnt

#genome=summary(read.fasta('BsubA.fas')$BsubA01)
#Pnt=as.vector(genome$composition / genome$length)
            
# likelihood of a given sequence
################################
likelihood=function(seq, pBgivenA, pA, pB) { # returns pAgivenB
  res = pA
	for (i in 1:length(seq)) 
    res = res * pBgivenA[seq[i],i] / pB[seq[i]]
	res
}            

            # test sequence
################
newseq=as.vector(t(read.table('CRP.sequence.test.txt',colClasses='character')[1,]))
            
len=length(newseq)-motifLength+1

likelihoods=vector(length=len)
for (i in 1:len) 
  likelihoods[i]=likelihood(newseq[i:(i+motifLength-1)], pNtPos, PCRP, Pnt)
likelihoods
plot(1:len, likelihoods, type="b")            
            
# Threshold to decide RBS presence
threshold = mean(likelihoods)+2*sd(likelihoods)
abline(h=threshold, col='red', lwd=3)
greens = which(likelihoods>=threshold)
points(greens, likelihoods[greens], col='green', pch=19)            
            