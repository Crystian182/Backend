package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.ISubjectService;
import it.unisalento.se.saw.domain.AcademicYear;
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.Term;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.domain.TypeSubject;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.exceptions.SubjectNotFoundException;
import it.unisalento.se.saw.repositories.SubjectRepository;
import it.unisalento.se.saw.repositories.TermRepository;
import it.unisalento.se.saw.repositories.TypeSubjectRepository;

@Service
public class SubjectService implements ISubjectService{
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Autowired
	TermRepository termRepository;
	
	@Autowired
	TypeSubjectRepository typeSubjectRepository;
	
	@Transactional(readOnly=true)
	public List<SubjectDTO> getAll(){
		List<Subject> subjects = subjectRepository.findAll();
		List<SubjectDTO> subjectDTOs = new ArrayList<SubjectDTO>();
		for(int i=0; i<subjects.size(); i++) {
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(subjects.get(i).getTeacher().getUser().getIduser());
			teacherDTO.setName(subjects.get(i).getTeacher().getUser().getName());
			teacherDTO.setSurname(subjects.get(i).getTeacher().getUser().getSurname());
			
			try {
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
				degreeCourseDTO.setCfu(subjects.get(i).getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				
				AcademicYearDTO academicYearDTO = new AcademicYearDTO();
				academicYearDTO.setIdacademicYear(subjects.get(i).getDegreeCourse().getAcademicYear().getIdacademicYear());
				academicYearDTO.setYear(subjects.get(i).getDegreeCourse().getAcademicYear().getYear());
				degreeCourseDTO.setAcademicYear(academicYearDTO);
				
				TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
				typeSubjectDTO.setIdtypeSubject(subjects.get(i).getTypeSubject().getIdtypeSubject());
				typeSubjectDTO.setName(subjects.get(i).getTypeSubject().getName());
				
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(subjects.get(i).getIdsubject());
				subjectDTO.setName(subjects.get(i).getTypeSubject().getName());
				subjectDTO.setDescription(subjects.get(i).getTypeSubject().getDescription());
				subjectDTO.setDegreecourseDTO(degreeCourseDTO);
				subjectDTO.setTeacherDTO(teacherDTO);
				subjectDTO.setCfu(subjects.get(i).getCfu());
				subjectDTO.setTypeSubjectDTO(typeSubjectDTO);
				subjectDTOs.add(subjectDTO);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
		
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
			degreeCourseDTO.setCfu(subject.getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(subject.getDegreeCourse().getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(subject.getDegreeCourse().getAcademicYear().getYear());
			degreeCourseDTO.setAcademicYear(academicYearDTO);
			
			TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
			typeSubjectDTO.setIdtypeSubject(subject.getTypeSubject().getIdtypeSubject());
			typeSubjectDTO.setName(subject.getTypeSubject().getName());
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(subject.getIdsubject());
			subjectDTO.setName(subject.getTypeSubject().getName());
			subjectDTO.setDescription(subject.getTypeSubject().getDescription());
			subjectDTO.setDegreecourseDTO(degreeCourseDTO);
			subjectDTO.setTeacherDTO(teacherDTO);
			subjectDTO.setTypeSubjectDTO(typeSubjectDTO);
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
				degreeCourseDTO.setCfu(subject.get(i).getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				
				AcademicYearDTO academicYearDTO = new AcademicYearDTO();
				academicYearDTO.setIdacademicYear(subject.get(i).getDegreeCourse().getAcademicYear().getIdacademicYear());
				
				List<TermDTO> termDTOs= new ArrayList<TermDTO>();
				List<Term> terms = termRepository.getByAcademicYear(subject.get(i).getDegreeCourse().getAcademicYear().getIdacademicYear());
				for(int k=0; k<terms.size(); k++) {
					TermDTO termDTO = new TermDTO();
					termDTO.setIdterm(terms.get(k).getIdterm());
					termDTO.setNumber(terms.get(k).getNumber());
					termDTO.setStart(terms.get(k).getStart());
					termDTO.setEnd(terms.get(k).getEnd());
					termDTOs.add(termDTO);
				}
				academicYearDTO.setTerms(termDTOs);
				academicYearDTO.setYear(subject.get(i).getDegreeCourse().getAcademicYear().getYear());
				degreeCourseDTO.setAcademicYear(academicYearDTO);
				
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
		
		AcademicYear academicYear = new AcademicYear();
		academicYear.setIdacademicYear(subjectDTO.getDegreecourseDTO().getAcademicYear().getIdacademicYear());
		academicYear.setYear(subjectDTO.getDegreecourseDTO().getAcademicYear().getYear());

		List<Term> terms = new ArrayList<Term>();
		for(int k=0; k<subjectDTO.getDegreecourseDTO().getAcademicYear().getTerms().size(); k++) {
			Term term = new Term();
			term.setIdterm(subjectDTO.getDegreecourseDTO().getAcademicYear().getTerms().get(k).getIdterm());
			term.setNumber(subjectDTO.getDegreecourseDTO().getAcademicYear().getTerms().get(k).getNumber());
			term.setStart(subjectDTO.getDegreecourseDTO().getAcademicYear().getTerms().get(k).getStart());
			term.setEnd(subjectDTO.getDegreecourseDTO().getAcademicYear().getTerms().get(k).getEnd());
			term.setAcademicYear(academicYear);
			terms.add(term);
		}
		
		List<Term> termsofNewDegreeCourse = termRepository.saveAll(terms);	
		
		degreeCourse.setAcademicYear(academicYear);
		
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
		
		List<TermDTO> termDTOs = new ArrayList<TermDTO>();
		for(int h=0; h<termsofNewDegreeCourse.size(); h++) {
			TermDTO termDTO = new TermDTO();
			termDTO.setIdterm(termsofNewDegreeCourse.get(h).getIdterm());
			termDTO.setNumber(termsofNewDegreeCourse.get(h).getNumber());
			termDTO.setStart(termsofNewDegreeCourse.get(h).getStart());
			termDTO.setEnd(termsofNewDegreeCourse.get(h).getEnd());
			
			termDTOs.add(termDTO);
		}
		
		AcademicYearDTO academicYearDTO = new AcademicYearDTO();
		academicYearDTO.setIdacademicYear(newSubject.getDegreeCourse().getAcademicYear().getIdacademicYear());
		academicYearDTO.setYear(newSubject.getDegreeCourse().getAcademicYear().getYear());
		academicYearDTO.setTerms(termDTOs);
		
		DegreeCourseDTO degreecourseDTO = new DegreeCourseDTO();
		degreecourseDTO.setIdcourse(newSubject.getDegreeCourse().getIddegreeCourse());
		degreecourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
		degreecourseDTO.setCfu(newSubject.getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		degreecourseDTO.setAcademicYear(academicYearDTO);
		
		TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
		typeSubjectDTO.setIdtypeSubject(newSubject.getTypeSubject().getIdtypeSubject());
		typeSubjectDTO.setName(newSubject.getTypeSubject().getName());
		
		SubjectDTO newSubjectDTO = new SubjectDTO();
		newSubjectDTO.setId(newSubject.getIdsubject());
		newSubjectDTO.setCfu(newSubject.getCfu());
		newSubjectDTO.setDescription(newSubject.getTypeSubject().getDescription());
		newSubjectDTO.setName(newSubject.getTypeSubject().getName());
		newSubjectDTO.setTeacherDTO(teacherDTO);
		newSubjectDTO.setDegreecourseDTO(degreecourseDTO);
		newSubjectDTO.setTypeSubjectDTO(typeSubjectDTO);
		
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
	
	@Transactional(readOnly=true)
	public List<TypeSubjectDTO> getAllSubjectTypes(){
		List<TypeSubject> typeSubjects = typeSubjectRepository.findAll();
		List<TypeSubjectDTO> typeSubjectDTOs = new ArrayList<TypeSubjectDTO>();
		for(int i=0; i<typeSubjects.size(); i++) {
			TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
			typeSubjectDTO.setIdtypeSubject(typeSubjects.get(i).getIdtypeSubject());
			typeSubjectDTO.setName(typeSubjects.get(i).getName());
			typeSubjectDTOs.add(typeSubjectDTO);
		}
		return typeSubjectDTOs;
	}
	
	@Transactional(rollbackFor=SubjectNotFoundException.class)
	public List<SubjectDTO> getByIdTeacher(int id) throws SubjectNotFoundException {
		
		try {
			List<Subject> subject = subjectRepository.getByIdTeacher(id);
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
				degreeCourseDTO.setCfu(subject.get(i).getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				
				AcademicYearDTO academicYearDTO = new AcademicYearDTO();
				academicYearDTO.setIdacademicYear(subject.get(i).getDegreeCourse().getAcademicYear().getIdacademicYear());
				
				List<TermDTO> termDTOs= new ArrayList<TermDTO>();
				List<Term> terms = termRepository.getByAcademicYear(subject.get(i).getDegreeCourse().getAcademicYear().getIdacademicYear());
				for(int k=0; k<terms.size(); k++) {
					TermDTO termDTO = new TermDTO();
					termDTO.setIdterm(terms.get(k).getIdterm());
					termDTO.setNumber(terms.get(k).getNumber());
					termDTO.setStart(terms.get(k).getStart());
					termDTO.setEnd(terms.get(k).getEnd());
					termDTOs.add(termDTO);
				}
				academicYearDTO.setTerms(termDTOs);
				academicYearDTO.setYear(subject.get(i).getDegreeCourse().getAcademicYear().getYear());
				degreeCourseDTO.setAcademicYear(academicYearDTO);
				
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
}
