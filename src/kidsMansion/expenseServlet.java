package kidsMansion;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class expenseServlet extends HttpServlet {
	
	private static final long serialVersionUID = 5165111770576173090L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String request = req.getParameter("type")!=null?req.getParameter("type"):"List";
		String searchString = req.getParameter("searchData")!=null?req.getParameter("searchData"):"";
		int counter = Integer.valueOf((req.getParameter("counter")!=null?req.getParameter("counter"):"0"));
		System.out.println("expenseServlet:doPost -->" + request + " counter -->" + counter);
		 if(request.equalsIgnoreCase("update")) {
			
			  int id = Integer.valueOf(req.getParameter("id"));
			
			  String name = req.getParameter("name"+counter)!=null?req.getParameter("name"+counter):""; 
			  String desc = req.getParameter("desc"+counter)!=null?req.getParameter("desc"+counter):"";
			  String month = req.getParameter("month"+counter)!=null?req.getParameter("month"+counter):""; 
			  int amount = Integer.valueOf((req.getParameter("amount"+counter)!=null?req.getParameter("amount"+counter):"0"));
			  
			  //New entry when id = -1
			  if(id== -1){
				   String sql = "INSERT INTO KM.EXPENSE (NAME,DESCRIPTION,MONTH,AMOUNT) VALUES ('" + name +"','"  +
						         desc + "','" + month + "'," + amount + ")";
				   System.out.println("expenseServlet:doPost:insertSQL--> " + sql);
				   controllerDAO cDAO = new controllerDAO();
		           int res = cDAO.addUser(sql);
		           System.out.println(" expenseServlet:doPost Number of Rows inserted = " + res);
				   
			  }else{
				   String sql = "UPDATE KM.EXPENSE SET  NAME= '"+ name +"', DESCRIPTION='"+desc+"',MONTH='"+month+"',AMOUNT="+amount+ " where id =" + id;
				   	   System.out.println("expenseServlet:doPost::updateChq -->" + sql);
				   	   controllerDAO cDAO = new controllerDAO();
			           int res = cDAO.addUser(sql);
			           System.out.println(" expenseServlet:doPost Number of Rows Updated = " + res);
			  }
		
		}
		 
		 //to reload the same page when the request type is not Search
		 if (!request.equalsIgnoreCase("fetch"))request= "List";
		 
		 
		 
		 if(request.equalsIgnoreCase("List")){
				
			 // String searchString =  req.getParameter("searchString")!= null? req.getParameter("searchString"): "";
			// System.out.println("controllerServlet:pendingDC  searchString " );
			 
			 String sql = "select *  from KM.EXPENSE" ;
			 System.out.println(" expenseServlet:doPost : searchString " + searchString );
			 if(searchString !="")
				 sql =  sql +  " where MONTH ='"+ searchString + "'";
			  System.out.println("expenseServlet:doPost " + sql);
			  controllerDAO cDAO = new controllerDAO();
			  ResultSet  rs1 =  cDAO.getResult(sql);
			  req.setAttribute("resultSet", rs1);
			  if(cDAO.isError()) {
				   req.setAttribute("message", "Error during Adding Please check Logs");
			   }else{
				   req.setAttribute("message", "Search Succesful!!!");
			   }
			  
		}
		
		  req.setAttribute("type", request);
		  getServletContext().getRequestDispatcher("/JSP/expense.jsp").forward(req, resp);
		  
		
	}

}
