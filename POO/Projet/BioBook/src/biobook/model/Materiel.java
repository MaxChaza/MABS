/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author Maxime
 */
public class Materiel implements Serializable{
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.labelMateriel);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Materiel other = (Materiel) obj;
        if (!Objects.equals(this.labelMateriel, other.labelMateriel)) {
            return false;
        }
        return true;
    }
    
    // Il est aussi nécessaire de sérialiser les classes lorsque l'accès à la base de données n'est pas réalisé 
    /**
     * Insert un <code>Materiel</code> dans un fichier.
     *
     * @param materiel
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public static void serializerChercheur(Materiel materiel) throws FileNotFoundException, IOException, ClassNotFoundException {
        // Recherche si ce chercheur existe
        if(deserializerUnChercheur(materiel.getName()) == null)
        {
            // Si il existe on insert le chercheur dans le fichier
            // Ouverture du ficher de serialisation des materiels
            FileOutputStream fichier = new FileOutputStream("./src/biobook/serialisation/materiel.ser", true);
            try (ObjectOutputStream oos = new ObjectOutputStream(fichier)) {
                oos.writeObject(materiel);
                oos.flush();
                oos.close();
                fichier.close(); 
            }
        }
    }
    
    /**
     * récupere les <code>materiel</code> dans un fichier.
     *
     * @return liste de materiel
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public static HashSet<Materiel> deserializerMateriels() throws FileNotFoundException, IOException, ClassNotFoundException {
        HashSet<Materiel> listMateriel = new HashSet<>();
        try {
        // Ouverture du ficher de serialisation des chercheur   
            try (FileInputStream fichier = new FileInputStream("./src/biobook/serialisation/materiel.ser")) {
                ObjectInputStream ois = new ObjectInputStream(fichier);
                // Iteration sur chaque objet du fichier
                while(ois != null){
                    Materiel materiel = (Materiel) ois.readObject();
                    listMateriel.add(materiel);
                    ois = new ObjectInputStream(fichier);                    
                }
            }
        } catch (java.io.IOException | ClassNotFoundException e) {
        }
        return listMateriel;
    }
    
    /**
     * récupere un <code>Materiel</code> dans un fichier.
     *
     * @param labelMateriel
     * @return 
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public static Materiel deserializerUnChercheur(String labelMateriel) throws FileNotFoundException, IOException, ClassNotFoundException {
        Materiel aMateriel = null;
        try {
            // Ouverture du ficher de serialisation des chercheur   
            try (FileInputStream fichier = new FileInputStream("./src/biobook/serialisation/materiel.ser")) {
                ObjectInputStream ois = new ObjectInputStream(fichier);
                Materiel materiel = (Materiel) ois.readObject();
                while(ois != null && aMateriel==null){
                    if(materiel.getName().equals(labelMateriel))
                        aMateriel=materiel;
                    else
                        ois = new ObjectInputStream(fichier);                    
                }
            }
        } catch (java.io.IOException | ClassNotFoundException e) {
        }
        return aMateriel;
    }
}
