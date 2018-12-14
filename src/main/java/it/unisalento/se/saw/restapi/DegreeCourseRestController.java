package it.unisalento.se.saw.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.Iservices.IDegreeCourseService;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.exceptions.DegreeCourseNotFoundException;

@RestController
@RequestMapping("/course")
public class DegreeCourseRestController {
	
	@Autowired
	IDegreeCourseService degreeCourseService;

	public DegreeCourseRestController() {
		super();
	}
	
	public DegreeCourseRestController(IDegreeCourseService degreeCourseService) {
		this.degreeCourseService = degreeCourseService;
	}
	
	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<DegreeCourseDTO> getAll(){
		return degreeCourseService.getAll();
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public DegreeCourseDTO getById(@PathVariable("id")int id) throws DegreeCourseNotFoundException{
		return degreeCourseService.getById(id);
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public DegreeCourseDTO save(@RequestBody DegreeCourseDTO courseDTO){
		return degreeCourseService.save(courseDTO);
	}
	
	@PostMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws DegreeCourseNotFoundException{
		degreeCourseService.delete(id);
	}	
	
	@GetMapping(value="/getAllTypes", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TypeDegreeCourseDTO> getAllTypes(){
		return degreeCourseService.getAllTypes();
	}	
	
	@GetMapping(value="/getTypesById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public TypeDegreeCourseDTO getTypesById(@PathVariable("id")int id){
		return degreeCourseService.getTypesById(id);
	}
	
	@GetMapping(value="/getAllCourseTypes", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<CourseTypeDTO> getAllCourseTypes(){
		return degreeCourseService.getAllCourseTypes();
	}	
	
	@GetMapping(value="/getCourseType/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public CourseTypeDTO getCourseType(@PathVariable("id")int id){
		return degreeCourseService.getCourseType(id);
	}
	
	@GetMapping(value="/getCourseByType/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<DegreeCourseDTO> getCourseByType(@PathVariable("id")int id) throws DegreeCourseNotFoundException{
		return degreeCourseService.getCourseByType(id);
	}
	
}
