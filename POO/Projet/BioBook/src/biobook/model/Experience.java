/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Maxime
 */
public class Experience implements Serializable, Comparable {
    static private final long serialVersionUID = 6L;
    private String labelExperience;
    private String problem;
    private String method;
    private String stateOfTheArt;
    private String assumption;
    private Chercheur createur;
    
    private HashSet<Variable> listVariables;
    private HashSet<Doc> listDocuments;
    private HashSet<Materiel> listMateriels;
    private HashSet<Chercheur> listChercheurs;

    public Experience(String label, String problem, String method, String stateOfTheArt, String assumption, Chercheur createur) {
        this.labelExperience = label;
        this.problem = problem;
        this.method = method;
        this.stateOfTheArt = stateOfTheArt;
        this.assumption = assumption;
        this.createur = createur;
        this.listMateriels = new HashSet<Materiel>();
        this.listChercheurs = new HashSet<Chercheur>();
    }

    public HashSet<Variable> getListVariables() {
        return listVariables;
    }

    public void setListVariables(HashSet<Variable> listVariables) {
        this.listVariables = listVariables;
    }

    public HashSet<Doc> getListDocuments() {
        return listDocuments;
    }

    public void setListDocuments(HashSet<Doc> listDocuments) {
        this.listDocuments = listDocuments;
    }

    
    public String getLabel() {
        return labelExperience;
    }

    public String getProblem() {
        return problem;
    }

    public String getMethode() {
        return method;
    }

    public String getStateOfTheArt() {
        return stateOfTheArt;
    }

    public String getAssumption() {
        return assumption;
    }

    public Chercheur getCreateur() {
        return createur;
    }

    public HashSet<Materiel> getListMateriels() {
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

    public void setMethode(String context) {
        this.method = context;
    }

    public void setStateOfTheArt(String stateOfTheArt) {
        this.stateOfTheArt = stateOfTheArt;
    }

    public void setAssumption(String assumption) {
        this.assumption = assumption;
    }

    public void setCreateur(Chercheur createur) {
        this.createur = createur;
    }

    public void setListMateriels(HashSet<Materiel> listMateriels) {
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
        s.append(", \nméthode=");
        s.append(method);
        s.append(", \nstateOfTheArt=");
        s.append(stateOfTheArt);
        s.append(", \nassumption=");
        s.append(assumption);
        s.append(", \ncreateur=");
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

    @Override
    public int compareTo(Object o) {
        return this.labelExperience.compareTo(((Experience) o).labelExperience);
    }
}
