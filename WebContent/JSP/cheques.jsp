<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.ResultSet" %>
     <%@page import="java.text.DecimalFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

<script language="JavaScript" src="./js/datepick/ts_picker.js">

//Script by Denis Gritcyuk: tspicker@yahoo.com
//Submitted to JavaScript Kit (http://javascriptkit.com)
//Visit http://javascriptkit.com for this script

</script>

<script>
		function onSubmit(id, counter){
			    var component = "chqStatus"+counter ;
			 //   alert(" component --> " + component);
			 	 var selected = document.getElementById(component).selectedIndex;
				 var selection = document.getElementsByTagName("option")[selected].value
				 var url = "./chequeServlet?type=updateChq&id="+id+"&chqStatus="+ selection+"&counter="+counter;
				// alert(" url  " +  url);
			//	  return false;
				 document.forms["cheque"].action= url;
				 document.forms["cheque"].submit();
		 }
 </script>

<form name ='cheque' method ='post' >
	<%  String resp = (String)request.getAttribute("type"); 
	    String message = (String)request.getAttribute("message"); 
	    ResultSet rs  = (ResultSet)request.getAttribute("resultSet");
	    DecimalFormat moneyFormat = new DecimalFormat("#,##,##0.00");
	%>
	   <table border = '1' width = '75%'>
	     <tr>
		     <td> Student Name </td>
		     <td> Cheque#</td>
		     <td> Cheque Name </td>
		     <td> Bank</td>
		     <td> Branch</td>
		      <td> City</td>
		     <td> Amount</td>
		     <td> Cheque Dt</td>
		     <td> Deposited Dt</td>
		     <td> Commments </td>
		     <td> Status</td>
		     <td> Update</td>
	    </tr>
	<%   int counter = 1 ;
	     if(resp!= null &&  resp.equalsIgnoreCase("List")) { 
	    	 while(rs.next()){
	    	 boolean isCredited = false;
	    	 boolean isPC = false;
	    	 if (rs.getString("STATUS").equalsIgnoreCase("CREDITED")) {
	    		 isCredited = true;
	    	 }else if (rs.getString("STATUS").equalsIgnoreCase("PC") ){
	    		 isPC = true;
	    	 }
	%>
	          <% if(isCredited || isPC ) { %>
	            <tr BGCOLOR ='#00FF40' >
	            <td><% out.print(rs.getString("NAME"));%></td>
	            <td><% out.print(rs.getString("CHEQUE_NO"));%></td>
		         <td><% out.print(rs.getString("CHEQUE_NAME"));%></td>
		        <td><% out.print(rs.getString("BANK"));%></td>
		        <td><% out.print(rs.getString("BRANCH"));%></td>
		        <td><% out.print(rs.getString("CITY"));%></td>
		        <td> <% out.print( moneyFormat.format( Integer.valueOf (rs.getString("AMOUNT")))); %></td>
		        <td><% out.print(rs.getString("CHEQUE_DATE"));%></td>
		        <td><% out.print(rs.getString("DEPOSITED_DATE"));%></td>
		        <td><% out.print(rs.getString("COMMENTS"));%></td>
		        <td> <Select disabled > 
		         <%if (isCredited) {%> <option value="CREDITED">CREDITED</option>
		         <% }else { %>
		           <option value="CREDITED">PARTIAL CREDIT </option>
		         <% } %>
		           </Select> </td>
		        <td><input type="button" value = "Update"  size ="8" disabled /> </td>
	           </tr>
	      <%  }else { %>
	          <!--  Adding a new row for cheque information  -->
	          <tr>
	            <td><input type ="text" name ="name<%=counter%>" id="name<%=counter%>" value="<%=rs.getString("NAME")%>"  /></td>
		        <td><input type ="text" name ="chqueNO<%=counter%>" id="chqueNO<%=counter%>" value="<%=rs.getString("CHEQUE_NO")%>"  size ="8" /></td>
		        <td><input type ="text" name ="issued<%=counter%>" id="issued<%=counter%>" value="<%=rs.getString("CHEQUE_NAME")%>" size ="20" /></td>
		        <td><input type ="text" name ="bank<%=counter%>" id="bank<%=counter%>" value="<%=rs.getString("BANK")%>" size ="8"  /> </td>
		        <td><input type ="text" name ="branch<%=counter%>" id="branch<%=counter%>" value="<%=rs.getString("BRANCH")%>" size ="8" /> </td>
		        <td><input type ="text" name ="city<%=counter%>" id="city<%=counter%>" value="<%=rs.getString("CITY")%>" size ="8" /> </td>
		        <td><input type ="text" name ="amount<%=counter%>" id="amount<%=counter%>"  size = '8' value="<%=rs.getString("AMOUNT")%>"  size ="8" /> </td>
		        <td><input type ="text" name ="chequeDt<%=counter%>" id="chequeDt<%=counter%>" value="<%=rs.getString("CHEQUE_DATE")%>" size ="8" /> 
		             <a href="javascript:show_calendar('document.cheque.chequeDt<%=counter%>', document.cheque.chequeDt<%=counter%>.value);">
				     <img src="./js/datepick/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick up the timestamp">
		        </td>
		        <td><input type ="text" name ="depositeDt<%=counter%>" id="depositeDt<%=counter%>" value="<%=rs.getString("DEPOSITED_DATE")%>"  size ="8" />
		            <a href="javascript:show_calendar('document.cheque.depositeDt<%=counter%>', document.cheque.depositeDt<%=counter%>.value);">
					<img src="./js/datepick/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick up the timestamp">
		        </td>
		         </td>
	            <td><input type ="text" name ="comment<%=counter%>" id="comment<%=counter%>" value="<%=rs.getString("COMMENTS")%>" size='15' /> </td>
	            <td>  <Select name="chqStatus<%=counter%>" id="chqStatus<%=counter%>"  >
		                     <%if (rs.getString("STATUS").equalsIgnoreCase("TOBE")){ %>  
		                           <option value="TOBE" selected >TO BE</option>  
		                           <option value="DEPOSITED">DEPOSITED</option>
		                           <option value="CREDITED">CREDITED</option>
		                           <option value="PC">PARTIAL CREDIT </option>
		                      <%} else  {%>     
		                           <option value="TOBE" >TO BE</option>  
		                           <option value="DEPOSITED" selected>DEPOSITED</option>
		                           <option value="CREDITED">CREDITED</option>
		                           <option value="PC">PARTIAL CREDIT </option>
		                      
		                      <% } %>
		                  </Select>
	            </td>
	             <td><input type="button" value = "Update" id="updateChq<%=counter%>" name="updateChq<%=counter%>" onClick="Javascript:onSubmit(<%=rs.getInt("ID")%>,<%=counter%>);" size ="8" /> 
	                  <input type ="hidden" value = "<%=rs.getString("PAYMENT_ID")%>" name="paymentID<%=counter%>" >
	             </td>
	          </tr>
	     
	<% }  
	     counter++;
	       }
	   %>   
	   
	    <!--  Adding a new row for cheque information  -->
	          <tr>
	             <td> <input type ="text" name ="name<%=counter%>" id="name<%=counter%>" />  </td>
	             <td> <input type ="text" name ="chqueNO<%=counter%>" id="chqueNO<%=counter%>" size ="8"  /> </td>
	             <td> <input type ="text" name ="issued<%=counter%>" id="issued<%=counter%>"  size ="20" /> </td>
	             <td> <input type ="text" name ="bank<%=counter%>" id="bank<%=counter%>" size ="8"  /> </td>
	             <td> <input type ="text" name ="branch<%=counter%>" id="branch<%=counter%>"  size ="8" /> </td>
	             <td> <input type ="text" name ="city<%=counter%>" id="city<%=counter%>"  size ="8" /> </td>
	             <td> <input type ="text" name ="amount<%=counter%>" id="amount<%=counter%>" size = '8'/> </td>
	             <td><input type ="text" name ="chequeDt<%=counter%>" id="chequeDt<%=counter%>" size='8' /> 
	              <a href="javascript:show_calendar('document.cheque.chequeDt<%=counter%>', document.cheque.chequeDt<%=counter%>.value);">
				 <img src="./js/datepick/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick up the timestamp">
	             </td>
		         <td><input type ="text" name ="depositeDt<%=counter%>" id="depositeDt<%=counter%>" size='8' /> 
		            <a href="javascript:show_calendar('document.cheque.depositeDt<%=counter%>', document.cheque.depositeDt<%=counter%>.value);">
					<img src="./js/datepick/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick up the timestamp">
		        </td>
		         <td><input type ="text" name ="comment<%=counter%>" id="comment<%=counter%>" size='15' /> </td>
	             <td> <Select name="chqStatus<%=counter%>" id="chqStatus<%=counter%>" >
		                   <option value="TOBE" selected >TO BE</option>  
			               <option value="DEPOSITED">DEPOSITED</option>
			               <option value="CREDITED">CREDITED</option>
			               <option value="PC">PARTIAL CREDIT </option>
		              </Select>             
				</td>
	             <td><input type="button" value = "Update" id="updateChq<%=counter%>" name="updateChq<%=counter%>" onClick="Javascript:onSubmit(-1,<%=counter%>);" size ="8" /> </td>
	          </tr>
	     
	   
	       
	 <%    }
	     
	     if(rs!= null) {
		      rs.close();
		      // // // // // System.out.println("cheques.jsp --> Result Set Closed ...");
		    }
	  %>
	
	 </table>
	 
	     
	       <br><br> <h3>
          <% if(message != null && message != "" )   		
    			out.println(" " + message) ;
       %>
     </h3>  
  </form>
</body>
</html>