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

import it.unisalento.se.saw.Iservices.ISubjectOfStudyService;
import it.unisalento.se.saw.Iservices.ITeacherService;
import it.unisalento.se.saw.domain.Course;
import it.unisalento.se.saw.domain.Subjectofstudy;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.CourseDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.exceptions.SubjectOfStudyNotFoundException;
import it.unisalento.se.saw.exceptions.UserNotFoundException;

@RestController
@RequestMapping("/subjectofstudy")
public class SubjectOfStudyRestController {
	
	@Autowired
	ISubjectOfStudyService subjectOfStudyService;
	
	@Autowired
	ITeacherService teacherService;
	
	public SubjectOfStudyRestController() {
		super();
	}
	
	public SubjectOfStudyRestController(ISubjectOfStudyService subjectOfStudyService) {
		this.subjectOfStudyService = subjectOfStudyService;
	}
	
	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<SubjectDTO> getAll() throws UserNotFoundException{
		List<Subjectofstudy> subjectsOfStudy = subjectOfStudyService.getAll();
		List<SubjectDTO> subjectOfStudyDTOs = new ArrayList<SubjectDTO>();
		for(int i=0; i<subjectsOfStudy.size(); i++) {
			Subjectofstudy subjectOfStudy = subjectsOfStudy.get(i);
			subjectOfStudyDTOs.add(this.entityToDTO(subjectOfStudy));
		}
		return subjectOfStudyDTOs;
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public SubjectDTO getById(@PathVariable("id")int id) throws SubjectOfStudyNotFoundException, UserNotFoundException {
		
		Subjectofstudy subjectOfStudy = subjectOfStudyService.getById(id);
		return this.entityToDTO(subjectOfStudy);
	}
	
	@PostMapping(value="/edit", produces=MediaType.APPLICATION_JSON_VALUE)
	public SubjectDTO edit(@RequestBody SubjectDTO subjectOfStudyDTO) throws SubjectOfStudyNotFoundException {
		Subjectofstudy newSubjectOfStudy = subjectOfStudyService.edit(this.DTOtoEntity(subjectOfStudyDTO));
		return this.entityToDTO(newSubjectOfStudy);
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public SubjectDTO save(@RequestBody SubjectDTO subjectOfStudyDTO){
		
		Subjectofstudy newSubjectOfStudy = subjectOfStudyService.save(this.DTOtoEntity(subjectOfStudyDTO));
		return this.entityToDTO(newSubjectOfStudy);
	}
	
	@GetMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws SubjectOfStudyNotFoundException {
		subjectOfStudyService.delete(id);
	}	
	
	public SubjectDTO entityToDTO(Subjectofstudy subjectOfStudy) {
		
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setId(subjectOfStudy.getUser().getIduser());
		teacherDTO.setName(subjectOfStudy.getUser().getName());
		teacherDTO.setSurname(subjectOfStudy.getUser().getSurname());
		
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setIdcourse(subjectOfStudy.getCourse().getIdcourse());
		courseDTO.setName(subjectOfStudy.getCourse().getName());
		courseDTO.setDescription(subjectOfStudy.getCourse().getDescription());
		courseDTO.setYears(subjectOfStudy.getCourse().getYears());
		
		SubjectDTO subjectOfStudyDTO = new SubjectDTO();
		subjectOfStudyDTO.setId(subjectOfStudy.getIdsubjectofstudy());
		subjectOfStudyDTO.setName(subjectOfStudy.getName());
		subjectOfStudyDTO.setDescription(subjectOfStudy.getDescription());
		subjectOfStudyDTO.setCourseDTO(courseDTO);
		subjectOfStudyDTO.setTeacherDTO(teacherDTO);
		
		return subjectOfStudyDTO;
		
	}
	
	public Subjectofstudy DTOtoEntity(SubjectDTO subjectOfStudyDTO) {
		
		Course course = new Course();
		course.setIdcourse(subjectOfStudyDTO.getCourseDTO().getIdcourse());
		course.setName(subjectOfStudyDTO.getCourseDTO().getName());
		course.setDescription(subjectOfStudyDTO.getCourseDTO().getDescription());
		course.setYears(subjectOfStudyDTO.getCourseDTO().getYears());
		
		User teacher = new User();
		teacher.setIduser(subjectOfStudyDTO.getTeacherDTO().getId());
		teacher.setName(subjectOfStudyDTO.getTeacherDTO().getName());
		teacher.setSurname(subjectOfStudyDTO.getTeacherDTO().getSurname());
		
		Subjectofstudy subjectOfStudy = new Subjectofstudy();
		try {
			subjectOfStudy.setIdsubjectofstudy(subjectOfStudyDTO.getId());
		} catch (Exception e) {
		}
		subjectOfStudy.setName(subjectOfStudyDTO.getName());
		subjectOfStudy.setDescription(subjectOfStudyDTO.getDescription());
		subjectOfStudy.setCourse(course);
		subjectOfStudy.setUser(teacher);
		
		return subjectOfStudy;
		
	}

}
