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

    public Variable(String nom, String valeur, Experience experience) {
        super(nom, valeur, experience);
    }
    

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();;
        s.append(nom);
        s.append(" = ");
        s.append(valeur);
        
        return s.toString();
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

