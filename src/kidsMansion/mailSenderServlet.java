package kidsMansion;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class mailSenderServlet extends HttpServlet {
	
	private static final long serialVersionUID = -6990596740077647269L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
				
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		       String type = req.getParameter("type");
		       List<mailerTO> mailerDate = new ArrayList<mailerTO>();
		       
		       if(type.equalsIgnoreCase("sendMail")){
		    	   String subject = req.getParameter("subject");
		    	   String selection = req.getParameter("selection");
		    	   String message= req.getParameter("message");
		    	   String uploadFile01 = req.getParameter("uploadFile01");
		    	   String uploadFile02 = req.getParameter("uploadFile02");
		    	   String uploadFile03 = req.getParameter("uploadFile03");
		    	   String FileName01 = req.getParameter("FileName01");
		    	   String FileName02 = req.getParameter("FileName02");
		    	   String FileName03 = req.getParameter("FileName03");
		    	   
		    	   System.out.println("Request type is sendMail " + subject +" -- " + selection + " -- " + message);
		    	   System.out.println("Request type is sendMail " + uploadFile01 + " -- " + uploadFile02 + " -- " + uploadFile03);
		    	   System.out.println("Request type is sendMail " + FileName01 + " -- " + FileName02 + " -- " + FileName03);
		    	   
		    	   String sql = "select * from KM.STUDENTS " ;
		    		if(!selection.equalsIgnoreCase("All"))	  sql = sql +" where class = '"+selection+"'";
		    		  controllerDAO cDAO = new controllerDAO();
				    ResultSet  rs =  cDAO.getResult(sql);
				    String emailList = "srinidhi.mc@gmail.com";
				    System.out.println(" Sql is *** " + sql);
				  //  int i =1;
				    try {
						while(rs.next()){
							String email1 =  rs.getString("EMAIL_1"); 
							String email2 = rs.getString("EMAIL_2");
					//		System.out.println(" emailList --> " + i + " -- " +  email1 + " -- " + email2);
							if(email1 != null && !email1.equalsIgnoreCase("")) emailList += "," + email1 ; //+ "," + emailList;
							if(email2 != null && !email2.equalsIgnoreCase("")) emailList += "," + email2; // + "," + emailList;
							//System.out.println(" emailList --> " +  + i + " -- " +  emailList);
						//	i++;
						}
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
		    	   
				    System.out.println(" Email id is " + emailList);
				    
				  int size =  insertMailData(subject, message, emailList, uploadFile01, uploadFile02, uploadFile03, FileName01, FileName02, FileName03);
				    
				    mailThreadExecutor mTE =   new mailThreadExecutor(size);
				    new Thread(mTE).start();
		    	    System.out.println(" *** Task Completed **** "); 
		    	   
		    	   
		       }
		       
		       /*  Display any pending task         */
		
				     String sql ="select * from KM.MAIL_INFO WHERE MAIL_SENT = 'N'";
				     System.out.println("mailSenderServlet:doPost.." +sql);
				      controllerDAO cDAO = new controllerDAO();
					  ResultSet  rs =  cDAO.getResult(sql);
					
					 try {
						while(rs.next()){
							mailerTO mTO = new mailerTO();
							mTO.setID(rs.getInt("ID"));
							//mTO.setPID(rs.getInt("PID"));
							mTO.setCREATE_DATE(rs.getString("CREATE_DATE"));
							mTO.setCREATE_DATE(rs.getString("MODIFIED_DATE"));
							mTO.setSUBJECT(rs.getString("SUBJECT"));	
							mTO.setATTACHMENT_FILE(rs.getString("aTTACHMENT_FILE"));
							mTO.setMAILER_LIST(rs.getString("mAILER_LIST"));
							mailerDate.add(mTO);
					  }
						rs.close();
					 
					} catch (SQLException e) {
						cDAO.setError(true);
						e.printStackTrace();
						
					}
					
				       
					  
					  if(cDAO.isError()) {
						   req.setAttribute("message", "Error during Adding Please check Logs");
					   }else{
						   req.setAttribute("message", "Search Succesful!!!");
					   }
			 
		         
		    req.setAttribute("mailStatus", mailerDate);
		    getServletContext().getRequestDispatcher("/JSP/mailSender.jsp").forward(req, resp);
			}
	
 public int insertMailData(String subject, String  message,String  emailLists, String uploadFile01,String  uploadFile02, String uploadFile03,String  FileName01,String  FileName02, String  FileName03){
	 SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	    String[] emailList = emailLists.split(",");
		System.out.println(" Total Number of Emails " + emailList.length);
		int size =  emailList.length /22 +1 ;// + 1;
		final int maxEmailList = 22;
		String[] emailBreakUp = new String[size];
		System.out.println(" *** Size " + size);
		int counter =0, listVar = 0;
		String emailsAdd = null ;// "srinidhi.mc@gmail.com";
		
		for(int i =1; i< emailList.length; i++){
			
			if((i % maxEmailList == 0 && i > 1) ) {
				 System.out.println(" its 22 -- " + i + " counter -- " + counter);
				 System.out.println(emailsAdd);
				 emailBreakUp[counter] = emailsAdd;
				 emailsAdd ="";
				 counter++;
			 }else if (i ==  emailList.length -1 ){
				 emailBreakUp[counter] = emailsAdd;
				 System.out.println("Total Size all mails " + i + " counter " + counter);
				 System.out.println(emailsAdd);
				 emailsAdd ="";
				 
			 } else {
			   // Add Email to Variable
				 emailsAdd +=  "," +emailList[i];
			 }	
		}
			
		
		/*
		 
		 for(int i =0; i< emailList.length; i++){
			counter++;
			if( (listVar +1 )== size){
				if (counter == 1) {
					emailsAdd = emailList[i] ;
				}else if(i== (emailList.length -1)){
					emailsAdd +=  "," +emailList[i];
					emailBreakUp[listVar] = emailsAdd;		
			   }else {
				    emailsAdd +=  "," +emailList[i];
				}
				
			}else if(counter <= 22  ){ //&& i != (emailList.length -1)
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
		  
		 */
		
		String filePathAppended = uploadFile01+ ";" + uploadFile02 + ";" + uploadFile03;
		String fileNamesAppended = FileName01 + ";" + FileName02 +";" + FileName03 ;
		for(String str: emailBreakUp ) {
			 System.out.println(" insertMailData:: Break Up Email is  " + str);
		   String  sql =" INSERT INTO KM.MAIL_INFO (SUBJECT,MODIFIED_DATE,MAILER_LIST,CREATE_DATE,BODY,ATTACHMENT_PATH,ATTACHMENT_FILE) VALUES ( " 
			        		+ "'" + subject + "','" + sdf.format(new Date()) + "','"  + str + "','"   +  sdf.format(new Date()) + "','" + message + "','"
				            +  filePathAppended + "','" + fileNamesAppended +"')";
		   controllerDAO cDAO = new controllerDAO();
		   cDAO.addUser(sql);
		   System.out.println(" insertMailData::run --> SQL " + sql);
		  
		}
		
	 return size;	
 }
   
	
	
}
