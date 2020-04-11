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
	private mailThreadExecutor mTE = null;
	
	/**
	 * TO_SEND : Report Creation Default 0 when Report submitted will set to 1
	 * DELETED : Deleted report from the Daily Report Submit Option
	 * STATUS:  Default 0 when successfully sent 1 and failed -1 
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
				
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		       String type = req.getParameter("type"); 
		       String ids = req.getParameter("id");
		       String selectDate = (String)req.getParameter("reportdate");
		       /*List<mailerTO> mailerDate = new ArrayList<mailerTO>();*/
		       List<DailyReportTO> dailReportTOList = new ArrayList<DailyReportTO>();
		       if(type.equalsIgnoreCase("fetch")){
		    	   dailReportTOList =  new mailSenderServlet().fetchData(selectDate);
				   
		       }else if (type.equalsIgnoreCase("deleted")){
		    	   boolean firstEntry = true;
		    	   StringBuilder sb = new StringBuilder();
		    	   sb.append("Update KM.DAILY_REPORT SET DELETED = 1, UPDATED_DATE = NOW() where ID IN (" );
		    	   if(ids != null && !ids.equalsIgnoreCase("")){
		    		   String[] idDetail = ids.split(",");
		    		   for(String idd: idDetail){
		    			   if(!idd.equalsIgnoreCase("0") && !firstEntry){
		    				   sb.append(", " + idd);
		    			   }else if(firstEntry){
		    				   sb.append(idd);
		    				   firstEntry = false;
		    			   }
		    		   }
		    		   sb.append(")");
		    		   controllerDAO cDAO = new controllerDAO();
		    		   cDAO.addUser(sb.toString());
		    	   }
		    	   dailReportTOList =  new mailSenderServlet().fetchData(selectDate);
		       
		       }else if(type.equalsIgnoreCase("submit")){
		    	    
		    	   if(mTE != null && mTE.isAlive()){
		    		   req.setAttribute("message", "Mail sending in progress, please resubmit after completion");
		    	   }else{
			    	   boolean firstEntry = true;
			    	   StringBuilder sb = new StringBuilder();
			    	   mTE = new mailThreadExecutor(selectDate, ids);
			    	   sb.append("Update KM.DAILY_REPORT SET TO_SEND = 1,STATUS = 0, UPDATED_DATE = NOW() where ID IN (" );
			    	   if(ids != null && !ids.equalsIgnoreCase("")){
			    		   String[] idDetail = ids.split(",");
			    		   for(String idd: idDetail){
			    			   if(!idd.equalsIgnoreCase("0") && !firstEntry){
			    				   sb.append(", " + idd);
			    			   }else if(firstEntry){
			    				   sb.append(idd);
			    				   firstEntry = false;
			    			   }
			    		   }
			    		 sb.append(")");
			    	     controllerDAO cDAO = new controllerDAO();
		    		    cDAO.addUser(sb.toString());
			    	   	}
		    	   	  mTE.start();
			          System.out.println(" *** Task Completed **** "); 
		    	  }
		    	   dailReportTOList =  new mailSenderServlet().fetchData(selectDate);
		      }
		      req.setAttribute("reportDate", selectDate);
		      if(type.equalsIgnoreCase("List") ){     
		    	    req.setAttribute("dailReportTOList", dailReportTOList);
				    getServletContext().getRequestDispatcher("/JSP/dailyReportTemplate.jsp").forward(req, resp);
		       }else{
		    	    req.setAttribute("dailReportTOList", dailReportTOList);
				    getServletContext().getRequestDispatcher("/JSP/dailyReportStatus.jsp").forward(req, resp);
		     }
	}
	
 /*public int insertMailData(String subject, String  message,String  emailLists, String uploadFile01,String  uploadFile02, String uploadFile03,String  FileName01,String  FileName02, String  FileName03){
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
   
	*/
	



public List<DailyReportTO> fetchData ( String selectDate){
	List<DailyReportTO> dailReportTOList = new ArrayList<DailyReportTO>();
	   String sql = "select * from KM.DAILY_REPORT where SEND_DATE = '"+selectDate +"' order by TIME" ;
	   controllerDAO cDAO = new controllerDAO();
	 
	  ResultSet rs = cDAO.getResult(sql);
	  try {
		while(rs.next()){
			DailyReportTO dailReportTO = new DailyReportTO();
			dailReportTO.setCONTENT( rs.getString("CONTENT"));
			dailReportTO.setID(rs.getInt("ID"));
			dailReportTO.setSTATUS(rs.getInt("STATUS"));
			dailReportTO.setSEND_DATE(rs.getString("SEND_DATE"));
			dailReportTO.setSUBJECT(rs.getString("SUBJECT"));
			dailReportTO.setTIME(rs.getString("TIME"));
			dailReportTO.setTO_SEND(rs.getInt("TO_SEND"));
			dailReportTO.setDELETED(rs.getInt("DELETED"));
			dailReportTOList.add(dailReportTO);
			//dailReportTOList.add(dailReportTO);
			
		  }
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	
	return dailReportTOList ;
}

}