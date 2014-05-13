/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.controller;

import biobook.model.Chercheur;
import biobook.model.Experience;
import biobook.model.Materiel;
import biobook.util.BioBookException;
import biobook.util.MD5;
import biobook.util.SimpleConnection;
import java.io.EOFException;
import java.io.File;
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
 * Permet de gérer les Experiences en base de données
 * @author Maxime
 */
public class GererExperience {
    
    // Pour plus de claireté dans le code on initilise nos requètes
    private static final String reqInsertIntoExperience = "INSERT INTO Experience(assumption, context, createur, labelExperience, problem, stateOfArt)VALUES(?,?,?,?,?,?)";    
    private static final String reqFindAllExperiences = "SELECT * FROM Experience";
    private static final String reqFindCreateur = "SELECT createur FROM Experience WHERE labelExperience=?";
    private static final String reqFindExperienceByLabel = "SELECT * FROM Experience WHERE labelExperience=?";
    private static final String reqUpdateExperienceByLabel= "UPDATE Experience SET assumption=?, context=?, createur=?, labelExperience=?, problem=?, stateOfArt=? WHERE labelExperience=?";
    private static final String reqDeleteExperienceByLabel= "DELETE FROM Experience WHERE labelExperience=?"; 
    private static final String reqDeleteAllExperiences = "DELETE FROM Experience";
    private static Connection c;
    
    public GererExperience(){
        // Appel à la classe Simple connection pour accéder à la base de données
        c = null;
        c = SimpleConnection.getInstance().getConnection();    
    }
    
    /** Insert un <code>Experience</code> en base de donn�es.
     * @param unExperience
     * @throws java.sql.SQLException
     * @throws biobook.util.BioBookException
     * @throws java.security.NoSuchAlgorithmException 
     * @throws java.io.IOException 
     * @throws java.io.FileNotFoundException 
     * @throws java.lang.ClassNotFoundException 
	*/
    public void insertExperience(Experience unExperience)throws SQLException, BioBookException, NoSuchAlgorithmException, IOException, FileNotFoundException, ClassNotFoundException
    {
        
        serializerExperience(unExperience);
        // On teste si la connexion a été mise en place
        if(c!=null)
        {
            //preparation of the request
            PreparedStatement pst = null;
            try
            {
                    pst = c.prepareStatement(reqInsertIntoExperience);

                    // On assigne une valeur à chaque "?" présent dans la requète 
                    // pst.setString(       1       , unExperience.getCreateur() );
                    // pst.set<Type>(<Indice Du "?">,   <Valeur passé>       );
                    pst.setString(1,unExperience.getAssumption());
                    pst.setString(2,unExperience.getContext());
                    pst.setString(3,unExperience.getCreateur().getLogin());
                    pst.setString(4,unExperience.getLabel());
                    pst.setString(5,unExperience.getContext());
                    pst.setString(6,unExperience.getStateOfTheArt());
                    
                    // Execution of the request
                    // Ceci est necessaire pour toutes les requètes qui modifient la base de données
                    pst.executeUpdate();
                    // Mise à jour de la BD
                    c.commit();
            }
            catch(SQLException e)
            {
               throw new BioBookException("Problem in the request reqInsertIntoExperience "+e.getMessage());
            }

            finally
            {
                try {
                if (pst!=null)    {  pst.close();}
                   }catch (SQLException e){
                }
            }
        }
    }
    
    /** Modifie un <code>Experience</code> en base de données.
     * @param unExperience
     * @throws java.sql.SQLException
     * @throws biobook.util.BioBookException
	*/
    public void updateExperience(Experience unExperience)throws SQLException, BioBookException
    {
        //preparation of the request
        PreparedStatement pst = null;
        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqUpdateExperienceByLabel);
                
                // On assigne une valeur à chaque "?" présent dans la requète 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passé>       );
                pst.setString(1,unExperience.getAssumption());
                pst.setString(2,unExperience.getContext());
                pst.setString(3,unExperience.getCreateur().getLogin());
                pst.setString(4,unExperience.getLabel());
                pst.setString(5,unExperience.getProblem());
                pst.setString(6,unExperience.getStateOfTheArt());
                
                pst.setString(7,unExperience.getLabel());
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requètes qui modifient la base de données
                pst.executeUpdate();
                // Mise à jour de la BD
                c.commit();
        }
        catch(SQLException e)
        {
                throw new BioBookException("Problem in the request reqUpdateExperienceByLabel "+e.getMessage());
        }

        finally
        {
                try {
                if (pst!=null)    {  pst.close();}
                   }catch (SQLException e){
                }
        }
    }
    
    /**
     * @param label
     * @return Liste de tous les Experiences 
     * @throws biobook.util.BioBookException 
    */
    public Experience getExperience(String label) throws BioBookException, IOException, FileNotFoundException, ClassNotFoundException{
        Experience unExperience = null;
        
        if(c==null)
        {
            unExperience = deserializerUnExperience(label);
        }
        else
        {
            //préparation de la requète
            PreparedStatement pst = null;
            ResultSet rs = null;

            try
            {
                pst = c.prepareStatement(reqFindExperienceByLabel);

                // On assigne une valeur à chaque "?" présent dans la requète 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passé>       );
                pst.setString(1,label);

                
                // Execution of the request
                // Nécessaire pour tous les SELECT
                rs = pst.executeQuery();
            }
            catch (SQLException e)
            {
                    throw new BioBookException("Problem when the request reqFindExperienceByLabel execute it:"+e.getMessage());
            }
            try
            {
                    if (rs.next())
                    {       
                            GererChercheur gererChercheur = new GererChercheur();
                            unExperience= new Experience(rs.getString("labelExperience"), rs.getString("problem"), rs.getString("context"), rs.getString("stateOfArt"), rs.getString("assumption"), gererChercheur.getChercheur(rs.getString("createur")));
                            // Récupération de la liste d'experiences d'un chercheur
                            HashSet<Materiel> listMateriels = new HashSet<>();
                            GererMaterielExperience g = new GererMaterielExperience();
                            listMateriels = g.getMaterielByExperience(unExperience.getLabel());
                            
                            unExperience.setListMateriels(listMateriels);
                    }
            }
            catch (SQLException e)
            {
                    throw new BioBookException("Problem when the Experience was create:"+e.getMessage());
            }

            finally
            {
                    try {
                    if (rs!=null)   {   rs.close();}
                    if (pst!=null)    {  pst.close();}
                       }catch (SQLException e){
                    }
            }
        }
        return unExperience;
    }
    
    /**
     * @param label
     * @return le createur de l'experience
     * @throws biobook.util.BioBookException 
    */
    public Chercheur getCreateur(String label) throws BioBookException, IOException, FileNotFoundException, ClassNotFoundException{
        Experience unExperience = null;
        Chercheur leCreateur = null;
        if(c==null)
        {
            unExperience = deserializerUnExperience(label);
            leCreateur = unExperience.getCreateur();
        }
        else
        {
            //préparation de la requète
            PreparedStatement pst = null;
            ResultSet rs = null;

            try
            {
                pst = c.prepareStatement(reqFindExperienceByLabel);

                // On assigne une valeur à chaque "?" présent dans la requète 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passé>       );
                pst.setString(1,label);

                // Execution of the request
                // Nécessaire pour tous les SELECT
                rs = pst.executeQuery();
            }
            catch (SQLException e)
            {
                    throw new BioBookException("Problem when the request reqFindExperienceByLabel execute it:"+e.getMessage());
            }
            try
            {
                    if (rs.next())
                    {
                            GererChercheur gererChercheur = new GererChercheur();
                            unExperience= new Experience(rs.getString("labelExperience"), rs.getString("problem"), rs.getString("context"), rs.getString("stateOfArt"), rs.getString("assumption"), gererChercheur.getChercheur(rs.getString("createur")));      
                    }
            }
            catch (SQLException e)
            {
                    throw new BioBookException("Problem when the Experience was create:"+e.getMessage());
            }

            finally
            {
                    try {
                    if (rs!=null)   {   rs.close();}
                    if (pst!=null)    {  pst.close();}
                       }catch (SQLException e){
                    }
            }
        }
        leCreateur = unExperience.getCreateur();
        return leCreateur;
    }
    
    /**
    * @return Liste de tous les Experiences 
     * @throws biobook.util.BioBookException 
     * @throws java.io.FileNotFoundException 
     * @throws java.lang.ClassNotFoundException 
    */
    public Collection<Experience> getExperiences() throws BioBookException, IOException, FileNotFoundException, ClassNotFoundException{
        // Initialisation de la liste retournée par la fonction 
        Collection <Experience> listExperiences = new HashSet<Experience>();
        if(c==null)
        {
            listExperiences = deserializerExperiences();
        }
        else
        {
            // Initialisation de la liste retournée par la fonction 
            
            //preparation of the request
            PreparedStatement pst = null;
            ResultSet rs = null;

            try
            {
                    pst = c.prepareStatement(reqFindAllExperiences);

                    //Execution of the request
                    rs = pst.executeQuery();
            }
            catch (SQLException e)
            {
                    throw new BioBookException("Problem when the request reqFindAllExperiences execute it:"+e.getMessage());
            }

            try
            {
                    //Fill in a list
                    while(rs.next())
                    {
                            // Récupération des données revoyées par la base de données
                            //dans la liste listExperiences 
                         GererChercheur gererChercheur = new GererChercheur();
                         Experience unExperience= new Experience(rs.getString("labelExperience"), rs.getString("problem"), rs.getString("context"), rs.getString("stateOfArt"), rs.getString("assumption"), gererChercheur.getChercheur(rs.getString("createur")));
                         listExperiences.add(unExperience);

                    }
            }
            catch (SQLException e)
            {
                    throw new BioBookException("Problem when the list of Experience was create:"+e.getMessage());
            }

            finally
            {
                    try {
                    if (rs!=null)   {   rs.close();}
                    if (pst!=null)    {  pst.close();}
                       }catch (SQLException e){
                    }
            }
        }
       return listExperiences;
    } 
    
    /** Delete the <code>Experience</code> by Createur.
     * @param Experience
     * @throws biobook.util.BioBookException 
    */
    public void deleteExperience(Experience unExperience) throws BioBookException{
        //preparation of the request
        PreparedStatement pst = null;

        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqDeleteExperienceByLabel);
                
                // On assigne une valeur à chaque "?" présent dans la requète 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passé>       );
                pst.setString(1,unExperience.getLabel());
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requètes qui modifient la base de données
                pst.executeUpdate();
                c.commit();

        }
        catch(SQLException e)
        {
                throw new BioBookException("Problem in the request reqDeleteExperienceLabel"+e.getMessage());
        }
        finally
        {
            try {
            if (pst!=null)    {  pst.close();}
               }catch (SQLException e){
            }
        }
    }
    
    /** Delete all <code>Experience</code>
     * @throws biobook.util.BioBookException 
    */
    public void deleteAllExperiences() throws BioBookException
    {
        //preparation of the request		
        PreparedStatement pst = null;
        File fichier = new File("./src/biobook/serialisation/Experience.ser");
        if (fichier.exists()) {
            fichier.delete();
        }
        
        if(c!=null)
        {
            try
            {
                    pst = c.prepareStatement(reqDeleteAllExperiences);

                    // Execution of the request
                    // Ceci est necessaire pour toutes les requètes qui modifient la base de données
                    pst.executeUpdate();
                    c.commit();

            }
            catch(SQLException e)
            {
                    throw new BioBookException("Problem in the request DeleteAllExperiences "+e.getMessage());
            }
            finally
            {
                try {
                if (pst!=null)    {  pst.close();}
                   }catch (SQLException e){
                }
            }

        }
    }
    
    // Il est aussi nécessaire de sérialiser les classes lorsque l'accès à la base de données n'est pas réalisé 
    /**
     * Insert un <code>Experience</code> dans un fichier.
     *
     * @param Experience
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public void serializerExperience(Experience unExperience) throws FileNotFoundException, IOException, ClassNotFoundException {
        // Recherche si ce Experience existe
        if(deserializerUnExperience(unExperience.getLabel()) == null)
        {
            // Si il existe on insert le Experience dans le fichier
            // Ouverture du ficher de serialisation des Experience
            FileOutputStream fichier = new FileOutputStream("./src/biobook/serialisation/experience.ser", true);
            try (ObjectOutputStream oos = new ObjectOutputStream(fichier)) {
                oos.writeObject(unExperience);
                oos.flush();
                oos.close();
                fichier.close(); 
            }
        }
//        else
//        {
//             if(!searchSerialisedExperience(Experience.getCreateur()).equals(Experience)){
//                FileOutputStream fichier = new FileOutputStream("./src/biobook/serialisation/Experience.ser", true);
//                try (ObjectOutputStream oos = new ObjectOutputStream(fichier)) {
//                    oos.writeObject(Experience);
//                    oos.flush();
//                    oos.close();
//                    fichier.close(); 
//                }
//            }  
//        }
    }
    
    /**
     * récupere les <code>Experience</code> dans un fichier.
     *
     * @return 
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public HashSet<Experience> deserializerExperiences() throws FileNotFoundException, IOException, ClassNotFoundException {
        HashSet<Experience> listExperiences = new HashSet<>();
        try {
        // Ouverture du ficher de serialisation des Experience   
            try (FileInputStream fichier = new FileInputStream("./src/biobook/serialisation/experience.ser")) {
                ObjectInputStream ois = new ObjectInputStream(fichier);
                // Iteration sur chaque objet du fichier
                while(ois != null){
                    Experience Experience = (Experience) ois.readObject();
                    listExperiences.add(Experience);
                    ois = new ObjectInputStream(fichier);                    
                }
            }
        } catch (java.io.IOException | ClassNotFoundException e) {
        }
        return listExperiences;
    }
    
    /**
     * récupere un <code>Experience</code> dans un fichier.
     *
     * @param String
     * @return 
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public Experience deserializerUnExperience(String label) throws FileNotFoundException, IOException, ClassNotFoundException {
        Experience aExperience = null;
        try {
            // Ouverture du ficher de serialisation des Experience   
            try (FileInputStream fichier = new FileInputStream("./src/biobook/serialisation/experience.ser")) {
                ObjectInputStream ois = new ObjectInputStream(fichier);
                Experience Experience = (Experience) ois.readObject();
                while(ois != null && aExperience==null){
                    if(Experience.getLabel().equals(label))
                        aExperience=Experience;
                    else
                        ois = new ObjectInputStream(fichier);                    
                }
            }
        } catch (java.io.IOException | ClassNotFoundException e) {
        }
        return aExperience;
    }
    
    /**
     * Vérifie si ce nom d'experience existe.
     *
     * @param nom
     * @return 
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public  boolean  libelleExist(String nom) throws BioBookException, IOException, FileNotFoundException, ClassNotFoundException {
        return getExperience(nom)!=null;    
    }
    
}
