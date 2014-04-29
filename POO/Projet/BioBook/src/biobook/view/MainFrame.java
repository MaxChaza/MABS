/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

/**
 *
 * @author Maxime
 */
public class MainFrame extends JFrame{
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    
    private String login;
    private String name;
    JPanel centre = new JPanel(new BorderLayout());
    Color inactif = new Color(240,240,240);
    Color actif = new Color(184,207,229);
    JTabbedPane tabbedPane = new JTabbedPane();

//    private JMenuBar jJMenuBar = new JMenuBar();
//    private JMenu jFichier = new JMenu();
//    private JMenu jPreference = new JMenu();
//    private JMenuItem jNewCateg = new JMenuItem();
//    private JMenuItem jEditCateg = new JMenuItem();
//
//    private JMenuItem jQuitter = new JMenuItem();
//    private JMenuItem jDelMeasure = new JMenuItem();
//
//
//    private JMenu jAide = new JMenu();

    //Onglet EspacePerso
    private EspacePersoView espacePerso;

    //Onglet General
    private GeneralView general;

    public MainFrame(String nom, String log){
        name = nom;
        login = log;
        // Caractéristiques JFrame
        Dimension tailleEcran = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-60);
        setSize(tailleEcran);
        isResizable();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
//        tabbedPane.setUI(new SeaGlassTabbedPaneUI();
        
        tabbedPane.setPreferredSize(new Dimension(320, 200));
        // Mise enplace des onglets
        espacePerso = new EspacePersoView(this);
        general = new GeneralView(this);
        tabbedPane.addTab("Espace personnel", espacePerso);
        tabbedPane.addTab("Général", general);
        tabbedPane.setBackgroundAt(0, Color.RED);
        tabbedPane.setBackgroundAt(1, Color.BLUE);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Composants de la fenetre
        
        setVisible(true);
    }
    
}

