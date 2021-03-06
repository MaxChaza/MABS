
### Simulation des fr�quences all�liques sous s�lection #####################################

#G�notypes AA, Aa, aa de fitness w1, w2, w3
w1=0.5
w2=1
w3=0.5
ngen=100			# nombre de g�n�rations de s�lection
AA=Aa=aa=A=a=w_bar=c()
A[1]=0.1			# fr�quences initiales
a[1]=1-A[1]			# fr�quences initiales

# calculs des fr�quences g�notypiques et all�liques � chaque g�n�ration de s�lection
for (i in 2:ngen){
w_bar[i]=(w1*A[i-1]^2)+(w2*2*A[i-1]*a[i-1])+(w3*a[i-1]^2)
AA[i]=(w1*A[i-1]^2)/w_bar[i]
Aa[i]=(w2*2*A[i-1]*a[i-1])/w_bar[i]
aa[i]=(w3*a[i-1]^2)/w_bar[i]
A[i]=((w1*A[i-1]^2)/w_bar[i])+((w2*A[i-1]*a[i-1])/w_bar[i])
a[i]=((w3*a[i-1]^2)/w_bar[i])+((w2*A[i-1]*a[i-1])/w_bar[i])
}

# trajectoires des fr�quences de chaque all�le et g�notypes
gen=seq(1,ngen,1)
plot(gen,A,col="red",ylim=c(0,1),type="l",lwd=3)	# all�le A
lines(gen,a,col="green",lwd=3)				# all�le a		
points(gen,AA,lty=3,lwd=2,col="red")			# g�notype AA			
points(gen,Aa,lty=2,lwd=2,col="blue")			# g�notype Aa
points(gen,aa,lty=3,lwd=2,col="green")			# g�notype aa
#lines(gen,w_bar,col="magenta")


freq_equ = (w3-w2)/(w1-2*w2+w3); freq_equ
A[ngen]



### Fst et d�rive/migration
nsim=1000
F_ST=m=N=c()
for (i in 1:nsim){
m[i]=runif(1,0,1)
N[i]=sample(1:100,1)
F_ST[i]=1/(1+4*N[i]*m[i])
}
plot(N*m,F_ST)


### Fst et d�rive pure
N=100
t=seq(1,1000,1)
F_ST=c()
for (i in 1:length(t)){
F_ST[i]=1-(1-1/(2*N))^t[i]
}
plot(t,F_ST)



