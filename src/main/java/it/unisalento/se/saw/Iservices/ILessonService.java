package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.exceptions.LessonNotFoundException;

public interface ILessonService {
	public List<LessonDTO> getAll();
	public LessonDTO getById(int id) throws LessonNotFoundException;
	public LessonDTO save(LessonDTO lessonDTO);
	public void delete(int id) throws LessonNotFoundException;
	public List<TypeLessonDTO> getCurrentSchedulerByCourse(DegreeCourseDTO degreeCourseDTO);

}
