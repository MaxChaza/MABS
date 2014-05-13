/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.view;

import biobook.controller.ParamPersoController;
import biobook.model.Chercheur;
import biobook.util.BioBookException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Maxime
 */
public class ParamPersoView  extends JPanel implements ActionListener{
    public EspacePersoView espacePersoView;
    
    private JLabel name;
    private JTextField jTextName;
    private JLabel firstName;
    private JTextField jTextFirstName;
    private JLabel mail;
    private JTextField jTextMail;
    private JLabel log;
    private JTextField jTextLog;

    private JLabel pass;
    private JPasswordField jTextOldPass;
    private JPasswordField jTextNewPass;
    private JPasswordField jTextNewPassConf;
    
    private ParamPersoController ctrl;
    
    private JButton modifierNom;
    private JButton modifierfirstName;
    private JButton modifierMail;
    private JButton modifierLog;
    private JButton modifierPass;
    
    private JButton annulerNom;
    private JButton annulerfirstName;
    private JButton annulerMail;
    private JButton annulerLog;
    private JButton annulerPass;
    
    private JButton validerNom;
    private JButton validerfirstName;
    private JButton validerMail;
    private JButton validerLog;
    private JButton validerPass;

    public JPanel getPanelBouttonPass() {
        return panelBouttonPass;
    }

    public void setPanelBouttonPass(JPanel panelBouttonPass) {
        this.panelBouttonPass = panelBouttonPass;
    }
    
    private JPanel panelDroite;
    private JPanel panelGauche;
    private JPanel panelBouttonPass;
    private JLabel titrePass;
    private JLabel titreOldPass;
    private JLabel titreNewPass;
    private JLabel titreNewPassConf;
    
    public ParamPersoView(EspacePersoView m){
        espacePersoView=m;
        ctrl = new ParamPersoController(this);
        setLayout(new BorderLayout());
        
        panelGauche = new JPanel(new GridBagLayout());
        Chercheur chercheurConnecte = espacePersoView.getMain().getChercheurConnecte();
        
        // Composants de départ
        JLabel titreNom = new JLabel("Nom  : ");
        name = new JLabel(chercheurConnecte.getUserName());
        modifierNom = new JButtonModifier();
        modifierNom.addActionListener(this);
        
        // Composants après click modif
        jTextName = new JTextField(chercheurConnecte.getUserName());
        validerNom = new JButtonValider();
        annulerNom = new JButtonAnnuler();
        
        // Contraintes des composants de départ
        GridBagConstraints gridBagConstraintsTitreName = new GridBagConstraints();
        gridBagConstraintsTitreName.gridx = 0;
        gridBagConstraintsTitreName.gridy = 0;
        gridBagConstraintsTitreName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreName.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsName = new GridBagConstraints();
        gridBagConstraintsName.gridx = 1;
        gridBagConstraintsName.gridy = 0;
        gridBagConstraintsName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsName.insets = new Insets(0, 0, 10, 30);
         
        GridBagConstraints gridBagConstraintsModifNom = new GridBagConstraints();
        gridBagConstraintsModifNom.gridx = 2;
        gridBagConstraintsModifNom.gridy = 0;
        gridBagConstraintsModifNom.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifNom.insets = new Insets(0, 0, 10, 0);
        
        // Contraintes des Composants après click modif
        GridBagConstraints gridBagConstraintsJTextName = new GridBagConstraints();
        gridBagConstraintsJTextName.gridx = 1;
        gridBagConstraintsJTextName.gridy = 0;
        gridBagConstraintsJTextName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextName.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsJButonValiderName = new GridBagConstraints();
        gridBagConstraintsJButonValiderName.gridx = 2;
        gridBagConstraintsJButonValiderName.gridy = 0;
        gridBagConstraintsJButonValiderName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonValiderName.insets = new Insets(0, 0, 10, 0);
         
        GridBagConstraints gridBagConstraintsJButonAnnulerName = new GridBagConstraints();
        gridBagConstraintsJButonAnnulerName.gridx = 3;
        gridBagConstraintsJButonAnnulerName.gridy = 0;
        gridBagConstraintsJButonAnnulerName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonAnnulerName.insets = new Insets(0, 0, 10, 0);
         
        // Ajout des Composants de départ
        panelGauche.add(titreNom, gridBagConstraintsTitreName);
        panelGauche.add(name,gridBagConstraintsName);
        panelGauche.add(modifierNom,gridBagConstraintsModifNom);
        
        // Ajout de Composants après click modif
        panelGauche.add(jTextName,gridBagConstraintsName);
        jTextName.setMinimumSize(new Dimension(140,20));
        jTextName.setVisible(false);
        panelGauche.add(validerNom, gridBagConstraintsJButonValiderName);
        validerNom.setVisible(false);
        validerNom.addActionListener(this);
        panelGauche.add(annulerNom, gridBagConstraintsJButonAnnulerName);
        annulerNom.setVisible(false);
        annulerNom.addActionListener(this);
        // Creation du prenom
        // Composants de départ
        JLabel titrePrenom = new JLabel("Prenom  :");
        firstName = new JLabel(chercheurConnecte.getFirstName());
        modifierfirstName = new JButtonModifier();
        modifierfirstName.addActionListener(this);
        
        // Ajout de Composants après click modif
        jTextFirstName = new JTextField(chercheurConnecte.getFirstName());
        validerfirstName = new JButtonValider();
        annulerfirstName = new JButtonAnnuler();
        
        // Contraintes des composants de départ
        GridBagConstraints gridBagConstraintsTitreFirstName = new GridBagConstraints();
        gridBagConstraintsTitreFirstName.gridx = 0;
        gridBagConstraintsTitreFirstName.gridy = 1;
        gridBagConstraintsTitreFirstName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreFirstName.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsFirstName = new GridBagConstraints();
        gridBagConstraintsFirstName.gridx = 1;
        gridBagConstraintsFirstName.gridy = 1;
        gridBagConstraintsFirstName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsFirstName.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsModifPrenom = new GridBagConstraints();
        gridBagConstraintsModifPrenom.gridx = 2;
        gridBagConstraintsModifPrenom.gridy = 1;
        gridBagConstraintsModifPrenom.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifPrenom.insets = new Insets(0, 0, 10, 0);
        
        // Contraintes des composants apres click modif
        GridBagConstraints gridBagConstraintsJTextFirstName = new GridBagConstraints();
        gridBagConstraintsJTextFirstName.gridx = 1;
        gridBagConstraintsJTextFirstName.gridy = 1;
        gridBagConstraintsJTextFirstName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextFirstName.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsJButonValiderFirstName = new GridBagConstraints();
        gridBagConstraintsJButonValiderFirstName.gridx = 2;
        gridBagConstraintsJButonValiderFirstName.gridy = 1;
        gridBagConstraintsJButonValiderFirstName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonValiderFirstName.insets = new Insets(0, 0, 10, 0);
        
        GridBagConstraints gridBagConstraintsJButonAnnulerFirstName = new GridBagConstraints();
        gridBagConstraintsJButonAnnulerFirstName.gridx = 3;
        gridBagConstraintsJButonAnnulerFirstName.gridy = 1;
        gridBagConstraintsJButonAnnulerFirstName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonAnnulerFirstName.insets = new Insets(0, 0, 10, 0);
        
        // Ajout du JTextField Nom
        // Ajout des composants de départ
        panelGauche.add(titrePrenom, gridBagConstraintsTitreFirstName);
        panelGauche.add(firstName,gridBagConstraintsFirstName);
        panelGauche.add(modifierfirstName,gridBagConstraintsModifPrenom);
        
        // Ajout des composants apres click modif
        panelGauche.add(jTextFirstName,gridBagConstraintsJTextFirstName);
        jTextFirstName.setPreferredSize(new Dimension(140,20));
        jTextFirstName.setVisible(false);
        panelGauche.add(validerfirstName,gridBagConstraintsJButonValiderFirstName);
        validerfirstName.setVisible(false);
        validerfirstName.addActionListener(this);
        panelGauche.add(annulerfirstName,gridBagConstraintsJButonAnnulerFirstName);
        annulerfirstName.setVisible(false);
        annulerfirstName.addActionListener(this);
        
        // Creation du Mail
        // composants de départ
        JLabel titreMail = new JLabel("Mail  :");
        mail = new JLabel(chercheurConnecte.getMail());
        modifierMail = new JButtonModifier();
        modifierMail.addActionListener(this);
        
        // composants apres click modif
        jTextMail = new JTextField(chercheurConnecte.getMail());
        validerMail = new JButtonValider();
        annulerMail = new JButtonAnnuler();
        
        // Contraintes des composants de départ
        GridBagConstraints gridBagConstraintsTitreMail = new GridBagConstraints();
        gridBagConstraintsTitreMail.gridx = 0;
        gridBagConstraintsTitreMail.gridy = 2;
        gridBagConstraintsTitreMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreMail.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsMail = new GridBagConstraints();
        gridBagConstraintsMail.gridx = 1;
        gridBagConstraintsMail.gridy = 2;
        gridBagConstraintsMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsMail.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsModifMail = new GridBagConstraints();
        gridBagConstraintsModifMail.gridx = 2;
        gridBagConstraintsModifMail.gridy = 2;
        gridBagConstraintsModifMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifMail.insets = new Insets(0, 0, 10, 0);
                
        // Contraintes des composants apres click modif
        GridBagConstraints gridBagConstraintsJTextMail = new GridBagConstraints();
        gridBagConstraintsJTextMail.gridx = 1;
        gridBagConstraintsJTextMail.gridy = 2;
        gridBagConstraintsJTextMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextMail.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsJButonValiderMail = new GridBagConstraints();
        gridBagConstraintsJButonValiderMail.gridx = 2;
        gridBagConstraintsJButonValiderMail.gridy = 2;
        gridBagConstraintsJButonValiderMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonValiderMail.insets = new Insets(0, 0, 10, 0);
        
        GridBagConstraints gridBagConstraintsJButonAnnulerMail = new GridBagConstraints();
        gridBagConstraintsJButonAnnulerMail.gridx = 3;
        gridBagConstraintsJButonAnnulerMail.gridy = 2;
        gridBagConstraintsJButonAnnulerMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonAnnulerMail.insets = new Insets(0, 0, 10, 0);
        
        // Ajout du JTextField Mail
        // des composants de départ
        panelGauche.add(titreMail, gridBagConstraintsTitreMail);
        panelGauche.add(mail,gridBagConstraintsMail);
        panelGauche.add(modifierMail,gridBagConstraintsModifMail);
        
        // composants apres click modif
        panelGauche.add(jTextMail,gridBagConstraintsJTextMail);
        jTextMail.setPreferredSize(new Dimension(140,20));
        jTextMail.setVisible(false);
        panelGauche.add(validerMail,gridBagConstraintsJButonValiderMail);
        validerMail.setVisible(false);
        validerMail.addActionListener(this);
        panelGauche.add(annulerMail,gridBagConstraintsJButonAnnulerMail);
        annulerMail.setVisible(false);
        annulerMail.addActionListener(this);
        
        // Creation du Login
        // des composants de départ
        JLabel titreLog = new JLabel("Login  :");
        log = new JLabel(chercheurConnecte.getLogin());
        log.setPreferredSize(new Dimension(150,20));
        modifierLog = new JButtonModifier();
        modifierLog.addActionListener(this);
        
        // composants apres click modif
        jTextLog = new JTextField(chercheurConnecte.getLogin());
        validerLog = new JButtonValider();
        annulerLog = new JButtonAnnuler();
        
        // Contraintes des composants de départ
        GridBagConstraints gridBagConstraintsTitreLog = new GridBagConstraints();
        gridBagConstraintsTitreLog.gridx = 0;
        gridBagConstraintsTitreLog.gridy = 3;
        gridBagConstraintsTitreLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreLog.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsLog = new GridBagConstraints();
        gridBagConstraintsLog.gridx = 1;
        gridBagConstraintsLog.gridy = 3;
        gridBagConstraintsLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsLog.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsModifLog = new GridBagConstraints();
        gridBagConstraintsModifLog.gridx = 2;
        gridBagConstraintsModifLog.gridy = 3;
        gridBagConstraintsModifLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifLog.insets = new Insets(0, 0, 10, 0);
        
        // Contraintes composants apres click modif
        GridBagConstraints gridBagConstraintsJTextLog = new GridBagConstraints();
        gridBagConstraintsJTextLog.gridx = 1;
        gridBagConstraintsJTextLog.gridy = 3;
        gridBagConstraintsJTextLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextLog.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsJButonValiderLog = new GridBagConstraints();
        gridBagConstraintsJButonValiderLog.gridx = 2;
        gridBagConstraintsJButonValiderLog.gridy = 3;
        gridBagConstraintsJButonValiderLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonValiderLog.insets = new Insets(0, 0, 10, 0);
        
        GridBagConstraints gridBagConstraintsJButonAnnulerLog = new GridBagConstraints();
        gridBagConstraintsJButonAnnulerLog.gridx = 3;
        gridBagConstraintsJButonAnnulerLog.gridy = 3;
        gridBagConstraintsJButonAnnulerLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonAnnulerLog.insets = new Insets(0, 0, 10, 0);
        
        // Ajout du JTextField Log
        // composants de départ
        panelGauche.add(titreLog, gridBagConstraintsTitreLog);
        panelGauche.add(log,gridBagConstraintsLog);
        panelGauche.add(modifierLog,gridBagConstraintsModifLog);
        
        // composants apres click modif
        panelGauche.add(jTextLog,gridBagConstraintsJTextLog);
        jTextLog.setPreferredSize(new Dimension(140,20));
        jTextLog.setVisible(false);
        panelGauche.add(validerLog,gridBagConstraintsJButonValiderLog);
        validerLog.setVisible(false);
        validerLog.addActionListener(this);
        panelGauche.add(annulerLog,gridBagConstraintsJButonAnnulerLog);
        annulerLog.setVisible(false);
        annulerLog.addActionListener(this);
        
        panelDroite = new JPanel(new GridBagLayout());
        
        // Creation du Pass
        // composants de départ
        titrePass = new JLabel("Mot de passe  :");
        pass = new JLabel("**************");
        
        modifierPass = new JButtonModifier();
        modifierPass.addActionListener(this);
        
        // composants apres click modif
        titreOldPass = new JLabel("Ancien mot de passe  : ");
        jTextOldPass = new JPasswordField();
        titreNewPass = new JLabel("Nouveau mot de passe  : ");
        jTextNewPass = new JPasswordField();
        titreNewPassConf = new JLabel("Confirmation  : ");
        jTextNewPassConf = new JPasswordField();
        validerPass = new JButtonValider();
        validerPass.addActionListener(this);
        annulerPass = new JButtonAnnuler(); 
        annulerPass.addActionListener(this);
        panelBouttonPass = new JPanel(new GridLayout(1, 2));
        panelBouttonPass.add(validerPass);
        panelBouttonPass.add(annulerPass);
        
        // Contraintes des composants de départ
        GridBagConstraints gridBagConstraintsTitrePass = new GridBagConstraints();
        gridBagConstraintsTitrePass.gridx = 0;
        gridBagConstraintsTitrePass.gridy = 0;
        gridBagConstraintsTitrePass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitrePass.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsPass = new GridBagConstraints();
        gridBagConstraintsPass.gridx = 1;
        gridBagConstraintsPass.gridy = 0;
        gridBagConstraintsPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsPass.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsModifPass = new GridBagConstraints();
        gridBagConstraintsModifPass.gridx = 2;
        gridBagConstraintsModifPass.gridy = 0;
        gridBagConstraintsModifPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifPass.insets = new Insets(0, 0, 10, 0);
        
        // Contraintes des composants après click modif
        GridBagConstraints gridBagConstraintsTitreOldPass = new GridBagConstraints();
        gridBagConstraintsTitreOldPass.gridx = 0;
        gridBagConstraintsTitreOldPass.gridy = 0;
        gridBagConstraintsTitreOldPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreOldPass.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsOldPass = new GridBagConstraints();
        gridBagConstraintsOldPass.gridx = 1;
        gridBagConstraintsOldPass.gridy = 0;
        gridBagConstraintsOldPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsOldPass.insets = new Insets(0, 0, 10, 0);
        
        GridBagConstraints gridBagConstraintsTitreNewPass = new GridBagConstraints();
        gridBagConstraintsTitreNewPass.gridx = 0;
        gridBagConstraintsTitreNewPass.gridy = 1;
        gridBagConstraintsTitreNewPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreNewPass.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsNewPass = new GridBagConstraints();
        gridBagConstraintsNewPass.gridx = 1;
        gridBagConstraintsNewPass.gridy = 1;
        gridBagConstraintsNewPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsNewPass.insets = new Insets(0, 0, 10, 0);
        
        GridBagConstraints gridBagConstraintsTitreNewPassConf = new GridBagConstraints();
        gridBagConstraintsTitreNewPassConf.gridx = 0;
        gridBagConstraintsTitreNewPassConf.gridy = 2;
        gridBagConstraintsTitreNewPassConf.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreNewPassConf.insets = new Insets(0, 0, 10, 30);
        
        GridBagConstraints gridBagConstraintsNewPassConf = new GridBagConstraints();
        gridBagConstraintsNewPassConf.gridx = 1;
        gridBagConstraintsNewPassConf.gridy = 2;
        gridBagConstraintsNewPassConf.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsNewPassConf.insets = new Insets(0, 0, 10, 0);
        
        GridBagConstraints gridBagConstraintsPanelBouttonPass = new GridBagConstraints();
        gridBagConstraintsPanelBouttonPass.gridx = 1;
        gridBagConstraintsPanelBouttonPass.gridy = 3;
        gridBagConstraintsPanelBouttonPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsPanelBouttonPass.insets = new Insets(0, 0, 0, 0);
        
        // Ajout du JTextField Log
        panelDroite.add(titrePass, gridBagConstraintsTitrePass);
        panelDroite.add(pass,gridBagConstraintsPass);
        panelDroite.add(jTextOldPass,gridBagConstraintsOldPass);
        
        panelDroite.add(titreOldPass, gridBagConstraintsTitreOldPass);
        titreOldPass.setVisible(false);
        jTextOldPass.setPreferredSize(new Dimension(140,20));
        jTextOldPass.setVisible(false);
        
        panelDroite.add(titreNewPass, gridBagConstraintsTitreNewPass);
        titreNewPass.setVisible(false);
        panelDroite.add(jTextNewPass,gridBagConstraintsNewPass);
        jTextNewPass.setVisible(false);
        
        panelDroite.add(titreNewPassConf,gridBagConstraintsTitreNewPassConf);
        titreNewPassConf.setVisible(false);
        panelDroite.add(jTextNewPassConf,gridBagConstraintsNewPassConf);
        jTextNewPassConf.setVisible(false);
        
        panelDroite.add(panelBouttonPass,gridBagConstraintsPanelBouttonPass);
        panelBouttonPass.setVisible(false);
        
        panelDroite.add(modifierPass,gridBagConstraintsModifPass);
        
        
        add(panelGauche,BorderLayout.WEST);
        add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER);
        add(panelDroite, BorderLayout.EAST);
        Dimension dimPanel = new Dimension((int)((espacePersoView.getMain().getTailleFenetre().getWidth()-17)/2-10),(int)((espacePersoView.getMain().getTailleFenetre().getHeight()-75)));
        panelGauche.setPreferredSize(dimPanel);
        panelDroite.setPreferredSize(dimPanel);
        setVisible(true);
    }

    public JTextField getjTextName() {
        return jTextName;
    }

    public void setjTextName(String jTextName) {
        this.jTextName.setText(jTextName);
    }

    
    public JPanel getPanelDroite() {
        return panelDroite;
    }

    public void setPanelDroite(JPanel panelDroite) {
        this.panelDroite = panelDroite;
    }

    public JPanel getPanelGauche() {
        return panelGauche;
    }

    public void setPanelGauche(JPanel panelGauche) {
        this.panelGauche = panelGauche;
    }

    public EspacePersoView getEspacePersoView() {
        return espacePersoView;
    }

    public void setEspacePersoView(EspacePersoView espacePersoView) {
        this.espacePersoView = espacePersoView;
    }

    public JLabel getUserName() {
        return name;
    }

    public void setUserName(String name) {
        this.name.setText(name);
    }

    public JLabel getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    public JLabel getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.setText(mail);
    }

    
    public JTextField getjTextFirstName() {
        return jTextFirstName;
    }

    public void setjTextFirstName(String jTextFirstName) {
        this.jTextFirstName.setText(jTextFirstName);
    }

    public JTextField getjTextMail() {
        return jTextMail;
    }

    public void setjTextMail(String jTextMail) {
        this.jTextMail.setText(jTextMail);
    }

    public JTextField getjTextLog() {
        return jTextLog;
    }

    public void setjTextLog(String jTextLog) {
        this.jTextLog.setText(jTextLog);
    }
    
    public JLabel getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log.setText(log);
    }

    public ParamPersoController getCtrl() {
        return ctrl;
    }

    public void setCtrl(ParamPersoController ctrl) {
        this.ctrl = ctrl;
    }

    public JLabel getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass.setText(pass);
    }

    
    public JPasswordField getjTextOldPass() {
        return jTextOldPass;
    }

    public void setjTextOldPass(JPasswordField jTextOldPass) {
        this.jTextOldPass = jTextOldPass;
    }

    public JPasswordField getjTextNewPass() {
        return jTextNewPass;
    }

    public void setjTextNewPass(JPasswordField jTextNewPass) {
        this.jTextNewPass = jTextNewPass;
    }

    public JPasswordField getjTextNewPassConf() {
        return jTextNewPassConf;
    }

    public void setjTextNewPassConf(JPasswordField jTextNewPassConf) {
        this.jTextNewPassConf = jTextNewPassConf;
    }
    
    public JLabel getTitrePass() {
        return titrePass;
    }

    public void setTitrePass(JLabel titrePass) {
        this.titrePass = titrePass;
    }

    public JLabel getTitreOldPass() {
        return titreOldPass;
    }

    public void setTitreOldPass(JLabel titreOldPass) {
        this.titreOldPass = titreOldPass;
    }

    public JLabel getTitreNewPass() {
        return titreNewPass;
    }

    public void setTitreNewPass(JLabel titreNewPass) {
        this.titreNewPass = titreNewPass;
    }

    public JLabel getTitreNewPassConf() {
        return titreNewPassConf;
    }

    public void setTitreNewPassConf(JLabel titreNewPassConf) {
        this.titreNewPassConf = titreNewPassConf;
    }

    public JButton getModifierNom() {
        return modifierNom;
    }

    public void setModifierNom(JButton modifierNom) {
        this.modifierNom = modifierNom;
    }

    public JButton getModifierfirstName() {
        return modifierfirstName;
    }

    public void setModifierfirstName(JButton modifierfirstName) {
        this.modifierfirstName = modifierfirstName;
    }

    public JButton getModifierMail() {
        return modifierMail;
    }

    public void setModifierMail(JButton modifierMail) {
        this.modifierMail = modifierMail;
    }

    public JButton getModifierLog() {
        return modifierLog;
    }

    public void setModifierLog(JButton modifierLog) {
        this.modifierLog = modifierLog;
    }

    public JButton getModifierPass() {
        return modifierPass;
    }

    public void setModifierPass(JButton modifierPass) {
        this.modifierPass = modifierPass;
    }

    public JButton getAnnulerNom() {
        return annulerNom;
    }

    public void setAnnulerNom(JButton annulerNom) {
        this.annulerNom = annulerNom;
    }

    public JButton getAnnulerfirstName() {
        return annulerfirstName;
    }

    public void setAnnulerfirstName(JButton annulerfirstName) {
        this.annulerfirstName = annulerfirstName;
    }

    public JButton getAnnulerMail() {
        return annulerMail;
    }

    public void setAnnulerMail(JButton annulerMail) {
        this.annulerMail = annulerMail;
    }

    public JButton getAnnulerLog() {
        return annulerLog;
    }

    public void setAnnulerLog(JButton annulerLog) {
        this.annulerLog = annulerLog;
    }

    public JButton getAnnulerPass() {
        return annulerPass;
    }

    public void setAnnulerPass(JButton annulerPass) {
        this.annulerPass = annulerPass;
    }

    public JButton getValiderNom() {
        return validerNom;
    }

    public void setValiderNom(JButton validerNom) {
        this.validerNom = validerNom;
    }

    public JButton getValiderfirstName() {
        return validerfirstName;
    }

    public void setValiderfirstName(JButton validerfirstName) {
        this.validerfirstName = validerfirstName;
    }

    public JButton getValiderMail() {
        return validerMail;
    }

    public void setValiderMail(JButton validerMail) {
        this.validerMail = validerMail;
    }

    public JButton getValiderLog() {
        return validerLog;
    }

    public void setValiderLog(JButton validerLog) {
        this.validerLog = validerLog;
    }

    public JButton getValiderPass() {
        return validerPass;
    }

    public void setValiderPass(JButton validerPass) {
        this.validerPass = validerPass;
    }
    
        
    @Override
    public void actionPerformed(ActionEvent e) {
        // Click sur un des boutton de modification
        if(e.getSource()== modifierNom){
            ctrl.clickModifierNom();
        }
        if(e.getSource()== modifierfirstName){
            ctrl.clickModifierFirstName();
        }
        if(e.getSource()== modifierMail){
            ctrl.clickModifierMail();
        }
        if(e.getSource()== modifierLog){
            ctrl.clickModifierLogin();
        }
        if(e.getSource()== modifierPass){
            ctrl.clickModifierPass();
        }
        
        // Click sur un des boutton de validation pour modifier un parametre
        if(e.getSource()== validerNom || e.getSource()==validerfirstName || e.getSource()==validerMail || e.getSource()==validerLog){
            try {
                ctrl.clickValiderParam();
            } catch (SQLException ex) {
                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BioBookException ex) {
                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(e.getSource()== validerPass){
            try {
                ctrl.clickValiderPass();
            } catch (BioBookException ex) {
                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Click sur un boutton d'annulation pour ne plus modifier la valeur
        if(e.getSource()== annulerNom){
            ctrl.clickAnnulerNom();
        }
        if(e.getSource()== annulerfirstName){
            ctrl.clickAnnulerFirstName();
        }
        if(e.getSource()== annulerMail){
            ctrl.clickAnnulerMail();
        }
        if(e.getSource()== annulerLog){
            ctrl.clickAnnulerLogin();
        }
        if(e.getSource()== annulerPass){
            ctrl.clickAnnulerPass();
        }
    }
}
