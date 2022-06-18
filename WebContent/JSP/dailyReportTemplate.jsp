<%@page import="kidsMansion.mailerTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Daily Report</title>
</head>
 <link href="./js/jquery/jquery-ui.min.css" rel="stylesheet">
 <link rel="stylesheet" href="//jqueryui.com/jquery-wp-content/themes/jqueryui.com/style.css">
 <link href="./js/jquery/jquery-ui.structure.min.css" rel="stylesheet">
<link href="./js/jquery/jquery-ui.theme.min.css" rel="stylesheet">
<!--  <link rel="stylesheet" href="//jqueryui.com/jquery-wp-content/themes/jquery/css/base.css?v=1"> --> 

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<% String selDate = (String)request.getAttribute("reportDate");
   String classString = (request.getAttribute("classString")!= null? (String)request.getAttribute("classString"):"");
   List<String> fetchReportData = (request.getAttribute("fetchReportData")!= null? (List<String>)request.getAttribute("fetchReportData"):null);  
   System.out.println(" dailyReportTemplate.jsp: date " + selDate + " classString " + classString + " fetchReportData " + fetchReportData); 
 %>

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



<script>
 
   function onSubmit(param1, dateElement, isFetchRptData){

	   var sendDate  = document.getElementById(dateElement).value;
	   var confirmResponse = confirm("Schedule Mail for Class: " + param1 + " Date:" + sendDate);
	
	   if (!confirmResponse){
		   return false;
	   }
	     var actionURL = "./dailyReportServlet?type="+param1+"&reportdate="+sendDate;
	  /*   if (isFetchRptData != null && isFetchRptData == 'true') {
	    	actionURL = actionURL + "&isFetchRptData=true"
	    } */
	    
		 document.forms["dailyReport"].action= actionURL;
		 document.forms["dailyReport"].submit();
   }
   
   
   function onLoad(){
	   <%if(classString.equalsIgnoreCase("PG")){ %>
	   		$( "#tabs" ).tabs({ active: 0 });
	   <% }else if(classString.equalsIgnoreCase("NURSERY")){%>
	  	 	$( "#tabs" ).tabs({ active: 1 });
	   <%}else if(classString.equalsIgnoreCase("L.K.G")){%>
	   		$( "#tabs" ).tabs({ active: 2 });
	   <%}else if(classString.equalsIgnoreCase("U.K.G")){%>
	   		$( "#tabs" ).tabs({ active: 3 });
	   <%}else if(classString.equalsIgnoreCase("I-STD")){%>
	   		$( "#tabs" ).tabs({ active: 4 });
	   <%}%>
   }
 
 
 </script>
<body onLoad= "javascript:onLoad();">

 <form name="dailyReport" id = "dailyReport" method="post" >
 <script>

$( function() {
    $( "#tabs" ).tabs();

    $('.datepicker').each(function(){
        $(this).datepicker({ dateFormat: 'dd-M-yy' }).val();
        <%if(selDate!= null) { %> 
	      $(this).datepicker("setDate",<%=selDate%>);
	      <%}else{ %>
	        $(this).datepicker("setDate", new Date());
	      <% } %>  
    });
    /* $( "#datepicker" ).datepicker({ dateFormat: 'dd-M-yy' }).val();
    $( "#datepicker" ).datepicker("setDate", new Date()); */
  } );


  </script>
</head>
<body>
 
<div id="tabs">
  <ul>
    <!-- <li><a href="#tabs-4">Mail Status</a></li> -->
    <li><a href="#tabs-1">Play Group</a></li>
    <li><a href="#tabs-2">Nursery</a></li>
    <li><a href="#tabs-3">L.K.G</a></li>
    <li><a href="#tabs-4">U.K.G</a></li>
    <li><a href="#tabs-5">1-STD </a></li>
    
  </ul>
  <%   String message = (String)request.getAttribute("message");  
             if(message != null && message != "" )  
    			out.println(" <table><font size =4 color = red>" + message + " </font></table>") ;
  %>
  <H5><b>Note: </b>Add &lt;BR&gt; at the end of the current line for the pointer to be printing on next line in the mail.</H5>
  <!--  PLAY GROUP -->
  <div id="tabs-1">
     		Time    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		 Activity
     <br><br>
    		Date:  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
    		<input type="text" name = 'datepicker' id = 'datepicker' class="datepicker"  <%if(selDate!= null) %> value = "<%=selDate%>" />
    		&nbsp;  <!--   <input type="submit" value ="Search" onclick="javascript:onSubmit('PG','datepicker','true')"/> -->
      <br><br>
      	  
   			<input type= 'text' id='poneT' name= 'poneT' value='9:30 To 9:45 AM' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="1"  id='poneA' name = 'poneA'>Circle Time(Prayer , National Anthem & Rhymes with action)</textarea>
       <br><br>
            <input type= 'text' id='ptwoT' name= 'ptwoT' value='9:45 to 10:00 AM' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="1" id='ptwoA' name = 'ptwoA'></textarea>
		<br><br>
   			<input type= 'text' id='pthreeT' name= 'pthreeT' value='10:00 to 10:20 AM' length = 75/> &nbsp;&nbsp;&nbsp;
    		 <textarea cols="80" rows="1" id='pthreeA' name = 'pthreeA'>Play Time</textarea>
 		 <br><br>
   			<input type= 'text' id='pfourT' name= 'pfourT' value='10:20 to 11:00 AM' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="3" id='pfourA' name = 'pfourA'></textarea>
    	<br><br>
     		 <input type= 'text' id='pfiveT' name= 'pfiveT' value='11:00 to 11:20 AM' length = 75/> &nbsp;&nbsp;&nbsp;
   		 	<textarea cols="80" rows="1" id='pfiveA' name = 'pfiveA'>Snacks Break</textarea>
   		<br><br>
   			<input type= 'text' id='pSixT' name= 'pSixT' value='11:30 to 11:50 AM' length = 75/> &nbsp;&nbsp;&nbsp;
   			 <textarea cols="80" rows="1" id='pSixA' name = 'pSixA'></textarea>
   	 		 
     <br><br>
    		 <input type="submit" value ="Submit" onclick="javascript:onSubmit('PG','datepicker')"/>
     <hr><hr>
       
  </div>
  
  <!--  NURSERY -->
  <div id="tabs-2">
     		Time    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		 Activity
     <br><br>
    		Date:  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
    		<input type="text" name = 'datepicker1' id = 'datepicker1' class="datepicker"  <%if(selDate!= null) %> value = "<%=selDate%>"/>
      <br><br>
   			<input type= 'text' id='noneT' name= 'noneT' value='9:30 To 9:45 AM' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="1"  id='noneA' name = 'noneA'>Circle Time(Prayer , National Anthem & Rhymes with action)</textarea>
       <br><br>
            <input type= 'text' id='ntwoT' name= 'ntwoT' value='9:45 to 10:05 AM' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="1" id='ntwoA' name = 'ntwoA'>Play Time</textarea>
		<br><br>
   			<input type= 'text' id='nthreeT' name= 'nthreeT' value='10:10 to 10:30 AM' length = 75/> &nbsp;&nbsp;&nbsp;
    		 <textarea cols="80" rows="1" id='nthreeA' name = 'nthreeA'></textarea>
 		 <br><br>
   			<input type= 'text' id='nfourT' name= 'nfourT' value='10:30 to 11:10 AM' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="3" id='nfourA' name = 'nfourA'></textarea>
    	<br><br>
     		 <input type= 'text' id='nfiveT' name= 'nfiveT' value='11:10 to 11:30 AM' length = 75/> &nbsp;&nbsp;&nbsp;
   		 	<textarea cols="80" rows="1" id='nfiveA' name = 'nfiveA'>Snacks Break</textarea>
   		<br><br>
   			<input type= 'text' id='nSixT' name= 'nSixT' value='11:40 to 12:00 Noon' length = 75/> &nbsp;&nbsp;&nbsp;
   			 <textarea cols="80" rows="1" id='nSixA' name = 'nSixA'></textarea>
   		<br><br>
   			<input type= 'text' id='nSevenT' name= 'nSevenT' value='12:00 Noon to 12:20 PM' length = 75/> &nbsp;&nbsp;&nbsp;
   			 <textarea cols="80" rows="1" id='nSevenA' name = 'nSevenA'></textarea>
     <br><br>
    		 <input type="submit" value ="Submit" onclick="javascript:onSubmit('NURSERY', 'datepicker1')"/>
     <hr><hr>
       
  </div>
  
  <!--  L.K.G -->
  <div id="tabs-3">
     		Time    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		 Activity
     <br><br>
    		Date:  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
    		<input type="text" name = 'datepicker2' id = 'datepicker2' class="datepicker"  <%if(selDate!= null) %> value = "<%=selDate%>"/>
      <br><br>
   			<input type= 'text' id='LoneT' name= 'LoneT' value='9:30 To 9:45 AM' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="1"  id='LoneA' name = 'LoneA'>Circle Time(Prayer , National Anthem & Rhymes with action)</textarea>
       <br><br>
            <input type= 'text' id='LtwoT' name= 'LtwoT' value='9:45 to 10:00 AM' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="1" id='LtwoA' name = 'LtwoA'></textarea>
		<br><br>
   			<input type= 'text' id='LthreeT' name= 'LthreeT' value='10:00 to 11:00 AM' length = 75/> &nbsp;&nbsp;&nbsp;
    		 <textarea cols="80" rows="3" id='LthreeA' name = 'LthreeA'></textarea>
 		 <br><br>
   			<input type= 'text' id='LfourT' name= 'LfourT' value='11:00 to 11:20 AM' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="1" id='LfourA' name = 'LfourA'>Snacks Break</textarea>
    	<br><br>
     		 <input type= 'text' id='LfiveT' name= 'LfiveT' value='11:20 to 11:40 AM' length = 75/> &nbsp;&nbsp;&nbsp;
   		 	<textarea cols="80" rows="1" id='LfiveA' name = 'LfiveA'>Play Time</textarea>
   		<br><br>
   			<input type= 'text' id='LSixT' name= 'LSixT' value='11:40 to 12:00 Noon' length = 75/> &nbsp;&nbsp;&nbsp;
   			 <textarea cols="80" rows="3" id='LSixA' name = 'LSixA'></textarea>
   		<br><br>
   			<input type= 'text' id='LSevenT' name= 'LSevenT' value='12:00 to 12:30 PM ' length = 75/> &nbsp;&nbsp;&nbsp;
   			<textarea cols="80" rows="2" id='LSevenA' name = 'LSevenA'></textarea>
   		<br><br>
   			<input type= 'text' id='LEightT' name= 'LEightT' value='12:30 to 12:50 PM ' length = 75 /> &nbsp;&nbsp;&nbsp;
   			<textarea cols="80" rows="3" id='LEightA' name = 'LEightA' ></textarea>
     <br><br>
    		 <input type="submit" value ="Submit" onclick="javascript:onSubmit('L.K.G', 'datepicker2')"/>
     <hr><hr>
       
  </div>
   
   
   
  <!--  U.K.G -->
  <div id="tabs-4">
     		Time    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		 Activity
     <br><br>
    		Date:  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
    		<input type="text" name = 'datepicker3' id = 'datepicker3' class="datepicker"  <%if(selDate!= null) %> value = "<%=selDate%>"/>
      <br><br>
   			<input type= 'text' id='UoneT' name= 'UoneT' value='9:30 To 10:00 AM' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="3"  id='UoneA' name = 'UoneA'>Circle Time(Prayer , National Anthem & Rhymes with action)</textarea>
       <br><br>
            <input type= 'text' id='UtwoT' name= 'UtwoT' value='10:00 to 11:00 AM' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="3" id='UtwoA' name = 'UtwoA'></textarea>
		<br><br>
   			<input type= 'text' id='UthreeT' name= 'UthreeT' value='11:00 to 11:20 AM' length = 75/> &nbsp;&nbsp;&nbsp;
    		 <textarea cols="80" rows="1" id='UthreeA' name = 'UthreeA'>Snacks Break</textarea>
 		 <br><br>
   			<input type= 'text' id='UfourT' name= 'UfourT' value='11:20 to 11:40 AM' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="1" id='UfourA' name = 'UfourA'>Play Time</textarea>
    	<br><br>
     		 <input type= 'text' id='UfiveT' name= 'UfiveT' value='11:40 to 12:10 PM' length = 75/> &nbsp;&nbsp;&nbsp;
   		 	<textarea cols="80" rows="3" id='UfiveA' name = 'UfiveA'></textarea>
   		<br><br>
   			<input type= 'text' id='USixT' name= 'USixT' value='12:10 to 12:30 Noon' length = 75/> &nbsp;&nbsp;&nbsp;
   			 <textarea cols="80" rows="3" id='USixA' name = 'USixA'></textarea>
   	 	<br><br>
   	 		<input type= 'text' id='USevenT' name= 'USevenT' value='12:30 to 12:50 PM' length = 75/> &nbsp;&nbsp;&nbsp;
   			 <textarea cols="80" rows="3" id='USevenA' name = 'USevenA'></textarea>
   	 <br><br>
    		 <input type="submit" value ="Submit" onclick="javascript:onSubmit('U.K.G', 'datepicker3')"/>
     <hr><hr>
  </div>
  
   <!--  1-STD -->
  <div id="tabs-5">
  		Time    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      		 Activity
     <br><br>
    		Date:  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
    		<input type="text" name = 'datepicker4' id = 'datepicker3' class="datepicker"  <%if(selDate!= null) %> value = "<%=selDate%>"/>
      <br><br>
   			<input type= 'text' id='FoneT' name= 'FoneT' value='Assembly' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="3"  id='FoneA' name = 'FoneA'>Circle Time(Prayer , National Anthem & Rhymes with action)</textarea>
       <br><br>
            <input type= 'text' id='FtwoT' name= 'FtwoT' value='ENGLISH' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="3" id='FtwoA' name = 'FtwoA'></textarea>
		<br><br>
   			<input type= 'text' id='FthreeT' name= 'FthreeT' value='KANNADA' length = 75/> &nbsp;&nbsp;&nbsp;
    		 <textarea cols="80" rows="3" id='FthreeA' name = 'FthreeA'></textarea>
 		 <br><br>
   			<input type= 'text' id='FfourT' name= 'FfourT' value='HINDI' length = 75/> &nbsp;&nbsp;&nbsp;
     		<textarea cols="80" rows="3" id='FfourA' name = 'FfourA'></textarea>
     	<br><br>
   			<input type= 'text' id='FfiveT' name= 'FfiveT' value='MATH' length = 75/> &nbsp;&nbsp;&nbsp;
   		 	<textarea cols="80" rows="3" id='FfiveA' name = 'FfiveA'></textarea>
   		<br><br>
   			<input type= 'text' id='FSixT' name= 'FSixT' value='General Awareness' length = 75/> &nbsp;&nbsp;&nbsp;
   			 <textarea cols="80" rows="3" id=FSixA' name = 'FSixA'></textarea>
   		<br><br>
   			<input type= 'text' id='FSevenT' name= 'FSevenT' value='EVS' length = 75/> &nbsp;&nbsp;&nbsp;
   			 <textarea cols="80" rows="3" id=FSevenA' name = 'FSevenA'></textarea>
   	 	<br><br>
   	 		 <input type="submit" value ="Submit" onclick="javascript:onSubmit('I-STD', 'datepicker3')"/>
     <hr><hr>
  
  
  
  
  </div>
  
</div> <!--  Final Div -->

  </form>
</body>
</html>