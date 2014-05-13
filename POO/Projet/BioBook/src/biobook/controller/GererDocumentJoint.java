/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.controller;


import biobook.model.Experience;
import biobook.model.DocumentJoint;
import biobook.model.Document;  
import biobook.model.Variable;

import biobook.model.Materiel;
import static biobook.model.Materiel.deserializerUnChercheur;

import biobook.util.BioBookException;
import biobook.util.SimpleConnection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
/**
 * Permet de gÃƒÂ©rer les chercheurs en base de donnÃƒÂ©es
 * @author Maxime
 */
public abstract class GererDocumentJoint {
    
    // Pour plus de clairetÃƒÂ© dans le code on initilise nos requÃƒÂ¨tes
    private static final String reqInsertDocument = "INSERT INTO document (labelDocument, valeur, labelExperience)VALUES(?,?)";    
    private static final String reqAllDocumentExp= "SELECT * FROM document WHERE labelExperience=?";
    private static final String reqdeleteAllDocumentByExp= "DELETE * FROM document WHERE labelExperience=?"; 
    private static final String reqDeleteDocument= "DELETE FROM document WHERE labelDocument=? and labelExperience=?";
    private static final String reqInsertVariable = "INSERT INTO variable (labelVariable, valeur, labelExperience)VALUES(?,?)";    
    private static final String reqAllVariableByExp= "SELECT * FROM variable WHERE labelExperience=?";
    private static final String reqdeleteAllVariableByExp= "DELETE * FROM variable WHERE labelExperience=?"; 
    private static final String reqDeleteVariable= "DELETE FROM variable WHERE labelVariable=? and labelExperience=?";
    private static Connection c;
    
    
    public GererDocumentJoint(){
        // Appel ÃƒÂ  la classe Simple connection pour accÃƒÂ©der ÃƒÂ  la base de donnÃƒÂ©es
        c = null;
        c = SimpleConnection.getInstance().getConnection();
    }
    /** Insert une relation <code>DocumentJoint</code> en base de donnÃ¯Â¿Â½es.
	 */
    public abstract void insert(DocumentJoint docJoint, String labExp);
   
    /**
    * @return Liste de tous les <code>DocumentJoint</code> utilisÃ© 
    */
    public abstract HashSet<DocumentJoint> getAllByExp(String labExp);
    
    /** Delete all relation <code>DocJoin_Experience</code> by Experience.
    * @throws PerfException 
    */
    public abstract void deleteAllByExp(String labExp);
    
    /** Delete a relation <code>Materiel_Experience</code>.
    * @throws PerfException 
    */
    public abstract void delete(String DocJoint, String labExp);
    
    public  void serializerDocumentJointExperience(DocumentJoint documentjoint) throws FileNotFoundException, IOException, ClassNotFoundException {
        // Recherche si ce chercheur existe
        if(deserializerUnDocumentJoint(documentjoint.getNom()) == null)
        {
            // Si il existe on insert le chercheur dans le fichier
            // Ouverture du ficher de serialisation des materiels
            FileOutputStream fichier = new FileOutputStream("./src/biobook/serialisation/documentjoint.ser", true);
            try (ObjectOutputStream oos = new ObjectOutputStream(fichier)) {
                oos.writeObject(documentjoint);
                oos.flush();
                oos.close();
                fichier.close(); 
            }
        }
    }   
     
    /**
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public HashSet<DocumentJoint> deserializerDocumentJoints() throws FileNotFoundException, IOException, ClassNotFoundException {
        HashSet<DocumentJoint> listDocumentJoints = new HashSet<>();
        try {
        // Ouverture du ficher de serialisation des chercheur   
            try (FileInputStream fichier = new FileInputStream("./src/biobook/serialisation/materiel.ser")) {
                ObjectInputStream ois = new ObjectInputStream(fichier);
                // Iteration sur chaque objet du fichier
                while(ois != null){
                    DocumentJoint documentjoint = (DocumentJoint) ois.readObject();
                    listDocumentJoints.add(documentjoint);
                    ois = new ObjectInputStream(fichier);                    
                }
            }
        } catch (java.io.IOException | ClassNotFoundException e) {
        }
        return listDocumentJoints;
    }
    
    /**
     * rÃ©cupere un <code>Materiel</code> dans un fichier.
     *
     * @param 
     * @return 
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public DocumentJoint deserializerUnDocumentJoint(String nom) throws FileNotFoundException, IOException, ClassNotFoundException {
        DocumentJoint aDocumentJoint = null;
        try {
            // Ouverture du ficher de serialisation des chercheur   
            try (FileInputStream fichier = new FileInputStream("./src/biobook/serialisation/documentJoint.ser")) {
                ObjectInputStream ois = new ObjectInputStream(fichier);
                DocumentJoint documentJoint = (DocumentJoint) ois.readObject();
                while(ois != null && aDocumentJoint==null){
                    if(documentJoint.getNom().equals(nom))
                        aDocumentJoint=documentJoint;
                    else
                        ois = new ObjectInputStream(fichier);                    
                }
            }
        } catch (java.io.IOException | ClassNotFoundException e) {
        }
        return aDocumentJoint;
    }
}
   

