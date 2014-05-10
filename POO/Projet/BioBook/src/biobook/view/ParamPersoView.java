/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.view;

import biobook.controller.GererChercheur;
import biobook.controller.ParamPersoController;
import biobook.model.Chercheur;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
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
    
    private final JButton modifierNom;
    private final JButton modifierfirstName;
    private final JButton modifierMail;
    private final JButton modifierLog;
    private final JButton modifierPass;
    private JPanel panelDroite;
    private JPanel panelGauche;
    
    public ParamPersoView(EspacePersoView m){
        espacePersoView=m;
        ctrl = new ParamPersoController(this);
        setLayout(new BorderLayout());
        JPanel panelGauche = new JPanel(new GridBagLayout());
        
        Chercheur chercheurConnecte = espacePersoView.main.getChercheurConnecte();
        
        // Creation du nom
        JLabel titreNom = new JLabel("Nom  : ");
        name = new JLabel(chercheurConnecte.getUserName());
        jTextName = new JTextField(chercheurConnecte.getUserName());
        modifierNom = new JButtonModifier();
        modifierNom.addActionListener(this);
        
        GridBagConstraints gridBagConstraintsName = new GridBagConstraints();
        gridBagConstraintsName.gridx = 1;
        gridBagConstraintsName.gridy = 0;
        gridBagConstraintsName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsName.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsJTextName = new GridBagConstraints();
        gridBagConstraintsJTextName.gridx = 1;
        gridBagConstraintsJTextName.gridy = 0;
        gridBagConstraintsJTextName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextName.insets = new Insets(0, 0, 10, 50);
        
        
        GridBagConstraints gridBagConstraintsTitreName = new GridBagConstraints();
        gridBagConstraintsTitreName.gridx = 0;
        gridBagConstraintsTitreName.gridy = 0;
        gridBagConstraintsTitreName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreName.insets = new Insets(0, 50, 10, 50);
        
        GridBagConstraints gridBagConstraintsModifNom = new GridBagConstraints();
        gridBagConstraintsModifNom.gridx = 2;
        gridBagConstraintsModifNom.gridy = 0;
        gridBagConstraintsModifNom.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifNom.insets = new Insets(0, 0, 10, 50);
        
            
        // Ajout du JTextField Nom
        panelGauche.add(titreNom, gridBagConstraintsTitreName);
        panelGauche.add(name,gridBagConstraintsName);
        panelGauche.add(jTextName,gridBagConstraintsName);
        jTextName.setVisible(false);
        panelGauche.add(modifierNom,gridBagConstraintsModifNom);
        
        // Creation du prenom
        JLabel titrePrenom = new JLabel("Prenom  :");
        firstName = new JLabel(chercheurConnecte.getFirstName());
        jTextFirstName = new JTextField(chercheurConnecte.getFirstName());
        modifierfirstName = new JButtonModifier();
        modifierfirstName.addActionListener(this);
        
        GridBagConstraints gridBagConstraintsFirstName = new GridBagConstraints();
        gridBagConstraintsFirstName.gridx = 1;
        gridBagConstraintsFirstName.gridy = 1;
        gridBagConstraintsFirstName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsFirstName.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsJTextFirstName = new GridBagConstraints();
        gridBagConstraintsJTextFirstName.gridx = 1;
        gridBagConstraintsJTextFirstName.gridy = 1;
        gridBagConstraintsJTextFirstName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextFirstName.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsTitreFirstName = new GridBagConstraints();
        gridBagConstraintsTitreFirstName.gridx = 0;
        gridBagConstraintsTitreFirstName.gridy = 1;
        gridBagConstraintsTitreFirstName.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreFirstName.insets = new Insets(0, 50, 10, 10);
        
        GridBagConstraints gridBagConstraintsModifPrenom = new GridBagConstraints();
        gridBagConstraintsModifPrenom.gridx = 2;
        gridBagConstraintsModifPrenom.gridy = 1;
        gridBagConstraintsModifPrenom.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifPrenom.insets = new Insets(0, 0, 10, 50);
        
        // Ajout du JTextField Nom
        panelGauche.add(titrePrenom, gridBagConstraintsTitreFirstName);
        panelGauche.add(firstName,gridBagConstraintsFirstName);
        panelGauche.add(jTextFirstName,gridBagConstraintsJTextFirstName);
        jTextFirstName.setVisible(false);
        panelGauche.add(modifierfirstName,gridBagConstraintsModifPrenom);
        
        // Creation du Mail
        JLabel titreMail = new JLabel("Mail  :");
        mail = new JLabel(chercheurConnecte.getMail());
        jTextMail = new JTextField(chercheurConnecte.getMail());
        modifierMail = new JButtonModifier();
        modifierMail.addActionListener(this);
        
        GridBagConstraints gridBagConstraintsMail = new GridBagConstraints();
        gridBagConstraintsMail.gridx = 1;
        gridBagConstraintsMail.gridy = 2;
        gridBagConstraintsMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsMail.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsJTextMail = new GridBagConstraints();
        gridBagConstraintsJTextMail.gridx = 1;
        gridBagConstraintsJTextMail.gridy = 2;
        gridBagConstraintsJTextMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextMail.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsTitreMail = new GridBagConstraints();
        gridBagConstraintsTitreMail.gridx = 0;
        gridBagConstraintsTitreMail.gridy = 2;
        gridBagConstraintsTitreMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreMail.insets = new Insets(0, 50, 10, 10);
        
        GridBagConstraints gridBagConstraintsModifMail = new GridBagConstraints();
        gridBagConstraintsModifMail.gridx = 2;
        gridBagConstraintsModifMail.gridy = 2;
        gridBagConstraintsModifMail.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifMail.insets = new Insets(0, 0, 10, 50);
        
        // Ajout du JTextField Mail
        panelGauche.add(titreMail, gridBagConstraintsTitreMail);
        panelGauche.add(mail,gridBagConstraintsMail);
        panelGauche.add(jTextMail,gridBagConstraintsJTextMail);
        jTextMail.setVisible(false);
        panelGauche.add(modifierMail,gridBagConstraintsModifMail);
        
        // Creation du Login
        JLabel titreLog = new JLabel("Login  :");
        log = new JLabel(chercheurConnecte.getLogin());
        log.setPreferredSize(new Dimension(150,20));
        jTextLog = new JTextField(chercheurConnecte.getLogin());
        modifierLog = new JButtonModifier();
        modifierLog.addActionListener(this);
        
        GridBagConstraints gridBagConstraintsLog = new GridBagConstraints();
        gridBagConstraintsLog.gridx = 1;
        gridBagConstraintsLog.gridy = 3;
        gridBagConstraintsLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsLog.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsJTextLog = new GridBagConstraints();
        gridBagConstraintsJTextLog.gridx = 1;
        gridBagConstraintsJTextLog.gridy = 3;
        gridBagConstraintsJTextLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextLog.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsTitreLog = new GridBagConstraints();
        gridBagConstraintsTitreLog.gridx = 0;
        gridBagConstraintsTitreLog.gridy = 3;
        gridBagConstraintsTitreLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreLog.insets = new Insets(0, 50, 10, 10);
        
        GridBagConstraints gridBagConstraintsModifLog = new GridBagConstraints();
        gridBagConstraintsModifLog.gridx = 2;
        gridBagConstraintsModifLog.gridy = 3;
        gridBagConstraintsModifLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifLog.insets = new Insets(0, 0, 10, 50);
        
        // Ajout du JTextField Log
        panelGauche.add(titreLog, gridBagConstraintsTitreLog);
        panelGauche.add(log,gridBagConstraintsLog);
        panelGauche.add(jTextLog,gridBagConstraintsJTextLog);
        jTextLog.setVisible(false);
        panelGauche.add(modifierLog,gridBagConstraintsModifLog);
        
        panelDroite = new JPanel(new GridBagLayout());
       // Creation du Login
        JLabel titrePass = new JLabel("Mot de passe  :");
        pass = new JLabel("**************");
        jTextOldPass = new JPasswordField();
        jTextNewPass = new JPasswordField();
        jTextNewPassConf = new JPasswordField();
        
        modifierPass = new JButtonModifier();
        modifierPass.addActionListener(this);
        
        
        GridBagConstraints gridBagConstraintsPass = new GridBagConstraints();
        gridBagConstraintsPass.gridx = 1;
        gridBagConstraintsPass.gridy = 0;
        gridBagConstraintsPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsPass.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsTitrePass = new GridBagConstraints();
        gridBagConstraintsTitrePass.gridx = 0;
        gridBagConstraintsTitrePass.gridy = 0;
        gridBagConstraintsTitrePass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitrePass.insets = new Insets(0, 0, 10, 10);
        
        GridBagConstraints gridBagConstraintsOldPass = new GridBagConstraints();
        gridBagConstraintsOldPass.gridx = 1;
        gridBagConstraintsOldPass.gridy = 0;
        gridBagConstraintsOldPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsOldPass.insets = new Insets(0, 0, 10, 10);
        
        GridBagConstraints gridBagConstraintsNewPass = new GridBagConstraints();
        gridBagConstraintsNewPass.gridx = 1;
        gridBagConstraintsNewPass.gridy = 1;
        gridBagConstraintsNewPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsNewPass.insets = new Insets(0, 0, 10, 10);
        
        GridBagConstraints gridBagConstraintsNewPassConf = new GridBagConstraints();
        gridBagConstraintsNewPassConf.gridx = 1;
        gridBagConstraintsNewPassConf.gridy = 2;
        gridBagConstraintsNewPassConf.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsNewPassConf.insets = new Insets(0, 0, 10, 10);
        
        GridBagConstraints gridBagConstraintsModifPass = new GridBagConstraints();
        gridBagConstraintsModifPass.gridx = 2;
        gridBagConstraintsModifPass.gridy = 0;
        gridBagConstraintsModifPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifPass.insets = new Insets(0, 0, 10, 50);
        
        // Ajout du JTextField Log
        panelDroite.add(titrePass, gridBagConstraintsTitrePass);
        panelDroite.add(pass,gridBagConstraintsPass);
        panelDroite.add(jTextOldPass,gridBagConstraintsOldPass);
        jTextOldPass.setVisible(false);
        panelDroite.add(jTextNewPass,gridBagConstraintsNewPass);
        jTextNewPass.setVisible(false);
        panelDroite.add(jTextNewPassConf,gridBagConstraintsNewPassConf);
        jTextNewPassConf.setVisible(false);
        panelDroite.add(modifierPass,gridBagConstraintsModifPass);
        
        add(panelGauche,BorderLayout.WEST);
        add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER);
        add(panelDroite, BorderLayout.EAST);
        setVisible(true);
    }

    public JTextField getjTextName() {
        return jTextName;
    }

    public void setjTextName(JTextField jTextName) {
        this.jTextName = jTextName;
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

    public void setUserName(JLabel name) {
        this.name = name;
    }

    public JLabel getFirstName() {
        return firstName;
    }

    public void setFirstName(JLabel firstName) {
        this.firstName = firstName;
    }

    public JLabel getMail() {
        return mail;
    }

    public void setMail(JLabel mail) {
        this.mail = mail;
    }

    
    public JTextField getjTextFirstName() {
        return jTextFirstName;
    }

    public void setjTextFirstName(JTextField jTextFirstName) {
        this.jTextFirstName = jTextFirstName;
    }

    public JTextField getjTextMail() {
        return jTextMail;
    }

    public void setjTextMail(JTextField jTextMail) {
        this.jTextMail = jTextMail;
    }

    public JTextField getjTextLog() {
        return jTextLog;
    }

    public void setjTextLog(JTextField jTextLog) {
        this.jTextLog = jTextLog;
    }
    
    public JLabel getLog() {
        return log;
    }

    public void setLog(JLabel log) {
        this.log = log;
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

    public void setPass(JLabel pass) {
        this.pass = pass;
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
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
    }
}
