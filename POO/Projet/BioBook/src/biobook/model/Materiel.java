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
    private String name;

    public Materiel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

     @Override
    public String toString() { 
        return name;
    }
}
