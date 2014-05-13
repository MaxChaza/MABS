/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.model;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
/**
 *
 * @author Said
 */
public class Document extends DocumentJoint implements Serializable, Comparable {
    static private final long serialVersionUID = 6L;
    private String nom;
    private String valeur;
    
    public Document(String nom, String valeur) {
        super(nom, valeur);
    }
   
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Document other = (Document) obj;
        return Objects.equals(this.nom, other.nom);
    }
   
    @Override
    public int compareTo(Object o) {
        return this.nom.compareTo(((Document) o).nom);
    }

}