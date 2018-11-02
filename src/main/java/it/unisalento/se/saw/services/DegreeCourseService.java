package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IDegreeCourseService;
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.exceptions.DegreeCourseNotFoundException;
import it.unisalento.se.saw.repositories.DegreeCourseRepository;


@Service
public class DegreeCourseService implements IDegreeCourseService {
	
	@Autowired
	DegreeCourseRepository degreeCourseRepository;

	@Transactional(readOnly=true)
	public List<DegreeCourseDTO> getAll() {
		List<DegreeCourse> degreeCourses = degreeCourseRepository.findAll();
		List<DegreeCourseDTO> degreeCourseDTOs = new ArrayList<DegreeCourseDTO>();
		for(int i=0; i<degreeCourses.size(); i++) {
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(degreeCourses.get(i).getIddegreeCourse());
			degreeCourseDTO.setName(degreeCourses.get(i).getName());
			degreeCourseDTO.setDescription(degreeCourses.get(i).getDescription());
			degreeCourseDTO.setAcademicYear(degreeCourses.get(i).getAcademicYear());
			degreeCourseDTO.setIdCourseType(degreeCourses.get(i).getCourseType().getIdcourseType());
			degreeCourseDTOs.add(degreeCourseDTO);
		}
		return degreeCourseDTOs;
	}

	@Transactional(rollbackFor=DegreeCourseNotFoundException.class)
	public DegreeCourseDTO getById(int id) throws DegreeCourseNotFoundException{
		try {
			DegreeCourse degreeCourse = degreeCourseRepository.findById(id).get();
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(degreeCourse.getIddegreeCourse());
			degreeCourseDTO.setName(degreeCourse.getName());
			degreeCourseDTO.setDescription(degreeCourse.getDescription());
			degreeCourseDTO.setAcademicYear(degreeCourse.getAcademicYear());
			degreeCourseDTO.setIdCourseType(degreeCourse.getCourseType().getIdcourseType());
			return degreeCourseDTO;
		} catch (Exception e) {
			throw new DegreeCourseNotFoundException();
		}
		
	}

	@Transactional
	public DegreeCourseDTO save(DegreeCourseDTO degreeCourseDTO) {
		CourseType courseType = new CourseType();
		courseType.setIdcourseType(degreeCourseDTO.getIdCourseType());
		DegreeCourse degreeCourse = new DegreeCourse();
		try {
			degreeCourse.setIddegreeCourse(degreeCourseDTO.getIdcourse());
		} catch (Exception e) {
			// TODO: handle exception
		}
		degreeCourse.setName(degreeCourseDTO.getName());
		degreeCourse.setDescription(degreeCourseDTO.getDescription());
		degreeCourse.setAcademicYear(degreeCourseDTO.getAcademicYear());
		degreeCourse.setCourseType(courseType);

		DegreeCourse newDegreeCourse = degreeCourseRepository.save(degreeCourse);
		DegreeCourseDTO newDegreeCourseDTO = new DegreeCourseDTO();
		newDegreeCourseDTO.setIdcourse(newDegreeCourse.getIddegreeCourse());
		newDegreeCourseDTO.setName(newDegreeCourse.getName());
		newDegreeCourseDTO.setDescription(newDegreeCourse.getDescription());
		newDegreeCourseDTO.setAcademicYear(newDegreeCourse.getAcademicYear());
		newDegreeCourseDTO.setIdCourseType(newDegreeCourse.getCourseType().getIdcourseType());
		
		return newDegreeCourseDTO;
	}
	
	@Transactional
	public void delete(int id) throws DegreeCourseNotFoundException{
		try {
			DegreeCourse degreeCourse = degreeCourseRepository.findById(id).get();
			degreeCourseRepository.delete(degreeCourse);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DegreeCourseNotFoundException();
		}
		
	}


}
