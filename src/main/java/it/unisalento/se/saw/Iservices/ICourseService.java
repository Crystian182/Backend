package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.domain.Course;
import it.unisalento.se.saw.domain.Subjectofstudy;
import it.unisalento.se.saw.exceptions.CourseNotFoundException;

public interface ICourseService {
	public List<Course> getAll();
	public Course getById(int id) throws CourseNotFoundException;
	public void delete(int id)  throws CourseNotFoundException;
	public Course save(Course course);
	public Course edit(Course course)  throws CourseNotFoundException;
	//public Course insert(int idcourse, Subjectofstudy subjectofstudy);
	
}
