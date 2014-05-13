/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.controller;

import biobook.util.BioBookException;
import biobook.util.SimpleConnection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
/**
 * Permet de gÃ©rer les chercheurs en base de donnÃ©es
 * @author Maxime
 */
public class GererChercheurExperience {
    
    // Pour plus de clairetÃ© dans le code on initilise nos requÃ¨tes
    private static final String reqInsertIntoChercheurExperience = "INSERT INTO Chercheur_Experience (login, labelExperience)VALUES(?,?)";    
    private static final String reqFindChercheurByExperience = "SELECT * FROM Chercheur_Experience where labelExperience=?";
    private static final String reqFindAllExperienceByChercheur = "SELECT * FROM Chercheur_Experience where login=?";
    private static final String reqUpdateExperienceByChercheur = "UPDATE Chercheur_Experience SET labelExperience=? WHERE login=?";
    private static final String reqDeleteExperience = "DELETE FROM Chercheur_Experience WHERE labelExperience=? and login=?";
    private static final String reqDeleteAllChercheurExperienceByChercheur = "DELETE FROM Chercheur_Experience WHERE login=?";
    private static Connection c;
    
    public GererChercheurExperience(){
        // Appel Ã  la classe Simple connection pour accÃ©der Ã  la base de donnÃ©es
        c=null;
        c = SimpleConnection.getInstance().getConnection();

    }
    
    /** Modifie le nom de l'experience <code>Experience</code> en base de données.
     * @param login
     * @param labelExp
     * @throws java.sql.SQLException
     * @throws biobook.util.BioBookException 
    */
    public void updateExperienceChercheur(String login,String labelExp)throws SQLException, BioBookException
    {
        
        //preparation of the request
        PreparedStatement pst = null;
        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqUpdateExperienceByChercheur);
                
                // On assigne une valeur à chaque "?" présent dans la requète 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passé>       );
                pst.setString(1,labelExp);
                pst.setString(2,login);
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requètes qui modifient la base de données
                pst.executeUpdate();
                // Mise à jour de la BD
                c.commit();
        }
        catch(SQLException e)
        {
                throw new BioBookException("Problem in the request reqUpdateMDPByLogin "+e.getMessage());
        }

        finally
        {
                try {
                if (pst!=null)    {  pst.close();}
                   }catch (SQLException e){
                }
        }
    }
    
    /** Insert une relation <code>Chercheur_Experience</code> en base de donnï¿½es.
	 * @throws PerfException 
	*/
    
    public void insertChercheurExperience(String labelExp, String login)throws SQLException, BioBookException, NoSuchAlgorithmException
    {
        
        //preparation of the request
        PreparedStatement pst = null;
        try
        {
                pst = c.prepareStatement(reqInsertIntoChercheurExperience);
                
                // On assigne une valeur Ã  chaque "?" prÃ©sent dans la requÃ¨te 
                // pst.setString(       1       , unChercheur.getLogin() );
                // pst.set<Type>(<Indice Du "?">,   <Valeur passÃ©>       );
                pst.setString(1,labelExp);
                pst.setString(2, login);
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requÃ¨tes qui modifient la base de donnÃ©es
                pst.executeUpdate();
                // Mise Ã  jour de la BD
                c.commit();
        }
        catch(SQLException e)
        {
           throw new BioBookException("Problem in the request reqInsertIntoChercheurExperience "+e.getMessage());
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
    
    public String getChercheur(String labelExperience) throws BioBookException{

        String unChercheur = null;
        
        //prÃ©paration de la requÃ¨te
        PreparedStatement pst = null;
        ResultSet rs = null;

        try
        {
            pst = c.prepareStatement(reqFindChercheurByExperience);
            
            // On assigne une valeur Ã  chaque "?" prÃ©sent dans la requÃ¨te 
            // pst.set<Type>(<Indice Du "?">,   <Valeur passÃ©>       );
            pst.setString(1,labelExperience);
            
            // Execution of the request
            // NÃ©cessaire pour tous les SELECT
            rs = pst.executeQuery();
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the request reqFindChercheurByExperience execute it:"+e.getMessage());
        }
        try
        {
                if (rs.next())
                {
                        unChercheur=rs.getString("login");
                }
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the Chercheur was create:"+e.getMessage());
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
        return unChercheur;
    }
    
    public HashSet<String> getExperienceByChercheur(String login) throws BioBookException, IOException, FileNotFoundException, ClassNotFoundException{

        HashSet<String> listeExperiences = new HashSet<>();
        //prÃ©paration de la requÃ¨te
        PreparedStatement pst = null;
        ResultSet rs = null;

        try
        {
            pst = c.prepareStatement(reqFindAllExperienceByChercheur);
            
            // On assigne une valeur Ã  chaque "?" prÃ©sent dans la requÃ¨te 
            // pst.set<Type>(<Indice Du "?">,   <Valeur passÃ©>       );
            pst.setString(1,login);
            
            // Execution of the request
            // NÃ©cessaire pour tous les SELECT
            rs = pst.executeQuery();
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the request reqFindAllExperienceByChercheur execute it:"+e.getMessage());
        }
        try
        {
            //Fill in a list
            while(rs.next())
            {
                // RÃ©cupÃ©ration des donnÃ©es revoyÃ©es par la base de donnÃ©es
                //dans la liste listChercheurs 
                String uneExp = rs.getString("labelExperience");
                listeExperiences.add(uneExp);
            }
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the Chercheur was create:"+e.getMessage());
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
        return listeExperiences;
    }
    
    /** Delete a relation <code>Chercheur_Experience</code> by labelExperience & login.
    * @throws PerfException 
    */
    public void deleteChercheurExperience(String labelExp, String login) throws BioBookException{

        //preparation of the request
        PreparedStatement pst = null;

        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqDeleteExperience);
                
                // On assigne une valeur Ã  chaque "?" prÃ©sent dans la requÃ¨te 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passÃ©>       );
                pst.setString(1,labelExp);
                pst.setString(2,login);
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requÃ¨tes qui modifient la base de donnÃ©es
                pst.executeUpdate();
                c.commit();

        }
        catch(SQLException e)
        {
                throw new BioBookException("Problem in the request reqDeleteExperience "+e.getMessage());
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
    
    /** Delete all relation <code>Chercheur_Experience</code> by login
    */
    public void deleteAllChercheurExperienceByLogin(String login) throws BioBookException
    {
        
        //preparation of the request		
        PreparedStatement pst = null;

        try
        {
                pst = c.prepareStatement(reqDeleteAllChercheurExperienceByChercheur);
                
                pst.setString(1,login);
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requÃ¨tes qui modifient la base de donnÃ©es
                pst.executeUpdate();
                c.commit();

        }
        catch(SQLException e)
        {
                throw new BioBookException("Problem in the request reqDeleteAllChercheurExperienceByChercheur "+e.getMessage());
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

