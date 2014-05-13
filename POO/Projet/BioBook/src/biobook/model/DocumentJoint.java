/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Maxime
 */
public class DocumentJoint implements Serializable, Comparable {
    static private final long serialVersionUID = 6L;
    private String nom;
    private String valeur;
    
    public DocumentJoint(String nom, String valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    public String getNom() {
        return nom;
    }

    public String getValeur() {
        return valeur;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentJoint other = (DocumentJoint) obj;
        return Objects.equals(this.nom, other.nom);
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\t\n"); 
        s.append("\tnom=");
        s.append(nom);
        s.append("\n\tvaleur=");
        s.append(valeur);
        
        return s.toString();
    }

    @Override
    public int compareTo(Object o) {
        return this.nom.compareTo(((DocumentJoint) o).nom);
    }
}
