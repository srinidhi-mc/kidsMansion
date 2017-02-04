package kidsMansion;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class dailyReportServlet extends HttpServlet {

	/**
	 * 
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
		String[] nursery = {"noneT","noneA" ,"ntwoT","ntwoA","nthreeT","nthreeA","nfourT","nfourA","nfiveT","nfiveA","nSixT","nSixA"};
		String[] lkg = {"LoneT","LoneA" ,"LtwoT","LtwoA","LthreeT","LthreeA","LfourT","LfourA","LfiveT","LfiveA","LSixT","LSixA","LSeveLT","LSeveLA","LEightT","LEightA"};
		String[] ukg ={"UoneT","UoneA" ,"UtwoT","UtwoA","UthreeT","UthreeA","UfourT","UfourA","UfiveT","UfiveA","USixT","USixA","USeveUT","USeveUA"};//,"UEightT","UEightA"
		 System.out.println("inside Do Post dailyReportServlet ");
		 String classValue = req.getParameter("type");
		 String reportdate =req.getParameter("reportdate");
		 if("PG".equalsIgnoreCase(classValue)){
			 generateReport(reportdate, classValue,playGroup, req, "Play Group" ) ;
		 }else if ("NURSERY".equalsIgnoreCase(classValue)){
			 generateReport(reportdate, classValue,nursery, req, "NURSERY" ) ;
		 }else if ("L.K.G".equalsIgnoreCase(classValue)){
			 generateReport(reportdate, classValue,lkg, req, "L.K.G" ) ;
		 } else if ("U.K.G".equalsIgnoreCase(classValue)){
			 generateReport(reportdate, classValue,ukg, req, "U.K.G" ) ;
		 }		 
		
		 req.setAttribute("reportDate", reportdate);
		 getServletContext().getRequestDispatcher("/JSP/dailyReportTemplate.jsp").forward(req, resp);
	}

	

 public static void generateReport(String reportdate, String classValue, String[] classDetails, HttpServletRequest req , String classString ){
	 StringBuilder sb = new StringBuilder();
	 int counter = 1;
	 String subject = classString +" Daily Report " + reportdate;
	String sql = "select count(*) Counter from DAILY_REPORT where SEND_DATE ='" + reportdate + "'  AND  ( (TO_SEND =0 AND STATUS = 0) OR (TO_SEND =1 AND STATUS = 0) OR (TO_SEND =0 AND STATUS = 1) OR (TO_SEND =0 AND STATUS = -1)) AND SUBJECT LIKE '%" + classString+ "%' AND DELETED = 0";
		System.out.println("generateReport:SQL " + sql);
	   if( new controllerDAO().returnPaymentId(sql) > 0 ){
		   req.setAttribute("message", "Report is already generated, Please delete the existing report from <I>Daily Report Submit </I> and Resubmit.");
	   } else {
		
		sb.append("<html> <body> <table border='1' width='100%' > <tr width = '100%'> <td align = 'center'>   <Font Face = 'Verdana' size = '4' color = 'Navy'>KIDS  </Font>  <Font Face = 'Verdana' size = '4' color = 'Green'>MANSION  </Font> <br>");
		sb.append(" " + subject);
		sb.append(" </td>  </tr> </table> <table border = '1'   width='100%'> <tr>  <th>Time</th>   <th> Activity</th> </tr>  ");
		 for(String reqParam: classDetails){
			 if(req.getParameter(reqParam)!= null && req.getParameter(reqParam)!= "" ){
				 
				 if(counter % 2 != 0) {
					 sb.append(" <tr> <td> " + req.getParameter(reqParam) + " </td> " );
			     }else{
			    	 sb.append("<td> " + req.getParameter(reqParam) + " </td> </tr> " );
			     }
		 }
			 counter++;
	 }
	    sb.append("</table> <br> <b>Note:</b>  Daily Report is sent to entire class even if the child is absent. This helps to know the activity performed for the day.");
		sb.append("</body> </html>");
	    System.out.println("dailyReportServlet " + sb.toString());
	  
	  sql = "Select * from email_id where CLASS = '" + classValue.toUpperCase() + "';";
	  System.out.println(" dailyReportServlet:doPost: sql --" + sql);
	  controllerDAO cDAO = new controllerDAO();
	  ResultSet  rs =  cDAO.getResult(sql.toString());
	  List<emailDetails> emailDetail = new ArrayList<emailDetails>();
	//  emailDetails emailDet = new emailDetails();
	  try{
		  while(rs.next()){
			  emailDetails emailDet = new emailDetails();
			  emailDet.setCLASS(rs.getString("CLASS"));
			  emailDet.setEMAIL_ID(rs.getString("EMAIL_ID"));
			  emailDet.setSEND_TIME(rs.getString("SEND_TIME"));
			  emailDetail.add(emailDet);
			  emailDet = null;
		  }
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
 }
	
