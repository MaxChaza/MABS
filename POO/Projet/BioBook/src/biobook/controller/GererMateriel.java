package biobook.controller;

import biobook.model.Materiel;
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
import java.util.HashSet;
import javax.management.MBeanAttributeInfo;

public class GererMateriel {
    
    
    private static final String reqInsertIntoMateriel = "INSERT INTO Materiel (labelMateriel)VALUES(?)";    
    private static final String reqFindAllMateriels = "SELECT * FROM Materiel";
    private static final String reqDeleteMateriel = "DELETE FROM Materiel WHERE labelMateriel=?";
    private static final String reqDeleteAllMateriels = "DELETE FROM Materiel";
    private static Connection c;
   
    public GererMateriel(){
        // Appel à la classe Simple connection pour accéder à la base de données
        c = null;
        c = SimpleConnection.getInstance().getConnection();    
    }
    
    public static void insertMateriel(Materiel unMateriel)throws SQLException, BioBookException, NoSuchAlgorithmException
    {
        PreparedStatement pst = null;
        try
        {
                pst = c.prepareStatement(reqInsertIntoMateriel);
                              
                pst.setString(1,unMateriel.getName());
                               
                pst.executeUpdate();
          
                c.commit();
        }
        catch(SQLException e)
        {
           throw new BioBookException("Problem in the request reqInsertIntoMateriel "+e.getMessage());
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
  
    
    public Collection<Materiel> getMateriels() throws BioBookException, SQLException{
        
        // Initialisation de la liste retournÃ©e par la fonction 
        Collection <Materiel> listMateriels= new HashSet<>();

        //preparation of the request
        PreparedStatement pst = null;
        ResultSet rs = null;

        try
        {
                pst = c.prepareStatement(reqFindAllMateriels);
                
                //Execution of the request
                rs = pst.executeQuery();
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the request reqFindAllMateriels execute it:"+e.getMessage());
        }

        //Fill in a list
        while(rs.next())
        {
                // RÃ©cupÃ©ration des donnÃ©es revoyÃ©es par la base de donnÃ©es
                //dans la liste listChercheurs 
                Materiel unMateriel= new Materiel(rs.getString("labelMateriel"));
                listMateriels.add(unMateriel);

        }
       
        {
                try {
                if (rs!=null)   {   rs.close();}
                if (pst!=null)    {  pst.close();}
                   }catch (Exception e){
                    e.printStackTrace();
                }
        }
       return listMateriels;
    } 
    
    /** Delete the <code>Chercheur</code> by login.
    * @throws PerfException 
    */
    public void deleteMateriel(Materiel materiel) throws BioBookException{
        
        // Appel Ã  la classe Simple connection pour accÃ©der Ã  la base de donnÃ©es
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();

        //preparation of the request
        PreparedStatement pst = null;

        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqDeleteMateriel);
                
                // On assigne une valeur Ã  chaque "?" prÃ©sent dans la requÃ¨te 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passÃ©>       );
                pst.setString(1,materiel.getName());
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requÃ¨tes qui modifient la base de donnÃ©es
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
    public static void deleteAllMateriels() throws BioBookException
    {
        // Appel Ã  la classe Simple connection pour accÃ©der Ã  la base de donnÃ©es
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();

        //preparation of the request		
        PreparedStatement pst = null;

        try
        {
                pst = c.prepareStatement(reqDeleteAllMateriels);

                // Execution of the request
                // Ceci est necessaire pour toutes les requÃ¨tes qui modifient la base de donnÃ©es
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
