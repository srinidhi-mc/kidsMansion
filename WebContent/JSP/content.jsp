<%@page import="sun.security.util.PendingException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<style>

div {
    width: 1400px;
    word-wrap: break-word;
}

</style>

<script>
	function onSubmit(result) {
		// alert("on Submit " + result );
		if (result == 'searchSubmit') {
			var selected = document.getElementById("group").selectedIndex;
			var selection = document.getElementsByTagName("option")[selected].value
			var url = "./controllerServlet?type=" + result;
			// alert(" selection  " +  selection);
			if (selection == 'pendingDC') {
				var searchString = document.getElementById("searchData").value;
				url = "./controllerServlet?type=" + selection
						+ "&searchString=" + searchString;
			}

			document.forms["content"].action = url;
			// alert(" url = " + url);
			document.forms["content"].submit();
		} else if (result == 'addSubmit') {
			document.forms["content"].action = "./controllerServlet?type="
					+ result;
			document.forms["content"].submit();
		}
	}

	function onSubmitUpdate(result, studentId) {
		document.forms["content"].action = "./controllerServlet?type=" + result
				+ "&id=" + studentId;
		document.forms["content"].submit();
	}

	function goBack() {
		window.history.back()
	}
</script>

<body>
	<form name='content' method='post'>
		<%
			String resp = (String) request.getAttribute("type");
			String message = (String) request.getAttribute("message");
			ResultSet rs = (ResultSet) request.getAttribute("resultSet");
			String searchString = (String) request.getAttribute("searchString");
			String classValue = (String) request.getAttribute("classValue");
			StringBuffer emailList = null;
			String active = "1", yearClass = "", selList = "";
			if (request.getAttribute("active") != null)
				active = (String) request.getAttribute("active");
			if (request.getAttribute("yearClass") != null)
				yearClass = (String) request.getAttribute("yearClass");
			if (request.getAttribute("selList") != null)
				selList = (String) request.getAttribute("selList");

			if (request.getAttribute("emailDetails") != null)
				emailList = (StringBuffer) request.getAttribute("emailDetails");

			String studentId = (String) request.getAttribute("studentId");
			DecimalFormat moneyFormat = new DecimalFormat("#,##,##0.00");
			int unpaid = 0, fullTotal = 0;
			// // // // // System.out.println("content.jsp :: studentId " + studentId);
			// // // // // System.out.println("content.jsp :: resp " + resp);
			// // // // // System.out.println("content.jsp :: classValue " + classValue);
		%>

		<!--  Search Screen -->
		<%
			if (resp != null && resp.equalsIgnoreCase("Search")) {
		%>

		<h1>Search</h1>

		Search Option:
		<%=selList%>
		<!-- <Select id="group" name = "group">
						      <option value="Student">STUDENT</option>
						      <option value="DayCare">DAYCARE</option>
						      <option value="PG"> PG </option>
							  <option value="NURSERY">NURSERY</option>
							  <option value="LKG">LKG</option>
							  <option value="UKG">UKG</option>
							  <option value="NETBANKING">NET BANKING</option>
							  <option value="CHEQUE">CHEQUE</option>
						</Select>   -->
		<input type="text" name="searchData" id="searchData" /> <Select
			id="active" name="active">
			<option value="1">Active</option>
			<option value="0">Inactive</option>
		</Select> <Select id="yearClass" name="yearClass">
			<option value="14">2014-15</option>
			<option value="15">2015-16</option>
			<option value="16">2016-17</option>
			<option value="17">2017-18</option>
			<option value="18">2018-19</option>
			<option value="19">2019-20</option>
			<option value="20">2020-21</option>
			<option value="21">2021-22</option>
			<option value="22">2022-23</option>
			<option value="23"  selected="selected">2023-24</option>

		</Select> <br> <br> <input type="submit" value="Search"
			onclick="javascript:onSubmit('searchSubmit');" />

		<!--  Add Screen -->
		<%
			} else if (resp != null && resp.equalsIgnoreCase("Add")) {
		%>

		<h1>Add</h1>

		Name: &nbsp;&nbsp;<input type='text' name='name' /><br> Class:
		&nbsp;&nbsp; <Select name="class">
			<option value="PG">PG</option>
			<option value="NURSERY">NURSERY</option>
			<option value="LKG">LKG</option>
			<option value="UKG">UKG</option>
			<option value="1-STD">1-STD</option>
			<option value="2-STD">2-STD</option>
			<option value="DayCare">Day Care</option>
		</Select> <br> Email1: &nbsp;&nbsp; <input type="text" name="email1" /> <br>
		Email2: &nbsp;&nbsp; <input type="text" name="email2" /> <br> <Br>
		Year: &nbsp;&nbsp;<Select id="yearClass" name="yearClass">
			<option value="15">2015-16</option>
			<option value="16">2016-17</option>
			<option value="17">2017-18</option>
			<option value="18">2018-19</option>
			<option value="19"">2019-20</option>
			<option value="20">2020-21</option>
			<option value="21">2021-22</option>
			<option value="22" >2022-23</option>
			<option value="23" selected="selected" >2023-24</option>
		</Select> <br> <br> Active: &nbsp;&nbsp;<Select id="active"
			name="active">
			<option value="1">Active</option>
			<option value="0">Inactive</option>
		</Select> <br> Telephone1: &nbsp;&nbsp; <input type="text"
			name="telephone1" /> <br> Telephone2: &nbsp;&nbsp; <input
			type="text" name="telephone2" /> <br> <br> <br> <input
			type="button" value="Add" onclick="javascript:onSubmit('addSubmit');" />

		<%
			} else if (resp != null && resp.equalsIgnoreCase("fetchStudent")) {

				while (rs.next()) {
		%>

		<h1>Update</h1>
		Name: &nbsp;&nbsp;<input type='text' name='name'
			value="<%=rs.getString("NAME")%>" size=40 /><br> Class:
		&nbsp;&nbsp; <Select name="class">
			<option value="PG">PG</option>
			<option value="NURSERY">NURSERY</option>
			<option value="LKG">LKG</option>
			<option value="UKG">UKG</option>
			<option value="1-STD">1-STD</option>
			<option value="2-STD">2-STD</option>
			<option value="DayCare">Day Care</option>
		</Select> <br> <br> Email1: &nbsp;&nbsp; <input type="text"
			name="email1" value="<%=rs.getString("email_1")%>" size=75 /> <br>
		Email2: &nbsp;&nbsp; <input type="text" name="email2"
			value="<%=rs.getString("email_2")%>" size=75 /> <br> Year:
		&nbsp;&nbsp;<Select id="yearClass" name="yearClass">
			<!--  <option value="14">2014-15</option>
						      <option value="15"  >2015-16</option>
						      <option value="16">2016-17</option>
						       <option value="17" >2017-18</option> -->
			<option value="18">2018-19</option>
			<option value="19">2019-20</option>
			<option value="20">2020-21</option>
			<option value="21">2021-22</option>
			<option value="22" >2022-23</option>
			<option value="23" selected="selected" >2023-24</option>

		</Select> <br> Active: &nbsp;&nbsp;<Select id="active" name="active">
			<option value="1">Active</option>
			<option value="0">Inactive</option>
		</Select> <br> Telephone1: &nbsp;&nbsp; <input type="text"
			name="telephone1" value="<%=rs.getString("telephone_1")%>" /> <br>
		Telephone2: &nbsp;&nbsp; <input type="text" name="telephone2"
			value="<%=rs.getString("telephone_2")%>" /> <br> <br> <br>
		<input type="button" value="add"
			onclick="javascript:onSubmit('addSubmit');" /> <input type="button"
			value="update"
			onclick="javascript:onSubmitUpdate('update','<%=studentId%>');" />

		<%
			}

			} else if (resp != null && resp.equalsIgnoreCase("searchSubmit")) {
		%>

		<h1>Search Result</h1>


		<table border='1' width='100%'>
			<tr>
				<td>Sl#</td>
				<td>NAME</td>
				<td>CLASS</td>
				<td>EMAIL_1</td>
				<td>EMAIL_2</td>
				<td>Year</td>
				<%
					if (searchString != null && !searchString.equalsIgnoreCase("")
								&& !searchString.equalsIgnoreCase("null")
								&& !classValue.equalsIgnoreCase("STUDENT")
								&& !classValue.equalsIgnoreCase("DC_TO_SEND")) {
				%>
				<td>TOTAL</td>
				<td>PAID_DATE</td>
				<td>TYPE</td>
				<%
					if (classValue.equalsIgnoreCase("DayCare")) {
				%>
				<td>MONTH</td>
				<%
					} else {
				%>
				<td>TERM</td>

				<%
					}
						}
				%>
				<td>PAYMENT</td>
				</B>
			</tr>
			<%
				if (rs != null) {
						int count = 1;
						while (rs.next()) {
			%>
			<tr
				<%if (searchString != null
								&& !searchString.equalsIgnoreCase("")
								&& !searchString.equalsIgnoreCase("null")
								&& !classValue.equalsIgnoreCase("STUDENT")
								&& !classValue.equalsIgnoreCase("DC_TO_SEND")
								&& rs.getInt("STATUS") == 1) {%>
				BGCOLOR='#00FF40' <%}%>>
				<td><a
					href="./controllerServlet?id=<%=rs.getString(1)%>&type=fetchStudent">
						<%
							out.print(count);
						%>
				</a></td>
				<td>
					<%
						if (rs.getString("NAME") != null)
					%> <%
 	out.print(rs.getString("NAME"));
 %>
				</td>
				<td>
					<%
						if (rs.getString("CLASS") != null)
										out.print(rs.getString("CLASS"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("EMAIL_1") != null)
										out.print(rs.getString("EMAIL_1"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("EMAIL_2") != null)
										out.print(rs.getString("EMAIL_2"));
					%>
				</td>
				<td>
					<%
						String strYear = "NA";
									if (rs.getInt("YEAR") > 0) {
										if (rs.getInt("YEAR") == 14) {
											strYear = "2014-15";
										} else if (rs.getInt("YEAR") == 15) {
											strYear = "2015-16";
										} else if (rs.getInt("YEAR") == 16) {
											strYear = "2016-17";
										} else if (rs.getInt("YEAR") == 17) {
											strYear = "2017-18";
										} else if (rs.getInt("YEAR") == 18) {
											strYear = "2018-19";
										} else if (rs.getInt("YEAR") == 19) {
											strYear = "2019-20";
										} else if (rs.getInt("YEAR") == 20) {
											strYear = "2020-21";
										} else if (rs.getInt("YEAR") == 21) {
											strYear = "2021-22";
										} else if (rs.getInt("YEAR") == 22) {
											strYear = "2022-23";
										} else
											strYear = "2023-24";
									}
									out.print(strYear);
					%> <!-- Generic Search no search String provided --> <%
 	if (searchString != null
 						&& !searchString.equalsIgnoreCase("")
 						&& !searchString.equalsIgnoreCase("null")
 						&& !classValue.equalsIgnoreCase("STUDENT")
 						&& !classValue.equalsIgnoreCase("DC_TO_SEND")) {
 %>
				
				<td>
					<%
						if (rs.getString("TOTAL") != null)
											out.print(moneyFormat.format(Integer.valueOf(rs
													.getString("TOTAL"))));
					%>
				</td>
				<td>
					<%
						if (rs.getString("PAID_DATE") != null)
											out.print(rs.getString("PAID_DATE"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("TYPE") != null)
											out.print(rs.getString("TYPE"));
					%>
				</td>
				<!--      <td><%// if(rs.getString("TERM")!= null) out.print( rs.getString("TERM")) ;%> </td> -->
				<td>
					<%
						if (rs.getString("MONTH") != null)
											out.print(rs.getString("MONTH"));
					%>
				</td>
				<%
					if (rs.getInt("STATUS") == 0)
										unpaid += Integer
												.valueOf(rs.getString("TOTAL"));
									fullTotal += Integer.valueOf(rs.getString("TOTAL"));
								}
				%>
				<td><a
					href="./paymentServlet?id=<%=rs.getString(1)%>&type=fetchPayment&searchData=<%=searchString%>&active=<%=active%>&yearClass=<%=yearClass%>">
						Payments</a>
			</tr>
			<%
				count++;
						}
					}
			%>
		</table>

		<h4>
			Total Amount : &nbsp;
			<%=moneyFormat.format(fullTotal)%>
			&nbsp;&nbsp;&nbsp; Pending: &nbsp;
			<%=moneyFormat.format(unpaid)%>
		</h4>
		<!--  None Option Selected -->
		<%
			} else if (resp != null && resp.equalsIgnoreCase("pendingDC")) {
		%>

		<h1>Pending Day Care Fees</h1>

		<table border='1' width='100%'>
			<tr>
				<B>
					<td>NAME</td>
					<td>MONTH</td>
					<td>AMOUNT</td>
					<td>PAYMENT</td>
				</B>
			</tr>
			<%
				int pendingTotal = 0;
					if (rs != null) {
						while (rs.next()) {
							pendingTotal += rs.getInt("TOTAL");
			%>
			<tr>
				<td>
					<%
						if (rs.getString("NAME") != null)
										out.print(rs.getString("NAME"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("MONTH") != null)
										out.print(rs.getString("MONTH"));
					%>
				</td>
				<td>
					<%
						out.print(moneyFormat.format(Integer.valueOf(rs
											.getString("TOTAL"))));
					%>
				</td>
				<td><a
					href="./paymentServlet?id=<%=rs.getString("STUDENT_ID")%>&type=fetchPayment">
						Payments</a>
			</tr>
			<%
				}
					}
			%>
		</table>
		<br> <br> <B>Pending Fees for <%
 	if (searchString != null)
 			out.print(searchString);
 %>: &nbsp; &#x20b9;<%=moneyFormat.format(Integer.valueOf(pendingTotal))%>
		</B>

		<%
			} else if (resp != null && resp.equalsIgnoreCase("NETBANKING")) {
		%>

		<h1>NETBANKING DETAILS</h1>

		<table border='1' width='100%'>
			<tr>
				<B>
					<td>NAME</td>
					<td>CLASS</td>
					<td>PAID DATE</td>
					<td>TOTAL</td>
					<td>COMMENTS</td>
				</B>
			</tr>
			<%
				if (rs != null) {
						while (rs.next()) {
			%>
			<tr>
				<td>
					<%
						if (rs.getString("NAME") != null)
										out.print(rs.getString("NAME"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("CLASS") != null)
										out.print(rs.getString("CLASS"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("PAID_DATE") != null)
										out.print(rs.getString("PAID_DATE"));
					%>
				</td>
				<td>
					<%
						out.print(moneyFormat.format(Integer.valueOf(rs
											.getString("TOTAL"))));
					%>
				</td>
				<td>
					<%
						if (rs.getString("COMMENTS") != null)
										out.print(rs.getString("COMMENTS"));
					%>
				</td>
			</tr>
			<%
				}
					}
			%>
		</table>
		<br> <br>


		<%
			} else if (resp != null && resp.equalsIgnoreCase("CHEQUE")) {
		%>

		<h1>CHEQUE DETAILS</h1>

		<table border='1' width='100%'>
			<tr>
				<B>
					<td>NAME</td>
					<td>CHEQUE NO</td>
					<td>CHEQUE NAME</td>
					<td>CHEQUE DATE</td>
					<td>BRANCH</td>
					<td>CITY</td>
					<td>AMOUNT</td>
					<td>DEPOSITED DATE</td>
					<td>STATUS</td>
					<td>COMMENTS</td>
				</B>
			</tr>
			<%
				if (rs != null) {
						while (rs.next()) {
			%>
			<tr>
				<td>
					<%
						if (rs.getString("NAME") != null)
										out.print(rs.getString("NAME"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("CHEQUE_NO") != null)
										out.print(rs.getString("CHEQUE_NO"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("CHEQUE_NAME") != null)
										out.print(rs.getString("CHEQUE_NAME"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("CHEQUE_DATE") != null)
										out.print(rs.getString("CHEQUE_DATE"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("BRANCH") != null)
										out.print(rs.getString("BRANCH"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("CITY") != null)
										out.print(rs.getString("CITY"));
					%>
				</td>
				<td>
					<%
						out.print(moneyFormat.format(Integer.valueOf(rs
											.getString("AMOUNT"))));
					%>
				</td>
				<td>
					<%
						if (rs.getString("DEPOSITED_DATE") != null)
										out.print(rs.getString("DEPOSITED_DATE"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("STATUS") != null)
										out.print(rs.getString("STATUS"));
					%>
				</td>
				<td>
					<%
						if (rs.getString("COMMENTS") != null)
										out.print(rs.getString("COMMENTS"));
					%>
				</td>
			</tr>
			<%
				}
					}
			%>
		</table>
		<br> <br>

		<!--  Listing the Class Emails -->
		<%
			} else if (resp != null && resp.equalsIgnoreCase("emailList")) { 			
		%>
             <div> <% out.print(emailList); %> </div>


		<%
			} else {
		%>
		Select one of the above Option
		<%
			}

			if (rs != null) {
				rs.close();
				// // // // // System.out.println("content.jsp --> Result Set Closed ...");
			}
		%>



		<br> <br>
		<h3>
			<%
				if (message != null && message != "")
					out.println(" " + message);
			%>
		</h3>

	</form>
</body>
</html>