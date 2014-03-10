# http://silico.biotoul.fr/site/index.php/M1_MABS_BBS_Math_TD_Proba
# load alignment
aln=read.table("bsub_rbs1.RBS")
aln
motifLength = ncol(aln)
nucleotides = c('A','C','G','T')
# frequency of each nucleotide at each position in an RBS
pNtPos = matrix( nrow=4, ncol=motifLength)
rownames(pNtPos) = nucleotides
for (i in nucleotides) for (j in 1:motifLength) pNtPos[i, j] = length(which(aln[,j]==i))/nrow(aln)
pNtPos
#   [,1] [,2] [,3] [,4] [,5]
# A 0.36 0.27 0.27 0.37 0.24
# C 0.08 0.05 0.03 0.05 0.06
# G 0.45 0.62 0.60 0.51 0.55
# T 0.11 0.06 0.10 0.07 0.15

# p(RBS+) : number of genes (~ number of RBS) divided by genome size
pRBS=4177/4215606
pRBS
# 0.0009908421 ~0.001

# frequency of each nucleotide in the genome
library(seqinr) # may need to install.packages('seqinr')
genome=summary(read.fasta('BsubA.fas')$BsubA01)
Pnt=as.vector(genome$composition / genome$length)
names(Pnt) = nucleotides
Pnt
#         A         C         G         T 
# 0.2818273 0.2180669 0.2170772 0.2830286 

# likelihood of a given sequence
################################
likelihood=function(seq, pBgivenA, pA, pB) { # returns pAgivenB
	res = pA
	for (i in 1:length(seq)) res = res * pBgivenA[seq[i],i] / pB[seq[i]]
	res
}

# test sequence
################
newseq=as.vector(t(read.table('sequence_test.txt',colClasses='character')[1,]))

len=length(newseq)-motifLength+1

likelihoods=vector(length=len)
for (i in 1:len) likelihoods[i]=likelihood(newseq[i:(i+motifLength-1)], pNtPos, pRBS, Pnt)
likelihoods
plot(1:len, likelihoods, type="b")

# Threshold to decide RBS presence
threshold = mean(likelihoods)+2*sd(likelihoods)
abline(h=threshold, col='red', lwd=3)
greens = which(likelihoods>=threshold)
points(greens, likelihoods[greens], col='green', pch=19)
