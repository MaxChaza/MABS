/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.util;

import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JProgressBar;


/**
 *
 * @author Maxime
 */
public class JProgressBarMail extends Frame 
{
    private JProgressBar maBarre;

    public JProgressBar getMaBarre() {
        return maBarre;
    }

    public JProgressBarMail()
    {
        setLayout(new BorderLayout());
        maBarre = new JProgressBar(0,100);
        maBarre.setString("Envoie du mail.");
        maBarre.setStringPainted(true);
        maBarre.setIndeterminate(true);
        add("Center", maBarre);
        setBounds(320,240,250,100);
        setVisible(true);
    }

} 

//public class JProgressBarMail extends Thread
//{
//    private JProgressBar maBarre;
//    private JFrame f;
//    public JProgressBar getMaBarre() {
//        return maBarre;
//    }
//
//    public JProgressBarMail()
//    {
//        f = new JFrame();
//        f.setLayout(new BorderLayout());
//        
//        maBarre = new JProgressBar();
//        maBarre.setString("Progression");
//        maBarre.setStringPainted(true);
////        maBarre.setIndeterminate(true);
//        f.add("Center", maBarre);
//        f.setBounds(320,240,250,100);
//        f.setVisible(true);   
//    }   
//}
