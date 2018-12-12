package it.unisalento.se.saw.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.Iservices.ILessonService;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;

@RestController
@RequestMapping("/scheduler")
public class SchedulerRestController {
	
	@Autowired
	ILessonService lessonService;
	
	public SchedulerRestController(){
		super();
	}
	
	public SchedulerRestController(ILessonService lessonService) {
		this.lessonService = lessonService;
	}
	
	@GetMapping(value="/getScheduler", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TypeLessonDTO> getCurrentSchedulerByCourse(@RequestBody DegreeCourseDTO degreeCourseDTO){
		return lessonService.getCurrentSchedulerByCourse(degreeCourseDTO);
	}

}
