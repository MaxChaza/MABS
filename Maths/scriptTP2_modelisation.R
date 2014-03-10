TP MODELISATION

Système discret : plantes annuelles 
simulation, sur 20 ans à partir de 100 plantes et d'un sol vierge

# Codage des paramètres du système (A, B et C) sous forme de liste  
## a(lpha): proportion de graines de l'année n-1 qui germent en mai
##b(eta): proportion de graines de l'année n-2 qui germent en mai
##g(amma): nombre de graines produites par une plante au mois d'aout
##s(igma): proportion de graines qui survivent à l'hiver
## les graines ont au plus 2 ans pour germer
## P0: population initiale
parameters <- c(a = 0.5, b =0.25, g=2, s=0.8)

# Codage de l'état du système (X, Y et Z) également sous forme de liste
state <- c(P=100, S1=0, S2=0)

# La fonction appliquant les équations d'état du système (calcul de P, S1 et S2)
plant<-function(t, state, parameters) {
  with(as.list(c(state, parameters)),{ #P<->Pn, S1 <->S1n, S2 <->S2n
    S1np1=P*g*s
    S2np1=s*(1-a)*S1
    Pnp1= S1np1*a+S2np1*b
    
    # rate of change
    dP = Pnp1 - P
    dS1 = S1np1 - S1
    dS2 =  S2np1 - S2
    
    # return the rate of change
    list(c(dP, dS1, dS2))
  })
  # end with(as.list ...
}

# durée de la simulation (doit commencer par 0)
times <- seq(0, 20, by = 1)
# résolution du système et simulation (Euler = discret)
out <- ode(y = state, times = times, func = plant, parms = parameters, method="euler")

head(out)
time      P      S1     S2
[1,]    0 100.00   0.000  0.000
[2,]    1  80.00 160.000  0.000
[3,]    2  80.00 128.000 64.000
[4,]    3  76.80 128.000 51.200
[5,]    4  74.24 122.880 51.200
[6,]    5  71.68 118.784 49.152

par(oma = c(0, 0, 3, 0))
plot(out, xlab = "années", ylab = "-")
plot(out[, "P"], out[, "S2"], pch = ".")
mtext(outer = TRUE, side = 3, "plant model", cex = 1.5)


######Cyanobactérie#########

parameters <- c(
  Bo=200,
  Co=2,
  p=6.3,
  a=0.207,
  b=0.063,
  f=4,
  g=25.2,
  k1=630,
  q=1.8,
  n=2,
  µ=6.3,
  m=4,
  h1=1,
  Î=15,
  epsilon1=0.07)
state <- c(X =0 , Y =0 , B=Bo, C=Co)

# La fonction appliquant les Ã©quations d'Ã©tat du systÃ¨me (calcul de dX, dY et dZ)
Cyanio<-function(t, state, parameters) {
  with(as.list(c(state, parameters)),{
    # rate of change
    dX <- p*a*(C-X)-b*X*(Y+f)
    dY<- g*(B-Y)-k1*Y*(X**n)/((q**n)+(X**n))
    dB <- epsilon1*(((Bo*Î)/(1+h1*(X**m)))-µ*B)
    dC <- epsilon1*(((Co*Î)/(1+h1*(X**m)))-µ*C)
    # return the rate of change
    list(c(dX, dY, dB, dC))
  })
  
}


out <- ode(y = state, times = times, func = Cyanio, parms = parameters)

head(out)
plot(out, xlab = "time", ylab = "-")

plot(out[,"time"],out[,"X"],xlab=" ",ylab=" ",type="l",col="red")
par(new=T)
plot(out[,"time"],out[,"Y"],axes=FALSE,xlab=" ",ylab=" ",type="l",col="blue")