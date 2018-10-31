package it.unisalento.se.saw.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.models.Login;
import it.unisalento.se.saw.services.StudentService;
import it.unisalento.se.saw.servlets.LoginServlet;
import it.unisalento.se.saw.Iservices.IAulaService;
import it.unisalento.se.saw.Iservices.IUserService;

@Controller
public class HomeController {
	
	/*@Autowired
	StudentService studentService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IAulaService aulaService;
	
	
	@Autowired
	public HomeController() {
		super();
	}
	
	public HomeController(IUserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value="/home/users", method = RequestMethod.GET)
	public String users(ModelMap modelMap) {
		List <User> users = userService.getAll();
		modelMap.addAttribute("users", users);
		return "users";
	}
	
	@RequestMapping(value={"", "/", "home"}, method=RequestMethod.GET)
	public String home(ModelMap modelMap, HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");
		
		if (user != null) {
			if(user.getType().getDescription().equals("Personale")) {
				return "redirect:/staff";
			}
			return "redirect:/teacher";
		}
		
		/*User user = new User();
		user.setEmail("osvaldo@email.it");
		user.setName("Jesse");
		user.setSurname("Rossi");
		
		userService.save(user);
		
		studentService.getStudentById(7);
		modelMap.addAttribute("param1","param2");
		System.out.println("HOME CONTROLLER");
		System.out.println("Sono qui");*/
		
		
		/*List<News> news = newsService.getAll();
		if (news.isEmpty()) {
			modelMap.addAttribute("valid", false);
		} else {
			News notizia = new News();
			notizia = news.get(0);
			modelMap.addAttribute("valid", true);
			modelMap.addAttribute("title", notizia.getTitle());
			modelMap.addAttribute("text", notizia.getText());
		}*/
		
		/*return "home";
	}
	
	/*@RequestMapping(value="login", method=RequestMethod.POST)
	public String login(@ModelAttribute("account") User user, ModelMap modelMap) {
		if(userService.findByEmailAndPassword(user.getEmail(), user.getPassword()) != null) {
			modelMap.addAttribute("email", user.getEmail());
			return "success";
		} else {
			return "error";
		}
	}*/
	
	/*@RequestMapping(value="/loginProcess", method=RequestMethod.GET)
	public String loginProcess(HttpServletRequest request) {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (email == null) {
			return "redirect:/home";
		}
		
		User user = userService.checkUser(email, password);
		HttpSession session = request.getSession();
		
		if (user == null) {
			session.setAttribute("valid", false);
			return "redirect:/home";
		}
		
		session.setAttribute("user", user);
		if(user.getType().getDescription().equals("Personale")) {
			return "redirect:/staff";
		}
		
		return "redirect:/teacher";
	}
	
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		
		if (request.getSession().getAttribute("user") != null) {
			request.getSession().invalidate();
			return "redirect:/home";
		}
		
		return "redirect:/home";
	}*/
}
