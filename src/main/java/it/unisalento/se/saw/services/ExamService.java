package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.internal.ServiceProxyGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IExamService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Exam;

import it.unisalento.se.saw.domain.ExamType;
import it.unisalento.se.saw.domain.Result;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.domain.StudentHasExam;
import it.unisalento.se.saw.domain.StudentHasExamId;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.domain.TypeSubject;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.ExamDTO;
import it.unisalento.se.saw.dto.StudentDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.exceptions.ExamNotFoundException;
//import it.unisalento.se.saw.repositories.ExamEnrollmentRepository;
import it.unisalento.se.saw.repositories.ExamRepository;
import it.unisalento.se.saw.repositories.StudentHasExamRepository;

@Service
public class ExamService implements IExamService{
	
	@Autowired
	ExamRepository examRepository;
	
	@Autowired
	StudentHasExamRepository studentHasExamRepository;
	

	@Transactional(readOnly=true)
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
			degreeCourseDTO.setAcademicYear(exams.get(i).getSubject().getDegreeCourse().getAcademicYear());
			degreeCourseDTO.setCfu(exams.get(i).getSubject().getDegreeCourse().getCfu());
			
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
	}

	@Transactional(rollbackFor=ExamNotFoundException.class)
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
			degreeCourseDTO.setCfu(exam.getSubject().getDegreeCourse().getCfu());
			degreeCourseDTO.setAcademicYear(exam.getSubject().getDegreeCourse().getAcademicYear());
			
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
	}

	@Transactional
	public void delete(int id) throws ExamNotFoundException {
		try {
			Exam exam = examRepository.findById(id).get();
			examRepository.delete(exam);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ExamNotFoundException();
		}
		
	}

	@Transactional
	public ExamDTO save(ExamDTO examDTO) {
		Building building = new Building();
		building.setIdbuilding(examDTO.getClassroom().getBuilding().getId());
		building.setName(examDTO.getClassroom().getBuilding().getName());
		building.setAddress(examDTO.getClassroom().getBuilding().getAddress());
		building.setLat(examDTO.getClassroom().getBuilding().getLat());
		building.setLng(examDTO.getClassroom().getBuilding().getLng());
		
		Classroom classroom = new Classroom();
		classroom.setIdclassroom(examDTO.getClassroom().getId());
		classroom.setName(examDTO.getClassroom().getName());
		classroom.setSeats(examDTO.getClassroom().getSeats());
		classroom.setBuilding(building);
		
		TypeDegreeCourse typeDegreeCourse = new TypeDegreeCourse();
		typeDegreeCourse.setIdtypeDegreeCourse(examDTO.getSubject().getDegreecourseDTO().getTypeDegreeCourse().getIdtypeDegreeCourse());
		typeDegreeCourse.setName(examDTO.getSubject().getDegreecourseDTO().getTypeDegreeCourse().getName());
		
		CourseType courseType = new CourseType();
		courseType.setIdcourseType(examDTO.getSubject().getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getIdcourseType());
		courseType.setCfu(examDTO.getSubject().getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getCfu());
		courseType.setDuration(examDTO.getSubject().getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getDuration());
		courseType.setDescription(examDTO.getSubject().getDegreecourseDTO().getTypeDegreeCourse().getCourseType().getDescription());
		typeDegreeCourse.setCourseType(courseType);
		
		DegreeCourse degreeCourse = new DegreeCourse();
		degreeCourse.setIddegreeCourse(examDTO.getSubject().getDegreecourseDTO().getIdcourse());
		degreeCourse.setCfu(examDTO.getSubject().getDegreecourseDTO().getCfu());
		degreeCourse.setTypeDegreeCourse(typeDegreeCourse);
		degreeCourse.setAcademicYear(examDTO.getSubject().getDegreecourseDTO().getAcademicYear());
		
		Subject subject = new Subject();
		subject.setIdsubject(examDTO.getSubject().getId());
	
		ExamType examType = new ExamType();
		examType.setIdexamType(examDTO.getIdExamType());
		
		Exam exam = new Exam();
		try {
			exam.setIdexam(examDTO.getIdexam());
		} catch (Exception e) {
			// TODO: handle exception
		}
		exam.setClassroom(classroom);
		exam.setDate(examDTO.getDate());
		exam.setStart(examDTO.getStart());
		exam.setEnd(examDTO.getEnd());
		exam.setSubject(subject);
		exam.setExamType(examType);
		
		Exam newExam = examRepository.save(exam);
		
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId(newExam.getClassroom().getBuilding().getIdbuilding());
		buildingDTO.setName(newExam.getClassroom().getBuilding().getName());
		buildingDTO.setAddress(newExam.getClassroom().getBuilding().getAddress());
		
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setIdteacher(newExam.getSubject().getTeacher().getIduser());
		teacherDTO.setSurname(newExam.getSubject().getTeacher().getUser().getName());
		teacherDTO.setName(newExam.getSubject().getTeacher().getUser().getSurname());
		
		DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
		degreeCourseDTO.setIdcourse(newExam.getSubject().getDegreeCourse().getIddegreeCourse());
		
		TypeDegreeCourseDTO typeDegreeCourseDTO = new TypeDegreeCourseDTO();
		typeDegreeCourseDTO.setIdtypeDegreeCourse(newExam.getSubject().getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
		typeDegreeCourseDTO.setName(newExam.getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
		
		CourseTypeDTO courseTypeDTO = new CourseTypeDTO();
		courseTypeDTO.setIdcourseType(newExam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
		courseTypeDTO.setDescription(newExam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
		courseTypeDTO.setCfu(newExam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		courseTypeDTO.setDuration(newExam.getSubject().getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		typeDegreeCourseDTO.setCourseType(courseTypeDTO);
		
		degreeCourseDTO.setCfu(newExam.getSubject().getDegreeCourse().getCfu());
		degreeCourseDTO.setAcademicYear(newExam.getSubject().getDegreeCourse().getAcademicYear());
		
		ClassroomDTO classroomDTO = new ClassroomDTO();
		classroomDTO.setId(newExam.getClassroom().getIdclassroom());
		classroomDTO.setName(newExam.getClassroom().getName());
		classroomDTO.setSeats(newExam.getClassroom().getSeats());
		classroomDTO.setBuilding(buildingDTO);
		
		SubjectDTO subjectDTO = new SubjectDTO();
		subjectDTO.setId(newExam.getSubject().getIdsubject());
		subjectDTO.setName(newExam.getSubject().getTypeSubject().getName());
		subjectDTO.setTeacherDTO(teacherDTO);
		subjectDTO.setDegreecourseDTO(degreeCourseDTO);
		
		ExamDTO newExamDTO = new ExamDTO();
		newExamDTO.setIdexam(newExam.getIdexam());
		newExamDTO.setClassroom(classroomDTO);
		newExamDTO.setSubject(subjectDTO);
		newExamDTO.setDate(newExam.getDate());
		newExamDTO.setIdExamType(newExam.getExamType().getIdexamType());
		newExamDTO.setStart(newExam.getStart());
		newExamDTO.setEnd(newExam.getEnd());
		
		return newExamDTO;
		
	}

	@Transactional
	public List<ExamDTO> getAllByCourse(int idcourse) throws ExamNotFoundException {
		try {
			List<Exam> exams = examRepository.findAllByCourse(idcourse);
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
				
				degreeCourseDTO.setCfu(exams.get(i).getSubject().getDegreeCourse().getCfu());
				degreeCourseDTO.setAcademicYear(exams.get(i).getSubject().getDegreeCourse().getAcademicYear());
				degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourseDTO);
				
				ClassroomDTO classroomDTO = new ClassroomDTO();
				classroomDTO.setId(exams.get(i).getClassroom().getIdclassroom());
				classroomDTO.setName(exams.get(i).getClassroom().getName());
				classroomDTO.setSeats(exams.get(i).getClassroom().getSeats());
				classroomDTO.setBuilding(buildingDTO);
				
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(exams.get(i).getSubject().getIdsubject());
				subjectDTO.setName(exams.get(i).getSubject().getDegreeCourse().getTypeDegreeCourse().getName());
				subjectDTO.setDescription(examDTOs.get(i).getSubject().getDescription());
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
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new ExamNotFoundException();
		}
		
	}


	
	@Transactional
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
		degreeCourseDTO.setAcademicYear(exam.getSubject().getDegreeCourse().getAcademicYear());
		degreeCourseDTO.setCfu(exam.getSubject().getDegreeCourse().getCfu());
		
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
	}

	
}
