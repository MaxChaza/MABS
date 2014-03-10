import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InterfaceGraphique extends JFrame{
	
	public InterfaceGraphique()
	{
		super("Ajouter Livre");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Container c=getContentPane();
		
		//Couverture
		JLabel labelCouverture = new JLabel("Couverture");
		JTextField textfieldCouverture = new JTextField(20);
		JButton boutonParcourir = new JButton("Parcourir");
		JButton boutonParcourir2 = new JButton("Parcourir2");
		
		boutonParcourir2.setActionCommand("bouton2");
		boutonParcourir2.addActionListener(this);
		boutonParocurir2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				textfieldCouverture.setText(t);
			}
		}
		
		boutonParcourir.setActionCommand("bouton1");
		EcouterBoutonParcourir ebp1 =new EcouterBoutonParcourir("Ecouter 1");
		boutonParcourir.addActionListener(ebp1);
		EcouterBoutonParcourir ebp2 =new EcouterBoutonParcourir("Ecouter 2");
		boutonParcourir.addActionListener(ebp2);
		boutonParcourir.addActionListener(this);
		
		Box ligne1= new Box(BoxLayout.X_AXIS);
		ligne1.add(labelCouverture);
		ligne1.add(textfieldCouverture);
		ligne1.add(boutonParcourir);
		ligne1.add(boutonParcourir2);
		
		//Titre
		JLabel labelTitre = new JLabel("Titre");
		JTextField textfieldTitre = new JTextField(20);
		
		Box ligne2= new Box(BoxLayout.X_AXIS);
		ligne2.add(labelTitre);
		ligne2.add(textfieldTitre);
		
		//Auteur
		JLabel labelAuteur = new JLabel("Auteur");
		JTextField textfieldAuteur = new JTextField(20);
		
		Box ligne3= new Box(BoxLayout.X_AXIS);
		ligne3.add(labelAuteur);
		ligne3.add(textfieldAuteur);
		
		//Résumé
		JLabel labelResume = new JLabel("Résumé");
		JTextField textfieldResume= new JTextField(200);
		
		Box ligne4= new Box(BoxLayout.X_AXIS);
		ligne4.add(labelResume);
		ligne4.add(textfieldResume);
		
		//Categorie
		Box ligne5= new Box(BoxLayout.X_AXIS);
		JLabel labelCategorie = new JLabel("Categorie");
		ligne5.add(labelCategorie);
		ButtonGroup bg = new ButtonGroup();
		for(Categories cate: Categories.values())
		{
			JRadioButton rb = new JRadioButton(cate.toString());
			ligne5.add(rb);
			bg.add(rb);
		}
		
		//Thème
		
		Box ligne6= new Box(BoxLayout.X_AXIS);
		JLabel labelTheme = new JLabel("Thèmes");
		ligne6.add(labelTheme);
		for(Themes th: Themes.values())
		{
			JCheckBox chb = new JCheckBox(th.toString());
			ligne6.add(chb);
			
		}
		
		//Nombre de pages
		JLabel labelPages = new JLabel("Nb Pages");
		JTextField textfieldPages= new JTextField(20);
		
		Box ligne7= new Box(BoxLayout.X_AXIS);
		ligne7.add(labelPages);
		ligne7.add(textfieldPages);
		
		//bouton annuler valider
		
		JPanel panelBouton= new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton bAnnuler = new JButton("Annuler");
		JButton bValider = new JButton("Valider");
		
		panelBouton.add(bAnnuler);
		panelBouton.add(bValider);
		
		Box ligne8=new Box(BoxLayout.X_AXIS);
		ligne8.add(panelBouton);
		
		

		c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
		c.add(ligne1);
		c.add(Box.createVerticalGlue());
		c.add(ligne2);
		c.add(ligne3);
		c.add(ligne4);
		c.add(ligne5);
		c.add(ligne6);
		c.add(ligne7);
		c.add(ligne8);
		
		
		setLocation(200,100);
		pack();
		setVisible(true);
	}
	
	public static void main(String[]args)
	{
		new InterfaceGraphique();
		
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getActionCommand().equals("bouton1"))
			System.out.println("Bien reçu par l'interface!");
		if(arg0.getActionCommand().equals("bouton2"))
			System.out.println("Envoyé par le bouton 2");
	}

}
