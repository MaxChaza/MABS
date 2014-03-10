library(deSolve)
# Codage des paramètres du système (A, B et C) sous forme de liste
parameters <- c(a = 0.038/12)
# Codage de l'état du système (X, Y et Z) également sous forme de liste
state <- c(Mois=0, Du = 120000, Cout = 0, Interet = 0, Mensualite = 0, Assurance=19, Amortis = 0)

# La fonction appliquant les équations d'état du système (calcul de dX, dY et dZ)
syst<-function(t, state, parameters) {
  with(as.list(c(state, parameters)),{
    
    if (Mois == 0) Mensualite1 = 0
    if (0<=Mois && Mois<144) Mensualite1 = 619
    if (Mois >= 144) Mensualite1 = 891.13

    Interet1 = round(Du * a,2)
    Amortis1 = Mensualite1-Interet1-Assurance
    Du1 = Du - Amortis1
    Cout1 = Interet1+Cout
    Mois1 = Mois+1
    
    dMois = Mois1-Mois
    dCout = Cout1-Cout
    dDu = Du1-Du
    dMensualite = Mensualite1-Mensualite
    dInteret = Interet1-Interet
    dAmortis = Amortis1-Amortis
    dAssurance = 0
    
    # return the rate of change
    list(c(dMois,dDu,dCout,dInteret,dMensualite,dAssurance,dAmortis))
  })
  # end with(as.list ...
}

# durée de la simulation (doit commencer par 0)
times <- seq(0, 400, by = 1) 
# résolution du système et simulation
out <- ode(y = state, times = times, func = syst, parms = parameters, method = euler)

head(out)