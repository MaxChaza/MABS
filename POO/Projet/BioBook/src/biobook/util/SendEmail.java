package biobook.util;

import biobook.model.Chercheur;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.SwingWorker;

public class SendEmail extends SwingWorker<Void, String>
{
    public JProgressBarMail progress;
    public final String username ;
    public final String password ;
    public Properties props;
    public Chercheur c;
    public String sujet;
    public String msg;
    
    public SendEmail(Chercheur c, String sujet, String msg)
    {
        
        this.c=c;
        this.sujet=sujet;
        this.msg=msg;
        
        username = "maxime.chazalviel@gmail.com";
        password = "Tu peux chercher";

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        progress = new JProgressBarMail();
        /* On ajoute un écouteur de barre de progression. */
      
    }

    public Void envoyerMail() throws InterruptedException{
        	
      
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
              @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                }
          });  
        try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("maxime.chazalviel@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(c.getMail()));

                message.setSubject(sujet);
                
                message.setText(msg);
                
                Transport.send(message);
                

        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
        return null;
       
    }
    @Override
    public Void doInBackground() throws InterruptedException {
	
        return envoyerMail();
    }

    @Override
    protected void done() {
            try {
                    /* Le traitement est terminé. */
                    progress.getMaBarre().setVisible(false);
                    progress.setVisible(false);
                    progress.dispose();

            } catch (Exception e) {
                    e.printStackTrace();
            }
    }    
}