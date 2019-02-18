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
import it.unisalento.se.saw.domain.ClassroomHasTool;
import it.unisalento.se.saw.domain.ClassroomHasToolId;
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.Day;
import it.unisalento.se.saw.domain.DegreeCourse;
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
import it.unisalento.se.saw.domain.Tool;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.domain.TypeLesson;
import it.unisalento.se.saw.domain.TypeSubject;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.ExamDTO;
import it.unisalento.se.saw.dto.ExamEnrollmentDTO;
import it.unisalento.se.saw.dto.ExamTypeDTO;
import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.ResultDTO;
import it.unisalento.se.saw.dto.StudentDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.exceptions.InstrumentNotFoundException;
import it.unisalento.se.saw.exceptions.LessonNotFoundException;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.repositories.ClassroomHasToolRepository;
import it.unisalento.se.saw.repositories.FeedbackLessonRepository;
import it.unisalento.se.saw.repositories.FeedbackRepository;
import it.unisalento.se.saw.repositories.LessonRepository;
import it.unisalento.se.saw.repositories.TeacherRepository;
import it.unisalento.se.saw.repositories.ToolRepository;
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
public class UserServiceTest {
    
	@Mock
	UserRepository userRepository;
	
	@Mock
	TeacherRepository teacherRepository;

    @InjectMocks
    UserService userService;

    @Test()
    public void getByIdTest() throws IOException, UserNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	User user = new User();
    	user.setIduser(1);
		user.setName("Cristian");
		user.setSurname("Vergallo");
		user.setDateBirth(mockDate);
		user.setCitizenship("ITA");
		user.setDomicile("Lecce");
		user.setPhone("333");
		user.setResidence("Lecce");
		user.setSex("M");
		user.setSsn("VRG");
		
        when(userRepository.getOne(1)).thenReturn(user);

        UserDTO userDTO = userService.getById(1);
        assertEquals(user.getName(), userDTO.getName());
    }
    
    @Test()
    public void getTeachersTest() throws IOException, UserNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	List<Teacher> teachers = new ArrayList<Teacher>();
    	User user = new User();
    	user.setIduser(1);
		user.setName("Cristian");
		user.setSurname("Vergallo");
		user.setDateBirth(mockDate);
		user.setCitizenship("ITA");
		user.setDomicile("Lecce");
		user.setPhone("333");
		user.setResidence("Lecce");
		user.setSex("M");
		user.setSsn("VRG");
		
		Teacher teacher = new Teacher();
		teacher.setUser(user);
		teachers.add(teacher);
		
        when(teacherRepository.findAll()).thenReturn(teachers);

        List<TeacherDTO> teacherDTOs = userService.getTeachers();
        assertEquals(user.getName(), teacherDTOs.get(0).getName());
    }
    
    @Test()
    public void getTeacherByIdTest() throws IOException, UserNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	User user = new User();
    	user.setIduser(1);
		user.setName("Cristian");
		user.setSurname("Vergallo");
		user.setDateBirth(mockDate);
		user.setCitizenship("ITA");
		user.setDomicile("Lecce");
		user.setPhone("333");
		user.setResidence("Lecce");
		user.setSex("M");
		user.setSsn("VRG");
		
		Teacher teacher = new Teacher();
		teacher.setUser(user);
		
        when(teacherRepository.getOne(1)).thenReturn(teacher);

        TeacherDTO teacherDTO = userService.getTeacherById(1);
        assertEquals(user.getName(), teacherDTO.getName());
    }

}