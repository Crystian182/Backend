package it.unisalento.se.saw.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IFCMService;
import it.unisalento.se.saw.Iservices.ILessonService;
import it.unisalento.se.saw.domain.AcademicYear;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Feedback;
import it.unisalento.se.saw.domain.FeedbackFile;
import it.unisalento.se.saw.domain.FeedbackFileId;
import it.unisalento.se.saw.domain.FeedbackLesson;
import it.unisalento.se.saw.domain.FeedbackLessonId;
import it.unisalento.se.saw.domain.Lesson;
import it.unisalento.se.saw.domain.Subject;
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
import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.SchedulerDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.LessonNotFoundException;
import it.unisalento.se.saw.repositories.FeedbackLessonRepository;
import it.unisalento.se.saw.repositories.FeedbackRepository;
import it.unisalento.se.saw.repositories.LessonRepository;
import it.unisalento.se.saw.repositories.TypeLessonRepository;


@Service
public class LessonService implements ILessonService{

	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	TypeLessonRepository typeLessonRepository;
	
	@Autowired
	FeedbackLessonRepository feedbackLessonRepository;
	
	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Autowired
	IFCMService fcmService;

	 @Transactional(readOnly = true)
	 public List<LessonDTO> getAll() {
		  List<Lesson> lessons = lessonRepository.findAll();
		  List<LessonDTO> lessonDTOs = new ArrayList<LessonDTO>();
		  for(int i=0; i<lessons.size(); i++) {
		    
		    BuildingDTO buildingDTO = new BuildingDTO();
		    buildingDTO.setId(lessons.get(i).getClassroom().getBuilding().getIdbuilding());
		    buildingDTO.setName(lessons.get(i).getClassroom().getBuilding().getName());
		    buildingDTO.setAddress(lessons.get(i).getClassroom().getBuilding().getAddress());
		    buildingDTO.setLat(lessons.get(i).getClassroom().getBuilding().getLat());
		    buildingDTO.setLng(lessons.get(i).getClassroom().getBuilding().getLng());
		    
		    ClassroomDTO classroomDTO = new ClassroomDTO();
		    classroomDTO.setId(lessons.get(i).getClassroom().getIdclassroom());
		    classroomDTO.setName(lessons.get(i).getClassroom().getName());
		    classroomDTO.setSeats(lessons.get(i).getClassroom().getSeats());
		    classroomDTO.setLat(lessons.get(i).getClassroom().getLat());
		    classroomDTO.setLng(lessons.get(i).getClassroom().getLng());
		    classroomDTO.setBuilding(buildingDTO);
		    
		    
		    TeacherDTO teacherDTO = new TeacherDTO();
		    teacherDTO.setIdteacher(lessons.get(i).getTypeLesson().getSubject().getTeacher().getIduser());
		    teacherDTO.setName(lessons.get(i).getTypeLesson().getSubject().getTeacher().getUser().getName());
		    teacherDTO.setSurname(lessons.get(i).getTypeLesson().getSubject().getTeacher().getUser().getSurname());
		    
		    TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
		    typeDegreeCourseDTO.setIdtypeDegreeCourse(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
		    typeDegreeCourseDTO.setName(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
		    
		    CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
		    courseTypeDTO.setIdcourseType(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
		    courseTypeDTO.setDescription(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
		    courseTypeDTO.setCfu(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		    courseTypeDTO.setDuration(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDuration());
		    typeDegreeCourseDTO.setCourseType(courseTypeDTO);
		    
		    DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
		    degreeCourseDTO.setIdcourse(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getIddegreeCourse());
		    degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
		    AcademicYearDTO academicYearDTO = new AcademicYearDTO();
		    academicYearDTO.setIdacademicYear(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
		    academicYearDTO.setYear(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getAcademicYear().getYear());
		    degreeCourseDTO.setAcademicYear(academicYearDTO);
		    degreeCourseDTO.setCfu(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		    
		    SubjectDTO subjectDTO = new SubjectDTO();
		    subjectDTO.setId(lessons.get(i).getTypeLesson().getSubject().getIdsubject());
		    subjectDTO.setName(lessons.get(i).getTypeLesson().getSubject().getTypeSubject().getName());
		    subjectDTO.setDescription(lessons.get(i).getTypeLesson().getSubject().getTypeSubject().getDescription());
		    subjectDTO.setCfu(lessons.get(i).getTypeLesson().getSubject().getCfu());
		    subjectDTO.setDegreecourseDTO(degreeCourseDTO);
		    subjectDTO.setTeacherDTO(teacherDTO);
		    
		    TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
		    typeLessonDTO.setSubject(subjectDTO);
		    
		    LessonDTO lessonDTO = new LessonDTO();
		    
		    lessonDTO.setIdlesson(lessons.get(i).getIdlesson());
		    lessonDTO.setStart(lessons.get(i).getStart());
		    lessonDTO.setEnd(lessons.get(i).getEnd());
		    lessonDTO.setClassroom(classroomDTO);
		    lessonDTO.setTypeLesson(typeLessonDTO);
		  
		    lessonDTOs.add(lessonDTO);
		   }
		   
		  return lessonDTOs;
		  
	 }
	 
	 @Transactional(readOnly = true)
	 public List<LessonDTO> getAllTeacherLessons(int idteacher) {
		  List<Lesson> lessons = lessonRepository.getAllTeacherLessons(idteacher);
		  List<LessonDTO> lessonDTOs = new ArrayList<LessonDTO>();
		  for(int i=0; i<lessons.size(); i++) {
		    
			    BuildingDTO buildingDTO = new BuildingDTO();
			    buildingDTO.setId(lessons.get(i).getClassroom().getBuilding().getIdbuilding());
			    buildingDTO.setName(lessons.get(i).getClassroom().getBuilding().getName());
			    buildingDTO.setAddress(lessons.get(i).getClassroom().getBuilding().getAddress());
			    buildingDTO.setLat(lessons.get(i).getClassroom().getBuilding().getLat());
			    buildingDTO.setLng(lessons.get(i).getClassroom().getBuilding().getLng());
			    
			    ClassroomDTO classroomDTO = new ClassroomDTO();
			    classroomDTO.setId(lessons.get(i).getClassroom().getIdclassroom());
			    classroomDTO.setName(lessons.get(i).getClassroom().getName());
			    classroomDTO.setSeats(lessons.get(i).getClassroom().getSeats());
			    classroomDTO.setLat(lessons.get(i).getClassroom().getLat());
			    classroomDTO.setLng(lessons.get(i).getClassroom().getLng());
			    classroomDTO.setBuilding(buildingDTO);
			    
			    
			    TeacherDTO teacherDTO = new TeacherDTO();
			    teacherDTO.setIdteacher(lessons.get(i).getTypeLesson().getSubject().getTeacher().getIduser());
			    teacherDTO.setName(lessons.get(i).getTypeLesson().getSubject().getTeacher().getUser().getName());
			    teacherDTO.setSurname(lessons.get(i).getTypeLesson().getSubject().getTeacher().getUser().getSurname());
			    
			    TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
			    typeDegreeCourseDTO.setIdtypeDegreeCourse(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
			    typeDegreeCourseDTO.setName(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
			    
			    CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
			    courseTypeDTO.setIdcourseType(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
			    courseTypeDTO.setDescription(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
			    courseTypeDTO.setCfu(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			    courseTypeDTO.setDuration(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			    typeDegreeCourseDTO.setCourseType(courseTypeDTO);
			    
			    DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			    degreeCourseDTO.setIdcourse(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getIddegreeCourse());
			    degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
			    AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			    academicYearDTO.setIdacademicYear(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
			    academicYearDTO.setYear(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getAcademicYear().getYear());
			    degreeCourseDTO.setAcademicYear(academicYearDTO);
			    degreeCourseDTO.setCfu(lessons.get(i).getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			    
			    SubjectDTO subjectDTO = new SubjectDTO();
			    subjectDTO.setId(lessons.get(i).getTypeLesson().getSubject().getIdsubject());
			    subjectDTO.setName(lessons.get(i).getTypeLesson().getSubject().getTypeSubject().getName());
			    subjectDTO.setDescription(lessons.get(i).getTypeLesson().getSubject().getTypeSubject().getDescription());
			    subjectDTO.setCfu(lessons.get(i).getTypeLesson().getSubject().getCfu());
			    subjectDTO.setDegreecourseDTO(degreeCourseDTO);
			    subjectDTO.setTeacherDTO(teacherDTO);
			    
			    TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
			    typeLessonDTO.setSubject(subjectDTO);
			    
			    LessonDTO lessonDTO = new LessonDTO();
			    
			    lessonDTO.setIdlesson(lessons.get(i).getIdlesson());
			    lessonDTO.setStart(lessons.get(i).getStart());
			    lessonDTO.setEnd(lessons.get(i).getEnd());
			    lessonDTO.setClassroom(classroomDTO);
			    lessonDTO.setTypeLesson(typeLessonDTO);
			  
			    lessonDTOs.add(lessonDTO);
		   }
		   
		  return lessonDTOs;
		  
	 }
	
	
	 @Transactional(rollbackFor=LessonNotFoundException.class)
	 public LessonDTO getById(int id) throws LessonNotFoundException {
		  try {
			   Lesson lesson = lessonRepository.findById(id).get();
			   
			   BuildingDTO buildingDTO = new BuildingDTO();
			   buildingDTO.setId(lesson.getClassroom().getBuilding().getIdbuilding());
			   buildingDTO.setName(lesson.getClassroom().getBuilding().getName());
			   buildingDTO.setAddress(lesson.getClassroom().getBuilding().getAddress());
			   buildingDTO.setLat(lesson.getClassroom().getBuilding().getLat());
			   buildingDTO.setLng(lesson.getClassroom().getBuilding().getLng());
			   
			   ClassroomDTO classroomDTO = new ClassroomDTO();
			   classroomDTO.setId(lesson.getClassroom().getIdclassroom());
			   classroomDTO.setName(lesson.getClassroom().getName());
			   classroomDTO.setSeats(lesson.getClassroom().getSeats());
			   classroomDTO.setLat(lesson.getClassroom().getLat());
			   classroomDTO.setLng(lesson.getClassroom().getLng());
			   classroomDTO.setBuilding(buildingDTO);
			   
			   TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
			   typeDegreeCourseDTO.setIdtypeDegreeCourse(lesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
			   typeDegreeCourseDTO.setName(lesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
			   
			   CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
			   courseTypeDTO.setIdcourseType(lesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
			   courseTypeDTO.setDescription(lesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
			   courseTypeDTO.setCfu(lesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			   courseTypeDTO.setDuration(lesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			   typeDegreeCourseDTO.setCourseType(courseTypeDTO);
			   
			   DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			   degreeCourseDTO.setIdcourse(lesson.getTypeLesson().getSubject().getDegreeCourse().getIddegreeCourse());
			   degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
			   degreeCourseDTO.setCfu(lesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			   AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			   academicYearDTO.setIdacademicYear(lesson.getTypeLesson().getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
			   academicYearDTO.setYear(lesson.getTypeLesson().getSubject().getDegreeCourse().getAcademicYear().getYear());
			   degreeCourseDTO.setAcademicYear(academicYearDTO);
			   
			   TeacherDTO teacherDTO = new TeacherDTO();
			   teacherDTO.setIdteacher(lesson.getTypeLesson().getSubject().getTeacher().getIduser());
			   teacherDTO.setName(lesson.getTypeLesson().getSubject().getTeacher().getUser().getName());
			   teacherDTO.setSurname(lesson.getTypeLesson().getSubject().getTeacher().getUser().getSurname());
			   
			   SubjectDTO subjectDTO = new SubjectDTO();
			   subjectDTO.setId(lesson.getTypeLesson().getSubject().getIdsubject());
			   subjectDTO.setName(lesson.getTypeLesson().getSubject().getTypeSubject().getName());
			   subjectDTO.setDescription(lesson.getTypeLesson().getSubject().getTypeSubject().getDescription());
			   subjectDTO.setCfu(lesson.getTypeLesson().getSubject().getCfu());
			   subjectDTO.setDegreecourseDTO(degreeCourseDTO);
			   subjectDTO.setTeacherDTO(teacherDTO);
			   
			   TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
			   typeLessonDTO.setSubject(subjectDTO);
			  
			   LessonDTO lessonDTO = new LessonDTO();
			   
			   lessonDTO.setIdlesson(lesson.getIdlesson());
			   lessonDTO.setStart(lesson.getStart());
			   lessonDTO.setEnd(lesson.getEnd());
			   lessonDTO.setClassroom(classroomDTO);
			   lessonDTO.setTypeLesson(typeLessonDTO);
			   
			   return lessonDTO;
		
		  } catch (Exception e) {
			  throw new LessonNotFoundException();
		  }
	 }
	
	
	 @Transactional
	 public LessonDTO save(LessonDTO lessonDTO) {
	
		  Building building = new Building();
		  building.setIdbuilding(lessonDTO.getClassroom().getBuilding().getId());
		  building.setName(lessonDTO.getClassroom().getBuilding().getName());
		  building.setAddress(lessonDTO.getClassroom().getBuilding().getAddress());
		  building.setLat(lessonDTO.getClassroom().getBuilding().getLat());
		  building.setLng(lessonDTO.getClassroom().getBuilding().getLng());
		  
		  Classroom classroom = new Classroom();
		  classroom.setIdclassroom(lessonDTO.getClassroom().getId());
		  classroom.setName(lessonDTO.getClassroom().getName());
		  classroom.setSeats(lessonDTO.getClassroom().getSeats());
		  classroom.setLat(lessonDTO.getClassroom().getLat());
		  classroom.setLng(lessonDTO.getClassroom().getLng());
		  classroom.setBuilding(building);
		  
		  TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
		  typeDegreeCourse.setIdtypeDegreeCourse(lessonDTO.getTypeLesson().getSubject().getDegreecourseDTO().getTypeDegreeCourse().getIdtypeDegreeCourse());
		  typeDegreeCourse.setName(lessonDTO.getTypeLesson().getSubject().getDegreecourseDTO().getTypeDegreeCourse().getName());
		  
		  CourseType courseType = new CourseType();
		  courseType.setIdcourseType(lessonDTO.getTypeLesson().getSubject().getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getIdcourseType());
		  courseType.setCfu(lessonDTO.getTypeLesson().getSubject().getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getCfu());
		  courseType.setDuration(lessonDTO.getTypeLesson().getSubject().getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getDuration());
		  courseType.setDescription(lessonDTO.getTypeLesson().getSubject().getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getDescription());
		  typeDegreeCourse.setCourseType(courseType);
		  
		  DegreeCourse degreeCourse = new DegreeCourse();
		  degreeCourse.setIddegreeCourse(lessonDTO.getTypeLesson().getSubject().getDegreecourseDTO().getIdcourse());
		  degreeCourse.setTypeDegreeCourse(typeDegreeCourse);
		  AcademicYear academicYear = new AcademicYear();
		  academicYear.setIdacademicYear(lessonDTO.getTypeLesson().getSubject().getDegreecourseDTO().getAcademicYear().getIdacademicYear());
		  academicYear.setYear(lessonDTO.getTypeLesson().getSubject().getDegreecourseDTO().getAcademicYear().getYear());
		  degreeCourse.setAcademicYear(academicYear);
		 
		  TypeSubject typeSubject = new TypeSubject();
		  typeSubject.setIdtypeSubject(lessonDTO.getTypeLesson().getSubject().getTypeSubjectDTO().getIdtypeSubject());
		  typeSubject.setName(lessonDTO.getTypeLesson().getSubject().getTypeSubjectDTO().getName());
		  
		  Subject subject = new Subject();
		  subject.setIdsubject(lessonDTO.getTypeLesson().getSubject().getId());
		  subject.setDegreeCourse(degreeCourse);
		  subject.setTypeSubject(typeSubject);
		  
		  /*TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
		  typeLessonDTO.seteSubject(subjectDTO);*/
		 
		  Lesson lesson = new Lesson();
		  try {
			  lesson.setIdlesson(lessonDTO.getIdlesson());
		  } catch (Exception e) {
		   // TODO: handle exception
		  }
		  lesson.setClassroom(classroom);
		  lesson.setStart(lessonDTO.getStart());
		  lesson.setEnd(lessonDTO.getEnd());
		  //lesson.setSubject(subject);
		 
		  Lesson newLesson = lessonRepository.save(lesson);
		  
		  BuildingDTO buildingDTO = new BuildingDTO();
		  buildingDTO.setId(newLesson.getClassroom().getBuilding().getIdbuilding());
		  buildingDTO.setName(newLesson.getClassroom().getBuilding().getName());
		  buildingDTO.setAddress(newLesson.getClassroom().getBuilding().getAddress());
		  buildingDTO.setLat(newLesson.getClassroom().getBuilding().getLat());
		  buildingDTO.setLng(newLesson.getClassroom().getBuilding().getLng());
		  
		  ClassroomDTO classroomDTO = new ClassroomDTO();
		  classroomDTO.setId(newLesson.getClassroom().getIdclassroom());
		  classroomDTO.setName(newLesson.getClassroom().getName());
		  classroomDTO.setSeats(newLesson.getClassroom().getSeats());
		  classroomDTO.setLat(newLesson.getClassroom().getLat());
		  classroomDTO.setLng(newLesson.getClassroom().getLng());
		  classroomDTO.setBuilding(buildingDTO);
		  
		  TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
		  typeDegreeCourseDTO.setIdtypeDegreeCourse(newLesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
		  typeDegreeCourseDTO.setName(newLesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
		  
		  CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
		  courseTypeDTO.setIdcourseType(newLesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
		  courseTypeDTO.setDescription(newLesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
		  courseTypeDTO.setCfu(newLesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		  courseTypeDTO.setDuration(newLesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		  typeDegreeCourseDTO.setCourseType(courseTypeDTO);
		  
		  DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
		  degreeCourseDTO.setIdcourse(newLesson.getTypeLesson().getSubject().getDegreeCourse().getIddegreeCourse());
		  degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
		  degreeCourseDTO.setCfu(newLesson.getTypeLesson().getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		  AcademicYearDTO academicYearDTO = new AcademicYearDTO();
		  academicYearDTO.setIdacademicYear(newLesson.getTypeLesson().getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
		  academicYearDTO.setYear(newLesson.getTypeLesson().getSubject().getDegreeCourse().getAcademicYear().getYear());
		  degreeCourseDTO.setAcademicYear(academicYearDTO);
		  
		  TeacherDTO teacherDTO = new TeacherDTO();
		  teacherDTO.setIdteacher(newLesson.getTypeLesson().getSubject().getTeacher().getIduser());
		  teacherDTO.setName(newLesson.getTypeLesson().getSubject().getTeacher().getUser().getName());
		  teacherDTO.setSurname(newLesson.getTypeLesson().getSubject().getTeacher().getUser().getSurname());
		
		  SubjectDTO subjectDTO = new SubjectDTO();
		  subjectDTO.setId(newLesson.getTypeLesson().getSubject().getIdsubject());
		  subjectDTO.setName(newLesson.getTypeLesson().getSubject().getTypeSubject().getName());
		  subjectDTO.setDescription(newLesson.getTypeLesson().getSubject().getTypeSubject().getDescription());
		  subjectDTO.setTeacherDTO(teacherDTO);
		  subjectDTO.setDegreecourseDTO(degreeCourseDTO);
		  
		  TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
		  typeLessonDTO.setSubject(subjectDTO);
		 
		  LessonDTO newLessonDTO = new LessonDTO();
		  
		  newLessonDTO.setIdlesson(newLesson.getIdlesson());
		  newLessonDTO.setStart(newLesson.getStart());
		  newLessonDTO.setEnd(newLesson.getEnd());
		  newLessonDTO.setClassroom(classroomDTO);
		  newLessonDTO.setTypeLesson(typeLessonDTO);
		  
		  return newLessonDTO;
		  
	 }
	
	@Transactional
	public List<TypeLessonDTO> getCurrentSchedulerByCourse(DegreeCourseDTO degreeCourseDTO, int idterm){
		List<TypeLesson> lessons = typeLessonRepository.getCurrentSchedulerByIDCourse(degreeCourseDTO.getIdcourse(), idterm);
		
		if(lessons == null) {
			return null;
		}
		
		List<TypeLessonDTO> typeLessonDTOs = new ArrayList<TypeLessonDTO>();
		for(int i=0; i<lessons.size(); i++) {
			TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
			typeLessonDTO.setIdtypeLesson(lessons.get(i).getIdtypeLesson());
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(lessons.get(i).getClassroom().getIdclassroom());
			classroomDTO.setName(lessons.get(i).getClassroom().getName());
			classroomDTO.setLat(lessons.get(i).getClassroom().getLat());
			classroomDTO.setLng(lessons.get(i).getClassroom().getLng());
			
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(lessons.get(i).getClassroom().getBuilding().getIdbuilding());
			buildingDTO.setName(lessons.get(i).getClassroom().getBuilding().getName());
			
			classroomDTO.setBuilding(buildingDTO);
			
			typeLessonDTO.setClassroom(classroomDTO);
			
			DayDTO dayDTO = new DayDTO();
			dayDTO.setIdDay(lessons.get(i).getDay().getIdday());
			dayDTO.setName(lessons.get(i).getDay().getName());
			
			typeLessonDTO.setDay(dayDTO);
			
			SchedulerDTO schedulerDTO = new SchedulerDTO();
			schedulerDTO.setIdScheduler(lessons.get(i).getScheduler().getIdscheduler());
			
			TermDTO termDTO = new TermDTO();
			termDTO.setIdterm(lessons.get(i).getScheduler().getTerm().getIdterm());
			termDTO.setStart(lessons.get(i).getScheduler().getTerm().getStart());
			termDTO.setEnd(lessons.get(i).getScheduler().getTerm().getEnd());
			
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(lessons.get(i).getScheduler().getTerm().getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(lessons.get(i).getScheduler().getTerm().getAcademicYear().getYear());
			
			termDTO.setAcademicYear(academicYearDTO);
			
			schedulerDTO.setTerm(termDTO);
			
			typeLessonDTO.setScheduler(schedulerDTO);
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(lessons.get(i).getSubject().getIdsubject());
			subjectDTO.setName(lessons.get(i).getSubject().getTypeSubject().getName());
			
			typeLessonDTO.setSubject(subjectDTO);
			typeLessonDTO.setStart(new java.sql.Time(lessons.get(i).getStart().getTime()));
			typeLessonDTO.setEnd(new java.sql.Time(lessons.get(i).getEnd().getTime()));
			
			typeLessonDTOs.add(typeLessonDTO);
		}
		return typeLessonDTOs;
	}
	
	@Transactional
	public List<TypeLessonDTO> getCurrentSchedulerTeacher(int idteacher){
		List<TypeLesson> lessons = typeLessonRepository.getCurrentSchedulerTeacher(idteacher);
		
		if(lessons == null) {
			return null;
		}
		
		List<TypeLessonDTO> typeLessonDTOs = new ArrayList<TypeLessonDTO>();
		for(int i=0; i<lessons.size(); i++) {
			TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
			typeLessonDTO.setIdtypeLesson(lessons.get(i).getIdtypeLesson());
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(lessons.get(i).getClassroom().getIdclassroom());
			classroomDTO.setName(lessons.get(i).getClassroom().getName());
			classroomDTO.setLat(lessons.get(i).getClassroom().getLat());
			classroomDTO.setLng(lessons.get(i).getClassroom().getLng());
			
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(lessons.get(i).getClassroom().getBuilding().getIdbuilding());
			buildingDTO.setName(lessons.get(i).getClassroom().getBuilding().getName());
			
			classroomDTO.setBuilding(buildingDTO);
			
			typeLessonDTO.setClassroom(classroomDTO);
			
			DayDTO dayDTO = new DayDTO();
			dayDTO.setIdDay(lessons.get(i).getDay().getIdday());
			dayDTO.setName(lessons.get(i).getDay().getName());
			
			typeLessonDTO.setDay(dayDTO);
			
			SchedulerDTO schedulerDTO = new SchedulerDTO();
			schedulerDTO.setIdScheduler(lessons.get(i).getScheduler().getIdscheduler());
			
			TermDTO termDTO = new TermDTO();
			termDTO.setIdterm(lessons.get(i).getScheduler().getTerm().getIdterm());
			termDTO.setStart(lessons.get(i).getScheduler().getTerm().getStart());
			termDTO.setEnd(lessons.get(i).getScheduler().getTerm().getEnd());
			
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(lessons.get(i).getScheduler().getTerm().getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(lessons.get(i).getScheduler().getTerm().getAcademicYear().getYear());
			
			termDTO.setAcademicYear(academicYearDTO);
			
			schedulerDTO.setTerm(termDTO);
			
			typeLessonDTO.setScheduler(schedulerDTO);
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(lessons.get(i).getSubject().getIdsubject());
			subjectDTO.setName(lessons.get(i).getSubject().getTypeSubject().getName());
			
			typeLessonDTO.setSubject(subjectDTO);
			typeLessonDTO.setStart(new java.sql.Time(lessons.get(i).getStart().getTime()));
			typeLessonDTO.setEnd(new java.sql.Time(lessons.get(i).getEnd().getTime()));
			
			typeLessonDTOs.add(typeLessonDTO);
		}
		return typeLessonDTOs;
	}
	
	public void saveFeedback(int idlesson, FeedbackDTO feedbackDTO) {
		
		User user = new User();
		user.setIduser(feedbackDTO.getUser().getIduser());
		
		Feedback feedback = new Feedback();
		feedback.setStars(feedbackDTO.getStars());
		feedback.setDate(feedbackDTO.getDate());
		feedback.setUser(user);
		
		Feedback newFeed = this.feedbackRepository.save(feedback);
		
		Lesson lesson = new Lesson();
		lesson.setIdlesson(idlesson);
		
		FeedbackLessonId id = new FeedbackLessonId();
		id.setIdfeedback(newFeed.getIdfeedback());
		id.setIdlesson(idlesson);
		FeedbackLesson feedLesson = new FeedbackLesson();
		feedLesson.setId(id);
		feedLesson.setDescription(feedbackDTO.getDescription());
		
		FeedbackLesson newFeedLesson = this.feedbackLessonRepository.save(feedLesson);
		
		try {
			fcmService.newFeedBackLesson(idlesson);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	public List<FeedbackDTO> getFeedback(int idlesson) {
		List<FeedbackLesson> feedbacks = feedbackLessonRepository.getFeedbackLesson(idlesson);
		List<FeedbackDTO> feedbackDTOs = new ArrayList<FeedbackDTO>();
		for(int i=0; i<feedbacks.size(); i++) {
			UserDTO user = new UserDTO();
			user.setIduser(feedbacks.get(i).getFeedback().getUser().getIduser());
			
			FeedbackDTO feedbackDTO = new FeedbackDTO();
			feedbackDTO.setId(feedbacks.get(i).getId().getIdfeedback());
			feedbackDTO.setDescription(feedbacks.get(i).getDescription());
			feedbackDTO.setDate(feedbacks.get(i).getFeedback().getDate());
			feedbackDTO.setStars(feedbacks.get(i).getFeedback().getStars());
			feedbackDTO.setUser(user);
			feedbackDTOs.add(feedbackDTO);
		}
		
		return feedbackDTOs;
	}
	
	public List<LessonDTO> searchLessons(int idcourse, int idterm, int idsubject, String from, String to) throws ParseException {
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<Lesson> lessons = lessonRepository.searchLessons(idcourse, idterm, idsubject, format.parse(from), format.parse(to));
		
		List<LessonDTO> lessonDTOs = new ArrayList<LessonDTO>();
		
		for (int i=0; i<lessons.size(); i++) {
			LessonDTO lessonDTO = new LessonDTO();
			lessonDTO.setIdlesson(lessons.get(i).getIdlesson());
			lessonDTO.setStart(lessons.get(i).getStart());
			lessonDTO.setEnd(lessons.get(i).getEnd());
			
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(lessons.get(i).getClassroom().getBuilding().getIdbuilding());
			buildingDTO.setName(lessons.get(i).getClassroom().getBuilding().getName());
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(lessons.get(i).getClassroom().getIdclassroom());
			classroomDTO.setName(lessons.get(i).getClassroom().getName());
			classroomDTO.setBuilding(buildingDTO);
			
			lessonDTO.setClassroom(classroomDTO);
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(lessons.get(i).getTypeLesson().getSubject().getIdsubject());
			subjectDTO.setName(lessons.get(i).getTypeLesson().getSubject().getTypeSubject().getName());
			
			TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
			typeLessonDTO.setIdtypeLesson(lessons.get(i).getTypeLesson().getIdtypeLesson());
			typeLessonDTO.setSubject(subjectDTO);
			
			lessonDTO.setTypeLesson(typeLessonDTO);
			
			lessonDTOs.add(lessonDTO);
		}
		
		return lessonDTOs;
		
	}
	
public List<LessonDTO> searchTeacherLessons(int idsubject, String from, String to) throws ParseException {
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<Lesson> lessons = lessonRepository.searchTeacherLessons(idsubject, format.parse(from), format.parse(to));
		
		List<LessonDTO> lessonDTOs = new ArrayList<LessonDTO>();
		
		for (int i=0; i<lessons.size(); i++) {
			LessonDTO lessonDTO = new LessonDTO();
			lessonDTO.setIdlesson(lessons.get(i).getIdlesson());
			lessonDTO.setStart(lessons.get(i).getStart());
			lessonDTO.setEnd(lessons.get(i).getEnd());
			
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(lessons.get(i).getClassroom().getBuilding().getIdbuilding());
			buildingDTO.setName(lessons.get(i).getClassroom().getBuilding().getName());
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(lessons.get(i).getClassroom().getIdclassroom());
			classroomDTO.setName(lessons.get(i).getClassroom().getName());
			classroomDTO.setBuilding(buildingDTO);
			
			lessonDTO.setClassroom(classroomDTO);
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(lessons.get(i).getTypeLesson().getSubject().getIdsubject());
			subjectDTO.setName(lessons.get(i).getTypeLesson().getSubject().getTypeSubject().getName());
			
			TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
			typeLessonDTO.setIdtypeLesson(lessons.get(i).getTypeLesson().getIdtypeLesson());
			typeLessonDTO.setSubject(subjectDTO);
			
			lessonDTO.setTypeLesson(typeLessonDTO);
			
			lessonDTOs.add(lessonDTO);
		}
		
		return lessonDTOs;
		
	}
	
	public List<LessonDTO> getAllLessonsByCourseAndTerm(int idcourse, int idterm) {
		
		List<Lesson> lessons = lessonRepository.getAllLessonsByCourseAndTerm(idcourse, idterm);
		
		List<LessonDTO> lessonDTOs = new ArrayList<LessonDTO>();
		
		for (int i=0; i<lessons.size(); i++) {
			LessonDTO lessonDTO = new LessonDTO();
			lessonDTO.setIdlesson(lessons.get(i).getIdlesson());
			lessonDTO.setStart(lessons.get(i).getStart());
			lessonDTO.setEnd(lessons.get(i).getEnd());
			
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(lessons.get(i).getClassroom().getBuilding().getIdbuilding());
			buildingDTO.setName(lessons.get(i).getClassroom().getBuilding().getName());
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(lessons.get(i).getClassroom().getIdclassroom());
			classroomDTO.setName(lessons.get(i).getClassroom().getName());
			classroomDTO.setBuilding(buildingDTO);
			
			lessonDTO.setClassroom(classroomDTO);
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(lessons.get(i).getTypeLesson().getSubject().getIdsubject());
			subjectDTO.setName(lessons.get(i).getTypeLesson().getSubject().getTypeSubject().getName());
			
			TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
			typeLessonDTO.setIdtypeLesson(lessons.get(i).getTypeLesson().getIdtypeLesson());
			typeLessonDTO.setSubject(subjectDTO);
			
			lessonDTO.setTypeLesson(typeLessonDTO);
			
			lessonDTOs.add(lessonDTO);
		}
		
		return lessonDTOs;
		
	}
	
public List<LessonDTO> getTodayLessons(int iduser) {
		
		List<Lesson> lessons = lessonRepository.getTodayLessons(iduser);
		
		List<LessonDTO> lessonDTOs = new ArrayList<LessonDTO>();
		
		for (int i=0; i<lessons.size(); i++) {
			LessonDTO lessonDTO = new LessonDTO();
			lessonDTO.setIdlesson(lessons.get(i).getIdlesson());
			lessonDTO.setStart(lessons.get(i).getStart());
			lessonDTO.setEnd(lessons.get(i).getEnd());
			
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(lessons.get(i).getClassroom().getBuilding().getIdbuilding());
			buildingDTO.setName(lessons.get(i).getClassroom().getBuilding().getName());
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(lessons.get(i).getClassroom().getIdclassroom());
			classroomDTO.setName(lessons.get(i).getClassroom().getName());
			classroomDTO.setBuilding(buildingDTO);
			
			lessonDTO.setClassroom(classroomDTO);
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(lessons.get(i).getTypeLesson().getSubject().getIdsubject());
			subjectDTO.setName(lessons.get(i).getTypeLesson().getSubject().getTypeSubject().getName());
			
			TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
			typeLessonDTO.setIdtypeLesson(lessons.get(i).getTypeLesson().getIdtypeLesson());
			typeLessonDTO.setSubject(subjectDTO);
			
			lessonDTO.setTypeLesson(typeLessonDTO);
			
			lessonDTOs.add(lessonDTO);
		}
		
		return lessonDTOs;
		
	}

public List<LessonDTO> getTeacherTodayLessons(int iduser) {
	
	List<Lesson> lessons = lessonRepository.getTeacherTodayLessons(iduser);
	
	List<LessonDTO> lessonDTOs = new ArrayList<LessonDTO>();
	
	for (int i=0; i<lessons.size(); i++) {
		LessonDTO lessonDTO = new LessonDTO();
		lessonDTO.setIdlesson(lessons.get(i).getIdlesson());
		lessonDTO.setStart(lessons.get(i).getStart());
		lessonDTO.setEnd(lessons.get(i).getEnd());
		
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId(lessons.get(i).getClassroom().getBuilding().getIdbuilding());
		buildingDTO.setName(lessons.get(i).getClassroom().getBuilding().getName());
		
		ClassroomDTO classroomDTO = new ClassroomDTO();
		classroomDTO.setId(lessons.get(i).getClassroom().getIdclassroom());
		classroomDTO.setName(lessons.get(i).getClassroom().getName());
		classroomDTO.setBuilding(buildingDTO);
		
		lessonDTO.setClassroom(classroomDTO);
		
		SubjectDTO subjectDTO = new SubjectDTO();
		subjectDTO.setId(lessons.get(i).getTypeLesson().getSubject().getIdsubject());
		subjectDTO.setName(lessons.get(i).getTypeLesson().getSubject().getTypeSubject().getName());
		
		TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
		typeLessonDTO.setIdtypeLesson(lessons.get(i).getTypeLesson().getIdtypeLesson());
		typeLessonDTO.setSubject(subjectDTO);
		
		lessonDTO.setTypeLesson(typeLessonDTO);
		
		lessonDTOs.add(lessonDTO);
	}
	
	return lessonDTOs;
	
}
	
	@Transactional
	public void edit(List<LessonDTO> lessonDTOs) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		for(int i=0; i<lessonDTOs.size(); i++) {
			
			Lesson oldLess = lessonRepository.getOne(lessonDTOs.get(i).getIdlesson());
			int oldroom = oldLess.getClassroom().getIdclassroom();
			Date oldstart = oldLess.getStart();
			Date oldend = oldLess.getEnd();
			
			Classroom classroom = new Classroom();
			classroom.setIdclassroom(lessonDTOs.get(i).getClassroom().getId());
			classroom.setName(lessonDTOs.get(i).getClassroom().getName());
			
			TypeLesson typeLesson = new TypeLesson();
			typeLesson.setIdtypeLesson(lessonDTOs.get(i).getTypeLesson().getIdtypeLesson());
		
			Lesson lesson = new Lesson();
			lesson.setIdlesson(lessonDTOs.get(i).getIdlesson());
			lesson.setClassroom(classroom);
			lesson.setTypeLesson(typeLesson);
			lesson.setStart(lessonDTOs.get(i).getStart());
			lesson.setEnd(lessonDTOs.get(i).getEnd());
		
			
			
			Lesson newLess = lessonRepository.save(lesson);
			
			if(oldroom != newLess.getClassroom().getIdclassroom() && (!sdf2.format(oldstart).equals(sdf2.format(newLess.getStart())) || !sdf2.format(oldend).equals(sdf2.format(newLess.getEnd())))) {
				try {
					fcmService.lessonChanged(newLess, "both");
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				if(oldroom != newLess.getClassroom().getIdclassroom()) {
					try {
						fcmService.lessonChanged(newLess, "room");
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (!sdf2.format(oldstart).equals(sdf2.format(newLess.getStart())) || !sdf2.format(oldend).equals(sdf2.format(newLess.getEnd()))) {
					try {
						fcmService.lessonChanged(newLess, "hour");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			
						
			
		}
	}
	
	 @Transactional
	 public void delete(int id) throws LessonNotFoundException {
	  try {
	   lessonRepository.deleteById(id);
	  } catch (Exception e) {
	   throw new LessonNotFoundException();
	  }
	 }
	 
	 /*@Transactional
	 public List<TypeLessonDTO> getCurrentSchedulerByCourse(DegreeCourseDTO degreeCourseDTO, int idterm){
	  List<TypeLesson> lessons = typeLessonRepository.getCurrentSchedulerByIDCourse(degreeCourseDTO.getIdcourse(), idterm);
	  
	  if(lessons == null) {
	   return null;
	  }
	  
	  List<TypeLessonDTO> typeLessonDTOs = new ArrayList<TypeLessonDTO>();
	  for(int i=0; i<lessons.size(); i++) {
	   TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
	   typeLessonDTO.setIdtypeLesson(lessons.get(i).getIdtypeLesson());
	   
	   ClassroomDTO classroomDTO = new ClassroomDTO();
	   classroomDTO.setId(lessons.get(i).getClassroom().getIdclassroom());
	   classroomDTO.setName(lessons.get(i).getClassroom().getName());
	   
	   BuildingDTO buildingDTO = new BuildingDTO();
	   buildingDTO.setId(lessons.get(i).getClassroom().getBuilding().getIdbuilding());
	   buildingDTO.setName(lessons.get(i).getClassroom().getBuilding().getName());
	   
	   classroomDTO.setBuilding(buildingDTO);
	   
	   typeLessonDTO.setClassroom(classroomDTO);
	   
	   DayDTO dayDTO = new DayDTO();
	   dayDTO.setIdDay(lessons.get(i).getDay().getIdday());
	   dayDTO.setName(lessons.get(i).getDay().getName());
	   
	   typeLessonDTO.setDay(dayDTO);
	   
	   SchedulerDTO schedulerDTO = new SchedulerDTO();
	   schedulerDTO.setIdScheduler(lessons.get(i).getScheduler().getIdscheduler());
	   
	   TermDTO termDTO = new TermDTO();
	   termDTO.setIdterm(lessons.get(i).getScheduler().getTerm().getIdterm());
	   termDTO.setStart(lessons.get(i).getScheduler().getTerm().getStart());
	   termDTO.setEnd(lessons.get(i).getScheduler().getTerm().getEnd());
	   
	   AcademicYearDTO academicYearDTO = new AcademicYearDTO();
	   academicYearDTO.setIdacademicYear(lessons.get(i).getScheduler().getTerm().getAcademicYear().getIdacademicYear());
	   academicYearDTO.setYear(lessons.get(i).getScheduler().getTerm().getAcademicYear().getYear());
	   
	   termDTO.setAcademicYear(academicYearDTO);
	   
	   schedulerDTO.setTerm(termDTO);
	   
	   typeLessonDTO.setScheduler(schedulerDTO);
	   
	   SubjectDTO subjectDTO = new SubjectDTO();
	   subjectDTO.setId(lessons.get(i).getSubject().getIdsubject());
	   subjectDTO.setName(lessons.get(i).getSubject().getTypeSubject().getName());
	   
	   typeLessonDTO.setSubject(subjectDTO);
	   typeLessonDTO.setStart(new java.sql.Time(lessons.get(i).getStart().getTime()));
	   typeLessonDTO.setEnd(new java.sql.Time(lessons.get(i).getEnd().getTime()));
	   
	   typeLessonDTOs.add(typeLessonDTO);
	  }
	  return typeLessonDTOs;
	 }*/
	 
}
