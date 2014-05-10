/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.view;

import biobook.controller.GererChercheur;
import biobook.controller.LoginController;
import biobook.model.Chercheur;
import biobook.util.BioBookException;
import biobook.util.JProgressBarMail;
import biobook.util.SendEmail;
import biobook.util.SendEmail;
import com.seaglasslookandfeel.ui.SeaGlassButtonUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author Maxime
 */
public class LoginView extends JFrame implements ActionListener, KeyListener{
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    private JTextField log;
    private JPasswordField pass;

    
    JButton annuler;
    JButton valider; 
    JButton enregistrer; 
    JButton mdpOublie;
    public Container c;
    public JPanel tout;
    LoginController ctrl ;
    
    public LoginView() throws BioBookException, SQLException{
        
        ctrl = new LoginController(this);
        // Caractéristiques JFrame
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        c = getContentPane();
        c.setLayout(new BorderLayout());

        tout = new JPanel(new BorderLayout());
        // Composants de la fenetre
        JPanel nord = new JPanel(new FlowLayout());
        // Image
        
        
        ImageIcon image = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\logoFrame2.gif");
        Image i = image.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH);
        JLabel jLabelImage = new JLabel(new ImageIcon(i));
        
        nord.add(jLabelImage);

        //Panel du formulaire
        JPanel jPanelLogin = new JPanel(new GridBagLayout());
        
        JLabel titreLog = new JLabel("Login");
        log = new JTextField("");
        log.setPreferredSize(new Dimension(200,20));
        log.addKeyListener(this);
        
        GridBagConstraints gridBagConstraintsLog = new GridBagConstraints();
        gridBagConstraintsLog.gridx = 1;
        gridBagConstraintsLog.gridy = 0;
        gridBagConstraintsLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsLog.insets = new Insets(0, 0, 10, 50);
        
        GridBagConstraints gridBagConstraintsTitreLog = new GridBagConstraints();
        gridBagConstraintsTitreLog.gridx = 0;
        gridBagConstraintsTitreLog.gridy = 0;
        gridBagConstraintsTitreLog.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreLog.insets = new Insets(0, 0, 10, 50);
        
        jPanelLogin.add(titreLog, gridBagConstraintsTitreLog);
        jPanelLogin.add(log,gridBagConstraintsLog);

        
        JLabel titrePass = new JLabel("Password ");
        pass = new JPasswordField("");
        pass.setPreferredSize(new Dimension(200,20));
        pass.addKeyListener(this);
        
        GridBagConstraints gridBagConstraintsPass = new GridBagConstraints();
        gridBagConstraintsPass.gridx = 1;
        gridBagConstraintsPass.gridy = 1;
        gridBagConstraintsPass.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsPass.insets = new Insets(0, 0, 0, 50);
        
        GridBagConstraints gridBagConstraintsTitrePass = new GridBagConstraints();
        gridBagConstraintsTitrePass.gridx = 0;
        gridBagConstraintsTitrePass.gridy = 1;
        gridBagConstraintsTitrePass.fill = GridBagConstraints.HORIZONTAL;
        
        jPanelLogin.add(titrePass, gridBagConstraintsTitrePass);
        jPanelLogin.add(pass, gridBagConstraintsPass);

        nord.add(jPanelLogin);
        // Panel des boutons
        JPanel jPanelBouton = new JPanel(new FlowLayout());

        valider = new JButton("Valider");
        
//        valider.setUI(new SeaGlassButtonUI());
        valider.addActionListener(this);
        
        annuler = new JButton("Annuler");
        annuler.addActionListener(this);
        
        enregistrer = new JButton("S'enregistrer");
        enregistrer.addActionListener(this);
        
        mdpOublie = new JButton("Mot de passe oublié");
        mdpOublie.addActionListener(this);
        
        jPanelBouton.add(annuler);
        jPanelBouton.add(valider);
        jPanelBouton.add(enregistrer);
        jPanelBouton.add(mdpOublie);

        // Panel général
        tout.add(BorderLayout.CENTER, nord);
        tout.add(BorderLayout.SOUTH, jPanelBouton);
        
        c.add(tout);
//        // Suppression de l'entete de la fenetre
//        setUndecorated(true);
//        // Modifier le bord de la frame
//        getRootPane().setBorder(new NimbusFrameBorder());
//        
        
        // Nouvel icone de l'application
        setIconImage(getToolkit().getImage("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\logoFrame.gif"));
        // On fixe le panel sur la fenètre
        setName("Login");
        setVisible(true);
        pack();
        setMinimumSize(getSize());
        
        Dimension tailleEcran = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
        setLocation((int)((tailleEcran.getWidth()/2)-(getSize().getWidth()/2)), (int)((tailleEcran.getHeight()/2)-(getSize().getHeight()/2)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==valider){
            boolean logIsSet = false;
            boolean passIsSet = false;
            if(!log.getText().equals("")){
                logIsSet=true;
                log.setBorder(UIManager.getBorder("TextField.border"));
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
//                 pass.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder(e));
               
                log.setBorder(border);
            }
            if(!pass.getText().equals("")){
                passIsSet=true;
                pass.setBorder(UIManager.getBorder("PasswordField.border"));
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                pass.setBorder(border);
            }
            if(logIsSet && passIsSet)
            {
                try {
                    try {
                        ctrl.clickValider();
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (BioBookException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(e.getSource()==annuler)
            try {
            ctrl.clickAnnuler();
        } catch (BioBookException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(e.getSource()==enregistrer){
            tout.setVisible(false);
            ctrl.clickEnregistrer();
        }
        
        if(e.getSource()==mdpOublie){
 
            boolean logIsSet = false;
            if(!log.getText().equals("")){
                
          
                logIsSet=true;
                log.setBorder(UIManager.getBorder("TextField.border"));
                try {
                    try {
                        ctrl.clickMDPOublie();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (BioBookException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
                }
                  
                  
                  
          
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                log.setBorder(border);
                JOptionPane.showMessageDialog(null, "Veuillez saisir votre identifiant!");
            }
        }
        
//        if(e.getSource()==mdpOublie)
//            ctrl.clickMDPOublie(log.getText());
    }
    
    // Ecouteur de clavier
    @Override
    public void keyTyped(KeyEvent e) {
         System.out.println("ok");
    }

    @Override
    public void keyPressed(KeyEvent e) {   
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
        {
            valider.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
         System.out.println("ok");
    }
    
    public String getPass() {
        return pass.getText();
    }

    public String getLog() {
        return log.getText();
    }

    
}
