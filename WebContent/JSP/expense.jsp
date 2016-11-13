<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.ResultSet" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
<script>
		function onSubmit(id, counter){

			    var searchString = document.getElementById("searchData").value;
			  //  alert("search Data " + searchString);
			    var url = "./expenseServlet?type=update&id="+id+"&counter="+counter+"&searchData="+searchString;
				// alert(" url  " +  url);
				 // return false;
				 document.forms["expense"].action= url;
				 document.forms["expense"].submit();
		 }

		function onSearch(request){

			 var searchString = document.getElementById("searchData").value;
			var url = "./expenseServlet?type="+request+"&searchData="+searchString;
			 //alert(" url  " +  url);
			 //return false;
			 document.forms["expense"].action= url;
			 document.forms["expense"].submit();         
		}
		 
 </script>

<form name ='expense' method ='post' >
	<%  String resp = (String)request.getAttribute("type"); 
	    String message = (String)request.getAttribute("message"); 
	    ResultSet rs  = (ResultSet)request.getAttribute("resultSet");
	%>
	
	     <h1> Search </h1> 	
                    Search Option: &nbsp; MONTH
				   <input type = "text" name="searchData" id= "searchData" />	
				   <br><br>
				   <input type = "button" value ="Search" onclick="javascript:onSearch('search');"/>
	
	   <table border = "1" width = "85%">
	     <tr>
		     <td> Name </td>
		     <td> Amount</td>
		     <td> Description</td>
		     <td> MONTH </td>
		     <td> Update</td>
	    </tr>
	<%   int counter = 0 , grandTotal = 0;
	     if(resp!= null &&  resp.equalsIgnoreCase("List")) {
	       while(rs.next()){
	    	   grandTotal += rs.getInt("AMOUNT");
	 %>
		        <tr>
		        <td><input type ="text" name ="name<%=counter%>" id="name<%=counter%>" value="<%=rs.getString("NAME")%>" /></td>
		        <td><input type ="text" name ="amount<%=counter%>" id="amount<%=counter%>" value="<%=rs.getInt("AMOUNT")%>" /></td>
		        <td><input type ="text" name ="desc<%=counter%>" id="desc<%=counter%>" value="<%=rs.getString("DESCRIPTION")%>" /></td>
		        <td><input type ="text" name ="month<%=counter%>" id="month<%=counter%>" value="<%=rs.getString("MONTH")%>" /> </td>
		        <td><input type="button" value = "Update" id="update<%=counter%>" name="update<%=counter%>" onClick="Javascript:onSubmit(<%=rs.getInt("ID")%>,<%=counter%>);"/> </td>
	           </tr>
	      <%  counter++;
	          } %>
	          <!--  Adding a new row for cheque information  -->
	          <tr>
	             <td> <input type ="text" name ="name<%=counter%>" id="name<%=counter%>" />  </td>
	             <td> <input type ="text" name ="amount<%=counter%>" id="amount<%=counter%>" /> </td>
	             <td> <input type ="text" name ="desc<%=counter%>" id="desc<%=counter%>" /> </td>
	             <td> <input type ="text" name ="month<%=counter%>" id="month<%=counter%>" /> </td>
	             <td><input type="button" value = "Update" id="update<%=counter%>" name="update<%=counter%>" onClick="Javascript:onSubmit(-1,<%=counter%>);" /> </td>
	          </tr>
	     </table>
	     
	      <br> Total Expense = <%=grandTotal %>
	<% } else if(resp!= null &&  resp.equalsIgnoreCase("fetch")) { %>
	          
	 
	<%}
	     
	     if(rs!= null) {
	      rs.close();
          System.out.println("expense.jsp --> Result Set Closed ...");
	     }
	 %> 
	     
	       <br><br> <h3>
          <% if(message != null && message != "" )   		
    			out.println(" " + message) ;
       %>
     </h3>  
  </form>
</body>
</html>