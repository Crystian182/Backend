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
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.exceptions.LessonNotFoundException;
import it.unisalento.se.saw.repositories.FeedbackLessonRepository;
import it.unisalento.se.saw.repositories.FeedbackRepository;
import it.unisalento.se.saw.repositories.LessonRepository;
import it.unisalento.se.saw.repositories.TypeLessonRepository;

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
public class LessonServiceTest {
    
	@Mock
	LessonRepository lessonRepository;
	
	@Mock
	TypeLessonRepository typeLessonRepository;
	
	@Mock
	FeedbackLessonRepository feedbackLessonRepository;
	
	@Mock
	FeedbackRepository feedbackRepository;

    @InjectMocks
    LessonService lessonService;

    @Test()
    public void getAllTest() throws IOException {
    	
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
    	
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setSubject(subject);
    	
    	List<Lesson> lessons = new ArrayList<Lesson>();
    	
    	Lesson lesson = new Lesson();
    	lesson.setClassroom(classroom1);
    	lesson.setTypeLesson(typeLesson);
    	lesson.setIdlesson(1);
    	lesson.setStart(mockDate);
    	lesson.setEnd(mockDate);
    	lessons.add(lesson);

    	
        when(lessonRepository.findAll()).thenReturn(lessons);

        List<LessonDTO> lessonDTOs = lessonService.getAll();
        assertEquals(lesson.getIdlesson(),(Integer) lessonDTOs.get(0).getIdlesson());
    }

    @Test()
    public void getAllTeacherLessonsTest() throws IOException {
    	
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
    	
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setSubject(subject);
    	
    	List<Lesson> lessons = new ArrayList<Lesson>();
    	
    	Lesson lesson = new Lesson();
    	lesson.setClassroom(classroom1);
    	lesson.setTypeLesson(typeLesson);
    	lesson.setIdlesson(1);
    	lesson.setStart(mockDate);
    	lesson.setEnd(mockDate);
    	lessons.add(lesson);

    	
        when(lessonRepository.getAllTeacherLessons(1)).thenReturn(lessons);

        List<LessonDTO> lessonDTOs = lessonService.getAllTeacherLessons(1);
        assertEquals(lesson.getIdlesson(),(Integer) lessonDTOs.get(0).getIdlesson());
    }
    
    @Test()
    public void searchLessonsTest() throws IOException, ParseException {
    	
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
    	
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setSubject(subject);
    	
    	List<Lesson> lessons = new ArrayList<Lesson>();
    	
    	Lesson lesson = new Lesson();
    	lesson.setClassroom(classroom1);
    	lesson.setTypeLesson(typeLesson);
    	lesson.setIdlesson(1);
    	lesson.setStart(mockDate);
    	lesson.setEnd(mockDate);
    	lessons.add(lesson);

    	
        when(lessonRepository.searchLessons(any(Integer.class), any(Integer.class), any(Integer.class), any(Date.class), any(Date.class))).thenReturn(lessons);

        List<LessonDTO> lessonDTOs = lessonService.searchLessons(1, 1, 1, "01-01-2019", "01-02-2019");
        assertEquals(lesson.getIdlesson(),(Integer) lessonDTOs.get(0).getIdlesson());
    }
    
    @Test()
    public void searchTeacherLessonsTest() throws IOException, ParseException {
    	
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
    	
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setSubject(subject);
    	
    	List<Lesson> lessons = new ArrayList<Lesson>();
    	
    	Lesson lesson = new Lesson();
    	lesson.setClassroom(classroom1);
    	lesson.setTypeLesson(typeLesson);
    	lesson.setIdlesson(1);
    	lesson.setStart(mockDate);
    	lesson.setEnd(mockDate);
    	lessons.add(lesson);

    	
        when(lessonRepository.searchTeacherLessons(any(Integer.class), any(Date.class), any(Date.class))).thenReturn(lessons);

        List<LessonDTO> lessonDTOs = lessonService.searchTeacherLessons(1, "01-01-2019", "01-02-2019");
        assertEquals(lesson.getIdlesson(),(Integer) lessonDTOs.get(0).getIdlesson());
    }
    
    @Test()
    public void getAllLessonsByCourseAndTermTest() throws IOException, ParseException {
    	
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
    	
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setSubject(subject);
    	
    	List<Lesson> lessons = new ArrayList<Lesson>();
    	
    	Lesson lesson = new Lesson();
    	lesson.setClassroom(classroom1);
    	lesson.setTypeLesson(typeLesson);
    	lesson.setIdlesson(1);
    	lesson.setStart(mockDate);
    	lesson.setEnd(mockDate);
    	lessons.add(lesson);

    	
        when(lessonRepository.getAllLessonsByCourseAndTerm(1,1)).thenReturn(lessons);

        List<LessonDTO> lessonDTOs = lessonService.getAllLessonsByCourseAndTerm(1,1);
        assertEquals(lesson.getIdlesson(),(Integer) lessonDTOs.get(0).getIdlesson());
    }
    
    @Test()
    public void getTodayLessonsTest() throws IOException {
    	
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
    	
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setSubject(subject);
    	
    	List<Lesson> lessons = new ArrayList<Lesson>();
    	
    	Lesson lesson = new Lesson();
    	lesson.setClassroom(classroom1);
    	lesson.setTypeLesson(typeLesson);
    	lesson.setIdlesson(1);
    	lesson.setStart(mockDate);
    	lesson.setEnd(mockDate);
    	lessons.add(lesson);

    	
        when(lessonRepository.getTodayLessons(1)).thenReturn(lessons);

        List<LessonDTO> lessonDTOs = lessonService.getTodayLessons(1);
        assertEquals(lesson.getIdlesson(),(Integer) lessonDTOs.get(0).getIdlesson());
    }
    
    @Test()
    public void getTeacherTodayLessonsTest() throws IOException {
    	
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
    	
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setSubject(subject);
    	
    	List<Lesson> lessons = new ArrayList<Lesson>();
    	
    	Lesson lesson = new Lesson();
    	lesson.setClassroom(classroom1);
    	lesson.setTypeLesson(typeLesson);
    	lesson.setIdlesson(1);
    	lesson.setStart(mockDate);
    	lesson.setEnd(mockDate);
    	lessons.add(lesson);

    	
        when(lessonRepository.getTeacherTodayLessons(1)).thenReturn(lessons);

        List<LessonDTO> lessonDTOs = lessonService.getTeacherTodayLessons(1);
        assertEquals(lesson.getIdlesson(),(Integer) lessonDTOs.get(0).getIdlesson());
    }
    
    @Test()
    public void saveTest() throws IOException, LessonNotFoundException {
    	
    	Date mockDate = new Date();
    	
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
		
    	TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
    	typeSubjectDTO.setIdtypeSubject(1);
    	typeSubjectDTO.setName("Analisi 1");
    	typeSubjectDTO.setDescription("Bella");

    	TeacherDTO teacherDTO = new TeacherDTO();
    	teacherDTO.setIdteacher(1);
    	teacherDTO.setName("Antonio");
    	teacherDTO.setSurname("Leaci");
    	
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
    	
    	SubjectDTO subjectDTO = new SubjectDTO();
    	subjectDTO.setId(1);
    	subjectDTO.setTypeSubjectDTO(typeSubjectDTO);
    	subjectDTO.setCfu(12);
    	subjectDTO.setDegreecourseDTO(courseDTO1);
    	subjectDTO.setTeacherDTO(teacherDTO);
    	
    	TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
    	typeLessonDTO.setSubject(subjectDTO);
    	
    	LessonDTO lessonDTO = new LessonDTO();
    	lessonDTO.setClassroom(classroomDTO1);
    	lessonDTO.setTypeLesson(typeLessonDTO);
    	lessonDTO.setIdlesson(1);
    	lessonDTO.setStart(mockDate);
    	lessonDTO.setEnd(mockDate);
    	
    	
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
    	
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setSubject(subject);
    	
    	Lesson lesson = new Lesson();
    	lesson.setClassroom(classroom1);
    	lesson.setTypeLesson(typeLesson);
    	lesson.setIdlesson(1);
    	lesson.setStart(mockDate);
    	lesson.setEnd(mockDate);
    	
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        LessonDTO savedLessonDTO = lessonService.save(lessonDTO);
        assertEquals(lesson.getIdlesson(),(Integer) savedLessonDTO.getIdlesson());
    }
    
    @Test()
    public void getByIdTest() throws IOException, LessonNotFoundException {
    	
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
    	
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setSubject(subject);
    	
    	Lesson lesson = new Lesson();
    	lesson.setClassroom(classroom1);
    	lesson.setTypeLesson(typeLesson);
    	lesson.setIdlesson(1);
    	lesson.setStart(mockDate);
    	lesson.setEnd(mockDate);
    	
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));

        LessonDTO lessonDTO = lessonService.getById(1);
        assertEquals(lesson.getIdlesson(),(Integer) lessonDTO.getIdlesson());
    }
    
    @Test()
    public void getCurrentSchedulerByCourseTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	DegreeCourseDTO courseDTO1 = new DegreeCourseDTO();
    	courseDTO1.setIdcourse(1);
    	
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
    	
    	Term term = new Term();
    	term.setIdterm(1);
    	term.setStart(mockDate);
    	term.setEnd(mockDate);
    	term.setAcademicYear(year1);
    	
    	Scheduler scheduler = new Scheduler();
    	scheduler.setIdscheduler(1);
    	scheduler.setTerm(term);
    	
    	Day day = new Day();
    	day.setIdday(1);
    	day.setName("Lunedi");
    	
    	List<TypeLesson> typeLessons = new ArrayList<TypeLesson>();
    	
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setIdtypeLesson(1);
    	typeLesson.setClassroom(classroom1);
    	typeLesson.setDay(day);
    	typeLesson.setScheduler(scheduler);
    	typeLesson.setSubject(subject);
    	typeLesson.setStart(mockDate);
    	typeLesson.setEnd(mockDate);
    	typeLessons.add(typeLesson);
    	
        when(typeLessonRepository.getCurrentSchedulerByIDCourse(1, 1)).thenReturn(typeLessons);

        List<TypeLessonDTO> lessonDTOs = lessonService.getCurrentSchedulerByCourse(courseDTO1, 1);
        assertEquals(typeLesson.getIdtypeLesson(),(Integer) lessonDTOs.get(0).getIdtypeLesson());
    }
    
    @Test()
    public void getCurrentSchedulerTeacherTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	DegreeCourseDTO courseDTO1 = new DegreeCourseDTO();
    	courseDTO1.setIdcourse(1);
    	
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
    	
    	Term term = new Term();
    	term.setIdterm(1);
    	term.setStart(mockDate);
    	term.setEnd(mockDate);
    	term.setAcademicYear(year1);
    	
    	Scheduler scheduler = new Scheduler();
    	scheduler.setIdscheduler(1);
    	scheduler.setTerm(term);
    	
    	Day day = new Day();
    	day.setIdday(1);
    	day.setName("Lunedi");
    	
    	List<TypeLesson> typeLessons = new ArrayList<TypeLesson>();
    	
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setIdtypeLesson(1);
    	typeLesson.setClassroom(classroom1);
    	typeLesson.setDay(day);
    	typeLesson.setScheduler(scheduler);
    	typeLesson.setSubject(subject);
    	typeLesson.setStart(mockDate);
    	typeLesson.setEnd(mockDate);
    	typeLessons.add(typeLesson);
    	
        when(typeLessonRepository.getCurrentSchedulerTeacher(1)).thenReturn(typeLessons);

        List<TypeLessonDTO> lessonDTOs = lessonService.getCurrentSchedulerTeacher(1);
        assertEquals(typeLesson.getIdtypeLesson(),(Integer) lessonDTOs.get(0).getIdtypeLesson());
    }
    
    @Test()
    public void getFeedbackTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	List<FeedbackLesson> feedbacks = new ArrayList<FeedbackLesson>();
    	
    	User user = new User();
    	user.setIduser(1);
    	
    	Feedback feedback = new Feedback();
    	feedback.setIdfeedback(1);
    	feedback.setUser(user);
    	feedback.setDate(mockDate);
    	feedback.setStars(5);
    	
    	FeedbackLessonId id = new FeedbackLessonId();
    	id.setIdfeedback(1);
    	id.setIdlesson(1);
    	
    	FeedbackLesson feedbackLesson = new FeedbackLesson();
    	feedbackLesson.setId(id);
    	feedbackLesson.setFeedback(feedback);
    	feedbackLesson.setDescription("Ottima lezione");
    	feedbacks.add(feedbackLesson);
    	
        when(feedbackLessonRepository.getFeedbackLesson(1)).thenReturn(feedbacks);

        List<FeedbackDTO> feedbackDTOs = lessonService.getFeedback(1);
        assertEquals(feedbackLesson.getFeedback().getIdfeedback(),(Integer) feedbackDTOs.get(0).getId());
    }
    
    @Test()
    public void saveFeedbackTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	UserDTO userDTO = new UserDTO();
    	userDTO.setIduser(1);
    	
    	FeedbackDTO feedbackDTO = new FeedbackDTO();
    	feedbackDTO.setUser(userDTO);
    	feedbackDTO.setDate(mockDate);
    	feedbackDTO.setStars(5);
    	feedbackDTO.setDescription("Ottima lezione");
    	
    	User user = new User();
    	user.setIduser(1);
    	
    	Feedback feedback = new Feedback();
    	feedback.setIdfeedback(1);
    	feedback.setUser(user);
    	feedback.setDate(mockDate);
    	feedback.setStars(5);
    	
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        lessonService.saveFeedback(1, feedbackDTO);
    }
    
    
    @Test()
    public void editTest() throws IOException, LessonNotFoundException {
    	
    	Date mockDate = new Date();
    	
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
		
    	TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
    	typeSubjectDTO.setIdtypeSubject(1);
    	typeSubjectDTO.setName("Analisi 1");
    	typeSubjectDTO.setDescription("Bella");

    	TeacherDTO teacherDTO = new TeacherDTO();
    	teacherDTO.setIdteacher(1);
    	teacherDTO.setName("Antonio");
    	teacherDTO.setSurname("Leaci");
    	
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
    	
    	SubjectDTO subjectDTO = new SubjectDTO();
    	subjectDTO.setId(1);
    	subjectDTO.setTypeSubjectDTO(typeSubjectDTO);
    	subjectDTO.setCfu(12);
    	subjectDTO.setDegreecourseDTO(courseDTO1);
    	subjectDTO.setTeacherDTO(teacherDTO);
    	
    	TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
    	typeLessonDTO.setSubject(subjectDTO);
    	
    	ArrayList<LessonDTO> lessonDTOs = new ArrayList<LessonDTO>();
    	LessonDTO lessonDTO = new LessonDTO();
    	lessonDTO.setClassroom(classroomDTO1);
    	lessonDTO.setTypeLesson(typeLessonDTO);
    	lessonDTO.setIdlesson(1);
    	lessonDTO.setStart(mockDate);
    	lessonDTO.setEnd(mockDate);
    	lessonDTOs.add(lessonDTO);
    	
    	
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
    	
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setSubject(subject);
    	
    	Lesson lesson = new Lesson();
    	lesson.setClassroom(classroom1);
    	lesson.setTypeLesson(typeLesson);
    	lesson.setIdlesson(1);
    	lesson.setStart(mockDate);
    	lesson.setEnd(mockDate);
    	
        when(lessonRepository.getOne(1)).thenReturn(lesson);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        lessonService.edit(lessonDTOs);
    }
    
    @Test
    public void deleteTest() throws LessonNotFoundException {
    	
    	lessonService.delete(1);
    }
}