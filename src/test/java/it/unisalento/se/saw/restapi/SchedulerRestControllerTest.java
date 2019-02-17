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

import it.unisalento.se.saw.Iservices.ILessonService;
import it.unisalento.se.saw.Iservices.ISchedulerService;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.DayDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.repositories.LessonRepository;


@RunWith(MockitoJUnitRunner.class)
public class SchedulerRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	private MockMvc mockMvc2;
	
	@Mock
	private ILessonService lessonServiceMock;
	
	@Mock
	private ISchedulerService schedulerServiceMock;
	
	@Mock
	private LessonRepository lessonRepository;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new SchedulerRestController(lessonServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getCurrentSchedulerTeacherTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		
		SubjectDTO subject = new SubjectDTO();
		subject.setId(1);
		subject.setName("Analisi 1");
		
		DayDTO day = new DayDTO();
		day.setIdDay(1);
		day.setName("Lunedi");
		
		List<TypeLessonDTO> typeLessons = new ArrayList<TypeLessonDTO>();

		TypeLessonDTO typeLesson1 = new TypeLessonDTO();
		typeLesson1.setIdtypeLesson(1);
		typeLesson1.setStart(mockDate);
		typeLesson1.setEnd(mockDate);
		typeLesson1.setClassroom(classroom1);
		typeLesson1.setDay(day);
		typeLesson1.setSubject(subject);
		typeLessons.add(typeLesson1);
		
		when(lessonServiceMock.getCurrentSchedulerTeacher(1)).thenReturn(typeLessons);
		
		mockMvc.perform(get("/scheduler/getSchedulerTeacher/{idteacher}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idtypeLesson", is(1)))
			.andExpect(jsonPath("$[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].day.idDay", is(1)))
			.andExpect(jsonPath("$[0].day.name", is("Lunedi")))
			.andExpect(jsonPath("$[0].subject.id", is(1)))
			.andExpect(jsonPath("$[0].subject.name", is("Analisi 1")))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")));
		
		verify(lessonServiceMock, times(1)).getCurrentSchedulerTeacher(1);
		verifyNoMoreInteractions(lessonServiceMock);
	}
	
	/*@Test
	public void getCurrentSchedulerByCourseTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		
		SubjectDTO subject = new SubjectDTO();
		subject.setId(1);
		subject.setName("Analisi 1");
		
		DayDTO day = new DayDTO();
		day.setIdDay(1);
		day.setName("Lunedi");
		
		List<TypeLessonDTO> typeLessons = new ArrayList<TypeLessonDTO>();

		TypeLessonDTO typeLesson1 = new TypeLessonDTO();
		typeLesson1.setIdtypeLesson(1);
		typeLesson1.setStart(mockDate);
		typeLesson1.setEnd(mockDate);
		typeLesson1.setClassroom(classroom1);
		typeLesson1.setDay(day);
		typeLesson1.setSubject(subject);
		typeLessons.add(typeLesson1);
		
		TypeDegreeCourseDTO typeCourse = new TypeDegreeCourseDTO();
		typeCourse.setIdtypeDegreeCourse(1);
		typeCourse.setName("Ingegneria dell'Informazione");
		
		DegreeCourseDTO course1 = new DegreeCourseDTO();
		course1.setIdcourse(1);
		course1.setName("Ingegneria dell'Informazione");
		course1.setCfu(180);
		course1.setTypeDegreeCourse(typeCourse);
		
		when(lessonServiceMock.getCurrentSchedulerByCourse(course1, 1)).thenReturn(typeLessons);
		
		mockMvc.perform(post("/scheduler/getScheduler/{idterm}", 1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(course1)))
		.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].idtypeLesson", is(1)))
			.andExpect(jsonPath("$[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].day.idDay", is(1)))
			.andExpect(jsonPath("$[0].day.name", is("Lunedi")))
			.andExpect(jsonPath("$[0].subject.id", is(1)))
			.andExpect(jsonPath("$[0].subject.name", is("Analisi 1")))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")));
		
		verify(lessonServiceMock, times(1)).getCurrentSchedulerByCourse(course1, 1);
		verifyNoMoreInteractions(lessonServiceMock);
	}*/
	
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