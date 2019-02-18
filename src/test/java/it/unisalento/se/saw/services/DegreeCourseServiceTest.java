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
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.Term;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.domain.TypeSubject;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.exceptions.DegreeCourseNotFoundException;
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

    @Test()
    public void getAll2Test() throws IOException {
    	
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
        when(subjectRepository.getSubjectsByIdDegreeCourse(1)).thenReturn(null);

        List<DegreeCourseDTO> courseDTOs = degreeCourseService.getAll();
        assertEquals(course1.getIddegreeCourse(),(Integer) courseDTOs.get(0).getIdcourse());
    }
    
    @Test()
    public void getByIdTest() throws IOException, DegreeCourseNotFoundException {
    	
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
    	
    	DegreeCourse course1 = new DegreeCourse();
    	course1.setIddegreeCourse(1);
    	course1.setAcademicYear(year1);
    	course1.setTypeDegreeCourse(typeDegreeCourse);
    	
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
		
        when(degreeCourseRepository.findById(1)).thenReturn(Optional.of(course1));
        when(termRepository.getByAcademicYear(1)).thenReturn(terms);
        when(subjectRepository.getSubjectsByIdDegreeCourse(1)).thenReturn(subjects);

        DegreeCourseDTO courseDTO = degreeCourseService.getById(1);
        assertEquals(course1.getIddegreeCourse(),(Integer) courseDTO.getIdcourse());
    }
    
    @Test()
    public void getById2Test() throws IOException, DegreeCourseNotFoundException {
    	
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
    	
    	DegreeCourse course1 = new DegreeCourse();
    	course1.setIddegreeCourse(1);
    	course1.setAcademicYear(year1);
    	course1.setTypeDegreeCourse(typeDegreeCourse);
    	
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
		
        when(degreeCourseRepository.findById(1)).thenReturn(Optional.of(course1));
        when(termRepository.getByAcademicYear(1)).thenReturn(terms);
        when(subjectRepository.getSubjectsByIdDegreeCourse(1)).thenReturn(null);

        DegreeCourseDTO courseDTO = degreeCourseService.getById(1);
        assertEquals(course1.getIddegreeCourse(),(Integer) courseDTO.getIdcourse());
    }
    
    @Test()
    public void getCourseByTypeTest() throws IOException, DegreeCourseNotFoundException {
    	
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
		
        when(degreeCourseRepository.getCourseByType(1)).thenReturn(courses);

        List<DegreeCourseDTO> courseDTOs = degreeCourseService.getCourseByType(1);
        assertEquals(course1.getIddegreeCourse(),(Integer) courseDTOs.get(0).getIdcourse());
    }
    
    @Test()
    public void getTeacherCoursesTest() throws IOException, DegreeCourseNotFoundException {
    	
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
		
        when(degreeCourseRepository.getCourseTeacher(1)).thenReturn(courses);

        List<DegreeCourseDTO> courseDTOs = degreeCourseService.getTeacherCourses(1);
        assertEquals(course1.getIddegreeCourse(),(Integer) courseDTOs.get(0).getIdcourse());
    }
    
    @Test()
    public void saveTest() throws IOException, DegreeCourseNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
    	courseTypeDTO.setIdcourseType(1);
    	courseTypeDTO.setDescription("Triennale");
    	courseTypeDTO.setDuration(3);
    	courseTypeDTO.setCfu(180);
    	
    	TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
    	typeDegreeCourseDTO.setIdtypeDegreeCourse(1);
    	typeDegreeCourseDTO.setName("Analisi 1");
    	typeDegreeCourseDTO.setCourseType(courseTypeDTO);
    	
    	AcademicYearDTO yearDTO1 = new AcademicYearDTO();
    	yearDTO1.setIdacademicYear(1);
    	yearDTO1.setYear(2018);
    	
    	TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
    	typeSubjectDTO.setIdtypeSubject(1);
    	typeSubjectDTO.setName("Analisi 1");
    	
    	TeacherDTO teacherDTO = new TeacherDTO();
    	teacherDTO.setIdteacher(1);
    	teacherDTO.setName("Antonio");
    	teacherDTO.setSurname("Leaci");
    	
    	List<SubjectDTO> subjectDTOs = new ArrayList<SubjectDTO>();
    	SubjectDTO subjectDTO = new SubjectDTO();
    	subjectDTO.setId(1);
    	subjectDTO.setTypeSubjectDTO(typeSubjectDTO);
    	subjectDTO.setCfu(12);
    	subjectDTO.setTeacherDTO(teacherDTO);
    	
    	DegreeCourseDTO courseDTO = new DegreeCourseDTO();
    	courseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
    	courseDTO.setAcademicYear(yearDTO1);
    	courseDTO.setSubjects(subjectDTOs);
    	courseDTO.setName("Analisi 1");
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setDescription("Triennale");
    	courseType.setDuration(3);
    	courseType.setCfu(180);
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Analisi 1");
    	typeDegreeCourse.setCourseType(courseType);
    	
    	AcademicYear year1 = new AcademicYear();
    	year1.setIdacademicYear(1);
    	year1.setYear(2018);
    	
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
    	
    	DegreeCourse course = new DegreeCourse();
    	course.setIddegreeCourse(1);
    	course.setTypeDegreeCourse(typeDegreeCourse);
    	course.setAcademicYear(year1);
    	
    	
		
        when(degreeCourseRepository.save(any(DegreeCourse.class))).thenReturn(course);
        when(subjectRepository.saveAll(Matchers.anyListOf(Subject.class))).thenReturn(subjects);

        DegreeCourseDTO savedCourseDTO = degreeCourseService.save(courseDTO);
        assertEquals(courseDTO.getName(),savedCourseDTO.getName());
    }
    
    @Test
    public void deleteTest() throws ClassroomNotFoundException, DegreeCourseNotFoundException {
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setDescription("Triennale");
    	courseType.setDuration(3);
    	courseType.setCfu(180);
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Analisi 1");
    	typeDegreeCourse.setCourseType(courseType);
    	
    	AcademicYear year1 = new AcademicYear();
    	year1.setIdacademicYear(1);
    	year1.setYear(2018);
    	
    	DegreeCourse course = new DegreeCourse();
    	course.setIddegreeCourse(1);
    	course.setTypeDegreeCourse(typeDegreeCourse);
    	course.setAcademicYear(year1);
    	
    	when(degreeCourseRepository.findById(1)).thenReturn(Optional.of(course));
    	
    	degreeCourseService.delete(1);
    }
    
    @Test()
    public void getAllTypesTest() throws IOException {
    	

    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
    	
    	List<TypeDegreeCourse> typeDegreeCourses = new ArrayList<TypeDegreeCourse>();
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
    	typeDegreeCourses.add(typeDegreeCourse);
		
        when(typeDegreeCourseRepository.findAll()).thenReturn(typeDegreeCourses);

        List<TypeDegreeCourseDTO> typeDegreeCourseDTOs = degreeCourseService.getAllTypes();
        assertEquals(typeDegreeCourse.getIdtypeDegreeCourse(),(Integer) typeDegreeCourseDTOs.get(0).getIdtypeDegreeCourse());
    }

    @Test()
    public void getTypesByIdTest() throws IOException {
    	

    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
		
        when(typeDegreeCourseRepository.findById(1)).thenReturn(Optional.of(typeDegreeCourse));

        TypeDegreeCourseDTO typeDegreeCourseDTO = degreeCourseService.getTypesById(1);
        assertEquals(typeDegreeCourse.getIdtypeDegreeCourse(),(Integer) typeDegreeCourseDTO.getIdtypeDegreeCourse());
    }
    
    @Test()
    public void getAllCourseTypesTest() throws IOException {
    	
    	List<CourseType> courseTypes = new ArrayList<CourseType>();
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
    	courseTypes.add(courseType);
		
        when(courseTypeRepository.findAll()).thenReturn(courseTypes);

        List<CourseTypeDTO> courseTypeDTOs = degreeCourseService.getAllCourseTypes();
        assertEquals(courseType.getIdcourseType(),(Integer) courseTypeDTOs.get(0).getIdcourseType());
    }
    
    @Test()
    public void getCourseTypeTest() throws IOException {
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
		
        when(courseTypeRepository.findById(1)).thenReturn(Optional.of(courseType));

        CourseTypeDTO courseTypeDTO = degreeCourseService.getCourseType(1);
        assertEquals(courseType.getIdcourseType(),(Integer) courseTypeDTO.getIdcourseType());
    }
    
    @Test()
    public void saveTypeTest() throws IOException {
    	
    	CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
    	courseTypeDTO.setIdcourseType(1);
    	courseTypeDTO.setCfu(180);
    	courseTypeDTO.setDescription("Triennale");
    	
    	TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
    	typeDegreeCourseDTO.setIdtypeDegreeCourse(1);
    	typeDegreeCourseDTO.setName("Ingegneria dell'Informazione");
    	typeDegreeCourseDTO.setCourseType(courseTypeDTO);

    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
		
        when(typeDegreeCourseRepository.save(any(TypeDegreeCourse.class))).thenReturn(typeDegreeCourse);

        TypeDegreeCourseDTO savedTypeDegreeCourseDTO = degreeCourseService.saveType(typeDegreeCourseDTO);
        assertEquals((Integer) typeDegreeCourseDTO.getIdtypeDegreeCourse(),(Integer) savedTypeDegreeCourseDTO.getIdtypeDegreeCourse());
    }
    
}