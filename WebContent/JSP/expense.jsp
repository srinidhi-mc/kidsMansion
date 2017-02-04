<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List" %>
     <%@page import="kidsMansion.DailyReportTO" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
<script>
		function onSubmit(id, type){
			var selected = '0';
	    	 checkboxes = document.getElementsByName('checker');
		    for(var i=0, n=checkboxes.length;i<n;i++) {
				  if(checkboxes[i].checked)
				        selected =  (checkboxes[i].id) + "," + selected; 
			 }
    	 	  if(selected == '0') {
	    		  alert("No rows are selected");
	    		  return false;
	    	  }
	    	  if(id == 'submit') {
		    	  var confirmResponse = confirm("Submit All the selected records?");
				   if (!confirmResponse){  return false; 	}			   
			  }
	    	  
	    	  if(id == 'delete') {
		    	  var confirmResponse = confirm("Delete All the selected records?");
				   if (!confirmResponse){  return false; 	}			   
			  }
		    	 
	    	  if(id == 'view') {
	    		  var splited = selected.split(",")
	    		  if(splited.length > 2) {
	    			  alert("Select only one record to view at a time");
	    			  return false;
	    		  }
		    	 			   
			  }
		    
		     
		     alert(selected);
			    //var url = "./expenseServlet?type=update&id="+id+"&counter="+counter+"&searchData="+searchString;
				// alert(" url  " +  url);
				 // return false;
				// document.forms["expense"].action= url;
				 return false;
				 //document.forms["expense"].submit();
		 }

		function onSearch(request){

			 var searchString = document.getElementById("searchData").value;
			var url = "./expenseServlet?type="+request+"&searchData="+searchString;
			 //alert(" url  " +  url);
			 //return false;
			 document.forms["expense"].action= url;
			 document.forms["expense"].submit();         
		}
		
		function toggle(source) {
			  checkboxes = document.getElementsByName('checker');
			  for(var i=0, n=checkboxes.length;i<n;i++) {
			    checkboxes[i].checked = source.checked;
			  }
			}
		 
 </script>
 
 <style>

table {
    border: solid 1px #ff0000;
    border-collapse: collapse;
 
}

tr{
   border: solid 1px #ff0000;
    border-collapse: collapse;
}
td{
    border: solid 1px #ff0000;
    border-collapse: collapse;
}
</style>

<form name ='expense' method ='post' >
	<%  List<DailyReportTO> dailyReportTO = (List<DailyReportTO>)request.getAttribute("dailReportTOList"); 
	   	    
	%>
	
	    
	   <table border = "1" width = "85%">
	     <tr>
	         <td width ="15%"> Select All <Br><input type= 'checkbox' id = 'selectAll' , name='selectAll'  onClick="toggle(this)" />  </td>
		     <td> SUBJECT </td>
		     <td> REPORT DATE</td>
		     <td> MAIL TIME</td>
		    
		     
	    </tr>
	  
	<% if(dailyReportTO != null){ 
	   for(DailyReportTO drTo : dailyReportTO ) {
	 %>
		        <tr>
		          <td> <input type= 'checkbox' id = '<%=drTo.getID() %>' , name='checker' /> </td>
		          <td><%= drTo.getSUBJECT()%> </td>
		          <td><%= drTo.getSEND_DATE()%> </td>
		          <td><%= drTo.getTIME()%> </td>
		         
		           
		       </tr>
	      
	<% } } %>
	     
	     </table>
	     <br><br>
	     <input type = 'button' name = 'submit01' id = 'submit01'  value  = "Submit" onclick="javascript:onSubmit('submit','ALL')"/> 
	     &nbsp;&nbsp;&nbsp; 
	     <input type = 'button' name = 'submit02' id = 'submit02'  value  = "Delete" onclick="javascript:onSubmit('delete','ALL')" /> </td>
	     &nbsp;&nbsp;&nbsp; 
	     <input type = 'button' name = 'submit02' id = 'submit02'  value  = "View" onclick="javascript:onSubmit('view','ALL')" /> </td>
	     
	
	     
	    <%--    <br><br> <h3>
          <% if(message != null && message != "" )   		
    			out.println(" " + message) ;
       %> --%>
     </h3>  
  </form>
</body>
</html>