package it.unisalento.se.saw.restapi;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.Iservices.IExamService;
import it.unisalento.se.saw.dto.ExamDTO;
<<<<<<< HEAD
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;
=======
import it.unisalento.se.saw.dto.StudentDTO;
>>>>>>> 7292ba6b82995e81824c24c8d2fc8432d6f05098
import it.unisalento.se.saw.exceptions.ExamNotFoundException;

@RestController
@RequestMapping("/exam")
public class ExamRestController {

	@Autowired
	IExamService examService;
	
	public ExamRestController() {
		super();
	}
	
	public ExamRestController(IExamService examService) {
		this.examService = examService;
	}
	
	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ExamDTO> getAll(){
		return examService.getAll();

	}
	
	@GetMapping(value="/getAllByCourse/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ExamDTO> getAllByCourse(@PathVariable("id")int id) throws ExamNotFoundException {
		return examService.getAllByCourse(id);
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ExamDTO getById(@PathVariable("id")int id) throws ExamNotFoundException{
		return examService.getById(id);
	}
	
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public ExamDTO save(@RequestBody ExamDTO examDTO){
		return examService.save(examDTO);

	}
	
	@PostMapping(value="/subscribe/{idexam}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ExamDTO subscribe(@RequestBody StudentDTO studentDTO, @PathVariable("idexam")int idexam) throws ExamNotFoundException{
	
		return examService.subscribe(idexam, studentDTO); 
		
	}
	
	@PostMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws ExamNotFoundException{
		examService.delete(id);
	}

<<<<<<< HEAD
	public User DTOToEntity(UserDTO userDTO) {
		User user = new User();
		user.setIduser(userDTO.getId());
		user.setName(userDTO.getName());
		user.setSurname(userDTO.getSurname());
		return user;
	}
	
	
	public ExamDTO EntityToDTO(Exam exam) {
		
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId(exam.getClassroom().getBuilding().getIdbuilding());
		buildingDTO.setName(exam.getClassroom().getBuilding().getName());
		buildingDTO.setAddress(exam.getClassroom().getBuilding().getAddress());
		
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setIdcourse(exam.getSubjectofstudy().getCourse().getIdcourse());
		courseDTO.setName(exam.getSubjectofstudy().getCourse().getName());
		courseDTO.setDescription(exam.getSubjectofstudy().getCourse().getDescription());
		courseDTO.setYears(exam.getSubjectofstudy().getCourse().getYears());
		
		ClassroomDTO classroomDTO = new ClassroomDTO();
		classroomDTO.setId(exam.getClassroom().getIdclassroom());
		classroomDTO.setName(exam.getClassroom().getName());
		classroomDTO.setSeats(exam.getClassroom().getSeats());
		classroomDTO.setBuilding(buildingDTO);
		
		SubjectDTO subjectofstudyDTO = new SubjectDTO();
		subjectofstudyDTO.setId(exam.getSubjectofstudy().getIdsubjectofstudy());
		subjectofstudyDTO.setName(exam.getSubjectofstudy().getName());
		subjectofstudyDTO.setDescription(exam.getSubjectofstudy().getDescription());
		/*subjectofstudyDTO.setTeacherDTO(teacherDTO);
		subjectofstudyDTO.setCourseDTO(courseDTO);
		subjectofstudyDTO.setExams(exam.getSubjectofstudy().getExams());
		subjectofstudyDTO.setLessons(exam.getSubjectofstudy().getLessons());
		subjectofstudyDTO.setUsers(exam.getSubjectofstudy().getUsers());
		subjectofstudyDTO.setPublicchats(exam.getSubjectofstudy().getPublicchats());*/
		
		ExamDTO examDTO = new ExamDTO();
		
		examDTO.setIdexam(exam.getIdexam());
		examDTO.setClassroom(classroomDTO);
		examDTO.setSubjectofstudy(subjectofstudyDTO);
		examDTO.setDate(exam.getDate());
		examDTO.setType(exam.getType());
		examDTO.setStartregistration(exam.getStartregistration());
		examDTO.setEndregistration(exam.getEndregistration());
		
		return examDTO;
	}
	
	public Exam DTOToEntity(ExamDTO examDTO) {	
		
		Building building = new Building();
		building.setIdbuilding(examDTO.getClassroom().getBuilding().getId());
		building.setName(examDTO.getClassroom().getBuilding().getName());
		building.setAddress(examDTO.getClassroom().getBuilding().getAddress());
		building.setLatitude(examDTO.getClassroom().getBuilding().getLatitude());
		building.setLongitude(examDTO.getClassroom().getBuilding().getLongitude());
		
		Classroom classroom = new Classroom();
		classroom.setIdclassroom(examDTO.getClassroom().getId());
		classroom.setName(examDTO.getClassroom().getName());
		classroom.setSeats(examDTO.getClassroom().getSeats());
		classroom.setLatitude(examDTO.getClassroom().getLatitude());
		classroom.setLongitude(examDTO.getClassroom().getLongitude());
		classroom.setBuilding(building);
		
		Course course = new Course();
		course.setIdcourse(examDTO.getSubjectofstudy().getCourseDTO().getIdcourse());
		course.setName(examDTO.getSubjectofstudy().getCourseDTO().getName());
		course.setDescription(examDTO.getSubjectofstudy().getCourseDTO().getDescription());
		course.setYears(examDTO.getSubjectofstudy().getCourseDTO().getYears());
		
		Subjectofstudy subjectofstudy = new Subjectofstudy();
		subjectofstudy.setIdsubjectofstudy(examDTO.getSubjectofstudy().getId());
		subjectofstudy.setName(examDTO.getSubjectofstudy().getName());
		subjectofstudy.setDescription(examDTO.getSubjectofstudy().getDescription());
		/*subjectofstudy.setExams(examDTO.getSubjectofstudy().getExams());
		subjectofstudy.setCourse(course);
		subjectofstudy.setLessons(examDTO.getSubjectofstudy().getLessons());
		subjectofstudy.setTeacherUserIduser(examDTO.getSubjectofstudy().getTeacherUserIduser());
		subjectofstudy.setUsers(examDTO.getSubjectofstudy().getUsers());
		subjectofstudy.setPublicchats(examDTO.getSubjectofstudy().getPublicchats());*/
		
		Exam exam = new Exam();
		try {
			exam.setIdexam(examDTO.getIdexam());
		} catch (Exception e) {
			// TODO: handle exception
		}
		exam.setClassroom(classroom);
		exam.setDate(examDTO.getDate());
		exam.setStartregistration(examDTO.getStartregistration());
		exam.setEndregistration(examDTO.getEndregistration());
		exam.setSubjectofstudy(subjectofstudy);
		exam.setType(examDTO.getType());
		
		return exam;
	}
=======
>>>>>>> 7292ba6b82995e81824c24c8d2fc8432d6f05098
	
}
