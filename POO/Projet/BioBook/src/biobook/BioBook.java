/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook;

import biobook.util.BioBookException;
import biobook.util.MyRandomPassword;
import biobook.view.MainFrame;
import biobook.view.LoginView;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import com.seaglasslookandfeel.SeaGlassLookAndFeel;
import javax.swing.UnsupportedLookAndFeelException;
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
        
        try
        {
          UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
          //Another way is to use the #setLookAndFeel method of the SyntheticaLookAndFeel class
          //SyntheticaLookAndFeel.setLookAndFeel(String className, boolean antiAlias, boolean useScreenMenuOnMac);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }

        LoginView login = new LoginView();
        
    }
   
}
