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
        LoginView login = new LoginView();
        
    }
}
