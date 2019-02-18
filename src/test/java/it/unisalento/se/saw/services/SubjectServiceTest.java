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
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.exceptions.LessonNotFoundException;
import it.unisalento.se.saw.exceptions.SubjectNotFoundException;
import it.unisalento.se.saw.repositories.FeedbackLessonRepository;
import it.unisalento.se.saw.repositories.FeedbackRepository;
import it.unisalento.se.saw.repositories.LessonRepository;
import it.unisalento.se.saw.repositories.SubjectRepository;
import it.unisalento.se.saw.repositories.TermRepository;
import it.unisalento.se.saw.repositories.TypeLessonRepository;
import it.unisalento.se.saw.repositories.TypeSubjectRepository;

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
public class SubjectServiceTest {
    
	@Mock
	SubjectRepository subjectRepository;
	
	@Mock
	TermRepository termRepository;
	
	@Mock
	TypeSubjectRepository typeSubjectRepository;

    @InjectMocks
    SubjectService subjectService;

    @Test()
    public void getAllTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	typeSubject.setDescription("Bella");
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	AcademicYear year1 = new AcademicYear();
    	year1.setIdacademicYear(1);
    	year1.setYear(2018);
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
    	courseType.setDuration(3);
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
    	
    	DegreeCourse course1 = new DegreeCourse();
    	course1.setIddegreeCourse(1);
    	course1.setAcademicYear(year1);
    	course1.setTypeDegreeCourse(typeDegreeCourse);
    	
    	List<Subject> subjects = new ArrayList<Subject>();
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setTypeSubject(typeSubject);
    	subject.setCfu(12);
    	subject.setDegreeCourse(course1);
    	subject.setTeacher(teacher);
    	subjects.add(subject);
    	
        when(subjectRepository.findAll()).thenReturn(subjects);

        List<SubjectDTO> subjectDTOs = subjectService.getAll();
        assertEquals(subject.getIdsubject(),(Integer) subjectDTOs.get(0).getId());
    }

    @Test()
    public void getByIdCourseTest() throws IOException, SubjectNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	typeSubject.setDescription("Bella");
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	AcademicYear year1 = new AcademicYear();
    	year1.setIdacademicYear(1);
    	year1.setYear(2018);
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
    	courseType.setDuration(3);
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
    	
    	DegreeCourse course1 = new DegreeCourse();
    	course1.setIddegreeCourse(1);
    	course1.setAcademicYear(year1);
    	course1.setTypeDegreeCourse(typeDegreeCourse);
    	
    	List<Subject> subjects = new ArrayList<Subject>();
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setTypeSubject(typeSubject);
    	subject.setCfu(12);
    	subject.setDegreeCourse(course1);
    	subject.setTeacher(teacher);
    	subjects.add(subject);
    	
    	List<Term> terms = new ArrayList<Term>();
    	Term term = new Term();
    	term.setIdterm(1);
    	term.setStart(mockDate);
    	term.setEnd(mockDate);
    	terms.add(term);
    	
        when(subjectRepository.getByIdCourse(1)).thenReturn(subjects);
        when(termRepository.getByAcademicYear(1)).thenReturn(terms);

        List<SubjectDTO> subjectDTOs = subjectService.getByIdCourse(1);
        assertEquals(subject.getIdsubject(),(Integer) subjectDTOs.get(0).getId());
    }
    
    @Test()
    public void getByIdTeacherTest() throws IOException, SubjectNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	typeSubject.setDescription("Bella");
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	AcademicYear year1 = new AcademicYear();
    	year1.setIdacademicYear(1);
    	year1.setYear(2018);
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
    	courseType.setDuration(3);
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
    	
    	DegreeCourse course1 = new DegreeCourse();
    	course1.setIddegreeCourse(1);
    	course1.setAcademicYear(year1);
    	course1.setTypeDegreeCourse(typeDegreeCourse);
    	
    	List<Subject> subjects = new ArrayList<Subject>();
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setTypeSubject(typeSubject);
    	subject.setCfu(12);
    	subject.setDegreeCourse(course1);
    	subject.setTeacher(teacher);
    	subjects.add(subject);
    	
    	List<Term> terms = new ArrayList<Term>();
    	Term term = new Term();
    	term.setIdterm(1);
    	term.setStart(mockDate);
    	term.setEnd(mockDate);
    	terms.add(term);
    	
        when(subjectRepository.getByIdTeacher(1)).thenReturn(subjects);
        when(termRepository.getByAcademicYear(1)).thenReturn(terms);

        List<SubjectDTO> subjectDTOs = subjectService.getByIdTeacher(1);
        assertEquals(subject.getIdsubject(),(Integer) subjectDTOs.get(0).getId());
    }
    
    @Test()
    public void getByIdStudentTest() throws IOException, SubjectNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	typeSubject.setDescription("Bella");
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	AcademicYear year1 = new AcademicYear();
    	year1.setIdacademicYear(1);
    	year1.setYear(2018);
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
    	courseType.setDuration(3);
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
    	
    	DegreeCourse course1 = new DegreeCourse();
    	course1.setIddegreeCourse(1);
    	course1.setAcademicYear(year1);
    	course1.setTypeDegreeCourse(typeDegreeCourse);
    	
    	List<Subject> subjects = new ArrayList<Subject>();
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setTypeSubject(typeSubject);
    	subject.setCfu(12);
    	subject.setDegreeCourse(course1);
    	subject.setTeacher(teacher);
    	subjects.add(subject);
    	
    	List<Term> terms = new ArrayList<Term>();
    	Term term = new Term();
    	term.setIdterm(1);
    	term.setStart(mockDate);
    	term.setEnd(mockDate);
    	terms.add(term);
    	
        when(subjectRepository.getCoursesByIdStudent(1)).thenReturn(subjects);
        when(termRepository.getByAcademicYear(1)).thenReturn(terms);

        List<SubjectDTO> subjectDTOs = subjectService.getByIdStudent(1);
        assertEquals(subject.getIdsubject(),(Integer) subjectDTOs.get(0).getId());
    }
    
    @Test()
    public void getByIdTest() throws IOException, SubjectNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	typeSubject.setDescription("Bella");
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	AcademicYear year1 = new AcademicYear();
    	year1.setIdacademicYear(1);
    	year1.setYear(2018);
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
    	courseType.setDuration(3);
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
    	
    	DegreeCourse course1 = new DegreeCourse();
    	course1.setIddegreeCourse(1);
    	course1.setAcademicYear(year1);
    	course1.setTypeDegreeCourse(typeDegreeCourse);
    	
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setTypeSubject(typeSubject);
    	subject.setCfu(12);
    	subject.setDegreeCourse(course1);
    	subject.setTeacher(teacher);
    	
        when(subjectRepository.findById(1)).thenReturn(Optional.of(subject));

        SubjectDTO subjectDTO = subjectService.getById(1);
        assertEquals(subject.getIdsubject(),(Integer) subjectDTO.getId());
    }
    
    @Test()
    public void getAllSubjectTypesTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	List<TypeSubject> typeSubjects = new ArrayList<TypeSubject>();
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	typeSubject.setDescription("Bella");
    	typeSubjects.add(typeSubject);
    	
        when(typeSubjectRepository.findAll()).thenReturn(typeSubjects);

        List<TypeSubjectDTO> typeSubjectDTOs = subjectService.getAllSubjectTypes();
        assertEquals(typeSubject.getIdtypeSubject(),(Integer) typeSubjectDTOs.get(0).getIdtypeSubject());
    }
    
    @Test()
    public void saveTest() throws IOException, SubjectNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
    	typeSubjectDTO.setIdtypeSubject(1);
    	typeSubjectDTO.setName("Analisi 1");
    	typeSubjectDTO.setDescription("Bella");
    	
    	TeacherDTO teacherDTO = new TeacherDTO();
    	teacherDTO.setIdteacher(1);
    	teacherDTO.setName("Antonio");
    	teacherDTO.setSurname("Leaci");
    	
    	TermDTO termDTO = new TermDTO();
    	termDTO.setIdterm(1);
    	
    	SubjectDTO subjectDTO = new SubjectDTO();
    	subjectDTO.setId(1);;
    	subjectDTO.setTypeSubjectDTO(typeSubjectDTO);
    	subjectDTO.setCfu(12);
    	subjectDTO.setTeacherDTO(teacherDTO);
    	subjectDTO.setTerm(termDTO);
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	typeSubject.setDescription("Bella");
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	AcademicYear year1 = new AcademicYear();
    	year1.setIdacademicYear(1);
    	year1.setYear(2018);
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
    	courseType.setDuration(3);
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
    	
    	DegreeCourse course1 = new DegreeCourse();
    	course1.setIddegreeCourse(1);
    	course1.setAcademicYear(year1);
    	course1.setTypeDegreeCourse(typeDegreeCourse);
    	
    	Term term = new Term();
    	term.setIdterm(1);
    	
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setTypeSubject(typeSubject);
    	subject.setCfu(12);
    	subject.setDegreeCourse(course1);
    	subject.setTeacher(teacher);
    	subject.setTerm(term);
    	
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        SubjectDTO savedSubjectDTO = subjectService.save(subjectDTO);
        assertEquals((Integer) subjectDTO.getCfu(),(Integer) savedSubjectDTO.getCfu());
    }
    
    @Test()
    public void saveAllTest() throws IOException, SubjectNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
    	typeSubjectDTO.setIdtypeSubject(1);
    	typeSubjectDTO.setName("Analisi 1");
    	typeSubjectDTO.setDescription("Bella");
    	
    	TeacherDTO teacherDTO = new TeacherDTO();
    	teacherDTO.setIdteacher(1);
    	teacherDTO.setName("Antonio");
    	teacherDTO.setSurname("Leaci");
    	
    	TermDTO termDTO = new TermDTO();
    	termDTO.setIdterm(1);
    	
    	AcademicYearDTO yearDTO1 = new AcademicYearDTO();
    	yearDTO1.setIdacademicYear(1);
    	yearDTO1.setYear(2018);
    	
    	CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
    	courseTypeDTO.setIdcourseType(1);
    	courseTypeDTO.setCfu(180);
    	courseTypeDTO.setDescription("Triennale");
    	courseTypeDTO.setDuration(3);
    	
    	TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
    	typeDegreeCourseDTO.setIdtypeDegreeCourse(1);
    	typeDegreeCourseDTO.setName("Ingegneria dell'Informazione");
    	typeDegreeCourseDTO.setCourseType(courseTypeDTO);
    	
    	DegreeCourseDTO courseDTO1 = new DegreeCourseDTO();
    	courseDTO1.setIdcourse(1);
    	courseDTO1.setAcademicYear(yearDTO1);
    	courseDTO1.setTypeDegreeCourse(typeDegreeCourseDTO);
    	
    	List<SubjectDTO> subjectDTOs = new ArrayList<SubjectDTO>();
    	SubjectDTO subjectDTO = new SubjectDTO();
    	subjectDTO.setId(1);;
    	subjectDTO.setTypeSubjectDTO(typeSubjectDTO);
    	subjectDTO.setCfu(12);
    	subjectDTO.setTeacherDTO(teacherDTO);
    	subjectDTO.setTerm(termDTO);
    	subjectDTO.setDegreecourseDTO(courseDTO1);
    	subjectDTOs.add(subjectDTO);
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	typeSubject.setDescription("Bella");
    	
    	User user = new User();
    	user.setIduser(1);
    	user.setName("Antonio");
    	user.setSurname("Leaci");
    	
    	Teacher teacher = new Teacher();
    	teacher.setIduser(1);
    	teacher.setUser(user);
    	
    	AcademicYear year1 = new AcademicYear();
    	year1.setIdacademicYear(1);
    	year1.setYear(2018);
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Triennale");
    	courseType.setDuration(3);
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
    	
    	DegreeCourse course1 = new DegreeCourse();
    	course1.setIddegreeCourse(1);
    	course1.setAcademicYear(year1);
    	course1.setTypeDegreeCourse(typeDegreeCourse);
    	
    	Term term = new Term();
    	term.setIdterm(1);
    	
    	List<Subject> subjects = new ArrayList<Subject>();
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setTypeSubject(typeSubject);
    	subject.setCfu(12);
    	subject.setDegreeCourse(course1);
    	subject.setTeacher(teacher);
    	subject.setTerm(term);
    	subjects.add(subject);
    	
        when(subjectRepository.saveAll(Matchers.anyListOf(Subject.class))).thenReturn(subjects);

        List<SubjectDTO> savedSubjectDTOs = subjectService.saveAll(subjectDTOs);
        assertEquals((Integer) subjectDTO.getCfu(),(Integer) savedSubjectDTOs.get(0).getCfu());
    }
    
    @Test
    public void deleteTest() throws SubjectNotFoundException {
    	
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setCfu(12);
    	
    	when(subjectRepository.findById(1)).thenReturn(Optional.of(subject));
    	
    	subjectService.delete(1);
    }
    
    @Test()
    public void saveTypeAllTest() throws IOException, SubjectNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
    	typeSubjectDTO.setIdtypeSubject(1);
    	typeSubjectDTO.setName("Analisi 1");
    	typeSubjectDTO.setDescription("Bella");
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setIdtypeSubject(1);
    	typeSubject.setName("Analisi 1");
    	typeSubject.setDescription("Bella");
    	
        when(typeSubjectRepository.save(any(TypeSubject.class))).thenReturn(typeSubject);

        TypeSubjectDTO savedTypeSubjectDTO = subjectService.saveType(typeSubjectDTO);
        assertEquals((Integer) typeSubjectDTO.getIdtypeSubject(),(Integer) savedTypeSubjectDTO.getIdtypeSubject());
    }
    
}