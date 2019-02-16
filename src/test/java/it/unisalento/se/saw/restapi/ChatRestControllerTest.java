package it.unisalento.se.saw.restapi;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.se.saw.Iservices.IChatService;
import it.unisalento.se.saw.dto.PreviewChatDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;


@RunWith(MockitoJUnitRunner.class)
public class ChatRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private IChatService chatServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new ChatRestController(chatServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getContactsTest() throws Exception {
		
		SubjectDTO subject = new SubjectDTO();
		subject.setId(1);
		subject.setName("Analisi 1");
		subject.setCfu(12);
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setName("Cristian");
		user.setSurname("Vergallo");
		
		List<PreviewChatDTO> chats = new ArrayList<PreviewChatDTO>();
		PreviewChatDTO chat1 = new PreviewChatDTO();
		chat1.setSubject(subject);
		chats.add(chat1);
		
		PreviewChatDTO chat2 = new PreviewChatDTO();
		chat2.setToUser(user);
		chats.add(chat2);
		
		when(chatServiceMock.getContacts(5, "cri")).thenReturn(chats);
		
		mockMvc.perform(get("/chat/getContacts/userid={userid}&keyword={keyword}", 5, "cri"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].subject.id", is(1)))
			.andExpect(jsonPath("$[0].subject.name", is("Analisi 1")))
			.andExpect(jsonPath("$[0].subject.cfu", is(12)))
			.andExpect(jsonPath("$[1].toUser.iduser", is(1)))
			.andExpect(jsonPath("$[1].toUser.name", is("Cristian")))
			.andExpect(jsonPath("$[1].toUser.surname", is("Vergallo")));
		
		verify(chatServiceMock, times(1)).getContacts(5, "cri");
		verifyNoMoreInteractions(chatServiceMock);
	}
	
	@Test
	public void getProfileUserTest() throws Exception {
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setName("Cristian");
		user.setSurname("Vergallo");
		
		when(chatServiceMock.getProfileUser(1)).thenReturn(user);
		
		mockMvc.perform(get("/chat/getProfileUser/{userid}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.iduser", is(1)))
			.andExpect(jsonPath("$.name", is("Cristian")))
			.andExpect(jsonPath("$.surname", is("Vergallo")));
		
		verify(chatServiceMock, times(1)).getProfileUser(1);
		verifyNoMoreInteractions(chatServiceMock);
	}
	
	@Test
	public void getSubscribedCoursesTest() throws Exception {
		
		List<PreviewChatDTO> chats = new ArrayList<PreviewChatDTO>();
		
		SubjectDTO subject1 = new SubjectDTO();
		subject1.setId(1);
		subject1.setName("Analisi 1");
		subject1.setCfu(12);
		
		PreviewChatDTO chat1 = new PreviewChatDTO();
		chat1.setSubject(subject1);
		chats.add(chat1);
		
		when(chatServiceMock.getSubscribedCourses(1)).thenReturn(chats);
		
		mockMvc.perform(get("/chat/getSubscribedCourses/userid={userid}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].subject.id", is(1)))
			.andExpect(jsonPath("$[0].subject.name", is("Analisi 1")))
			.andExpect(jsonPath("$[0].subject.cfu", is(12)));
		
		verify(chatServiceMock, times(1)).getSubscribedCourses(1);
		verifyNoMoreInteractions(chatServiceMock);
	}
	
	@Test
	public void getPropicTest() throws Exception {
		
		String propic = "iVBORw0KGgoAAAANSUhEUgAAAYsAAAErCAIAAACgnjgyAAAABmJLR0QA";
		
		when(chatServiceMock.getPropic(1)).thenReturn(propic);
		
		mockMvc.perform(get("/chat/getpropic/{userid}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.propic", is("iVBORw0KGgoAAAANSUhEUgAAAYsAAAErCAIAAACgnjgyAAAABmJLR0QA")));
		
		verify(chatServiceMock, times(1)).getPropic(1);
		verifyNoMoreInteractions(chatServiceMock);
	}
	
	static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

}