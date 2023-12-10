package kidsMansion;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class chequeServlet extends HttpServlet {
	
	private static final long serialVersionUID = -3194455707832738900L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		doPost(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String request = req.getParameter("type")!=null?req.getParameter("type"):"List";
		int counter = Integer.valueOf((req.getParameter("counter")!=null?req.getParameter("counter"):"0"));
		int studentID = Integer.valueOf((req.getParameter("studentID")!=null?req.getParameter("studentID"):"0"));
		String redirect = req.getParameter("redirect")!= null ?req.getParameter("redirect"):"";
		System.out.println("chequeServlet:doPost -->" + request + " counter -->" + counter + "studentId " + studentID);
		
		if(request.equalsIgnoreCase("addCheque")){
			String sql = null;
			controllerDAO cDAO = new controllerDAO();
			  sql = "select distinct(UPPER(CHEQUE_NAME)) CHEQUE_NAME from cheques order by CHEQUE_NAME"  ;
			  List<String> issuedBy = cDAO.createRSList(sql);
			   sql = "select distinct(UPPER(name)) name from students where active = 1 order by Name"  ;
			   List<String> stuName = cDAO.createRSList(sql);
			  sql = "select distinct(BANK) from km.cheques c where c.bank <> null or  c.bank <> \"\" order by c.bank;";
			   List<String> banks = cDAO.createRSList(sql);
			  sql = "select distinct (UPPER (branch))BRANCH from cheques order by BRANCH";
			  List<String> branch = cDAO.createRSList(sql);
			  sql = "select distinct (UPPER (CITY))CITY from cheques order by CITY";
			  List<String> city = cDAO.createRSList(sql);
			  req.setAttribute("bank", banks);
			  req.setAttribute("branch", branch);
			  req.setAttribute("city", city);
			  req.setAttribute("stuName", stuName);
			  req.setAttribute("issuedBy", issuedBy);
			  req.setAttribute("type", "addCheque");
			
			  
			  getServletContext().getRequestDispatcher("/JSP/popUpCheque.jsp").forward(req, resp);
			
		
		}else if(request.equalsIgnoreCase("updateChq")) {
			
			  int id = Integer.valueOf(req.getParameter("id"));
			  String chqStatus = req.getParameter("chqStatus"+counter)!= null ?req.getParameter("chqStatus"+counter):"";
			  String name = req.getParameter("name"+counter)!=null?req.getParameter("name"+counter):""; 
			  String chqueNO = req.getParameter("chqueNO"+counter)!=null?req.getParameter("chqueNO"+counter):"";
			  String issued = req.getParameter("issued"+counter)!=null?req.getParameter("issued"+counter):"";
			  String branch = req.getParameter("branch"+counter)!=null?req.getParameter("branch"+counter):""; 
			  String bank = req.getParameter("bank"+counter)!=null?req.getParameter("bank"+counter):"";
			  String city = req.getParameter("city"+counter)!=null?req.getParameter("city"+counter):"";
			  String chequeDt = req.getParameter("chequeDt"+counter)!=null?req.getParameter("chequeDt"+counter):"";
			  String depositeDt = req.getParameter("depositeDt"+counter)!=null?req.getParameter("depositeDt"+counter):"";
			  String comment = req.getParameter("comment"+counter)!=null?req.getParameter("comment"+counter):"";
			  int amount = Integer.valueOf((req.getParameter("amount"+counter)!=null?req.getParameter("amount"+counter):"0"));
			  String payment_id = req.getParameter("paymentID"+counter);
			  int paymentID =  0;
			  if(payment_id != null && !payment_id.equalsIgnoreCase("null") )
			    paymentID = Integer.valueOf((req.getParameter("paymentID"+counter)!=null?req.getParameter("paymentID"+counter):"0")); 
			  
			  if(name.equalsIgnoreCase("NA")) name =  req.getParameter("name1")!=null?req.getParameter("name1"):""; 
			  if(issued.equalsIgnoreCase("NA")) issued =  req.getParameter("issued1")!=null?req.getParameter("issued1"):""; 
			  if(bank.equalsIgnoreCase("NA")) bank =  req.getParameter("bankName")!=null?req.getParameter("bankName"):""; 
			  if(branch.equalsIgnoreCase("NA")) branch =  req.getParameter("bankBranch")!=null?req.getParameter("bankBranch"):""; 
			  if(city.equalsIgnoreCase("NA")) city =  req.getParameter("cityName")!=null?req.getParameter("cityName"):""; 
			  
			  
			  
			  //New entry when id = -1
			  if(id== -1){
				   String sql = "INSERT INTO KM.CHEQUES (CHEQUE_NAME,BRANCH,CITY,STATUS,AMOUNT,NAME,BANK, CHEQUE_NO, CHEQUE_DATE, DEPOSITED_DATE, COMMENTS,payment_ID ,CREATED_DATE, UPDATED_DATE ) VALUES ('" + issued +"','"  +
						         branch + "','" + city +"','" + chqStatus + "'," + amount + ",'" + name + "','" + bank + "','"+  chqueNO + "','" + chequeDt + "','"+ depositeDt + "','"+ comment + "'," + paymentID + ", now(), now())";
				   System.out.println("chequeServlet:doPost:insertSQL--> " + sql);
				   controllerDAO cDAO = new controllerDAO();
		           int res = cDAO.addUser(sql);
		           System.out.println(" chequeServlet:doPost Number of Rows inserted = " + res);
				   
			  }else{
				   String sql = "UPDATE KM.CHEQUES SET  CHEQUE_NAME= '"+ issued +"', BRANCH='"+branch+"',CITY='"+city+"',STATUS='"+
						   chqStatus + "',AMOUNT="+amount + ",NAME='"+ name + "',BANK='" + bank + "',CHEQUE_NO='"+ chqueNO + "', CHEQUE_DATE ='" + chequeDt + "',DEPOSITED_DATE='"+ depositeDt + "',COMMENTS='"+ comment + "', UPDATED_DATE = NOW() where id =" + id;
				   	   System.out.println("chequeServlet:doPost::updateChq -->" + sql);
				   	 controllerDAO cDAO = new controllerDAO();
			           int res = cDAO.addUser(sql);
			           System.out.println(" chequeServlet:doPost Number of Rows Updated = " + res);
			  }
			  
			// Update the status to paid for the cheque "status" is CREDITED.
			  if((chqStatus.equalsIgnoreCase("CREDITED") || chqStatus.equalsIgnoreCase("PC")) && paymentID != 0 ){
				  String sql = null;
				  Calendar cal = Calendar.getInstance();
					String date = new SimpleDateFormat("dd-M-yyyy").format(cal.getTime());
					if(chqStatus.equalsIgnoreCase("CREDITED"))
				          sql = "UPDATE KM.PAYMENT_DETAILS SET STATUS =1 , UPDATED_DATE = NOW(), comments = '" + comment + " : " + chqueNO  + " : " + date  + " : " + amount +"' where ID = " + paymentID;
					else
						 sql = "UPDATE KM.PAYMENT_DETAILS SET  UPDATED_DATE = NOW(), comments = '" + comment + " : " + chqueNO  + " : " + date  + " : " + amount +"' where ID = " + paymentID;
				  System.out.println("chequeServlet:doPost::update Payment " + sql);
				  int res = new controllerDAO().addUser(sql);
				  System.out.println(" chequeServlet:doPost Number of Rows Updated = " + res);
			  }
		
		}
		 
		 
		  
		 
		 
		 //to reload the same page
		 if (!request.equalsIgnoreCase("addCheque"))   request= "List";
		 if( redirect.equalsIgnoreCase("true")){
			  System.out.println("Cheque Request redirecting to Payment Page");
			 //getServletContext().getRequestDispatcher("./paymentServlet?type=fetchPayment&isNew=null&id="+studentID).forward(req, resp);
			  resp.sendRedirect("/kidsMansion/paymentServlet?type=fetchPayment&id="+studentID);
		}else  if(request.equalsIgnoreCase("List")){
				
			 // String searchString =  req.getParameter("searchString")!= null? req.getParameter("searchString"): "";
			// System.out.println("controllerServlet:pendingDC  searchString " );
			 
			 String sql = "select *  from KM.CHEQUES order by  updated_date desc, status desc,  name asc" ;
			  System.out.println("chequeServlet:doPost " + sql);
			  controllerDAO cDAO = new controllerDAO();
			  ResultSet  rs1 =  cDAO.getResult(sql);
			  req.setAttribute("resultSet", rs1);
			  if(cDAO.isError()) {
				   req.setAttribute("message", "Error during Adding Please check Logs");
			   }else{
				   req.setAttribute("message", "Search Succesful!!!");
			   }
			  req.setAttribute("type", request);
			  getServletContext().getRequestDispatcher("/JSP/cheques.jsp").forward(req, resp);
		} 
		 
		 
		
		 
		
		
	}

}
