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
import org.springframework.ui.ModelMap;

import it.unisalento.se.saw.Iservices.IClassroomService;
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
//@WebServlet("/staff/classroom/edit/*")
public class EditClassroomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IClassroomService classroomService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditClassroomServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws ServletException, IOException {
		// TODO Auto-generated method stub

		execute(request, response, modelMap);
	}
	
	private void execute (HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws ServletException, IOException {
		/*Building building = new Building();
		building.setIdbuilding(Integer.parseInt(request.getParameter("build")));
		Classroom classroom = (Classroom)request.getSession().getAttribute("classroom");
		classroom.setName(request.getParameter("name"));
		classroom.setSeats(Integer.parseInt(request.getParameter("seats")));
		classroom.setLatitude(Float.parseFloat(request.getParameter("latitude")));
		classroom.setLongitude(Float.parseFloat(request.getParameter("longitude")));
		classroom.setBuilding(building);
		
		System.out.println(classroom);*/
		String[] pathInfo = request.getPathInfo().split("/");
		String idClassroom = pathInfo[pathInfo.length-1];
		/*Classroom classroom = classroomService.getById(Integer.parseInt(idClassroom));
		modelMap.addAttribute("classroom", classroom);*/
		System.out.println(idClassroom);
		//response.sendRedirect(request.getContextPath() + "/classroom/edit/class/" + idClassroom);
		
	}


}
