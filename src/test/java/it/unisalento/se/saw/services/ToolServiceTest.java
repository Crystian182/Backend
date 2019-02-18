package it.unisalento.se.saw.services;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.dialect.function.TemplateRenderer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import it.unisalento.se.saw.domain.AcademicYear;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.ClassroomHasTool;
import it.unisalento.se.saw.domain.ClassroomHasToolId;
import it.unisalento.se.saw.domain.CourseType;
import it.unisalento.se.saw.domain.Day;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Exam;
import it.unisalento.se.saw.domain.ExamStatus;
import it.unisalento.se.saw.domain.ExamType;
import it.unisalento.se.saw.domain.Feedback;
import it.unisalento.se.saw.domain.FeedbackLesson;
import it.unisalento.se.saw.domain.FeedbackLessonId;
import it.unisalento.se.saw.domain.Lesson;
import it.unisalento.se.saw.domain.Result;
import it.unisalento.se.saw.domain.Scheduler;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.domain.StudentHasExam;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.Teacher;
import it.unisalento.se.saw.domain.Term;
import it.unisalento.se.saw.domain.Tool;
import it.unisalento.se.saw.domain.TypeDegreeCourse;
import it.unisalento.se.saw.domain.TypeLesson;
import it.unisalento.se.saw.domain.TypeSubject;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.ExamDTO;
import it.unisalento.se.saw.dto.ExamEnrollmentDTO;
import it.unisalento.se.saw.dto.ExamTypeDTO;
import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.LessonDTO;
import it.unisalento.se.saw.dto.ResultDTO;
import it.unisalento.se.saw.dto.StudentDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TeacherDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.exceptions.InstrumentNotFoundException;
import it.unisalento.se.saw.exceptions.LessonNotFoundException;
import it.unisalento.se.saw.repositories.ClassroomHasToolRepository;
import it.unisalento.se.saw.repositories.FeedbackLessonRepository;
import it.unisalento.se.saw.repositories.FeedbackRepository;
import it.unisalento.se.saw.repositories.LessonRepository;
import it.unisalento.se.saw.repositories.ToolRepository;
import it.unisalento.se.saw.repositories.TypeLessonRepository;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.imageio.IIOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ToolServiceTest {
    
	@Mock
	ToolRepository toolRepository;
	
	@Mock
	ClassroomHasToolRepository classroomHasInstrumentsRepository;

    @InjectMocks
    ToolService toolService;

    @Test()
    public void getAllTest() throws IOException {
    	
    	List<Tool> tools = new ArrayList<Tool>();
    	
    	Tool tool = new Tool();
    	tool.setIdtool(1);
    	tool.setName("Proiettore");
    	tools.add(tool);
		
        when(toolRepository.findAll()).thenReturn(tools);

        List<ToolDTO> toolDTOs = toolService.getAll();
        assertEquals(tool.getName(), toolDTOs.get(0).getName());
    }
    
    @Test()
    public void deleteTest() throws IOException, InstrumentNotFoundException {
    	
    	Tool tool = new Tool();
    	tool.setIdtool(1);
    	tool.setName("Proiettore");
		
        when(toolRepository.findById(1)).thenReturn(Optional.of(tool));

        toolService.delete(1);
    }
    
    @Test()
    public void saveTest() throws IOException {
    	
    	ToolDTO toolDTO = new ToolDTO();
    	toolDTO.setId(1);
    	toolDTO.setName("Proiettore");
    	
    	Tool tool = new Tool();
    	tool.setIdtool(1);
    	tool.setName("Proiettore");
		
        when(toolRepository.save(any(Tool.class))).thenReturn(tool);

        ToolDTO savedToolDTO = toolService.save(toolDTO);
        assertEquals(tool.getName(), savedToolDTO.getName());
    }

}