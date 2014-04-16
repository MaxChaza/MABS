/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.controller;

import biobook.model.Chercheur;
import biobook.util.BioBookException;
import biobook.util.SendEmail;
import biobook.util.SendEmailTest;
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
public class EnregistrerController {
    public EnregistrerView enregistrerView;
    public GererChercheur gererChercheur;
    
    public EnregistrerController(EnregistrerView enrView){
        enregistrerView=enrView;
        gererChercheur = new GererChercheur();
    }

    public void clickAnnuler() throws BioBookException {        
        enregistrerView.setVisible(false);
        enregistrerView.login.tout.setVisible(true); 
    } 

    public boolean  loginExist(String log) throws BioBookException {
        return gererChercheur.getChercheur(log)!=null;    
    }
    
    public void clickValider() throws SQLException, BioBookException {
        

        Chercheur unChercheur = null;
        String login;
        String pass=enregistrerView.getPass().getText();
        String name=enregistrerView.getName();
        String firstName=enregistrerView.getFirstName();
        String mail=enregistrerView.getMail();
        if(enregistrerView.isLogIsSet())
        { 
            login= enregistrerView.getLog();
            unChercheur = new Chercheur(login, pass, name, firstName, mail);
        }
        else
        {
            login= name+"."+firstName;
            unChercheur = new Chercheur(pass, name, firstName, mail);
        }
        
        if(loginExist(login))
        {
            JOptionPane.showMessageDialog(null, "Ce login est déjà utilisé.");                           
        }
        else
        {
            gererChercheur.insertChercheur(unChercheur);
            JOptionPane.showMessageDialog(null, "Votre compte a été créé. Vous allez recevoir un mail de confirmation.");
            String sujet = "Confirmation inscription BioBook";
            StringBuilder msg = new StringBuilder();
            msg.append("Bonjour ");
            msg.append(firstName);
            msg.append(" ");
            msg.append(name);
            msg.append(".\n");
            msg.append("Votre incription a bien était pris en compte, votre login est ");
            msg.append(login);
            msg.append(".");
            
            
            SendEmailTest send = new SendEmailTest(mail, sujet, msg.toString());
                    
            SendEmail sendA = new SendEmail();
            enregistrerView.setVisible(false);
            enregistrerView.login.tout.setVisible(true);
        }                   
    }
    
}
