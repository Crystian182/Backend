package it.unisalento.se.saw.restapi;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import it.unisalento.se.saw.Iservices.IClassroomService;
import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.repositories.ClassroomRepository;
import it.unisalento.se.saw.services.ClassroomService;
import net.minidev.json.JSONObject;

@RunWith(MockitoJUnitRunner.class)
public class ClassroomRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));
	
	private MockMvc mockMvc;

	@Mock
	private IClassroomService classroomServiceMock;
	
	@Mock
	private ClassroomRepository classroomRepository;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new ClassroomRestController(classroomServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getByIdTest() throws Exception {
		
		Random r = new Random();
		float lat = r.nextFloat();
		float lon = r.nextFloat();
		Building building = new Building();
		building.setIdbuilding(2);
		building.setName("test");
		building.setAddress("test2");
		building.setLatitude(lat);
		building.setLongitude(lon);
		
		float lat2 = r.nextFloat();
		float lon2 = r.nextFloat();
		Classroom classroom = new Classroom();
		classroom.setIdclassroom(1);
		classroom.setName("I1");
		classroom.setSeats(100);
		classroom.setLatitude(lat2);
		classroom.setLongitude(lon2);
		classroom.setBuilding(building);

		when(classroomServiceMock.getById(1)).thenReturn(classroom);
		
		mockMvc.perform(get("/classroom/getById/{id}",1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id",is(1)))
			.andExpect(jsonPath("$.name",is("I1")))
			.andExpect(jsonPath("$.seats",is(100)))
			.andExpect(jsonPath("$.building.id",is(2)))
			.andExpect(jsonPath("$.building.name",is("test")))
			.andExpect(jsonPath("$.building.address",is("test2")));
		
		verify(classroomServiceMock, times(1)).getById(1);
		verifyNoMoreInteractions(classroomServiceMock);
	}
	
	
	@Test
	public void getAllTest() throws Exception {
		
		Random r = new Random();
		float lat = r.nextFloat();
		float lon = r.nextFloat();
		float lat2 = r.nextFloat();
		float lon2 = r.nextFloat();
		
		Building building = new Building();
		building.setIdbuilding(2);
		building.setName("test");
		building.setAddress("test2");
		building.setLatitude(lat);
		building.setLongitude(lon);
		
		List<Classroom> classrooms = new ArrayList<Classroom>();
		
		for(int i=1; i<11; i++) {
			Classroom classroom = new Classroom();
			classroom.setIdclassroom(i);
			classroom.setName("I"+i);
			classroom.setSeats(100+i);
			classroom.setLatitude(lat2);
			classroom.setLongitude(lon2);
			classroom.setBuilding(building);
			classrooms.add(classroom);
		}		

		when(classroomServiceMock.getAll()).thenReturn(classrooms);
		
		mockMvc.perform(get("/classroom/getAll")).andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.[0].id",is(1)))
			.andExpect(jsonPath("$.[0].name",is("I1")))
			.andExpect(jsonPath("$.[0].seats",is(101)))
			.andExpect(jsonPath("$.[0].building.id",is(2)))
			.andExpect(jsonPath("$.[0].building.name",is("test")))
			.andExpect(jsonPath("$.[0].building.address",is("test2")))
			.andExpect(jsonPath("$.[9].id",is(10)))
			.andExpect(jsonPath("$.[9].name",is("I10")))
			.andExpect(jsonPath("$.[9].seats",is(110)))
			.andExpect(jsonPath("$.[9].building.id",is(2)))
			.andExpect(jsonPath("$.[9].building.name",is("test")))
			.andExpect(jsonPath("$.[9].building.address",is("test2")));
		
		verify(classroomServiceMock, times(1)).getAll();
		verifyNoMoreInteractions(classroomServiceMock);
		
	}
	
	/*@Test
	public void saveTest() throws Exception {
		
		Random r = new Random();
		float lat = r.nextFloat();
		float lon = r.nextFloat();
		Building building = new Building();
		building.setIdbuilding(2);
		building.setName("test");
		building.setAddress("test2");
		building.setLatitude(lat);
		building.setLongitude(lon);
		
		float lat2 = r.nextFloat();
		float lon2 = r.nextFloat();
		Classroom classroom = new Classroom();
		classroom.setIdclassroom(1);
		classroom.setName("I1");
		classroom.setSeats(58);
		classroom.setLatitude(lat2);
		classroom.setLongitude(lon2);
		classroom.setBuilding(building);

		when(classroomServiceMock.save(classroom)).thenReturn(classroom);
		
		JSONObject build = new JSONObject();
		build.put("id", 2);
		build.put("name", "Stecca");
		build.put("address", "test");
		build.put("latitude", lat2);
		build.put("longitude", lon2);
		
		JSONObject item = new JSONObject();
		item.put("id", null);
		item.put("name", "I1");
		item.put("seats", 58);
		item.put("latitude", lat2);
		item.put("longitude", lon2);
		item.put("building", build);
		
		mockMvc.perform(post("/classroom/save",item))
			.andExpect(status().isOk());
			/*.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id",is(1)))
			.andExpect(jsonPath("$.name",is("I1")))
			.andExpect(jsonPath("$.seats",is(100)))
			.andExpect(jsonPath("$.building.id",is(2)))
			.andExpect(jsonPath("$.building.name",is("test")))
			.andExpect(jsonPath("$.building.address",is("test2")));
		
		verify(classroomServiceMock, times(1)).getById(1);
		verifyNoMoreInteractions(classroomServiceMock);*/
	//}
	
	/*@Test
	public void editTest() throws Exception {
		Random r = new Random();
		float lat = r.nextFloat();
		float lon = r.nextFloat();
		Building building = new Building();
		building.setIdbuilding(2);
		building.setName("test");
		building.setAddress("test2");
		building.setLatitude(lat);
		building.setLongitude(lon);
		
		float lat2 = r.nextFloat();
		float lon2 = r.nextFloat();
		Classroom classroom = new Classroom();
		classroom.setIdclassroom(1);
		classroom.setName("I1");
		classroom.setSeats(58);
		classroom.setLatitude(lat2);
		classroom.setLongitude(lon2);
		classroom.setBuilding(building);
		
		JSONObject build = new JSONObject();
		build.put("id", 2);
		build.put("name", "Stecca");
		build.put("address", "test");
		build.put("latitude", lat2);
		build.put("longitude", lon2);
		
		JSONObject item = new JSONObject();
		item.put("id", 1);
		item.put("name", "I1");
		item.put("seats", 190);
		item.put("latitude", lat2);
		item.put("longitude", lon2);
		item.put("building", build);
		
		this.saveTest();
		
		mockMvc.perform(post("/classroom/edit", item))
			.andExpect(status().isOk());
			/*.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id",is(1)))
			.andExpect(jsonPath("$.name",is("I1")))
			.andExpect(jsonPath("$.seats",is(100)))
			.andExpect(jsonPath("$.building.id",is(2)))
			.andExpect(jsonPath("$.building.name",is("test")))
			.andExpect(jsonPath("$.building.address",is("test2")));*/
		
		//verify(classroomServiceMock, times(1)).edit(classroom);
		//verifyNoMoreInteractions(classroomServiceMock);
	//}
	
	/*@Test
	public void findClassroomByIdTest() throws Exception {
		
		Random r = new Random();
		Building building = new Building();
		building.setName("test");
		building.setAddress("test");
		building.setLatitude(r.nextFloat());
		building.setLongitude(r.nextFloat());
		
		Classroom classroom = new Classroom();
		classroom.setIdclassroom(1);
		classroom.setName("I1");
		classroom.setSeats(100);
		classroom.setLatitude(r.nextFloat());
		classroom.setLongitude(r.nextFloat());
		classroom.setBuilding(building);

		//when(classroomServiceMock.getById(1)).thenReturn(classroom);
		when(classroomRepositoryMock.findById(1).get()).thenReturn(classroom);
		
		mockMvc.perform(get("/classroom/getById/{id}",1)).andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.name",is("I1")))
			.andExpect(jsonPath("$.seats",is(100)));
		
		verify(classroomServiceMock, times(1)).getById(1);
		verifyNoMoreInteractions(classroomServiceMock);
		
	}*/
	
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

}
