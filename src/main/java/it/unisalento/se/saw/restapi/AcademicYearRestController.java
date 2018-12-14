package it.unisalento.se.saw.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
