
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.view;

import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Maxime
 */
public class JButtonModifier extends JButton implements MouseListener{
    private String pathToImage = ".\\src\\biobook\\image\\";
    public JButtonModifier()  {

        ImageIcon bouttonModifier = new ImageIcon(pathToImage + "boutonmodifier\\bouton-modifier-exited.png");
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
        ImageIcon bouttonModifier = new ImageIcon(pathToImage +"boutonmodifier\\bouton-modifier-entered.png");
        setIcon(bouttonModifier);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ImageIcon bouttonModifier = new ImageIcon(pathToImage +"boutonmodifier\\bouton-modifier.png");
        setIcon(bouttonModifier);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ImageIcon bouttonModifier = new ImageIcon(pathToImage +"boutonmodifier\\bouton-modifier.png");
        setIcon(bouttonModifier);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ImageIcon bouttonModifier = new ImageIcon(pathToImage +"boutonmodifier\\bouton-modifier-entered.png");
        setIcon(bouttonModifier);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ImageIcon bouttonModifier = new ImageIcon(pathToImage +"boutonmodifier\\bouton-modifier-exited.png");
        setIcon(bouttonModifier);
    }
    
}
