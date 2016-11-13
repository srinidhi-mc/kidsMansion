<%@page import="kidsMansion.mailerTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Send Mail</title>
</head>

<script>
 
   function onSubmit(){
	   
	   var confirmResponse = confirm("Send Mail...?");
	
	   if (confirmResponse){
		   alert("Mail is being sent")
	   }else{
		   return;
	   }
	   
	   var selected = document.getElementById("group").selectedIndex;
	    var selection = document.getElementsByTagName("option")[selected].value
	     var actionURL = "./mailSenderServlet?type=sendMail&selection="+selection;
	   	 document.forms["mailStatus"].action= actionURL;
		 document.forms["mailStatus"].submit();
	   
	 
   }
 
 
 </script>
<body>
 <form name="mailStatus" id = "mailStatus" method="post">
 
 
 
   <% List<mailerTO> mailStatus = (List<mailerTO>)request.getAttribute("mailStatus"); 
      if(mailStatus.size() == 0) {
   %>
       <H4>NO PENDING MAILS IN SENDING STATUS </H4>
 
  <% }else {   %>
	   <table border = "1" width = "60%">
	   <tr> <td>ID</td><td> CREATED DATE </td> <td>SUBJECT</td><td> ATTACH FILE </td> </tr>
	    
	    <H4> Has Mails Pending </H4>
	    <% for(mailerTO l: mailStatus ) {%>
	        <tr>
	          <td><% out.print(l.getID()) ;%> </td>
	           <td><%   out.print(l.getCREATE_DATE()); %> </td>
	            <td><%   out.print(l.getSUBJECT()); %> </td>
	            <td><%   out.print(l.getATTACHMENT_FILE()); %> </td>
	           
	        </tr>
	    
	    <% } %>
	    
	   </table> 
	    <br><br>
   <% } %>
  
   Send Mail:  		  <Select id="group" name = "group">
						      <option value="DayCare">DayCare</option>
						      <option value="PG"> PG </option>
							  <option value="NURSERY">NURSERY</option>
							  <option value="LKG">LKG</option>
							  <option value="UKG">UKG</option>
							  <option value="All">All School</option>
						</Select> 
						<br><br>    
     Subject: &nbsp;&nbsp;&nbsp; <input type ="text" id="subject" name="subject" size = 50/>
     <br><br>
      Message: <br><textarea rows="10" cols="100" id = "message" name="message"></textarea> 
      <br><br>
      Attachments:  <br>Path:&nbsp;&nbsp;<input type="text" name="uploadFile01" id="uploadFile01" size="100" /> File Name:&nbsp;&nbsp;<input type="text" name="FileName01" id="FileName01" size="50" /><br>
                     Path:&nbsp;&nbsp;<input type="text" name="uploadFile02" id="uploadFile02" size=100" /> File Name:&nbsp;&nbsp;<input type="text" name="FileName02" id="FileName02" size="50" /><br>
      				 Path:&nbsp;&nbsp;<input type="text" name="uploadFile03" id="uploadFile03" size="100" /> File Name:&nbsp;&nbsp;<input type="text" name="FileName03" id="FileName03" size="50" /><br>
      <Br><input type="button" name= "submitMail" value ="Submit" size='5' id ="submitMail" onClick="javascript:onSubmit();"/>  
      
 </form>
</body>
</html>