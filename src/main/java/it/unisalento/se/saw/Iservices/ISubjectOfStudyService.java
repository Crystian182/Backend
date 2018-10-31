package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.domain.Subjectofstudy;
import it.unisalento.se.saw.exceptions.SubjectOfStudyNotFoundException;

public interface ISubjectOfStudyService {
	
	public List<Subjectofstudy> getAll();
	public Subjectofstudy getById(int id) throws SubjectOfStudyNotFoundException;
	public Subjectofstudy edit(Subjectofstudy subjectOfStudy) throws SubjectOfStudyNotFoundException;
	public Subjectofstudy save(Subjectofstudy subjectOfStudy);
	public void delete(int id) throws SubjectOfStudyNotFoundException;

}
