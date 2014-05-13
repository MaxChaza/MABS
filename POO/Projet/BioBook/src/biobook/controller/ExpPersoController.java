/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.controller;

import biobook.model.Chercheur;
import biobook.model.Experience;
import biobook.model.Materiel;
import biobook.util.BioBookException;
import biobook.view.DualListBox;
import biobook.view.ExpPersoView;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

/**
 *
 * @author Maxime
 */
public class ExpPersoController {
    private ExpPersoView expPersoView;
    private GererExperience gererExperience;
    private GererMateriel gererMateriel;
    private GererChercheur gererChercheur;
    private boolean isCreateur;
    public ExpPersoController(ExpPersoView aThis) {
        expPersoView = aThis;
        gererExperience = new GererExperience();
        gererMateriel = new GererMateriel();
        gererChercheur = new GererChercheur();
        
    }

    public void chooseExp(boolean isC) throws BioBookException, IOException, IOException, FileNotFoundException, ClassNotFoundException, SQLException {
        clickAnnulerAssumption();
        clickAnnulerLibelle();
        clickAnnulerProblem();
        clickAnnulerStateOfTheArt();
        clickAnnulerParticipants();
        clickAnnulerMateriels();
        isCreateur = isC;
        expPersoView.getLabelSansExp().setVisible(false);
        
        expPersoView.getModifierLibelle().setVisible(false);
        Experience expChoisie = null;
        if(isCreateur){
            expChoisie = gererExperience.getExperience((String) expPersoView.getJlistCree().getSelectedValue());        
            expPersoView.getModifierLibelle().setVisible(true);
            expPersoView.getModifierParticipants().setVisible(true);
            expPersoView.getDualBoxParticipants().getSourceList().setEnabled(true);
        }
        else
        {
            expPersoView.getDualBoxParticipants().getSourceList().setSelectedIndices(new int[] {});
            expPersoView.getDualBoxParticipants().getSourceList().setEnabled(false);
            
            expPersoView.getModifierParticipants().setVisible(false);
            expChoisie = gererExperience.getExperience((String) expPersoView.getJlistParticipe().getSelectedValue());
        }
        expPersoView.getTabbedExperiencesPersoDroite().setVisible(true);
        expPersoView.getModifierProblem().setVisible(true);
        expPersoView.getModifierAssumption().setVisible(true);
        expPersoView.getModifierContext().setVisible(true);
        expPersoView.getModifierStateOfTheArt().setVisible(true);
        
        expPersoView.getjLabelTitreLibelle().setVisible(true);
        expPersoView.getjLabelLibelle().setVisible(true);
        expPersoView.getjLabelLibelle().setText(expChoisie.getLabel());
        
        expPersoView.getjLabelTitreAssumption().setVisible(true);
        expPersoView.getjLabelAssumption().setVisible(true);
        expPersoView.getjLabelAssumption().setText(expChoisie.getAssumption());
        
        expPersoView.getjLabelTitreContext().setVisible(true);
        expPersoView.getjLabelContext().setVisible(true);
        expPersoView.getjLabelContext().setText(expChoisie.getContext());
        
        expPersoView.getjLabelTitreStateOfTheArt().setVisible(true);
        expPersoView.getjLabelStateOfTheArt().setVisible(true);
        expPersoView.getjLabelStateOfTheArt().setText(expChoisie.getStateOfTheArt());
        
        expPersoView.getProblem().setVisible(true);
        expPersoView.getTitreProblem().setVisible(true);
        expPersoView.getProblem().setText(expChoisie.getProblem());
        
        expPersoView.getjLabelCreateur().setVisible(true);
        expPersoView.getjLabelCreateur().setText(expChoisie.getCreateur().getFirstName() + " " + expChoisie.getCreateur().getUserName());
        expPersoView.getjLabelTitreCreateur().setVisible(true);
        
             
        
//      Récupération des chercheurs  
        expChoisie.getListChercheur().add(new Chercheur("d", "d", "d", "d"));
        Object[] listParticipants = expChoisie.getListChercheur().toArray();
        expPersoView.getDualBoxParticipants().addFirstSourceElements(listParticipants);
                
        HashSet tous = new HashSet();
        boolean participe= false;
        // Récupération des chercheur qui ne sont pas dans cette exp
        for (Chercheur chercheurBuff : gererChercheur.getChercheurs()) {
            if (!chercheurBuff.equals(expPersoView.getEspacePersoView().getMain().getChercheurConnecte())) {
                participe= false;
                for (Chercheur chercheurParticipe : expChoisie.getListChercheur()){
                    if(chercheurParticipe.equals(chercheurBuff))
                    {
                        participe=true;
                    }
                }
                if(!participe){
                    // Nom du chercheur
                    tous.add(chercheurBuff);
                }
            }
        }
        
        tous.add(new Chercheur(expPersoView.getEspacePersoView().getMain().getChercheurConnecte()));
        Object[] listToutLesChercheurs = tous.toArray();
        
        expPersoView.getDualBoxParticipants().addFirstDestinationElements(listToutLesChercheurs);
        expPersoView.getDualBoxParticipants().setVisible(true);
        
        // Récupération des materiels
        expChoisie.getListMateriels().add(new Materiel("d"));
        Object[] listMateriels = expChoisie.getListMateriels().toArray();
        expPersoView.getDualBoxMateriels().addFirstSourceElements(listMateriels);
                
        HashSet tousMat = new HashSet();
        boolean materielDeLExperience= false;
        // Récupération des Materiel qui ne sont pas dans cette exp
        for (Materiel materielBuff : gererMateriel.getMateriels()) {
            for (Materiel materielParticipe : expChoisie.getListMateriels()){
                if(materielParticipe.equals(materielBuff))
                {
                    materielDeLExperience=true;
                }
            }
            if(!materielDeLExperience){
                // Nom du materiel
                tousMat.add(materielBuff);
            }
        }
        
        tousMat.add(new Materiel("tata"));
        tousMat.add(new Materiel("titi"));
        tousMat.add(new Materiel("tonton"));
        Object[] listToutLesMateriels = tousMat.toArray();
        
        expPersoView.getjComboBoxMateriels().setEditable(true);
        DefaultComboBoxModel def = new DefaultComboBoxModel(listToutLesMateriels); 
        expPersoView.getjComboBoxMateriels().setModel(def);
        
        expPersoView.getDualBoxMateriels().setVisible(true);
        
    }

    public void clickModifierLibelle() {
        expPersoView.getjTextLibelle().setVisible(true);
        expPersoView.getValiderLibelle().setVisible(true);
        expPersoView.getAnnulerLibelle().setVisible(true);
        
        expPersoView.getjLabelLibelle().setVisible(false);
        expPersoView.getModifierLibelle().setVisible(false);
        
        clickAnnulerAssumption();
        clickAnnulerContext();
        clickAnnulerStateOfTheArt();
        clickAnnulerProblem();
    }

    public void clickModifierProblem() {
        expPersoView.getjTextProblem().setVisible(true);
        expPersoView.getValiderProblem().setVisible(true);
        expPersoView.getAnnulerProblem().setVisible(true);
        
        expPersoView.getProblem().setVisible(false);
        expPersoView.getModifierProblem().setVisible(false);    
        
        
        clickAnnulerAssumption();
        clickAnnulerContext();
        clickAnnulerStateOfTheArt();
        clickAnnulerLibelle();
    }

    public void clickModifierAssumption() {
        expPersoView.getjTextAssumption().setVisible(true);
        expPersoView.getValiderAssumption().setVisible(true);
        expPersoView.getAnnulerAssumption().setVisible(true);
        
        expPersoView.getjLabelAssumption().setVisible(false);
        expPersoView.getModifierAssumption().setVisible(false);
        
        clickAnnulerLibelle();
        clickAnnulerContext();
        clickAnnulerStateOfTheArt();
        clickAnnulerProblem();
    }

    public void clickModifierStateOfTheArt() {
        expPersoView.getjTextStateOfTheArt().setVisible(true);
        expPersoView.getValiderStateOfTheArt().setVisible(true);
        expPersoView.getAnnulerStateOfTheArt().setVisible(true);
        
        expPersoView.getjLabelStateOfTheArt().setVisible(false);
        expPersoView.getModifierStateOfTheArt().setVisible(false);    
        
        clickAnnulerAssumption();
        clickAnnulerContext();
        clickAnnulerLibelle();
        clickAnnulerProblem();
    }

    public void clickModifierContext() {
        expPersoView.getjTextContext().setVisible(true);
        expPersoView.getValiderContext().setVisible(true);
        expPersoView.getAnnulerContext().setVisible(true);
        
        expPersoView.getjLabelContext().setVisible(false);
        expPersoView.getModifierContext().setVisible(false);
        
        clickAnnulerAssumption();
        clickAnnulerLibelle();
        clickAnnulerStateOfTheArt();
        clickAnnulerProblem();
    }
    
    public void clickAnnulerLibelle() {
        expPersoView.getjTextLibelle().setVisible(false);
        expPersoView.getValiderLibelle().setVisible(false);
        expPersoView.getAnnulerLibelle().setVisible(false);
        
        expPersoView.getjLabelLibelle().setVisible(true);
        expPersoView.getModifierLibelle().setVisible(true);      
    }

    public void clickAnnulerProblem() {
        expPersoView.getjTextProblem().setVisible(false);
        expPersoView.getValiderProblem().setVisible(false);
        expPersoView.getAnnulerProblem().setVisible(false);
        
        expPersoView.getProblem().setVisible(true);
        expPersoView.getModifierProblem().setVisible(true);    
    }

    public void clickAnnulerAssumption() {
        expPersoView.getjTextAssumption().setVisible(false);
        expPersoView.getValiderAssumption().setVisible(false);
        expPersoView.getAnnulerAssumption().setVisible(false);
        
        expPersoView.getjLabelAssumption().setVisible(true);
        expPersoView.getModifierAssumption().setVisible(true);    
    }

    public void clickAnnulerStateOfTheArt() {
        expPersoView.getjTextStateOfTheArt().setVisible(false);
        expPersoView.getValiderStateOfTheArt().setVisible(false);
        expPersoView.getAnnulerStateOfTheArt().setVisible(false);
        
        expPersoView.getjLabelStateOfTheArt().setVisible(true);
        expPersoView.getModifierStateOfTheArt().setVisible(true);    
    }

    public void clickAnnulerContext() {
        expPersoView.getjTextContext().setVisible(false);
        expPersoView.getValiderContext().setVisible(false);
        expPersoView.getAnnulerContext().setVisible(false);
        
        expPersoView.getjLabelContext().setVisible(true);
        expPersoView.getModifierContext().setVisible(true);    
    }

    public void clickModifierParticipants() {
        expPersoView.getModifierParticipants().setVisible(false);
        expPersoView.getValiderParticipants().setVisible(true);
        expPersoView.getAnnulerParticipants().setVisible(true);
        expPersoView.getDualBoxParticipants().clickModif();
    }

    public void clickAnnulerParticipants() {
        expPersoView.getModifierParticipants().setVisible(true);
        expPersoView.getValiderParticipants().setVisible(false);
        expPersoView.getAnnulerParticipants().setVisible(false);
        expPersoView.getDualBoxParticipants().clickAnnuler();
    }

    public void clickAnnulerMateriels() {
        expPersoView.getModifierMateriels().setVisible(true);
        
        expPersoView.getAddMateriels().setVisible(false);
        expPersoView.getSuppMateriels().setVisible(false);
        expPersoView.getjComboBoxMateriels().setVisible(false);
        expPersoView.getValiderMateriels().setVisible(false);
        expPersoView.getAnnulerMateriels().setVisible(false);
        
        expPersoView.getDualBoxMateriels().clickAnnuler();
    }
    
    public void clickModifierMateriels() {
        expPersoView.getModifierMateriels().setVisible(false);
                
        expPersoView.getAddMateriels().setVisible(true);
        expPersoView.getSuppMateriels().setVisible(true);
        expPersoView.getjComboBoxMateriels().setVisible(true);
        expPersoView.getValiderMateriels().setVisible(true);
        expPersoView.getAnnulerMateriels().setVisible(true);
        
    }
    
    public void clickValiderMateriels() throws BioBookException, BioBookException, IOException, FileNotFoundException, FileNotFoundException, ClassNotFoundException, ClassNotFoundException, SQLException {
        chooseExp(isCreateur);
    }

    public void clickAddMateriel() {
        Object o[] = new Object[1];
        if(expPersoView.getjComboBoxMateriels().getSelectedItem().getClass().equals("".getClass()))
        {
            o[0] = new Materiel( (String) expPersoView.getjComboBoxMateriels().getSelectedItem());
        }
        else
            o[0] = expPersoView.getjComboBoxMateriels().getSelectedItem();
        
        expPersoView.getDualBoxMateriels().addSourceElements(o);
        expPersoView.getjComboBoxMateriels().removeItem(expPersoView.getjComboBoxMateriels().getSelectedItem());
    }
    
    public void clickSuppMateriel() {
        Object o[] = new Object[1];
        o[0] = expPersoView.getjComboBoxMateriels().getSelectedItem();
        expPersoView.getjComboBoxMateriels().addItem(expPersoView.getDualBoxMateriels().getSourceList().getSelectedValue());
        expPersoView.getDualBoxMateriels().clearSourceSelected();
        
    }
}
