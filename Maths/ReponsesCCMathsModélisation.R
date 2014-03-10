library(deSolve)
parameters <- c(a = 0.038/12, b = 601)
# Codage de l'état du système également sous forme de liste
state <- c(du = 120000, cout = 0, interets = 0, amortis = 0)

# La fonction appliquant les équations d'état du système (calcul de dX, dY et dZ)
Lorenz<-function(t, state, parameters) {
  with(as.list(c(state, parameters)),{
    # rate of change
    print(paste("du=",du))
    interetsnp1 = round(du * a,2)
    print(paste("interetsnp1=",interetsnp1))
    coutnp1 = interetsnp1 + cout
    print(paste("coutnp1=",coutnp1))
    amortisnp1 = b - interetsnp1
    print(paste("amortisnp1=",amortisnp1))
    dunp1 = du - amortisnp1
    
    ddu = dunp1 - du
    dcout = coutnp1 - cout
    dinterets = interetsnp1 - interets
    damortis = amortisnp1 - amortis
    # return the rate of change
    list(c(ddu, dcout, dinterets, damortis))
  })
  # end with(as.list ...
}

# durée de la simulation (doit commencer par 0)
times <- seq(0, 2, by = 1)
# résolution du système et simulation
out <- ode(y = state, times = times, func = Lorenz, parms = parameters, method = euler)

head(out)
