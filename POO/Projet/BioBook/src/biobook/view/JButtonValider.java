/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.view;

import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Maxime
 */
class JButtonValider extends JButton implements MouseListener{

    public JButtonValider() {
        ImageIcon bouttonValider = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\boutonvalider\\bouton-valider-exited.png");
        setIcon(bouttonValider);
        setOpaque(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        addMouseListener(this);
        setBorder(null);
        setFocusPainted(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ImageIcon bouttonValider = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\boutonvalider\\bouton-valider.png");
        setIcon(bouttonValider);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ImageIcon bouttonValider = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\boutonvalider\\bouton-valider.png");
        setIcon(bouttonValider);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ImageIcon bouttonValider = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\boutonvalider\\bouton-valider.png");
        setIcon(bouttonValider);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ImageIcon bouttonValider = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\boutonvalider\\bouton-valider-entered.png");
        setIcon(bouttonValider);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ImageIcon bouttonValider = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\boutonvalider\\bouton-valider-exited.png");
        setIcon(bouttonValider);
    }
    
}
