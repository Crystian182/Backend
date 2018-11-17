package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.ISubjectService;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
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
			
	/*		DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(subjects.get(i).getDegreeCourse().getIddegreeCourse());
			degreeCourseDTO.setName(subjects.get(i).getDegreeCourse().getName());
			degreeCourseDTO.setDescription(subjects.get(i).getDegreeCourse().getDescription());
			degreeCourseDTO.setAcademicYear(subjects.get(i).getDegreeCourse().getAcademicYear());
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(subjects.get(i).getIdsubject());
			subjectDTO.setName(subjects.get(i).getName());
			subjectDTO.setDescription(subjects.get(i).getDescription());
			subjectDTO.setDegreecourseDTO(degreeCourseDTO);
			subjectDTO.setTeacherDTO(teacherDTO);
			subjectDTO.setCfu(subjects.get(i).getCfu());
			subjectDTOs.add(subjectDTO);*/
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
			
		/*	DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(subject.getDegreeCourse().getIddegreeCourse());
			degreeCourseDTO.setName(subject.getDegreeCourse().getName());
			degreeCourseDTO.setDescription(subject.getDegreeCourse().getDescription());
			degreeCourseDTO.setAcademicYear(subject.getDegreeCourse().getAcademicYear());*/
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(subject.getIdsubject());
			/*subjectDTO.setName(subject.getName());
			subjectDTO.setDescription(subject.getDescription());
			subjectDTO.setDegreecourseDTO(degreeCourseDTO);*/
			subjectDTO.setTeacherDTO(teacherDTO);
			subjectDTO.setCfu(subject.getCfu());
	
			return subjectDTO;
			
		} catch (Exception e) {
			throw new SubjectNotFoundException();
		}
	}
	
	
	@Transactional
	public SubjectDTO save(SubjectDTO subjectDTO) {
		/*DegreeCourse degreeCourse = new DegreeCourse();
		degreeCourse.setIddegreeCourse(subjectDTO.getDegreecourseDTO().getIdcourse());
		degreeCourse.setName(subjectDTO.getDegreecourseDTO().getName());
		degreeCourse.setDescription(subjectDTO.getDegreecourseDTO().getDescription());
		degreeCourse.setAcademicYear(subjectDTO.getDegreecourseDTO().getAcademicYear());*/
		
		User user = new User();
		user.setName(subjectDTO.getTeacherDTO().getName());
		user.setSurname(subjectDTO.getTeacherDTO().getSurname());
		Teacher teacher = new Teacher();
		teacher.setIduser(subjectDTO.getTeacherDTO().getIdteacher());
		teacher.setUser(user);
		
		Subject subject= new Subject();
		try {
			subject.setIdsubject(subjectDTO.getId());
		} catch (Exception e) {
		}
	/*	subject.setName(subjectDTO.getName());
		subject.setDescription(subjectDTO.getDescription());
		subject.setDegreeCourse(degreeCourse);;*/
		subject.setTeacher(teacher);
		subject.setCfu(subjectDTO.getCfu());
		
		Subject newSubject = subjectRepository.save(subject);
		
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setIdteacher(newSubject.getTeacher().getIduser());
		teacherDTO.setName(newSubject.getTeacher().getUser().getName());
		teacherDTO.setSurname(newSubject.getTeacher().getUser().getSurname());
	/*	
		DegreeCourseDTO degreecourseDTO = new DegreeCourseDTO();
		degreecourseDTO.setIdcourse(newSubject.getDegreeCourse().getIddegreeCourse());
		degreecourseDTO.setName(newSubject.getDegreeCourse().getName());
		degreecourseDTO.setDescription(newSubject.getDegreeCourse().getDescription());
		degreecourseDTO.setAcademicYear(newSubject.getDegreeCourse().getAcademicYear());*/
		
		SubjectDTO newSubjectDTO = new SubjectDTO();
		newSubjectDTO.setId(newSubject.getIdsubject());
		newSubjectDTO.setCfu(newSubject.getCfu());
	/*	newSubjectDTO.setDescription(newSubject.getDescription());
		newSubjectDTO.setName(newSubject.getName());
		newSubjectDTO.setTeacherDTO(teacherDTO);
		newSubjectDTO.setDegreecourseDTO(degreecourseDTO);*/
		
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
