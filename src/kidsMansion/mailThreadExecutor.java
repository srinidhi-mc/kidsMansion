package kidsMansion;

import java.sql.ResultSet;
import java.sql.SQLException;
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

public class mailThreadExecutor extends Thread  {
	
  public static String last_mail_sent = null;
  private String selectDate, iDs;
  
  
  public mailThreadExecutor(String selectDate, String iDs){
	  this.selectDate = selectDate;
	  this.iDs = iDs;
	  
  }

	@SuppressWarnings("null")
	public void run()  {
		int id = 0;
		String sql = null;
		controllerDAO cDAO = new controllerDAO();
		try {
				SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
				String curentTime = sdf.format(Calendar.getInstance().getTime());
				String startTime = null;
				long waitTime =0; ;
				sql = "select * from KM.DAILY_REPORT where SEND_DATE = '" + selectDate + "' and ID in ("+ iDs + ") AND DELETED = 0 AND STATUS in (0,-1)  AND TO_SEND = 1 ORDER BY TIME ";
				
				ResultSet rs1 = cDAO.getResult(sql);
				if(rs1.next()){
					startTime = rs1.getString("TIME");
					Date startingTime = sdf.parse(startTime);
					Date currentTime = sdf.parse(curentTime);
					 waitTime =  startingTime.getTime() - currentTime.getTime();
					 System.out.println("wait time --> " + waitTime);
				}
				rs1.close();
				// Only if the wait time is more than wait till 13:15 else get started.
			  if(waitTime > 0)	Thread.sleep(waitTime);
					 id = 0;
				   while(true) {
					System.out.println("mailThreadExecutor:run Invoked....");
				    sql = "select * from KM.DAILY_REPORT where SEND_DATE = '" + selectDate + "' and ID in ("+ iDs + ") AND DELETED = 0 AND STATUS =0 AND TO_SEND= 1 order by TIME";
					ResultSet rs = cDAO.getResult(sql);
					if(rs.next()) {
						id = rs.getInt("ID");
						sendMailContent(rs.getString("SUBJECT"), rs.getString("CONTENT"), rs.getString("MAIL_ID"));
					}else{
						break;
					}
					rs.close();
			    	sql = "UPDATE KM.DAILY_REPORT SET STATUS = 1 , UPDATED_DATE = NOW() WHERE ID =" + id;
			    	 cDAO.addUser(sql); 
			    	 Thread.sleep(900000); // 15 min = 900000 milliseconds
			}	
		} catch (InterruptedException e) {
			sql = "UPDATE KM.DAILY_REPORT SET STATUS = -1, TO_SEND =0 , UPDATED_DATE = NOW() WHERE STATUS = 0 and  ID in (" + iDs + ")";
	    	 cDAO.addUser(sql); 
			 e.printStackTrace();
		} catch (MessagingException me){
			sql = "UPDATE KM.DAILY_REPORT SET STATUS = -1, TO_SEND =0 , UPDATED_DATE = NOW() WHERE STATUS = 0 and  ID in (" + iDs + ")";
	    	 cDAO.addUser(sql);
		     me.printStackTrace();
	     }catch(Exception e){
	    	 sql = "UPDATE KM.DAILY_REPORT SET STATUS = -1, TO_SEND =0 , UPDATED_DATE = NOW() WHERE STATUS = 0 and  ID in (" + iDs + ")";
	    	 cDAO.addUser(sql);
			e.printStackTrace();
		}
  }
	
	
	
	public boolean sendMailContent(String Subject, String Content, String recipients) throws MessagingException, Exception	{
	    boolean sent = true;
		System.out.println(" sendMailContent::Start...");
        
        final String username = "";
		final String password = "";
		
        Properties props = new Properties();
        props.put("mail.smtp.host", "lnx7sg-u.securehostdns.com"); //SMTP Host smtp.gmail.com
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port
       
        
        Session session = Session.getInstance(props,
      		  new javax.mail.Authenticator(
      				  ) {
      			protected PasswordAuthentication getPasswordAuthentication() {
      				return new PasswordAuthentication(username, password);
      			}
      		  });
        
	    
        	 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("vinaya@kidsmansion.in","Vinaya Srinidhi"));
			//String recipient = emails;
			System.out.println("actual recipients " + recipients);
			// recipients ="srinidhi.mc@gmail.com,srinidhi_mc@yahoo.com";
			String[] recipientList = recipients.split(",");
			InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
			int counter = 0;
			for (String rec : recipientList) {
			    recipientAddress[counter] = new InternetAddress(rec.trim());
			    counter++;
			}
			message.setRecipients(Message.RecipientType.BCC, recipientAddress);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress("vinaya@kidsmansion.in","Vinaya Srinidhi"));
		    message.setSentDate(new Date());
		    message.setSubject(Subject);
		    
			StringBuilder sb = new StringBuilder();
			
			sb.append(Content);
						
			// Signature
			sb.append("<HTML> <BODY> <Font Face = 'Verdana' size = '2' color = 'Navy'>" );
			sb.append("<Br><br> -- <br>Thank You <br> Vinaya Srinidhi </font> ");
			sb.append("<Font Face = 'Verdana' size = '1.5' color = 'Navy'> <br> KIDS MANSION <br> Ph: +91 9980264602 <br> <a href='http://www.kidsmansion.in'>www.kidsmansion.in </a>   ");
			sb.append("<br> <a href='http://www.facebook.com/kidsmansion'> www.facebook.com/kidsmansion </a> </font>");
			sb.append("</body></html>");
			System.out.println(" Text body is " + sb);
	       message.setContent(sb.toString(), "text/html");
				    
	        Transport.send(message);
           System.out.println(" sendMailContent::Done..");
 
		
	
	 return sent;
 }

}
