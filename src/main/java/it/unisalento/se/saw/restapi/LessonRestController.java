package it.unisalento.se.saw.restapi;

import java.text.ParseException;
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
import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
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
	
	@GetMapping(value="/getAllTeacherLessons/{idteacher}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<LessonDTO> getAllTeacherLessons(@PathVariable("idteacher")int idteacher){
		return lessonService.getAllTeacherLessons(idteacher);
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
	
	@GetMapping(value="/getFeedback/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<FeedbackDTO> getFeedback(@PathVariable("id")int id) {
		return lessonService.getFeedback(id);
	}
	
	@GetMapping(value="/getAllLessonsByCourseAndTerm/idcourse={idcourse}&idterm={idterm}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<LessonDTO> getAllLessonsByCourseAndTerm(@PathVariable("idcourse")int idcourse, @PathVariable("idterm")int idterm){
		return lessonService.getAllLessonsByCourseAndTerm(idcourse, idterm);
	}
	
	@GetMapping(value="/searchLessons/idcourse={idcourse}&idterm={idterm}&idsubject={idsubject}&from={from}&to={to}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<LessonDTO> searchLessons(@PathVariable("idcourse")int idcourse, @PathVariable("idterm")int idterm, @PathVariable("idsubject")int idsubject, @PathVariable("from")String from, @PathVariable("to")String to) throws ParseException{
		return lessonService.searchLessons(idcourse, idterm, idsubject, from, to);
	}
	
	@PostMapping(value="/edit", produces=MediaType.APPLICATION_JSON_VALUE)
	public void editLessons(@RequestBody ArrayList<LessonDTO> lessonDTOs){
		lessonService.edit(lessonDTOs);
		
	}
	
	@GetMapping(value="/getTodayLessons/{iduser}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<LessonDTO> getTodayLessons(@PathVariable("iduser")int iduser){
		return lessonService.getTodayLessons(iduser);
	}
	
}
