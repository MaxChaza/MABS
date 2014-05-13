/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.controller;

import biobook.model.Experience;
import biobook.util.BioBookException;
import biobook.view.CreerExperienceView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 *
 * @author Maxime
 */
public class CreerExperienceController {
    private CreerExperienceView creerExperienceView;
    private GererExperience gererExperience;
    
    public CreerExperienceController(CreerExperienceView aThis) {
        creerExperienceView = aThis;
        gererExperience=new GererExperience();
    }

    public void clickValider() throws SQLException, BioBookException, BioBookException, NoSuchAlgorithmException, IOException, IOException, FileNotFoundException, FileNotFoundException, ClassNotFoundException {
        Experience exp = new Experience(
                creerExperienceView.getLabelExperience().getText(),
                creerExperienceView.getProblem().getText(),
                creerExperienceView.getContext().getText(),
                creerExperienceView.getStateOfTheArt().getText(),
                creerExperienceView.getAssumption().getText(),
                creerExperienceView.getMain().getChercheurConnecte());
        
        gererExperience.insertExperience(exp);
    }

    public void clickAnnuler() {
        creerExperienceView.dispose();
    }
    
    public void clickReset() {
        creerExperienceView.getLabelExperience().setText("");
        creerExperienceView.getContext().setText("");
        creerExperienceView.getProblem().setText("");
        creerExperienceView.getAssumption().setText("");
        creerExperienceView.getStateOfTheArt().setText("");
    }
}
