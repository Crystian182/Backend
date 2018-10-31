package it.unisalento.se.saw.restapi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.Iservices.ICourseService;
import it.unisalento.se.saw.domain.Course;
import it.unisalento.se.saw.domain.Subjectofstudy;
import it.unisalento.se.saw.dto.CourseDTO;
import it.unisalento.se.saw.dto.SubjectOfStudyDTO;
import it.unisalento.se.saw.exceptions.CourseNotFoundException;




@RestController
@RequestMapping("/course")
public class CourseRestController {
	
	@Autowired
	ICourseService courseService;

	public CourseRestController() {
		super();
	}
	
	public CourseRestController(ICourseService courseService) {
		this.courseService = courseService;
	}
	
	@RequestMapping(value="/getAll", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<CourseDTO> getAll(){
		List<Course> courses = courseService.getAll();
		List<CourseDTO> newCourseDTOs = new ArrayList<CourseDTO>();
		for(int i=0; i<courses.size(); i++) {
			Course course = courses.get(i);
			newCourseDTOs.add(this.EntityToDTO(course));
		}
		return newCourseDTOs;
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public CourseDTO getById(@PathVariable("id")int id) throws CourseNotFoundException{
		Course course = courseService.getById(id);
		CourseDTO courseDTO = this.EntityToDTO(course);
		return courseDTO;
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public CourseDTO save(@RequestBody CourseDTO courseDTO){
		Course course = courseService.save(this.DTOToEntity(courseDTO));
		return this.EntityToDTO(course);
	}
	
	@PostMapping(value="/edit", produces=MediaType.APPLICATION_JSON_VALUE)
	public CourseDTO edit(@RequestBody CourseDTO courseDTO) throws CourseNotFoundException{
		Course course = courseService.edit(this.DTOToEntity(courseDTO));
		return this.EntityToDTO(course);
	}
	
	@PostMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws CourseNotFoundException{
		courseService.delete(id);
	}	
	

	
	
	public Subjectofstudy DTOToEntity(SubjectOfStudyDTO subjectofstudyDTO) {
		
		Course course = new Course();
		course.setIdcourse(subjectofstudyDTO.getCourseDTO().getIdcourse());
		course.setName(subjectofstudyDTO.getCourseDTO().getName());
		course.setDescription(subjectofstudyDTO.getCourseDTO().getDescription());
		course.setYears(subjectofstudyDTO.getCourseDTO().getYears());
		
		Subjectofstudy subjectofstudy = new Subjectofstudy();
		subjectofstudy.setIdsubjectofstudy(subjectofstudyDTO.getId());
		subjectofstudy.setName(subjectofstudyDTO.getName());
		subjectofstudy.setCourse(course);
		subjectofstudy.setDescription(subjectofstudyDTO.getDescription());
		
		return subjectofstudy;
	}
	
	public CourseDTO EntityToDTO(Course course) {
		
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setIdcourse(course.getIdcourse());
		courseDTO.setName(course.getName());
		courseDTO.setDescription(course.getDescription());
		courseDTO.setYears(course.getYears());
		
		return courseDTO;
	}
	
	
	public Course DTOToEntity(CourseDTO courseDTO) {
		
		Course course = new Course();
		try {
			course.setIdcourse(courseDTO.getIdcourse());
		} catch (Exception e) {
			// TODO: handle exception
		}
		course.setName(courseDTO.getName());
		course.setDescription(courseDTO.getDescription());
		course.setYears(courseDTO.getYears());

		return course;
	}
	
	
}
