/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.controller;

import biobook.model.Chercheur;
import biobook.util.BioBookException;
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
public class EnregistrerController {
    public EnregistrerView enregistrerView;
    public GererChercheur gererChercheur;
    
    public EnregistrerController(EnregistrerView enrView){
        enregistrerView=enrView;
        gererChercheur = new GererChercheur();
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
//                connection();
//            }
//        }
    }  
    
    public void clickAnnuler() throws BioBookException {        
        enregistrerView.setVisible(false);
        enregistrerView.login.tout.setVisible(true);
        
//        enregistrerView.dispose(); 
    } 
}
