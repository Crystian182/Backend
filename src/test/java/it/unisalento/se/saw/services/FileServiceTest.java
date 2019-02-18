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
import it.unisalento.se.saw.domain.FeedbackFile;
import it.unisalento.se.saw.domain.FeedbackFileId;
import it.unisalento.se.saw.domain.FeedbackLesson;
import it.unisalento.se.saw.domain.FeedbackLessonId;
import it.unisalento.se.saw.domain.File;
import it.unisalento.se.saw.domain.FileLesson;
import it.unisalento.se.saw.domain.FileLessonId;
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
import it.unisalento.se.saw.dto.FileDTO;
import it.unisalento.se.saw.dto.FileLessonDTO;
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
import it.unisalento.se.saw.repositories.BuildingRepository;
import it.unisalento.se.saw.repositories.FeedbackFileRepository;
import it.unisalento.se.saw.repositories.FeedbackLessonRepository;
import it.unisalento.se.saw.repositories.FeedbackRepository;
import it.unisalento.se.saw.repositories.FileLessonRepository;
import it.unisalento.se.saw.repositories.FileRepository;
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
public class FileServiceTest {
    
	@Mock
	FileRepository fileRepository;
	
	@Mock
	FeedbackFileRepository feedbackFileRepository;
	
	@Mock
	FeedbackRepository feedbackRepository;
	
	@Mock
	FileLessonRepository fileLessonRepository;
	
	@Mock
	BuildingRepository buildingRepository;

    @InjectMocks
    FileService fileService;

    @Test()
    public void getLessonFilesTest() throws IOException {
    	
    	List<it.unisalento.se.saw.domain.File> files = new ArrayList<it.unisalento.se.saw.domain.File>();
    	
    	File file = new File();
    	file.setIdfile(1);
    	file.setName("soluzione.txt");
    	files.add(file);
    	
        when(fileLessonRepository.getLessonFiles(1)).thenReturn(files);
        when(feedbackFileRepository.getStars(1)).thenReturn((double) 5);

        List<FileDTO> fileDTOs = fileService.getLessonFiles(1);
        assertEquals(file.getIdfile(),(Integer) fileDTOs.get(0).getIdFile());
    }
    
    @Test()
    public void saveFeedbackTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	UserDTO userDTO = new UserDTO();
    	userDTO.setIduser(1);
    	
    	FeedbackDTO feedbackDTO = new FeedbackDTO();
    	feedbackDTO.setDate(mockDate);
    	feedbackDTO.setStars(5);
    	feedbackDTO.setDescription("Ottimo file");
    	
    	Feedback feedback = new Feedback();
    	feedback.setIdfeedback(1);
    	
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);
        when(fileLessonRepository.getIdLessonFromIdFile(1)).thenReturn(1);
    }
    
    @Test()
    public void getLastFilesTest() throws IOException {
    	
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
    	
    	List<FileLesson> fileLessons = new ArrayList<FileLesson>();
    	
    	FileLessonId id = new FileLessonId();
    	id.setFileIdfile(1);
    	id.setLessonIdlesson(1);
    	
    	File file = new File();
    	file.setIdfile(1);
    	file.setName("soluzione.txt");
    	
    	FileLesson fileLesson = new FileLesson();
    	fileLesson.setId(id);
    	fileLesson.setFile(file);
    	fileLesson.setLesson(lesson);
    	fileLesson.setDate(mockDate);
    	fileLessons.add(fileLesson);

    	
        when(fileLessonRepository.getLastFiles(1)).thenReturn(fileLessons);

        List<FileLessonDTO> fileLessonDTOs = fileService.getLastFiles(1);
        assertEquals(file.getIdfile(),(Integer) fileLessonDTOs.get(0).getFile().getIdFile());
    }
    
    @Test()
    public void getSubjectFilesTest() throws IOException {
    	
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
    	
    	List<FileLesson> fileLessons = new ArrayList<FileLesson>();
    	
    	FileLessonId id = new FileLessonId();
    	id.setFileIdfile(1);
    	id.setLessonIdlesson(1);
    	
    	File file = new File();
    	file.setIdfile(1);
    	file.setName("soluzione.txt");
    	
    	FileLesson fileLesson = new FileLesson();
    	fileLesson.setId(id);
    	fileLesson.setFile(file);
    	fileLesson.setLesson(lesson);
    	fileLesson.setDate(mockDate);
    	fileLessons.add(fileLesson);

    	
        when(fileLessonRepository.getSubjectFiles(1)).thenReturn(fileLessons);
        when(feedbackFileRepository.getStars(1)).thenReturn((double) 5);

        List<FileLessonDTO> fileLessonDTOs = fileService.getSubjectFiles(1);
        assertEquals(file.getIdfile(),(Integer) fileLessonDTOs.get(0).getFile().getIdFile());
    }
    
    @Test()
    public void getFeedbackFileTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	FeedbackFileId id = new FeedbackFileId();
    	id.setIdfeedback(1);
    	
    	User user = new User();
    	user.setIduser(1);
    	
    	Feedback feedback = new Feedback();
    	feedback.setUser(user);
    	feedback.setDate(mockDate);
    	feedback.setStars(5);
    	
    	List<FeedbackFile> feedbackFiles = new ArrayList<FeedbackFile>();
    	
    	FeedbackFile feedbackFile = new FeedbackFile();
    	feedbackFile.setId(id);
    	feedbackFile.setFeedback(feedback);
    	feedbackFile.setDescription("Ottimo file");
    	feedbackFiles.add(feedbackFile);
    	
        when(feedbackFileRepository.getFeedbackFile(1)).thenReturn(feedbackFiles);
        
        List<FeedbackDTO> feedbackDTOs = fileService.getFeedbackFile(1);
        assertEquals(feedbackFile.getDescription(), feedbackDTOs.get(0).getDescription());
    }
}