package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.ICourseService;
import it.unisalento.se.saw.domain.Course;
import it.unisalento.se.saw.domain.Subjectofstudy;
import it.unisalento.se.saw.exceptions.CourseNotFoundException;
import it.unisalento.se.saw.repositories.CourseRepository;

@Service
public class CourseService implements ICourseService {
	
	@Autowired
	CourseRepository courseRepository;

	@Transactional(readOnly=true)
	public List<Course> getAll() {
		// TODO Auto-generated method stub
		return courseRepository.findAll();
	}

	@Transactional(rollbackFor=CourseNotFoundException.class)
	public Course getById(int id) throws CourseNotFoundException{
		try {
			return courseRepository.findById(id).get();
		} catch (Exception e) {
			throw new CourseNotFoundException();
		}
		
	}

	@Transactional
	public Course save(Course course) {
		// TODO Auto-generated method stub
		return courseRepository.save(course);
	}

	@Transactional
	public Course edit(Course course) throws CourseNotFoundException{
		try {
			courseRepository.findById(course.getIdcourse()).get();
			return courseRepository.save(course);
		} catch (Exception e) {
			// TODO: handle exception
			throw new CourseNotFoundException();
		}
		
	}
	
	@Transactional
	public void delete(int id) throws CourseNotFoundException{
		try {
			Course course = courseRepository.findById(id).get();
			courseRepository.delete(course);
		} catch (Exception e) {
			// TODO: handle exception
			throw new CourseNotFoundException();
		}
		
	}


}
