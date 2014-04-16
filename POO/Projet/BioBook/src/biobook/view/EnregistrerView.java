/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.view;

import biobook.controller.EnregistrerController;
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
public class EnregistrerView extends JPanel implements ActionListener{
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    JTextField name;
    JTextField firstName;
    JTextField mail;
    JTextField log;
    JPasswordField pass;
    JPasswordField passConf;
    JButton annuler;
    JButton valider; 
    public LoginView login;
    
    EnregistrerController ctrl ;
    public EnregistrerView(LoginView logV){
        login=logV;
        ctrl = new EnregistrerController(this);
        // Caractéristiques JFrame
        setMinimumSize(new Dimension(500, 300));
        setLayout(new BorderLayout());

        // Composants de la fenetre
        JPanel nord = new JPanel(new GridLayout());
        //Image
        
        
        ImageIcon image = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\fox.jpg");
        Image i = image.getImage().getScaledInstance(230, 200, Image.SCALE_SMOOTH);
        JLabel jLabelImage = new JLabel(new ImageIcon(i));
        
        nord.add(jLabelImage);

        //Panel du formulaire
        JPanel jPanelEnregistrer = new JPanel(new GridBagLayout());

        // Creation du nom
        JLabel titreNom = new JLabel("Nom");
        name = new JTextField("");
        name.setPreferredSize(new Dimension(100,20));
        
        GridBagConstraints gridBagConstraintsName = new GridBagConstraints();
        gridBagConstraintsName.gridx = 1;
        gridBagConstraintsName.gridy = 0;
        gridBagConstraintsName.fill = GridBagConstraints.HORIZONTAL;
        
        GridBagConstraints gridBagConstraintsTitreName = new GridBagConstraints();
        gridBagConstraintsTitreName.gridx = 0;
        gridBagConstraintsTitreName.gridy = 0;
        gridBagConstraintsTitreName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreName.insets = new Insets(0, 0, 0, 50);
        
        // Ajout du JTextField Nom
        jPanelEnregistrer.add(titreNom, gridBagConstraintsTitreName);
        jPanelEnregistrer.add(name,gridBagConstraintsName);

        // Creation du prenom
        JLabel titrePrenom = new JLabel("Prenom");
        firstName = new JTextField("");
        firstName.setPreferredSize(new Dimension(100,20));
        
        GridBagConstraints gridBagConstraintsFirstName = new GridBagConstraints();
        gridBagConstraintsFirstName.gridx = 1;
        gridBagConstraintsFirstName.gridy = 1;
        gridBagConstraintsFirstName.fill = GridBagConstraints.HORIZONTAL;
        
        GridBagConstraints gridBagConstraintsTitreFirstName = new GridBagConstraints();
        gridBagConstraintsTitreFirstName.gridx = 0;
        gridBagConstraintsTitreFirstName.gridy = 1;
        gridBagConstraintsTitreFirstName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreFirstName.insets = new Insets(0, 0, 0, 50);
        
        // Ajout du JTextField Nom
        jPanelEnregistrer.add(titrePrenom, gridBagConstraintsTitreFirstName);
        jPanelEnregistrer.add(firstName,gridBagConstraintsFirstName);

        // Creation du Mail
        JLabel titreMail = new JLabel("Mail");
        mail = new JTextField("");
        mail.setPreferredSize(new Dimension(100,20));
        
        GridBagConstraints gridBagConstraintsMail = new GridBagConstraints();
        gridBagConstraintsMail.gridx = 1;
        gridBagConstraintsMail.gridy = 2;
        gridBagConstraintsMail.fill = GridBagConstraints.HORIZONTAL;
        
        GridBagConstraints gridBagConstraintsTitreMail = new GridBagConstraints();
        gridBagConstraintsTitreMail.gridx = 0;
        gridBagConstraintsTitreMail.gridy = 2;
        gridBagConstraintsTitreMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreMail.insets = new Insets(0, 0, 0, 50);
        
        // Ajout du JTextField Mail
        jPanelEnregistrer.add(titreMail, gridBagConstraintsTitreMail);
        jPanelEnregistrer.add(mail,gridBagConstraintsMail);

        // Creation du Login
        JLabel titreLog = new JLabel("Login");
        log = new JTextField("");
        log.setPreferredSize(new Dimension(100,20));
        
        GridBagConstraints gridBagConstraintsLog = new GridBagConstraints();
        gridBagConstraintsLog.gridx = 1;
        gridBagConstraintsLog.gridy = 3;
        gridBagConstraintsLog.fill = GridBagConstraints.HORIZONTAL;
        
        GridBagConstraints gridBagConstraintsTitreLog = new GridBagConstraints();
        gridBagConstraintsTitreLog.gridx = 0;
        gridBagConstraintsTitreLog.gridy = 3;
        gridBagConstraintsTitreLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreLog.insets = new Insets(0, 0, 0, 50);
        
        // Ajout du JTextField Log
        jPanelEnregistrer.add(titreLog, gridBagConstraintsTitreLog);
        jPanelEnregistrer.add(log,gridBagConstraintsLog);

        // Creation du JTextField Pass
        JLabel titrePass = new JLabel("Password ");
        pass = new JPasswordField("");
        pass.setPreferredSize(new Dimension(100,20));

        GridBagConstraints gridBagConstraintsPass = new GridBagConstraints();
        gridBagConstraintsPass.gridx = 1;
        gridBagConstraintsPass.gridy = 4;
        gridBagConstraintsPass.fill = GridBagConstraints.HORIZONTAL;
        
        GridBagConstraints gridBagConstraintsTitrePass = new GridBagConstraints();
        gridBagConstraintsTitrePass.gridx = 0;
        gridBagConstraintsTitrePass.gridy = 4;
        gridBagConstraintsTitrePass.fill = GridBagConstraints.HORIZONTAL;
       
        // Ajout du JTextField Pass
        jPanelEnregistrer.add(titrePass, gridBagConstraintsTitrePass);
        jPanelEnregistrer.add(pass, gridBagConstraintsPass);

         // Creation du JTextField Pass2
        JLabel titrePassConf = new JLabel("Confirm password ");
        passConf = new JPasswordField("");
        passConf.setPreferredSize(new Dimension(100,20));

        GridBagConstraints gridBagConstraintsPass2 = new GridBagConstraints();
        gridBagConstraintsPass2.gridx = 1;
        gridBagConstraintsPass2.gridy = 5;
        gridBagConstraintsPass2.fill = GridBagConstraints.HORIZONTAL;
        
        GridBagConstraints gridBagConstraintsTitrePass2 = new GridBagConstraints();
        gridBagConstraintsTitrePass2.gridx = 0;
        gridBagConstraintsTitrePass2.gridy = 5;
        gridBagConstraintsTitrePass2.fill = GridBagConstraints.HORIZONTAL;
       
        // Ajout du JTextField Pass2
        jPanelEnregistrer.add(titrePassConf, gridBagConstraintsTitrePass2);
        jPanelEnregistrer.add(passConf, gridBagConstraintsPass2);

        nord.add(jPanelEnregistrer);
        // Panel des boutons
        JPanel jPanelBouton = new JPanel(new FlowLayout());

        valider = new JButton("Valider");
        valider.addActionListener(this);
        
        annuler = new JButton("Annuler");
        annuler.addActionListener(this);
        
        jPanelBouton.add(annuler);
        jPanelBouton.add(valider);

        // Panel général
        add(BorderLayout.CENTER, nord);
        add(BorderLayout.SOUTH, jPanelBouton);

        // On fixe le panel sur la fenètre
        
        
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
