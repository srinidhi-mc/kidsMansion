package kidsMansion;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.mail.search.SentDateTerm;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class paymentServlet extends HttpServlet{
	
	private static final long serialVersionUID = 2389785373394101823L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		  doPost(req,resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 String id = req.getParameter("id");
		 String type = req.getParameter("type");
		 //String insStatus = req.getParameter("insStatus");
		 String sendMail = req.getParameter("sendMail");
		 String searchData = (String)req.getParameter("searchData");
		 String active = req.getParameter("active");
		 String yearClass = req.getParameter("yearClass");
		 boolean mailSent = false;
		 String paymenttype = null;
		 System.out.println("paymentServlet:doPost id " + id + " type " + type + " searchData " + searchData);
		 if(type.equalsIgnoreCase("fetchPayment")){
			 
			 //insert/update data prior loading
			// boolean isAddNew = Boolean.valueOf((String)req.getParameter("addNew"));
			 String isNew = (String)req.getParameter("isNew");
			 boolean isPayStatus = Boolean.valueOf((String)req.getParameter("payStatus"));
			 int STATUS=0;
			// String COMMENT = req.getParameter("COMMENTS");
			
			 
			 System.out.println( "paymentServlet:doPost  isNew " + isNew + " payStatus " + isPayStatus + " searchData "  + searchData + " sendMail " + sendMail);
			
			 // System.out.println( "paymentServlet:doPost  COMMENTS " + COMMENT );
			 if(isNew != null) {
				 int STUDENT_ID=0 , BOOK=0 ,ACTIVITY=0 , TERM_FEES=0 , ADMISSION=0 , TRANSPORT=0 , DAYCARE=0 , ART=0 , DANCE=0 , SNACKS=0 , LUNCH=0 , BF=0 , ADHOC=0 , OTHERS=0 ,UNIFORM = 0,TOTAL;
				 String CLASS="",TERM="",TYPE="",PAID_DATE="",MONTH="",COMMENTS="";
				 int  counter = Integer.valueOf(req.getParameter("counter" ));
				 CLASS =  req.getParameter("CLASS");
				
				 
				 
				 System.out.println( "paymentServlet:doPost  counter " + counter);
				 
				 if((String)req.getParameter("id" )!= "")  STUDENT_ID = Integer.valueOf((String)req.getParameter("id" ));
				 if((String)req.getParameter("TRANSPORT"+counter )!= "")  TRANSPORT = Integer.valueOf((String)req.getParameter("TRANSPORT"+counter ));
				 if(!CLASS.equalsIgnoreCase("DAYCARE")) {
					 if((String)req.getParameter("TERM"+counter )!= "")  TERM =  req.getParameter("TERM"+counter );
					 if((String)req.getParameter("BOOK"+counter )!= "")  BOOK = Integer.valueOf((String)req.getParameter("BOOK"+counter ));
					 if((String)req.getParameter("ACTIVITY"+counter )!= "")  ACTIVITY = Integer.valueOf((String)req.getParameter("ACTIVITY"+counter ));
					 if((String)req.getParameter("TERM_FEES"+counter )!= "")  TERM_FEES = Integer.valueOf((String)req.getParameter("TERM_FEES"+counter ));
					 if((String)req.getParameter("ADMISSION"+counter )!= "")  ADMISSION = Integer.valueOf((String)req.getParameter("ADMISSION"+counter ));
					 if((String)req.getParameter("UNIFORM"+counter )!= "")  UNIFORM = Integer.valueOf((String)req.getParameter("UNIFORM"+counter ));
				 } 
				// System.out.println(" paymentServlet:CLASS " + CLASS);
				 if( CLASS.equalsIgnoreCase("DAYCARE")) {
					 if(req.getParameter("DAYCARE"+counter ) != null && req.getParameter("DAYCARE"+counter )!= "")  DAYCARE = Integer.valueOf((String)req.getParameter("DAYCARE"+counter ));
					 if(req.getParameter("ART"+counter ) != null && req.getParameter("ART"+counter )!= "")  ART = Integer.valueOf((String)req.getParameter("ART"+counter ));
					 if(req.getParameter("DANCE"+counter ) != null && req.getParameter("DANCE"+counter )!= "")  DANCE = Integer.valueOf((String)req.getParameter("DANCE"+counter ));
					 if(req.getParameter("SNACKS"+counter ) != null && req.getParameter("SNACKS"+counter )!= "")  SNACKS = Integer.valueOf((String)req.getParameter("SNACKS"+counter ));
					 if(req.getParameter("LUNCH"+counter ) != null && req.getParameter("LUNCH"+counter )!= "")  LUNCH = Integer.valueOf((String)req.getParameter("LUNCH"+counter ));
					 if(req.getParameter("BREAKFAST"+counter ) != null && req.getParameter("BREAKFAST"+counter )!= "")  BF = Integer.valueOf((String)req.getParameter("BREAKFAST"+counter ));
					 if(req.getParameter("ADHOC"+counter ) != null && req.getParameter("ADHOC"+counter )!= "")  ADHOC = Integer.valueOf((String)req.getParameter("ADHOC"+counter ));
					 if(req.getParameter("OTHERS"+counter ) != null && req.getParameter("OTHERS"+counter )!= "")  OTHERS = Integer.valueOf((String)req.getParameter("OTHERS"+counter ));
				 }
				 if((String)req.getParameter("TOTAL"+counter )!= "")  TOTAL = Integer.valueOf((String)req.getParameter("TOTAL"+counter ));
				
				 //Payment ID will be -1 for new ones
				 int ID = Integer.valueOf((String)req.getParameter("isNew" ));
				
				// if( req.getParameter("STATUS " )!= null) STATUS =  req.getParameter("STATUS " );
				 if( req.getParameter("PAYMENT_TYPE"+counter )!= null) TYPE =  req.getParameter("PAYMENT_TYPE"+counter );
				 if( req.getParameter("PAID_DATE"+counter )!= null) PAID_DATE =  req.getParameter("PAID_DATE"+counter );
				 if( req.getParameter("MONTH"+counter )!= null) MONTH =  req.getParameter("MONTH"+counter );
				 if( req.getParameter("COMMENTS")!= null) COMMENTS =  req.getParameter("COMMENTS");
				 if(isPayStatus) STATUS = 1;
                 // Setting paymenttype value for cheque identification
				 paymenttype = TYPE;

				 if(isNew.equals("-1")){
					 
					 TOTAL = BOOK+ACTIVITY+TERM_FEES+ADMISSION+TRANSPORT+DAYCARE+ART+DANCE+SNACKS+LUNCH+BF+ADHOC+OTHERS+UNIFORM;
					 String sql = "INSERT INTO KM.PAYMENT_DETAILS(STUDENT_ID,BOOK,ACTIVITY,TERM_FEES,ADMISSION,TRANSPORT,DAYCARE,ART,DANCE,SNACKS,LUNCH,BF,ADHOC,OTHERS,TOTAL,CLASS,TERM,STATUS,TYPE,PAID_DATE,MONTH,COMMENTS, UNIFORM, CREATED_DATE, UPDATED_DATE) VALUES ("
							       + STUDENT_ID + ", " + BOOK + ", "+ ACTIVITY + ", "+ TERM_FEES + ", "+ ADMISSION + ", "+ TRANSPORT + ", "+ DAYCARE + ", "+ ART + ", "+ DANCE + ", "+ SNACKS + ", "+ LUNCH + ", "+ BF + ", "+ ADHOC + ", "+ OTHERS + ", "+ TOTAL  
							       + ",'" + CLASS + "'" + ","+ "'" + TERM + "'" + ","+ "'" + STATUS + "'" + ","+ "'" + TYPE + "'" + ","+ "'" + PAID_DATE + "'" + ","+ "'" + MONTH + "'" + ","+ "'" + COMMENTS + "', " + UNIFORM  +", NOW(), NOW() )" ;
							System.out.println(" paymentServlet:doPost ::sql --> " + sql )   ;           
					 
							controllerDAO cDAO = new controllerDAO();
					        int res = cDAO.addUser(sql);
					        System.out.println(" paymentServlet:doPost: Number of Rows inserted = " + res);
					   
				 }else{
					    TOTAL = BOOK+ACTIVITY+TERM_FEES+ADMISSION+TRANSPORT+DAYCARE+ART+DANCE+SNACKS+LUNCH+BF+ADHOC+OTHERS+UNIFORM;
					    String sql = "UPDATE KM.PAYMENT_DETAILS SET BOOK=" + BOOK + ", ACTIVITY = " + ACTIVITY + ", TERM_FEES = " + TERM_FEES  + ", ADMISSION = " + ADMISSION + ", TRANSPORT = " + TRANSPORT + ", DAYCARE = " + DAYCARE + ", ART = "
					                  + ART + ", DANCE = " + DANCE + ", SNACKS = " + SNACKS + ", LUNCH = " + LUNCH + ", BF = " + BF + ", ADHOC = " + ADHOC + ", OTHERS = " + OTHERS + ", TOTAL="+ TOTAL + ", TYPE = '" + TYPE + "'" +
					                  ", CLASS =" +"'" + CLASS +"' ," + " TERM =" +"'" + TERM +"' ," +  " STATUS =" +"'" + STATUS +"' ," +  " PAID_DATE =" +"'" + PAID_DATE +"' ," + " MONTH =" +"'" + MONTH +"' ," + " COMMENTS =" +"'" + COMMENTS +"', UNIFORM=" + UNIFORM +" , UPDATED_DATE = NOW() where ID=" + Integer.valueOf(isNew) ;
					    
					       System.out.println(" paymentServlet:doPost ::sql --> " + sql )   ;  
					       controllerDAO cDAO = new controllerDAO();
				           int res = cDAO.addUser(sql);
				          System.out.println(" paymentServlet:doPost: Number of Rows Updated = " + res);
				 }
			 
			 }
			 
			 String sql = "SELECT NAME, CLASS, ID, EMAIL_1, EMAIL_2 FROM KM.STUDENTS WHERE ID =" +id ;
			 String studentName = null;
			 String studentClass= null;
			 String email1= null, email2= null;
			 int studentId =0;
			 controllerDAO cDAO = new controllerDAO();
			 ResultSet rs = cDAO.getResult(sql);
			 ResultSet rs1 = null ;
			
			   try {
					if(rs.next()) {
					  studentName = rs.getString("NAME");
					  studentClass = rs.getString("CLASS");
					  studentId = rs.getInt("ID");
					  email1 = rs.getString("EMAIL_1");
					  email2= rs.getString("EMAIL_2");
				    }
					
					if(isNew != null ) {
					 sql = "select sentmail from payment_details where payment_details.ID =" + Integer.valueOf(isNew) ;
					 System.out.println("paymentServlet:doPost::mail Sent " + sql);
					} 
					 if(sendMail != null && sendMail.equalsIgnoreCase("true") &&  cDAO.isMailSent(sql)==0 &&   STATUS == 0  ){
						   sql = "select max(id) from payment_details where payment_details.STUDENT_ID =" + studentId ;
						   int paymentId = cDAO.returnPaymentId(sql);
						   StringBuilder emails = new StringBuilder();
						   if(email1 != null && !email1.equalsIgnoreCase(""))  emails.append(email1) ;
						   if(email2 != null && !email2.equalsIgnoreCase("") ){
							   System.out.println("Inside --" + email2 + " --" + email1);
							    emails.append(",");
							    emails.append(email2) ;
						   }
						   
						   System.out.println(" paymentServlet:doPost::Email Send to... " + emails.toString() + " payment Id " + paymentId + " studentId " + studentId );
						   mailSent =  new sendMail().send(paymentId, emails.toString());
						   //System.out.println(" **** Email Sent... " + emails.toString() + " payment Id " + paymentId + " studentId " + studentId );
					 }

					
					// sql = "Select * from KM.PAYMENT_DETAILS WHERE STUDENT_ID="+ studentId;
					 // fetch the latest 5 records in the ascending order of payment id	
					 sql =   " SELECT * FROM (SELECT * FROM payment_details where student_id = " +  studentId + " ORDER BY id DESC LIMIT 5)sub ORDER BY id ASC " ;
					 rs1 = cDAO.getResult(sql);
					 System.out.println(" ** " + studentName + " "  + studentClass + "  " + studentId + " searchData " + searchData + " active " + active + " yearClass " + yearClass);
					 req.setAttribute("studentName", studentName);
					 req.setAttribute("studentClass", studentClass);
					 req.setAttribute("studentId", studentId);
					 req.setAttribute("searchData", searchData);
					 req.setAttribute("active", active);
					 req.setAttribute("yearClass", yearClass);
					 
			   } catch (SQLException e) {
				cDAO.setError(true);
				e.printStackTrace(); 
			}
			   
			  
			   
			  
			   
	 		 
			 if(cDAO.isError()) {
				   req.setAttribute("message", "Error during Searching Please check Logs");
			   }else{
				   if(mailSent){
					   req.setAttribute("message", "Mail Sent Successfully!!!");
				   }else{
					   req.setAttribute("message", "Details Obtained!!!");
				   }
			   }
			 
			 req.setAttribute("resultSet", rs1);
			 
			  System.out.println(" paymentServlet:doPost***: paymenttype " + paymenttype);
			  // check if the cheque entry is present in the cheque table.
			   sql = "select max(id) from cheques where payment_id =" + isNew ;
			  if (paymenttype != null && (paymenttype.equalsIgnoreCase("CHEQUE") ||  paymenttype.equalsIgnoreCase("PC") ) && cDAO.returnPaymentId(sql) ==0 ){
			 //if(STATUS != 1 && paymenttype != null &&( paymenttype.equalsIgnoreCase("CHEQUE") ||  paymenttype.equalsIgnoreCase("PC") )){
				 sql = "select max(id) from payment_details where payment_details.STUDENT_ID =" + studentId ;
				 int paymentId = cDAO.returnPaymentId(sql);
				  req.setAttribute("paymentId", paymentId);
				
				  sql = "select distinct((CHEQUE_NAME)) from cheques where CHEQUE_NAME <> null or  CHEQUE_NAME <> \"\" order by CHEQUE_NAME;"  ;
				  List<String> issuedBy = cDAO.createRSList(sql); 
				  				  sql = "select distinct(BANK) from km.cheques c where c.bank <> null or  c.bank <> \"\" order by c.bank;";
				   List<String> banks = cDAO.createRSList(sql);
				  sql = "select distinct (UPPER (branch))BRANCH from cheques order by BRANCH";
				  List<String> branch = cDAO.createRSList(sql);
				  sql = "select distinct (UPPER (CITY))CITY from cheques order by CITY";
				  List<String> city = cDAO.createRSList(sql);
				  req.setAttribute("bank", banks);
				  req.setAttribute("branch", branch);
				  req.setAttribute("city", city);
				  req.setAttribute("issuedBy", issuedBy);
				  
				 
				  getServletContext().getRequestDispatcher("/JSP/popUpCheque.jsp").forward(req, resp);
			//	  System.out.println(" paymentServlet:doPost***: type " + type);
			 }else {
				 
				  req.setAttribute("type", type);
				  getServletContext().getRequestDispatcher("/JSP/paymentDetails.jsp").forward(req, resp);
				 
			 }
			 
		 } else if( type.equalsIgnoreCase("searchSubmit")){
			 
			 String search = req.getParameter("searchString");
			 System.out.println(" paymentServlet:doPost: type " + type + " search " + search);
			 
		 }
		 
		  
	}

}
