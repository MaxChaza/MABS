/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.controller;

import biobook.util.MD5;
import biobook.model.Chercheur;
import biobook.util.BioBookException;
import biobook.util.MyRandomPassword;
import biobook.util.SendEmail;
import biobook.view.EnregistrerView;
import biobook.view.LoginView;
import biobook.view.MainFrame;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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
        
    public void clickValider() throws BioBookException, NoSuchAlgorithmException, IOException, FileNotFoundException, ClassNotFoundException {
        String login = logView.getLog();
        String pass = logView.getPass();
        if(gererChercheur.loginExist(login))
        {
            if(gererChercheur.motDePasseOK(login,pass)) {
                connection();
            }
            else {
                JOptionPane.showMessageDialog(null, "Votre login et votre mot de passe ne corresponde pas!");
            }                           
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Login inconnu!");
        }
    }  
    
    public void clickAnnuler() throws BioBookException {

        logView.setVisible(false);
        logView.dispose(); 
    } 
    
    public void connection() throws BioBookException, IOException, FileNotFoundException, ClassNotFoundException {
        logView.setVisible(false);
        logView.dispose(); 
        MainFrame main = new MainFrame(logView.getLog());
    }

    public void clickEnregistrer() {
        EnregistrerView enregistrerView = new EnregistrerView(logView);
        logView.add(enregistrerView);
        logView.pack();
    }

    public void clickMDPOublie() throws BioBookException, SQLException, InterruptedException, IOException, FileNotFoundException, ClassNotFoundException {
        if(gererChercheur.loginExist(logView.getLog()))
        {
            final Chercheur unChercheur = gererChercheur.getChercheur(logView.getLog());
            MyRandomPassword mrp = new MyRandomPassword();
            final String newMDP = mrp.generateRandomString();
            SendEmail send = new SendEmail(unChercheur, "Ré-initialisation de votre mot de passe.", "Votre nouveau mot de passe est \""+ newMDP +"\", veuillez le changer rapidement.");
            send.execute();
            unChercheur.setPassword(newMDP);
            gererChercheur.updateMDPChercheur(unChercheur);
        }
        else 
            JOptionPane.showMessageDialog(null, "Identifiant inconnue!");
    }
}
