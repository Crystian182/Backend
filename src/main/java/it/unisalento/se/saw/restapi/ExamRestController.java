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
import it.unisalento.se.saw.dto.ExamTypeDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.dto.StudentDTO;
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
	
	/*@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ExamDTO> getAll(){
		return examService.getAll();

	}*/
	
	@GetMapping(value="/getAllByCourseAndTerm/idcourse={idcourse}&idterm={idterm}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ExamDTO> getAllByCourse(@PathVariable("idcourse")int idcourse, @PathVariable("idterm")int idterm) throws ExamNotFoundException {
		return examService.getAllByCourseAndTerm(idcourse, idterm);
	}
	
	@GetMapping(value="/getAllTypes", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ExamTypeDTO> getAllExamTypes() throws ExamNotFoundException {
		return examService.getAllTypes();
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public void save(@RequestBody List<ExamDTO> examDTOs){
		examService.save(examDTOs);

	}
	
	/*@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
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
	}*/
	
}
