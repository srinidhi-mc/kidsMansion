package kidsMansion;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.sun.xml.internal.ws.util.StringUtils;

public class dailyReportServlet extends HttpServlet {

	/**
	 * TO_SEND : Report Creation Default 0 when Report submitted will set to 1
	 * DELETED : Deleted report from the Daily Report Submit Option
	 * STATUS:  Default 0 when successfully sent 1 and failed -1 
	 */
	private static final long serialVersionUID = -5467734113232851687L;

	public  static void initialize() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		doPost(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String[] playGroup = {"poneT","poneA" ,"ptwoT","ptwoA","pthreeT","pthreeA","pfourT","pfourA","pfiveT","pfiveA","pSixT","pSixA"};
		String[] nursery = {"noneT","noneA" ,"ntwoT","ntwoA","nthreeT","nthreeA","nfourT","nfourA","nfiveT","nfiveA","nSixT","nSixA","nSevenT","nSevenA"};
		String[] lkg = {"LoneT","LoneA" ,"LtwoT","LtwoA","LthreeT","LthreeA","LfourT","LfourA","LfiveT","LfiveA","LSixT","LSixA","LSevenT","LSevenA","LEightT","LEightA"};
		String[] ukg ={"UoneT","UoneA" ,"UtwoT","UtwoA","UthreeT","UthreeA","UfourT","UfourA","UfiveT","UfiveA","USixT","USixA","USevenT","USevenA"};//,"UEightT","UEightA"
		String[] first ={"FoneT","FoneA" ,"FtwoT","FtwoA","FthreeT","FthreeA","FfourT","FfourA","FfiveT","FfiveA","FSixT","FSixA","FSevenT","FSevenA"};
		String[] second  ={"SoneT","SoneA" ,"StwoT","StwoA","SthreeT","SthreeA","SfourT","SfourA","SfiveT","SfiveA","SSixT","SSixA","SSevenT","SSevenA"};
		 System.out.println("inside Do Post dailyReportServlet ");
		 String classValue = req.getParameter("type");
		 String reportdate =req.getParameter("reportdate");
		 String isFetchRptData = req.getParameter("isFetchRptData");
		 System.out.println("classValue " + classValue + " reportdate " + reportdate);
		 if(isFetchRptData != null && isFetchRptData.equalsIgnoreCase("true")){
		   List<String> DataSet =  fetchReportData(reportdate, classValue, req);
		   req.setAttribute("fetchReportData", DataSet);
		 } else  if("PG".equalsIgnoreCase(classValue)){
			 generateReport(reportdate, classValue,playGroup, req, "Play Group" ) ;
		 }else if ("NURSERY".equalsIgnoreCase(classValue)){
			 generateReport(reportdate, classValue,nursery, req, "NURSERY" ) ;
		 }else if ("LKG".equalsIgnoreCase(classValue)){
			 generateReport(reportdate, classValue,lkg, req, "LKG" ) ;
		 } else if ("UKG".equalsIgnoreCase(classValue)){
			 generateReport(reportdate, classValue,ukg, req, "UKG" ) ;
		 } else if ("1-STD".equalsIgnoreCase(classValue)){
			 generateReport(reportdate, classValue,first, req, "1-STD" ) ;
		 } else if ("2-STD".equalsIgnoreCase(classValue)){
			 generateReport(reportdate, classValue,second, req, "2-STD" ) ;
		 }	 
		 req.setAttribute("reportDate", reportdate);
		 getServletContext().getRequestDispatcher("/JSP/dailyReportTemplate.jsp").forward(req, resp);
	}

	

 public static void generateReport(String reportdate, String classValue, String[] classDetails, HttpServletRequest req , String classString ){
	 StringBuilder sb = new StringBuilder();
	 int counter = 1;
	 String subject = classString +" Daily Report " + reportdate;
	String sql = "select count(*) Counter from DAILY_REPORT where SEND_DATE ='" + reportdate + "'  AND  ( (TO_SEND =0 AND STATUS = 0) OR (TO_SEND =1 AND STATUS = 0) OR (TO_SEND =1 AND STATUS = 1) OR (TO_SEND =0 AND STATUS = -1)) AND SUBJECT LIKE '%" + classString+ "%' AND DELETED = 0";
		System.out.println("generateReport:SQL " + sql);
	   if( new controllerDAO().returnPaymentId(sql) > 0 ){
		   req.setAttribute("message", "Either Report is sent or already Submitted for the selected Date, Please delete the existing report from <I>Daily Report Submit </I> and Resubmit.");
	   } else {
		
		sb.append("<html> <body> <table border='1'  > <tr> <td align = 'center'>   <Font Face = 'Verdana' size = '4' color = 'Navy'>KIDS  </Font>  <Font Face = 'Verdana' size = '4' color = 'Green'>MANSION  </Font> <br>");
		sb.append(" " + subject);
		sb.append(" </td>  </tr> <tr><td align = 'center'><b>Activity </b></td></tr>");
		 for(String reqParam: classDetails){
			 if(req.getParameter(reqParam)!= null && req.getParameter(reqParam)!= "" ){
				 sb.append("<tr> <td>" + req.getParameter(reqParam) + "</td></tr>" );
				/* if(counter % 2 != 0) {
					 sb.append("<tr><td>" + req.getParameter(reqParam) + "</td>" );
			     }else{
			    	 sb.append("<td>" + req.getParameter(reqParam) + "</td></tr>" );
			     }*/
		 }
			 counter++;
	 }
	    sb.append("</table> <br> <b>Note:</b>  Daily Report is sent to entire class even if the student is absent. <Br> This helps to know the activity performed for the day.");
		sb.append("</body> </html>");
	    System.out.println("dailyReportServlet " + sb.toString());
	    
	  Map<String, String> eMailTiming = new HashMap<String, String>();
	  eMailTiming.put("PG", "15.30");
	  eMailTiming.put("NURSERY", "15.32");
	  eMailTiming.put("LKG", "15.34");
	  eMailTiming.put("UKG", "15.38");
	  eMailTiming.put("1-STD", "15.40");
	  eMailTiming.put("2-STD", "15.50");
	  
	  
	  sql = "Select TRIM(EMAIL_1) 'EMAIL_1', TRIM(EMAIL_2)'EMAIL_2' from students where YEAR = 23 and ACTIVE = 1  AND CLASS = '" + classValue.toUpperCase() + "';";
	  System.out.println(" dailyReportServlet:doPost: sql --" + sql);
	  controllerDAO cDAO = new controllerDAO();
	  ResultSet  rs =  cDAO.getResult(sql.toString());
	  List<emailDetails> emailDetail = new ArrayList<emailDetails>();
	  emailDetails emailDet = new emailDetails();
	  StringBuffer emailString  = new StringBuffer();
	  try{
		  while(rs.next()){
			  if(rs.getString("EMAIL_1") != null &&  rs.getString("EMAIL_1") != "" &&  !rs.getString("EMAIL_1").equalsIgnoreCase("null")){
			      emailString.append(rs.getString("EMAIL_1"));
			      emailString.append(",");
			  }   
			  if(rs.getString("EMAIL_2") != null &&  rs.getString("EMAIL_2") != "" &&  !rs.getString("EMAIL_2").equalsIgnoreCase("null")){
				   emailString.append(rs.getString("EMAIL_2"));
				   emailString.append(",");
			  }else {
				  emailString.append(",");
			  }
			  
		  }
		  
		  emailDet.setCLASS(classValue.toUpperCase());
		  emailDet.setEMAIL_ID(emailString.toString());
		  emailDet.setSEND_TIME(eMailTiming.get(classValue.toUpperCase()));
		  emailDetail.add(emailDet);
		 rs.close();
		  for(emailDetails e : emailDetail ){
		   sql ="insert into KM.DAILY_REPORT (CONTENT,STATUS,SEND_DATE,CREATED_DATE,UPDATED_DATE,SUBJECT,MAIL_ID, TIME) values( '" + (sb.toString()).replaceAll("'", "''") +"' ,"
		  		+ "0,'"+ reportdate +"', now(),now(),'" + subject +"','" + e.getEMAIL_ID() +"', '" + e.getSEND_TIME() +"')";
		   System.out.println(" -- " + sql + " -- " + cDAO.addUser(sql));
		  }
		  /*boolean sent = new sendMailModified().sendMailContent(subject, "srinidhi.mc@gmail.com,srinidhi.chatrapathi@tarams.com", sb.toString());
		  System.out.println("Mail sent "+ sent);*/
		  
		  } catch(SQLException se){
			  se.printStackTrace();
		 
		  } catch(Exception e){
			  e.printStackTrace();
		  }
	  		req.setAttribute("message", "Successfully Submitted.");
	   }
	   	  req.setAttribute("classString", classString);
		  
	  }
 
 /* <html> <body> <table border='1' width='100%' > <tr width = '100%'> <td align = 'center'>  
  *  <Font Face = 'Verdana' size = '4' color = 'Navy'>KIDS  </Font>  
  *  <Font Face = 'Verdana' size = '4' color = 'Green'>MANSION  </Font> 
  *  <br> Play Group Daily Report 16-Jun-2017 </td>  </tr> </table> 
  *  <table border = '1'   width='100%'> 
  *  <tr>  <th>Time</th>   <th> Activity</th> </tr>  
  *   <tr> <td> 9:30 To 9:45 AM </td> <td> Circle Time(Prayer , National Anthem & Rhymes with action) </td> </tr> 
  *    <tr> <td> 9:45 to 10:00 AM </td> <td> Conversation with teachers and friends </td> </tr> 
  *     <tr> <td> 10:00 to 10:20 AM </td> <td> Free Play </td> </tr> 
  *      <tr> <td> 10:20 to 11:00 AM </td> <td> Oral- Introduction to Alphabet and numbers<BR> Fine Motor Skills- Touch and feel the wheat flour (Aata)
 </td> </tr>  
 <tr> <td> 11:00 to 11:20 AM </td> <td> Snacks Break </td> </tr> 
  <tr> <td> 11:30 to 11:50 AM </td> <td> Gross Motor Skill- Shake your body </td> </tr> </table> 
  <br> <b>Note:</b>  Daily Report is sent to entire class even if the child is absent. This helps to know the activity performed for the day.</body> </html>
 
  */
    public List<String> fetchReportData(String reportDate, String classString , HttpServletRequest req) {
    	List<String> returnReport = new ArrayList<String>();
    	if("PG".equalsIgnoreCase(classString)) classString = "Play Group";
    	String subject = classString +" Daily Report " + reportDate;
    	String reportData = null ;
    	String sql = "select count(*) Counter from DAILY_REPORT where SEND_DATE ='" + reportDate + "'  AND  STATUS != 1 AND SUBJECT LIKE '%" + subject+ "%' AND DELETED = 0";
		System.out.println("fetchReportData :SQL " + sql);
		
	   if( new controllerDAO().returnPaymentId(sql) <= 0 ){
		   req.setAttribute("message", "<B>ERROR: Report Does Not Exist </B>");
	   } else {
		   sql = "select * from DAILY_REPORT where SEND_DATE ='" + reportDate + "'  AND  STATUS != 1 AND SUBJECT LIKE '%" + subject+ "%' AND DELETED = 0";
		   ResultSet  rsExecute =  new controllerDAO().getResult(sql.toString());
    	    try {
				if(rsExecute.next())  reportData = rsExecute.getString("CONTENT");
				System.out.println(" reportData " + reportData);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    /* Iterate to fetch each data componenet */
    	    int startDataPoint =0, endDataPoint =0 ;
    	    String[] splitDataArray = reportData.split("<td>");
    	    
    	        	    
    	    for(int count = 1; count < splitDataArray.length; count++){
    	    	
    	    	returnReport.add(splitDataArray[count].substring(0, splitDataArray[count].indexOf("<")));
    	    	
    	    	//System.out.println(" --> " + splitDataArray[count].substring(0, splitDataArray[count].indexOf("<")));
    	    	
    	    }
    	    
    	    /*while ( reportData.indexOf("</td>")!= -1){
    	    	   System.out.println(reportData.indexOf("</td>"));
    	    	   startDataPoint = reportData.indexOf("<td>",startDataPoint);
    	    	   endDataPoint = reportData.indexOf("</td>", startDataPoint);
    	    	   System.out.println("reportData " + reportData);
    	    	   System.out.println(" startDataPoint --> " + startDataPoint + " --> " + endDataPoint);
    	    	   System.out.println(" data " + reportData.substring(startDataPoint, endDataPoint));
    	    	   
    	    	  reportData = reportData.substring(endDataPoint);
    	    			   
    	    }*/
    	}
	   System.out.println(" returnReport --> " + returnReport);
    	return returnReport;
    	
    }
 
 
 }
	
