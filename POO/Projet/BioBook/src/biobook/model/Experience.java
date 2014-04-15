/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Maxime
 */
public class Experience {
    private String labelExperience;
    private String problem;
    private String context;
    private String stateOfTheArt;
    private String assumption;
    private String createur;
    
    private ArrayList<Materiel> listMateriels;
    private HashSet<Chercheur> listChercheurs;

    public Experience(String label, String problem, String context, String stateOfTheArt, String assumption, String createur) {
        this.labelExperience = label;
        this.problem = problem;
        this.context = context;
        this.stateOfTheArt = stateOfTheArt;
        this.assumption = assumption;
        this.createur = createur;
        this.listMateriels = new ArrayList<Materiel>();
        this.listChercheurs = new HashSet<Chercheur>();
    }

    public String getLabel() {
        return labelExperience;
    }

    public String getProblem() {
        return problem;
    }

    public String getContext() {
        return context;
    }

    public String getStateOfTheArt() {
        return stateOfTheArt;
    }

    public String getAssumption() {
        return assumption;
    }

    public String getCreateur() {
        return createur;
    }

    public ArrayList<Materiel> getListMateriels() {
        return listMateriels;
    }

    public HashSet<Chercheur> getListChercheur() {
        return listChercheurs;
    }

    public void setLabel(String label) {
        this.labelExperience = label;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setStateOfTheArt(String stateOfTheArt) {
        this.stateOfTheArt = stateOfTheArt;
    }

    public void setAssumption(String assumption) {
        this.assumption = assumption;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public void setListMateriels(ArrayList<Materiel> listMateriels) {
        this.listMateriels = listMateriels;
    }

    public void setListChercheur(HashSet<Chercheur> listChercheur) {
        this.listChercheurs = listChercheur;
    }
    
    
    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();;
        s.append("#############\n"); 
        s.append("label=");
        s.append(labelExperience);
        s.append(", \nproblem=");
        s.append(problem);
        s.append(", \ncontext=");
        s.append(context);
        s.append(", \nstateOfTheArt=");
        s.append(stateOfTheArt);
        s.append(", \nassumption=");
        s.append(assumption);
        s.append(", \nassumption=");
        s.append(createur);
        s.append(", \nchercheurs=");
        for(Chercheur chercheur : listChercheurs){
            s.append(chercheur.getLogin());
            s.append("\n");
        }
        
        s.append(", \nmateriels=");
        for(Materiel mat : listMateriels){
            s.append(mat.toString());
            s.append("\n");
        }
        return s.toString();
    }
}
