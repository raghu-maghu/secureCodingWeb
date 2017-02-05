package test.accounttransfer;

//*DataBase Connectivity from the Servlet Program no-11.
import java.io.*;
import java.util.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException{
		response.setContentType("text/html");
		// connecting to database
		Connection con = null;    
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection ("jdbc:mysql://localhost:3306/test", "root", "root");
			stmt = con.createStatement();
			String ParameterNames = "";
			String ParameterValue = "";
			for(java.util.Enumeration e = request.getParameterNames(); e.hasMoreElements(); ){
				ParameterNames = (String)e.nextElement();
				ParameterValue = (String)request.getParameter(ParameterNames);
				System.out.println(ParameterNames + "="+ ParameterValue+ "<br/>");
			}
			String fromDate = request.getParameter("fromYY");// + request.getParameter("fromMM") + request.getParameter("fromDD");
			String toDate = request.getParameter("toYY") + request.getParameter("toMM") + request.getParameter("toDD");
			String sql = "SELECT * FROM test.account_transfer where TransDate > '" + fromDate + "' and TransDate < '" + toDate + "' and UserId='raghu'" ;
			System.out.println ("fromDate changed==" + fromDate);
			System.out.println ("toDate ==" + toDate);
			System.out.println ("sql ==" + sql);
			rs = stmt.executeQuery(sql);
			// displaying records
			ArrayList al = new ArrayList();
			while(rs.next()){
				AccountTransferCom atc = new AccountTransferCom();
				atc.setTransId(rs.getInt("TransId"));
				atc.setTransDate(rs.getDate("TransDate"));
				atc.setUserId(rs.getString("UserId"));
				atc.setTransAmount(rs.getDouble("TransAmount"));
				atc.setCreditAccount(rs.getString("CreditAccount"));
				atc.setDebitAccount(rs.getString("DebitAccount"));
				atc.setTransRermarks(rs.getString("TransRermarks"));
				al.add(atc);
				
				/*out.print(rs.getObject(1).toString());
				out.print("\t\t\t");
				out.print(rs.getObject(2).toString());
				out.print("<br>");*/
			}
			request.setAttribute("TransList", al);
			RequestDispatcher dispatcher = 
		        request.getRequestDispatcher("form/Results.jsp");
		    dispatcher.forward( request, response );
		    
		} catch (SQLException e) {
			throw new ServletException("Servlet Could not display records.", e);
		  } catch (ClassNotFoundException e) {
			  throw new ServletException("JDBC Driver not found.", e);
			} finally {
				try {
					if(rs != null) {
						rs.close();
						rs = null;
					}
					if(stmt != null) {
						stmt.close();
						stmt = null;
					}
					if(con != null) {
						con.close();
						con = null;
					}
				} catch (SQLException e) {}
			}
   		   /* out.println("</body></html>");	
			out.close();*/
		}
	}