package com.ldap;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LdapLogin
 */
public class LdapLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LdapLogin() {
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
		
		//request.setAttribute("TransList", al);
		LDAPUtil lu = new LDAPUtil();
		
		Person person = new Person();
		person.setName(request.getParameter("login"));
		person.setPassword(request.getParameter("password"));
		
		LdapResult lr = lu.searchRecord(person);
		String nextJsp = "8-login-form/welcome.jsp";
		if (lr.getResultCode() == 99)
			nextJsp = "8-login-form/ldaplogin.jsp";
		//*)(uid=*))(|(uid=*	
		request.setAttribute("LdapResult",lr);
		request.setAttribute("User",person);
		
		RequestDispatcher dispatcher = 
	        request.getRequestDispatcher(nextJsp);
	    dispatcher.forward( request, response );

	}

}
