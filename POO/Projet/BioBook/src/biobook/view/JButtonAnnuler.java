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
public class JButtonAnnuler extends JButton implements MouseListener{
    private String pathToImage = ".\\src\\biobook\\image\\";
    public JButtonAnnuler() {
        ImageIcon bouttonAnnuler = new ImageIcon(pathToImage + "boutonannuler\\bouton-annuler-exited.png");
        setIcon(bouttonAnnuler);
        setOpaque(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        addMouseListener(this);
        setBorder(null);
        setFocusPainted(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ImageIcon bouttonAnnuler = new ImageIcon(pathToImage + "boutonannuler\\bouton-annuler-entered.png");
        setIcon(bouttonAnnuler);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ImageIcon bouttonAnnuler = new ImageIcon(pathToImage + "boutonannuler\\bouton-annuler.png");
        setIcon(bouttonAnnuler);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ImageIcon bouttonAnnuler = new ImageIcon(pathToImage + "boutonannuler\\bouton-annuler.png");
        setIcon(bouttonAnnuler);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ImageIcon bouttonAnnuler = new ImageIcon(pathToImage + "boutonannuler\\bouton-annuler-entered.png");
        setIcon(bouttonAnnuler);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ImageIcon bouttonAnnuler = new ImageIcon(pathToImage + "boutonannuler\\bouton-annuler-exited.png");
        setIcon(bouttonAnnuler);
    }
    
}
