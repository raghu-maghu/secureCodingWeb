package test.accounttransfer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import weblogic.auddi.util.Logger;

/**
 * Servlet implementation class AccountTransfer
 */
//@WebServlet("/AccountTransfer.svl")
public class AccountTransfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountTransfer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("--------------Entering doGet ------------------");
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		/*PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Hello World</title></head>");
		out.println("<body>");
		out.println("<h1>Hello World2</h1>");*/
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
			
			HttpSession session = request.getSession();
			session.setAttribute("UserId", "raghu");
			//String fromDate = request.getParameter("fromYY") + request.getParameter("fromMM") + request.getParameter("fromDD");
			//String toDate = request.getParameter("toYY") + request.getParameter("toMM") + request.getParameter("toDD");
			System.out.println("Am I here2");
			String UserId =(String) session.getAttribute("UserId");
			String TransAmount = request.getParameter("TransAmount");
			double transAmt = Double.parseDouble("TransAmount");
			String CreditAccount = request.getParameter("CreditAccount");
			String DebitAccount = request.getParameter("DebitAccount");
			String TransRermarks = request.getParameter("TransRermarks");
			
			System.out.println("transAmt" + "="+ transAmt + "<br/>");
			
			String sql = "INSERT INTO `test`.`ACCOUNT_TRANSFER`(`TransDate`,`UserId`,`TransAmount`,`CreditAccount`,`DebitAccount`,`TransRermarks`)"+
			"VALUES(curtime(),'"+UserId +"',"+TransAmount +",'"+CreditAccount +"','"+DebitAccount +"','"+TransRermarks +"')";

			/*String userName = ctx.getAuthenticatedUserName();
			PreparedStatement ps = con.createStatement("select * from items where owner = ? and itemname = ?");
			ps.setString(1, userName);
			ps.setString(2, itemName);
			
			ResultSet rs = stmt.executeQuery(ps); 
			
			System.out.println ("fromDate ==" + fromDate);
			System.out.println ("toDate ==" + toDate);
			*/
			System.out.println ("sql ==" + sql);
			stmt.execute(sql);
			rs = stmt.executeQuery("select * from ACCOUNT_TRANSFER order by TransId desc limit 1");
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
		        request.getRequestDispatcher("trres/trres.jsp");
		    dispatcher.forward( request, response );
		    
		}catch(NumberFormatException ex)
		{
			ex.printStackTrace();

			System.out.println("Unable to parse the amount " + request.getParameter("TransAmount"));
		}
		catch (SQLException e) {
			throw new ServletException("Servlet Could not display records.", e);
		  } catch (ClassNotFoundException e) {
			  throw new ServletException("JDBC Driver not found.", e);
			}catch (Exception ex)
			{
				ex.printStackTrace();
			}
			finally {
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
