package kidsMansion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class testMail {

	public static void main(String[] args) {
		new testMail().send(1,"vinaya@kidsmansion.in,srinidhi.mc@gmail.com");
	     System.out.println("Sent Successfully......");

	}

	public boolean send(int id, String emails)	{
		    boolean sent = true;
		    final String username = "vinaya@kidsmansion.in";
			final String password = "xxxx";
			System.out.println("Start...");
	       /* Properties props = new Properties();
	        props.put("mail.smtp.host", "lnx7sg-u.securehostdns.com"); //SMTP Host smtp.gmail.com
	        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
	        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
	        props.put("mail.smtp.port", "465"); //SMTP Port
*/	        
	        Properties props = new Properties();
			props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
			props.put("mail.smtp.starttls.enable", "true"); 
	        props.put("mail.smtp.host", "smtp.office365.com"); //SMTP Host smtp.gmail.com
	        props.put("mail.smtp.port", "587"); //SMTP Port
	        
	        Session session = Session.getInstance(props,
	      		  new javax.mail.Authenticator() {
	      			protected PasswordAuthentication getPasswordAuthentication() {
	      				return new PasswordAuthentication(username, password);
	      			}
	      		  });
	        
		     try {
	        	 
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("vinaya@kidsmansion.in","Vinaya Srinidhi"));
				String recipient = emails;
				String[] recipientList = recipient.split(",");
				InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
				int counter = 0;
				for (String rec : recipientList) {
				    recipientAddress[counter] = new InternetAddress(rec.trim());
				    counter++;
				}
				message.setRecipients(Message.RecipientType.TO, recipientAddress);
				message.setSubject("Testing Mail");
			    message.setContent("Test Mail Content", "text/html");
				Transport.send(message);
	 
				System.out.println("Done");
	 
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			} catch (Exception e){
				e.printStackTrace();
			}
		
		
		 return sent;
	 }	
	
}
