package it.unisalento.se.saw.Iservices;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;

public interface IClassroomService {
	
	public List<ClassroomDTO> getAll();
	public ClassroomDTO getById(int idClassroom) throws ClassroomNotFoundException;
	public List<ClassroomDTO> getByIdBuilding(int idBuilding) throws ClassroomNotFoundException;
	public List<ClassroomDTO> getByIdBuildingAndName(int idBuilding, String name) throws ClassroomNotFoundException;
	public List<ClassroomDTO> getByName(String name) throws ClassroomNotFoundException;
	public ClassroomDTO save(ClassroomDTO classroomDTO);
	public void delete(int idClassroom) throws ClassroomNotFoundException;
	public List<ClassroomDTO> getAvailableByIdBuilding(int idBuilding, TypeLessonDTO typeLessonDTO) throws ClassroomNotFoundException;

}
