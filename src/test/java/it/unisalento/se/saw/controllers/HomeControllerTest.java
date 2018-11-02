package it.unisalento.se.saw.controllers;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.domain.User;


//@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	/*private MockMvc mockMvc;
	
	@Mock
	private IUserService userService;
	
	@Mock
	private HomeController homeController;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new HomeController(userService)).setViewResolvers(viewResolver()).build();
				
	}
	
	@Test
	public void getAllUsersTest() throws Exception {
		/*User first = new User();
		first.setEmail("osvaldo@email.it");
		first.setName("Osvaldo");
		first.setSurname("Rossi");
		
		User second = new User();
		second.setEmail("mario@email.it");
		second.setName("Mario");
		second.setSurname("Bianchi");
		
		when(userService.getAll()).thenReturn(Arrays.asList(first,second));
		
		mockMvc.perform(get("/home/users")).andExpect(view().name("users")).andExpect(forwardedUrl("/templates/users.jsp")).andExpect(model().attribute("users", hasSize(2))).andExpect(model().attribute("users", hasItem(allOf(hasProperty("email", is("osvaldo@email.it")),hasProperty("name", is("Osvaldo")),hasProperty("surname", is("Rossi"))))));
		
		verify(userService, times(1)).getAll();
		verifyNoMoreInteractions(userService);*/
	}
	
	/*@Test
	public void wrongAddressTest() throws Exception {		
		mockMvc.perform(get("/loginProcess")).andExpect(redirectedUrl("/home"));
		mockMvc.perform(get("/staff")).andExpect(redirectedUrl("/home"));
		mockMvc.perform(get("/logout")).andExpect(redirectedUrl("/home"));
	}
	
	@Test
	public void teacherTypeTest() throws Exception {
		
		Type typeD = new Type();
		typeD.setIdtype(3);
		typeD.setDescription("Docente");
		
		User userD = new User();
		userD.setEmail("docente@email.it");
		userD.setType(typeD);
		
		when(userService.checkUser("docente@email.it", "password")).thenReturn(userD);
		
		mockMvc.perform(get("/loginProcess").param("email", "docente@email.it").param("password", "password"))
		.andExpect(redirectedUrl("/teacher"));
			
		mockMvc.perform(get("/teacher").sessionAttr("email", "docente@email.it"));
		
		mockMvc.perform(get("/logout")).andExpect(redirectedUrl("/home"));
		
		verify(userService, times(1)).checkUser("docente@email.it", "password");
		verifyNoMoreInteractions(userService);
	}
	
	@Test
	public void staffTypeTest() throws Exception {
		Type typeS = new Type();
		typeS.setIdtype(2);
		typeS.setDescription("Personale");
		
		User userS = new User();
		userS.setEmail("personale@email.it");
		userS.setType(typeS);
		
		when(userService.checkUser("personale@email.it", "password")).thenReturn(userS);

		mockMvc.perform(get("/loginProcess").param("email", "personale@email.it").param("password", "password"))
			.andExpect(redirectedUrl("/staff"));
		
		mockMvc.perform(get("/staff").sessionAttr("email", "personale@email.it"));
		
		mockMvc.perform(get("/logout")).andExpect(redirectedUrl("/home"));
		
		verify(userService, times(1)).checkUser("personale@email.it", "password");
		verifyNoMoreInteractions(userService);
	}
	
	
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}*/
	
	

