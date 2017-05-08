/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mailsender;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 *
 * @author Shaplygin
 */
public class Sender {
    
    public Sender(String hostMail, String portMail, String username, String password, boolean tls){
        this.hostMail = hostMail;
        this.portMail = portMail;
        this.username = username;
        this.password = password;
        
        props = new Properties();
        if (tls){
            props.put("mail.smtp.auth", "true");  
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", hostMail);
            props.put("mail.smtp.port", portMail);        
        }else {
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.host", hostMail);
            props.put("mail.smtp.socketFactory.port", portMail);
            props.put("mail.smtp.auth", "true");
        }
    }
    
    public void send(String subject, String text, String toEmails, String filePath) throws UnsupportedEncodingException{
        try{
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmails, false));
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            
            setFileAsAttachment(text, filePath);
            Transport.send(msg);
        } catch(MessagingException exp){
            exp.printStackTrace();
            while(exp.getNextException() != null){
                Exception ex = exp.getNextException();
                ex.printStackTrace();
                if (!(ex instanceof MessagingException)) break;
                else exp = (MessagingException)ex;
            }
        }
    }

    private static void setFileAsAttachment(String text, String filePath) throws MessagingException, UnsupportedEncodingException {
        Multipart mp = new MimeMultipart();
        if (filePath.equals("")){ 
            MimeBodyPart filePart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(filePath);
            filePart.setDataHandler(new DataHandler(fds));

            String fileName = fds.getName();
            filePart.setFileName(javax.mail.internet.MimeUtility.encodeWord(fileName)); 
            mp.addBodyPart(filePart);
        }
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(text);
        mp.addBodyPart(textPart);
        msg.setContent(mp);
    }
    
    
    private static Message msg;
    private String hostMail;
    private String portMail;
    private Properties props;
    private String password;
    private String username;
}

