package it.unisalento.se.saw.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.LoginDTO;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.restapi.UserRestController;

/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/loginProcess")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IUserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

			try {
				System.out.println("ahia");
				execute(request, response);
				
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}
	
	private void execute (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UserNotFoundException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//User valid = validate(email, password);
		
		//if (valid) {
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			response.sendRedirect(request.getContextPath() + "/welcome");
		/*} else {
			response.sendRedirect(request.getContextPath() + "/home");
		}*/
		
		//response.sendRedirect(request.getContextPath() + "/loginProcess/" + email);
		
		
		
		/*
		System.out.println("SERVLET LEGGE " + email);
		User user = userService.checkUser(email);
		RequestDispatcher rd;
		
		if(user != null) {
			/*rd = request.getRequestDispatcher("home.jsp");
			request.setAttribute("valid", true);*/
			/*rd = request.getRequestDispatcher("/templates/success.jsp");
			request.setAttribute("email", email);
			request.setAttribute("password", password);
			rd.forward(request, response);
		} else {
			/*rd = request.getRequestDispatcher("/templates/home.jsp");
			request.setAttribute("valid", false);*/
			 /*request.getSession().setAttribute("valid", false);
			    response.sendRedirect(request.getContextPath() + "/home");
		}*/
		
	}
	
	/*private User validate (String email, String password) throws UserNotFoundException {
		return userService.checkUser(email, password);
	}*/

}
