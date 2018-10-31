package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.ILessonService;
import it.unisalento.se.saw.domain.Lesson;
import it.unisalento.se.saw.exceptions.LessonNotFoundException;
import it.unisalento.se.saw.repositories.LessonRepository;


@Service
public class LessonService implements ILessonService{

	@Autowired
	LessonRepository lessonRepository;

	@Transactional(readOnly = true)
	public List<Lesson> getAll() {
		// TODO Auto-generated method stub
		return lessonRepository.findAll();
	}

	@Transactional(rollbackFor=LessonNotFoundException.class)
	public Lesson getById(int id) throws LessonNotFoundException {
		try {
			return lessonRepository.findById(id).get();
		} catch (Exception e) {
			throw new LessonNotFoundException();
		}
	}

	@Transactional
	public Lesson edit(Lesson lesson) throws LessonNotFoundException {
		try {
			lessonRepository.findById(lesson.getIdlesson()).get();
			return lessonRepository.save(lesson);
		} catch (Exception e) {
			throw new LessonNotFoundException();
		}
	}

	@Transactional
	public Lesson save(Lesson lesson) {
		// TODO Auto-generated method stub
		return lessonRepository.save(lesson);
	}

	@Transactional
	public void delete(int id) throws LessonNotFoundException {
		try {
			lessonRepository.deleteById(id);
		} catch (Exception e) {
			throw new LessonNotFoundException();
		}
	}
	
}
