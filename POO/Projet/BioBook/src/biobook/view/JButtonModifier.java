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
class JButtonModifier extends JButton implements MouseListener{

    public JButtonModifier() {
        ImageIcon bouttonModifier = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\boutonmodifier\\bouton-modifier-exited.png");
        setIcon(bouttonModifier);
        setOpaque(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        addMouseListener(this);
        setBorder(null);
        setFocusPainted(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ImageIcon bouttonModifier = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\boutonmodifier\\bouton-modifier.png");
        setIcon(bouttonModifier);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ImageIcon bouttonModifier = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\boutonmodifier\\bouton-modifier.png");
        setIcon(bouttonModifier);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ImageIcon bouttonModifier = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\boutonmodifier\\bouton-modifier.png");
        setIcon(bouttonModifier);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ImageIcon bouttonModifier = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\boutonmodifier\\bouton-modifier-entered.png");
        setIcon(bouttonModifier);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ImageIcon bouttonModifier = new ImageIcon("C:\\Users\\Maxime\\Documents\\MABS\\POO\\Projet\\BioBook\\src\\biobook\\image\\boutonmodifier\\bouton-modifier-exited.png");
        setIcon(bouttonModifier);
    }
    
}
