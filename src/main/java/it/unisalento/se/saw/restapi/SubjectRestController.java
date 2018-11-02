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

import it.unisalento.se.saw.Iservices.ISubjectService;
import it.unisalento.se.saw.Iservices.ITeacherService;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.exceptions.SubjectNotFoundException;
import it.unisalento.se.saw.exceptions.UserNotFoundException;

@RestController
@RequestMapping("/subjectofstudy")
public class SubjectRestController {
	
	@Autowired
	ISubjectService subjectService;
	
	@Autowired
	ITeacherService teacherService;
	
	public SubjectRestController() {
		super();
	}
	
	public SubjectRestController(ISubjectService subjectOfStudyService) {
		this.subjectService = subjectOfStudyService;
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
	public List<SubjectDTO> getAll() throws SubjectNotFoundException{
		return subjectService.getAll();
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public SubjectOfStudyDTO getById(@PathVariable("id")int id) throws SubjectNotFoundException, UserNotFoundException {
		
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
	public void delete(@PathVariable("id")int id) throws SubjectNotFoundException {
		subjectOfStudyService.delete(id);
	}	

}
