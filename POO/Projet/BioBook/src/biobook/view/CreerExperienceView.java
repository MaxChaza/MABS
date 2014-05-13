/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biobook.view;

import biobook.controller.CreerExperienceController;
import biobook.controller.GererChercheur;
import biobook.controller.GererExperience;
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
import java.io.FileNotFoundException;
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
public class CreerExperienceView extends JFrame implements ActionListener, KeyListener{
    
    private JTextField labelExperience;
    private JTextField problem;
    private JTextField context;
    private JTextField stateOfTheArt;
    private JTextField assumption;

    private JButton annuler;
    private JButton reset;
    private JButton valider; 
    
    public Container c;
    private CreerExperienceController ctrl ;
    private MainFrame main;
    GererExperience gererExperience;
    
    public CreerExperienceView(MainFrame m) throws BioBookException, SQLException{
        
        ctrl = new CreerExperienceController(this);
        main = m;
        gererExperience= new GererExperience();
    
        // Caractéristiques JFrame
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        c = getContentPane();
        c.setLayout(new BorderLayout());

        // Composants de la fenetre
        JPanel form = new JPanel(new GridBagLayout());
        
        JLabel titreLabelExperience = new JLabel("Nom de l'expérience :");
        JLabel titreProblem = new JLabel("Problème :");
        JLabel titreContext = new JLabel("Contexte :");
        JLabel titreStateOfTheArt = new JLabel("Etat de l'art :");
        JLabel titreAssumption = new JLabel("Hypothèse :");
        
        labelExperience = new JTextField("");
        labelExperience.setPreferredSize(new Dimension(200,20));
        labelExperience.addKeyListener(this);
        
        problem = new JTextField("");
        problem.setPreferredSize(new Dimension(200,20));
        problem.addKeyListener(this);
        
        context = new JTextField("");
        context.setPreferredSize(new Dimension(200,20));
        context.addKeyListener(this);
        
        stateOfTheArt = new JTextField("");
        stateOfTheArt.setPreferredSize(new Dimension(200,20));
        stateOfTheArt.addKeyListener(this);
        
        assumption = new JTextField("");
        assumption.setPreferredSize(new Dimension(200,20));
        assumption.addKeyListener(this);
        
        //Contrainte du nom de l'experience
        GridBagConstraints gridBagConstraintsTitreLibExp = new GridBagConstraints();
        gridBagConstraintsTitreLibExp.gridx = 0;
        gridBagConstraintsTitreLibExp.gridy = 0;
        gridBagConstraintsTitreLibExp.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreLibExp.insets = new Insets(20, 50, 10, 50);
        
        GridBagConstraints gridBagConstraintsLibExp = new GridBagConstraints();
        gridBagConstraintsLibExp.gridx = 1;
        gridBagConstraintsLibExp.gridy = 0;
        gridBagConstraintsLibExp.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsLibExp.insets = new Insets(20, 0, 10, 50);
        
        //Contrainte du nom de l'experience
        GridBagConstraints gridBagConstraintsTitreProblem = new GridBagConstraints();
        gridBagConstraintsTitreProblem.gridx = 0;
        gridBagConstraintsTitreProblem.gridy = 1;
        gridBagConstraintsTitreProblem.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreProblem.insets = new Insets(0, 50, 10, 50);
        
        GridBagConstraints gridBagConstraintsProblem = new GridBagConstraints();
        gridBagConstraintsProblem.gridx = 1;
        gridBagConstraintsProblem.gridy = 1;
        gridBagConstraintsProblem.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsProblem.insets = new Insets(0, 0, 10, 50);
        
        //Contrainte du nom de l'experience
        GridBagConstraints gridBagConstraintsTitreContext = new GridBagConstraints();
        gridBagConstraintsTitreContext.gridx = 0;
        gridBagConstraintsTitreContext.gridy = 2;
        gridBagConstraintsTitreContext.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreContext.insets = new Insets(0, 50, 10, 50);
        
        GridBagConstraints gridBagConstraintsContext = new GridBagConstraints();
        gridBagConstraintsContext.gridx = 1;
        gridBagConstraintsContext.gridy = 2;
        gridBagConstraintsContext.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsContext.insets = new Insets(0, 0, 10, 50);
        
        //Contrainte du nom de l'experience
        GridBagConstraints gridBagConstraintsTitreStateOfTheArt = new GridBagConstraints();
        gridBagConstraintsTitreStateOfTheArt.gridx = 0;
        gridBagConstraintsTitreStateOfTheArt.gridy = 3;
        gridBagConstraintsTitreStateOfTheArt.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreStateOfTheArt.insets = new Insets(0, 50, 10, 50);
        
        GridBagConstraints gridBagConstraintsStateOfTheArt = new GridBagConstraints();
        gridBagConstraintsStateOfTheArt.gridx = 1;
        gridBagConstraintsStateOfTheArt.gridy = 3;
        gridBagConstraintsStateOfTheArt.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsStateOfTheArt.insets = new Insets(0, 0, 10, 50);
        
        //Contrainte du nom de l'experience
        GridBagConstraints gridBagConstraintsTitreAssumption = new GridBagConstraints();
        gridBagConstraintsTitreAssumption.gridx = 0;
        gridBagConstraintsTitreAssumption.gridy = 4;
        gridBagConstraintsTitreAssumption.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreAssumption.insets = new Insets(0, 50, 10, 50);
        
        GridBagConstraints gridBagConstraintsAssumption = new GridBagConstraints();
        gridBagConstraintsAssumption.gridx = 1;
        gridBagConstraintsAssumption.gridy = 4;
        gridBagConstraintsAssumption.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsAssumption.insets = new Insets(0, 0, 10, 50);
        
        form.add(titreLabelExperience, gridBagConstraintsTitreLibExp);
        form.add(labelExperience,gridBagConstraintsLibExp);
        
        form.add(titreProblem, gridBagConstraintsTitreProblem);
        form.add(problem,gridBagConstraintsProblem);
        
        form.add(titreContext, gridBagConstraintsTitreContext);
        form.add(context,gridBagConstraintsContext);
        
        form.add(titreStateOfTheArt, gridBagConstraintsTitreStateOfTheArt);
        form.add(stateOfTheArt,gridBagConstraintsStateOfTheArt);
        
        form.add(titreAssumption, gridBagConstraintsTitreAssumption);
        form.add(assumption,gridBagConstraintsAssumption);
        
        // Panel contenant les bouttons
        JPanel panelBouttons = new JPanel(new FlowLayout());
        
        annuler = new JButton("Annuler");
        annuler.addActionListener(this);
        reset = new JButton("Reset");
        reset.addActionListener(this);
        valider = new JButton("Valider");
        valider.addActionListener(this);
        
        panelBouttons.add(annuler);
        panelBouttons.add(reset);
        panelBouttons.add(valider);
        
        c.add(BorderLayout.NORTH, form);
        c.add(BorderLayout.SOUTH, panelBouttons);
        
        // Nouvel icone de l'application
        setIconImage(getToolkit().getImage("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\logoFrame.gif"));
        // On fixe le panel sur la fenètre
        setTitle("Créer une expérience");
        Dimension tailleFenetre = new Dimension(510, 250);
        setMinimumSize(tailleFenetre);
        setMaximumSize(tailleFenetre);
        setVisible(true);
        pack();
        Dimension tailleEcran = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
        setLocation((int)((tailleEcran.getWidth()/2)-(getSize().getWidth()/2)), (int)((tailleEcran.getHeight()/2)-(getSize().getHeight()/2)));
    }
@Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==valider){
            boolean libIsSet = false;
            boolean problemIsSet = false;
            boolean contextIsSet = false;
            boolean assumptionIsSet = false;
            boolean stateOfTheArtIsSet = false;
            
            // Teste si le nom est remplit
            if(!labelExperience.getText().equals("")){
                libIsSet=true;
                labelExperience.setBorder(UIManager.getBorder("TextField.border"));
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                labelExperience.setBorder(border);
            }
            
            // Teste si le problem est remplit
            if(!problem.getText().equals("")){
                problemIsSet=true;
                problem.setBorder(UIManager.getBorder("TextField.border"));
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                problem.setBorder(border);
            }
            
            // Teste si le context est remplit
            if(!context.getText().equals("")){
                contextIsSet=true;
                context.setBorder(UIManager.getBorder("TextField.border"));
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                context.setBorder(border);
            }
            
            // Teste si l'etat de l'art est remplit
            if(!stateOfTheArt.getText().equals("")){
                stateOfTheArtIsSet=true;
                stateOfTheArt.setBorder(UIManager.getBorder("TextField.border"));
                }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                stateOfTheArt.setBorder(border);
            }
            
            // Teste si ll'hypothese est remplit
            if(!assumption.getText().equals("")){
                assumptionIsSet=true;
                assumption.setBorder(UIManager.getBorder("TextField.border"));
            }
            else
            {
                //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.RED, 1);
                assumption.setBorder(border);
            }
            
            if(assumptionIsSet && contextIsSet && libIsSet && problemIsSet && stateOfTheArtIsSet)
            {
                try {
                    if(!gererExperience.libelleExist(labelExperience.getText())){
                        try {
                            ctrl.clickValider();
                            JOptionPane.showMessageDialog(null, "Votre expérience a bien été créé.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
//                            int reply = JOptionPane.showConfirmDialog(null, "Voulez vous creer une autre expérience?", null, JOptionPane.OK_OPTION);
//                            if (reply == JOptionPane.NO_OPTION)
//                            {
//                                dispose();
//                            }
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(CreerExperienceView.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BioBookException ex) {
                            Logger.getLogger(CreerExperienceView.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(CreerExperienceView.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(CreerExperienceView.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(CreerExperienceView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else
                    {
                        Border border = BorderFactory.createLineBorder(Color.RED, 1);
                        labelExperience.setBorder(border);
                        JOptionPane.showMessageDialog(null, "Ce nom d'expérience est déjà utilisé.", "Attention", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (BioBookException ex) {
                    Logger.getLogger(CreerExperienceView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CreerExperienceView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CreerExperienceView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Veullez saisir tous les champs.");
            }
        }
        
        if(e.getSource()==annuler) {
            ctrl.clickAnnuler();
        }
        
        if(e.getSource()==reset) {
            ctrl.clickReset();
        }
    }

    public JTextField getLabelExperience() {
        return labelExperience;
    }

    public void setLabelExperience(JTextField labelExperience) {
        this.labelExperience = labelExperience;
    }

    public JTextField getProblem() {
        return problem;
    }

    public void setProblem(JTextField problem) {
        this.problem = problem;
    }

    public JTextField getContext() {
        return context;
    }

    public void setContext(JTextField context) {
        this.context = context;
    }

    public JTextField getStateOfTheArt() {
        return stateOfTheArt;
    }

    public void setStateOfTheArt(JTextField stateOfTheArt) {
        this.stateOfTheArt = stateOfTheArt;
    }

    public JTextField getAssumption() {
        return assumption;
    }

    public void setAssumption(JTextField assumption) {
        this.assumption = assumption;
    }

    public MainFrame getMain() {
        return main;
    }

    public void setMain(MainFrame main) {
        this.main = main;
    }
    
    
    // Ecouteur de clavier
    @Override
    public void keyTyped(KeyEvent e) {
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
    }
}
