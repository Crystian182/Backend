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
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.Term;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.domain.TypeSubject;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.repositories.CourseTypeRepository;
import it.unisalento.se.saw.repositories.DegreeCourseRepository;
import it.unisalento.se.saw.repositories.SubjectRepository;
import it.unisalento.se.saw.repositories.TermRepository;
import it.unisalento.se.saw.repositories.TypeDegreeCourseRepository;

import java.io.IOException;
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
public class DegreeCourseServiceTest {
    
    
    
	@Mock
	DegreeCourseRepository degreeCourseRepository;
	
	@Mock
	TypeDegreeCourseRepository typeDegreeCourseRepository;
	
	@Mock
	CourseTypeRepository courseTypeRepository;
	
	@Mock
	SubjectRepository subjectRepository;

	@Mock
	TermRepository termRepository;

    @InjectMocks
    DegreeCourseService degreeCourseService;

    @Test()
    public void getAllTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	List<Term> terms = new ArrayList<Term>();
    	Term term = new Term();
    	term.setIdterm(1);
    	term.setStart(mockDate);
    	term.setEnd(mockDate);
    	terms.add(term);
    	
    	AcademicYear year1 = new AcademicYear();
    	year1.setIdacademicYear(1);
    	year1.setYear(2018);
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
    	
    	List<DegreeCourse> courses = new ArrayList<DegreeCourse>();
    	DegreeCourse course1 = new DegreeCourse();
    	course1.setIddegreeCourse(1);
    	course1.setAcademicYear(year1);
    	course1.setTypeDegreeCourse(typeDegreeCourse);
    	courses.add(course1);
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	List<Subject> subjects = new ArrayList<Subject>();
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setTypeSubject(typeSubject);
    	subject.setCfu(12);
    	subject.setTeacher(teacher);
		
        when(degreeCourseRepository.findAll()).thenReturn(courses);
        when(termRepository.getByAcademicYear(1)).thenReturn(terms);
        when(subjectRepository.getSubjectsByIdDegreeCourse(1)).thenReturn(subjects);

        List<DegreeCourseDTO> courseDTOs = degreeCourseService.getAll();
        assertEquals(course1.getIddegreeCourse(),(Integer) courseDTOs.get(0).getIdcourse());
    }

    
}