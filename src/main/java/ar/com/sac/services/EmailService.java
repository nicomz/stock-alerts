package ar.com.sac.services;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
 
@Service
@PropertySource("classpath:application.properties") 
public class EmailService {
   
   //587
   @Value("${mail.smtp.port}")
   private String port;
   
   //true
   @Value("${mail.smtp.auth}")
   private String auth;
   
   //true
   @Value("${mail.smtp.starttls.enable}")
   private String ttls;
   
   //xxx@gmail.com
   @Value("${mail.recipient.to}")
   private String to;
   
   //smtp
   @Value("${mail.session.transport}")
   private String protocol;
   
   //smtp.gmail.com
   @Value("${mail.session.host}")
   private String host;
   
   @Value("${mail.session.user.id}")
   private String userId;
   
   @Value("${mail.session.user.password}")
   private String password;
   
   @Value("${mail.smtp.ssl.trust}")
   private String trust;
   
   public void generateAndSendEmail(String subject, String body) throws AddressException, MessagingException {
      Properties mailServerProperties = System.getProperties();
      mailServerProperties.put("mail.smtp.port", port);
      mailServerProperties.put("mail.smtp.auth", auth);
      mailServerProperties.put("mail.smtp.starttls.enable", ttls);
      mailServerProperties.put("mail.smtp.ssl.trust", trust);
 
      // Step2
      Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
      MimeMessage generateMailMessage = new MimeMessage(getMailSession);
      generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      generateMailMessage.setSubject(subject);
      String emailBody = body;
      generateMailMessage.setContent(emailBody, "text/html");
 
      // Step3
      Transport transport = getMailSession.getTransport(protocol);
      transport.connect(host, userId, password);
      transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
      transport.close();
   }
 
   public void generateAndSendTestEmail() throws AddressException, MessagingException {
      generateAndSendEmail( "Stock Alert", "Test email by Stock Alerts.<br><br>Regards, <br>Sergio Cormio");
   }
}