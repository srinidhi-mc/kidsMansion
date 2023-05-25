package kidsMansion;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class controllerDAO {
	
	  private static Connection connect = null;
	  private static Statement statement = null;
	 // private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;
	  private boolean error = false;
	  static String  months[] = { "JAN", "FEB", "MAR", "APR","MAY", "JUN","JUL","AUG","SEP","OCT","NOV","DEC" } ;
	
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Connection getConnection(){
		 try {
			 if(connect != null ) {
				 System.out.println("controllerDAO:getConnection::Returning the existing connection");
				 return connect ;
			 }
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/KM?user=root&password=root");
			connect.setAutoCommit(true);
			System.out.println("controllerDAO:getConnection::Created a new connection");
		  } catch (ClassNotFoundException e) {
			  setError(true);
			e.printStackTrace();
		  } catch (SQLException e) {
			  setError(true);
			e.printStackTrace();
		  }
		return connect;
	}
	
	public ResultSet getResult(String sql) {
		
		try {
			if(statement == null) {
				System.out.println("controllerDAO:getResult::Created a new statement");
				statement = getConnection().createStatement();
			}
			 resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			  setError(true);
			e.printStackTrace();
			closeAll();
		} /*finally{
			
			closeConStat();
			
		}*/
		
		
		return resultSet ;
	}
	
	public int addUser(String sql){
		int successful = -1;
		try {
			
			successful =  getConnection().createStatement().executeUpdate(sql);
			
		} catch (SQLException e) {
			  setError(true);
			e.printStackTrace();
		}finally{
			closeAll();
		}
		
		return successful;
	}
	
	public void closeAll(){
		try {
			System.out.println("controllerDAO:closeAll::Invoking");
			if(connect != null ){ connect.close(); System.out.println("controllerDAO:closeAll::connection Closed"); }
			if(statement != null ){ statement.close(); System.out.println("controllerDAO:closeAll::Statement closed");}
			if(resultSet !=null) { resultSet.close(); System.out.println("controllerDAO:closeAll::resultset closed"); }
		} catch (SQLException e) {
			  setError(true);
			e.printStackTrace();
		}finally{
			connect =null;
			statement= null;
			resultSet = null;
			
		}
		
	}
		public void closeConStat(){
			try {
				System.out.println("controllerDAO:closeAll::Invoking");
				if(connect != null ){ connect.close(); System.out.println("controllerDAO:closeConStat::connection Closed"); }
				if(statement != null ){ statement.close(); System.out.println("controllerDAO:closeConStat::Statement closed");}
				//if(resultSet !=null) { resultSet.close(); System.out.println("controllerDAO:closeAll::resultset closed"); }
			} catch (SQLException e) {
				  setError(true);
				  e.printStackTrace();
			}finally{
				connect =null;
				statement= null;
								
			}
			
		
		
		}
		
		
		public static String getMonth(String month){
			boolean found = false;
			for(String it: months){
				if(found){
					System.out.println("controllerDAO:getMonth::month Found " + it);
					return it;
				}				
				if(it.equalsIgnoreCase(month)){
					 found = true;
				}  
				
			}
			
			System.out.println("controllerDAO:getMonth::month " + month);
			return month;
		}
	
	
		public int returnPaymentId(String sql){
			int rtnValue = 0;
			Statement stmt ;
			ResultSet rset ;
			try {
				
				stmt = getConnection().createStatement();
				rset = stmt.executeQuery(sql);
				 while(rset.next())
				    rtnValue = rset.getInt(1);
				 rset.close();
				 stmt.close();
							 
			} catch (SQLException e) {
				  setError(true);
				e.printStackTrace();
				
			}
			
			System.out.println("controllerDAO:returnPaymentId::PaymentID = " + rtnValue) ;
			return rtnValue ;
			
			
		}
		
		public int isMailSent(String sql){
			int rtnValue = 0;
			Statement stmt ;
			ResultSet rset ;
			try {
				
				stmt = getConnection().createStatement();
				rset = stmt.executeQuery(sql);
				 while(rset.next())
				    rtnValue = rset.getInt("SENTMAIL");
				 rset.close();
				 stmt.close();
							 
			} catch (SQLException e) {
				  setError(true);
				e.printStackTrace();
				
			}
			
			System.out.println("controllerDAO:returnPaymentId::isMailSent = " + rtnValue) ;
			return rtnValue ;
			
			
		}
		
		// execute the sql and populate the ArrayList for one value only.
		public List<String> createRSList(String sql) {
		 List<String> rtnList = new ArrayList<String>();
			try{
				 controllerDAO cDAO = new controllerDAO();
				 ResultSet rs = cDAO.getResult(sql);
				 while (rs.next()){
					 if(rs.getString(1)!= null)
					 rtnList.add(rs.getString(1));
				 }
			}catch (SQLException e) {
				  setError(true);
				  e.printStackTrace();
			}
			return rtnList;
		}
		
		public String generateListBox(String type){
			
			StringBuilder sb = new StringBuilder();
			sb.append("<Select id=\"group\" name = \"group\">");
			if(type.equalsIgnoreCase("STUDENT")) {
				sb.append("<option value=\" "+ type +"\" selected > " +type + "</option>");
			}else {
				sb.append("<option value=\"STUDENT\"> STUDENT </option>");
			}
			 
			if(type.equalsIgnoreCase("DAYCARE")) {
				sb.append("<option value=\" "+ type +"\" selected > " +type + "</option>");
			}else {
				sb.append("<option value=\"DAYCARE\"> DAYCARE </option>");
			}
			 
			if(type.equalsIgnoreCase("PG")) {
				sb.append("<option value=\" "+ type +"\" selected > " +type + "</option>");
			}else {
				sb.append("<option value=\"PG\"> PG </option>");
			}
			
			if(type.equalsIgnoreCase("NURSERY")) {
				sb.append("<option value=\" "+ type +"\" selected > " +type + "</option>");
			}else {
				sb.append("<option value=\"NURSERY\"> NURSERY </option>");
			}
			
			if(type.equalsIgnoreCase("LKG")) {
				sb.append("<option value=\" "+ type +"\" selected > " +type + "</option>");
			}else {
				sb.append("<option value=\"LKG\"> LKG </option>");
			}
			
			if(type.equalsIgnoreCase("UKG")) {
				sb.append("<option value=\" "+ type +"\" selected > " +type + "</option>");
			}else {
				sb.append("<option value=\"UKG\"> UKG </option>");
			}
			
			if(type.equalsIgnoreCase("1-STD")) {
				sb.append("<option value=\" "+ type +"\" selected > " +type + "</option>");
			}else {
				sb.append("<option value=\"1-STD\"> 1-STD </option>");
			}
			 
			if(type.equalsIgnoreCase("2-STD")) {
				sb.append("<option value=\" "+ type +"\" selected > " +type + "</option>");
			}else {
				sb.append("<option value=\"2-STD\"> 2-STD </option>");
			}
			
			if(type.equalsIgnoreCase("NETBANKING")) {
				sb.append("<option value=\" "+ type +"\" selected > " +type + "</option>");
			}else {
				sb.append("<option value=\"NETBANKING\"> NETBANKING </option>");
			}
			
			if(type.equalsIgnoreCase("CHEQUE")) {
				sb.append("<option value=\" "+ type +"\" selected > " +type + "</option>");
			}else {
				sb.append("<option value=\"CHEQUE\"> CHEQUE </option>");
			}
			
			if(type.equalsIgnoreCase("DC_TO_SEND")) {
				sb.append("<option value=\" "+ type +"\" selected > " +type + "</option>");
			}else {
				sb.append("<option value=\"DC_TO_SEND\"> DC_TO_SEND </option>");
			}
			
			sb.append("</Select>");
			return sb.toString();
		}

	public static void main(String args[]) throws ParseException{
		String test = "<html> <body> <table border='1' width='100%' > <tr width = '100%'> <td align = 'center'>   <Font Face = 'Verdana' size = '4' color = 'Navy'>KIDS  </Font>  <Font Face = 'Verdana' size = '4' color = 'Green'>MANSION  </Font> <br> L.K.G Daily Report 04-Feb-2017 </td>  </tr> </table> <table border = '1'   width='100%'> <tr>  <th>Time</th>   <th> Activity</th> </tr>   <tr> <td> 9:30 To 9:45 AM </td> <td> Circle Time(Prayer , National Anthem & Rhymes with action) </td> </tr>  <tr> <td> 9:45 to 10:00 AM </td> <td> Picture Writing ? Revision  </td> </tr>  <tr> <td> 10:00 to 11:00 AM </td> <td> L.K.G English - & * > < $ # @ ! ( ) ^ \\ /  Cursive-  Alphabet- t </td> </tr>  <tr> <td> 11:00 to 11:20 AM </td> <td>  Snacks Break </td> </tr>  <tr> <td> 11:20 to 11:40 AM </td> <td> Play Time </td> </tr>  <tr> <td> 11:40 to 12:00 Noon </td> <td> GK ? Revision ? People around us </td> </tr>  <tr> <td> 12:30 to 12:50 PM  </td> <td> Annual day Practice </td> </tr> </table> <br> <b>Note:</b>  Daily Report is sent to entire class even if the child is absent. This helps to know the activity performed for the day.</body> </html>";
		StringBuilder sb = new StringBuilder();
		sb.append(test);
		System.out.println(sb);
	//	StringEscapeUtils.escapeHtml(sb.toString());
		System.out.println(sb);
		//sb.toString().replaceAll(regex, replacement)
		/*SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
		Date time = sdf.parse("13.15");
		String curentTime = sdf.format(Calendar.getInstance().getTime());
		Date currentTime = sdf.parse(curentTime);
		long timetoStart =  time.getTime() - currentTime.getTime();*/
		
		
		
		
		//System.out.println(timetoStart);
		
	}
		
}
