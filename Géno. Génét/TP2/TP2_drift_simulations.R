

#### Forward simulation of genetic drift for a biallelic locus, with constant population size, on a diploid organism ######

nbsim=1000	# number of simulations (number of populations simulated)
nbgen=100	# generation number
n=1000	# population size
p0 = 0.1	# initial frequency
freq=list()	# replicates of allele frequencies over generations


for (i in c(1:nbsim)){
	freq[[i]]=prob=p0
	for (j in c(0:nbgen)){
		all=rbinom(1, n, prob)
		prob=all/n
		freq[[i]][j+1]=prob
		}
	}

#### Summary stats calculated from the data
mat = matrix(unlist(freq),nr=nbsim,byrow=T) # matrix (row=simulation, column=allelic frequency each generation)

mean.freq = apply(mat,2,mean);mean.freq	# E(p_t) each generation 
var.freq  = apply(mat,2,var);var.freq	# Var(p_t) each generation 
het.freq =  2*mat*(1-mat)
het =  apply(het.freq,2,mean);het
inb  = var.freq/(p0*(1-p0));inb		# inbreeding coefficient each generation (f_t)

#### Mathematical expectations
exp.mean = p0							# E(p_t) = p0 
exp.var = p0*(1-p0)*(1-(1-(1/(2*n)))^seq(0,nbgen,1)) 	# Var(p_t)= p0*(1-p0)*(1-(1-1/(2*n))^t)
exp.het = 2*p0*(1-p0)*(1-(1/(2*n)))^seq(0,nbgen,1)	# He(t) = 2*p0*(1-p0)*(1-1/(2*n))^t
exp.inb = (1-(1-(1/(2*n)))^seq(0,nbgen,1))*(1-0.001)			# expected inbreeding coefficient assuming inb_0 = 0 which is not true
# if He_0 = 100% => inb = 0 à la génération 0. But here we have He_0 = 0.5 maximum so inb_0 is not 0)


### Graphes with population frequencies trajectories

par(mfrow=c(1,2))

plot(seq(0,nbgen,1),freq[[i]], ylim=c(0,1),xlab="generation number",ylab="allele frequency", type="l",main="calculated summary stat")
for (i in c(1:nbsim)){
lines(seq(0,nbgen,1),freq[[i]], ylim=c(0,1))
}
lines(seq(0,nbgen,1),mean.freq, col="red",pch=16,lwd=2,lty=2)
lines(seq(0,nbgen,1),mean.freq+var.freq, col="red",lwd=2,lty=2)
lines(seq(0,nbgen,1),mean.freq-var.freq, col="red",lwd=2,lty=2)
lines(seq(0,nbgen,1),het, col="green",lwd=2,lty=2)
lines(seq(0,nbgen,1),inb, col="purple",lwd=2,lty=2)


plot(seq(0,nbgen,1),freq[[i]], ylim=c(0,1),xlab="generation number",ylab="allele frequency", type="l",main="expected summary stat")
for (i in c(1:nbsim)){
lines(seq(0,nbgen,1),freq[[i]], ylim=c(0,1))
}
abline(h=exp.mean, col="red",lwd=2)
lines(seq(0,nbgen,1),exp.mean+exp.var, col="red",lwd=2)
lines(seq(0,nbgen,1),exp.mean-exp.var, col="red",lwd=2)
lines(seq(0,nbgen,1),exp.het, col="green",lwd=2)
lines(seq(0,nbgen,1),exp.inb, col="purple",lwd=2)



### Graphes with only summary stat population frequencies trajectories
par(mfrow=c(1,2))

plot(seq(0,nbgen,1),mean.freq, col="red",pch=16,lwd=2,lty=2, ylim=c(0,1),main="calculated summary stat")
lines(seq(0,nbgen,1),mean.freq+var.freq, col="red",lwd=2,lty=2)
lines(seq(0,nbgen,1),mean.freq-var.freq, col="red",lwd=2,lty=2)
lines(seq(0,nbgen,1),het, col="green",lwd=2,lty=2)
lines(seq(0,nbgen,1),inb, col="purple",lwd=2,lty=2)

plot(seq(0,nbgen,1),exp.mean+exp.var, col="red",lwd=2, ylim=c(0,1),main="expected summary stat")
lines(seq(0,nbgen,1),exp.mean-exp.var, col="red",lwd=2)
lines(seq(0,nbgen,1),exp.het, col="green",lwd=2)
lines(seq(0,nbgen,1),exp.inb, col="purple",lwd=2)
abline(h=exp.mean, col="red",lwd=2)

































