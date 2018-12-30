package it.unisalento.se.saw.Iservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.exceptions.LessonNotFoundException;

public interface ILessonService {
	public List<LessonDTO> getAll();
	public LessonDTO getById(int id) throws LessonNotFoundException;
	public LessonDTO save(LessonDTO lessonDTO);
	public void delete(int id) throws LessonNotFoundException;
	public List<TypeLessonDTO> getCurrentSchedulerByCourse(DegreeCourseDTO degreeCourseDTO, int idterm);
	public List<LessonDTO> getAllTeacherLessons(int idteacher);
	public List<FeedbackDTO> getFeedback(int idlesson);
	public List<LessonDTO> getAllLessonsByCourseAndTerm(int idcourse, int idterm);
	public void edit(ArrayList<LessonDTO> lessonDTOs);

}
