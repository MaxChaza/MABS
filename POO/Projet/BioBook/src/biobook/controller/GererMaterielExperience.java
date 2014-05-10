/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.controller;

import biobook.model.Experience;
import biobook.model.Materiel;
import biobook.util.BioBookException;
import biobook.util.SimpleConnection;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
/**
 * Permet de gÃ©rer les chercheurs en base de donnÃ©es
 * @author Maxime
 */
public class GererMaterielExperience {
    
    // Pour plus de clairetÃ© dans le code on initilise nos requÃ¨tes
    private static final String reqInsertIntoMaterielExperience = "INSERT INTO Materiel_Experience (labelExperience, labelMateriel)VALUES(?,?)";    
    private static final String reqFindAllMaterielByExperience = "SELECT * FROM Materiel_Experience where labelExperience=?";
    private static final String reqDeleteMaterielByExperience = "DELETE FROM Materiel_Experience WHERE labelExperience=?";
    private static final String reqDeleteMaterielExperience = "DELETE FROM Materiel_Experience WHERE labelExperience=? and labelMateriel=?";
    private static Connection c;
    
    public GererMaterielExperience(){
        // Appel Ã  la classe Simple connection pour accÃ©der Ã  la base de donnÃ©es
        c=null;
        c = SimpleConnection.getInstance().getConnection();
    }
    /** Insert une relation <code>Materiel_Experience</code> en base de donnï¿½es.
	 */
    public static void insertMaterielExperience (String unMateriel, String unExperience)throws SQLException, BioBookException, NoSuchAlgorithmException
    {
        //preparation of the request
        PreparedStatement pst = null;
        try
        {
                pst = c.prepareStatement(reqInsertIntoMaterielExperience);
                
                // On assigne une valeur Ã  chaque "?" prÃ©sent dans la requÃ¨te 
                // pst.setString(       1       , unChercheur.getLogin() );
                // pst.set<Type>(<Indice Du "?">,   <Valeur passÃ©>       );
                pst.setString(1,unExperience);
                pst.setString(2,unMateriel);
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requÃ¨tes qui modifient la base de donnÃ©es
                pst.executeUpdate();
                // Mise Ã  jour de la BD
                c.commit();
        }
        catch(SQLException e)
        {
           throw new BioBookException("Problem in the request reqInsertIntoMaterielExperience "+e.getMessage());
        }

        finally
        {
            try {
            if (pst!=null)    {  pst.close();}
               }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
   
    /**
    * @return Liste de tous les <code>Materiel</code> utilisé 
    */
    public static HashSet<Materiel> getMaterielByExperience(String unExperience) throws BioBookException{
        
        // Initialisation de la liste retournÃ©e par la fonction 
        HashSet <Materiel> listMateriel = new HashSet<>();

        //preparation of the request
        PreparedStatement pst = null;
        ResultSet rs = null;

        try
        {
                pst = c.prepareStatement(reqFindAllMaterielByExperience);
                pst.setString(1,unExperience);
                 
                //Execution of the request
                rs = pst.executeQuery();
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the request reqFindAllMaterielByExperience execute it:"+e.getMessage());
        }

        try
        {
                //Fill in a list
                while(rs.next())
                {
                        // RÃ©cupÃ©ration des donnÃ©es revoyÃ©es par la base de donnÃ©es
                        //dans la liste listChercheurs 
                        Materiel unMateriel= new Materiel(rs.getString("labelMateriel"));
                        listMateriel.add(unMateriel);

                }
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the list of Chercheur was create:"+e.getMessage());
        }

        finally
        {
                try {
                if (rs!=null)   {   rs.close();}
                if (pst!=null)    {  pst.close();}
                   }catch (Exception e){
                    e.printStackTrace();
                }
        }
       return listMateriel;
    } 
    
    /** Delete all relation <code>Materiel_Experience</code> by Experience.
    * @throws PerfException 
    */
    public void deleteAllMaterielByExperience(Experience exp) throws BioBookException{
        
        // Appel Ã  la classe Simple connection pour accÃ©der Ã  la base de donnÃ©es
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();

        //preparation of the request
        PreparedStatement pst = null;

        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqDeleteMaterielByExperience);
                
                // On assigne une valeur Ã  chaque "?" prÃ©sent dans la requÃ¨te 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passÃ©>       );
                pst.setString(1,exp.getLabel());
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requÃ¨tes qui modifient la base de donnÃ©es
                pst.executeUpdate();
                c.commit();

        }
        catch(SQLException e)
        {
                throw new BioBookException("Problem in the request reqDeleteMaterielByExperience "+e.getMessage());
        }
        finally
        {
                try {
                if (pst!=null)    {  pst.close();}
                   }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }
    
    /** Delete a relation <code>Materiel_Experience</code>.
    * @throws PerfException 
    */
    public void deleteMaterielExperience(String mat, String exp) throws BioBookException
    {
        // Appel Ã  la classe Simple connection pour accÃ©der Ã  la base de donnÃ©es
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();

        //preparation of the request		
        PreparedStatement pst = null;

        try
        {
                pst = c.prepareStatement(reqDeleteMaterielExperience);

                // On assigne une valeur Ã  chaque "?" prÃ©sent dans la requÃ¨te 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passÃ©>       );
                pst.setString(1,exp);
                pst.setString(2,mat);
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requÃ¨tes qui modifient la base de donnÃ©es
                pst.executeUpdate();
                c.commit();

        }
        catch(SQLException e)
        {
                throw new BioBookException("Problem in the request reqDeleteMaterielExperience "+e.getMessage());
        }

        finally
        {
            try {
            if (pst!=null)    {  pst.close();}
               }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

