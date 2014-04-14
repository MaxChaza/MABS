/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
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
    private String name;
    EspacePersoView espacePerso;
    public MainFrame(String nom){
        name = nom;
        
        // Caractéristiques JFrame
        Dimension tailleEcran = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-60);
        setSize(tailleEcran);
        isResizable();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        // Composants de la fenetre
        // Démarage sur l'espace perso
        espacePerso= new EspacePersoView();
        setVisible(true);
    }
}
