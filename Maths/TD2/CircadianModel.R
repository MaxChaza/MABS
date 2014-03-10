library(deSolve)
parameters <- c(m=4, C0=2, B0=200, n=2, p=6.3, a=0.207, b=0.063, f=4, 
g=25.2, k1=630, qu=1.8, l=15,h1=1, u=6.3, E1=0.07)
# Codage de l'état du système (X, Y et Z) également sous forme de liste
# 
#
state <- c(x=0, y=0, B=200 ,C=2)
 
CiradianModel<-function(t, state, parameters) {
	with(as.list(c(state, parameters)),{
		# rate of change
	
		dx = p*a*(C -x) - b*x*(y+f)
		dy = g*(B-y)-k1*y*x^n/(qu^n+x^n)
		dB = E1*(B0*l / (1 + h1*x^m)-u*B)
		dC = E1*(C0*l / (1 + h1*x^m)-u*C)
	
		# return the rate of change
		list(c(dx, dy, dB ,dC ))
	})
	# end with(as.list ...
}
 
# durée de la simulation (doit commencer par 0)
times <- seq(0, 120, by = 1)
# résolution du système et simulation
out <- ode(y = state, times = times, func = CiradianModel, parms = parameters)
 

head(out)
par(oma = c(0, 0, 3, 0))
plot(out, xlab = "time", ylab = "-")
plot(out[, "X"], out[, "Z"])