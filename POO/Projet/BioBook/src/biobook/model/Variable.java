package biobook.model;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
/**
 *
 * @author Said
 */
public class Variable extends DocumentJoint implements Serializable, Comparable {
    static private final long serialVersionUID = 6L;
    private String nom;
    private String valeur;
    
    public Variable(String nom, String valeur) {
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
        final Variable other = (Variable) obj;
        return Objects.equals(this.nom, other.nom);
    }
    
    @Override
    public int compareTo(Object o) {
        return this.nom.compareTo(((Variable) o).nom);
    }
}

