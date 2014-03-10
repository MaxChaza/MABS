
### Simulation des fréquences alléliques sous sélection #####################################

#Génotypes AA, Aa, aa de fitness w1, w2, w3
w1=0.5
w2=1
w3=0.5
ngen=100			# nombre de générations de sélection
AA=Aa=aa=A=a=w_bar=c()
A[1]=0.1			# fréquences initiales
a[1]=1-A[1]			# fréquences initiales

# calculs des fréquences génotypiques et alléliques à chaque génération de sélection
for (i in 2:ngen){
w_bar[i]=(w1*A[i-1]^2)+(w2*2*A[i-1]*a[i-1])+(w3*a[i-1]^2)
AA[i]=(w1*A[i-1]^2)/w_bar[i]
Aa[i]=(w2*2*A[i-1]*a[i-1])/w_bar[i]
aa[i]=(w3*a[i-1]^2)/w_bar[i]
A[i]=((w1*A[i-1]^2)/w_bar[i])+((w2*A[i-1]*a[i-1])/w_bar[i])
a[i]=((w3*a[i-1]^2)/w_bar[i])+((w2*A[i-1]*a[i-1])/w_bar[i])
}

# trajectoires des fréquences de chaque allèle et génotypes
gen=seq(1,ngen,1)
plot(gen,A,col="red",ylim=c(0,1),type="l",lwd=3)	# allèle A
lines(gen,a,col="green",lwd=3)				# allèle a		
points(gen,AA,lty=3,lwd=2,col="red")			# génotype AA			
points(gen,Aa,lty=2,lwd=2,col="blue")			# génotype Aa
points(gen,aa,lty=3,lwd=2,col="green")			# génotype aa
#lines(gen,w_bar,col="magenta")


freq_equ = (w3-w2)/(w1-2*w2+w3); freq_equ
A[ngen]



### Fst et dérive/migration
nsim=1000
F_ST=m=N=c()
for (i in 1:nsim){
m[i]=runif(1,0,1)
N[i]=sample(1:100,1)
F_ST[i]=1/(1+4*N[i]*m[i])
}
plot(N*m,F_ST)


### Fst et dérive pure
N=100
t=seq(1,1000,1)
F_ST=c()
for (i in 1:length(t)){
F_ST[i]=1-(1-1/(2*N))^t[i]
}
plot(t,F_ST)



