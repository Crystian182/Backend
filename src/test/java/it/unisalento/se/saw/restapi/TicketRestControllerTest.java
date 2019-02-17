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

import it.unisalento.se.saw.Iservices.ITicketService;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.EmployeeDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TicketDTO;
import it.unisalento.se.saw.dto.TicketMessageDTO;
import it.unisalento.se.saw.dto.TicketStatusDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.repositories.TicketRepository;


@RunWith(MockitoJUnitRunner.class)
public class TicketRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private ITicketService ticketServiceMock;
	
	@Mock
	private TicketRepository ticketRepository;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new TicketRestController(ticketServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getAllTest() throws Exception {
		
		Date mockDate = new Date();
		
		List<ToolDTO> tools = new ArrayList<ToolDTO>();
		
		ToolDTO tool1 = new ToolDTO();
		tool1.setId(1);
		tool1.setName("Proiettore");
		tool1.setQuantity(2);
		tools.add(tool1);
		
		BuildingDTO building = new BuildingDTO();
		building.setId(1);
		building.setName("Stecca");
		
		ClassroomDTO classroom = new ClassroomDTO();
		classroom.setId(1);
		classroom.setName("I1");
		classroom.setSeats(100);
		classroom.setLat((float)17.3);
		classroom.setLng((float)21.9);
		classroom.setBuilding(building);
		classroom.setTool(tools);

		TeacherDTO teacher = new TeacherDTO();
		teacher.setIdteacher(1);
		teacher.setName("Antonio");
		teacher.setSurname("Leaci");
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setIdemployee(1);
		employee.setName("Lucio");
		employee.setSurname("De Cagna");
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setName("Antonio");
		user.setSurname("Leaci");
		
		TicketStatusDTO ticketStatus = new TicketStatusDTO();
		ticketStatus.setIdstatus(1);
		ticketStatus.setDescription("In lavorazione");
		
		List<TicketMessageDTO> messages = new ArrayList<TicketMessageDTO>();
		TicketMessageDTO message1 = new TicketMessageDTO();
		message1.setIdticket(1);
		message1.setIdticketmessage(1);
		message1.setText("Ho un problema");
		message1.setDate(mockDate);
		message1.setUser(user);
		messages.add(message1);
		
		List<TicketDTO> tickets = new ArrayList<TicketDTO>();
		
		TicketDTO ticket1 = new TicketDTO();
		ticket1.setId(1);
		ticket1.setClassroom(classroom);
		ticket1.setDate(mockDate);
		ticket1.setTitle("Problema");
		ticket1.setTeacher(teacher);
		ticket1.setEmployee(employee);
		ticket1.setTicketStatus(ticketStatus);
		ticket1.setTicketmessages(messages);
		tickets.add(ticket1);
		
		when(ticketServiceMock.getAll()).thenReturn(tickets);
		
		mockMvc.perform(get("/ticket/getAll"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].title", is("Problema")))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].teacher.idteacher", is(1)))
			.andExpect(jsonPath("$[0].teacher.name", is("Antonio")))
			.andExpect(jsonPath("$[0].teacher.surname", is("Leaci")))
			.andExpect(jsonPath("$[0].employee.idemployee", is(1)))
			.andExpect(jsonPath("$[0].employee.name", is("Lucio")))
			.andExpect(jsonPath("$[0].employee.surname", is("De Cagna")))
			.andExpect(jsonPath("$[0].ticketStatus.idstatus", is(1)))
			.andExpect(jsonPath("$[0].ticketStatus.description", is("In lavorazione")))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")))
			.andExpect(jsonPath("$[0].classroom.seats", is(100)))
			.andExpect(jsonPath("$[0].classroom.lat", is(17.3)))
			.andExpect(jsonPath("$[0].classroom.lng", is(21.9)))
			.andExpect(jsonPath("$[0].classroom.building.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.building.name", is("Stecca")))
			.andExpect(jsonPath("$[0].classroom.tool[0].id", is(1)))
			.andExpect(jsonPath("$[0].classroom.tool[0].name", is("Proiettore")))
			.andExpect(jsonPath("$[0].classroom.tool[0].quantity", is(2)))
			.andExpect(jsonPath("$[0].ticketmessages[0].idticketmessage", is(1)))
			.andExpect(jsonPath("$[0].ticketmessages[0].idticket", is(1)))
			.andExpect(jsonPath("$[0].ticketmessages[0].text", is("Ho un problema")))
			.andExpect(jsonPath("$[0].ticketmessages[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].ticketmessages[0].user.iduser", is(1)))
			.andExpect(jsonPath("$[0].ticketmessages[0].user.name", is("Antonio")))
			.andExpect(jsonPath("$[0].ticketmessages[0].user.surname", is("Leaci")));
		
		verify(ticketServiceMock, times(1)).getAll();
		verifyNoMoreInteractions(ticketServiceMock);
	}
	
	@Test
	public void getAllTeacherTicketsTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom = new ClassroomDTO();
		classroom.setId(1);
		classroom.setName("I1");
		classroom.setSeats(100);
		classroom.setLat((float)17.3);
		classroom.setLng((float)21.9);

		TeacherDTO teacher = new TeacherDTO();
		teacher.setIdteacher(1);
		teacher.setName("Antonio");
		teacher.setSurname("Leaci");
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setIdemployee(1);
		employee.setName("Lucio");
		employee.setSurname("De Cagna");
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setName("Antonio");
		user.setSurname("Leaci");
		
		TicketStatusDTO ticketStatus = new TicketStatusDTO();
		ticketStatus.setIdstatus(1);
		ticketStatus.setDescription("In lavorazione");
		
		List<TicketMessageDTO> messages = new ArrayList<TicketMessageDTO>();
		TicketMessageDTO message1 = new TicketMessageDTO();
		message1.setIdticket(1);
		message1.setIdticketmessage(1);
		message1.setText("Ho un problema");
		message1.setDate(mockDate);
		message1.setUser(user);
		messages.add(message1);
		
		List<TicketDTO> tickets = new ArrayList<TicketDTO>();
		
		TicketDTO ticket1 = new TicketDTO();
		ticket1.setId(1);
		ticket1.setClassroom(classroom);
		ticket1.setDate(mockDate);
		ticket1.setTitle("Problema");
		ticket1.setTeacher(teacher);
		ticket1.setEmployee(employee);
		ticket1.setTicketStatus(ticketStatus);
		ticket1.setTicketmessages(messages);
		tickets.add(ticket1);
		
		when(ticketServiceMock.getAllTeacherTickets(1)).thenReturn(tickets);
		
		mockMvc.perform(get("/ticket/getAllTeacherTickets/{idteacher}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].title", is("Problema")))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].teacher.idteacher", is(1)))
			.andExpect(jsonPath("$[0].teacher.name", is("Antonio")))
			.andExpect(jsonPath("$[0].teacher.surname", is("Leaci")))
			.andExpect(jsonPath("$[0].employee.idemployee", is(1)))
			.andExpect(jsonPath("$[0].employee.name", is("Lucio")))
			.andExpect(jsonPath("$[0].employee.surname", is("De Cagna")))
			.andExpect(jsonPath("$[0].ticketStatus.idstatus", is(1)))
			.andExpect(jsonPath("$[0].ticketStatus.description", is("In lavorazione")))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")))
			.andExpect(jsonPath("$[0].classroom.seats", is(100)))
			.andExpect(jsonPath("$[0].classroom.lat", is(17.3)))
			.andExpect(jsonPath("$[0].classroom.lng", is(21.9)))
			.andExpect(jsonPath("$[0].ticketmessages[0].idticketmessage", is(1)))
			.andExpect(jsonPath("$[0].ticketmessages[0].idticket", is(1)))
			.andExpect(jsonPath("$[0].ticketmessages[0].text", is("Ho un problema")))
			.andExpect(jsonPath("$[0].ticketmessages[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].ticketmessages[0].user.iduser", is(1)))
			.andExpect(jsonPath("$[0].ticketmessages[0].user.name", is("Antonio")))
			.andExpect(jsonPath("$[0].ticketmessages[0].user.surname", is("Leaci")));
		
		verify(ticketServiceMock, times(1)).getAllTeacherTickets(1);
		verifyNoMoreInteractions(ticketServiceMock);
	}
	
	@Test
	public void getByIdTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom = new ClassroomDTO();
		classroom.setId(1);
		classroom.setName("I1");
		classroom.setSeats(100);
		classroom.setLat((float)17.3);
		classroom.setLng((float)21.9);

		TeacherDTO teacher = new TeacherDTO();
		teacher.setIdteacher(1);
		teacher.setName("Antonio");
		teacher.setSurname("Leaci");
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setIdemployee(1);
		employee.setName("Lucio");
		employee.setSurname("De Cagna");
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setName("Antonio");
		user.setSurname("Leaci");
		
		TicketStatusDTO ticketStatus = new TicketStatusDTO();
		ticketStatus.setIdstatus(1);
		ticketStatus.setDescription("In lavorazione");
		
		List<TicketMessageDTO> messages = new ArrayList<TicketMessageDTO>();
		TicketMessageDTO message1 = new TicketMessageDTO();
		message1.setIdticket(1);
		message1.setIdticketmessage(1);
		message1.setText("Ho un problema");
		message1.setDate(mockDate);
		message1.setUser(user);
		messages.add(message1);
		
		TicketDTO ticket1 = new TicketDTO();
		ticket1.setId(1);
		ticket1.setClassroom(classroom);
		ticket1.setDate(mockDate);
		ticket1.setTitle("Problema");
		ticket1.setTeacher(teacher);
		ticket1.setEmployee(employee);
		ticket1.setTicketStatus(ticketStatus);
		ticket1.setTicketmessages(messages);
		
		when(ticketServiceMock.getById(1)).thenReturn(ticket1);
		
		mockMvc.perform(get("/ticket/getById/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.title", is("Problema")))
			.andExpect(jsonPath("$.date", is(mockDate.getTime())))
			.andExpect(jsonPath("$.teacher.idteacher", is(1)))
			.andExpect(jsonPath("$.teacher.name", is("Antonio")))
			.andExpect(jsonPath("$.teacher.surname", is("Leaci")))
			.andExpect(jsonPath("$.employee.idemployee", is(1)))
			.andExpect(jsonPath("$.employee.name", is("Lucio")))
			.andExpect(jsonPath("$.employee.surname", is("De Cagna")))
			.andExpect(jsonPath("$.ticketStatus.idstatus", is(1)))
			.andExpect(jsonPath("$.ticketStatus.description", is("In lavorazione")))
			.andExpect(jsonPath("$.classroom.id", is(1)))
			.andExpect(jsonPath("$.classroom.name", is("I1")))
			.andExpect(jsonPath("$.classroom.seats", is(100)))
			.andExpect(jsonPath("$.classroom.lat", is(17.3)))
			.andExpect(jsonPath("$.classroom.lng", is(21.9)))
			.andExpect(jsonPath("$.ticketmessages[0].idticketmessage", is(1)))
			.andExpect(jsonPath("$.ticketmessages[0].idticket", is(1)))
			.andExpect(jsonPath("$.ticketmessages[0].text", is("Ho un problema")))
			.andExpect(jsonPath("$.ticketmessages[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$.ticketmessages[0].user.iduser", is(1)))
			.andExpect(jsonPath("$.ticketmessages[0].user.name", is("Antonio")))
			.andExpect(jsonPath("$.ticketmessages[0].user.surname", is("Leaci")));
		
		verify(ticketServiceMock, times(1)).getById(1);
		verifyNoMoreInteractions(ticketServiceMock);
	}
	
	@Test
	public void getAllClassroomTicketsTest() throws Exception {
		
		Date mockDate = new Date();
		
		ClassroomDTO classroom = new ClassroomDTO();
		classroom.setId(1);
		classroom.setName("I1");
		classroom.setSeats(100);
		classroom.setLat((float)17.3);
		classroom.setLng((float)21.9);

		TeacherDTO teacher = new TeacherDTO();
		teacher.setIdteacher(1);
		teacher.setName("Antonio");
		teacher.setSurname("Leaci");
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setIdemployee(1);
		employee.setName("Lucio");
		employee.setSurname("De Cagna");
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setName("Antonio");
		user.setSurname("Leaci");
		
		TicketStatusDTO ticketStatus = new TicketStatusDTO();
		ticketStatus.setIdstatus(1);
		ticketStatus.setDescription("In lavorazione");
		
		List<TicketMessageDTO> messages = new ArrayList<TicketMessageDTO>();
		TicketMessageDTO message1 = new TicketMessageDTO();
		message1.setIdticket(1);
		message1.setIdticketmessage(1);
		message1.setText("Ho un problema");
		message1.setDate(mockDate);
		message1.setUser(user);
		messages.add(message1);
		
		List<TicketDTO> tickets = new ArrayList<TicketDTO>();
		
		TicketDTO ticket1 = new TicketDTO();
		ticket1.setId(1);
		ticket1.setClassroom(classroom);
		ticket1.setDate(mockDate);
		ticket1.setTitle("Problema");
		ticket1.setTeacher(teacher);
		ticket1.setEmployee(employee);
		ticket1.setTicketStatus(ticketStatus);
		ticket1.setTicketmessages(messages);
		tickets.add(ticket1);
		
		when(ticketServiceMock.getAllClassroomTickets(1)).thenReturn(tickets);
		
		mockMvc.perform(get("/ticket/getAllClassroomTickets/{idclassroom}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].title", is("Problema")))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].teacher.idteacher", is(1)))
			.andExpect(jsonPath("$[0].teacher.name", is("Antonio")))
			.andExpect(jsonPath("$[0].teacher.surname", is("Leaci")))
			.andExpect(jsonPath("$[0].employee.idemployee", is(1)))
			.andExpect(jsonPath("$[0].employee.name", is("Lucio")))
			.andExpect(jsonPath("$[0].employee.surname", is("De Cagna")))
			.andExpect(jsonPath("$[0].ticketStatus.idstatus", is(1)))
			.andExpect(jsonPath("$[0].ticketStatus.description", is("In lavorazione")))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")))
			.andExpect(jsonPath("$[0].classroom.seats", is(100)))
			.andExpect(jsonPath("$[0].classroom.lat", is(17.3)))
			.andExpect(jsonPath("$[0].classroom.lng", is(21.9)))
			.andExpect(jsonPath("$[0].ticketmessages[0].idticketmessage", is(1)))
			.andExpect(jsonPath("$[0].ticketmessages[0].idticket", is(1)))
			.andExpect(jsonPath("$[0].ticketmessages[0].text", is("Ho un problema")))
			.andExpect(jsonPath("$[0].ticketmessages[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].ticketmessages[0].user.iduser", is(1)))
			.andExpect(jsonPath("$[0].ticketmessages[0].user.name", is("Antonio")))
			.andExpect(jsonPath("$[0].ticketmessages[0].user.surname", is("Leaci")));
		
		verify(ticketServiceMock, times(1)).getAllClassroomTickets(1);
		verifyNoMoreInteractions(ticketServiceMock);
	}
	
	@Test
	public void getMessagesTest() throws Exception {
		
		Date mockDate = new Date();
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setName("Antonio");
		user.setSurname("Leaci");
		
		List<TicketMessageDTO> messages = new ArrayList<TicketMessageDTO>();
		TicketMessageDTO message1 = new TicketMessageDTO();
		message1.setIdticket(1);
		message1.setIdticketmessage(1);
		message1.setText("Ho un problema");
		message1.setDate(mockDate);
		message1.setUser(user);
		messages.add(message1);
		
		when(ticketServiceMock.getMessages(1)).thenReturn(messages);
		
		mockMvc.perform(get("/ticket/getMessages/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idticketmessage", is(1)))
			.andExpect(jsonPath("$[0].idticket", is(1)))
			.andExpect(jsonPath("$[0].text", is("Ho un problema")))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].user.iduser", is(1)))
			.andExpect(jsonPath("$[0].user.name", is("Antonio")))
			.andExpect(jsonPath("$[0].user.surname", is("Leaci")));
		
		verify(ticketServiceMock, times(1)).getMessages(1);
		verifyNoMoreInteractions(ticketServiceMock);
	}
	
	@Test
	public void deleteTest() throws Exception {
			
		mockMvc.perform(post("/ticket/delete/{id}", 1))
			.andExpect(status().isOk());
		
		verify(ticketServiceMock, times(1)).delete(1);;
		verifyNoMoreInteractions(ticketServiceMock);
	}
	
	public void saveTest() throws Exception {
		Date mockDate = new Date();
		
		ClassroomDTO classroom = new ClassroomDTO();
		classroom.setId(1);
		classroom.setName("I1");
		classroom.setSeats(100);
		classroom.setLat((float)17.3);
		classroom.setLng((float)21.9);

		TeacherDTO teacher = new TeacherDTO();
		teacher.setIdteacher(1);
		teacher.setName("Antonio");
		teacher.setSurname("Leaci");
		
		TicketDTO ticket1 = new TicketDTO();
		ticket1.setId(1);
		ticket1.setClassroom(classroom);
		ticket1.setDate(mockDate);
		ticket1.setTitle("Problema");
		ticket1.setTeacher(teacher);
		
		when(ticketServiceMock.save(ticket1)).thenReturn(ticket1);
		
		mockMvc.perform(post("/ticket/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(ticket1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].title", is("Problema")))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].teacher.idteacher", is(1)))
			.andExpect(jsonPath("$[0].teacher.name", is("Antonio")))
			.andExpect(jsonPath("$[0].teacher.surname", is("Leaci")))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")))
			.andExpect(jsonPath("$[0].classroom.seats", is(100)))
			.andExpect(jsonPath("$[0].classroom.lat", is(17.3)))
			.andExpect(jsonPath("$[0].classroom.lng", is(21.9)));
		
		verify(ticketServiceMock, times(1)).save(ticket1);
		verifyNoMoreInteractions(ticketServiceMock);
	}
	
	public void saveStaffTest() throws Exception {
		Date mockDate = new Date();
		
		ClassroomDTO classroom = new ClassroomDTO();
		classroom.setId(1);
		classroom.setName("I1");
		classroom.setSeats(100);
		classroom.setLat((float)17.3);
		classroom.setLng((float)21.9);

		TeacherDTO teacher = new TeacherDTO();
		teacher.setIdteacher(1);
		teacher.setName("Antonio");
		teacher.setSurname("Leaci");
		
		TicketDTO ticket1 = new TicketDTO();
		ticket1.setId(1);
		ticket1.setClassroom(classroom);
		ticket1.setDate(mockDate);
		ticket1.setTitle("Problema");
		ticket1.setTeacher(teacher);
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setName("Antonio");
		user.setSurname("Leaci");
		
		TicketMessageDTO message1 = new TicketMessageDTO();
		message1.setIdticket(1);
		message1.setIdticketmessage(1);
		message1.setText("Ho un problema");
		message1.setDate(mockDate);
		message1.setUser(user);
		
		when(ticketServiceMock.saveMessages(ticket1, message1)).thenReturn(ticket1);
		
		mockMvc.perform(post("/ticket/savestaff").contentType(MediaType.APPLICATION_JSON).content(asJsonString(ticket1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].title", is("Problema")))
			.andExpect(jsonPath("$[0].date", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].teacher.idteacher", is(1)))
			.andExpect(jsonPath("$[0].teacher.name", is("Antonio")))
			.andExpect(jsonPath("$[0].teacher.surname", is("Leaci")))
			.andExpect(jsonPath("$[0].classroom.id", is(1)))
			.andExpect(jsonPath("$[0].classroom.name", is("I1")))
			.andExpect(jsonPath("$[0].classroom.seats", is(100)))
			.andExpect(jsonPath("$[0].classroom.lat", is(17.3)))
			.andExpect(jsonPath("$[0].classroom.lng", is(21.9)));
		
		verify(ticketServiceMock, times(1)).saveMessages(ticket1, message1);
		verifyNoMoreInteractions(ticketServiceMock);
	}
	
	public void saveMessageTest() throws Exception {
		Date mockDate = new Date();
		
		UserDTO user = new UserDTO();
		user.setIduser(1);
		user.setName("Antonio");
		user.setSurname("Leaci");
		
		TicketMessageDTO message1 = new TicketMessageDTO();
		message1.setIdticket(1);
		message1.setIdticketmessage(1);
		message1.setText("Ho un problema");
		message1.setDate(mockDate);
		message1.setUser(user);
		
		when(ticketServiceMock.saveMessage(message1)).thenReturn(message1);
		
		mockMvc.perform(post("/ticket/savemessage").contentType(MediaType.APPLICATION_JSON).content(asJsonString(message1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.idticketmessage", is(1)))
			.andExpect(jsonPath("$.idticket", is(1)))
			.andExpect(jsonPath("$.text", is("Ho un problema")))
			.andExpect(jsonPath("$.date", is(mockDate.getTime())))
			.andExpect(jsonPath("$.user.iduser", is(1)))
			.andExpect(jsonPath("$.user.name", is("Antonio")))
			.andExpect(jsonPath("$.user.surname", is("Leaci")));
		
		verify(ticketServiceMock, times(1)).saveMessage(message1);
		verifyNoMoreInteractions(ticketServiceMock);
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