import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class EcouterBoutonParcourir implements ActionListener{
	
	String nom;
	
	public EcouterBoutonParcourir(String nom)
	{
		this.nom = nom;
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		System.out.println("Bien reçu par"+nom);
	}
	

}
