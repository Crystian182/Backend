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
import it.unisalento.se.saw.domain.Lesson;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
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
		return lessonService.getAll();
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public LessonDTO getById(@PathVariable("id")int id) throws LessonNotFoundException{
		return lessonService.getById(id);
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public LessonDTO save(@RequestBody LessonDTO lessonDTO){
		return lessonService.save(lessonDTO);
		
	}
	
	@PostMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws LessonNotFoundException{
		lessonService.delete(id);
	}	
	
	
}
