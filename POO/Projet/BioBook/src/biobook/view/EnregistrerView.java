/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.view;

import biobook.controller.EnregistrerController;
import biobook.controller.LoginController;
import biobook.util.BioBookException;
import biobook.util.RegexBioBook;
import java.awt.BorderLayout;
import java.awt.Color;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

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
    private JTextField name;
    private JTextField firstName;
    private JTextField mail;
    private JTextField log;
    private JPasswordField pass;
    private JPasswordField passConf;
    private boolean logIsSet;

    JButton annuler;
    JButton valider; 
    public LoginView login;
    
    EnregistrerController ctrl ;
    
    public EnregistrerView(LoginView logV){
        login=logV;
        ctrl = new EnregistrerController(this);
        // Caractéristiques JFrame
        setMinimumSize(new Dimension(850, 300));
        setLayout(new BorderLayout());

        // Composants de la fenetre
        JPanel nord = new JPanel(new FlowLayout());
        //Image
        
        
        ImageIcon image = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\logoFrame2.gif");
        Image i = image.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH);
        JLabel jLabelImage = new JLabel(new ImageIcon(i));
        
        nord.add(jLabelImage);

        //Panel du formulaire
        JPanel jPanelEnregistrer = new JPanel(new GridBagLayout());

        // Creation du nom
        JLabel titreNom = new JLabel("Nom");
        name = new JTextField("");
        name.setPreferredSize(new Dimension(150,20));
        
        GridBagConstraints gridBagConstraintsName = new GridBagConstraints();
        gridBagConstraintsName.gridx = 1;
        gridBagConstraintsName.gridy = 0;
        gridBagConstraintsName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsName.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsTitreName = new GridBagConstraints();
        gridBagConstraintsTitreName.gridx = 0;
        gridBagConstraintsTitreName.gridy = 0;
        gridBagConstraintsTitreName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreName.insets = new Insets(0, 0, 10, 50);
        
        // Ajout du JTextField Nom
        jPanelEnregistrer.add(titreNom, gridBagConstraintsTitreName);
        jPanelEnregistrer.add(name,gridBagConstraintsName);

        // Creation du prenom
        JLabel titrePrenom = new JLabel("Prenom");
        firstName = new JTextField("");
        firstName.setPreferredSize(new Dimension(150,20));
        
        GridBagConstraints gridBagConstraintsFirstName = new GridBagConstraints();
        gridBagConstraintsFirstName.gridx = 1;
        gridBagConstraintsFirstName.gridy = 1;
        gridBagConstraintsFirstName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsFirstName.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsTitreFirstName = new GridBagConstraints();
        gridBagConstraintsTitreFirstName.gridx = 0;
        gridBagConstraintsTitreFirstName.gridy = 1;
        gridBagConstraintsTitreFirstName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreFirstName.insets = new Insets(0, 0, 10, 10);
        
        // Ajout du JTextField Nom
        jPanelEnregistrer.add(titrePrenom, gridBagConstraintsTitreFirstName);
        jPanelEnregistrer.add(firstName,gridBagConstraintsFirstName);

        // Creation du Mail
        JLabel titreMail = new JLabel("Mail");
        mail = new JTextField("");
        mail.setPreferredSize(new Dimension(150,20));
        
        GridBagConstraints gridBagConstraintsMail = new GridBagConstraints();
        gridBagConstraintsMail.gridx = 1;
        gridBagConstraintsMail.gridy = 2;
        gridBagConstraintsMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsMail.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsTitreMail = new GridBagConstraints();
        gridBagConstraintsTitreMail.gridx = 0;
        gridBagConstraintsTitreMail.gridy = 2;
        gridBagConstraintsTitreMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreMail.insets = new Insets(0, 0, 10, 10);
        
        // Ajout du JTextField Mail
        jPanelEnregistrer.add(titreMail, gridBagConstraintsTitreMail);
        jPanelEnregistrer.add(mail,gridBagConstraintsMail);

        // Creation du Login
        JLabel titreLog = new JLabel("Login");
        log = new JTextField("");
        log.setPreferredSize(new Dimension(150,20));
        
        GridBagConstraints gridBagConstraintsLog = new GridBagConstraints();
        gridBagConstraintsLog.gridx = 1;
        gridBagConstraintsLog.gridy = 3;
        gridBagConstraintsLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsLog.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsTitreLog = new GridBagConstraints();
        gridBagConstraintsTitreLog.gridx = 0;
        gridBagConstraintsTitreLog.gridy = 3;
        gridBagConstraintsTitreLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreLog.insets = new Insets(0, 0, 10, 10);
        
        // Ajout du JTextField Log
        jPanelEnregistrer.add(titreLog, gridBagConstraintsTitreLog);
        jPanelEnregistrer.add(log,gridBagConstraintsLog);

        // Creation du JTextField Pass
        JLabel titrePass = new JLabel("Password ");
        pass = new JPasswordField("");
        JLabel nbCarPass = new JLabel("> 8 car.");
        pass.setMinimumSize(new Dimension(150,20));

        GridBagConstraints gridBagConstraintsNbCarPass = new GridBagConstraints();
        gridBagConstraintsNbCarPass.gridx = 2;
        gridBagConstraintsNbCarPass.gridy = 4;
        gridBagConstraintsNbCarPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsNbCarPass.insets = new Insets(0, -40, 10, 40);
        
        GridBagConstraints gridBagConstraintsPass = new GridBagConstraints();
        gridBagConstraintsPass.gridx = 1;
        gridBagConstraintsPass.gridy = 4;
        gridBagConstraintsPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsPass.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsTitrePass = new GridBagConstraints();
        gridBagConstraintsTitrePass.gridx = 0;
        gridBagConstraintsTitrePass.gridy = 4;
        gridBagConstraintsTitrePass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitrePass.insets = new Insets(0, 0, 10, 10);
       
        // Ajout du JTextField Pass
        jPanelEnregistrer.add(nbCarPass, gridBagConstraintsNbCarPass);
        jPanelEnregistrer.add(pass, gridBagConstraintsPass);
        jPanelEnregistrer.add(titrePass, gridBagConstraintsTitrePass);

        // Creation du JTextField Pass2
        JLabel titrePassConf = new JLabel("Confirm password ");
        passConf = new JPasswordField("");
        passConf.setPreferredSize(new Dimension(150,20));

        GridBagConstraints gridBagConstraintsPass2 = new GridBagConstraints();
        gridBagConstraintsPass2.gridx = 1;
        gridBagConstraintsPass2.gridy = 5;
        gridBagConstraintsPass2.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsPass2.insets = new Insets(0, 0, 0, 50);
        
        GridBagConstraints gridBagConstraintsTitrePass2 = new GridBagConstraints();
        gridBagConstraintsTitrePass2.gridx = 0;
        gridBagConstraintsTitrePass2.gridy = 5;
        gridBagConstraintsTitrePass2.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitrePass2.insets = new Insets(0, 0, 0, 10);
        
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
        if(e.getSource()==valider){
            logIsSet = false;
            boolean passIsSet = false;
            boolean pass2IsSet = false;
            boolean mailIsSet = false;
            boolean nameIsSet = false;
            boolean firstNameIsSet = false;
            boolean passEgal = false;
            boolean mailOk = false;
            boolean min8car = false;
            // Teste si le nom est remplit
            if(!name.getText().equals("")){
                nameIsSet=true;
                name.setBorder(UIManager.getBorder("TextField.border"));
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                name.setBorder(border);
            }
            
            // Teste si le log est remplit
            if(!log.getText().equals("")){
                logIsSet=true;
            }
            
            // Teste si le prenom est remplit
            if(!firstName.getText().equals("")){
                firstNameIsSet=true;
                firstName.setBorder(UIManager.getBorder("TextField.border"));
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                firstName.setBorder(border);
            }
            
            // Teste si le mail est remplit
            if(!mail.getText().equals("")){
                mailIsSet=true;
                mail.setBorder(UIManager.getBorder("TextField.border"));
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                mail.setBorder(border);
            }
            
            // Teste si le mail Bde la bonne syntaxe
            RegexBioBook regexMail = new RegexBioBook("mail");
            if(regexMail.test(mail.getText())){
                mailOk=true;
                mail.setBorder(UIManager.getBorder("TextField.border"));
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                mail.setBorder(border);
                JOptionPane.showMessageDialog(null, "Votre mail est erroné!");
            }
            
            // Teste si le password est remplit
            if(!pass.getText().equals("")){
                passIsSet=true;
                pass.setBorder(UIManager.getBorder("TextField.border"));
                if(pass.getText().length()>=8){
                    min8car=true;
                }
                else
                {
                    //  create a line border with the specified color and width
                    Border border = BorderFactory.createLineBorder(Color.RED, 1);
                    pass.setBorder(border);
                }
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                pass.setBorder(border);
            }
            
            // Teste si le password fait au momins 8 caractèress
           
            
            // Teste si la confirmation du password est remplit
            if(!passConf.getText().equals("")){
                pass2IsSet=true;
                passConf.setBorder(UIManager.getBorder("TextField.border"));
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                passConf.setBorder(border);
            }
            if(passIsSet && pass2IsSet)
            {
                // Teste si la confirmation du password est remplit
                if(passConf.getText().equals(pass.getText())){
                    passEgal=true;
                    passConf.setBorder(UIManager.getBorder("TextField.border"));
                    
                }
                else
                {
                    //  create a line border with the specified color and width
                    Border border = BorderFactory.createLineBorder(Color.RED, 1);
                    passConf.setBorder(border);
                    pass.setBorder(border);
                }
            }
            
            if(passIsSet && pass2IsSet && mailOk && nameIsSet && firstNameIsSet && mailIsSet)
            {
                if(min8car){
                    if(passEgal)
                    {
                        try {
                            try {
                                try {
                                    ctrl.clickValider();
                                } catch (IOException ex) {
                                    Logger.getLogger(EnregistrerView.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(EnregistrerView.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (NoSuchAlgorithmException ex) {
                                Logger.getLogger(EnregistrerView.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(EnregistrerView.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BioBookException ex) {
                            Logger.getLogger(EnregistrerView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else
                    JOptionPane.showMessageDialog(null, "Les mots de passes ne sont pas les mêmes.");
                }
                else
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un mot de passe d'au moins 8 caractères.");
            }
            else
                if(mailOk)
                    JOptionPane.showMessageDialog(null, "Veullez saisir tous les champs.");
        }
            
        
        if(e.getSource()==annuler)
            try {
            ctrl.clickAnnuler();
        } catch (BioBookException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUserName() {
        return name.getText();
    }

    public String getFirstName() {
        return firstName.getText();
    }

    public String getMail() {
        return mail.getText();
    }

    public String getLog() {
        return log.getText();
    }

    public JPasswordField getPass() {
        return pass;
    }

    public boolean isLogIsSet() {
        return logIsSet;
    }
}
