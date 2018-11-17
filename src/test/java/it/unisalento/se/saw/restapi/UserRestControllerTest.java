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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.controllers.HomeController;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.LoginDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.repositories.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));
	
	private MockMvc mockMvc;

	@Mock
	private IUserService userServiceMock;
	
	@Before
	public void setUp() {
		//mockMvc = MockMvcBuilders.standaloneSetup(new UserRestController(userServiceMock)).setViewResolvers(viewResolver()).build();
		mockMvc = MockMvcBuilders.standaloneSetup(new UserRestController(userServiceMock)).build();
		
	}
	
	@Test
	public void loginTest() throws Exception {
		
		LoginDTO request = new LoginDTO();
		request.setEmail("crifede");
		request.setPassword("pass");

		UserDTO userDTO = new UserDTO();
		//userDTO.setSsn("VRGCST95A21E506G");
		userDTO.setName("Cristian");
		userDTO.setSurname("Vergallo");
		userDTO.setEmail("crifede");
		userDTO.setDateBirth(new Date());
		userDTO.setPlaceBirth("lecce");
		userDTO.setResidence("lecce");
		userDTO.setDomicile("lecce");
		userDTO.setPhone("334");
		userDTO.setSex("m");
		userDTO.setCitizenship("ita");
		userDTO.setType("student");
		
		User user = new User();
		user.setSsn("VRGCST95A21E506G");
		user.setName("Cristian");
		user.setSurname("Vergallo");
		user.setEmail("crifede");
		user.setDateBirth(new Date());
		user.setPlaceBirth("lecce");
		user.setResidence("lecce");
		user.setDomicile("lecce");
		user.setPhone("334");
		user.setSex("m");
		user.setCitizenship("ita");

		when(userServiceMock.login(request)).thenReturn(userDTO);
	 
		 mockMvc.perform(post("/user/login")
		            .contentType(APPLICATION_JSON_UTF8)
		            .content(objToJson(request)))
		            .andExpect(status().isOk())
		            //.andExpect(jsonPath("$.name",is("Cristian")))
		            .andDo(print()); 
		
		//verify(userServiceMock, times(1)).login(request);
		//verifyNoMoreInteractions(userServiceMock);
	}
	
	public String objToJson(LoginDTO request) throws JsonProcessingException {
		 ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		 return ow.writeValueAsString(request);
	}
	
	@Test
	public void findUserByIdTest() throws Exception {
		/*Utente user = new Utente();
		user.setName("Sandro");
		user.setSurname("Fiore");
		

		when(userServiceMock.getById(1)).thenReturn(user);
		
		mockMvc.perform(get("/user/getById/{id}",1)).andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.name",is("Sandro")))
			.andExpect(jsonPath("$.surname",is("Fiore")));
		
		verify(userServiceMock, times(1)).getById(1);
		verifyNoMoreInteractions(userServiceMock);*/
		
	}
	
	@Test
	public void checkUser() throws Exception {
		/*Utente user = new Utente();
		user.setName("Sandro");
		user.setSurname("Fiore");
		

		when(userServiceMock.getById(1)).thenReturn(user);
		
		mockMvc.perform(get("/user/getById/{id}",1)).andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.name",is("Sandro")))
			.andExpect(jsonPath("$.surname",is("Fiore")));
		
		verify(userServiceMock, times(1)).getById(1);
		verifyNoMoreInteractions(userServiceMock);*/
		
	}
	
	
	
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
}
