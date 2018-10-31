package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IExamService;
import it.unisalento.se.saw.domain.Exam;
import it.unisalento.se.saw.domain.ExamHasUser;
import it.unisalento.se.saw.domain.ExamHasUserId;
import it.unisalento.se.saw.domain.Result;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.ExamNotFoundException;
import it.unisalento.se.saw.repositories.ExamHasUserRepository;
import it.unisalento.se.saw.repositories.ExamRepository;

@Service
public class ExamService implements IExamService{
	
	@Autowired
	ExamRepository examRepository;
	
	@Autowired
	ExamHasUserRepository examHasUserRepository;
	

	@Transactional(readOnly=true)
	public List<Exam> getAll() {
		// TODO Auto-generated method stub
		return examRepository.findAll();
	}

	@Transactional(rollbackFor=ExamNotFoundException.class)
	public Exam getById(int id) throws ExamNotFoundException {
		try {
			return examRepository.findById(id).get();
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
	public Exam save(Exam exam) {
		// TODO Auto-generated method stub
		return examRepository.save(exam);
	}

	@Transactional
	public Exam edit(Exam exam) throws ExamNotFoundException {
		try {
			examRepository.findById(exam.getIdexam()).get();
			return examRepository.save(exam);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ExamNotFoundException();
		}
	}

	@Transactional
	public List<Exam> getAllByCourse(int idcourse) throws ExamNotFoundException {
		try {
			return examRepository.findAllByCourse(idcourse);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ExamNotFoundException();
		}
		
	}

	@Transactional
	public Exam subscribe(Exam exam) throws ExamNotFoundException {
		try {
			return examRepository.save(exam);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ExamNotFoundException();
		}
	}
	
	@Transactional
	public Exam subscribe(int idexam, User user) {
		// TODO Auto-generated method stub
		ExamHasUserId examHasUserId = new ExamHasUserId();
		examHasUserId.setUserIduser(user.getIduser());
		examHasUserId.setExamIdexam(idexam);
		
		Exam exam = new Exam();
		exam.setIdexam(idexam);
		
		Result result = new Result();
		result.setIdresult(1);
		result.setDescription("prenotato");
		
		ExamHasUser examHasUser = new ExamHasUser();
		examHasUser.setId(examHasUserId);
		examHasUser.setUser(user);
		examHasUser.setDateregistration(new java.util.Date(System.currentTimeMillis()));
		examHasUser.setResult(result);
		
		examHasUserRepository.save(examHasUser);
		return examRepository.getOne(idexam);
	}
	
}
