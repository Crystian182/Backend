package it.unisalento.se.saw.services;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.dialect.function.TemplateRenderer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import it.unisalento.se.saw.domain.AcademicYear;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.Day;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Employee;
import it.unisalento.se.saw.domain.Exam;
import it.unisalento.se.saw.domain.ExamStatus;
import it.unisalento.se.saw.domain.ExamType;
import it.unisalento.se.saw.domain.Feedback;
import it.unisalento.se.saw.domain.FeedbackLesson;
import it.unisalento.se.saw.domain.FeedbackLessonId;
import it.unisalento.se.saw.domain.Lesson;
import it.unisalento.se.saw.domain.Result;
import it.unisalento.se.saw.domain.Scheduler;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.domain.StudentHasExam;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.Term;
import it.unisalento.se.saw.domain.Ticket;
import it.unisalento.se.saw.domain.TicketMessage;
import it.unisalento.se.saw.domain.TicketStatus;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.domain.TypeLesson;
import it.unisalento.se.saw.domain.TypeSubject;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.EmployeeDTO;
import it.unisalento.se.saw.dto.ExamDTO;
import it.unisalento.se.saw.dto.ExamEnrollmentDTO;
import it.unisalento.se.saw.dto.ExamTypeDTO;
import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.ResultDTO;
import it.unisalento.se.saw.dto.StudentDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TicketDTO;
import it.unisalento.se.saw.dto.TicketMessageDTO;
import it.unisalento.se.saw.dto.TicketStatusDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.exceptions.LessonNotFoundException;
import it.unisalento.se.saw.exceptions.TicketNotFoundException;
import it.unisalento.se.saw.repositories.FeedbackLessonRepository;
import it.unisalento.se.saw.repositories.FeedbackRepository;
import it.unisalento.se.saw.repositories.LessonRepository;
import it.unisalento.se.saw.repositories.TicketMessageRepository;
import it.unisalento.se.saw.repositories.TicketRepository;
import it.unisalento.se.saw.repositories.TicketStatusRepository;
import it.unisalento.se.saw.repositories.TypeLessonRepository;
import it.unisalento.se.saw.repositories.UserRepository;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.imageio.IIOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {
    
	@Mock
	TicketRepository ticketRepository;
	
	@Mock
	TicketMessageRepository ticketMessageRepository;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	TicketStatusRepository statusRepository;

    @InjectMocks
    TicketService ticketService;

    @Test()
    public void getAllTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	User user1 = new User();
    	user1.setIduser(2);
    	user1.setName("Lucio");
    	user1.setSurname("De Cagna");
    	
    	Employee employee = new Employee();
    	employee.setIduser(2);
    	employee.setUser(user1);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		
		TicketStatus ticketStatus = new TicketStatus();
		ticketStatus.setIdticketStatus(1);
		ticketStatus.setDescription("In lavorazione");
    	
    	List<Ticket> tickets = new ArrayList<Ticket>();
    	Ticket ticket = new Ticket();
    	ticket.setIdticket(1);
    	ticket.setTeacher(teacher);
    	ticket.setEmployee(employee);
    	ticket.setClassroom(classroom1);
    	ticket.setTicketStatus(ticketStatus);
    	ticket.setTitle("Problema");
    	ticket.setDate(mockDate);
    	tickets.add(ticket);
    	
    	List<TicketMessage> ticketMessages = new ArrayList<TicketMessage>();
    	
    	TicketMessage ticketMessage = new TicketMessage();
    	ticketMessage.setUser(user);
    	ticketMessage.setIdticketMessage(1);
    	ticketMessage.setTicket(ticket);
    	ticketMessage.setDate(mockDate);
    	ticketMessage.setText("Ho un problema");
    	ticketMessages.add(ticketMessage);
    	
        when(ticketRepository.findAll()).thenReturn(tickets);
        when(ticketRepository.getMessages(1)).thenReturn(ticketMessages);

        List<TicketDTO> ticketDTOs = ticketService.getAll();
        assertEquals(ticket.getTitle(), ticketDTOs.get(0).getTitle());
    }
    
    @Test()
    public void getAllTeacherTicketsTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	User user1 = new User();
    	user1.setIduser(2);
    	user1.setName("Lucio");
    	user1.setSurname("De Cagna");
    	
    	Employee employee = new Employee();
    	employee.setIduser(2);
    	employee.setUser(user1);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		
		TicketStatus ticketStatus = new TicketStatus();
		ticketStatus.setIdticketStatus(1);
		ticketStatus.setDescription("In lavorazione");
    	
    	List<Ticket> tickets = new ArrayList<Ticket>();
    	Ticket ticket = new Ticket();
    	ticket.setIdticket(1);
    	ticket.setTeacher(teacher);
    	ticket.setEmployee(employee);
    	ticket.setClassroom(classroom1);
    	ticket.setTicketStatus(ticketStatus);
    	ticket.setTitle("Problema");
    	ticket.setDate(mockDate);
    	tickets.add(ticket);
    	
    	List<TicketMessage> ticketMessages = new ArrayList<TicketMessage>();
    	
    	TicketMessage ticketMessage = new TicketMessage();
    	ticketMessage.setUser(user);
    	ticketMessage.setIdticketMessage(1);
    	ticketMessage.setTicket(ticket);
    	ticketMessage.setDate(mockDate);
    	ticketMessage.setText("Ho un problema");
    	ticketMessages.add(ticketMessage);
    	
        when(ticketRepository.getAllTeacherTickets(1)).thenReturn(tickets);
        when(ticketRepository.getMessagesASC(1)).thenReturn(ticketMessages);

        List<TicketDTO> ticketDTOs = ticketService.getAllTeacherTickets(1);
        assertEquals(ticket.getTitle(), ticketDTOs.get(0).getTitle());
    }
    
    @Test()
    public void getAllClassroomTicketsTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	User user1 = new User();
    	user1.setIduser(2);
    	user1.setName("Lucio");
    	user1.setSurname("De Cagna");
    	
    	Employee employee = new Employee();
    	employee.setIduser(2);
    	employee.setUser(user1);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		
		TicketStatus ticketStatus = new TicketStatus();
		ticketStatus.setIdticketStatus(1);
		ticketStatus.setDescription("In lavorazione");
    	
    	List<Ticket> tickets = new ArrayList<Ticket>();
    	Ticket ticket = new Ticket();
    	ticket.setIdticket(1);
    	ticket.setTeacher(teacher);
    	ticket.setEmployee(employee);
    	ticket.setClassroom(classroom1);
    	ticket.setTicketStatus(ticketStatus);
    	ticket.setTitle("Problema");
    	ticket.setDate(mockDate);
    	tickets.add(ticket);
    	
    	List<TicketMessage> ticketMessages = new ArrayList<TicketMessage>();
    	
    	TicketMessage ticketMessage = new TicketMessage();
    	ticketMessage.setUser(user);
    	ticketMessage.setIdticketMessage(1);
    	ticketMessage.setTicket(ticket);
    	ticketMessage.setDate(mockDate);
    	ticketMessage.setText("Ho un problema");
    	ticketMessages.add(ticketMessage);
    	
        when(ticketRepository.getAllClassroomTickets(1)).thenReturn(tickets);
        when(ticketRepository.getMessagesASC(1)).thenReturn(ticketMessages);

        List<TicketDTO> ticketDTOs = ticketService.getAllClassroomTickets(1);
        assertEquals(ticket.getTitle(), ticketDTOs.get(0).getTitle());
    }
    
    @Test()
    public void getByIdTest() throws IOException, TicketNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	User user1 = new User();
    	user1.setIduser(2);
    	user1.setName("Lucio");
    	user1.setSurname("De Cagna");
    	
    	Employee employee = new Employee();
    	employee.setIduser(2);
    	employee.setUser(user1);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		
		TicketStatus ticketStatus = new TicketStatus();
		ticketStatus.setIdticketStatus(1);
		ticketStatus.setDescription("In lavorazione");
    	
    	Ticket ticket = new Ticket();
    	ticket.setIdticket(1);
    	ticket.setTeacher(teacher);
    	ticket.setEmployee(employee);
    	ticket.setClassroom(classroom1);
    	ticket.setTicketStatus(ticketStatus);
    	ticket.setTitle("Problema");
    	ticket.setDate(mockDate);
    	
    	List<TicketMessage> ticketMessages = new ArrayList<TicketMessage>();
    	
    	TicketMessage ticketMessage = new TicketMessage();
    	ticketMessage.setUser(user);
    	ticketMessage.setIdticketMessage(1);
    	ticketMessage.setTicket(ticket);
    	ticketMessage.setDate(mockDate);
    	ticketMessage.setText("Ho un problema");
    	ticketMessages.add(ticketMessage);
    	
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));
        when(ticketRepository.getMessages(1)).thenReturn(ticketMessages);

        TicketDTO ticketDTO = ticketService.getById(1);
        assertEquals(ticket.getTitle(), ticketDTO.getTitle());
    }
    
    @Test()
    public void getMessagesTest() throws IOException, TicketNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	User user1 = new User();
    	user1.setIduser(2);
    	user1.setName("Lucio");
    	user1.setSurname("De Cagna");
    	
    	Employee employee = new Employee();
    	employee.setIduser(2);
    	employee.setUser(user1);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		
		TicketStatus ticketStatus = new TicketStatus();
		ticketStatus.setIdticketStatus(1);
		ticketStatus.setDescription("In lavorazione");
    	
    	Ticket ticket = new Ticket();
    	ticket.setIdticket(1);
    	ticket.setTeacher(teacher);
    	ticket.setEmployee(employee);
    	ticket.setClassroom(classroom1);
    	ticket.setTicketStatus(ticketStatus);
    	ticket.setTitle("Problema");
    	ticket.setDate(mockDate);
    	
    	List<TicketMessage> ticketMessages = new ArrayList<TicketMessage>();
    	
    	TicketMessage ticketMessage = new TicketMessage();
    	ticketMessage.setUser(user);
    	ticketMessage.setIdticketMessage(1);
    	ticketMessage.setTicket(ticket);
    	ticketMessage.setDate(mockDate);
    	ticketMessage.setText("Ho un problema");
    	ticketMessages.add(ticketMessage);
    	
        when(ticketRepository.getMessages(1)).thenReturn(ticketMessages);

        List<TicketMessageDTO> ticketMessageDTOs = ticketService.getMessages(1);
        assertEquals(ticketMessages.get(0).getIdticketMessage(), ticketMessageDTOs.get(0).getIdticketmessage());
    }
    
    @Test()
    public void saveTest() throws IOException, TicketNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	TeacherDTO teacherDTO = new TeacherDTO();
    	teacherDTO.setIdteacher(1);
    	teacherDTO.setName("Antonio");
    	teacherDTO.setSurname("Leaci");

    	EmployeeDTO employeeDTO = new EmployeeDTO();
    	employeeDTO.setIdemployee(1);
    	employeeDTO.setName("Lucio");
    	employeeDTO.setSurname("De Cagna");
    	
    	BuildingDTO buildingDTO1 = new BuildingDTO();
		buildingDTO1.setId(1);
		buildingDTO1.setName("Stecca");
		buildingDTO1.setAddress("Via Ecotekne");
		buildingDTO1.setLat((float) 17.9);
		buildingDTO1.setLng((float) 21.3);
    	
		ClassroomDTO classroomDTO1 = new ClassroomDTO();
		classroomDTO1.setId(1);
		classroomDTO1.setName("I1");
		classroomDTO1.setSeats(180);
		classroomDTO1.setLat((float) 17.9);
		classroomDTO1.setLng((float) 21.3);
		classroomDTO1.setBuilding(buildingDTO1);
		
		TicketStatusDTO ticketStatusDTO = new TicketStatusDTO();
		ticketStatusDTO.setIdstatus(1);
		ticketStatusDTO.setDescription("In lavorazione");
    	
    	TicketDTO ticketDTO = new TicketDTO();
    	ticketDTO.setId(1);
    	ticketDTO.setTeacher(teacherDTO);
    	ticketDTO.setEmployee(employeeDTO);
    	ticketDTO.setClassroom(classroomDTO1);
    	ticketDTO.setTicketStatus(ticketStatusDTO);
    	ticketDTO.setTitle("Problema");
    	ticketDTO.setDate(mockDate);
    	
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	User user1 = new User();
    	user1.setIduser(2);
    	user1.setName("Lucio");
    	user1.setSurname("De Cagna");
    	
    	Employee employee = new Employee();
    	employee.setIduser(2);
    	employee.setUser(user1);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		
		TicketStatus ticketStatus = new TicketStatus();
		ticketStatus.setIdticketStatus(1);
		ticketStatus.setDescription("In lavorazione");
    	
    	Ticket ticket = new Ticket();
    	ticket.setIdticket(1);
    	ticket.setTeacher(teacher);
    	ticket.setEmployee(employee);
    	ticket.setClassroom(classroom1);
    	ticket.setTicketStatus(ticketStatus);
    	ticket.setTitle("Problema");
    	ticket.setDate(mockDate);
    	
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketDTO savedTicketDTO = ticketService.save(ticketDTO);
        assertEquals(ticketDTO.getTitle(), savedTicketDTO.getTitle());
    }
    
    @Test
    public void deleteTest() throws TicketNotFoundException {
    	
    	ticketService.delete(1);
    }
    
    @Test()
    public void saveMessageTest() throws IOException, TicketNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	UserDTO userDTO = new UserDTO();
    	userDTO.setIduser(1);
    	userDTO.setName("Antonio");
    	userDTO.setSurname("Leaci");
    	
    	TicketDTO ticketDTO = new TicketDTO();
    	ticketDTO.setId(1);
    	
    	TicketMessageDTO ticketMessageDTO = new TicketMessageDTO();
    	ticketMessageDTO.setUser(userDTO);
    	ticketMessageDTO.setIdticket(1);
    	ticketMessageDTO.setIdticketmessage(1);
    	ticketMessageDTO.setDate(mockDate);
    	ticketMessageDTO.setText("Ho un problema");
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Ticket ticket = new Ticket();
    	ticket.setIdticket(1);
    	
    	TicketMessage ticketMessage = new TicketMessage();
    	ticketMessage.setUser(user);
    	ticketMessage.setIdticketMessage(1);
    	ticketMessage.setDate(mockDate);
    	ticketMessage.setTicket(ticket);
    	ticketMessage.setText("Ho un problema");
    	
        when(ticketMessageRepository.save(any(TicketMessage.class))).thenReturn(ticketMessage);
        when(userRepository.isTeacher(1)).thenReturn(true);

        TicketMessageDTO savedTicketMessageDTO = ticketService.saveMessage(ticketMessageDTO);
        assertEquals(ticketMessageDTO.getText(), savedTicketMessageDTO.getText());
    }
    
    @Test()
    public void getAllTicketStatusTest() throws IOException {
    	
    	Date mockDate = new Date();
		
    	List<TicketStatus> ticketStatuses = new ArrayList<TicketStatus>();
		TicketStatus ticketStatus = new TicketStatus();
		ticketStatus.setIdticketStatus(1);
		ticketStatus.setDescription("In lavorazione");
		ticketStatuses.add(ticketStatus);

        when(statusRepository.findAll()).thenReturn(ticketStatuses);

        List<TicketStatusDTO> ticketStatusDTOs = ticketService.getAllTicketStatus();
        assertEquals(ticketStatus.getDescription(), ticketStatusDTOs.get(0).getDescription());
    }
}