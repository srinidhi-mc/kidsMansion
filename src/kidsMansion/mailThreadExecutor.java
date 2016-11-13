package kidsMansion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

public class mailThreadExecutor implements Runnable  {
	String emails, subject, message, fileName01, fileName02, fileName03, uploadfile01 ,uploadfile02, uploadfile03 ;
	int size;
	
	public mailThreadExecutor(int size){
		this.size = size;
		
	}
	
	public mailThreadExecutor(String Subject, String Message, String Emails, String uploadFile01, 
			                  String uploadFile02, String uploadFile03, String FileName01, String FileName02, String FileName03  ){
		
		this.subject = Subject;
		this.message= Message;
		this.emails = Emails;
		this.uploadfile01 = uploadFile01;
		this.uploadfile02 = uploadFile02;
		this.uploadfile03= uploadFile03;
		this.fileName01 = FileName01;
		this.fileName02 = FileName02;
		this.fileName03 = FileName03;
		//this.size = size;
	}
	
	
	@Override
	public String toString() {
		return "mailThreadExecutor [emails=" + emails + ", subject=" + subject
				+ ", message=" + message + ", fileName01=" + fileName01
				+ ", fileName02=" + fileName02 + ", fileName03=" + fileName03
				+ ", uploadfile01=" + uploadfile01 + ", uploadfile02="
				+ uploadfile02 + ", uploadfile03=" + uploadfile03 + "]";
	}

	@SuppressWarnings("null")
	public void run()  {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		/*String[] emailList = this.emails.split(",");
		System.out.println(" Total Number of Emails " + emailList.length);
		int size =  emailList.length /22 +1 ;// + 1;
		String[] emailBreakUp = new String[size];
		System.out.println(" *** Size " + size);
		int counter =0, listVar = 0;
		 String emailsAdd = null;
		for(int i =0; i< emailList.length; i++){
			counter++;
			if(counter <= 22 ){
				if (counter == 1) {
					emailsAdd = emailList[i] ;
				}else {
				    emailsAdd +=  "," +emailList[i];
				}	
		    } else {
		    //	System.out.println(" ****  " + i);
		    	emailBreakUp[listVar] = emailsAdd;
		    	counter = 0;
		    	listVar++;
		    	emailsAdd = null;
		    }
			
			
		}
		
		String filePathAppended = this.uploadfile01+ ";" + this.uploadfile02 + ";" + this.uploadfile03;
		String fileNamesAppended = this.fileName01+ ";" + this.fileName02 +";" + this.fileName03 ;
		for(String str: emailBreakUp ) {
			 System.out.println(" Break Up Email is  " + str);
		   String  sql =" INSERT INTO KM.MAIL_INFO (SUBJECT,MODIFIED_DATE,MAILER_LIST,CREATE_DATE,BODY,ATTACHMENT_PATH,ATTACHMENT_FILE) VALUES ( " 
			        		+ "'" + this.subject + "','" + sdf.format(new Date()) + "','"  + str + "','"   +  sdf.format(new Date()) + "','" + this.message + "','"
				            +  filePathAppended + "','" + fileNamesAppended +"')";
		   controllerDAO cDAO = new controllerDAO();
		   cDAO.addUser(sql);
		   System.out.println(" mailThreadExecutor::run --> SQL " + sql);
		  
		}*/
		
		// Insertion Completed...
	  int counter = 1, id=0;
	  while(counter <= size) {
		try {
			
			System.out.println(" run Invoked....");
			
			
			
			String sql = "select * from mail_info where MAIL_SENT ='N' limit 1";
			controllerDAO cDAO = new controllerDAO();
			ResultSet rs = cDAO.getResult(sql);
			while(rs.next()) {
				this.subject  = rs.getString("SUBJECT");
				this.message = rs.getString("BODY");
				this.emails = rs.getString("MAILER_LIST");
				String fileNamess = rs.getString("ATTACHMENT_FILE");
				String filePathss = rs.getString("ATTACHMENT_PATH");
				id = rs.getInt("ID");
			
			String[] fileNamesss = null;
			String[] filePathsss = null ;
			
			if(!fileNamess.equalsIgnoreCase(";;"))  {
				 fileNamesss  = fileNamess.split(";");
						
				if(fileNamesss.length!= 0 &&!fileNamesss[0].equalsIgnoreCase("")){
					this.fileName01 = fileNamesss[0];
				}
				
				if(fileNamesss.length >1 && !fileNamesss[1].equalsIgnoreCase("")){
					this.fileName02 = fileNamesss[1];
				}
				
				if(fileNamesss.length >2 &&!fileNamesss[2].equalsIgnoreCase("")){
					this.fileName03 = fileNamesss[2];
				}
			
			}
			 
			if(!filePathss.equalsIgnoreCase(";;"))  {
				 filePathsss  = filePathss.split(";");
				
					if( filePathsss.length != 0 &&!filePathsss[0].equalsIgnoreCase("")){
						this.uploadfile01 = filePathsss[0];
					}
					
					if( filePathsss.length > 1 && !filePathsss[1].equalsIgnoreCase("")){
						this.uploadfile02 = filePathsss[1];
					}
					
					if( filePathsss.length >2 &&  !filePathsss[2].equalsIgnoreCase("")){
						this.uploadfile03 = filePathsss[2];
					}
				
				}
			
			}	
			rs.close();
	    	
	    	sendMailContent();
	    	
	    	 sql = "UPDATE KM.MAIL_INFO SET MAIL_SENT = 'Y', MODIFIED_DATE = '" + sdf.format(new Date())  +"' WHERE ID =" + id;
	    	 cDAO.addUser(sql);
	    	
			if(counter != size )Thread.sleep(10000); //600000
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		counter++;
		
	}	
		System.out.println( sdf.format(new Date()) + "  mailThreadExecutor Completed..." + this.toString());
		
	}
	
	
	
	public boolean sendMailContent()	{
	    boolean sent = true;
		System.out.println(" sendMailContent::Start...");
        
        final String username = "vinaya@kidsmansion.in";
		final String password = "vin_sri_22";
		
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
        
	     try {
        	 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("vinaya@kidsmansion.in","Vinaya Srinidhi"));
			//String recipient = emails;
			String recipient ="srinidhi.mc@gmail.com,srinidhi_mc@yahoo.com";
			String[] recipientList = recipient.split(",");
			InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
			int counter = 0;
			for (String rec : recipientList) {
			    recipientAddress[counter] = new InternetAddress(rec.trim());
			    counter++;
			}
			message.setRecipients(Message.RecipientType.BCC, recipientAddress);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress("vinaya@kidsmansion.in","Vinaya Srinidhi"));
		    message.setSentDate(new Date());
		    message.setSubject(this.subject);
		    
			StringBuilder sb = new StringBuilder();
			sb.append("<HTML> <BODY> <Font Face = 'Verdana' size = '2' color = 'Navy'>" );
			sb.append(this.message);
						
			// Signature
			sb.append("<Br><br> -- <br>Thank You <br> Vinaya Srinidhi </font> ");
			sb.append("<Font Face = 'Verdana' size = '1.5' color = 'Navy'> <br> KIDS MANSION <br> Ph: +91 9980264602 <br> <a href='http://www.kidsmansion.in'>www.kidsmansion.in </a>   ");
			sb.append("<br> <a href='http://www.facebook.com/kidsmansion'> www.facebook.com/kidsmansion </a> </font>");
			sb.append("</body></html>");
			System.out.println(" Text body is " + sb);
			
			
		 
		// If attachment Exists	 
		  if(this.fileName01!= null && !this.fileName01.equalsIgnoreCase("")) {
			  // Set Message Information
			  MimeBodyPart messageBodyPart = new MimeBodyPart();
		      messageBodyPart.setContent(sb.toString(), "text/html");
		       
		      Multipart multipart = new MimeMultipart();
		      multipart.addBodyPart(messageBodyPart);
			 
		      //For first Attachment     
		      MimeBodyPart attachPart = new MimeBodyPart();
		      attachPart.attachFile(this.uploadfile01 + "//"+ this.fileName01);
		      multipart.addBodyPart(attachPart);
		      
			   if(this.fileName02!= null && !this.fileName02.equalsIgnoreCase("")) {
				   MimeBodyPart attachPart01 = new MimeBodyPart();
				   attachPart01.attachFile( this.uploadfile02 + "//"+ this.fileName02);
				   multipart.addBodyPart(attachPart01);
			   }	   
			   if(this.fileName03!= null && !this.fileName03.equalsIgnoreCase("")) {
				   MimeBodyPart attachPart01 = new MimeBodyPart();
				   messageBodyPart.attachFile(this.uploadfile03 + "//"+ this.fileName03);
				   multipart.addBodyPart(attachPart01);
				   
			   }  
			    
			   message.setContent(multipart);
		  }else{
			  message.setContent(sb.toString(), "text/html");
		  }
		    
	//	    Transport.send(message);
           System.out.println(" sendMailContent::Done..");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (Exception e){
			e.printStackTrace();
		}
	
	
	 return sent;
 }

}
