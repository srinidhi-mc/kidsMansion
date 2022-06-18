package kidsMansion;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class sendMailModified {

	public static void main(String[] args) {
          sendMailModified sm = new sendMailModified();
          sm.send(1, "srinidhi.mc@gmail.com,srinidhi_mc@yahoo.com,srinidhi.chatrapathi@tarams.com");
          System.out.println("Sent Successfully......");

	}
	
	
	
 public boolean send(int payment_id, String emails)	{
	 boolean sent = true;
	 int daycare=2400, transport=400, snacks=0, breakfast=0, lunch=0,artClass = 0, danceClass=0, adhoc=0, total = 2800;
	 
	 final String username = "vinaya@kidsmansion.in";
		final String password = "xxxx";
		System.out.println("Start...");
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
			
			String sql= "select * from KM.PAYMENT_DETAILS where id = " + payment_id;
			/*ResultSet rsMail = null;
			
			daycare = rsMail.getInt("DAYCARE");
			transport = rsMail.getInt("TRANSPORT");
			breakfast = rsMail.getInt("BF");
			lunch = rsMail.getInt("LUNCH");
			snacks = rsMail.getInt("SNACKS");
			danceClass = rsMail.getInt("DANCE");
			artClass = rsMail.getInt("ART");
			adhoc = rsMail.getInt("ADHOC");
			total = rsMail.getInt("TOTAL");*/
			
			Calendar cal = Calendar.getInstance();
			StringBuilder sb = new StringBuilder();
			sb.append("<HTML> <BODY> <Font Face = 'Verdana' size = '2' color = 'Navy'> Dear Parents, <br><br> ");
			sb.append(" Please find the fees details. <br><br>");
			if(daycare > 0) sb.append(" Day Care: Rs." + daycare  );
			if(transport > 0)  sb.append("<br> Transport: Rs." + transport);
			if(breakfast > 0)  sb.append("<br> Break Fast: Rs." + breakfast);
			if(lunch > 0)  sb.append("<br> Lunch: Rs." + lunch);
			if(snacks > 0)  sb.append("<br> Snacks: Rs." + snacks);
			if(danceClass > 0)  sb.append("<br> Dance Class: Rs." + danceClass);
			if(artClass > 0)  sb.append("<br> Art Class: Rs." + artClass);
			if(adhoc > 0) sb.append("<br> Ad Hoc: Rs." + artClass);
			sb.append(" <br> <b>Total Fees: Rs. " + total +"</b> <br><br> ");
			sb.append(" -- <br>Thank You <br> Vinaya Srinidhi </font> ");
			sb.append("<Font Face = 'Verdana' size = '1.5' color = 'Navy'> <br> KIDS MANSION <br> Ph: +91 9980264602 <br> <a href='http://www.kidsmansion.in'>www.kidsmansion.in </a>   ");
			sb.append("<br> <a href='http://www.facebook.com/kidsmansion'> www.facebook.com/kidsmansion </a> </font>");
			sb.append("</body></html>");
			
			System.out.println(" Text body is " + sb);
			message.setSubject("Day Care Fees " + new SimpleDateFormat("MMM").format(cal.getTime()) + "- " + new SimpleDateFormat("YYYY").format(cal.getTime()));
		    message.setContent(sb.toString(), "text/html");
			/*//message.setContent(arg0, arg1);n("<HTML> <BODY> <BR> <B>Dear Parents <B>,"
				+ "\n\n No spam to my email, please! </BODY></HTML>");*/
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (Exception e){
			e.printStackTrace();
		}
	
	
	 return sent;
 }
 
 public boolean sendMailContent(String Subject, String emails, String mailBody)	{
	    boolean sent = true;
		System.out.println(" sendMailContent::Start...");
        
        final String username = "vinaya@kidsmansion.in";
		final String password = "xxxx";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
		props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.host", "smtp.office365.com"); //SMTP Host smtp.gmail.com
        props.put("mail.smtp.port", "587"); //SMTP Port
       
        
        Session session = Session.getInstance(props,
      		  new javax.mail.Authenticator(
      				  ) {
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
			
						
			
			StringBuilder sb = new StringBuilder();
			sb.append("<HTML> <BODY> <Font Face = 'Verdana' size = '2' color = 'Navy'>" );
			sb.append(mailBody);
			
			//sb.append("</FONT>");
			// Signature
			sb.append("<Br><br> -- <br>Thank You <br> Vinaya Srinidhi </font> ");
			sb.append("<Font Face = 'Verdana' size = '1.5' color = 'Navy'> <br> KIDS MANSION <br> Ph: +91 9980264602 <br> <a href='http://www.kidsmansion.in'>www.kidsmansion.in </a>   ");
			sb.append("<br> <a href='http://www.facebook.com/kidsmansion'> www.facebook.com/kidsmansion </a> </font>");
			sb.append("</body></html>");
			
			System.out.println(" Text body is " + sb);
			message.setSubject(Subject);
			
			
		   boolean isAttachment = false;
			 
		  if(!isAttachment) {
			  message.setContent(sb.toString(), "text/html");
		  }else{
			  message.setText(sb.toString());
		  }
		    message.setSentDate(new Date());
			
		    //Attachement 
		   
		    if(isAttachment ){
			    MimeBodyPart messageBodyPart = new MimeBodyPart();
		        Multipart multipart = new MimeMultipart();
		        messageBodyPart = new MimeBodyPart();
		        String file = "path of file to be attached";
		        String fileName = "attachmentName";
		        DataSource source = new FileDataSource(file);
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(fileName);
		        multipart.addBodyPart(messageBodyPart);
		        message.setContent(multipart);
		    }
	        Transport.send(message);
            Thread.sleep(100); 
			System.out.println(" sendMailContent::Done..");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (Exception e){
			e.printStackTrace();
		}
	
	
	 return sent;
 }
	

}
