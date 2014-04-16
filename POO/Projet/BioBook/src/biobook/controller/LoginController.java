/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.controller;

import biobook.model.Chercheur;
import biobook.util.BioBookException;
import biobook.util.SendEmail;
import biobook.view.EnregistrerView;
import biobook.view.LoginView;
import biobook.view.MainFrame;
import java.util.ArrayList;
import java.util.Collection;
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

    public void clickValider() {
        String login = logView.getLog();
        String pass = logView.getPass();
        if(gererChercheur.loginExist(login))
        {
            if(gererChercheur.motDePasseOK(login,pass))
            {

            }
        }
//        Chercheur chercheur = gererChercheur.getChercheur();
//        if(chercheur.equals(null))
//        {
//            //Traitement de logView
//        }
//        else
//        {
//            if(chercheur.getPassword()==pass)
//            {
                connection();
//            }
//        }
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
        MainFrame main = new MainFrame("BioBook");
    }

    public void clickEnregistrer() {
        EnregistrerView enregistrerView = new EnregistrerView(logView);
        logView.add(enregistrerView);
    }

    public void clickMDPOublie(String login) {
//        SendEmail send = new SendEmail(gererChercheur.resetMDP());
    }
}
