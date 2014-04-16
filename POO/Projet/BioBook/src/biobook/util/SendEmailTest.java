package biobook.util;

import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailTest
{
    public SendEmailTest(String mailTo, String msg, String sujet){

        final String username = "maxime.chazalviel@gmail.com";
        final String password = "Tu peux chercher";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                }
          });

        try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("maxime.chazalviel@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(mailTo));
                message.setSubject(sujet);
                message.setText(msg);

                Transport.send(message);
                System.out.println("ok");

        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
    }
}