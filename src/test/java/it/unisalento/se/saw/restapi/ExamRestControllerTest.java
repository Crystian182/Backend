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

import it.unisalento.se.saw.Iservices.IExamService;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.ExamDTO;
import it.unisalento.se.saw.dto.ExamEnrollmentDTO;
import it.unisalento.se.saw.dto.ExamTypeDTO;
import it.unisalento.se.saw.dto.StudentDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.repositories.ExamRepository;


@RunWith(MockitoJUnitRunner.class)
public class ExamRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private IExamService examServiceMock;
	
	@Mock
	private ExamRepository examRepository;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new ExamRestController(examServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getAllByCourseTest() throws Exception {
		
		Date mockDate = new Date();
		
		SubjectDTO subject = new SubjectDTO();
		subject.setId(1);
		subject.setName("Analisi 1");
		
		List<ExamDTO> exams = new ArrayList<ExamDTO>();

		ExamDTO exam1 = new ExamDTO();
		exam1.setIdexam(1);
		exam1.setDate(mockDate);
		exam1.setSubject(subject);
		exams.add(exam1);
		
		when(examServiceMock.getAllByCourseAndTerm(1,1)).thenReturn(exams);
		
		mockMvc.perform(get("/exam/getAllByCourseAndTerm/idcourse={idcourse}&idterm={idterm}", 1, 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idexam", is(1)))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].subject.id", is(1)))
			.andExpect(jsonPath("$[0].subject.name", is("Analisi 1")));
		
		verify(examServiceMock, times(1)).getAllByCourseAndTerm(1,1);
		verifyNoMoreInteractions(examServiceMock);
	}
	
	@Test
	public void getAllAvailableByTeacherTest() throws Exception {
		
		Date mockDate = new Date();
		
		SubjectDTO subject = new SubjectDTO();
		subject.setId(1);
		subject.setName("Analisi 1");
		
		List<ExamDTO> exams = new ArrayList<ExamDTO>();

		ExamDTO exam1 = new ExamDTO();
		exam1.setIdexam(1);
		exam1.setDate(mockDate);
		exam1.setSubject(subject);
		exams.add(exam1);
		
		when(examServiceMock.getAllAvailableByTeacher(1)).thenReturn(exams);
		
		mockMvc.perform(get("/exam/getAllAvailableByTeacher/{idteacher}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idexam", is(1)))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].subject.id", is(1)))
			.andExpect(jsonPath("$[0].subject.name", is("Analisi 1")));
		
		verify(examServiceMock, times(1)).getAllAvailableByTeacher(1);
		verifyNoMoreInteractions(examServiceMock);
	}
	
	@Test
	public void getAllAvailableByStudentTest() throws Exception {
		
		Date mockDate = new Date();
		
		SubjectDTO subject = new SubjectDTO();
		subject.setId(1);
		subject.setName("Analisi 1");
		
		List<ExamDTO> exams = new ArrayList<ExamDTO>();

		ExamDTO exam1 = new ExamDTO();
		exam1.setIdexam(1);
		exam1.setDate(mockDate);
		exam1.setSubject(subject);
		exams.add(exam1);
		
		when(examServiceMock.getAllAvailableByStudent(1)).thenReturn(exams);
		
		mockMvc.perform(get("/exam/getAllAvailableByStudent/{idstudent}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idexam", is(1)))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].subject.id", is(1)))
			.andExpect(jsonPath("$[0].subject.name", is("Analisi 1")));
		
		verify(examServiceMock, times(1)).getAllAvailableByStudent(1);
		verifyNoMoreInteractions(examServiceMock);
	}
	
	@Test
	public void getAllExamTypesTest() throws Exception {
		
		List<ExamTypeDTO> examTypes = new ArrayList<ExamTypeDTO>();

		ExamTypeDTO examType1 = new ExamTypeDTO();
		examType1.setIdexamType(1);
		examType1.setDescription("Prova orale");
		examTypes.add(examType1);
		
		ExamTypeDTO examType2 = new ExamTypeDTO();
		examType2.setIdexamType(2);
		examType2.setDescription("Prova scritta");
		examTypes.add(examType2);
		
		when(examServiceMock.getAllTypes()).thenReturn(examTypes);
		
		mockMvc.perform(get("/exam/getAllTypes"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idexamType", is(1)))
			.andExpect(jsonPath("$[0].description", is("Prova orale")))
			.andExpect(jsonPath("$[1].idexamType", is(2)))
			.andExpect(jsonPath("$[1].description", is("Prova scritta")));
		
		verify(examServiceMock, times(1)).getAllTypes();
		verifyNoMoreInteractions(examServiceMock);
	}
	
	@Test
	public void getRecordBookTest() throws Exception {
		
		Date mockDate = new Date();
		
		SubjectDTO subject = new SubjectDTO();
		subject.setId(1);
		subject.setName("Analisi 1");

		ExamDTO exam1 = new ExamDTO();
		exam1.setIdexam(1);
		exam1.setDate(mockDate);
		exam1.setSubject(subject);
		
		StudentDTO student = new StudentDTO();
		student.setIdstudent(1);
		student.setName("Cristian");
		student.setSurname("Vergallo");
		
		List<ExamEnrollmentDTO> examEnrollments = new ArrayList<ExamEnrollmentDTO>();

		ExamEnrollmentDTO examEnrollment1 = new ExamEnrollmentDTO();
		examEnrollment1.setExam(exam1);
		examEnrollment1.setDate(mockDate);
		examEnrollment1.setGrade(18);
		examEnrollment1.setStudent(student);
		examEnrollments.add(examEnrollment1);
		
		when(examServiceMock.getRecordBook(1)).thenReturn(examEnrollments);
		
		mockMvc.perform(get("/exam/getRecordBook/{idstudent}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].student.idstudent", is(1)))
			.andExpect(jsonPath("$[0].student.name", is("Cristian")))
			.andExpect(jsonPath("$[0].student.surname", is("Vergallo")))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].grade", is(18)))
			.andExpect(jsonPath("$[0].exam.idexam", is(1)))
			.andExpect(jsonPath("$[0].exam.date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].exam.subject.id", is(1)))
			.andExpect(jsonPath("$[0].exam.subject.name", is("Analisi 1")));
		
		verify(examServiceMock, times(1)).getRecordBook(1);
		verifyNoMoreInteractions(examServiceMock);
	}
	
	@Test
	public void getAllEnrollmentsTest() throws Exception {
		
		Date mockDate = new Date();
		
		SubjectDTO subject = new SubjectDTO();
		subject.setId(1);
		subject.setName("Analisi 1");

		ExamDTO exam1 = new ExamDTO();
		exam1.setIdexam(1);
		exam1.setDate(mockDate);
		exam1.setSubject(subject);
		
		StudentDTO student = new StudentDTO();
		student.setIdstudent(1);
		student.setName("Cristian");
		student.setSurname("Vergallo");
		
		List<ExamEnrollmentDTO> examEnrollments = new ArrayList<ExamEnrollmentDTO>();

		ExamEnrollmentDTO examEnrollment1 = new ExamEnrollmentDTO();
		examEnrollment1.setExam(exam1);
		examEnrollment1.setDate(mockDate);
		examEnrollment1.setGrade(18);
		examEnrollment1.setStudent(student);
		examEnrollments.add(examEnrollment1);
		
		when(examServiceMock.getAllEnrollments(1)).thenReturn(examEnrollments);
		
		mockMvc.perform(get("/exam/getAllEnrollments/{idstudent}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].student.idstudent", is(1)))
			.andExpect(jsonPath("$[0].student.name", is("Cristian")))
			.andExpect(jsonPath("$[0].student.surname", is("Vergallo")))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].grade", is(18)))
			.andExpect(jsonPath("$[0].exam.idexam", is(1)))
			.andExpect(jsonPath("$[0].exam.date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].exam.subject.id", is(1)))
			.andExpect(jsonPath("$[0].exam.subject.name", is("Analisi 1")));
		
		verify(examServiceMock, times(1)).getAllEnrollments(1);
		verifyNoMoreInteractions(examServiceMock);
	}
	
	@Test
	public void BookStudentTest() throws Exception {
		
		mockMvc.perform(get("/exam/bookStudent/idstudent={idstudent}&idexam={idexam}", 1, 1))
			.andExpect(status().isOk());
		
		verify(examServiceMock, times(1)).bookStudent(1, 1);;
		verifyNoMoreInteractions(examServiceMock);
	}
	
	public void saveTest() throws Exception {
		Date mockDate = new Date();
		
		SubjectDTO subject = new SubjectDTO();
		subject.setId(1);
		subject.setName("Analisi 1");
		
		List<ExamDTO> exams = new ArrayList<ExamDTO>();

		ExamDTO exam1 = new ExamDTO();
		exam1.setIdexam(1);
		exam1.setDate(mockDate);
		exam1.setSubject(subject);
		exams.add(exam1);
		
		mockMvc.perform(post("/exam/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(exams)))
			.andExpect(status().isOk());
		
		verify(examServiceMock, times(1)).save(exams);
		verifyNoMoreInteractions(examServiceMock);
	}
	
	public void insertGradeTest() throws Exception {
Date mockDate = new Date();
		
		SubjectDTO subject = new SubjectDTO();
		subject.setId(1);
		subject.setName("Analisi 1");

		ExamDTO exam1 = new ExamDTO();
		exam1.setIdexam(1);
		exam1.setDate(mockDate);
		exam1.setSubject(subject);
		
		StudentDTO student = new StudentDTO();
		student.setIdstudent(1);
		student.setName("Cristian");
		student.setSurname("Vergallo");
		
		List<ExamEnrollmentDTO> examEnrollments = new ArrayList<ExamEnrollmentDTO>();

		ExamEnrollmentDTO examEnrollment1 = new ExamEnrollmentDTO();
		examEnrollment1.setExam(exam1);
		examEnrollment1.setDate(mockDate);
		examEnrollment1.setGrade(18);
		examEnrollment1.setStudent(student);
		examEnrollments.add(examEnrollment1);
		
		mockMvc.perform(post("/exam/insertGrade/{idexam}", 1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(examEnrollments)))
			.andExpect(status().isOk());
		
		verify(examServiceMock, times(1)).insertGrade(examEnrollments, 1);
		verifyNoMoreInteractions(examServiceMock);
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