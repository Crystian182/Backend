package it.unisalento.se.saw.Iservices;

import java.util.List;
import java.util.Set;

import it.unisalento.se.saw.domain.Exam;
import it.unisalento.se.saw.domain.ExamHasUser;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.ExamNotFoundException;

public interface IExamService {
	public List<Exam> getAll();
	public Exam getById(int id) throws ExamNotFoundException;
	public List<Exam> getAllByCourse(int id) throws ExamNotFoundException;
	public void delete(int id)  throws ExamNotFoundException;
	public Exam save(Exam exam);
	public Exam edit(Exam exam)  throws ExamNotFoundException;
	public Exam subscribe(int idexam, User user);
}
