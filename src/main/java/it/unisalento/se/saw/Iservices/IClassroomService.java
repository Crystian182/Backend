package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;

public interface IClassroomService {
	
	public List<Classroom> getAll();
	public Classroom getById(int idClassroom) throws ClassroomNotFoundException;
	public List<Classroom> getByIdBuilding(int idBuilding) throws ClassroomNotFoundException;
	public List<Classroom> getByIdBuildingAndName(int idBuilding, String name) throws ClassroomNotFoundException;
	public List<Classroom> getByName(String name) throws ClassroomNotFoundException;
	public Classroom edit(Classroom classroom) throws ClassroomNotFoundException;
	public Classroom save(Classroom classroom);
	public void delete(int idClassroom) throws ClassroomNotFoundException;

}
