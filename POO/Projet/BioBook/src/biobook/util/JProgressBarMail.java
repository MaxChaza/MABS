/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.util;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 *
 * @author Maxime
 */
public class JProgressBarMail extends Frame implements ChangeListener
{
    private JProgressBar maBarre;

    public JProgressBar getMaBarre() {
        return maBarre;
    }

    public JProgressBarMail()
    {
        setLayout(new BorderLayout());
        maBarre = new JProgressBar(0,99);
        maBarre.setString("Progression");
        maBarre.setStringPainted(true);
        maBarre.addChangeListener(this);
        add("Center", maBarre);
        setBounds(320,240,250,100);
        setVisible(true);
    }

    public void stateChanged(ChangeEvent e)
    {
        maBarre.setString ("Progession: " + (int)(maBarre.getPercentComplete()*100) + "%");
    } 
}
