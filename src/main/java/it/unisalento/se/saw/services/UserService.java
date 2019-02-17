package it.unisalento.se.saw.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.domain.StudentHasDegreeCourse;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.Term;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.EnrollmentStatusDTO;
import it.unisalento.se.saw.dto.StudentDTO;
import it.unisalento.se.saw.dto.StudentHasDegreeCourseDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.WrongCredentialsException;
import it.unisalento.se.saw.repositories.StudentHasDegreeCourseRepository;
import it.unisalento.se.saw.repositories.TeacherRepository;
import it.unisalento.se.saw.repositories.TermRepository;
import it.unisalento.se.saw.repositories.UserRepository;

@Service
public class UserService implements IUserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TeacherRepository teacherRepository;

	private static final String location = "C:\\Users\\Federico\\Desktop\\Computer Engineering\\Backend\\Backend\\src\\main\\resources\\users\\";

	@Autowired
	StudentHasDegreeCourseRepository studentHasDegreeCourseRepository;
	
	@Autowired
	TermRepository termRepository;
	
	@Transactional(readOnly=true)
	public List<User> getAll(){
		return userRepository.findAll();	
	}
	
	@Transactional(readOnly=true)
	public String getPropic(int iduser) throws IOException{
		User user = userRepository.getOne(iduser);
		try {
			return this.getUserImage(user.getFile().getIdfile());
		} catch (Exception e) {
			return "null";
		}
	}
	
	@Transactional
	public UserDTO save(UserDTO userDTO) throws IOException {
		User user = new User();
		user.setIduser(userDTO.getIduser());
		user.setName(userDTO.getName());
		user.setSurname(userDTO.getSurname());
		user.setDateBirth(userDTO.getDateBirth());
		user.setCitizenship(userDTO.getCitizenship());
		user.setDomicile(userDTO.getDomicile());
		user.setPhone(userDTO.getPhone());
		user.setResidence(userDTO.getResidence());
		user.setSex(userDTO.getSex());
		user.setSsn(userDTO.getSsn());
		user.setEmail(userDTO.getEmail());
		user.setPlaceBirth(userDTO.getPlaceBirth());
		user.setPassword(userRepository.getOne(userDTO.getIduser()).getPassword());
		
		it.unisalento.se.saw.domain.File file = new it.unisalento.se.saw.domain.File();
		file.setIdfile(userRepository.getOne(userDTO.getIduser()).getFile().getIdfile());
		user.setFile(file);
		User newUser = userRepository.save(user);
		UserDTO newuserDTO = new UserDTO();
		newuserDTO.setIduser(newUser.getIduser());
		newuserDTO.setName(newUser.getName());
		newuserDTO.setSurname(newUser.getSurname());
		newuserDTO.setDateBirth(newUser.getDateBirth());
		newuserDTO.setCitizenship(newUser.getCitizenship());
		newuserDTO.setDomicile(newUser.getDomicile());
		newuserDTO.setPhone(newUser.getPhone());
		newuserDTO.setResidence(newUser.getResidence());
		newuserDTO.setSex(newUser.getSex());
		newuserDTO.setSsn(newUser.getSsn());
		newuserDTO.setEmail(newUser.getEmail());
		newuserDTO.setPlaceBirth(newUser.getPlaceBirth());
		newuserDTO.setType(userDTO.getType());
		newuserDTO.setPropic(this.getUserImage(newUser.getFile().getIdfile()));
		return newuserDTO;
	}
	
	@Transactional
	public UserDTO getById(int id) throws UserNotFoundException {
		try {
			User user = userRepository.getOne(id);
			UserDTO userDTO = new UserDTO();
			userDTO.setIduser(user.getIduser());
			userDTO.setName(user.getName());
			userDTO.setSurname(user.getSurname());
			userDTO.setDateBirth(user.getDateBirth());
			userDTO.setCitizenship(user.getCitizenship());
			userDTO.setDomicile(user.getDomicile());
			userDTO.setPhone(user.getPhone());
			userDTO.setResidence(user.getResidence());
			userDTO.setSex(user.getSex());
			userDTO.setSsn(user.getSsn());
			return userDTO;
		} catch (Exception e) {
			// TODO: handle exception
			throw new UserNotFoundException();
		}
	}
	
	@Transactional
	public List<TeacherDTO> getTeachers() throws UserNotFoundException {
		List<Teacher> teachers = teacherRepository.findAll();
		List<TeacherDTO> teacherDTOs = new ArrayList<TeacherDTO>();
		for(int i=0; i<teachers.size(); i++) {
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(teachers.get(i).getIduser());
			teacherDTO.setSurname(teachers.get(i).getUser().getSurname());
			teacherDTO.setName(teachers.get(i).getUser().getName());
			teacherDTO.setDateBirth(teachers.get(i).getUser().getDateBirth());
			teacherDTO.setDomicile(teachers.get(i).getUser().getDomicile());
			teacherDTO.setPlaceBirth(teachers.get(i).getUser().getPlaceBirth());
			teacherDTO.setResidence(teachers.get(i).getUser().getResidence());
			teacherDTOs.add(teacherDTO);
		}
		return teacherDTOs;
	}
	
	public TeacherDTO getTeacherById(int id) throws UserNotFoundException {
		try {
			Teacher teacher = teacherRepository.getOne(id);
			TeacherDTO teacherDTO = new TeacherDTO();
			teacherDTO.setIdteacher(teacher.getIduser());
			teacherDTO.setName(teacher.getUser().getName());
			teacherDTO.setSurname(teacher.getUser().getSurname());
			teacherDTO.setDateBirth(teacher.getUser().getDateBirth());
			teacherDTO.setDomicile(teacher.getUser().getDomicile());
			teacherDTO.setPlaceBirth(teacher.getUser().getPlaceBirth());
			teacherDTO.setResidence(teacher.getUser().getResidence());
			return teacherDTO;
		} catch (Exception e) {
			// TODO: handle exception
			throw new UserNotFoundException();
		}
	}
	/*
	
	@Transactional(rollbackFor=UserNotFoundException.class)
	public void removeUserById(int id) throws UserNotFoundException {
		
		
		try {
			User user = userRepository.getOne(id);
			userRepository.delete(user);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}
		
	}*/
	
	/*@Transactional(rollbackFor=UserNotFoundException.class)
	public User checkUser(String email) throws UserNotFoundException {
		
		try {
			User user = userRepository.findByEmail(email);
			return user;
		} catch (Exception e) {
			throw new UserNotFoundException();
		}
		
	}*/
	
	@Transactional(rollbackFor=UserNotFoundException.class)
	public UserDTO login(String email, String token) throws UserNotFoundException {
		
		User user = userRepository.checkUser(email);
		
		try {
			
				UserDTO userDTO = new UserDTO();
				userDTO.setIduser(user.getIduser());
				userDTO.setName(user.getName());
				userDTO.setSurname(user.getSurname());
				userDTO.setEmail(user.getEmail());
				userDTO.setDateBirth(user.getDateBirth());
				userDTO.setPlaceBirth(user.getPlaceBirth());
				userDTO.setResidence(user.getResidence());
				userDTO.setDomicile(user.getDomicile());
				userDTO.setPhone(user.getPhone());
				userDTO.setSsn(user.getSsn());
				userDTO.setSex(user.getSex());
				userDTO.setCitizenship(user.getCitizenship());
				userDTO.setToken(token);
				if(userRepository.isStudent(user.getIduser())) {
					userDTO.setType("student");
					userDTO.setPropic(this.getUserImage(user.getFile().getIdfile()));
				} else if (userRepository.isTeacher(user.getIduser())) {
					userDTO.setType("teacher");
					userDTO.setPropic(this.getUserImage(user.getFile().getIdfile()));
				} else {
					userDTO.setType("employee");
					userDTO.setPropic(this.getUserImage(user.getFile().getIdfile()));
				}
				return userDTO;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new UserNotFoundException();
		}
		
	}
	
	public String getUserImage(int idfile) throws IOException {
		BufferedImage img = ImageIO.read(new File(location + idfile));             
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", baos);
		baos.flush();
		Base64 base = new Base64(false);
		String encodedImage = base.encodeToString(baos.toByteArray());
		baos.close();
		return encodedImage = java.net.URLEncoder.encode(encodedImage, "ISO-8859-1");
	}
	
	@Transactional(rollbackFor=UserNotFoundException.class)
	public User getUser(int idUser) throws UserNotFoundException {
		try {
			return userRepository.findById(idUser).get();
		} catch (Exception e) {
			// TODO: handle exception
			throw new UserNotFoundException();
		}
	}
	
	/*@Transactional
	public List<User> getColleagues(User user, String keyword) throws UserNotFoundException {
		try{
			User existingUser = userRepository.findById(user.getIduser()).get();
			return userRepository.getColleagues(existingUser.getCourse().getIdcourse(), existingUser.getIduser(), keyword);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}
	}*/
	
	/*@Transactional
	public boolean login(String email, String password){
		return userRepository.login(email, password);
	}*/
	
	@Transactional(rollbackFor=UserNotFoundException.class)
	public StudentHasDegreeCourseDTO getInfo(int idStudent) throws UserNotFoundException {
		
		StudentHasDegreeCourse studentHasDegreeCourse = studentHasDegreeCourseRepository.getStudentCourse(idStudent);
		
		StudentHasDegreeCourseDTO studentHasDegreeCourseDTO = new StudentHasDegreeCourseDTO();

		AcademicYearDTO academicYearDTO = new AcademicYearDTO();
		academicYearDTO.setIdacademicYear(studentHasDegreeCourse.getDegreeCourse().getAcademicYear().getIdacademicYear());
		academicYearDTO.setYear(studentHasDegreeCourse.getDegreeCourse().getAcademicYear().getYear());
		List<Term> terms = termRepository.getByAcademicYear(academicYearDTO.getIdacademicYear());
		List<TermDTO> termDTOs= new ArrayList<TermDTO>();
		for(int k=0; k<terms.size(); k++) {
			TermDTO termDTO = new TermDTO();
			termDTO.setIdterm(terms.get(k).getIdterm());
			termDTO.setStart(terms.get(k).getStart());
			termDTO.setEnd(terms.get(k).getEnd());
			termDTOs.add(termDTO);
		}
		academicYearDTO.setTerms(termDTOs);	
		CourseTypeDTO courseType = new CourseTypeDTO();
		courseType.setIdcourseType(studentHasDegreeCourse.getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
		courseType.setDescription(studentHasDegreeCourse.getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
		courseType.setCfu(studentHasDegreeCourse.getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
		courseType.setDuration(studentHasDegreeCourse.getDegreeCourse().getTypeDegreeCourse().getCourseType().getDuration());
		TypeDegreeCourseDTO typeDegreeCourse = new TypeDegreeCourseDTO();
		typeDegreeCourse.setIdtypeDegreeCourse(studentHasDegreeCourse.getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
		typeDegreeCourse.setName(studentHasDegreeCourse.getDegreeCourse().getTypeDegreeCourse().getName());
		typeDegreeCourse.setCourseType(courseType);
		DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
		degreeCourseDTO.setIdcourse(studentHasDegreeCourse.getDegreeCourse().getIddegreeCourse());
		degreeCourseDTO.setName(studentHasDegreeCourse.getDegreeCourse().getTypeDegreeCourse().getName());
		degreeCourseDTO.setAcademicYear(academicYearDTO);
		degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourse);
		EnrollmentStatusDTO enrollmentStatusDTO = new EnrollmentStatusDTO();
		enrollmentStatusDTO.setIdenrollmentStatus(studentHasDegreeCourse.getEnrollmentStatus().getIdenrollmentStatus());
		enrollmentStatusDTO.setDescription(studentHasDegreeCourse.getEnrollmentStatus().getDescription());
		
		studentHasDegreeCourseDTO.setDegreeCourse(degreeCourseDTO);
		studentHasDegreeCourseDTO.setDate(studentHasDegreeCourse.getDate());
		studentHasDegreeCourseDTO.setEnrollmentStatus(enrollmentStatusDTO);
		
		
		return studentHasDegreeCourseDTO;
		
	}
}
