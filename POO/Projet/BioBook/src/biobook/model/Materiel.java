/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.model;

/**
 *
 * @author Maxime
 */
public class Materiel {
    private String labelMateriel;

    public Materiel(String name) {
        this.labelMateriel = name;
    }

    public String getName() {
        return labelMateriel;
    }

     @Override
    public String toString() { 
        return labelMateriel;
    }
}
