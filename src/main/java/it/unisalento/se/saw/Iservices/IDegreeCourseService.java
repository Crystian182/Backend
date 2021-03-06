package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.exceptions.DegreeCourseNotFoundException;

public interface IDegreeCourseService {
	public List<DegreeCourseDTO> getAll();
	public DegreeCourseDTO getById(int id) throws DegreeCourseNotFoundException;
	public void delete(int id)  throws DegreeCourseNotFoundException;
	public DegreeCourseDTO save(DegreeCourseDTO degreeCourseDTO);
	public TypeDegreeCourseDTO saveType(TypeDegreeCourseDTO typeDegreeCourseDTO);
	//public Course insert(int idcourse, Subjectofstudy subjectofstudy);
	public TypeDegreeCourseDTO getTypesById(int id);
	public List<TypeDegreeCourseDTO> getAllTypes();
	public List<CourseTypeDTO> getAllCourseTypes();
	public CourseTypeDTO getCourseType(int id);
	public List<DegreeCourseDTO> getCourseByType(int idTypeCourse) throws DegreeCourseNotFoundException;
	public List<DegreeCourseDTO> getTeacherCourses(int idteacher) throws DegreeCourseNotFoundException;
}
