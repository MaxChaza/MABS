

package biobook.view;
import biobook.util.SendEmail;
import biobook.model.Chercheur;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 *
 * @author Said
 */
public class SendMailView extends JFrame implements ActionListener{
    JTextField jt1= new JTextField(5);
    JTextField jt2= new JTextField(5);
    JTextArea jt3= new JTextArea("", 3, 30);
    
     

    
    public SendMailView(){
        this.setTitle("Envoyer Mail");
        setLocationRelativeTo(null); //On centre la fenÃªtre sur l'Ã©cran
        setResizable(true); //On permet le redimensionnement
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        this.setVisible(true);
        
        BorderLayout bl= new BorderLayout();
        Container cp;
        cp = this.getContentPane();
        
        JPanel b1= new JPanel();
        b1.setPreferredSize(new Dimension(600,200));
        
        JPanel b2= new JPanel();
        JPanel b3= new JPanel();

        
        b1.setLayout(new GridLayout(1,2));

        FlowLayout fl= new FlowLayout();
        JPanel flmail= new JPanel(); 
        
       
 


        
        
       
        flmail.add(new JLabel("Mail", SwingConstants.CENTER));
        flmail.add(jt1);
        
        
        flmail.add(new JLabel("Sujet"));
        flmail.add(jt2);
        
        flmail.add(new JLabel("Message"));
        flmail.add(jt3);

        JButton  sendmail= new JButton("Envoyer Mail");
        
        b3.add(sendmail);
        sendmail.addActionListener(this);
        
        b1.add(flmail);
        this.getContentPane().add(BorderLayout.CENTER,b1);
        this.getContentPane().add(BorderLayout.SOUTH,b3);
        
        this.setVisible(true);
        
             
        
                
    }
            
    public void actionPerformed( ActionEvent arg0){
        
         String Chercheur = jt1.getText();
         String sujet= jt2.getText();
         String msg= jt3.getText();
            
    }
     public static void main(String[] args){
         SendMailView fen= new SendMailView();
         
     }

}
