package it.unisalento.se.saw.restapi;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.cglib.transform.impl.AddDelegateTransformer;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

import it.unisalento.se.saw.Iservices.IFileService;
import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.FileDTO;
import it.unisalento.se.saw.dto.FileLessonDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.UserDTO;

@RunWith(MockitoJUnitRunner.class)
public class FileRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private IFileService fileServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new FileRestController(fileServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getLessonFilesTest() throws Exception {
		
		List<FileDTO> files = new ArrayList<FileDTO>();
		
		FileDTO file = new FileDTO();
		file.setIdFile(1);
		file.setName("soluzione.txt");
		file.setStars(3.5);
		files.add(file);
		
		when(fileServiceMock.getLessonFiles(1)).thenReturn(files);
		
		mockMvc.perform(get("/file/getLessonFiles/{idlesson}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idFile", is(1)))
			.andExpect(jsonPath("$[0].name", is("soluzione.txt")))
			.andExpect(jsonPath("$[0].stars", is(3.5)));
		
		verify(fileServiceMock, times(1)).getLessonFiles(1);
		verifyNoMoreInteractions(fileServiceMock);
	}
	
	@Test
	public void getFeedbackFileTest() throws Exception {
		
		Date mockDate = new Date();
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setName("Cristian");
		user.setSurname("Vergallo");
		
		List<FeedbackDTO> feedbacks = new ArrayList<FeedbackDTO>();
		FeedbackDTO feedback1 = new FeedbackDTO();
		feedback1.setId(1);
		feedback1.setDescription("Ottimo file");
		feedback1.setStars(5);
		feedback1.setDate(mockDate);
		feedback1.setUser(user);
		feedbacks.add(feedback1);
		
		when(fileServiceMock.getFeedbackFile(1)).thenReturn(feedbacks);
		
		mockMvc.perform(get("/file/getFeedbackFile/{idfile}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].description", is("Ottimo file")))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].stars", is(5)))
			.andExpect(jsonPath("$[0].user.iduser", is(1)))
			.andExpect(jsonPath("$[0].user.name", is("Cristian")))
			.andExpect(jsonPath("$[0].user.surname", is("Vergallo")));
		
		verify(fileServiceMock, times(1)).getFeedbackFile(1);
		verifyNoMoreInteractions(fileServiceMock);
	}
	
	@Test
	public void getLastFilesTest() throws Exception {
		
		Date mockDate = new Date();
		
		List<FileLessonDTO> files = new ArrayList<FileLessonDTO>();
		
		FileDTO file = new FileDTO();
		file.setIdFile(1);
		file.setName("soluzione.txt");
		file.setStars(3.5);
		
		LessonDTO lesson = new LessonDTO();
		lesson.setIdlesson(1);
		
		FileLessonDTO fileLesson = new FileLessonDTO();
		fileLesson.setFile(file);
		fileLesson.setDate(mockDate);
		fileLesson.setLesson(lesson);
		files.add(fileLesson);
		
		when(fileServiceMock.getLastFiles(1)).thenReturn(files);
		
		mockMvc.perform(get("/file/getLastFiles/{idstudent}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].file.idFile", is(1)))
			.andExpect(jsonPath("$[0].file.name", is("soluzione.txt")))
			.andExpect(jsonPath("$[0].file.stars", is(3.5)))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].lesson.idlesson", is(1)));
		
		verify(fileServiceMock, times(1)).getLastFiles(1);
		verifyNoMoreInteractions(fileServiceMock);
	}

	@Test
	public void getSubjectFilesTest() throws Exception {
		
		Date mockDate = new Date();
		
		List<FileLessonDTO> files = new ArrayList<FileLessonDTO>();
		
		FileDTO file = new FileDTO();
		file.setIdFile(1);
		file.setName("soluzione.txt");
		file.setStars(3.5);
		
		LessonDTO lesson = new LessonDTO();
		lesson.setIdlesson(1);
		
		FileLessonDTO fileLesson = new FileLessonDTO();
		fileLesson.setFile(file);
		fileLesson.setDate(mockDate);
		fileLesson.setLesson(lesson);
		files.add(fileLesson);
		
		when(fileServiceMock.getSubjectFiles(1)).thenReturn(files);
		
		mockMvc.perform(get("/file/getSubjectFiles/{idsubject}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].file.idFile", is(1)))
			.andExpect(jsonPath("$[0].file.name", is("soluzione.txt")))
			.andExpect(jsonPath("$[0].file.stars", is(3.5)))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].lesson.idlesson", is(1)));
		
		verify(fileServiceMock, times(1)).getSubjectFiles(1);
		verifyNoMoreInteractions(fileServiceMock);
	}
	
	@Test
	public void saveFeedBackTest() throws Exception {
		Date mockDate = new Date();
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setName("Cristian");
		user.setSurname("Vergallo");
		
		FeedbackDTO feedback1 = new FeedbackDTO();
		feedback1.setId(1);
		feedback1.setDate(mockDate);
		feedback1.setDescription("Bella lezione!");
		feedback1.setStars(5);
		feedback1.setUser(user);
		
		mockMvc.perform(post("/file/saveFeedback/idfile={idfile}&idlesson={idlesson}", 1, 1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(feedback1)))
			.andExpect(status().isOk());
		
		verify(fileServiceMock, times(1)).saveFeedback(any(Integer.class), any(Integer.class), any(FeedbackDTO.class));
		verifyNoMoreInteractions(fileServiceMock);
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