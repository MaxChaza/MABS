/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.view;

import biobook.controller.GererChercheur;
import biobook.model.Chercheur;
import biobook.util.BioBookException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

/**
 *
 * @author Maxime
 */
public class MainFrame extends JFrame implements ActionListener{
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    
    private Chercheur chercheurConnecte;
    Dimension tailleFenetre;
    JPanel centre = new JPanel(new BorderLayout());
    Color inactif = new Color(240,240,240);
    Color actif = new Color(184,207,229);
    

    private JMenuBar jJMenuBar;
    private JMenu jEspace;
    
    private JMenu jPerso;
    private JMenuItem jExpPerso;
    private JMenuItem jDonneesPerso;
    
    private JMenu jEditer;
    
    private JMenu jCreation;
    private JMenuItem jCreerMateriel;
    private JMenuItem jCreeExp;
    
   
//    private JMenu jPreference = new JMenu();
//    private JMenuItem jDelMeasure = new JMenuItem();
    private JMenuItem jQuitter;


//    private JMenu jAide = new JMenu();

    //Onglet EspacePerso
    private EspacePersoView espacePerso;

    //Onglet General
    private GeneralView general;
    private GererChercheur gererChercheur;
    
    public MainFrame(String log) throws BioBookException, IOException, FileNotFoundException, ClassNotFoundException{
        gererChercheur = new GererChercheur();
        chercheurConnecte = gererChercheur.getChercheur(log);
       // Caractéristiques JFrame
        tailleFenetre = new Dimension((int)((Toolkit.getDefaultToolkit().getScreenSize().width)*4/5),(int)((Toolkit.getDefaultToolkit().getScreenSize().height-41)* 4/5));
        Dimension tailleEcran = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-41);
        setSize(tailleFenetre);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        espacePerso = new EspacePersoView(this);
        general = new GeneralView(this);
        
        jJMenuBar = new JMenuBar();
        
        // JMenu Espace
        jEspace = new JMenu("Espace");
        
        jPerso = new JMenu("Personel");
        jCreeExp = new JMenuItem("Créer expériences");
        jDonneesPerso = new JMenuItem("Données personnelles");
        jExpPerso = new JMenuItem("Expériences personnelles");
        
        jQuitter = new JMenuItem("Quitter");
        
        jEspace.add(jPerso);
        
        jPerso.add(jDonneesPerso);
        jPerso.add(jCreeExp);
        jPerso.add(jExpPerso);
        jPerso.add(jDonneesPerso);
        
        jEspace.add(new JSeparator());
        jEspace.add(jQuitter);
        jJMenuBar.add(jEspace);

        // JMenu Editer
        jEditer = new JMenu("Edition");
        jCreation = new JMenu("Création");
        jCreeExp = new JMenuItem("Créer expérience");
        jCreeExp.addActionListener(this);
        jCreerMateriel = new JMenuItem("Créer materiel");
        jCreerMateriel.addActionListener(this);
        
        jCreation.add(jCreeExp);
        jCreation.add(jCreerMateriel);
        jEditer.add(jCreation);
        jJMenuBar.add(jEditer);
        
        add(jJMenuBar,BorderLayout.NORTH);   
        add(espacePerso,BorderLayout.CENTER);
        // Composants de la fenetre   
         // Nouvel icone de l'application
        setIconImage(getToolkit().getImage("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\logoFrame.gif"));
        this.setTitle("BioBook");
        setLocation((int)((tailleEcran.getWidth()/2)-(getSize().getWidth()/2)), (int)((tailleEcran.getHeight()/2)-(getSize().getHeight()/2)));
        setMinimumSize(tailleFenetre);
        setVisible(true);        
    }
    
     public void setChercheurConnecte(String login) {
        this.chercheurConnecte = chercheurConnecte;
    }

    public Dimension getTailleFenetre() {
        return tailleFenetre;
    }

    public void setTailleFenetre(Dimension tailleFenetre) {
        this.tailleFenetre = tailleFenetre;
    }

    public void setEspacePerso(EspacePersoView espacePerso) {
        this.espacePerso = espacePerso;
    }

    public void setGeneral(GeneralView general) {
        this.general = general;
    }
    
    public Chercheur getChercheurConnecte() {
        return chercheurConnecte;
    }

    public EspacePersoView getEspacePerso() {
        return espacePerso;
    }

    public GeneralView getGeneral() {
        return general;
    }

    public GererChercheur getGererChercheur() {
        return gererChercheur;
    }

    public void setGererChercheur(GererChercheur gererChercheur) {
        this.gererChercheur = gererChercheur;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jCreeExp)
        {
            try {
                CreerExperienceView c = new CreerExperienceView(this);
            } catch (BioBookException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(e.getSource()==jCreerMateriel)
        {
                CreerMaterielView c = new CreerMaterielView(this);
            
        }
    }
}

