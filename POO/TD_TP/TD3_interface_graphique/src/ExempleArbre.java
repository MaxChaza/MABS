import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ExempleArbre extends JFrame{
	
	public ExempleArbre()
	{
		super("Exemple arbre");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Container c=getContentPane();
		c.setLayout (new BorderLayout()); //on peut faire ça car c'est la seule fois qu'on s'en sert
		
		//création du panneau central
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new GridLayout(2,1));
		
		//création du panneau login
		JPanel panelLogin = new JPanel(new FlowLayout(FlowLayout.LEFT));//on ajoute les composants à gauche
		JLabel login = new JLabel("Login");
		JTextField textfield = new JTextField(20);
		panelLogin.add(login);
		panelLogin.add(textfield);
		
		
		//création du panneau password
		JPanel panelPwd = new JPanel(new FlowLayout(FlowLayout.LEFT));//on ajoute les composants à gauche
		JLabel pwd = new JLabel("Password");
		JPasswordField passwordfield = new JPasswordField(20);
		panelPwd.add(pwd);
		panelPwd.add(passwordfield);
		
		panelCentral.add(panelLogin);
		panelCentral.add(panelPwd);
		//Fin de création du panneau central
		
		//Création de l'image
		
		ImageIcon ii= new ImageIcon("index.jpg");
		Image i = ii.getImage().getScaledInstance(80,-1,Image.SCALE_DEFAULT);
		JLabel labelImage = new JLabel(new ImageIcon(i));
		
		//création du panneau de bouton
		JPanel panelBouton= new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton bAnnuler = new JButton("Annuler");
		JButton bValider = new JButton("Valider");
		
		panelBouton.add(bAnnuler);
		panelBouton.add(bValider);
		
		c.add(labelImage,BorderLayout.WEST);
		c.add(panelBouton,BorderLayout.SOUTH);
		c.add(panelCentral,BorderLayout.CENTER);
		
		
		setBounds(200,200,300,200);
		setVisible(true);
		
	}
	
	public static void main(String[]args)
	{
		new ExempleArbre();
	}
	

}
