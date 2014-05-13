/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.view;

import biobook.controller.ExpPersoController;
import biobook.model.Chercheur;
import biobook.model.Experience;
import biobook.util.BioBookException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Maxime
 */
public class ExpPersoView extends JPanel implements ActionListener, ListSelectionListener {

    public EspacePersoView espacePersoView;
    private JTabbedPane tabbedExperiencesPersoDroite;
    private ExpPersoController expPersoController;
    private JPanel panelGauche;
    private JLabel expCree;
    private JLabel expParticipe;
    private Vector<String> listExpCree;
    private Vector<String> listExpParticipe;
    private JList jlistCree;
    private JList jlistParticipe;
    private JPanel panelDroite;
    private JLabel labelSansExp;
    private JLabel jLabelTitreLibelle;
    private JLabel jLabelLibelle;
    private JButtonValider validerLibelle;
    private JButtonAnnuler annulerLibelle;
    private JButtonModifier modifierLibelle;
    private JTextField jTextLibelle;
    private JLabel titreProblem;
    private JLabel problem;
    private JButtonModifier modifierProblem;
    private JTextField jTextProblem;
    private JButtonValider validerProblem;
    private JButtonAnnuler annulerProblem;
    private JLabel jLabelTitreMethode;
    private JLabel jLabelMethode;
    private JButtonModifier modifierMethode;
    private JTextField jTextMethode;
    private JButtonValider validerMethode;
    private JButtonAnnuler annulerMethode;
    private JLabel jLabelTitreStateOfTheArt;
    private JLabel jLabelStateOfTheArt;
    private JButtonModifier modifierStateOfTheArt;
    private JTextField jTextStateOfTheArt;
    private JButtonValider validerStateOfTheArt;
    private JButtonAnnuler annulerStateOfTheArt;
    private JLabel jLabelTitreAssumption;
    private JLabel jLabelAssumption;
    private JButtonModifier modifierAssumption;
    private JTextField jTextAssumption;
    private JButtonValider validerAssumption;
    private JButtonAnnuler annulerAssumption;
    private JLabel jLabelTitreCreateur;
    private JLabel jLabelCreateur;
    private DualListBox dualBoxParticipants;
    private DualListBox dualBoxMateriels;
    private JButtonModifier modifierParticipants;
    private JButtonValider validerParticipants;
    private JButtonAnnuler annulerParticipants;
    private JButtonModifier modifierMateriels;
    private JButtonValider validerMateriels;
    private JButtonAnnuler annulerMateriels;
    private JPanel panelNordSuivi;
    private JPanel panelChercheur;
    private JPanel panelParam;
    private JComboBox jComboBoxMateriels;
    private JButton addMateriels;
    private  JPanel panelSuivi;
    private JButton suppMateriels;
    private DualListBox dualBoxVariables;
    private JButtonModifier modifierVariables;
    private JButtonValider validerVariables;
    private JButtonAnnuler annulerVariables;
    private JButton addVariables;
    private JButton suppVariables;
    private DualListBox dualBoxDocuments;
    private JButtonModifier modifierDocuments;
    private JButtonValider validerDocuments;
    private JButtonAnnuler annulerDocuments;
    private JButton addDocuments;
    private JButton suppDocuments;
    private JTextField jTextLabelVariable;
    private JTextField jTextValeurVariable;
    private JPanel panelBouttonMateriel;
    private JPanel panelBouttonVariable;
    private JPanel panelJtextVar;
    private JPanel panelBouttonDocument;
    private JButton supprimerExperience;

    public ExpPersoView(EspacePersoView m) {
        espacePersoView = m;
        expPersoController = new ExpPersoController(this);
        setLayout(new BorderLayout());
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Panel gauche permet de choisir l'experience à visionner
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        panelGauche = new JPanel(new GridBagLayout());
        JScrollPane scroll = new JScrollPane(panelGauche,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Récupération du chercheur connecté
        Chercheur chercheurConnecte = espacePersoView.getMain().getChercheurConnecte();
        Chercheur c = new Chercheur(chercheurConnecte);
        c.setLogin("toto");
        HashSet list = new HashSet();
        listExpCree = new Vector<String>();
        listExpParticipe = new Vector<String>();
        for (int i = 0; i < 20; i++) {
            Experience e = new Experience("a", "a", "a", "a", "a", chercheurConnecte);
            list.add(e);
        }
        for (int i = 0; i < 20; i++) {
            Experience e = new Experience("z", "sdfgh", "a", "a", "a", c);
            list.add(e);
        }

        chercheurConnecte.setListExperiences(list);


        // Récupération des expériences du chercheur
        for (Experience expBuff : chercheurConnecte.getListExperiences()) {
            if (expBuff.getCreateur().equals(chercheurConnecte)) {
                // Expérience crée par ce chercheur
                listExpCree.add(expBuff.getLabel());
            } else {
                // Expérience auxquelles participe le chercheur sans l'avoir créé
                listExpParticipe.add(expBuff.getLabel());
            }
        }

        expCree = new JLabel("Les expériences que vous avez créé : ");
        jlistCree = new JList(listExpCree);
        jlistCree.addListSelectionListener(this);

        expParticipe = new JLabel("Les expériences aux quelles vous participé : ");
        jlistParticipe = new JList(listExpParticipe);
        jlistParticipe.addListSelectionListener(this);


        // Contraintes du titre de la liste des experiences créées par le chercheur
        GridBagConstraints gridBagConstraintsTitreListeCree = new GridBagConstraints();
        gridBagConstraintsTitreListeCree.gridx = 0;
        gridBagConstraintsTitreListeCree.gridy = 0;
        gridBagConstraintsTitreListeCree.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreListeCree.insets = new Insets(0, 10, 10, 30);

        // Contraintes de la liste des experiences créées
        GridBagConstraints gridBagConstraintsListCree = new GridBagConstraints();
        gridBagConstraintsListCree.gridx = 0;
        gridBagConstraintsListCree.gridy = 1;
        gridBagConstraintsListCree.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsListCree.insets = new Insets(0, 30, 10, 100);

        // Contrainte du titre de la liste des expériences ou participe le chercheur
        GridBagConstraints gridBagConstraintsTitreListeParticipe = new GridBagConstraints();
        gridBagConstraintsTitreListeParticipe.gridx = 0;
        gridBagConstraintsTitreListeParticipe.gridy = 2;
        gridBagConstraintsTitreListeParticipe.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreListeParticipe.insets = new Insets(0, 10, 10, 30);

        // Contraintes de la liste des experiences aux quelles le chercheur participe
        GridBagConstraints gridBagConstraintsListParticipe = new GridBagConstraints();
        gridBagConstraintsListParticipe.gridx = 0;
        gridBagConstraintsListParticipe.gridy = 3;
        gridBagConstraintsListParticipe.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsListParticipe.insets = new Insets(0, 30, 10, 100);

        // Ajout des Composants du panel gauche
        panelGauche.add(expCree, gridBagConstraintsTitreListeCree);
        panelGauche.add(jlistCree, gridBagConstraintsListCree);
        panelGauche.add(expParticipe, gridBagConstraintsTitreListeParticipe);
        panelGauche.add(jlistParticipe, gridBagConstraintsListParticipe);


        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Panel Droite permet de visionner l'experience choisie 
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        panelDroite = new JPanel(new BorderLayout());
        
        // Composants de départ
        JPanel sansExp = new JPanel(new GridBagLayout());
        labelSansExp = new JLabel("<= Veuillez choisir une expérience dans la liste de gauche.");
        
        GridBagConstraints gridBagConstraintsLabelSansExp = new GridBagConstraints();
        gridBagConstraintsLabelSansExp.gridx = 0;
        gridBagConstraintsLabelSansExp.gridy = 0;
        gridBagConstraintsLabelSansExp.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsLabelSansExp.insets = new Insets(0, 0, 10, 0);
        
        // Ajout des composants de départ
        sansExp.add(labelSansExp, gridBagConstraintsLabelSansExp);
        panelDroite.add(sansExp,BorderLayout.WEST);
        
        // Ajout de Composants après choix de l'expérience
        
        panelSuivi = new JPanel(new GridLayout(2, 1));
        
        panelNordSuivi = new JPanel(new GridBagLayout());
        
        jLabelTitreLibelle = new JLabel("Libellé  :");
        jLabelLibelle = new JLabel();
        modifierLibelle = new JButtonModifier();
        modifierLibelle.addActionListener(this);

        // Ajout de Composants après click modif
        jTextLibelle = new JTextField();
        validerLibelle = new JButtonValider();
        annulerLibelle = new JButtonAnnuler();
        validerLibelle.addActionListener(this);
        annulerLibelle.addActionListener(this);

        // Contraintes des composants après choix de l'expérience
        GridBagConstraints gridBagConstraintsTitreLibelle = new GridBagConstraints();
        gridBagConstraintsTitreLibelle.gridx = 0;
        gridBagConstraintsTitreLibelle.gridy = 0;
        gridBagConstraintsTitreLibelle.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreLibelle.insets = new Insets(30, 0, 10, 30);

        GridBagConstraints gridBagConstraintsLibelle = new GridBagConstraints();
        gridBagConstraintsLibelle.gridx = 1;
        gridBagConstraintsLibelle.gridy = 0;
        gridBagConstraintsLibelle.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsLibelle.insets = new Insets(30, 0, 10, 30);

        GridBagConstraints gridBagConstraintsModifLibelle = new GridBagConstraints();
        gridBagConstraintsModifLibelle.gridx = 2;
        gridBagConstraintsModifLibelle.gridy = 0;
        gridBagConstraintsModifLibelle.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifLibelle.insets = new Insets(30, 0, 10, 0);

        // Contraintes des composants apres click modif
        GridBagConstraints gridBagConstraintsJTextLibelle = new GridBagConstraints();
        gridBagConstraintsJTextLibelle.gridx = 1;
        gridBagConstraintsJTextLibelle.gridy = 0;
        gridBagConstraintsJTextLibelle.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextLibelle.insets = new Insets(30, 0, 10, 30);

        GridBagConstraints gridBagConstraintsJButonValiderLibelle = new GridBagConstraints();
        gridBagConstraintsJButonValiderLibelle.gridx = 2;
        gridBagConstraintsJButonValiderLibelle.gridy = 0;
        gridBagConstraintsJButonValiderLibelle.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonValiderLibelle.insets = new Insets(30, 0, 10, 0);

        GridBagConstraints gridBagConstraintsJButonAnnulerLibelle = new GridBagConstraints();
        gridBagConstraintsJButonAnnulerLibelle.gridx = 3;
        gridBagConstraintsJButonAnnulerLibelle.gridy = 0;
        gridBagConstraintsJButonAnnulerLibelle.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonAnnulerLibelle.insets = new Insets(30, 0, 10, 0);
        
        // Ajout de Composants après choix de l'expérience
        panelNordSuivi.add(jLabelTitreLibelle, gridBagConstraintsTitreLibelle);
        panelNordSuivi.add(jLabelLibelle, gridBagConstraintsLibelle);
        panelNordSuivi.add(modifierLibelle, gridBagConstraintsModifLibelle);
       
        // Ajout des composants apres click modif
        panelNordSuivi.add(jTextLibelle, gridBagConstraintsJTextLibelle);
        jTextLibelle.setPreferredSize(new Dimension(140, 20));
        jTextLibelle.setVisible(false);
        panelNordSuivi.add(validerLibelle, gridBagConstraintsJButonValiderLibelle);
        validerLibelle.setVisible(false);
        validerLibelle.addActionListener(this);
        panelNordSuivi.add(annulerLibelle, gridBagConstraintsJButonAnnulerLibelle);
        annulerLibelle.setVisible(false);
        annulerLibelle.addActionListener(this);

        // Creation du Problem
        // composants de départ
        titreProblem = new JLabel("Problème  :");
        problem = new JLabel();
        modifierProblem = new JButtonModifier();
        modifierProblem.addActionListener(this);

        // composants apres click modif
        jTextProblem = new JTextField();
        validerProblem = new JButtonValider();
        annulerProblem = new JButtonAnnuler();

        // Contraintes des composants de départ
        GridBagConstraints gridBagConstraintsTitreProblem = new GridBagConstraints();
        gridBagConstraintsTitreProblem.gridx = 0;
        gridBagConstraintsTitreProblem.gridy = 1;
        gridBagConstraintsTitreProblem.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreProblem.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsProblem = new GridBagConstraints();
        gridBagConstraintsProblem.gridx = 1;
        gridBagConstraintsProblem.gridy = 1;
        gridBagConstraintsProblem.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsProblem.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsModifProblem = new GridBagConstraints();
        gridBagConstraintsModifProblem.gridx = 2;
        gridBagConstraintsModifProblem.gridy = 1;
        gridBagConstraintsModifProblem.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifProblem.insets = new Insets(0, 0, 10, 0);

        // Contraintes des composants apres click modif
        GridBagConstraints gridBagConstraintsJTextProblem = new GridBagConstraints();
        gridBagConstraintsJTextProblem.gridx = 1;
        gridBagConstraintsJTextProblem.gridy = 1;
        gridBagConstraintsJTextProblem.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextProblem.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsJButonValiderProblem = new GridBagConstraints();
        gridBagConstraintsJButonValiderProblem.gridx = 2;
        gridBagConstraintsJButonValiderProblem.gridy = 1;
        gridBagConstraintsJButonValiderProblem.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonValiderProblem.insets = new Insets(0, 0, 10, 0);

        GridBagConstraints gridBagConstraintsJButonAnnulerProblem = new GridBagConstraints();
        gridBagConstraintsJButonAnnulerProblem.gridx = 3;
        gridBagConstraintsJButonAnnulerProblem.gridy = 1;
        gridBagConstraintsJButonAnnulerProblem.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonAnnulerProblem.insets = new Insets(0, 0, 10, 0);

        // Ajout du JTextField Problem
        // des composants de départ
        panelNordSuivi.add(titreProblem, gridBagConstraintsTitreProblem);
        panelNordSuivi.add(problem, gridBagConstraintsProblem);
        panelNordSuivi.add(modifierProblem, gridBagConstraintsModifProblem);
        
        // composants apres click modif
        panelNordSuivi.add(jTextProblem, gridBagConstraintsJTextProblem);
        jTextProblem.setPreferredSize(new Dimension(140, 20));
        jTextProblem.setVisible(false);
        panelNordSuivi.add(validerProblem, gridBagConstraintsJButonValiderProblem);
        validerProblem.setVisible(false);
        validerProblem.addActionListener(this);
        panelNordSuivi.add(annulerProblem, gridBagConstraintsJButonAnnulerProblem);
        annulerProblem.setVisible(false);
        annulerProblem.addActionListener(this);

        // Methode
        // Ajout de Composants après choix de l'expérience
        jLabelTitreMethode = new JLabel("Methode  :");
        jLabelMethode = new JLabel();
        modifierMethode = new JButtonModifier();
        modifierMethode.addActionListener(this);

        // Ajout de Composants après click modif
        jTextMethode = new JTextField();
        validerMethode = new JButtonValider();
        annulerMethode = new JButtonAnnuler();
        validerMethode.addActionListener(this);
        annulerMethode.addActionListener(this);

        // Contraintes des composants après choix de l'expérience
        GridBagConstraints gridBagConstraintsTitreMethode = new GridBagConstraints();
        gridBagConstraintsTitreMethode.gridx = 0;
        gridBagConstraintsTitreMethode.gridy = 2;
        gridBagConstraintsTitreMethode.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreMethode.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsMethode = new GridBagConstraints();
        gridBagConstraintsMethode.gridx = 1;
        gridBagConstraintsMethode.gridy = 2;
        gridBagConstraintsMethode.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsMethode.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsModifMethode = new GridBagConstraints();
        gridBagConstraintsModifMethode.gridx = 2;
        gridBagConstraintsModifMethode.gridy = 2;
        gridBagConstraintsModifMethode.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifMethode.insets = new Insets(0, 0, 10, 0);

        // Contraintes des composants apres click modif
        GridBagConstraints gridBagConstraintsJTextMethode = new GridBagConstraints();
        gridBagConstraintsJTextMethode.gridx = 1;
        gridBagConstraintsJTextMethode.gridy = 2;
        gridBagConstraintsJTextMethode.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextMethode.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsJButonValiderMethode = new GridBagConstraints();
        gridBagConstraintsJButonValiderMethode.gridx = 2;
        gridBagConstraintsJButonValiderMethode.gridy = 2;
        gridBagConstraintsJButonValiderMethode.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonValiderMethode.insets = new Insets(0, 0, 10, 0);

        GridBagConstraints gridBagConstraintsJButonAnnulerMethode = new GridBagConstraints();
        gridBagConstraintsJButonAnnulerMethode.gridx = 3;
        gridBagConstraintsJButonAnnulerMethode.gridy = 2;
        gridBagConstraintsJButonAnnulerMethode.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonAnnulerMethode.insets = new Insets(0, 0, 10, 0);


        // Ajout de Composants après choix de l'expérience
        panelNordSuivi.add(jLabelTitreMethode, gridBagConstraintsTitreMethode);
        panelNordSuivi.add(jLabelMethode, gridBagConstraintsMethode);
        panelNordSuivi.add(modifierMethode, gridBagConstraintsModifMethode);
        
        // Ajout des composants apres click modif
        panelNordSuivi.add(jTextMethode, gridBagConstraintsJTextMethode);
        jTextMethode.setPreferredSize(new Dimension(140, 20));
        jTextMethode.setVisible(false);
        panelNordSuivi.add(validerMethode, gridBagConstraintsJButonValiderMethode);
        validerMethode.setVisible(false);
        validerMethode.addActionListener(this);
        panelNordSuivi.add(annulerMethode, gridBagConstraintsJButonAnnulerMethode);
        annulerMethode.setVisible(false);
        annulerMethode.addActionListener(this);

        // Etat de l'art
        // Ajout de Composants après choix de l'expérience
        jLabelTitreStateOfTheArt = new JLabel("Etat de l'art  :");
        jLabelStateOfTheArt = new JLabel();
        modifierStateOfTheArt = new JButtonModifier();
        modifierStateOfTheArt.addActionListener(this);

        // Ajout de Composants après click modif
        jTextStateOfTheArt = new JTextField();
        validerStateOfTheArt = new JButtonValider();
        annulerStateOfTheArt = new JButtonAnnuler();
        validerStateOfTheArt.addActionListener(this);
        annulerStateOfTheArt.addActionListener(this);

        // Contraintes des composants après choix de l'expérience
        GridBagConstraints gridBagConstraintsTitreStateOfTheArt = new GridBagConstraints();
        gridBagConstraintsTitreStateOfTheArt.gridx = 0;
        gridBagConstraintsTitreStateOfTheArt.gridy = 3;
        gridBagConstraintsTitreStateOfTheArt.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreStateOfTheArt.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsStateOfTheArt = new GridBagConstraints();
        gridBagConstraintsStateOfTheArt.gridx = 1;
        gridBagConstraintsStateOfTheArt.gridy = 3;
        gridBagConstraintsStateOfTheArt.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsStateOfTheArt.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsModifStateOfTheArt = new GridBagConstraints();
        gridBagConstraintsModifStateOfTheArt.gridx = 2;
        gridBagConstraintsModifStateOfTheArt.gridy = 3;
        gridBagConstraintsModifStateOfTheArt.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifStateOfTheArt.insets = new Insets(0, 0, 10, 0);

        // Contraintes des composants apres click modif
        GridBagConstraints gridBagConstraintsJTextStateOfTheArt = new GridBagConstraints();
        gridBagConstraintsJTextStateOfTheArt.gridx = 1;
        gridBagConstraintsJTextStateOfTheArt.gridy = 3;
        gridBagConstraintsJTextStateOfTheArt.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextStateOfTheArt.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsJButonValiderStateOfTheArt = new GridBagConstraints();
        gridBagConstraintsJButonValiderStateOfTheArt.gridx = 2;
        gridBagConstraintsJButonValiderStateOfTheArt.gridy = 3;
        gridBagConstraintsJButonValiderStateOfTheArt.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonValiderStateOfTheArt.insets = new Insets(0, 0, 10, 0);

        GridBagConstraints gridBagConstraintsJButonAnnulerStateOfTheArt = new GridBagConstraints();
        gridBagConstraintsJButonAnnulerStateOfTheArt.gridx = 3;
        gridBagConstraintsJButonAnnulerStateOfTheArt.gridy = 3;
        gridBagConstraintsJButonAnnulerStateOfTheArt.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonAnnulerStateOfTheArt.insets = new Insets(0, 0, 10, 0);


        // Ajout de Composants après choix de l'expérience
        panelNordSuivi.add(jLabelTitreStateOfTheArt, gridBagConstraintsTitreStateOfTheArt);
        panelNordSuivi.add(jLabelStateOfTheArt, gridBagConstraintsStateOfTheArt);
        panelNordSuivi.add(modifierStateOfTheArt, gridBagConstraintsModifStateOfTheArt);
        
        // Ajout des composants apres click modif
        panelNordSuivi.add(jTextStateOfTheArt, gridBagConstraintsJTextStateOfTheArt);
        jTextStateOfTheArt.setPreferredSize(new Dimension(140, 20));
        jTextStateOfTheArt.setVisible(false);
        panelNordSuivi.add(validerStateOfTheArt, gridBagConstraintsJButonValiderStateOfTheArt);
        validerStateOfTheArt.setVisible(false);
        validerStateOfTheArt.addActionListener(this);
        panelNordSuivi.add(annulerStateOfTheArt, gridBagConstraintsJButonAnnulerStateOfTheArt);
        annulerStateOfTheArt.setVisible(false);
        annulerStateOfTheArt.addActionListener(this);


        // Hypothèse
        // Ajout de Composants après choix de l'expérience
        jLabelTitreAssumption = new JLabel("Hypothèse  :");
        jLabelAssumption = new JLabel();
        modifierAssumption = new JButtonModifier();
        modifierAssumption.addActionListener(this);

        // Ajout de Composants après click modif
        jTextAssumption = new JTextField();
        validerAssumption = new JButtonValider();
        annulerAssumption = new JButtonAnnuler();
        validerAssumption.addActionListener(this);
        annulerAssumption.addActionListener(this);

        // Contraintes des composants après choix de l'expérience
        GridBagConstraints gridBagConstraintsTitreAssumption = new GridBagConstraints();
        gridBagConstraintsTitreAssumption.gridx = 0;
        gridBagConstraintsTitreAssumption.gridy = 4;
        gridBagConstraintsTitreAssumption.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreAssumption.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsAssumption = new GridBagConstraints();
        gridBagConstraintsAssumption.gridx = 1;
        gridBagConstraintsAssumption.gridy = 4;
        gridBagConstraintsAssumption.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsAssumption.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsModifAssumption = new GridBagConstraints();
        gridBagConstraintsModifAssumption.gridx = 2;
        gridBagConstraintsModifAssumption.gridy = 4;
        gridBagConstraintsModifAssumption.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifAssumption.insets = new Insets(0, 0, 10, 0);

        // Contraintes des composants apres click modif
        GridBagConstraints gridBagConstraintsJTextAssumption = new GridBagConstraints();
        gridBagConstraintsJTextAssumption.gridx = 1;
        gridBagConstraintsJTextAssumption.gridy = 4;
        gridBagConstraintsJTextAssumption.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJTextAssumption.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsJButonValiderAssumption = new GridBagConstraints();
        gridBagConstraintsJButonValiderAssumption.gridx = 2;
        gridBagConstraintsJButonValiderAssumption.gridy = 4;
        gridBagConstraintsJButonValiderAssumption.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonValiderAssumption.insets = new Insets(0, 0, 10, 0);

        GridBagConstraints gridBagConstraintsJButonAnnulerAssumption = new GridBagConstraints();
        gridBagConstraintsJButonAnnulerAssumption.gridx = 3;
        gridBagConstraintsJButonAnnulerAssumption.gridy = 4;
        gridBagConstraintsJButonAnnulerAssumption.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonAnnulerAssumption.insets = new Insets(0, 0, 10, 0);


        // Ajout de Composants après choix de l'expérience
        panelNordSuivi.add(jLabelTitreAssumption, gridBagConstraintsTitreAssumption);
        panelNordSuivi.add(jLabelAssumption, gridBagConstraintsAssumption);
        panelNordSuivi.add(modifierAssumption, gridBagConstraintsModifAssumption);
        
        // Ajout des composants apres click modif
        panelNordSuivi.add(jTextAssumption, gridBagConstraintsJTextAssumption);
        jTextAssumption.setPreferredSize(new Dimension(140, 20));
        jTextAssumption.setVisible(false);
        panelNordSuivi.add(validerAssumption, gridBagConstraintsJButonValiderAssumption);
        validerAssumption.setVisible(false);
        validerAssumption.addActionListener(this);
        panelNordSuivi.add(annulerAssumption, gridBagConstraintsJButonAnnulerAssumption);
        annulerAssumption.setVisible(false);
        annulerAssumption.addActionListener(this);

        // Créateur
        // Ajout de Composants après choix de l'expérience
        jLabelTitreCreateur = new JLabel("Créateur  :");
        jLabelCreateur = new JLabel();

        // Contraintes des composants après choix de l'expérience
        GridBagConstraints gridBagConstraintsTitreCreateur = new GridBagConstraints();
        gridBagConstraintsTitreCreateur.gridx = 0;
        gridBagConstraintsTitreCreateur.gridy = 5;
        gridBagConstraintsTitreCreateur.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsTitreCreateur.insets = new Insets(10, 0, 30, 30);

        GridBagConstraints gridBagConstraintsCreateur = new GridBagConstraints();
        gridBagConstraintsCreateur.gridx = 1;
        gridBagConstraintsCreateur.gridy = 5;
        gridBagConstraintsCreateur.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsCreateur.insets = new Insets(10, 0, 30, 30);

        // Ajout de Composants après choix de l'expérience
        panelNordSuivi.add(jLabelTitreCreateur, gridBagConstraintsTitreCreateur);
        panelNordSuivi.add(jLabelCreateur, gridBagConstraintsCreateur);
        
        panelSuivi.add(panelNordSuivi);
        
        // Materiels
        // Ajout de Composants après choix de l'expérience
        JPanel panelMateriel = new JPanel(new FlowLayout());
        dualBoxMateriels = new DualListBox("Materiels utilisés", "Matériels existants");
        modifierMateriels = new JButtonModifier();
        modifierMateriels.addActionListener(this);

        // Ajout de Composants après click modif
        validerMateriels = new JButtonValider();
        annulerMateriels = new JButtonAnnuler();
        validerMateriels.addActionListener(this);
        annulerMateriels.addActionListener(this);
        
        addMateriels = new JButton("Ajouter");
        addMateriels.addActionListener(this);
        suppMateriels = new JButton("Supprimer");
        suppMateriels.addActionListener(this);
        
        jComboBoxMateriels = new JComboBox();
        jComboBoxMateriels.addActionListener(this);
        
        GridBagConstraints gridBagConstraintsJPanelMateriels = new GridBagConstraints();
        gridBagConstraintsJPanelMateriels.gridx = 0;
        gridBagConstraintsJPanelMateriels.gridy = 6;
        gridBagConstraintsJPanelMateriels.gridwidth = 4;
        gridBagConstraintsJPanelMateriels.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJPanelMateriels.insets = new Insets(0, 0, 10, 0);


        // Ajout de Composants après choix de l'expérience
        panelMateriel.add(dualBoxMateriels);
        panelMateriel.add(modifierMateriels);
        modifierMateriels.addActionListener(this);
        panelBouttonMateriel = new JPanel(new GridLayout(2,1));
        
        panelBouttonMateriel.add(addMateriels);
        panelBouttonMateriel.add(suppMateriels);
        panelBouttonMateriel.setVisible(false);
        panelMateriel.add(panelBouttonMateriel);
        
        // Ajout des composants apres click modif
        panelMateriel.add(jComboBoxMateriels);
        jComboBoxMateriels.setVisible(false);
        
        panelMateriel.add(validerMateriels);
        validerMateriels.setVisible(false);
        panelMateriel.add(annulerMateriels);
        annulerMateriels.setVisible(false);
        panelSuivi.add(panelMateriel);
        JScrollPane scrollPaneSuivi = new JScrollPane(panelSuivi);
        
        // Participant
        panelChercheur = new JPanel(new GridBagLayout());
        
        // Ajout de Composants après choix de l'expérience
        dualBoxParticipants = new DualListBox("Chercheurs participants", "Chercheurs existants");
        modifierParticipants = new JButtonModifier();
        modifierParticipants.addActionListener(this);

        // Ajout de Composants après click modif
        validerParticipants = new JButtonValider();
        annulerParticipants = new JButtonAnnuler();
        validerParticipants.addActionListener(this);
        annulerParticipants.addActionListener(this);
        
        // Contraintes des composants après choix de l'expérience
        GridBagConstraints gridBagConstraintsDualListBox = new GridBagConstraints();
        gridBagConstraintsDualListBox.gridx = 0;
        gridBagConstraintsDualListBox.gridy = 0;
        gridBagConstraintsDualListBox.gridwidth = 2;
//        gridBagConstraintsDualListBox.gridheight = ;
        gridBagConstraintsDualListBox.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsDualListBox.insets = new Insets(0, 0, 10, 30);

        GridBagConstraints gridBagConstraintsModifParticipants = new GridBagConstraints();
        gridBagConstraintsModifParticipants.gridx = 2;
        gridBagConstraintsModifParticipants.gridy = 0;
        gridBagConstraintsModifParticipants.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsModifParticipants.insets = new Insets(0, 0, 10, 0);

        // Contraintes des composants apres click modif
        GridBagConstraints gridBagConstraintsJButonValiderParticipants = new GridBagConstraints();
        gridBagConstraintsJButonValiderParticipants.gridx = 2;
        gridBagConstraintsJButonValiderParticipants.gridy = 0;
        gridBagConstraintsJButonValiderParticipants.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonValiderParticipants.insets = new Insets(0, 0, 10, 0);

        GridBagConstraints gridBagConstraintsJButonAnnulerParticipants = new GridBagConstraints();
        gridBagConstraintsJButonAnnulerParticipants.gridx = 3;
        gridBagConstraintsJButonAnnulerParticipants.gridy = 0;
        gridBagConstraintsJButonAnnulerParticipants.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraintsJButonAnnulerParticipants.insets = new Insets(0, 0, 10, 0);


        // Ajout de Composants après choix de l'expérience
        panelChercheur.add(dualBoxParticipants, gridBagConstraintsDualListBox);
        panelChercheur.add(modifierParticipants,gridBagConstraintsModifParticipants);
        
          
        modifierParticipants.addActionListener(this);
        
        // Ajout des composants apres click modif
        panelChercheur.add(validerParticipants, gridBagConstraintsJButonValiderParticipants);
        validerParticipants.setVisible(false);
        validerParticipants.addActionListener(this);
        panelChercheur.add(annulerParticipants, gridBagConstraintsJButonAnnulerParticipants);
        annulerParticipants.setVisible(false);
        annulerParticipants.addActionListener(this);

        JScrollPane scrollPaneChercheur = new JScrollPane(panelChercheur);
        
        // Onglet avec les variables à retenir et des documents
        panelParam = new JPanel(new GridLayout(2,1));
        
        // Variables
        // Ajout de Composants après choix de l'expérience
        JPanel panelVariable = new JPanel(new FlowLayout());
        dualBoxVariables = new DualListBox("Variables utilisés", "");
        modifierVariables = new JButtonModifier();
        modifierVariables.addActionListener(this);

        // Ajout de Composants après click modif
        validerVariables = new JButtonValider();
        annulerVariables = new JButtonAnnuler();
        validerVariables.addActionListener(this);
        annulerVariables.addActionListener(this);
        
        addVariables = new JButton("Ajouter");
        addVariables.addActionListener(this);
        suppVariables = new JButton("Supprimer");
        suppVariables.addActionListener(this);
        
        panelJtextVar = new JPanel(new GridLayout(2, 1));
        jTextLabelVariable = new JTextField("Nom de la variable",20);
        jTextValeurVariable = new JTextField("Valeur de la variable",20);
        
        // Ajout de Composants après choix de l'expérience
        panelVariable.add(dualBoxVariables);
        panelVariable.add(modifierVariables);
        modifierVariables.addActionListener(this);
        panelBouttonVariable = new JPanel(new GridLayout(2,1));
        
        panelBouttonVariable.add(addVariables);
        panelBouttonVariable.add(suppVariables);
        panelBouttonVariable.setVisible(false);
        panelVariable.add(panelBouttonVariable);
        
        // Ajout des composants apres click modif
        panelJtextVar.add(jTextLabelVariable);
        panelJtextVar.add(jTextValeurVariable);
        panelJtextVar.setVisible(false);
        
        panelVariable.add(panelJtextVar);
        
        panelVariable.add(validerVariables);
        validerVariables.setVisible(false);
        panelVariable.add(annulerVariables);
        annulerVariables.setVisible(false);
        panelParam.add(panelVariable);
        
        // Documents
        // Ajout de Composants après choix de l'expérience
        JPanel panelDocument = new JPanel(new FlowLayout());
        dualBoxDocuments = new DualListBox("Documents utilisés", "");
        modifierDocuments = new JButtonModifier();
        modifierDocuments.addActionListener(this);

        // Ajout de Composants après click modif
        validerDocuments = new JButtonValider();
        annulerDocuments = new JButtonAnnuler();
        validerDocuments.addActionListener(this);
        annulerDocuments.addActionListener(this);
        
        addDocuments = new JButton("Importer un fichier");
        addDocuments.addActionListener(this);
        suppDocuments = new JButton("Supprimer");
        suppDocuments.addActionListener(this);
        
        // Ajout de Composants après choix de l'expérience
        panelDocument.add(dualBoxDocuments);
        panelDocument.add(modifierDocuments);
        modifierDocuments.addActionListener(this);
        panelBouttonDocument = new JPanel(new GridLayout(2,1));
        
        panelBouttonDocument.add(addDocuments);
        panelBouttonDocument.add(suppDocuments);
        panelBouttonDocument.setVisible(false);
        panelDocument.add(panelBouttonDocument);
        
        // Ajout des composants apres click modif
        panelDocument.add(validerDocuments);
        validerDocuments.setVisible(false);
        panelDocument.add(annulerDocuments);
        annulerDocuments.setVisible(false);
        panelParam.add(panelDocument);
        
        
        JScrollPane scrollPaneParam = new JScrollPane(panelParam);
        
        // Onglet
        tabbedExperiencesPersoDroite = new JTabbedPane();
        
        // Mise enplace des onglets
        tabbedExperiencesPersoDroite.addTab("Suivi", scrollPaneSuivi);
        tabbedExperiencesPersoDroite.addTab("Participants", panelChercheur);
        tabbedExperiencesPersoDroite.addTab("Paramètres", scrollPaneParam);
        
        panelDroite.add(tabbedExperiencesPersoDroite,BorderLayout.CENTER);
        
        // Boutton pour supprimer l'exp
        supprimerExperience = new JButton("Supprimer l'experience");
        supprimerExperience.addActionListener(this);
        supprimerExperience.setVisible(false);
        panelDroite.add(supprimerExperience, BorderLayout.SOUTH);
        
        tabbedExperiencesPersoDroite.setVisible(false);
        add(scroll, BorderLayout.WEST);
        add(panelDroite, BorderLayout.CENTER);
//        Dimension dimPanel = new Dimension((int)((espacePersoView.getMain().getTailleFenetre().getWidth()-17)/2-10),(int)((espacePersoView.getMain().getTailleFenetre().getHeight()-75)));
//        panelGauche.setPreferredSize(dimPanel);
//        panelDroite.setPreferredSize(dimPanel);
        setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == jlistCree) {
            try {
                try {
                    expPersoController.chooseExp(true);
                } catch (IOException ex) {
                    Logger.getLogger(ExpPersoView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ExpPersoView.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (BioBookException ex) {
                Logger.getLogger(ExpPersoView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ExpPersoView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == jlistParticipe) {
            try {
                try {
                    expPersoController.chooseExp(false);
                } catch (IOException ex) {
                    Logger.getLogger(ExpPersoView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ExpPersoView.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (BioBookException ex) {
                Logger.getLogger(ExpPersoView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ExpPersoView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Click sur un des boutton de modification
        if (e.getSource() == modifierLibelle) {
            expPersoController.clickModifierLibelle();
        }
        if (e.getSource() == modifierProblem) {
            expPersoController.clickModifierProblem();
        }
        if (e.getSource() == modifierAssumption) {
            expPersoController.clickModifierAssumption();
        }
        if (e.getSource() == modifierStateOfTheArt) {
            expPersoController.clickModifierStateOfTheArt();
        }
        if (e.getSource() == modifierMethode) {
            expPersoController.clickModifierMethode();
        }
        if (e.getSource() == modifierParticipants) {
            expPersoController.clickModifierParticipants();
        }
        if (e.getSource() == modifierMateriels) {
            expPersoController.clickModifierMateriels();
        }
        
        if (e.getSource() == modifierVariables) {
            expPersoController.clickModifierVariables();
        }
        if (e.getSource() == modifierDocuments) {
            expPersoController.clickModifierDocuments();
        }
//        
//        // Click sur un des boutton de validation pour modifier un parametre
//        if(e.getSource()== validerNom || e.getSource()==validerfirstName || e.getSource()==validerMail || e.getSource()==validerLog){
//            try {
//                ctrl.clickValiderParam();
//            } catch (SQLException ex) {
//                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (BioBookException ex) {
//                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        if(e.getSource()== validerPass){
//            try {
//                ctrl.clickValiderPass();
//            } catch (BioBookException ex) {
//                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (NoSuchAlgorithmException ex) {
//                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SQLException ex) {
//                Logger.getLogger(ParamPersoView.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//         Click sur un boutton d'annulation pour ne plus modifier la valeur
        if (e.getSource() == annulerLibelle) {
            expPersoController.clickAnnulerLibelle();
        }
        if (e.getSource() == annulerProblem) {
            expPersoController.clickAnnulerProblem();
        }
        if (e.getSource() == annulerMethode) {
            expPersoController.clickAnnulerMethode();
        }
        if (e.getSource() == annulerAssumption) {
            expPersoController.clickAnnulerAssumption();
        }
        if (e.getSource() == annulerStateOfTheArt) {
            expPersoController.clickAnnulerStateOfTheArt();
        }
        if (e.getSource() == annulerParticipants) {
            expPersoController.clickAnnulerParticipants();
        }
        if (e.getSource() == annulerMateriels) {
            expPersoController.clickAnnulerMateriels();
        }
        if (e.getSource() == annulerDocuments) {
            expPersoController.clickAnnulerDocument();
        }
        if (e.getSource() == annulerVariables) {
            expPersoController.clickAnnulerVariables();
        }
        
        if (e.getSource() == addMateriels) {
            expPersoController.clickAddMateriel();
        }
        if (e.getSource() == suppMateriels) {
            expPersoController.clickSuppMateriel();
        }
        if (e.getSource() == supprimerExperience) {
            expPersoController.clickSuppExperience();
        }
        
        if (e.getSource() == addDocuments) {
            try {
                expPersoController.clickAddDocument();
            } catch (IOException ex) {
                Logger.getLogger(ExpPersoView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == suppDocuments) {
            expPersoController.clickSuppDocument();
        }
    }

    public JButton getSupprimerExperience() {
        return supprimerExperience;
    }

    public void setSupprimerExperience(JButton supprimerExperience) {
        this.supprimerExperience = supprimerExperience;
    }
    
    public JPanel getPanelBouttonDocument() {
        return panelBouttonDocument;
    }

    public void setPanelBouttonDocument(JPanel panelBouttonDocument) {
        this.panelBouttonDocument = panelBouttonDocument;
    }

    public JPanel getPanelJtextVar() {
        return panelJtextVar;
    }

    public void setPanelJtextVar(JPanel panelJtextVar) {
        this.panelJtextVar = panelJtextVar;
    }

    public JPanel getPanelBouttonMateriel() {
        return panelBouttonMateriel;
    }

    public void setPanelBouttonMateriel(JPanel panelBouttonMateriel) {
        this.panelBouttonMateriel = panelBouttonMateriel;
    }

    public JPanel getPanelBouttonVariable() {
        return panelBouttonVariable;
    }

    public void setPanelBouttonVariable(JPanel panelBouttonVariable) {
        this.panelBouttonVariable = panelBouttonVariable;
    }

    
    
    public JButton getSuppMateriels() {
        return suppMateriels;
    }

    public void setSuppMateriels(JButton suppMateriels) {
        this.suppMateriels = suppMateriels;
    }

    public JComboBox getjComboBoxMateriels() {
        return jComboBoxMateriels;
    }

    public void setjComboBoxMateriels(JComboBox jComboBoxMateriels) {
        this.jComboBoxMateriels = jComboBoxMateriels;
    }

    public JButton getAddMateriels() {
        return addMateriels;
    }

    public void setAddMateriels(JButton addMateriels) {
        this.addMateriels = addMateriels;
    }

    
    public JTabbedPane getTabbedExperiencesPersoDroite() {
        return tabbedExperiencesPersoDroite;
    }

    public void setTabbedExperiencesPersoDroite(JTabbedPane tabbedExperiencesPersoDroite) {
        this.tabbedExperiencesPersoDroite = tabbedExperiencesPersoDroite;
    }

    
    public JButtonValider getValiderParticipants() {
        return validerParticipants;
    }

    public void setValiderParticipants(JButtonValider validerParticipants) {
        this.validerParticipants = validerParticipants;
    }

    public JButtonAnnuler getAnnulerParticipants() {
        return annulerParticipants;
    }

    public void setAnnulerParticipants(JButtonAnnuler annulerParticipants) {
        this.annulerParticipants = annulerParticipants;
    }

    
    public DualListBox getDualBoxParticipants() {
        return dualBoxParticipants;
    }

    public void setDualBoxParticipants(DualListBox dualBoxParticipants) {
        this.dualBoxParticipants = dualBoxParticipants;
    }

    public DualListBox getDualBoxMateriels() {
        return dualBoxMateriels;
    }

    public void setDualBoxMateriels(DualListBox dualBoxMateriels) {
        this.dualBoxMateriels = dualBoxMateriels;
    }

    public EspacePersoView getEspacePersoView() {
        return espacePersoView;
    }

    public void setEspacePersoView(EspacePersoView espacePersoView) {
        this.espacePersoView = espacePersoView;
    }

    public ExpPersoController getCtrl() {
        return expPersoController;
    }

    public void setCtrl(ExpPersoController ctrl) {
        this.expPersoController = expPersoController;
    }

    public JPanel getPanelGauche() {
        return panelGauche;
    }

    public void setPanelGauche(JPanel panelGauche) {
        this.panelGauche = panelGauche;
    }

    public JLabel getExpCree() {
        return expCree;
    }

    public void setExpCree(JLabel expCree) {
        this.expCree = expCree;
    }

    public JLabel getExpParticipe() {
        return expParticipe;
    }

    public void setExpParticipe(JLabel expParticipe) {
        this.expParticipe = expParticipe;
    }

    public Vector<String> getListExpCree() {
        return listExpCree;
    }

    public void setListExpCree(Vector<String> listExpCree) {
        this.listExpCree = listExpCree;
    }

    public Vector<String> getListExpParticipe() {
        return listExpParticipe;
    }

    public void setListExpParticipe(Vector<String> listExpParticipe) {
        this.listExpParticipe = listExpParticipe;
    }

    public JList getJlistCree() {
        return jlistCree;
    }

    public void setJlistCree(JList jlistCree) {
        this.jlistCree = jlistCree;
    }

    public JList getJlistParticipe() {
        return jlistParticipe;
    }

    public void setJlistParticipe(JList jlistParticipe) {
        this.jlistParticipe = jlistParticipe;
    }

    public JPanel getPanelDroite() {
        return panelDroite;
    }

    public void setPanelDroite(JPanel panelDroite) {
        this.panelDroite = panelDroite;
    }

    public JLabel getLabelSansExp() {
        return labelSansExp;
    }

    public void setLabelSansExp(JLabel labelSansExp) {
        this.labelSansExp = labelSansExp;
    }

    public JLabel getjLabelTitreLibelle() {
        return jLabelTitreLibelle;
    }

    public void setjLabelTitreLibelle(JLabel jLabelTitreLibelle) {
        this.jLabelTitreLibelle = jLabelTitreLibelle;
    }

    public JLabel getjLabelLibelle() {
        return jLabelLibelle;
    }

    public void setjLabelLibelle(JLabel jLabelLibelle) {
        this.jLabelLibelle = jLabelLibelle;
    }

    public JButtonValider getValiderLibelle() {
        return validerLibelle;
    }

    public void setValiderLibelle(JButtonValider validerLibelle) {
        this.validerLibelle = validerLibelle;
    }

    public JButtonAnnuler getAnnulerLibelle() {
        return annulerLibelle;
    }

    public void setAnnulerLibelle(JButtonAnnuler annulerLibelle) {
        this.annulerLibelle = annulerLibelle;
    }

    public JButtonModifier getModifierLibelle() {
        return modifierLibelle;
    }

    public void setModifierLibelle(JButtonModifier modifierLibelle) {
        this.modifierLibelle = modifierLibelle;
    }

    public JTextField getjTextLibelle() {
        return jTextLibelle;
    }

    public void setjTextLibelle(JTextField jTextLibelle) {
        this.jTextLibelle = jTextLibelle;
    }

    public JLabel getProblem() {
        return problem;
    }

    public void setProblem(JLabel problem) {
        this.problem = problem;
    }

    public JButtonModifier getModifierProblem() {
        return modifierProblem;
    }

    public void setModifierProblem(JButtonModifier modifierProblem) {
        this.modifierProblem = modifierProblem;
    }

    public JTextField getjTextProblem() {
        return jTextProblem;
    }

    public void setjTextProblem(JTextField jTextProblem) {
        this.jTextProblem = jTextProblem;
    }

    public JButtonValider getValiderProblem() {
        return validerProblem;
    }

    public void setValiderProblem(JButtonValider validerProblem) {
        this.validerProblem = validerProblem;
    }

    public JButtonAnnuler getAnnulerProblem() {
        return annulerProblem;
    }

    public void setAnnulerProblem(JButtonAnnuler annulerProblem) {
        this.annulerProblem = annulerProblem;
    }

    public JLabel getjLabelTitreMethode() {
        return jLabelTitreMethode;
    }

    public void setjLabelTitreMethode(JLabel jLabelTitreMethode) {
        this.jLabelTitreMethode = jLabelTitreMethode;
    }

    public JLabel getjLabelMethode() {
        return jLabelMethode;
    }

    public void setjLabelMethode(JLabel jLabelMethode) {
        this.jLabelMethode = jLabelMethode;
    }

    public JButtonModifier getModifierMethode() {
        return modifierMethode;
    }

    public void setModifierMethode(JButtonModifier modifierMethode) {
        this.modifierMethode = modifierMethode;
    }

    public JTextField getjTextMethode() {
        return jTextMethode;
    }

    public void setjTextMethode(JTextField jTextMethode) {
        this.jTextMethode = jTextMethode;
    }

    public JButtonValider getValiderMethode() {
        return validerMethode;
    }

    public void setValiderMethode(JButtonValider validerMethode) {
        this.validerMethode = validerMethode;
    }

    public JButtonAnnuler getAnnulerMethode() {
        return annulerMethode;
    }

    public void setAnnulerMethode(JButtonAnnuler annulerMethode) {
        this.annulerMethode = annulerMethode;
    }

    public JLabel getjLabelTitreStateOfTheArt() {
        return jLabelTitreStateOfTheArt;
    }

    public void setjLabelTitreStateOfTheArt(JLabel jLabelTitreStateOfTheArt) {
        this.jLabelTitreStateOfTheArt = jLabelTitreStateOfTheArt;
    }

    public JLabel getjLabelStateOfTheArt() {
        return jLabelStateOfTheArt;
    }

    public void setjLabelStateOfTheArt(JLabel jLabelStateOfTheArt) {
        this.jLabelStateOfTheArt = jLabelStateOfTheArt;
    }

    public JButtonModifier getModifierStateOfTheArt() {
        return modifierStateOfTheArt;
    }

    public void setModifierStateOfTheArt(JButtonModifier modifierStateOfTheArt) {
        this.modifierStateOfTheArt = modifierStateOfTheArt;
    }

    public JTextField getjTextStateOfTheArt() {
        return jTextStateOfTheArt;
    }

    public void setjTextStateOfTheArt(JTextField jTextStateOfTheArt) {
        this.jTextStateOfTheArt = jTextStateOfTheArt;
    }

    public JButtonValider getValiderStateOfTheArt() {
        return validerStateOfTheArt;
    }

    public void setValiderStateOfTheArt(JButtonValider validerStateOfTheArt) {
        this.validerStateOfTheArt = validerStateOfTheArt;
    }

    public JButtonAnnuler getAnnulerStateOfTheArt() {
        return annulerStateOfTheArt;
    }

    public void setAnnulerStateOfTheArt(JButtonAnnuler annulerStateOfTheArt) {
        this.annulerStateOfTheArt = annulerStateOfTheArt;
    }

    public JLabel getjLabelTitreAssumption() {
        return jLabelTitreAssumption;
    }

    public void setjLabelTitreAssumption(JLabel jLabelTitreAssumption) {
        this.jLabelTitreAssumption = jLabelTitreAssumption;
    }

    public JLabel getjLabelAssumption() {
        return jLabelAssumption;
    }

    public void setjLabelAssumption(JLabel jLabelAssumption) {
        this.jLabelAssumption = jLabelAssumption;
    }

    public JButtonModifier getModifierAssumption() {
        return modifierAssumption;
    }

    public void setModifierAssumption(JButtonModifier modifierAssumption) {
        this.modifierAssumption = modifierAssumption;
    }

    public JTextField getjTextAssumption() {
        return jTextAssumption;
    }

    public void setjTextAssumption(JTextField jTextAssumption) {
        this.jTextAssumption = jTextAssumption;
    }

    public JButtonValider getValiderAssumption() {
        return validerAssumption;
    }

    public void setValiderAssumption(JButtonValider validerAssumption) {
        this.validerAssumption = validerAssumption;
    }

    public JButtonAnnuler getAnnulerAssumption() {
        return annulerAssumption;
    }

    public void setAnnulerAssumption(JButtonAnnuler annulerAssumption) {
        this.annulerAssumption = annulerAssumption;
    }

    public JLabel getTitreProblem() {
        return titreProblem;
    }

    public void setTitreProblem(JLabel titreProblem) {
        this.titreProblem = titreProblem;
    }

    public JLabel getjLabelCreateur() {
        return jLabelCreateur;
    }

    public void setjLabelCreateur(JLabel jLabelCreateur) {
        this.jLabelCreateur = jLabelCreateur;
    }

    public JLabel getjLabelTitreCreateur() {
        return jLabelTitreCreateur;
    }

    public void setjLabelTitreCreateur(JLabel jLabelTitreCreateur) {
        this.jLabelTitreCreateur = jLabelTitreCreateur;
    }

    public JButtonModifier getModifierParticipants() {
        return modifierParticipants;
    }

    public void setModifierParticipants(JButtonModifier modifierParticipants) {
        this.modifierParticipants = modifierParticipants;
    }
    
    
    public JButtonModifier getModifierMateriels() {
        return modifierMateriels;
    }

    public void setModifierMateriels(JButtonModifier modifierMateriels) {
        this.modifierMateriels = modifierMateriels;
    }

    public JButtonValider getValiderMateriels() {
        return validerMateriels;
    }

    public void setValiderMateriels(JButtonValider validerMateriels) {
        this.validerMateriels = validerMateriels;
    }

    public JButtonAnnuler getAnnulerMateriels() {
        return annulerMateriels;
    }

    public void setAnnulerMateriels(JButtonAnnuler annulerMateriels) {
        this.annulerMateriels = annulerMateriels;
    }

    
    public ExpPersoController getExpPersoController() {
        return expPersoController;
    }

    public void setExpPersoController(ExpPersoController expPersoController) {
        this.expPersoController = expPersoController;
    }

    public JPanel getPanelNordSuivi() {
        return panelNordSuivi;
    }

    public void setPanelNordSuivi(JPanel panelNordSuivi) {
        this.panelNordSuivi = panelNordSuivi;
    }

    public JPanel getPanelChercheur() {
        return panelChercheur;
    }

    public void setPanelChercheur(JPanel panelChercheur) {
        this.panelChercheur = panelChercheur;
    }

    public JPanel getPanelParam() {
        return panelParam;
    }

    public void setPanelParam(JPanel panelParam) {
        this.panelParam = panelParam;
    }

    public JPanel getPanelSuivi() {
        return panelSuivi;
    }

    public void setPanelSuivi(JPanel panelSuivi) {
        this.panelSuivi = panelSuivi;
    }

    public DualListBox getDualBoxVariables() {
        return dualBoxVariables;
    }

    public void setDualBoxVariables(DualListBox dualBoxVariables) {
        this.dualBoxVariables = dualBoxVariables;
    }

    public JButtonModifier getModifierVariables() {
        return modifierVariables;
    }

    public void setModifierVariables(JButtonModifier modifierVariables) {
        this.modifierVariables = modifierVariables;
    }

    public JButtonValider getValiderVariables() {
        return validerVariables;
    }

    public void setValiderVariables(JButtonValider validerVariables) {
        this.validerVariables = validerVariables;
    }

    public JButtonAnnuler getAnnulerVariables() {
        return annulerVariables;
    }

    public void setAnnulerVariables(JButtonAnnuler annulerVariables) {
        this.annulerVariables = annulerVariables;
    }

    public JButton getAddVariables() {
        return addVariables;
    }

    public void setAddVariables(JButton addVariables) {
        this.addVariables = addVariables;
    }

    public JButton getSuppVariables() {
        return suppVariables;
    }

    public void setSuppVariables(JButton suppVariables) {
        this.suppVariables = suppVariables;
    }

    public DualListBox getDualBoxDocuments() {
        return dualBoxDocuments;
    }

    public void setDualBoxDocuments(DualListBox dualBoxDocuments) {
        this.dualBoxDocuments = dualBoxDocuments;
    }

    public JButtonModifier getModifierDocuments() {
        return modifierDocuments;
    }

    public void setModifierDocuments(JButtonModifier modifierDocuments) {
        this.modifierDocuments = modifierDocuments;
    }

    public JButtonValider getValiderDocuments() {
        return validerDocuments;
    }

    public void setValiderDocuments(JButtonValider validerDocuments) {
        this.validerDocuments = validerDocuments;
    }

    public JButtonAnnuler getAnnulerDocuments() {
        return annulerDocuments;
    }

    public void setAnnulerDocuments(JButtonAnnuler annulerDocuments) {
        this.annulerDocuments = annulerDocuments;
    }

    public JButton getAddDocuments() {
        return addDocuments;
    }

    public void setAddDocuments(JButton addDocuments) {
        this.addDocuments = addDocuments;
    }

    public JButton getSuppDocuments() {
        return suppDocuments;
    }

    public void setSuppDocuments(JButton suppDocuments) {
        this.suppDocuments = suppDocuments;
    }

    public JTextField getjTextLabelVariable() {
        return jTextLabelVariable;
    }

    public void setjTextLabelVariable(JTextField jTextLabelVariable) {
        this.jTextLabelVariable = jTextLabelVariable;
    }

    public JTextField getjTextValeurVariable() {
        return jTextValeurVariable;
    }

    public void setjTextValeurVariable(JTextField jTextValeurVariable) {
        this.jTextValeurVariable = jTextValeurVariable;
    }
}
