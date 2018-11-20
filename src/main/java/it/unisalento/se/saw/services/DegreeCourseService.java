package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IDegreeCourseService;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
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
			degreeCourseDTO.setName(degreeCourses.get(i).getTypeDegreeCourse().getName());
			degreeCourseDTO.setAcademicYear(degreeCourses.get(i).getAcademicYear());
			degreeCourseDTO.setIdTypeDegreeCourse(degreeCourses.get(i).getTypeDegreeCourse().getIdtypeDegreeCourse());
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
			degreeCourseDTO.setName(degreeCourse.getTypeDegreeCourse().getName());
			degreeCourseDTO.setAcademicYear(degreeCourse.getAcademicYear());
			degreeCourseDTO.setIdTypeDegreeCourse(degreeCourse.getTypeDegreeCourse().getIdtypeDegreeCourse());
			return degreeCourseDTO;
		} catch (Exception e) {
			throw new DegreeCourseNotFoundException();
		}
		
	}

	@Transactional
	public DegreeCourseDTO save(DegreeCourseDTO degreeCourseDTO) {
		TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
		typeDegreeCourse.setIdtypeDegreeCourse(degreeCourseDTO.getIdTypeDegreeCourse());

		DegreeCourse degreeCourse = new DegreeCourse();
		try {
			degreeCourse.setIddegreeCourse(degreeCourseDTO.getIdcourse());
		} catch (Exception e) {
			// TODO: handle exception
		}
		TypeDegreeCourse courseType = new TypeDegreeCourse();
		courseType.setName(degreeCourseDTO.getName());
		degreeCourse.setCfu(degreeCourseDTO.getCfu());
		degreeCourse.setAcademicYear(degreeCourseDTO.getAcademicYear());
		degreeCourse.setTypeDegreeCourse(courseType);

		DegreeCourse newDegreeCourse = degreeCourseRepository.save(degreeCourse);
		DegreeCourseDTO newDegreeCourseDTO = new DegreeCourseDTO();
		newDegreeCourseDTO.setIdcourse(newDegreeCourse.getIddegreeCourse());
		newDegreeCourseDTO.setName(newDegreeCourse.getTypeDegreeCourse().getName());
		newDegreeCourseDTO.setAcademicYear(newDegreeCourse.getAcademicYear());
		newDegreeCourseDTO.setIdTypeDegreeCourse(newDegreeCourse.getTypeDegreeCourse().getIdtypeDegreeCourse());
		
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
