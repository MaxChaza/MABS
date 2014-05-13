/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.controller;

import biobook.model.Doc;
import biobook.model.DocumentJoint;
import biobook.model.Experience;
import biobook.model.Materiel;
import biobook.util.BioBookException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import biobook.util.BioBookException;
import biobook.util.MD5;
import biobook.util.SimpleConnection;
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
import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Maxime
 */
public class GererDocument implements GererDocumentJoint{
    private static final String reqInsert = "INSERT INTO document (labelDocument, valeur, labelExperience)VALUES(?,?,?)";    
    private static final String reqAllByExp= "SELECT * FROM document where labelExperience=?";
    private static final String reqDeleteAllByExp= "DELETE * FROM document WHERE labelExperience=?"; 
    private static final String reqDelete= "DELETE FROM document WHERE labelExperience=? and labelDocument=?";
    private static Connection c;
    
    public GererDocument(){
        // Appel ÃƒÂ  la classe Simple connection pour accÃƒÂ©der ÃƒÂ  la base de donnÃƒÂ©es
        c = null;
        c = SimpleConnection.getInstance().getConnection();
    }
        /** Insert un <code>Chercheur</code> en base de donnï¿½es.
     * @param docJoint 
     * @throws java.sql.SQLException
     * @throws biobook.util.BioBookException
     * @throws java.security.NoSuchAlgorithmException 
     * @throws java.io.IOException 
     * @throws java.io.FileNotFoundException 
     * @throws java.lang.ClassNotFoundException 
	*/

    @Override
    public void insert(DocumentJoint docJoint) throws BioBookException {
        PreparedStatement pst = null;
        try
        {
                pst = c.prepareStatement(reqInsert);
                              
                pst.setString(1,docJoint.getNom());
                pst.setString(2,docJoint.getValeur());
                pst.setString(3,docJoint.getExperience().getLabel());
                
                
                               
                pst.executeUpdate();
          
                c.commit();
        }
        catch(SQLException e)
        {
           throw new BioBookException("Problem in the request reqInsert "+e.getMessage());
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

    @Override
    public HashSet getAllByExp(String labExp) throws BioBookException {
        HashSet  listDocuments = new HashSet<>();

        //preparation of the request
        PreparedStatement pst = null;
        ResultSet rs = null;

        try
        {
            pst = c.prepareStatement(reqAllByExp);
            pst.setString(1,labExp);

            

            //Execution of the request
            rs = pst.executeQuery();
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the request reqAllbyExp execute it:"+e.getMessage());
        }

        try
        {
                //Fill in a list
                while(rs.next())
                {
                        // RÃƒÂ©cupÃƒÂ©ration des donnÃƒÂ©es revoyÃƒÂ©es par la base de donnÃƒÂ©es
                        //dans la liste listChercheurs 
                        GererExperience g = new GererExperience();
                        Doc document= new Doc(rs.getString("labelDocument"),rs.getString("valeur"),g.getExperience(rs.getString("labelExperience")));
                        listDocuments.add(document);

                }
        }
        catch (SQLException e)
        {
                throw new BioBookException("Problem when the list of Document was create:"+e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(GererDocument.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GererDocument.class.getName()).log(Level.SEVERE, null, ex);
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
       return listDocuments;
    } 

    @Override
    public void deleteAllByExp(String labExp)throws BioBookException {
        PreparedStatement pst = null;

        try
        {
                //Execution of the request
                pst = c.prepareStatement(reqDeleteAllByExp);
                
                // On assigne une valeur Ã  chaque "?" prÃ©sent dans la requÃ¨te 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passÃ©>       );
                pst.setString(1,labExp);
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requÃ¨tes qui modifient la base de donnÃ©es
                pst.executeUpdate();
                c.commit();

        }
        catch(SQLException e)
        {
                throw new BioBookException("Problem in the request reqDeleteAllByExp "+e.getMessage());
        }
        finally
        {
            try {
            if (pst!=null)    {  pst.close();}
               }catch (SQLException e){
            }
        }
    }

    @Override
    public void delete(DocumentJoint docJoint) throws BioBookException{
         // Appel ÃƒÂ  la classe Simple connection pour accÃƒÂ©der ÃƒÂ  la base de donnÃƒÂ©es
        Connection c=null;
        c = SimpleConnection.getInstance().getConnection();

        //preparation of the request		
        PreparedStatement pst = null;

        try
        {
                pst = c.prepareStatement(reqDelete);

                // On assigne une valeur ÃƒÂ  chaque "?" prÃƒÂ©sent dans la requÃƒÂ¨te 
                // pst.set<Type>(<Indice Du "?">,   <Valeur passÃƒÂ©>       );
                pst.setString(1,docJoint.getNom());
                pst.setString(2,docJoint.getValeur());
                
                // Execution of the request
                // Ceci est necessaire pour toutes les requÃƒÂ¨tes qui modifient la base de donnÃƒÂ©es
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

    @Override
    public void serializerDocumentJointExperience(DocumentJoint documentjoint) throws FileNotFoundException, IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HashSet deserializerDocumentJoints() throws FileNotFoundException, IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DocumentJoint deserializerUnDocumentJoint(String nom) throws FileNotFoundException, IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
    
   
        

    

