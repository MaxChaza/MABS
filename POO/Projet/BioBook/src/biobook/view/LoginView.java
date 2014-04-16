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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
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
    private JTextField log;
    private JPasswordField pass;

    
    JButton annuler;
    JButton valider; 
    JButton enregistrer; 
    JButton mdpOublie;
    public Container c;
    public JPanel tout;
    LoginController ctrl ;
    public LoginView(){
        ctrl = new LoginController(this);
        // Caractéristiques JFrame
        setMinimumSize(new Dimension(500, 300));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        c = getContentPane();
        c.setLayout(new BorderLayout());

        tout = new JPanel(new BorderLayout());
        // Composants de la fenetre
        JPanel nord = new JPanel(new GridLayout());
        //Image
        
        
        ImageIcon image = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\fox.jpg");
        Image i = image.getImage().getScaledInstance(230, 200, Image.SCALE_SMOOTH);
        JLabel jLabelImage = new JLabel(new ImageIcon(i));
        
        nord.add(jLabelImage);

        //Panel du formulaire
        JPanel jPanelLogin = new JPanel(new GridBagLayout());

        JLabel titreLog = new JLabel("Login ");
        log = new JTextField("Toto");
        log.setPreferredSize(new Dimension(100,20));
        
        GridBagConstraints gridBagConstraintsLog = new GridBagConstraints();
        gridBagConstraintsLog.gridx = 1;
        gridBagConstraintsLog.gridy = 0;
        gridBagConstraintsLog.fill = GridBagConstraints.HORIZONTAL;
        
        GridBagConstraints gridBagConstraintsTitreLog = new GridBagConstraints();
        gridBagConstraintsTitreLog.gridx = 0;
        gridBagConstraintsTitreLog.gridy = 0;
        gridBagConstraintsTitreLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreLog.insets = new Insets(0, 0, 0, 50);
        
        jPanelLogin.add(titreLog, gridBagConstraintsTitreLog);
        jPanelLogin.add(log,gridBagConstraintsLog);

        
        JLabel titrePass = new JLabel("Password ");
        pass = new JPasswordField("");
        pass.setPreferredSize(new Dimension(100,20));

        GridBagConstraints gridBagConstraintsPass = new GridBagConstraints();
        gridBagConstraintsPass.gridx = 1;
        gridBagConstraintsPass.gridy = 1;
        gridBagConstraintsPass.fill = GridBagConstraints.HORIZONTAL;
        
        GridBagConstraints gridBagConstraintsTitrePass = new GridBagConstraints();
        gridBagConstraintsTitrePass.gridx = 0;
        gridBagConstraintsTitrePass.gridy = 1;
        gridBagConstraintsTitrePass.fill = GridBagConstraints.HORIZONTAL;
        
        jPanelLogin.add(titrePass, gridBagConstraintsTitrePass);
        jPanelLogin.add(pass, gridBagConstraintsPass);

        nord.add(jPanelLogin);
        // Panel des boutons
        JPanel jPanelBouton = new JPanel(new FlowLayout());

        valider = new JButton("Valider");
        valider.addActionListener(this);
        
        annuler = new JButton("Annuler");
        annuler.addActionListener(this);
        
        enregistrer = new JButton("S'enregistrer");
        enregistrer.addActionListener(this);
        
        mdpOublie = new JButton("Mot de passe oublié");
        mdpOublie.addActionListener(this);
        
        jPanelBouton.add(annuler);
        jPanelBouton.add(valider);
        jPanelBouton.add(enregistrer);
        jPanelBouton.add(mdpOublie);

        // Panel général
        tout.add(BorderLayout.CENTER, nord);
        tout.add(BorderLayout.SOUTH, jPanelBouton);
        
        c.add(tout);
        // On fixe le panel sur la fenètre
        setName("Login");
        setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==valider)
            ctrl.clickValider();
        
        if(e.getSource()==annuler)
            try {
            ctrl.clickAnnuler();
        } catch (BioBookException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(e.getSource()==enregistrer){
            tout.setVisible(false);
            ctrl.clickEnregistrer();
        }
        
        if(e.getSource()==mdpOublie)
            ctrl.clickMDPOublie(log.getText());
    }
    
    public String getPass() {
        return pass.getText();
    }

    public String getLog() {
        return log.getText();
    }
}
