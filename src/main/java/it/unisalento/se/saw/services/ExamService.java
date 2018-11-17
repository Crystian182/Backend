package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IExamService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Exam;

import it.unisalento.se.saw.domain.ExamType;
import it.unisalento.se.saw.domain.Result;
import it.unisalento.se.saw.domain.Student;

import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.ExamDTO;
import it.unisalento.se.saw.dto.StudentDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.exceptions.ExamNotFoundException;
//import it.unisalento.se.saw.repositories.ExamEnrollmentRepository;
import it.unisalento.se.saw.repositories.ExamRepository;

@Service
public class ExamService implements IExamService{
	
	@Autowired
	ExamRepository examRepository;
	
	//@Autowired
	//ExamEnrollmentRepository examEnrollmentRepository;
	

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
			
			/*DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(exams.get(i).get);
			degreeCourseDTO.setIdCourseType(exams.get(i).getSubject().getDegreeCourse().getCourseType().getIdcourseType());
			degreeCourseDTO.setName(exams.get(i).getSubject().getDegreeCourse().getName());
			degreeCourseDTO.setDescription(exams.get(i).getSubject().getDegreeCourse().getDescription());
			degreeCourseDTO.setAcademicYear(exams.get(i).getSubject().getDegreeCourse().getAcademicYear());*/
			
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
			//subjectDTO.setDegreecourseDTO(degreeCourseDTO);
			
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
			
			/*DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(exam.getSubject().getDegreeCourse().getIddegreeCourse());
			degreeCourseDTO.setIdCourseType(exam.getSubject().getDegreeCourse().getCourseType().getIdcourseType());
			degreeCourseDTO.setName(exam.getSubject().getDegreeCourse().getName());
			degreeCourseDTO.setDescription(exam.getSubject().getDegreeCourse().getDescription());
			degreeCourseDTO.setAcademicYear(exam.getSubject().getDegreeCourse().getAcademicYear());*/
			
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
			//subjectDTO.setDegreecourseDTO(degreeCourseDTO);
			
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
		
		/*DegreeCourse degreeCourse = new DegreeCourse();
		degreeCourse.setIddegreeCourse(examDTO.getSubject().getDegreecourseDTO().getIdcourse());
		degreeCourse.setName(examDTO.getSubject().getDegreecourseDTO().getName());
		degreeCourse.setDescription(examDTO.getSubject().getDegreecourseDTO().getDescription());
		degreeCourse.setAcademicYear(examDTO.getSubject().getDegreecourseDTO().getAcademicYear());*/
		
		Subject subject = new Subject();
		subject.setIdsubject(examDTO.getSubject().getId());
		//subject.setTypeSubject(typeSubject);(examDTO.getSubject().getName());
	
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
		
	/*	DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
		degreeCourseDTO.setIdcourse(newExam.getSubject().getDegreeCourse().getIddegreeCourse());
		degreeCourseDTO.setIdCourseType(newExam.getSubject().getDegreeCourse().getCourseType().getIdcourseType());
		degreeCourseDTO.setName(newExam.getSubject().getDegreeCourse().getName());
		degreeCourseDTO.setDescription(newExam.getSubject().getDegreeCourse().getDescription());
		degreeCourseDTO.setAcademicYear(newExam.getSubject().getDegreeCourse().getAcademicYear());*/
		
		ClassroomDTO classroomDTO = new ClassroomDTO();
		classroomDTO.setId(newExam.getClassroom().getIdclassroom());
		classroomDTO.setName(newExam.getClassroom().getName());
		classroomDTO.setSeats(newExam.getClassroom().getSeats());
		classroomDTO.setBuilding(buildingDTO);
		
		/*SubjectDTO subjectDTO = new SubjectDTO();
		subjectDTO.setId(newExam.getSubject().getIdsubject());
		subjectDTO.setName(newExam.getSubject().getName());
		subjectDTO.setDescription(newExam.getSubject().getDescription());
		subjectDTO.setTeacherDTO(teacherDTO);
		subjectDTO.setDegreecourseDTO(degreeCourseDTO);*/
		
		ExamDTO newExamDTO = new ExamDTO();
		newExamDTO.setIdexam(newExam.getIdexam());
		newExamDTO.setClassroom(classroomDTO);
		//newExamDTO.setSubject(subjectDTO);
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
				
				/*DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
				degreeCourseDTO.setIdcourse(exams.get(i).getSubject().getDegreeCourse().getIddegreeCourse());
				degreeCourseDTO.setIdCourseType(exams.get(i).getSubject().getDegreeCourse().getCourseType().getIdcourseType());
				degreeCourseDTO.setName(exams.get(i).getSubject().getDegreeCourse().getName());
				degreeCourseDTO.setDescription(exams.get(i).getSubject().getDegreeCourse().getDescription());
				degreeCourseDTO.setAcademicYear(exams.get(i).getSubject().getDegreeCourse().getAcademicYear());
				
				ClassroomDTO classroomDTO = new ClassroomDTO();
				classroomDTO.setId(exams.get(i).getClassroom().getIdclassroom());
				classroomDTO.setName(exams.get(i).getClassroom().getName());
				classroomDTO.setSeats(exams.get(i).getClassroom().getSeats());
				classroomDTO.setBuilding(buildingDTO);
				
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(exams.get(i).getSubject().getIdsubject());
				subjectDTO.setName(exams.get(i).getSubject().getName());
				subjectDTO.setDescription(exams.get(i).getSubject().getDescription());
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
				
				examDTOs.add(examDTO);*/
			
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
	/*	ExamEnrollmentId examEnrollmentId = new ExamEnrollmentId();
		examEnrollmentId.setStudentSn(studentDTO.getIdstudent());
		examEnrollmentId.setExamIdexam(idexam);*/
		
		Exam exam = new Exam();
		exam.setIdexam(idexam);
		
		Result result = new Result();
		result.setIdresult(1);
		
		Student student = new Student();
		/*StudentId studentId = new StudentId();
		studentId.setIduser(studentDTO.getIdstudent());
		studentId.setSerialNumber(studentDTO.getSerialNumber());
		student.setId(studentId);
		User user = new User();
		user.setName(studentDTO.getName());
		user.setSurname(studentDTO.getSurname());
		student.setUser(user);
		
		ExamEnrollment examEnrollment = new ExamEnrollment();
		examEnrollment.setId(examEnrollmentId);
		examEnrollment.setStudent(student);
		examEnrollment.setDate(new java.util.Date(System.currentTimeMillis()));
		examEnrollment.setResult(result);
		
		examEnrollmentRepository.save(examEnrollment);*/
		
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
		
	/*	DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
		degreeCourseDTO.setIdcourse(exam.getSubject().getDegreeCourse().getIddegreeCourse());
		degreeCourseDTO.setIdCourseType(exam.getSubject().getDegreeCourse().getCourseType().getIdcourseType());
		degreeCourseDTO.setName(exam.getSubject().getDegreeCourse().getName());
		degreeCourseDTO.setDescription(exam.getSubject().getDegreeCourse().getDescription());
		degreeCourseDTO.setAcademicYear(exam.getSubject().getDegreeCourse().getAcademicYear());
		
		SubjectDTO subjectDTO = new SubjectDTO();
		subjectDTO.setId(exam.getSubject().getIdsubject());
		subjectDTO.setName(exam.getSubject().getName());
		subjectDTO.setDescription(exam.getSubject().getDescription());
		subjectDTO.setTeacherDTO(teacherDTO);
		subjectDTO.setDegreecourseDTO(degreeCourseDTO);*/
		
		ExamDTO newExamDTO = new ExamDTO();
		newExamDTO.setIdexam(subscribedExam.getIdexam());
		newExamDTO.setIdExamType(subscribedExam.getExamType().getIdexamType());
		newExamDTO.setDate(subscribedExam.getDate());
		newExamDTO.setClassroom(classroomDTO);
	//	newExamDTO.setSubject(subjectDTO);
		
		return newExamDTO;
	}

	
}
