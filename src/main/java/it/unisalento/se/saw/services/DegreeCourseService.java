package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IDegreeCourseService;
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
import it.unisalento.se.saw.exceptions.DegreeCourseNotFoundException;
import it.unisalento.se.saw.repositories.CourseTypeRepository;
import it.unisalento.se.saw.repositories.DegreeCourseRepository;
import it.unisalento.se.saw.repositories.SubjectRepository;
import it.unisalento.se.saw.repositories.TermRepository;
import it.unisalento.se.saw.repositories.TypeDegreeCourseRepository;


@Service
public class DegreeCourseService implements IDegreeCourseService {
	
	@Autowired
	DegreeCourseRepository degreeCourseRepository;
	
	@Autowired
	TypeDegreeCourseRepository typeDegreeCourseRepository;
	
	@Autowired
	CourseTypeRepository courseTypeRepository;
	
	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	TermRepository termRepository;
	
	@Transactional(readOnly=true)
	public List<DegreeCourseDTO> getAll() {
		List<DegreeCourse> degreeCourses = degreeCourseRepository.findAll();
		List<DegreeCourseDTO> degreeCourseDTOs = new ArrayList<DegreeCourseDTO>();
		for(int i=0; i<degreeCourses.size(); i++) {
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(degreeCourses.get(i).getIddegreeCourse());
			
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(degreeCourses.get(i).getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(degreeCourses.get(i).getAcademicYear().getYear());
			
			List<TermDTO> termDTOs= new ArrayList<TermDTO>();
			List<Term> terms = termRepository.getByAcademicYear(degreeCourses.get(i).getAcademicYear().getIdacademicYear());
			for(int k=0; k<terms.size(); k++) {
				TermDTO termDTO = new TermDTO();
				termDTO.setIdterm(terms.get(k).getIdterm());
				termDTO.setStart(terms.get(k).getStart());
				termDTO.setEnd(terms.get(k).getEnd());
				termDTOs.add(termDTO);
			}
			academicYearDTO.setTerms(termDTOs);
			degreeCourseDTO.setAcademicYear(academicYearDTO);
			
			TypeDegreeCourseDTO typeDegreeCourse = new TypeDegreeCourseDTO();
			typeDegreeCourse.setIdtypeDegreeCourse(degreeCourses.get(i).getTypeDegreeCourse().getIdtypeDegreeCourse());
			typeDegreeCourse.setName(degreeCourses.get(i).getTypeDegreeCourse().getName());
			
			CourseTypeDTO courseType = new CourseTypeDTO();
			courseType.setIdcourseType(degreeCourses.get(i).getTypeDegreeCourse().getCourseType().getIdcourseType());
			courseType.setCfu(degreeCourses.get(i).getTypeDegreeCourse().getCourseType().getCfu());
			courseType.setDescription(degreeCourses.get(i).getTypeDegreeCourse().getCourseType().getDescription());
			
			typeDegreeCourse.setCourseType(courseType);
			degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourse);
			degreeCourseDTO.setName(typeDegreeCourse.getName());
			List<SubjectDTO> subjectDTOs= new ArrayList<SubjectDTO>();
			List<Subject> subjects = subjectRepository.getSubjectsByIdDegreeCourse(degreeCourses.get(i).getIddegreeCourse());
			if(subjects != null) {
				for(int j=0; j<subjects.size(); j++) {
					SubjectDTO subjectDTO = new SubjectDTO();
					subjectDTO.setId(subjects.get(j).getIdsubject());
					subjectDTO.setName(subjects.get(j).getTypeSubject().getName());
					subjectDTO.setCfu(subjects.get(j).getCfu());
					TeacherDTO teacherDTO = new TeacherDTO();
					teacherDTO.setIdteacher(subjects.get(j).getTeacher().getIduser());
					teacherDTO.setName(subjects.get(j).getTeacher().getUser().getName());
					teacherDTO.setSurname(subjects.get(j).getTeacher().getUser().getSurname());
					subjectDTO.setTeacherDTO(teacherDTO);
					subjectDTOs.add(subjectDTO);
				}
			}
			degreeCourseDTO.setSubjects(subjectDTOs);
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
			List<TermDTO> termDTOs= new ArrayList<TermDTO>();
			List<Term> terms = termRepository.getByAcademicYear(degreeCourse.getAcademicYear().getIdacademicYear());
			for(int k=0; k<terms.size(); k++) {
				TermDTO termDTO = new TermDTO();
				termDTO.setIdterm(terms.get(k).getIdterm());
				termDTO.setStart(terms.get(k).getStart());
				termDTO.setEnd(terms.get(k).getEnd());
				termDTOs.add(termDTO);
			}
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(degreeCourse.getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(degreeCourse.getAcademicYear().getYear());
			academicYearDTO.setTerms(termDTOs);
			degreeCourseDTO.setAcademicYear(academicYearDTO);
			TypeDegreeCourseDTO typeDegreeCourse = new TypeDegreeCourseDTO();
			typeDegreeCourse.setIdtypeDegreeCourse(degreeCourse.getTypeDegreeCourse().getIdtypeDegreeCourse());
			typeDegreeCourse.setName(degreeCourse.getTypeDegreeCourse().getName());
			CourseTypeDTO courseType = new CourseTypeDTO();
			courseType.setIdcourseType(degreeCourse.getTypeDegreeCourse().getCourseType().getIdcourseType());
			courseType.setCfu(degreeCourse.getTypeDegreeCourse().getCourseType().getCfu());
			courseType.setDescription(degreeCourse.getTypeDegreeCourse().getCourseType().getDescription());
			typeDegreeCourse.setCourseType(courseType);
			degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourse);
			List<SubjectDTO> subjectDTOs= new ArrayList<SubjectDTO>();
			List<Subject> subjects = subjectRepository.getSubjectsByIdDegreeCourse(degreeCourse.getIddegreeCourse());
			if(subjects!= null) {
				for(int j=0; j<subjects.size(); j++) {
					SubjectDTO subjectDTO = new SubjectDTO();
					subjectDTO.setId(subjects.get(j).getIdsubject());
					subjectDTO.setName(subjects.get(j).getTypeSubject().getName());
					subjectDTO.setCfu(subjects.get(j).getCfu());
					TeacherDTO teacherDTO = new TeacherDTO();
					teacherDTO.setIdteacher(subjects.get(j).getTeacher().getIduser());
					teacherDTO.setName(subjects.get(j).getTeacher().getUser().getName());
					teacherDTO.setSurname(subjects.get(j).getTeacher().getUser().getSurname());
					subjectDTO.setTeacherDTO(teacherDTO);
					subjectDTOs.add(subjectDTO);
				}
			}
			degreeCourseDTO.setSubjects(subjectDTOs);
			return degreeCourseDTO;
		} catch (Exception e) {
			throw new DegreeCourseNotFoundException();
		}
		
	}
	
@Transactional(rollbackFor=DegreeCourseNotFoundException.class)
	public List<DegreeCourseDTO> getCourseByType(int idTypeCourse) throws DegreeCourseNotFoundException{
		try {
			List<DegreeCourse> degreeCourses = degreeCourseRepository.getCourseByType(idTypeCourse);
			
			List<DegreeCourseDTO> degreeCourseDTOs = new ArrayList<DegreeCourseDTO>();
			
			for(int i=0; i<degreeCourses.size(); i++) {
				DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
				degreeCourseDTO.setIdcourse(degreeCourses.get(i).getIddegreeCourse());
				degreeCourseDTO.setName(degreeCourses.get(i).getTypeDegreeCourse().getName());
				degreeCourseDTO.setCfu(degreeCourses.get(i).getTypeDegreeCourse().getCourseType().getCfu());
				AcademicYearDTO academicYearDTO = new AcademicYearDTO();
				academicYearDTO.setIdacademicYear(degreeCourses.get(i).getAcademicYear().getIdacademicYear());
				academicYearDTO.setYear(degreeCourses.get(i).getAcademicYear().getYear());
				degreeCourseDTO.setAcademicYear(academicYearDTO);
				
				degreeCourseDTOs.add(degreeCourseDTO);
			}
			
			return degreeCourseDTOs;
		} catch (Exception e) {
			throw new DegreeCourseNotFoundException();
		}
		
	}

@Transactional(rollbackFor=DegreeCourseNotFoundException.class)
public List<DegreeCourseDTO> getTeacherCourses(int idteacher) throws DegreeCourseNotFoundException{
	try {
		List<DegreeCourse> degreeCourses = degreeCourseRepository.getCourseTeacher(idteacher);
		
		List<DegreeCourseDTO> degreeCourseDTOs = new ArrayList<DegreeCourseDTO>();
		
		for(int i=0; i<degreeCourses.size(); i++) {
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(degreeCourses.get(i).getIddegreeCourse());
			degreeCourseDTO.setName(degreeCourses.get(i).getTypeDegreeCourse().getName());
			degreeCourseDTO.setCfu(degreeCourses.get(i).getTypeDegreeCourse().getCourseType().getCfu());
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(degreeCourses.get(i).getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(degreeCourses.get(i).getAcademicYear().getYear());
			degreeCourseDTO.setAcademicYear(academicYearDTO);
			
			degreeCourseDTOs.add(degreeCourseDTO);
		}
		
		return degreeCourseDTOs;
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
		
		/*List<Term> terms = new ArrayList<Term>();
		for(int k=0; k<degreeCourseDTO.getAcademicYear().getTerms().size(); k++) {
			Term term = new Term();
			term.setIdterm(degreeCourseDTO.getAcademicYear().getTerms().get(k).getIdterm());
			term.setStart(degreeCourseDTO.getAcademicYear().getTerms().get(k).getStart());
			term.setEnd(degreeCourseDTO.getAcademicYear().getTerms().get(k).getEnd());
			AcademicYear academicYear = new AcademicYear();
			academicYear.setIdacademicYear(degreeCourseDTO.getAcademicYear().getIdacademicYear());
			academicYear.setYear(degreeCourseDTO.getAcademicYear().getYear());
			term.setAcademicYear(academicYear);
			terms.add(term);
		}*/
		
		//List<Term> termsofNewDegreeCourse = termRepository.saveAll(terms);
		
		AcademicYear academicYear = new AcademicYear();
		academicYear.setIdacademicYear(degreeCourseDTO.getAcademicYear().getIdacademicYear());
		academicYear.setYear(degreeCourseDTO.getAcademicYear().getYear());
		
		degreeCourse.setAcademicYear(academicYear);
		degreeCourse.setTypeDegreeCourse(typeDegreeCourse);

		DegreeCourse newDegreeCourse = degreeCourseRepository.save(degreeCourse);

		List<Subject> subjects = new ArrayList<Subject>();
		if(degreeCourseDTO.getSubjects() != null) {
			for(int j=0; j<degreeCourseDTO.getSubjects().size(); j++) {
				Subject subject = new Subject();
				subject.setIdsubject(degreeCourseDTO.getSubjects().get(j).getId());
				subject.setCfu(degreeCourseDTO.getSubjects().get(j).getCfu());
				User user = new User();
				user.setName(degreeCourseDTO.getSubjects().get(j).getTeacherDTO().getName());
				user.setSurname(degreeCourseDTO.getSubjects().get(j).getTeacherDTO().getSurname());
				Teacher teacher = new Teacher();
				teacher.setIduser(degreeCourseDTO.getSubjects().get(j).getTeacherDTO().getIdteacher());
				teacher.setUser(user);
				subject.setTeacher(teacher);
				Term term = new Term();
				term.setIdterm(degreeCourseDTO.getSubjects().get(j).getTerm().getIdterm());
				TypeSubject typeSubject = new TypeSubject();
				typeSubject.setIdtypeSubject(degreeCourseDTO.getSubjects().get(j).getTypeSubjectDTO().getIdtypeSubject());
				typeSubject.setName(degreeCourseDTO.getSubjects().get(j).getTypeSubjectDTO().getName());
				subject.setTypeSubject(typeSubject);
				subject.setDegreeCourse(newDegreeCourse);
				subject.setTypeSubject(typeSubject);
				subject.setTerm(term);
				subjects.add(subject);
			}
		}
		List<Subject> subjectsOfNewDegreeCourse = subjectRepository.saveAll(subjects);	
		
		DegreeCourseDTO newDegreeCourseDTO = new DegreeCourseDTO();
		newDegreeCourseDTO.setIdcourse(newDegreeCourse.getIddegreeCourse());
		newDegreeCourseDTO.setName(newDegreeCourse.getTypeDegreeCourse().getName());
		

	/*	List<TermDTO> termDTOs = new ArrayList<TermDTO>();
		for(int h=0; h<termsofNewDegreeCourse.size(); h++) {
			TermDTO termDTO = new TermDTO();
			termDTO.setIdterm(termsofNewDegreeCourse.get(h).getIdterm());
			termDTO.setStart(termsofNewDegreeCourse.get(h).getStart());
			termDTO.setEnd(termsofNewDegreeCourse.get(h).getEnd());
			
			termDTOs.add(termDTO);
		}
		*/
		AcademicYearDTO academicYearDTO = new AcademicYearDTO();
		academicYearDTO.setIdacademicYear(newDegreeCourse.getAcademicYear().getIdacademicYear());
		academicYearDTO.setYear(newDegreeCourse.getAcademicYear().getYear());
	
		newDegreeCourseDTO.setAcademicYear(academicYearDTO);
		newDegreeCourseDTO.setCfu(newDegreeCourse.getTypeDegreeCourse().getCourseType().getCfu());
		
		
		TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
		typeDegreeCourseDTO.setIdtypeDegreeCourse(newDegreeCourse.getTypeDegreeCourse().getIdtypeDegreeCourse());
		typeDegreeCourseDTO.setName(newDegreeCourse.getTypeDegreeCourse().getName());
		
		CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
		courseTypeDTO.setIdcourseType(newDegreeCourse.getTypeDegreeCourse().getCourseType().getIdcourseType());
		courseTypeDTO.setDescription(newDegreeCourse.getTypeDegreeCourse().getCourseType().getDescription());
		courseTypeDTO.setCfu(newDegreeCourse.getTypeDegreeCourse().getCourseType().getCfu());
		courseTypeDTO.setDuration(newDegreeCourse.getTypeDegreeCourse().getCourseType().getDuration());
		typeDegreeCourseDTO.setCourseType(courseTypeDTO);
		newDegreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
		
		List<SubjectDTO> subjectDTOs = new ArrayList<SubjectDTO>();
		if(subjectsOfNewDegreeCourse != null) {
			for(int i=0; i<subjectsOfNewDegreeCourse.size(); i++) {
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(subjectsOfNewDegreeCourse.get(i).getIdsubject());
				subjectDTO.setCfu(subjectsOfNewDegreeCourse.get(i).getCfu());
				subjectDTO.setName(subjectsOfNewDegreeCourse.get(i).getTypeSubject().getName());
				TeacherDTO teacherDTO = new TeacherDTO();
				teacherDTO.setIdteacher(subjectsOfNewDegreeCourse.get(i).getTeacher().getIduser());
				teacherDTO.setName(subjectsOfNewDegreeCourse.get(i).getTeacher().getUser().getName());
				teacherDTO.setSurname(subjectsOfNewDegreeCourse.get(i).getTeacher().getUser().getSurname());
				subjectDTO.setTeacherDTO(teacherDTO);
				TermDTO termDTO = new TermDTO();
				termDTO.setIdterm(subjectsOfNewDegreeCourse.get(i).getTerm().getIdterm());
				TypeSubjectDTO typeSubjectDTO = new TypeSubjectDTO();
				typeSubjectDTO.setIdtypeSubject(subjectsOfNewDegreeCourse.get(i).getTypeSubject().getIdtypeSubject());
				typeSubjectDTO.setName(subjectsOfNewDegreeCourse.get(i).getTypeSubject().getName());
				typeSubjectDTO.setDescription(subjectsOfNewDegreeCourse.get(i).getTypeSubject().getDescription());
				subjectDTO.setTypeSubjectDTO(typeSubjectDTO);
				subjectDTO.setTerm(termDTO);
				subjectDTOs.add(subjectDTO);
			}
		}
		newDegreeCourseDTO.setSubjects(subjectDTOs);
		
		
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
			courseTypeDTO.setDuration(typeDegreeCourses.get(i).getCourseType().getDuration());
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

	@Transactional
	public TypeDegreeCourseDTO saveType(TypeDegreeCourseDTO typeDegreeCourseDTO) {
		TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
		typeDegreeCourse.setIdtypeDegreeCourse(typeDegreeCourseDTO.getIdtypeDegreeCourse());
		typeDegreeCourse.setName(typeDegreeCourseDTO.getName());
		
		CourseType courseType = new CourseType();
		courseType.setIdcourseType(typeDegreeCourseDTO.getCourseType().getIdcourseType());
		courseType.setCfu(typeDegreeCourseDTO.getCourseType().getCfu());
		courseType.setDuration(typeDegreeCourseDTO.getCourseType().getDuration());
		courseType.setDescription(typeDegreeCourseDTO.getCourseType().getDescription());
		typeDegreeCourse.setCourseType(courseType);
		
		TypeDegreeCourse newTypeDegreeCourse = typeDegreeCourseRepository.save(typeDegreeCourse);
		
		TypeDegreeCourseDTO newType = new TypeDegreeCourseDTO();
		newType.setIdtypeDegreeCourse(newTypeDegreeCourse.getIdtypeDegreeCourse());
		newType.setName(newTypeDegreeCourse.getName());
		CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
		courseTypeDTO.setIdcourseType(newTypeDegreeCourse.getCourseType().getIdcourseType());
		courseTypeDTO.setCfu(newTypeDegreeCourse.getCourseType().getCfu());
		courseTypeDTO.setDuration(newTypeDegreeCourse.getCourseType().getDuration());
		courseTypeDTO.setDescription(newTypeDegreeCourse.getCourseType().getDescription());
		
		newType.setCourseType(courseTypeDTO);
		return newType;
	}


}
