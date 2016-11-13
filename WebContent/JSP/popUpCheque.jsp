<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>

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

function goBack() {
    window.history.back();
}

function onSubmit(paymentId,studentID){
	 var actionURL
	 if(paymentId == '-99' && studentID == '-99') {
		 actionURL= "./chequeServlet?type=updateChq&id=-1";
	 }else {
		 actionURL = "./chequeServlet?type=updateChq&redirect=true&paymentID0="+paymentId+"&id=-1&studentID="+studentID;
	 }
	  document.forms["popUpCheque"].action= actionURL;
	  document.forms["popUpCheque"].submit();	
	
}
<% /*
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
*/
%>

</script>

	<%    
      String reqType = (String)request.getAttribute("type");
      List<String> banks  = (List<String>)request.getAttribute("bank");
      List<String>  branch  = (List<String>)request.getAttribute("branch");
      List<String>  city  = (List<String>)request.getAttribute("city");
      List<String>  issuedBy = (List<String>)request.getAttribute("issuedBy");
      int paymentID = 0, studentID = 0;
      if (request.getAttribute("paymentId") != null)
    	  paymentID = (Integer)request.getAttribute("paymentId");
      if(request.getAttribute("studentId") != null)
        studentID = (Integer)request.getAttribute("studentId");
      

%>


	<form name='popUpCheque' id='popUpCheque' method='post'>
		<input type="hidden" name="chqStatus0" id="chqStatus0" value="TOBE" />

		<h3>Cheque Details</h3>
		<%-- <% out.print( "banks " + banks.size() + "Branch " + branch.size()); %> --%>
		<table width="50%" height="50%">
			<tr>
				<td>Student Name</td>
				<% if( reqType != null && reqType.equalsIgnoreCase("addCheque")) {
    	 List<String>  stuName  = (List<String>)request.getAttribute("stuName");
   	 %>
				<td><select id='name0' name='name0'>
						<option value='NA'></option>
						<% for(String sName:stuName)  { %>
						<option value="<%=sName%>"><%=sName%>
						</option>

						<% } %>

				</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="name1"
					id="name1" /></td>
				<% } else { %>
				<td><input type='text' name='name0' id='name0'
					value="<%=(String)request.getAttribute("studentName")%>"
					readonly="readonly" /></td>
				<% } %>
			</tr>
			<tr></tr>
			<tr>
				<td>Issued By</td>
				<td><select id='issued0' name='issued0'>
						<option value='NA'></option>
						<% for(String issued:issuedBy)  { %>
						<option value="<%=issued%>"><%=issued%>
						</option>

						<% } %>

				</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type='text' name='issued1'
					id='issued1' size='35' /></td>
			</tr>
			<tr></tr>
			<tr>
				<td>Cheque No</td>
				<td><input type='text' name='chqueNO0' id='chqueNO0' /></td>
			</tr>
			<tr></tr>
			<tr>
				<td>Bank</td>
				<td><select id='bank0' name='bank0'>
						<option value='NA'></option>
						<% for(String bank:banks)  { %>
						<option value="<%=bank %>"><%=bank%>
						</option>
						<% } %>

				</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="bankName"
					id="bankName" /></td>
			</tr>
			<tr></tr>
			<tr>
				<td>Branch</td>
				<td><select id='branch0' name='branch0'>
						<option value='NA'></option>
						<% for(String branchS: branch) { %>
						<option value="<%=branchS %>"><%=branchS %>
						</option>
						<% } %>
				</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="bankBranch"
					id="bankBranch" /></td>
			</tr>
			<tr></tr>

			<tr>
				<td>City</td>
				<td><select id='city0' name='city0'>
						<option value='NA'></option>
						<% for(String cityS: city) { %>
						<option value="<%=cityS %>"><%=cityS %>
						</option>
						<% } %>
				</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="cityName"
					id="cityName" /></td>
			</tr>
			<tr></tr>



			<tr>
				<td>Cheque Date</td>
				<td><input type="text" name="chequeDt0" id="chequeDt0" size='5' />
					<a
					href="javascript:show_calendar('document.popUpCheque.chequeDt0', document.popUpCheque.chequeDt0.value);">
						<img src="./js/datepick/cal.gif" width="16" height="16" border="0"
						alt="Click Here to Pick up the timestamp">
				</a></td>
			</tr>
			<tr></tr>
			<tr>
				<td>Amount</td>
				<td><input type="text" name="amount0" id="amount0" size='8' />
				</td>
			</tr>
			</tr>
			<tr>
			<tr>
				<td>Deposit Account</td>
				<td><select Name='comment0' id='comment0'>
						<option value="KM AC">KM</option>
						<option value="VINAYA">VINAYA</option>
				</select></td>
			</tr>


		</table>
		<br>
		<br>
		<% if( reqType != null && reqType.equalsIgnoreCase("addCheque")) { %>
		<input type='button' id='submitBtn' name='submitBtn'
			onClick='javascript:onSubmit(-99, -99)' value="Submit" />
		<% } else { %>
		<input type='button' id='submitBtn' name='submitBtn'
			onClick='javascript:onSubmit(<%=paymentID%>, <%=studentID%>)'
			value="Submit" />
		<% } %>
		&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
		<input type="button" id='backBtn' name='backBtn' onClick='goBack();'
			value='Back' />


	</form>
</body>
</html>