package it.unisalento.se.saw.restapi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.Iservices.ILessonService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Course;
import it.unisalento.se.saw.domain.Lesson;
import it.unisalento.se.saw.domain.Subjectofstudy;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.CourseDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.exceptions.LessonNotFoundException;

@RestController
@RequestMapping("/lesson")
public class LessonRestController {

	@Autowired
	ILessonService lessonService;
	
	public LessonRestController(){
		super();
	}
	
	public LessonRestController(ILessonService lessonService) {
		this.lessonService = lessonService;
	}
	
	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<LessonDTO> getAll(){
		List<Lesson> lessons = lessonService.getAll();
		List<LessonDTO> newLessonDTOs = new ArrayList<LessonDTO>();
		for(int i=0; i<lessons.size(); i++) {
			Lesson lesson = lessons.get(i);
			newLessonDTOs.add(this.EntityToDTO(lesson));
		}
		return newLessonDTOs;
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public LessonDTO getById(@PathVariable("id")int id) throws LessonNotFoundException{
		Lesson lesson = lessonService.getById(id);
		return this.EntityToDTO(lesson);
	}
	
	@PostMapping(value="/edit", produces=MediaType.APPLICATION_JSON_VALUE)
	public LessonDTO edit(@RequestBody LessonDTO lessonDTO) throws LessonNotFoundException{
		Lesson lesson = lessonService.edit(this.DTOToEntity(lessonDTO));
		return this.EntityToDTO(lesson);
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public LessonDTO save(@RequestBody LessonDTO lessonDTO){
		Lesson lesson = lessonService.save(this.DTOToEntity(lessonDTO));
		return this.EntityToDTO(lesson);
	}
	
	@PostMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws LessonNotFoundException{
		lessonService.delete(id);
	}	
	
	

	public LessonDTO EntityToDTO(Lesson lesson) {
		
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId(lesson.getClassroom().getBuilding().getIdbuilding());
		buildingDTO.setName(lesson.getClassroom().getBuilding().getName());
		buildingDTO.setAddress(lesson.getClassroom().getBuilding().getAddress());
		buildingDTO.setLatitude(lesson.getClassroom().getBuilding().getLatitude());
		buildingDTO.setLongitude(lesson.getClassroom().getBuilding().getLongitude());
		
		ClassroomDTO classroomDTO = new ClassroomDTO();
		classroomDTO.setId(lesson.getClassroom().getIdclassroom());
		classroomDTO.setName(lesson.getClassroom().getName());
		classroomDTO.setSeats(lesson.getClassroom().getSeats());
		classroomDTO.setLatitude(lesson.getClassroom().getLatitude());
		classroomDTO.setLongitude(lesson.getClassroom().getLongitude());
		classroomDTO.setBuilding(buildingDTO);
		
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setIdcourse(lesson.getSubjectofstudy().getCourse().getIdcourse());
		courseDTO.setName(lesson.getSubjectofstudy().getCourse().getName());
		courseDTO.setDescription(lesson.getSubjectofstudy().getCourse().getDescription());
		courseDTO.setYears(lesson.getSubjectofstudy().getCourse().getYears());
		
		SubjectDTO subjectofstudyDTO = new SubjectDTO();
		subjectofstudyDTO.setId(lesson.getSubjectofstudy().getIdsubjectofstudy());
		subjectofstudyDTO.setName(lesson.getSubjectofstudy().getName());
		subjectofstudyDTO.setDescription(lesson.getSubjectofstudy().getDescription());
		/*subjectofstudyDTO.setTeacherUserIduser(lesson.getSubjectofstudy().getTeacherUserIduser());
		subjectofstudyDTO.setLessons(lesson.getSubjectofstudy().getLessons());
		subjectofstudyDTO.setExams(lesson.getSubjectofstudy().getExams());
		subjectofstudyDTO.setCourse(courseDTO);
		subjectofstudyDTO.setUsers(lesson.getSubjectofstudy().getUsers());
		subjectofstudyDTO.setPublicchats(lesson.getSubjectofstudy().getPublicchats());*/
	
		LessonDTO lessonDTO = new LessonDTO();
		
		lessonDTO.setIdlesson(lesson.getIdlesson());
		lessonDTO.setDay(lesson.getDay());
		lessonDTO.setStart(lesson.getStart());
		lessonDTO.setEnd(lesson.getEnd());
		lessonDTO.setFeedbacklessons(lesson.getFeedbacklessons());
		lessonDTO.setFiles(lesson.getFiles());
		lessonDTO.setClassroom(classroomDTO);
		lessonDTO.setSubjectofstudy(subjectofstudyDTO);

		return lessonDTO;
	}
	
	
	public Lesson DTOToEntity(LessonDTO lessonDTO) {	
		
		Building building = new Building();
		building.setIdbuilding(lessonDTO.getClassroom().getBuilding().getId());
		building.setName(lessonDTO.getClassroom().getBuilding().getName());
		building.setAddress(lessonDTO.getClassroom().getBuilding().getAddress());
		building.setLatitude(lessonDTO.getClassroom().getBuilding().getLatitude());
		building.setLongitude(lessonDTO.getClassroom().getBuilding().getLongitude());
		
		Classroom classroom = new Classroom();
		classroom.setIdclassroom(lessonDTO.getClassroom().getId());
		classroom.setName(lessonDTO.getClassroom().getName());
		classroom.setSeats(lessonDTO.getClassroom().getSeats());
		classroom.setLatitude(lessonDTO.getClassroom().getLatitude());
		classroom.setLongitude(lessonDTO.getClassroom().getLongitude());
		classroom.setBuilding(building);
		
		Course course = new Course();
		course.setIdcourse(lessonDTO.getSubjectofstudy().getCourseDTO().getIdcourse());
		course.setName(lessonDTO.getSubjectofstudy().getCourseDTO().getName());
		course.setDescription(lessonDTO.getSubjectofstudy().getCourseDTO().getDescription());
		course.setYears(lessonDTO.getSubjectofstudy().getCourseDTO().getYears());
		
		Subjectofstudy subjectofstudy = new Subjectofstudy();
		subjectofstudy.setIdsubjectofstudy(lessonDTO.getSubjectofstudy().getId());
		subjectofstudy.setName(lessonDTO.getSubjectofstudy().getName());
		subjectofstudy.setDescription(lessonDTO.getSubjectofstudy().getDescription());
		/*subjectofstudy.setExams(lessonDTO.getSubjectofstudy().getExams());
		subjectofstudy.setCourse(course);
		subjectofstudy.setLessons(lessonDTO.getSubjectofstudy().getLessons());
		subjectofstudy.setTeacherUserIduser(lessonDTO.getSubjectofstudy().getTeacherUserIduser());
		subjectofstudy.setUsers(lessonDTO.getSubjectofstudy().getUsers());
		subjectofstudy.setPublicchats(lessonDTO.getSubjectofstudy().getPublicchats());*/
	
		Lesson lesson = new Lesson();
		try {
			lesson.setIdlesson(lessonDTO.getIdlesson());
		} catch (Exception e) {
			// TODO: handle exception
		}
		lesson.setClassroom(classroom);
		lesson.setDay(lessonDTO.getDay());
		lesson.setStart(lessonDTO.getStart());
		lesson.setEnd(lessonDTO.getEnd());
		lesson.setFeedbacklessons(lessonDTO.getFeedbacklessons());
		lesson.setFiles(lessonDTO.getFiles());
		lesson.setSubjectofstudy(subjectofstudy);
	
		return lesson;
	}
	
	
}
