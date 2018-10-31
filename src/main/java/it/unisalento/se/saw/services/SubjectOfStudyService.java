package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.ISubjectOfStudyService;
import it.unisalento.se.saw.domain.Subjectofstudy;
import it.unisalento.se.saw.exceptions.SubjectOfStudyNotFoundException;
import it.unisalento.se.saw.repositories.SubjectOfStudyRepository;

@Service
public class SubjectOfStudyService implements ISubjectOfStudyService{
	
	@Autowired
	SubjectOfStudyRepository subjectOfStudyRepository;
	
	@Transactional(readOnly=true)
	public List<Subjectofstudy> getAll(){
		return subjectOfStudyRepository.findAll();	
	}
	
	@Transactional(rollbackFor=SubjectOfStudyNotFoundException.class)
	public Subjectofstudy getById(int id) throws SubjectOfStudyNotFoundException {
		
		try {
			return subjectOfStudyRepository.findById(id).get();
		} catch (Exception e) {
			throw new SubjectOfStudyNotFoundException();
		}
	}
	
	@Transactional
	public Subjectofstudy edit(Subjectofstudy subjectOfStudy) throws SubjectOfStudyNotFoundException {
		try {
			subjectOfStudyRepository.findById(subjectOfStudy.getIdsubjectofstudy()).get();
			return subjectOfStudyRepository.save(subjectOfStudy);
		} catch (Exception e) {
			throw new SubjectOfStudyNotFoundException();
		}
	}
	
	@Transactional
	public Subjectofstudy save(Subjectofstudy subjectOfStudy) {
		return subjectOfStudyRepository.save(subjectOfStudy);
	}
	
	@Transactional
	public void delete(int id) throws SubjectOfStudyNotFoundException {
		// TODO Auto-generated method stub
		try {
			Subjectofstudy subjectOfStudy = subjectOfStudyRepository.findById(id).get();
			subjectOfStudyRepository.delete(subjectOfStudy);
		} catch (Exception e) {
			// TODO: handle exception
			throw new SubjectOfStudyNotFoundException();
		}

	}

}
