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

import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.domain.EnrollmentStatus;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.EnrollmentStatusDTO;
import it.unisalento.se.saw.dto.StudentDTO;
import it.unisalento.se.saw.dto.StudentHasDegreeCourseDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;

@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private IUserService userServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new UserRestController(userServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getByIdTest() throws Exception {
		
		Date mockDate = new Date();
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setSsn("VRG");
		user.setName("Cristian");
		user.setSurname("Vergallo");
		user.setEmail("cristian.vergallo@email.it");
		user.setDateBirth(mockDate);
		user.setPlaceBirth("Lecce");
		user.setResidence("Lecce");
		user.setDomicile("Lecce");
		user.setPhone("333");
		user.setSex("M");
		user.setCitizenship("ITA");
		user.setType("student");
		user.setToken("abc");
		user.setPropic("123");
		
		when(userServiceMock.getById(1)).thenReturn(user);
		
		mockMvc.perform(get("/user/getById/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.iduser", is(1)))
			.andExpect(jsonPath("$.ssn", is("VRG")))
			.andExpect(jsonPath("$.name", is("Cristian")))
			.andExpect(jsonPath("$.surname", is("Vergallo")))
			.andExpect(jsonPath("$.email", is("cristian.vergallo@email.it")))
			.andExpect(jsonPath("$.dateBirth", is(mockDate.getTime())))
			.andExpect(jsonPath("$.placeBirth", is("Lecce")))
			.andExpect(jsonPath("$.residence", is("Lecce")))
			.andExpect(jsonPath("$.domicile", is("Lecce")))
			.andExpect(jsonPath("$.sex", is("M")))
			.andExpect(jsonPath("$.phone", is("333")))
			.andExpect(jsonPath("$.citizenship", is("ITA")))
			.andExpect(jsonPath("$.type", is("student")))
			.andExpect(jsonPath("$.token", is("abc")))
			.andExpect(jsonPath("$.propic", is("123")));
		
		verify(userServiceMock, times(1)).getById(1);
		verifyNoMoreInteractions(userServiceMock);
	}
	
	@Test
	public void getTeachersTest() throws Exception {
		
		Date mockDate = new Date();
		
		List<TeacherDTO> teachers = new ArrayList<TeacherDTO>();
		TeacherDTO teacher = new TeacherDTO();
		teacher.setIdteacher(1);
		teacher.setName("Antonio");
		teacher.setSurname("Leaci");
		teacher.setResidence("Lecce");
		teacher.setDomicile("Lecce");
		teacher.setPlaceBirth("Bari");
		teacher.setDateBirth(mockDate);
		teachers.add(teacher);
		
		when(userServiceMock.getTeachers()).thenReturn(teachers);
		
		mockMvc.perform(get("/user/getTeachers"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].name", is("Antonio")))
			.andExpect(jsonPath("$[0].surname", is("Leaci")))
			.andExpect(jsonPath("$[0].dateBirth", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].placeBirth", is("Bari")))
			.andExpect(jsonPath("$[0].domicile", is("Lecce")))
			.andExpect(jsonPath("$[0].residence", is("Lecce")));
		
		verify(userServiceMock, times(1)).getTeachers();
		verifyNoMoreInteractions(userServiceMock);
	}
	
	@Test
	public void getTeachersByIdTest() throws Exception {
		
		Date mockDate = new Date();
		
		TeacherDTO teacher = new TeacherDTO();
		teacher.setIdteacher(1);
		teacher.setName("Antonio");
		teacher.setSurname("Leaci");
		teacher.setResidence("Lecce");
		teacher.setDomicile("Lecce");
		teacher.setPlaceBirth("Bari");
		teacher.setDateBirth(mockDate);
		
		when(userServiceMock.getTeacherById(1)).thenReturn(teacher);
		
		mockMvc.perform(get("/user/getTeacherById/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.name", is("Antonio")))
			.andExpect(jsonPath("$.surname", is("Leaci")))
			.andExpect(jsonPath("$.dateBirth", is(mockDate.getTime())))
			.andExpect(jsonPath("$.placeBirth", is("Bari")))
			.andExpect(jsonPath("$.domicile", is("Lecce")))
			.andExpect(jsonPath("$.residence", is("Lecce")));
		
		verify(userServiceMock, times(1)).getTeacherById(1);
		verifyNoMoreInteractions(userServiceMock);
	}
	
	@Test
	public void getInfoTest() throws Exception {
		
		Date mockDate = new Date();
		
		DegreeCourseDTO course1 = new DegreeCourseDTO();
		course1.setIdcourse(1);
		course1.setName("Ingegneria dell'Informazione");
		
		StudentDTO student = new StudentDTO();
		student.setIdstudent(1);
		student.setName("Cristian");
		student.setSurname("Vergallo");
		
		EnrollmentStatusDTO status = new EnrollmentStatusDTO();
		status.setIdenrollmentStatus(1);
		status.setDescription("Iscritto");
		
		StudentHasDegreeCourseDTO enrollment = new StudentHasDegreeCourseDTO();
		enrollment.setDate(mockDate);
		enrollment.setDegreeCourse(course1);
		enrollment.setStudent(student);
		enrollment.setEnrollmentStatus(status);
		
		when(userServiceMock.getInfo(1)).thenReturn(enrollment);
		
		mockMvc.perform(get("/user/getInfoStudent/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.date", is(mockDate.getTime())))
			.andExpect(jsonPath("$.degreeCourse.idcourse", is(1)))
			.andExpect(jsonPath("$.degreeCourse.name", is("Ingegneria dell'Informazione")))
			.andExpect(jsonPath("$.enrollmentStatus.idenrollmentStatus", is(1)))
			.andExpect(jsonPath("$.enrollmentStatus.description", is("Iscritto")))
			.andExpect(jsonPath("$.student.idstudent", is(1)))
			.andExpect(jsonPath("$.student.name", is("Cristian")))
			.andExpect(jsonPath("$.student.surname", is("Vergallo")));
		
		verify(userServiceMock, times(1)).getInfo(1);
		verifyNoMoreInteractions(userServiceMock);
	}
	
	public void saveTest() throws Exception {
		
		Date mockDate = new Date();
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setSsn("VRG");
		user.setName("Cristian");
		user.setSurname("Vergallo");
		user.setEmail("cristian.vergallo@email.it");
		user.setDateBirth(mockDate);
		user.setPlaceBirth("Lecce");
		user.setResidence("Lecce");
		user.setDomicile("Lecce");
		user.setPhone("333");
		user.setSex("M");
		user.setCitizenship("ITA");
		user.setType("student");
		user.setPropic("123");
		
		when(userServiceMock.save(user)).thenReturn(user);
		
		mockMvc.perform(post("/user/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.iduser", is(1)))
			.andExpect(jsonPath("$.ssn", is("VRG")))
			.andExpect(jsonPath("$.name", is("Cristian")))
			.andExpect(jsonPath("$.surname", is("Vergallo")))
			.andExpect(jsonPath("$.email", is("cristian.vergallo@email.it")))
			.andExpect(jsonPath("$.dateBirth", is(mockDate.getTime())))
			.andExpect(jsonPath("$.placeBirth", is("Lecce")))
			.andExpect(jsonPath("$.residence", is("Lecce")))
			.andExpect(jsonPath("$.domicile", is("Lecce")))
			.andExpect(jsonPath("$.sex", is("M")))
			.andExpect(jsonPath("$.phone", is("333")))
			.andExpect(jsonPath("$.citizenship", is("ITA")))
			.andExpect(jsonPath("$.type", is("student")))
			.andExpect(jsonPath("$.propic", is("123")));
		
		verify(userServiceMock, times(1)).save(user);
		verifyNoMoreInteractions(userServiceMock);
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
