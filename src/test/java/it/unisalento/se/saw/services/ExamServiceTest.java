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
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Exam;
import it.unisalento.se.saw.domain.ExamStatus;
import it.unisalento.se.saw.domain.ExamType;
import it.unisalento.se.saw.domain.Result;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.domain.StudentHasExam;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.domain.TypeSubject;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.ExamDTO;
import it.unisalento.se.saw.dto.ExamEnrollmentDTO;
import it.unisalento.se.saw.dto.ExamTypeDTO;
import it.unisalento.se.saw.dto.ResultDTO;
import it.unisalento.se.saw.dto.StudentDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.exceptions.ExamNotFoundException;
import it.unisalento.se.saw.repositories.ExamRepository;
import it.unisalento.se.saw.repositories.ExamTypeRepository;
import it.unisalento.se.saw.repositories.StudentHasExamRepository;
import it.unisalento.se.saw.repositories.SubjectRepository;

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
public class ExamServiceTest {
    
	@Mock
	ExamRepository examRepository;
	
	@Mock
	ExamTypeRepository examTypeRepository;
	
	@Mock
	StudentHasExamRepository studentHasExamRepository;
	
	@Mock
	SubjectRepository subjectRepository;

    @InjectMocks
    ExamService examService;

    @Test()
    public void getAllByCourseAndTermTest() throws IOException, ExamNotFoundException {
    	
    	Date mockDate = new Date();
    	
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
    	
    	ExamType examType = new ExamType();
    	examType.setIdexamType(1);
    	examType.setDescription("Prova orale");
    	
    	List<Exam> exams = new ArrayList<Exam>();
    	
    	Exam exam = new Exam();
    	exam.setIdexam(1);
    	exam.setClassroom(classroom1);
    	exam.setSubject(subject);
    	exam.setDate(mockDate);
    	exam.setExamType(examType);
    	exams.add(exam);
    	
        when(examRepository.findAllByCourseAndTerm(1, 1)).thenReturn(exams);

        List<ExamDTO> examDTOs = examService.getAllByCourseAndTerm(1,1);
        assertEquals(exam.getIdexam(),(Integer) examDTOs.get(0).getIdexam());
    }

    @Test()
    public void getAllTypesTest() throws IOException, ExamNotFoundException {
    	
    	List<ExamType> examTypes = new ArrayList<ExamType>();
    	ExamType examType = new ExamType();
    	examType.setIdexamType(1);
    	examType.setDescription("Prova orale");
    	examTypes.add(examType);
    	
        when(examTypeRepository.findAll()).thenReturn(examTypes);

        List<ExamTypeDTO> examTypeDTOs = examService.getAllTypes();
        assertEquals(examType.getIdexamType(),(Integer) examTypeDTOs.get(0).getIdexamType());
    }
    
    @Test()
    public void getAllAvailableByTeacherTest() throws IOException, ExamNotFoundException {
    	
    	Date mockDate = new Date();
    	
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
    	
    	ExamType examType = new ExamType();
    	examType.setIdexamType(1);
    	examType.setDescription("Prova orale");
    	
    	ExamStatus status = new ExamStatus();
    	status.setIdexamStatus(1);
    	status.setDescription("Chiuso");
    	
    	List<Exam> exams = new ArrayList<Exam>();
    	
    	Exam exam = new Exam();
    	exam.setIdexam(1);
    	exam.setClassroom(classroom1);
    	exam.setSubject(subject);
    	exam.setDate(mockDate);
    	exam.setExamType(examType);
    	exam.setExamStatus(status);
    	exams.add(exam);
    	
    	List<StudentHasExam> enrollments = new ArrayList<StudentHasExam>();
    	
    	User user1 = new User();
    	user1.setIduser(2);
    	user1.setName("Cristian");
    	user1.setSurname("Vergallo");
    	user1.setDateBirth(mockDate);
    	user1.setEmail("cristian.vergallo@email.it");
    	
    	Student student = new Student();
    	student.setIduser(1);
    	student.setUser(user1);
    	
    	Result result = new Result();
    	result.setIdresult(1);
    	result.setDescription("Superato");
    	
    	StudentHasExam enrollment = new StudentHasExam();
    	enrollment.setStudent(student);
    	enrollment.setDate(mockDate);
    	enrollment.setGrade(18);
    	enrollment.setResult(result);
    	enrollments.add(enrollment);
    	
        when(examRepository.getAllAvailableByTeacher(1)).thenReturn(exams);
        when(studentHasExamRepository.getExamStudents(1)).thenReturn(enrollments);
        
        List<ExamDTO> examDTOs = examService.getAllAvailableByTeacher(1);
        assertEquals(exam.getIdexam(),(Integer) examDTOs.get(0).getIdexam());
    }
    
    @Test()
    public void getAllAvailableByStudentTest() throws IOException, ExamNotFoundException {
    	
    	Date mockDate = new Date();
    	
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
    	
    	ExamType examType = new ExamType();
    	examType.setIdexamType(1);
    	examType.setDescription("Prova orale");
    	
    	ExamStatus status = new ExamStatus();
    	status.setIdexamStatus(1);
    	status.setDescription("Chiuso");
    	
    	List<Exam> exams = new ArrayList<Exam>();
    	
    	Exam exam = new Exam();
    	exam.setIdexam(1);
    	exam.setClassroom(classroom1);
    	exam.setSubject(subject);
    	exam.setDate(mockDate);
    	exam.setExamType(examType);
    	exam.setExamStatus(status);
    	exams.add(exam);
    	
        when(examRepository.getAllAvailableByStudent(1)).thenReturn(exams);
        
        List<ExamDTO> examDTOs = examService.getAllAvailableByStudent(1);
        assertEquals(exam.getIdexam(),(Integer) examDTOs.get(0).getIdexam());
    }
    
    @Test()
    public void getRecordBookTest() throws IOException, ExamNotFoundException {
    	
    	Date mockDate = new Date();
    	
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
    	subjects.add(subject);
    	
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
    	
    	ExamType examType = new ExamType();
    	examType.setIdexamType(1);
    	examType.setDescription("Prova orale");
    	
    	ExamStatus status = new ExamStatus();
    	status.setIdexamStatus(1);
    	status.setDescription("Chiuso");
    	
    	List<Exam> exams = new ArrayList<Exam>();
    	
    	Exam exam = new Exam();
    	exam.setIdexam(1);
    	exam.setClassroom(classroom1);
    	exam.setDate(mockDate);
    	exam.setExamType(examType);
    	exam.setExamStatus(status);
    	exam.setSubject(subject);
    	exams.add(exam);
    	
    	User user1 = new User();
    	user1.setIduser(2);
    	user1.setName("Cristian");
    	user1.setSurname("Vergallo");
    	user1.setDateBirth(mockDate);
    	user1.setEmail("cristian.vergallo@email.it");
    	
    	Student student = new Student();
    	student.setIduser(1);
    	student.setUser(user1);
    	
    	Result result = new Result();
    	result.setIdresult(1);
    	result.setDescription("Superato");
    	
    	StudentHasExam enrollment = new StudentHasExam();
    	enrollment.setStudent(student);
    	enrollment.setDate(mockDate);
    	enrollment.setGrade(18);
    	enrollment.setResult(result);
    	enrollment.setExam(exam);
    	
    	when(subjectRepository.getCoursesByIdStudent(1)).thenReturn(subjects);
    	when(studentHasExamRepository.getPassedExam(1, 1)).thenReturn(enrollment);
        
        List<ExamEnrollmentDTO> examEnrollmentDTOs = examService.getRecordBook(1);
        assertEquals(exam.getIdexam(),(Integer) examEnrollmentDTOs.get(0).getExam().getIdexam());
    }
    
    @Test()
    public void getRecordBook2Test() throws IOException, ExamNotFoundException {
    	
    	Date mockDate = new Date();
    	
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
    	subjects.add(subject);
    	
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
    	
    	ExamType examType = new ExamType();
    	examType.setIdexamType(1);
    	examType.setDescription("Prova orale");
    	
    	ExamStatus status = new ExamStatus();
    	status.setIdexamStatus(1);
    	status.setDescription("Chiuso");
    	
    	List<Exam> exams = new ArrayList<Exam>();
    	
    	Exam exam = new Exam();
    	exam.setIdexam(1);
    	exam.setClassroom(classroom1);
    	exam.setDate(mockDate);
    	exam.setExamType(examType);
    	exam.setExamStatus(status);
    	exam.setSubject(subject);
    	exams.add(exam);
    	
    	User user1 = new User();
    	user1.setIduser(2);
    	user1.setName("Cristian");
    	user1.setSurname("Vergallo");
    	user1.setDateBirth(mockDate);
    	user1.setEmail("cristian.vergallo@email.it");
    	
    	Student student = new Student();
    	student.setIduser(1);
    	student.setUser(user1);
    	
    	Result result = new Result();
    	result.setIdresult(1);
    	result.setDescription("Superato");
    	
    	StudentHasExam enrollment = new StudentHasExam();
    	enrollment.setStudent(student);
    	enrollment.setDate(mockDate);
    	enrollment.setGrade(18);
    	enrollment.setResult(result);
    	enrollment.setExam(exam);
    	
    	when(subjectRepository.getCoursesByIdStudent(1)).thenReturn(subjects);
    	when(studentHasExamRepository.getPassedExam(1, 1)).thenReturn(null);
        
        List<ExamEnrollmentDTO> examEnrollmentDTOs = examService.getRecordBook(1);
        assertEquals("Non conseguito", examEnrollmentDTOs.get(0).getExam().getStatus().getDescription());
    }
    
    @Test()
    public void getAllEnrollmentsTest() throws IOException, ExamNotFoundException {
    	
    	Date mockDate = new Date();
    	
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
    	subjects.add(subject);
    	
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
    	
    	ExamType examType = new ExamType();
    	examType.setIdexamType(1);
    	examType.setDescription("Prova orale");
    	
    	ExamStatus status = new ExamStatus();
    	status.setIdexamStatus(1);
    	status.setDescription("Chiuso");
    	
    	List<Exam> exams = new ArrayList<Exam>();
    	
    	Exam exam = new Exam();
    	exam.setIdexam(1);
    	exam.setClassroom(classroom1);
    	exam.setDate(mockDate);
    	exam.setExamType(examType);
    	exam.setExamStatus(status);
    	exam.setSubject(subject);
    	exams.add(exam);
    	
    	User user1 = new User();
    	user1.setIduser(2);
    	user1.setName("Cristian");
    	user1.setSurname("Vergallo");
    	user1.setDateBirth(mockDate);
    	user1.setEmail("cristian.vergallo@email.it");
    	
    	Student student = new Student();
    	student.setIduser(1);
    	student.setUser(user1);
    	
    	Result result = new Result();
    	result.setIdresult(1);
    	result.setDescription("Superato");
    	
    	List<StudentHasExam> enrollments = new ArrayList<StudentHasExam>();
    	StudentHasExam enrollment = new StudentHasExam();
    	enrollment.setStudent(student);
    	enrollment.setDate(mockDate);
    	enrollment.setGrade(18);
    	enrollment.setResult(result);
    	enrollment.setExam(exam);
    	enrollments.add(enrollment);
    	
    	when(studentHasExamRepository.getAllEnrollment(1)).thenReturn(enrollments);
        
        List<ExamEnrollmentDTO> examEnrollmentDTOs = examService.getAllEnrollments(1);
        assertEquals(exam.getIdexam(),(Integer) examEnrollmentDTOs.get(0).getExam().getIdexam());
    }
    
    @Test()
    public void saveTest() throws IOException, ExamNotFoundException {
    	
    	Date mockDate = new Date();

		ClassroomDTO classroom1 = new ClassroomDTO();
		classroom1.setId(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
    	
    	SubjectDTO subject = new SubjectDTO();
    	subject.setId(1);
    	
    	ExamTypeDTO examType = new ExamTypeDTO();
    	examType.setIdexamType(1);
    	examType.setDescription("Prova orale");
    	
    	List<ExamDTO> exams = new ArrayList<ExamDTO>();
    	
    	ExamDTO exam = new ExamDTO();
    	exam.setIdexam(1);
    	exam.setClassroom(classroom1);
    	exam.setSubject(subject);
    	exam.setDate(mockDate);
    	exam.setExamtype(examType);
    	exams.add(exam);

        examService.save(exams);
    }
    
    @Test()
    public void insertGradeTest() throws IOException, ExamNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	ExamTypeDTO examType = new ExamTypeDTO();
    	examType.setIdexamType(1);
    	examType.setDescription("Prova orale");
    	
    	StudentDTO student = new StudentDTO();
    	student.setIdstudent(1);
    	
    	ResultDTO result = new ResultDTO();
    	result.setIdresult(1);
    	result.setDescription("Superato");
    	
    	List<ExamEnrollmentDTO> enrollments = new ArrayList<ExamEnrollmentDTO>();
    	
    	ExamEnrollmentDTO enrollment = new ExamEnrollmentDTO();
    	enrollment.setStudent(student);
    	enrollment.setDate(mockDate);
    	enrollment.setGrade(18);
    	enrollment.setResult(result);
    	enrollments.add(enrollment);

        examService.insertGrade(enrollments, 1);
    }
    
}