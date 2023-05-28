<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.ResultSet" %>
    <%@page import="kidsMansion.controllerDAO" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<script language="JavaScript" src="./js/datepick/ts_picker.js">

//Script by Denis Gritcyuk: tspicker@yahoo.com
//Submitted to JavaScript Kit (http://javascriptkit.com)
//Visit http://javascriptkit.com for this script

</script>
<script>

 function onSubmit(result, id,studentClass, counter, active, yearClass ){
	 
	    //alert("counter " + counter);
	     var sendMail = document.getElementById("sendMail"+counter).checked;
	     var payStatus = document.getElementById("payStatus"+counter).checked;
	     var comments =document.getElementById("COMMMENTS"+counter).value;
	     var payType = document.getElementById("PAYMENT_TYPE"+counter).value;
	     var actionURL = "./paymentServlet?type=fetchPayment&isNew="+ result+ "&id="+id+"&CLASS="+studentClass+"&payStatus="+payStatus+"&COMMENTS="+comments+"&counter="+counter+"&sendMail="+sendMail+ "&active=" + active + "&yearClass="+ yearClass;;
	     
	     //alert(" actionURL " + actionURL );
	    // alert("comments -->  " + comments);
	    // return false;
	     /* if(payType != 'NA' ){
	    	 alert(" payType " + payType );
	    	 document.getElementById("modPaymentType").value = payType;
	    	 document.getElementById("myDialog").showModal(); 
	    }else {
		 document.forms["paymentDetail"].action= actionURL;
		 document.forms["paymentDetail"].submit();
	    }   */
	 
	    document.forms["paymentDetail"].action= actionURL;
		 document.forms["paymentDetail"].submit();
  }
 
 function goBack(stuClass, searchData, active, yearClass) {
	    
	    var url = "./controllerServlet?type=searchSubmit&group="+stuClass+ "&searchData="+searchData + "&active=" + active + "&yearClass="+ yearClass;
	 //   alert("Alerting Go Back Event " + url  );
	    document.forms["paymentDetail"].action= url;
	    document.forms["paymentDetail"].submit();
	}
 
 
</script>
<body>
<h1> Payment Information </h1>

	<form name ='paymentDetail' method ='post' >
		<% 
		    String resp = (String)request.getAttribute("type"); 
		    String message = (String)request.getAttribute("message"); 
		    String searchData = (String)request.getAttribute("searchData"); 
		    String  active = (String) request.getAttribute("active");
		    String  yearClass = (String) request.getAttribute("yearClass");
		    
		    // // // // // System.out.println("paymentDetail.jsp --> searchData " + searchData + "yearClass " + yearClass) ;
		    
		    ResultSet rs  = (ResultSet)request.getAttribute("resultSet");
		    int recCount = -1;
		    String lastmonth="";
		%>
	<!--  Modal Box -->
	  <dialog id="myDialog">
           <table>
             <%out.print (request.getParameter("modPayType")); %>
              <tr> <td> Payment Type </td> <td> <input type ='text' id= 'modPaymentType' name = 'modPaymentType' disabled="disabled" />
              
              
              </tr>
           
           
           </table>

       </dialog>
	
	
       <!--  Search Screen -->
       <font size = '2'>
       
	<%    if(resp != null &&  resp.equalsIgnoreCase("fetchPayment")){   %>
	              
	              <% String studentName = (String)request.getAttribute("studentName"); 
	                 String studentClass = (String)request.getAttribute("studentClass"); 
	                 int studentId = (Integer)request.getAttribute("studentId"); 
	                 
	              %>
	          
	          <input type = 'button' id='back'name='back' onClick = "javaScript:goBack('<%=studentClass %>','<%=searchData %>' ,<%=active%>, <%=yearClass %>);" value = "Back"/> 
      		 <br><br>  
	              <table border = '1' width = '100%'>
	              <tr>
	               <td> NAME </td>
	               <td> CLASS </td>
	                 <td>TRANSPORT</td>
	             <% if(!studentClass.equalsIgnoreCase("DayCare")) { %>
		                 <td>ADMISSION</td>
		                 <td>BOOK</td>
		                 <td>UNIFORM</td>
		                 <td>ACTIVITY</td>
		                 <td>TERM FEES</td>
						 <td>TERM</td>
						 
						 
			      <%} else {%>
	                     <td>DAY CARE</td>
		                 <td>BREAK FAST</td>
						 <td>LUNCH</td>
						 <td>SNACKS</td>
						 <td>ART</td>
						 <td>DANCE</td>
						 <td>AD HOC</td>
						 <td>MONTH</td>
				  
	               <% } %>
	                     <td>TOTAL</td>
	                     <td>PAYMENT TYPE</td>
						 <td>PAID DATE</td>
						 <td>PAID</td>
						 <td>COMMENTS</td>
						 <td>UPDATE</td>
	              </tr>
	              
	               <%     int counter = 0,TRANSPORT=0,DAYCARE=0,BF=0,LUNCH=0,SNACKS=0,ART=0,DANCE=0,ADHOC=0,UNIFORM=0;
	                      while(rs.next()) { 
	            	      recCount =  rs.getInt("ID");
	            	      // These values are stored to input for a new row for day care fees list
	            	      
	            	       TRANSPORT= rs.getInt("TRANSPORT"); DAYCARE=rs.getInt("DAYCARE");BF=rs.getInt("BF");LUNCH=rs.getInt("LUNCH");SNACKS=rs.getInt("SNACKS");
	            	            ART=rs.getInt("ART");DANCE=rs.getInt("DANCE"); ADHOC=rs.getInt("ADHOC"); UNIFORM=rs.getInt("UNIFORM"); lastmonth = rs.getString("MONTH");
	            	      %>
	                      
	                     <tr <% if (rs.getInt("STATUS")==1) { %>   BGCOLOR ='#00FF40' <%}%> >
		      			          <td><%  out.print(studentName);  %> </td>
		      			          <td><% out.print( studentClass);  %> </td>
		      			          <td><input type="text" name= "TRANSPORT<%=counter%>" size = '5'  value="<%=rs.getInt("TRANSPORT")%>" /></td>
		      			          <% if(!studentClass.equalsIgnoreCase("DayCare")) { %>
			      			          <td><input type="text" name= "ADMISSION<%=counter%>" size = '5' value="<%=rs.getInt("ADMISSION")%>" /> </td>
			      			          <td><input type="text" name= "BOOK<%=counter%>" size = '5' value="<%=rs.getInt("BOOK")%>"  /></td>
			      			          <td><input type="text" name= "UNIFORM<%=counter%>" size = '5' value="<%=rs.getInt("UNIFORM")%>" /> </td>
			      			          <td><input type="text" name= "ACTIVITY<%=counter%>" size = '5' value="<%=rs.getInt("ACTIVITY")%>" /> </td>
			      			           <td> <input type="text" name= "TERM_FEES<%=counter%>" size = '5'  value="<%=rs.getString("TERM_FEES")%>" /> </td>
			      			          <td> <input type="text" name= "TERM<%=counter%>" size = '5'  value="<%=rs.getString("TERM")%>" /> </td>
			      			       <%}else{ %>   
			      			       	  <td><input type="text" name= "DAYCARE<%=counter%>" size='5' value="<%=rs.getInt("DAYCARE")%>" /> </td>
			      			          <td><input type="text" name= "BREAKFAST<%=counter%>"  size='5' value="<%=rs.getInt("BF")%>"/> </td>
			      			          <td><input type="text" name= "LUNCH<%=counter%>" size='5'  value="<%=rs.getInt("LUNCH")%>" /> </td>
			      			          <td> <input type="text" name= "SNACKS<%=counter%>" size='5' value="<%=rs.getInt("SNACKS")%>" /> </td>
			      			          <td><input type="text" name= "ART<%=counter%>"  size='5' value="<%=rs.getInt("ART")%>" /> </td>
			      			          <td><input type="text" name= "DANCE<%=counter%>" size='5' value="<%=rs.getInt("DANCE")%>"  /> </td>
			      			          <td> <input type="text" name= "ADHOC<%=counter%>" size='5' value="<%=rs.getInt("ADHOC")%>"  /> </td>
			      			          <td> <input type="text" name= "MONTH<%=counter%>" size='5' value="<%=rs.getString("MONTH")%>"  /> </td>
			      			       
			      			       <% } %>
		      			              <td><input type="text" name= "TOTAL<%=counter%>"  size='5' value="<%=rs.getInt("TOTAL")%>"  /> </td>
	      			               <td> 
										<Select name="PAYMENT_TYPE<%=counter%>" id="PAYMENT_TYPE<%=counter%>"  >
										<% if (rs.getString("TYPE")!= null) { %>
										 <%if (rs.getString("TYPE").equalsIgnoreCase("NETBANK-V")){ %>  
											   <option value="NETBANK-V" selected >NETBANK-V</option> 
											   <option value="NETBANK-KM" >NETBANK-KM</option>  
											   <option value="NETBANK-UPI">NETBANK-UPI</option>  
											   <option value="CHEQUE">CHEQUE</option>
											   <option value="CASH">CASH</option>
										  <%}else if (rs.getString("TYPE").equalsIgnoreCase("CHEQUE")){ %>
											   <option value="NETBANK-V"  >NETBANK-V</option> 
											   <option value="NETBANK-KM" >NETBANK-KM</option>   
											   <option value="NETBANK-UPI">NETBANK-UPI</option> 
											   <option value="CHEQUE" selected>CHEQUE</option>
											   <option value="CASH">CASH</option>
										 <%}else if (rs.getString("TYPE").equalsIgnoreCase("NETBANK-KM")){ %>
											   <option value="NETBANK-V"  >NETBANK-V</option> 
											   <option value="NETBANK-KM" selected >NETBANK-KM</option>  
											   <option value="NETBANK-UPI">NETBANK-UPI</option>  
											   <option value="CHEQUE" >CHEQUE</option>
											   <option value="CASH">CASH</option>	   
										 <%}else if (rs.getString("TYPE").equalsIgnoreCase("CASH")){ %>
											   <option value="NETBANK-V"  >NETBANK-V</option> 
											   <option value="NETBANK-KM" selected >NETBANK-KM</option> 
												<option value="NETBANK-UPI">NETBANK-UPI</option>  
											   <option value="CHEQUE" >CHEQUE</option>
											   <option value="CASH" selected >CASH</option>	   
										 <%}else if (rs.getString("TYPE").equalsIgnoreCase("NETBANK-UPI")){ %>
											   <option value="NETBANK-V"  >NETBANK-V</option> 
											   <option value="NETBANK-KM"  >NETBANK-KM</option>
											   <option value="NETBANK-UPI" selected >NETBANK-UPI</option>    
											   <option value="CHEQUE" >CHEQUE</option>
											   <option value="CASH" >CASH</option>	
										  <%  }else {%>   
										       <option value="NA"></option>  
										       <option value="NETBANK-V"  >NETBANK-V</option> 
											   <option value="NETBANK-KM">NETBANK-KM</option>   
											   <option value="NETBANK-UPI">NETBANK-UPI</option> 
											   <option value="CHEQUE" >CHEQUE</option>
											   <option value="CASH">CASH</option>	
										   <% } 
										   } %>   
										        
										 </Select>
										</td>
			      			         <%--  <td><input type="text" name= "PAYMENT_TYPE<%=counter%>" size='10'  value="<%=rs.getString("TYPE")%>"  /> </td> --%>
			      			          <td><input type="text" name= "PAID_DATE<%=counter%>" size='5'  value="<%=rs.getString("PAID_DATE")%>"   /> 
			      			               <a href="javascript:show_calendar('document.paymentDetail.PAID_DATE<%=counter%>', document.paymentDetail.PAID_DATE<%=counter%>.value);">
											<img src="./js/datepick/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick up the timestamp"></a>
			      			          </td>
			      			          <td><input type="checkbox" name="payStatus<%=counter%>" id="payStatus<%=counter%>" <% if(rs.getInt("STATUS") ==1 ) { %> checked disabled <% } %> /> </td>
			      			          <td> <textarea name="COMMMENTS<%=counter%>" cols="20" rows="4" id = "COMMMENTS<%=counter%>"><%=rs.getString("COMMENTS")%></textarea> </td>
			      			          <td><input type= "checkbox" name="sendMail<%=counter%>" id = "sendMail<%=counter%>"  <% if(rs.getInt("SENTMAIL") ==1 ) {%> checked  <%}%>/> <input type="button" name= "addPayment<%=counter%>" value ="Update" size='5'  onClick="javascript:onSubmit(<%=recCount%>, <%= studentId%>, '<%=studentClass %>' , <%= counter%>, <%=active%>, <%=yearClass%>);"  <% if(rs.getInt("STATUS") ==1 ) {%> disabled  <%}%> /> </td>
			      			          
		      			          
	      			     </tr>  
	               
	               <%  counter++;
	                    }%>
	               
	              
	                  <%if(! rs.first() || (rs.last() && rs.getInt("STATUS")==1 ))  {
	                	  recCount = -1;
	                	  %> 
	                      <tr>
	                              <td><%out.print(studentName);  %> </td>
		      			          <td><% out.print( studentClass);  %> </td>
		      			          <td><input type="text" name= "TRANSPORT<%=counter%>" size='5' value= "<%=TRANSPORT%>" /> </td>
		      			          <% if(!studentClass.equalsIgnoreCase("DayCare")) { %>
			      			          <td><input type="text" name= "ADMISSION<%=counter%>" size='5' value = ""  /> </td>
			      			          <td><input type="text" name= "BOOK<%=counter%>"  size='5' value = "" /> </td>
			      			          <td><input type="text" name= "UNIFORM<%=counter%>" size='5' value = ""  /> </td>
			      			          <td><input type="text" name= "ACTIVITY<%=counter%>" size='5' value = ""  /> </td>
			      			          <td> <input type="text" name= "TERM_FEES<%=counter%>" size='5'  value = "" /> </td>
			      			          <td> <input type="text" name= "TERM<%=counter%>" size='5'  value = "" /> </td>
			      			          
			      			      <%}else{ %>   
			      			          <td><input type="text" name= "DAYCARE<%=counter%>" size='5' value = "<%=DAYCARE%>" /> </td>
			      			          <td><input type="text" name= "BREAKFAST<%=counter%>"  size='5' value = "<%=BF%>" /> </td>
			      			          <td><input type="text" name= "LUNCH<%=counter%>" size='5' value = "<%=LUNCH%>"  /> </td>
			      			          <td> <input type="text" name= "SNACKS<%=counter%>" size='5'  value = "<%=SNACKS%>"   /> </td>
			      			          <td><input type="text" name= "ART<%=counter%>"  size='5' value = "<%=ART%>"  /> </td>
			      			          <td><input type="text" name= "DANCE<%=counter%>" size='5' value = "<%=DANCE%>"   /> </td>
			      			          <td> <input type="text" name= "ADHOC<%=counter%>" size='5' value = "<%=ADHOC%>"    /> </td>
			      			          <td> <input type="text" name= "MONTH<%=counter%>" size='5'  value = "<%=controllerDAO.getMonth(lastmonth)%>"  /> </td>
			      			       <% } %>
			      			       
			      			          <td><input type="text" name= "TOTAL<%=counter%>"  size='5'  /> </td>
			      			          <td> 
										<Select name="PAYMENT_TYPE<%=counter%>" id="PAYMENT_TYPE<%=counter%>"  >
										<%-- <% if (rs.getString("TYPE")!= null) { %>
										 <%if (rs.getString("TYPE").equalsIgnoreCase("NETBANK-V")){ %>  
											   <option value="NETBANK-V" selected >NETBANK-V</option> 
											   <option value="NETBANK-KM" >NETBANK-KM</option>   
											   <option value="CHEQUE">CHEQUE</option>
											   <option value="CASH">CASH</option>
										  <%}else if (rs.getString("TYPE").equalsIgnoreCase("CHEQUE")){ %>
											   <option value="NETBANK-V"  >NETBANK-V</option> 
											   <option value="NETBANK-KM" >NETBANK-KM</option>   
											   <option value="CHEQUE" selected>CHEQUE</option>
											   <option value="CASH">CASH</option>
										 <%}else if (rs.getString("TYPE").equalsIgnoreCase("NETBANK-KM")){ %>
											   <option value="NETBANK-V"  >NETBANK-V</option> 
											   <option value="NETBANK-KM" selected >NETBANK-KM</option>   
											   <option value="CHEQUE" >CHEQUE</option>
											   <option value="CASH">CASH</option>	   
										 <%}else if (rs.getString("TYPE").equalsIgnoreCase("CASH")){ %>
											   <option value="NETBANK-V"  >NETBANK-V</option> 
											   <option value="NETBANK-KM"  >NETBANK-KM</option>   
											   <option value="CHEQUE" >CHEQUE</option>
											   <option value="CASH" selected >CASH</option>	   
										 
										  <%  }else {%> --%>   
										       <option value="NA"></option>  
										       <option value="NETBANK-V"  >NETBANK-V</option> 
											   <option value="NETBANK-KM">NETBANK-KM</option>  
											   <option value="NETBANK-UPI">NETBANK-UPI</option> 
											   <option value="CHEQUE" >CHEQUE</option>
											   <option value="CASH">CASH</option>	
										   <%/*  } 
										   }  */%>    
										</Select>
			      			          
			      			         <%--  <td><input type="text" name= "PAYMENT_TYPE<%=counter%>" size='10' value = ""  /> </td> --%>
			      			          <td><input type="text" name= "PAID_DATE<%=counter%>" size='5'/> 
			      			          <a href="javascript:show_calendar('document.paymentDetail.PAID_DATE<%=counter%>', document.paymentDetail.PAID_DATE<%=counter%>.value);">
										<img src="./js/datepick/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick up the timestamp"></a>
			      			          </td>
			      			          <td><input type="checkbox" name="payStatus<%=counter%>" id="payStatus<%=counter%>"/> </td>
			      			          <td> <textarea name="COMMMENTS<%=counter%>" id = "COMMMENTS<%=counter%>" cols="20" rows="4"></textarea> </td>
			      			          <td><input type= "checkbox" name="sendMail<%=counter%>" id = "sendMail<%=counter%>" /> <input type="button" name= "addPayment<%=counter%>" value ="Update" size='5' onClick="javascript:onSubmit(<%=recCount%>, <%= studentId%>, '<%=studentClass %>', <%= counter%>);" /> </td>
			      			          
			      		   </tr>       
	               
	               <% }
	                  rs.close();
	                  // // // // // System.out.println("paymentDetails.jsp --> Result Set Closed ...");
	                  %>
	              </div>
	              </table>
	       <%} %>  
	       
	          </font>
	       
	       
	       
	       <br><br> <h3>
    <% if(message != null && message != "" )   		
    			out.println(" " + message) ;
    
		
			
		
       %>
     </h3>  
	 </form>     
</body>
</html>