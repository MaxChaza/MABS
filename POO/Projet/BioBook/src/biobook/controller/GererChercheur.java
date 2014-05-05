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
    private static Connection c;
    
    public GererChercheur(){
        // Appel à la classe Simple connection pour accéder à la base de données
        c = null;
        c = SimpleConnection.getInstance().getConnection();    
    }
    
    /** Insert un <code>Chercheur</code> en base de donn�es.
     * @param unChercheur
     * @throws java.sql.SQLException
     * @throws biobook.util.BioBookException
     * @throws java.security.NoSuchAlgorithmException 
     * @throws java.io.IOException 
     * @throws java.io.FileNotFoundException 
     * @throws java.lang.ClassNotFoundException 
	*/
    public static void insertChercheur(Chercheur unChercheur)throws SQLException, BioBookException, NoSuchAlgorithmException, IOException, FileNotFoundException, ClassNotFoundException
    {
        
        serializerChercheur(unChercheur);
        // On teste si la connexion a été mise en place
        if(c!=null)
        {
            System.out.println("ok");
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
                   }catch (SQLException e){
                }
            }
        }
    }
    /** Modifie un <code>Chercheur</code> en base de données.
     * @param unChercheur
     * @throws java.sql.SQLException
     * @throws biobook.util.BioBookException
	*/
    public static void updateChercheur(Chercheur unChercheur)throws SQLException, BioBookException
    {
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
                   }catch (SQLException e){
                }
        }
    }
    
    /** Modifie le mdp d'un <code>Chercheur</code> en base de données.
     * @param unChercheur
     * @throws java.sql.SQLException
     * @throws biobook.util.BioBookException 
    */
    public static void updateMDPChercheur(Chercheur unChercheur)throws SQLException, BioBookException
    {
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
                   }catch (SQLException e){
                }
        }
    }
    
    /**
     * @param login
     * @return Liste de tous les chercheurs 
     * @throws biobook.util.BioBookException 
    */
    public static Chercheur getChercheur(String login) throws BioBookException, IOException, FileNotFoundException, ClassNotFoundException{
        Chercheur unChercheur = null;
        
        if(c==null)
        {
            unChercheur = deserializerUnChercheur(login);
        }
        else
        {
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
                       }catch (SQLException e){
                    }
            }
        }
        return unChercheur;
    }
    
    /**
     * @param login
    * @return mot de passe du chercheur 
     * @throws biobook.util.BioBookException 
    */
    public String getPassChercheur(String login) throws BioBookException, IOException, ClassNotFoundException{
        // Initialisation de la variable retournée
        String pass = null;
        if(c==null)
        {
            Chercheur unChercheur = deserializerUnChercheur(login);
            pass = unChercheur.getPassword();
        }
        else
        {
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
                       }catch (SQLException e){
                    }
            }
        }
        return pass;
    }
    
    /**
    * @return Liste de tous les chercheurs 
     * @throws biobook.util.BioBookException 
     * @throws java.io.FileNotFoundException 
     * @throws java.lang.ClassNotFoundException 
    */
    public Collection<Chercheur> getChercheurs() throws BioBookException, IOException, FileNotFoundException, ClassNotFoundException{
        // Initialisation de la liste retournée par la fonction 
        Collection <Chercheur> listChercheurs = new HashSet<Chercheur>();
        if(c==null)
        {
            listChercheurs = deserializerChercheurs();
        }
        else
        {
            // Initialisation de la liste retournée par la fonction 
            
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
                       }catch (SQLException e){
                    }
            }
        }
       return listChercheurs;
    } 
    
    /** Delete the <code>Chercheur</code> by login.
     * @param chercheur
     * @throws biobook.util.BioBookException 
    */
    public void deleteChercheur(Chercheur chercheur) throws BioBookException{
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
               }catch (SQLException e){
            }
        }
    }
    
    /** Delete all <code>Chercheur</code>
     * @throws biobook.util.BioBookException 
    */
    public static void deleteAllChercheurs() throws BioBookException
    {
        //preparation of the request		
        PreparedStatement pst = null;
        File fichier = new File("./src/biobook/serialisation/chercheur.ser");
        if (fichier.exists()) {
            fichier.delete();
        }
        
        if(c!=null)
        {
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
                   }catch (SQLException e){
                }
            }

        }
    }
    
    // Il est aussi nécessaire de sérialiser les classes lorsque l'accès à la base de données n'est pas réalisé 
    /**
     * Insert un <code>Chercheur</code> dans un fichier.
     *
     * @param chercheur
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public static void serializerChercheur(Chercheur chercheur) throws FileNotFoundException, IOException, ClassNotFoundException {
        // Recherche si ce chercheur existe
        if(deserializerUnChercheur(chercheur.getLogin()) == null)
        {
            // Si il existe on insert le chercheur dans le fichier
            // Ouverture du ficher de serialisation des chercheur
            FileOutputStream fichier = new FileOutputStream("./src/biobook/serialisation/chercheur.ser", true);
            try (ObjectOutputStream oos = new ObjectOutputStream(fichier)) {
                oos.writeObject(chercheur);
                oos.flush();
                oos.close();
                fichier.close(); 
            }
        }
//        else
//        {
//             if(!searchSerialisedChercheur(chercheur.getLogin()).equals(chercheur)){
//                FileOutputStream fichier = new FileOutputStream("./src/biobook/serialisation/chercheur.ser", true);
//                try (ObjectOutputStream oos = new ObjectOutputStream(fichier)) {
//                    oos.writeObject(chercheur);
//                    oos.flush();
//                    oos.close();
//                    fichier.close(); 
//                }
//            }  
//        }
    }
    
    /**
     * récupere les <code>Chercheur</code> dans un fichier.
     *
     * @return 
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public static HashSet<Chercheur> deserializerChercheurs() throws FileNotFoundException, IOException, ClassNotFoundException {
        HashSet<Chercheur> listChercheurs = new HashSet<>();
        try {
        // Ouverture du ficher de serialisation des chercheur   
            try (FileInputStream fichier = new FileInputStream("./src/biobook/serialisation/chercheur.ser")) {
                ObjectInputStream ois = new ObjectInputStream(fichier);
                // Iteration sur chaque objet du fichier
                while(ois != null){
                    Chercheur chercheur = (Chercheur) ois.readObject();
                    listChercheurs.add(chercheur);
                    ois = new ObjectInputStream(fichier);                    
                }
            }
        } catch (java.io.IOException | ClassNotFoundException e) {
        }
        return listChercheurs;
    }
    
    /**
     * récupere un <code>Chercheur</code> dans un fichier.
     *
     * @param login
     * @return 
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public static Chercheur deserializerUnChercheur(String login) throws FileNotFoundException, IOException, ClassNotFoundException {
        Chercheur aChercheur = null;
        try {
            // Ouverture du ficher de serialisation des chercheur   
            try (FileInputStream fichier = new FileInputStream("./src/biobook/serialisation/chercheur.ser")) {
                ObjectInputStream ois = new ObjectInputStream(fichier);
                Chercheur chercheur = (Chercheur) ois.readObject();
                while(ois != null && aChercheur==null){
                    if(chercheur.getLogin().equals(login))
                        aChercheur=chercheur;
                    else
                        ois = new ObjectInputStream(fichier);                    
                }
            }
        } catch (java.io.IOException | ClassNotFoundException e) {
        }
        return aChercheur;
    }
}
