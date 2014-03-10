library(deSolve)
aparameters <- c(g= 2, a = 0.5, b= 0.25, s=0.8)
# Codage de l'état du système (X, Y et Z) également sous forme de liste
state <- c(P = 100, S1=0 ,S2=0)
 
Plantes<-function(t, state, parameters) {
	with(as.list(c(state, parameters)),{
		# rate of change
		S1np1=P*g*s
		S2np1=S1*(1-a)*s
		Pnp1=S1np1*a + S2np1*b
		
		dP = Pnp1-P
		dS1= S1np1 - S1
		dS2= S2np1 - S2
		
		
		
		# return the rate of change
		list(c(S1np1, S2np1, Pnp1))
	})
	# end with(as.list ...
}
 
# durée de la simulation (doit commencer par 0)
times <- seq(0, 20, by = 1)
# résolution du système et simulation
out <- ode(y = state, times = times, func = Plantes, method="euler", parms = parameters)
 
head(out)
par(oma = c(0, 0, 3, 0))
plot(out, xlab = "time", ylab = "-")
plot(out[, "X"], out[, "Z"], pch = ".")
mtext(outer = TRUE, side = 3, "Lorenz model", cex = 1.5)

