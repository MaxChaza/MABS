package biobook.util;

import biobook.model.Chercheur;
import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail
{
    public SendEmail(Chercheur c, String sujet, String msg){
        
        JProgressBarMail progress = new JProgressBarMail();
        progress.getMaBarre().setValue(10);
        final String username = "maxime.chazalviel@gmail.com";
        final String password = "Tu peux chercher";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        progress.getMaBarre().setValue(20);
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                }
          });
        progress.getMaBarre().setValue(40);
        try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("maxime.chazalviel@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(c.getMail()));
                progress.getMaBarre().setValue(60);
                message.setSubject(sujet);
                
                progress.getMaBarre().setValue(70);
                message.setText(msg);
                
                progress.getMaBarre().setValue(90);
                Transport.send(message);
                progress.getMaBarre().setValue(100);

        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
        progress.getMaBarre().setValue(110);
    }
}