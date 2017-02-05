package com.captcha;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CaptchaLoginServlet
@WebServlet(description = "Login Servlet", urlPatterns = { "/LoginServlet" }, initParams = {
		@WebInitParam(name = "user", value = "Pankaj"),
		@WebInitParam(name = "password", value = "journaldev") })
 */

public class CaptchaLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	
		protected void doPost(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {

			// get request parameters for userID and password
			String user = request.getParameter("user");
			String pwd = request.getParameter("pwd");
			// get reCAPTCHA request param
			String gRecaptchaResponse = request
					.getParameter("g-recaptcha-response");
			System.out.println(gRecaptchaResponse);
			boolean verify = VerifyCaptcha.verify(gRecaptchaResponse);

			// get servlet config init params
			String userID = getServletConfig().getInitParameter("user");
			String password = getServletConfig().getInitParameter("password");
			// logging example
			System.out.println("User=" + user + "::password=" + pwd + "::Captcha Verify"+verify);

			if (verify) {
				response.sendRedirect("captcha/LoginSuccess.jsp");
			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher(
						"captcha/login.html");
				PrintWriter out = response.getWriter();
				if (verify) {
					out.println("<font color=red>Either user name or password is wrong.</font>");
				} else {
					out.println("<font color=red>You missed the Captcha.</font>");
				}

				//rd.include(request, response);

			}

		}

	}
