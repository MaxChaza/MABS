/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.controller;

import biobook.model.Chercheur;
import biobook.model.Doc;
import biobook.model.DocumentJoint;
import biobook.model.Experience;
import biobook.model.Materiel;
import biobook.model.Variable;
import biobook.util.BioBookException;
import biobook.util.GererFile;
import biobook.view.ExpPersoView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.Document;

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
    private Experience expChoisie;
    private GererVariable gererVariable;
    private GererDocument gererDocument;
    
    public ExpPersoController(ExpPersoView aThis) {
        expPersoView = aThis;
        gererExperience = new GererExperience();
        gererMateriel = new GererMateriel();
        gererChercheur = new GererChercheur();
        gererVariable = new GererVariable();
        gererDocument = new GererDocument();
        
    }

    public void chooseExp(boolean isC) throws BioBookException, IOException, IOException, FileNotFoundException, ClassNotFoundException, SQLException {
        clickAnnulerAssumption();
        clickAnnulerLibelle();
        clickAnnulerProblem();
        clickAnnulerStateOfTheArt();
        clickAnnulerParticipants();
        clickAnnulerMateriels();
        clickAnnulerVariables();
        clickAnnulerMethode();
        expPersoView.getSupprimerExperience().setVisible(false);
        
        isCreateur = isC;
        expPersoView.getLabelSansExp().setVisible(false);
        
        expPersoView.getModifierLibelle().setVisible(false);
        expChoisie = null;
        if(isCreateur){
            expChoisie = gererExperience.getExperience((String) expPersoView.getJlistCree().getSelectedValue());        
            expPersoView.getModifierLibelle().setVisible(true);
            expPersoView.getModifierParticipants().setVisible(true);
            expPersoView.getDualBoxParticipants().getSourceList().setEnabled(true);
            expPersoView.getSupprimerExperience().setVisible(true);
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
        expPersoView.getModifierMethode().setVisible(true);
        expPersoView.getModifierStateOfTheArt().setVisible(true);
        
        expPersoView.getjLabelTitreLibelle().setVisible(true);
        expPersoView.getjLabelLibelle().setVisible(true);
        expPersoView.getjLabelLibelle().setText(expChoisie.getLabel());
        
        expPersoView.getjLabelTitreAssumption().setVisible(true);
        expPersoView.getjLabelAssumption().setVisible(true);
        expPersoView.getjLabelAssumption().setText(expChoisie.getAssumption());
        
        expPersoView.getjLabelTitreMethode().setVisible(true);
        expPersoView.getjLabelMethode().setVisible(true);
        expPersoView.getjLabelMethode().setText(expChoisie.getMethode());
        
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
        
        tous.add(new Chercheur(expPersoView.getEspacePersoView().getMain().getChercheurConnecte()));
       
        // Récupération des Variables
        Variable v = new Variable("d", "10", expChoisie);
        expChoisie.getListVariables().add(v);
        Object[] listVariables = expChoisie.getListVariables().toArray();
        expPersoView.getDualBoxVariables().addFirstSourceElements(listVariables);
                
        
        HashSet tousVar = new HashSet();
        boolean variableDeLExperience= false;
        // Récupération des Variable qui ne sont pas dans cette exp
        HashSet<Variable> listVarExp = gererVariable.getAllByExp(expChoisie.getLabel());
        for (Variable variableBuff : listVarExp) {
            for (Variable variable : expChoisie.getListVariables()){
                if(variable.equals(variableBuff))
                {
                    variableDeLExperience=true;
                }
            }
            if(!variableDeLExperience){
                // Nom du materiel
                tousMat.add(variableBuff);
            }
        }
        
        tousMat.add(new Variable("tata","",expChoisie));
        tousMat.add(new Variable("titi","",expChoisie));
        tousMat.add(new Variable("tonton","",expChoisie));
        Object[] listToutLesVariables = tousMat.toArray();
        
        // Récupération des Documents
        Doc d = new Doc("d", "10", expChoisie);
       
        expChoisie.getListDocuments().add(d);
        Object[] listDocs = expChoisie.getListDocuments().toArray();
        expPersoView.getDualBoxDocuments().addFirstSourceElements(listDocs);
                
        
        HashSet tousDoc = new HashSet();
        boolean documentDeLExperience= false;
        // Récupération des Doc qui ne sont pas dans cette exp
        HashSet<Doc> listDocExp = gererDocument.getAllByExp(expChoisie.getLabel());
        for (Doc documentBuff : listDocExp) {
            for (Doc document : expChoisie.getListDocuments()){
                if(document.equals(documentBuff))
                {
                    documentDeLExperience=true;
                }
            }
            if(!documentDeLExperience){
                // Nom du materiel
                tousMat.add(documentBuff);
            }
        }
        
        tousMat.add(new Doc("tata","",expChoisie));
        tousMat.add(new Doc("titi","",expChoisie));
        tousMat.add(new Doc("tonton","",expChoisie));
        Object[] listToutLesDocs = tousMat.toArray();
        
    }

    public void clickModifierLibelle() {
        expPersoView.getjTextLibelle().setVisible(true);
        expPersoView.getValiderLibelle().setVisible(true);
        expPersoView.getAnnulerLibelle().setVisible(true);
        
        expPersoView.getjLabelLibelle().setVisible(false);
        expPersoView.getModifierLibelle().setVisible(false);
        
        clickAnnulerAssumption();
        clickAnnulerMethode();
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
        clickAnnulerMethode();
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
        clickAnnulerMethode();
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
        clickAnnulerMethode();
        clickAnnulerLibelle();
        clickAnnulerProblem();
    }

    public void clickModifierMethode() {
        expPersoView.getjTextMethode().setVisible(true);
        expPersoView.getValiderMethode().setVisible(true);
        expPersoView.getAnnulerMethode().setVisible(true);
        
        expPersoView.getjLabelMethode().setVisible(false);
        expPersoView.getModifierMethode().setVisible(false);
        
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

    public void clickAnnulerMethode() {
        expPersoView.getjTextMethode().setVisible(false);
        expPersoView.getValiderMethode().setVisible(false);
        expPersoView.getAnnulerMethode().setVisible(false);
        
        expPersoView.getjLabelMethode().setVisible(true);
        expPersoView.getModifierMethode().setVisible(true);    
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
        
        expPersoView.getPanelBouttonMateriel().setVisible(false);
        expPersoView.getjComboBoxMateriels().setVisible(false);
        expPersoView.getValiderMateriels().setVisible(false);
        expPersoView.getAnnulerMateriels().setVisible(false);
        
        expPersoView.getDualBoxMateriels().clickAnnuler();
    }
    
    public void clickModifierMateriels() {
        expPersoView.getModifierMateriels().setVisible(false);
                
        expPersoView.getPanelBouttonMateriel().setVisible(true);
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

    public void clickModifierVariables() {
        expPersoView.getModifierVariables().setVisible(false);
                
        expPersoView.getPanelJtextVar().setVisible(true);
        expPersoView.getPanelBouttonVariable().setVisible(true);
        expPersoView.getValiderVariables().setVisible(true);
        expPersoView.getAnnulerVariables().setVisible(true);
    }
    
    public void clickAnnulerVariables() {
        expPersoView.getModifierVariables().setVisible(true);
                
        expPersoView.getPanelJtextVar().setVisible(false);
        expPersoView.getPanelBouttonVariable().setVisible(false);
        expPersoView.getValiderVariables().setVisible(false);
        expPersoView.getAnnulerVariables().setVisible(false);
    }

    public void clickModifierDocuments() {
        expPersoView.getModifierDocuments().setVisible(false);
                
        expPersoView.getPanelBouttonDocument().setVisible(true);
        expPersoView.getValiderDocuments().setVisible(true);
        expPersoView.getAnnulerDocuments().setVisible(true);
    }
    
    public void clickAnnulerDocument() {
        expPersoView.getModifierDocuments().setVisible(true);
                
        expPersoView.getPanelBouttonDocument().setVisible(false);
        expPersoView.getValiderDocuments().setVisible(false);
        expPersoView.getAnnulerDocuments().setVisible(false);
    }

    public void clickAddDocument() throws FileNotFoundException, IOException {
        
        String pathToFile = "./src/biobook/file/";
        JFileChooser choose = new JFileChooser();
        choose.showDialog(choose, "Importer");  
        
        File fileSource = choose.getSelectedFile();
        File fileDest = new File(pathToFile+fileSource.getName());
        
        GererFile.copyFile(fileSource, fileDest);
        Doc document = new Doc(fileSource.getName(), fileDest.getPath(), expChoisie) {};
        
        Object o[] = new Object[1];
        o[0] = document;
        
        expPersoView.getDualBoxDocuments().addSourceElements(o);
    }

    public void clickSuppDocument() {
        expPersoView.getDualBoxDocuments().clearSourceSelected();
    }

    public void clickSuppExperience() {
        JOptionPane.showConfirmDialog(expPersoView, "Voulez vous vraiment supprimer cette expérience?","Supprimer experience", JOptionPane.WARNING_MESSAGE,JOptionPane.WARNING_MESSAGE);
    }
}
