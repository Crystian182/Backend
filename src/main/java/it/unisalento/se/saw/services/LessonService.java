package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.ILessonService;
import it.unisalento.se.saw.domain.AcademicYear;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Lesson;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.domain.TypeLesson;
import it.unisalento.se.saw.domain.TypeSubject;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DayDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.SchedulerDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.exceptions.LessonNotFoundException;
import it.unisalento.se.saw.repositories.LessonRepository;
import it.unisalento.se.saw.repositories.TypeLessonRepository;


@Service
public class LessonService implements ILessonService{

	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	TypeLessonRepository typeLessonRepository;

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
				teacherDTO.setIdteacher(lessons.get(i).getSubject().getTeacher().getIduser());
				teacherDTO.setName(lessons.get(i).getSubject().getTeacher().getUser().getName());
				teacherDTO.setSurname(lessons.get(i).getSubject().getTeacher().getUser().getSurname());
				
				TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
				typeDegreeCourseDTO.setIdtypeDegreeCourse(lessons.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
				typeDegreeCourseDTO.setName(lessons.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
				
				CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
				courseTypeDTO.setIdcourseType(lessons.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
				courseTypeDTO.setDescription(lessons.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
				courseTypeDTO.setCfu(lessons.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				courseTypeDTO.setDuration(lessons.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				typeDegreeCourseDTO.setCourseType(courseTypeDTO);
				
				DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
				degreeCourseDTO.setIdcourse(lessons.get(i).getSubject().getDegreeCourse().getIddegreeCourse());
				degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
				AcademicYearDTO academicYearDTO = new AcademicYearDTO();
				academicYearDTO.setIdacademicYear(lessons.get(i).getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
				academicYearDTO.setYear(lessons.get(i).getSubject().getDegreeCourse().getAcademicYear().getYear());
				degreeCourseDTO.setAcademicYear(academicYearDTO);
				degreeCourseDTO.setCfu(lessons.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(lessons.get(i).getSubject().getIdsubject());
				subjectDTO.setName(lessons.get(i).getSubject().getTypeSubject().getName());
				subjectDTO.setDescription(lessons.get(i).getSubject().getTypeSubject().getDescription());
				subjectDTO.setCfu(lessons.get(i).getSubject().getCfu());
				subjectDTO.setDegreecourseDTO(degreeCourseDTO);
				subjectDTO.setTeacherDTO(teacherDTO);
				
				LessonDTO lessonDTO = new LessonDTO();
				
				lessonDTO.setIdlesson(lessons.get(i).getIdlesson());
				lessonDTO.setStart(lessons.get(i).getStart());
				lessonDTO.setEnd(lessons.get(i).getEnd());
				lessonDTO.setClassroom(classroomDTO);
				lessonDTO.setSubject(subjectDTO);
		
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
			typeDegreeCourseDTO.setIdtypeDegreeCourse(lesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
			typeDegreeCourseDTO.setName(lesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
			
			CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
			courseTypeDTO.setIdcourseType(lesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
			courseTypeDTO.setDescription(lesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
			courseTypeDTO.setCfu(lesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			courseTypeDTO.setDuration(lesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			typeDegreeCourseDTO.setCourseType(courseTypeDTO);
			
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(lesson.getSubject().getDegreeCourse().getIddegreeCourse());
			degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
			degreeCourseDTO.setCfu(lesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(lesson.getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(lesson.getSubject().getDegreeCourse().getAcademicYear().getYear());
			degreeCourseDTO.setAcademicYear(academicYearDTO);
			
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(lesson.getSubject().getTeacher().getIduser());
			teacherDTO.setName(lesson.getSubject().getTeacher().getUser().getName());
			teacherDTO.setSurname(lesson.getSubject().getTeacher().getUser().getSurname());
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(lesson.getSubject().getIdsubject());
			subjectDTO.setName(lesson.getSubject().getTypeSubject().getName());
			subjectDTO.setDescription(lesson.getSubject().getTypeSubject().getDescription());
			subjectDTO.setCfu(lesson.getSubject().getCfu());
			subjectDTO.setDegreecourseDTO(degreeCourseDTO);
			subjectDTO.setTeacherDTO(teacherDTO);
		
			LessonDTO lessonDTO = new LessonDTO();
			
			lessonDTO.setIdlesson(lesson.getIdlesson());
			lessonDTO.setStart(lesson.getStart());
			lessonDTO.setEnd(lesson.getEnd());
			lessonDTO.setClassroom(classroomDTO);
			lessonDTO.setSubject(subjectDTO);
			
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
		typeDegreeCourse.setIdtypeDegreeCourse(lessonDTO.getSubject().getDegreecourseDTO().getTypeDegreeCourse().getIdtypeDegreeCourse());
		typeDegreeCourse.setName(lessonDTO.getSubject().getDegreecourseDTO().getTypeDegreeCourse().getName());
		
		CourseType courseType = new CourseType();
		courseType.setIdcourseType(lessonDTO.getSubject().getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getIdcourseType());
		courseType.setCfu(lessonDTO.getSubject().getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getCfu());
		courseType.setDuration(lessonDTO.getSubject().getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getDuration());
		courseType.setDescription(lessonDTO.getSubject().getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getDescription());
		typeDegreeCourse.setCourseType(courseType);
		
		DegreeCourse degreeCourse = new DegreeCourse();
		degreeCourse.setIddegreeCourse(lessonDTO.getSubject().getDegreecourseDTO().getIdcourse());
		degreeCourse.setTypeDegreeCourse(typeDegreeCourse);
		AcademicYear academicYear = new AcademicYear();
		academicYear.setIdacademicYear(lessonDTO.getSubject().getDegreecourseDTO().getAcademicYear().getIdacademicYear());
		academicYear.setYear(lessonDTO.getSubject().getDegreecourseDTO().getAcademicYear().getYear());
		degreeCourse.setAcademicYear(academicYear);
	
		TypeSubject typeSubject = new TypeSubject();
		typeSubject.setIdtypeSubject(lessonDTO.getSubject().getTypeSubjectDTO().getIdtypeSubject());
		typeSubject.setName(lessonDTO.getSubject().getTypeSubjectDTO().getName());
		
		Subject subject = new Subject();
		subject.setIdsubject(lessonDTO.getSubject().getId());
		subject.setDegreeCourse(degreeCourse);
		subject.setTypeSubject(typeSubject);
	
		Lesson lesson = new Lesson();
		try {
			lesson.setIdlesson(lessonDTO.getIdlesson());
		} catch (Exception e) {
			// TODO: handle exception
		}
		lesson.setClassroom(classroom);
		lesson.setStart(lessonDTO.getStart());
		lesson.setEnd(lessonDTO.getEnd());
		lesson.setSubject(subject);
	
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
		typeDegreeCourseDTO.setIdtypeDegreeCourse(newLesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
		typeDegreeCourseDTO.setName(newLesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
		
		CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
		courseTypeDTO.setIdcourseType(newLesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
		courseTypeDTO.setDescription(newLesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
		courseTypeDTO.setCfu(newLesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		courseTypeDTO.setDuration(newLesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		typeDegreeCourseDTO.setCourseType(courseTypeDTO);
		
		DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
		degreeCourseDTO.setIdcourse(newLesson.getSubject().getDegreeCourse().getIddegreeCourse());
		degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
		degreeCourseDTO.setCfu(newLesson.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		AcademicYearDTO academicYearDTO = new AcademicYearDTO();
		academicYearDTO.setIdacademicYear(newLesson.getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
		academicYearDTO.setYear(newLesson.getSubject().getDegreeCourse().getAcademicYear().getYear());
		degreeCourseDTO.setAcademicYear(academicYearDTO);
		
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setIdteacher(newLesson.getSubject().getTeacher().getIduser());
		teacherDTO.setName(newLesson.getSubject().getTeacher().getUser().getName());
		teacherDTO.setSurname(newLesson.getSubject().getTeacher().getUser().getSurname());

		SubjectDTO subjectDTO = new SubjectDTO();
		subjectDTO.setId(newLesson.getSubject().getIdsubject());
		subjectDTO.setName(newLesson.getSubject().getTypeSubject().getName());
		subjectDTO.setDescription(newLesson.getSubject().getTypeSubject().getDescription());
		subjectDTO.setTeacherDTO(teacherDTO);
		subjectDTO.setDegreecourseDTO(degreeCourseDTO);
	
		LessonDTO newLessonDTO = new LessonDTO();
		
		newLessonDTO.setIdlesson(newLesson.getIdlesson());
		newLessonDTO.setStart(newLesson.getStart());
		newLessonDTO.setEnd(newLesson.getEnd());
		newLessonDTO.setClassroom(classroomDTO);
		newLessonDTO.setSubject(subjectDTO);
		
		return newLessonDTO;
		
	}

	@Transactional
	public void delete(int id) throws LessonNotFoundException {
		try {
			lessonRepository.deleteById(id);
		} catch (Exception e) {
			throw new LessonNotFoundException();
		}
	}
	
	@Transactional
	public List<TypeLessonDTO> getCurrentSchedulerByCourse(DegreeCourseDTO degreeCourseDTO){
		List<TypeLesson> lessons = typeLessonRepository.getCurrentSchedulerByIDCourse(degreeCourseDTO.getIdcourse());
		
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
			schedulerDTO.setName(lessons.get(i).getScheduler().getName());
			
			TermDTO termDTO = new TermDTO();
			termDTO.setIdterm(lessons.get(i).getScheduler().getTerm().getIdterm());
			termDTO.setNumber(lessons.get(i).getScheduler().getTerm().getNumber());
			termDTO.setStart(lessons.get(i).getScheduler().getTerm().getStart());
			termDTO.setEnd(lessons.get(i).getScheduler().getTerm().getEnd());
			
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(lessons.get(i).getScheduler().getTerm().getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(lessons.get(i).getScheduler().getTerm().getAcademicYear().getYear());
			
			termDTO.setAcademicYear(academicYearDTO);
			
			schedulerDTO.setTerm(termDTO);
			
			typeLessonDTO.setScheduler(schedulerDTO);
			
			TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
			typeSubjectDTO.setIdtypeSubject(lessons.get(i).getTypeSubject().getIdtypeSubject());
			typeSubjectDTO.setName(lessons.get(i).getTypeSubject().getName());
			
			typeLessonDTO.setTypeSubject(typeSubjectDTO);
			typeLessonDTO.setStart(new java.sql.Time(lessons.get(i).getStart().getTime()));
			typeLessonDTO.setEnd(new java.sql.Time(lessons.get(i).getEnd().getTime()));
			
			typeLessonDTOs.add(typeLessonDTO);
		}
		return typeLessonDTOs;
	}
	
}
