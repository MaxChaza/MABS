/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.controller;

import biobook.model.Chercheur;
import biobook.view.LoginView;
import biobook.view.MainFrame;
import java.util.ArrayList;
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
    }

    public void clickValider(String log, String pass) {
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
    
    public void clickAnnuler() {
        logView.setVisible(false);
        logView.dispose(); 
    } 
    
    public void connection() {
        logView.setVisible(false);
        logView.dispose(); 
        MainFrame main = new MainFrame("BioBook");
    }
}
