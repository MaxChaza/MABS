/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.controller;

import biobook.model.Chercheur;
import biobook.util.BioBookException;
import biobook.util.MyRandomPassword;
import biobook.util.SendEmail;
import biobook.view.EnregistrerView;
import biobook.view.LoginView;
import biobook.view.MainFrame;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Maxime
 */
public class LoginController {
    public LoginView logView;
    public GererChercheur gererChercheur;
    
    public LoginController(LoginView loginView){
        logView=loginView;
        gererChercheur = new GererChercheur();
    }

    
    public boolean  loginExist(String log) throws BioBookException {
        return gererChercheur.getChercheur(log)!=null;    
    }
    
    public boolean motDePasseOK(String log, String pass) throws BioBookException {
        String password = gererChercheur.getPassChercheur(log);
        Boolean ok;
        if(pass.equals(password))
            ok=true;
        else 
            ok=false;
        return ok;    
    }
        
    public void clickValider() throws BioBookException {
        String login = logView.getLog();
        String pass = logView.getPass();
        if(loginExist(login))
        {
            if(motDePasseOK(login,pass))
                connection();
            else
                JOptionPane.showMessageDialog(null, "Votre login et votre mot de passe ne corresponde pas!");                           
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Login inconnu!");
        }
    }  
    
    public void clickAnnuler() throws BioBookException {
        Chercheur ch = gererChercheur.getChercheur("toto");
        if(ch!=null){
            System.out.println(ch);
        }else
            System.out.println("merde");
                
        logView.setVisible(false);
        logView.dispose(); 
    } 
    
    public void connection() {
        logView.setVisible(false);
        logView.dispose(); 
        MainFrame main = new MainFrame("BioBook", logView.getLog());
    }

    public void clickEnregistrer() {
        EnregistrerView enregistrerView = new EnregistrerView(logView);
        logView.add(enregistrerView);
    }

    public void clickMDPOublie() throws BioBookException, SQLException {
        if(loginExist(logView.getLog()))
        {
            Chercheur unChercheur = gererChercheur.getChercheur(logView.getLog());
            MyRandomPassword mrp = new MyRandomPassword();
            String newMDP = mrp.generateRandomString();
            SendEmail send = new SendEmail(unChercheur, "Ré-initialisation de votre mot de passe.", "Votre nouveau mot de passe est \""+ newMDP +"\", veuillez le changer rapidement.");
            unChercheur.setPassword(newMDP);
            gererChercheur.updateMDPChercheur(unChercheur);
        }
        else 
            JOptionPane.showMessageDialog(null, "Identifiant inconnue!");
    }
}
