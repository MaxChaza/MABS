/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.view;

import biobook.controller.EspacePersoController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Maxime
 */

public class EspacePersoView extends JPanel{
    private MainFrame main;
    ExpPersoView panelExpPerso;
    ParamPersoView panelParamPerso;
    public EspacePersoView(MainFrame m)
    {
        main = m;
        setLayout(new BorderLayout());
        
        panelExpPerso = new ExpPersoView(this);
        panelParamPerso = new ParamPersoView(this);
        JTabbedPane tabbedPane = new JTabbedPane();
        
//        Dimension tailleEcran = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-41);
//        tabbedPane.setPreferredSize(tailleEcran);
        // Mise enplace des onglets
        tabbedPane.addTab("Parametres personnelles", panelParamPerso);
        tabbedPane.addTab("Experiences personnelles", panelExpPerso);
        
        tabbedPane.setBackgroundAt(0, Color.RED);
        tabbedPane.setBackgroundAt(1, Color.BLUE);
        add(tabbedPane, BorderLayout.CENTER);
    }

    public MainFrame getMain() {
        return main;
    }

    public void setMain(MainFrame main) {
        this.main = main;
    }
    
}
