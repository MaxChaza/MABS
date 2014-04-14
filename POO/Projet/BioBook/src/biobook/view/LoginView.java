/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.view;

import biobook.controller.LoginController;
import biobook.util.BioBookException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author Maxime
 */
public class LoginView extends JFrame implements ActionListener{
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    JTextField log;
    JTextField pass;
    JButton annuler;
    JButton valider; 
    
    LoginController ctrl ;
    public LoginView(){
        ctrl = new LoginController(this);
        // Caractéristiques JFrame
        setMinimumSize(new Dimension(500, 300));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        // Composants de la fenetre
        JPanel nord = new JPanel(new GridLayout());
        //Image
        
        
        ImageIcon image = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\fox.jpg");
        Image i = image.getImage().getScaledInstance(230, 200, Image.SCALE_SMOOTH);
        JLabel jLabelImage = new JLabel(new ImageIcon(i));
        
        nord.add(jLabelImage);

        //Panel du formulaire
        JPanel jPanelLogin = new JPanel(new GridLayout(2,2));

        JLabel titreLog = new JLabel("Login ");
        log = new JTextField("Toto");
        log.setPreferredSize(new Dimension(100,20));
        jPanelLogin.add(titreLog);
        jPanelLogin.add(log);

        
        JLabel titrePass = new JLabel("Password ");
        pass = new JPasswordField("");
        pass.setPreferredSize(new Dimension(100,20));

        jPanelLogin.add(titrePass);
        jPanelLogin.add(pass);

        nord.add(jPanelLogin);
        // Panel des boutons
        JPanel jPanelBouton = new JPanel(new FlowLayout());

        valider = new JButton("Valider");
        valider.addActionListener(this);
        
        annuler = new JButton("Annuler");
        annuler.addActionListener(this);
        
        jPanelBouton.add(annuler);
        jPanelBouton.add(valider);

        // Panel général
        
        c.add(BorderLayout.CENTER, nord);
        c.add(BorderLayout.SOUTH, jPanelBouton);

        // On fixe le panel sur la fenètre
        setName("Login");
        setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==valider)
            ctrl.clickValider(log.getText(), pass.getText());
        
        if(e.getSource()==annuler)
            try {
            ctrl.clickAnnuler();
        } catch (BioBookException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
