package it.unisalento.se.saw.Iservices;

import java.text.ParseException;
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
	public List<TypeLessonDTO> getCurrentSchedulerTeacher(int idteacher);
	public List<LessonDTO> getAllTeacherLessons(int idteacher);
	public List<FeedbackDTO> getFeedback(int idlesson);
	public List<LessonDTO> getAllLessonsByCourseAndTerm(int idcourse, int idterm);
	public List<LessonDTO> searchLessons(int idcourse, int idterm, int idsubject, String from, String to) throws ParseException;
	public List<LessonDTO> searchTeacherLessons(int idsubject, String from, String to) throws ParseException;
	public List<LessonDTO> getTodayLessons(int iduser);
	public List<LessonDTO> getTeacherTodayLessons(int iduser);
	public void edit(List<LessonDTO> lessonDTOs);
	public void saveFeedback(int idlesson, FeedbackDTO feedbackDTO);

}
