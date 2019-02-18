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

import it.unisalento.se.saw.Iservices.IBuildingService;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.repositories.BuildingRepository;


@RunWith(MockitoJUnitRunner.class)
public class BuildingRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private IBuildingService buildingServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new BuildingRestController(buildingServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getAllTest() throws Exception {
		
		List<ClassroomDTO> classrooms1 = new ArrayList<ClassroomDTO>();
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classrooms1.add(classroom1);
		
		List<BuildingDTO> buildings = new ArrayList<BuildingDTO>();
		BuildingDTO building1 = new BuildingDTO();
		building1.setId(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		building1.setClassrooms(classrooms1);
		building1.setPic("abc");
		buildings.add(building1);
		
		when(buildingServiceMock.getAll()).thenReturn(buildings);
		
		mockMvc.perform(get("/building/getAll"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("Stecca")))
			.andExpect(jsonPath("$[0].address", is("Via Ecotekne")))
			.andExpect(jsonPath("$[0].pic", is("abc")))
			.andExpect(jsonPath("$[0].classrooms[0].id", is(1)))
			.andExpect(jsonPath("$[0].classrooms[0].name", is("I1")))
			.andExpect(jsonPath("$[0].classrooms[0].seats", is(180)));
		
		verify(buildingServiceMock, times(1)).getAll();
		verifyNoMoreInteractions(buildingServiceMock);
	}
	
	@Test
	public void getByIdTest() throws Exception {
		
		List<ClassroomDTO> classrooms1 = new ArrayList<ClassroomDTO>();
		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classrooms1.add(classroom1);
		
		BuildingDTO building1 = new BuildingDTO();
		building1.setId(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		building1.setClassrooms(classrooms1);
		
		when(buildingServiceMock.getById(1)).thenReturn(building1);
		
		mockMvc.perform(get("/building/getById/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("Stecca")))
			.andExpect(jsonPath("$.address", is("Via Ecotekne")))
			.andExpect(jsonPath("$.classrooms[0].id", is(1)))
			.andExpect(jsonPath("$.classrooms[0].name", is("I1")))
			.andExpect(jsonPath("$.classrooms[0].seats", is(180)));
		
		verify(buildingServiceMock, times(1)).getById(1);
		verifyNoMoreInteractions(buildingServiceMock);
	}
	
	@Test
	public void deleteTest() throws Exception {
			
		mockMvc.perform(get("/building/delete/{id}", 1))
			.andExpect(status().isOk());
		
		verify(buildingServiceMock, times(1)).delete(1);;
		verifyNoMoreInteractions(buildingServiceMock);
	}
	
	@Test
	public void saveTest() throws Exception {
		BuildingDTO building1 = new BuildingDTO();
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		
		when(buildingServiceMock.save(any(BuildingDTO.class))).thenReturn(building1);
		
		mockMvc.perform(post("/building/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(building1)))
			.andExpect(status().isOk());
		
		verify(buildingServiceMock, times(1)).save(refEq(building1));
		verifyNoMoreInteractions(buildingServiceMock);
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