<!--  http://salarycalculator.co.in/calculator.php -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>KIDS MANSION</title>
	</head>
	<script src="js/jquery-1.11.1.min.js"></script> 
	
	 <script>
			$().ready = function() {
			     alert("Inovked");
			     $('#studentInfo').hide();
			     $('#search').hide();
		     
			    $("#Add").click(function() {
			           alert("add event");
			           $('#studentInfo').visible();
			           $('#search').hide();
			    });
		    
			     $("#Update").click(function() {
			    	   alert("search event");
			            $('#search').toggle();
			            $('#studentInfo').hide();
			    });
			
		}();
	 
	 
	 
	 </script>
	
	<body>
		<form>
		
		   <table>
			    <tr>
			        <td> <input type="button" value="Add" id="Add" /> </td>
			        <td><input type ="button" value="Search" id="Search" /></td>
			    </tr>
		 </table>  
		<br><br>
		<div id = "search">
		   Search   <input type="text" id="text" /> 
		</div>
		
		 <div id = "studentInfo">
		 
		  Name111   <input type="text" id="text" /> 
		   <!-- <fieldset>
			  <legend>Student Details</legend>
			  Name: <input type="text"><br>
			  Class:  <input list="group">
					  <datalist id="group">
						  <option value="PG">
						  <option value="NURSERY">
						  <option value="LKG">
						  <option value="UKG">
						  <option value="DayCare">
					</datalist>
			  <br>
			  Email1: <input type="text"><br>
			  Email2: <input type="text"><br>
			  Telephone1: <input type="text"><br>
			  Telephone2: <input type="text"><br>
			 
			</fieldset> -->
		 </div>	
		  
		</form>
	
	
	</body>
</html>