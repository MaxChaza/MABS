/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.controller;

import biobook.model.Chercheur;
import biobook.util.BioBookException;
import biobook.util.RegexBioBook;
import biobook.view.ParamPersoView;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
/**
 *
 * @author Maxime
 */
public class ParamPersoController {
    private ParamPersoView paramPersoView; 
    private GererChercheur gererChercheur;

    public ParamPersoController(ParamPersoView aThis) {
        paramPersoView=aThis;
        gererChercheur = paramPersoView.getEspacePersoView().getMain().getGererChercheur();
        
    }

    public void clickModifierNom() {
        paramPersoView.getUserName().setVisible(false);
        paramPersoView.getModifierNom().setVisible(false);
        
        paramPersoView.getjTextName().setVisible(true);
        paramPersoView.getValiderNom().setVisible(true);
        paramPersoView.getAnnulerNom().setVisible(true);
    }

    public void clickModifierFirstName() {
        paramPersoView.getFirstName().setVisible(false);
        paramPersoView.getModifierfirstName().setVisible(false);
        
        paramPersoView.getjTextFirstName().setVisible(true);
        paramPersoView.getValiderfirstName().setVisible(true);
        paramPersoView.getAnnulerfirstName().setVisible(true);
    }

    public void clickModifierMail() {
        paramPersoView.getMail().setVisible(false);
        paramPersoView.getModifierMail().setVisible(false);
        
        paramPersoView.getjTextMail().setVisible(true);
        paramPersoView.getValiderMail().setVisible(true);
        paramPersoView.getAnnulerMail().setVisible(true);
    }

    public void clickModifierLogin() {
        paramPersoView.getLog().setVisible(false);
        paramPersoView.getModifierLog().setVisible(false);
        
        paramPersoView.getjTextLog().setVisible(true);
        paramPersoView.getValiderLog().setVisible(true);
        paramPersoView.getAnnulerLog().setVisible(true);
    }

    public void clickModifierPass() {
        paramPersoView.getTitrePass().setVisible(false);
        paramPersoView.getPass().setVisible(false);
        paramPersoView.getModifierPass().setVisible(false);
        
        paramPersoView.getTitreOldPass().setVisible(true);
        paramPersoView.getTitreNewPass().setVisible(true);
        paramPersoView.getTitreNewPassConf().setVisible(true);
        
        paramPersoView.getjTextOldPass().setVisible(true);
        paramPersoView.getjTextNewPass().setVisible(true);
        paramPersoView.getjTextNewPassConf().setVisible(true);
        
        paramPersoView.getPanelBouttonPass().setVisible(true);
    }
    
    public void clickValiderParam() throws SQLException, BioBookException, IOException, FileNotFoundException, ClassNotFoundException {
        Chercheur chercheur = paramPersoView.getEspacePersoView().getMain().getChercheurConnecte();
        Chercheur ancienChercheur = new Chercheur(chercheur);
        boolean isModif = false;
        if(paramPersoView.getjTextName().isVisible() && !(paramPersoView.getjTextName().getText().equals(null)) && !(paramPersoView.getjTextName().getText().equals(ancienChercheur.getUserName())) ) {
            chercheur.setUserName(paramPersoView.getjTextName().getText());
            isModif = true;
        }
        
        if(paramPersoView.getjTextFirstName().isVisible() && !(paramPersoView.getjTextFirstName().getText().equals(null)) && !(paramPersoView.getjTextFirstName().getText().equals(ancienChercheur.getFirstName())) ) {
            chercheur.setFirstName(paramPersoView.getjTextFirstName().getText());
            isModif = true;
        }
        
        RegexBioBook regexMail = new RegexBioBook("mail");
        
        if(paramPersoView.getjTextMail().isVisible() && !(paramPersoView.getjTextMail().getText().equals(null)) && !(paramPersoView.getjTextMail().getText().equals(ancienChercheur.getMail())) ) {
            if(regexMail.test(paramPersoView.getjTextMail().getText())){
                chercheur.setMail(paramPersoView.getjTextMail().getText());
                isModif = true;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Votre mail est erroné! Il n'a pas était pris en compte.");
            }
        }
        
        if(paramPersoView.getjTextLog().isVisible() && !(paramPersoView.getjTextLog().getText().equals(null)) && !(paramPersoView.getjTextLog().getText().equals(ancienChercheur.getLogin())) ) {
            if(gererChercheur.loginExist(paramPersoView.getjTextLog().getText()))
            {
                JOptionPane.showMessageDialog(null, "Ce login est déjà utilisé! Il n'a pas était pris en compte.");                           
            }
            else
            {   
                chercheur.setLogin(paramPersoView.getjTextLog().getText());
                isModif = true;
            }
        }
        if(isModif){
            gererChercheur.updateChercheur(chercheur, ancienChercheur.getLogin());
            paramPersoView.getAnnulerLog().doClick();
            paramPersoView.getAnnulerMail().doClick();
            paramPersoView.getAnnulerNom().doClick();
            paramPersoView.getAnnulerfirstName().doClick();

            paramPersoView.setUserName(chercheur.getUserName());
            paramPersoView.setjTextName(chercheur.getUserName());
            paramPersoView.setFirstName(chercheur.getFirstName());
            paramPersoView.setjTextFirstName(chercheur.getFirstName());
            paramPersoView.setMail(chercheur.getMail());
            paramPersoView.setjTextMail(chercheur.getMail());
            paramPersoView.setLog(chercheur.getLogin());
            paramPersoView.setjTextLog(chercheur.getLogin());
        }
        else
        {
             JOptionPane.showMessageDialog(null, "Vous n'avez rien modifier.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void clickValiderPass() throws BioBookException, NoSuchAlgorithmException, IOException, ClassNotFoundException, SQLException {
        boolean passIsSet = false;
        boolean oldPassIsSet = false;
        boolean pass2IsSet = false;
        boolean passEgal = false;
        boolean min8car = false;
        Chercheur chercheur = new Chercheur(paramPersoView.getEspacePersoView().getMain().getChercheurConnecte());
        // Teste si le password est remplit
        if(!paramPersoView.getjTextOldPass().getText().equals("")){
            oldPassIsSet=true;
            paramPersoView.getjTextNewPass().setBorder(UIManager.getBorder("TextField.border"));
        }
        else
        {
            //  create a line border with the specified color and width
            Border border = BorderFactory.createLineBorder(Color.RED, 1);
            paramPersoView.getjTextNewPass().setBorder(border);
        }
        
        if(!paramPersoView.getjTextNewPass().getText().equals("")){
            passIsSet=true;
            paramPersoView.getjTextNewPass().setBorder(UIManager.getBorder("TextField.border"));
            if(paramPersoView.getjTextNewPass().getText().length()>=8){
                min8car=true;
            }
            else
            {
                //  create a line border with the specified color and width
                Border border = BorderFactory.createLineBorder(Color.RED, 1);
                paramPersoView.getjTextNewPass().setBorder(border);
            }
        }
        else
        {
            //  create a line border with the specified color and width
            Border border = BorderFactory.createLineBorder(Color.RED, 1);
            paramPersoView.getjTextNewPass().setBorder(border);
        }

        // Teste si le password fait au momins 8 caractèress
        // Teste si la confirmation du password est remplit
        if(!paramPersoView.getjTextNewPassConf().getText().equals("")){
            pass2IsSet=true;
            paramPersoView.getjTextNewPassConf().setBorder(UIManager.getBorder("TextField.border"));
        }
        else
        {
            //  create a line border with the specified color and width
            Border border = BorderFactory.createLineBorder(Color.RED, 1);
            paramPersoView.getjTextNewPassConf().setBorder(border);
        }
        
        if(passIsSet && pass2IsSet)
        {
            // Teste si la confirmation du password est remplit
            if(paramPersoView.getjTextNewPass().getText().equals(paramPersoView.getjTextNewPassConf().getText())){
                passEgal=true;
                paramPersoView.getjTextNewPassConf().setBorder(UIManager.getBorder("TextField.border"));

            }
            else
            {
                //  create a line border with the specified color and width
                Border border = BorderFactory.createLineBorder(Color.RED, 1);
                paramPersoView.getjTextNewPassConf().setBorder(border);
                paramPersoView.getjTextNewPass().setBorder(border);
            }
        }

        if(passIsSet && pass2IsSet && oldPassIsSet)
        {
            if(gererChercheur.motDePasseOK(paramPersoView.getEspacePersoView().getMain().getChercheurConnecte().getLogin(), paramPersoView.getjTextOldPass().getText()))
            {
                if(min8car){
                    if(passEgal)
                    {
                        chercheur.setPassword(paramPersoView.getjTextNewPass().getText());
                        gererChercheur.updateMDPChercheur(chercheur);
                        clickAnnulerPass();
                    }
                    else
                    JOptionPane.showMessageDialog(null, "Les mots de passes ne sont pas les mêmes.");
                }
                else
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un mot de passe d'au moins 8 caractères.");
            }
            else
                JOptionPane.showMessageDialog(null, "Votre mot de passe n'est pas le bon.");
        }
    }

    public void clickAnnulerNom() {
        paramPersoView.getjTextName().setVisible(false);
        paramPersoView.getValiderNom().setVisible(false);
        paramPersoView.getAnnulerNom().setVisible(false);
        
        paramPersoView.getUserName().setVisible(true);
        paramPersoView.getModifierNom().setVisible(true);        
    }

    public void clickAnnulerFirstName() {
        paramPersoView.getjTextFirstName().setVisible(false);
        paramPersoView.getValiderfirstName().setVisible(false);
        paramPersoView.getAnnulerfirstName().setVisible(false);
        
        paramPersoView.getFirstName().setVisible(true);
        paramPersoView.getModifierfirstName().setVisible(true);
    }

    public void clickAnnulerMail() {
        paramPersoView.getjTextMail().setVisible(false);
        paramPersoView.getValiderMail().setVisible(false);
        paramPersoView.getAnnulerMail().setVisible(false);
        
        paramPersoView.getMail().setVisible(true);
        paramPersoView.getModifierMail().setVisible(true);
    }

    public void clickAnnulerLogin() {
        paramPersoView.getjTextLog().setVisible(false);
        paramPersoView.getValiderLog().setVisible(false);
        paramPersoView.getAnnulerLog().setVisible(false);
        
        paramPersoView.getLog().setVisible(true);
        paramPersoView.getModifierLog().setVisible(true);
    }

    public void clickAnnulerPass() {
        paramPersoView.getTitreOldPass().setVisible(false);
        paramPersoView.getTitreNewPass().setVisible(false);
        paramPersoView.getTitreNewPassConf().setVisible(false);
        
        paramPersoView.getjTextOldPass().setVisible(false);
        paramPersoView.getjTextNewPass().setVisible(false);
        paramPersoView.getjTextNewPassConf().setVisible(false);
        
        paramPersoView.getPanelBouttonPass().setVisible(false);
        
        paramPersoView.getTitrePass().setVisible(true);
        paramPersoView.getPass().setVisible(true);
        paramPersoView.getModifierPass().setVisible(true);
    }
    
}
