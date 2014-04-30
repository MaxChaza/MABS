/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook;

import biobook.util.BioBookException;
import biobook.view.LoginView;
import java.sql.SQLException;

/**
 *
 * @author Maxime
 */
public class BioBook {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws BioBookException, SQLException {
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
        
        LoginView login;
        login = new LoginView();
        
    }
   
}
