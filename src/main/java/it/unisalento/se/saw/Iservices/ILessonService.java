package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.domain.Lesson;
import it.unisalento.se.saw.exceptions.LessonNotFoundException;

public interface ILessonService {
	public List<Lesson> getAll();
	public Lesson getById(int id) throws LessonNotFoundException;
	public Lesson edit(Lesson lesson) throws LessonNotFoundException;
	public Lesson save(Lesson lesson);
	public void delete(int id) throws LessonNotFoundException;

}
