/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.controller;

import biobook.model.Chercheur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import biobook.util.SimpleConnection;
import biobook.util.BioBookException;
import java.util.Collection;
/**
 * Permet de gérer les chercheurs en base de données
 * @author Maxime
 */
public class GererChercheur {
    
    private static final String reqInsertIntoChercheur = "INSERT INTO Chercheur (login, password, name, firstName, mail)VALUES(?,?,?,?,?)";    
    private static final String reqFindAllChercheurs = "SELECT * FROM Chercheur";
    private static final String reqFindChercheurByLogin = "SELECT * FROM Chercheur WHERE login=?";
    private static final String reqFindPassChercheurByLogin = "SELECT password FROM Chercheur WHERE login=?";
    private static final String reqUpdateMDPByLogin = "UPDATE Chercheur SET password=? WHERE login=?";
    
    /** Insert une <code>measure</code> en base de donn�es.
	 * @throws PerfException 
	*/
    public static void insertChercheur(Chercheur unChercheur)throws SQLException, BioBookException
    {
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();

        //preparation of the request
        PreparedStatement pst = null;

        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqInsertIntoChercheur);
                
                pst.setString(1,unChercheur.getLogin());
                pst.setString(2,unChercheur.getPassword());
                pst.setString(3,unChercheur.getName());
                pst.setString(4,unChercheur.getFirstName());
                pst.setString(5,unChercheur.getMail());

                pst.executeUpdate();
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
     
    /** Modifie un <code>Chercheur</code> en base de donn�es.
	 * @throws PerfException 
	*/
    public static void updateMDPChercheur(Chercheur unChercheur)throws SQLException, BioBookException
    {
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();

        //preparation of the request
        PreparedStatement pst = null;
        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqUpdateMDPByLogin);
                
                pst.setString(1,unChercheur.getPassword());
                pst.setString(2,unChercheur.getLogin());
                pst.executeUpdate();
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
        System.out.println("ok");
    }
    
    /**
    * @return Liste de tous les chercheurs 
    */
    public Chercheur getChercheur(String login) throws BioBookException{
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();
        Chercheur unChercheur = null;
        
        //pr�paration de la requ�te
        PreparedStatement pst = null;
        ResultSet rs = null;

        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqFindChercheurByLogin);
                pst.setString(1,login);
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
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();
        String pass = null;
        
        //pr�paration de la requ�te
        PreparedStatement pst = null;
        ResultSet rs = null;

        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqFindPassChercheurByLogin);
                pst.setString(1,login);
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
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();
        Collection <Chercheur> listChercheurs = new ArrayList<Chercheur>();

        //preparation of the request
        PreparedStatement pst = null;
        ResultSet rs = null;

        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqFindAllChercheurs);
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
    
    /**
    * @param Un nouveau chercheur 
    */
    void addChercheur(String login, String pass, String name, String firstName, String mail) {
        
    }
//    
//    /**
//    * 
//    * @param Un chercheur avec de nouveaux paramètres 
//    */
//    public void updateChercheur(Chercheur chercheur){
//                
//    }
//    
    /**
    * @param Un nouveau chercheur 
    */
    public void deleteChercheur(Chercheur chercheur){
                
    }

}
