package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.exceptions.DegreeCourseNotFoundException;

public interface IDegreeCourseService {
	public List<DegreeCourseDTO> getAll();
	public DegreeCourseDTO getById(int id) throws DegreeCourseNotFoundException;
	public void delete(int id)  throws DegreeCourseNotFoundException;
	public DegreeCourseDTO save(DegreeCourseDTO degreeCourseDTO);
	//public Course insert(int idcourse, Subjectofstudy subjectofstudy);
	
}
