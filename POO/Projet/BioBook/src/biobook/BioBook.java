/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook;

import biobook.controller.GererChercheur;
import biobook.controller.GererExperience;
import biobook.model.Chercheur;
import biobook.model.Experience;
import biobook.util.BioBookException;
import biobook.util.SimpleConnection;
import biobook.view.LoginView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Maxime
 */
public class BioBook {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws BioBookException, SQLException, IOException, FileNotFoundException, ClassNotFoundException, NoSuchAlgorithmException {
        // TODO code application logic here
        
//        try
//        {
//          UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
//          //Another way is to use the #setLookAndFeel method of the SyntheticaLookAndFeel class
//          //SyntheticaLookAndFeel.setLookAndFeel(String className, boolean antiAlias, boolean useScreenMenuOnMac);
//        }
//        catch (Exception e)
//        {
//          e.printStackTrace();
//        }
        
        GererExperience g=new GererExperience();
        g.serializerExperience(new Experience("e", "e", "e", "e", "e", null));
        System.out.println(g.deserializerUnExperience("e"));

//        LoginView login;
//        login = new LoginView();
        
    }
   
}
