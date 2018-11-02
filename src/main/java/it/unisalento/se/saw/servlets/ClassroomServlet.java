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
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.LoginDTO;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.restapi.UserRestController;

/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/staff/classroom/edit/classroomProcess")
public class ClassroomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IUserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassroomServlet() {
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

		execute(request, response);
	}
	
	private void execute (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Building building = new Building();
		building.setIdbuilding(Integer.parseInt(request.getParameter("build")));
		Classroom classroom = (Classroom)request.getSession().getAttribute("classroom");
		classroom.setName(request.getParameter("name"));
		classroom.setSeats(Integer.parseInt(request.getParameter("seats")));
		classroom.setLatitude(Float.parseFloat(request.getParameter("latitude")));
		classroom.setLongitude(Float.parseFloat(request.getParameter("longitude")));
		classroom.setBuilding(building);
		
		System.out.println(classroom);*/
		
		System.out.println(request.getParameter("hiddenLat") + " " + request.getParameter("hiddenLon"));
		
	}
	
	/*private User validate (String email, String password) throws UserNotFoundException {
		return userService.checkUser(email, password);
	}*/

}
