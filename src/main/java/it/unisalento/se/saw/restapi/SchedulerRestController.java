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

import it.unisalento.se.saw.Iservices.ILessonService;
import it.unisalento.se.saw.Iservices.ISchedulerService;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.SchedulerDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;

@RestController
@RequestMapping("/scheduler")
public class SchedulerRestController {
	
	@Autowired
	ILessonService lessonService;
	
	@Autowired
	ISchedulerService schedulerService;
	
	public SchedulerRestController(){
		super();
	}
	
	public SchedulerRestController(ILessonService lessonService) {
		this.lessonService = lessonService;
	}
	
	public SchedulerRestController(ISchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}
	
	@PostMapping(value="/getScheduler/{idterm}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TypeLessonDTO> getCurrentSchedulerByCourse(@PathVariable("idterm") int idterm, @RequestBody DegreeCourseDTO degreeCourseDTO){
		return lessonService.getCurrentSchedulerByCourse(degreeCourseDTO, idterm);
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public SchedulerDTO saveScheduler(@RequestBody SchedulerDTO schedulerDTO){
		return schedulerService.save(schedulerDTO);
	}
	
	@GetMapping(value="/exists/{idterm}&{idcourse}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Integer periodHasScheduler(@PathVariable("idterm") int idterm, @PathVariable("idcourse") int idcourse){
		return schedulerService.periodHasScheduler(idterm, idcourse);
	}
	
	@GetMapping(value="/getSchedulerTeacher/{idteacher}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TypeLessonDTO> getCurrentSchedulerTeacher(@PathVariable("idteacher") int idteacher){
		return lessonService.getCurrentSchedulerTeacher(idteacher);
	}

}
