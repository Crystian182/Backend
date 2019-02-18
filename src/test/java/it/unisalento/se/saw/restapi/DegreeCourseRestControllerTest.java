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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.refEq;
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

import it.unisalento.se.saw.Iservices.IDegreeCourseService;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;


@RunWith(MockitoJUnitRunner.class)
public class DegreeCourseRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private IDegreeCourseService degreeCourseServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new DegreeCourseRestController(degreeCourseServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getAllTest() throws Exception {
		
		TypeDegreeCourseDTO typeCourse = new TypeDegreeCourseDTO();
		typeCourse.setIdtypeDegreeCourse(1);
		typeCourse.setName("Ingegneria dell'Informazione");
		
		AcademicYearDTO year1 = new AcademicYearDTO();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		
		List<SubjectDTO> subjects = new ArrayList<SubjectDTO>();
		
		SubjectDTO subject1 = new SubjectDTO();
		subject1.setId(1);
		subject1.setName("Analisi 1");
		subjects.add(subject1);
		
		List<DegreeCourseDTO> courses = new ArrayList<DegreeCourseDTO>();
		
		DegreeCourseDTO course1 = new DegreeCourseDTO();
		course1.setIdcourse(1);
		course1.setName("Ingegneria dell'Informazione");
		course1.setCfu(180);
		course1.setTypeDegreeCourse(typeCourse);
		course1.setAcademicYear(year1);
		course1.setSubjects(subjects);
		courses.add(course1);
		
		when(degreeCourseServiceMock.getAll()).thenReturn(courses);
		
		mockMvc.perform(get("/course/getAll"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idcourse", is(1)))
			.andExpect(jsonPath("$[0].name", is("Ingegneria dell'Informazione")))
			.andExpect(jsonPath("$[0].cfu", is(180)))
			.andExpect(jsonPath("$[0].typeDegreeCourse.idtypeDegreeCourse", is(1)))
			.andExpect(jsonPath("$[0].typeDegreeCourse.name", is("Ingegneria dell'Informazione")))
			.andExpect(jsonPath("$[0].academicYear.idacademicYear", is(1)))
			.andExpect(jsonPath("$[0].academicYear.year", is(2018)))
			.andExpect(jsonPath("$[0].subjects[0].id", is(1)))
			.andExpect(jsonPath("$[0].subjects[0].name", is("Analisi 1")));
		
		verify(degreeCourseServiceMock, times(1)).getAll();
		verifyNoMoreInteractions(degreeCourseServiceMock);
	}
	
	@Test
	public void getByIdTest() throws Exception {
		
		TypeDegreeCourseDTO typeCourse = new TypeDegreeCourseDTO();
		typeCourse.setIdtypeDegreeCourse(1);
		typeCourse.setName("Ingegneria dell'Informazione");
		
		DegreeCourseDTO course1 = new DegreeCourseDTO();
		course1.setIdcourse(1);
		course1.setName("Ingegneria dell'Informazione");
		course1.setCfu(180);
		course1.setTypeDegreeCourse(typeCourse);
		
		when(degreeCourseServiceMock.getById(1)).thenReturn(course1);
		
		mockMvc.perform(get("/course/getById/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.idcourse", is(1)))
			.andExpect(jsonPath("$.name", is("Ingegneria dell'Informazione")))
			.andExpect(jsonPath("$.cfu", is(180)))
			.andExpect(jsonPath("$.typeDegreeCourse.idtypeDegreeCourse", is(1)))
			.andExpect(jsonPath("$.typeDegreeCourse.name", is("Ingegneria dell'Informazione")));
		
		verify(degreeCourseServiceMock, times(1)).getById(1);
		verifyNoMoreInteractions(degreeCourseServiceMock);
	}
	
	@Test
	public void getCourseByTypeTest() throws Exception {
		
		TypeDegreeCourseDTO typeCourse = new TypeDegreeCourseDTO();
		typeCourse.setIdtypeDegreeCourse(1);
		typeCourse.setName("Ingegneria dell'Informazione");
		
		List<DegreeCourseDTO> courses = new ArrayList<DegreeCourseDTO>();
		
		DegreeCourseDTO course1 = new DegreeCourseDTO();
		course1.setIdcourse(1);
		course1.setName("Ingegneria dell'Informazione");
		course1.setCfu(180);
		course1.setTypeDegreeCourse(typeCourse);
		courses.add(course1);
		
		when(degreeCourseServiceMock.getCourseByType(1)).thenReturn(courses);
		
		mockMvc.perform(get("/course/getCourseByType/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$[0].idcourse", is(1)))
		.andExpect(jsonPath("$[0].name", is("Ingegneria dell'Informazione")))
		.andExpect(jsonPath("$[0].cfu", is(180)))
		.andExpect(jsonPath("$[0].typeDegreeCourse.idtypeDegreeCourse", is(1)))
		.andExpect(jsonPath("$[0].typeDegreeCourse.name", is("Ingegneria dell'Informazione")));
		
		verify(degreeCourseServiceMock, times(1)).getCourseByType(1);
		verifyNoMoreInteractions(degreeCourseServiceMock);
	}
	
	@Test
	public void getTeacherCoursesTest() throws Exception {
		
		TypeDegreeCourseDTO typeCourse = new TypeDegreeCourseDTO();
		typeCourse.setIdtypeDegreeCourse(1);
		typeCourse.setName("Ingegneria dell'Informazione");
		
		List<DegreeCourseDTO> courses = new ArrayList<DegreeCourseDTO>();
		
		DegreeCourseDTO course1 = new DegreeCourseDTO();
		course1.setIdcourse(1);
		course1.setName("Ingegneria dell'Informazione");
		course1.setCfu(180);
		course1.setTypeDegreeCourse(typeCourse);
		courses.add(course1);
		
		when(degreeCourseServiceMock.getTeacherCourses(1)).thenReturn(courses);
		
		mockMvc.perform(get("/course/getTeacherCourses/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$[0].idcourse", is(1)))
		.andExpect(jsonPath("$[0].name", is("Ingegneria dell'Informazione")))
		.andExpect(jsonPath("$[0].cfu", is(180)))
		.andExpect(jsonPath("$[0].typeDegreeCourse.idtypeDegreeCourse", is(1)))
		.andExpect(jsonPath("$[0].typeDegreeCourse.name", is("Ingegneria dell'Informazione")));
		
		verify(degreeCourseServiceMock, times(1)).getTeacherCourses(1);
		verifyNoMoreInteractions(degreeCourseServiceMock);
	}
	
	@Test
	public void getAllTypesTest() throws Exception {
		
		List<TypeDegreeCourseDTO> typeCourses = new ArrayList<TypeDegreeCourseDTO>();
		
		TypeDegreeCourseDTO typeCourse1 = new TypeDegreeCourseDTO();
		typeCourse1.setIdtypeDegreeCourse(1);
		typeCourse1.setName("Ingegneria dell'Informazione");		
		typeCourses.add(typeCourse1);
		
		TypeDegreeCourseDTO typeCourse2 = new TypeDegreeCourseDTO();
		typeCourse2.setIdtypeDegreeCourse(2);
		typeCourse2.setName("Ingegneria Civile");		
		typeCourses.add(typeCourse2);
		
		when(degreeCourseServiceMock.getAllTypes()).thenReturn(typeCourses);
		
		mockMvc.perform(get("/course/getAllTypes"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idtypeDegreeCourse", is(1)))
			.andExpect(jsonPath("$[0].name", is("Ingegneria dell'Informazione")))
			.andExpect(jsonPath("$[1].idtypeDegreeCourse", is(2)))
			.andExpect(jsonPath("$[1].name", is("Ingegneria Civile")));
		
		verify(degreeCourseServiceMock, times(1)).getAllTypes();
		verifyNoMoreInteractions(degreeCourseServiceMock);
	}
	
	@Test
	public void getTypesByIdTest() throws Exception {
		
		TypeDegreeCourseDTO typeCourse1 = new TypeDegreeCourseDTO();
		typeCourse1.setIdtypeDegreeCourse(1);
		typeCourse1.setName("Ingegneria dell'Informazione");
		
		when(degreeCourseServiceMock.getTypesById(1)).thenReturn(typeCourse1);
		
		mockMvc.perform(get("/course/getTypesById/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.idtypeDegreeCourse", is(1)))
			.andExpect(jsonPath("$.name", is("Ingegneria dell'Informazione")));
		
		verify(degreeCourseServiceMock, times(1)).getTypesById(1);
		verifyNoMoreInteractions(degreeCourseServiceMock);
	}
	
	@Test
	public void getAllCourseTypesTest() throws Exception {
		
		List<CourseTypeDTO> courseTypes = new ArrayList<CourseTypeDTO>();
		
		CourseTypeDTO courseType1 = new CourseTypeDTO();
		courseType1.setIdcourseType(1);
		courseType1.setDuration(3);		
		courseType1.setDescription("Triennale");
		courseType1.setCfu(180);
		courseTypes.add(courseType1);
		
		CourseTypeDTO courseType2 = new CourseTypeDTO();
		courseType2.setIdcourseType(2);
		courseType2.setDuration(2);		
		courseType2.setDescription("Magistrale");
		courseType2.setCfu(150);
		courseTypes.add(courseType2);
		
		when(degreeCourseServiceMock.getAllCourseTypes()).thenReturn(courseTypes);
		
		mockMvc.perform(get("/course/getAllCourseTypes"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idcourseType", is(1)))
			.andExpect(jsonPath("$[0].duration", is(3)))
			.andExpect(jsonPath("$[0].description", is("Triennale")))
			.andExpect(jsonPath("$[0].cfu", is(180)))
			.andExpect(jsonPath("$[1].idcourseType", is(2)))
			.andExpect(jsonPath("$[1].duration", is(2)))
			.andExpect(jsonPath("$[1].description", is("Magistrale")))
			.andExpect(jsonPath("$[1].cfu", is(150)));
		
		verify(degreeCourseServiceMock, times(1)).getAllCourseTypes();
		verifyNoMoreInteractions(degreeCourseServiceMock);
	}
	
	@Test
	public void getCourseTypeTest() throws Exception {
		
		CourseTypeDTO courseType1 = new CourseTypeDTO();
		courseType1.setIdcourseType(1);
		courseType1.setDuration(3);		
		courseType1.setDescription("Triennale");
		courseType1.setCfu(180);
		
		when(degreeCourseServiceMock.getCourseType(1)).thenReturn(courseType1);
		
		mockMvc.perform(get("/course/getCourseType/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.idcourseType", is(1)))
			.andExpect(jsonPath("$.duration", is(3)))
			.andExpect(jsonPath("$.description", is("Triennale")))
			.andExpect(jsonPath("$.cfu", is(180)));
		
		verify(degreeCourseServiceMock, times(1)).getCourseType(1);
		verifyNoMoreInteractions(degreeCourseServiceMock);
	}
	
	@Test
	public void deleteTest() throws Exception {
			
		mockMvc.perform(post("/course/delete/{id}", 1))
			.andExpect(status().isOk());
		
		verify(degreeCourseServiceMock, times(1)).delete(1);;
		verifyNoMoreInteractions(degreeCourseServiceMock);
	}
	
	@Test
	public void saveTest() throws Exception {
		DegreeCourseDTO course1 = new DegreeCourseDTO();
		course1.setIdcourse(1);
		course1.setName("Ingegneria dell'Informazione");
		course1.setCfu(180);
		
		when(degreeCourseServiceMock.save(any(DegreeCourseDTO.class))).thenReturn(course1);
		
		mockMvc.perform(post("/course/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(course1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.idcourse", is(1)))
			.andExpect(jsonPath("$.name", is("Ingegneria dell'Informazione")))
			.andExpect(jsonPath("$.cfu", is(180)));
		
		verify(degreeCourseServiceMock, times(1)).save(refEq(course1));
		verifyNoMoreInteractions(degreeCourseServiceMock);
	}
	
	@Test
	public void saveTypeTest() throws Exception {
		TypeDegreeCourseDTO typeCourse1 = new TypeDegreeCourseDTO();
		typeCourse1.setIdtypeDegreeCourse(1);
		typeCourse1.setName("Ingegneria dell'Informazione");
		
		when(degreeCourseServiceMock.saveType(any(TypeDegreeCourseDTO.class))).thenReturn(typeCourse1);
		
		mockMvc.perform(post("/course/saveType").contentType(MediaType.APPLICATION_JSON).content(asJsonString(typeCourse1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.idtypeDegreeCourse", is(1)))
			.andExpect(jsonPath("$.name", is("Ingegneria dell'Informazione")));
		
		verify(degreeCourseServiceMock, times(1)).saveType(refEq(typeCourse1));
		verifyNoMoreInteractions(degreeCourseServiceMock);
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