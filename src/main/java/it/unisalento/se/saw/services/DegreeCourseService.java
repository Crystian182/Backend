package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IDegreeCourseService;
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.exceptions.DegreeCourseNotFoundException;
import it.unisalento.se.saw.repositories.CourseTypeRepository;
import it.unisalento.se.saw.repositories.DegreeCourseRepository;
import it.unisalento.se.saw.repositories.TypeDegreeCourseRepository;


@Service
public class DegreeCourseService implements IDegreeCourseService {
	
	@Autowired
	DegreeCourseRepository degreeCourseRepository;
	
	@Autowired
	TypeDegreeCourseRepository typeDegreeCourseRepository;
	
	@Autowired
	CourseTypeRepository courseTypeRepository;

	
	@Transactional(readOnly=true)
	public List<DegreeCourseDTO> getAll() {
		List<DegreeCourse> degreeCourses = degreeCourseRepository.findAll();
		List<DegreeCourseDTO> degreeCourseDTOs = new ArrayList<DegreeCourseDTO>();
		for(int i=0; i<degreeCourses.size(); i++) {
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(degreeCourses.get(i).getIddegreeCourse());
			degreeCourseDTO.setAcademicYear(degreeCourses.get(i).getAcademicYear());
			TypeDegreeCourseDTO typeDegreeCourse = new TypeDegreeCourseDTO();
			typeDegreeCourse.setIdtypeDegreeCourse(degreeCourses.get(i).getTypeDegreeCourse().getIdtypeDegreeCourse());
			typeDegreeCourse.setName(degreeCourses.get(i).getTypeDegreeCourse().getName());
			CourseTypeDTO courseType = new CourseTypeDTO();
			courseType.setIdcourseType(degreeCourses.get(i).getTypeDegreeCourse().getCourseType().getIdcourseType());
			courseType.setCfu(degreeCourses.get(i).getTypeDegreeCourse().getCourseType().getCfu());
			courseType.setDescription(degreeCourses.get(i).getTypeDegreeCourse().getCourseType().getDescription());
			typeDegreeCourse.setCourseType(courseType);
			degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourse);
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
			degreeCourseDTO.setAcademicYear(degreeCourse.getAcademicYear());
			TypeDegreeCourseDTO typeDegreeCourse = new TypeDegreeCourseDTO();
			typeDegreeCourse.setIdtypeDegreeCourse(degreeCourse.getTypeDegreeCourse().getIdtypeDegreeCourse());
			typeDegreeCourse.setName(degreeCourse.getTypeDegreeCourse().getName());
			CourseTypeDTO courseType = new CourseTypeDTO();
			courseType.setIdcourseType(degreeCourse.getTypeDegreeCourse().getCourseType().getIdcourseType());
			courseType.setCfu(degreeCourse.getTypeDegreeCourse().getCourseType().getCfu());
			courseType.setDescription(degreeCourse.getTypeDegreeCourse().getCourseType().getDescription());
			typeDegreeCourse.setCourseType(courseType);
			degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourse);
	
			return degreeCourseDTO;
		} catch (Exception e) {
			throw new DegreeCourseNotFoundException();
		}
		
	}

	@Transactional
	public DegreeCourseDTO save(DegreeCourseDTO degreeCourseDTO) {
		
		TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
		typeDegreeCourse.setIdtypeDegreeCourse(degreeCourseDTO.getTypeDegreeCourse().getIdtypeDegreeCourse());
		typeDegreeCourse.setName(degreeCourseDTO.getTypeDegreeCourse().getName());
		
		CourseType courseType = new CourseType();
		courseType.setIdcourseType(degreeCourseDTO.getTypeDegreeCourse().getCourseType().getIdcourseType());
		courseType.setCfu(degreeCourseDTO.getTypeDegreeCourse().getCourseType().getCfu());
		courseType.setDuration(degreeCourseDTO.getTypeDegreeCourse().getCourseType().getDuration());
		courseType.setDescription(degreeCourseDTO.getTypeDegreeCourse().getCourseType().getDescription());
		typeDegreeCourse.setCourseType(courseType);
		
		DegreeCourse degreeCourse = new DegreeCourse();
		try {
			degreeCourse.setIddegreeCourse(degreeCourseDTO.getIdcourse());
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		degreeCourse.setCfu(degreeCourseDTO.getCfu());
		degreeCourse.setAcademicYear(degreeCourseDTO.getAcademicYear());
		degreeCourse.setTypeDegreeCourse(typeDegreeCourse);

		DegreeCourse newDegreeCourse = degreeCourseRepository.save(degreeCourse);
		DegreeCourseDTO newDegreeCourseDTO = new DegreeCourseDTO();
		newDegreeCourseDTO.setIdcourse(newDegreeCourse.getIddegreeCourse());
		newDegreeCourseDTO.setAcademicYear(newDegreeCourse.getAcademicYear());
		
		TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
		typeDegreeCourseDTO.setIdtypeDegreeCourse(newDegreeCourse.getTypeDegreeCourse().getIdtypeDegreeCourse());
		typeDegreeCourseDTO.setName(newDegreeCourse.getTypeDegreeCourse().getName());
		
		CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
		courseTypeDTO.setIdcourseType(newDegreeCourse.getTypeDegreeCourse().getCourseType().getIdcourseType());
		courseTypeDTO.setDescription(newDegreeCourse.getTypeDegreeCourse().getCourseType().getDescription());
		courseTypeDTO.setCfu(newDegreeCourse.getTypeDegreeCourse().getCourseType().getCfu());
		courseTypeDTO.setDuration(newDegreeCourse.getTypeDegreeCourse().getCourseType().getCfu());
		typeDegreeCourseDTO.setCourseType(courseTypeDTO);
		newDegreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
		
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
	
	@Transactional(readOnly=true)
	public List<TypeDegreeCourseDTO> getAllTypes() {
		List<TypeDegreeCourse> typeDegreeCourses = typeDegreeCourseRepository.findAll();
		List<TypeDegreeCourseDTO> typeDegreeCourseDTOs = new ArrayList<TypeDegreeCourseDTO>();
		for(int i=0; i<typeDegreeCourses.size(); i++) {
			CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
			courseTypeDTO.setIdcourseType(typeDegreeCourses.get(i).getCourseType().getIdcourseType());
			courseTypeDTO.setDuration(typeDegreeCourses.get(i).getCourseType().getCfu());
			courseTypeDTO.setDescription(typeDegreeCourses.get(i).getDescription());
			courseTypeDTO.setCfu(typeDegreeCourses.get(i).getCourseType().getCfu());
			
			TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
			typeDegreeCourseDTO.setIdtypeDegreeCourse(typeDegreeCourses.get(i).getIdtypeDegreeCourse());
			typeDegreeCourseDTO.setName(typeDegreeCourses.get(i).getName());
			typeDegreeCourseDTO.setCourseType(courseTypeDTO);
			typeDegreeCourseDTOs.add(typeDegreeCourseDTO);
		}
		return typeDegreeCourseDTOs;
	}
	
	@Transactional(readOnly=true)
	public TypeDegreeCourseDTO getTypesById(int id){
			TypeDegreeCourse typeDegreeCourse = typeDegreeCourseRepository.findById(id).get();
			TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
			typeDegreeCourseDTO.setIdtypeDegreeCourse(typeDegreeCourse.getIdtypeDegreeCourse());
			typeDegreeCourseDTO.setName(typeDegreeCourse.getName());
			CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
			courseTypeDTO.setIdcourseType(typeDegreeCourse.getCourseType().getIdcourseType());
			courseTypeDTO.setDescription(typeDegreeCourse.getCourseType().getDescription());
			courseTypeDTO.setCfu(typeDegreeCourse.getCourseType().getCfu());
			courseTypeDTO.setDuration(typeDegreeCourse.getCourseType().getDuration());
			typeDegreeCourseDTO.setCourseType(courseTypeDTO);
			return typeDegreeCourseDTO;
	}
	
	@Transactional(readOnly=true)
	public List<CourseTypeDTO> getAllCourseTypes(){
		List<CourseType> courseTypes = courseTypeRepository.findAll();
		List<CourseTypeDTO> courseTypeDTOs = new ArrayList<CourseTypeDTO>();
		for(int i=0; i<courseTypes.size(); i++) {
			CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
			courseTypeDTO.setIdcourseType(courseTypes.get(i).getIdcourseType());
			courseTypeDTO.setDuration(courseTypes.get(i).getCfu());
			courseTypeDTO.setDescription(courseTypes.get(i).getDescription());
			courseTypeDTO.setCfu(courseTypes.get(i).getCfu());

			courseTypeDTOs.add(courseTypeDTO);
		}
		return courseTypeDTOs;
	}
	
	@Transactional(readOnly=true)
	public CourseTypeDTO getCourseType(int id){
		CourseType courseType = courseTypeRepository.findById(id).get();
		CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
		courseTypeDTO.setIdcourseType(courseType.getIdcourseType());
		courseTypeDTO.setDuration(courseType.getDuration());
		courseTypeDTO.setDescription(courseType.getDescription());
		courseTypeDTO.setCfu(courseType.getCfu());
		return courseTypeDTO;
	}


}
