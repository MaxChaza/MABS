/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook;

import biobook.controller.GererChercheur;
import biobook.controller.GererChercheurExperience;
import biobook.controller.GererMateriel;
import biobook.controller.GererMaterielExperience;
import biobook.model.Chercheur;
import biobook.model.Materiel;
import biobook.util.BioBookException;
import biobook.view.LoginView;
import biobook.view.MainFrame;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
        
/**
 *
 * @author Maxime
 */
public class BioBook {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws BioBookException, SQLException, IOException, FileNotFoundException, ClassNotFoundException, NoSuchAlgorithmException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
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
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Label.font",new Font("Arial",Font.PLAIN,15));
        
        UIManager.put("MenuItem.font",new Font("Arial",Font.PLAIN,12));
        UIManager.put("Menu.font",new Font("Arial",Font.PLAIN,12));
        UIManager.put("TabbedPane.font",new Font("Arial",Font.PLAIN,12));
//        LoginView login;
//        login = new LoginView();
    
        MainFrame m = new MainFrame("a");
    }
}
