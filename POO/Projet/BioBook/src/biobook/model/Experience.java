/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.model;

import java.util.ArrayList;

/**
 *
 * @author Maxime
 */
public class Experience {
    private Integer id;
    private String problem;
    private String context;
    private String stateOfTheArt;
    private String assumption;
    private String createur;
    
    private ArrayList<Materiel> listMateriels;
    private ArrayList<Chercheur> listChercheurs;

    public Experience(Integer id, String problem, String context, String stateOfTheArt, String assumption, String createur) {
        this.id = id;
        this.problem = problem;
        this.context = context;
        this.stateOfTheArt = stateOfTheArt;
        this.assumption = assumption;
        this.createur = createur;
        this.listMateriels = new ArrayList<Materiel>();
        this.listChercheurs = new ArrayList<Chercheur>();
    }

    public Integer getId() {
        return id;
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

    public ArrayList<Chercheur> getListChercheur() {
        return listChercheurs;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setListChercheur(ArrayList<Chercheur> listChercheur) {
        this.listChercheurs = listChercheur;
    }
    
    
    @Override
    public String toString() {
        StringBuffer s = null;
        s.append("Experience{");
        s.append("id=");
        s.append(id);
        s.append(", problem=");
        s.append(problem);
        s.append(", context=");
        s.append(context);
        s.append(", stateOfTheArt=");
        s.append(stateOfTheArt);
        s.append(", assumption=");
        s.append(assumption);
        s.append(", assumption=");
        s.append(createur);
        s.append(", chercheurs=");
        for(Chercheur chercheur : listChercheurs)
            s.append(chercheur.toString());
        
        
        s.append(", materiels=");
        for(Materiel mat : listMateriels)
            s.append(mat.toString());
        
        s.append('}');
        return s.toString();
    }
    
    

}
