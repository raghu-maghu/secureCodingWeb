package com.commandinjection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fileupload.FileUploadCom;

import test.accounttransfer.AccountTransferCom;

/**
 * Servlet implementation class CommandInjection
 */
public class CommandInjection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommandInjection() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
			String  customerCode= request.getParameter("CustomerCode");
			String sql = "SELECT * FROM test.FILE_UPLOAD where CUSTOMER_CODE = '" + customerCode + "'" ;
			System.out.println ("customerCode ==" + customerCode);
			rs = stmt.executeQuery(sql);
			// displaying records
			ArrayList al = new ArrayList();
			while(rs.next()){
				System.out.println("This is the output of SQL");
				FileUploadCom atc = new FileUploadCom();
				atc.setFileId(rs.getInt("FILE_ID"));
				atc.setFileName(rs.getString("FILE_NAME"));
				atc.setStatus(rs.getString("STATUS"));
				al.add(atc);
				
				/*out.print(rs.getObject(1).toString());
				out.print("\t\t\t");
				out.print(rs.getObject(2).toString());
				out.print("<br>");*/
			}
			if(request.getParameter("Approve") != null && request.getParameter("Approve").equals("Approve"))
			{
			Exec ex = new Exec();
			ExecResults result = ex.execSimple("cmd /c move C:\\Temp\\"+ request.getParameter("FileName") + " C:\\Temp\\" + customerCode);
			System.out.println(result);
			 PrintWriter out = response.getWriter();
		        out.write("<html><head></head><body>");
		        out.write(result.toString());
		        out.println("</body></html>");	
				out.close();
		        return;
			}   
			request.setAttribute("FileList", al);
			RequestDispatcher dispatcher = 
		        request.getRequestDispatcher("commandinjection/CommandInjection.jsp");
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


