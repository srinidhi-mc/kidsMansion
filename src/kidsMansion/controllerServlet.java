package kidsMansion;

import java.io.IOException;
import java.sql.ResultSet;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 *  This is a controller servlet
 */
public class controllerServlet extends HttpServlet {

	private static final long serialVersionUID = -993456507836868036L;
	private static String LINEBREAK = "\r\n";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		   doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String request = "Search";
		
		request= req.getParameter("type");
	    String active = req.getParameter("active");
	    String yearClass =  req.getParameter("yearClass");
	    String selList = null;
	//	String submtResp = req.getParameter("submtResp");
	    controllerDAO cdao = new controllerDAO();
		if(request.equalsIgnoreCase("Search")) selList = cdao.generateListBox("NA");
		
			
		if(request.equalsIgnoreCase("emailList")) {
			
			  StringBuffer emailString = new StringBuffer();
			  StringBuffer emailStringFinal = new StringBuffer();
			  emailStringFinal.append("<HTML> <BODY>");

			  String[] classes = { "PG","NURSERY","LKG","UKG","1-STD"};
			
			for (String class1 : classes) {

				String sql = "Select TRIM(EMAIL_1) 'EMAIL_1', TRIM(EMAIL_2)'EMAIL_2' from students where YEAR = 22 and ACTIVE = 1  AND CLASS = '"
						+ class1.toUpperCase() + "';";
				System.out.println(" dailyReportServlet:doPost: sql --" + sql);
				controllerDAO cDAO = new controllerDAO();
				ResultSet rs = cDAO.getResult(sql.toString());
				emailString.append("<BR>" + class1 + ":: ");
				//emailString.append(System.getProperty("line.separator"));
				
				try {
					while (rs.next()) {
						if (rs.getString("EMAIL_1") != null
								&& rs.getString("EMAIL_1") != ""
								&& !rs.getString("EMAIL_1").equalsIgnoreCase(
										"null")) {
							emailString.append(rs.getString("EMAIL_1"));
							emailString.append(",");
						}
						if (rs.getString("EMAIL_2") != null
								&& rs.getString("EMAIL_2") != ""
								&& !rs.getString("EMAIL_2").equalsIgnoreCase(
										"null")) {
							emailString.append(rs.getString("EMAIL_2"));
							emailString.append(",");
						} else {
							emailString.append(",");
						}

					}

				} catch (SQLException se) {
					se.printStackTrace();

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//emailString.append(System.getProperty("line.separator"));
				String t1 =emailString.toString().replaceAll("([\\,])\\1{2,}", "$1");
				String t2 = t1.replaceAll(",,", ",");
				String t3 = t2.replaceAll(";", ",");
				System.out.println(" ** tempemailString " + t3);
				emailStringFinal.append(t3);
			//	System.out.println("finalReceipientList " +  emailStringFinal);
				
				//emailStringFinal.append(wrap(emailStringFinal.toString(), 80));
		//		System.out.println("**** -->  " + wrap(emailStringFinal.toString(), 80));
				emailString.delete(0, emailString.length());
			}
			req.setAttribute("message", "Listing Emails..");
			req.setAttribute("emailDetails",emailStringFinal);
			
	  } else if(request != null && request.equalsIgnoreCase("searchSubmit")){
			 
			 String classValue = req.getParameter("group");
			 String searchString = req.getParameter("searchData");
		//	 String yearClass =  req.getParameter("year");
			 StringBuilder sql = new StringBuilder();
			 selList = new controllerDAO().generateListBox(classValue);
			 
			 if (classValue.equalsIgnoreCase("NETBANKING")){
				 sql.append( "Select STU.NAME, STU.CLASS,PD.PAID_DATE, PD.TOTAL, PD.COMMENTS from KM.STUDENTS STU INNER JOIN KM.PAYMENT_DETAILS PD ON STU.ID = PD.STUDENT_ID AND PD.COMMENTS LIKE '%" + searchString + "%'") ;
				 request = "NETBANKING";
			 } else if (classValue.equalsIgnoreCase("CHEQUE")){
				 sql.append( "Select NAME,CHEQUE_NO, CHEQUE_NAME, BRANCH, CITY, AMOUNT, STATUS, COMMENTS,DEPOSITED_DATE, CHEQUE_DATE FROM CHEQUES  WHERE  CHEQUE_NO LIKE '%" + searchString + "%' ORDER BY UPDATED_DATE DESC") ;
				 request = "CHEQUE";
				 
			 
			 } else if (classValue.equalsIgnoreCase("STUDENT")){
				   sql.append("SELECT STU.ID, STU.NAME, STU.CLASS, STU.EMAIL_1, STU.EMAIL_2, STU.YEAR"); 
				   sql.append(" FROM KM.STUDENTS STU  ");
					sql.append(" WHERE NAME like '%" + searchString + "%' and active = " + active + " and (year is null or year =" + yearClass + ")"); 
					if(searchString == null || searchString == "" || searchString.equalsIgnoreCase("null")) {
						sql.append(" and STU.CLASS <>'DayCare' ");
					}
					sql.append(" order by NAME asc"); 
			//  sql.append( " FROM KM.STUDENTS STU where NAME like '%" + searchString + "%' and active =" + active + " order by STU.NAME ") ;
			 
			 }else if(classValue.equalsIgnoreCase("DayCare")) {
				 sql.append("SELECT STU.ID, STU.NAME, STU.CLASS, STU.EMAIL_1, STU.EMAIL_2, STU.YEAR");
				 			 		// Enter only when Search String is provided.
			 		 if(searchString != null && searchString != "" && !searchString.equalsIgnoreCase("null")) {
			 			sql.append(", PD.TOTAL,PD.PAID_DATE ,PD.TYPE, PD.STATUS, PD.MONTH  FROM KM.STUDENTS STU INNER JOIN PAYMENT_DETAILS PD ON STU.ID = PD.STUDENT_ID  AND STU.CLASS ='" + classValue + "'");
						sql.append(" AND PD.MONTH LIKE '" + searchString + "%' and ACTIVE = " + active + "  and  PD.ID >4900 "); 
						sql.append(" order by STATUS, NAME asc");
					 }else{
						 sql.append(" FROM KM.STUDENTS STU  where STU.CLASS ='DayCare' and active = " + active +" order by  NAME asc");
					 }
			 	
			 		 
			 }else if(classValue.equalsIgnoreCase("DC_TO_SEND")) {
				 if(searchString != null && searchString != "" && !searchString.equalsIgnoreCase("null")) {
				 sql.append("SELECT S.ID, S.NAME, S.CLASS, S.EMAIL_1, S.EMAIL_2, S.YEAR");
			 	 sql.append(" from students s where UPPER(s.CLASS) = UPPER('DayCare') and s.ACTIVE = 1  and s.id and s.ID NOT IN ");
		         sql.append(" (select s.id from payment_details pd inner join students s on s.id = pd.STUDENT_ID and  UPPER(pd.CLASS) = UPPER('DayCare') and s.ACTIVE = 1 and  "); 
		         sql.append("  PD.MONTH LIKE '" + searchString + "%' and PD.ID >3001) "); 
		         sql.append(" order by NAME asc");
	       }

			 }else {
				 sql.append("SELECT STU.ID, STU.NAME, STU.CLASS, STU.EMAIL_1, STU.EMAIL_2, STU.YEAR");
				 if(searchString != null && searchString != ""  && !searchString.equalsIgnoreCase("null")) {
			 			sql.append(", PD.TOTAL,PD.PAID_DATE ,PD.TYPE, PD.STATUS, PD.MONTH  FROM KM.STUDENTS STU INNER JOIN PAYMENT_DETAILS PD ON STU.ID = PD.STUDENT_ID  AND STU.CLASS ='" + classValue + "'");
						sql.append(" AND PD.TERM LIKE '%" + searchString + "%' and YEAR = " + yearClass + " and active = " + active); 
						sql.append(" order by STATUS, NAME asc");
					 }else{
						 sql.append(" FROM KM.STUDENTS STU  where STU.CLASS ='" + classValue + "' and YEAR = "+ yearClass +  " and active = " + active + " order by  NAME asc");
					 }
			 }
			 
			 System.out.println(" controllerServlet:doPost::classValue---  " + classValue + " searchString  -- " + searchString);
			 System.out.println(sql);
			 		 
			 
			 controllerDAO cDAO = new controllerDAO();
			 ResultSet  rs =  cDAO.getResult(sql.toString());
			 req.setAttribute("resultSet", rs);
			 req.setAttribute("classValue", classValue);
			 req.setAttribute("searchString", searchString);
			 		 
			 if(cDAO.isError()) {
				   req.setAttribute("message", "Error during Searching Please check Logs");
			   }else{
				   req.setAttribute("message", "Search Completed!!!");
			   }
			   
			
		 }else if(request != null && request.equalsIgnoreCase("addSubmit")){
			 
			 String name = req.getParameter("name");
			 String stuClass = req.getParameter("class");
			 String email1 = req.getParameter("email1");
			 String email2 = req.getParameter("email2");
			 String telephone1 = req.getParameter("telephone1");
			 String telephone2 = req.getParameter("telephone2");
			 
			 StringBuilder sql = new StringBuilder();
			 StringBuilder values = new StringBuilder();
			 sql.append("INSERT INTO KM.STUDENTS ( " );
			 values.append (" VALUES ( ");
			 if(name != null && name != ""){ sql.append(" NAME ");  values.append("UPPER('" + name + "')") ;}
			 if(stuClass != null && stuClass != "") { sql.append(", CLASS"); values.append( ",'" +stuClass + "'" ); };
			 if(email1 != null && email1 != "") { sql.append(", EMAIL_1"); values.append(",'" + email1 +"'" ); };
			 if(email2 != null && email2 != "") { sql.append(", EMAIL_2"); values.append(",'" + email2+"'" ); };
			 if(telephone1 != null && telephone1 != ""){ sql.append(", TELEPHONE_1"); values.append(",'" + telephone1 + "'"); };
			 if(telephone2 != null && telephone2 != "") { sql.append(", TELEPHONE_2 "); values.append( ",'" + telephone2 + "'" ); };
			 if ( !stuClass.equalsIgnoreCase("DayCare")) { sql.append(", YEAR "); values.append( "," + yearClass + "" ) ;};
			 sql.append(", active "); values.append( "," + active + "" );
			 sql.append(", CREATED_DATE"); values.append(", NOW()");
			 sql.append(", UPDATED_DATE"); values.append(", NOW()");
			 sql.append (" )");
			 values.append(" )");
			 
			 System.out.println(" name " + name +  " stuClass " +  stuClass + " email1 " + email1 +
					 " email2 " + email2 + " telephone1 " + telephone1 + " telephone2 " + telephone2);
			 sql.append(values);
			 
			  System.out.println( sql);
			  controllerDAO cDAO = new controllerDAO();
			   int res = cDAO.addUser(sql.toString());
			   System.out.println("Number of Rows inserted = " + res);
			   if(cDAO.isError()) {
				   req.setAttribute("message", "Error during Adding Please check Logs");
			   }else{
				   req.setAttribute("message", "Record Inserted Succesfully!!!");
			   }
			   request = "Add";
	    
		 
		 } else if(request != null && request.equalsIgnoreCase("fetchStudent")){
			 
			 System.out.println("  controllerServlet:doPost::fetchStudent " + request + " studentID " + req.getParameter("id") );
			 
			// String searchString = req.getParameter("searchString");
			 
			 String sql = "select * from KM.STUDENTS where ID =" + req.getParameter("id") ;
			 			 
			 controllerDAO cDAO = new controllerDAO();
			  ResultSet  rs =  cDAO.getResult(sql);
			  if(cDAO.isError()) {
				   req.setAttribute("message", "Error during Adding Please check Logs");
			   }else{
				   req.setAttribute("message", "Search Succesful!!!");
			   }
			  req.setAttribute("resultSet", rs);
			  //Sending back the month searched for Pending fees.
			  req.setAttribute("studentId", req.getParameter("id"));
			
		 } else if(request != null && request.equalsIgnoreCase("update")){
			 
			 System.out.println("  controllerServlet:doPost::update " + request + " studentID " + req.getParameter("id") );
			 
			 String name = req.getParameter("name");
			 String stuClass = req.getParameter("class");
			 String email1 = req.getParameter("email1");
			 String email2 = req.getParameter("email2");
			 String telephone1 = req.getParameter("telephone1");
			 String telephone2 = req.getParameter("telephone2");
			 String id = req.getParameter("id");
			 
			 StringBuilder sql = new StringBuilder();
			 
			 sql.append("UPDATE KM.STUDENTS " );
			
			 if(name != null && name != ""){ sql.append(" SET NAME = " + "UPPER('" + name + "'),"); };
			 if(stuClass != null && stuClass != ""){ sql.append(" CLASS = '" + stuClass + "',"); };
			 if(email1 != null && email1 != "") { sql.append("  EMAIL_1 = '" + email1 +"' ," ); };
			 if(email2 != null && email2 != "") { sql.append("  EMAIL_2 = '" + email2+"'" ); };
			 if(telephone1 != null && telephone1 != ""){ sql.append(", TELEPHONE_1= '" + telephone1 + "'"); };
			 if(telephone2 != null && telephone2 != "") { sql.append(", TELEPHONE_2= '" + telephone2 + "'" ); };
			 if (!stuClass.equalsIgnoreCase("DayCare") )sql.append(", YEAR  =" + yearClass + "" );
			 sql.append(", active = " + active  );
			 sql.append(", UPDATED_DATE = NOW() " );
			 sql.append (" where id = " + id) ;
			
			 
			 System.out.println("  controllerServlet:doPost::update " + sql);
			  
			 // String searchString = req.getParameter("searchString");
			 
			// String sql = "select * from KM.STUDENTS where ID =" + req.getParameter("id") ;
			 			 
			 controllerDAO cDAO = new controllerDAO();
			  int  rs1 =  cDAO.addUser(sql.toString());
			  
			  System.out.println("  controllerServlet:doPost::update Rows Updated " + rs1);
			  if(cDAO.isError()) {
				   req.setAttribute("message", "Error during Adding Please check Logs");
			   }else{
				   req.setAttribute("message", "Update Succesful!!!");
			   }
			//  req.setAttribute("resultSet", rs);
			  //Sending back the month searched for Pending fees.
			//  req.setAttribute("searchString", searchString);
			
		 } else if (request != null && request.equalsIgnoreCase("DCMISSING")){
			 
			 System.out.println("  controllerServlet:doPost::update " + request + " studentID " + req.getParameter("id") ); 
		 
			 
		 }
		 
		  req.setAttribute("type", request);
		  req.setAttribute("selList", selList);
		  System.out.println("controllerServlet:doPost::active " + active + " yearClass " + yearClass);
		  req.setAttribute("active", active);
		  req.setAttribute("yearClass", yearClass);
		  System.out.println("selList " + selList);
		  getServletContext().getRequestDispatcher("/JSP/content.jsp").forward(req, resp);
   	}
	
	
	public static String wrap(String string, int lineLength) {
	    StringBuilder b = new StringBuilder();
	    for (String line : string.split(Pattern.quote(LINEBREAK))) {
	        b.append(wrapLine(line, lineLength));
	    }
	    System.out.println("wrap " + b.toString());
	    return b.toString();
	}

	private static String wrapLine(String line, int lineLength) {
	    if (line.length() == 0) return LINEBREAK;
	    if (line.length() <= lineLength) return line + LINEBREAK;
	    String[] words = line.split(" ");
	    StringBuilder allLines = new StringBuilder();
	    StringBuilder trimmedLine = new StringBuilder();
	    for (String word : words) {
	        if (trimmedLine.length() + 1 + word.length() <= lineLength) {
	            trimmedLine.append(word).append(" ");
	        } else {
	            allLines.append(trimmedLine).append(LINEBREAK);
	            trimmedLine = new StringBuilder();
	            trimmedLine.append(word).append(" ");
	        }
	    }
	    if (trimmedLine.length() > 0) {
	        allLines.append(trimmedLine);
	    }
	    allLines.append(LINEBREAK);
	    return allLines.toString();
	}
	
}
