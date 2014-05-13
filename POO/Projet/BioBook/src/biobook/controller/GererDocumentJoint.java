/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.controller;


import biobook.model.Experience;
import biobook.model.DocumentJoint;  
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
public interface GererDocumentJoint {
    
    /** Insert une relation <code>DocumentJoint</code> en base de donnÃ¯Â¿Â½es.
	 */
    public abstract void insert(DocumentJoint docJoint)throws BioBookException;
   
    /**
    * @return Liste de tous les <code>DocumentJoint</code> utilisÃ© 
    */
    public abstract HashSet getAllByExp(String labExp) throws BioBookException;
    
    /** Delete all relation <code>DocJoin_Experience</code> by Experience.
    * @throws PerfException 
    */
    public abstract void deleteAllByExp(String labExp) throws BioBookException;
    
    /** Delete a relation <code>Materiel_Experience</code>.
    * @throws PerfException 
    */
    public abstract void delete(DocumentJoint docJoint)throws BioBookException;
    
    /**
     *
     * @param documentjoint
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public abstract void serializerDocumentJointExperience(DocumentJoint documentjoint) throws FileNotFoundException, IOException, ClassNotFoundException ;

    /**
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public abstract HashSet<DocumentJoint> deserializerDocumentJoints() throws FileNotFoundException, IOException, ClassNotFoundException ;
    
    /**
     * rÃ©cupere un <code>Materiel</code> dans un fichier.
     *
     * @param 
     * @return 
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public abstract DocumentJoint deserializerUnDocumentJoint(String nom) throws FileNotFoundException, IOException, ClassNotFoundException;
}
   

