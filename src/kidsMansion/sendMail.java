package kidsMansion;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sendMail {

	public static void main(String[] args) {
          //sendMail.send(1, "srinidhi.mc@gmail.com,srinidhi_mc@yahoo.com,srinidhi.chatrapathi@tarams.com");
          
		final int maxEmailList = 22;
		int totalsize = 211;
		int size = totalsize / maxEmailList;
		int counter = 0;
		System.out.println(" -- > " + (size +1 ));
		for(int i =0; i< totalsize ;i++ ){
			
			 if((i % maxEmailList == 0 && i > 1) ) {
				 System.out.println(" its 22 -- " + i + " counter -- " + counter);
				 counter++;
			 }else if (i == totalsize -1 ){
				 System.out.println("Total Size all mails " + i + " counter " + counter);
			 }
		}
		
		System.out.println("Sent Successfully......");

	}
	
	
	
 public boolean send(int payment_id, String emails)	{
	 boolean sent = true;
	 int daycare=0, transport=0, snacks=0, breakfast=0, lunch=0,artClass = 0, danceClass=0, adhoc=0, total = 0;
	 
	 
	    final String username = "vinaya@kidsmansion.in";
		final String password = "vin_sri_22";
		System.out.println("Start...");
        Properties props = new Properties();
        /* commented out as this port 465 is not working and pointing to pop3 25*/
       /* props.put("mail.smtp.host", "lnx7sg-u.securehostdns.com"); //SMTP Host smtp.gmail.com
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port
*/        
        
        props.put("mail.smtp.host", "lnx7sg-u.securehostdns.com"); //SMTP Host smtp.gmail.com
        props.put("mail.smtp.starttls.enables", "true"); 
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "25"); //SMTP Port
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
			message.addRecipient(Message.RecipientType.BCC, new InternetAddress("vinaya@kidsmansion.in"));
			message.setSentDate(new Date());
					
			String sql= "select * from KM.PAYMENT_DETAILS where id = " + payment_id;
			Calendar cal = Calendar.getInstance();
			String month = new SimpleDateFormat("MMM").format(cal.getTime());
			controllerDAO cDAO = new controllerDAO();
			ResultSet rsMail = cDAO.getResult(sql);
			while ( rsMail.next() ) {
				daycare = rsMail.getInt("DAYCARE");
				transport = rsMail.getInt("TRANSPORT");
				breakfast = rsMail.getInt("BF");
				lunch = rsMail.getInt("LUNCH");
				snacks = rsMail.getInt("SNACKS");
				danceClass = rsMail.getInt("DANCE");
				artClass = rsMail.getInt("ART");
				adhoc = rsMail.getInt("ADHOC");
				total = rsMail.getInt("TOTAL");
				month = rsMail.getString("MONTH");
			}
			
			StringBuilder sb = new StringBuilder();
			DecimalFormat moneyFormat = new DecimalFormat("#,##,##0.00");
			sb.append("<HTML> <BODY> <Font Face = 'Verdana' size = '2' color = 'Navy'> Dear Parents, <br><br> ");
			sb.append(" Please find the fees details. <br><br>");
			if(daycare > 0) sb.append(" Day Care: Rs." + moneyFormat.format(  daycare ) );
			if(transport > 0)  sb.append("<br> Transport: Rs." + moneyFormat.format( transport));
			if(breakfast > 0)  sb.append("<br> Break Fast: Rs." + moneyFormat.format( breakfast));
			if(lunch > 0)  sb.append("<br> Lunch: Rs." + moneyFormat.format( lunch ));
			if(snacks > 0)  sb.append("<br> Snacks: Rs." + moneyFormat.format(  snacks));
			if(danceClass > 0)  sb.append("<br> Dance Class: Rs." + moneyFormat.format( danceClass));
			if(artClass > 0)  sb.append("<br> Art Class: Rs." + moneyFormat.format( artClass));
			if(adhoc > 0) sb.append("<br> Ad Hoc: Rs." + moneyFormat.format( adhoc ));
			sb.append(" <br> <b>Total Fees: Rs. " + moneyFormat.format( total )+"</b> <br><br> ");
			sb.append(" -- <br>Thank You <br> Vinaya Srinidhi </font> ");
			sb.append("<Font Face = 'Verdana' size = '1.5' color = 'Navy'> <br> KIDS MANSION <br> Ph: +91 9980264602 <br> <a href='http://www.kidsmansion.in'>www.kidsmansion.in </a>   ");
			sb.append("<br> <a href='http://www.facebook.com/kidsmansion'> www.facebook.com/kidsmansion </a> </font>");
			sb.append("</body></html>");
			
			System.out.println("sendMail:send::Text body is " + sb);
			message.setSubject("Day Care Fees " + month/*new SimpleDateFormat("MMM").format(cal.getTime())*/ + "- " + new SimpleDateFormat("YYYY").format(cal.getTime()));
		    message.setContent(sb.toString(), "text/html");
			/*//message.setContent(arg0, arg1);n("<HTML> <BODY> <BR> <B>Dear Parents <B>,"
				+ "\n\n No spam to my email, please! </BODY></HTML>");*/
 
			Transport.send(message);
            Thread.sleep(150);
			System.out.println("sendMail:send:: Mail Sent.....");
			sql = "update KM.PAYMENT_DETAILS set SENTMAIL = 1 where id =" + payment_id ;
			int rtn = cDAO.addUser(sql);
			System.out.println("sendMail:send::Updated Rows " + rtn);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (Exception e){
			e.printStackTrace();
		}
	
	
	 return sent;
 }
	

}
