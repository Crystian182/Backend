package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.ISubjectService;
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.domain.TypeSubject;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.exceptions.SubjectNotFoundException;
import it.unisalento.se.saw.repositories.SubjectRepository;

@Service
public class SubjectService implements ISubjectService{
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Transactional(readOnly=true)
	public List<SubjectDTO> getAll(){
		List<Subject> subjects = subjectRepository.findAll();
		List<SubjectDTO> subjectDTOs = new ArrayList<SubjectDTO>();
		for(int i=0; i<subjects.size(); i++) {
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(subjects.get(i).getTeacher().getUser().getIduser());
			teacherDTO.setName(subjects.get(i).getTeacher().getUser().getName());
			teacherDTO.setSurname(subjects.get(i).getTeacher().getUser().getSurname());
			
			TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
			typeDegreeCourseDTO.setIdtypeDegreeCourse(subjects.get(i).getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
			typeDegreeCourseDTO.setName(subjects.get(i).getDegreeCourse().getTypeDegreeCourse().getName());
			
			CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
			courseTypeDTO.setIdcourseType(subjects.get(i).getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
			courseTypeDTO.setDescription(subjects.get(i).getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
			courseTypeDTO.setCfu(subjects.get(i).getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			courseTypeDTO.setDuration(subjects.get(i).getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			typeDegreeCourseDTO.setCourseType(courseTypeDTO);
			
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(subjects.get(i).getDegreeCourse().getIddegreeCourse());
			degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
			degreeCourseDTO.setCfu(subjects.get(i).getDegreeCourse().getCfu());
			degreeCourseDTO.setAcademicYear(subjects.get(i).getDegreeCourse().getAcademicYear());
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(subjects.get(i).getIdsubject());
			subjectDTO.setName(subjects.get(i).getTypeSubject().getName());
			subjectDTO.setDescription(subjects.get(i).getTypeSubject().getDescription());
			subjectDTO.setDegreecourseDTO(degreeCourseDTO);
			subjectDTO.setTeacherDTO(teacherDTO);
			subjectDTO.setCfu(subjects.get(i).getCfu());
			subjectDTOs.add(subjectDTO);
		}
	
		return subjectDTOs;
	}
	
	@Transactional(rollbackFor=SubjectNotFoundException.class)
	public SubjectDTO getById(int id) throws SubjectNotFoundException {
		
		try {
			Subject subject = subjectRepository.findById(id).get();
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(subject.getTeacher().getUser().getIduser());
			teacherDTO.setName(subject.getTeacher().getUser().getName());
			teacherDTO.setSurname(subject.getTeacher().getUser().getSurname());
			
			TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
			typeDegreeCourseDTO.setIdtypeDegreeCourse(subject.getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
			typeDegreeCourseDTO.setName(subject.getDegreeCourse().getTypeDegreeCourse().getName());
			
			CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
			courseTypeDTO.setIdcourseType(subject.getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
			courseTypeDTO.setDescription(subject.getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
			courseTypeDTO.setCfu(subject.getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			courseTypeDTO.setDuration(subject.getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			typeDegreeCourseDTO.setCourseType(courseTypeDTO);
			
			
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(subject.getDegreeCourse().getIddegreeCourse());
			degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
			degreeCourseDTO.setCfu(subject.getDegreeCourse().getCfu());
			degreeCourseDTO.setAcademicYear(subject.getDegreeCourse().getAcademicYear());
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(subject.getIdsubject());
			subjectDTO.setName(subject.getTypeSubject().getName());
			subjectDTO.setDescription(subject.getTypeSubject().getDescription());
			subjectDTO.setDegreecourseDTO(degreeCourseDTO);
			subjectDTO.setTeacherDTO(teacherDTO);
			subjectDTO.setCfu(subject.getCfu());
	
			return subjectDTO;
			
		} catch (Exception e) {
			throw new SubjectNotFoundException();
		}
	}
	
	@Transactional(rollbackFor=SubjectNotFoundException.class)
	public List<SubjectDTO> getByIdCourse(int id) throws SubjectNotFoundException {
		
		try {
			List<Subject> subject = subjectRepository.getByIdCourse(id);
			List<SubjectDTO> subjectDTOs = new ArrayList<SubjectDTO>();
			
			for(int i=0; i<subject.size(); i++) {

				TeacherDTO teacherDTO = new TeacherDTO();
				teacherDTO.setIdteacher(subject.get(i).getTeacher().getUser().getIduser());
				teacherDTO.setName(subject.get(i).getTeacher().getUser().getName());
				teacherDTO.setSurname(subject.get(i).getTeacher().getUser().getSurname());
				
				TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
				typeDegreeCourseDTO.setIdtypeDegreeCourse(subject.get(i).getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
				typeDegreeCourseDTO.setName(subject.get(i).getDegreeCourse().getTypeDegreeCourse().getName());
				
				CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
				courseTypeDTO.setIdcourseType(subject.get(i).getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
				courseTypeDTO.setDescription(subject.get(i).getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
				courseTypeDTO.setCfu(subject.get(i).getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				courseTypeDTO.setDuration(subject.get(i).getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				typeDegreeCourseDTO.setCourseType(courseTypeDTO);
				
				DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
				degreeCourseDTO.setIdcourse(subject.get(i).getDegreeCourse().getIddegreeCourse());
				degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
				degreeCourseDTO.setCfu(subject.get(i).getDegreeCourse().getCfu());
				degreeCourseDTO.setAcademicYear(subject.get(i).getDegreeCourse().getAcademicYear());
				
				TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
				typeSubjectDTO.setIdtypeSubject(subject.get(i).getTypeSubject().getIdtypeSubject());
				typeSubjectDTO.setName(subject.get(i).getTypeSubject().getName());
				typeSubjectDTO.setDescription(subject.get(i).getTypeSubject().getDescription());
				
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(subject.get(i).getIdsubject());
				subjectDTO.setName(subject.get(i).getTypeSubject().getName());
				subjectDTO.setDescription(subject.get(i).getTypeSubject().getDescription());
				subjectDTO.setDegreecourseDTO(degreeCourseDTO);
				subjectDTO.setTeacherDTO(teacherDTO);
				subjectDTO.setCfu(subject.get(i).getCfu());
				subjectDTO.setTypeSubjectDTO(typeSubjectDTO);
				
				subjectDTOs.add(subjectDTO);
			
			}
	
			return subjectDTOs;
			
		} catch (Exception e) {
			throw new SubjectNotFoundException();
		}
	}
	
	@Transactional
	public SubjectDTO save(SubjectDTO subjectDTO) {
		TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
		typeDegreeCourse.setIdtypeDegreeCourse(subjectDTO.getDegreecourseDTO().getTypeDegreeCourse().getIdtypeDegreeCourse());
		typeDegreeCourse.setName(subjectDTO.getDegreecourseDTO().getTypeDegreeCourse().getName());
		
		CourseType courseType = new CourseType();
		courseType.setIdcourseType(subjectDTO.getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getIdcourseType());
		courseType.setCfu(subjectDTO.getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getCfu());
		courseType.setDuration(subjectDTO.getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getDuration());
		courseType.setDescription(subjectDTO.getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getDescription());
		typeDegreeCourse.setCourseType(courseType);
		
		DegreeCourse degreeCourse = new DegreeCourse();
		degreeCourse.setIddegreeCourse(subjectDTO.getDegreecourseDTO().getIdcourse());
		degreeCourse.setTypeDegreeCourse(typeDegreeCourse);
		degreeCourse.setCfu(subjectDTO.getDegreecourseDTO().getCfu());
		degreeCourse.setAcademicYear(subjectDTO.getDegreecourseDTO().getAcademicYear());
		
		User user = new User();
		user.setName(subjectDTO.getTeacherDTO().getName());
		user.setSurname(subjectDTO.getTeacherDTO().getSurname());
		Teacher teacher = new Teacher();
		teacher.setIduser(subjectDTO.getTeacherDTO().getIdteacher());
		teacher.setUser(user);
		
		TypeSubject typeSubject = new TypeSubject();
		typeSubject.setIdtypeSubject(subjectDTO.getTypeSubjectDTO().getIdtypeSubject());
		typeSubject.setName(subjectDTO.getTypeSubjectDTO().getName());
		
		Subject subject= new Subject();
		try {
			subject.setIdsubject(subjectDTO.getId());
		} catch (Exception e) {
		}
		subject.setTypeSubject(typeSubject);
		subject.setDegreeCourse(degreeCourse);
		subject.setTeacher(teacher);
		subject.setCfu(subjectDTO.getCfu());
		
		Subject newSubject = subjectRepository.save(subject);
		
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setIdteacher(newSubject.getTeacher().getIduser());
		teacherDTO.setName(newSubject.getTeacher().getUser().getName());
		teacherDTO.setSurname(newSubject.getTeacher().getUser().getSurname());
		
		TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
		typeDegreeCourseDTO.setIdtypeDegreeCourse(newSubject.getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
		typeDegreeCourseDTO.setName(newSubject.getDegreeCourse().getTypeDegreeCourse().getName());
		
		CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
		courseTypeDTO.setIdcourseType(newSubject.getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
		courseTypeDTO.setDescription(newSubject.getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
		courseTypeDTO.setCfu(newSubject.getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		courseTypeDTO.setDuration(newSubject.getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		typeDegreeCourseDTO.setCourseType(courseTypeDTO);
		
		DegreeCourseDTO degreecourseDTO = new DegreeCourseDTO();
		degreecourseDTO.setIdcourse(newSubject.getDegreeCourse().getIddegreeCourse());
		degreecourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
		degreecourseDTO.setCfu(newSubject.getDegreeCourse().getCfu());
		degreecourseDTO.setAcademicYear(newSubject.getDegreeCourse().getAcademicYear());
		
		SubjectDTO newSubjectDTO = new SubjectDTO();
		newSubjectDTO.setId(newSubject.getIdsubject());
		newSubjectDTO.setCfu(newSubject.getCfu());
		newSubjectDTO.setDescription(newSubject.getTypeSubject().getDescription());
		newSubjectDTO.setName(newSubject.getTypeSubject().getName());
		newSubjectDTO.setTeacherDTO(teacherDTO);
		newSubjectDTO.setDegreecourseDTO(degreecourseDTO);
		
		return newSubjectDTO;
	}
	
	@Transactional
	public void delete(int id) throws SubjectNotFoundException {
		// TODO Auto-generated method stub
		try {
			Subject subject = subjectRepository.findById(id).get();
			subjectRepository.delete(subject);
		} catch (Exception e) {
			// TODO: handle exception
			throw new SubjectNotFoundException();
		}

	}

}
