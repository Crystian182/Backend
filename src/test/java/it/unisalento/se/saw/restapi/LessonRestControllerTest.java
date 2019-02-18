package it.unisalento.se.saw.restapi;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
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
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.DayDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.SchedulerDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.repositories.LessonRepository;


@RunWith(MockitoJUnitRunner.class)
public class LessonRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private ILessonService lessonServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new LessonRestController(lessonServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getAllTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		
		List<LessonDTO> lessons = new ArrayList<LessonDTO>();

		LessonDTO lesson1 = new LessonDTO();
		lesson1.setIdlesson(1);
		lesson1.setStart(mockDate);
		lesson1.setEnd(mockDate);
		lesson1.setClassroom(classroom1);
		lessons.add(lesson1);
		
		when(lessonServiceMock.getAll()).thenReturn(lessons);
		
		mockMvc.perform(get("/lesson/getAll"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idlesson", is(1)))
			.andExpect(jsonPath("$[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")));
		
		verify(lessonServiceMock, times(1)).getAll();
		verifyNoMoreInteractions(lessonServiceMock);
	}
	
	@Test
	public void getAllTeacherLessonTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		
		List<LessonDTO> lessons = new ArrayList<LessonDTO>();

		LessonDTO lesson1 = new LessonDTO();
		lesson1.setIdlesson(1);
		lesson1.setStart(mockDate);
		lesson1.setEnd(mockDate);
		lesson1.setClassroom(classroom1);
		lessons.add(lesson1);
		
		when(lessonServiceMock.getAllTeacherLessons(1)).thenReturn(lessons);
		
		mockMvc.perform(get("/lesson/getAllTeacherLessons/{idteacher}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idlesson", is(1)))
			.andExpect(jsonPath("$[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")));
		
		verify(lessonServiceMock, times(1)).getAllTeacherLessons(1);
		verifyNoMoreInteractions(lessonServiceMock);
	}
	
	@Test
	public void getAllLessonsByCourseAndTermTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		
		List<LessonDTO> lessons = new ArrayList<LessonDTO>();

		LessonDTO lesson1 = new LessonDTO();
		lesson1.setIdlesson(1);
		lesson1.setStart(mockDate);
		lesson1.setEnd(mockDate);
		lesson1.setClassroom(classroom1);
		lessons.add(lesson1);
		
		when(lessonServiceMock.getAllLessonsByCourseAndTerm(1,1)).thenReturn(lessons);
		
		mockMvc.perform(get("/lesson/getAllLessonsByCourseAndTerm/idcourse={idcourse}&idterm={idterm}", 1, 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idlesson", is(1)))
			.andExpect(jsonPath("$[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")));
		
		verify(lessonServiceMock, times(1)).getAllLessonsByCourseAndTerm(1,1);
		verifyNoMoreInteractions(lessonServiceMock);
	}
	
	@Test
	public void searchLessonsTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		
		List<LessonDTO> lessons = new ArrayList<LessonDTO>();

		LessonDTO lesson1 = new LessonDTO();
		lesson1.setIdlesson(1);
		lesson1.setStart(mockDate);
		lesson1.setEnd(mockDate);
		lesson1.setClassroom(classroom1);
		lessons.add(lesson1);
		
		when(lessonServiceMock.searchLessons(1,1,1,"01-01-2019","31-01-2019")).thenReturn(lessons);
		
		mockMvc.perform(get("/lesson/searchLessons/idcourse={idcourse}&idterm={idterm}&idsubject={idsubject}&from={from}&to={to}", 1,1,1,"01-01-2019","31-01-2019"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idlesson", is(1)))
			.andExpect(jsonPath("$[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")));
		
		verify(lessonServiceMock, times(1)).searchLessons(1,1,1,"01-01-2019","31-01-2019");
		verifyNoMoreInteractions(lessonServiceMock);
	}
	
	@Test
	public void searchTeacherLessonsTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		
		List<LessonDTO> lessons = new ArrayList<LessonDTO>();

		LessonDTO lesson1 = new LessonDTO();
		lesson1.setIdlesson(1);
		lesson1.setStart(mockDate);
		lesson1.setEnd(mockDate);
		lesson1.setClassroom(classroom1);
		lessons.add(lesson1);
		
		when(lessonServiceMock.searchTeacherLessons(1,"01-01-2019","31-01-2019")).thenReturn(lessons);
		
		mockMvc.perform(get("/lesson/searchTeacherLessons/idsubject={idsubject}&from={from}&to={to}", 1,"01-01-2019","31-01-2019"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idlesson", is(1)))
			.andExpect(jsonPath("$[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")));
		
		verify(lessonServiceMock, times(1)).searchTeacherLessons(1,"01-01-2019","31-01-2019");
		verifyNoMoreInteractions(lessonServiceMock);
	}
	
	@Test
	public void getTodayLessonsTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		
		List<LessonDTO> lessons = new ArrayList<LessonDTO>();

		LessonDTO lesson1 = new LessonDTO();
		lesson1.setIdlesson(1);
		lesson1.setStart(mockDate);
		lesson1.setEnd(mockDate);
		lesson1.setClassroom(classroom1);
		lessons.add(lesson1);
		
		when(lessonServiceMock.getTodayLessons(1)).thenReturn(lessons);
		
		mockMvc.perform(get("/lesson/getTodayLessons/{iduser}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idlesson", is(1)))
			.andExpect(jsonPath("$[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")));
		
		verify(lessonServiceMock, times(1)).getTodayLessons(1);
		verifyNoMoreInteractions(lessonServiceMock);
	}
	
	@Test
	public void getTeacherTodayLessonsTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		
		List<LessonDTO> lessons = new ArrayList<LessonDTO>();

		LessonDTO lesson1 = new LessonDTO();
		lesson1.setIdlesson(1);
		lesson1.setStart(mockDate);
		lesson1.setEnd(mockDate);
		lesson1.setClassroom(classroom1);
		lessons.add(lesson1);
		
		when(lessonServiceMock.getTeacherTodayLessons(1)).thenReturn(lessons);
		
		mockMvc.perform(get("/lesson/getTeacherTodayLessons/{iduser}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idlesson", is(1)))
			.andExpect(jsonPath("$[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")));
		
		verify(lessonServiceMock, times(1)).getTeacherTodayLessons(1);
		verifyNoMoreInteractions(lessonServiceMock);
	}
	
	@Test
	public void getByIdTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		
		TermDTO term = new TermDTO();
		term.setIdterm(1);
		
		DayDTO day = new DayDTO();
		day.setIdDay(1);
		day.setName("Lunedi");
		
		DegreeCourseDTO course = new DegreeCourseDTO();
		course.setIdcourse(1);
		
		SubjectDTO subject = new SubjectDTO();
		subject.setId(1);
		subject.setName("Analisi 1");
		
		SchedulerDTO scheduler1 = new SchedulerDTO();
		scheduler1.setIdScheduler(1);
		scheduler1.setTerm(term);
		scheduler1.setDegreeCourse(course);
		
		SchedulerDTO scheduler = new SchedulerDTO();
		scheduler.setIdScheduler(1);
		scheduler.setTerm(term);
		scheduler.setDegreeCourse(course);
		
		TypeLessonDTO typeLesson = new TypeLessonDTO();
		typeLesson.setIdtypeLesson(1);
		typeLesson.setClassroom(classroom1);
		typeLesson.setDay(day);
		typeLesson.setStart(mockDate);
		typeLesson.setEnd(mockDate);
		typeLesson.setSubject(subject);
		typeLesson.setScheduler(scheduler);
		
		List<TypeLessonDTO> typeLessons = new ArrayList<TypeLessonDTO>();
		typeLessons.add(typeLesson);
		
		scheduler1.setTypeLessons(typeLessons);

		LessonDTO lesson1 = new LessonDTO();
		lesson1.setIdlesson(1);
		lesson1.setStart(mockDate);
		lesson1.setEnd(mockDate);
		lesson1.setClassroom(classroom1);
		lesson1.setTypeLesson(typeLesson);
		
		List<LessonDTO> lessons = new ArrayList<LessonDTO>();
		lessons.add(lesson1);
		
		scheduler1.setLessons(lessons);
		
		when(lessonServiceMock.getById(1)).thenReturn(lesson1);
		
		mockMvc.perform(get("/lesson/getById/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.idlesson", is(1)))
			.andExpect(jsonPath("$.start", is(mockDate.getTime())))
			.andExpect(jsonPath("$.end", is(mockDate.getTime())))
			.andExpect(jsonPath("$.classroom.id", is(1)))
			.andExpect(jsonPath("$.classroom.name", is("I1")))
			.andExpect(jsonPath("$.typeLesson.idtypeLesson", is(1)))
			.andExpect(jsonPath("$.typeLesson.start", is(mockDate.getTime())))
			.andExpect(jsonPath("$.typeLesson.end", is(mockDate.getTime())))
			.andExpect(jsonPath("$.typeLesson.day.idDay", is(1)))
			.andExpect(jsonPath("$.typeLesson.day.name", is("Lunedi")))
			.andExpect(jsonPath("$.typeLesson.subject.id", is(1)))
			.andExpect(jsonPath("$.typeLesson.subject.name", is("Analisi 1")))
			.andExpect(jsonPath("$.typeLesson.classroom.id", is(1)))
			.andExpect(jsonPath("$.typeLesson.classroom.name", is("I1")))
			.andExpect(jsonPath("$.typeLesson.scheduler.idScheduler", is(1)))
			.andExpect(jsonPath("$.typeLesson.scheduler.term.idterm", is(1)))
			.andExpect(jsonPath("$.typeLesson.scheduler.degreeCourse.idcourse", is(1)));
		
		verify(lessonServiceMock, times(1)).getById(1);
		verifyNoMoreInteractions(lessonServiceMock);
	}
	
	@Test
	public void deleteTest() throws Exception {
			
		mockMvc.perform(post("/lesson/delete/{id}", 1))
			.andExpect(status().isOk());
		
		verify(lessonServiceMock, times(1)).delete(1);;
		verifyNoMoreInteractions(lessonServiceMock);
	}
	
	@Test
	public void getFeedBackTest() throws Exception {
		
		Date mockDate = new Date();
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setName("Cristian");
		user.setSurname("Vergallo");
		
		List<FeedbackDTO> feedbacks = new ArrayList<FeedbackDTO>();
		
		FeedbackDTO feedback1 = new FeedbackDTO();
		feedback1.setId(1);
		feedback1.setDate(mockDate);
		feedback1.setDescription("Bella lezione!");
		feedback1.setStars(5);
		feedback1.setUser(user);
		feedbacks.add(feedback1);
		
		when(lessonServiceMock.getFeedback(1)).thenReturn(feedbacks);
		
		mockMvc.perform(get("/lesson/getFeedback/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].description", is("Bella lezione!")))
			.andExpect(jsonPath("$[0].stars", is(5)))
			.andExpect(jsonPath("$[0].user.iduser", is(1)))
			.andExpect(jsonPath("$[0].user.name", is("Cristian")))
			.andExpect(jsonPath("$[0].user.surname", is("Vergallo")));
		
		verify(lessonServiceMock, times(1)).getFeedback(1);
		verifyNoMoreInteractions(lessonServiceMock);
	}
	
	@Test
	public void saveTest() throws Exception {
		Date mockDate = new Date();
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");

		LessonDTO lesson1 = new LessonDTO();
		lesson1.setIdlesson(1);
		lesson1.setStart(mockDate);
		lesson1.setEnd(mockDate);
		lesson1.setClassroom(classroom1);
		
		when(lessonServiceMock.save(any(LessonDTO.class))).thenReturn(lesson1);
		
		mockMvc.perform(post("/lesson/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(lesson1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.idlesson", is(1)))
			.andExpect(jsonPath("$.start", is(mockDate.getTime())))
			.andExpect(jsonPath("$.end", is(mockDate.getTime())))
			.andExpect(jsonPath("$.classroom.id", is(1)))
			.andExpect(jsonPath("$.classroom.name", is("I1")));
		
		verify(lessonServiceMock, times(1)).save(any(LessonDTO.class));
		verifyNoMoreInteractions(lessonServiceMock);
	}
	
	@Test
	public void editTest() throws Exception {
		Date mockDate = new Date();
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		
		List<LessonDTO> lessons = new ArrayList<LessonDTO>();

		LessonDTO lesson1 = new LessonDTO();
		lesson1.setIdlesson(1);
		lesson1.setStart(mockDate);
		lesson1.setEnd(mockDate);
		lesson1.setClassroom(classroom1);
		lessons.add(lesson1);
		
		mockMvc.perform(post("/lesson/edit").contentType(MediaType.APPLICATION_JSON).content(asJsonString(lessons)))
			.andExpect(status().isOk());
		
		verify(lessonServiceMock, times(1)).edit(Matchers.anyListOf(LessonDTO.class));
		verifyNoMoreInteractions(lessonServiceMock);
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
		
		mockMvc.perform(post("/lesson/saveFeedback/{idlesson}", 1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(feedback1)))
			.andExpect(status().isOk());
		
		verify(lessonServiceMock, times(1)).saveFeedback(any(Integer.class), any(FeedbackDTO.class));
		verifyNoMoreInteractions(lessonServiceMock);
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