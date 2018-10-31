package it.unisalento.se.saw.restapi;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.nio.charset.Charset;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.controllers.HomeController;
import it.unisalento.se.saw.domain.Type;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.WrongPasswordException;
import it.unisalento.se.saw.models.Login;
import it.unisalento.se.saw.repositories.UserRepository;
import it.unisalento.se.saw.services.UserService;

@RunWith(MockitoJUnitRunner.class)
public class LoginRestControllerTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));
	
	private MockMvc mockMvc;

	@Mock
	private IUserService userServiceMock;
	
	@Mock
	private UserRepository userRepository;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new LoginRestController(userServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	/*@Test
	public void loginIsCorrectTest() throws Exception {
		Type typePersonale = new Type();
		typePersonale.setIdtype(2);
		typePersonale.setDescription("Personale");
		
		Login loginQuery = new Login();
		loginQuery.setEmail("personale@email.it");
		loginQuery.setPassword("password");
		
		User user = new User();
		user.setEmail("personale@email.it");
		user.setPassword("password");
		user.setType(typePersonale);

		when(userServiceMock.checkUser(loginQuery)).thenReturn(user);
		when(userRepository.checkUser("personale@email.it")).thenReturn(user);
		
		mockMvc.perform(post("/login"))
			.andExpect(status().isOk());
			/*.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.email",is("personale@email.it")))
			.andExpect(jsonPath("$.type",is(2)))
			.andExpect(jsonPath("$.typeDescription",is("Personale")));*/
		
		/*verify(userServiceMock, times(1)).checkUser(loginQuery);
		verifyNoMoreInteractions(userServiceMock);
	}*/	
	
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
}
