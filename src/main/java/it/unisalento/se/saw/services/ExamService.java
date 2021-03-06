package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.service.internal.ServiceProxyGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IExamService;
import it.unisalento.se.saw.domain.AcademicYear;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.EnrollmentStatus;
import it.unisalento.se.saw.domain.Exam;
import it.unisalento.se.saw.domain.ExamStatus;
import it.unisalento.se.saw.domain.ExamType;
import it.unisalento.se.saw.domain.Result;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.domain.StudentHasExam;
import it.unisalento.se.saw.domain.StudentHasExamId;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.domain.TypeSubject;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.ExamDTO;
import it.unisalento.se.saw.dto.ExamEnrollmentDTO;
import it.unisalento.se.saw.dto.ExamStatusDTO;
import it.unisalento.se.saw.dto.ExamTypeDTO;
import it.unisalento.se.saw.dto.ResultDTO;
import it.unisalento.se.saw.dto.StudentDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.exceptions.ExamNotFoundException;
//import it.unisalento.se.saw.repositories.ExamEnrollmentRepository;
import it.unisalento.se.saw.repositories.ExamRepository;
import it.unisalento.se.saw.repositories.ExamTypeRepository;
import it.unisalento.se.saw.repositories.StudentHasExamRepository;
import it.unisalento.se.saw.repositories.SubjectRepository;

@Service
public class ExamService implements IExamService{
	
	@Autowired
	ExamRepository examRepository;
	
	@Autowired
	ExamTypeRepository examTypeRepository;
	
	@Autowired
	StudentHasExamRepository studentHasExamRepository;
	
	@Autowired
	SubjectRepository subjectRepository;

	/*@Transactional(readOnly=true)
	public List<ExamDTO> getAll() {
		List<Exam> exams = examRepository.findAll();
		List<ExamDTO> examDTOs = new ArrayList<ExamDTO>();
		for(int i=0; i<exams.size(); i++) {
			
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(exams.get(i).getClassroom().getBuilding().getIdbuilding());
			buildingDTO.setName(exams.get(i).getClassroom().getBuilding().getName());
			buildingDTO.setAddress(exams.get(i).getClassroom().getBuilding().getAddress());
			
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(exams.get(i).getSubject().getTeacher().getIduser());
			teacherDTO.setSurname(exams.get(i).getSubject().getTeacher().getUser().getName());
			teacherDTO.setName(exams.get(i).getSubject().getTeacher().getUser().getSurname());
			
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(exams.get(i).getSubject().getDegreeCourse().getIddegreeCourse());
			TypeDegreeCourseDTO typeDegreeCourse = new TypeDegreeCourseDTO();
			typeDegreeCourse.setIdtypeDegreeCourse(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
			typeDegreeCourse.setName(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
			CourseTypeDTO courseType = new CourseTypeDTO();
			courseType.setIdcourseType(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
			courseType.setCfu(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			courseType.setDescription(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
			typeDegreeCourse.setCourseType(courseType);
			degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourse);
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(exams.get(i).getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(exams.get(i).getSubject().getDegreeCourse().getAcademicYear().getYear());
			degreeCourseDTO.setAcademicYear(academicYearDTO);
			degreeCourseDTO.setCfu(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(exams.get(i).getClassroom().getIdclassroom());
			classroomDTO.setName(exams.get(i).getClassroom().getName());
			classroomDTO.setSeats(exams.get(i).getClassroom().getSeats());
			classroomDTO.setBuilding(buildingDTO);
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(exams.get(i).getSubject().getIdsubject());
			subjectDTO.setName(exams.get(i).getSubject().getTypeSubject().getName());
			subjectDTO.setDescription(exams.get(i).getSubject().getTypeSubject().getDescription());
			subjectDTO.setTeacherDTO(teacherDTO);
			subjectDTO.setDegreecourseDTO(degreeCourseDTO);
			
			ExamDTO examDTO = new ExamDTO();
			
			examDTO.setIdexam(exams.get(i).getIdexam());
			examDTO.setClassroom(classroomDTO);
			examDTO.setSubject(subjectDTO);
			examDTO.setDate(exams.get(i).getDate());
			examDTO.setIdExamType(exams.get(i).getExamType().getIdexamType());
			examDTO.setStart(exams.get(i).getStart());
			examDTO.setEnd(exams.get(i).getEnd());
			
			examDTOs.add(examDTO);
		
		}
		return examDTOs;
	}*/

	/*@Transactional(rollbackFor=ExamNotFoundException.class)
	public ExamDTO getById(int id) throws ExamNotFoundException {
		try {
			Exam exam = examRepository.findById(id).get();
			
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(exam.getClassroom().getBuilding().getIdbuilding());
			buildingDTO.setName(exam.getClassroom().getBuilding().getName());
			buildingDTO.setAddress(exam.getClassroom().getBuilding().getAddress());
			
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(exam.getSubject().getTeacher().getIduser());
			teacherDTO.setSurname(exam.getSubject().getTeacher().getUser().getName());
			teacherDTO.setName(exam.getSubject().getTeacher().getUser().getSurname());
			
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(exam.getSubject().getDegreeCourse().getIddegreeCourse());
			TypeDegreeCourseDTO typeDegreeCourse = new TypeDegreeCourseDTO();
			typeDegreeCourse.setIdtypeDegreeCourse(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
			typeDegreeCourse.setName(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
			CourseTypeDTO courseType = new CourseTypeDTO();
			courseType.setIdcourseType(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
			courseType.setCfu(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			courseType.setDescription(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
			typeDegreeCourse.setCourseType(courseType);
			degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourse);
			degreeCourseDTO.setCfu(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(exam.getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(exam.getSubject().getDegreeCourse().getAcademicYear().getYear());
			degreeCourseDTO.setAcademicYear(academicYearDTO);
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(exam.getClassroom().getIdclassroom());
			classroomDTO.setName(exam.getClassroom().getName());
			classroomDTO.setSeats(exam.getClassroom().getSeats());
			classroomDTO.setBuilding(buildingDTO);
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(exam.getSubject().getIdsubject());
			subjectDTO.setName(exam.getSubject().getTypeSubject().getName());
			subjectDTO.setDescription(exam.getSubject().getTypeSubject().getDescription());
			subjectDTO.setTeacherDTO(teacherDTO);
			subjectDTO.setDegreecourseDTO(degreeCourseDTO);
			
			ExamDTO examDTO = new ExamDTO();
			
			examDTO.setIdexam(exam.getIdexam());
			examDTO.setClassroom(classroomDTO);
			examDTO.setSubject(subjectDTO);
			examDTO.setDate(exam.getDate());
			examDTO.setIdExamType(exam.getExamType().getIdexamType());
			examDTO.setStart(exam.getStart());
			examDTO.setEnd(exam.getEnd());
			
			return examDTO;
			
		} catch (Exception e) {
			throw new ExamNotFoundException();
		}
	}*/

	/*@Transactional
	public void delete(int id) throws ExamNotFoundException {
		try {
			Exam exam = examRepository.findById(id).get();
			examRepository.delete(exam);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ExamNotFoundException();
		}
		
	}*/

	@Transactional
	public void save(List<ExamDTO> examDTOs) {
		
		for(int i=0; i<examDTOs.size(); i++) {
			Exam exam = new Exam();
			try {
				exam.setIdexam(examDTOs.get(i).getIdexam());
			} catch (Exception e) {
				// TODO: handle exception
			}
			exam.setDate(examDTOs.get(i).getDate());
			
			ExamType examType = new ExamType();
			examType.setIdexamType(examDTOs.get(i).getExamtype().getIdexamType());
			
			exam.setExamType(examType);
			
			Classroom classroom = new Classroom();
			classroom.setIdclassroom(examDTOs.get(i).getClassroom().getId());
			
			exam.setClassroom(classroom);
			
			Subject subject = new Subject();
			subject.setIdsubject(examDTOs.get(i).getSubject().getId());
			
			exam.setSubject(subject);
			
			ExamStatus status = new ExamStatus();
			status.setIdexamStatus(1);
			
			exam.setExamStatus(status);
			examRepository.save(exam);
		}
	}

	@Transactional
	public List<ExamDTO> getAllByCourseAndTerm(int idcourse, int idterm) throws ExamNotFoundException {
		try {
			List<Exam> exams = examRepository.findAllByCourseAndTerm(idcourse, idterm);
			System.out.println(exams.size());
			List<ExamDTO> examDTOs = new ArrayList<ExamDTO>();
			for(int i=0; i<exams.size(); i++) {
				
				BuildingDTO buildingDTO = new BuildingDTO();
				buildingDTO.setId(exams.get(i).getClassroom().getBuilding().getIdbuilding());
				buildingDTO.setName(exams.get(i).getClassroom().getBuilding().getName());
				buildingDTO.setAddress(exams.get(i).getClassroom().getBuilding().getAddress());
				
				TeacherDTO teacherDTO = new TeacherDTO();
				teacherDTO.setIdteacher(exams.get(i).getSubject().getTeacher().getIduser());
				teacherDTO.setSurname(exams.get(i).getSubject().getTeacher().getUser().getName());
				teacherDTO.setName(exams.get(i).getSubject().getTeacher().getUser().getSurname());
				
				DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
				degreeCourseDTO.setIdcourse(exams.get(i).getSubject().getDegreeCourse().getIddegreeCourse());
				
				TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
				typeDegreeCourseDTO.setIdtypeDegreeCourse(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
				typeDegreeCourseDTO.setName(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
				
				CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
				courseTypeDTO.setIdcourseType(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
				courseTypeDTO.setDescription(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
				courseTypeDTO.setCfu(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				courseTypeDTO.setDuration(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDuration());
				typeDegreeCourseDTO.setCourseType(courseTypeDTO);
				
				degreeCourseDTO.setCfu(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				AcademicYearDTO academicYearDTO = new AcademicYearDTO();
				academicYearDTO.setIdacademicYear(exams.get(i).getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
				academicYearDTO.setYear(exams.get(i).getSubject().getDegreeCourse().getAcademicYear().getYear());
				degreeCourseDTO.setAcademicYear(academicYearDTO);
				degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
				
				ClassroomDTO classroomDTO = new ClassroomDTO();
				classroomDTO.setId(exams.get(i).getClassroom().getIdclassroom());
				classroomDTO.setName(exams.get(i).getClassroom().getName());
				classroomDTO.setSeats(exams.get(i).getClassroom().getSeats());
				classroomDTO.setBuilding(buildingDTO);
				
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(exams.get(i).getSubject().getIdsubject());
				subjectDTO.setName(exams.get(i).getSubject().getTypeSubject().getName());
				subjectDTO.setTeacherDTO(teacherDTO);
				subjectDTO.setDegreecourseDTO(degreeCourseDTO);
				
				ExamDTO examDTO = new ExamDTO();
				
				examDTO.setIdexam(exams.get(i).getIdexam());
				examDTO.setClassroom(classroomDTO);
				examDTO.setSubject(subjectDTO);
				examDTO.setDate(exams.get(i).getDate());
				
				ExamTypeDTO examTypeDTO = new ExamTypeDTO();
				examTypeDTO.setIdexamType(exams.get(i).getExamType().getIdexamType());
				examTypeDTO.setDescription(exams.get(i).getExamType().getDescription());
				
				examDTO.setExamtype(examTypeDTO);
				
				examDTOs.add(examDTO);
			
			}
			return examDTOs;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new ExamNotFoundException();
		}
		
	}
	
	@Transactional
	public List<ExamTypeDTO> getAllTypes() {
		List<ExamType> examTypes = examTypeRepository.findAll();
		List<ExamTypeDTO> examTypeDTOs = new ArrayList<ExamTypeDTO>();
		
		for(int i=0; i<examTypes.size(); i++) {
			ExamTypeDTO examTypeDTO = new ExamTypeDTO();
			examTypeDTO.setIdexamType(examTypes.get(i).getIdexamType());
			examTypeDTO.setDescription(examTypes.get(i).getDescription());
			examTypeDTOs.add(examTypeDTO);
		}
		
		return examTypeDTOs;
	}


	
	/*@Transactional
	public ExamDTO subscribe(int idexam, StudentDTO studentDTO) {
		// TODO Auto-generated method stub
		StudentHasExamId studentHasExamId = new StudentHasExamId();
		studentHasExamId.setIdexam(idexam);
		studentHasExamId.setIduser(studentDTO.getIdstudent());
		
		Exam exam = new Exam();
		exam.setIdexam(idexam);
		
		Result result = new Result();
		result.setIdresult(1);
		
		Student student = new Student();
	
		student.setIduser(studentDTO.getIdstudent());
		User user = new User();
		user.setName(studentDTO.getName());
		user.setSurname(studentDTO.getSurname());
		student.setUser(user);
		
		StudentHasExam studentHasExam = new StudentHasExam();
		studentHasExam.setId(studentHasExamId);
		studentHasExam.setStudent(student);
		studentHasExam.setExam(exam);
		studentHasExam.setResult(result);
	
		studentHasExamRepository.save(studentHasExam);
		
		Exam subscribedExam = examRepository.getOne(idexam);
		
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId(exam.getClassroom().getBuilding().getIdbuilding());
		buildingDTO.setName(exam.getClassroom().getBuilding().getName());
		buildingDTO.setAddress(exam.getClassroom().getBuilding().getAddress());
		
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setIdteacher(exam.getSubject().getTeacher().getIduser());
		teacherDTO.setSurname(exam.getSubject().getTeacher().getUser().getName());
		teacherDTO.setName(exam.getSubject().getTeacher().getUser().getSurname());
		
		ClassroomDTO classroomDTO = new ClassroomDTO();
		classroomDTO.setId(exam.getClassroom().getIdclassroom());
		classroomDTO.setName(exam.getClassroom().getName());
		classroomDTO.setSeats(exam.getClassroom().getSeats());
		classroomDTO.setBuilding(buildingDTO);
		
		DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
		degreeCourseDTO.setIdcourse(exam.getSubject().getDegreeCourse().getIddegreeCourse());
		
		TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
		typeDegreeCourseDTO.setIdtypeDegreeCourse(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
		typeDegreeCourseDTO.setName(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
		
		CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
		courseTypeDTO.setIdcourseType(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
		courseTypeDTO.setDescription(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
		courseTypeDTO.setCfu(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		courseTypeDTO.setDuration(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		typeDegreeCourseDTO.setCourseType(courseTypeDTO);
		
		degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
		
		AcademicYearDTO academicYearDTO = new AcademicYearDTO();
		academicYearDTO.setIdacademicYear(exam.getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
		academicYearDTO.setYear(exam.getSubject().getDegreeCourse().getAcademicYear().getYear());
		degreeCourseDTO.setAcademicYear(academicYearDTO);
		degreeCourseDTO.setCfu(exam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		
		SubjectDTO subjectDTO = new SubjectDTO();
		subjectDTO.setId(exam.getSubject().getIdsubject());
		subjectDTO.setName(exam.getSubject().getTypeSubject().getName());
		subjectDTO.setDescription(exam.getSubject().getTypeSubject().getName());
		subjectDTO.setTeacherDTO(teacherDTO);
		subjectDTO.setDegreecourseDTO(degreeCourseDTO);
		
		ExamDTO newExamDTO = new ExamDTO();
		newExamDTO.setIdexam(subscribedExam.getIdexam());
		newExamDTO.setIdExamType(subscribedExam.getExamType().getIdexamType());
		newExamDTO.setDate(subscribedExam.getDate());
		newExamDTO.setClassroom(classroomDTO);
		newExamDTO.setSubject(subjectDTO);
		
		return newExamDTO;
	}*/
	
	public List<ExamDTO> getAllAvailableByTeacher(int idteacher) {

			List<Exam> exams = examRepository.getAllAvailableByTeacher(idteacher);
			List<ExamDTO> examDTOs = new ArrayList<ExamDTO>();
			for(int i=0; i<exams.size(); i++) {
				
				BuildingDTO buildingDTO = new BuildingDTO();
				buildingDTO.setId(exams.get(i).getClassroom().getBuilding().getIdbuilding());
				buildingDTO.setName(exams.get(i).getClassroom().getBuilding().getName());
				buildingDTO.setAddress(exams.get(i).getClassroom().getBuilding().getAddress());
				
				TeacherDTO teacherDTO = new TeacherDTO();
				teacherDTO.setIdteacher(exams.get(i).getSubject().getTeacher().getIduser());
				teacherDTO.setSurname(exams.get(i).getSubject().getTeacher().getUser().getName());
				teacherDTO.setName(exams.get(i).getSubject().getTeacher().getUser().getSurname());
				
				DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
				degreeCourseDTO.setIdcourse(exams.get(i).getSubject().getDegreeCourse().getIddegreeCourse());
				
				TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
				typeDegreeCourseDTO.setIdtypeDegreeCourse(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
				typeDegreeCourseDTO.setName(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
				
				CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
				courseTypeDTO.setIdcourseType(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
				courseTypeDTO.setDescription(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
				courseTypeDTO.setCfu(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				courseTypeDTO.setDuration(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				typeDegreeCourseDTO.setCourseType(courseTypeDTO);
				
				degreeCourseDTO.setCfu(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
				AcademicYearDTO academicYearDTO = new AcademicYearDTO();
				academicYearDTO.setIdacademicYear(exams.get(i).getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
				academicYearDTO.setYear(exams.get(i).getSubject().getDegreeCourse().getAcademicYear().getYear());
				degreeCourseDTO.setAcademicYear(academicYearDTO);
				degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
				
				ClassroomDTO classroomDTO = new ClassroomDTO();
				classroomDTO.setId(exams.get(i).getClassroom().getIdclassroom());
				classroomDTO.setName(exams.get(i).getClassroom().getName());
				classroomDTO.setSeats(exams.get(i).getClassroom().getSeats());
				classroomDTO.setBuilding(buildingDTO);
				
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(exams.get(i).getSubject().getIdsubject());
				subjectDTO.setName(exams.get(i).getSubject().getTypeSubject().getName());
				subjectDTO.setTeacherDTO(teacherDTO);
				subjectDTO.setDegreecourseDTO(degreeCourseDTO);
				
				ExamStatusDTO statusDTO = new ExamStatusDTO();
				statusDTO.setIdstatus(exams.get(i).getExamStatus().getIdexamStatus());
				statusDTO.setDescription(exams.get(i).getExamStatus().getDescription());
				
				ExamDTO examDTO = new ExamDTO();
				
				examDTO.setIdexam(exams.get(i).getIdexam());
				examDTO.setClassroom(classroomDTO);
				examDTO.setSubject(subjectDTO);
				examDTO.setDate(exams.get(i).getDate());
				examDTO.setStatus(statusDTO);
				
				ExamTypeDTO examTypeDTO = new ExamTypeDTO();
				examTypeDTO.setIdexamType(exams.get(i).getExamType().getIdexamType());
				examTypeDTO.setDescription(exams.get(i).getExamType().getDescription());
				
				examDTO.setExamtype(examTypeDTO);
				
				List<StudentHasExam> enrollments = studentHasExamRepository.getExamStudents(exams.get(i).getIdexam());
				
				List<ExamEnrollmentDTO> enrollmentsDTO = new ArrayList<ExamEnrollmentDTO>();
				
				for(int j=0; j<enrollments.size(); j++) {
					
					ExamEnrollmentDTO enrollmentDTO = new ExamEnrollmentDTO();
					
					StudentDTO studentDTO = new StudentDTO();
					studentDTO.setIdstudent(enrollments.get(j).getStudent().getIduser());
					studentDTO.setName(enrollments.get(j).getStudent().getUser().getName());
					studentDTO.setSurname(enrollments.get(j).getStudent().getUser().getSurname());
					studentDTO.setDateBirth(enrollments.get(j).getStudent().getUser().getDateBirth());
					studentDTO.setEmail(enrollments.get(j).getStudent().getUser().getEmail());
					
					enrollmentDTO.setStudent(studentDTO);
					enrollmentDTO.setDate(enrollments.get(j).getDate());
					try {
						enrollmentDTO.setGrade(enrollments.get(j).getGrade());
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
					ResultDTO resultDTO = new ResultDTO();
					resultDTO.setIdresult(enrollments.get(j).getResult().getIdresult());
					resultDTO.setDescription(enrollments.get(j).getResult().getDescription());
					
					enrollmentDTO.setResult(resultDTO);
					
					enrollmentsDTO.add(enrollmentDTO);
					
				}
				
				Collections.sort(enrollmentsDTO, new Comparator<ExamEnrollmentDTO>() {
                    @Override
                    public int compare(ExamEnrollmentDTO o1, ExamEnrollmentDTO o2) {
                        return o1.getStudent().getSurname().compareTo(o2.getStudent().getSurname());
                    }
                });
				
				examDTO.setEnrollments(enrollmentsDTO);
				
				examDTOs.add(examDTO);
			
			}
			return examDTOs;
			
		
	}
	
	public void bookStudent(int idstudent, int idexam) {
		
		StudentHasExamId id = new StudentHasExamId();
		id.setIdexam(idexam);
		id.setIduser(idstudent);
		
		Result result = new Result();
		result.setIdresult(1);
		
		StudentHasExam enrollment = new StudentHasExam();
		enrollment.setId(id);
		enrollment.setResult(result);
		enrollment.setDate(new Date());
		
		this.studentHasExamRepository.save(enrollment);
	}
	
	public List<ExamDTO> getAllAvailableByStudent(int idstudent) {

		List<Exam> exams = examRepository.getAllAvailableByStudent(idstudent);
		List<ExamDTO> examDTOs = new ArrayList<ExamDTO>();
		for(int i=0; i<exams.size(); i++) {
			
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(exams.get(i).getClassroom().getBuilding().getIdbuilding());
			buildingDTO.setName(exams.get(i).getClassroom().getBuilding().getName());
			buildingDTO.setAddress(exams.get(i).getClassroom().getBuilding().getAddress());
			
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(exams.get(i).getSubject().getTeacher().getIduser());
			teacherDTO.setSurname(exams.get(i).getSubject().getTeacher().getUser().getName());
			teacherDTO.setName(exams.get(i).getSubject().getTeacher().getUser().getSurname());
			
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(exams.get(i).getSubject().getDegreeCourse().getIddegreeCourse());
			
			TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
			typeDegreeCourseDTO.setIdtypeDegreeCourse(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
			typeDegreeCourseDTO.setName(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
			
			CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
			courseTypeDTO.setIdcourseType(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
			courseTypeDTO.setDescription(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
			courseTypeDTO.setCfu(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			courseTypeDTO.setDuration(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			typeDegreeCourseDTO.setCourseType(courseTypeDTO);
			
			degreeCourseDTO.setCfu(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(exams.get(i).getSubject().getDegreeCourse().getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(exams.get(i).getSubject().getDegreeCourse().getAcademicYear().getYear());
			degreeCourseDTO.setAcademicYear(academicYearDTO);
			degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(exams.get(i).getClassroom().getIdclassroom());
			classroomDTO.setName(exams.get(i).getClassroom().getName());
			classroomDTO.setSeats(exams.get(i).getClassroom().getSeats());
			classroomDTO.setBuilding(buildingDTO);
			
			SubjectDTO subjectDTO = new SubjectDTO();
			subjectDTO.setId(exams.get(i).getSubject().getIdsubject());
			subjectDTO.setName(exams.get(i).getSubject().getTypeSubject().getName());
			subjectDTO.setTeacherDTO(teacherDTO);
			subjectDTO.setDegreecourseDTO(degreeCourseDTO);
			subjectDTO.setCfu(exams.get(i).getSubject().getCfu());
			
			ExamStatusDTO statusDTO = new ExamStatusDTO();
			statusDTO.setIdstatus(exams.get(i).getExamStatus().getIdexamStatus());
			statusDTO.setDescription(exams.get(i).getExamStatus().getDescription());
			
			ExamDTO examDTO = new ExamDTO();
			
			examDTO.setIdexam(exams.get(i).getIdexam());
			examDTO.setClassroom(classroomDTO);
			examDTO.setSubject(subjectDTO);
			examDTO.setDate(exams.get(i).getDate());
			examDTO.setStatus(statusDTO);
			
			ExamTypeDTO examTypeDTO = new ExamTypeDTO();
			examTypeDTO.setIdexamType(exams.get(i).getExamType().getIdexamType());
			examTypeDTO.setDescription(exams.get(i).getExamType().getDescription());
			
			examDTO.setExamtype(examTypeDTO);
			
			examDTOs.add(examDTO);
		
		}
		return examDTOs;
		
	
}
	
	public List<ExamEnrollmentDTO> getRecordBook(int idstudent) {

		List<Subject> subjects = subjectRepository.getCoursesByIdStudent(idstudent);
		List<ExamEnrollmentDTO> examEnrollmentDTOs = new ArrayList<ExamEnrollmentDTO>();
		for(int i=0; i<subjects.size(); i++) {
			StudentHasExam enrollment = studentHasExamRepository.getPassedExam(idstudent, subjects.get(i).getIdsubject());
			if(enrollment != null) {
				BuildingDTO buildingDTO = new BuildingDTO();
				buildingDTO.setId(enrollment.getExam().getClassroom().getBuilding().getIdbuilding());
				buildingDTO.setName(enrollment.getExam().getClassroom().getBuilding().getName());
				buildingDTO.setAddress(enrollment.getExam().getClassroom().getBuilding().getAddress());
				
				ClassroomDTO classroomDTO = new ClassroomDTO();
				classroomDTO.setId(enrollment.getExam().getClassroom().getIdclassroom());
				classroomDTO.setName(enrollment.getExam().getClassroom().getName());
				classroomDTO.setSeats(enrollment.getExam().getClassroom().getSeats());
				classroomDTO.setBuilding(buildingDTO);
				
				ExamTypeDTO examType = new ExamTypeDTO();
				examType.setIdexamType(enrollment.getExam().getExamType().getIdexamType());
				examType.setDescription(enrollment.getExam().getExamType().getDescription());
				
				TeacherDTO teacherDTO = new TeacherDTO();
				teacherDTO.setIdteacher(enrollment.getExam().getSubject().getTeacher().getIduser());
				teacherDTO.setSurname(enrollment.getExam().getSubject().getTeacher().getUser().getName());
				teacherDTO.setName(enrollment.getExam().getSubject().getTeacher().getUser().getSurname());
				
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(enrollment.getExam().getSubject().getIdsubject());
				subjectDTO.setName(enrollment.getExam().getSubject().getTypeSubject().getName());
				subjectDTO.setCfu(enrollment.getExam().getSubject().getCfu());
				subjectDTO.setTeacherDTO(teacherDTO);

				ExamStatusDTO status = new ExamStatusDTO();
				status.setIdstatus(enrollment.getExam().getExamStatus().getIdexamStatus());
				status.setDescription(enrollment.getExam().getExamStatus().getDescription());
				
				ExamDTO examDTO = new ExamDTO();
				examDTO.setIdexam(enrollment.getExam().getIdexam());
				examDTO.setDate(enrollment.getExam().getDate());
				examDTO.setClassroom(classroomDTO);
				examDTO.setExamtype(examType);
				examDTO.setSubject(subjectDTO);
				examDTO.setStatus(status);
				
				ResultDTO result = new ResultDTO();
				result.setIdresult(enrollment.getResult().getIdresult());
				result.setDescription(enrollment.getResult().getDescription());
				
				ExamEnrollmentDTO enrollmentDTO = new ExamEnrollmentDTO();
				enrollmentDTO.setExam(examDTO);
				enrollmentDTO.setDate(enrollment.getDate());
				enrollmentDTO.setGrade(enrollment.getGrade());
				enrollmentDTO.setResult(result);
				
				examEnrollmentDTOs.add(enrollmentDTO);
				
			} else {
				
				TeacherDTO teacherDTO = new TeacherDTO();
				teacherDTO.setIdteacher(subjects.get(i).getTeacher().getIduser());
				teacherDTO.setSurname(subjects.get(i).getTeacher().getUser().getName());
				teacherDTO.setName(subjects.get(i).getTeacher().getUser().getSurname());
				
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(subjects.get(i).getIdsubject());
				subjectDTO.setName(subjects.get(i).getTypeSubject().getName());
				subjectDTO.setCfu(subjects.get(i).getCfu());
				subjectDTO.setTeacherDTO(teacherDTO);

				ExamStatusDTO status = new ExamStatusDTO();
				status.setDescription("Non conseguito");
				
				ExamDTO examDTO = new ExamDTO();
				examDTO.setSubject(subjectDTO);
				examDTO.setStatus(status);
				
				ExamEnrollmentDTO enrollmentDTO = new ExamEnrollmentDTO();
				enrollmentDTO.setExam(examDTO);
				
				examEnrollmentDTOs.add(enrollmentDTO);
			}
			
		
		}
		return examEnrollmentDTOs;
		
	
}
	
	public List<ExamEnrollmentDTO> getAllEnrollments(int idstudent) {

		
			List<StudentHasExam> enrollments = studentHasExamRepository.getAllEnrollment(idstudent);
			List<ExamEnrollmentDTO> examEnrollmentDTOs = new ArrayList<ExamEnrollmentDTO>();
			for(int i=0; i<enrollments.size(); i++) {
				BuildingDTO buildingDTO = new BuildingDTO();
				buildingDTO.setId(enrollments.get(i).getExam().getClassroom().getBuilding().getIdbuilding());
				buildingDTO.setName(enrollments.get(i).getExam().getClassroom().getBuilding().getName());
				buildingDTO.setAddress(enrollments.get(i).getExam().getClassroom().getBuilding().getAddress());
				
				ClassroomDTO classroomDTO = new ClassroomDTO();
				classroomDTO.setId(enrollments.get(i).getExam().getClassroom().getIdclassroom());
				classroomDTO.setName(enrollments.get(i).getExam().getClassroom().getName());
				classroomDTO.setSeats(enrollments.get(i).getExam().getClassroom().getSeats());
				classroomDTO.setBuilding(buildingDTO);
				
				ExamTypeDTO examType = new ExamTypeDTO();
				examType.setIdexamType(enrollments.get(i).getExam().getExamType().getIdexamType());
				examType.setDescription(enrollments.get(i).getExam().getExamType().getDescription());
				
				TeacherDTO teacherDTO = new TeacherDTO();
				teacherDTO.setIdteacher(enrollments.get(i).getExam().getSubject().getTeacher().getIduser());
				teacherDTO.setSurname(enrollments.get(i).getExam().getSubject().getTeacher().getUser().getName());
				teacherDTO.setName(enrollments.get(i).getExam().getSubject().getTeacher().getUser().getSurname());
				
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(enrollments.get(i).getExam().getSubject().getIdsubject());
				subjectDTO.setName(enrollments.get(i).getExam().getSubject().getTypeSubject().getName());
				subjectDTO.setCfu(enrollments.get(i).getExam().getSubject().getCfu());
				subjectDTO.setTeacherDTO(teacherDTO);

				ExamStatusDTO status = new ExamStatusDTO();
				status.setIdstatus(enrollments.get(i).getExam().getExamStatus().getIdexamStatus());
				status.setDescription(enrollments.get(i).getExam().getExamStatus().getDescription());
				
				ExamDTO examDTO = new ExamDTO();
				examDTO.setIdexam(enrollments.get(i).getExam().getIdexam());
				examDTO.setDate(enrollments.get(i).getExam().getDate());
				examDTO.setClassroom(classroomDTO);
				examDTO.setExamtype(examType);
				examDTO.setSubject(subjectDTO);
				examDTO.setStatus(status);
				
				ResultDTO result = new ResultDTO();
				result.setIdresult(enrollments.get(i).getResult().getIdresult());
				result.setDescription(enrollments.get(i).getResult().getDescription());
				
				ExamEnrollmentDTO enrollmentDTO = new ExamEnrollmentDTO();
				enrollmentDTO.setExam(examDTO);
				enrollmentDTO.setDate(enrollments.get(i).getDate());
				if(enrollments.get(i).getGrade() != null) {
					enrollmentDTO.setGrade(enrollments.get(i).getGrade());
				} else {
					enrollmentDTO.setGrade(0);
				}
				
				enrollmentDTO.setResult(result);
				
				examEnrollmentDTOs.add(enrollmentDTO);
				
			}
				return examEnrollmentDTOs;
				
			} 
		
	
	@Transactional
	public void insertGrade(List<ExamEnrollmentDTO> enrollmentDTOs, int idexam) {
		
		for(int i=0; i<enrollmentDTOs.size(); i++) {
			StudentHasExam enrollment = new StudentHasExam();
			
			StudentHasExamId id = new StudentHasExamId();
			
			id.setIdexam(idexam);
			id.setIduser(enrollmentDTOs.get(i).getStudent().getIdstudent());
			
			Result result = new Result();
			result.setIdresult(enrollmentDTOs.get(i).getResult().getIdresult());
			
			enrollment.setId(id);
			enrollment.setResult(result);
			enrollment.setDate(enrollmentDTOs.get(i).getDate());
			enrollment.setGrade(enrollmentDTOs.get(i).getGrade());
			studentHasExamRepository.save(enrollment);
		}
		
		examRepository.closeExam(idexam);
		
	}

	
}
