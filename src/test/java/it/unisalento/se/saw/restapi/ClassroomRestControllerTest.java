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

import it.unisalento.se.saw.Iservices.IClassroomService;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;


@RunWith(MockitoJUnitRunner.class)
public class ClassroomRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private IClassroomService classroomServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new ClassroomRestController(classroomServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getAllTest() throws Exception {
		
		List<ClassroomDTO> classrooms = new ArrayList<ClassroomDTO>();
		
		BuildingDTO building1 = new BuildingDTO();
		building1.setId(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
		when(classroomServiceMock.getAll()).thenReturn(classrooms);
		
		mockMvc.perform(get("/classroom/getAll"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("I1")))
			.andExpect(jsonPath("$[0].seats", is(180)))
			.andExpect(jsonPath("$[0].building.id", is(1)))
			.andExpect(jsonPath("$[0].building.name", is("Stecca")))
			.andExpect(jsonPath("$[0].building.address", is("Via Ecotekne")));
		
		verify(classroomServiceMock, times(1)).getAll();
		verifyNoMoreInteractions(classroomServiceMock);
	}
	
	@Test
	public void getByIdTest() throws Exception {
		
		BuildingDTO building1 = new BuildingDTO();
		building1.setId(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		
		when(classroomServiceMock.getById(1)).thenReturn(classroom1);
		
		mockMvc.perform(get("/classroom/getById/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("I1")))
			.andExpect(jsonPath("$.seats", is(180)))
			.andExpect(jsonPath("$.building.id", is(1)))
			.andExpect(jsonPath("$.building.name", is("Stecca")))
			.andExpect(jsonPath("$.building.address", is("Via Ecotekne")));
		
		verify(classroomServiceMock, times(1)).getById(1);
		verifyNoMoreInteractions(classroomServiceMock);
	}
	
	@Test
	public void getByIdBuildingTest() throws Exception {
		
		List<ClassroomDTO> classrooms = new ArrayList<ClassroomDTO>();
		
		BuildingDTO building1 = new BuildingDTO();
		building1.setId(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
		when(classroomServiceMock.getByIdBuilding(1)).thenReturn(classrooms);
		
		mockMvc.perform(get("/classroom/getByIdBuilding/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("I1")))
			.andExpect(jsonPath("$[0].seats", is(180)))
			.andExpect(jsonPath("$[0].building.id", is(1)))
			.andExpect(jsonPath("$[0].building.name", is("Stecca")))
			.andExpect(jsonPath("$[0].building.address", is("Via Ecotekne")));
		
		verify(classroomServiceMock, times(1)).getByIdBuilding(1);
		verifyNoMoreInteractions(classroomServiceMock);
	}
	
	@Test
	public void getByIdBuildingAndNameTest() throws Exception {
		
		List<ClassroomDTO> classrooms = new ArrayList<ClassroomDTO>();
		
		BuildingDTO building1 = new BuildingDTO();
		building1.setId(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
		when(classroomServiceMock.getByIdBuildingAndName(1, "Stecca")).thenReturn(classrooms);
		
		mockMvc.perform(get("/classroom/getByIdBuildingAndName/{idBuilding}&{classroom}", 1, "Stecca"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("I1")))
			.andExpect(jsonPath("$[0].seats", is(180)))
			.andExpect(jsonPath("$[0].building.id", is(1)))
			.andExpect(jsonPath("$[0].building.name", is("Stecca")))
			.andExpect(jsonPath("$[0].building.address", is("Via Ecotekne")));
		
		verify(classroomServiceMock, times(1)).getByIdBuildingAndName(1, "Stecca");
		verifyNoMoreInteractions(classroomServiceMock);
	}
	
	@Test
	public void getByNameTest() throws Exception {
		
		List<ClassroomDTO> classrooms = new ArrayList<ClassroomDTO>();
		
		BuildingDTO building1 = new BuildingDTO();
		building1.setId(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
		when(classroomServiceMock.getByName("I1")).thenReturn(classrooms);
		
		mockMvc.perform(get("/classroom/getByName/{name}", "I1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("I1")))
			.andExpect(jsonPath("$[0].seats", is(180)))
			.andExpect(jsonPath("$[0].building.id", is(1)))
			.andExpect(jsonPath("$[0].building.name", is("Stecca")))
			.andExpect(jsonPath("$[0].building.address", is("Via Ecotekne")));
		
		verify(classroomServiceMock, times(1)).getByName("I1");
		verifyNoMoreInteractions(classroomServiceMock);
	}
	
	@Test
	public void deleteTest() throws Exception {
			
		mockMvc.perform(get("/classroom/delete/{id}", 1))
			.andExpect(status().isOk());
		
		verify(classroomServiceMock, times(1)).delete(1);;
		verifyNoMoreInteractions(classroomServiceMock);
	}
	
	@Test
	public void saveTest() throws Exception {
		BuildingDTO building1 = new BuildingDTO();
		building1.setId(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		
		when(classroomServiceMock.save(any(ClassroomDTO.class))).thenReturn(classroom1);
		
		mockMvc.perform(post("/classroom/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(classroom1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is("I1")))
			.andExpect(jsonPath("$.seats", is(180)));
		
		verify(classroomServiceMock, times(1)).save(any(ClassroomDTO.class));
		verifyNoMoreInteractions(classroomServiceMock);
	}
	
	@Test
	public void getAvailableClassroomByIdBuildingTest() throws Exception {
		List<ClassroomDTO> classrooms = new ArrayList<ClassroomDTO>();
		
		BuildingDTO building1 = new BuildingDTO();
		building1.setId(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
		TypeLessonDTO typeLesson = new TypeLessonDTO();
		typeLesson.setIdtypeLesson(1);
		
		when(classroomServiceMock.getAvailableByIdBuilding(any(Integer.class), any(TypeLessonDTO.class))).thenReturn(classrooms);
		
		mockMvc.perform(post("/classroom/getAvailableByIdBuilding/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(typeLesson)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("I1")))
			.andExpect(jsonPath("$[0].seats", is(180)));
		
		verify(classroomServiceMock, times(1)).getAvailableByIdBuilding(any(Integer.class), any(TypeLessonDTO.class));
		verifyNoMoreInteractions(classroomServiceMock);
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
