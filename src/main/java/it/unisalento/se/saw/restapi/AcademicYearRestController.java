package it.unisalento.se.saw.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.Iservices.IAcademicYearService;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.exceptions.DegreeCourseNotFoundException;

@RestController
@RequestMapping("/academicyear")
public class AcademicYearRestController {
	
	@Autowired
	IAcademicYearService academicYearService;
	
	public AcademicYearRestController() {
		super();
	}
	
	public AcademicYearRestController(IAcademicYearService academicYearService) {
		this.academicYearService = academicYearService;
	}
	
	@GetMapping(value="/getTermsByAaId/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TermDTO> getTermsByAcademicYearId(@PathVariable("id")int id) {
		return academicYearService.getTermsByAcademicYearId(id);
	}

	@GetMapping(value="/getAllYearsOfCourse/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<AcademicYearDTO> getAllYearsOfCourse(@PathVariable("id")int id) throws DegreeCourseNotFoundException{
		return academicYearService.getAllYearsOfCourse(id);
	}

	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<AcademicYearDTO> getAll() {
		return academicYearService.getAll();
	}

	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public AcademicYearDTO save(@RequestBody AcademicYearDTO academicYearDTO){
		return academicYearService.save(academicYearDTO);
	}
	
	@PostMapping(value="/saveTerm", produces=MediaType.APPLICATION_JSON_VALUE)
	public TermDTO saveTerm(@RequestBody TermDTO termDTO){
		return academicYearService.saveTerm(termDTO);
	}
	
	@PostMapping(value="/saveAllTerm", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TermDTO> saveAllTerm(@RequestBody List<TermDTO> termDTOs){
		return academicYearService.saveAllTerm(termDTOs);
	}
}
