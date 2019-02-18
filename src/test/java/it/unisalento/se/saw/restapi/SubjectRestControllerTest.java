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

import it.unisalento.se.saw.Iservices.ISubjectService;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.repositories.SubjectRepository;


@RunWith(MockitoJUnitRunner.class)
public class SubjectRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private ISubjectService subjectServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new SubjectRestController(subjectServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getAllTest() throws Exception {
		
		Date mockDate = new Date();
		
		List<TermDTO> terms = new ArrayList<TermDTO>();
		TermDTO term = new TermDTO();
		term.setIdterm(1);
		term.setStart(mockDate);
		term.setEnd(mockDate);
		terms.add(term);
		
		AcademicYearDTO aa = new AcademicYearDTO();
		aa.setIdacademicYear(1);
		aa.setYear(2018);
		aa.setTerms(terms);
		
		CourseTypeDTO courseType = new CourseTypeDTO();
		courseType.setIdcourseType(1);
		courseType.setDuration(3);
		courseType.setDescription("Laurea triennale");
		courseType.setCfu(180);
		
		TypeDegreeCourseDTO typeCourse = new TypeDegreeCourseDTO();
		typeCourse.setIdtypeDegreeCourse(1);
		typeCourse.setName("Analisi 1");
		typeCourse.setCourseType(courseType);
		
		DegreeCourseDTO course = new DegreeCourseDTO();
		course.setIdcourse(1);
		course.setName("Ingegneria dell'Informazione");
		course.setCfu(180);
		course.setAcademicYear(aa);
		course.setTypeDegreeCourse(typeCourse);
		
		TypeSubjectDTO typeSubject = new TypeSubjectDTO();
		typeSubject.setIdtypeSubject(1);
		typeSubject.setName("Analisi 1");
		typeSubject.setDescription("Bella");
		
		TeacherDTO teacher = new TeacherDTO();
		teacher.setIdteacher(1);
		teacher.setName("Antonio");
		teacher.setSurname("Leaci");
		teacher.setResidence("Lecce");
		teacher.setDomicile("Lecce");
		teacher.setPlaceBirth("Bari");
		teacher.setDateBirth(mockDate);
		
		List<SubjectDTO> subjects = new ArrayList<SubjectDTO>();
		
		SubjectDTO subject1 = new SubjectDTO();
		subject1.setId(1);
		subject1.setName("Analisi 1");
		subject1.setDescription("Bella");
		subject1.setCfu(12);
		subject1.setTypeSubjectDTO(typeSubject);
		subject1.setTeacherDTO(teacher);
		subject1.setDegreecourseDTO(course);
		subject1.setTerm(term);
		subjects.add(subject1);
		
		when(subjectServiceMock.getAll()).thenReturn(subjects);
		
		mockMvc.perform(get("/subject/getAll"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("Analisi 1")))
			.andExpect(jsonPath("$[0].cfu", is(12)))
			.andExpect(jsonPath("$[0].description", is("Bella")))
			.andExpect(jsonPath("$[0].typeSubjectDTO.idtypeSubject", is(1)))
			.andExpect(jsonPath("$[0].typeSubjectDTO.name", is("Analisi 1")))
			.andExpect(jsonPath("$[0].typeSubjectDTO.description", is("Bella")))
			.andExpect(jsonPath("$[0].term.idterm", is(1)))
			.andExpect(jsonPath("$[0].term.start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].term.end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].degreecourseDTO.idcourse", is(1)))
			.andExpect(jsonPath("$[0].degreecourseDTO.name", is("Ingegneria dell'Informazione")))
			.andExpect(jsonPath("$[0].degreecourseDTO.cfu", is(180)))
			.andExpect(jsonPath("$[0].degreecourseDTO.typeDegreeCourse.idtypeDegreeCourse", is(1)))
			.andExpect(jsonPath("$[0].degreecourseDTO.typeDegreeCourse.name", is("Analisi 1")))
			.andExpect(jsonPath("$[0].degreecourseDTO.typeDegreeCourse.courseType.idcourseType", is(1)))
			.andExpect(jsonPath("$[0].degreecourseDTO.typeDegreeCourse.courseType.description", is("Laurea triennale")))
			.andExpect(jsonPath("$[0].degreecourseDTO.typeDegreeCourse.courseType.duration", is(3)))
			.andExpect(jsonPath("$[0].degreecourseDTO.typeDegreeCourse.courseType.cfu", is(180)))
			.andExpect(jsonPath("$[0].degreecourseDTO.academicYear.idacademicYear", is(1)))
			.andExpect(jsonPath("$[0].degreecourseDTO.academicYear.year", is(2018)))
			.andExpect(jsonPath("$[0].degreecourseDTO.academicYear.terms[0].idterm", is(1)))
			.andExpect(jsonPath("$[0].degreecourseDTO.academicYear.terms[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].degreecourseDTO.academicYear.terms[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].teacherDTO.idteacher", is(1)))
			.andExpect(jsonPath("$[0].teacherDTO.name", is("Antonio")))
			.andExpect(jsonPath("$[0].teacherDTO.surname", is("Leaci")))
			.andExpect(jsonPath("$[0].teacherDTO.dateBirth", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].teacherDTO.placeBirth", is("Bari")))
			.andExpect(jsonPath("$[0].teacherDTO.domicile", is("Lecce")))
			.andExpect(jsonPath("$[0].teacherDTO.residence", is("Lecce")));
		
		verify(subjectServiceMock, times(1)).getAll();
		verifyNoMoreInteractions(subjectServiceMock);
	}
	
	@Test
	public void getByIdTest() throws Exception {
		
		SubjectDTO subject1 = new SubjectDTO();
		subject1.setId(1);
		subject1.setName("Analisi 1");
		subject1.setDescription("Bella");
		subject1.setCfu(12);
		
		when(subjectServiceMock.getById(1)).thenReturn(subject1);
		
		mockMvc.perform(get("/subject/getById/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("Analisi 1")))
			.andExpect(jsonPath("$.cfu", is(12)))
			.andExpect(jsonPath("$.description", is("Bella")));
		
		verify(subjectServiceMock, times(1)).getById(1);
		verifyNoMoreInteractions(subjectServiceMock);
	}
	
	@Test
	public void getByIdCourseTest() throws Exception {
		
		List<SubjectDTO> subjects = new ArrayList<SubjectDTO>();
		
		SubjectDTO subject1 = new SubjectDTO();
		subject1.setId(1);
		subject1.setName("Analisi 1");
		subject1.setDescription("Bella");
		subject1.setCfu(12);
		subjects.add(subject1);
		
		when(subjectServiceMock.getByIdCourse(1)).thenReturn(subjects);
		
		mockMvc.perform(get("/subject/getByIdCourse/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("Analisi 1")))
			.andExpect(jsonPath("$[0].cfu", is(12)))
			.andExpect(jsonPath("$[0].description", is("Bella")));
		
		verify(subjectServiceMock, times(1)).getByIdCourse(1);
		verifyNoMoreInteractions(subjectServiceMock);
	}
	
	@Test
	public void getByIdTeacherTest() throws Exception {
		
		List<SubjectDTO> subjects = new ArrayList<SubjectDTO>();
		
		SubjectDTO subject1 = new SubjectDTO();
		subject1.setId(1);
		subject1.setName("Analisi 1");
		subject1.setDescription("Bella");
		subject1.setCfu(12);
		subjects.add(subject1);
		
		when(subjectServiceMock.getByIdTeacher(1)).thenReturn(subjects);
		
		mockMvc.perform(get("/subject/getByIdTeacher/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("Analisi 1")))
			.andExpect(jsonPath("$[0].cfu", is(12)))
			.andExpect(jsonPath("$[0].description", is("Bella")));
		
		verify(subjectServiceMock, times(1)).getByIdTeacher(1);
		verifyNoMoreInteractions(subjectServiceMock);
	}
	
	@Test
	public void getByIdStudentTest() throws Exception {
		
		List<SubjectDTO> subjects = new ArrayList<SubjectDTO>();
		
		SubjectDTO subject1 = new SubjectDTO();
		subject1.setId(1);
		subject1.setName("Analisi 1");
		subject1.setDescription("Bella");
		subject1.setCfu(12);
		subjects.add(subject1);
		
		when(subjectServiceMock.getByIdStudent(1)).thenReturn(subjects);
		
		mockMvc.perform(get("/subject/getByIdStudent/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("Analisi 1")))
			.andExpect(jsonPath("$[0].cfu", is(12)))
			.andExpect(jsonPath("$[0].description", is("Bella")));
		
		verify(subjectServiceMock, times(1)).getByIdStudent(1);
		verifyNoMoreInteractions(subjectServiceMock);
	}
	
	@Test
	public void getAllSubjectTypesTest() throws Exception {
		
		List<TypeSubjectDTO> typeSubjects = new ArrayList<TypeSubjectDTO>();
		
		TypeSubjectDTO typeSubject1 = new TypeSubjectDTO();
		typeSubject1.setIdtypeSubject(1);
		typeSubject1.setName("Analisi 1");
		typeSubject1.setDescription("Bella");
		typeSubjects.add(typeSubject1);
		
		TypeSubjectDTO typeSubject2 = new TypeSubjectDTO();
		typeSubject2.setIdtypeSubject(2);
		typeSubject2.setName("Fisica 1");
		typeSubject2.setDescription("Brutta");
		typeSubjects.add(typeSubject2);
		
		when(subjectServiceMock.getAllSubjectTypes()).thenReturn(typeSubjects);
		
		mockMvc.perform(get("/subject/getAllSubjectTypes"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idtypeSubject", is(1)))
			.andExpect(jsonPath("$[0].name", is("Analisi 1")))
			.andExpect(jsonPath("$[0].description", is("Bella")))
			.andExpect(jsonPath("$[1].idtypeSubject", is(2)))
			.andExpect(jsonPath("$[1].name", is("Fisica 1")))
			.andExpect(jsonPath("$[1].description", is("Brutta")));
			
		verify(subjectServiceMock, times(1)).getAllSubjectTypes();
		verifyNoMoreInteractions(subjectServiceMock);
	}
	
	@Test
	public void deleteTest() throws Exception {
			
		mockMvc.perform(post("/subject/delete/{id}", 1))
			.andExpect(status().isOk());
		
		verify(subjectServiceMock, times(1)).delete(1);;
		verifyNoMoreInteractions(subjectServiceMock);
	}
	
	@Test
	public void saveTest() throws Exception {
		
		SubjectDTO subject1 = new SubjectDTO();
		subject1.setId(1);
		subject1.setName("Analisi 1");
		subject1.setDescription("Bella");
		subject1.setCfu(12);
		
		when(subjectServiceMock.save(any(SubjectDTO.class))).thenReturn(subject1);
		
		mockMvc.perform(post("/subject/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(subject1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("Analisi 1")))
			.andExpect(jsonPath("$.cfu", is(12)))
			.andExpect(jsonPath("$.description", is("Bella")));
		
		verify(subjectServiceMock, times(1)).save(any(SubjectDTO.class));
		verifyNoMoreInteractions(subjectServiceMock);
	}
	
	@Test
	public void saveAllTest() throws Exception {
		
		List<SubjectDTO> subjects = new ArrayList<SubjectDTO>();
		
		SubjectDTO subject1 = new SubjectDTO();
		subject1.setId(1);
		subject1.setName("Analisi 1");
		subject1.setDescription("Bella");
		subject1.setCfu(12);
		subjects.add(subject1);
		
		when(subjectServiceMock.saveAll(Matchers.anyListOf(SubjectDTO.class))).thenReturn(subjects);
		
		mockMvc.perform(post("/subject/saveAll").contentType(MediaType.APPLICATION_JSON).content(asJsonString(subjects)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("Analisi 1")))
			.andExpect(jsonPath("$[0].cfu", is(12)))
			.andExpect(jsonPath("$[0].description", is("Bella")));
		
		verify(subjectServiceMock, times(1)).saveAll(Matchers.anyListOf(SubjectDTO.class));
		verifyNoMoreInteractions(subjectServiceMock);
	}
	
	@Test
	public void saveTypeSubjectTest() throws Exception {
		
		TypeSubjectDTO typeSubject1 = new TypeSubjectDTO();
		typeSubject1.setIdtypeSubject(1);
		typeSubject1.setName("Analisi 1");
		typeSubject1.setDescription("Bella");
		
		when(subjectServiceMock.saveType(any(TypeSubjectDTO.class))).thenReturn(typeSubject1);
		
		mockMvc.perform(post("/subject/saveTypeSubject").contentType(MediaType.APPLICATION_JSON).content(asJsonString(typeSubject1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.idtypeSubject", is(1)))
			.andExpect(jsonPath("$.name", is("Analisi 1")))
			.andExpect(jsonPath("$.description", is("Bella")));
		
		verify(subjectServiceMock, times(1)).saveType(any(TypeSubjectDTO.class));
		verifyNoMoreInteractions(subjectServiceMock);
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