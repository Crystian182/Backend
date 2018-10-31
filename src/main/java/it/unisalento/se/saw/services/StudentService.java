package it.unisalento.se.saw.services;

import org.springframework.stereotype.Service;

import it.unisalento.se.saw.models.Student;

@Service
public class StudentService {

	public Student getStudentById(int id) {
		Student student = new Student();
		student.setName("PIPPO");
		return student;
	}
}
