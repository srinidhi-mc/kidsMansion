<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List" %>
     <%@page import="kidsMansion.DailyReportTO" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 
 <link href="./js/jquery/jquery-ui.min.css" rel="stylesheet">
 <link rel="stylesheet" href="//jqueryui.com/jquery-wp-content/themes/jqueryui.com/style.css">
 <link href="./js/jquery/jquery-ui.structure.min.css" rel="stylesheet">
<link href="./js/jquery/jquery-ui.theme.min.css" rel="stylesheet">


</head>

<body>
<%  List<DailyReportTO> dailyReportTO = (List<DailyReportTO>)request.getAttribute("dailReportTOList");
	String selDate = (String)request.getAttribute("reportDate");
	// // // // // System.out.println(" date " + selDate);
%>


<script>
		

		function onSearchData(selDate){
			
			var selectDate  = document.getElementById(selDate).value;
			 var actionURL = "./mailSenderServlet?type=fetch&reportdate="+selectDate;
			 document.forms["dailReportStatus"].action= actionURL;
			 document.forms["dailReportStatus"].submit();

			
		}
		
		function onSubmit(id, type, selDate){
			var selected = '0';
			var selectDate  = document.getElementById(selDate).value;
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
	    	  
	    	  if(id == 'deleted') {
		    	  var confirmResponse = confirm("Delete All the selected records?");
				   if (!confirmResponse){  return false; 	}			   
			  }
		    	 
	    	 /*  if(id == 'view') {
	    		  var splited = selected.split(",")
	    		  if(splited.length > 2) {
	    			  alert("Select only one record to view at a time");
	    			  return false;
	    		  }
	    		  alert("inside");
	    		  document.getElementById("divModal").style.display = "block";
	    		  alert("Done");
	    	  } */
		         var url = "./mailSenderServlet?type="+id+"&id="+selected+"&reportdate="+selectDate;
		         document.forms["dailReportStatus"].action= url;
		         document.forms["dailReportStatus"].submit();
		 }

				
		function toggle(source) {
			  checkboxes = document.getElementsByName('checker');
			  for(var i=0, n=checkboxes.length;i<n;i++) {
				  if(! checkboxes[i].disabled)
			    		checkboxes[i].checked = source.checked;
			  }
			}
		
		$( function() {
		     $('.datepicker').each(function(){
		        $(this).datepicker({ dateFormat: 'dd-M-yy' }).val();
		      <%if(selDate!= null) { %> 
		      $(this).datepicker("setDate",<%=selDate%>);
		      <%}else{ %>
		        $(this).datepicker("setDate", new Date());
		      <% } %>  
		    });
		     
		    
		  } );
		
		function show (elements, specifiedDisplay) {
			 
			  var computedDisplay, element, index;

			  elements = elements.length ? elements : [elements];
			  for (index = 0; index < elements.length; index++) {
			    element = elements[index];

			    // Remove the element's inline display styling
			    element.style.display = '';
			    computedDisplay = window.getComputedStyle(element, null).getPropertyValue('display');

			    if (computedDisplay === 'none') {
			        element.style.display = specifiedDisplay || 'block';
			    }
			  }
			}

			function hide (elements) {
			  elements = elements.length ? elements : [elements];
			  for (var index = 0; index < elements.length; index++) {
			    elements[index].style.display = 'none';
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


<form name ='dailReportStatus' method ='post' >
	<%   String message = (String)request.getAttribute("message");  
             if(message != null && message != "" )   		
    			out.println(" <table><font size =4 color = red>" + message + " </font></table>") ;
    %>
	
	<br><br>
    		Date:  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
    		<input type="text" name = 'datepicker' id = 'datepicker' class="datepicker" size= '12'  <%if(selDate!= null) %> value = "<%=selDate%>" />
    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    		<input type='button' id='searchData' name='searchData' value = "Search" onClick= "javascript:onSearchData('datepicker');"/>
      <br><br>
	    
	   <table border = "1" width = "85%">
	     <tr>
	         <td width ="15%"> Select All <Br><input type= 'checkbox' id = 'selectAll' , name='selectAll'  onClick="toggle(this)" />  </td>
		     <td> SUBJECT </td>
		     <td> REPORT DATE</td>
		     <td> MAIL TIME</td>
		     <td> STATUS </td>
		         
		     
	    </tr>
	  
	<% if(dailyReportTO != null){ 
		int counter = 0;
	   for(DailyReportTO drTo : dailyReportTO ) {
	 %>
		        <tr>
		          <td> 
		             <input type= 'checkbox' id = '<%=drTo.getID() %>' , name='checker' <%if (drTo.getDELETED() == 1 || drTo.getSTATUS() ==1 || drTo.getTO_SEND() ==1){ %> disabled <%} %> /> </td>
		          <td><%= drTo.getSUBJECT()%> </td>
		          <td><%= drTo.getSEND_DATE()%> </td>
		          <td><%= drTo.getTIME()%> </td>
		          <td> <% if(drTo.getDELETED() ==1){ %> <b>DELETED</b>  
		          		<%} else if (drTo.getSTATUS() ==1) {%> <b>SENT</b> 
		          		<%} else if (drTo.getSTATUS() == -1) {%> <b><font color = 'red'>Failed! Resubmit</font> </b> 
		          		<% } else if (drTo.getTO_SEND() == 1){ %> <b> IN_PROCESS </b>
		          		
		          		<% } else { %> READY <% } %>
		           </td>
		          
		       </tr>
	      
	<% counter++ ;} } %>
	     
	     </table>
	     <br><br>
	     <input type = 'button' name = 'submit01' id = 'submit01'  value  = "Send Mail" onclick="javascript:onSubmit('submit','ALL','datepicker')"/> 
	     &nbsp;&nbsp;&nbsp; 
	     <input type = 'button' name = 'submit02' id = 'submit02'  value  = "Delete Report" onclick="javascript:onSubmit('deleted','ALL','datepicker')" /> 
	     &nbsp;&nbsp;&nbsp; 
	     <input type = 'button' name = 'submit02' id = 'submit02'  value  = "View Report" onclick="javascript:show(document.querySelectorAll('.target'));" /> 
	      &nbsp;&nbsp;&nbsp; 
	     <input type = 'button' name = 'submit02' id = 'submit02'  value  = "Hide Report" onclick="javascript:hide(document.querySelectorAll('.target'));" /> 
		  <br>
		  <H4><table>Note:Active Reports will be displayed.</table> </H4>
		  <br>
		<% if(dailyReportTO != null){
			boolean isPGDone= false,isNURDone= false,isLKGDone= false,isUKGDone= false, isFirstDone=false, isSecondDone=false;
			
		int counter = 0;
	  		 for(DailyReportTO drTo : dailyReportTO ) {
		 %>
		 <% //// // // // // System.out.println("entered 0 " + isPGDone  + " subject " + drTo.getSUBJECT() + " - " + (drTo.getSUBJECT()).indexOf("Group")); %>
		 <% if(((drTo.getSUBJECT()).indexOf("Play")>-1) && !isPGDone && drTo.getDELETED() == 0){ %>
			<div class="target" style="display: none; width:900px;">
					<%=drTo.getCONTENT() %> <hr align = 'left' width ='70%'> <br><br>
			 </div>
		 <% isPGDone = true ;
		 } %>
		 
		  <% if((drTo.getSUBJECT().indexOf("NURSERY")>-1) && !isNURDone && drTo.getDELETED() == 0){ %>
			<div class="target" style="display: none; width:900px;">
					<%=drTo.getCONTENT() %> <hr align = 'left' width ='70%'>  <br><br>
			 </div>
		 <% isNURDone = true; } %>
		 
		 <% if((drTo.getSUBJECT().indexOf("LKG")>-1) && !isLKGDone && drTo.getDELETED() == 0){ %>
			<div class="target" style="display: none; width:900px;">
					<%=drTo.getCONTENT()%> <hr align = 'left' width ='70%'> <br><br>
			 </div>
		 <% isLKGDone = true; } %>
		 
		 <% if((drTo.getSUBJECT().indexOf("UKG")>-1) && !isUKGDone && drTo.getDELETED() == 0){ %>
			<div class="target" style="display: none; width:900px;">
					<%=drTo.getCONTENT()%> <hr align = 'left' width ='70%'>  <br><br>
			 </div>
		 <% isUKGDone = true; } %>
		 
		  <% if((drTo.getSUBJECT().indexOf("1-STD")>-1) && !isFirstDone && drTo.getDELETED() == 0){ %>
			<div class="target" style="display: none; width:900px;">
					<%=drTo.getCONTENT()%> <hr align = 'left' width ='70%'>  <br><br>
			 </div>
		 <% isFirstDone = true; } %>
		 
		 <% if((drTo.getSUBJECT().indexOf("2-STD")>-1) && !isSecondDone && drTo.getDELETED() == 0){ %>
			<div class="target" style="display: none; width:900px;">
					<%=drTo.getCONTENT()%> <hr align = 'left' width ='70%'>  <br><br>
			 </div>
		 <% isSecondDone = true; } %>
		 
		 
	     
	    <% } } %>
     </h3>  
  </form>
</body>
</html>