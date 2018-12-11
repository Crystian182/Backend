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

import it.unisalento.se.saw.Iservices.ISubjectService;
import it.unisalento.se.saw.Iservices.ITeacherService;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.exceptions.SubjectNotFoundException;
import it.unisalento.se.saw.exceptions.UserNotFoundException;

@RestController
@RequestMapping("/subject")
public class SubjectRestController {
	
	@Autowired
	ISubjectService subjectService;
	
	@Autowired
	ITeacherService teacherService;
	
	public SubjectRestController() {
		super();
	}
	
	public SubjectRestController(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}
	
	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<SubjectDTO> getAll() throws SubjectNotFoundException{
		return subjectService.getAll();
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public SubjectDTO getById(@PathVariable("id")int id) throws SubjectNotFoundException, UserNotFoundException {
		
		return subjectService.getById(id);
		
	}
	
	@GetMapping(value="/getByIdCourse/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<SubjectDTO> getByIdCourse(@PathVariable("id")int id) throws SubjectNotFoundException {
		
		return subjectService.getByIdCourse(id);
		
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public SubjectDTO save(@RequestBody SubjectDTO subjectDTO){
		return subjectService.save(subjectDTO);
	}
	
	@PostMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws SubjectNotFoundException {
		subjectService.delete(id);
	}	
	
	@GetMapping(value="/getAllSubjectTypes", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TypeSubjectDTO> getAllSubjectTypes(){
		return subjectService.getAllSubjectTypes();
	}
	
}
