package it.unisalento.se.saw.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import it.unisalento.se.saw.domain.AcademicYear;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.Term;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.domain.TypeSubject;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.PreviewChatDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.repositories.AcademicYearRepository;
import it.unisalento.se.saw.repositories.DegreeCourseRepository;
import it.unisalento.se.saw.repositories.SubjectRepository;
import it.unisalento.se.saw.repositories.TermRepository;
import it.unisalento.se.saw.repositories.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ChatServiceTest {

	@Mock
	DegreeCourseRepository courseRepository;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	SubjectRepository subjectRepository;

    @InjectMocks
    ChatService chatService;

    @Test
    public void getContactsTest() throws IOException, NullPointerException {
    	
    	Date mockDate = new Date();
    	
    	List<Subject> subjects = new ArrayList<Subject>();
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	
    	AcademicYear year1 = new AcademicYear();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		
		Term term1 = new Term();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		term1.setAcademicYear(year1);
		
		TypeDegreeCourse typeCourse = new TypeDegreeCourse();
		typeCourse.setIdtypeDegreeCourse(1);
		typeCourse.setName("Analisi 1");
		
		DegreeCourse course = new DegreeCourse();
		course.setIddegreeCourse(1);
		course.setTypeDegreeCourse(typeCourse);
		
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setCfu(12);
    	subject.setTypeSubject(typeSubject);
    	subject.setTerm(term1);
    	subjects.add(subject);
    	
    	List<Teacher> teachers = new ArrayList<Teacher>();
    	
    	User user = new User();
    	user.setIduser(2);
    	user.setName("Giovanni");
    	user.setSurname("Mancarella");
    	
    	Teacher teacher = new Teacher();
    	teacher.setUser(user);
    	teachers.add(teacher);
    	
    	List<Student> students = new ArrayList<Student>();
    	
    	User user1 = new User();
    	user1.setIduser(3);
    	user1.setName("Matteo");
    	user1.setSurname("Fasano");
    	
    	Student student = new Student();
    	student.setUser(user1);
    	students.add(student);

        when(userRepository.isTeacher(1)).thenReturn(true);
        when(subjectRepository.searchByIdTeacher(1, "Antonio")).thenReturn(subjects);
        when(userRepository.searchTeachers(1, "gio")).thenReturn(teachers);
        when(userRepository.searchStudents(1, "gio")).thenReturn(students);

        List<PreviewChatDTO> chatDTOs = chatService.getContacts(1, "gio");
        assertEquals((Integer) 3, (Integer) chatDTOs.get(1).getToUser().getIduser());
    }
    
    @Test
    public void getContacts2Test() throws IOException, NullPointerException {
    	
    	Date mockDate = new Date();
    	
    	List<Subject> subjects = new ArrayList<Subject>();
    	
    	List<Teacher> teachers = new ArrayList<Teacher>();
    	
    	User user = new User();
    	user.setIduser(2);
    	user.setName("Giovanni");
    	user.setSurname("Mancarella");
    	
    	Teacher teacher = new Teacher();
    	teacher.setUser(user);
    	teachers.add(teacher);
    	
    	List<Student> students = new ArrayList<Student>();
    	
    	User user1 = new User();
    	user1.setIduser(3);
    	user1.setName("Matteo");
    	user1.setSurname("Fasano");
    	
    	Student student = new Student();
    	student.setUser(user1);
    	students.add(student);

        when(userRepository.isTeacher(1)).thenReturn(true);
        when(subjectRepository.searchByIdTeacher(1, "Antonio")).thenReturn(subjects);
        when(userRepository.searchTeachers(1, "gio")).thenReturn(teachers);
        when(userRepository.searchStudents(1, "gio")).thenReturn(students);

        List<PreviewChatDTO> chatDTOs = chatService.getContacts(1, "gio");
        assertEquals((Integer) 3, (Integer) chatDTOs.get(1).getToUser().getIduser());
    }

    @Test
    public void getContacts3Test() throws IOException, NullPointerException {
    	
    	Date mockDate = new Date();
    	
    	List<Subject> subjects = new ArrayList<Subject>();
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	
    	AcademicYear year1 = new AcademicYear();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		
		Term term1 = new Term();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		term1.setAcademicYear(year1);
		
		TypeDegreeCourse typeCourse = new TypeDegreeCourse();
		typeCourse.setIdtypeDegreeCourse(1);
		typeCourse.setName("Analisi 1");
		
		DegreeCourse course = new DegreeCourse();
		course.setIddegreeCourse(1);
		course.setTypeDegreeCourse(typeCourse);
		
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setCfu(12);
    	subject.setTypeSubject(typeSubject);
    	subject.setTerm(term1);
    	subjects.add(subject);
    	
    	List<Teacher> teachers = new ArrayList<Teacher>();
    	
    	User user = new User();
    	user.setIduser(2);
    	user.setName("Giovanni");
    	user.setSurname("Mancarella");
    	
    	Teacher teacher = new Teacher();
    	teacher.setUser(user);
    	teachers.add(teacher);
    	
    	List<Student> students = new ArrayList<Student>();
    	
    	User user1 = new User();
    	user1.setIduser(3);
    	user1.setName("Matteo");
    	user1.setSurname("Fasano");
    	
    	Student student = new Student();
    	student.setUser(user1);
    	students.add(student);

        when(userRepository.isTeacher(1)).thenReturn(true);
        when(subjectRepository.searchByIdStudent(1, "Antonio")).thenReturn(subjects);
        when(userRepository.searchTeachers(1, "gio")).thenReturn(teachers);
        when(userRepository.searchStudents(1, "gio")).thenReturn(students);

        List<PreviewChatDTO> chatDTOs = chatService.getContacts(1, "gio");
        assertEquals((Integer) 3, (Integer) chatDTOs.get(1).getToUser().getIduser());
    }
   
    @Test
    public void getSubscribedCoursesTest() throws IOException, NullPointerException {
    	
    	Date mockDate = new Date();
    	
    	List<Subject> subjects = new ArrayList<Subject>();
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	
    	AcademicYear year1 = new AcademicYear();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		
		Term term1 = new Term();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		term1.setAcademicYear(year1);
		
		TypeDegreeCourse typeCourse = new TypeDegreeCourse();
		typeCourse.setIdtypeDegreeCourse(1);
		typeCourse.setName("Analisi 1");
		
		DegreeCourse course = new DegreeCourse();
		course.setIddegreeCourse(1);
		course.setTypeDegreeCourse(typeCourse);
		course.setAcademicYear(year1);
		
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setCfu(12);
    	subject.setTypeSubject(typeSubject);
    	subject.setTerm(term1);
    	subject.setDegreeCourse(course);
    	subjects.add(subject);
    	
        when(userRepository.isTeacher(1)).thenReturn(true);
        when(subjectRepository.getCoursesByIdTeacher(1)).thenReturn(subjects);

        List<PreviewChatDTO> chatDTOs = chatService.getSubscribedCourses(1);
        assertEquals((Integer) 1, (Integer) chatDTOs.get(0).getSubject().getId());
    }
    
    @Test
    public void getSubscribedCourses2Test() throws IOException, NullPointerException {
    	
    	Date mockDate = new Date();
    	
    	List<Subject> subjects = new ArrayList<Subject>();
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	
    	AcademicYear year1 = new AcademicYear();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		
		Term term1 = new Term();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		term1.setAcademicYear(year1);
		
		TypeDegreeCourse typeCourse = new TypeDegreeCourse();
		typeCourse.setIdtypeDegreeCourse(1);
		typeCourse.setName("Analisi 1");
		
		DegreeCourse course = new DegreeCourse();
		course.setIddegreeCourse(1);
		course.setTypeDegreeCourse(typeCourse);
		course.setAcademicYear(year1);
		
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setCfu(12);
    	subject.setTypeSubject(typeSubject);
    	subject.setTerm(term1);
    	subject.setDegreeCourse(course);
    	subjects.add(subject);
    	
        when(userRepository.isTeacher(1)).thenReturn(false);
        when(subjectRepository.getCoursesByIdStudent(1)).thenReturn(subjects);

        List<PreviewChatDTO> chatDTOs = chatService.getSubscribedCourses(1);
        assertEquals((Integer) 1, (Integer) chatDTOs.get(0).getSubject().getId());
    }
    
}