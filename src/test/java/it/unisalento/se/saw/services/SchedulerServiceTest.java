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
import it.unisalento.se.saw.dto.DayDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.ExamDTO;
import it.unisalento.se.saw.dto.ExamEnrollmentDTO;
import it.unisalento.se.saw.dto.ExamTypeDTO;
import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.ResultDTO;
import it.unisalento.se.saw.dto.SchedulerDTO;
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
import it.unisalento.se.saw.repositories.FeedbackLessonRepository;
import it.unisalento.se.saw.repositories.FeedbackRepository;
import it.unisalento.se.saw.repositories.LessonRepository;
import it.unisalento.se.saw.repositories.SchedulerRepository;
import it.unisalento.se.saw.repositories.TermRepository;
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
public class SchedulerServiceTest {
    
	@Mock
	SchedulerRepository schedulerRepository;
	 
	@Mock
	TypeLessonRepository typeLessonRepository;
	 
	@Mock
	LessonRepository lessonRepository;
	 
	@Mock
	TermRepository termRepository;

    @InjectMocks
    SchedulerService schedulerService;

    @Test()
    public void saveTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	DegreeCourseDTO courseDTO = new DegreeCourseDTO();
    	courseDTO.setIdcourse(1);
    	
    	TermDTO termDTO = new TermDTO();
    	termDTO.setIdterm(1);
    	
    	SubjectDTO subjectDTO = new SubjectDTO();
    	subjectDTO.setId(1);
    	
    	DayDTO dayDTO = new DayDTO();
    	dayDTO.setIdDay(1);
    	dayDTO.setName("Lunedi");
    	
    	ClassroomDTO classroomDTO = new ClassroomDTO();
		classroomDTO.setId(1);
		classroomDTO.setName("I1");
		classroomDTO.setSeats(180);
		classroomDTO.setLat((float) 17.9);
		classroomDTO.setLng((float) 21.3);
    	
    	List<TypeLessonDTO> typeLessonDTOs = new ArrayList<TypeLessonDTO>();
    	TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
    	typeLessonDTO.setIdtypeLesson(1);
    	typeLessonDTO.setSubject(subjectDTO);
    	typeLessonDTO.setStart(mockDate);
    	typeLessonDTO.setEnd(mockDate);
    	typeLessonDTO.setDay(dayDTO);
    	typeLessonDTO.setClassroom(classroomDTO);
    	typeLessonDTOs.add(typeLessonDTO);
    	
    	SchedulerDTO schedulerDTO = new SchedulerDTO();
    	schedulerDTO.setIdScheduler(1);
    	schedulerDTO.setDegreeCourse(courseDTO);
    	schedulerDTO.setTerm(termDTO);
    	schedulerDTO.setTypeLessons(typeLessonDTOs);
    	
    	List<Term> terms = new ArrayList<Term>();
    	Term term = new Term();
    	term.setIdterm(1);
    	term.setStart(mockDate);
    	term.setEnd(mockDate);
    	terms.add(term);
    	
    	AcademicYear year = new AcademicYear();
    	year.setIdacademicYear(1);
    	year.setYear(2018);
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Laurea Triennale");
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
    	
    	DegreeCourse course = new DegreeCourse();
    	course.setIddegreeCourse(1);
    	course.setAcademicYear(year);
    	course.setTypeDegreeCourse(typeDegreeCourse);
    	
    	Scheduler scheduler = new Scheduler();
    	scheduler.setIdscheduler(1);
    	scheduler.setDegreeCourse(course);
    	scheduler.setTerm(term);
    	
    	Day day = new Day();
    	day.setIdday(1);
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setName("Analisi 1");
    	
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setTypeSubject(typeSubject);
    	
    	Building building = new Building();
    	building.setIdbuilding(1);
    	building.setName("Stecca");
    	
    	Classroom classroom = new Classroom();
    	classroom.setIdclassroom(1);
    	classroom.setName("I1");
    	classroom.setBuilding(building);

    	List<TypeLesson> typeLessons = new ArrayList<TypeLesson>();
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setIdtypeLesson(1);
    	typeLesson.setStart(mockDate);
    	typeLesson.setEnd(mockDate);
    	typeLesson.setDay(day);
    	typeLesson.setSubject(subject);
    	typeLesson.setClassroom(classroom);
    	typeLessons.add(typeLesson);
    	
        when(schedulerRepository.save(any(Scheduler.class))).thenReturn(scheduler);
        when(schedulerRepository.getOne(1)).thenReturn(scheduler);
        when(typeLessonRepository.save(any(TypeLesson.class))).thenReturn(typeLesson);
        when(termRepository.getByAcademicYear(1)).thenReturn(terms);
        when(typeLessonRepository.getTypeLessonsOfScheduler(1)).thenReturn(typeLessons);

        SchedulerDTO savedSchedulerDTO = schedulerService.save(schedulerDTO);
        assertEquals(schedulerDTO.getDegreeCourse().getIdcourse(),(Integer) savedSchedulerDTO.getDegreeCourse().getIdcourse());
    }

    @Test()
    public void save2Test() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	DegreeCourseDTO courseDTO = new DegreeCourseDTO();
    	courseDTO.setIdcourse(1);
    	
    	TermDTO termDTO = new TermDTO();
    	termDTO.setIdterm(1);
    	
    	SubjectDTO subjectDTO = new SubjectDTO();
    	subjectDTO.setId(1);
    	
    	DayDTO dayDTO = new DayDTO();
    	dayDTO.setIdDay(1);
    	dayDTO.setName("Lunedi");
    	
    	ClassroomDTO classroomDTO = new ClassroomDTO();
		classroomDTO.setId(1);
		classroomDTO.setName("I1");
		classroomDTO.setSeats(180);
		classroomDTO.setLat((float) 17.9);
		classroomDTO.setLng((float) 21.3);
    	
    	List<TypeLessonDTO> typeLessonDTOs = new ArrayList<TypeLessonDTO>();
    	TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
    	typeLessonDTO.setIdtypeLesson(1);
    	typeLessonDTO.setSubject(subjectDTO);
    	typeLessonDTO.setStart(mockDate);
    	typeLessonDTO.setEnd(mockDate);
    	typeLessonDTO.setDay(dayDTO);
    	typeLessonDTO.setClassroom(classroomDTO);
    	typeLessonDTOs.add(typeLessonDTO);
    	
    	SchedulerDTO schedulerDTO = new SchedulerDTO();
    	schedulerDTO.setIdScheduler(0);
    	schedulerDTO.setDegreeCourse(courseDTO);
    	schedulerDTO.setTerm(termDTO);
    	schedulerDTO.setTypeLessons(typeLessonDTOs);
    	
    	List<Term> terms = new ArrayList<Term>();
    	Term term = new Term();
    	term.setIdterm(1);
    	term.setStart(mockDate);
    	term.setEnd(mockDate);
    	terms.add(term);
    	
    	AcademicYear year = new AcademicYear();
    	year.setIdacademicYear(1);
    	year.setYear(2018);
    	
    	CourseType courseType = new CourseType();
    	courseType.setIdcourseType(1);
    	courseType.setCfu(180);
    	courseType.setDescription("Laurea Triennale");
    	
    	TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
    	typeDegreeCourse.setIdtypeDegreeCourse(1);
    	typeDegreeCourse.setName("Ingegneria dell'Informazione");
    	typeDegreeCourse.setCourseType(courseType);
    	
    	DegreeCourse course = new DegreeCourse();
    	course.setIddegreeCourse(1);
    	course.setAcademicYear(year);
    	course.setTypeDegreeCourse(typeDegreeCourse);
    	
    	Scheduler scheduler = new Scheduler();
    	scheduler.setIdscheduler(1);
    	scheduler.setDegreeCourse(course);
    	scheduler.setTerm(term);
    	
    	Day day = new Day();
    	day.setIdday(1);
    	
    	TypeSubject typeSubject = new TypeSubject();
    	typeSubject.setName("Analisi 1");
    	
    	Subject subject = new Subject();
    	subject.setIdsubject(1);
    	subject.setTypeSubject(typeSubject);
    	
    	Building building = new Building();
    	building.setIdbuilding(1);
    	building.setName("Stecca");
    	
    	Classroom classroom = new Classroom();
    	classroom.setIdclassroom(1);
    	classroom.setName("I1");
    	classroom.setBuilding(building);

    	List<TypeLesson> typeLessons = new ArrayList<TypeLesson>();
    	TypeLesson typeLesson = new TypeLesson();
    	typeLesson.setIdtypeLesson(1);
    	typeLesson.setStart(mockDate);
    	typeLesson.setEnd(mockDate);
    	typeLesson.setDay(day);
    	typeLesson.setSubject(subject);
    	typeLesson.setClassroom(classroom);
    	typeLessons.add(typeLesson);
    	
        when(schedulerRepository.save(any(Scheduler.class))).thenReturn(scheduler);
        when(schedulerRepository.getOne(1)).thenReturn(scheduler);
        when(typeLessonRepository.save(any(TypeLesson.class))).thenReturn(typeLesson);
        when(termRepository.getByAcademicYear(1)).thenReturn(terms);
        when(typeLessonRepository.getTypeLessonsOfScheduler(1)).thenReturn(typeLessons);

        SchedulerDTO savedSchedulerDTO = schedulerService.save(schedulerDTO);
        assertEquals(schedulerDTO.getDegreeCourse().getIdcourse(),(Integer) savedSchedulerDTO.getDegreeCourse().getIdcourse());
    }
   

}