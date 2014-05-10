/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.controller;

import biobook.view.ParamPersoView;
import javax.swing.JTextField;
/**
 *
 * @author Maxime
 */
public class ParamPersoController {
    private ParamPersoView paramPersoView; 
    private  GererChercheur gererChercheur;

    public ParamPersoController(ParamPersoView aThis) {
        paramPersoView=aThis;
    }

    public void clickModifier() {
        // Quand on click sur le boutton de modif on enlève la valeure à mofifier
        
    }

    public void clickModifierNom() {
        paramPersoView.getUserName().setVisible(false);
        paramPersoView.getjTextName().setVisible(true);
    }

    public void clickModifierFirstName() {
        paramPersoView.getFirstName().setVisible(false);
        paramPersoView.getjTextFirstName().setVisible(true);
    }

    public void clickModifierMail() {
        paramPersoView.getMail().setVisible(false);
        paramPersoView.getjTextMail().setVisible(true);
    }

    public void clickModifierLogin() {
        paramPersoView.getLog().setVisible(false);
        paramPersoView.getjTextLog().setVisible(true);
    }

    public void clickModifierPass() {
        paramPersoView.getTitrePass().setVisible(false);
        paramPersoView.getPass().setVisible(false);
        
        paramPersoView.getTitreOldPass().setVisible(true);
        paramPersoView.getTitreNewPass().setVisible(true);
        paramPersoView.getTitreNewPassConf().setVisible(true);
        
        paramPersoView.getjTextOldPass().setVisible(true);
        paramPersoView.getjTextNewPass().setVisible(true);
        paramPersoView.getjTextNewPassConf().setVisible(true);
    }
    
}
