/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.controller;

import biobook.model.Chercheur;
import biobook.util.BioBookException;
import biobook.util.MD5;
import biobook.util.SimpleConnection;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
/**
 * Permet de gérer les chercheurs en base de données
 * @author Maxime
 */
public class GererChercheur {
    
    // Pour plus de claireté dans le code on initilise nos requètes
    private static final String reqInsertIntoChercheur = "INSERT INTO Chercheur (login, password, name, firstName, mail)VALUES(?,?,?,?,?)";    
    private static final String reqFindAllChercheurs = "SELECT * FROM Chercheur";
    private static final String reqFindChercheurByLogin = "SELECT * FROM Chercheur WHERE login=?";
    private static final String reqFindPassChercheurByLogin = "SELECT password FROM Chercheur WHERE login=?";
    private static final String reqUpdateMDPByLogin = "UPDATE Chercheur SET password=? WHERE login=?";
    private static final String reqUpdateChercheurByLogin = "UPDATE Chercheur SET login=?, password=?, name=?, firstName=?, mail=? WHERE login=?";
    private static final String reqDeleteChercheurByLogin = "DELETE FROM chercheur WHERE login=?"; 
    private static final String reqDeleteAllChercheurs = "DELETE FROM Chercheur";

	
    /** Insert un <code>Chercheur</code> en base de donn�es.
	 * @throws PerfException 
	*/
    public static void insertChercheur(Chercheur unChercheur)throws SQLException, BioBookException, NoSuchAlgorithmException
    {
        // Appel à la classe Simple connection pour accéder à la base de données
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();

        //preparation of the request
        PreparedStatement pst = null;
        try
        {
                pst = c.prepareStatement(reqInsertIntoChercheur);
                
                // On assigne une valeur à chaque "?" présent dans la requète 
                // pst.setString(       1       , unChercheur.getLogin() );
                // pst.set<Type>(<Indice Du "?">,   <Valeur passé>       );
                pst.setString(1,unChercheur.getLogin());
                
                // Cryptage du mot de passe en MD5
                MD5 md5 = new MD5(unChercheur.getPassword());
                pst.setString(2,md5.getMD5());
                
                pst.setString(3,unChercheur.getUserName());
                pst.setString(4,unChercheur.getFirstName());
                pst.setString(5,unChercheur.getMail());
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requètes qui modifient la base de données
                pst.executeUpdate();
                // Mise à jour de la BD
                c.commit();
        }
        catch(SQLException e)
        {
           throw new BioBookException("Problem in the request reqInsertIntoChercheur "+e.getMessage());
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
    /** Modifie un <code>Chercheur</code> en base de données.
	 * @throws PerfException 
	*/
    public static void updateChercheur(Chercheur unChercheur)throws SQLException, BioBookException
    {
        // Appel à la classe Simple connection pour accéder à la base de données
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();

        //preparation of the request
        PreparedStatement pst = null;
        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqUpdateChercheurByLogin);
                
                // On assigne une valeur à chaque "?" présent dans la requète 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passé>       );
                pst.setString(1,unChercheur.getLogin());
                pst.setString(2,unChercheur.getPassword());
                pst.setString(3,unChercheur.getUserName());
                pst.setString(4,unChercheur.getFirstName());
                pst.setString(5,unChercheur.getMail());
                pst.setString(6,unChercheur.getLogin());
                
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
                   }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }
    
    /** Modifie le mdp d'un <code>Chercheur</code> en base de données.
	 * @throws PerfException 
	*/
    public static void updateMDPChercheur(Chercheur unChercheur)throws SQLException, BioBookException
    {
        // Appel à la classe Simple connection pour accéder à la base de données
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();

        //preparation of the request
        PreparedStatement pst = null;
        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqUpdateMDPByLogin);
                
                // On assigne une valeur à chaque "?" présent dans la requète 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passé>       );
                pst.setString(1,unChercheur.getPassword());
                pst.setString(2,unChercheur.getLogin());
                
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
                   }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }
    
    /**
    * @return Liste de tous les chercheurs 
    */
    public Chercheur getChercheur(String login) throws BioBookException{
        // Appel à la classe Simple connection pour accéder à la base de données
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();
        Chercheur unChercheur = null;
        
        //préparation de la requète
        PreparedStatement pst = null;
        ResultSet rs = null;

        try
        {
            pst = c.prepareStatement(reqFindChercheurByLogin);
            
            // On assigne une valeur à chaque "?" présent dans la requète 
            // pst.set<Type>(<Indice Du "?">,   <Valeur passé>       );
            pst.setString(1,login);
            
            // Execution of the request
            // Nécessaire pour tous les SELECT
            rs = pst.executeQuery();
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the request reqFindChercheurByLogin execute it:"+e.getMessage());
        }
        try
        {
                if (rs.next())
                {
                        unChercheur= new Chercheur(rs.getString("login"),rs.getString("password"),rs.getString("name"),rs.getString("firstName"),rs.getString("mail"));
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
    
    /**
    * @return mot de passe du chercheur 
    */
    public String getPassChercheur(String login) throws BioBookException{
        // Appel à la classe Simple connection pour accéder à la base de données
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();
        
        // Initialisation de la variable retournée
        String pass = null;
        
        //pr�paration de la requ�te
        PreparedStatement pst = null;
        ResultSet rs = null;

        try
        {
            pst = c.prepareStatement(reqFindPassChercheurByLogin);

            // On assigne une valeur à chaque "?" présent dans la requète 
            // pst.set<Type>(<Indice Du "?">,   <Valeur passé>       );
            pst.setString(1,login);
            
            // Execution of the request
            // Nécessaire pour tous les SELECT
            rs = pst.executeQuery();
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the request reqFindPassChercheurByLogin execute it:"+e.getMessage());
        }
        try
        {
                if (rs.next())
                {
                        pass= rs.getString("password");
                }
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the password was getting:"+e.getMessage());
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
        return pass;
    }
    
    /**
    * @return Liste de tous les chercheurs 
    */
    public Collection<Chercheur> getChercheurs() throws BioBookException{
        // Appel à la classe Simple connection pour accéder à la base de données
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();
        
        // Initialisation de la liste retournée par la fonction 
        Collection <Chercheur> listChercheurs = new ArrayList<Chercheur>();

        //preparation of the request
        PreparedStatement pst = null;
        ResultSet rs = null;

        try
        {
                pst = c.prepareStatement(reqFindAllChercheurs);
                
                //Execution of the request
                rs = pst.executeQuery();
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the request reqFindAllChercheurs execute it:"+e.getMessage());
        }

        try
        {
                //Fill in a list
                while(rs.next())
                {
                        // Récupération des données revoyées par la base de données
                        //dans la liste listChercheurs 
                        Chercheur unChercheur= new Chercheur(rs.getString("login"),rs.getString("password"),rs.getString("name"),rs.getString("firstName"),rs.getString("mail"));
                        listChercheurs.add(unChercheur);

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
       return listChercheurs;
    } 
    
    /** Delete the <code>Chercheur</code> by login.
    * @throws PerfException 
    */
    public void deleteChercheur(Chercheur chercheur) throws BioBookException{
        
        // Appel à la classe Simple connection pour accéder à la base de données
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();

        //preparation of the request
        PreparedStatement pst = null;

        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqDeleteChercheurByLogin);
                
                // On assigne une valeur à chaque "?" présent dans la requète 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passé>       );
                pst.setString(1,chercheur.getLogin());
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requètes qui modifient la base de données
                pst.executeUpdate();
                c.commit();

        }
        catch(SQLException e)
        {
                throw new BioBookException("Problem in the request reqDeleteChercheurByLogin "+e.getMessage());
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
    
    /** Delete all <code>Chercheur</code>
    * @throws PerfException 
    */
    public static void deleteAllChercheurs() throws BioBookException
    {
        // Appel à la classe Simple connection pour accéder à la base de données
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();

        //preparation of the request		
        PreparedStatement pst = null;

        try
        {
                pst = c.prepareStatement(reqDeleteAllChercheurs);

                // Execution of the request
                // Ceci est necessaire pour toutes les requètes qui modifient la base de données
                pst.executeUpdate();
                c.commit();

        }
        catch(SQLException e)
        {
                throw new BioBookException("Problem in the request DeleteAllChercheurs "+e.getMessage());
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
