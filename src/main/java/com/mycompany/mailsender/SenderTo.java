package com.mycompany.mailsender;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shaplygin
 */
public class SenderTo {
    
    public static void main(String[] args){
        //If you want used gmail, you need smtp or ftp protocol for sengind messages
        String hostMail = "smtp.gmail.com";
        //Default port gmail is  25, 465 or 587. I use SSL, need used port 465.
        String portMail = "465";
        //Your mail adress
        String userName = "example@gmail.com";
        //Example password for example@gmail.com
        String password = "qweerty123";
        //If you want user TLS protocol = true, if you want use SSL  protocol = false
        boolean protocol = false;
        //Create object to sending messages
        Sender s = new Sender(hostMail, portMail, userName, password, protocol);
        //append for subject and text message
        String subject = "Simple subject for sending message";
        String text = "Someone text in this message";
        //append adress about mails to send
        String toEmails = "example1@gmail.com example2@gmail.com";
        //If you wnat srick someone file you need change var filePath 
        String filePath = "";
        /*
        If you wan sending to many peoples, use class ReadToWho
        Example:
        Created object ReadToWho
        Append for text file with peoples
        ReadToWho rtw = new ReadToWho("/home/<user>/toMails");
        //this fuction separation info in file and write to filePAth mail names
        filePath = rtw.readFile();
        */
        try {
            s.send(subject, text, toEmails, filePath);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SenderTo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
