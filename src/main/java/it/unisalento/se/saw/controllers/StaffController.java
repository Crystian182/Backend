package it.unisalento.se.saw.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.models.Login;
import it.unisalento.se.saw.services.StudentService;
import it.unisalento.se.saw.servlets.LoginServlet;
import it.unisalento.se.saw.Iservices.IAulaService;
import it.unisalento.se.saw.Iservices.IBuildingService;
import it.unisalento.se.saw.Iservices.IClassroomService;
import it.unisalento.se.saw.Iservices.IToolService;
import it.unisalento.se.saw.Iservices.IUserService;

@Controller
@RequestMapping(value="/staff")
public class StaffController {
	
	/*@Autowired
	IBuildingService buildingService;
	
	@Autowired
	IClassroomService classroomService;
	
	@Autowired
	IInstrumentsService instrumentsService;
	
	@Autowired
	IClassroomHasInstrumentsService classroomHasInstrumentsService;
	
	@Autowired
	public StaffController() {
		super();
	}
	
	@RequestMapping(value= {"/",""}, method=RequestMethod.GET)
	public String welcome(HttpServletRequest request) {
		
		if (request.getSession().getAttribute("user") == null) {
			return "redirect:/home";
		}
		
		return "staff";
	}
	
	@RequestMapping(value="/classroom", method=RequestMethod.GET)
	public String classroom(HttpServletRequest request) {
		
		List<Building> edifici = buildingService.getAll();
		request.getSession().setAttribute("building", edifici);
		return "classroom";
	}
	
	@RequestMapping(value="/classroom/search", method=RequestMethod.GET)
	public String searchClassroom(@RequestParam int build, @RequestParam String name, HttpServletRequest request) {
		if(name.isEmpty() && build == 0) {
			request.getSession().setAttribute("valid", false);
			return "redirect:/classroom";
		} 		
		List<Classroom> classroom = classroomService.findClasses(name, build);
		request.getSession().setAttribute("valid", true);
		request.getSession().setAttribute("classroom", classroom);
		return "classroom";
	}
	
	@RequestMapping(value="/classroom/{idClassroom}", method=RequestMethod.GET)
	public String detailClassroom(@PathVariable int idClassroom, HttpServletRequest request) {
		Classroom classroom = classroomService.getById(idClassroom);
		request.getSession().setAttribute("classroom", classroom);
		return "detailClassroom";
	}
	
	/*@RequestMapping(value="/classroom/edit/{idClassroom}", method=RequestMethod.GET)
	public String editClassroom(@PathVariable int idClassroom, HttpServletRequest request) {
		Classroom classroom = classroomService.getById(idClassroom);
		request.getSession().setAttribute("classroom", classroom);
		List<Building> edifici = buildingService.getAll();
		request.getSession().setAttribute("building", edifici);
		List<Instruments> instruments = instrumentsService.getAll();
		request.getSession().setAttribute("instruments", instruments);
		List<ClassroomHasInstruments> instrumentsInClassroom = classroomHasInstrumentsService.getInstrumentsFromClassroom(idClassroom);
		request.getSession().setAttribute("instrumentsInClassroom", instrumentsInClassroom);
		return "editClassroom";
	}*/
	
	/*@RequestMapping(value="/classroom/edit/class/{idClassroom}")
	public String editClassroom(@PathVariable int idClassroom, HttpServletRequest request) {
		return "test";
	}*/
	
}