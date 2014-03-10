library(deSolve)
parameters <- c(a = 0.038/12)
# Codage de l'état du système également sous forme de liste
state <- c(mois = 0, du = 120000, cout = 0, interets = 0, mensualite = 0, amortis = 0)

# La fonction appliquant les équations d'état du système (calcul de dX, dY et dZ)
Lorenz<-function(t, state, parameters) {
  with(as.list(c(state, parameters)),{
    # rate of change
    moisnp1 = mois +1
    
    if (mois == 0) mensualitenp1 = 0
    if (0<=mois && mois<144) mensualitenp1 = 619
    if (mois >= 144) mensualitenp1 = 891.13
    
    
    interetsnp1 = round(du * a,2)
    
    coutnp1 = interetsnp1 + cout
    
    amortisnp1 = mensualitenp1 - interetsnp1
    
    dunp1 = du - amortisnp1
    
    
    dmois= moisnp1 - mois
    ddu = dunp1 - du
    dcout = coutnp1 - cout
    dinterets = interetsnp1 - interets
    damortis = amortisnp1 - amortis
    dmensualite = mensualitenp1 - mensualite
    # return the rate of change
    list(c(dmois, ddu, dcout, dinterets, dmensualite, damortis))
  })
  # end with(as.list ...
}

# durée de la simulation (doit commencer par 0)
times <- seq(0, 400, by = 1)
# résolution du système et simulation
out <- ode(y = state, times = times, func = Lorenz, parms = parameters, method = euler)

head(out)
out
